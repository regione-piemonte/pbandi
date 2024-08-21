/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.business.checklisthtml;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.CheckListManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ReportManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.EsitoSalvaModuloCheckListDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistAffidamentoHtmlDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.EsitoChecklistAffidamentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.EsitoEliminaCheckListHtmlDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.EsitoSalvaModuloCheckListHtmlDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.FileDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.AllegatoCheckListDocumentaleAffidamentiDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.AllegatoCheckListInLocoAffidamentiDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.AllegatoCheckListInLocoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CheckListDocumentaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ChecklistHtmlAnagraficaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.checklist.CheckListException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.checklisthtml.ChecklistHtmlException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.exception.RecordNotFoundException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.exception.TooMuchRecordFoundException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiChecklistDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.CheckListAppaltoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DocumentoIndexMaxVersioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.VoceDiSpesaConAmmessoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDStatoTipoDocIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRDocuIndexTipoStatoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAffidamentoChecklistVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAppaltoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTChecklistHtmlVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTChecklistVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEsitiNoteAffidamentVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProceduraAggiudicazVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.checklisthtml.ChecklistHtmlSrv;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.ErrorConstants;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.MessaggiConstants;


/**
 */
public class ChecklistHtmlBusinessImpl extends BusinessImpl implements ChecklistHtmlSrv {
	
	@Autowired
	private CheckListManager checkListManager;
	private ContoEconomicoManager contoEconomicoManager;
	
	@Autowired
	private DocumentoManager documentoManager;
	
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	private ProgettoManager progettoManager;
	@Autowired
	private PbandiChecklistDAOImpl pbandiChecklistDAO;
	private ReportManager reportManager;
	

	private static final Map<String, String> MAP_CHECKLIST_IN_LOCO_DTO_TO_ALLEGATO_VERBALE_DTO = new HashMap<String, String>();

	static {
		MAP_CHECKLIST_IN_LOCO_DTO_TO_ALLEGATO_VERBALE_DTO.put("idProgetto",
		"idProgetto");
		MAP_CHECKLIST_IN_LOCO_DTO_TO_ALLEGATO_VERBALE_DTO.put(
				"bytesVerbale", "bytesDocumento");
		MAP_CHECKLIST_IN_LOCO_DTO_TO_ALLEGATO_VERBALE_DTO.put(
				"nomeFile", "nomeFile");
	}

	public CheckListManager getCheckListManager() {
		return checkListManager;
	}

	public ReportManager getReportManager() {
		return reportManager;
	}

	public void setReportManager(ReportManager reportManager) {
		this.reportManager = reportManager;
	}

	public ContoEconomicoManager getContoEconomicoManager() {
		return contoEconomicoManager;
	}

	public void setContoEconomicoManager(ContoEconomicoManager contoEconomicoManager) {
		this.contoEconomicoManager = contoEconomicoManager;
	}

	public void setCheckListManager(CheckListManager checkListManager) {
		this.checkListManager = checkListManager;
	}

	public PbandiChecklistDAOImpl getPbandiChecklistDAO() {
		return pbandiChecklistDAO;
	}

	public void setPbandiChecklistDAO(PbandiChecklistDAOImpl pbandiChecklistDAO) {
		this.pbandiChecklistDAO = pbandiChecklistDAO;
	}
	public DocumentoManager getDocumentoManager() {
		return documentoManager;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setDocumentoManager(DocumentoManager documentoManager) {
		this.documentoManager = documentoManager;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}
	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}


	public it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO getModuloCheckListInLocoHtml (
			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			java.lang.Long idProgetto,
			String soggettoControllore
	)
	throws	it.csi.csi.wrapper.CSIException, it.csi.csi.wrapper.SystemException,
	it.csi.csi.wrapper.UnrecoverableException,
	it.csi.pbandi.pbservizit.pbandisrv.exception.checklisthtml.ChecklistHtmlException
	{
		String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto);
		logger.info("\n\n\n\n\n\n\ngetModuloCheckListInLocoHtml idProgetto "+idProgetto+" , soggettoControllore: "+soggettoControllore);

		// long progrBandoLinea = progettoManager.getIdBandoLinea(idProgetto);
		// logger.info("progrBandoLinea : "+progrBandoLinea);

		BigDecimal idTipoDocumentoIndexCLIL = decodificheManager
		.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,
				GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO);
		BigDecimal idModello=documentoManager.getIdModello(BigDecimal.valueOf(idProgetto),idTipoDocumentoIndexCLIL);
		logger.info("idModello ----------->  "+idModello);
		String checklistHtmlModel =null;
		try {
			checklistHtmlModel = pbandiChecklistDAO
			.getChecklistHtmlModel(idModello);

		} catch (Exception e) {
			logger.error("Lettura modello (id:"+idModello+") fallita per idProgetto "+idProgetto, e);
			throw new ChecklistHtmlException("Modello non configurato");
		}

		ChecklistHtmlAnagraficaDTO checkListHtmlAnagraficaDTO = null;
		try {
			checkListHtmlAnagraficaDTO = checkListManager.loadCheckListHtmlAnagraficaDTO( idProgetto,
					GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO,null,null,null,checklistHtmlModel );
			checkListHtmlAnagraficaDTO.setFirmaResponsabile(soggettoControllore);
		} catch (Exception e) {
			logger.error("Lettura dati anagrafica progetto fallita: " ,e);
			throw new ChecklistHtmlException(e.getMessage());
		} 
		checklistHtmlModel=checkListManager.injectAnagraficaProgetto(checklistHtmlModel,checkListHtmlAnagraficaDTO);
		//@todo recuperare le vds
		VoceDiSpesaConAmmessoVO[] vociDiSpesaConAmmesso = contoEconomicoManager.getVociDiSpesaConAmmesso(idProgetto);
		checklistHtmlModel= checkListManager.injectVociDiSpesa(checklistHtmlModel,vociDiSpesaConAmmesso);
		checklistHtmlModel= checkListManager.injectSezioneAppalti(idUtente, identitaDigitale, GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO, idProgetto, checklistHtmlModel);

		ChecklistHtmlDTO checklistHtmlDTO=new ChecklistHtmlDTO();
		checklistHtmlDTO.setContentHtml(checklistHtmlModel);
		// logger.info("returning checklist html: \n\n"+checklistHtmlModel+"\n\n\n\n\n\n");
		return checklistHtmlDTO;

	}


	/**
	 */
	public it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO getCheckListInLocoHtml (
			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			java.lang.Long idDocumentoIndex
	)
	throws
	it.csi.csi.wrapper.CSIException, it.csi.csi.wrapper.SystemException, it.csi.csi.wrapper.UnrecoverableException
	,it.csi.pbandi.pbservizit.pbandisrv.exception.checklisthtml.ChecklistHtmlException

	{
		String[] nameParameter = { "idUtente", "identitaDigitale",
		"idDocumentoIndex" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idDocumentoIndex);

		logger.info("\n\n\n\n\n\n getCheckListInLocoHtml ,idDocumentoIndex:"+idDocumentoIndex);
		PbandiTDocumentoIndexVO docIndex=new PbandiTDocumentoIndexVO(BigDecimal.valueOf(idDocumentoIndex));
		docIndex= genericDAO.findSingleWhere(docIndex);
		BigDecimal idChecklist=docIndex.getIdTarget();
		logger.info("idChecklist to retrieve ----> "+idChecklist);
		ChecklistHtmlDTO checklistHtmlDTO= new ChecklistHtmlDTO();

		String checklistHtmlCompiled = pbandiChecklistDAO.getChecklistHtmlCompiled( idChecklist);




		ChecklistHtmlAnagraficaDTO checkListHtmlAnagraficaDTO = null;
		try {
			checkListHtmlAnagraficaDTO = checkListManager.loadCheckListHtmlAnagraficaDTO( docIndex.getIdProgetto().longValue(),
					GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO,docIndex.getIdDocumentoIndex().longValue(),
					null,idChecklist,checklistHtmlCompiled );

		} catch (Exception e) {
			logger.error("Lettura dati anagrafica progetto fallita: " ,e);
			throw new ChecklistHtmlException(e.getMessage());
		} 
		checklistHtmlCompiled=checkListManager.injectAnagraficaProgetto(checklistHtmlCompiled,checkListHtmlAnagraficaDTO);




		checklistHtmlDTO.setContentHtml(checklistHtmlCompiled);
		return checklistHtmlDTO;

	}


	/*
	 * GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_BOZZA --> B
	 * GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO --> D
	 * */
	public it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.EsitoSalvaModuloCheckListDTO saveCheckListInLocoHtml (

			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			java.lang.Long idProgetto,
			java.lang.String codStatoTipoDocIndex,
			it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO checkListHtmlDTO
	)
	throws
	it.csi.csi.wrapper.CSIException, it.csi.csi.wrapper.SystemException, it.csi.csi.wrapper.UnrecoverableException
	,it.csi.pbandi.pbservizit.pbandisrv.exception.checklisthtml.ChecklistHtmlException

	{
		logger.info("saveCheckListInLocoHtml ++++");
		logger.info("saveCheckListInLocoHtml: codStatoTipoDocIndex " + codStatoTipoDocIndex);
		logger.info("saveCheckListInLocoHtml: checkListHtmlDTO: getIdChecklist " + checkListHtmlDTO.getIdChecklist());
		logger.info("saveCheckListInLocoHtml: checkListHtmlDTO: getIdDocumentoIndex " + checkListHtmlDTO.getIdDocumentoIndex());
		logger.info("saveCheckListInLocoHtml: checkListHtmlDTO: getIdProgetto " + checkListHtmlDTO.getIdProgetto());
		logger.info("saveCheckListInLocoHtml: checkListHtmlDTO: getSoggettoControllore " + checkListHtmlDTO.getSoggettoControllore());
		//logger.info("saveCheckListInLocoHtml: checkListHtmlDTO: getContentHtml() " + checkListHtmlDTO.getContentHtml());
		logger.info("saveCheckListInLocoHtml: checkListHtmlDTO: getNomeFile " + checkListHtmlDTO.getNomeFile());
		logger.info("saveCheckListInLocoHtml: checkListHtmlDTO: getBytesVerbale() " + checkListHtmlDTO.getBytesVerbale());
		logger.info("saveCheckListInLocoHtml: checkListHtmlDTO: idProgetto " + idProgetto);
		if (checkListHtmlDTO.getAllegati() == null)
			logger.info("saveCheckListInLocoHtml: checkListHtmlDTO: getAllegati() NULLO");
		else
			logger.info("saveCheckListInLocoHtml: checkListHtmlDTO: getAllegati().length " + checkListHtmlDTO.getAllegati().length);

		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto","codStatoTipoDocIndex", "checkListHtmlDTO" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto, checkListHtmlDTO);
		
		logger.info("parametri verificati con successo");
		Boolean result=Boolean.FALSE;
		BigDecimal idChecklist=null;

		BigDecimal idDocumentoIndex = beanUtil.transform(checkListHtmlDTO.getIdDocumentoIndex(), BigDecimal.class);
		PbandiTDocumentoIndexVO documentoIndexVO =null;
		if(idDocumentoIndex!=null){
			documentoIndexVO = new PbandiTDocumentoIndexVO(idDocumentoIndex);
			documentoIndexVO = genericDAO.findSingleOrNoneWhere(documentoIndexVO);
			if(documentoIndexVO!=null) idChecklist=documentoIndexVO.getIdTarget();
		}
		
		//Controllare se e' sif o meno!
		Boolean isProgettoSif = progettoManager.isProgettoSIF(idProgetto);
		logger.info("isProgettoSif = "+isProgettoSif);
		
		BigDecimal idTipoDocumentoIndexCLIL = decodificheManager
		.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,
				GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO);
		logger.info("idTipoDocumentoIndexCLIL = "+idTipoDocumentoIndexCLIL);
		
		BigDecimal idModello=documentoManager.getIdModello(BigDecimal.valueOf(idProgetto), idTipoDocumentoIndexCLIL);
		logger.info("idModello ----------->  "+idModello);
		
		byte[] jasperModel = null;
		try {
			jasperModel = pbandiChecklistDAO.getModelloJasperChecklist(idModello);

		} catch (Exception e) {
			logger.error("Lettura modello (id:"+idModello+") fallita per idProgetto "+idProgetto, e);
			throw new ChecklistHtmlException("Modello non configurato");
		}

		logger.info("template jasperModel caricato ");
		
		byte[] pdfBytes = checkListManager.getChecklistPdfBytes( idProgetto,
				GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO,
				checkListHtmlDTO,null,idChecklist, isProgettoSif, jasperModel);

		logger.info("pdfBytes caricato ");
		
		PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
		progettoVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
		progettoVO = genericDAO.findSingleWhere(progettoVO);

		logger.info("saveCheckListInLocoHtml: progettoVO: getCodiceProgetto " + progettoVO.getCodiceProgetto());
		logger.info("saveCheckListInLocoHtml: progettoVO: getIdProgetto " + progettoVO.getIdProgetto());
		
		EsitoSalvaModuloCheckListDTO esito = new EsitoSalvaModuloCheckListDTO();
		
		try {
			esito = checkListManager.salvaChecklistHtmlInLoco(idUtente, progettoVO,
					pdfBytes,
					checkListHtmlDTO.getContentHtml(),
					codStatoTipoDocIndex,
					documentoIndexVO);
			
			// Multi-upload: ora ci possono essere piu allegati.
			FileDTO[] allegati = checkListHtmlDTO.getAllegati();
			if(allegati != null) {
				logger.info("\n\ncheckListHtmlDTO.getAllegati() != null");

				for (FileDTO fileDTO : allegati) {

					AllegatoCheckListInLocoDTO allegatoCheckListInLocoDTO = beanUtil
					.transform(checkListHtmlDTO,
							AllegatoCheckListInLocoDTO.class,
							MAP_CHECKLIST_IN_LOCO_DTO_TO_ALLEGATO_VERBALE_DTO);
					//allegatoCheckListInLocoDTO.setIdChecklist(idChecklist); commentato poich� sovrascritto sotto.			
					allegatoCheckListInLocoDTO.setIdChecklist(new BigDecimal(esito.getIdChecklist()));
					allegatoCheckListInLocoDTO.setIdProgetto(BigDecimal.valueOf(idProgetto));
					allegatoCheckListInLocoDTO.setCodiceProgetto(progettoVO.getCodiceProgetto());					
					allegatoCheckListInLocoDTO.setBytesDocumento(fileDTO.getBytes());
					allegatoCheckListInLocoDTO.setNomeFile(fileDTO.getNome());
					
					logger.info("\n\n\n\n\n\n\n\n\n\n\n\nbefore documentoManager.creaDocumento for allegatoCheckListInLocoDTO con idChecklist "+idChecklist+" e nomeFile "+allegatoCheckListInLocoDTO.getNomeFile());
					documentoManager.creaDocumento(idUtente, allegatoCheckListInLocoDTO,null,null,null,null);
				}
				
				esito.setEsito(true);
			} else {
				logger.warn("\nnbytesVerbale null");
			}

			// Caso prima del multi-upload, quando si passava 1 solo verbale; mantenuto per eventuali retrocompatibilita'.
			byte[] bytesVerbale = checkListHtmlDTO.getBytesVerbale();
			if(bytesVerbale != null){
				logger.info("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nbytesVerbale != null");

				AllegatoCheckListInLocoDTO allegatoCheckListInLocoDTO = beanUtil
				.transform(checkListHtmlDTO,
						AllegatoCheckListInLocoDTO.class,
						MAP_CHECKLIST_IN_LOCO_DTO_TO_ALLEGATO_VERBALE_DTO);
				allegatoCheckListInLocoDTO.setIdChecklist(idChecklist);
				allegatoCheckListInLocoDTO.setIdProgetto(BigDecimal.valueOf(idProgetto));
				allegatoCheckListInLocoDTO.setCodiceProgetto(progettoVO.getCodiceProgetto());
				allegatoCheckListInLocoDTO.setIdChecklist(new BigDecimal(esito.getIdChecklist()));

				logger.info("\n\n\n\n\n\n\n\n\n\n\n\nbefore documentoManager.creaDocumento for allegatoCheckListInLocoDTO con idChecklist "+idChecklist+" e nomeFile "+allegatoCheckListInLocoDTO.getNomeFile());

				documentoManager.creaDocumento(idUtente, allegatoCheckListInLocoDTO,null,null,null,null);

				esito.setEsito(true);
			}else{
				logger.warn("\nnbytesVerbale null");
			}

		} catch (Exception e) {
			logger.error("Errore  checkListManager.salvaChecklistInLoco ",e);
			throw new ChecklistHtmlException("",e);
		}

		return esito;
	}


	/* versione modificata da Chiesa.
	public it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.EsitoSalvaModuloCheckListDTO saveCheckListInLocoHtml (

			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			java.lang.Long idProgetto,
			java.lang.String codStatoTipoDocIndex,
			it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO checkListHtmlDTO
	)
	throws
	it.csi.csi.wrapper.CSIException, it.csi.csi.wrapper.SystemException, it.csi.csi.wrapper.UnrecoverableException
	,it.csi.pbandi.pbservizit.pbandisrv.exception.checklisthtml.ChecklistHtmlException

	{
		logger.info("saveCheckListInLocoHtml: codStatoTipoDocIndex " + codStatoTipoDocIndex);
		logger.info("saveCheckListInLocoHtml: checkListHtmlDTO: getNomeFile " + checkListHtmlDTO.getNomeFile());
		logger.info("saveCheckListInLocoHtml: checkListHtmlDTO: getIdChecklist " + checkListHtmlDTO.getIdChecklist());
		logger.info("saveCheckListInLocoHtml: checkListHtmlDTO: getIdDocumentoIndex " + checkListHtmlDTO.getIdDocumentoIndex());
		logger.info("saveCheckListInLocoHtml: checkListHtmlDTO: getIdProgetto " + checkListHtmlDTO.getIdProgetto());
		//logger.info("saveCheckListInLocoHtml: checkListHtmlDTO: getContentHtml() " + checkListHtmlDTO.getContentHtml());
		logger.info("saveCheckListInLocoHtml: checkListHtmlDTO: getBytesVerbale() " + checkListHtmlDTO.getBytesVerbale());
		logger.info("saveCheckListInLocoHtml: checkListHtmlDTO: idProgetto " + idProgetto);

		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto","codStatoTipoDocIndex", "checkListHtmlDTO" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto, checkListHtmlDTO);
		logger.info("\n\n\n\n\n\n\n\n\n\n\nsaveCheckListInLocoHtml ++++  idProgetto "+idProgetto+
				" , codStatoTipoDocIndex: "+codStatoTipoDocIndex+
				" , idDocumentoIndex() ++++++ : "+checkListHtmlDTO.getIdDocumentoIndex()+
				" , soggettoControllore: "+checkListHtmlDTO.getSoggettoControllore());


		Boolean result=Boolean.FALSE;
		BigDecimal idChecklist=null;

		BigDecimal idDocumentoIndex = beanUtil.transform(checkListHtmlDTO.getIdDocumentoIndex(), BigDecimal.class);
		PbandiTDocumentoIndexVO documentoIndexVO =null;
		if(idDocumentoIndex!=null){
			documentoIndexVO = new PbandiTDocumentoIndexVO(idDocumentoIndex);
			documentoIndexVO = genericDAO.findSingleOrNoneWhere(documentoIndexVO);
			if(documentoIndexVO!=null) idChecklist=documentoIndexVO.getIdTarget();
		}
		
		//Controllare se e' sif o meno!
		Boolean isProgettoSif = progettoManager.isProgettoSIF(idProgetto);

		BigDecimal idTipoDocumentoIndexCLIL = decodificheManager
		.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,
				GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO);
		BigDecimal idModello=documentoManager.getIdModello(BigDecimal.valueOf(idProgetto), idTipoDocumentoIndexCLIL);
		logger.info("idModello ----------->  "+idModello);
		byte[] jasperModel = null;
		try {
			jasperModel = pbandiChecklistDAO
			.getModelloJasperChecklist(idModello);

		} catch (Exception e) {
			logger.error("Lettura modello (id:"+idModello+") fallita per idProgetto "+idProgetto, e);
			throw new ChecklistHtmlException("Modello non configurato");
		}

		byte[] pdfBytes = checkListManager.getChecklistPdfBytes( idProgetto,
				GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO,
				checkListHtmlDTO,null,idChecklist, isProgettoSif, jasperModel);

		PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
		progettoVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
		progettoVO = genericDAO.findSingleWhere(progettoVO);

		logger.info("saveCheckListInLocoHtml: progettoVO: getCodiceProgetto " + progettoVO.getCodiceProgetto());
		logger.info("saveCheckListInLocoHtml: progettoVO: getIdProgetto " + progettoVO.getIdProgetto());
		
		EsitoSalvaModuloCheckListDTO esito = new EsitoSalvaModuloCheckListDTO();
		try {
			if(checkListHtmlDTO.getIdChecklist() == null)
				esito = checkListManager.salvaChecklistHtmlInLoco(idUtente, progettoVO,
						pdfBytes,
						checkListHtmlDTO.getContentHtml(),
						codStatoTipoDocIndex,
						documentoIndexVO);
			else {
				idChecklist = new BigDecimal(checkListHtmlDTO.getIdChecklist());
				esito.setIdChecklist(idChecklist != null ? idChecklist.longValue() : null);
				esito.setIdDocumentoIndex(idDocumentoIndex != null ? idDocumentoIndex.longValue() : null);
			}

			logger.info("saveCheckListInLocoHtml: progettoVO: getCodiceProgetto2 " + progettoVO.getCodiceProgetto());
			logger.info("saveCheckListInLocoHtml: progettoVO: getIdProgetto2 " + progettoVO.getIdProgetto());
			
			byte[] bytesVerbale = checkListHtmlDTO.getBytesVerbale();

			if(bytesVerbale!=null){
				logger.info("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nbytesVerbale!=null");

				AllegatoCheckListInLocoDTO allegatoCheckListInLocoDTO = beanUtil
				.transform(checkListHtmlDTO,
						AllegatoCheckListInLocoDTO.class,
						MAP_CHECKLIST_IN_LOCO_DTO_TO_ALLEGATO_VERBALE_DTO);
				allegatoCheckListInLocoDTO.setIdChecklist(idChecklist);
				allegatoCheckListInLocoDTO.setIdProgetto(BigDecimal.valueOf(idProgetto));
				allegatoCheckListInLocoDTO.setCodiceProgetto(progettoVO.getCodiceProgetto());
				allegatoCheckListInLocoDTO.setIdChecklist(new BigDecimal(esito.getIdChecklist()));

				logger.info("\n\n\n\n\n\n\n\n\n\n\n\nbefore documentoManager.creaDocumento for allegatoCheckListInLocoDTO con idChecklist "+idChecklist+" e nomeFile "+allegatoCheckListInLocoDTO.getNomeFile());

				documentoManager.creaDocumento(idUtente, allegatoCheckListInLocoDTO,null,null,null,null);

				esito.setEsito(true);
			}else{
				logger.warn("\nnbytesVerbale null");
			}

			//result=esito.getEsito();
		} catch (Exception e) {
			logger.error("Errore  checkListManager.salvaChecklistInLoco ",e);
			throw new ChecklistHtmlException("",e);
		}

		if (codStatoTipoDocIndex.equalsIgnoreCase(GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO)){
		}
		return esito;
	}
	*/



	public EsitoEliminaCheckListHtmlDTO eliminaCheckList(Long idUtente,
			String identitaDigitale, Long idDocumentoIndex) throws CSIException,
			SystemException, UnrecoverableException, ChecklistHtmlException {
		logger.info("\n\n\neliminaCheckList html idDocumentoIndex: "+idDocumentoIndex);
		String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoIndex" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idDocumentoIndex);

		EsitoEliminaCheckListHtmlDTO esitoEliminaCheckList = new EsitoEliminaCheckListHtmlDTO();
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

				//
				//pbandiChecklistDAO.deleteChecklistCompiled(docVO.getIdTarget());

				PbandiTChecklistHtmlVO checklistHtmlVO = new PbandiTChecklistHtmlVO(docVO.getIdTarget());
				genericDAO.delete(checklistHtmlVO);

				pbandiChecklistDAO.deleteChecklistCompiled(docVO.getIdTarget());
				
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

	public ChecklistHtmlDTO getModuloCheckListValidazioneHtml(Long idUtente,
			String identitaDigitale, Long idProgetto, String soggettoControllore,
			Long idDichiarazione)
	throws CSIException, SystemException, UnrecoverableException,
	ChecklistHtmlException {
		logger.info("BEGIN");
		String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto);
		ChecklistHtmlDTO checklistHtmlDTO=new ChecklistHtmlDTO();
		logger.info("\n\n\n\n\n\n\ngetModuloCheckListValidazioneHtml idProgetto "+idProgetto+" , soggettoControllore: "+soggettoControllore);

		BigDecimal idTipoDocChecklistDocumentale = decodificheManager
		.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,
				GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE);

		BigDecimal idModello=documentoManager.getIdModello(BigDecimal.valueOf(idProgetto),idTipoDocChecklistDocumentale);
		logger.info("idModello ----------->  "+idModello);

		String checklistHtmlModel = null;

		BigDecimal idChecklist=null;

		DocumentoIndexMaxVersioneVO docIndex = getDocumentoIndex(idProgetto,
				idDichiarazione, PbandiTDichiarazioneSpesaVO.class, idTipoDocChecklistDocumentale, null);

		Long idDocumentoIndex=null;

		if(docIndex!=null){
			logger.info("docIndex not null");
			idDocumentoIndex= docIndex.getIdDocumentoIndex().longValue();
			idChecklist = getIdChecklist(idDichiarazione,docIndex.getIdDocumentoIndex());
			checklistHtmlDTO.setIdDocumentoIndex(docIndex.getIdDocumentoIndex().longValue());
		} 


		if(idChecklist!=null){
			logger.info("\n\n\ntrovata checklist documentale in bozza con idChecklist: ");
			checklistHtmlModel = pbandiChecklistDAO.getChecklistHtmlCompiled(idChecklist);

			logger.info("checklistHtmlModel="+checklistHtmlModel);
			
			if(checklistHtmlModel == null || StringUtil.isEmpty(checklistHtmlModel)){
				PbandiTChecklistVO checklistVO = new PbandiTChecklistVO();
				checklistVO.setIdChecklist(idChecklist);
				checklistVO = genericDAO.findSingleOrNoneWhere(checklistVO);
				
				logger.info("checklistVO.isModol="+checklistVO.isModol());
				
				if(checklistVO.isModol()){
					ChecklistHtmlDTO checklistHtmlModol = getModuloChecklistHtmlValidazione(idUtente, identitaDigitale, idProgetto, checklistVO.getSoggettoControllore(), idDichiarazione);
					checklistHtmlModel = checklistHtmlModol.getContentHtml();
				}
			}

		}else{

			try {
				checklistHtmlModel = pbandiChecklistDAO.getChecklistHtmlModel(idModello);
			} catch (Exception e) {
				logger.error("Lettura modello (id:"+idModello+") fallita per idProgetto "+idProgetto, e);
				throw new ChecklistHtmlException("Modello non configurato");
			}
			VoceDiSpesaConAmmessoVO[] vociDiSpesaConAmmesso = contoEconomicoManager.getVociDiSpesaConAmmesso(idProgetto);
			checklistHtmlModel = checkListManager.injectVociDiSpesa(checklistHtmlModel,vociDiSpesaConAmmesso);
			checklistHtmlModel = checkListManager.injectSezioneAppalti(idUtente, identitaDigitale, GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE, idProgetto, checklistHtmlModel);
		}

		ChecklistHtmlAnagraficaDTO checkListHtmlAnagraficaDTO = null;
		try {

			checkListHtmlAnagraficaDTO = checkListManager.loadCheckListHtmlAnagraficaDTO( idProgetto,
					GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE,
					idDocumentoIndex,idDichiarazione,idChecklist, checklistHtmlModel );
			if(checkListHtmlAnagraficaDTO.getFirmaResponsabile()==null)
				checkListHtmlAnagraficaDTO.setFirmaResponsabile(soggettoControllore);
		} catch (Exception e) {
			logger.error("Lettura dati anagrafica progetto fallita: " ,e);
			throw new ChecklistHtmlException(e.getMessage());
		} 
		checklistHtmlModel=checkListManager.injectAnagraficaProgetto(checklistHtmlModel,checkListHtmlAnagraficaDTO);
		checkListHtmlAnagraficaDTO.setIdDichiarazione(String.valueOf(idDichiarazione));

		checklistHtmlDTO.setContentHtml(checklistHtmlModel);
		return checklistHtmlDTO;
	}

	private BigDecimal getIdChecklist(Long idDichiarazione, 
			BigDecimal idDocumentoIndex) {
		BigDecimal idChecklist=null;
		PbandiTChecklistVO pbandiTChecklistVO = new PbandiTChecklistVO();
		pbandiTChecklistVO.setIdDichiarazioneSpesa(BigDecimal.valueOf(idDichiarazione));
		pbandiTChecklistVO.setIdDocumentoIndex(idDocumentoIndex);

		List<PbandiTChecklistVO> list = genericDAO.where(Condition.filterBy(pbandiTChecklistVO)).select();

		if (list.size() == 1) {
			idChecklist = list.get(0).getIdChecklist();
		}

		logger.info("idChecklist : "+idChecklist);

		pbandiTChecklistVO.setIdChecklist(idChecklist);
		return idChecklist;
	}

	// Tramite pbandi_t_appalto_checklist trova la checklist associata all'affidamento, 
	// di cui legge il dettaglio in pbandi_t_checklist 
	private BigDecimal getIdChecklistAffidamento(Long idAffidamento, BigDecimal idDocumentoIndex){
		
		CheckListAppaltoVO checkListAppaltoVO = new CheckListAppaltoVO();
		checkListAppaltoVO.setIdAppalto(new BigDecimal(idAffidamento));
		checkListAppaltoVO.setIdDocumentoIndex(idDocumentoIndex);

		List<CheckListAppaltoVO> list = genericDAO.where(Condition.filterBy(checkListAppaltoVO)).select();

		BigDecimal idChecklist = null;
		if (list.size() == 1) {
			idChecklist = list.get(0).getIdChecklist();
		}
		logger.info("idChecklist : "+idChecklist);

		return idChecklist;
	}

	private DocumentoIndexMaxVersioneVO getDocumentoIndex(Long idProgetto,
			Long idTarget, Class<? extends GenericVO> entita, BigDecimal idTipoDocChecklistDocumentale, BigDecimal idStatoTipoDocIndex) {
		DocumentoIndexMaxVersioneVO documentoIndexVO = new DocumentoIndexMaxVersioneVO();
		documentoIndexVO.setIdEntita(documentoManager.getIdEntita(entita));
		documentoIndexVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
		documentoIndexVO.setIdTarget(BigDecimal.valueOf(idTarget));
		documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocChecklistDocumentale);
		documentoIndexVO.setIdStatoTipoDocIndex(idStatoTipoDocIndex);

		DocumentoIndexMaxVersioneVO docIndex = genericDAO.findSingleOrNoneWhere(documentoIndexVO) ;
		return docIndex;
	}


	public EsitoSalvaModuloCheckListHtmlDTO saveCheckListDocumentaleHtml(Long idUtente,
			String identitaDigitale, Long idProgetto, String codStatoTipoDocIndex,
			ChecklistHtmlDTO checklistHtmlDTO,Long  idDichiarazione) throws CSIException,
			SystemException, UnrecoverableException, ChecklistHtmlException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto" ,"codStatoTipoDocIndex","checklistHtmlDTO","idDichiarazione"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto,codStatoTipoDocIndex,checklistHtmlDTO,idDichiarazione);

		EsitoSalvaModuloCheckListHtmlDTO esitoSalvaModuloManager = new EsitoSalvaModuloCheckListHtmlDTO();
		esitoSalvaModuloManager.setEsito(Boolean.FALSE);

		logger.info("\n\n\n\n\n\n\n\nsaveCheckListDocumentaleHtml idProgetto:"+idProgetto+" ,codStatoTipoDocIndex:"+codStatoTipoDocIndex+
				",idDichiarazione:"+idDichiarazione+"\nsearching PbandiTChecklistVO with idDocumentoIndex: " + 
				checklistHtmlDTO.getIdDocumentoIndex()
				+" and idDichiarazione: "+idDichiarazione);


		try {
			esitoSalvaModuloManager = beanUtil.transform(checkListManager
					.saveChecklistValidazioneHtml(idUtente,
							idProgetto,
							codStatoTipoDocIndex,
							idDichiarazione,
							checklistHtmlDTO ), EsitoSalvaModuloCheckListHtmlDTO.class);
		} catch (it.csi.pbandi.pbservizit.pbandisrv.exception.manager.CheckListException e) {
			throw new CheckListException(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
		return esitoSalvaModuloManager;
	}

	public ChecklistHtmlDTO getCheckListValidazioneHtml(Long idUtente,
			String identitaDigitale, Long idDocumentoIndex, Long idDichiarazione,Long idProgetto)
	throws CSIException, SystemException, UnrecoverableException,
	ChecklistHtmlException {

		String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoIndex","idDichiarazione","idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idDocumentoIndex,idDichiarazione,idProgetto);
		logger.info("\n\n\n\n\n\n getCheckListInLocoHtml ,idDocumentoIndex:"+ idDocumentoIndex+" ,idDichiarazione:"+idDichiarazione+" ,idProgetto: "+idProgetto);


		BigDecimal idTarget = BigDecimal.valueOf(idDichiarazione);

		CheckListDocumentaleDTO checkListValidazioneDTO = new CheckListDocumentaleDTO();
		checkListValidazioneDTO.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
		checkListValidazioneDTO.setIdDichiarazioneDiSpesa(idTarget);
		checkListValidazioneDTO.setVersione(checkListManager.getVersioneIniziale());
		try {
			if (checkListManager.isInviataPregressa(checkListValidazioneDTO)) {
				throw new ChecklistHtmlException(
						ErrorConstants.ERRORE_CHECKLIST_NON_MODIFICABILE_PREGRESSO);
			}
		} catch (Exception e) {
			logger.error("Errore checkListManager.isInviataPregressa ", e);
			throw new ChecklistHtmlException(e.getMessage());
		}

		logger.info("********* checklist documentale, checking if existent ");
		PbandiTDocumentoIndexVO documentoIndexVO = getDocumentoManager()
		.getDocumentoIndexSuDb(
				idTarget.longValue(),
				idProgetto,
				GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE,
				new PbandiTDichiarazioneSpesaVO().getTableNameForValueObject());

		PbandiTChecklistVO checklistVO = new PbandiTChecklistVO();
		checklistVO.setIdDichiarazioneSpesa(BigDecimal.valueOf(idDichiarazione));
		checklistVO.setIdDocumentoIndex(documentoIndexVO.getIdDocumentoIndex());
		logger.info("searching PbandiTChecklistVO with idDocumentoIndex: " + documentoIndexVO.getIdDocumentoIndex()
				+" and idDichiarazioneDiSpesa: "+idTarget);
		List<PbandiTChecklistVO> list = genericDAO.where(Condition.filterBy(checklistVO)).select();
		BigDecimal idChecklist = null;

		String checklistHtmlCompiled = null;

		ChecklistHtmlDTO checklistHtmlDTO = new ChecklistHtmlDTO();

		if (list != null && list.size() == 1) {

			checklistVO = list.get(0);

			idChecklist = checklistVO.getIdChecklist();	

			checklistHtmlCompiled = pbandiChecklistDAO
			.getChecklistHtmlCompiled(idChecklist);

			if(checklistHtmlCompiled != null){

				ChecklistHtmlAnagraficaDTO checkListHtmlAnagraficaDTO = null;
				try {
					checkListHtmlAnagraficaDTO = checkListManager
					.loadCheckListHtmlAnagraficaDTO(
							documentoIndexVO.getIdProgetto().longValue(),
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE,
							idDocumentoIndex, idDichiarazione, idChecklist, checklistHtmlCompiled );

				} catch (Exception e) {
					logger.error("Lettura dati anagrafica progetto fallita: ", e);
					throw new ChecklistHtmlException(e.getMessage());
				}
				checklistHtmlCompiled = checkListManager.injectAnagraficaProgetto(
						checklistHtmlCompiled, checkListHtmlAnagraficaDTO);

			}else if(checklistVO.isModol()){
				//Se è modol allora non avrà salvata una checklist html da ricaricare dalla tablle pbandi_t_checklist_html perciò bisogna restituire il modello html vuoto
				ChecklistHtmlDTO checklistHtmlModel = getModuloChecklistHtmlValidazione(idUtente, identitaDigitale, idProgetto, checklistVO.getSoggettoControllore(), idDichiarazione);
				checklistHtmlCompiled = checklistHtmlModel.getContentHtml();
			}

		}

		logger.info("idChecklist to retrieve ----> " + idChecklist);
		checklistHtmlDTO.setContentHtml(checklistHtmlCompiled);
		return checklistHtmlDTO;
	}

	//*********************************************************************************************
	//										AFFIDAMENTI
	//*********************************************************************************************


	// Jira PBANDI-2773: aggiunto isRichiestaIntegrazione e noteRichiestaIntegrazione.
	public EsitoSalvaModuloCheckListHtmlDTO saveCheckListAffidamentoDocumentaleHtml(
			Long idUtente, String identitaDigitale, Long idProgetto,
			String codStatoTipoDocIndex, ChecklistAffidamentoHtmlDTO checklistHtmlDTO,
			Long idAffidamento, Boolean isRichiestaIntegrazione, String noteRichiestaIntegrazione) throws CSIException, SystemException,
			UnrecoverableException, ChecklistHtmlException {
		logger.begin();
		String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto" ,"codStatoTipoDocIndex","checklistHtmlDTO","idAffidamento"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto,codStatoTipoDocIndex,checklistHtmlDTO,idAffidamento);

		EsitoSalvaModuloCheckListHtmlDTO esitoSalvaModuloManager = new EsitoSalvaModuloCheckListHtmlDTO();
		esitoSalvaModuloManager.setEsito(Boolean.FALSE);

		logger.info(" idProgetto:"+idProgetto+", codStatoTipoDocIndex:"+codStatoTipoDocIndex+
				", idDichiarazione:"+idAffidamento+", idDocumentoIndex: " + 
				checklistHtmlDTO.getIdDocumentoIndex()+", idDichiarazione: "+idAffidamento);
		logger.info(" isRichiestaIntegrazione:"+isRichiestaIntegrazione+", noteRichiestaIntegrazione:"+noteRichiestaIntegrazione);
		
		try {
			esitoSalvaModuloManager = beanUtil.transform(
					checkListManager.saveChecklistAffidamentoValidazioneHtml(idUtente,
							idProgetto,
							codStatoTipoDocIndex,
							idAffidamento,
							checklistHtmlDTO,
							isRichiestaIntegrazione,
							noteRichiestaIntegrazione), EsitoSalvaModuloCheckListHtmlDTO.class);
			
			logger.info(" esitoSalvaModuloManager.getMessage="+esitoSalvaModuloManager.getMessage());
			logger.info(" esitoSalvaModuloManager.getEsito="+esitoSalvaModuloManager.getEsito());
			logger.info(" esitoSalvaModuloManager.getIdDocumentoIndex="+esitoSalvaModuloManager.getIdDocumentoIndex());
			
			esitoSalvaModuloManager.setEsito(Boolean.TRUE); //PK TODO : verificare questo TRUE, potrebbe arrivare false da saveChecklistAffidamentoValidazioneHtml
			
			FileDTO[] arrFileDTO = checklistHtmlDTO.getAllegati();
			if(arrFileDTO != null){
				logger.info("saveCheckListAffidamentoDocumentaleHtml(): num file allegati = "+checklistHtmlDTO.getAllegati().length);				
				
				// Recupero l'id affidamento e il nome della checklist.
				BigDecimal idDocumentoIndexBD = new BigDecimal(esitoSalvaModuloManager.getIdDocumentoIndex());
				PbandiTDocumentoIndexVO docIndexVO = new PbandiTDocumentoIndexVO(idDocumentoIndexBD);
				docIndexVO = genericDAO.findSingleOrNoneWhere(docIndexVO);
				String timeChecklist = estraiTimeChecklist(docIndexVO.getNomeFile());
				logger.info("saveCheckListAffidamentoDocumentaleHtml() timeChecklist="+timeChecklist );
				
				String idAppalto = (docIndexVO.getIdTarget() == null) ? "" : ""+docIndexVO.getIdTarget().intValue();
				PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
				progettoVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
				progettoVO = genericDAO.findSingleWhere(progettoVO);
				BigDecimal idProgettoBD = new BigDecimal(idProgetto);
				BigDecimal idChecklist = getIdChecklistAffidamento(idAffidamento, idDocumentoIndexBD);
				int indice = 0;
				for(int i = 0; i < arrFileDTO.length; ++ i) {					
					
					indice = indice + 1;
					String nomeFile = "VCDA_"+(indice)+"_"+idAppalto+"_"+timeChecklist+"_"+arrFileDTO[i].getNome();
					
					AllegatoCheckListDocumentaleAffidamentiDTO allegatoCheckListDocumentaleAffidamentiDTO = new AllegatoCheckListDocumentaleAffidamentiDTO();
					allegatoCheckListDocumentaleAffidamentiDTO.setIdProgetto(idProgettoBD);
					allegatoCheckListDocumentaleAffidamentiDTO.setBytesDocumento(arrFileDTO[i].getBytes());
					allegatoCheckListDocumentaleAffidamentiDTO.setNomeFile(nomeFile);
					allegatoCheckListDocumentaleAffidamentiDTO.setCodiceProgetto(progettoVO.getCodiceProgetto());
					allegatoCheckListDocumentaleAffidamentiDTO.setIdChecklist(idChecklist);
					
					logger.info("before documentoManager.creaDocumento for allegatoCheckListDocumentaleAffidamentiDTO con idChecklist "+idChecklist+"; allegato "+allegatoCheckListDocumentaleAffidamentiDTO.getNomeFile());
//					documentoManager.creaDocumento(idUtente, allegatoCheckListDocumentaleAffidamentiDTO,null,null,null,null);
					//PK salvo verbali su storage
					checkListManager.creaDocumentoCL(idUtente, allegatoCheckListDocumentaleAffidamentiDTO, null, null);
					
				}
			}else{
				logger.warn("checkListHtmlDTO.getAllegati() null");
			}
			
		} catch (it.csi.pbandi.pbservizit.pbandisrv.exception.manager.CheckListException e) {
			throw new CheckListException(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
		logger.end();
		return esitoSalvaModuloManager;
	}



	public ChecklistHtmlDTO getModuloCheckListAffidamentoHtml(Long idUtente,
			String identitaDigitale, Long idAffidamento, Long idProgetto,
			String soggettoControllore, String codTipoChecklist)
	throws CSIException, SystemException, UnrecoverableException,
	ChecklistHtmlException {
		
		logger.begin();
		
		String[] nameParameter = { "idUtente", "identitaDigitale","idAffidamento" , "codTipoChecklist"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale,idAffidamento,codTipoChecklist);

		logger.info(" idAffidamento="+idAffidamento+"; idProgetto="+idProgetto+"; soggettoControllore="+soggettoControllore+"; codTipoChecklist="+codTipoChecklist);
		
		ChecklistHtmlDTO checklistHtmlDTO = new ChecklistHtmlDTO();

		checklistHtmlDTO.setIdProgetto(idProgetto);
		checklistHtmlDTO.setSoggettoControllore(soggettoControllore);
		
		try{
			PbandiTAppaltoVO appalto = new PbandiTAppaltoVO(new BigDecimal(idAffidamento));
			appalto = genericDAO.findSingleOrNoneWhere(appalto);

			if(appalto == null)
				throw new ChecklistHtmlException("Nessun appalto trovato per l'appalto : " + idAffidamento);

			if(appalto.getIdProceduraAggiudicaz() == null)
				throw new ChecklistHtmlException("Nessuna procedura di aggiudicazione trovata per l'appalto : " + idAffidamento);

			PbandiTProceduraAggiudicazVO proceduraAggiudicaz = new PbandiTProceduraAggiudicazVO(appalto.getIdProceduraAggiudicaz());
			proceduraAggiudicaz.setIdProceduraAggiudicaz(appalto.getIdProceduraAggiudicaz());
			proceduraAggiudicaz = genericDAO.findSingleOrNoneWhere(proceduraAggiudicaz);

			PbandiTAffidamentoChecklistVO affidamentoChecklist = new PbandiTAffidamentoChecklistVO();

			affidamentoChecklist.setIdNorma(appalto.getIdNorma());
			affidamentoChecklist.setIdTipoAffidamento(appalto.getIdTipoAffidamento());
			affidamentoChecklist.setIdTipoAppalto(appalto.getIdTipologiaAppalto());
			affidamentoChecklist.setIdTipologiaAggiudicaz(proceduraAggiudicaz.getIdTipologiaAggiudicaz());
			affidamentoChecklist.setSopraSoglia(appalto.getSopraSoglia());
			
			// recupero il valore id_linea_di_intervento partendo dal idProgetto
			BigDecimal idLineaDInt = pbandiChecklistDAO.getIdLineaDiIntervento(idProgetto);
			logger.info(" idLineaDInt="+idLineaDInt);
			affidamentoChecklist.setIdLineaDiIntervento(idLineaDInt); 

			try{
				affidamentoChecklist = genericDAO.findSingleOrNoneWhere(affidamentoChecklist);
				if(affidamentoChecklist==null) {
					logger.info(" affidamentoChecklist NULL per idLineaDInt="+idLineaDInt);
					// ricerco con idLineaDInt = NULL
					affidamentoChecklist = new PbandiTAffidamentoChecklistVO();
					affidamentoChecklist.setIdNorma(appalto.getIdNorma());
					affidamentoChecklist.setIdTipoAffidamento(appalto.getIdTipoAffidamento());
					affidamentoChecklist.setIdTipoAppalto(appalto.getIdTipologiaAppalto());
					affidamentoChecklist.setIdTipologiaAggiudicaz(proceduraAggiudicaz.getIdTipologiaAggiudicaz());
					affidamentoChecklist.setSopraSoglia(appalto.getSopraSoglia());

					 Condition<PbandiTAffidamentoChecklistVO> condition = new AndCondition<PbandiTAffidamentoChecklistVO>(
							 Condition.filterBy(affidamentoChecklist),
							 Condition.isFieldNull(PbandiTAffidamentoChecklistVO.class, "idLineaDiIntervento"));
					 
					 affidamentoChecklist = genericDAO.where(condition).selectSingle();
					 logger.info(" affidamentoChecklist="+affidamentoChecklist);
				}
			}catch(Exception ex){
				logger.error(": Lettura modello (id:"+ idAffidamento +") fallita per idProgetto "+idProgetto, ex);
				throw new ChecklistHtmlException("Modello non configurato");
			}

			// codTipoChecklist: affidamento documentale (CLA) oppure affidamento in loco (CLILA)
			BigDecimal idTipoDocChecklist = decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,codTipoChecklist);

			String checklistHtmlContent = null;
			BigDecimal idChecklist = null;
			Long idDocumentoIndex = null;

			BigDecimal idStatoTipoDocumentoIndexBozza = decodificheManager.decodeDescBreve(PbandiDStatoTipoDocIndexVO.class, GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_BOZZA);

			DocumentoIndexMaxVersioneVO docIndex = getDocumentoIndex(idProgetto,
					idAffidamento, PbandiTAppaltoVO.class, idTipoDocChecklist, null);
			
			// Se la checklist affidamento in loco trovata e' una definitiva, devo creare una nuova checklist.
			if (Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_IN_LOCO.equalsIgnoreCase(codTipoChecklist) &&				// Jira Pbandi-2859
				docIndex != null && docIndex.getIdStatoTipoDocIndex() != null) {								
				int id1 = idStatoTipoDocumentoIndexBozza.intValue();
				int id2 = docIndex.getIdStatoTipoDocIndex().intValue();
				if (id1 != id2) {
					logger.info(" ultima checklist e' definitiva: ne apro una nuova.");
					docIndex = null;
				}
			}
			
			if(docIndex != null){
				logger.info(" trovato documento index: IdDocumentoIndex() = "+docIndex.getIdDocumentoIndex());
				
				/* codice precedente alla Jira PBANDI-2829
				boolean flagEsisteEsitoDefinitivo = false;
				//Ignoro le bozze e prendo solo 
				if(docIndex.getIdStatoTipoDocIndex().compareTo(idStatoTipoDocumentoIndex) != 0 && idTipoDocChecklistDocumentale.compareTo(Constants.ID_TIPO_DOCUMENTO_INDEX_CHECKLIST_VALIDAZIONE) == 0){
					PbandiTEsitiNoteAffidamentVO filtroEsiti = new PbandiTEsitiNoteAffidamentVO();
					filtroEsiti.setIdChecklist(getIdChecklistAffidamento(idAffidamento, docIndex.getIdDocumentoIndex()));
					ArrayList<PbandiTEsitiNoteAffidamentVO> esiti = (ArrayList<PbandiTEsitiNoteAffidamentVO>)genericDAO.findListWhere(filtroEsiti);
					if(esiti != null && !esiti.isEmpty()){
						for (PbandiTEsitiNoteAffidamentVO esito : esiti) {
							if(esito.getFase().compareTo(new BigDecimal(Constants.ID_ESITO_CHECKLIST_AFFIDAMENTO_DEFINITIVO)) == 0){
								flagEsisteEsitoDefinitivo = true;
							}
						}
					}
					
				}
				if(!flagEsisteEsitoDefinitivo){
					idDocumentoIndex= docIndex.getIdDocumentoIndex().longValue();
					idChecklist = getIdChecklistAffidamento(idAffidamento, docIndex.getIdDocumentoIndex());
					checklistHtmlDTO.setIdDocumentoIndex(docIndex.getIdDocumentoIndex().longValue());
				}
				fine codice precedente alla Jira PBANDI-2829 */
				
				// Jira PBANDI-2829: codice che sostituisce quello commentato sopra.
				idDocumentoIndex= docIndex.getIdDocumentoIndex().longValue();
				idChecklist = getIdChecklistAffidamento(idAffidamento, docIndex.getIdDocumentoIndex());
				checklistHtmlDTO.setIdDocumentoIndex(docIndex.getIdDocumentoIndex().longValue());
				
				// Jira PBANDI-2849.
				checklistHtmlDTO.setNomeFile(docIndex.getNomeFile());
			}

			
			
			if(idChecklist != null){
				
				// Legge l'html della checklist compilato dall'utente e salvato sul db.
				logger.info("\n\ntrovata checklist in bozza con idChecklist: "+idChecklist+"\n\n");
				checklistHtmlContent = pbandiChecklistDAO.getChecklistHtmlCompiled(idChecklist);

				checklistHtmlDTO.setIdChecklist(idChecklist.longValue());
				
			}else{

				logger.info("\n\nnessuna checklist trovata; carico l'html vuoto per una nuova checklist (PBANDI_C_CHECKLIST_MODEL_HTML).\n\n");
				try{
					if (Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_DOCUMENTALE		// Jira Pbandi-2859
							.equals(codTipoChecklist)){
						checklistHtmlContent = pbandiChecklistDAO.getChecklistHtmlModel(affidamentoChecklist.getIdModelloCd());
					} else {
						checklistHtmlContent = pbandiChecklistDAO.getChecklistHtmlModel(affidamentoChecklist.getIdModelloCl());
					}
				}catch(Exception ex){
					logger.error("Lettura modello (id:"+affidamentoChecklist.getIdModelloCd()+") fallita per idAffidamento "+ idAffidamento, ex);
					throw new ChecklistHtmlException("Modello non configurato");
				}
			}

			logger.info("checklistHtmlContent="+checklistHtmlContent);
			
			ChecklistHtmlAnagraficaDTO checkListHtmlAnagraficaDTO = null;
			try {

				checkListHtmlAnagraficaDTO = checkListManager.loadCheckListHtmlAnagraficaDTO( idProgetto, null,
						idDocumentoIndex,null,idChecklist, checklistHtmlContent);
				if(checkListHtmlAnagraficaDTO.getFirmaResponsabile()==null)
					checkListHtmlAnagraficaDTO.setFirmaResponsabile(soggettoControllore);

				checkListManager.caricaDatiAffidamento(checkListHtmlAnagraficaDTO, idAffidamento);
				
				//aggiungo il CIG e il CPA
				//JIRA PBANDI-2798
				if(proceduraAggiudicaz != null){
					checkListHtmlAnagraficaDTO.setCodProcAgg(proceduraAggiudicaz.getCodProcAgg());
					checkListHtmlAnagraficaDTO.setCigProcAgg(proceduraAggiudicaz.getCigProcAgg());
				}
				


			} catch (Exception e) {
				logger.error("Lettura dati anagrafica progetto fallita: " ,e);
				throw new ChecklistHtmlException(e.getMessage());
			} 
			checklistHtmlContent = checkListManager.injectAnagraficaProgetto(checklistHtmlContent,checkListHtmlAnagraficaDTO);

			checklistHtmlDTO.setContentHtml(checklistHtmlContent);
			
			logger.info("checklistHtmlContent="+checklistHtmlContent);

			// Jira PBANDI-2829: cerco eventuali esiti associati alla checklist
			//                   (serve per le checklist documentali degli affidamenti).
			logger.info("\n\nCerco gli esiti della checklist "+idChecklist+"\n\n");
			if (idChecklist != null) {

				// Cerca l'eventuale esito intermedio.
				PbandiTEsitiNoteAffidamentVO esitoFase1 = new PbandiTEsitiNoteAffidamentVO();
				esitoFase1.setIdChecklist(idChecklist);
				esitoFase1.setFase(new BigDecimal(1));
				esitoFase1 = genericDAO.findSingleOrNoneWhere(esitoFase1);
				if (esitoFase1 != null && esitoFase1.getIdEsito() != null) {
					checklistHtmlDTO.setEsitoIntermedio(beanUtil.transform(esitoFase1, EsitoChecklistAffidamentoDTO.class));
					logger.info("\n\nId esito intermedio = "+checklistHtmlDTO.getEsitoIntermedio().getIdEsito()+"\n\n");
				} else {
					logger.info("\n\nId esito intermedio non trovato.\n\n");
				}
				
				// Cerca l'eventuale esito definitivo.
				PbandiTEsitiNoteAffidamentVO esitoFase2 = new PbandiTEsitiNoteAffidamentVO();
				esitoFase2.setIdChecklist(idChecklist);
				esitoFase2.setFase(new BigDecimal(2));
				esitoFase2 = genericDAO.findSingleOrNoneWhere(esitoFase2);
				if (esitoFase2 != null && esitoFase2.getIdEsito() != null) {
					checklistHtmlDTO.setEsitoDefinitivo(beanUtil.transform(esitoFase2, EsitoChecklistAffidamentoDTO.class));
					logger.info("\n\nId esito definitivo = "+checklistHtmlDTO.getEsitoDefinitivo().getIdEsito()+"\n\n");		
				} else {
					logger.info("\n\nId esito definitivo non trovato.\n\n");
				}
			}			

		}catch(TooMuchRecordFoundException tmrfe){
			logger.error("[GetModuloCheckListAffidamento] errore durante l'esecuzione del servizio getModuloCheckListAffidamentoInLocoHtml: ", tmrfe);
			throw new ChecklistHtmlException("[GetModuloCheckListAffidamento] errore durante l'esecuzione del servizio getModuloCheckListAffidamentoInLocoHtml: ",tmrfe);
		}catch(Exception e){
			logger.error("[GetModuloCheckListAffidamento] errore durante l'esecuzione del servizio getModuloCheckListAffidamentoInLocoHtml: ", e);
			throw new ChecklistHtmlException("[GetModuloCheckListAffidamento] errore durante l'esecuzione del servizio getModuloCheckListAffidamentoInLocoHtml: ",e);
		}

		logger.debug("checklistHtmlDTO="+checklistHtmlDTO);
		
		
		logger.end();
		return checklistHtmlDTO;
	}


	/*
	 * GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_BOZZA --> B
	 * GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO --> D
	 */
	/*
	 * Per il muilti-upload degli allegati delle checklist in loco affidamenti,
	 * ho aggiunto l'attributo ChecklistAffidamentoHtmlDTO.affidamenti
	 * da usare al posto di bytesVerbale usato quando c'era 1 solo allegato.
	 */
	public EsitoSalvaModuloCheckListHtmlDTO saveCheckListAffidamentoInLocoHtml(
			Long idUtente, String identitaDigitale, Long idProgetto,
			String codStatoTipoDocIndex,
			ChecklistAffidamentoHtmlDTO checkListHtmlDTO, Long idAffidamento)
	throws CSIException, SystemException, UnrecoverableException,
	ChecklistHtmlException {

		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto","codStatoTipoDocIndex", "checkListHtmlDTO" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto, checkListHtmlDTO);
	
		logger.info("saveCheckListAffidamentoInLocoHtml ++++  idProgetto "+idProgetto+
				" , codStatoTipoDocIndex: "+codStatoTipoDocIndex+
				" , idDocumentoIndex() ++++++ : "+checkListHtmlDTO.getIdDocumentoIndex()+
				" , soggettoControllore: "+checkListHtmlDTO.getSoggettoControllore());
		if (checkListHtmlDTO.getAllegati() == null)
			logger.info("saveCheckListAffidamentoInLocoHtml(): checkListHtmlDTO.getAllegati() == null");
		else 
			logger.info("saveCheckListAffidamentoInLocoHtml(): checkListHtmlDTO.getAllegati() = "+checkListHtmlDTO.getAllegati().length);

		Boolean result = Boolean.FALSE;
		BigDecimal idChecklist=null;

		BigDecimal idDocumentoIndex = beanUtil.transform(checkListHtmlDTO.getIdDocumentoIndex(), BigDecimal.class);
		logger.info("saveCheckListAffidamentoInLocoHtml(), idDocumentoIndex= "+ idDocumentoIndex);
				
		PbandiTDocumentoIndexVO documentoIndexVO = null;
		if(idDocumentoIndex != null){
			documentoIndexVO = new PbandiTDocumentoIndexVO(idDocumentoIndex);
			documentoIndexVO = genericDAO.findSingleOrNoneWhere(documentoIndexVO);
			if(documentoIndexVO!=null){ 
				idChecklist = getIdChecklistAffidamento(idAffidamento, documentoIndexVO.getIdDocumentoIndex());
				checkListHtmlDTO.setIdChecklist(idChecklist.longValue());
			}
		}

		// genera il pdf della checklist
		byte[] pdfBytes = checkListManager.getChecklistAffidamentoPdfBytes( idProgetto,
				Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_IN_LOCO,		// Jira Pbandi-2859
				checkListHtmlDTO,idAffidamento,idChecklist, null);

		PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
		progettoVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
		progettoVO = genericDAO.findSingleWhere(progettoVO);
		
		// Determina la VERSIONE da assegnare alla checklist (contenuta in PBANDI_T_DOCUMENTO_INDEX e PBANDI_T_CHECKLIST).
		BigDecimal versione = calcolaVersioneChecklistAffidamentoInLoco(idProgetto, idAffidamento);
		logger.info("saveCheckListAffidamentoInLocoHtml(), versione= "+ versione);
		
		EsitoSalvaModuloCheckListDTO esito =null;
		try {
			
			esito = checkListManager.salvaChecklistAffidamentoInLoco(idUtente, progettoVO,
					pdfBytes,
					checkListHtmlDTO,
					codStatoTipoDocIndex,
					documentoIndexVO, idAffidamento,
					versione);
			
			FileDTO[] arrFileDTO = checkListHtmlDTO.getAllegati();
			BigDecimal idProgettoBD = new BigDecimal(idProgetto);
			if(arrFileDTO != null){
				logger.info("saveCheckListAffidamentoInLocoHtml(): num file allegati = "+checkListHtmlDTO.getAllegati().length);				
				
				// Recupero l'id affidamento e il nome della checklist.
				BigDecimal idDocumentoIndexBD = new BigDecimal(esito.getIdDocumentoIndex());
				PbandiTDocumentoIndexVO docIndexVO = new PbandiTDocumentoIndexVO(idDocumentoIndexBD);
				docIndexVO = genericDAO.findSingleOrNoneWhere(docIndexVO);
				String timeChecklist = estraiTimeChecklist(docIndexVO.getNomeFile());
				logger.info("saveCheckListAffidamentoInLocoHtml() timeChecklist="+timeChecklist );
				
				String idAppalto = (docIndexVO.getIdTarget() == null) ? "" : ""+docIndexVO.getIdTarget().intValue();
				
				int indice = 0;
				for(int i = 0; i < arrFileDTO.length; ++ i) {					
					
					indice = indice + 1;
					String nomeFile = "VCLA_"+(indice)+"_"+idAppalto+"_"+timeChecklist+"_"+arrFileDTO[i].getNome();
					
					AllegatoCheckListInLocoAffidamentiDTO allegatoCheckListInLocoAffidamentiDTO = new AllegatoCheckListInLocoAffidamentiDTO();
					allegatoCheckListInLocoAffidamentiDTO.setIdProgetto(idProgettoBD);
					allegatoCheckListInLocoAffidamentiDTO.setBytesDocumento(arrFileDTO[i].getBytes());
					allegatoCheckListInLocoAffidamentiDTO.setNomeFile(nomeFile);
					allegatoCheckListInLocoAffidamentiDTO.setCodiceProgetto(progettoVO.getCodiceProgetto());
					allegatoCheckListInLocoAffidamentiDTO.setIdChecklist(new BigDecimal(esito.getIdChecklist()));
					
					logger.info("before documentoManager.creaDocumento for allegatoCheckListInLocoAffidamentiDTO con idChecklist "+idChecklist+"; allegato "+allegatoCheckListInLocoAffidamentiDTO.getNomeFile());
//					documentoManager.creaDocumento(idUtente, allegatoCheckListInLocoAffidamentiDTO,null,null,null,null);
					//PK salvo verbali su storage
					checkListManager.creaDocumentoCL(idUtente, allegatoCheckListInLocoAffidamentiDTO, null, null);
					
				}
			}else{
				logger.warn("checkListHtmlDTO.getAllegati() null");
			}
		
			/* versione vecchia che gestiva 1 solo allegato.
			byte[] bytesVerbale = checkListHtmlDTO.getBytesVerbale();
			*/
			
			result=esito.getEsito();
		} catch (Exception e) {
			logger.error("Errore  checkListManager.saveCheckListAffidamentoInLocoHtml ",e);
			throw new ChecklistHtmlException("",e);
		}


		if (codStatoTipoDocIndex.equalsIgnoreCase(GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO)){

			/*logger.info("stato chklist DEFINITIVO, inserisco allegato verbale"); 
			 * AllegatoCheckListInLocoDTO allegatoCheckListInLocoDTO = beanUtil
					.transform(checkListInLoco,
							AllegatoCheckListInLocoDTO.class,
							MAP_CHECKLIST_IN_LOCO_DTO_TO_ALLEGATO_VERBALE_DTO);



			allegatoCheckListInLocoDTO.setCodiceProgetto(progettoVO
					.getCodiceProgetto());
			allegatoCheckListInLocoDTO
					.setIdChecklist(esitoSalvaModuloManager.getIdChecklist());

			documentoManager.creaDocumento(idUtente, allegatoCheckListInLocoDTO,null,null,null,null);*/
		}
		return beanUtil.transform(esito, EsitoSalvaModuloCheckListHtmlDTO.class);
	}
	
	// Estrae "04022020111722" da "checkListAffidamento_3930_V1_04022020111722.pdf"
	private String estraiTimeChecklist(String nomeFileChecklist) {		
		String nomeSenzaEstensione = FilenameUtils.removeExtension(nomeFileChecklist);
		int index = nomeSenzaEstensione.lastIndexOf("_");
		String time = nomeSenzaEstensione.substring(index+1);
		return time;
	}
	
	private BigDecimal calcolaVersioneChecklistAffidamentoInLoco(Long idProgetto, Long idAffidamento) {
		BigDecimal idTipoDocChecklistInLoco = decodificheManager
		.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,
				Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_IN_LOCO);		// Jira Pbandi-2859
		DocumentoIndexMaxVersioneVO docIndexMaxVersione = getDocumentoIndex(idProgetto,
				idAffidamento, PbandiTAppaltoVO.class, idTipoDocChecklistInLoco, null);
		if (docIndexMaxVersione == null || docIndexMaxVersione.getIdDocumentoIndex() == null) {
			// .
			logger.info("calcolaVersioneChecklistAffidamentoInLoco(): Nessuna checklist affidamento in loco precedente: nuovaVersione = 1.");
			return new BigDecimal(1);
		} else {
			BigDecimal idStatoTipoDocIndexBozza = decodificheManager
			.decodeDescBreve(PbandiDStatoTipoDocIndexVO.class,
					GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_BOZZA);
			int intIdStatoBozza = idStatoTipoDocIndexBozza.intValue();
			int intIdStatoChecklist = docIndexMaxVersione.getIdStatoTipoDocIndex().intValue();
			BigDecimal nuovaVersione = null;
			if (intIdStatoBozza == intIdStatoChecklist) {
				// L'ultima checklist inserita su db � una bozza: lascio la versione trovata.
				if (docIndexMaxVersione.getVersione() == null) {					
					nuovaVersione = new BigDecimal(1);
				} else {
					nuovaVersione = docIndexMaxVersione.getVersione();
				}
				logger.info("calcolaVersioneChecklistAffidamentoInLoco(): bozza: versione da "+docIndexMaxVersione.getVersione()+" a "+nuovaVersione.intValue());
			} else {
				// L'ultima checklist inserita su db � una definitiva: aumento la versione di 1.
				if (docIndexMaxVersione.getVersione() == null) {
					nuovaVersione = new BigDecimal(1);
				} else {
					nuovaVersione = new BigDecimal(docIndexMaxVersione.getVersione().intValue() + 1);
				}
				logger.info("calcolaVersioneChecklistAffidamentoInLoco(): definitiva: versione da "+docIndexMaxVersione.getVersione()+" a "+nuovaVersione.intValue());
			}			
			return nuovaVersione;
		}
	}

	private ChecklistHtmlDTO getModuloChecklistHtmlValidazione(Long idUtente, String identitaDigitale, Long idProgetto, String soggettoControllore, Long idDichiarazione) throws ChecklistHtmlException {

		ChecklistHtmlDTO checklistHtmlDTO = new ChecklistHtmlDTO();

		BigDecimal idTipoDocChecklistDocumentale = decodificheManager
		.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,
				GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE);

		BigDecimal idModello=documentoManager.getIdModello(BigDecimal.valueOf(idProgetto),idTipoDocChecklistDocumentale);
		logger.info("idModello ----------->  "+idModello);

		String checklistHtmlModel = null;

		BigDecimal idChecklist=null;

		DocumentoIndexMaxVersioneVO docIndex = getDocumentoIndex(idProgetto,
				idDichiarazione, PbandiTDichiarazioneSpesaVO.class, idTipoDocChecklistDocumentale, null);

		Long idDocumentoIndex=null;

		if(docIndex!=null){
			//		idDocumentoIndex= docIndex.getIdDocumentoIndex().longValue();
			//		idChecklist = getIdChecklist(idDichiarazione,docIndex.getIdDocumentoIndex());
			checklistHtmlDTO.setIdDocumentoIndex(docIndex.getIdDocumentoIndex().longValue());
		}

		try {
			checklistHtmlModel = pbandiChecklistDAO.getChecklistHtmlModel(idModello);
		} catch (Exception e) {
			logger.error("Lettura modello (id:"+idModello+") fallita per idProgetto "+idProgetto, e);
			throw new ChecklistHtmlException("Modello non configurato");
		}
		VoceDiSpesaConAmmessoVO[] vociDiSpesaConAmmesso = contoEconomicoManager.getVociDiSpesaConAmmesso(idProgetto);
		checklistHtmlModel = checkListManager.injectVociDiSpesa(checklistHtmlModel,vociDiSpesaConAmmesso);
		checklistHtmlModel = checkListManager.injectSezioneAppalti(idUtente, identitaDigitale, GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE, idProgetto, checklistHtmlModel);

		ChecklistHtmlAnagraficaDTO checkListHtmlAnagraficaDTO = null;
		try {

			checkListHtmlAnagraficaDTO = checkListManager.loadCheckListHtmlAnagraficaDTO( idProgetto,
					GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE,
					idDocumentoIndex,idDichiarazione,idChecklist, checklistHtmlModel );
			if(checkListHtmlAnagraficaDTO.getFirmaResponsabile()==null)
				checkListHtmlAnagraficaDTO.setFirmaResponsabile(soggettoControllore);
		} catch (Exception e) {
			logger.error("Lettura dati anagrafica progetto fallita: " ,e);
			throw new ChecklistHtmlException(e.getMessage());
		} 
		checklistHtmlModel=checkListManager.injectAnagraficaProgetto(checklistHtmlModel,checkListHtmlAnagraficaDTO);
		checkListHtmlAnagraficaDTO.setIdDichiarazione(String.valueOf(idDichiarazione));

		checklistHtmlDTO.setContentHtml(checklistHtmlModel);
		
		return checklistHtmlDTO;
	}

}
