/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.business.checklist;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbweb.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.CheckListManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.TimerManager;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.CheckListDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.CheckListInLocoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.DichiarazioneDiSpesaChecklistDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoEliminaCheckListDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoGetModuloCheckListDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoLockCheckListDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoSalvaModuloCheckListDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoUnLockCheckListDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.FileDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.FiltroRicercaCheckListDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.AllegatoCheckListInLocoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.CheckListDocumentaleDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.checklist.CheckListException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.exception.RecordNotFoundException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.exception.TooMuchRecordFoundException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiArchivioFileDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ArchivioFileVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoIndexChecklistVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoIndexMaxVersioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.LikeStartsWithCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStatoTipoDocIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRDocuIndexTipoStatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTAppaltoChecklistVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTAppaltoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTChecklistHtmlVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTChecklistVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTContoEconomicoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoIndexLockVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTFileVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.checklist.CheckListSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.Constants;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.messages.ErrorConstants;
import it.csi.pbandi.pbweb.pbandiutil.common.messages.MessaggiConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


public class CheckListBusinessImpl extends BusinessImpl implements CheckListSrv {
	
	private PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl;

	public PbandiArchivioFileDAOImpl getPbandiArchivioFileDAOImpl() {
		return pbandiArchivioFileDAOImpl;
	}

	public void setPbandiArchivioFileDAOImpl(PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl) {
		this.pbandiArchivioFileDAOImpl = pbandiArchivioFileDAOImpl;
	}
	
	private static final Map<String, String> MAP_CHECKLIST_IN_LOCO_DTO_TO_ALLEGATO_VERBALE_DTO = new HashMap<String, String>();

	static {
		MAP_CHECKLIST_IN_LOCO_DTO_TO_ALLEGATO_VERBALE_DTO.put("idProgetto",
				"idProgetto");
		MAP_CHECKLIST_IN_LOCO_DTO_TO_ALLEGATO_VERBALE_DTO.put(
				"bytesVerbaleAllegatoPdf", "bytesDocumento");
	}

	@Autowired
	private GenericDAO genericDAO;

	@Autowired
	private DocumentoManager documentoManager;

	private CheckListManager checkListManager;

	private TimerManager timerManager;

	public TimerManager getTimerManager() {
		return timerManager;
	}

	public void setTimerManager(TimerManager timerManager) {
		this.timerManager = timerManager;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setDocumentoManager(DocumentoManager documentoManager) {
		this.documentoManager = documentoManager;
	}

	public DocumentoManager getDocumentoManager() {
		return documentoManager;
	}

	public void setCheckListManager(CheckListManager checkListManager) {
		this.checkListManager = checkListManager;
	}

	public CheckListManager getCheckListManager() {
		return checkListManager;
	}

	public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.DichiarazioneDiSpesaChecklistDTO[] caricaComboElencoDichiarazioniDiSpesa(
			Long idUtente, String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			CheckListException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto);

		List<PbandiTDichiarazioneSpesaVO> dichiarazioneDiSpesaVOs = null;
		DichiarazioneDiSpesaChecklistDTO[] ritorno = null;

		try {
			PbandiTDichiarazioneSpesaVO dichiarazioneDiSpesaVO = new PbandiTDichiarazioneSpesaVO();
			dichiarazioneDiSpesaVO.setIdProgetto(beanUtil.transform(idProgetto,
					BigDecimal.class));
			dichiarazioneDiSpesaVOs = genericDAO
					.findListWhere(new FilterCondition<PbandiTDichiarazioneSpesaVO>(
							dichiarazioneDiSpesaVO));

			Map<String, String> mapVOToDTORes = new HashMap<String, String>();
			mapVOToDTORes.put("idDichiarazioneSpesa", "idDichiarazione");
			mapVOToDTORes.put("dtDichiarazione", "descDichiarazione");
			ritorno = beanUtil.transform(dichiarazioneDiSpesaVOs,
					DichiarazioneDiSpesaChecklistDTO.class, mapVOToDTORes);
			for (int i = 0; i < ritorno.length; i++) {
				ritorno[i].setDescDichiarazione(NumberUtil
						.getStringValue(ritorno[i].getIdDichiarazione())
						+ "-"
						+ ritorno[i].getDescDichiarazione());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
		return ritorno;
	}

	/**
	 * Vale per utti i tipi di checklist
	 */
	public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.CheckListDTO[] findCheckList(
			Long idUtente, String identitaDigitale, Long idProgetto,
			FiltroRicercaCheckListDTO filtroRicercaCheckList)
			throws CSIException, SystemException, UnrecoverableException,
			CheckListException {

		logger.begin();

		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto", "filtroRicercaCheckList" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, filtroRicercaCheckList);

		filtroRicercaCheckList.setIdProgetto(idProgetto);

		Map<String, String> mapVOToDTOStato = new HashMap<String, String>();
		mapVOToDTOStato.put(".", "descBreveStatoTipDocIndex");

		Map<String, String> mapVOToDTORic = new HashMap<String, String>();
		mapVOToDTORic.put("idProgetto", "idProgetto");
		mapVOToDTORic.put("codiceTipoCheckList", "descBreveTipoDocIndex");
		mapVOToDTORic.put("flagRilevazioneIrregolarita", "flagIrregolarita");
		mapVOToDTORic.put("idDichiarazione", "idTarget");
		mapVOToDTORic.put("dataControllo", "dtControllo");
		mapVOToDTORic.put("versione", "versione");

		Map<String, String> mapVOToDTORicLike = new HashMap<String, String>();
		mapVOToDTORicLike.put("soggettoControllore", "soggettoControllore");

		List<DocumentoIndexChecklistVO> filtriStato = new ArrayList<DocumentoIndexChecklistVO>();
		if (filtroRicercaCheckList.getCodiciStatoCheckList() != null) {
			for (int i = 0; i < filtroRicercaCheckList
					.getCodiciStatoCheckList().length; i++) {
				DocumentoIndexChecklistVO filtroStatoVO = beanUtil.transform(
						filtroRicercaCheckList.getCodiciStatoCheckList()[i],
						DocumentoIndexChecklistVO.class, mapVOToDTOStato);
				filtriStato.add(filtroStatoVO);
			}
		}

		DocumentoIndexChecklistVO condGeneraleVO = beanUtil.transform(
				filtroRicercaCheckList, DocumentoIndexChecklistVO.class,
				mapVOToDTORic);
		DocumentoIndexChecklistVO condSoggettoControlloListVO = beanUtil
				.transform(filtroRicercaCheckList,
						DocumentoIndexChecklistVO.class, mapVOToDTORicLike);

		if (condGeneraleVO.getIdTarget() != null) {
			condGeneraleVO.setIdEntita(documentoManager
					.getIdEntita(PbandiTDichiarazioneSpesaVO.class));
		}

		AndCondition<DocumentoIndexChecklistVO> condizioneComplessiva = new AndCondition<DocumentoIndexChecklistVO>(
				new LikeStartsWithCondition<DocumentoIndexChecklistVO>(
						condSoggettoControlloListVO),
				new FilterCondition<DocumentoIndexChecklistVO>(condGeneraleVO));
		AndCondition<DocumentoIndexChecklistVO> condizioneComplessivaStato = null;

		String[] codiciStato = filtroRicercaCheckList.getCodiciStatoCheckList();
		if (filtroRicercaCheckList != null && (!isEmpty(codiciStato))) {
			if ((codiciStato != null) && (codiciStato.length == 1)
					&& (StringUtil.isEmpty(codiciStato[0]))) {
				condizioneComplessivaStato = condizioneComplessiva;
			} else {
				condizioneComplessivaStato = new AndCondition<DocumentoIndexChecklistVO>(
						condizioneComplessiva,
						new FilterCondition<DocumentoIndexChecklistVO>(
								filtriStato));
			}
		} else {
			condizioneComplessivaStato = condizioneComplessiva;
		}

		List<DocumentoIndexChecklistVO> elenco = genericDAO.where(
				condizioneComplessivaStato).select();

		mapVOToDTORic.put("codiceTipoCheckList", "descBreveTipoDocIndex");

		Map<String, String> mapVOToDTORes = new HashMap<String, String>();
		mapVOToDTORes.put("idDocumentoIndex", "idDocumentoIndex");
		mapVOToDTORes.put("idTarget", "idDichiarazione");
		mapVOToDTORes.put("nomeFile", "nome");
		mapVOToDTORes.put("descBreveStatoTipDocIndex", "codiceStato");
		mapVOToDTORes.put("descStatoTipoDocIndex", "descStato");
		mapVOToDTORes.put("descBreveTipoDocIndex", "codiceTipo");
		mapVOToDTORes.put("descTipoDocIndex", "descTipo");
		mapVOToDTORes.put("soggettoControllore", "soggettoControllore");
		mapVOToDTORes.put("descBreveTipoDocIndex", "codiceTipo");
		mapVOToDTORes.put("dtControllo", "dataControllo");
		mapVOToDTORes.put("versione", "versione");
		
		logger.end();
		return beanUtil.transform(elenco, CheckListDTO.class, mapVOToDTORes);
	}

	/**
	 * Servizio di cancellazione di CheckList IN LOCO (le checklist documentali
	 * che arrivano dalle validazioni non si cancellano mai) in stato BOZZA (se
	 * sono in un altro stato non le si cancella)
	 */
	public EsitoEliminaCheckListDTO eliminaCheckList(Long idUtente,
			String identitaDigitale, Long idDocumentoIndex)
			throws CSIException, SystemException, UnrecoverableException,
			CheckListException {
	 

		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idDocumentoIndex" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idDocumentoIndex);

		EsitoEliminaCheckListDTO esitoEliminaCheckList = new EsitoEliminaCheckListDTO();
		esitoEliminaCheckList
				.setCodiceMessaggio(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);

		BigDecimal idDocumentoIndexBigDecimal = beanUtil.transform(
				idDocumentoIndex, BigDecimal.class);

		try {
			PbandiTDocumentoIndexVO docVO = new PbandiTDocumentoIndexVO();
			docVO.setIdDocumentoIndex(idDocumentoIndexBigDecimal);
			docVO.setIdTipoDocumentoIndex(decodificheManager
					.decodeDescBreve(
							PbandiCTipoDocumentoIndexVO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO));
			docVO = genericDAO.findSingleWhere(Condition.filterBy(docVO));

			try {
				PbandiRDocuIndexTipoStatoVO docuIndexTipoStatoVO = new PbandiRDocuIndexTipoStatoVO();
				docuIndexTipoStatoVO
						.setIdDocumentoIndex(idDocumentoIndexBigDecimal);

				List<PbandiRDocuIndexTipoStatoVO> docuIndexTipoStatoStatiCancellabili = new ArrayList<PbandiRDocuIndexTipoStatoVO>();

				PbandiRDocuIndexTipoStatoVO docuIndexTipoStatoVO1 = new PbandiRDocuIndexTipoStatoVO();
				docuIndexTipoStatoVO1
						.setIdStatoTipoDocIndex(decodificheManager
								.decodeDescBreve(
										PbandiDStatoTipoDocIndexVO.class,
										GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_BOZZA));
				PbandiRDocuIndexTipoStatoVO docuIndexTipoStatoVO2 = new PbandiRDocuIndexTipoStatoVO();
				docuIndexTipoStatoVO2
						.setIdStatoTipoDocIndex(decodificheManager
								.decodeDescBreve(
										PbandiDStatoTipoDocIndexVO.class,
										GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO));

				docuIndexTipoStatoStatiCancellabili.add(docuIndexTipoStatoVO1);
				docuIndexTipoStatoStatiCancellabili.add(docuIndexTipoStatoVO2);

				genericDAO
						.findSingleWhere(Condition
								.filterBy(docuIndexTipoStatoVO)
								.and(Condition
										.filterBy(docuIndexTipoStatoStatiCancellabili)));

				
			 
				
				
				// ed elimino la corrispondente entit� sulla t_checklist
				PbandiTChecklistVO pbandiTChecklistVO = new PbandiTChecklistVO();
				pbandiTChecklistVO.setIdChecklist(docVO.getIdTarget());
				genericDAO.delete(pbandiTChecklistVO);

				// nel caso elimino il documento
				documentoManager.eliminaDocumento(
						beanUtil.transform(idUtente, BigDecimal.class),
						idDocumentoIndexBigDecimal);

			} catch (RecordNotFoundException e) {
				logger.warn("La checklist non � in stato BOZZA.");
				throw new CheckListException(
						MessaggiConstants.KEY_CHECKLIST_NON_POSSIBILE_ELIMINARE_STATO);
			} catch (Exception e) {
				String message = "Impossibile effettuare la cancellazione: "
						+ e.getMessage();
				logger.error(message, e);
				throw new CheckListException(
						MessaggiConstants.KEY_CHECKLIST_LOCCATA, e);
			}
		} catch (RecordNotFoundException e) {
			logger.warn("Tentativo di cancellare una checklist non esistente o non di tipo IN LOCO.");
			throw new CheckListException(
					MessaggiConstants.KEY_CHECKLIST_NON_POSSIBILE_ELIMINARE);
		}  

		return esitoEliminaCheckList;
	}
	
	// Motodo chiamato durante il 'respingi' di un affidamento (Jira PBANDI-2773).
	// Presuppone che l'affidamento abbia al pi� 1 sola bozza di una checklist documentale.
	// Metodo creato sulla falsa riga di eliminaCheckList().
	public EsitoEliminaCheckListDTO eliminaBozzaCheckListDocumentaleAffidamento(
			Long idUtente, String identitaDigitale, 
			Long idAffidamento)
			throws CSIException, SystemException, UnrecoverableException,
			CheckListException { 
	 
		String[] nameParameter = { "idUtente","identitaDigitale","idAffidamento" };
		ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale,idAffidamento);		

		EsitoEliminaCheckListDTO esitoEliminaCheckList = new EsitoEliminaCheckListDTO();
		esitoEliminaCheckList
				.setCodiceMessaggio(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);

		logger.info("eliminaBozzaCheckListDocumentaleAffidamento(): idAffidamento = "+idAffidamento);
		try {
			
			BigDecimal idEntitaPbandiTAppalto = new BigDecimal(Constants.ID_ENTITA_PBANDI_T_APPALTO);
			BigDecimal idTipoDocIndexChecklistDocumentale = 
				decodificheManager.decodeDescBreve(
					PbandiCTipoDocumentoIndexVO.class,
					GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_AFFIDAMENTO_VALIDAZIONE);
			
			// Dall'id appalto\affidamento recupera il docuemnto index relativo alla checklist documentale.			
			PbandiTDocumentoIndexVO docVO = new PbandiTDocumentoIndexVO();
			docVO.setIdTarget(new BigDecimal(idAffidamento));
			docVO.setIdEntita(idEntitaPbandiTAppalto);
			docVO.setIdTipoDocumentoIndex(idTipoDocIndexChecklistDocumentale);
			
			docVO = genericDAO.findSingleOrNoneWhere(Condition.filterBy(docVO));
			
			if (docVO == null) {
				logger.info("eliminaBozzaCheckListDocumentaleAffidamento(): nessuna checklist documentale.");
			} else {
				try {
					
					PbandiRDocuIndexTipoStatoVO docuIndexTipoStatoVO = new PbandiRDocuIndexTipoStatoVO();
					docuIndexTipoStatoVO.setIdDocumentoIndex(docVO.getIdDocumentoIndex());					
	
					PbandiRDocuIndexTipoStatoVO docuIndexTipoStatoVO1 = new PbandiRDocuIndexTipoStatoVO();
					docuIndexTipoStatoVO1
							.setIdStatoTipoDocIndex(decodificheManager
									.decodeDescBreve(
											PbandiDStatoTipoDocIndexVO.class,
											GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_BOZZA));
					
					//PbandiRDocuIndexTipoStatoVO docuIndexTipoStatoVO2 = new PbandiRDocuIndexTipoStatoVO();
					//docuIndexTipoStatoVO2
					//		.setIdStatoTipoDocIndex(decodificheManager
					//				.decodeDescBreve(
					//						PbandiDStatoTipoDocIndexVO.class,
					//						GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO));
	
					List<PbandiRDocuIndexTipoStatoVO> docuIndexTipoStatoStatiCancellabili = new ArrayList<PbandiRDocuIndexTipoStatoVO>();
					docuIndexTipoStatoStatiCancellabili.add(docuIndexTipoStatoVO1);
					//docuIndexTipoStatoStatiCancellabili.add(docuIndexTipoStatoVO2);
	
					genericDAO
							.findSingleWhere(Condition
									.filterBy(docuIndexTipoStatoVO)
									.and(Condition
											.filterBy(docuIndexTipoStatoStatiCancellabili)));
					
					// Trovo la checklist in PBANDI_T_CHECKLIST.
					PbandiTChecklistVO pbandiTChecklistVO = new PbandiTChecklistVO();
					pbandiTChecklistVO.setIdDocumentoIndex(docVO.getIdDocumentoIndex());
					pbandiTChecklistVO = genericDAO.findSingleWhere(pbandiTChecklistVO);

					// Elimino il record in PBANDI_T_CHECKLIST_HTML.
					PbandiTChecklistHtmlVO pbandiTChecklistHtmlVO = new PbandiTChecklistHtmlVO();
					pbandiTChecklistHtmlVO.setIdChecklist(pbandiTChecklistVO.getIdChecklist());
					genericDAO.delete(pbandiTChecklistHtmlVO);
					
					// Elimino il record in PBANDI_T_APPALTO_CHECKLIST.
					PbandiTAppaltoChecklistVO pbandiTAppaltoChecklistVO = new PbandiTAppaltoChecklistVO();
					pbandiTAppaltoChecklistVO.setIdAppalto(new BigDecimal(idAffidamento));
					pbandiTAppaltoChecklistVO.setIdChecklist(pbandiTChecklistVO.getIdChecklist());
					pbandiTAppaltoChecklistVO = genericDAO.findSingleWhere(pbandiTAppaltoChecklistVO);
					genericDAO.delete(pbandiTAppaltoChecklistVO);
					
					// Elimino il record in PBANDI_T_CHECKLIST.
					genericDAO.delete(pbandiTChecklistVO);
					
					// Cancello eventuale lock.
					PbandiTDocumentoIndexLockVO lockVO = new PbandiTDocumentoIndexLockVO();
					lockVO.setIdEntita(idEntitaPbandiTAppalto);
					lockVO.setIdTarget(new BigDecimal(idAffidamento));
					lockVO.setIdProgetto(docVO.getIdProgetto());
					lockVO.setIdTipoDocumentoIndex(idTipoDocIndexChecklistDocumentale);
					genericDAO.delete(lockVO);
					
					// nel caso elimino il documento (PBANDI_T_DOCUMENTO_INDEX e PBANDI_R_DOCU_INDEX_TIPO_STATO)
					documentoManager.eliminaDocumento(
							beanUtil.transform(idUtente, BigDecimal.class),
							docVO.getIdDocumentoIndex());
	
				} catch (RecordNotFoundException e) {
					logger.warn("Trovati nessuno o pi� record in PbandiRDocuIndexTipoStato.");
					throw new CheckListException(
							MessaggiConstants.KEY_CHECKLIST_NON_POSSIBILE_ELIMINARE_STATO);
				} catch (Exception e) {
					String message = "Impossibile effettuare la cancellazione: "
							+ e.getMessage();
					logger.error(message, e);
					throw new CheckListException(
							MessaggiConstants.KEY_CHECKLIST_NON_POSSIBILE_ELIMINARE, e);
				}
			}
		} catch (TooMuchRecordFoundException e) {
			logger.error("eliminaBozzaCheckListDocumentaleAffidamento(): trovato pi� di 1 record in PbandiTDocumentoIndex.");
			throw new CheckListException(
					MessaggiConstants.KEY_CHECKLIST_NON_POSSIBILE_ELIMINARE);
		}  

		return esitoEliminaCheckList;
	}

	public EsitoGetModuloCheckListDTO getModuloCheckListValidazione(
			Long idUtente, String identitaDigitale, Long idProgetto,
			Long idDichiarazione) throws CSIException, SystemException,
			UnrecoverableException, CheckListException {
		logger.info("\n\n\n\n\n\n\n\n\n\n\n\n\n\ngetModuloCheckListValidazione idDichiarazione:"+idDichiarazione+" ,idProgetto:"+idProgetto);
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto", "idDichiarazione" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto, idDichiarazione);
		EsitoGetModuloCheckListDTO esitoGetModulo = new EsitoGetModuloCheckListDTO();
		try {

			BigDecimal idTarget = BigDecimal.valueOf(idDichiarazione);

			CheckListDocumentaleDTO checkListValidazioneDTO = new CheckListDocumentaleDTO();
			checkListValidazioneDTO.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
			checkListValidazioneDTO.setIdDichiarazioneDiSpesa(idTarget);
			checkListValidazioneDTO.setVersione(checkListManager.getVersioneIniziale());

			if (checkListManager.isInviataPregressa(checkListValidazioneDTO)) {
				throw new CheckListException(
						ErrorConstants.ERRORE_CHECKLIST_NON_MODIFICABILE_PREGRESSO);
			}

			esitoGetModulo = checkListManager
					.caricaModuloChecklist(idUtente,identitaDigitale,
							idProgetto,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE,
							documentoManager
									.getNomeFile(checkListValidazioneDTO),
							idTarget, PbandiTDichiarazioneSpesaVO.class);

			return esitoGetModulo;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		} 
	}

	public EsitoGetModuloCheckListDTO getModuloCheckListInLoco(Long idUtente,
			String identitaDigitale, Long idProgetto, Long idDocumentoIndex)
			throws CSIException, SystemException, UnrecoverableException,
			CheckListException {

		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto);
		EsitoGetModuloCheckListDTO esitoGetModulo = new EsitoGetModuloCheckListDTO();
		try {
			it.csi.pbandi.pbweb.pbandisrv.dto.manager.CheckListInLocoDTO checkListInLocoDTO = new it.csi.pbandi.pbweb.pbandisrv.dto.manager.CheckListInLocoDTO();
			checkListInLocoDTO.setIdProgetto(BigDecimal.valueOf(idProgetto));

			if (idDocumentoIndex != null) {
				PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();
				documentoIndexVO.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex ));

				checkListInLocoDTO.setIdChecklist(genericDAO.findSingleWhere(documentoIndexVO).getIdTarget());

				if (checkListManager.isInviata(checkListInLocoDTO)) {
					throw new CheckListException(
							ErrorConstants.ERRORE_CHECKLIST_NON_MODIFICABILE_INVIATA);
				}
			}

			esitoGetModulo = checkListManager
					.caricaModuloChecklist(idUtente,identitaDigitale,
							idProgetto,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO,
							documentoManager.getNomeFile(checkListInLocoDTO),
							checkListInLocoDTO.getIdChecklist(),
							PbandiTChecklistVO.class);

			return esitoGetModulo;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
	}

	public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoSalvaModuloCheckListDTO salvaModuloCheckListValidazione(
			java.lang.Long idUtente, java.lang.String identitaDigitale,
			java.lang.Long idProgetto, byte[] bytesModuloPdf,
			String codStatoTipoDocIndex, java.lang.Long idDichiarazione)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException,
			it.csi.pbandi.pbweb.pbandisrv.exception.checklist.CheckListException {

	 

		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto", "bytesModuloPdf", "codStatoTipoDocIndex",
				"idDichiarazione" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto, bytesModuloPdf,
				codStatoTipoDocIndex, idDichiarazione);

		EsitoSalvaModuloCheckListDTO esitoSalvaModulo = null;
		try {
			EsitoSalvaModuloCheckListDTO esitoSalvaModuloManager = checkListManager
					.salvaChecklistValidazione(idUtente, idProgetto,
							bytesModuloPdf, codStatoTipoDocIndex,
							idDichiarazione);
			esitoSalvaModulo = new it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoSalvaModuloCheckListDTO();
			esitoSalvaModulo.setMessage(esitoSalvaModuloManager.getMessage());
			esitoSalvaModulo.setByteModulo(esitoSalvaModuloManager
					.getByteModulo());
			esitoSalvaModulo.setEsito(esitoSalvaModuloManager.getEsito());
			esitoSalvaModulo.setParams(esitoSalvaModuloManager.getParams());
			/*
			 * FIX PBandi-2006
			 * Inserisco nell' esito anche l'id del documento index
			 */
			esitoSalvaModulo.setIdDocumentoIndex(esitoSalvaModuloManager.getIdDocumentoIndex());
		} catch (it.csi.pbandi.pbweb.pbandisrv.exception.manager.CheckListException e) {
			throw new CheckListException(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
		return esitoSalvaModulo;
	}

	public EsitoSalvaModuloCheckListDTO salvaModuloCheckListInLoco(
			Long idUtente, String identitaDigitale,
			CheckListInLocoDTO checkListInLoco) throws CSIException,
			SystemException, UnrecoverableException, CheckListException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"checkListInLoco.idProgetto", "checkListInLoco.bytesModuloPdf",
				"checkListInLoco.codStatoTipoDocIndex" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, checkListInLoco.getIdProgetto(),
				checkListInLoco.getBytesModuloPdf(),
				checkListInLoco.getCodStatoTipoDocIndex());

		EsitoSalvaModuloCheckListDTO esitoSalvaModulo = null;

		logger.info("\n\n\n\n\n\n\n\n\n\nsaving checkListInLoco.getIdDocumentoIndex(): "+checkListInLoco.getIdDocumentoIndex()
				+" , idUtente:"+idUtente 
				+" ,checkListInLoco.getIdProgetto(): "+checkListInLoco.getIdProgetto());
		
		BigDecimal idDocumentoIndex = beanUtil.transform(checkListInLoco.getIdDocumentoIndex(), BigDecimal.class);
		

		try {
			PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
			progettoVO.setIdProgetto(BigDecimal.valueOf(checkListInLoco.getIdProgetto()));
			progettoVO = genericDAO.findSingleWhere(progettoVO);
			logger.info("\n\n\ncalling checkListManager.salvaChecklistInLoco() idDocumentoIndex: "+idDocumentoIndex);
			EsitoSalvaModuloCheckListDTO esitoSalvaModuloManager = checkListManager
					.salvaChecklistInLoco(idUtente, progettoVO,
							checkListInLoco.getBytesModuloPdf(),
							checkListInLoco.getCodStatoTipoDocIndex(),
							idDocumentoIndex);
			
			logger.info("esitoSalvaModuloManager.getIdDocumentoIndex() ---->>> "+esitoSalvaModuloManager.getIdDocumentoIndex());

//			if (esitoSalvaModuloManager.getEsito()
//					&& GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO
//							.equals(checkListInLoco.getCodStatoTipoDocIndex())) {
//				AllegatoCheckListInLocoDTO allegatoCheckListInLocoDTO = beanUtil
//						.transform(checkListInLoco,
//								AllegatoCheckListInLocoDTO.class,
//								MAP_CHECKLIST_IN_LOCO_DTO_TO_ALLEGATO_VERBALE_DTO);
//
//			
//
//				allegatoCheckListInLocoDTO.setCodiceProgetto(progettoVO
//						.getCodiceProgetto());
//				allegatoCheckListInLocoDTO
//						.setIdChecklist(esitoSalvaModuloManager.getIdChecklist());
//				 
//				documentoManager.creaDocumento(idUtente, allegatoCheckListInLocoDTO,null,null,null,null);
//			}

			esitoSalvaModulo = new it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoSalvaModuloCheckListDTO();
			logger.info("esitoSalvaModulo setMessage: "+esitoSalvaModuloManager.getMessage());
			esitoSalvaModulo.setMessage(esitoSalvaModuloManager.getMessage());
			logger.info("esitoSalvaModulo setByteModulo: "+esitoSalvaModuloManager.getByteModulo());
			esitoSalvaModulo.setByteModulo(esitoSalvaModuloManager.getByteModulo());
			logger.info("esitoSalvaModulo setEsito: "+esitoSalvaModuloManager.getEsito());
			esitoSalvaModulo.setEsito(esitoSalvaModuloManager.getEsito());
			logger.info("esitoSalvaModulo setParams: "+esitoSalvaModuloManager.getParams());
			esitoSalvaModulo.setParams(esitoSalvaModuloManager.getParams());
			logger.info("esitoSalvaModulo setIdDocumentoIndex: "+esitoSalvaModuloManager.getIdDocumentoIndex());
			esitoSalvaModulo.setIdDocumentoIndex(esitoSalvaModuloManager.getIdDocumentoIndex().longValue());
			logger.info("esitoSalvaModulo setIdChecklist: "+esitoSalvaModuloManager.getIdChecklist());
			esitoSalvaModulo.setIdChecklist(esitoSalvaModuloManager.getIdChecklist().longValue());
		} catch (it.csi.pbandi.pbweb.pbandisrv.exception.manager.CheckListException e) {
			throw new CheckListException(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
		return esitoSalvaModulo;
	}

	public boolean salvaDocumentoSuDB(Long idUtente, String identitaDigitale,
			Long idProgetto, Long idChecklist, Long getIdDocumentoIndex, String codStato,
			byte[] bytesAllegatoChecklistInLoco) throws CSIException,
			SystemException, UnrecoverableException, CheckListException {

		logger.begin();
		logger.info("CheckListBusinessImpl::salvaDocumentoSuDB idProgetto : " + idProgetto);
		logger.info("CheckListBusinessImpl::salvaDocumentoSuDB idChecklist : " + idChecklist);
		logger.info("CheckListBusinessImpl::salvaDocumentoSuDB getIdDocumentoIndex : " + getIdDocumentoIndex);
		logger.info("CheckListBusinessImpl::salvaDocumentoSuDB codStato : " + codStato);
		logger.info("CheckListBusinessImpl::salvaDocumentoSuDB bytesAllegatoChecklistInLoco : " + (bytesAllegatoChecklistInLoco == null ? "niente" : bytesAllegatoChecklistInLoco.length));
		
		CheckListInLocoDTO checkListInLocoDTO = new CheckListInLocoDTO();
		checkListInLocoDTO.setIdProgetto(idProgetto);
		checkListInLocoDTO.setIdDocumentoIndex(getIdDocumentoIndex);
		checkListInLocoDTO.setCodStatoTipoDocIndex(codStato);
		checkListInLocoDTO.setBytesModuloPdf(null);
		checkListInLocoDTO.setBytesVerbaleAllegatoPdf(bytesAllegatoChecklistInLoco);
		
		logger.info("CheckListBusinessImpl::salvaDocumentoSuDB checkListInLocoDTO : " + checkListInLocoDTO.toString());
		
		PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
		progettoVO.setIdProgetto(BigDecimal.valueOf(checkListInLocoDTO.getIdProgetto()));
		
		logger.info("CheckListBusinessImpl::salvaDocumentoSuDB progettoVO : " + progettoVO.toString());

		//BigDecimal idDocumentoIndex = beanUtil.transform(checkListInLocoDTO.getIdDocumentoIndex(), BigDecimal.class);

		try {
			if (GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO
							.equals(checkListInLocoDTO.getCodStatoTipoDocIndex())) {
				AllegatoCheckListInLocoDTO allegatoCheckListInLocoDTO = beanUtil
						.transform(checkListInLocoDTO,
								AllegatoCheckListInLocoDTO.class,
								MAP_CHECKLIST_IN_LOCO_DTO_TO_ALLEGATO_VERBALE_DTO);

				allegatoCheckListInLocoDTO.setCodiceProgetto(progettoVO
						.getCodiceProgetto());
				allegatoCheckListInLocoDTO
						.setIdChecklist(new BigDecimal(idChecklist));
				
				logger.info("CheckListBusinessImpl::salvaDocumentoSuDB creaDocumento Start");

				documentoManager.creaDocumentoCL(idUtente, allegatoCheckListInLocoDTO,null,null,null,null);
				//documentoManager.creaDocumento(idUtente, allegatoCheckListInLocoDTO,codStato,null,null,null);
				
				logger.info("CheckListBusinessImpl::salvaDocumentoSuDB creaDocumento End");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
		logger.end();
		return true;
	}

	/**
	 * ritorna true se ha loccato il record e false se non lo ha loccato perch�
	 * loccato da un altro utente Verifica che la checklist sia loccata da un
	 * altro utente che non sia l'utente stesso che ha richiesto la modifica
	 * L'utente si considera loccato quando � presente un lock con data lock
	 * inferiore a sysdate + un timeout definito Se non � presente il lock
	 * l'utente inserisce il lock con data lock = sysdate Se il lock � presente
	 * viene restituito un errore al client che il record � loccato e ogni altra
	 * operazione di modifica della checklist viene vietata per rimuovere il
	 * lock l'utente rimuove ogni lock presete corrispondente al documento
	 * appena modificato
	 */
	public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoLockCheckListDTO salvaLockCheckListInLoco(
			Long idUtente, String identitaDigitale, Long idProgetto,
			Long idDocumentoIndexLong) throws CSIException, SystemException,
			UnrecoverableException, CheckListException {
		 

		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto", "idCheckList" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto, idDocumentoIndexLong);

		String codTipoDocIndex = GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO;
		Class<PbandiTChecklistVO> voClass = PbandiTChecklistVO.class;

		String descBreveStatoDocumento = "";
		BigDecimal idDocumentoIndex = beanUtil.transform(idDocumentoIndexLong,
				BigDecimal.class);
		descBreveStatoDocumento = documentoManager
				.getDescBreveStatoDocumento(idDocumentoIndex);

		EsitoLockCheckListDTO e = new EsitoLockCheckListDTO();
		e.setDescBreveStato(descBreveStatoDocumento);
		try {
			PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();
			documentoIndexVO.setIdDocumentoIndex(idDocumentoIndex);

			e.setEsito(documentoManager.lockDocument(idUtente, idProgetto,
					genericDAO.findSingleWhere(documentoIndexVO).getIdTarget()
							.longValue(), codTipoDocIndex, voClass));
			if (!e.getEsito()) {
				e.setCodiceMessaggio(MessaggiConstants.KEY_CHECKLIST_LOCCATA);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new UnrecoverableException(ex.getMessage(), ex);
		}  
		return e;
	}

	public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoLockCheckListDTO salvaLockCheckListValidazione(
			Long idUtente, String identitaDigitale, Long idProgetto,
			Long idDichiarazione) throws CSIException, SystemException,
			UnrecoverableException, CheckListException {
		 

		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto", "idDichiarazione" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto, idDichiarazione);
		
		Long idTarget = idDichiarazione;
		String codTipoDocIndex = GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE;
		Class<PbandiTDichiarazioneSpesaVO> voClass = PbandiTDichiarazioneSpesaVO.class;

		String descBreveStatoDocumento = "";
		PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = getDocumentoManager()
				.getDocumentoIndexSuDb(
						idTarget,
						idProgetto,
						codTipoDocIndex,
						new PbandiTDichiarazioneSpesaVO()
								.getTableNameForValueObject());
		logger.info(" pbandiTDocumentoIndexVO="+pbandiTDocumentoIndexVO);
		if (pbandiTDocumentoIndexVO != null) {
			descBreveStatoDocumento = documentoManager
					.getDescBreveStatoDocumento(pbandiTDocumentoIndexVO
							.getIdDocumentoIndex());
		}

		EsitoLockCheckListDTO e = new EsitoLockCheckListDTO();
		e.setDescBreveStato(descBreveStatoDocumento);
		try {
			e.setEsito(documentoManager.lockDocument(idUtente, idProgetto,
					idTarget, codTipoDocIndex, voClass));
			
			logger.info("CheckListBusinessImpl::salvaLockCheckListValidazione] esitoLock="+e);
			
			if (!e.getEsito()) {
				e.setCodiceMessaggio(MessaggiConstants.KEY_CHECKLIST_LOCCATA);
			}
			
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new UnrecoverableException(ex.getMessage(), ex);
		} 
		return e;
	}

	public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoUnLockCheckListDTO eliminaLockCheckListValidazione(
			Long idUtente, String identitaDigitale, Long idProgetto,
			Long idDichiarazione) throws CSIException, SystemException,
			UnrecoverableException, CheckListException {
	 
		EsitoUnLockCheckListDTO esito = new EsitoUnLockCheckListDTO();
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto", "idDichiarazione" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto, idDichiarazione);
		try {
			boolean lockRimosso = documentoManager
					.eliminaLock(
							beanUtil.transform(idUtente, BigDecimal.class),
							beanUtil.transform(idProgetto, BigDecimal.class),
							beanUtil.transform(idDichiarazione,
									BigDecimal.class),
							PbandiTDichiarazioneSpesaVO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE);

			esito.setEsito(lockRimosso);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		} 
		return esito;
	}

	public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoUnLockCheckListDTO eliminaLockCheckListInLoco(
			Long idUtente, String identitaDigitale, Long idCheckList)
			throws CSIException, SystemException, UnrecoverableException,
			CheckListException {
		 
		EsitoUnLockCheckListDTO esito = new EsitoUnLockCheckListDTO();
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idCheckList" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idCheckList);
		try {
			boolean lockRimosso = documentoManager
					.eliminaLock(
							beanUtil.transform(idUtente, BigDecimal.class),
							null,
							beanUtil.transform(idCheckList, BigDecimal.class),
							PbandiTChecklistVO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO);

			esito.setEsito(lockRimosso);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
		return esito;
	}

	public EsitoUnLockCheckListDTO eliminaLockCheckListAffidamentoValidazione(
			Long idUtente, String identitaDigitale, Long idProgetto,
			Long idAffidamento) throws CSIException, SystemException,
			UnrecoverableException, CheckListException {
		
		EsitoUnLockCheckListDTO esito = new EsitoUnLockCheckListDTO();
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto", "idAffidamento" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto, idAffidamento);
		try {
			boolean lockRimosso = documentoManager
					.eliminaLock(
							beanUtil.transform(idUtente, BigDecimal.class),
							beanUtil.transform(idProgetto, BigDecimal.class),
							beanUtil.transform(idAffidamento,
									BigDecimal.class),
							PbandiTAppaltoVO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE);

			esito.setEsito(lockRimosso);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		} 
		return esito;
	}

	public EsitoLockCheckListDTO salvaLockCheckListAffidamentoValidazione(
			Long idUtente, String identitaDigitale, Long idProgetto,
			Long idAffidamento) throws CSIException, SystemException,
			UnrecoverableException, CheckListException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto", "idAffidamento" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto, idAffidamento);

		Long idTarget = idAffidamento;
		String codTipoDocIndex = Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_DOCUMENTALE;		// Jira Pbandi-2859
		Class<PbandiTAppaltoVO> voClass = PbandiTAppaltoVO.class;

		String descBreveStatoDocumento = "";
		PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = getDocumentoManager()
				.getDocumentoIndexSuDb(
						idTarget,
						idProgetto,
						codTipoDocIndex,
						new PbandiTAppaltoVO()
								.getTableNameForValueObject());
		if (pbandiTDocumentoIndexVO != null) {
			descBreveStatoDocumento = documentoManager
					.getDescBreveStatoDocumento(pbandiTDocumentoIndexVO
							.getIdDocumentoIndex());
		}

		EsitoLockCheckListDTO e = new EsitoLockCheckListDTO();
		e.setDescBreveStato(descBreveStatoDocumento);
		try {
			e.setEsito(documentoManager.lockDocument(idUtente, idProgetto,
					idTarget, codTipoDocIndex, voClass));
			if (!e.getEsito()) {
				e.setCodiceMessaggio(MessaggiConstants.KEY_CHECKLIST_LOCCATA);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new UnrecoverableException(ex.getMessage(), ex);
		} 
		return e;
	}

	public EsitoUnLockCheckListDTO eliminaLockCheckListAffidamentoInLoco(
			Long idUtente, String identitaDigitale, Long idChecklist)
			throws CSIException, SystemException, UnrecoverableException,
			CheckListException {
		
		EsitoUnLockCheckListDTO esito = new EsitoUnLockCheckListDTO();
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idCheckList" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idChecklist);
		try {
			boolean lockRimosso = documentoManager
					.eliminaLock(
							beanUtil.transform(idUtente, BigDecimal.class),
							null,
							beanUtil.transform(idChecklist, BigDecimal.class),
							PbandiTAppaltoVO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO);

			esito.setEsito(lockRimosso);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
		return esito;
	}

	public EsitoLockCheckListDTO salvaLockCheckListAffidamentoInLoco(
			Long idUtente, String identitaDigitale, Long idProgetto,
			Long idDocumentoIndexLong) throws CSIException, SystemException,
			UnrecoverableException, CheckListException {
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto", "idDocumentoIndexLong" };
		
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto, idDocumentoIndexLong);

		String codTipoDocIndex = GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO;
		Class<PbandiTAppaltoVO> voClass = PbandiTAppaltoVO.class;

		String descBreveStatoDocumento = "";
		BigDecimal idDocumentoIndex = beanUtil.transform(idDocumentoIndexLong, BigDecimal.class);
		descBreveStatoDocumento = documentoManager
				.getDescBreveStatoDocumento(idDocumentoIndex);

		EsitoLockCheckListDTO e = new EsitoLockCheckListDTO();
		e.setDescBreveStato(descBreveStatoDocumento);
		try {
			PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();
			documentoIndexVO.setIdDocumentoIndex(idDocumentoIndex);

			e.setEsito(documentoManager.lockDocument(idUtente, idProgetto,
					genericDAO.findSingleWhere(documentoIndexVO).getIdTarget()
							.longValue(), codTipoDocIndex, voClass));
			if (!e.getEsito()) {
				e.setCodiceMessaggio(MessaggiConstants.KEY_CHECKLIST_LOCCATA);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new UnrecoverableException(ex.getMessage(), ex);
		}  
		return e;
	}

	public FileDTO[] getFilesAssociatedVerbaleChecklistOld(
			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			java.lang.String codTipoDocIndex,
			java.lang.Long idChecklist,
			java.lang.Long idDocIndex
	)	throws it.csi.csi.wrapper.CSIException, it.csi.csi.wrapper.SystemException,
	it.csi.csi.wrapper.UnrecoverableException,
	it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentazione.GestioneDocumentazioneException
	{
		logger.begin();
		
		String[] nameParameter = { "idUtente", "identitaDigitale","codTipoDocIndex","idChecklist" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, codTipoDocIndex,idChecklist);
		logger.info("codTipoDocIndex:"+codTipoDocIndex+", idChecklist "+idChecklist);
		
		
		//Dal codTipoDocIndex recuperiamo l'oggetto interessato
		PbandiCTipoDocumentoIndexVO tipoDocIndex = new PbandiCTipoDocumentoIndexVO();
		tipoDocIndex.setDescBreveTipoDocIndex(codTipoDocIndex);
		tipoDocIndex = genericDAO.findSingleOrNoneWhere(tipoDocIndex);
		if (tipoDocIndex == null || tipoDocIndex.getIdTipoDocumentoIndex() == null) {
			return null;
		}
		
		
		FileDTO[] listaDTO =null;
		ArrayList<PbandiTDocumentoIndexVO> listaVO = null;		
		
			
		//set dati per recuperare allegati
		PbandiTDocumentoIndexVO docIndex =new PbandiTDocumentoIndexVO();
		docIndex.setIdTarget(new BigDecimal(idChecklist));
		docIndex.setIdTipoDocumentoIndex(tipoDocIndex.getIdTipoDocumentoIndex());
		
		
		//se idDoc index == 35 allora devo fare una query diversa
		if(tipoDocIndex.getIdTipoDocumentoIndex().equals(new BigDecimal(35))) {
			PbandiTChecklistVO filter = new PbandiTChecklistVO();
			filter.setIdDichiarazioneSpesa(new BigDecimal(idChecklist));
			List<String> orderByList = new ArrayList<String>();
			orderByList.add("versione");
			filter.setOrderPropertyNamesList(orderByList);
			List <PbandiTChecklistVO> checklistList = (List<PbandiTChecklistVO>) genericDAO.findListWhere(filter);
			PbandiTChecklistVO checklist = checklistList.get(0);
			docIndex.setIdTarget(checklist.getIdChecklist());
		}
			
		listaVO = (ArrayList<PbandiTDocumentoIndexVO>) genericDAO.findListWhere(docIndex);
			
			
		
	
	/*
	if(idDocIndex == null) {			
		else {
			
			PbandiTDocumentoIndexVO docIndex =new PbandiTDocumentoIndexVO();
			docIndex.setIdDocumentoIndex(new BigDecimal(idDocIndex));
			listaVO = (ArrayList<PbandiTDocumentoIndexVO>) genericDAO.findListWhere(docIndex);
			
		}				
	}	
	
	*/
			
		if (listaVO == null || listaVO.size() == 0) {
			logger.info("listaVO null");
			return null;
		}
		
		listaDTO = new FileDTO[listaVO.size()];
		for (int i = 0; i < listaVO.size(); i++) {
			PbandiTDocumentoIndexVO doc = listaVO.get(i);
			
			PbandiTFileVO pbandiTFile = new PbandiTFileVO(); 
			pbandiTFile.setIdDocumentoIndex(doc.getIdDocumentoIndex());
			PbandiTFileVO file = genericDAO.findSingleOrNoneWhere(pbandiTFile);
			
			Long sizeFile = null;
			if (file != null && file.getSizeFile() != null)
				sizeFile = file.getSizeFile().longValue();
			
			FileDTO fileDTO = new FileDTO();
			fileDTO.setIdDocumentoIndex(doc.getIdDocumentoIndex().longValue());
			fileDTO.setNomeFile(doc.getNomeFile());
			fileDTO.setSizeFile(sizeFile);
			
			listaDTO[i] = fileDTO; 
		}
		
		
		
		logger.end();
		return listaDTO;
	}
	
	public FileDTO[] getFilesAssociatedVerbaleChecklist(
			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			java.lang.Long idDocIndex
	)	throws it.csi.csi.wrapper.CSIException, it.csi.csi.wrapper.SystemException,
	it.csi.csi.wrapper.UnrecoverableException,
	it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentazione.GestioneDocumentazioneException
	{
		logger.begin();
		
		String[] nameParameter = { "idUtente", "identitaDigitale","idDocIndex"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idDocIndex);
		logger.info("idDocIndex:"+idDocIndex);
		
		FileDTO[] listaDTO =null;
		ArrayList<PbandiTDocumentoIndexVO> listaVO = null;
		
		PbandiTDocumentoIndexVO docIndex =new PbandiTDocumentoIndexVO();
		docIndex.setIdDocumentoIndex(new BigDecimal(idDocIndex));
		listaVO = (ArrayList<PbandiTDocumentoIndexVO>) genericDAO.findListWhere(docIndex);		
		
			
		if (listaVO == null || listaVO.size() == 0) {
			logger.info("listaVO null");
			return null;
		}
		
		listaDTO = new FileDTO[listaVO.size()];
		for (int i = 0; i < listaVO.size(); i++) {
			PbandiTDocumentoIndexVO doc = listaVO.get(i);
			
			PbandiTFileVO pbandiTFile = new PbandiTFileVO(); 
			pbandiTFile.setIdDocumentoIndex(doc.getIdDocumentoIndex());
			PbandiTFileVO file = genericDAO.findSingleOrNoneWhere(pbandiTFile);
			
			Long sizeFile = null;
			if (file != null && file.getSizeFile() != null)
				sizeFile = file.getSizeFile().longValue();
			
			FileDTO fileDTO = new FileDTO();
			fileDTO.setIdDocumentoIndex(doc.getIdDocumentoIndex().longValue());
			fileDTO.setNomeFile(doc.getNomeFile());
			fileDTO.setSizeFile(sizeFile);
			
			listaDTO[i] = fileDTO; 
		}
		
		
		
		logger.end();
		return listaDTO;
	}
	
	public FileDTO[] getFilesAssociatedVerbaleChecklist(
			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			java.lang.String codTipoDocIndex,
			java.lang.Long idChecklistOld,
			java.lang.Long idDocIndex
	)	throws it.csi.csi.wrapper.CSIException, it.csi.csi.wrapper.SystemException,
	it.csi.csi.wrapper.UnrecoverableException,
	it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentazione.GestioneDocumentazioneException
	{
		logger.begin();
		
		String[] nameParameter = { "idUtente", "identitaDigitale","codTipoDocIndex","idChecklist" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, codTipoDocIndex,idChecklistOld);
		logger.info("codTipoDocIndex:"+codTipoDocIndex+", idChecklist "+idChecklistOld);
		
		
		//Dal codTipoDocIndex recuperiamo l'oggetto interessato
		PbandiCTipoDocumentoIndexVO tipoDocIndex = new PbandiCTipoDocumentoIndexVO();
		tipoDocIndex.setDescBreveTipoDocIndex(codTipoDocIndex);
		tipoDocIndex = genericDAO.findSingleOrNoneWhere(tipoDocIndex);
		if (tipoDocIndex == null || tipoDocIndex.getIdTipoDocumentoIndex() == null) {
			return null;
		}
		BigDecimal idTipoDocumentoIndex = tipoDocIndex.getIdTipoDocumentoIndex();
		
		
		//Ricerca nella PbandiTChecklist
		PbandiTChecklistVO filter = new PbandiTChecklistVO();
		filter.setIdDocumentoIndex(new BigDecimal(idDocIndex));
		PbandiTChecklistVO checklistList = (PbandiTChecklistVO) genericDAO.findSingleOrNoneWhere(filter);
		BigDecimal idChecklist = checklistList.getIdChecklist();
		
		
		
		//set dati per recuperare allegati
		PbandiTDocumentoIndexVO docIndexFilter =new PbandiTDocumentoIndexVO();
		docIndexFilter.setIdTarget(idChecklist);
		docIndexFilter.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
		docIndexFilter.setIdEntita(new BigDecimal(242));
		ArrayList<PbandiTDocumentoIndexVO> listaVO = 
				(ArrayList<PbandiTDocumentoIndexVO>) genericDAO.findListWhere(docIndexFilter);	

		
		
		
		
		
		/*
		
		//Dal codTipoDocIndex recuperiamo l'oggetto interessato
		PbandiCTipoDocumentoIndexVO tipoDocIndex = new PbandiCTipoDocumentoIndexVO();
		tipoDocIndex.setDescBreveTipoDocIndex(codTipoDocIndex);
		tipoDocIndex = genericDAO.findSingleOrNoneWhere(tipoDocIndex);
		if (tipoDocIndex == null || tipoDocIndex.getIdTipoDocumentoIndex() == null) {
			return null;
		}
		
		
		FileDTO[] listaDTO =null;
		ArrayList<PbandiTDocumentoIndexVO> listaVO = null;		
		
			
		//set dati per recuperare allegati
		PbandiTDocumentoIndexVO docIndex =new PbandiTDocumentoIndexVO();
		docIndex.setIdTarget(new BigDecimal(idChecklist));
		docIndex.setIdTipoDocumentoIndex(tipoDocIndex.getIdTipoDocumentoIndex());
		
		
		//se idDoc index == 35 allora devo fare una query diversa
		if(tipoDocIndex.getIdTipoDocumentoIndex().equals(new BigDecimal(35))) {
			PbandiTChecklistVO filter = new PbandiTChecklistVO();
			filter.setIdDichiarazioneSpesa(new BigDecimal(idChecklist));
			List<String> orderByList = new ArrayList<String>();
			orderByList.add("versione");
			filter.setOrderPropertyNamesList(orderByList);
			List <PbandiTChecklistVO> checklistList = (List<PbandiTChecklistVO>) genericDAO.findListWhere(filter);
			PbandiTChecklistVO checklist = checklistList.get(0);
			docIndex.setIdTarget(checklist.getIdChecklist());
		}
			
		listaVO = (ArrayList<PbandiTDocumentoIndexVO>) genericDAO.findListWhere(docIndex);
			
			
		
	
	
	if(idDocIndex == null) {			
		else {
			
			PbandiTDocumentoIndexVO docIndex =new PbandiTDocumentoIndexVO();
			docIndex.setIdDocumentoIndex(new BigDecimal(idDocIndex));
			listaVO = (ArrayList<PbandiTDocumentoIndexVO>) genericDAO.findListWhere(docIndex);
			
		}				
	}	
	
	*/
			
		if (listaVO == null || listaVO.size() == 0) {
			logger.info("listaVO null");
			return null;
		}
		
		FileDTO[] listaDTO =null;
		listaDTO = new FileDTO[listaVO.size()];
		for (int i = 0; i < listaVO.size(); i++) {
			PbandiTDocumentoIndexVO doc = listaVO.get(i);
			
			PbandiTFileVO pbandiTFile = new PbandiTFileVO(); 
			pbandiTFile.setIdDocumentoIndex(doc.getIdDocumentoIndex());
			PbandiTFileVO file = genericDAO.findSingleOrNoneWhere(pbandiTFile);
			
			Long sizeFile = null;
			if (file != null && file.getSizeFile() != null)
				sizeFile = file.getSizeFile().longValue();
			
			FileDTO fileDTO = new FileDTO();
			fileDTO.setIdDocumentoIndex(doc.getIdDocumentoIndex().longValue());
			fileDTO.setNomeFile(doc.getNomeFile());
			fileDTO.setSizeFile(sizeFile);
			
			listaDTO[i] = fileDTO; 
		}
		
		
		
		logger.end();
		return listaDTO;
	}
	
	
	
	
	/*
	 * 
	 * String[] nameParameter = { "idUtente", "identitaDigitale","codTipoDocIndex","idChecklist" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, codTipoDocIndex,idChecklist);
		logger.info("codTipoDocIndex:"+codTipoDocIndex+", idChecklist "+idChecklist);
		
		//Dal codTipoDocIndex recuperiamo l'oggetto interessato
		PbandiCTipoDocumentoIndexVO tipoDocIndex = new PbandiCTipoDocumentoIndexVO();
		tipoDocIndex.setDescBreveTipoDocIndex(codTipoDocIndex);
		tipoDocIndex = genericDAO.findSingleOrNoneWhere(tipoDocIndex);
		if (tipoDocIndex == null || tipoDocIndex.getIdTipoDocumentoIndex() == null) {
			return null;
		}
		
		//set dati per recuperare allegati
		PbandiTDocumentoIndexVO docIndex =new PbandiTDocumentoIndexVO();
		docIndex.setIdTarget(new BigDecimal(idChecklist));
		docIndex.setIdTipoDocumentoIndex(tipoDocIndex.getIdTipoDocumentoIndex());
		ArrayList<PbandiTDocumentoIndexVO> listaVO = null;
		
		FileDTO[] listaDTO =null;
		
		//se idDoc index == 35 allora devo fare una query diversa
		if(tipoDocIndex.getIdTipoDocumentoIndex().equals(new BigDecimal(35))) {
			PbandiTChecklistVO filter = new PbandiTChecklistVO();
			filter.setIdDichiarazioneSpesa(new BigDecimal(idChecklist));
			PbandiTChecklistVO checklist = ((List<PbandiTChecklistVO>) genericDAO.findListWhere(filter)).get(0);
			
			docIndex.setIdTarget(checklist.getIdChecklist());
			
			if (listaVO == null || listaVO.size() == 0) {
				logger.info("listaVO null");
				return null;
			}
			
			listaDTO = new FileDTO[listaVO.size()];
			for (int i = 0; i < listaVO.size(); i++) {
				PbandiTDocumentoIndexVO doc = listaVO.get(i);
				
				PbandiTFileVO pbandiTFile = new PbandiTFileVO(); 
				pbandiTFile.setIdDocumentoIndex(doc.getIdDocumentoIndex());
				PbandiTFileVO file = genericDAO.findSingleOrNoneWhere(pbandiTFile);
				
				Long sizeFile = null;
				if (file != null && file.getSizeFile() != null)
					sizeFile = file.getSizeFile().longValue();
				
				FileDTO fileDTO = new FileDTO();
				fileDTO.setIdDocumentoIndex(doc.getIdDocumentoIndex().longValue());
				fileDTO.setNomeFile(doc.getNomeFile());
				fileDTO.setSizeFile(sizeFile);
				
				listaDTO[i] = fileDTO; 
			}
		}
		
		//altrimenti lacio la qury che era presente in precedenza e funziona per le checklist in loco
		else {						
			
			listaVO = (ArrayList<PbandiTDocumentoIndexVO>) genericDAO.findListWhere(docIndex);
			
			if (listaVO == null || listaVO.size() == 0) {
				logger.info("listaVO null");
				return null;
			}
			
			listaDTO = new FileDTO[listaVO.size()];
			for (int i = 0; i < listaVO.size(); i++) {
				PbandiTDocumentoIndexVO doc = listaVO.get(i);
				
				PbandiTFileVO pbandiTFile = new PbandiTFileVO(); 
				pbandiTFile.setIdDocumentoIndex(doc.getIdDocumentoIndex());
				PbandiTFileVO file = genericDAO.findSingleOrNoneWhere(pbandiTFile);
				
				Long sizeFile = null;
				if (file != null && file.getSizeFile() != null)
					sizeFile = file.getSizeFile().longValue();
				
				FileDTO fileDTO = new FileDTO();
				fileDTO.setIdDocumentoIndex(doc.getIdDocumentoIndex().longValue());
				fileDTO.setNomeFile(doc.getNomeFile());
				fileDTO.setSizeFile(sizeFile);
				
				listaDTO[i] = fileDTO; 
			}
			
		}
	 */
	
	
	/*
	 * logger.begin();
		
		String[] nameParameter = { "idUtente", "identitaDigitale","codTipoDocIndex","idChecklist" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, codTipoDocIndex,idChecklist);
		logger.info("codTipoDocIndex:"+codTipoDocIndex+", idChecklist "+idChecklist);
		PbandiCTipoDocumentoIndexVO tipoDocIndex = new PbandiCTipoDocumentoIndexVO();
		tipoDocIndex.setDescBreveTipoDocIndex(codTipoDocIndex);
		tipoDocIndex = genericDAO.findSingleOrNoneWhere(tipoDocIndex);
		if (tipoDocIndex == null || tipoDocIndex.getIdTipoDocumentoIndex() == null) {
			return null;
		}
		PbandiTDocumentoIndexVO docIndex =new PbandiTDocumentoIndexVO();
		docIndex.setIdTarget(new BigDecimal(idChecklist));
		docIndex.setIdTipoDocumentoIndex(tipoDocIndex.getIdTipoDocumentoIndex());
		ArrayList<PbandiTDocumentoIndexVO> listaVO = (ArrayList<PbandiTDocumentoIndexVO>) genericDAO.findListWhere(docIndex);
		if (listaVO == null || listaVO.size() == 0) {
			logger.info("listaVO null");
			return null;
		}
		
		FileDTO[] listaDTO = new FileDTO[listaVO.size()];
		for (int i = 0; i < listaVO.size(); i++) {
			PbandiTDocumentoIndexVO doc = listaVO.get(i);
			
			PbandiTFileVO pbandiTFile = new PbandiTFileVO(); 
			pbandiTFile.setIdDocumentoIndex(doc.getIdDocumentoIndex());
			PbandiTFileVO file = genericDAO.findSingleOrNoneWhere(pbandiTFile);
			
			Long sizeFile = null;
			if (file != null && file.getSizeFile() != null)
				sizeFile = file.getSizeFile().longValue();
			
			FileDTO fileDTO = new FileDTO();
			fileDTO.setIdDocumentoIndex(doc.getIdDocumentoIndex().longValue());
			fileDTO.setNomeFile(doc.getNomeFile());
			fileDTO.setSizeFile(sizeFile);
			
			listaDTO[i] = fileDTO; 
		}
		logger.end();
		return listaDTO;
	 * 
	 */
	
	public FileDTO[] getFilesAssociatedVerbaleChecklistAffidamento(
			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			java.lang.String codTipoDocIndex,
			java.lang.Long idDocIndex

	)
	throws it.csi.csi.wrapper.CSIException,
	it.csi.csi.wrapper.SystemException,
	it.csi.csi.wrapper.UnrecoverableException
	,
	it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentazione.GestioneDocumentazioneException

	{
		String[] nameParameter = { "idUtente", "identitaDigitale","codTipoDocIndex","idDocIndex" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, codTipoDocIndex,idDocIndex);
		
		logger.info("\n\n#############\ngetFilesAssociatedVerbaleChecklist ,codTipoDocIndex:"+codTipoDocIndex+", idDocIndex "+idDocIndex);
				
		PbandiTChecklistVO vo = new PbandiTChecklistVO();
		vo.setIdDocumentoIndex(new BigDecimal(idDocIndex));
		vo = genericDAO.findSingleWhere(vo);
		
		Long idTarget = vo.getIdChecklist().longValue();
		
		return getFilesAssociatedVerbaleChecklist(idUtente,identitaDigitale,codTipoDocIndex,idTarget,null);
	}

	private FileDTO createDocumentoDTO(ArchivioFileVO file) {
		FileDTO documentoDTO=new FileDTO();
		documentoDTO.setIdDocumentoIndex(file.getIdDocumentoIndex().longValue());
		documentoDTO.setIdProgetto(NumberUtil.toLong(file.getIdProgetto()));
		documentoDTO.setNomeFile(file.getNomeFile());
		documentoDTO.setSizeFile(file.getSizeFile()!=null?file.getSizeFile().longValue():0l);
		return documentoDTO;
	}

	public String findStatoChecklistUltimaVersione(
			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			java.lang.Long idEntita,
			java.lang.Long idTarget,
			java.lang.Long idTipoDocumentoIndex,
			java.lang.Long idProgetto
	)
	throws it.csi.csi.wrapper.CSIException, it.csi.csi.wrapper.SystemException,
	it.csi.csi.wrapper.UnrecoverableException,
	it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentazione.GestioneDocumentazioneException
	{
		String[] nameParameter = { "idUtente", "identitaDigitale","idEntita","idTarget","idTipoDocumentoIndex","idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale,idEntita,idTarget,idTipoDocumentoIndex,idProgetto);		
		logger.info("\n\n#############\nfindStatoChecklistUltimaVersione(): idEntita = "+idEntita+", idTarget = "+idTarget+", idTipoDocumentoIndex = "+idTipoDocumentoIndex+", idProgetto = "+idProgetto);
		
		// Trova l'ultima checklist.
		DocumentoIndexMaxVersioneVO documentoIndexVO = new DocumentoIndexMaxVersioneVO();
		documentoIndexVO.setIdEntita(BigDecimal.valueOf(idEntita));
		documentoIndexVO.setIdTarget(BigDecimal.valueOf(idTarget));
		documentoIndexVO.setIdTipoDocumentoIndex(BigDecimal.valueOf(idTipoDocumentoIndex));
		documentoIndexVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
		documentoIndexVO = genericDAO.findSingleOrNoneWhere(documentoIndexVO);
		
		if (documentoIndexVO == null)
			return "";
		
		// Trova lo stato della checklist
		PbandiDStatoTipoDocIndexVO statoVO = new PbandiDStatoTipoDocIndexVO();
		statoVO.setIdStatoTipoDocIndex(documentoIndexVO.getIdStatoTipoDocIndex());
		statoVO = genericDAO.findSingleOrNoneWhere(statoVO);

		if (statoVO == null)
			return "";
		
		return statoVO.getDescBreveStatoTipDocIndex();
	}
	
}
