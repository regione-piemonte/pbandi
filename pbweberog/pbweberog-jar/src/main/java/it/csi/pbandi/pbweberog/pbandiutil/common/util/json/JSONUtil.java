/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.pbandiutil.common.util.json;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class JSONUtil {
	
	public static JSONObject createJSonObject(Object data, boolean success,  ArrayList actionMessages) {
		
		JSONObjectCustom jsonCustom = new JSONObjectCustom();
		jsonCustom.setData(data);
		jsonCustom.setSuccess(success);
		jsonCustom.setActionMessages(actionMessages);
		
		JSONObject jsonObj = JSONObject.fromObject(jsonCustom);
		
		return jsonObj;
		
	}
	
	public static JSONObject createJSonObject(Object data, boolean success) {
		
		JSONObjectCustom jsonCustom = new JSONObjectCustom();
		jsonCustom.setData(data);
		jsonCustom.setSuccess(success);
		
		JSONObject jsonObj = JSONObject.fromObject(jsonCustom);
		
		return jsonObj;
		
	}
	
	
	public static JSONObject createJSonObject(Object data, boolean success, ArrayList fieldErrors, ArrayList globalErrors, ArrayList actionMessages) {
		
		JSONObjectCustom jsonCustom = new JSONObjectCustom();
		jsonCustom.setData(data);
		jsonCustom.setSuccess(success);
		jsonCustom.setErrors(fieldErrors);
		jsonCustom.setGlobalErrors(globalErrors);
		jsonCustom.setActionMessages(actionMessages);
		
		JSONObject jsonObj = JSONObject.fromObject(jsonCustom);
		
		return jsonObj;
		
	}
	
	public static JSONObject createJSonObject(boolean success, boolean isConfirmRequired, String msgConfirm) {
		
		JSONObjectCustom jsonCustom = new JSONObjectCustom();
		jsonCustom.setSuccess(success);
		jsonCustom.setConfirmRequired(isConfirmRequired);
		jsonCustom.setMsgConfirm(msgConfirm);
		JSONObject jsonObj = JSONObject.fromObject(jsonCustom);
		
		return jsonObj;
		
	}
	
	
	public static void writeJSONResponse(HttpServletResponse  response, JSONObject jsonObj) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonObj.toString());
	}
	
	public static JSONObjectCustom toJSONObjectCustom(JSONObject jsonObject) {
		return (JSONObjectCustom) JSONObject.toBean(jsonObject, JSONObjectCustom.class);
	}
	

}
