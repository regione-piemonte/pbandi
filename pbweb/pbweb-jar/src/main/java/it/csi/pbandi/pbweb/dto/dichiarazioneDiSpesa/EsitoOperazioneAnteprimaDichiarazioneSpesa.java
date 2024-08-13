/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa;

public class EsitoOperazioneAnteprimaDichiarazioneSpesa implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Boolean esito = null;
	private java.lang.String nomeFile = null;
	private byte[] pdfBytes = null;
	private it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.DichiarazioneDiSpesaDTO dichiarazioneDiSpesa = null;
	private java.lang.String msg = null;
	
	public java.lang.Boolean getEsito() {
		return esito;
	}
	public void setEsito(java.lang.Boolean esito) {
		this.esito = esito;
	}
	public java.lang.String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(java.lang.String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public byte[] getPdfBytes() {
		return pdfBytes;
	}
	public void setPdfBytes(byte[] pdfBytes) {
		this.pdfBytes = pdfBytes;
	}
	public it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.DichiarazioneDiSpesaDTO getDichiarazioneDiSpesa() {
		return dichiarazioneDiSpesa;
	}
	public void setDichiarazioneDiSpesa(
			it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.DichiarazioneDiSpesaDTO dichiarazioneDiSpesa) {
		this.dichiarazioneDiSpesa = dichiarazioneDiSpesa;
	}
	public java.lang.String getMsg() {
		return msg;
	}
	public void setMsg(java.lang.String msg) {
		this.msg = msg;
	}

}
