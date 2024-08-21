/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.archivioFile;

public class Esito implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Boolean esito = null;
	private java.lang.String messaggio = null;
	
	public java.lang.Boolean getEsito() {
		return esito;
	}
	public void setEsito(java.lang.Boolean esito) {
		this.esito = esito;
	}
	public java.lang.String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(java.lang.String messaggio) {
		this.messaggio = messaggio;
	}

}
