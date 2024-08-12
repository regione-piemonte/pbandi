package it.csi.pbandi.pbweb.business.service.impl;

import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservizit.pbandisrv.business.checklisthtml.ChecklistHtmlBusinessImpl;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweb.business.service.CheckListService;
import it.csi.pbandi.pbweb.dto.CercaChecklistRequestDTO;
import it.csi.pbandi.pbweb.dto.CheckListItem;
import it.csi.pbandi.pbweb.integration.dao.CheckListDAO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoOperazioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.FileDTO;
import it.csi.pbandi.pbweb.util.Constants;

@Service
public class CheckListServiceImpl implements CheckListService {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	private CheckListDAO checkListDAO;
	
	@Autowired
	ChecklistHtmlBusinessImpl checklistHtmlBusinessImpl;

	@Override
	public Response getModuloCheckList(String idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		String prf ="[CheckListServiceImpl::getModuloCheckList] ";
		
		// CPBEGestioneCheckListHtml > inizializzaCP "checklist IN INSERIMENTO"
		
		LOG.debug(prf+"BEGIN");
		LOG.debug(prf+"idProgetto="+idProgetto);
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		String soggettoControllore = "";
		if(StringUtils.isNotBlank(userInfo.getCognome()) && StringUtils.isNotBlank(userInfo.getNome())) {
			soggettoControllore = userInfo.getCognome() + " " + userInfo.getNome();
		}else if(StringUtils.isNotBlank(userInfo.getCodFisc())) {
			soggettoControllore = userInfo.getCodFisc();
		}
		LOG.debug(prf+"soggettoControllore="+soggettoControllore);
		
		return Response.ok().entity(checkListDAO.getModuloCheckList(userInfo.getIdUtente(), userInfo.getIdIride(), Long.valueOf(idProgetto), soggettoControllore)).build();
	}

	@Override
	public Response caricaDichiarazioneDiSpesa(String idProgetto, String isFP, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		String prf ="[CheckListServiceImpl::caricaDichiarazioneDiSpesa] ";
		
		// CPBEGestioneCheckList > resetParametriRicercaChecklist
		
		LOG.debug(prf+"BEGIN");
		LOG.debug(prf+"idProgetto="+idProgetto);
		LOG.debug(prf+"isFP="+isFP);

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		
		return Response.ok().entity(checkListDAO.caricaDichiarazioneDiSpesa(idProgetto, Objects.equals("S", isFP), userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response caricaStatoSoggetto(String idUtente, String idIride, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		String prf ="[CheckListServiceImpl::caricaStatoSoggetto] ";
		
		// CPBEGestioneCheckList > resetParametriRicercaChecklist
		
		LOG.debug(prf+"BEGIN");
		LOG.debug(prf+"idUtente="+idUtente);
		LOG.debug(prf+"idIride="+idIride);
		
		return Response.ok().entity(checkListDAO.caricaStatoSoggetto(idUtente, idIride)).build();
	}

	@Override
	public Response cercaChecklist(CercaChecklistRequestDTO request, HttpServletRequest req)
			throws InvalidParameterException, Exception {

		String prf ="[CheckListServiceImpl::cercaChecklist] ";
		
		// CPBEGestioneCheckList > resetParametriRicercaChecklist
		
		LOG.debug(prf+"BEGIN");
		LOG.debug(prf+"dichiarazioneSpesa="+request.getDichiarazioneSpesa());
		LOG.debug(prf+"dataControllo="+request.getDataControllo());
		LOG.debug(prf+"soggettoControllo="+request.getSoggettoControllo());
		if(request.getStati() != null) {
			LOG.debug(prf+"stati="+request.getStati().toString());
		} else {
			LOG.debug(prf+"stati=null");
		}
		LOG.debug(prf+"tipologia="+request.getTipologia());
		LOG.debug(prf+"rilevazione="+request.getRilevazione());
		LOG.debug(prf+"versione="+request.getVersione());
		LOG.debug(prf+"idProgetto="+request.getIdProgetto());
		
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		
		// PK : spulcio la lista e imposto isElimabile e isModificabile
		
		List<CheckListItem> listaCH = checkListDAO.cercaChecklist(userInfo.getIdUtente(), userInfo.getIdIride(),
				request.getDichiarazioneSpesa(),  request.getDataControllo(),  request.getSoggettoControllo(),
				request.getStati(),  request.getTipologia(),  request.getRilevazione(),  request.getVersione(), request.getIdProgetto());
		
		for (CheckListItem checkListItem : listaCH) {
			
			if (checkListItem.getCodiceTipo().equals(
					Constants.COD_TIPO_CHEKCLIST_DOCUMENTALE)
					&& checkListItem.getCodiceStato().equals(
							Constants.COD_STATO_TIPO_DOC_INVIATO)) {
				
				checkListItem.setModificabile(false);
				
			} else if (checkListItem.getCodiceTipo().equals(
					Constants.COD_TIPO_CHEKCLIST_IN_LOCO)
					&& checkListItem.getCodiceStato().equals(
							Constants.COD_STATO_TIPO_DOC_DEFINITIVO)) {
				checkListItem.setModificabile(false);
			}else {
				checkListItem.setModificabile(true);
			}
			//TODO PK : vedere per l'eliminazione
			
		}
		return Response.ok().entity(listaCH).build();
//		return Response.ok().entity(checkListDAO.cercaChecklist(userInfo.getIdUtente(), userInfo.getIdIride(),
//				dichiarazioneSpesa,  dataControllo,  soggettoControllo,
//				 stato,  tipologia,  rilevazione,  versione, idProgetto)).build();
	}
	
	@Override
	public Response inizializzaGestioneChecklist(Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {		
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(checkListDAO.inizializzaGestioneChecklist(userInfo.getIdUtente(), userInfo.getIdSoggetto(), userInfo.getCodiceRuolo(), idProgetto)).build();
	}
	
	@Override
	public Response eliminaChecklist(Long idDocumentoIndex, HttpServletRequest req) throws InvalidParameterException, Exception {		
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(checkListDAO.eliminaChecklist(idDocumentoIndex, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response salvaLockCheckListInLoco(Long idProgetto, Long idDocumentoIndex, HttpServletRequest req) throws InvalidParameterException, Exception {		
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(checkListDAO.salvaLockCheckListInLoco(idProgetto, idDocumentoIndex, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response salvaLockCheckListValidazione(Long idProgetto, Long idDichiarazione, HttpServletRequest req) throws InvalidParameterException, Exception {		
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(checkListDAO.salvaLockCheckListValidazione(idProgetto, idDichiarazione, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	@Transactional
	public Response salvaCheckListInLoco(MultipartFormDataInput multipartFormData) throws InvalidParameterException, Exception {
		String prf ="[CheckListServiceImpl::salvaCheckListInLoco] ";
		LOG.debug(prf+"BEGIN");
		
		Long idUtente = multipartFormData.getFormDataPart("idUtente", Long.class, null);
		String idIride = multipartFormData.getFormDataPart("idIride", String.class, null);
		
		Long idProgetto = multipartFormData.getFormDataPart("idProgetto", Long.class, null);
		String statoChecklist = multipartFormData.getFormDataPart("statoChecklist", String.class, null);
		String checklistHtml = multipartFormData.getFormDataPart("checklistHtml", String.class, null);
		String idChecklist = multipartFormData.getFormDataPart("idChecklist", String.class, null);
		
		LOG.debug(prf+"idProgetto="+idProgetto);
		LOG.debug(prf+"statoChecklist="+statoChecklist);
		LOG.debug(prf+"idChecklist="+idChecklist);
//		LOG.debug(prf+"checklistHtml="+checklistHtml);
		
		Long idCh = null;
		if(idChecklist!=null && !"".equals(idChecklist))
			idCh = new Long(idChecklist);
		
		EsitoOperazioneDTO esito = checkListDAO.saveCheckListInLocoHtml(idUtente, idIride, idCh,
				idProgetto, statoChecklist, checklistHtml);			
		return Response.ok().entity(esito).build();
	}

	@Override
	public Response getCheckListInLocoHtml(String idDocumentoIndex, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		String prf ="[CheckListServiceImpl::getCheckListInLocoHtml] ";
		
		// CPBEGestioneCheckListHtml > inizializzaCP "checklist IN MODIFICA"
		LOG.debug(prf+"BEGIN");
		LOG.debug(prf+"idDocumentoIndex="+idDocumentoIndex);
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		
		return Response.ok().entity(checkListDAO.getCheckListInLocoHtml(userInfo.getIdUtente(), userInfo.getIdIride(), Long.valueOf(idDocumentoIndex))).build();
	}

	@Override
	public Response getCheckListValidazioneHtml(String idDocumentoIndex, String idDichiarazione, String idProgetto,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		String prf ="[CheckListServiceImpl::getCheckListValidazioneHtml] ";
		
		// CPBEGestioneCheckListHtml > inizializzaCP "checklist IN MODIFICA"
		LOG.debug(prf+"BEGIN");
		LOG.debug(prf+"idDocumentoIndex="+idDocumentoIndex);
		LOG.debug(prf+"idDichiarazione="+idDichiarazione);
		LOG.debug(prf+"idProgetto="+idProgetto);
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		
		return Response.ok().entity(checkListDAO.getCheckListValidazioneHtml(userInfo.getIdUtente(), userInfo.getIdIride(),
				Long.valueOf(idDocumentoIndex),Long.valueOf(idDichiarazione),Long.valueOf(idProgetto))).build();
	
	}
	
	@Override
	@Transactional
	public Response saveCheckListDocumentaleHtml(MultipartFormDataInput multipartFormData) throws InvalidParameterException, Exception {
		String prf ="[CheckListServiceImpl::saveCheckListDocumentaleHtml] ";
		LOG.debug(prf+"BEGIN");
		
		Long idUtente = multipartFormData.getFormDataPart("idUtente", Long.class, null);
		String idIride = multipartFormData.getFormDataPart("idIride", String.class, null);
		
		Long idProgetto = multipartFormData.getFormDataPart("idProgetto", Long.class, null);
		String statoChecklist = multipartFormData.getFormDataPart("statoChecklist", String.class, null);
		String checklistHtml = multipartFormData.getFormDataPart("checklistHtml", String.class, null);
		Long idChecklist = multipartFormData.getFormDataPart("idChecklist", Long.class, null);
		if(idChecklist == -1) idChecklist = null;
		
		LOG.debug(prf+"idProgetto="+idProgetto);
		LOG.debug(prf+"statoChecklist="+statoChecklist);
		LOG.debug(prf+"idChecklist="+idChecklist);
		
		Long idCh = null;
		if(idChecklist!=null && !"".equals(idChecklist))
			idCh = new Long(idChecklist);
		
		Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
		List<InputPart> files = map.get("file");
		FileDTO[] verbali = leggiFilesDaMultipart(files);	
		if(verbali != null && verbali.length > 0) 
			LOG.debug(prf + " verbali.length="+verbali.length);	
		
		
		return Response.ok().entity(checkListDAO.saveCheckListDocumentaleHtml(idUtente, idIride, idCh,
				idProgetto, statoChecklist, checklistHtml, verbali)).build();
	}

	@Override
	public Response saveCheckListDefinitivo(MultipartFormDataInput multipartFormData)
			throws InvalidParameterException, Exception {
		String prf ="[CheckListServiceImpl::saveCheckListDefinitivo] ";
		LOG.debug(prf+"BEGIN");
		
		Long idUtente = multipartFormData.getFormDataPart("idUtente", Long.class, null);
		String idIride = multipartFormData.getFormDataPart("idIride", String.class, null);
		
		Long idProgetto = multipartFormData.getFormDataPart("idProgetto", Long.class, null);
		String statoChecklist = multipartFormData.getFormDataPart("statoChecklist", String.class, null);
		String checklistHtml = multipartFormData.getFormDataPart("checklistHtml", String.class, null);
		Long idChecklist = multipartFormData.getFormDataPart("idChecklist", Long.class, null);
		
		LOG.debug(prf+"idProgetto="+idProgetto);
		LOG.debug(prf+"statoChecklist="+statoChecklist); // dovrebbe essere D
		LOG.debug(prf+"idChecklist="+idChecklist);
        
		Long idCh = null;
		if(idChecklist!=null && !"".equals(idChecklist))
			idCh = new Long(idChecklist);

		
		Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
		List<InputPart> files = map.get("file");

		FileDTO[] verbali = leggiFilesDaMultipart(files);	
		if(verbali== null || verbali.length == 0) {
			throw new Exception("Nessun file allegato.");
		}else {
			LOG.debug(prf + " verbali.length="+verbali.length);
		}
		
		return Response.ok().entity(checkListDAO.saveCheckListInLocoHtmlDef(idUtente, idIride, idCh,
				idProgetto, statoChecklist, checklistHtml, verbali)).build();
	}
	
	private FileDTO[] leggiFilesDaMultipart(
			List<InputPart> files) throws Exception {
		String prf = "[ChecklistServiceImpl::leggiFilesDaMultipart]";
		LOG.debug(prf + " BEGIN");
		
		FileDTO[] res = null;
		
		if (files != null) {
		
		res = new FileDTO[files.size()];
			try {
		
				String nomeFile = null;
				byte[] content;
				MultivaluedMap<String, String> header;
				int i = 0;
				
				for (InputPart inputPart : files) {
					
					header = inputPart.getHeaders();				
					nomeFile = getFileName(header);
					content = IOUtils.toByteArray(inputPart.getBody(InputStream.class, null));
					
					LOG.debug(prf + " nomeFile orig ="+nomeFile);
					if(content!=null) {
						LOG.debug(prf + " content length="+content.length);
					}else {
						LOG.debug(prf + " content NULL");
					}
					
					String time = DateUtil.getTime(new java.util.Date(), "ddMMyyyyHHmmss");
					String newNome = "VCL_"+(i + 1)+"_"+time+"_"+nomeFile;
					LOG.debug(prf + " newNome ="+newNome);
					
					FileDTO f = new FileDTO();
					f.setNome(newNome);
					f.setBytes(content);
					
					res[i]=f;
					i++;
				}
				
			} catch (Exception e) {
				LOG.error(prf+" ERRORE: ", e);
				throw new Exception("Errore durante la lettura dei file da MultipartFormDataInput.", e);
			}
			finally {
				LOG.info(prf+" END");
			}
		}
	
		return res;
	}
	
	public static String getFileName(MultivaluedMap<String, String> header) {
		
		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		for (String value : contentDisposition) {
			if (value.trim().startsWith("filename")) {
				String[] name = value.split("=");
				if (name.length > 1) {
					return name[1].trim().replaceAll("\"", "");
				}
			}
		}
		return null;
	}
	
	
	
	
	
}
