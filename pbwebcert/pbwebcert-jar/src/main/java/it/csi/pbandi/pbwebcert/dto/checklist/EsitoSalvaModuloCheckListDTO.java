/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.dto.checklist;

public class EsitoSalvaModuloCheckListDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private byte[] byteModulo = null;
	public void setByteModulo(byte[] val) {
		byteModulo = val;
	}
	public byte[] getByteModulo() {
		return byteModulo;
	}
	private boolean esito = true;
	public void setEsito(boolean val) {
		esito = val;
	}

	public boolean getEsito() {
		return esito;
	}
	private java.lang.String message = null;

	public void setMessage(java.lang.String val) {
		message = val;
	}

	public java.lang.String getMessage() {
		return message;
	}

	private java.lang.String[] params = null;

	public void setParams(java.lang.String[] val) {
		params = val;
	}

	public java.lang.String[] getParams() {
		return params;
	}

	private java.lang.Long idDocumentoIndex = null;

	public void setIdDocumentoIndex(java.lang.Long val) {
		idDocumentoIndex = val;
	}

	public java.lang.Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	private java.lang.Long idChecklist = null;

	public void setIdChecklist(java.lang.Long val) {
		idChecklist = val;
	}

	public java.lang.Long getIdChecklist() {
		return idChecklist;
	}
}
