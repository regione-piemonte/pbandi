/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.RegolaManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.EsitoFindQuadroPrevisionaleDTO;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.EsitoSalvaQuadroPrevisionaleDTO;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.ProgettoDTO;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.QuadroPrevisionaleComplessivoDTO;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.QuadroPrevisionaleDTO;
import it.csi.pbandi.pbwebrce.integration.dao.QuadroPrevisionaleDAO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.ContoEconomicoMaxDataFineVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiRProgettoFaseMonitVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTDomandaVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTQuadroPrevisionaleVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTRigoQuadroPrevisioVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.VoceDiSpesaConAmmessoVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.VoceDiSpesaConRealizzatoVO;
import it.csi.pbandi.pbwebrce.util.BeanUtil;
import it.csi.pbandi.pbwebrce.util.Constants;
import it.csi.pbandi.pbwebrce.util.DateUtil;
import it.csi.pbandi.pbwebrce.util.NumberUtil;

@Component
public class QuadroPrevisionaleDAOImpl extends JdbcDaoSupport implements QuadroPrevisionaleDAO{

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	protected BeanUtil beanUtil;
	
	@Autowired
	private NeofluxBusinessImpl neofluxBusiness;
	
	@Autowired
	private GenericDAO genericDAO;

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	@Autowired
	public QuadroPrevisionaleDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
		
		this.genericDAO = new GenericDAO(dataSource);
	}
	
	@Autowired
	private RegolaManager regolaManager;
	
	@Override
	@Transactional
	public EsitoFindQuadroPrevisionaleDTO findQuadroPrevisionale(Long idUtente, String idIride, Long idProgetto) throws Exception {
		String prf = "[QuadroPrevisionaleDAOImpl::findQuadroPrevisionale]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "identitaDigitale", "progetto", "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto);
		EsitoFindQuadroPrevisionaleDTO esito = new EsitoFindQuadroPrevisionaleDTO();
		try {
			// periodo iniziale=data presentazione domanda
			// periodo finale = il default ï¿½ data presentazione domanda + 2 (es
			// 2008 - 2010)
			// periodo finale = data effettiva chiusura progetto indicata nelle
			// fasi monitoraggio
			Set<String> periodi = calcolaPeriodi(idProgetto);
			VoceDiSpesaConAmmessoVO[] vociDiSpesaConAmmessoDB = getVociDiSpesaConAmmesso(idProgetto);
			LOG.info(prf + " trovati " + vociDiSpesaConAmmessoDB.length + " voci con ammesso");
	
			Date dtAmmessaUltima = getDataAmmessaUltima(idProgetto);
			esito.setDataUltimaSpesaAmmessa(dtAmmessaUltima);
	
			QuadroPrevisionaleDTO quadroPrevisionaleDTO = getQuadroPrevisionale(idProgetto, esito);
			List<QuadroPrevisionaleDTO> vociDiSpesaPerPeriodo = clonaVociDiSpesaConAmmessoPerPeriodo(periodi, vociDiSpesaConAmmessoDB);	
			VoceDiSpesaConRealizzatoVO[] vociDiSpesaConRealizzato = getVociDiSpesaConRealizzato(idProgetto);
	
			if (vociDiSpesaConRealizzato.length > 0) {
				mappaVociDispesaConRealizzato(vociDiSpesaPerPeriodo, vociDiSpesaConRealizzato);
			}
			List<QuadroPrevisionaleDTO> righiQuadroPrevisionaleDB = null;
			righiQuadroPrevisionaleDB = getRighiQuadroPrevisionale(idProgetto);
			LOG.info(prf + " trovati " + righiQuadroPrevisionaleDB.size() + " righi quadro");
	
			if (righiQuadroPrevisionaleDB.size() > 0) {
				mappaRighiQuadroPrevisionale(vociDiSpesaPerPeriodo, righiQuadroPrevisionaleDB);
			}
	
			quadroPrevisionaleDTO.setFigli(vociDiSpesaPerPeriodo.toArray(new QuadroPrevisionaleDTO[vociDiSpesaPerPeriodo.size()]));	
			setImportiPerMacroVoce(quadroPrevisionaleDTO);	
			aggiungiFigliPeriodi(quadroPrevisionaleDTO, periodi);	
			setImportoDaRealizzare(quadroPrevisionaleDTO);
	
			List<QuadroPrevisionaleComplessivoDTO> quadroPrevisionaleComplessivoDTO = mappaQuadroComplessivo(quadroPrevisionaleDTO, vociDiSpesaConAmmessoDB);	
			esito.setQuadroPrevisionale(quadroPrevisionaleDTO);	
			esito.setQuadroPrevisionaleComplessivo(quadroPrevisionaleComplessivoDTO.toArray(new QuadroPrevisionaleComplessivoDTO[0]));	
			esito.setVociVisibili(isVociVisibili(idProgetto));	
			esito.setControlloNuovoImportoBloccante(isControlloNuovoImportoBloccante(idProgetto));
	
			LOG.info(prf + " END");
			return esito;
	
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
		
	}
	
	private Set<String> calcolaPeriodi(Long idProgetto) throws Exception {
		String prf = "[QuadroPrevisionaleDAOImpl::calcolaPeriodi]";
		LOG.info(prf + " START");
		PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO(new BigDecimal(idProgetto));
		pbandiTProgettoVO = genericDAO.findSingleWhere(pbandiTProgettoVO);
		PbandiTDomandaVO pbandiTDomandaVO = new PbandiTDomandaVO();
		pbandiTDomandaVO.setIdDomanda(pbandiTProgettoVO.getIdDomanda());
		pbandiTDomandaVO = genericDAO.findSingleWhere(pbandiTDomandaVO);
		Date dataInizio = pbandiTDomandaVO.getDtPresentazioneDomanda();
		int annoInizio = DateUtil.getYear(dataInizio);
		int annoFine = annoInizio + 2;// default
		int annoChiusuraProgetto = getDataChiusuraProgettoFasiMonitoraggio(idProgetto);
		if (annoChiusuraProgetto > annoFine)
			annoFine = annoChiusuraProgetto;
		Set<String> periodi = new LinkedHashSet<String>();
		int currentYear = DateUtil.getAnno();
		if (annoFine < currentYear)
			annoFine = currentYear;
		while (annoInizio <= annoFine) {
			periodi.add(Integer.toString(annoInizio));
			annoInizio++;
		}
		LOG.info(prf + " END");
		return periodi;
	}
	
	private int getDataChiusuraProgettoFasiMonitoraggio(Long idProgetto) throws Exception {
		String prf = "[QuadroPrevisionaleDAOImpl::getDataChiusuraProgettoFasiMonitoraggio]";
		LOG.info(prf + " START");
		int annoChiusura = 0;
		PbandiRProgettoFaseMonitVO pbandiRProgettoFaseMonitVO = new PbandiRProgettoFaseMonitVO();
		pbandiRProgettoFaseMonitVO.setIdProgetto(new BigDecimal(idProgetto));
		List<PbandiRProgettoFaseMonitVO> listFasiMonitoraggio = genericDAO.findListWhere(pbandiRProgettoFaseMonitVO);
		Date dataFineEffettiva = null, dataFinePrevista = null;
		for (PbandiRProgettoFaseMonitVO faseMonitoraggio : listFasiMonitoraggio) {
			if (dataFineEffettiva == null) {
				dataFineEffettiva = faseMonitoraggio.getDtFineEffettiva();
			} else if (faseMonitoraggio.getDtFineEffettiva() != null && faseMonitoraggio.getDtFineEffettiva().after(dataFineEffettiva)) {
				dataFineEffettiva = faseMonitoraggio.getDtFineEffettiva();
			}
			if (dataFinePrevista == null) {
				dataFinePrevista = faseMonitoraggio.getDtFinePrevista();
			} else if (faseMonitoraggio.getDtFinePrevista() != null && faseMonitoraggio.getDtFinePrevista().after(dataFinePrevista)) {
				dataFinePrevista = faseMonitoraggio.getDtFinePrevista();
			}

		}
		if (dataFineEffettiva != null) {
			annoChiusura = DateUtil.getYear(dataFineEffettiva);
		} else if (dataFinePrevista != null) {
			annoChiusura = DateUtil.getYear(dataFinePrevista);
		}
		LOG.info(prf + " END");
		return annoChiusura;
	}
	
	private VoceDiSpesaConAmmessoVO[] getVociDiSpesaConAmmesso(Long idProgetto) {
		String prf = "[QuadroPrevisionaleDAOImpl::getVociDiSpesaConAmmesso]";
		LOG.info(prf + " START");
		VoceDiSpesaConAmmessoVO voceDiSpesaConAmmesso = new VoceDiSpesaConAmmessoVO();
		voceDiSpesaConAmmesso.setIdProgetto(new BigDecimal(idProgetto));
		VoceDiSpesaConAmmessoVO[] vociDiSpesaConAmmesso = genericDAO.findWhere(voceDiSpesaConAmmesso);
		LOG.info(prf + " END");
		return vociDiSpesaConAmmesso;
	}
	
	private Date getDataAmmessaUltima(Long idProgetto) throws Exception {
		String prf = "[QuadroPrevisionaleDAOImpl::getDataAmmessaUltima]";
		LOG.info(prf + " START");
		ProgettoDTO progetto = new ProgettoDTO();
		progetto.setIdProgetto(idProgetto);
		ContoEconomicoMaxDataFineVO datiUltimaRimodulazione = getDatiUltimaRimodulazione(progetto);
		Date dataUltimaRimodulazione = null;
		if (datiUltimaRimodulazione != null) {
			dataUltimaRimodulazione = datiUltimaRimodulazione.getDtFineValidita();
		} else {
			ContoEconomicoMaxDataFineVO datiUltimaIstruttoria = getDatiUltimaIstruttoria(progetto);
			dataUltimaRimodulazione = datiUltimaIstruttoria.getDtFineValidita();
		}
		LOG.info(prf + " END");
		return dataUltimaRimodulazione;
	}
	

	private ContoEconomicoMaxDataFineVO getDatiUltimaRimodulazione(ProgettoDTO progetto) {
		return getDatiContoEconomicoByStato(progetto, Constants.STATO_CONTO_ECONOMICO_NUOVA_RIMODULAZIONE_CONCLUSA);
	}

	private ContoEconomicoMaxDataFineVO getDatiUltimaIstruttoria(ProgettoDTO progetto) {
		return getDatiContoEconomicoByStato(progetto, Constants.STATO_CONTO_ECONOMICO_APPROVATO_IN_ISTRUTTORIA);
	}

	private ContoEconomicoMaxDataFineVO getDatiContoEconomicoByStato(
			ProgettoDTO progetto, String stato) {
		String prf = "[QuadroPrevisionaleDAOImpl::getDatiContoEconomicoByStato]";
		LOG.info(prf + " START");
		LOG.info(prf + " idProgetto: "+ progetto.getIdProgetto() + " stato: "+ stato);
		ContoEconomicoMaxDataFineVO contoEconomicoMaxDataFineVO = new ContoEconomicoMaxDataFineVO();
		contoEconomicoMaxDataFineVO.setIdProgetto(NumberUtil.createScaledBigDecimal(progetto.getIdProgetto()));
		contoEconomicoMaxDataFineVO.setDescBreveStatoContoEconom(stato);
		ContoEconomicoMaxDataFineVO[] voz = genericDAO.findWhere(contoEconomicoMaxDataFineVO);
		LOG.info(prf + " END");
		if (voz.length > 0 && voz!=null)
			return voz[0];
		else
			return null;
	}

	private QuadroPrevisionaleDTO getQuadroPrevisionale(Long idProgetto, EsitoFindQuadroPrevisionaleDTO esito) {
		String prf = "[QuadroPrevisionaleDAOImpl::getQuadroPrevisionale]";
		LOG.info(prf + " START");
		QuadroPrevisionaleDTO quadroPrevisionaleDTO = new QuadroPrevisionaleDTO();
		try {
			PbandiTQuadroPrevisionaleVO quadroPrevisionaleVO = null;
			PbandiTQuadroPrevisionaleVO filtro = new PbandiTQuadroPrevisionaleVO();
			filtro.setIdProgetto(new BigDecimal(idProgetto));
			Condition<PbandiTQuadroPrevisionaleVO> conditionDataFineNull = new AndCondition<PbandiTQuadroPrevisionaleVO>(
					new FilterCondition<PbandiTQuadroPrevisionaleVO>(filtro), Condition.validOnly(PbandiTQuadroPrevisionaleVO.class));
			quadroPrevisionaleVO = genericDAO.findSingleWhere(conditionDataFineNull);
			if (quadroPrevisionaleVO != null) {
				quadroPrevisionaleDTO.setIdProgetto(quadroPrevisionaleVO.getIdProgetto().longValue());
				quadroPrevisionaleDTO.setIdQuadro(quadroPrevisionaleVO.getIdQuadroPrevisionale().longValue());
				quadroPrevisionaleDTO.setNote(quadroPrevisionaleVO.getNote());
				esito.setDataUltimoPreventivo(quadroPrevisionaleVO.getDtAggiornamento());
			}
		} catch (Exception e) {
			LOG.debug(prf + " non é stato salvato ancora nessun quadro previsionale per il progetto " + idProgetto);
		}
		LOG.info(prf + " END");
		return quadroPrevisionaleDTO;
	}

	private List<QuadroPrevisionaleDTO> clonaVociDiSpesaConAmmessoPerPeriodo(Set<String> periodi,
			VoceDiSpesaConAmmessoVO[] vociDiSpesaConAmmessoDB) {
		String prf = "[QuadroPrevisionaleDAOImpl::clonaVociDiSpesaConAmmessoPerPeriodo]";
		LOG.info(prf + " START");
		List<QuadroPrevisionaleDTO> vociDiSpesaPerPeriodo = new ArrayList<QuadroPrevisionaleDTO>();
		for (String periodo : periodi) {
			for (VoceDiSpesaConAmmessoVO voceConAmmesso : vociDiSpesaConAmmessoDB) {
				QuadroPrevisionaleDTO voce = new QuadroPrevisionaleDTO();
				voce.setDescVoce(voceConAmmesso.getDescVoceDiSpesa());
				voce.setIdVoce(new Long(voceConAmmesso.getIdVoceDiSpesa().longValue()));
				if (voceConAmmesso.getIdVoceDiSpesaPadre() != null) {
					voce.setIdVocePadre(voceConAmmesso.getIdVoceDiSpesaPadre().longValue());
					voce.setMacroVoce(false);
				} else {
					voce.setMacroVoce(true);
				}
				voce.setPeriodo(periodo);
				vociDiSpesaPerPeriodo.add(voce);
			}
		}
		LOG.debug(prf + " da " + vociDiSpesaConAmmessoDB.length + " vociDiSpesaConAmmessoDB ho creato " + (vociDiSpesaPerPeriodo == null ? 0 : vociDiSpesaPerPeriodo.size()) + " voci con periodo");
		LOG.info(prf + " END");
		return vociDiSpesaPerPeriodo;
	}


	private VoceDiSpesaConRealizzatoVO[] getVociDiSpesaConRealizzato(Long idProgetto) {
		String prf = "[QuadroPrevisionaleDAOImpl::getVociDiSpesaConRealizzato]";
		LOG.info(prf + " START");
		VoceDiSpesaConRealizzatoVO voceDiSpesaConRealizzatoVO = new VoceDiSpesaConRealizzatoVO();
		voceDiSpesaConRealizzatoVO.setIdProgetto(idProgetto);
		VoceDiSpesaConRealizzatoVO[] vociDiSpesaConRealizzato = genericDAO.findWhere(voceDiSpesaConRealizzatoVO);
		LOG.debug("trovati " + (vociDiSpesaConRealizzato == null ? 0 : vociDiSpesaConRealizzato.length) + " voci con realizzato");
		LOG.info(prf + " END");
		return vociDiSpesaConRealizzato;
	}
	
	private void mappaVociDispesaConRealizzato( List<QuadroPrevisionaleDTO> vociDiSpesaPerPeriodo, VoceDiSpesaConRealizzatoVO[] vociDiSpesaConRealizzato) {
		String prf = "[QuadroPrevisionaleDAOImpl::mappaVociDispesaConRealizzato]";
		LOG.info(prf + " START");
		List<VoceDiSpesaConRealizzatoVO> listVociDiSpesaConRealizzato = Arrays.asList(vociDiSpesaConRealizzato);
		for (QuadroPrevisionaleDTO voceDiSpesaConAmmesso : vociDiSpesaPerPeriodo) {
			for (VoceDiSpesaConRealizzatoVO voceDiSpesaConRealizzato : listVociDiSpesaConRealizzato) {
				if (voceDiSpesaConAmmesso.getIdVoce().longValue() == voceDiSpesaConRealizzato.getIdVoceDiSpesa().longValue() && voceDiSpesaConAmmesso.getPeriodo().equals(voceDiSpesaConRealizzato.getPeriodo())) {
					voceDiSpesaConAmmesso.setImportoRealizzato(NumberUtil.toDouble(voceDiSpesaConRealizzato.getRealizzato()));
					break;
				}

			}
		}
		LOG.info(prf + " END");
	}
		
	private List<QuadroPrevisionaleDTO> getRighiQuadroPrevisionale(Long idProgetto) {
		String prf = "[QuadroPrevisionaleDAOImpl::getRighiQuadroPrevisionale]";
		LOG.info(prf + " START");
		PbandiTRigoQuadroPrevisioVO pbandiTRigoQuadroPrevisioVO = new PbandiTRigoQuadroPrevisioVO();
		pbandiTRigoQuadroPrevisioVO.setIdProgetto(new BigDecimal(idProgetto));
		List<QuadroPrevisionaleDTO> figli = new ArrayList<QuadroPrevisionaleDTO>();
		Condition<PbandiTRigoQuadroPrevisioVO> filtroDataFineNull = new AndCondition<PbandiTRigoQuadroPrevisioVO>(
				new FilterCondition<PbandiTRigoQuadroPrevisioVO>( pbandiTRigoQuadroPrevisioVO), Condition.validOnly(PbandiTRigoQuadroPrevisioVO.class));
		PbandiTRigoQuadroPrevisioVO righiDb[] = genericDAO.findWhere(filtroDataFineNull);
		Map<String, String> mapPropsToCopy = new HashMap<String, String>();
		mapPropsToCopy.put("idRigoQuadroPrevisio", "idRigoQuadro");
		mapPropsToCopy.put("idVoceDiSpesa", "idVoce");
		mapPropsToCopy.put("importoPreventivo", "importoPreventivo");
		mapPropsToCopy.put("periodo", "periodo");

		for (PbandiTRigoQuadroPrevisioVO rigoQuadroDb : righiDb) {
			QuadroPrevisionaleDTO rigoFiglio = new QuadroPrevisionaleDTO();
			beanUtil.valueCopy(rigoQuadroDb, rigoFiglio, mapPropsToCopy);
			figli.add(rigoFiglio);
		}
		LOG.info(prf + " END");
		return figli;
	}
	

	private void mappaRighiQuadroPrevisionale(List<QuadroPrevisionaleDTO> vociDiSpesaPerPeriodo, List<QuadroPrevisionaleDTO> righiQuadroPrevisionaleDB) {
		String prf = "[QuadroPrevisionaleDAOImpl::mappaRighiQuadroPrevisionale]";
		LOG.info(prf + " START");
		for (QuadroPrevisionaleDTO voceDiSpesaConAmmesso : vociDiSpesaPerPeriodo) {
			for (QuadroPrevisionaleDTO rigoQuadroPrevisionaleDB : righiQuadroPrevisionaleDB) {
				if ( voceDiSpesaConAmmesso.getIdVoce().longValue() == rigoQuadroPrevisionaleDB.getIdVoce().longValue() 
							&& voceDiSpesaConAmmesso.getPeriodo().equals( rigoQuadroPrevisionaleDB.getPeriodo()) ) {
					voceDiSpesaConAmmesso.setImportoPreventivo(rigoQuadroPrevisionaleDB.getImportoPreventivo());
					voceDiSpesaConAmmesso.setIdRigoQuadro(rigoQuadroPrevisionaleDB.getIdRigoQuadro());
					break;
				}
			}
		}
		LOG.info(prf + " END");
	}
	
	private void setImportiPerMacroVoce(QuadroPrevisionaleDTO quadroPrevisionaleDTO) {
		String prf = "[QuadroPrevisionaleDAOImpl::setImportiPerMacroVoce]";
		LOG.info(prf + " START");
		QuadroPrevisionaleDTO[] figli = quadroPrevisionaleDTO.getFigli();
		Map<String, Map<Long, QuadroPrevisionaleDTO>> periodoMacroVoci = new HashMap<String, Map<Long, QuadroPrevisionaleDTO>>();
		for (QuadroPrevisionaleDTO figlio : figli) {
			figlio.setHaFigli(false);
			Long idVoce = figlio.getIdVocePadre();
			if (idVoce == null)
				idVoce = figlio.getIdVoce();
			if (periodoMacroVoci.containsKey(figlio.getPeriodo())) {
				Map<Long, QuadroPrevisionaleDTO> macroVoci = periodoMacroVoci.get(figlio.getPeriodo());
				if (macroVoci.containsKey(idVoce)) {
					QuadroPrevisionaleDTO macroVoce = macroVoci.get(idVoce);
					if (figlio.getIdVocePadre() != null)
						macroVoce.setHaFigli(true);
					Double preventivo = macroVoce.getImportoPreventivo();
					Double realizzato = macroVoce.getImportoRealizzato();
					Double spesaAmmessa = macroVoce.getImportoSpesaAmmessa();
					if (figlio.getImportoPreventivo()!=null) {
						if (preventivo == null)
							preventivo = 0d;
						macroVoce.setImportoPreventivo(NumberUtil.sum(preventivo, figlio.getImportoPreventivo()));
					}
					if (figlio.getImportoRealizzato()!=null) {
						if (realizzato == null)
							realizzato = 0d;
						macroVoce.setImportoRealizzato(NumberUtil.sum( realizzato, figlio.getImportoRealizzato()));
					}
					if (figlio.getImportoSpesaAmmessa()!=null) {
						if (spesaAmmessa == null)
							spesaAmmessa = 0d;
						macroVoce.setImportoSpesaAmmessa(NumberUtil.sum( spesaAmmessa, figlio.getImportoSpesaAmmessa()));
					}

				} else {
					macroVoci.put(idVoce, figlio);
				}
			} else {
				Map<Long, QuadroPrevisionaleDTO> macroVoci = new HashMap<Long, QuadroPrevisionaleDTO>();
				macroVoci.put(idVoce, figlio);
				periodoMacroVoci.put(figlio.getPeriodo(), macroVoci);
			}
		}
		LOG.info(prf + " END");
	}

	private void aggiungiFigliPeriodi(QuadroPrevisionaleDTO quadroPrevisionaleDTO, Set<String> periodi) {
		String prf = "[QuadroPrevisionaleDAOImpl::aggiungiFigliPeriodi]";
		LOG.info(prf + " START");
		List<QuadroPrevisionaleDTO> figliConPeriodi = new ArrayList<QuadroPrevisionaleDTO>();
		Map<String, QuadroPrevisionaleDTO> mapPeriodi = new HashMap<String, QuadroPrevisionaleDTO>();

		// sommo solo le macrovoci!
		for (QuadroPrevisionaleDTO figlio : quadroPrevisionaleDTO.getFigli()) {
			if (mapPeriodi.containsKey(figlio.getPeriodo())) {
				if (figlio.getMacroVoce()) {
					QuadroPrevisionaleDTO periodo = mapPeriodi.get(figlio
							.getPeriodo());
					Double preventivo = periodo.getImportoPreventivo();
					Double realizzato = periodo.getImportoRealizzato();
					Double spesaAmmessa = periodo.getImportoSpesaAmmessa();
					if (figlio.getImportoPreventivo()!=null) {
						if (preventivo == null)
							preventivo = 0d;
						periodo.setImportoPreventivo(NumberUtil.sum(preventivo, figlio.getImportoPreventivo()));
					}
					if (figlio.getImportoRealizzato()!=null) {
						if (realizzato == null)
							realizzato = 0d;
						periodo.setImportoRealizzato(NumberUtil.sum(realizzato, figlio.getImportoRealizzato()));
					}
					if (figlio.getImportoSpesaAmmessa()!=null) {
						if (spesaAmmessa == null)
							spesaAmmessa = 0d;
						periodo.setImportoSpesaAmmessa(NumberUtil.sum( spesaAmmessa, figlio.getImportoSpesaAmmessa()));
					}
				}
			} else {
				if (figlio.getMacroVoce()) {
					QuadroPrevisionaleDTO figlioPeriodo = new QuadroPrevisionaleDTO();
					figlioPeriodo.setImportoPreventivo(figlio.getImportoPreventivo());
					figlioPeriodo.setImportoRealizzato(figlio.getImportoRealizzato());
					figlioPeriodo.setImportoSpesaAmmessa(figlio.getImportoSpesaAmmessa());

					mapPeriodi.put(figlio.getPeriodo(), figlioPeriodo);
				}
			}
		}

		for (QuadroPrevisionaleDTO figlio : quadroPrevisionaleDTO.getFigli()) {
			if (periodi.contains(figlio.getPeriodo())) {
				QuadroPrevisionaleDTO periodo = mapPeriodi.get(figlio.getPeriodo());
				periodo.setDescVoce(figlio.getPeriodo());
				periodo.setPeriodo(figlio.getPeriodo());
				periodo.setIsPeriodo(true);
				periodo.setMacroVoce(false);
				periodo.setHaFigli(false);
				figliConPeriodi.add(periodo);
				periodi.remove(figlio.getPeriodo());
			}
			figlio.setIsPeriodo(false);
			figliConPeriodi.add(figlio);
		}

		quadroPrevisionaleDTO.setFigli( figliConPeriodi.toArray(new QuadroPrevisionaleDTO[figliConPeriodi.size()]) );
		LOG.info(prf + " END");
	}
	
	private void setImportoDaRealizzare(QuadroPrevisionaleDTO quadroPrevisionaleDTO) {
		String prf = "[QuadroPrevisionaleDAOImpl::setImportoDaRealizzare]";
		LOG.info(prf + " START");
		for (QuadroPrevisionaleDTO figlio : quadroPrevisionaleDTO.getFigli()) {
			Double preventivo = figlio.getImportoPreventivo(), realizzato = figlio.getImportoRealizzato();
			if (preventivo == null) preventivo = 0d;
			if (realizzato == null) realizzato = 0d;
			Double daRealizzare = NumberUtil.toRoundDouble( preventivo - realizzato );
			figlio.setImportoDaRealizzare(daRealizzare);
		}
		LOG.info(prf + " END");
	}


	private ArrayList<QuadroPrevisionaleComplessivoDTO> mappaQuadroComplessivo(QuadroPrevisionaleDTO quadroPrevisionaleDTO, VoceDiSpesaConAmmessoVO[] vociDiSpesaConAmmessoDB) {
		String prf = "[QuadroPrevisionaleDAOImpl::mappaQuadroComplessivo]";
		LOG.info(prf + " START");
		Map<Long, QuadroPrevisionaleComplessivoDTO> mapQuadroPrevisionaleComplessivo = new LinkedHashMap<Long, QuadroPrevisionaleComplessivoDTO>();
		Set<Long> macroVociConFigli = new HashSet<Long>();

		creaQuadroComplessivo(quadroPrevisionaleDTO, mapQuadroPrevisionaleComplessivo, macroVociConFigli);

		ArrayList<QuadroPrevisionaleComplessivoDTO> list = new ArrayList<QuadroPrevisionaleComplessivoDTO>(mapQuadroPrevisionaleComplessivo.values());
		Map<Long, QuadroPrevisionaleComplessivoDTO> mapMacroVoci = new HashMap<Long, QuadroPrevisionaleComplessivoDTO>();

		for (QuadroPrevisionaleComplessivoDTO figlio : list) {

			Double newSpesaAmmessa = null;
			for (VoceDiSpesaConAmmessoVO voceDiSpesaConAmmessoDB : vociDiSpesaConAmmessoDB) {
				if (figlio.getIdVoce().longValue() == voceDiSpesaConAmmessoDB.getIdVoceDiSpesa().longValue()) {
					newSpesaAmmessa = NumberUtil.toDouble(voceDiSpesaConAmmessoDB.getUltimaSpesaAmmessa());
					break;
				}
			}
			if (newSpesaAmmessa == null) newSpesaAmmessa = 0d;
			figlio.setSpesaAmmessa(newSpesaAmmessa);

			Long idVoce = figlio.getIdVocePadre();
			if (idVoce == null)
				idVoce = figlio.getIdVoce();
			if (mapMacroVoci.containsKey(idVoce)) {
				QuadroPrevisionaleComplessivoDTO macroVoce = mapMacroVoci.get(idVoce);
				Double spesaAmmessa = figlio.getSpesaAmmessa();
				Double oldSpesaAmmessa = macroVoce.getSpesaAmmessa();
				if (oldSpesaAmmessa == null)
					oldSpesaAmmessa = 0d;

				if (spesaAmmessa != null) {
					spesaAmmessa = NumberUtil.sum(oldSpesaAmmessa, spesaAmmessa);
					macroVoce.setSpesaAmmessa(spesaAmmessa);
				}
			} else {
				mapMacroVoci.put(idVoce, figlio);
			}

		}
		setVoceQuadroComplessivoHaFigli(macroVociConFigli, list, mapMacroVoci);

		LOG.info(prf + " END");
		return list;
	}
	
	private void creaQuadroComplessivo(QuadroPrevisionaleDTO quadroPrevisionaleDTO,
			Map<Long, QuadroPrevisionaleComplessivoDTO> mapQuadroPrevisionaleComplessivo,
			Set<Long> macroVociConFigli) {
		String prf = "[QuadroPrevisionaleDAOImpl::creaQuadroComplessivo]";
		LOG.info(prf + " START");
		QuadroPrevisionaleDTO figli[] = quadroPrevisionaleDTO.getFigli();
		for (QuadroPrevisionaleDTO figlio : figli) {
			
			QuadroPrevisionaleComplessivoDTO quadroPrevisionaleComplessivoDTO = null;
			if (figlio.getIdVoce() != null) {
				if (figlio.getIdVocePadre() != null) {
					macroVociConFigli.add(figlio.getIdVocePadre());
				}
				if (mapQuadroPrevisionaleComplessivo.containsKey(figlio.getIdVoce())) {

					Double currentRealizzato = figlio.getImportoRealizzato();
					if (currentRealizzato == null) currentRealizzato = 0d;
					Double currentPreventivo = figlio.getImportoPreventivo();
					if (currentPreventivo == null) currentPreventivo = 0d;

					quadroPrevisionaleComplessivoDTO = mapQuadroPrevisionaleComplessivo.get(figlio.getIdVoce());
					/* Realizzato */
					Double realizzatoComplessivo = quadroPrevisionaleComplessivoDTO.getRealizzato();
					realizzatoComplessivo = NumberUtil.sum( realizzatoComplessivo, currentRealizzato);
					quadroPrevisionaleComplessivoDTO.setRealizzato(realizzatoComplessivo);
					/* preventivo */
					Double preventivoComplessivo = quadroPrevisionaleComplessivoDTO.getPreventivo();
					preventivoComplessivo = NumberUtil.sum( preventivoComplessivo, currentPreventivo);
					quadroPrevisionaleComplessivoDTO.setPreventivo(preventivoComplessivo);
					/* Da Realizzare */
					Double daRealizzare = NumberUtil.toRoundDouble(preventivoComplessivo - realizzatoComplessivo);
					quadroPrevisionaleComplessivoDTO.setDaRealizzare(daRealizzare);
				} else {
					quadroPrevisionaleComplessivoDTO = new QuadroPrevisionaleComplessivoDTO();
					quadroPrevisionaleComplessivoDTO.setDescVoce(figlio.getDescVoce());
					quadroPrevisionaleComplessivoDTO.setIdVoce(figlio.getIdVoce());
					quadroPrevisionaleComplessivoDTO.setMacroVoce(figlio.getIdVocePadre() == null);
					quadroPrevisionaleComplessivoDTO.setIdVocePadre(figlio.getIdVocePadre());

					Double preventivo = figlio.getImportoPreventivo();
					Double realizzato = figlio.getImportoRealizzato();
					if (preventivo == null) preventivo = 0d;
					if (realizzato == null) realizzato = 0d;
					Double daRealizzare = NumberUtil.toRoundDouble(preventivo - realizzato);
					quadroPrevisionaleComplessivoDTO.setDaRealizzare(daRealizzare);
					quadroPrevisionaleComplessivoDTO.setRealizzato(realizzato);
					quadroPrevisionaleComplessivoDTO.setPreventivo(preventivo);			
				}
				mapQuadroPrevisionaleComplessivo.put(figlio.getIdVoce(), quadroPrevisionaleComplessivoDTO);
			}
		}
		LOG.info(prf + " END");
	}
		
	private void setVoceQuadroComplessivoHaFigli(Set<Long> macroVociConFigli,
			ArrayList<QuadroPrevisionaleComplessivoDTO> list,
			Map<Long, QuadroPrevisionaleComplessivoDTO> mapMacroVoci) {
		String prf = "[QuadroPrevisionaleDAOImpl::setVoceQuadroComplessivoHaFigli]";
		LOG.info(prf + " START");
		for (QuadroPrevisionaleComplessivoDTO figlio : list) {

			figlio.setHaFigli(false);
			if (mapMacroVoci.containsKey(figlio.getIdVoce())) {
				figlio = mapMacroVoci.get(figlio.getIdVoce());
				if (macroVociConFigli.contains(figlio.getIdVoce())) {
					figlio.setHaFigli(true);
				} else {
					figlio.setHaFigli(false);
				}
			}
		}
		LOG.info(prf + " END");
	}

	private boolean isVociVisibili(Long idProgetto)throws FormalParameterException {
		/*  if il bandolinea legato al progetto ha la regola */		
		return regolaManager.isRegolaApplicabileForProgetto(idProgetto, Constants.BR31_VOCI_VISIBILI_PER_QUADRO_PREVISIONALE);
	}
	
	private boolean isControlloNuovoImportoBloccante(Long idProgetto) throws FormalParameterException {
		return regolaManager.isRegolaApplicabileForProgetto(idProgetto, Constants.BR32_CONTROLLO_NUOVO_PREVENTIVO_BLOCCANTE);
	}

	
	
	@Override
	@Transactional
	public EsitoSalvaQuadroPrevisionaleDTO salvaQuadroPrevisionale(Long idUtente, String idIride,
			QuadroPrevisionaleDTO quadroPrevizionaleDTO, Long idProgetto) throws UnrecoverableException, FormalParameterException {
		String prf = "[QuadroPrevisionaleDAOImpl::salvaQuadroPrevisionale]";
		LOG.info(prf + " BEGIN");
		
		String[] nameParameter = { "idUtente", "idIride", "quadroPrevizionaleDTO"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, quadroPrevizionaleDTO);
		EsitoSalvaQuadroPrevisionaleDTO esito = new EsitoSalvaQuadroPrevisionaleDTO();

		try {
			// solo se le note del client non corrispondono con quelle del db o  il quadro non esiste ancora
			salvaEStoricizzaQuadroPrevisionale(idUtente, quadroPrevizionaleDTO);
			salvaEStoricizzaRighiQuadroPrevisionale(idUtente, quadroPrevizionaleDTO);
			EsitoFindQuadroPrevisionaleDTO esitoFind = findQuadroPrevisionale(idUtente, idIride, quadroPrevizionaleDTO.getIdProgetto());
			esito.setQuadroPrevisionale(esitoFind.getQuadroPrevisionale());
			//Long idProgetto = quadroPrevizionaleDTO.getIdProgetto();
			 
			// 20/01/2015 la chiusura del task sul quadro previsionale avviene in chiudi progetto tramite pl/sql, qui faccio UNLOCK
			logger.warn("\n\n\n############################ NEOFLUX UNLOCK QUADRO_PREVISIO ##############################\n");
			neofluxBusiness.unlockAttivita(idUtente, idIride, idProgetto,Constants.TASK_QUADRO_PREVISIO);
			logger.warn("############################ NEOFLUX UNLOCK QUADRO_PREVISIO ##############################\n\n\n\n");
			
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		} 
				
	}
	
	/*
	 * 1) importo nullo(macro voce con figli): non inserisco
	 * 
	 * 2) record non esiste : inserisco un nuovo record
	 * 
	 * 3) record esiste e importo ï¿½ diverso : storicizzo e inserisco un nuovo
	 * record
	 * 
	 * 4) record esiste e importo ï¿½ lo stesso: non faccio nulla
	 */
	private void salvaEStoricizzaRighiQuadroPrevisionale(Long idUtente, QuadroPrevisionaleDTO quadroPrevizionaleDTO) throws Exception {
		String prf = "[QuadroPrevisionaleDAOImpl::salvaEStoricizzaRighiQuadroPrevisionale]";
		LOG.info(prf + " START");
		QuadroPrevisionaleDTO[] figli = quadroPrevizionaleDTO.getFigli();
		for (QuadroPrevisionaleDTO figlio : figli) {

			if (figlio.getImportoPreventivo() != null) {
				PbandiTRigoQuadroPrevisioVO pbandiTRigoQuadroPrevisioVO = new PbandiTRigoQuadroPrevisioVO();
				if (figlio.getIdRigoQuadro() == null) {
					// INSERISCO ,caso 2)
					pbandiTRigoQuadroPrevisioVO.setIdVoceDiSpesa(new BigDecimal(figlio.getIdVoce()));
					pbandiTRigoQuadroPrevisioVO.setImportoPreventivo(new BigDecimal(figlio.getImportoPreventivo()));
					pbandiTRigoQuadroPrevisioVO.setIdProgetto(new BigDecimal(quadroPrevizionaleDTO.getIdProgetto()));
					pbandiTRigoQuadroPrevisioVO.setPeriodo(figlio.getPeriodo());
					pbandiTRigoQuadroPrevisioVO.setIdUtenteIns(new BigDecimal( idUtente));
					genericDAO.insert(pbandiTRigoQuadroPrevisioVO);
				} else {
					pbandiTRigoQuadroPrevisioVO.setIdRigoQuadroPrevisio(new BigDecimal(figlio.getIdRigoQuadro()));
					pbandiTRigoQuadroPrevisioVO = genericDAO.findSingleWhere(pbandiTRigoQuadroPrevisioVO);
					if (NumberUtil.compare(pbandiTRigoQuadroPrevisioVO.getImportoPreventivo(), beanUtil.transform(figlio.getImportoPreventivo(), BigDecimal.class)) != 0) {
						// STORICIZZO E INSERISCO,caso 3)
						pbandiTRigoQuadroPrevisioVO.setDtFineValidita(DateUtil.getSysdate());
						pbandiTRigoQuadroPrevisioVO.setIdUtenteAgg(new BigDecimal(idUtente));
						genericDAO.update(pbandiTRigoQuadroPrevisioVO);
						pbandiTRigoQuadroPrevisioVO.setIdVoceDiSpesa(new BigDecimal(figlio.getIdVoce()));
						pbandiTRigoQuadroPrevisioVO.setIdUtenteAgg(null);
						pbandiTRigoQuadroPrevisioVO.setIdUtenteIns(new BigDecimal(idUtente));
						pbandiTRigoQuadroPrevisioVO.setIdRigoQuadroPrevisio(null);
						pbandiTRigoQuadroPrevisioVO.setDtFineValidita(null);
						pbandiTRigoQuadroPrevisioVO.setDtInizioValidita(null);
						pbandiTRigoQuadroPrevisioVO.setImportoPreventivo(new BigDecimal(figlio.getImportoPreventivo()));
						genericDAO.insert(pbandiTRigoQuadroPrevisioVO);
					} else {
						// caso 4)
					}
				}
			} else {
				// NON FO NULLA: caso 1)
				LOG.debug(prf + " macro voce con figli,non inserisco");
			}
		}
		LOG.info(prf + " END");
	}

	/*
	 * 1)il quadro non esiste : lo inserisco
	 * 
	 * 2)OLD (sostituito da 4) ): il quadro esiste e le note SONO cambiate :
	 * storicizza il vecchio e ne inserisce uno nuovo
	 * 
	 * 3)OLD (sostituito da 4) )il quadro esiste e le note NON sono cambiate :
	 * aggiorna solo la data aggiornamento
	 * 
	 * 4)il quadro esiste : storicizza il vecchio e ne inserisce uno nuovo
	 */

	private BigDecimal salvaEStoricizzaQuadroPrevisionale(Long idUtente, QuadroPrevisionaleDTO quadroPrevizionaleDTO) throws Exception {
		String prf = "[QuadroPrevisionaleDAOImpl::salvaEStoricizzaQuadroPrevisionale]";
		LOG.info(prf + " START");
		PbandiTQuadroPrevisionaleVO pbandiTQuadroPrevisionaleVO = new PbandiTQuadroPrevisionaleVO();
		BigDecimal idQuadro = null;
		if (quadroPrevizionaleDTO.getIdQuadro() == null) {
			// caso 1)
			pbandiTQuadroPrevisionaleVO.setNote(quadroPrevizionaleDTO.getNote());
			pbandiTQuadroPrevisionaleVO.setIdUtenteIns(new BigDecimal(idUtente));
			pbandiTQuadroPrevisionaleVO.setIdProgetto(new BigDecimal( quadroPrevizionaleDTO.getIdProgetto()));
			pbandiTQuadroPrevisionaleVO.setDtAggiornamento(DateUtil.getSysdate());
			idQuadro = ((PbandiTQuadroPrevisionaleVO) genericDAO.insert(pbandiTQuadroPrevisionaleVO)).getIdQuadroPrevisionale();
		} else {
			idQuadro = new BigDecimal(quadroPrevizionaleDTO.getIdQuadro());
			pbandiTQuadroPrevisionaleVO.setIdQuadroPrevisionale(idQuadro);

			pbandiTQuadroPrevisionaleVO = genericDAO.findSingleWhere(pbandiTQuadroPrevisionaleVO);

			if (quadroPrevizionaleDTO.getNote() == null)
				quadroPrevizionaleDTO.setNote("");

			java.sql.Date currentDate = DateUtil.getSysdate();
			/*
			 * if (!pbandiTQuadroPrevisionaleVO.getNote().equals(
			 * quadroPrevizionaleDTO.getNote())) {
			 */
			// caso 2)
			pbandiTQuadroPrevisionaleVO.setDtFineValidita(currentDate);
			pbandiTQuadroPrevisionaleVO.setDtAggiornamento(currentDate);
			pbandiTQuadroPrevisionaleVO.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.update(pbandiTQuadroPrevisionaleVO);
			
			pbandiTQuadroPrevisionaleVO.setIdQuadroPrevisionale(null);
			pbandiTQuadroPrevisionaleVO.setDtInizioValidita(null);
			pbandiTQuadroPrevisionaleVO.setDtFineValidita(null);
			pbandiTQuadroPrevisionaleVO.setDtAggiornamento(currentDate);
			pbandiTQuadroPrevisionaleVO.setNote(quadroPrevizionaleDTO.getNote());
			pbandiTQuadroPrevisionaleVO.setIdUtenteIns(new BigDecimal(idUtente));
			idQuadro = ((PbandiTQuadroPrevisionaleVO) genericDAO.insert(pbandiTQuadroPrevisionaleVO)).getIdQuadroPrevisionale();

		}
		LOG.info(prf + " END");
		return idQuadro;
	}

}
