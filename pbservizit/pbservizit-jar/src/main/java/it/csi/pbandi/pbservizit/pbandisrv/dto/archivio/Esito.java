/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.archivio;


public class Esito implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Boolean esito;

	private String url;

	private String message;

	private String[] params;

	public Boolean getEsito() {
		return esito;
	}

	public void setEsito(Boolean esito) {
		this.esito = esito;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}

	
}
