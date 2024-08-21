/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.utils;

import java.io.Serializable;

public class ResponseCodeMessage implements Serializable {
	
	private static final long serialVersionUID = -1;
	
	private String code;
	private String message;	
	
	public ResponseCodeMessage(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
		
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
