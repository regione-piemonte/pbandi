/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.filestorage.business.api.impl.FileApiServiceImpl;
import it.csi.pbandi.filestorage.exceptions.IncorrectUploadPathException;
import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.RegolaManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.RevocaRecuperoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Task;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.CausaleErogazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.DettaglioRecuperoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.EsitoSalvaRecuperoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.MessaggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.ModalitaAgevolazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.MotivoRevocaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.RecuperoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.RevocaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.TipologiaRecuperoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.PeriodoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.ContoEconomicoNonTrovatoException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO.Pair;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ErogazioneCausaleModalitaAgevolazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ImportoTotaleDisimpegniVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ModalitaDiAgevolazioneContoEconomicoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.recupero.ModalitaAgevolazioneTotaleRecuperoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.recupero.RecuperoProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.recupero.TipoRecuperoTipoAnagraficaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.revoca.ModalitaAgevolazioneTotaleRevocheVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.revoca.MotivoRevocaTotaleRevocatoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.revoca.RevocaProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDStatoPropostaCertifVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipoRecuperoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRDettPropCertifRecuVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDettPropostaCertifVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTPeriodoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTPropostaCertificazVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRecuperoVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbservizit.util.UseCaseConstants;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.recupero.Soppressione;
import it.csi.pbandi.pbweberog.integration.dao.RecuperiDAO;
import it.csi.pbandi.pbweberog.integration.response.ResponseInizializzaSoppressioniDTO;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.DateUtil;
import it.csi.pbandi.pbweberog.util.ErrorMessages;
import it.csi.pbandi.pbweberog.util.MessageConstants;
import it.csi.pbandi.pbweberog.util.NumberUtil;

@Component
public class RecuperiDAOImpl extends JdbcDaoSupport implements RecuperiDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	protected BeanUtil beanUtil;
	
	protected FileApiServiceImpl fileApiServiceImpl;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	ContoEconomicoManager contoEconomicoManager;
	
	@Autowired
	RegolaManager regolaManager;
	
	@Autowired
	NeofluxBusinessImpl neofluxBusiness;
	
	@Autowired
	RevocaRecuperoManager revocaRecuperoManager;
	
	@Autowired
	DecodificheManager decodificheManager;
	
	@Autowired
	protected ProfilazioneDAO profilazioneDao;
	
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	@Autowired
	public RecuperiDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
		this.genericDAO = new GenericDAO(dataSource);
		try {
			fileApiServiceImpl = new FileApiServiceImpl(Constants.ROOT_FILE_SYSTEM);
		} catch (IncorrectUploadPathException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional
	public boolean isRecuperoAccessibile(Long idUtente, String idIride, Long idProgetto) throws Exception {
		String prf = "[RecuperiDAOImpl::isRecuperoAccessibile]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idProgetto);
			return regolaManager.isRegolaApplicabileForProgetto(idProgetto, RegoleConstants.BR28_RECUPERO_DISPONIBILE);
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public TipologiaRecuperoDTO[] findTipologieRecuperi(Long idUtente, String idIride, String codiceRuolo) throws Exception {
		String prf = "[RecuperiDAOImpl::isRecuperoAccessibile]";
		LOG.info(prf + " BEGIN");
		try {

			String[] nameParameter = { "idUtente", "identitaDigitale", "codiceRuolo" };
			ValidatorInput.verifyNullValue(nameParameter, codiceRuolo);		
			TipoRecuperoTipoAnagraficaVO vo = new TipoRecuperoTipoAnagraficaVO();
			vo.setDescBreveTipoAnagrafica(codiceRuolo);
			vo.setAscendentOrder("descTipoRecupero");
			List<TipoRecuperoTipoAnagraficaVO> result = genericDAO.findListWhere(vo);
			LOG.info(prf + " END");
			return beanUtil.transform(result, TipologiaRecuperoDTO.class);		
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public PeriodoDTO[] findPeriodi(Long idUtente, String idIride) throws Exception {
		String prf = "[RecuperiDAOImpl::findPeriodi]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,idIride);
			PbandiTPeriodoVO pbandiTPeriodoVO=new PbandiTPeriodoVO();
			pbandiTPeriodoVO.setIdTipoPeriodo(Constants.ID_TIPO_PERIODO_ANNO_CONTABILE);
			pbandiTPeriodoVO.setDescendentOrder("dtInizioContabile");
			List<PbandiTPeriodoVO> periodi = genericDAO.findListWhere(new FilterCondition(pbandiTPeriodoVO));
			Iterator<PbandiTPeriodoVO> iter = periodi.iterator();
			int currentYear = DateUtil.getAnno();
			int mese = DateUtil.getMese();
			
			String anno=""+currentYear;
			if(mese<7)
				anno=""+(currentYear-1);			
			while(iter.hasNext()){
				PbandiTPeriodoVO periodo = iter.next();
				String descPeriodoVisualizzata = periodo.getDescPeriodoVisualizzata();
				if(descPeriodoVisualizzata.contains(anno)) { anno=""; }
				else { iter.remove(); }
			}
			PeriodoDTO[] result = beanUtil.transform(periodi, PeriodoDTO.class);
			LOG.info(prf + " END");
			return result;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public RecuperoDTO[] findRecuperi(Long idUtente, String idIride, Long idProgetto) throws Exception {
		String prf = "[RecuperiDAOImpl::findRecuperi]";
		LOG.info(prf + " BEGIN");
		try {
			List<ModalitaDiAgevolazioneContoEconomicoVO> listModalitaVO = null;
			String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idProgetto);
			try {
				listModalitaVO = contoEconomicoManager.caricaModalitaAgevolazione( NumberUtil.toBigDecimal(idProgetto), Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
			} catch (ContoEconomicoNonTrovatoException e) {
				LOG.error("Nessun conto economico trovato", e);			
				throw new Exception(ErrorMessages.ERRORE_SERVER);
			}

			RecuperoDTO[] result = beanUtil.transform(listModalitaVO, RecuperoDTO.class);
			if (result != null) {
				List<ModalitaAgevolazioneTotaleRecuperoVO> listModalita = new ArrayList<ModalitaAgevolazioneTotaleRecuperoVO>();
				List<MotivoRevocaTotaleRevocatoVO> listMotiviRevoca = new ArrayList<MotivoRevocaTotaleRevocatoVO>();
				for (ModalitaDiAgevolazioneContoEconomicoVO vo : listModalitaVO) {
					ModalitaAgevolazioneTotaleRecuperoVO modalitaAgevolazioneVO = new ModalitaAgevolazioneTotaleRecuperoVO();
					modalitaAgevolazioneVO.setIdModalitaAgevolazione(vo.getIdModalitaAgevolazione());
					listModalita.add(modalitaAgevolazioneVO);
					MotivoRevocaTotaleRevocatoVO motivoRevocaVO = new MotivoRevocaTotaleRevocatoVO();
					motivoRevocaVO.setIdModalitaAgevolazione(vo.getIdModalitaAgevolazione());
					listMotiviRevoca.add(motivoRevocaVO);
				}
				/** Ricerco i recuperi */
				ModalitaAgevolazioneTotaleRecuperoVO filtroVO = new ModalitaAgevolazioneTotaleRecuperoVO();
				filtroVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
				filtroVO.setAscendentOrder("idModalitaAgevolazione");

				FilterCondition<ModalitaAgevolazioneTotaleRecuperoVO> inConditionModalita = new FilterCondition<ModalitaAgevolazioneTotaleRecuperoVO>( listModalita);
				FilterCondition<ModalitaAgevolazioneTotaleRecuperoVO> filterConditionModalita = new FilterCondition<ModalitaAgevolazioneTotaleRecuperoVO>( filtroVO);				
				AndCondition<ModalitaAgevolazioneTotaleRecuperoVO> andConditionModalita = new AndCondition<ModalitaAgevolazioneTotaleRecuperoVO>( inConditionModalita, filterConditionModalita);
				List<ModalitaAgevolazioneTotaleRecuperoVO> listRecuperiVO = genericDAO.findListWhere(andConditionModalita);

				/** Ricerco i motivi di revoca per ogni modalita di agevolazione. */
				MotivoRevocaTotaleRevocatoVO filtroRevocaVO = new MotivoRevocaTotaleRevocatoVO();
				filtroRevocaVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
				filtroRevocaVO.setAscendentOrder("idModalitaAgevolazione", "descMotivoRevoca");

				FilterCondition<MotivoRevocaTotaleRevocatoVO> inConditionMotivi = new FilterCondition<MotivoRevocaTotaleRevocatoVO>( listMotiviRevoca);
				FilterCondition<MotivoRevocaTotaleRevocatoVO> filterConditionMotivi = new FilterCondition<MotivoRevocaTotaleRevocatoVO>( filtroRevocaVO);
				AndCondition<MotivoRevocaTotaleRevocatoVO> andConditionMotivi = new AndCondition<MotivoRevocaTotaleRevocatoVO>( inConditionMotivi, filterConditionMotivi);

				List<MotivoRevocaTotaleRevocatoVO> listMotiviVO = genericDAO.findListWhere(andConditionMotivi);
				for (RecuperoDTO dto : result) {
					/** Ciclo per i motivi di revoca */
					if (listMotiviVO != null) {
						List<MotivoRevocaDTO> motivi = new ArrayList<MotivoRevocaDTO>();
						for (MotivoRevocaTotaleRevocatoVO vo : listMotiviVO) {
							if (NumberUtil.compare(vo.getIdModalitaAgevolazione(), NumberUtil.toBigDecimal(dto.getIdModalitaAgevolazione())) == 0) {
								motivi.add(beanUtil.transform(vo, MotivoRevocaDTO.class));
							}
						}
						dto.setMotiviRevoche(beanUtil.transform(motivi, MotivoRevocaDTO.class));
					}

					if (listRecuperiVO != null) {
						for (ModalitaAgevolazioneTotaleRecuperoVO vo : listRecuperiVO) {
							if (NumberUtil.compare(vo.getIdModalitaAgevolazione(), NumberUtil.toBigDecimal(dto.getIdModalitaAgevolazione())) == 0) {
								dto.setDtUltimaRevoca(vo.getDtUltimaRevoca());							
								Double importoRecuperatoDouble = NumberUtil.toDouble(vo.getTotaleImportoRecuperato());
								if (dto.getTotaleImportoRecuperato() == null)
									dto.setTotaleImportoRecuperato(importoRecuperatoDouble);
								else 
									dto.setTotaleImportoRecuperato(dto.getTotaleImportoRecuperato()+importoRecuperatoDouble);
								dto.setTotaleImportoRevocato(NumberUtil.toDouble(vo.getTotaleImportoRevocato()));
								dto.setEstremiUltimaRevoca(vo.getEstremiUltimaRevoca());
							}
						}
					}
				}
			}
			LOG.info(prf + " END");
			return result;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public EsitoSalvaRecuperoDTO checkRecuperi(Long idUtente, String idIride, RecuperoDTO[] recuperi) throws FormalParameterException {
		String prf = "[RecuperiDAOImpl::checkRecuperi]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "idIride", "recuperi" };
		try {
			ValidatorInput.verifyNullValue(nameParameter, recuperi);
			List<MessaggioDTO> msgs = new ArrayList<MessaggioDTO>();
			Double totaleRecupero = 0D;
			Double totaleRevocato = 0D;
			EsitoSalvaRecuperoDTO esito = new EsitoSalvaRecuperoDTO();
			esito.setEsito(Boolean.FALSE);
			for (RecuperoDTO recupero : recuperi) {
				boolean hasRecuperoError = false;
				/*
				 * Verifico che per il recupero ci siano delle revoche
				 */
				if (recupero.getTotaleImportoRevocato() == null || NumberUtil.compare(recupero.getTotaleImportoRevocato(), 0D) == 0) {
					MessaggioDTO error = new MessaggioDTO();
					error.setId(recupero.getIdModalitaAgevolazione());
					error.setMsgKey(ErrorMessages.ERRORE_RECUPERO_NESSUNA_REVOCA);
					msgs.add(error);
					hasRecuperoError = true;
				}
			
				/** Verifico che l' importo del recupero sia maggiore di zero  */
				if (!hasRecuperoError&& (recupero.getImportoRecupero() == null || NumberUtil.compare(recupero.getImportoRecupero(), 0D) < 0)) {
					MessaggioDTO error = new MessaggioDTO();
					error.setId(recupero.getIdModalitaAgevolazione());
					error.setMsgKey(ErrorMessages.ERRORE_RECUPERO_IMPORTO_RECUPERO_NEGATIVO);
					msgs.add(error);
					hasRecuperoError = true;
				}
			
				/*
				 * Verifico che la somma dei recuperi (gia' recuperato + nuovo
				 * recupero) non sia maggiore del totale delle revoche
				 */
				if (!hasRecuperoError && recupero.getImportoRecupero() != null) {
					Double nuovoTotaleRecuperatoModalita = NumberUtil.sum( NumberUtil.getDoubleValue(recupero.getTotaleImportoRecuperato()), recupero.getImportoRecupero());
					if (NumberUtil.compare(nuovoTotaleRecuperatoModalita, recupero.getTotaleImportoRevocato()) > 0) {
						MessaggioDTO error = new MessaggioDTO();
						error.setId(recupero.getIdModalitaAgevolazione());
						error.setMsgKey(ErrorMessages.ERRORE_RECUPERO_TOTALE_MAGGIORE_REVOCHE);
						msgs.add(error);
						hasRecuperoError = true;
					}
					totaleRecupero = NumberUtil.sum(totaleRecupero, nuovoTotaleRecuperatoModalita);
				} else {
					totaleRecupero = NumberUtil.sum(totaleRecupero, NumberUtil.getDoubleValue(recupero.getTotaleImportoRecuperato()));
				}
				totaleRevocato = NumberUtil.sum(totaleRecupero, NumberUtil.getDoubleValue(recupero.getTotaleImportoRevocato()));
			}
			/*  Verifico che la somma totale dei recuperi per il progetto sia * minore o uguale alla somma delle revoche */
			if (!msgs.isEmpty() && NumberUtil.compare(totaleRecupero, totaleRevocato) > 0) {
				MessaggioDTO error = new MessaggioDTO();
				error.setMsgKey(ErrorMessages.ERRORE_RECUPERO_TOTALE_PROGETTO_MAGGIORE_REVOCHE);
				msgs.add(error);
			}
			
			if (msgs.isEmpty()) {
				esito.setEsito(Boolean.TRUE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(Constants.WARN_CONFERMA_SALVATAGGIO);
				msgs.add(msg);
			}
			esito.setMsgs(beanUtil.transform(msgs, MessaggioDTO.class));
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			throw e;
		}		
	}

	@Override
	@Transactional
	public EsitoSalvaRecuperoDTO salvaRecuperi(Long idUtente, String idIride, RecuperoDTO[] recuperi) throws Exception {
		String prf = "[RecuperiDAOImpl::salvaRecuperi]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "idIride", "recuperi" };
		try {
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, recuperi);
			LOG.info("\n\n\n\n\n\n\nsalvaRecuperi ################");
			Long idProgetto=null;		
			for (RecuperoDTO recupero : recuperi) {
				idProgetto=recupero.getIdProgetto();			
				if (NumberUtil.compare(recupero.getImportoRecupero(), 0D) != 0) {
					PbandiTRecuperoVO vo = new PbandiTRecuperoVO();
					vo.setDtRecupero(DateUtil.utilToSqlDate(recupero.getDtRecupero()));
					vo.setIdModalitaAgevolazione(NumberUtil.toBigDecimal(recupero.getIdModalitaAgevolazione()));
					vo.setIdProgetto(NumberUtil.toBigDecimal(recupero.getIdProgetto()));
					vo.setIdTipoRecupero(NumberUtil.toBigDecimal(recupero.getIdTipoRecupero()));
					vo.setIdUtenteIns(NumberUtil.toBigDecimal(idUtente));
					vo.setImportoRecupero(BeanUtil.transformToBigDecimal(recupero.getImportoRecupero()));
					vo.setNote(recupero.getNoteRecupero());
					vo.setEstremiRecupero(recupero.getEstremiRecupero());
					LOG.info("recupero.getIdPeriodo(): "+recupero.getIdPeriodo());
					if(recupero.getIdPeriodo()!=null)
						vo.setIdPeriodo( NumberUtil.toBigDecimal(recupero.getIdPeriodo()));
					try {
						genericDAO.insert(vo);
					} catch (Exception e) {
						LOG.error("Errore durante insert PBANDI_T_RECUPERO", e);
						throw new Exception(ErrorMessages.ERRORE_SERVER);
					}
				}
			}
			LOG.warn("\n\n\n############################ NEOFLUX UNLOCK RECUPERO ##############################\n");
			neofluxBusiness.unlockAttivita(idUtente, idIride, idProgetto,Task.RECUPERO);
			LOG.warn("############################ NEOFLUX UNLOCK RECUPERO ##############################\n\n\n\n");

			EsitoSalvaRecuperoDTO esito = new EsitoSalvaRecuperoDTO();
			esito.setEsito(Boolean.TRUE);
			MessaggioDTO msg = new MessaggioDTO();
			msg.setMsgKey(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			esito.setMsgs(new MessaggioDTO[] { msg });
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			throw e;
		}
	}
	
	//////////////////////////////////////MODIFICA RECUPERO //////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////	
	@Override
	@Transactional
	public ModalitaAgevolazioneDTO[] findRiepilogoRecuperi(Long idUtente, String idIride, DettaglioRecuperoDTO filtro) throws Exception {
		String prf = "[RecuperiDAOImpl::findRiepilogoRecuperi]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "identitaDigitale", "filtro", "filtro.idProgetto" };
		try {
			ValidatorInput.verifyNullValue(nameParameter, filtro, filtro.getIdProgetto());
			List<ModalitaDiAgevolazioneContoEconomicoVO> listModalitaVO = null;
			Long idProgetto = filtro.getIdProgetto();
			Long idModalitaAgegolazione = filtro.getIdModalitaAgevolazione();
			Long idRecupero = filtro.getIdRecupero();
			/* Recupero le modalita' di agevolazioni legate al progetto (Conto  Economico Master) */
			try {
				listModalitaVO = contoEconomicoManager.caricaModalitaAgevolazione(NumberUtil.toBigDecimal(idProgetto), Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
			} catch (ContoEconomicoNonTrovatoException e) {
				LOG.error("Nessun conto economico trovato", e);
				throw new Exception(ErrorMessages.ERRORE_SERVER);
			}
			List<ModalitaAgevolazioneDTO> result = new ArrayList<ModalitaAgevolazioneDTO>();

			if (listModalitaVO != null) {
				for (ModalitaDiAgevolazioneContoEconomicoVO modalitaVO : listModalitaVO) {
					ModalitaAgevolazioneDTO dto = beanUtil.transform(modalitaVO, ModalitaAgevolazioneDTO.class);
					dto.setIdProgetto(idProgetto);
					if (idModalitaAgegolazione != null) {
						/* Ricerco solo di dati della modalita' di agevolazione  che interessa */
						if (NumberUtil.compare(modalitaVO.getIdModalitaAgevolazione(), NumberUtil.toBigDecimal(idModalitaAgegolazione)) == 0) {
							dto = findRiepilogoRecupero(dto, idRecupero);
						}
					} else {
						/* Ricerco i dati di tutte le modalita' di agevolazioni */
						dto = findRiepilogoRecupero(dto, idRecupero);
					}
					if (dto != null)
						result.add(dto);
				}
			}
			LOG.info(prf + " END");
			return beanUtil.transform(result, ModalitaAgevolazioneDTO.class);
		} catch (Exception e) {
			throw e;
		}
	}

	private ModalitaAgevolazioneDTO findRiepilogoRecupero(ModalitaAgevolazioneDTO dto, Long idRecupero) throws FormalParameterException {
		String prf = "[RecuperiDAOImpl::findRiepilogoRecuperi]";
		LOG.info(prf + " START");
		Long idProgetto = dto.getIdProgetto();
		Long idModalitaAgevolazione = dto.getIdModalitaAgevolazione();
		List<RecuperoProgettoVO> listRecuperiModalitaVO = revocaRecuperoManager.findRecuperiProgetto(idProgetto, idModalitaAgevolazione, idRecupero);
		/* Considero solo le modalita' di agevolazione per le quali sono stati  inseriti dei recuperi */
		if (!listRecuperiModalitaVO.isEmpty()) {
			dto.setRecuperi(beanUtil.transform(listRecuperiModalitaVO, DettaglioRecuperoDTO.class));

			/* Ricerco le revoche */
			List<RevocaProgettoVO> listRevocaProgettoVO = revocaRecuperoManager.findRevocheProgetto(idProgetto, idModalitaAgevolazione,
							null);
			dto.setRevoche(beanUtil.transform(listRevocaProgettoVO, RevocaDTO.class));

			/* Ricerco le causali */
			List<ErogazioneCausaleModalitaAgevolazioneVO> listCausaleModalitaAgevolazioniVO = revocaRecuperoManager.findCausaliModalitaAgevolazione(idProgetto,
							idModalitaAgevolazione);
			dto.setCausaliErogazioni(beanUtil.transform(listCausaleModalitaAgevolazioniVO, CausaleErogazioneDTO.class));

			/* Ricerco i totali */
			ModalitaAgevolazioneTotaleRevocheVO totaliVO = revocaRecuperoManager.findTotaleRevocheRecuperi(idProgetto, idModalitaAgevolazione);
			dto.setTotaleImportoRecuperato(NumberUtil.toDouble(totaliVO.getTotaleImportoRecuperato()));
			dto.setTotaleImportoRevocato(NumberUtil.toDouble(totaliVO.getTotaleImportoRevocato()));
			LOG.info(prf + " END");
			return dto;
		}
		LOG.info(prf + " END");
		return null;
	}

	@Override
	@Transactional
	public Boolean checkPropostaCertificazione(Long idUtente, String idIride, Long idProgetto) throws FormalParameterException {
		String prf = "[RecuperiDAOImpl::checkPropostaCertificazione]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "idIride", "idProgetto" };
		try {
			ValidatorInput.verifyNullValue(nameParameter, idProgetto);
			LOG.info(prf + " END");
			return revocaRecuperoManager.checkPropostaCertificazioneProgetto(idProgetto);
		} catch (Exception e) {
			throw e;
		}		
	}

	@Override
	@Transactional
	public EsitoSalvaRecuperoDTO modificaRecupero(Long idUtente, String idIride, ModalitaAgevolazioneDTO modalitaAgevolazione ) throws Exception {
		String prf = "[RecuperiDAOImpl::modificaRecupero]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "modalitaAgevolazione" };
			ValidatorInput.verifyNullValue(nameParameter, modalitaAgevolazione);
			ValidatorInput.verifyAtLeastOneNotNullValue(modalitaAgevolazione);
			ValidatorInput.verifyNullValue(new String[] { "modalitaAgevolazione,recuperi[0]" }, modalitaAgevolazione.getRecuperi()[0]);
			ValidatorInput.verifyNullValue(new String[] { "modalitaAgevolazione,recuperi[0].idRecupero" }, modalitaAgevolazione.getRecuperi()[0].getIdRecupero());
		
			EsitoSalvaRecuperoDTO result = new EsitoSalvaRecuperoDTO();
			result.setEsito(Boolean.TRUE);
			List<MessaggioDTO> msgs = new ArrayList<MessaggioDTO>();
		
			DettaglioRecuperoDTO recupero = modalitaAgevolazione.getRecuperi()[0];
			LOG.info("\n\n\n\n\nmodificaRecupero idPeriodo: "+recupero.getIdPeriodo());
			/** Ricerco i dati precedenti del recupero */
			PbandiTRecuperoVO recuperoOldVO = new PbandiTRecuperoVO();
			recuperoOldVO.setIdRecupero(NumberUtil.toBigDecimal(recupero.getIdRecupero()));
			recuperoOldVO = genericDAO.findSingleWhere(recuperoOldVO);
		
			/** Al totale recuperato devo sottrarre il vecchio importo ed * aggiungere il nuovo */
			BigDecimal totaleRecuperato = BeanUtil.transformToBigDecimal(modalitaAgevolazione.getTotaleImportoRecuperato());
			totaleRecuperato = NumberUtil.subtract(totaleRecuperato, recuperoOldVO.getImportoRecupero());
			totaleRecuperato = NumberUtil.sum(totaleRecuperato, BeanUtil.transformToBigDecimal(recupero.getImportoRecupero()));
		
			BigDecimal totaleRevocato = BeanUtil.transformToBigDecimal(modalitaAgevolazione.getTotaleImportoRevocato());
		
			/** Verifico se il recupero e' legato ad una proposta di certificazione e sono state inserite delle note. */
			if (isRecuperoCertificato(recupero.getIdRecupero()) && (recupero.getNote() == null || recupero.getNote().trim().length() == 0)) {
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ErrorMessages.WARN_RECUPERO_RECUPERO_CERTIFICATO);
				msgs.add(msg);
				result.setEsito(Boolean.FALSE);
			}
		
			/* Verifico che il nuovo totale recuperato non sia superiore al  totale delle revoche */
			if (NumberUtil.compare(totaleRecuperato, totaleRevocato) > 0) {
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ErrorMessages.WARN_RECUPERO_TOTALE_RECUPERATO_SUPERIORE_TOTALE_REVOCATO);
				msgs.add(msg);
				result.setEsito(Boolean.FALSE);
			}
		
			/** Se non ci sono errori eseguo l' update */
			if (result.getEsito()) {
				PbandiTRecuperoVO recuperoVO = beanUtil.transform(recupero, PbandiTRecuperoVO.class);
				try {
					recuperoVO.setIdUtenteAgg(new BigDecimal(idUtente));
					genericDAO.update(recuperoVO);
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
					msgs.add(msg);
				} catch (Exception e) {
					LOG.error("Errore in fase di update su PBANDI_T_RECUPERO.", e);
					throw new Exception("Errore in fase di update su PBANDI_T_RECUPERO.");
				}
			}
			result.setMsgs(beanUtil.transform(msgs, MessaggioDTO.class));		
			LOG.info(prf + " END");
			return result;
		} catch (Exception e) {
				throw e;
		}		
	}

	private boolean isRecuperoCertificato(Long idRecupero) {
		String prf = "[RecuperiDAOImpl::isRecuperoCertificato]";
		LOG.info(prf + " START");
		PbandiRDettPropCertifRecuVO dettPropCertRecuperoVO = new PbandiRDettPropCertifRecuVO();
		dettPropCertRecuperoVO.setIdRecupero(NumberUtil.toBigDecimal(idRecupero));
		List<PbandiRDettPropCertifRecuVO> listDettCertRecupero = genericDAO.findListWhere(dettPropCertRecuperoVO);
		LOG.info(prf + " END");
		if (listDettCertRecupero != null && !listDettCertRecupero.isEmpty())
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	@Override
	@Transactional
	public EsitoSalvaRecuperoDTO eliminaRecupero(Long idUtente, String idIride, Long idRecupero) throws Exception {
		String prf = "[RecuperiDAOImpl::eliminaRecupero]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "idRecupero" };
			ValidatorInput.verifyNullValue(nameParameter, idRecupero);
			EsitoSalvaRecuperoDTO result = new EsitoSalvaRecuperoDTO();
			List<MessaggioDTO> msgs = new ArrayList<MessaggioDTO>();
			/** Verifico che il recupero non sia legato ad una proposta di certificazione */
			if (isRecuperoCertificato(idRecupero)) {
				MessaggioDTO msg = new MessaggioDTO();
				if(isSoppressione(idRecupero)) {
					msg.setMsgKey(ErrorMessages.ERRORE_SOPPRESSIONE_PRESENTE_CERTIFICAZIONE_RECUPERO);
				} else {
					msg.setMsgKey(ErrorMessages.ERRORE_RECUPERO_PRESENTE_CERTIFICAZIONE_RECUPERO);
				}
				msgs.add(msg);
				result.setEsito(Boolean.FALSE);
		
			} else {
				/** Cancello fisicamente il recupero */
				PbandiTRecuperoVO recuperoVO = new PbandiTRecuperoVO();
				recuperoVO.setIdRecupero(NumberUtil.toBigDecimal(idRecupero));
				try {
					genericDAO.delete(recuperoVO);
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(MessageConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
					msgs.add(msg);
					result.setEsito(Boolean.TRUE);
				} catch (Exception e) {
					LOG.error("Errore in fase di cancellazione su PBANDI_T_RECUPERO.", e);
					throw new Exception(ErrorMessages.ERRORE_SERVER);
				}
			}
			result.setMsgs(beanUtil.transform(msgs, MessaggioDTO.class));
			LOG.info(prf + " END");
			return result;						
		} catch (Exception e) {
			throw e;
		}	
	}

	private boolean isSoppressione(Long idRecupero) {
		String prf = "[RecuperiDAOImpl::isSoppressione]";
		LOG.info(prf + " START");
		PbandiTRecuperoVO vo = new PbandiTRecuperoVO();
		vo.setIdRecupero(beanUtil.transform(idRecupero, BigDecimal.class));
		vo.setIdTipoRecupero(decodificheManager.decodeDescBreve(PbandiDTipoRecuperoVO.class, Constants.COD_FIDEIUSSIONI_TIPO_RECUPERO_SOPPRESSIONE));
		LOG.info(prf + " END");
		return genericDAO.where(vo).count() > 0;
	}
	
	@Override
	public ResponseInizializzaSoppressioniDTO inizializzaSoppressioni(Long idProgetto, String codiceRuolo, Long idSoggetto, Long idUtente) throws Exception {
		String prf = "[RecuperiDAOImpl::inizializzaSoppressioni]";
		LOG.info(prf + " BEGIN");
		ResponseInizializzaSoppressioniDTO result = new ResponseInizializzaSoppressioniDTO();
		try {
			
			result.setSoppressioni(this.findSoppressioni(idProgetto));
			
			result.setImportoCertificabileLordo(this.doFindLastImportoCertificabileLordoValidato(idProgetto));
			
			result.setImportoTotaleDisimpegni(this.findImportoTotaleDisimpegni(idProgetto));
			
			result.setInsModDelAbilitati(profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo, "OPEROG015"));
			
			LOG.info(prf + " END");
			return result;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}
	
	private ArrayList<Soppressione> findSoppressioni(Long idProgetto) {
		String prf = "[RecuperiDAOImpl::findSoppressioni]";
		LOG.info(prf + " BEGIN");
		ArrayList<Soppressione> result = new ArrayList<Soppressione>(); 
		try {
			PbandiTRecuperoVO query = new PbandiTRecuperoVO();
			query.setIdProgetto(beanUtil
					.transform(idProgetto, BigDecimal.class));
			query.setIdTipoRecupero(decodificheManager.decodeDescBreve(
					PbandiDTipoRecuperoVO.class,
					Constants.COD_FIDEIUSSIONI_TIPO_RECUPERO_SOPPRESSIONE));
			DettaglioRecuperoDTO[] soppressioniSrv = beanUtil.transform(genericDAO.where(query).select(), DettaglioRecuperoDTO.class);
						
			for (DettaglioRecuperoDTO soppSrv : soppressioniSrv) {
				Soppressione sopp = new Soppressione();
				sopp.setDataSoppressione(soppSrv.getDtRecupero());
				sopp.setEstremiDetermina(soppSrv.getEstremiRecupero());
				sopp.setIdAnnoContabile(soppSrv.getIdPeriodo());
				sopp.setIdSoppressione(soppSrv.getIdRecupero());
				sopp.setImportoSoppressione(soppSrv.getImportoRecupero());
				sopp.setNote(soppSrv.getNote());				
				sopp.setIdProgetto(idProgetto);
				sopp.setFlagMonit(soppSrv.getFlagMonit());
				result.add(sopp);
			}
			
			LOG.info(prf + " END");
			return result;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}
	
	private Double doFindLastImportoCertificabileLordoValidato(Long idProgetto) {
		PbandiTDettPropostaCertifVO dett = new PbandiTDettPropostaCertifVO();
		dett.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
		PbandiTPropostaCertificazVO prop = new PbandiTPropostaCertificazVO();
		prop
				.setIdStatoPropostaCertif(decodificheManager
						.decodeDescBreve(
								PbandiDStatoPropostaCertifVO.class,
								it.csi.pbandi.pbweberog.pbandiutil.common.Constants.STATO_PROPOSTA_CERTIFICAZIONE_APPROVATA));
		List<Pair<GenericVO, PbandiTDettPropostaCertifVO, PbandiTPropostaCertificazVO>> join = genericDAO
				.join(Condition.filterBy(dett), Condition.filterBy(prop)).by(
						"idPropostaCertificaz").orderBy(
						GenericDAO.Order.descendent("dtOraCreazione", 1))
				.select();
		if (!join.isEmpty()) {
			PbandiTDettPropostaCertifVO dettaglio = join.get(0).getFirst();
			return beanUtil.transform(dettaglio.getSpesaCertificabileLorda(),
					Double.class);
		} else {
			return null;
		}
	}
	
	//Jira PBANDI-2853
	public Double findImportoTotaleDisimpegni(Long idProgetto) {
		
			ImportoTotaleDisimpegniVO vo = new ImportoTotaleDisimpegniVO();
			vo.setIdProgetto(new BigDecimal(idProgetto));
			vo = genericDAO.findSingleOrNoneWhere(vo);
		
			if (vo == null || vo.getImportoTotaleDisimpegni() == null)
				return new Double(0);
			else
				return vo.getImportoTotaleDisimpegni();
	}
	
	@Override
	public EsitoOperazioni salvaSoppressione(Soppressione soppressione, Long idUtente) throws Exception {
		String prf = "[RecuperiDAOImpl::findSoppressioni]";
		LOG.info(prf + " BEGIN");
		
		String[] nameParameter = { "idUtente", "soppressione" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, soppressione);
		
		try {
			
			DettaglioRecuperoDTO sospSrv = new DettaglioRecuperoDTO();
			sospSrv.setDtRecupero(soppressione.getDataSoppressione());
			sospSrv.setEstremiRecupero(soppressione.getEstremiDetermina());
			sospSrv.setIdPeriodo(soppressione.getIdAnnoContabile());
			sospSrv.setIdRecupero(soppressione.getIdSoppressione());
			sospSrv.setImportoRecupero(soppressione.getImportoSoppressione());
			sospSrv.setNote(soppressione.getNote());
			sospSrv.setIdProgetto(soppressione.getIdProgetto());
			sospSrv.setFlagMonit(soppressione.getFlagMonit());
			
			EsitoOperazioni esito = new EsitoOperazioni();
			PbandiTRecuperoVO soppressioneVO = beanUtil.transform(sospSrv, PbandiTRecuperoVO.class);
			soppressioneVO.setIdTipoRecupero(decodificheManager.decodeDescBreve(
									PbandiDTipoRecuperoVO.class,
									Constants.COD_FIDEIUSSIONI_TIPO_RECUPERO_SOPPRESSIONE));
			PbandiTRecuperoVO soppressioneCheckVO = new PbandiTRecuperoVO();
			soppressioneCheckVO.setIdProgetto(soppressioneVO.getIdProgetto());
			soppressioneCheckVO.setIdTipoRecupero(soppressioneVO.getIdTipoRecupero());
			try {
				Double soppressioneTotale;
				if (soppressioneVO.getIdRecupero() == null) {
					soppressioneTotale = NumberUtil.sum(genericDAO.sum(
							soppressioneCheckVO, "importoRecupero"),
							sospSrv.getImportoRecupero());
				} else {
					PbandiTRecuperoVO soppressioneOldVO = new PbandiTRecuperoVO();
					soppressioneOldVO.setIdRecupero(soppressioneVO.getIdRecupero());
					soppressioneTotale = NumberUtil.subtract(
							NumberUtil.sum(genericDAO.sum(soppressioneCheckVO,"importoRecupero"), 
									       sospSrv.getImportoRecupero()), NumberUtil
							.toDouble((genericDAO.findSingleWhere(soppressioneOldVO).getImportoRecupero())));
				}

				Double lastImportoCertificabileLordoValidato = doFindLastImportoCertificabileLordoValidato(
						beanUtil.transform(soppressioneCheckVO.getIdProgetto(),	Long.class));
				if (NumberUtil.compare(soppressioneTotale, lastImportoCertificabileLordoValidato) <= 0) {
					soppressioneVO.setIdUtenteIns(new BigDecimal(idUtente));
					LOG.info(prf+"soppressioneVO.getIdPeriodo(): "+soppressioneVO.getIdPeriodo());
					soppressioneVO = genericDAO.insertOrUpdateExisting(soppressioneVO);

					esito.setEsito(true);
					esito.setMsg("Salvataggio avvenuto con successo");
				} else {
					esito.setEsito(false);
					esito.setMsg("L\u2019 importo soppressioni \u00E8 maggiore dell\u2019 importo certificabile lordo del progetto (ultima proposta di certificazione approvata).");;
				}

			} catch (Exception e) {
				LOG.error(prf+"Errore nella insert/update della soppressione: "+e);
				esito.setEsito(false);
				esito.setMsg("Non \u00E8 stato possibile portare a termine l\u2019 operazione.");
			}

			return esito;

		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

}
