/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api.impl;

import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.business.api.ChecklistCommonApi;
import it.csi.pbandi.pbservizit.integration.dao.ChecklistCommonDAO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.FileDTO;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.util.BeanUtil;
import it.csi.pbandi.pbservizit.util.Constants;

@Service
public class ChecklistCommonApiImpl implements ChecklistCommonApi {

	@Autowired
	protected BeanUtil beanUtil;
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	ChecklistCommonDAO checklistCommonDAO;

	@Override
	public Response allegaFilesChecklist(MultipartFormDataInput multipartFormData, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		String prf = "[ChecklistCommonApiImpl::allegaFileChecklist] ";
		LOG.debug(prf + "BEGIN");
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		Long idProgetto = multipartFormData.getFormDataPart("idProgetto", Long.class, null);
		String codTipoDocumento = multipartFormData.getFormDataPart("codTipoDocumento", String.class, null);
		String idDocumentoIndexChecklist = multipartFormData.getFormDataPart("idDocumentoIndexChecklist", String.class,
				null);

		LOG.debug(prf + "idProgetto=" + idProgetto);
		LOG.debug(prf + "codTipoDocumento=" + codTipoDocumento);
		LOG.debug(prf + "idDocumentoIndexChecklist=" + idDocumentoIndexChecklist);

		Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
		List<InputPart> files = map.get("file");
		FileDTO[] allegati = leggiFilesDaMultipart(files, codTipoDocumento);
		if (allegati != null && allegati.length > 0)
			LOG.debug(prf + " allegati.length=" + allegati.length);

		return Response.ok().entity(checklistCommonDAO.allegaFilesChecklist(userInfo.getIdUtente(), userInfo.getIdIride(), idProgetto,
				codTipoDocumento, idDocumentoIndexChecklist, allegati)).build();
	}

	private FileDTO[] leggiFilesDaMultipart(List<InputPart> files, String codTipoDocumento) throws Exception {
		String prf = "[ChecklistCommonApiImpl::leggiFilesDaMultipart]";
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

					LOG.debug(prf + " nomeFile orig =" + nomeFile);
					if (content != null) {
						LOG.debug(prf + " content length=" + content.length);
					} else {
						LOG.debug(prf + " content NULL");
					}

					String time = DateUtil.getTime(new java.util.Date(), "ddMMyyyyHHmmss");
					String pre="";
					switch (codTipoDocumento) {
					      case "VCV":
					    	  pre="VCL";
					    	  break;
					      case "VCD":
					    	  pre="VCD";
					    	  break;
					      case "VCVA":
					    	  pre="VCLA";
					    	  break;
					      case "VCDA":
					    	  pre="VCDA";
					    	  break;
					      default:
					    	  break;
					    }
					String newNome = pre + "_" + (i + 1) + "_" + time + "_" + nomeFile;
					LOG.debug(prf + " newNome =" + newNome);

					FileDTO f = new FileDTO();
					f.setNome(newNome);
					f.setBytes(content);

					res[i] = f;
					i++;
				}

			} catch (Exception e) {
				LOG.error(prf + " ERRORE: ", e);
				throw new Exception("Errore durante la lettura dei file da MultipartFormDataInput.", e);
			} finally {
				LOG.info(prf + " END");
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
