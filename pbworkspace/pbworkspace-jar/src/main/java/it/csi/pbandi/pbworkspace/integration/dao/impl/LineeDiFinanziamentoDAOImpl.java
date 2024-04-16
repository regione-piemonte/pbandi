/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.MessaggiConstants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.util.UseCaseConstants;
import it.csi.pbandi.pbworkspace.dto.BandoProcesso;
import it.csi.pbandi.pbworkspace.dto.EsitoAvviaProgettiDTO;
import it.csi.pbandi.pbworkspace.dto.InizializzaLineeDiFinanziamentoDTO;
import it.csi.pbandi.pbworkspace.dto.InizializzaSchedaProgettoDTO;
import it.csi.pbandi.pbworkspace.dto.Progetto;
import it.csi.pbandi.pbworkspace.exception.ErroreGestitoException;
import it.csi.pbandi.pbworkspace.integration.dao.LineeDiFinanziamentoDAO;
import it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbworkspace.integration.request.AvviaProgettiRequest;
import it.csi.pbandi.pbworkspace.integration.request.RicercaProgettiRequest;
import it.csi.pbandi.pbworkspace.util.BeanUtil;
import it.csi.pbandi.pbworkspace.util.Constants;
import it.csi.pbandi.pbworkspace.util.TraduttoreMessaggiPbandisrv;


@Service
public class LineeDiFinanziamentoDAOImpl extends JdbcDaoSupport implements LineeDiFinanziamentoDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	protected BeanUtil beanUtil;
	
	@Autowired
	it.csi.pbandi.pbworkspace.pbandisrv.business.WorkspaceBusinessImpl workspaceBusinessImpl;
	
	@Autowired
	it.csi.pbandi.pbworkspace.pbandisrv.business.GestioneAvvioProgettoBusinessImpl gestioneAvvioProgettoBusinessImpl;
	
	@Autowired
	it.csi.pbandi.pbservizit.pbandiutil.commonweb.UserInfoHelper userInfoHelper;
	
	@Autowired
	it.csi.pbandi.pbservizit.pbandiutil.common.TimerUtil timerUtil;
	
	@Autowired
	it.csi.pbandi.pbservizit.integration.dao.impl.ProfilazioneDAOImpl profilazioneDAOImpl;	
	
	@Autowired
	public LineeDiFinanziamentoDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public LineeDiFinanziamentoDAOImpl() {
	}
	
	@Override
	public InizializzaLineeDiFinanziamentoDTO inizializzaLineeDiFinanziamento(Long progrBandoLineaIntervento, Long idSoggetto, String codiceRuolo,Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[LineeDiFinanziamentoDAOImpl::inizializzaLineeDiFinanziamento] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idSoggetto = "+idSoggetto+"; codiceRuolo = "+codiceRuolo+"; idUtente = "+idUtente);
		
		if (progrBandoLineaIntervento == null) {
			throw new InvalidParameterException("progrBandoLineaIntervento non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		
		InizializzaLineeDiFinanziamentoDTO result = new InizializzaLineeDiFinanziamentoDTO();
		try {
			
			// Se true, i bottono di inserimento e modifica progetto sono visibili.
			result.setInserimentoModificaProgettoAbilitati(profilazioneDAOImpl.hasPermesso(idSoggetto, idUtente, codiceRuolo, UseCaseConstants.UC_TRSCSP001));
			
			// Se true, il bottone di creazione progetto va nascosto.
			result.setEsistonoProgettiSifAvviati(gestioneAvvioProgettoBusinessImpl.hasProgettiSifAvviati(idUtente, idIride, progrBandoLineaIntervento));
			
			String sqlNome = "SELECT NOME_BANDO_LINEA FROM PBANDI_R_BANDO_LINEA_INTERVENT WHERE PROGR_BANDO_LINEA_INTERVENTO = "+progrBandoLineaIntervento;
			LOG.info("\n"+sqlNome);			
			String nomeBando = (String) getJdbcTemplate().queryForObject(sqlNome, String.class);
			result.setNomeBandoLinea(nomeBando);
			
		} catch (Exception e) {
			String msg = "Errore nella inizializzazione delle linee di finanziamento.";
			LOG.error(prf + msg, e);
			throw new ErroreGestitoException(msg, e);
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	public List<BandoProcesso> lineeDiFinanziamento(String descrizione, UserInfoSec userInfo) throws InvalidParameterException, Exception {
		String prf = "[LineeDiFinanziamentoDAOImpl::lineeDiFinanziamento] ";
		LOG.info(prf + " BEGIN");
		
		if (userInfo == null) {
			throw new InvalidParameterException("userInfo non valorizzato.");
		}
		
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
		LOG.info(prf + "descrizione = "+descrizione+"; idUtente = "+idUtente+"; idIride = "+idIride);
		
		//Esempio di codUtente = "2130090_AAAAAA00A11O000W@CREATOR";		
		String codUtente = userInfoHelper.getCodUtenteFlux(userInfo);
		LOG.info(prf + "codUtente = "+codUtente);
		
		it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.DefinizioneProcessoDTO definizioneProcesso = new it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.DefinizioneProcessoDTO();
		definizioneProcesso.setDescr(descrizione);
		
		it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.DefinizioneProcessoDTO[] definizioniProcesso;
		definizioniProcesso = workspaceBusinessImpl.ricercaDefinizioneProcesso(idUtente, idIride, codUtente, definizioneProcesso);
		if(definizioniProcesso!=null) logger.info("definizioniProcesso.length: "+definizioniProcesso.length);
			
		ArrayList<BandoProcesso> elencoBandoProcesso = new ArrayList<BandoProcesso>();
		for (it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.DefinizioneProcessoDTO def : definizioniProcesso) {
			BandoProcesso b = new BandoProcesso();
			b.setId(def.getId());
			b.setNome(def.getDescr());
			b.setProcesso(def.getIdProcesso());
			elencoBandoProcesso.add(b);
		}
		
		return elencoBandoProcesso;
	}
	
	@Override
	// Ex GestioneAvvioProgettoBusinessImpl.ricercaProgetti()
	public List<Progetto> ricercaProgetti(RicercaProgettiRequest ricercaProgettiRequest, UserInfoSec userInfo) throws InvalidParameterException, Exception {
		String prf = "[LineeDiFinanziamentoDAOImpl::ricercaProgetti] ";
		LOG.info(prf + " BEGIN");
		
		if (ricercaProgettiRequest == null) {
			throw new InvalidParameterException("ricercaProgettiRequest non valorizzato.");
		}
		if (ricercaProgettiRequest.getIdSoggetto() == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(ricercaProgettiRequest.getCodiceRuolo())) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (userInfo == null) {
			throw new InvalidParameterException("userInfo non valorizzato.");
		}
		
		LOG.info(prf + "idUtente = "+userInfo.getIdUtente());
		LOG.info(prf + ricercaProgettiRequest);
		
		List<Progetto> result = new ArrayList<Progetto>();
		try {
			
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			// Popola il filtro di ricerca da passare a pbandisrv.
			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.ProgettoDTO progetto = new it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.ProgettoDTO();
			progetto.setBeneficiario(ricercaProgettiRequest.getBeneficiario());
			progetto.setCodice(ricercaProgettiRequest.getCodiceProgetto());
			progetto.setCup(ricercaProgettiRequest.getCup());
			progetto.setId(null);									// immagino sia l'id progetto.
			if (ricercaProgettiRequest.getProgrBandoLineaIntervento() != null)
				progetto.setIdBandoLinea(ricercaProgettiRequest.getProgrBandoLineaIntervento().toString());
			progetto.setTitolo(ricercaProgettiRequest.getTitoloProgetto());
			
			String codUtente = userInfoHelper.getCodUtenteFlux(userInfo);
			Boolean abilitatoUC_TRSCSP001 = profilazioneDAOImpl.hasPermesso(ricercaProgettiRequest.getIdSoggetto(), idUtente, ricercaProgettiRequest.getCodiceRuolo(), UseCaseConstants.UC_TRSCSP001);
			LOG.info(prf + "codUtente = "+codUtente+"; abilitatoUC_TRSCSP001 = "+abilitatoUC_TRSCSP001);
			
			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.ProgettoDTO[] progettiTrovati;
			progettiTrovati = gestioneAvvioProgettoBusinessImpl.findProgetti(idUtente, idIride, codUtente, progetto, abilitatoUC_TRSCSP001);
			
			Progetto[] progettiConvertiti = new Progetto[progettiTrovati.length];
			beanUtil.valueCopy(progettiTrovati, progettiConvertiti);
			result.addAll(Arrays.asList(progettiConvertiti));
			
		} catch (Exception e) {
			String msg = "Errore nella ricerca dei progetti.";
			LOG.error(prf + msg, e);
			throw new ErroreGestitoException(msg, e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return result;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class})
	// Ex GestioneAvvioProgettoBusinessImpl.avviaProgetti()
	public EsitoAvviaProgettiDTO avviaProgetti(AvviaProgettiRequest avviaProgettiRequest, UserInfoSec userInfo) throws InvalidParameterException, Exception {
		String prf = "[LineeDiFinanziamentoDAOImpl::avviaProgetti] ";
		LOG.info(prf + " BEGIN");
		
		if (avviaProgettiRequest == null) {
			throw new InvalidParameterException("avviaProgettiRequest non valorizzato.");
		}
		if (avviaProgettiRequest.getIdSoggetto() == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(avviaProgettiRequest.getCodiceRuolo())) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (userInfo == null) {
			throw new InvalidParameterException("userInfo non valorizzato.");
		}
		
		LOG.info(prf + "idUtente = "+userInfo.getIdUtente());
		LOG.info(prf + avviaProgettiRequest.toString());
		
		EsitoAvviaProgettiDTO result = new EsitoAvviaProgettiDTO();
		try {
			
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			String codUtente = userInfoHelper.getCodUtenteFlux(userInfo);
			Boolean abilitatoUC_TRSCSP001 = profilazioneDAOImpl.hasPermesso(avviaProgettiRequest.getIdSoggetto(), idUtente, avviaProgettiRequest.getCodiceRuolo(), UseCaseConstants.UC_TRSCSP001);
			LOG.info(prf + "codUtente = "+codUtente+"; abilitatoUC_TRSCSP001 = "+abilitatoUC_TRSCSP001);
			
			// Recupero il timeout per l'avvio del progetto configurato sul db
			Long timeoutAvvioProgetti = 55000L;
			String timeoutDb = this.costante("conf.timeoutAvvioProgetto", false);
			if (!StringUtils.isBlank(timeoutDb))
				timeoutAvvioProgetti = new Long(timeoutDb);
			LOG.info(prf + "Timeout per avvio progetti: "+timeoutAvvioProgetti);
			
			Map<?, Progetto> progettiPerId = beanUtil.index(avviaProgettiRequest.getProgettiDaAvviare(), "id");
			
			List<it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoAvvioProcessoDTO> messaggiRitornati = new ArrayList<it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoAvvioProcessoDTO>();
			
			Long startTime = timerUtil.start();
			for (Progetto progettoDaAvviare : avviaProgettiRequest.getProgettiDaAvviare()) {
				Long idProgetto = progettoDaAvviare.getId();
				if (timerUtil.checkTimeout(timeoutAvvioProgetti, startTime)) {
					it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoAvvioProcessoDTO[] messaggiRitornatiArray;
					messaggiRitornatiArray = gestioneAvvioProgettoBusinessImpl.avviaProgetto(idUtente, idIride, codUtente, 
							avviaProgettiRequest.getProgrBandoLineaIntervento().toString(), 
							new Long[] { idProgetto },
							abilitatoUC_TRSCSP001);
					for (it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoAvvioProcessoDTO esitoAvvioProcessoDTO : messaggiRitornatiArray) {
						messaggiRitornati.add(esitoAvvioProcessoDTO);
					}
				} else {
					it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoAvvioProcessoDTO esitoAvvioProcessoDTO = new it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoAvvioProcessoDTO();
					esitoAvvioProcessoDTO.setCodiceMessaggio(MessaggiConstants.TIMEOUT_AVVIO_PROCESSO);
					Progetto progetto = progettiPerId.get(idProgetto);
					esitoAvvioProcessoDTO.setCodiceProgetto(ObjectUtil.nvl(progetto
							.getCodice(), progetto.getTitolo()));
					messaggiRitornati.add(esitoAvvioProcessoDTO);
				}
			}

			Map<String, Integer> totaliPerMessaggio = new HashMap<String, Integer>();
			Map<String, StringBuilder> progettiPerMessaggio = new HashMap<String, StringBuilder>();

			for (it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoAvvioProcessoDTO esitoAvvioProcesso : messaggiRitornati) {
				String codiceMessaggio = decodeMessageKey(esitoAvvioProcesso
						.getCodiceMessaggio(), 1);

				Integer tot = totaliPerMessaggio.get(codiceMessaggio);
				tot = tot == null ? 1 : tot + 1;
				totaliPerMessaggio.put(codiceMessaggio, tot);

				StringBuilder progetti = progettiPerMessaggio.get(codiceMessaggio);
				progetti = progetti == null ? new StringBuilder(esitoAvvioProcesso
						.getCodiceProgetto()) : progetti.append(", "
						+ esitoAvvioProcesso.getCodiceProgetto());
				progettiPerMessaggio.put(codiceMessaggio, progetti);
			}

			for (String keyMessaggio : progettiPerMessaggio.keySet()) {
				//String message = messageManager.getMessage(decMsgKey, progettiPerMessaggio.get(keyMessaggio).toString());
				String decMsgKey = decodeMessageKey(keyMessaggio, totaliPerMessaggio.get(keyMessaggio));
				String param = progettiPerMessaggio.get(keyMessaggio).toString();
				String message = TraduttoreMessaggiPbandisrv.traduci(decMsgKey, new String[] { param });
				if (isError(keyMessaggio)) {
					result.getErrori().add(message);
				} else {
					result.getMessaggi().add(message);
				}
			}
			
			LOG.info(prf+result.toString());
			
		} catch (Exception e) {
			String msg = "Errore nell'avvio dei progetti.";
			LOG.error(prf + msg, e);
			throw new ErroreGestitoException(msg, e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return result;
	}
	
	private boolean isError(String keyMessaggio) {
		return !MessaggiConstants.PROCESSI_AVVIATI
		.equals(keyMessaggio) && !MessaggiConstants.PROCESSO_AVVIATO
		.equals(keyMessaggio);
	}
	
	// Restituisce il campo PBANDI_C_COSTANTI.VALORE in base al campo ATTRIBUTO.
	// Se non la trova e obbligatoria = true, innalza una eccezione.
	private String costante(String attributo, boolean obbligatoria) throws Exception {
		String prf = "[DecodificheDAOImpl::costante()] ";
		LOG.info(prf + "BEGIN");
		String valore = null;
		try {
			
			String sql = "SELECT VALORE FROM PBANDI_C_COSTANTI WHERE ATTRIBUTO = '"+attributo+"'";
			LOG.debug("\n"+sql.toString());
			
			valore = (String) getJdbcTemplate().queryForObject(sql.toString(), String.class);
			
			if (obbligatoria && StringUtils.isBlank(valore)) {
				throw new Exception("Costante "+attributo+" non trovata.");
			}
			
		} catch (Exception e) {
			LOG.error(prf+"ERRORE nella ricerca di una COSTANTE: ", e);
			throw new Exception("ERRORE nella ricerca della costante "+attributo+".", e);
		}
		finally {
			LOG.info(prf+"END");
		}
		
		return valore;
	}
	
	private String decodeMessageKey(String codiceMessaggio, int i) {
		String result = codiceMessaggio;
		if (i == 1) {
			if (MessaggiConstants.PROCESSI_NON_AVVIATI.equals(codiceMessaggio)) {
				result = MessaggiConstants.PROCESSO_NON_AVVIATO;
			} else if (MessaggiConstants.PROCESSI_AVVIATI
					.equals(codiceMessaggio)) {
				result = MessaggiConstants.PROCESSO_AVVIATO;
			} else if (MessaggiConstants.CSP_PROGETTI_INCOMPLETI
					.equals(codiceMessaggio)) {
				result = MessaggiConstants.CSP_PROGETTO_INCOMPLETO;
			} else if (MessaggiConstants.TIMEOUT_AVVIO_PROCESSI
					.equals(codiceMessaggio)) {
				result = MessaggiConstants.TIMEOUT_AVVIO_PROCESSO;
			}
		} else {
			if (MessaggiConstants.PROCESSO_NON_AVVIATO.equals(codiceMessaggio)) {
				result = MessaggiConstants.PROCESSI_NON_AVVIATI;
			} else if (MessaggiConstants.PROCESSO_AVVIATO
					.equals(codiceMessaggio)) {
				result = MessaggiConstants.PROCESSI_AVVIATI;
			} else if (MessaggiConstants.CSP_PROGETTO_INCOMPLETO
					.equals(codiceMessaggio)) {
				result = MessaggiConstants.CSP_PROGETTI_INCOMPLETI;
			} else if (MessaggiConstants.TIMEOUT_AVVIO_PROCESSO
					.equals(codiceMessaggio)) {
				result = MessaggiConstants.TIMEOUT_AVVIO_PROCESSI;
			}
		}
		return result;
	}
	
}
