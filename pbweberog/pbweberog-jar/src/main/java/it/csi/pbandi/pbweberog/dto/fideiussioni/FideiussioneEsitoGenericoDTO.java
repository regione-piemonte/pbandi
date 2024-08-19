/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.fideiussioni;

public class FideiussioneEsitoGenericoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Boolean esito;

	public void setEsito(Boolean val) {
		esito = val;
	}

	public Boolean getEsito() {
		return esito;
	}

	private String message;

	public void setMessage(String val) {
		message = val;
	}

	public String getMessage() {
		return message;
	}

	private String[] params;

	public void setParams(String[] val) {
		params = val;
	}

	public String[] getParams() {
		return params;
	}

}
