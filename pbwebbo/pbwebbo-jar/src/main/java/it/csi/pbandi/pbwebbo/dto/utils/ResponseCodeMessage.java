/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.utils;

import java.io.Serializable;

public class ResponseCodeMessage implements Serializable{

	private String message;
	private String code;

	public String getMessage() {
		return message;
	}
	
	

	public ResponseCodeMessage() {
		super();
	}



	public ResponseCodeMessage(String code, String message) {
		super();
		this.message = message;
		this.code = code;
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
