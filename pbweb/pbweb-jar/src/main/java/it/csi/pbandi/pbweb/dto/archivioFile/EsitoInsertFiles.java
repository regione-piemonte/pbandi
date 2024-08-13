/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.archivioFile;

public class EsitoInsertFiles implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Boolean esito = null;
	private java.lang.String messaggio = null;
	private java.lang.String nomeFile = null;
	private java.lang.Long idDocumentoIndex = null;
	
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
	public java.lang.String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(java.lang.String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public java.lang.Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(java.lang.Long idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	
}
