/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.util;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.map.ObjectMapper;

import it.csi.pbandi.pbworkspace.dto.utils.ResponseCodeMessage;


public class ResponseUtils {

	public static String createJSONResponseMessage(String code, String message) {
		//Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String jsonResult = "";
		ResponseCodeMessage ex = new ResponseCodeMessage();
		
		ex.setCode(code);
		ex.setMessage(message);

		ObjectMapper om = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try 
		{
			om.writeValue(sw, ex);
		} 
		catch (IOException e) 
		{
			//Qualunque eccezione mi produce una stringa vuota, devo gestire altro?
			e.printStackTrace();
			jsonResult = "";
		}
		jsonResult=sw.toString();

		//return gson.toJson(ex);
		return jsonResult;
	}
}
