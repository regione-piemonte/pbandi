/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.business.intf.MessageKey;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.FileDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoInvioPropostaRimodulazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.EsitoOperazioneSalvaTipoAllegati;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbwebrce.dto.CodiceDescrizione;
import it.csi.pbandi.pbwebrce.dto.ContoEconomico;
import it.csi.pbandi.pbwebrce.dto.ContoEconomicoItem;
import it.csi.pbandi.pbwebrce.dto.DocumentoAllegato;
import it.csi.pbandi.pbwebrce.dto.EsitoOperazioni;
import it.csi.pbandi.pbwebrce.dto.FiltroProcedureAggiudicazione;
import it.csi.pbandi.pbwebrce.dto.FinanziamentoFonteFinanziaria;
import it.csi.pbandi.pbwebrce.dto.FonteFinanziaria;
import it.csi.pbandi.pbwebrce.dto.ModalitaAgevolazione;
import it.csi.pbandi.pbwebrce.dto.ProceduraAggiudicazione;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.AltriCostiDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.EsitoInviaPropostaRimodulazioneDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.EsitoSalvaPropostaRimodulazioneDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.EsitoSalvaRimodulazioneConfermataDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.EsitoSalvaRimodulazioneDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.EsitoValidaRimodulazioneConfermataDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.InizializzaConcludiPropostaRimodulazioneDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.InizializzaConcludiRichiestaContoEconomicoDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.InizializzaConcludiRimodulazioneDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.InizializzaPropostaRimodulazioneDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.InizializzaRimodulazioneDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.InizializzaRimodulazioneIstruttoriaDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.InizializzaUploadFileFirmatoDTO;
import it.csi.pbandi.pbwebrce.integration.dao.ContoEconomicoDAO;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.AltriCostiRowMapper;
import it.csi.pbandi.pbwebrce.integration.request.InviaPropostaRimodulazioneRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaPropostaRimodulazioneRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaRichiestaContoEconomicoRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaRimodulazioneConfermataRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaRimodulazioneRequest;
import it.csi.pbandi.pbwebrce.integration.request.ValidaRimodulazioneConfermataRequest;
import it.csi.pbandi.pbwebrce.pbandisrv.business.contoeconomico.ContoEconomicoException;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbwebrce.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbwebrce.util.BeanUtil;
import it.csi.pbandi.pbwebrce.util.Constants;
import it.csi.pbandi.pbwebrce.util.DateUtil;
import it.csi.pbandi.pbwebrce.util.FileUtil;
import it.csi.pbandi.pbwebrce.util.NumberUtil;
import it.csi.pbandi.pbwebrce.util.StringUtil;
import it.csi.pbandi.pbwebrce.util.TraduttoreMessaggiPbandisrv;

@Component
public class ContoEconomicoDAOImpl extends JdbcDaoSupport implements ContoEconomicoDAO {
	
	private static String CONTO_ECONOMICO_LOCKED_PER_PROPOSTA = "Il conto economico del progetto risulta in fase di rimodulazione da parte dell\u2019 istruttore. <br />Non \u00E8 quindi al momento possibile effettuare una nuova proposta. <br />Attendere la conclusione della rimodulazione dell\u2019 istruttore prima di effettuare una nuova proposta di rimodulazione";
	private static String CONTO_ECONOMICO_LOCKED_PER_RIMODULAZIONE = "Il conto economico del progetto risulta in fase di nuova proposta da parte del beneficiario. <br />Non \u00E8 quindi al momento possibile effettuare una rimodulazione della spesa ammessa. <br />Attendere l\u2019invio della nuova proposta del beneficiario prima di effettuare la rimodulazione della spesa ammessa.";
	private static String PROPOSTA_SALVATA = "I dati della nuova proposta di rimodulazione del conto economico sono stati salvati correttamente. <br />E\u2019 possibile modificarli nuovamente oppure procedere con l\u2019invio della proposta.";
	private static String RIMODULAZIONE_SALVATA = "I dati della nuova rimodulazione del conto economico sono stati salvati correttamente. E\u2019  possibile modificarli nuovamente oppure procedere con la conclusione dell\u2019 attivit\u00E0 di rimodulazione.";
	private static String RICHIESTO_IN_DOMANDA_SALVATO = "I dati della  richiesta del conto economico in domanda sono stati salvati correttamente. E\u2019 possibile modificarli nuovamente oppure procedere con l\u2019 invio della richiesta.";
	private static String ERRORE_GENERICO = "Si \u00E8 verificato un errore.";
	private static String RICHIESTA_CONTOECONOMICO_INVIATA = "La richiesta del conto economico in domanda \u00E8 stata inviata correttamente.";
	
//	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	@Autowired
	protected LoggerUtil LOG;
	
	@Autowired
	protected BeanUtil beanUtil;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	public ContoEconomicoDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);		
		this.genericDAO = new GenericDAO(dataSource);
	}
	
	@Autowired
	it.csi.pbandi.pbwebrce.business.manager.ContoEconomicoManager contoEconomicoManager;
	
	@Autowired
	it.csi.pbandi.pbwebrce.business.manager.ContoEconomicoIstruttoriaManager contoEconomicoIstruttoriaManager;
	
	@Autowired
	it.csi.pbandi.pbwebrce.business.manager.ContoEconomicoInDomandaManager contoEconomicoInDomandaManager;
	
	@Autowired
	it.csi.pbandi.pbwebrce.pbandisrv.business.contoeconomico.ContoeconomicoBusinessImpl contoeconomicoBusinessImpl;
	
	@Autowired
	it.csi.pbandi.pbservizit.pbandisrv.business.profilazione.ProfilazioneBusinessImpl profilazioneBusinessImpl;
	
	@Autowired
	it.csi.pbandi.pbservizit.pbandisrv.business.manager.RappresentanteLegaleManager rappresentanteLegaleManager;
	
	@Autowired
	it.csi.pbandi.pbservizit.pbandisrv.business.manager.CertificazioneManager certificazioneManager;
	
	@Autowired
	it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusinessImpl;
	
	@Autowired
	it.csi.pbandi.pbservizit.pbandisrv.business.archivio.ArchivioBusinessImpl archivioBusinessImpl;
	
	@Autowired
	it.csi.pbandi.pbservizit.integration.dao.impl.ArchivioFileDAOImpl archivioFileDAOImpl;
	
	@Autowired
	it.csi.pbandi.pbservizit.business.manager.DocumentoManager documentoManager;
	
	@Autowired
	it.csi.pbandi.pbwebrce.pbandisrv.business.digitalsign.DigitalSignBusinessImpl digitalSignBusinessImpl;
	
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl gestioneDatiDiDominioBusinessImpl;
	
	@Override
	public InizializzaPropostaRimodulazioneDTO inizializzaPropostaRimodulazione(Long idProgetto, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::inizializzaPropostaRimodulazione] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + "idProgetto = "+idProgetto+"; idUtente = "+idUtente);
				
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		InizializzaPropostaRimodulazioneDTO result = new InizializzaPropostaRimodulazioneDTO();
		try {
			
			// Codice visualizzato del progetto.
			String sql = "SELECT CODICE_VISUALIZZATO FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = ?";
			Object[] param = new Object[] {idProgetto};
			String codiceProgetto = getJdbcTemplate().queryForObject(sql, param, String.class);
			result.setCodiceVisualizzatoProgetto(codiceProgetto);
			
			// CONTO ECONOMICO LETTO DA DB.
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO  esito;
			esito = this.findContoEconomicoPerPropostaRimodulazione(idProgetto,idUtente, idIride);
			result.setEsitoFindContoEconomicoDTO(esito);
			
			// DATI DEL CONTO ECONOMICO.
			
			ContoEconomico conto = this.getDatiContoEconomico(esito, false);
			result.setDatiContoEconomico(conto);
			
			// RIGHE DEL CONTO ECONOMICO PER VISUALIZZAZIONE.
			
			boolean isRimodulazione = false;
			ArrayList<ContoEconomicoItem> righe = this.getContoEconomicoItem(conto, esito, false, isRimodulazione);
			result.setRigheContoEconomico(righe);
			
			logger.info(prf + result.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return result;
	}
	
	private it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO 
			findContoEconomicoPerPropostaRimodulazione(Long idProgetto, Long idUtente, String idIride) 
					throws ContoEconomicoException, SystemException, UnrecoverableException, CSIException {
		
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO progetto = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO();
		progetto.setIdProgetto(idProgetto);
		
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esito;
		esito = contoeconomicoBusinessImpl.findContoEconomicoPerPropostaRimodulazione(idUtente,	idIride, progetto);
		
		return esito;		
	}
	
	private it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO 
		findContoEconomicoPerRimodulazione(Long idProgetto, Long idUtente, String idIride) 
				throws ContoEconomicoException, SystemException, UnrecoverableException, CSIException {
	
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO progetto = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO();
		progetto.setIdProgetto(idProgetto);
		
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esito;
		esito = contoeconomicoBusinessImpl.findContoEconomicoPerRimodulazione(idUtente,	idIride, progetto);
		
		return esito;		
	}
		
	private void nascondiColonnaNuovaProposta(ContoEconomico conto, boolean nascondi) {
		conto.setNascondiColonnaImportoRichiesto(nascondi);
	}
	
	// Al momento usato per la Proposta di Rimodulazione del Beneficiario.
	private ContoEconomico getDatiContoEconomico(
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO, boolean isModifica) throws Exception {

		Boolean copiaPresente = esitoFindContoEconomicoDTO.getCopiaModificataPresente();

		ContoEconomico contoEconomico = new ContoEconomico();

		contoEconomico.setHasCopiaPresente(copiaPresente);

		contoEconomico.setDataFineIstruttoria(DateUtil.getDate(esitoFindContoEconomicoDTO.getDataFineIstruttoria()));

		contoEconomico.setDataPresentazioneDomanda(DateUtil.getDate(esitoFindContoEconomicoDTO.getDataPresentazioneDomanda()));

		contoEconomico.setDataUltimaProposta(DateUtil.getDate(esitoFindContoEconomicoDTO.getDataUltimaProposta()));
		
		contoEconomico.setDataUltimaRimodulazione(DateUtil.getDate(esitoFindContoEconomicoDTO.getDataUltimaRimodulazione()));

		return contoEconomico;		
	}
	
	// Al momento usato per la Proposta di Rimodulazione del Beneficiario e la Rimodulazione.
	private ArrayList<ContoEconomicoItem> getContoEconomicoItem(
			ContoEconomico contoEconomico, 
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO, 
			boolean isModifica, Boolean isRimodulazione) throws Exception {
		
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione;
		contoEconomicoRimodulazione = esitoFindContoEconomicoDTO.getContoEconomico();
		
		Boolean copiaPresente = esitoFindContoEconomicoDTO.getCopiaModificataPresente();

		ArrayList<ContoEconomicoItem> contoEconomicoItemList = new ArrayList<ContoEconomicoItem>();
		contoEconomicoItemList = contoEconomicoManager.mappaContoEconomicoPerVisualizzazione(
				contoEconomico,
				contoEconomicoRimodulazione, contoEconomicoItemList, copiaPresente, isModifica, 
				esitoFindContoEconomicoDTO.getDataFineIstruttoria(), 
				esitoFindContoEconomicoDTO.getInIstruttoria(),
				isRimodulazione);
		
		return contoEconomicoItemList;
	}

	// Ribasso d'asta non più gestito.
	// Ribasso d'asta. Se il flagRibassoAsta e' uguale a S, allora calcolo la percentuale del ribasso e la visualizzo.
	/*
	private void gestioneRibassoAsta(
			InizializzaPropostaRimodulazioneDTO inizializzaPropostaRimodulazioneDTO,
			ArrayList<ContoEconomicoItem> itemsContoEconomico,
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO) {
				
		if (esitoFindContoEconomicoDTO.getContoEconomico() != null && 
			esitoFindContoEconomicoDTO.getContoEconomico().getFlagRibassoAsta() != null && 
			esitoFindContoEconomicoDTO.getContoEconomico().getFlagRibassoAsta()
				.equals(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE)) {
			
			inizializzaPropostaRimodulazioneDTO.setFlagRibassoAsta(Boolean.TRUE);
			//theModel.setAppDataflagRibassoAsta(Boolean.TRUE);
			
			BigDecimal percentuale = contoEconomicoManager.calcolaPercentualeInPropostaRimodulazione(itemsContoEconomico);
			inizializzaPropostaRimodulazioneDTO.setPercRibassoAstaInProposta(NumberUtil.toDouble(percentuale));
			//theModel.setAppDatapercRibassoAstaInProposta(NumberUtil.toDouble(percentuale));
			
			// Calcolo la differenza tra totale nuovo richiesto ed il totale spesa ammessa.
			BigDecimal differenza = calcolaImpostaDifferenza(itemsContoEconomico, itemsContoEconomico.get(0));
			inizializzaPropostaRimodulazioneDTO.setImportoRibassoAsta(NumberUtil.toDouble(differenza));
			//theModel.setAppDataimportoRibassoAsta(NumberUtil.toDouble(differenza));

		} else {
			inizializzaPropostaRimodulazioneDTO.setFlagRibassoAsta(Boolean.FALSE);
			//theModel.setAppDataflagRibassoAsta(Boolean.FALSE);
			inizializzaPropostaRimodulazioneDTO.setPercRibassoAstaInProposta(null);
			//theModel.setAppDatapercRibassoAstaInProposta(null);
		}
		 
		// Gestione del flag ribasso d'asta relativo all' ultima proposta.
		if (esitoFindContoEconomicoDTO != null && 
			it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE
				.equals(esitoFindContoEconomicoDTO.getFlagUltimoRibassoAstaInProposta())) {
			inizializzaPropostaRimodulazioneDTO.setFlagUltimoRibassoAstaInProposta(true);
			//theModel.setAppDataflagUltimoRibassoAstaInProposta(true);
		} else {
			inizializzaPropostaRimodulazioneDTO.setFlagUltimoRibassoAstaInProposta(false);
			//theModel.setAppDataflagUltimoRibassoAstaInProposta(false);
		}
 
		// Gestione del flag ribasso d'asta relativo all' ultima rimodulazione.
		if (esitoFindContoEconomicoDTO != null && 
				it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE
					.equals(esitoFindContoEconomicoDTO.getFlagUltimoRibassoAstaInRimodulazione())) {
			inizializzaPropostaRimodulazioneDTO.setFlagUltimoRibassoAstaInRimodulazione(true);
			//theModel.setAppDataflagUltimoRibassoAstaInRimodulazione(true);
		} else {
			inizializzaPropostaRimodulazioneDTO.setFlagUltimoRibassoAstaInRimodulazione(false);
			//theModel.setAppDataflagUltimoRibassoAstaInRimodulazione(false);
		}
	}
	*/
	
	private BigDecimal calcolaImpostaDifferenza(
			ArrayList<ContoEconomicoItem> itemsContoEconomico,
			ContoEconomicoItem rigaDifferenza) {
		BigDecimal differenza = contoEconomicoManager.calcolaDifferenzaInPropostaRimodulazione(itemsContoEconomico);
		contoEconomicoManager.impostaDifferenzaInPropostaRimodulazione(rigaDifferenza, differenza);
		return differenza;
	}
	
	@Override
	public Boolean contoEconomicoLocked(Long idProgetto, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::contoEconomicoLocked] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + "idProgetto = "+idProgetto+"; idUtente = "+idUtente);
				
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		Boolean result = new Boolean(false);
		try {
			
			// CONTO ECONOMICO LETTO DA DB.
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esito;
			esito = this.findContoEconomicoPerPropostaRimodulazione(idProgetto,idUtente, idIride);
			
			result = esito.getLocked();
					
			logger.info(prf + "contoEconomicoLocked = " + result.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return result;
	}
	
	@Override
	public EsitoSalvaPropostaRimodulazioneDTO salvaPropostaRimodulazione(SalvaPropostaRimodulazioneRequest salvaPropostaRimodulazioneRequest, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::salvaPropostaRimodulazione] ";
		logger.info(prf + " BEGIN");		
				
		if (salvaPropostaRimodulazioneRequest == null) {
			throw new InvalidParameterException("salvaPropostaRimodulazioneRequest non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		Long idProgetto = salvaPropostaRimodulazioneRequest.getIdProgetto();
		ArrayList<ContoEconomicoItem> contoEconomicoItemList = salvaPropostaRimodulazioneRequest.getContoEconomicoItemList();
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (contoEconomicoItemList == null) {
			throw new InvalidParameterException("contoEconomicoItemList non valorizzato.");
		}
		logger.info(prf + "idProgetto = "+idProgetto+"; contoEconomicoItemList.size = "+contoEconomicoItemList.size()+"; idUtente = "+idUtente);
		
		EsitoSalvaPropostaRimodulazioneDTO esitoSalva = new EsitoSalvaPropostaRimodulazioneDTO();
		try {
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO;
			esitoFindContoEconomicoDTO = this.findContoEconomicoPerPropostaRimodulazione(idProgetto,idUtente, idIride);
			
			// NOTA: 
			//  - contoEconomicoItemList: conto economico modificato a video e da salvare.
			//  - esitoFindContoEconomicoDTO: conto economico letto da db.
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione;
			contoEconomicoRimodulazione = esitoFindContoEconomicoDTO.getContoEconomico();
			//theModel.getSession().put(CONTO_ECONOMICO_DTO,contoEconomicoRimodulazione);
		
			Boolean copiaPresente = esitoFindContoEconomicoDTO.getCopiaModificataPresente();		
			//theModel.getSession().put(COPIA_PRESENTE, copiaPresente);
		
			boolean isModifica = false;
			boolean isRimodulazione = false;
			
			ContoEconomico datiContoEconomico = this.getDatiContoEconomico(esitoFindContoEconomicoDTO, false);
			
			contoEconomicoItemList = contoEconomicoManager.mappaContoEconomicoPerVisualizzazione(
					datiContoEconomico,
					contoEconomicoRimodulazione, contoEconomicoItemList, copiaPresente, isModifica, 
					esitoFindContoEconomicoDTO.getDataFineIstruttoria(), 
					esitoFindContoEconomicoDTO.getInIstruttoria(),
					isRimodulazione);
		
			if (esitoFindContoEconomicoDTO.getLocked()) {
				logger.info("Conto economico LOCKED");
				esitoSalva.setEsito(false);
				esitoSalva.getMessaggi().add(CONTO_ECONOMICO_LOCKED_PER_PROPOSTA);
			} else {
				logger.info("CONTROLLI VALIDAZIONE , prendo i dati del conto economico dal web e li confronto con quelli arrivati dal server");
		
				Map<?, ContoEconomicoItem> mapContoEconomicoItem = beanUtil.index(contoEconomicoItemList, "idVoce");
		
				Set<String> set = new HashSet<String>();
		
				// Viene restituito un elenco di CODICI di messaggi di errore\warning.
				set = contoEconomicoManager.controllaImportiPerProposta(mapContoEconomicoItem, contoEconomicoRimodulazione, set);
		
				Collection<String> messaggi = new ArrayList<String>(set);
		
				nascondiColonnaNuovaProposta(datiContoEconomico, false);
				
				if (messaggi.contains(MessageKey.IMPORTI_NON_VALIDI)) {					
		
					//result.setResultCode("KO");		
					//messageManager.setGlobalErrors(result, messaggi.toArray(new String[]{}));
		
					contoEconomicoManager.removeCodiceWarning(contoEconomicoItemList);
		
					contoEconomicoManager.setRigaRibaltamento(contoEconomicoItemList, 1);
					
					esitoSalva.setEsito(false);
		
				} else {
		
					messaggi.add((MessageKey.CONFERMA_SALVATAGGIO));
					
					esitoSalva.setEsito(true);
		
					//result.setResultCode("CONFERMA");
					//MessageManager.setGlobalMessages(result, messaggi.toArray(new String[]{}));		
					
				}
				
				StringBuilder sb = new StringBuilder();
				sb.append("Conto economico in output: ");
				for (ContoEconomicoItem i : contoEconomicoItemList) {
					sb.append("\nLabel = "+i.getLabel()+"; ImportoRichiestoNuovaProposta = "+i.getImportoRichiestoNuovaProposta()+"; CodiceErrore = "+i.getCodiceErrore());
				}
				logger.info("\n\n" + sb.toString() + "\n\n");
		
				//theModel.setAppDatacontoEconomico(contoEconomicoItemList);
				
				esitoSalva.setRigheContoEconomicoAggiornato(contoEconomicoItemList);
				
				for (String msg : messaggi) {
					esitoSalva.getMessaggi().add(TraduttoreMessaggiPbandisrv.traduci(msg));
				}
			}
					
			logger.info(prf + esitoSalva.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return esitoSalva;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class})
	public EsitoSalvaPropostaRimodulazioneDTO salvaPropostaRimodulazioneConfermata(SalvaPropostaRimodulazioneRequest salvaPropostaRimodulazioneRequest, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::salvaPropostaRimodulazioneConfermata] ";
		logger.info(prf + " BEGIN");		
				
		if (salvaPropostaRimodulazioneRequest == null) {
			throw new InvalidParameterException("salvaPropostaRimodulazioneConfermataRequest non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		Long idProgetto = salvaPropostaRimodulazioneRequest.getIdProgetto();
		ArrayList<ContoEconomicoItem> contoEconomicoItemList = salvaPropostaRimodulazioneRequest.getContoEconomicoItemList();
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (contoEconomicoItemList == null) {
			throw new InvalidParameterException("contoEconomicoItemList non valorizzato.");
		}
		logger.info(prf + "idProgetto = "+idProgetto+"; contoEconomicoItemList.size = "+contoEconomicoItemList.size()+"; idUtente = "+idUtente);
		
		EsitoSalvaPropostaRimodulazioneDTO esitoSalva = new EsitoSalvaPropostaRimodulazioneDTO();
		try {
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO;
			esitoFindContoEconomicoDTO = this.findContoEconomicoPerPropostaRimodulazione(idProgetto,idUtente, idIride);
			
			if (esitoFindContoEconomicoDTO.getLocked()) {
				logger.info("Conto economico LOCKED");
				esitoSalva.setEsito(false);
				esitoSalva.getMessaggi().add(CONTO_ECONOMICO_LOCKED_PER_PROPOSTA);				
				return esitoSalva;
			} 			
			
			// Alex: nel vecchio CONTO_ECONOMICO_DTO viene salvato in sessione durante salvaPropostaRimodulazione().
			//       non so se questo è il modo giusto di tradurlo nel nuovo.
			//ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione = (ContoEconomicoRimodulazioneDTO) theModel.getSession().get(CONTO_ECONOMICO_DTO);
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione;
			contoEconomicoRimodulazione = esitoFindContoEconomicoDTO.getContoEconomico();
			
			// NOTA: 
			//  - contoEconomicoItemList: conto economico modificato a video e da salvare.
			//  - esitoFindContoEconomicoDTO: conto economico letto da db.
			
			logger.debug("prendo i dati del conto economico dal web");
			
			Map<?, ContoEconomicoItem> mapContoEconomicoItem = beanUtil.index(contoEconomicoItemList, "idVoce");
			
			contoEconomicoManager.mappaContoEconomicoPerSalvataggio(mapContoEconomicoItem, contoEconomicoRimodulazione);

			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoPropostaRimodulazioneDTO esito;
			esito = contoeconomicoBusinessImpl.proponiRimodulazione(idUtente, idIride, contoEconomicoRimodulazione);
			
			Boolean copiaPresente = esitoFindContoEconomicoDTO.getCopiaModificataPresente();

			if (esito != null) {

				boolean isModifica = false;
				boolean isRimodulazione = false;
				
				contoEconomicoRimodulazione = esito.getContoEconomico();

				if (esito.getLocked()) {
					logger.info("Conto economico LOCKED");
					esitoSalva.setEsito(false);
					String msg = "";
					esitoSalva.getMessaggi().add(msg);				
					return esitoSalva;
				}

				contoEconomicoItemList = new ArrayList<ContoEconomicoItem>();

				copiaPresente = Boolean.TRUE;

				ContoEconomico datiContoEconomico = this.getDatiContoEconomico(esitoFindContoEconomicoDTO, false);
				
				contoEconomicoManager.mappaContoEconomicoPerVisualizzazione(
						datiContoEconomico,
						contoEconomicoRimodulazione,
						contoEconomicoItemList, 
						copiaPresente, isModifica, 
						esitoFindContoEconomicoDTO.getDataFineIstruttoria(),
						esitoFindContoEconomicoDTO.getInIstruttoria(),
						isRimodulazione);

				esitoSalva.setEsito(true);
				esitoSalva.getMessaggi().add(PROPOSTA_SALVATA);				

				nascondiColonnaNuovaProposta(datiContoEconomico, false);
				
				List<AltriCostiDTO> altriCosti = salvaPropostaRimodulazioneRequest.getAltriCosti();
				if(altriCosti != null && altriCosti.size() > 0) {
					for(AltriCostiDTO item : altriCosti) {
						if(item != null) {
							LOG.info(prf + item.toString());
							if(item.getImpCeApprovato() != null && item.getImpCePropmod() != null) {
								//se gli importi sono valorizzati, faccio una insert o un'update
								if(item.getIdTCeAltriCosti() == null) {
									//insert
									StringBuilder sql = new StringBuilder("INSERT INTO PBANDI_T_CE_ALTRI_COSTI (ID_T_CE_ALTRI_COSTI,ID_PROGETTO,ID_D_CE_ALTRI_COSTI,IMP_CE_APPROVATO,IMP_CD_PROPMOD) \r\n"
											+ "VALUES(SEQ_PBANDI_T_CE_ALTRI_COSTI.nextval, ?, ?, ?, ?)");
									LOG.info(sql.toString());
									Object[] args = new Object[] { idProgetto, item.getIdDCeAltriCosti(), item.getImpCeApprovato(), item.getImpCePropmod() };
									getJdbcTemplate().update(sql.toString(), args);
								} else {
									//update
									StringBuilder sql = new StringBuilder("UPDATE PBANDI_T_CE_ALTRI_COSTI \r\n"
											+ "SET IMP_CE_APPROVATO = ?, IMP_CD_PROPMOD = ?\r\n"
											+ "WHERE ID_T_CE_ALTRI_COSTI = ?");
									LOG.info(sql.toString());
									Object[] args = new Object[] { item.getImpCeApprovato(), item.getImpCePropmod(), item.getIdTCeAltriCosti() };
									getJdbcTemplate().update(sql.toString(), args);
								}
							} else if (item.getIdTCeAltriCosti() != null) {
								//se gli importi non sono valorizzati, se esiste gia un record, lo elimino
								//delete
								StringBuilder sql = new StringBuilder("DELETE FROM PBANDI_T_CE_ALTRI_COSTI \r\n"
										+ "WHERE ID_T_CE_ALTRI_COSTI = ?");
								LOG.info(sql.toString());
								Object[] args = new Object[] { item.getIdTCeAltriCosti() };
								getJdbcTemplate().update(sql.toString(), args);
							}
						}
					}
				}
				
				
			} else {
				esitoSalva.setEsito(false);
				esitoSalva.getMessaggi().add(ERRORE_GENERICO);
			}
					
			logger.info(prf + esitoSalva.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return esitoSalva;
	}	

	@Override
	public InizializzaConcludiPropostaRimodulazioneDTO inizializzaConcludiPropostaRimodulazione(Long idProgetto, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::inizializzaConcludiPropostaRimodulazione] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + "idProgetto = "+idProgetto+"; idUtente = "+idUtente);
				
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
			
		InizializzaConcludiPropostaRimodulazioneDTO result = new InizializzaConcludiPropostaRimodulazioneDTO();
		try {
			
			// Codice visualizzato del progetto.
			String sql = "SELECT CODICE_VISUALIZZATO FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = ?";
			Object[] param = new Object[] {idProgetto};
			String codiceProgetto = getJdbcTemplate().queryForObject(sql, param, String.class);
			result.setCodiceVisualizzatoProgetto(codiceProgetto);
			
			// Gli allegati alla proposta sono ammessi solo se e' abilitata la regola BR42.
			Boolean allegatiAmmessi = profilazioneBusinessImpl.isRegolaApplicabileForProgetto(idUtente, idIride, idProgetto, RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);			
			result.setAllegatiAmmessi(allegatiAmmessi);
			
			// Combo Rappresentante legale.
			ArrayList<CodiceDescrizione> rappr = this.findRappresentantiLegali(idUtente, idIride, idProgetto);
			result.setRappresentantiLegali(rappr);
			
			// Combo Delegato.
			ArrayList<CodiceDescrizione> delegati = this.findDelegati(idUtente, idIride, idProgetto);
			result.setDelegati(delegati);
			
			// Tabella procedure di aggiudicazione.
			FiltroProcedureAggiudicazione filtroProcAggiudicazione = new FiltroProcedureAggiudicazione();
			ArrayList<ProceduraAggiudicazione> procAgg = this.cercaProcedureAggiudicazione(idUtente, idIride,
					filtroProcAggiudicazione, idProgetto, true);
			result.setListaProcedureAggiudicazione(procAgg);
			
			// Tabella Modalita Agevolazione.
			this.datiProposta(idUtente, idIride, idProgetto, result);
					
			logger.info(prf + result.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return result;
	}
	
	public ArrayList<ProceduraAggiudicazione> cercaProcedureAggiudicazione(Long idUtente, String idIride, 
			FiltroProcedureAggiudicazione filtro, Long idProgetto, Boolean isModificabile) throws Exception {

		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProceduraAggiudicazioneDTO f = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProceduraAggiudicazioneDTO();
		f.setIdTipologiaAggiudicaz(filtro.getIdTipologiaAggiudicaz());
		f.setCodProcAgg((StringUtil.isEmpty(filtro.getCodProcAgg()) ? null : filtro.getCodProcAgg()));
		f.setCigProcAgg((StringUtil.isEmpty(filtro.getCigProcAgg()) ? null : filtro.getCigProcAgg()));
		f.setIdProgetto(idProgetto);
		
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProceduraAggiudicazioneDTO[] procedure;
		procedure = contoeconomicoBusinessImpl.findProcedureAggiudicazione(idUtente, idIride, f);
		
		ArrayList<ProceduraAggiudicazione> res = new ArrayList<ProceduraAggiudicazione>();
		if (procedure != null) {
			for (it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProceduraAggiudicazioneDTO p : procedure) {
				ProceduraAggiudicazione proc = beanUtil.transform(p, ProceduraAggiudicazione.class);
				proc.setIsModificabile(isModificabile);
				res.add(proc);
			} 
		}
		return res;
	}
	
	private void datiProposta(Long idUtente, String idIride, Long idProgetto, InizializzaConcludiPropostaRimodulazioneDTO result) throws Exception {
		
		String prf = "[ContoEconomicoDAOImpl::datiProposta] ";
		logger.info(prf + " BEGIN");
		
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO progettoDTO = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO();
		progettoDTO.setIdProgetto(idProgetto);
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerInvioPropostaRimodulazioneDTO datiDTO;
		datiDTO = contoeconomicoBusinessImpl.caricaDatiPerInvioPropostaRimodulazione(idUtente, idIride, progettoDTO);
		
		//Mostra/nascondi tabella procedura di aggiudicazione
		//theModel.setAppDataidTipoOperazione(""+ datiDTO.getIdTipoOperazione());

		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO[] modalitaDTO = datiDTO.getModalitaDiAgevolazione();
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO totaleModalitaDTO = datiDTO.getTotaliModalitaDiAgevolazione();

		//theModel.getSession().put(Constants.IS_IMPORTI_MODIFICABILI, datiDTO.getIsImportiModificabili());
		
		//theModel.setAppDatatotaleRichiestoNuovaProposta(datiDTO.getImportoSpesaRichiesto());
		result.setTotaleRichiestoNuovaProposta(datiDTO.getImportoSpesaRichiesto());
		logger.info(prf + " TotaleRichiestoNuovaProposta = "+result.getTotaleRichiestoNuovaProposta());
		
		ArrayList<ModalitaAgevolazione> listModalita = new ArrayList<ModalitaAgevolazione>();
		if (modalitaDTO != null && modalitaDTO.length > 0) {
			for (it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO dto : modalitaDTO) {
				ModalitaAgevolazione modalita = new ModalitaAgevolazione();
				modalita.setIdModalitaAgevolazione(dto.getIdModalitaDiAgevolazione());
				modalita.setDescModalita(dto.getDescrizione());
				modalita.setImportoMassimoAgevolato(dto.getImportoMassimoAgevolato());
				modalita.setPercentualeMassimoAgevolato(dto.getPercImportoMassimoAgevolato());
				modalita.setUltimoImportoRichiesto(dto.getImportoRichiestoUltimo());
				modalita.setImportoRichiesto(NumberUtil.getDoubleValue(dto.getImportoRichiestoNuovo()));
				modalita.setPercentualeImportoRichiesto(NumberUtil.getDoubleValue(dto.getPercImportoRichiestoNuovo()));
				if (!ObjectUtil.isNull(dto.getImportoAgevolatoUltimo()) && dto.getImportoAgevolatoUltimo() > 0) {
					modalita.setImportoRichiesto(dto.getImportoAgevolatoUltimo());
				}
				modalita.setImportoErogato(dto.getImportoErogato());
				modalita.setUltimoImportoAgevolato(dto.getImportoAgevolatoUltimo());
				modalita.setPercentualeUltimoImportoAgevolato(dto.getPercImportoAgevolatoUltimo());
				modalita.setModificabile(true);
				listModalita.add(modalita);
			}
		}

		// totali
		if (totaleModalitaDTO != null) {
			
			ModalitaAgevolazione totaliModalita = new ModalitaAgevolazione();
			totaliModalita.setIsModalitaTotale(true);
			totaliModalita.setDescModalita(Constants.MSG_PROPOSTA_RIMODULAZIONE_TOTALE);
			totaliModalita.setImportoMassimoAgevolato(totaleModalitaDTO.getImportoMassimoAgevolato());
			totaliModalita.setPercentualeMassimoAgevolato(totaleModalitaDTO.getPercImportoMassimoAgevolato());
			totaliModalita.setUltimoImportoRichiesto(totaleModalitaDTO.getImportoRichiestoUltimo());
			totaliModalita.setImportoRichiesto(totaleModalitaDTO.getImportoRichiestoNuovo());
			totaliModalita.setPercentualeImportoRichiesto(totaleModalitaDTO.getPercImportoRichiestoNuovo());
			totaliModalita.setImportoErogato(totaleModalitaDTO.getImportoErogato());
			totaliModalita.setUltimoImportoAgevolato(totaleModalitaDTO.getImportoAgevolatoUltimo());
			totaliModalita.setPercentualeUltimoImportoAgevolato(totaleModalitaDTO.getPercImportoAgevolatoUltimo());
			totaliModalita.setModificabile(false);
			if (!ObjectUtil.isNull(totaleModalitaDTO.getImportoAgevolatoUltimo()) && totaleModalitaDTO.getImportoAgevolatoUltimo() > 0) {
				totaliModalita.setImportoRichiesto(totaleModalitaDTO.getImportoAgevolatoUltimo());
				totaliModalita.setPercentualeImportoRichiesto(NumberUtil.getDoubleValue(totaleModalitaDTO.getPercImportoAgevolatoUltimo()));
			}

			listModalita.add(listModalita.size(), totaliModalita);
		}
		
		// Alex: non so se serve davvero.
		this.calcolaTotaleModalita(listModalita, result.getTotaleRichiestoNuovaProposta());

		//theModel.setAppDatamodalitaAgevolazioniProposta(listModalita);
		result.setListaModalitaAgevolazione(listModalita);
		logger.info(prf + " END");
	}
	
	private void calcolaTotaleModalita(ArrayList<ModalitaAgevolazione> listModalita, Double totaleRichiestoNuovaProposta) {
		
		//Double totaleRichiesto = NumberUtil.getDoubleValue(theModel.getAppDatatotaleRichiestoNuovaProposta());
		Double totaleRichiesto =  NumberUtil.getDoubleValue(totaleRichiestoNuovaProposta);
		
		ModalitaAgevolazione totaliModalita = new ModalitaAgevolazione();
		totaliModalita.setIsModalitaTotale(true);
		totaliModalita.setDescModalita(Constants.MSG_PROPOSTA_RIMODULAZIONE_TOTALE);
		for (int i = 0; i < listModalita.size() - 1; ++i) {
			ModalitaAgevolazione modalita = listModalita.get(i);
			if (totaleRichiesto > 0) {
				BigDecimal percRichiestoModalita = NumberUtil.percentage(modalita.getImportoRichiesto(), totaleRichiesto);
				modalita.setPercentualeImportoRichiesto(NumberUtil.toDouble(percRichiestoModalita));
			}
			Double importoAgevolato = NumberUtil.getDoubleValue(totaliModalita.getImportoAgevolato())
					+ NumberUtil.getDoubleValue(modalita.getImportoAgevolato());
			Double importoErogato = NumberUtil.getDoubleValue(totaliModalita.getImportoErogato())
					+ NumberUtil.getDoubleValue(modalita.getImportoErogato());
			Double importoMassimoAgevolato = NumberUtil.getDoubleValue(totaliModalita.getImportoMassimoAgevolato())
					+ NumberUtil.getDoubleValue(modalita.getImportoMassimoAgevolato());
			Double importoRichiesto = NumberUtil.getDoubleValue(totaliModalita.getImportoRichiesto())
					+ NumberUtil.getDoubleValue(modalita.getImportoRichiesto());
			Double ultimoImportoRichiesto = NumberUtil.getDoubleValue(totaliModalita.getUltimoImportoRichiesto())
					+ NumberUtil.getDoubleValue(modalita.getUltimoImportoRichiesto());
			Double ultimoImportoAgevolato = NumberUtil.getDoubleValue(totaliModalita.getUltimoImportoAgevolato())
					+ NumberUtil.getDoubleValue(modalita.getUltimoImportoAgevolato());
			Double percImportoAgevolato = NumberUtil.getDoubleValue(totaliModalita.getPercentualeImportoAgevolato())
					+ NumberUtil.getDoubleValue(modalita.getPercentualeImportoAgevolato());
			Double percImportoRichiesto = NumberUtil.getDoubleValue(totaliModalita.getPercentualeImportoRichiesto())
					+ NumberUtil.getDoubleValue(modalita.getPercentualeImportoRichiesto());
			Double percImportoMassimoAgevolato = NumberUtil.getDoubleValue(totaliModalita.getPercentualeMassimoAgevolato())
					+ NumberUtil.getDoubleValue(modalita.getPercentualeMassimoAgevolato());
			Double percUltimoImportoAgevolato = NumberUtil.getDoubleValue(totaliModalita.getPercentualeUltimoImportoAgevolato())
					+ NumberUtil.getDoubleValue(modalita.getPercentualeUltimoImportoAgevolato());

			totaliModalita.setImportoAgevolato(importoAgevolato);
			totaliModalita.setImportoErogato(importoErogato);
			totaliModalita.setImportoMassimoAgevolato(importoMassimoAgevolato);
			totaliModalita.setImportoRichiesto(importoRichiesto);
			totaliModalita.setUltimoImportoRichiesto(ultimoImportoRichiesto);
			totaliModalita.setUltimoImportoAgevolato(ultimoImportoAgevolato);
			totaliModalita.setPercentualeImportoAgevolato(percImportoAgevolato);
			totaliModalita.setPercentualeImportoRichiesto(percImportoRichiesto);
			totaliModalita.setPercentualeMassimoAgevolato(percImportoMassimoAgevolato);
			totaliModalita.setPercentualeUltimoImportoAgevolato(percUltimoImportoAgevolato);

		}

		listModalita.set(listModalita.size() - 1, totaliModalita);
	}
	
	private ArrayList<CodiceDescrizione> findRappresentantiLegali(Long idUtente, String idIride, Long idProgetto) throws Exception {
		
		List<it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO> rappresentanti;
		rappresentanti = rappresentanteLegaleManager.findRappresentantiLegali(idProgetto, null);
		
		ArrayList<CodiceDescrizione> list = new ArrayList<CodiceDescrizione>();
		for (it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO dto : rappresentanti) {
			CodiceDescrizione codiceDescrizione = new CodiceDescrizione();
			codiceDescrizione.setCodice(NumberUtil.getStringValue(dto.getIdSoggetto()));
			String codiceFiscale = null;
			if (dto.getCodiceFiscaleSoggetto() != null)
				codiceFiscale = dto.getCodiceFiscaleSoggetto();
			if (codiceFiscale != null)
				codiceFiscale = "(" + codiceFiscale + ")";
			codiceDescrizione.setDescrizione(dto.getCognome() + " "	+ dto.getNome() + codiceFiscale);
			list.add(codiceDescrizione);
		}

		return list;
	}
	
	private ArrayList<CodiceDescrizione> findDelegati(Long idUtente, String idIride, Long idProgetto) throws Exception {
		
		it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DelegatoDTO[] delegatiSrv;
		delegatiSrv = gestioneDatiGeneraliBusinessImpl.findDelegati(idUtente, idIride, idProgetto);

		ArrayList<CodiceDescrizione> delegati = new ArrayList<CodiceDescrizione>();
		if (delegatiSrv != null && delegatiSrv.length > 0) {
			for (it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DelegatoDTO delegatoDTO : delegatiSrv) {
				CodiceDescrizione cd = new CodiceDescrizione();
				cd.setDescrizione(delegatoDTO.getNome() + " "
						+ delegatoDTO.getCognome() + "["
						+ delegatoDTO.getCodiceFiscaleSoggetto() + "]");
				cd.setCodice(delegatoDTO.getIdSoggetto().toString());
				delegati.add(cd);
			}
		}

		return delegati;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class})
	// Associa allegati alla proposta di rimodulazione.
	public EsitoAssociaFilesDTO associaAllegatiAPropostaRimodulazione(AssociaFilesRequest associaFilesRequest, Long idUtente) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::associaAllegatiAPropostaRimodulazione] ";
		logger.info(prf + "BEGIN");
			
		EsitoAssociaFilesDTO esito = new EsitoAssociaFilesDTO();
		try {
			
			Long idEntita = this.getIdEntita("PBANDI_T_CONTO_ECONOMICO");
			
			esito = archivioFileDAOImpl.associaFiles(associaFilesRequest, idEntita, idUtente);
			logger.info(prf+esito.toString());
		
		} catch (InvalidParameterException e) {
			logger.error(prf+e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error(prf+" ERRORE nel associare allegati alla proposta: ", e);
			throw new Exception(" ERRORE nel associare allegati alla proposta.");
		}
		finally {
			logger.info(prf+" END");
		}
		
		return esito;
	}
	
	@Override
	// Ex pbandiweb ArchivioFileAction.dissociate()
	public EsitoOperazioni disassociaAllegatoPropostaRimodulazione(Long idDocumentoIndex, Long idProgetto, Long idUtente) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::disassociaAllegatoPropostaRimodulazione] ";
		logger.info(prf + "BEGIN");
		logger.info(prf + "idDocumentoIndex = " + idDocumentoIndex + "; idProgetto = " + idProgetto + "; idUtente = " + idUtente);
		
		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		
		EsitoOperazioni esito = new EsitoOperazioni();
		try {
			
			Long idEntita = this.getIdEntita("PBANDI_T_CONTO_ECONOMICO");
			Long idFile = this.getIdFile(idDocumentoIndex);
			
			String sql = "DELETE PBANDI_T_FILE_ENTITA WHERE ID_FILE = "+idFile+" AND ID_ENTITA = "+idEntita+" AND ID_TARGET = "+idProgetto;
			logger.info("\n"+sql.toString());
			getJdbcTemplate().update(sql);
			
			esito.setEsito(true);
			esito.setMsg("Operazione eseguita correttamente.");
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE nel disassociare gli allegati della proposta: ", e);
			throw new Exception(" ERRORE nel disassociare gli allegati della proposta.");
		}
		finally {
			logger.info(prf+" END");
		}
		
		return esito;
	}
	
	private Long getIdEntita(String entita) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = '"+entita+"'");
		logger.info("\n"+sql.toString());
		Long idEntita = (Long) getJdbcTemplate().queryForObject(sql.toString(), Long.class);			
		if (idEntita == null)
			throw new Exception("Id entita non trovato.");
		return idEntita;
	}
	
	private Long getIdFile(Long idDocumentoIndex) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID_FILE FROM PBANDI_T_FILE WHERE ID_DOCUMENTO_INDEX = "+idDocumentoIndex);
		logger.info("\n"+sql.toString());
		Long idFile = (Long) getJdbcTemplate().queryForObject(sql.toString(), Long.class);			
		if (idFile == null)
			throw new Exception("Id File non trovato.");
		return idFile;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class, UnrecoverableException.class})
	public EsitoInviaPropostaRimodulazioneDTO inviaPropostaRimodulazione(InviaPropostaRimodulazioneRequest inviaPropostaRimodulazioneRequest, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::inviaPropostaRimodulazione] ";
		logger.info(prf + " BEGIN");		
				
		if (inviaPropostaRimodulazioneRequest == null) {
			throw new InvalidParameterException("inviaPropostaRimodulazioneRequest non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		EsitoInviaPropostaRimodulazioneDTO esitoInvia = new EsitoInviaPropostaRimodulazioneDTO();
		try {
			
			// Salvo i tipi allegato
			TipoAllegatoDTO[] tipoAllegati = inviaPropostaRimodulazioneRequest.getTipiAllegato();
			if (tipoAllegati != null && tipoAllegati.length > 0) {
				LOG.info(prf + "Salvataggio tipi allegato - inizio");
				for(int i = 0; i < tipoAllegati.length; i++) {
					tipoAllegati[i].setIdProgetto(inviaPropostaRimodulazioneRequest.getIdProgetto());
				}
				EsitoOperazioneSalvaTipoAllegati esitoTipiAllegati;
				esitoTipiAllegati = gestioneDatiGeneraliBusinessImpl.salvaTipoAllegati(idUtente, idIride, tipoAllegati);
				if (esitoTipiAllegati == null || !esitoTipiAllegati.getEsito()) {
					throw new Exception("Errore durante il salvataggio dei tipi allegato.");
				}
				LOG.info(prf + "Salvataggio tipi allgato - fine");
			} else {
				LOG.info(prf + "Nessun tipo allgato in input.");
			}
			
			//salvo le procedure di aggiudicazione
			Long idProceduraAggiudicazione = null;
			String idDelegato = ((inviaPropostaRimodulazioneRequest.getIdDelegato() == null) ? null : inviaPropostaRimodulazioneRequest.getIdDelegato().toString());
			EsitoInvioPropostaRimodulazioneDTO esito = inviaPropostaRimodulazione(
					idUtente, idIride, 
					inviaPropostaRimodulazioneRequest.getListaModalitaAgevolazione(),
					inviaPropostaRimodulazioneRequest.getIdProgetto(), 
					inviaPropostaRimodulazioneRequest.getIdSoggettoBeneficiario(),
					inviaPropostaRimodulazioneRequest.getNote(), 
					inviaPropostaRimodulazioneRequest.getIdRapprensentanteLegale(),
					inviaPropostaRimodulazioneRequest.getIdContoEconomico(),
					inviaPropostaRimodulazioneRequest.getImportoFinanziamentoRichiesto(),
					idProceduraAggiudicazione,
					idDelegato);
					
			esitoInvia.setEsito(true);
			esitoInvia.setIdDocumentoIndex(esito.getIdDocIndex());
			esitoInvia.setIdContoEconomico(esito.getIdContoEconomicoLocal());
			esitoInvia.setNomeFile(esito.getFileName());
			esitoInvia.setDataProposta(DateUtil.getDataOdierna());
			
			logger.info(prf + esitoInvia.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw new Exception("Invio proposta fallito.");
		}
		finally {
			logger.info(prf+" END");
		}
		
		return esitoInvia;
	}
	
	private EsitoInvioPropostaRimodulazioneDTO inviaPropostaRimodulazione(
			Long idUtente, String idIride, 
			List<ModalitaAgevolazione> listModalita, 
			Long idProgetto, Long idSoggetto,
			String noteContoEconomico, Long idSoggettoRappresentante,
			Long idContoEconomico, Double importoFinanziamentoRichiesto,
			Long idProceduraAggiudicazione,String idDelegato)
			throws Exception {

		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO progettoDTO = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO();
		progettoDTO.setIdProgetto(idProgetto);

		// Alex: metto valori a caso poichè il metodo di pbandisrv li vuole obbligatori ma non li usa.
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.IstanzaAttivitaDTO istanza = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.IstanzaAttivitaDTO();		
		istanza.setId("TaskIdentity");					//istanza.setId(istanzaAttivita.getTaskIdentity());
		istanza.setCodUtente("CodUtenteFlux");			//istanza.setCodUtente(userInfoHelper.getCodUtenteFlux(currentUser));

		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerInvioPropostaRimodulazioneDTO datiDTO = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerInvioPropostaRimodulazioneDTO();
		List<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO> listModalitaDTO = new ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO>();
		for (int i = 0; i < listModalita.size() - 1; ++i) {
			ModalitaAgevolazione modalita = listModalita.get(i);
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO dto = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO();
			dto.setDescrizione(modalita.getDescModalita());
			dto.setIdModalitaDiAgevolazione(modalita.getIdModalitaAgevolazione());
			dto.setImportoAgevolatoNuovo(modalita.getImportoAgevolato());
			dto.setImportoAgevolatoUltimo(modalita.getUltimoImportoAgevolato());
			dto.setImportoErogato(modalita.getImportoErogato());
			dto.setImportoMassimoAgevolato(modalita.getImportoMassimoAgevolato());
			dto.setImportoRichiestoNuovo(modalita.getImportoRichiesto());
			dto.setImportoRichiestoUltimo(modalita.getUltimoImportoRichiesto());
			dto.setPercImportoAgevolatoNuovo(modalita.getPercentualeImportoAgevolato());
			dto.setPercImportoAgevolatoUltimo(modalita.getPercentualeUltimoImportoAgevolato());
			dto.setPercImportoMassimoAgevolato(modalita.getPercentualeMassimoAgevolato());
			dto.setPercImportoRichiestoNuovo(modalita.getPercentualeImportoRichiesto());
			listModalitaDTO.add(dto);
		}
		datiDTO.setModalitaDiAgevolazione(listModalitaDTO.toArray(new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO[] {}));
		datiDTO.setProgetto(progettoDTO);
		datiDTO.setNoteContoEconomico(noteContoEconomico);

		datiDTO.setIdSoggetto(idSoggetto);
		datiDTO.setIdSoggettoRappresentante(idSoggettoRappresentante);
		datiDTO.setIdContoEconomico(idContoEconomico);
		datiDTO.setImportoFinanziamentoRichiesto(importoFinanziamentoRichiesto);
		datiDTO.setIdProceduraAggiudicazione(idProceduraAggiudicazione);
		
		if(!ObjectUtil.isEmpty(idDelegato) )
			datiDTO.setIdDelegato(NumberUtil.toNullableLong(idDelegato));
		
		EsitoInvioPropostaRimodulazioneDTO esito = contoeconomicoBusinessImpl.inviaPropostaRimodulazione(
				idUtente, idIride, istanza, datiDTO);
		
		return esito;
	}
	
	@Override
	public ArrayList<DocumentoAllegato> allegatiPropostaRimodulazione(Long idProgetto, Long idUtente) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::allegatiPropostaRimodulazione] ";
		logger.info(prf + "BEGIN");
		logger.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente);
		
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		
		ArrayList<DocumentoAllegato> result = null;
		try {
						
			result = contoeconomicoBusinessImpl.getAllegatiPropostaRimodulazioneByIdProgetto(idUtente, idProgetto);
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE nella ricerca degli allegati della proposta: ", e);
			throw new Exception(" ERRORE nella ricerca degli allegati della proposta.");
		}
		finally {
			logger.info(prf+" END");
		}
		
		return result;
	}
	
	@Override
	public InizializzaUploadFileFirmatoDTO inizializzaUploadFileFirmato(Long idProgetto, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::inizializzaUploadFileFirmato] ";
		logger.info(prf + "BEGIN");
		logger.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente);
		
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		
		InizializzaUploadFileFirmatoDTO result = new InizializzaUploadFileFirmatoDTO();
		try {

			// Codice visualizzato del progetto.
			String sql = "SELECT CODICE_VISUALIZZATO FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = ?";
			Object[] param = new Object[] {idProgetto};
			logger.info("\n"+sql);
			String codiceProgetto = getJdbcTemplate().queryForObject(sql, param, String.class);
			result.setCodiceVisualizzatoProgetto(codiceProgetto);
			
			// Codice visualizzato del progetto.
			String attributo = "conf.digitalSignFileSizeLimit";
			String sql2 = "SELECT VALORE FROM PBANDI_C_COSTANTI WHERE ATTRIBUTO = '"+attributo+"'";
			logger.info("\n"+sql2);
			String valore = (String) getJdbcTemplate().queryForObject(sql2, String.class);			
			if (StringUtil.isEmpty(valore)) {
				throw new Exception("Costante "+attributo+" non trovata.");
			}
			result.setDimMaxFileFirmato(new Long(valore));
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE nell'inizializza Upload File Firmato: ", e);
			throw new Exception(" ERRORE nell'inizializza Upload File Firmato.");
		}
		finally {
			logger.info(prf+" END");
		}
		
		return result;
	}
	
	@Override
	// Salva il pdf firmato della dichiarazione di spesa.
	@Transactional(rollbackFor = {Exception.class})
	public Boolean salvaFileFirmato(MultipartFormDataInput multipartFormData, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::salvaFileFirmato] ";
		logger.info(prf + "BEGIN");
		
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (multipartFormData == null) {
			throw new InvalidParameterException("multipartFormData non valorizzato");
		}
				
		Long idDocumentoIndex = multipartFormData.getFormDataPart("idDocumentoIndex", Long.class, null);
		logger.info(prf+"input idDocumentoIndex = "+idDocumentoIndex);
		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato");
		}
		
		// Legge il file firmato dal multipart.		
		Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
		List<InputPart> listInputPart = map.get("fileFirmato");
		
		if (listInputPart == null) {
			logger.info("listInputPart NULLO");
		} else {
			logger.info("listInputPart SIZE = "+listInputPart.size());
		}
		for (InputPart i : listInputPart) {
			MultivaluedMap<String, String> m = i.getHeaders();
			Set<String> s = m.keySet();
			for (String x : s) {
				logger.info("SET = "+x);
			}
		}
		
		FileDTO file = FileUtil.leggiFileDaMultipart(listInputPart, null);
		if (file == null) {
			throw new InvalidParameterException("File non valorizzato");
		}
		
		logger.info(prf+"input idDocumentoIndex = "+idDocumentoIndex);
		logger.info(prf+"input nomeFile = "+file.getNomeFile());
		logger.info(prf+"input bytes.length = "+file.getBytes().length);
		
		
		Boolean esito = true;
		try {
			
			esito = documentoManager.salvaFileFirmato(file.getBytes(), file.getNomeFile(), idDocumentoIndex, idUtente);
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE nel salvataggio del file firmato: ", e);
			throw new Exception(" ERRORE nel salvataggio del file firmato.");
		}
		finally {
			logger.info(prf+" END");
		}
		
		return esito;
	}
	
	@Override
	// Verifica la firma digitale del file in input e lo marca temporalmente.
	// NOTA: questo metodo viene chiamato in modo asincrono da Angular.
	// Ex DigitalSignAction.upload()
	public Boolean verificaFirmaDigitale(Long idDocumentoIndex, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::verificaFirmaDigitale] ";
		logger.info(prf + "BEGIN");
		
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato");
		}

		logger.info(prf+"input idDocumentoIndex = "+idDocumentoIndex);
		Boolean esito = true;
		try {
			
			it.csi.pbandi.pbservizit.dto.archivioFile.FileDTO fileFirmato = documentoManager.leggiFileFirmato(idDocumentoIndex); 
			if (fileFirmato == null) {
				logger.error(prf+" Lettura del file firmato fallita.");
				throw new Exception("Lettura del file firmato fallita.");
			}
			
			it.csi.pbandi.pbwebrce.pbandisrv.dto.digitalsign.SignedFileDTO signedFileDTO = new it.csi.pbandi.pbwebrce.pbandisrv.dto.digitalsign.SignedFileDTO();
			signedFileDTO.setBytes(fileFirmato.getBytes());
			signedFileDTO.setFileName(fileFirmato.getNomeFile());
			signedFileDTO.setIdDocIndex(idDocumentoIndex);
			
			logger.info(prf+"Chiamo digitalSignSrv.signFile");
			digitalSignBusinessImpl.signFile(idUtente, idIride, signedFileDTO);
			logger.info(prf+"Terminato digitalSignSrv.signFile");
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE nella verifica del file firmato: ", e);
			throw new Exception(" ERRORE nella verifica del file firmato.");
		}
		finally {
			logger.info(prf+" END");
		}
		
		return esito;
	}
	
	@Override
	// Setta il campo PBANDI_T_DOCUMENTO_INDEX.FLAG_FIRMA_CARTACEA a "S".
	// Ex DigitalSignBusinessImpl.setFlagCartaceo()
	public Boolean salvaInvioCartaceo(Boolean invioCartaceo, Long idDocumentoIndex, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::salvaInvioCartaceo] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + "invioCartaceo = "+invioCartaceo+"; idDocumentoIndex = "+idDocumentoIndex+"; idUtente = "+idUtente);
		
		if (invioCartaceo == null) {
			throw new InvalidParameterException("invioCartaceo non valorizzato.");
		}
		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		Boolean esito = true;
		try {			
			
			String flagFirmaCartacea = (invioCartaceo != null && invioCartaceo) ? "S" : "N"; 

			String sql = "UPDATE PBANDI_T_DOCUMENTO_INDEX SET FLAG_FIRMA_CARTACEA = '"+flagFirmaCartacea+"', DT_AGGIORNAMENTO_INDEX = SYSDATE, ID_UTENTE_AGG = "+idUtente+" where ID_DOCUMENTO_INDEX = "+idDocumentoIndex;
			logger.info(prf + "\n"+sql);					
			getJdbcTemplate().update(sql);

		} catch (Exception e) {
			logger.error(prf+" ERRORE durante la valorizzazione del Flag Firma Cartacea: ", e);
			esito = false;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return esito;
	}
	
	@Override
	public InizializzaRimodulazioneDTO inizializzaRimodulazione(Long idProgetto, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::inizializzaRimodulazione] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + "idProgetto = "+idProgetto+"; idUtente = "+idUtente);
				
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		InizializzaRimodulazioneDTO result = new InizializzaRimodulazioneDTO();
		try {
			
			// Codice visualizzato del progetto.
			String sql = "SELECT CODICE_VISUALIZZATO FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = ?";
			Object[] param = new Object[] {idProgetto};
			String codiceProgetto = getJdbcTemplate().queryForObject(sql, param, String.class);
			result.setCodiceVisualizzatoProgetto(codiceProgetto);
			
			// CONTO ECONOMICO LETTO DA DB.
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO  esito;
			esito = this.findContoEconomicoPerRimodulazione(idProgetto,idUtente, idIride);
			result.setEsitoFindContoEconomicoDTO(esito);
			
			// DATI DEL CONTO ECONOMICO.
			
			ContoEconomico conto = this.getDatiContoEconomico(esito, false);
			result.setDatiContoEconomico(conto);
			
			// RIGHE DEL CONTO ECONOMICO PER VISUALIZZAZIONE.
			
			boolean isRimodulazione = true;
			ArrayList<ContoEconomicoItem> righe = this.getContoEconomicoItem(conto, esito, false, isRimodulazione);
			result.setRigheContoEconomico(righe);
						
			// GESTIONE ECONOMIE.
			
			// Nel vecchio serviva a popolare IS_PROGETTO_RICEVENTE usato in datiGeneraliRimodulazione.jsp
			// per visualizzare o no il bottone 'economie' nella popup dei Dati Generali.
			result.setProgettoRicevente(contoeconomicoBusinessImpl.isProgettoRicevente(idUtente, idIride, idProgetto));
			
			result.setSommaEconomieUtilizzate(contoeconomicoBusinessImpl.findSommaEconomieUtilizzate(idUtente, idIride, idProgetto));
			
			logger.info(prf + result.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return result;
	}
	
	@Override
	// Ex CPBERimodulazione.confermaRimodulazione()
	public EsitoSalvaRimodulazioneDTO salvaRimodulazione(SalvaRimodulazioneRequest salvaRimodulazioneRequest, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::salvaRimodulazione] ";
		logger.info(prf + " BEGIN");		
				
		if (salvaRimodulazioneRequest == null) {
			throw new InvalidParameterException("salvaRimodulazioneRequest non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		Long idProgetto = salvaRimodulazioneRequest.getIdProgetto();
		ArrayList<ContoEconomicoItem> contoEconomicoItemList = salvaRimodulazioneRequest.getContoEconomicoItemList();
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (contoEconomicoItemList == null) {
			throw new InvalidParameterException("contoEconomicoItemList non valorizzato.");
		}
		logger.info(prf + "idProgetto = "+idProgetto+"; idUtente = "+idUtente);
		
		EsitoSalvaRimodulazioneDTO esitoSalva = new EsitoSalvaRimodulazioneDTO();
		try {
				
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO;
			esitoFindContoEconomicoDTO = this.findContoEconomicoPerRimodulazione(idProgetto,idUtente, idIride);

			// NOTA: 
			//  - contoEconomicoItemList: conto economico modificato a video e da salvare.
			//  - esitoFindContoEconomicoDTO: conto economico letto da db.
						
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione;
			contoEconomicoRimodulazione = esitoFindContoEconomicoDTO.getContoEconomico();
			//theModel.getSession().put(CONTO_ECONOMICO_DTO,contoEconomicoRimodulazione);

			if (esitoFindContoEconomicoDTO.getLocked()) {
				logger.info("Conto economico LOCKED");
				esitoSalva.setEsito(false);
				esitoSalva.getMessaggi().add(CONTO_ECONOMICO_LOCKED_PER_RIMODULAZIONE);
				return esitoSalva;
			}

			// contoEconomicoItemList ora arriva da Angular come parametro di input.
			//ArrayList<ContoEconomicoItem> contoEconomicoItemList = theModel.getAppDatacontoEconomico();
			
			Map<?, ContoEconomicoItem> mapContoEconomicoItem = beanUtil.index(contoEconomicoItemList, "idVoce");
			
			contoEconomicoManager.mappaContoEconomicoPerSalvataggio(mapContoEconomicoItem, contoEconomicoRimodulazione);			

			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoRimodulazioneDTO esito;
			esito = contoeconomicoBusinessImpl.rimodula(idUtente, idIride, contoEconomicoRimodulazione);

			//Boolean copiaPresente = (Boolean) theModel.getSession().get(COPIA_PRESENTE);

			if (esito != null) {

				boolean isModifica = false;
				boolean isRimodulazione = true;
				
				contoEconomicoRimodulazione = esito.getContoEconomico();
				contoEconomicoItemList = new ArrayList<ContoEconomicoItem>();

				if (esito.getLocked()) {
					logger.info("Conto economico LOCKED");
					esitoSalva.setEsito(false);
					esitoSalva.getMessaggi().add(CONTO_ECONOMICO_LOCKED_PER_RIMODULAZIONE);
				} else {
					esitoSalva.setEsito(true);
					esitoSalva.getMessaggi().add(RIMODULAZIONE_SALVATA);
					
					Boolean copiaPresente = Boolean.TRUE;

					ContoEconomico datiContoEconomico = this.getDatiContoEconomico(esitoFindContoEconomicoDTO, false);
					
					contoEconomicoItemList = contoEconomicoManager.mappaContoEconomicoPerVisualizzazione(
							datiContoEconomico, contoEconomicoRimodulazione, contoEconomicoItemList, 
							copiaPresente, isModifica, 
							esitoFindContoEconomicoDTO.getDataFineIstruttoria(), 
							esitoFindContoEconomicoDTO.getInIstruttoria(),
							isRimodulazione);
					
					//theModel.setAppDatacontoEconomico(contoEconomicoItemList);
					//theModel.getSession().put(COPIA_PRESENTE, copiaPresente);
					
					datiContoEconomico.setNascondiColonnaSpesaAmmessa(false);
					//theModel.setAppDatadatiContoEconomico(contoEconomico);
					
					esitoSalva.setRigheContoEconomicoAggiornato(contoEconomicoItemList);

				}
				
			} else {
				esitoSalva.setEsito(false);
				esitoSalva.getMessaggi().add(ERRORE_GENERICO);
			}
			
			logger.info(prf + esitoSalva.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return esitoSalva;
	}
	
	@Override
	public InizializzaConcludiRimodulazioneDTO inizializzaConcludiRimodulazione(Long idProgetto, Long idBandoLinea, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::inizializzaConcludiRimodulazione] ";
		logger.info( prf + " BEGIN");
		logger.info( prf + "idProgetto = "+idProgetto+"; idUtente = "+idUtente);
				
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
			
		InizializzaConcludiRimodulazioneDTO result = new InizializzaConcludiRimodulazioneDTO();
		try {
			
			// Codice visualizzato del progetto.
			String sql = "SELECT CODICE_VISUALIZZATO FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = ?";
			Object[] param = new Object[] {idProgetto};
			String codiceProgetto = getJdbcTemplate().queryForObject(sql, param, String.class);
			result.setCodiceVisualizzatoProgetto(codiceProgetto);
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerConclusioneRimodulazioneDTO datiDTO;
			datiDTO = this.caricaDatiPerConclusioneRimodulazione(idProgetto, idBandoLinea, idUtente, idIride);

			LOG.shallowDump(datiDTO, "debug");
			logger.info( prf + "datiDTO = "+datiDTO);
			
			result.setTipoOperazione(datiDTO.getTipoOperazione());
			result.setTotaleSpesaAmmessaInRimodulazione(datiDTO.getImportoSpesaAmmessa());
			result.setTotaleUltimaSpesaAmmessa(datiDTO.getImportoSpesaAmmessaUltima());
			result.setImportoFinanziamentoBancario(datiDTO.getImportoFinanziamentoBancario());
			result.setImportoImpegnoGiuridico(datiDTO.getImportoImpegnoVincolante());			
			if (!StringUtils.isBlank(datiDTO.getDataConcessione()))
				result.setDataConcessione(DateUtil.getDate(datiDTO.getDataConcessione()));
			
			 
			// PK : JIRA 3529 -> calcolo l'ImportoAgevolato che deve essere usato per il controllo
			// importo totale dell'agevolato deve essere calcolato andando a sommare per il progetto corrente tutti gli 
			// importi presenti in 	PBANDI_R_PROG_SOGG_FINANZIAT.IMP_QUOTA_SOGG_FINANZIATORE
			// per tutti gli ID_SOGGETTO_FINANZIATORE DIVERSI DA:
			// Privato, Da reperire,  Stato Estero,  Privato FESR,  Regione 
			StringBuffer sb = new StringBuffer();
			sb.append(" select nvl(sum(IMP_QUOTA_SOGG_FINANZIATORE),0)  from   PBANDI_R_PROG_SOGG_FINANZIAT psf,  PBANDI_D_SOGGETTO_FINANZIATORE sf ");
			sb.append(" where  psf.id_progetto = ? ");
			sb.append(" and    psf.ID_SOGGETTO_FINANZIATORE = sf.ID_SOGGETTO_FINANZIATORE ");
			sb.append(" and    sf.DESC_BREVE_SOGG_FINANZIATORE NOT IN ('Privato','Da reperire', 'Stato Estero','Privato FESR', 'Regione') ");
			sb.append(" group by PSF.ID_PROGETTO ");
        
			Object[] param2 = new Object[] {idProgetto};
			Double importoTotaleAgevolato = getJdbcTemplate().queryForObject(sb.toString(), param2, Double.class);
			logger.debug(prf + "importoTotaleAgevolato="+importoTotaleAgevolato);
			
			result.setImportoTotaleAgevolato(importoTotaleAgevolato);			
			
			// Tabella Modalità Agevolazione.
			ArrayList<ModalitaAgevolazione> listaModalita = this.loadModalitaAgevolazione(datiDTO);
			for (ModalitaAgevolazione modalitaAgevolazione : listaModalita) {
				logger.debug(prf + "IdModalitaAgevolazione="+modalitaAgevolazione.getIdModalitaAgevolazione() +
						", ImportoAgevolato=" + modalitaAgevolazione.getImportoAgevolato() +   // importo netto certificato --> PK ERRATO
						", IsModalitaTotale=" + modalitaAgevolazione.getIsModalitaTotale());
				// Angular prende importoAgevolato = modalitaAgevolazione.getImportoAgevolato() dell'elemento che ha modalitaAgevolazione.getIsModalitaTotale() = TRUE
			}
			
			result.setListaModalitaAgevolazione(listaModalita);
			
			// Fonti finanziarie.
			
			this.loadFontiFinanziarie(datiDTO, result);
			
			// importoUltimaPropostaCertificazionePerProgetto: usato per la validazione delle Modalità.
			Double importoUltimaProposta = certificazioneManager.findImportoUltimaPropostaCertificazionePerProgetto(idProgetto, it.csi.pbandi.pbwebrce.pbandiutil.common.Constants.STATO_PROPOSTA_CERTIFICAZIONE_APPROVATA);
			result.setImportoUltimaPropostaCertificazionePerProgetto(importoUltimaProposta);
			logger.info( prf + "importoUltimaProposta="+importoUltimaProposta);	
			
			logger.info( prf + "InizializzaConcludiRimodulazioneDTO="+result.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf + " END");
		}
		
		return result;
	}
	
	

	@Override
	// Esegue dei controlli BLOCCANTI e restituisce un elenco di eventuali msg di errore.
	public EsitoValidaRimodulazioneConfermataDTO validaRimodulazioneConfermata (ValidaRimodulazioneConfermataRequest req, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		
		EsitoValidaRimodulazioneConfermataDTO esito = new EsitoValidaRimodulazioneConfermataDTO();
		
		ArrayList<String> errori = new ArrayList<String>();
		
		this.validaQuotaUE(errori, req.getFontiFiltrate(), req.getTotaleSpesaAmmessaRimodulazione(), idUtente, idIride);
		if (errori.size() > 0) {
			esito.setEsito(false);
			esito.setErroreBloccante(true);
			esito.setMessaggi(errori);
			return esito;
		}
		
		// Solo in caso di rimodulazione istruttore 'normale'.
		if (!req.getRimodulazioneInIstruttoria()) {
			this.validaImportiBloccanti(errori, req.getListaModalitaAgevolazione(), req.getFontiFiltrate(), req.getTotaleSpesaAmmessaRimodulazione(), req.getIdBando(), idUtente, idIride);
			if (errori.size() > 0) {
				esito.setEsito(false);
				esito.setErroreBloccante(true);
				esito.setMessaggi(errori);
				return esito;
			}
		}
		
		this.validaImportiWarning(errori, req.getListaModalitaAgevolazione(), req.getFontiFiltrate(), req.getTotaleSpesaAmmessaRimodulazione(), idUtente, idIride);
		if (errori.size() > 0) {
			esito.setEsito(false);
			esito.setErroreBloccante(false);
			esito.setMessaggi(errori);
			esito.setListaModalitaAgevolazione(req.getListaModalitaAgevolazione());
			return esito;
		}
		
		esito.setEsito(true);
		return esito;
	}
	
	public void validaImportiWarning(ArrayList<String> errori, ArrayList<ModalitaAgevolazione> listaModalitaAgevolazione, ArrayList<FinanziamentoFonteFinanziaria> fontiFiltrate, Double totaleSpesaAmmessaRimodulazione, Long idUtente, String idIride) throws GestioneDatiDiDominioException, SystemException, UnrecoverableException, CSIException {
		logger.info("validaImportiBloccanti()");
		
		ArrayList<ModalitaAgevolazione> listModalita = listaModalitaAgevolazione;
		ArrayList<FinanziamentoFonteFinanziaria> listFonti = fontiFiltrate;
		
		boolean errors = false;
		boolean errorsM5 = false;
		boolean errorsM6 = false;
		boolean errorsM7 = false;
		boolean errorsM8 = false;
		boolean errorsM14 = false;
		boolean errorsM15 = false;
		
		//Controlli relativi alle modalita' di finanziamento
		
		for (int i = 0; i < listModalita.size() - 1; ++i) {
			ModalitaAgevolazione modalita = listModalita.get(i);
			boolean errorsImportoAgevolato = false;
			boolean errorsPercImportoAgevolato = false;
			modalita.setHasImportoAgevolatoError(false);
			modalita.setHasPercImportoAgevolatoError(false);

			// importoAgevolato > importoMassimoAgevolato (M5)

			if (!errorsImportoAgevolato && modalita.getImportoMassimoAgevolato() != null &&
				NumberUtil.compare(modalita.getImportoAgevolato(), modalita.getImportoMassimoAgevolato()) > 0) {
				modalita.setHasImportoAgevolatoError(true);
				modalita.setErrorImportoAgevolatoMsg("( a )");
				if (!errorsM5) {
					//MessageManager.setGlobalErrors(result, MessageKey.KEY_WARNING_CONCLUDI_RIMODULAZIONE_M5);
					errori.add("Gli importi contrassegnati con <cite>( a )</cite> sono superiori rispetto al relativo importo massimo agevolazione previsto per la modalit\u00E0 di contribuzione.");
					errors = true;
					errorsM5 = true;
				}
				errorsImportoAgevolato = true;
			}

			// percImportoAgevolato > percImportoMassimoAgevolato (M6)
			
			if (!errorsPercImportoAgevolato && modalita.getPercentualeMassimoAgevolato() != null &&
				NumberUtil.compare(modalita.getPercentualeImportoAgevolato(), modalita.getPercentualeMassimoAgevolato()) > 0) {
				modalita.setHasPercImportoAgevolatoError(true);
				modalita.setErrorPercImportoAgevolatoMsg("( b )");
				if (!errorsM6) {
					//MessageManager.setGlobalErrors(result,MessageKey.KEY_WARNING_CONCLUDI_RIMODULAZIONE_M6);
					errori.add("Le percentuali contrassegnate con <cite>( b )</cite> sono superiori rispetto alla relativa percentuale di importo  previsto per la modalit\u00E0 di contribuzione.");
					errors = true;
					errorsM6 = true;
				}
				errorsPercImportoAgevolato = true;
			}

			// percImportoAgevolato < percImportoMassimoAgevolato (M7)
			if (!errorsPercImportoAgevolato && modalita.getPercentualeMassimoAgevolato() != null &&
				NumberUtil.compare(modalita.getPercentualeImportoAgevolato(), modalita.getPercentualeMassimoAgevolato()) < 0) {
				modalita.setHasPercImportoAgevolatoError(true);
				modalita.setErrorPercImportoAgevolatoMsg("( c )");
				if (!errorsM7) {
					//MessageManager.setGlobalErrors(result,MessageKey.KEY_WARNING_CONCLUDI_RIMODULAZIONE_M7);
					errori.add("Le percentuali contrassegnate con <cite>( c )</cite> sono inferiori rispetto alla relativa percentuale di importo  previsto per la modalit\u00E0 di contribuzione.");
					errors = true;
					errorsM7 = true;
				}
				errorsPercImportoAgevolato = true;
			}

			// importoAgevolato < importoErogato (M8)

			if (!errorsImportoAgevolato	&& modalita.getImportoErogato() != null	&&
				NumberUtil.compare(modalita.getImportoAgevolato(), modalita.getImportoErogato()) < 0) {
				modalita.setHasImportoAgevolatoError(true);
				modalita.setErrorImportoAgevolatoMsg("( d )");
				if (!errorsM8) {
					//MessageManager.setGlobalMessage(result,MessageKey.KEY_WARNING_CONCLUDI_RIMODULAZIONE_M8);
					errori.add("Gli importi contrassegnati con <cite>( d )</cite> sono inferiori rispetto al relativo importo gi\u00E0 erogato per la modalit\u00E0 di contribuzione.");
					errors = true;
					errorsM8 = true;
				}
				errorsImportoAgevolato = true;
			}

			if (!errorsImportoAgevolato	&& modalita.getImportoAgevolato() != null && 
				NumberUtil.compare(modalita.getImportoAgevolato(), modalita.getUltimoImportoAgevolato()) > 0) {
				modalita.setHasImportoAgevolatoError(true);
				modalita.setErrorImportoAgevolatoMsg(" ( e )");
				errorsImportoAgevolato = true;
				if (!errorsM14) {
					//MessageManager.setGlobalMessage(result,MessageKey.KEY_WARNING_CONCLUDI_PROPOSTA_M14);
					errori.add("Gli importi contrassegnati con <cite>( e )</cite> sono superiori rispetto all\u2019  ultimo importo agevolato per la modalit\u00E0 di agevolazione.");
					errorsM14 = true;
					errors = true;
				}
				errorsImportoAgevolato = true;
			}

			if (!errorsImportoAgevolato	&& modalita.getImportoAgevolato() != null && 
				NumberUtil.compare(modalita.getImportoAgevolato(), modalita.getUltimoImportoAgevolato()) < 0) {
				modalita.setHasImportoAgevolatoError(true);
				modalita.setErrorImportoAgevolatoMsg(" ( f )");
				errorsImportoAgevolato = true;
				if (!errorsM15) {
					//MessageManager.setGlobalMessage(result, MessageKey.KEY_WARNING_CONCLUDI_PROPOSTA_M15);
					errori.add("Gli importi contrassegnati con <cite>( f )</cite> sono inferiori rispetto all\u2019 ultimo importo agevolato per la modalit\u00E0 di agevolazione.");
					errorsM15 = true;
					errors = true;
				}
				errorsImportoAgevolato = true;
			}

		}

		logger.info(" controllo subtotale fonti con totale modalita agevolazione");
	
		// Controlli relativi alle fonti finanziarie
		
		ModalitaAgevolazione totaleModalita = listModalita.get(listModalita.size() - 1);
		logger.info("\ntotaleModalita.getImportoAgevolato() ---> " + totaleModalita.getImportoAgevolato());

		Double importoSubTotale = getImportoSubtotaleFontiPubbliche(listFonti);
		logger.info("importoSubTotale ---> " + importoSubTotale);
		if (NumberUtil.compare(totaleModalita.getImportoAgevolato(), importoSubTotale) > 0) {
			//MessageManager.setGlobalErrors(result,MessageKey.KEY_WARNING_CONCLUDI_RIMODULAZIONE_M9);
			errori.add("Il totale dei nuovi importi agevolati \u00E8 superiore rispetto al subtotale delle fonti di finanziamento.");
			errors = true;
			// XXX bloccante per regola BR024 1
		}

		if (NumberUtil.compare(totaleModalita.getImportoAgevolato(), importoSubTotale) < 0) {
			//MessageManager.setGlobalErrors(result, MessageKey.KEY_WARNING_CONCLUDI_RIMODULAZIONE_M11);
			errori.add("Il totale dei nuovi importi agevolati \u00E8 inferiore rispetto al subtotale delle fonti di finanziamento.");
			errors = true;
			// XXX bloccante per regola BR024 2
		}

		Double importoTotaleFonti = getImportoTotaleFonti(listFonti);
		Double totaleNuovoAmmessoRimodulazione = NumberUtil.getDoubleValue(totaleSpesaAmmessaRimodulazione);
		logger.info("\nimportoTotaleFonti: " + importoTotaleFonti + "\ntotaleNuovoAmmessoRimodulazione:" + totaleNuovoAmmessoRimodulazione);

		// totale nuove quote finanziarie > totale della spesa ammessa (M12)

		if (importoTotaleFonti > totaleNuovoAmmessoRimodulazione) {
			//MessageManager.setGlobalErrors(result,MessageKey.KEY_WARNING_CONCLUDI_RIMODULAZIONE_M12);
			errori.add("Il totale delle nuove quote fonti finanziarie \u00E8 superiore rispetto al totale della spesa ammessa in rimodulazione.");
			errors = true;
			logger.info("Errore totale fonti > nuovo ammesso in rimod ");
			// XXX bloccante per regola BR025 3
		}

		// totale nuove quote finanziarie < totale della spesa ammessa (M13)

		if (importoTotaleFonti < totaleNuovoAmmessoRimodulazione) {
			//MessageManager.setGlobalErrors(result, MessageKey.KEY_WARNING_CONCLUDI_RIMODULAZIONE_M13);
			errori.add("Il totale delle nuove quote fonti finanziarie \u00E8 inferiore rispetto al totale della spesa ammessa in rimodulazione.");
			errors = true;
			logger.info("Errore totale fonti < nuovo ammesso in rimod ");
			// XXX bloccante per regola BR025 4
		}

	}

	public void validaImportiBloccanti(ArrayList<String> errori, ArrayList<ModalitaAgevolazione> listaModalitaAgevolazione, ArrayList<FinanziamentoFonteFinanziaria> fontiFiltrate, Double totaleSpesaAmmessaRimodulazione, Long idBando, Long idUtente, String idIride) throws GestioneDatiDiDominioException, SystemException, UnrecoverableException, CSIException {
		
		boolean abilitataBR24 = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(
				idUtente, idIride, idBando,	RegoleConstants.BR24_CHECK_RIMODULAZIONE_NUOVI_IMPORTI_AGEVOLATI_RISPETTO_FONTI_FINANZIARIE);

		boolean abilitataBR25 = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(
				idUtente, idIride, idBando,	RegoleConstants.BR25_CHECK_RIMODULAZIONE_NUOVE_QUOTE_FINANZIARIE_RISPETTO_SPESA_AMMESSA);		
				
		ArrayList<ModalitaAgevolazione> listModalita = listaModalitaAgevolazione;
		ArrayList<FinanziamentoFonteFinanziaria> listFonti = fontiFiltrate;
		
		ModalitaAgevolazione totaleModalita = listModalita.get(listModalita.size() - 1);
		Double importoSubTotale = getImportoSubtotaleFontiPubbliche(listFonti);
		
		logger.info("validaImportiBloccanti(): abilitataBR24 = "+abilitataBR24+"; abilitataBR25 = "+abilitataBR25+"; ImportoAgevolato = "+totaleModalita.getImportoAgevolato()+"; importoSubTotale = "+importoSubTotale);

		if (abilitataBR24) {
			logger.info("controlli per isBR24 ... ");
			if (NumberUtil.compare(totaleModalita.getImportoAgevolato(), importoSubTotale) > 0) {
				//MessageManager.setGlobalMessage(result,MessageKey.KEY_WARNING_CONCLUDI_RIMODULAZIONE_M9);
				errori.add("Il totale dei nuovi importi agevolati \u00E8 superiore rispetto al subtotale delle fonti di finanziamento.");
				logger.info("isBR24,importo agevolato > importoSubTotale");
			}

			if (NumberUtil.compare(totaleModalita.getImportoAgevolato(),importoSubTotale) < 0) {
				//MessageManager.setGlobalErrors(result,MessageKey.KEY_WARNING_CONCLUDI_RIMODULAZIONE_M11);
				errori.add("Il totale dei nuovi importi agevolati \u00E8 inferiore rispetto al subtotale delle fonti di finanziamento.");
				logger.info("isBR24,importo agevolato < importoSubTotale");
			}
		}

		if (abilitataBR25) {

			Double importoTotaleFonti = getImportoTotaleFonti(listFonti);
			logger.info("importoTotaleFonti ---> " + importoTotaleFonti);
			//Double totaleNuovoAmmessoRimodulazione = NumberUtil.getDoubleValue(theModel.getAppDatatotaleSpesaAmmessaRimodulazione());
			Double totaleNuovoAmmessoRimodulazione = NumberUtil.getDoubleValue(totaleSpesaAmmessaRimodulazione);			
			logger.info("totaleNuovoAmmessoRimodulazione ---> " + totaleNuovoAmmessoRimodulazione);

			// totale nuove quote finanziarie > totale della spesa ammessa (M12)
			
			if (importoTotaleFonti > totaleNuovoAmmessoRimodulazione) {
				//MessageManager.setGlobalErrors(result,MessageKey.KEY_WARNING_CONCLUDI_RIMODULAZIONE_M12);
				errori.add("Il totale delle nuove quote fonti finanziarie \u00E8 superiore rispetto al totale della spesa ammessa in rimodulazione.");
				logger.info("isBR25,importo totale fonti  > totaleNuovoAmmessoRimodulazione");
			}

			// totale nuove quote finanziarie < totale della spesa ammessa (M13)

			if (importoTotaleFonti < totaleNuovoAmmessoRimodulazione) {
				//MessageManager.setGlobalMessage(result,	MessageKey.KEY_WARNING_CONCLUDI_RIMODULAZIONE_M13);
				errori.add("Il totale delle nuove quote fonti finanziarie \u00E8 inferiore rispetto al totale della spesa ammessa in rimodulazione.");
				logger.info("isBR25,importo totale fonti  < totaleNuovoAmmessoRimodulazione");
			}
		}
		
	}
	
	private Double getImportoTotaleFonti(ArrayList<FinanziamentoFonteFinanziaria> listFonti) {
		Double importoTotale = 0d;
		for (FinanziamentoFonteFinanziaria fonte : listFonti) {
			if (!fonte.getIsSubTotale() && !fonte.getIsFonteTotale() && fonte.getImportoQuota() != null) {
				importoTotale = NumberUtil.sum(importoTotale, fonte.getImportoQuota());
				logger.info("sommo importoTotale: " + importoTotale	+ " a fonte.getImportoQuota():"
						+ fonte.getImportoQuota() + " , fonte: "
						+ fonte.getDescFonteFinanziaria() + " privata: "
						+ fonte.getIsFontePrivata());
			}
		}
		return importoTotale;
	}
	
	private Double getImportoSubtotaleFontiPubbliche(ArrayList<FinanziamentoFonteFinanziaria> listFonti) {
		Double importoSubTotale = 0d;
		for (FinanziamentoFonteFinanziaria fonte : listFonti) {
			logger.info("fonte: " + fonte.getDescFonteFinanziaria()
					+ " privata: " + fonte.getIsFontePrivata()
					+ " fonte.getImportoQuota():" + fonte.getImportoQuota());
			if (!fonte.getIsSubTotale() && !fonte.getIsFontePrivata()
					&& !fonte.getIsFonteTotale()
					&& fonte.getImportoQuota() != null) {
				logger.info("importoSubTotale " + importoSubTotale
						+ " -  fonte.getImportoQuota(): "
						+ fonte.getImportoQuota());
				importoSubTotale = NumberUtil.sum(importoSubTotale, fonte.getImportoQuota());
			}
		}
		return importoSubTotale;
	}
	
	public void validaQuotaUE(ArrayList<String> errori, ArrayList<FinanziamentoFonteFinanziaria> fontiFiltrate, Double totaleSpesaAmmessaRimodulazione, Long idUtente, String idIride) throws GestioneDatiDiDominioException, SystemException, UnrecoverableException, CSIException {

		ArrayList<Long> idSoggettiFinanziatoriNonFesr = new ArrayList<Long>();
		Long idSoggettoFinanziatoreUE = null;
		try {
			
			// Trovo i soggetti non-FESR
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica[] tipoCofPor;
			tipoCofPor = gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(
							idUtente, idIride,
							gestioneDatiDiDominioBusinessImpl.TIPO_SOGG_FINANZIAT,
							"descBreveTipoSoggFinanziat",
							Constants.TIPO_SOGGETTO_FINANZIATORE_COFPOR);
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica[] soggettiFinanziatoriNonCertificazione;
			soggettiFinanziatoriNonCertificazione = gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(
							idUtente, idIride,
							gestioneDatiDiDominioBusinessImpl.SOGGETTO_FINANZIATORE,
							"flagCertificazione",
							Constants.FLAG_FALSE);
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica[] soggettoFinanziatoreUE;
			soggettoFinanziatoreUE = gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(
							idUtente, idIride,
							gestioneDatiDiDominioBusinessImpl.SOGGETTO_FINANZIATORE,
							"descBreveSoggFinanziatore",
							Constants.SOGGETTO_FINANZIATORE_UE);
			
			if (soggettoFinanziatoreUE.length == 1) {
				idSoggettoFinanziatoreUE = soggettoFinanziatoreUE[0].getId();
			}
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica[] soggettiFinanziatoriCofPor = new it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica[0];
			if (tipoCofPor.length == 1) {
				soggettiFinanziatoriCofPor = gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(
							idUtente, idIride,
							gestioneDatiDiDominioBusinessImpl.SOGGETTO_FINANZIATORE,
							"idTipoSoggFinanziat", tipoCofPor[0].getId().toString());
			}
			
			// estraggo gli ID dei soggetti finanziatori non-FESR
			it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica[] soggettiFinanziatoriNonFesr = concat(
					soggettiFinanziatoriNonCertificazione,
					soggettiFinanziatoriCofPor);
			for (it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica decodifica : soggettiFinanziatoriNonFesr) {
				idSoggettiFinanziatoriNonFesr.add(decodifica.getId());
			}
			
		} catch (Exception e) {
			logger.error("Errore nella validazione della conclusione della rimodulazione: non ï¿½ stato possibile trovare gli id dei soggetti finanziatori non FESR", e);
		}
		
		BigDecimal ammesso = beanUtil.transform(totaleSpesaAmmessaRimodulazione, BigDecimal.class); // A
		Double percAltro = 0D;
		Double ammessoFESR=0d;
		boolean fonteFesrEsiste=false;
		BigDecimal impUE = new BigDecimal(0);
		
		ArrayList<FinanziamentoFonteFinanziaria> listaFontiFinanziarie = fontiFiltrate;
		for (int i = 0; i < listaFontiFinanziarie.size() - 1; ++i) {
			FinanziamentoFonteFinanziaria fonte = listaFontiFinanziarie.get(i);
			Double percImportoQuota = fonte.getPercentualeQuota();
			Double importoQuota = fonte.getImportoQuota();
			if(fonte.getFlagCertificazione() != null && fonte.getFlagCertificazione() &&
			   !Constants.TIPO_SOGGETTO_FINANZIATORE_COFPOR.equalsIgnoreCase(fonte.getDescBreveTipoSoggFinanziat()) &&
			   importoQuota != null) {
				fonteFesrEsiste=true;
				ammessoFESR=NumberUtil.sum(ammessoFESR,importoQuota);
			}
			
			if (!fonte.getIsSubTotale()) {
				if (percImportoQuota == null) {
					// DO NOTHING
				} else if (percImportoQuota < 0) {
					// già verificato lato Angular.
				} else if (idSoggettiFinanziatoriNonFesr.contains(fonte.getIdFonteFinanziaria())) {
					percAltro = NumberUtil.sum(percAltro, fonte.getPercentualeQuota());
				} else if (idSoggettoFinanziatoreUE.equals(fonte.getIdFonteFinanziaria())) {
					// entro qui solo una volta
					if (fonte.getImportoQuota() != null) {
						impUE = new BigDecimal(fonte.getImportoQuota());	
					}
				}
			}
		}
		
		// controllo le fonti in relazione alla quota UE
		
		// OLD CONTROLLO PERC_ALTRO  A / 100 = IMP_ALTRO
		//BigDecimal impAltro = NumberUtil.multiply(percAltro, NumberUtil.toDouble(NumberUtil.divide(ammesso, new BigDecimal(100))));
		//BigDecimal ammessoFESR = NumberUtil.subtract(ammesso, impAltro);
		
		// NEW CONTROL A_FESR= la somma degli importi corrispondenti ai record 
		// dei soggetti finanziatori eventualmente presenti sul progetto con 
		// FLAG_CERTIFICAZIONE='S' e ID_TIPO_SOGG_FINANZIAT diverso dalla DESC_BREVE='COFPOR'  

		// PBANDI-2767: considero l'importo al posto della percentuale.
		//BigDecimal impUE = NumberUtil.multiply(percUE, NumberUtil.toDouble(NumberUtil.divide(ammesso, new BigDecimal(100), 4)), 4);
		
//		17-07/2023 utilizzo la percentuale al posto dell'importo per dare un margine all'approssimazione
//		BigDecimal metaAmmessoFesr = new BigDecimal(0);
//		if(ammessoFESR > 0)
//			metaAmmessoFesr= NumberUtil.divide(ammessoFESR,	2d);
		
		// PBANDI-2767: il controllo rimane lo stesso, è cambiato solo il modo di reperire impUE.
//		logger.info("validaQuotaUE(): impUE = "+impUE+"; metaAmmessoFesr = "+metaAmmessoFesr);
		
		if(!ammessoFESR.equals(0D)) {
			BigDecimal percImpUE =  NumberUtil.divide(impUE.doubleValue() * 100D, ammessoFESR);
			logger.info("validaQuotaUE(): impUE = "+impUE+"; ammessoFESR = "+ammessoFESR + "; percImpUE = "+ percImpUE );
			if (NumberUtil.compare(percImpUE.setScale(5), new BigDecimal(50)) > 0) {
				// PBANDI-1377 : scatta il controllo bloccante
				errori.add("Per le fonti finanziarie, la quota UE complessiva non pu\u00F2 superare il 50% dell\u2019 ammissibile FESR (ovvero la somma delle quote FESR non private). Correggere la quota UE per proseguire.");
			}		
		}
		
	}
	
	private static it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica[] concat
			(it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica[] a, 
			 it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica[] b) {
		it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica[] c = new it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}
	
	private void loadFontiFinanziarie(
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerConclusioneRimodulazioneDTO datiDTO,
			InizializzaConcludiRimodulazioneDTO result) {
		String prf = "[ContoEconomicoDAOImpl::loadFontiFinanziarie] ";
		logger.info( prf + "BEGIN");
		
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO[] fontiNonPrivateDTO = datiDTO.getFontiFinanziarieNonPrivate();
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO[] fontiPrivateDTO = datiDTO.getFontiFinanziariePrivate();
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO totaleFontiDTO = datiDTO.getTotaliFontiFinanziarie();

		ArrayList<FinanziamentoFonteFinanziaria> listFonti = new ArrayList<FinanziamentoFonteFinanziaria>();
		ArrayList<FinanziamentoFonteFinanziaria> listFontiPrivate = new ArrayList<FinanziamentoFonteFinanziaria>();
		ArrayList<FinanziamentoFonteFinanziaria> listFontiNonPrivate = new ArrayList<FinanziamentoFonteFinanziaria>();

		HashMap<String, String> finanziamentoFonteFinanziariaMap = createFinanziamentoFonteFinanziariaMap();
		Boolean isPeriodoUnico = datiDTO.getIsPeriodoUnico();
		if (fontiNonPrivateDTO != null && fontiNonPrivateDTO.length > 0) {
			listFontiNonPrivate = creaFontiNonPrivate(fontiNonPrivateDTO,finanziamentoFonteFinanziariaMap);
			listFonti.addAll(listFontiNonPrivate);
		}

		if (fontiPrivateDTO != null && fontiPrivateDTO.length > 0) {
			listFontiPrivate = creaFontiPrivate(fontiPrivateDTO, finanziamentoFonteFinanziariaMap);
			listFonti.addAll(listFontiPrivate);
		}

		FinanziamentoFonteFinanziaria totaleFonti = getTotaleFonte(totaleFontiDTO, finanziamentoFonteFinanziariaMap);
		totaleFonti.setDescFonteFinanziaria(Constants.MSG_RIMODULAZIONE_TOTALE_COMPLESSIVO);

		//creare mappa periodoFontiNonPrivate
		Map<String, ArrayList<FinanziamentoFonteFinanziaria>> mapPeriodoFontiNonPrivate = creaMapPeriodoFonti(listFontiNonPrivate);
		//ciclare mappa, per ogni periodo calcolare il subtotale non privato 

		//creare mappa periodoFontiPrivate
		Map<String, ArrayList<FinanziamentoFonteFinanziaria>> mapPeriodoFontiPrivate = creaMapPeriodoFonti(listFontiPrivate);
		//ciclare mappe: per ogni periodo aggiungere totale(private+ non private) del periodo

		ArrayList<FinanziamentoFonteFinanziaria> fontiFiltrate = joinFonti(
				mapPeriodoFontiNonPrivate, mapPeriodoFontiPrivate, isPeriodoUnico);

		fontiFiltrate.add(totaleFonti);
		result.setFontiFiltrate(fontiFiltrate);
		//theModel.setAppDatafontiFinanziarieVisualizzate(fontiFiltrate);

		listFonti.add(totaleFonti);
		result.setListaFonti(listFonti);
		//theModel.setAppDatafontiFinanziarie(listFonti);
		
		logger.info(prf + " END");
	}
	
	private ArrayList<FinanziamentoFonteFinanziaria> joinFonti(
			Map<String, ArrayList<FinanziamentoFonteFinanziaria>> mapPeriodoFontiNonPrivate,
			Map<String, ArrayList<FinanziamentoFonteFinanziaria>> mapPeriodoFontiPrivate,
			Boolean isPeriodoUnico) {

		logger.info("\n\n\n\njoinFonti\n" + " mapPeriodoFontiNonPrivate : "	+ mapPeriodoFontiNonPrivate.size()	+ " mapPeriodoFontiPrivate : " + mapPeriodoFontiPrivate.size());

		ArrayList<FinanziamentoFonteFinanziaria> fontiFiltrate = new ArrayList<FinanziamentoFonteFinanziaria>();

		FinanziamentoFonteFinanziaria fonteSubTotaleComplessivo = new FinanziamentoFonteFinanziaria();
		fonteSubTotaleComplessivo.setIsSubTotale(true);
		fonteSubTotaleComplessivo.setDescFonteFinanziaria(Constants.MSG_RIMODULAZIONE_SUBTOTALE_COMPLESSIVO);
		for (Map.Entry<String, ArrayList<FinanziamentoFonteFinanziaria>> entryNonPrivata : mapPeriodoFontiNonPrivate.entrySet()) {
			ArrayList<FinanziamentoFonteFinanziaria> fontiNonPrivate = entryNonPrivata.getValue();
			FinanziamentoFonteFinanziaria fontePeriodo = new FinanziamentoFonteFinanziaria();
			fontePeriodo.setDescPeriodo(entryNonPrivata.getKey());
			fontiFiltrate.add(fontePeriodo);
			FinanziamentoFonteFinanziaria fonteSubtotaleNonPrivata = new FinanziamentoFonteFinanziaria();
			fonteSubtotaleNonPrivata.setIsSubTotale(true);
			fonteSubtotaleNonPrivata.setDescFonteFinanziaria(Constants.MSG_RIMODULAZIONE_SUBTOTALE);

			FinanziamentoFonteFinanziaria fonteTotalePeriodo = new FinanziamentoFonteFinanziaria();
			fonteTotalePeriodo.setIsFonteTotale(true);
			fonteTotalePeriodo.setDescFonteFinanziaria(Constants.MSG_RIMODULAZIONE_TOTALE);

			for (FinanziamentoFonteFinanziaria fonteNonPrivata : fontiNonPrivate) {
				fontiFiltrate.add(fonteNonPrivata);
				fonteSubtotaleNonPrivata.setImportoQuota(sum(
						fonteSubtotaleNonPrivata.getImportoQuota(),
						fonteNonPrivata.getImportoQuota()));
				fonteSubtotaleNonPrivata.setImportoUltimaQuota(sum(
						fonteSubtotaleNonPrivata.getImportoUltimaQuota(),
						fonteNonPrivata.getImportoUltimaQuota()));
				fonteSubtotaleNonPrivata.setPercentualeQuota(sum(
						fonteSubtotaleNonPrivata.getPercentualeQuota(),
						fonteNonPrivata.getPercentualeQuota()));
				fonteSubtotaleNonPrivata.setPercentualeUltimaQuota(sum(
						fonteSubtotaleNonPrivata.getPercentualeUltimaQuota(),
						fonteNonPrivata.getPercentualeUltimaQuota()));

				fonteTotalePeriodo.setImportoQuota(sum(fonteTotalePeriodo
						.getImportoQuota(), fonteNonPrivata.getImportoQuota()));
				fonteTotalePeriodo.setImportoUltimaQuota(sum(
						fonteTotalePeriodo.getImportoUltimaQuota(),
						fonteNonPrivata.getImportoUltimaQuota()));
				fonteTotalePeriodo.setPercentualeQuota(sum(
						fonteTotalePeriodo.getPercentualeQuota(),
						fonteNonPrivata.getPercentualeQuota()));
				fonteTotalePeriodo.setPercentualeUltimaQuota(sum(
						fonteTotalePeriodo.getPercentualeUltimaQuota(),
						fonteNonPrivata.getPercentualeUltimaQuota()));
			}

			// calcolare subtotale

			fonteSubtotaleNonPrivata.setDescFonteFinanziaria(Constants.MSG_RIMODULAZIONE_SUBTOTALE);
			fontiFiltrate.add(fonteSubtotaleNonPrivata);
			Double importoQuotaSommaSubTotali = fonteSubTotaleComplessivo.getImportoQuota();
			Double percentualeQuotaSommaSubTotali = fonteSubTotaleComplessivo.getPercentualeQuota();
			fonteSubTotaleComplessivo.setImportoQuota(sum(
					fonteSubtotaleNonPrivata.getImportoQuota(),
					importoQuotaSommaSubTotali));
			fonteSubTotaleComplessivo.setPercentualeQuota(sum(
					fonteSubtotaleNonPrivata.getPercentualeQuota(),
					percentualeQuotaSommaSubTotali));

			for (Map.Entry<String, ArrayList<FinanziamentoFonteFinanziaria>> entryPrivata : mapPeriodoFontiPrivate.entrySet()) {
				ArrayList<FinanziamentoFonteFinanziaria> fontiPrivate = entryPrivata.getValue();
				for (FinanziamentoFonteFinanziaria fontePrivata : fontiPrivate) {
					if (entryNonPrivata.getKey().equals(fontePrivata.getDescPeriodo())) {
						fontiFiltrate.add(fontePrivata);
						fonteTotalePeriodo.setImportoQuota(sum(
								fonteTotalePeriodo.getImportoQuota(),
								fontePrivata.getImportoQuota()));
						fonteTotalePeriodo.setImportoUltimaQuota(sum(
								fonteTotalePeriodo.getImportoUltimaQuota(),
								fontePrivata.getImportoUltimaQuota()));
						fonteTotalePeriodo.setPercentualeQuota(sum(
								fonteTotalePeriodo.getPercentualeQuota(),
								fontePrivata.getPercentualeQuota()));
						fonteTotalePeriodo.setPercentualeUltimaQuota(sum(
								fonteTotalePeriodo.getPercentualeUltimaQuota(),
								fontePrivata.getPercentualeUltimaQuota()));
					}
				}
			}

			// calcolare totale periodo
			if (isPeriodoUnico == null || !isPeriodoUnico)
				fontiFiltrate.add(fonteTotalePeriodo);
		}
		if (isPeriodoUnico == null || !isPeriodoUnico)
			fontiFiltrate.add(fonteSubTotaleComplessivo);
		
		return fontiFiltrate;
	}
	
	private static Double sum(Double a, Double b) {
		if (a == null && b == null) {
			return null;
		}
		if (a == null)
			return b;
		if (b == null)
			return a;
		return NumberUtil.sum(a, b);
	}
	
	private Map<String, ArrayList<FinanziamentoFonteFinanziaria>> creaMapPeriodoFonti(
			ArrayList<FinanziamentoFonteFinanziaria> listFonti) {
		Map<String, ArrayList<FinanziamentoFonteFinanziaria>> mapFontiPeriodo = new LinkedHashMap<String, ArrayList<FinanziamentoFonteFinanziaria>>();

		for (FinanziamentoFonteFinanziaria fonte : listFonti) {
			logger.info(fonte.getDescFonteFinanziaria() + " periodo " + fonte.getDescPeriodo() + "\n");
			if (mapFontiPeriodo.containsKey(fonte.getDescPeriodo())) {
				ArrayList<FinanziamentoFonteFinanziaria> fontiPeriodo = mapFontiPeriodo.get(fonte.getDescPeriodo());
				fontiPeriodo.add(fonte);
				mapFontiPeriodo.put(fonte.getDescPeriodo(), fontiPeriodo);
			} else {
				ArrayList<FinanziamentoFonteFinanziaria> fontiPeriodo = new ArrayList<FinanziamentoFonteFinanziaria>();
				fontiPeriodo.add(fonte);
				mapFontiPeriodo.put(fonte.getDescPeriodo(), fontiPeriodo);
			}
		}

		return mapFontiPeriodo;
	}
	
	private FinanziamentoFonteFinanziaria getTotaleFonte(
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO totaleFontiDTO,
			HashMap<String, String> finanziamentoFonteFinanziariaMap) {
		FinanziamentoFonteFinanziaria totaleFonte = new FinanziamentoFonteFinanziaria();
		if (totaleFontiDTO != null) {
			totaleFonte = beanUtil.transform(totaleFontiDTO,
					FinanziamentoFonteFinanziaria.class,
					finanziamentoFonteFinanziariaMap);
		}

		totaleFonte.setIsFonteTotale(true);
		totaleFonte.setDescFonteFinanziaria(Constants.MSG_RIMODULAZIONE_TOTALE);

		return totaleFonte;
	}
	
	private ArrayList<FinanziamentoFonteFinanziaria> creaFontiPrivate(
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO[] fontiPrivateDTO,
			HashMap<String, String> finanziamentoFonteFinanziariaMap) {
		ArrayList<FinanziamentoFonteFinanziaria> listFontiPrivate = new ArrayList<FinanziamentoFonteFinanziaria>();
		for (it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO dto : fontiPrivateDTO) {
			FinanziamentoFonteFinanziaria fonte = beanUtil.transform(dto,
					FinanziamentoFonteFinanziaria.class,
					finanziamentoFonteFinanziariaMap);
			fonte.setModificabile(true);
			fonte.setIsFontePrivata(true);
			listFontiPrivate.add(fonte);
		}
		return listFontiPrivate;
	}
	
	private ArrayList<FinanziamentoFonteFinanziaria> creaFontiNonPrivate(
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO[] fontiNonPrivateDTO,
			HashMap<String, String> finanziamentoFonteFinanziariaMap) {
		ArrayList<FinanziamentoFonteFinanziaria> listFontiNonPrivate = new ArrayList<FinanziamentoFonteFinanziaria>();
		for (it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO dto : fontiNonPrivateDTO) {
			FinanziamentoFonteFinanziaria fonte = beanUtil.transform(dto,
					FinanziamentoFonteFinanziaria.class,
					finanziamentoFonteFinanziariaMap);
			fonte.setModificabile(true);
			fonte.setIsFontePrivata(false);
			listFontiNonPrivate.add(fonte);
		}
		return listFontiNonPrivate;
	}
	
	private HashMap<String, String> createFinanziamentoFonteFinanziariaMap() {
		HashMap<String, String> finanziamentoFonteFinanziariaMap = new HashMap<String, String>() {
			{
				put("descBreveTipoSoggFinanziat", "descBreveTipoSoggFinanziat");
				put("descPeriodoVisualizzata", "descPeriodo");
				put("descrizione", "descFonteFinanziaria");
				put("estremiProvv", "estremiProvvedimento");
				put("flagCertificazione", "flagCertificazione");
				put("flagEconomie", "flagEconomie");
				put("flagLvlprj", "flagLvlprj");
				put("idComune", "idComune");
				put("idDelibera", "idDelibera");
				put("idFonteFinanziaria", "idFonteFinanziaria");
				put("idNorma", "idNorma");
				put("idPeriodo", "idPeriodo");
				put("idProvincia", "idProvincia");
				put("idSoggetto", "idSoggettoPrivato");
				put("note", "note");
				put("percQuotaDefault", "percQuotaDefault");
				put("percQuotaFonteFinanziariaNuova", "percentualeQuota");
				put("percQuotaFonteFinanziariaUltima", "percentualeUltimaQuota");
				put("progrProgSoggFinanziat", "progrProgSoggFinanziat");
				put("quotaFonteFinanziariaNuova", "importoQuota");
				put("quotaFonteFinanziariaUltima", "importoUltimaQuota");
			}
		};
		return finanziamentoFonteFinanziariaMap;
	}
	
	private it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerConclusioneRimodulazioneDTO 
				caricaDatiPerConclusioneRimodulazione(Long idProgetto, Long idBandoLinea, Long idUtente, String idIride) 
						throws ContoEconomicoException, SystemException, UnrecoverableException, CSIException {
		logger.info("[ContoEconomicoDAOImpl::caricaDatiPerConclusioneRimodulazione] BEGIN");
		
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO progettoDTO = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO();
		progettoDTO.setIdProgetto(idProgetto);
		
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerConclusioneRimodulazioneDTO datiDTO;
		datiDTO = contoeconomicoBusinessImpl.caricaDatiPerConclusioneRimodulazione(idUtente, idIride, progettoDTO, idBandoLinea);
		logger.info("[ContoEconomicoDAOImpl::caricaDatiPerConclusioneRimodulazione] END");
		return datiDTO;
	}
	
	private ArrayList<ModalitaAgevolazione> loadModalitaAgevolazione (it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerConclusioneRimodulazioneDTO datiDTO) {
		logger.info("[ContoEconomicoDAOImpl::loadModalitaAgevolazione] BEGIN");
		
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO[] modalitaDTO = datiDTO.getModalitaDiAgevolazione();
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO totaliModalitaDTO = datiDTO.getTotaliModalitaDiAgevolazione();

		ArrayList<ModalitaAgevolazione> listModalita = new ArrayList<ModalitaAgevolazione>();

		HashMap<String, String> modalitaAgevolazioneMap = createModalitaAgevolazioneMap();

		if (modalitaDTO != null && modalitaDTO.length > 0) {
			
			for (int i = 0; i < modalitaDTO.length; i++) {
				it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO modalitaDiAgevolazioneDTO = modalitaDTO[i];
				LOG.shallowDump(modalitaDiAgevolazioneDTO, "debug");
			}
			addModalita(modalitaDTO, listModalita, modalitaAgevolazioneMap);
		}

		LOG.shallowDump(totaliModalitaDTO, "debug");
		if (totaliModalitaDTO != null) {
			addTotaleModalita(totaliModalitaDTO, listModalita,
					modalitaAgevolazioneMap);
		}
		logger.info("[ContoEconomicoDAOImpl::loadModalitaAgevolazione] END");
		return listModalita;
	}
	
	private HashMap<String, String> createModalitaAgevolazioneMap() {
		HashMap<String, String> modalitaAgevolazioneMap = new HashMap<String, String>() {
			{
				put("descrizione", "descModalita");
				put("idModalitaDiAgevolazione", "idModalitaAgevolazione");
				put("importoAgevolatoNuovo", "importoAgevolato");
				put("importoAgevolatoUltimo", "ultimoImportoAgevolato");
				put("importoErogato", "importoErogato");
				put("importoMassimoAgevolato", "importoMassimoAgevolato");
				put("importoRichiestoUltimo", "ultimoImportoRichiesto");
				put("percImportoMassimoAgevolato", "percentualeMassimoAgevolato");
				put("percImportoAgevolatoNuovo", "percentualeImportoAgevolato");
				put("percImportoAgevolatoUltimo", "percentualeUltimoImportoAgevolato");
			}
		};
		return modalitaAgevolazioneMap;
	}
	
	private void addModalita(it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO[] modalitaDTO,
			ArrayList<ModalitaAgevolazione> listModalita,
			HashMap<String, String> modalitaAgevolazioneMap) {
		for (it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO dto : modalitaDTO) {
			ModalitaAgevolazione modalita = beanUtil.transform(dto,	ModalitaAgevolazione.class, modalitaAgevolazioneMap);
			modalita.setIsModalitaTotale(false);
			modalita.setModificabile(true);
			listModalita.add(modalita);
		}
	}
	
	private void addTotaleModalita(it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO totaliModalitaDTO,
			ArrayList<ModalitaAgevolazione> listModalita,
			HashMap<String, String> modalitaAgevolazioneMap) {
		ModalitaAgevolazione totaleModalita = beanUtil.transform(
				totaliModalitaDTO, ModalitaAgevolazione.class,
				modalitaAgevolazioneMap);
		totaleModalita.setDescModalita(Constants.MSG_PROPOSTA_RIMODULAZIONE_TOTALE);
		totaleModalita.setIsModalitaTotale(true);
		totaleModalita.setModificabile(false);
		listModalita.add(listModalita.size(), totaleModalita);
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class})
	public EsitoSalvaRimodulazioneConfermataDTO salvaRimodulazioneConfermata(SalvaRimodulazioneConfermataRequest salvaRimodulazioneConfermataRequest, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::salvaRimodulazioneConfermata] ";
		logger.info(prf + " BEGIN");		
				
		if (salvaRimodulazioneConfermataRequest == null) {
			throw new InvalidParameterException("salvaRimodulazioneConfermataRequest non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		logger.info(prf + salvaRimodulazioneConfermataRequest.toString());
		
		Long idProgetto = salvaRimodulazioneConfermataRequest.getIdProgetto();
		Long idContoEconomico = salvaRimodulazioneConfermataRequest.getIdContoEconomico();
		Long idSoggetto = salvaRimodulazioneConfermataRequest.getIdSoggettoBeneficiario();
		Date dataConcessione = salvaRimodulazioneConfermataRequest.getDataConcessione();
		String note = salvaRimodulazioneConfermataRequest.getNote();
		String riferimento = salvaRimodulazioneConfermataRequest.getRiferimento();		
		Double importoFinanziamentoBanca = salvaRimodulazioneConfermataRequest.getImportoFinanziamentoBancario();
		Double importoVincolante = salvaRimodulazioneConfermataRequest.getImportoImpegnoGiuridico();
		List<ModalitaAgevolazione> listModalita = salvaRimodulazioneConfermataRequest.getListaModalitaAgevolazione();
		List<FinanziamentoFonteFinanziaria> listFonti = salvaRimodulazioneConfermataRequest.getListaFonti();
		Long idProceduraAggiudicazione = null;
		String contrattiDaStipulare = null;
		
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idContoEconomico == null) {
			throw new InvalidParameterException("idContoEconomico non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (dataConcessione == null) {
			throw new InvalidParameterException("dataConcessione non valorizzato.");
		}
		if (riferimento == null) {
			throw new InvalidParameterException("riferimento non valorizzato.");
		}
		if (importoVincolante == null) {
			throw new InvalidParameterException("importoVincolante non valorizzato.");
		}
		if (listModalita == null) {
			throw new InvalidParameterException("listModalita non valorizzato.");
		}
		if (listFonti == null) {
			throw new InvalidParameterException("listFonti non valorizzato.");
		}

		EsitoSalvaRimodulazioneConfermataDTO esitoSalva = new EsitoSalvaRimodulazioneConfermataDTO();
		try {
			
			// l'app data conteneva dataConcessione, note, etc.
			//DettagliConcludiRimodulazione dettagli = theModel.getAppDatadettagliConcludiRimodulazione();
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO progettoDTO = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO();
			progettoDTO.setIdProgetto(idProgetto);

			// Alex: metto valori a caso poichè il metodo di pbandisrv li vuole obbligatori ma non li usa.
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.IstanzaAttivitaDTO istanza = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.IstanzaAttivitaDTO();		
			istanza.setId("TaskIdentity");					//istanza.setId(istanzaAttivita.getTaskIdentity());
			istanza.setCodUtente("CodUtenteFlux");			//istanza.setCodUtente(userInfoHelper.getCodUtenteFlux(currentUser));

			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerConclusioneRimodulazioneDTO datiDTO = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerConclusioneRimodulazioneDTO();
			datiDTO.setProgetto(progettoDTO);
			
			// 17/6/2011			
			datiDTO.setImportoImpegnoVincolante(importoVincolante);
			
			// Modalita
			
			logger.info(prf+"Modalita");
			HashMap<String, String> trsMap = new HashMap<String, String>() {
				{
					put("descModalita", "descrizione");
					put("idModalitaAgevolazione", "idModalitaDiAgevolazione");
					put("importoAgevolato", "importoAgevolatoNuovo");
					put("ultimoImportoAgevolato", "importoAgevolatoUltimo");
					put("importoErogato", "importoErogato");
					put("importoMassimoAgevolato", "importoMassimoAgevolato");
					put("importoRichiesto", "importoRichiestoNuovo");
					put("ultimoImportoRichiesto", "importoRichiestoUltimo");
					put("percentualeImportoAgevolato", "percImportoAgevolatoNuovo");
					put("percentualeUltimoImportoAgevolato", "percImportoAgevolatoUltimo");
					put("percentualeMassimoAgevolato", "percImportoMassimoAgevolato");
					put("percentualeImportoRichiesto", "percImportoRichiestoNuovo");
				}
			};
									
			ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO> listModalitaDTO = new ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO>();
			for (int i = 0; i < listModalita.size() - 1; ++i) {
				ModalitaAgevolazione modalita = listModalita.get(i);
				listModalitaDTO.add(beanUtil.transform(modalita, it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO.class, trsMap));
			}
			datiDTO.setModalitaDiAgevolazione(listModalitaDTO.toArray(new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO[] {}));

			// Fonti finanziarie
			
			logger.info(prf+"Fonti finanziarie");
			List<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO> fontiPrivate = new ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO>();
			List<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO> fontiNonPrivate = new ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO>();
			trsMap = new HashMap<String, String>() {
				{
					put("descFonteFinanziaria", "descrizione");
					put("estremiProvvedimento", "estremiProvv");
					put("flagEconomie", "flagEconomie");
					put("flagLvlprj", "flagLvlprj");
					put("idComune", "idComune");
					put("idDelibera", "idDelibera");
					put("idFonteFinanziaria", "idFonteFinanziaria");
					put("idNorma", "idNorma");
					put("idPeriodo", "idPeriodo");
					put("idProvincia", "idProvincia");
					put("idSoggettoPrivato", "idSoggetto");
					put("importoQuota", "quotaFonteFinanziariaNuova");
					put("importoUltimaQuota", "quotaFonteFinanziariaUltima");
					put("note", "note");
					put("percentualeQuota", "percQuotaFonteFinanziariaNuova");
					put("percentualeUltimaQuota", "percQuotaFonteFinanziariaUltima");
					put("progrProgSoggFinanziat", "progrProgSoggFinanziat");
				}
			};
			
			for (FinanziamentoFonteFinanziaria fonte : listFonti) {
				// Alex: fonte.getImportoQuota() != null l'ho aggiunto rispetto al vecchio
				// perchè in input ricevo le fonti visualizzate a video
				// che hanno delle righe in più.
				if (fonte.getImportoQuota() != null) {
					if (!fonte.getIsSubTotale()) {
						logger.info("fonte: "+fonte.getDescFonteFinanziaria()+
								    ", fonte.getPercentualeQuota(): "+fonte.getPercentualeQuota()+
								   ", fonte.getImportoQuota(): "+fonte.getImportoQuota());
					}
					if (fonte.getIsFontePrivata()) {
						fontiPrivate.add(beanUtil.transform(fonte, it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO.class, trsMap));
					} else if(!fonte.getIsFonteTotale()){
						fontiNonPrivate.add(beanUtil.transform(fonte, it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO.class, trsMap));
					}
				}
			}

			datiDTO.setFontiFinanziarieNonPrivate(fontiNonPrivate.toArray(new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO[] {}));
			datiDTO.setFontiFinanziariePrivate(fontiPrivate.toArray(new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO[] {}));

			if (dataConcessione != null)				
				datiDTO.setDataConcessione(DateUtil.getDate(dataConcessione));
			datiDTO.setNote(note);
			datiDTO.setContrattiDaStipulare(contrattiDaStipulare);
			datiDTO.setRiferimento(riferimento);
			
			datiDTO.setIdSoggetto(idSoggetto);
			datiDTO.setIdContoEconomico(idContoEconomico);
			datiDTO.setImportoFinanziamentoBancario(importoFinanziamentoBanca);

			// Procedura aggiudicazione
			datiDTO.setIdProceduraAggiudicazione(idProceduraAggiudicazione);
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoConclusioneRimodulazioneDTO esito;
			esito = contoeconomicoBusinessImpl.concludiRimodulazione(idUtente, idIride, istanza, datiDTO);

			// Alex: non ho capito a cosa serve.
			//theModel.getSession().put(Constants.ID_CONTO_ECONOMICO_LOCAL,esito.getIdContoEconomicoLocal());

			//MessageManager.setGlobalMessages(result,MessageKey.KEY_MSG_RIMODULAZIONE_CONCLUSA);
			
			esitoSalva.setEsito(true);
			esitoSalva.getMessaggi().add("La rimodulazione del conto economico \u00E8 stata conclusa correttamente.");
			// Usato per il bottone 'scarica rimodulazione' alla fine del salvataggio.
			esitoSalva.setNuovoIdContoEconomico(esito.getIdContoEconomicoLocal());			

			logger.info(prf + esitoSalva.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return esitoSalva;
	}
	
	@Override
	public EsitoLeggiFile scaricaRimodulazione (Long idProgetto, Long idSoggettoBeneficiario, Long idContoEconomico, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::scaricaRimodulazione] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + "idProgetto = "+idProgetto+"; idSoggettoBeneficiario = "+idSoggettoBeneficiario+"; idContoEconomico = "+idContoEconomico+"; idUtente = "+idUtente);
				
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idSoggettoBeneficiario == null) {
			throw new InvalidParameterException("idSoggettoBeneficiario non valorizzato.");
		}
		if (idContoEconomico == null) {
			throw new InvalidParameterException("idContoEconomico non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		EsitoLeggiFile result = new EsitoLeggiFile();
		try {
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoScaricaRimodulazioneDTO esitoScarica;
			esitoScarica = contoeconomicoBusinessImpl.scaricaRimodulazione(idUtente, idIride, idProgetto, idSoggettoBeneficiario, idContoEconomico);
			
			if (esitoScarica == null || esitoScarica.getBytesPdf() == null || esitoScarica.getBytesPdf().length == 0)
				throw new Exception("Pdf rimodulazione non trovato.");
			
			result.setEsito(true);
			result.setNomeFile(esitoScarica.getNomeFile());
			result.setBytes(esitoScarica.getBytesPdf());
			
			logger.info(prf+"esito = "+result.getEsito()+"; nome file = "+result.getNomeFile());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return result;
	}
	
	@Override
	public InizializzaRimodulazioneIstruttoriaDTO inizializzaRimodulazioneIstruttoria(Long idProgetto, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::inizializzaRimodulazioneIstruttoria] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + "idProgetto = "+idProgetto+"; idUtente = "+idUtente);
				
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		InizializzaRimodulazioneIstruttoriaDTO result = new InizializzaRimodulazioneIstruttoriaDTO();
		try {
			
			// Codice visualizzato del progetto.
			String sql = "SELECT CODICE_VISUALIZZATO FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = ?";
			Object[] param = new Object[] {idProgetto};
			String codiceProgetto = getJdbcTemplate().queryForObject(sql, param, String.class);
			result.setCodiceVisualizzatoProgetto(codiceProgetto);
			
			// CONTO ECONOMICO LETTO DA DB.
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO  esito;
			esito = this.findContoEconomicoPerRimodulazioneIstruttoria(idProgetto,idUtente, idIride);
			result.setEsitoFindContoEconomicoDTO(esito);
			
			// DATI DEL CONTO ECONOMICO.
			
			ContoEconomico contoEconomico = getDatiContoEconomicoIstruttoria(esito);			
			//result.setDatiContoEconomico(contoEconomico);
			
			// RIGHE DEL CONTO ECONOMICO PER VISUALIZZAZIONE.

			ArrayList<ContoEconomicoItem> righe = getContoEconomicoItemIstruttoria(contoEconomico, esito);
			result.setRigheContoEconomico(righe);
						
			//if (theModel.getAppDatarimodulazioneConclusa() != null	&& theModel.getAppDatarimodulazioneConclusa().booleanValue()) {
			//	result.setResultCode(CARICACONTOECONOMICO_OUTCOME_CODE__ATTIVITA_TERMINATA);
			//} else if (!esitoFindContoEconomicoDTO.getModificabile()) {
			//	MessageManager.setGlobalError(result,MessageKey.FUNZIONALITA_RIMODULAZIONE_NON_ABILITATA);
			//	result.setResultCode(CARICACONTOECONOMICO_OUTCOME_CODE__CONTO_LOCKED);
			//} else if (esitoFindContoEconomicoDTO.getInIstruttoria()) {
			//	result.setResultCode(CARICACONTOECONOMICO_OUTCOME_CODE__ATTIVITA_CONCLUSA);
			//} else {
			//	result.setResultCode(CARICACONTOECONOMICO_OUTCOME_CODE__ATTIVITA_DA_SVOLGERE);
			//}

			logger.info(prf + result.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return result;
	}
	
	public it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO findContoEconomicoPerRimodulazioneIstruttoria(
			Long idProgetto, Long idUtente, String identitaDigitale) throws Exception {
		
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO progetto = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO();
		progetto.setIdProgetto(idProgetto);
		
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esito;
		esito = contoeconomicoBusinessImpl.findContoEconomicoPerRimodulazioneIstruttoria(idUtente, identitaDigitale, progetto);
		return esito;
	}
	
	private ArrayList<ContoEconomicoItem> getContoEconomicoItemIstruttoria(
			ContoEconomico contoEconomico, 
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO) throws Exception {
		
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione = esitoFindContoEconomicoDTO.getContoEconomico();

		boolean isModifica = false;
		
		ArrayList<ContoEconomicoItem> contoEconomicoItemList = new ArrayList<ContoEconomicoItem>();
		contoEconomicoItemList = contoEconomicoIstruttoriaManager
				.mappaContoEconomicoPerVisualizzazioneIstruttoria(
						contoEconomico,
						contoEconomicoRimodulazione,
						contoEconomicoItemList, isModifica,
						esitoFindContoEconomicoDTO.getInStatoRichiesto());
		
		return contoEconomicoItemList;
	}
	
	private ContoEconomico getDatiContoEconomicoIstruttoria(
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO) throws Exception {

		ContoEconomico contoEconomico = new ContoEconomico();
		
		if (esitoFindContoEconomicoDTO.getInIstruttoria()) {
			contoEconomico.setNascondiColonnaSpesaAmmessa(false);
		} else if (esitoFindContoEconomicoDTO.getInStatoRichiesto()) {
			contoEconomico.setNascondiColonnaSpesaAmmessa(true);
		} else
			logger.info("ATTENZIONE! INCONGRUENZA: lo stato del conto economico non e' tra quelli previsti [IN ISTRUTTORIA | RICHIESTO ]");
		
		if (esitoFindContoEconomicoDTO.getDataPresentazioneDomanda() != null)
			contoEconomico.setDataPresentazioneDomanda(DateUtil.getDate(esitoFindContoEconomicoDTO.getDataPresentazioneDomanda()));
				
		return contoEconomico;		
	}
	
	@Override
	// Chiamato al click su Salva.
	public EsitoSalvaRimodulazioneDTO validaRimodulazioneIstruttoria(SalvaRimodulazioneRequest salvaRimodulazioneRequest, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::validaRimodulazioneIstruttoria] ";
		logger.info(prf + " BEGIN");		
				
		if (salvaRimodulazioneRequest == null) {
			throw new InvalidParameterException("salvaRimodulazioneRequest non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		Long idProgetto = salvaRimodulazioneRequest.getIdProgetto();
		ArrayList<ContoEconomicoItem> contoEconomicoItemList = salvaRimodulazioneRequest.getContoEconomicoItemList();
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (contoEconomicoItemList == null) {
			throw new InvalidParameterException("contoEconomicoItemList non valorizzato.");
		}
		logger.info(prf + "idProgetto = "+idProgetto+"; idUtente = "+idUtente);
		
		EsitoSalvaRimodulazioneDTO esitoSalva = new EsitoSalvaRimodulazioneDTO();
		try {
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO;
			esitoFindContoEconomicoDTO = this.findContoEconomicoPerRimodulazioneIstruttoria(idProgetto, idUtente, idIride);

			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione = esitoFindContoEconomicoDTO.getContoEconomico();

			// contoEconomicoItemList ora arriva da Angular come parametro di input.
			//ArrayList<ContoEconomicoItem> contoEconomicoItemList = theModel.getAppDatacontoEconomico();

			boolean isModifica = false;
			
			ContoEconomico contoEconomico = getDatiContoEconomicoIstruttoria(esitoFindContoEconomicoDTO);

			contoEconomicoItemList = contoEconomicoIstruttoriaManager
					.mappaContoEconomicoPerVisualizzazioneIstruttoria(
					contoEconomico,
					contoEconomicoRimodulazione,
					contoEconomicoItemList, isModifica,
					esitoFindContoEconomicoDTO.getInStatoRichiesto());

			logger.info(prf+"inizio controlli validazione.");

			Map<?, ContoEconomicoItem> mapContoEconomicoItem = beanUtil.index(contoEconomicoItemList, "idVoce");

			Set<String> set = new HashSet<String>();

			Map<String, String> fieldErrors = new HashMap<String, String>();

			set = contoEconomicoIstruttoriaManager.controllaImportiPerRimodulazioneIstruttoria(
					mapContoEconomicoItem, contoEconomicoRimodulazione, set, fieldErrors);

			Collection<String> messaggi = new ArrayList<String>(set);

			if (messaggi.contains(MessageKey.IMPORTI_NON_VALIDI)) {
				esitoSalva.setEsito(false);
			} else {
				esitoSalva.setEsito(true);
				messaggi.add(MessageKey.CONFERMA_SALVATAGGIO);
			}
			
			for (String m : messaggi)
				esitoSalva.getMessaggi().add(TraduttoreMessaggiPbandisrv.traduci(m));
			
			esitoSalva.setRigheContoEconomicoAggiornato(contoEconomicoItemList);
			
			logger.info(prf + esitoSalva.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return esitoSalva;
	}
	
	@Override
	// Chiamato al click su Conferma.
	public EsitoSalvaRimodulazioneDTO salvaRimodulazioneIstruttoria(SalvaRimodulazioneRequest salvaRimodulazioneRequest, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::salvaRimodulazioneIstruttoria] ";
		logger.info(prf + " BEGIN");		
				
		if (salvaRimodulazioneRequest == null) {
			throw new InvalidParameterException("salvaRimodulazioneRequest non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		Long idProgetto = salvaRimodulazioneRequest.getIdProgetto();
		ArrayList<ContoEconomicoItem> contoEconomicoItemList = salvaRimodulazioneRequest.getContoEconomicoItemList();
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (contoEconomicoItemList == null) {
			throw new InvalidParameterException("contoEconomicoItemList non valorizzato.");
		}
		logger.info(prf + "idProgetto = "+idProgetto+"; idUtente = "+idUtente);
		
		EsitoSalvaRimodulazioneDTO esitoSalva = new EsitoSalvaRimodulazioneDTO();
		try {
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO;
			esitoFindContoEconomicoDTO = this.findContoEconomicoPerRimodulazioneIstruttoria(idProgetto, idUtente, idIride);

			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione = esitoFindContoEconomicoDTO.getContoEconomico();


			// contoEconomicoItemList ora arriva da Angular come parametro di input.
			//ArrayList<ContoEconomicoItem> contoEconomicoItemList = theModel.getAppDatacontoEconomico();

			Map<?, ContoEconomicoItem> mapContoEconomicoItem = beanUtil.index(contoEconomicoItemList, "idVoce");

			contoEconomicoIstruttoriaManager.mappaContoEconomicoPerSalvataggio(mapContoEconomicoItem, contoEconomicoRimodulazione);

			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoRimodulazioneDTO esito;
			esito = contoeconomicoBusinessImpl.rimodulaIstruttoria(idUtente, idIride, contoEconomicoRimodulazione);

			if (esito != null) {			

				//messageManager.setGlobalMessage(result,MessageKey.RIMODULAZIONE_SALVATA);
				
				esitoSalva.setEsito(true);
				esitoSalva.getMessaggi().add("I dati della nuova rimodulazione del conto economico sono stati salvati correttamente. E\u2019  possibile modificarli nuovamente oppure procedere con la conclusione dell\u2019 attivit\u00E0 di rimodulazione.");
				
				ContoEconomico contoEconomico = getDatiContoEconomicoIstruttoria(esitoFindContoEconomicoDTO);
				
				boolean isModifica = false;
				contoEconomicoItemList = contoEconomicoIstruttoriaManager
						.mappaContoEconomicoPerVisualizzazioneIstruttoria(
								contoEconomico,
								contoEconomicoRimodulazione,
								contoEconomicoItemList, isModifica, false);
				
				contoEconomico.setNascondiColonnaSpesaAmmessa(false);
				
				esitoSalva.setRigheContoEconomicoAggiornato(contoEconomicoItemList);
		
			} else {
				
				esitoSalva.setEsito(false);
				esitoSalva.getMessaggi().add("Si \u00E8 verificato un errore. Si prega di contattare il servizio assistenza.");
				
			}
			
			logger.info(prf + esitoSalva.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return esitoSalva;
	}
	
	@Override
	public InizializzaConcludiRimodulazioneDTO inizializzaConcludiRimodulazioneIstruttoria(Long idProgetto, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::inizializzaConcludiRimodulazioneIstruttoria] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + "idProgetto = "+idProgetto+"; idUtente = "+idUtente);
				
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
			
		InizializzaConcludiRimodulazioneDTO result = new InizializzaConcludiRimodulazioneDTO();
		try {

			// Codice visualizzato del progetto.
			String sql = "SELECT CODICE_VISUALIZZATO FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = ?";
			Object[] param = new Object[] {idProgetto};
			String codiceProgetto = getJdbcTemplate().queryForObject(sql, param, String.class);
			result.setCodiceVisualizzatoProgetto(codiceProgetto);
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerConclusioneRimodulazioneDTO datiDTO;
			datiDTO = contoeconomicoBusinessImpl.caricaDatiPerConclusioneIstruttoria(idUtente, idIride, idProgetto);

			if (datiDTO != null) {
				logger.info("IS_PERIODO_UNICO : " + datiDTO.getIsPeriodoUnico());
				//theModel.getSession().put("IS_PERIODO_UNICO",	Boolean.TRUE);
				
				// Jira PBANDI-2853
				//theModel.setAppDataimportoTotaleDisimpegni(contoEconomicoBusiness.findImportoTotaleDisimpegni(user, progetto.getId()));				
				contoeconomicoBusinessImpl.findImportoTotaleDisimpegni(idProgetto);
				
				logger.info("importoSpesaAmmessaRimodulazione ---------->  " + datiDTO.getImportoSpesaAmmessa() + " , importoSpesaAmmessaRimodulazioneUltima ---------->  " + datiDTO.getImportoSpesaAmmessaUltima() + " , isPeriodoUnico ---------->  " + datiDTO.getIsPeriodoUnico());

				if (!StringUtils.isBlank(datiDTO.getDataConcessione()))
					result.setDataConcessione(DateUtil.getDate(datiDTO.getDataConcessione()));

				result.setTotaleSpesaAmmessaInRimodulazione(datiDTO.getImportoSpesaAmmessa());

				result.setListaModalitaAgevolazione(this.loadModalitaAgevolazione(datiDTO));

				this.loadFontiFinanziarie(datiDTO, result);
			}
	
			logger.info(prf + result.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return result;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class})
	public EsitoSalvaRimodulazioneConfermataDTO salvaRimodulazioneIstruttoriaConfermata(SalvaRimodulazioneConfermataRequest salvaRimodulazioneConfermataRequest, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::salvaRimodulazioneIstruttoriaConfermata] ";
		logger.info(prf + " BEGIN");		
				
		if (salvaRimodulazioneConfermataRequest == null) {
			throw new InvalidParameterException("salvaRimodulazioneConfermataRequest non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		logger.info(prf + salvaRimodulazioneConfermataRequest.toString());
		
		Long idProgetto = salvaRimodulazioneConfermataRequest.getIdProgetto();
		Long idContoEconomico = salvaRimodulazioneConfermataRequest.getIdContoEconomico();
		Long idSoggetto = salvaRimodulazioneConfermataRequest.getIdSoggettoBeneficiario();
		Date dataConcessione = salvaRimodulazioneConfermataRequest.getDataConcessione();
		String note = salvaRimodulazioneConfermataRequest.getNote();
		String riferimento = salvaRimodulazioneConfermataRequest.getRiferimento();		
		List<ModalitaAgevolazione> listModalita = salvaRimodulazioneConfermataRequest.getListaModalitaAgevolazione();
		List<FinanziamentoFonteFinanziaria> listFonti = salvaRimodulazioneConfermataRequest.getListaFonti();
		String contrattiDaStipulare = null;
		Double importoFinanziamentoBanca = null;
		
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idContoEconomico == null) {
			throw new InvalidParameterException("idContoEconomico non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (dataConcessione == null) {
			throw new InvalidParameterException("dataConcessione non valorizzato.");
		}
		if (riferimento == null) {
			throw new InvalidParameterException("riferimento non valorizzato.");
		}
		if (listModalita == null) {
			throw new InvalidParameterException("listModalita non valorizzato.");
		}
		if (listFonti == null) {
			throw new InvalidParameterException("listFonti non valorizzato.");
		}

		EsitoSalvaRimodulazioneConfermataDTO esitoSalva = new EsitoSalvaRimodulazioneConfermataDTO();
		try {
					
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO progettoDTO = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO();
			progettoDTO.setIdProgetto(idProgetto);

			// Alex: metto valori a caso poichè il metodo di pbandisrv li vuole obbligatori ma non li usa.
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.IstanzaAttivitaDTO istanza = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.IstanzaAttivitaDTO();		
			istanza.setId("TaskIdentity");					//istanza.setId(istanzaAttivita.getTaskIdentity());
			istanza.setCodUtente("CodUtenteFlux");			//istanza.setCodUtente(userInfoHelper.getCodUtenteFlux(currentUser));

			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerConclusioneRimodulazioneDTO datiDTO = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerConclusioneRimodulazioneDTO();
			datiDTO.setProgetto(progettoDTO);
						
			// Modalita
			
			logger.info(prf+"Modalita");
			HashMap<String, String> trsMap = new HashMap<String, String>() {
				{
					put("descModalita", "descrizione");
					put("idModalitaAgevolazione", "idModalitaDiAgevolazione");
					put("importoAgevolato", "importoAgevolatoNuovo");
					put("ultimoImportoAgevolato", "importoAgevolatoUltimo");
					put("importoErogato", "importoErogato");
					put("importoMassimoAgevolato", "importoMassimoAgevolato");
					put("importoRichiesto", "importoRichiestoNuovo");
					put("ultimoImportoRichiesto", "importoRichiestoUltimo");
					put("percentualeImportoAgevolato", "percImportoAgevolatoNuovo");
					put("percentualeUltimoImportoAgevolato", "percImportoAgevolatoUltimo");
					put("percentualeMassimoAgevolato", "percImportoMassimoAgevolato");
					put("percentualeImportoRichiesto", "percImportoRichiestoNuovo");
				}
			};
									
			ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO> listModalitaDTO = new ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO>();
			for (int i = 0; i < listModalita.size() - 1; ++i) {
				ModalitaAgevolazione modalita = listModalita.get(i);
				listModalitaDTO.add(beanUtil.transform(modalita, it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO.class, trsMap));
			}
			datiDTO.setModalitaDiAgevolazione(listModalitaDTO.toArray(new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO[] {}));

			// Fonti finanziarie
			
			logger.info(prf+"Fonti finanziarie");
			List<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO> fontiPrivate = new ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO>();
			List<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO> fontiNonPrivate = new ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO>();
			trsMap = new HashMap<String, String>() {
				{
					put("descFonteFinanziaria", "descrizione");
					put("estremiProvvedimento", "estremiProvv");
					put("flagEconomie", "flagEconomie");
					put("flagLvlprj", "flagLvlprj");
					put("idComune", "idComune");
					put("idDelibera", "idDelibera");
					put("idFonteFinanziaria", "idFonteFinanziaria");
					put("idNorma", "idNorma");
					put("idPeriodo", "idPeriodo");
					put("idProvincia", "idProvincia");
					put("idSoggettoPrivato", "idSoggetto");
					put("importoQuota", "quotaFonteFinanziariaNuova");
					put("importoUltimaQuota", "quotaFonteFinanziariaUltima");
					put("note", "note");
					put("percentualeQuota", "percQuotaFonteFinanziariaNuova");
					put("percentualeUltimaQuota", "percQuotaFonteFinanziariaUltima");
					put("progrProgSoggFinanziat", "progrProgSoggFinanziat");
				}
			};
			
			for (int i = 0; i < listFonti.size() - 1; ++i) {
				FonteFinanziaria fonte = listFonti.get(i);
				logger.info("\n:fonte.getIsSubTotale() "+fonte.getIsSubTotale());
				if (!fonte.getIsSubTotale()) {
					logger.info("fonte.getIsFontePrivata() "+fonte.getIsFontePrivata());					
					it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO dto = beanUtil.transform(fonte,it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO.class, trsMap);
					if(fonte.getPercentualeQuota()!=null && fonte.getPercentualeQuota().doubleValue()>0){
					    dto.setFlagLvlprj("S");
					}
					if (fonte.getIsFontePrivata()) {
						fontiPrivate.add(dto);
					} else {
						fontiNonPrivate.add(dto);
					}
				}
			}

			datiDTO.setFontiFinanziarieNonPrivate(fontiNonPrivate.toArray(new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO[] {}));
			datiDTO.setFontiFinanziariePrivate(fontiPrivate.toArray(new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO[] {}));

			if (dataConcessione != null)				
				datiDTO.setDataConcessione(DateUtil.getDate(dataConcessione));
			datiDTO.setNote(note);
			datiDTO.setContrattiDaStipulare(contrattiDaStipulare);
			datiDTO.setRiferimento(riferimento);
			
			datiDTO.setIdSoggetto(idSoggetto);
			datiDTO.setIdContoEconomico(idContoEconomico);
			datiDTO.setImportoFinanziamentoBancario(importoFinanziamentoBanca);

			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoConclusioneRimodulazioneDTO esito;
			esito = contoeconomicoBusinessImpl.concludiRimodulazioneIstruttoria(idUtente, idIride, istanza, datiDTO);

			// Alex: non ho capito a cosa serve.
			//theModel.getSession().put(Constants.ID_CONTO_ECONOMICO_LOCAL,esito.getIdContoEconomicoLocal());

			//MessageManager.setGlobalMessages(result,MessageKey.KEY_MSG_RIMODULAZIONE_CONCLUSA);
			
			esitoSalva.setEsito(true);
			esitoSalva.getMessaggi().add("La rimodulazione del conto economico \u00E8 stata conclusa correttamente.");
			
			logger.info(prf + esitoSalva.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return esitoSalva;
	}
	
	@Override
	public InizializzaPropostaRimodulazioneDTO inizializzaPropostaRimodulazioneInDomanda(Long idProgetto, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::inizializzaPropostaRimodulazioneInDomanda] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + "idProgetto = "+idProgetto+"; idUtente = "+idUtente);
				
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		InizializzaPropostaRimodulazioneDTO result = new InizializzaPropostaRimodulazioneDTO();
		try {
			
			// Codice visualizzato del progetto.
			String sql = "SELECT CODICE_VISUALIZZATO FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = ?";
			Object[] param = new Object[] {idProgetto};
			String codiceProgetto = getJdbcTemplate().queryForObject(sql, param, String.class);
			result.setCodiceVisualizzatoProgetto(codiceProgetto);			
			
			// CONTO ECONOMICO LETTO DA DB.
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO  esito;
			esito = this.findContoEconomicoPerPropostaRimodulazioneInDomanda(idProgetto,idUtente, idIride);
			result.setEsitoFindContoEconomicoDTO(esito);
			
			if (esito != null && esito.getContoEconomico() != null) {
				
				it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione;
				contoEconomicoRimodulazione = esito.getContoEconomico();

				boolean setZeroToNullVal = false;
				ArrayList<ContoEconomicoItem> contoEconomicoItemList = new ArrayList<ContoEconomicoItem>();
				contoEconomicoItemList = contoEconomicoInDomandaManager.mappaContoEconomicoPerVisualizzazioneInDomanda(
								contoEconomicoRimodulazione, contoEconomicoItemList, setZeroToNullVal);
				result.setRigheContoEconomico(contoEconomicoItemList);
				
				//if (esitoFindContoEconomicoDTO.getIsContoMainNew()) {
				//	result.setResultCode(CARICACONTOECONOMICO_OUTCOME_CODE__NUOVA_PROPOSTA);
				//} else if (esitoFindContoEconomicoDTO.getInStatoRichiesto()) {
				//	// posso solo tornare alle attivita
				//	result.setResultCode(CARICACONTOECONOMICO_OUTCOME_CODE__PROPOSTA_CONCLUSA);					
				//} else {
				//	// posso modificare o inviare richiesta
				//	result.setResultCode(CARICACONTOECONOMICO_OUTCOME_CODE__PROPOSTA_ESISTENTE);
				//}				
				
			} else {
				// devo inserire degli importi, per adesso è tutto a zero
				//result.setResultCode(CARICACONTOECONOMICO_OUTCOME_CODE__NUOVA_PROPOSTA);
			}
			
			logger.info(prf + result.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return result;
	}
	
	private it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO 
		findContoEconomicoPerPropostaRimodulazioneInDomanda(Long idProgetto, Long idUtente, String idIride) 
			throws ContoEconomicoException, SystemException, UnrecoverableException, CSIException {

		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO progetto = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO();
		progetto.setIdProgetto(idProgetto);

		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esito;
		//esito = contoeconomicoBusinessImpl.findContoEconomicoPerPropostaRimodulazione(idUtente,	idIride, progetto);
		esito = contoeconomicoBusinessImpl.findContoEconomicoInDomanda(idUtente,	idIride, progetto);

		return esito;		
	}
	
	@Override
	public EsitoSalvaPropostaRimodulazioneDTO validaPropostaRimodulazioneInDomanda(SalvaPropostaRimodulazioneRequest salvaPropostaRimodulazioneRequest, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::validaPropostaRimodulazioneInDomanda] ";
		logger.info(prf + " BEGIN");		
				
		if (salvaPropostaRimodulazioneRequest == null) {
			throw new InvalidParameterException("salvaPropostaRimodulazioneRequest non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		Long idProgetto = salvaPropostaRimodulazioneRequest.getIdProgetto();
		ArrayList<ContoEconomicoItem> contoEconomicoItemList = salvaPropostaRimodulazioneRequest.getContoEconomicoItemList();
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (contoEconomicoItemList == null) {
			throw new InvalidParameterException("contoEconomicoItemList non valorizzato.");
		}
		logger.info(prf + "idProgetto = "+idProgetto+"; contoEconomicoItemList.size = "+contoEconomicoItemList.size()+"; idUtente = "+idUtente);
		
		EsitoSalvaPropostaRimodulazioneDTO esitoSalva = new EsitoSalvaPropostaRimodulazioneDTO();
		try {
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO;
			esitoFindContoEconomicoDTO = this.findContoEconomicoPerPropostaRimodulazioneInDomanda(idProgetto,idUtente, idIride);
			
			// NOTA: 
			//  - contoEconomicoItemList: conto economico modificato a video e da salvare.
			//  - esitoFindContoEconomicoDTO: conto economico letto da db.
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione;
			contoEconomicoRimodulazione = esitoFindContoEconomicoDTO.getContoEconomico();
			
			boolean setZeroToNullVal = false;
			contoEconomicoItemList = contoEconomicoInDomandaManager
					.mappaContoEconomicoPerVisualizzazioneInDomanda(
							contoEconomicoRimodulazione, contoEconomicoItemList, setZeroToNullVal);
			
			Set<String> errors = new HashSet<String>();
			Map<?, ContoEconomicoItem> mapContoEconomicoItem = beanUtil.index(contoEconomicoItemList, "idVoce");

			errors = contoEconomicoInDomandaManager.controllaImportiPerRichiestoInDomanda(
							mapContoEconomicoItem, contoEconomicoRimodulazione,	errors);

			Collection<String> messaggi = new ArrayList<String>(errors);
			if (errors.contains(MessageKey.IMPORTI_NON_VALIDI)) {				
				contoEconomicoInDomandaManager.removeCodiceWarning(contoEconomicoItemList);
				esitoSalva.setEsito(false);
			} else {
				messaggi.add((MessageKey.CONFERMA_SALVATAGGIO));
				esitoSalva.setEsito(true);
			}
			
			esitoSalva.setRigheContoEconomicoAggiornato(contoEconomicoItemList);
			
			for (String msg : messaggi) {
				esitoSalva.getMessaggi().add(TraduttoreMessaggiPbandisrv.traduci(msg));
			}
				
			logger.info(prf + esitoSalva.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return esitoSalva;
	}
	
	@Override
	public EsitoSalvaPropostaRimodulazioneDTO salvaPropostaRimodulazioneInDomanda(SalvaPropostaRimodulazioneRequest salvaPropostaRimodulazioneRequest, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::salvaPropostaRimodulazioneInDomanda] ";
		logger.info(prf + " BEGIN");		
				
		if (salvaPropostaRimodulazioneRequest == null) {
			throw new InvalidParameterException("salvaPropostaRimodulazioneRequest non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		Long idProgetto = salvaPropostaRimodulazioneRequest.getIdProgetto();
		ArrayList<ContoEconomicoItem> contoEconomicoItemList = salvaPropostaRimodulazioneRequest.getContoEconomicoItemList();
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (contoEconomicoItemList == null) {
			throw new InvalidParameterException("contoEconomicoItemList non valorizzato.");
		}
		logger.info(prf + "idProgetto = "+idProgetto+"; contoEconomicoItemList.size = "+contoEconomicoItemList.size()+"; idUtente = "+idUtente);
		
		EsitoSalvaPropostaRimodulazioneDTO esitoSalva = new EsitoSalvaPropostaRimodulazioneDTO();
		try {
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO;
			esitoFindContoEconomicoDTO = this.findContoEconomicoPerPropostaRimodulazioneInDomanda(idProgetto,idUtente, idIride);
			
			// NOTA: 
			//  - contoEconomicoItemList: conto economico modificato a video e da salvare.
			//  - esitoFindContoEconomicoDTO: conto economico letto da db.
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione;
			contoEconomicoRimodulazione = esitoFindContoEconomicoDTO.getContoEconomico();
			
			Map<?, ContoEconomicoItem> mapContoEconomicoItem = beanUtil.index(contoEconomicoItemList, "idVoce");

			contoEconomicoManager.mappaContoEconomicoPerSalvataggio(mapContoEconomicoItem, contoEconomicoRimodulazione);

			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoSalvaContoEconomicoDTO esito;
			esito = contoeconomicoBusinessImpl.salvaContoEconomicoRichiestoInDomanda(
					idUtente, idIride, contoEconomicoRimodulazione, idProgetto);

			if (esito != null) {

				contoEconomicoRimodulazione = esito.getContoEconomico();

				contoEconomicoItemList = new ArrayList<ContoEconomicoItem>();
				boolean setZeroToNullVal = false;
				contoEconomicoItemList = contoEconomicoInDomandaManager
						.mappaContoEconomicoPerVisualizzazioneInDomanda(
								contoEconomicoRimodulazione, contoEconomicoItemList, setZeroToNullVal);

				esitoSalva.setEsito(true);
				esitoSalva.getMessaggi().add(RICHIESTO_IN_DOMANDA_SALVATO);
				esitoSalva.setRigheContoEconomicoAggiornato(contoEconomicoItemList);

			} else {
				esitoSalva.setEsito(false);
				esitoSalva.getMessaggi().add(ERRORE_GENERICO);
			}
				
			logger.info(prf + esitoSalva.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return esitoSalva;
	}
	
	// Finestra che 'conclude' la Rimodulazione in Domanda'.
	@Override
	public InizializzaConcludiRichiestaContoEconomicoDTO inizializzaConcludiRichiestaContoEconomico(Long idProgetto, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::inizializzaConcludiRichiestaContoEconomico] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + "idProgetto = "+idProgetto+"; idUtente = "+idUtente);
				
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
			
		InizializzaConcludiRichiestaContoEconomicoDTO result = new InizializzaConcludiRichiestaContoEconomicoDTO();
		try {
			
			// Codice visualizzato del progetto.
			String sql = "SELECT CODICE_VISUALIZZATO FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = ?";
			Object[] param = new Object[] {idProgetto};
			String codiceProgetto = getJdbcTemplate().queryForObject(sql, param, String.class);
			result.setCodiceVisualizzatoProgetto(codiceProgetto);
			
			// Gli allegati alla proposta sono ammessi solo se e' abilitata la regola BR42.
			//Boolean allegatiAmmessi = profilazioneBusinessImpl.isRegolaApplicabileForProgetto(idUtente, idIride, idProgetto, RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);			
			//result.setAllegatiAmmessi(allegatiAmmessi);
			
			// Combo Rappresentante legale.
			ArrayList<CodiceDescrizione> rappr = this.findRappresentantiLegali(idUtente, idIride, idProgetto);
			result.setRappresentantiLegali(rappr);
			
			// Combo Delegato.
			//ArrayList<CodiceDescrizione> delegati = this.findDelegati(idUtente, idIride, idProgetto);
			//result.setDelegati(delegati);
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO progetto = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO();
			progetto.setIdProgetto(idProgetto);
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerInvioPropostaRimodulazioneDTO datiDTO;
			datiDTO = contoeconomicoBusinessImpl.caricaDatiPerRichiestaContoEconomico(idUtente, idIride, progetto);
			
			result.setIdContoEconomico(datiDTO.getIdContoEconomico());
			result.setImportModificabili(datiDTO.getIsImportiModificabili());
			result.setImportoFinanziamentoRichiesto(datiDTO.getImportoFinanziamentoRichiesto());
			result.setTotaleRichiestoInDomanda(datiDTO.getImportoSpesaRichiesto());
			
			result.setListaModalitaAgevolazione(this.popolaModalitaConcludiRichiestaContoEconomico(datiDTO));
			
			logger.info(prf + result.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return result;
	}
	
	private ArrayList<ModalitaAgevolazione> popolaModalitaConcludiRichiestaContoEconomico(it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerInvioPropostaRimodulazioneDTO datiDTO) {
		
		ArrayList<ModalitaAgevolazione> listaModalitaAgevolazione = new ArrayList<ModalitaAgevolazione>();
		
		if (datiDTO.getModalitaDiAgevolazione() != null && datiDTO.getModalitaDiAgevolazione().length > 0)
			for (it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO dto : datiDTO.getModalitaDiAgevolazione()) {
				ModalitaAgevolazione modalita = new ModalitaAgevolazione();
				modalita.setIdModalitaAgevolazione(dto.getIdModalitaDiAgevolazione());
				modalita.setDescModalita(dto.getDescrizione());
				modalita.setImportoMassimoAgevolato(dto.getImportoMassimoAgevolato());
				modalita.setPercentualeMassimoAgevolato(dto.getPercImportoMassimoAgevolato());
				modalita.setUltimoImportoRichiesto(dto.getImportoRichiestoUltimo());
				modalita.setImportoRichiesto(NumberUtil.getDoubleValue(dto.getImportoRichiestoNuovo()));
				if (!ObjectUtil.isNull(dto.getImportoAgevolatoUltimo()) && dto.getImportoAgevolatoUltimo() > 0)
					modalita.setImportoRichiesto(dto.getImportoAgevolatoUltimo());
				modalita.setPercentualeImportoRichiesto(NumberUtil.getDoubleValue(dto.getPercImportoRichiestoNuovo()));
				modalita.setImportoErogato(dto.getImportoErogato());
				modalita.setUltimoImportoAgevolato(dto.getImportoAgevolatoUltimo());
				modalita.setPercentualeUltimoImportoAgevolato(dto.getPercImportoAgevolatoUltimo());
				modalita.setModificabile(true);
				listaModalitaAgevolazione.add(modalita);
			}
		
		it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO totaleModalitaDTO = datiDTO.getTotaliModalitaDiAgevolazione(); 
		if (totaleModalitaDTO != null) {
			ModalitaAgevolazione totaliModalita = new ModalitaAgevolazione();
			totaliModalita.setIsModalitaTotale(true);
			totaliModalita.setDescModalita(Constants.MSG_PROPOSTA_RIMODULAZIONE_TOTALE);
			totaliModalita.setImportoMassimoAgevolato(totaleModalitaDTO.getImportoMassimoAgevolato());
			totaliModalita.setPercentualeMassimoAgevolato(totaleModalitaDTO.getPercImportoMassimoAgevolato());
			totaliModalita.setUltimoImportoRichiesto(totaleModalitaDTO.getImportoRichiestoUltimo());
			totaliModalita.setImportoRichiesto(totaleModalitaDTO.getImportoRichiestoNuovo());
			totaliModalita.setPercentualeImportoRichiesto(totaleModalitaDTO.getPercImportoRichiestoNuovo());
			totaliModalita.setImportoErogato(totaleModalitaDTO.getImportoErogato());
			totaliModalita.setUltimoImportoAgevolato(totaleModalitaDTO.getImportoAgevolatoUltimo());
			totaliModalita.setPercentualeUltimoImportoAgevolato(totaleModalitaDTO.getPercImportoAgevolatoUltimo());
			totaliModalita.setModificabile(false);
			listaModalitaAgevolazione.add(listaModalitaAgevolazione.size(), totaliModalita);
		}
		
		return listaModalitaAgevolazione;
	}
	
	@Override
	public EsitoSalvaPropostaRimodulazioneDTO salvaRichiestaContoEconomico(SalvaRichiestaContoEconomicoRequest salvaRichiestaContoEconomicoRequest, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::salvaRichiestaContoEconomico] ";
		logger.info(prf + " BEGIN");		
				
		if (salvaRichiestaContoEconomicoRequest == null) {
			throw new InvalidParameterException("salvaRichiestaContoEconomicoRequest non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		Long idProgetto = salvaRichiestaContoEconomicoRequest.getIdProgetto(); 		
		Long idContoEconomico = salvaRichiestaContoEconomicoRequest.getIdContoEconomico();
		Long idSoggettoBeneficiario = salvaRichiestaContoEconomicoRequest.getIdSoggettoBeneficiario();
		ArrayList<ModalitaAgevolazione> listModalita = salvaRichiestaContoEconomicoRequest.getListaModalitaAgevolazione();
		String note = salvaRichiestaContoEconomicoRequest.getNote();
		Long idRapprensentanteLegale = salvaRichiestaContoEconomicoRequest.getIdRapprensentanteLegale();
		Double importoFinanziamentoRichiesto = salvaRichiestaContoEconomicoRequest.getImportoFinanziamentoRichiesto();
		
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (listModalita == null) {
			throw new InvalidParameterException("listaModalitaAgevolazione non valorizzato.");
		}
		if (idSoggettoBeneficiario == null) {
			throw new InvalidParameterException("idSoggettoBeneficiario non valorizzato.");
		}
		logger.info(prf + "idProgetto = "+idProgetto+"; idUtente = "+idUtente);
		
		EsitoSalvaPropostaRimodulazioneDTO esitoSalva = new EsitoSalvaPropostaRimodulazioneDTO();
		
		try {
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO progettoDTO = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO();
			progettoDTO.setIdProgetto(idProgetto);

			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerInvioPropostaRimodulazioneDTO datiDTO = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.DatiPerInvioPropostaRimodulazioneDTO();
			List<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO> listModalitaDTO = new ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO>();
			for (int i = 0; i < listModalita.size() - 1; ++i) {
				ModalitaAgevolazione modalita = listModalita.get(i);
				it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO dto = new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO();
				dto.setDescrizione(modalita.getDescModalita());
				dto.setIdModalitaDiAgevolazione(modalita.getIdModalitaAgevolazione());
				dto.setImportoAgevolatoNuovo(modalita.getImportoAgevolato());
				dto.setImportoAgevolatoUltimo(modalita.getUltimoImportoAgevolato());
				dto.setImportoErogato(modalita.getImportoErogato());
				dto.setImportoMassimoAgevolato(modalita.getImportoMassimoAgevolato());
				dto.setImportoRichiestoNuovo(modalita.getImportoRichiesto());
				dto.setImportoRichiestoUltimo(modalita.getUltimoImportoRichiesto());
				dto.setPercImportoAgevolatoNuovo(modalita.getPercentualeImportoAgevolato());
				dto.setPercImportoAgevolatoUltimo(modalita.getPercentualeUltimoImportoAgevolato());
				dto.setPercImportoMassimoAgevolato(modalita.getPercentualeMassimoAgevolato());
				dto.setPercImportoRichiestoNuovo(modalita.getPercentualeImportoRichiesto());
				listModalitaDTO.add(dto);
			}
			
			datiDTO.setModalitaDiAgevolazione(listModalitaDTO.toArray(new it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO[] {}));
			datiDTO.setProgetto(progettoDTO);
			datiDTO.setNoteContoEconomico(note);
			datiDTO.setIdSoggetto(idSoggettoBeneficiario);
			datiDTO.setIdSoggettoRappresentante(idRapprensentanteLegale);
			datiDTO.setIdContoEconomico(idContoEconomico);
			datiDTO.setImportoFinanziamentoRichiesto(importoFinanziamentoRichiesto);

			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoRichiestaContoEconomicoDTO esito;
			esito = contoeconomicoBusinessImpl.inviaRichiestaContoEconomico(idUtente, idIride, datiDTO);
			
			esitoSalva.setEsito(true);
			esitoSalva.getMessaggi().add(RICHIESTA_CONTOECONOMICO_INVIATA);
					
			logger.info(prf + esitoSalva.toString());

		} catch (Exception e) {
			logger.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return esitoSalva;
	}

	@Override
	public List<AltriCostiDTO> getAltriCosti(Long idBandoLinea, Long idProgetto, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoDAOImpl::getAltriCosti] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + "idProgetto = " + idProgetto + "; idBandoLinea = " + idBandoLinea + "; idUtente = "+idUtente);
				
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idBandoLinea == null) {
			throw new InvalidParameterException("idBandoLinea non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (idIride == null || idIride.isEmpty()) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
			
		List<AltriCostiDTO> result = new ArrayList<>();
		try {
			String sql = "SELECT pdcac.ID_D_CE_ALTRI_COSTI, pdcac.DESC_BREVE_CE_ALTRI_COSTI, pdcac.DESC_CE_ALTRI_COSTI, ptcac.ID_T_CE_ALTRI_COSTI, ptcac.IMP_CE_APPROVATO , ptcac.IMP_CD_PROPMOD \r\n"
					+ "FROM PBANDI_D_CE_ALTRI_COSTI pdcac\r\n"
					+ "JOIN PBANDI_R_BANDO_CE_ALTRI_COSTI prbcac ON prbcac.ID_D_CE_ALTRI_COSTI = pdcac.ID_D_CE_ALTRI_COSTI\r\n"
					+ "JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.ID_BANDO = prbcac.ID_BANDO\r\n"
					+ "LEFT OUTER JOIN PBANDI_T_CE_ALTRI_COSTI ptcac ON ptcac.ID_D_CE_ALTRI_COSTI = pdcac.ID_D_CE_ALTRI_COSTI AND ptcac.ID_PROGETTO = ?\r\n"
					+ "WHERE prbli.PROGR_BANDO_LINEA_INTERVENTO = ? AND pdcac.DT_INZIO_VALIDITA <= SYSDATE \r\n"
					+ "AND (pdcac.DT_FINE_VALIDITA IS NULL OR pdcac.DT_FINE_VALIDITA <= SYSDATE)";
			LOG.info(sql);
			Object[] params = new Object[] {idProgetto, idBandoLinea};
			result = getJdbcTemplate().query(sql, params, new AltriCostiRowMapper());
		
			LOG.info(prf + " trovati " + (result != null ? result.size() : 0) + " record");

		} catch (Exception e) {
			LOG.error(prf, e);
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return result;
	}
	
}
