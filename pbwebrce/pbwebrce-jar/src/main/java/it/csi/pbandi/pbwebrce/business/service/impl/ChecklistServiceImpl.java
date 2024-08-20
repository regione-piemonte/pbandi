/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.service.impl;


import java.io.File;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.exception.DaoException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.business.checklist.CheckListBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.checklisthtml.ChecklistHtmlBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.EsitoLockCheckListDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.EsitoUnLockCheckListDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.FileDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistAffidamentoHtmlDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistAffidamentoHtmlDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.EsitoSalvaModuloCheckListHtmlDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Entita;
import it.csi.pbandi.pbservizit.pbandisrv.exception.checklisthtml.ChecklistHtmlException;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.MessaggiConstants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebrce.business.service.ChecklistService;
import it.csi.pbandi.pbwebrce.dto.EsitoOperazioni;
import it.csi.pbandi.pbwebrce.integration.request.SalvaCheckListAffidamentoDocumentaleHtmlRequest;
import it.csi.pbandi.pbwebrce.util.Constants;

@Service
public class ChecklistServiceImpl implements ChecklistService {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	ChecklistHtmlBusinessImpl checklistHtmlBusinessImpl;
	
	@Autowired
	CheckListBusinessImpl checkListBusinessImpl;
	
	@Autowired
	GestioneDatiDiDominioBusinessImpl gestioneDatiDiDominioBusinessImpl;
	
	@Override
	public Response getModelloCheckListAffidamentoHtml(HttpServletRequest req, Long idProgetto, Long idAffidamento,
			String soggettoControllore, String codTipoChecklist) 
					throws UtenteException, Exception {
		
		String prf = "[ChecklistServiceImpl::getModelloCheckListAffidamentoHtml]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + " idProgetto="+idProgetto);
		LOG.debug(prf + " idAffidamento="+idAffidamento);
		LOG.debug(prf + " soggettoControllore="+soggettoControllore);
		LOG.debug(prf + " codTipoChecklist="+codTipoChecklist);
		
		if(idProgetto == null) return inviaErroreBadRequest("Parametero mancato ?[idProgetto]");
		if(idAffidamento == null) return inviaErroreBadRequest("Parametero mancato ?[idAffidamento]");
		
		ChecklistHtmlDTO checklistHtmlDTO = null;
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
		
			LOG.debug(prf + " idUtente="+idUtente);
			LOG.debug(prf + " idIride="+idIride);
			
			
			try {
				checklistHtmlDTO = checklistHtmlBusinessImpl.getModuloCheckListAffidamentoHtml(idUtente, idIride, idAffidamento, idProgetto, soggettoControllore, codTipoChecklist);
				
				LOG.debug(prf + " checklistHtmlDTO="+checklistHtmlDTO);
				if(checklistHtmlDTO!=null) {
					LOG.debug(prf + " checklistHtmlDTO.IdChecklist="+checklistHtmlDTO.getIdChecklist());
				}else {
					LOG.debug(prf + " checklistHtmlDTO NULLA");
				}
				
				//TODO PK : annullo... poi vediamo
//				checklistHtmlDTO.setAllegati(null);
//				checklistHtmlDTO.setBytesVerbale(null);
//				checklistHtmlDTO.setEsitoDefinitivo(null);
//				checklistHtmlDTO.setEsitoIntermedio(null);
//				checklistHtmlDTO.setIdProgetto(idProgetto);
				
			} catch (ChecklistHtmlException e) {
				LOG.error(prf + "ChecklistHtmlException " + e.getMessage());
				checkListBusinessImpl.eliminaLockCheckListAffidamentoValidazione(idUtente, idIride, idProgetto, idAffidamento);
				throw e;
			} catch (SystemException e) {
				LOG.error(prf + "SystemException " + e.getMessage());
				checkListBusinessImpl.eliminaLockCheckListAffidamentoValidazione(idUtente, idIride, idProgetto, idAffidamento);
				throw e;
			} catch (UnrecoverableException e) {
				LOG.error(prf + "UnrecoverableException " + e.getMessage());
				checkListBusinessImpl.eliminaLockCheckListAffidamentoValidazione(idUtente, idIride, idProgetto, idAffidamento);
				throw e;
			} catch (CSIException e) {
				LOG.error(prf + "CSIException " + e.getMessage());
				checkListBusinessImpl.eliminaLockCheckListAffidamentoValidazione(idUtente, idIride, idProgetto, idAffidamento);
				throw e;
			}
		
		} catch (Exception e) {
			LOG.error(prf+"Exception e "+e.getMessage());
			//e.printStackTrace();
			throw e;
		}
		return Response.ok().entity(checklistHtmlDTO).build();
	}



	@Override
	public Response salvaLockCheckListValidazione(HttpServletRequest req, Long idProgetto, Long idAffidamento)
			throws UtenteException, Exception {
		String prf = "[ChecklistServiceImpl::salvaLockCheckListValidazione]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + " idProgetto="+idProgetto);
		LOG.debug(prf + " idAffidamento="+idAffidamento);
		
		EsitoLockCheckListDTO esitoLockCheckListDTO = new EsitoLockCheckListDTO();
		
		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
	
		LOG.debug(prf + " idUtente="+idUtente);
		LOG.debug(prf + " idIride="+idIride);
		
		try {
			esitoLockCheckListDTO = checkListBusinessImpl.salvaLockCheckListAffidamentoValidazione(idUtente, idIride, idProgetto, idAffidamento);
		
		}catch(Exception e) {
			esitoLockCheckListDTO.setEsito(false);
			esitoLockCheckListDTO.setCodiceMessaggio(MessaggiConstants.KEY_CHECKLIST_LOCCATA);
		}
		
		LOG.debug(prf + " END");
		return Response.ok().entity(esitoLockCheckListDTO).build();
	}
	
	@Override
	public Response salvaCheckListAffidamentoInLocoHtml (MultipartFormDataInput multipartFormData) throws UtenteException, Exception {
		String prf = "[ChecklistServiceImpl::salvaCheckListAffidamentoInLocoHtml]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + " multipartFormData="+multipartFormData);
		if (multipartFormData == null) {
			throw new InvalidParameterException("multipartFormData non valorizzato.");
		}
				
		// PK 
		// parte da  : pbandiwebrce.action.custom.DettaglioAffidamentoCustomAction::salvaCheckListDefinitivoInLoco]
		// arriva in : presa da pbandiwebrce -> CPBEChecklistAffidamentoHtml -> salvaCheckListDefinitivo
	
		Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
		List<InputPart> files = map.get("file");
		
		Long idUtente = multipartFormData.getFormDataPart("idUtente", Long.class, null);
		String idIride = multipartFormData.getFormDataPart("idIride", String.class, null);
		
		LOG.debug(prf + " idUtente="+idUtente);
		LOG.debug(prf + " idIride="+idIride);
		
		Long idProgetto = multipartFormData.getFormDataPart("idProgetto", Long.class, null);
		String codStatoTipoDocIndex = multipartFormData.getFormDataPart("codStatoTipoDocIndex", String.class, null);
		Long idAffidamento = multipartFormData.getFormDataPart("idAffidamento", Long.class, null);
		
		LOG.debug(prf + " idProgetto="+idProgetto);
		LOG.debug(prf + " idAffidamento="+idAffidamento);
		LOG.debug(prf + " codStatoTipoDocIndex="+codStatoTipoDocIndex);
		
		ChecklistAffidamentoHtmlDTO checklistHtmlDTO = new ChecklistAffidamentoHtmlDTO();
		
//		checklistHtmlDTO.setBytesVerbale(null); // non gestisco BytesVerbale
		checklistHtmlDTO.setCodStatoTipoDocIndex(multipartFormData.getFormDataPart("checkListcodStatoTipoDocIndex", String.class, null));
		checklistHtmlDTO.setContentHtml(multipartFormData.getFormDataPart("checkListcontentHtml", String.class, null));
		checklistHtmlDTO.setFasiHtml(multipartFormData.getFormDataPart("checkListfasiHtml", String.class, null));
		checklistHtmlDTO.setIdChecklist(multipartFormData.getFormDataPart("checkListidChecklist", Long.class, null));
		checklistHtmlDTO.setIdDocumentoIndex(multipartFormData.getFormDataPart("checkListidDocumentoIndex", Long.class, null));
		checklistHtmlDTO.setIdProgetto(multipartFormData.getFormDataPart("checkListidProgetto", Long.class, null));
		checklistHtmlDTO.setSoggettoControllore(multipartFormData.getFormDataPart("checkListsoggettoControllore", String.class, null));
		
		EsitoSalvaModuloCheckListHtmlDTO esitoOK = null;
		try {
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.FileDTO[] verbali = leggiFilesDaMultipart(files);	
		
			if(verbali== null || verbali.length == 0) {
				throw new Exception("Nessun file allegato.");
			}else {
				LOG.debug(prf + " verbali.length="+verbali.length);
			}
			checklistHtmlDTO.setAllegati(verbali);
			
			esitoOK = checklistHtmlBusinessImpl.saveCheckListAffidamentoInLocoHtml(idUtente,  
					idIride, idProgetto,
					codStatoTipoDocIndex, //PK in questo caso deve valere D, lo imposto da Angular
					checklistHtmlDTO,
					idAffidamento);
			
			return Response.ok().entity(esitoOK).build();
			
		}catch(Exception e) {
			
			EsitoUnLockCheckListDTO unlock= checkListBusinessImpl.eliminaLockCheckListAffidamentoValidazione(idUtente, idIride, 
					idProgetto, idAffidamento);
			
			LOG.debug(prf + " unlock="+unlock);
			LOG.error(prf + "Errore ex="+e.getMessage());
			throw e;
			
		}finally {
			LOG.debug(prf + " END");
		}
	}
	
	private ArrayList<FileDTO> leggiFilesDaMultipart(List<InputPart> files, Long idFolder) throws DaoException {
		String prf = "[DocumentoManager::leggiFilesDaMultipart] ";
		LOG.info(prf + " BEGIN");
		ArrayList<FileDTO> result = new ArrayList<FileDTO>();
		try {

			String nomeFile = null;
			String mimeType = null;
			byte[] content;
			MultivaluedMap<String, String> header;
			
			for (InputPart inputPart : files) {
				
				header = inputPart.getHeaders();				
				nomeFile = getFileName(header);
				content = IOUtils.toByteArray(inputPart.getBody(InputStream.class, null));
								
				FileDTO fileDTO = new FileDTO();
				fileDTO.setBytes(content);
				fileDTO.setNomeFile(nomeFile);				
				fileDTO.setSizeFile(new Long(content.length));
				fileDTO.setIdFolder(idFolder);	
				LOG.info(prf+"file estratto: nome = " + fileDTO.getNomeFile()+"; size = "+fileDTO.getSizeFile());
			
				result.add(fileDTO);
			}
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante la lettura dei file da MultipartFormDataInput.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	}



	@Override
	public Response salvaCheckListAffidamentoDocumentaleHtml(HttpServletRequest req,
			MultipartFormDataInput multipartFormData) throws UtenteException, Exception {
		
		String prf = "[ChecklistServiceImpl::salvaCheckListAffidamentoDocumentaleHtml]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + " multipartFormData="+multipartFormData);
		if (multipartFormData == null) {
			throw new InvalidParameterException("multipartFormData non valorizzato.");
		}
				
		Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
		List<InputPart> files = map.get("file");
		
		Long idProgetto = multipartFormData.getFormDataPart("idProgetto", Long.class, null);
		String codStatoTipoDocIndex = multipartFormData.getFormDataPart("codStatoTipoDocIndex", String.class, null);
		Long idAffidamento = multipartFormData.getFormDataPart("idAffidamento", Long.class, null);
		
		LOG.debug(prf + " idProgetto="+idProgetto);
		LOG.debug(prf + " idAffidamento="+idAffidamento);
		LOG.debug(prf + " codStatoTipoDocIndex="+codStatoTipoDocIndex);
		
		ChecklistAffidamentoHtmlDTO checklistHtmlDTO = new ChecklistAffidamentoHtmlDTO();
		

		checklistHtmlDTO.setCodStatoTipoDocIndex(multipartFormData.getFormDataPart("checkListcodStatoTipoDocIndex", String.class, null));
		checklistHtmlDTO.setContentHtml(multipartFormData.getFormDataPart("checkListcontentHtml", String.class, null));
		checklistHtmlDTO.setFasiHtml(multipartFormData.getFormDataPart("checkListfasiHtml", String.class, null));
		checklistHtmlDTO.setIdChecklist(multipartFormData.getFormDataPart("checkListidChecklist", Long.class, null));
		checklistHtmlDTO.setIdDocumentoIndex(multipartFormData.getFormDataPart("checkListidDocumentoIndex", Long.class, null));
		checklistHtmlDTO.setIdProgetto(multipartFormData.getFormDataPart("checkListidProgetto", Long.class, null));
		checklistHtmlDTO.setSoggettoControllore(multipartFormData.getFormDataPart("checkListsoggettoControllore", String.class, null));
		String codTipoChecklist = multipartFormData.getFormDataPart("codTipoChecklist", String.class, null);
		LOG.debug(prf + " codTipoChecklist="+codTipoChecklist);
		Boolean isRichiestaIntegrazione = multipartFormData.getFormDataPart("isRichiestaIntegrazione", Boolean.class, null);
		LOG.debug(prf + " isRichiestaIntegrazione="+isRichiestaIntegrazione);
		String noteRichiestaIntegrazione = multipartFormData.getFormDataPart("noteRichiestaIntegrazione", String.class, null);
		LOG.debug(prf + " noteRichiestaIntegrazione="+noteRichiestaIntegrazione);
		
		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
	
		LOG.debug(prf + " idUtente="+idUtente);
		LOG.debug(prf + " idIride="+idIride);
		
		//PK : presa da pbandiwebrce -> CPBEChecklistAffidamentoHtml -> salvaCheckListDefinitivo
	
		EsitoSalvaModuloCheckListHtmlDTO esitoOK = null;
		try {
			if(files != null) {
				it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.FileDTO[] verbali = leggiFilesDaMultipart(files);	
				
				if(verbali== null || verbali.length == 0) {
					LOG.debug(prf + " verbali.length=0");
				}else {
					LOG.debug(prf + " verbali.length="+verbali.length);
				}
				checklistHtmlDTO.setAllegati(verbali);
			}
			//--------------------------------------------------------------------
			if (codTipoChecklist
					.equalsIgnoreCase(Constants.COD_TIPO_CHEKCLIST_IN_LOCO)) {
				
				/*
				 *File[] verbali = getVerbale();
					if (verbali == null || verbali.length == 0) {
						throw new Exception("Nessun file allegato.");
					}

					String nomiVerbali = getNomiVerbaliFromSession();
					if (nomiVerbali == null || nomiVerbali.length() == 0) {
						throw new Exception("Nessun nome degli allegati.");
					}

					String[] arrNomiVerbali = nomiVerbali.split(",");
					if (arrNomiVerbali == null || arrNomiVerbali.length == 0) {
						throw new Exception(
								"Errata lettura dei nomi degli allegati.");
					}

					if (verbali.length != arrNomiVerbali.length) {
						throw new Exception("Numero diverso di allegati ("
								+ verbali.length + ") e nomi file ("
								+ arrNomiVerbali.length + ").");
					}
					for (int i = 0; i < verbali.length; ++i) {
						log.info("salvaCheckListDefinitivo(): file " + i
								+ " : nome = " + arrNomiVerbali[i]
								+ "; size = " + verbali[i].length());
					}

					// Crea l'elenco dei file da passare al servizio lato server.
					FileDTO[] arrFileDTO = new FileDTO[verbali.length];
					for (int i = 0; i < verbali.length; ++i) {
						FileDTO fileDTO = new FileDTO();
						fileDTO.setBytes(FileUtils
								.readFileToByteArray(verbali[i]));
						fileDTO.setNome(arrNomiVerbali[i]);
						arrFileDTO[i] = fileDTO;
					}
					checklistHtmlDTO.setAllegati(arrFileDTO);
 
				 */
				
//				salvachechlistaffReq.getChecklistHtmlDTO().getBytesVerbale();
				
				LOG.debug(prf + " saveCheckListAffidamentoInLocoHtml da invocare");
				
				esitoOK = checklistHtmlBusinessImpl.saveCheckListAffidamentoInLocoHtml(idUtente,  
						idIride, idProgetto,
						checklistHtmlDTO.getCodStatoTipoDocIndex(),
						checklistHtmlDTO,
						idAffidamento);
				
			} else {
				
				LOG.debug(prf + " saveCheckListAffidamentoDocumentaleHtml da invocare");
				
				esitoOK = checklistHtmlBusinessImpl.saveCheckListAffidamentoDocumentaleHtml(idUtente, 
						idIride, idProgetto,
						checklistHtmlDTO.getCodStatoTipoDocIndex(),
						checklistHtmlDTO,
						idAffidamento, 
						isRichiestaIntegrazione, 
						noteRichiestaIntegrazione);
				
				
				
				
			}
			
			
			return Response.ok().entity(esitoOK).build();
			
		}catch(Exception e) {
			
			EsitoUnLockCheckListDTO unlock= checkListBusinessImpl.eliminaLockCheckListAffidamentoValidazione(idUtente, idIride, 
					idProgetto, idAffidamento);
			
			LOG.debug(prf + " unlock="+unlock);
			LOG.error(prf + "Errore ex="+e.getMessage());
			throw e;
		}
		
	}
	
	
	public File[] getVerbale() throws Exception {

		File[] files = null;

		return files;
	}
	
	
	
	
	public Response salvaBozzaCheckListAffidamentoDocumentaleHtml(HttpServletRequest req,
			SalvaCheckListAffidamentoDocumentaleHtmlRequest salvachechlistaffReq) throws UtenteException, Exception {
		
		String prf = "[ChecklistServiceImpl::salvaBozzaCheckListAffidamentoDocumentaleHtml]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + " salvachechlistaffReq="+salvachechlistaffReq);
		
		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
	
		LOG.debug(prf + " idUtente="+idUtente);
		LOG.debug(prf + " idIride="+idIride);
		
		EsitoSalvaModuloCheckListHtmlDTO esitoOK = new EsitoSalvaModuloCheckListHtmlDTO();
		esitoOK.setEsito(false);
		
		try {
			LOG.info(prf + "salvachechlistaffReq.getCodStatoTipoDocIndex() = "+salvachechlistaffReq.getCodStatoTipoDocIndex());
			String codStatoTipoDocIndex = GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_BOZZA;
			
			if (salvachechlistaffReq.getCodTipoChecklist()
					.equalsIgnoreCase(Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_IN_LOCO)) {
				
//				LOG.debug(prf + " 	TODO: da implementare");
				
				esitoOK = checklistHtmlBusinessImpl.saveCheckListAffidamentoInLocoHtml(idUtente,  
						idIride, salvachechlistaffReq.getIdProgetto(),
						codStatoTipoDocIndex,
						salvachechlistaffReq.getChecklistHtmlDTO(),
						salvachechlistaffReq.getIdAffidamento());
				
			}
			else {
				esitoOK = checklistHtmlBusinessImpl.saveCheckListAffidamentoDocumentaleHtml(idUtente,  
						idIride, salvachechlistaffReq.getIdProgetto(),
						codStatoTipoDocIndex,
						salvachechlistaffReq.getChecklistHtmlDTO(),
						salvachechlistaffReq.getIdAffidamento(), 
						salvachechlistaffReq.getIsRichiestaIntegrazione(), 
						salvachechlistaffReq.getNoteRichiestaIntegrazione());
			}
			
			
			
			
			LOG.debug(prf + " salvaBozzaCheckListAffidamentoDocumentaleHtml invocata");
//			return Response.ok().entity(esitoOK).build();
			
		}catch(Exception e) {
			
			EsitoUnLockCheckListDTO unlock= checkListBusinessImpl.eliminaLockCheckListAffidamentoValidazione(idUtente, idIride, 
					salvachechlistaffReq.getIdProgetto(), salvachechlistaffReq.getIdAffidamento());
			
			LOG.debug(prf + " unlock="+unlock);
			LOG.error(prf + "Errore ex="+e.getMessage());
//			throw e;
		}
		
		
		if (esitoOK.getEsito()) {
			
			// Ricarica il modello, altrimenti vengono visualizzati i dati precedenti al salvataggio.
//			caricaModelloChecklist(theModel);   -- probabilmente nel nuovo non serve

			// Rimette il lock per evitare che 2 salvataggi bozza consecutivi 
			// provochino l'errore "Impossibile salvare, lock scaduto"
			
			
			// provochino l'errore "Impossibile salvare, lock scaduto"
			try {
				
				if(Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_IN_LOCO.equalsIgnoreCase(salvachechlistaffReq.getCodTipoChecklist())) {
					checkListBusinessImpl.salvaLockCheckListValidazione(idUtente, idIride, salvachechlistaffReq.getIdProgetto(), salvachechlistaffReq.getIdAffidamento());
				}

				return Response.ok().entity(esitoOK).build();
				
			} catch (Exception e) {
				LOG.error(prf + "ERRORE: " +e.getMessage());
				throw e;
			}
			
		}else {
			LOG.error(prf + "Si &egrave; verificato un errore durante il salvataggio della checklist.");
			throw new Exception("Si &egrave; verificato un errore durante il salvataggio della checklist.");
		}
		
		
	}
	
	
	
	public Response salvaChecklistInBozzaIntegrazioneAffidamentoDocumentaleHtml(HttpServletRequest req,
			SalvaCheckListAffidamentoDocumentaleHtmlRequest salvachechlistaffReq) throws UtenteException, Exception {
		
		String prf = "[ChecklistServiceImpl::salvaChecklistInBozzaIntegrazioneAffidamentoDocumentaleHtml]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + " salvachechlistaffReq="+salvachechlistaffReq);
		
		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
	
		LOG.debug(prf + " idUtente="+idUtente);
		LOG.debug(prf + " idIride="+idIride);
		
		EsitoSalvaModuloCheckListHtmlDTO esitoOK = new EsitoSalvaModuloCheckListHtmlDTO();
		esitoOK.setEsito(false);
		
		try {
			
			String codStatoTipoDocIndex = GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_BOZZA;
			LOG.info(prf + "salvachechlistaffReq.getCodStatoTipoDocIndex() = "+salvachechlistaffReq.getCodStatoTipoDocIndex());
			boolean isRichiestaIntegrazione = true;
			LOG.info(prf + "salvachechlistaffReq.getIsRichiestaIntegrazione() = "+salvachechlistaffReq.getIsRichiestaIntegrazione());
			esitoOK = checklistHtmlBusinessImpl.saveCheckListAffidamentoDocumentaleHtml(idUtente,  
					idIride, salvachechlistaffReq.getIdProgetto(),
					codStatoTipoDocIndex,
					salvachechlistaffReq.getChecklistHtmlDTO(),
					salvachechlistaffReq.getIdAffidamento(), 
					isRichiestaIntegrazione, 
					salvachechlistaffReq.getNoteRichiestaIntegrazione());

			LOG.debug(prf + "salvaCheckListBozza(): esito = " + esitoOK.getEsito()
			+ "; msg = " + esitoOK.getMessage());
			
			
		}catch(Exception e) {

			LOG.error(prf + "Errore ex="+e.getMessage());
			throw e;
		}
		
		return Response.ok().entity(esitoOK).build();
		
	}
	
	
	@Override
	public Response salvaCheckListAffidamentoDocumentaleHtmlBozza(HttpServletRequest req, MultipartFormDataInput multipartFormData)
			throws Exception {
		String prf = "[ChecklistServiceImpl::salvaCheckListAffidamentoDocumentaleHtmlBozza]";
		LOG.debug(prf + " BEGIN");
		
		// PK rispetto l'analoga funzione "salvaCheckListAffidamentoDocumentaleHtml"
		// - non considera il verbale allegato
		// - risetta il lock
		
		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
	
		LOG.debug(prf + " idUtente="+idUtente);
		LOG.debug(prf + " idIride="+idIride);
		
		//PK : pbandiwebrce -> CPBEChecklistAffidamentoHtml.salvaCheckListBozza 
		
		if (multipartFormData == null) {
			throw new Exception("multipartFormData non valorizzato.");
		}
		
		Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
		List<InputPart> files = map.get("file");
		
		String codTipoChecklist = multipartFormData.getFormDataPart("codTipoChecklist", String.class, null);
		LOG.debug(prf + " codTipoChecklist="+codTipoChecklist);
		
		Long idProgetto = multipartFormData.getFormDataPart("idProgetto", Long.class, null);
		LOG.debug(prf + " idProgetto="+idProgetto);
		
		String codStatoTipoDocIndex = multipartFormData.getFormDataPart("codStatoTipoDocIndex", String.class, null);
		LOG.debug(prf + " codStatoTipoDocIndex="+codStatoTipoDocIndex);
		
		String checkListCodStatoTipoDocIndex = multipartFormData.getFormDataPart("checkListCodStatoTipoDocIndex", String.class, null);
		LOG.debug(prf + " checkListCodStatoTipoDocIndex="+checkListCodStatoTipoDocIndex);
		
		Long idAffidamento = multipartFormData.getFormDataPart("idAffidamento", Long.class, null);
		LOG.debug(prf + " idAffidamento="+idAffidamento);
		
		Long checkListIdDocumentoIndex = multipartFormData.getFormDataPart("checkListIdDocumentoIndex", Long.class, null);
		LOG.debug(prf + " checkListIdDocumentoIndex="+checkListIdDocumentoIndex);

		String checkListBytesVerbale = multipartFormData.getFormDataPart("checkListBytesVerbale", String.class, null);
		LOG.debug(prf + " checkListBytesVerbale="+checkListBytesVerbale);
		
		String checkListContentHtml = multipartFormData.getFormDataPart("checkListContentHtml", String.class, null);
//		LOG.debug(prf + " checkListContentHtml="+checkListContentHtml);
		
		String checkListFasiHtml = multipartFormData.getFormDataPart("checkListFasiHtml", String.class, null);
//		LOG.debug(prf + " checkListFasiHtml="+checkListFasiHtml);
		
		Long checkListIdChecklist = multipartFormData.getFormDataPart("checkListIdChecklist", Long.class, null);
		LOG.debug(prf + " checkListIdChecklist="+checkListIdChecklist);
		
		Long checkListIdProgetto = multipartFormData.getFormDataPart("checkListIdProgetto", Long.class, null);
		LOG.debug(prf + " checkListIdProgetto="+checkListIdProgetto);
		
		
		String checkListSoggettoControllore = multipartFormData.getFormDataPart("checkListSoggettoControllore", String.class, null);
		LOG.debug(prf + " checkListSoggettoControllore="+checkListSoggettoControllore);
		
		EsitoSalvaModuloCheckListHtmlDTO esitoOK = null;
		ChecklistAffidamentoHtmlDTO checklistHtmlDTO = new ChecklistAffidamentoHtmlDTO();
		
//		checklistHtmlDTO.setBytesVerbale(null); //checkListBytesVerbale
		checklistHtmlDTO.setCodStatoTipoDocIndex(checkListCodStatoTipoDocIndex);
		checklistHtmlDTO.setContentHtml(checkListContentHtml);
		checklistHtmlDTO.setFasiHtml(checkListFasiHtml);
		checklistHtmlDTO.setIdChecklist(checkListIdChecklist);
		checklistHtmlDTO.setIdDocumentoIndex(checkListIdDocumentoIndex);
		checklistHtmlDTO.setIdProgetto(checkListIdProgetto);
		checklistHtmlDTO.setSoggettoControllore(checkListSoggettoControllore);
		
		// Estae i file dal multipart.
//		ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.FileDTO> filesDTO = null;
		it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.FileDTO[] filesArr = null;
		
		if(files!=null) {
			filesArr = leggiFilesDaMultipart(files);
		
//			if(filesDTO!=null && !filesDTO.isEmpty()) {
//				LOG.debug(prf + " numero allegati filesDTO = "+filesDTO.size());
//				filesArr =(it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.FileDTO[]) filesDTO.toArray(new it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.FileDTO[0]);
//			}
			
			LOG.debug(prf + " numero allegati filesArr = "+filesArr.length);
		}else {
			LOG.debug(prf + " nessun file passato nel Multipart");
		}
		checklistHtmlDTO.setAllegati(filesArr);
		
		try {
			
			//--------------------------------------------------------------------
			if (Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_IN_LOCO.equalsIgnoreCase(codTipoChecklist)) {
				
				LOG.debug(prf + " checklist 1 CLILA");
				
			/*
			 * PK : passo di qui quando faccio "Gestione Affidamenti" > "Esamina Affidamento" > "Checklist in loco"
			 */
				
				esitoOK = checklistHtmlBusinessImpl.saveCheckListAffidamentoInLocoHtml(idUtente,  
						idIride, idProgetto,
						codStatoTipoDocIndex,
						checklistHtmlDTO,
						idAffidamento);
				
			}else {
				
				LOG.debug(prf + " checklist 2 "+codTipoChecklist);

		        Boolean isRichiestaIntegrazione = multipartFormData.getFormDataPart("isRichiestaIntegrazione", Boolean.class, null);
				LOG.debug(prf + " isRichiestaIntegrazione="+isRichiestaIntegrazione);
				
				String noteRichiestaIntegrazione = multipartFormData.getFormDataPart("noteRichiestaIntegrazione", String.class, null);
				LOG.debug(prf + " noteRichiestaIntegrazione="+noteRichiestaIntegrazione);
					
				/**
				 * boolean isRichiestaIntegrazione = false;
				  String noteRichiestaIntegrazione = null;
				
				 * esito = gestioneAffidamentiBusiness
							.salvaCheckListAffidamentoDocumentaleHtml(theModel
									.getAppDatacurrentUser(), idProgetto,
									codStatoTipoDocIndex, checklistHtmlDTO,
									idAffidamento, isRichiestaIntegrazione,
									noteRichiestaIntegrazione);
				 */
				esitoOK = checklistHtmlBusinessImpl.saveCheckListAffidamentoDocumentaleHtml(idUtente, 
						idIride, idProgetto,
						codStatoTipoDocIndex,
						checklistHtmlDTO,
						idAffidamento, 
						isRichiestaIntegrazione, 
						noteRichiestaIntegrazione);
				
				
				LOG.debug(prf + " saveCheckListAffidamentoDocumentaleHtml invocata");
				
			}
			
			if (esitoOK.getEsito()) {

				// Ricarica il modello, altrimenti vengono visualizzati i dati precedenti al salvataggio.
				//caricaModelloChecklist(theModel);

				// Rimette il lock per evitare che 2 salvataggi bozza consecutivi 
				// provochino l'errore "Impossibile salvare, lock scaduto"
				
				try {
					
					if (Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_IN_LOCO.equalsIgnoreCase(codTipoChecklist)) {
						checkListBusinessImpl.salvaLockCheckListValidazione(idUtente, idIride, idProgetto, idAffidamento);
					}

					return Response.ok().entity(esitoOK).build();
					
				} catch (Exception e) {
					LOG.error(prf + "ERRORE: " +e.getMessage());
					throw e;
				}
			}else {
				LOG.error(prf + "Si &egrave; verificato un errore durante il salvataggio della bozza della checklist.");
				throw new Exception("Si &egrave; verificato un errore durante il salvataggio della checklist.");
			}
			
		}catch(Exception e) {
			
			EsitoUnLockCheckListDTO unlock= checkListBusinessImpl.eliminaLockCheckListAffidamentoValidazione(idUtente, idIride, 
					idProgetto, idAffidamento);
			
			LOG.debug(prf + " unlock="+unlock);
			LOG.error(prf + "Errore ex="+e.getMessage());
			throw e;
			
		}finally {
			LOG.debug(prf + " END");
		}

	}
	
	/*
	@Override
	public Response salvaCheckListAffidamentoDocumentaleHtmlBozza(HttpServletRequest req,
			SalvaCheckListAffidamentoDocumentaleHtmlRequest salvachechlistaffReq) throws UtenteException, Exception {
		
		String prf = "[ChecklistServiceImpl::salvaCheckListAffidamentoDocumentaleHtmlBozza]";
		LOG.debug(prf + " BEGIN");
		
		// PK rispetto l'analoga funzione "salvaCheckListAffidamentoDocumentaleHtml"
		// - non considera il verbale allegato
		// - risetta il lock
		
		LOG.debug(prf + " salvachechlistaffReq="+salvachechlistaffReq);
		
		if(salvachechlistaffReq.getChecklistHtmlDTO().getAllegati()!=null){
			LOG.debug(prf + " getAllegati.length="+salvachechlistaffReq.getChecklistHtmlDTO().getAllegati().length);
		}else {
			LOG.debug(prf + " getAllegati null");
		}
		
		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
	
		LOG.debug(prf + " idUtente="+idUtente);
		LOG.debug(prf + " idIride="+idIride);
		
		//PK : pbandiwebrce -> CPBEChecklistAffidamentoHtml.salvaCheckListBozza 
		
		EsitoSalvaModuloCheckListHtmlDTO esitoOK = null;
		try {
			
			//--------------------------------------------------------------------
			if (salvachechlistaffReq.getCodTipoChecklist()
					.equalsIgnoreCase(Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_IN_LOCO)) {
				
				LOG.debug(prf + " checklist 1 CLILA");
				
			/ *
			 * PK : passo di qui quando faccio "Gestione Affidamenti" > "Esamina Affidamento" > "Checklist in loco"
			 * /
				
				esitoOK = checklistHtmlBusinessImpl.saveCheckListAffidamentoInLocoHtml(idUtente,  
						idIride, salvachechlistaffReq.getIdProgetto(),
						salvachechlistaffReq.getCodStatoTipoDocIndex(),
						salvachechlistaffReq.getChecklistHtmlDTO(),
						salvachechlistaffReq.getIdAffidamento());
				
			}else {
				
				LOG.debug(prf + " checklist 2 "+salvachechlistaffReq.getCodTipoChecklist());
				
				/ * *
				 * boolean isRichiestaIntegrazione = false;
				  String noteRichiestaIntegrazione = null;
				
				 * esito = gestioneAffidamentiBusiness
							.salvaCheckListAffidamentoDocumentaleHtml(theModel
									.getAppDatacurrentUser(), idProgetto,
									codStatoTipoDocIndex, checklistHtmlDTO,
									idAffidamento, isRichiestaIntegrazione,
									noteRichiestaIntegrazione);
				 * /
				esitoOK = checklistHtmlBusinessImpl.saveCheckListAffidamentoDocumentaleHtml(idUtente, 
						idIride, salvachechlistaffReq.getIdProgetto(),
						salvachechlistaffReq.getCodStatoTipoDocIndex(),
						salvachechlistaffReq.getChecklistHtmlDTO(),
						salvachechlistaffReq.getIdAffidamento(), 
						salvachechlistaffReq.getIsRichiestaIntegrazione(), 
						salvachechlistaffReq.getNoteRichiestaIntegrazione());
				
				
				LOG.debug(prf + " saveCheckListAffidamentoDocumentaleHtml invocata");
				
			}
			
			if (esitoOK.getEsito()) {

				// Ricarica il modello, altrimenti vengono visualizzati i dati precedenti al salvataggio.
				//caricaModelloChecklist(theModel);

				// Rimette il lock per evitare che 2 salvataggi bozza consecutivi 
				// provochino l'errore "Impossibile salvare, lock scaduto"
				
				try {
					
					if(Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_IN_LOCO.equalsIgnoreCase(salvachechlistaffReq.getCodTipoChecklist())) {
						checkListBusinessImpl.salvaLockCheckListValidazione(idUtente, idIride, salvachechlistaffReq.getIdProgetto(), salvachechlistaffReq.getIdAffidamento());
					}

					return Response.ok().entity(esitoOK).build();
					
				} catch (Exception e) {
					LOG.error(prf + "ERRORE: " +e.getMessage());
					throw e;
				}
			}else {
				LOG.error(prf + "Si &egrave; verificato un errore durante il salvataggio della bozza della checklist.");
				throw new Exception("Si &egrave; verificato un errore durante il salvataggio della checklist.");
			}
			
		}catch(Exception e) {
			
			EsitoUnLockCheckListDTO unlock= checkListBusinessImpl.eliminaLockCheckListAffidamentoValidazione(idUtente, idIride, 
					salvachechlistaffReq.getIdProgetto(), salvachechlistaffReq.getIdAffidamento());
			
			LOG.debug(prf + " unlock="+unlock);
			LOG.error(prf + "Errore ex="+e.getMessage());
			throw e;
			
		}finally {
			LOG.debug(prf + " END");
		}
		
	}
	*/

	private it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.FileDTO[] leggiFilesDaMultipart(
			List<InputPart> files) throws Exception {
		String prf = "[ChecklistServiceImpl::leggiFilesDaMultipart]";
		LOG.debug(prf + " BEGIN");
		
		it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.FileDTO[] res = new it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.FileDTO[files.size()];
//		ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.FileDTO> res = new ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.FileDTO>();
		try {

			String nomeFile = null;
			byte[] content;
			MultivaluedMap<String, String> header;
			int i = 0;
			
			for (InputPart inputPart : files) {
				
				header = inputPart.getHeaders();				
				nomeFile = getFileName(header);
				content = IOUtils.toByteArray(inputPart.getBody(InputStream.class, null));
				
				LOG.debug(prf + " nomeFile="+nomeFile);
				if(content!=null) {
					LOG.debug(prf + " content length="+content.length);
				}else {
					LOG.debug(prf + " content NULL");
				}
				
				it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.FileDTO f = new it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.FileDTO();
				f.setNome(nomeFile);
				f.setBytes(content);
				
				res[i]=f;
				i++;
//				res.add(f);
			}
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE: ", e);
			throw new Exception("Errore durante la lettura dei file da MultipartFormDataInput.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
	
		return res;
	}


		public static String getFileName(MultivaluedMap<String, String> header) {
			
			/**
			 * public static final String CONTENT_DISPOSITION = "Content-Disposition";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String FILE_NAME_KEY = "filename";
			 */
		
			//String[] contentDisposition = header.getFirst(Constants.CONTENT_DISPOSITION).split(";");
			String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
			for (String value : contentDisposition) {
				//if (value.trim().startsWith(Constants.FILE_NAME_KEY)) {
				if (value.trim().startsWith("filename")) {
					String[] name = value.split("=");
					if (name.length > 1) {
						return name[1].trim().replaceAll("\"", "");
					}
				}
			}
			return null;
		}
		
		
		
	@Override
	public Response leggiStatoChecklist(HttpServletRequest req, Long idEntita, Long idTarget, Long idTipoDocIndexDoc,
			Long idProgetto) throws UtenteException, Exception {
		String prf = "[ChecklistServiceImpl::leggiStatoChecklist]";
		LOG.debug(prf + " BEGIN");
		
		String stato = null;
		
		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
	
		LOG.debug(prf + " idUtente="+idUtente);
		LOG.debug(prf + " idIride="+idIride);
		
		try {
			stato = checkListBusinessImpl.findStatoChecklistUltimaVersione(idUtente, idIride, idEntita, idTarget, idTipoDocIndexDoc, idProgetto);
			LOG.debug(prf + " stato="+stato);
			return Response.ok().entity(stato).build();
		} catch(Exception e) {
			throw e;
		}finally {
			LOG.debug(prf + " END");
		}
	}
	
	@Override
	public Response findEntita(HttpServletRequest req, String nomeEntita) throws UtenteException, Exception {
		String prf = "[ChecklistServiceImpl::findEntita]";
		LOG.debug(prf + " BEGIN");
		
		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
	
		LOG.debug(prf + " idUtente="+idUtente);
		LOG.debug(prf + " idIride="+idIride);
		Entita entita = null;
		try {
			entita = gestioneDatiDiDominioBusinessImpl.findEntita(idUtente, idIride, nomeEntita);
			return Response.ok().entity(entita).build();
		} catch(Exception e) {
			throw e;

		}finally {
			LOG.debug(prf + " END");
		}
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	///////////////////////////////////////////// METODI DI RESPONSE HTTP /////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private Response inviaErroreBadRequest(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.BAD_REQUEST).entity(esito).type( MediaType.APPLICATION_JSON).build();
	}




}
