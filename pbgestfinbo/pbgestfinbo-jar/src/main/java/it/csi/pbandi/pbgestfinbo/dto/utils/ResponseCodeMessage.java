/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.dto.utils;

public class ResponseCodeMessage {

	
	private String code;
	private String message;
	
	
	public ResponseCodeMessage () {}
	
	public ResponseCodeMessage (String code, String message) {
		
		this.code = code;
		this.message = message;
	}
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
