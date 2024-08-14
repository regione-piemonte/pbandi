/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.pbandi.pbwebcert.integration.vo.AllegatoVO;

public class UploadUtil {

	private static Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	public static final String CONTENT_DISPOSITION = "Content-Disposition";

	public static final String CONTENT_TYPE = "Content-Type";
	public static final String FILE_NAME_KEY = "filename";
	
	public static List<AllegatoVO> getAllegatiFromInput(List<InputPart> inputParts) throws IOException{
		String prf = "[UploadUtil :: getAllegatiFromInput]";
		LOG.debug(prf + " BEGIN");
		
		AllegatoVO vo = null;
		String fileName = null;
		String mimeType = null;
		byte[] content;
		MultivaluedMap<String, String> header;
		List<AllegatoVO> result = new ArrayList<AllegatoVO>();

		//List<InputPart> inputParts = getParts(input);
		LOG.debug(prf + " inputParts " + inputParts);

		for (InputPart inputPart : inputParts) {
			vo = new AllegatoVO();
			header = inputPart.getHeaders();
			fileName = getFileName(header);
			LOG.debug(prf + " header=[" + fileName + "]");
			mimeType = getMimeType(header);
			LOG.debug("[UploadUtil::getAllegatiFromInput] mimeType=[" + mimeType + "]");
			content = IOUtils.toByteArray(inputPart.getBody(InputStream.class, null));
			LOG.debug(prf +" " + content.length);
			vo.setContent(content);
			vo.setFileName(fileName);
			vo.setMimeType(mimeType);
			result.add(vo);
		}
		
		LOG.debug(prf + " END");
		return result;
		
	}
	
	
	private static List<InputPart> getParts(MultipartFormDataInput input) {
		Map<String, List<InputPart>> form = input.getFormDataMap();
		List<InputPart> result = new ArrayList<>();

		for (Map.Entry<String, List<InputPart>> item : form.entrySet()) {
			List<InputPart> inputParts = item.getValue();
			if (!CollectionUtils.isEmpty(inputParts)) {
				result.addAll(inputParts);
			}
		}
		return result;
	}
	
	public static String getFileName(MultivaluedMap<String, String> header) {
		String[] contentDisposition = header.getFirst(CONTENT_DISPOSITION).split(";");

		for (String value : contentDisposition) {
			if (value.trim().startsWith(FILE_NAME_KEY)) {
				String[] name = value.split("=");
				if (name.length > 1) {
					return name[1].trim().replaceAll("\"", "");
				}
			}
		}
		return null;
	}

		public static String getMimeType(MultivaluedMap<String, String> header) {
		String[] contentType = header.getFirst(CONTENT_TYPE).split(";");

		for (String value : contentType) {
		if (!value.contains("=")) {
		return value.trim();
		}
		}
		return null;
		}
		

}
