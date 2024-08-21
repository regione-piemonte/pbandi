/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto;

import java.io.Serializable;

public class EsitoOperazioni implements Serializable {

	static final long serialVersionUID = 1;

	private Boolean esito = null;
	private String msg = null;
	private String[] params = null;
	
	public void setEsito(Boolean val) {
		esito = val;
	}

	public Boolean getEsito() {
		return esito;
	}

	public void setMsg(String val) {
		msg = val;
	}

	public String getMsg() {
		return msg;
	}

	public void setParams(String[] val) {
		params = val;
	}

	public String[] getParams() {
		return params;
	}

}
