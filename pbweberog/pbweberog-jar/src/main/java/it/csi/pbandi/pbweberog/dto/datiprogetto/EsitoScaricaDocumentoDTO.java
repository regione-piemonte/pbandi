/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.datiprogetto;

public class EsitoScaricaDocumentoDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private Boolean esito;
	private String message;
	private String[] params;
	private byte[] bytesDocumento;
	private String nomeFile;
	public Boolean getEsito() {
		return esito;
	}
	public void setEsito(Boolean esito) {
		this.esito = esito;
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
	public byte[] getBytesDocumento() {
		return bytesDocumento;
	}
	public void setBytesDocumento(byte[] bytesDocumento) {
		this.bytesDocumento = bytesDocumento;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
