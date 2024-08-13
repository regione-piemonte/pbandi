/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.documentoDiSpesa;

public class EsitoOperazioneVociDiSpesa implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.String message = null;
	private java.lang.String[] params = null;
	private VoceDiSpesaDTO voceDiSpesa = null;
	private java.lang.Boolean esito = null;
	public java.lang.String getMessage() {
		return message;
	}
	public void setMessage(java.lang.String message) {
		this.message = message;
	}
	public java.lang.String[] getParams() {
		return params;
	}
	public void setParams(java.lang.String[] params) {
		this.params = params;
	}
	public VoceDiSpesaDTO getVoceDiSpesa() {
		return voceDiSpesa;
	}
	public void setVoceDiSpesa(VoceDiSpesaDTO voceDiSpesa) {
		this.voceDiSpesa = voceDiSpesa;
	}
	public java.lang.Boolean getEsito() {
		return esito;
	}
	public void setEsito(java.lang.Boolean esito) {
		this.esito = esito;
	}

}
