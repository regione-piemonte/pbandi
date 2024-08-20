/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.dto.digitalsign;

public class SignedFileDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private byte[] bytes = null;
	private java.util.Date dtInserimento = null;
	private java.util.Date dtAggiornamento = null;
	private java.lang.Long idDocIndex = null;
	private java.lang.Long idEntita = null;
	private java.lang.Long idProgetto = null;
	private java.lang.Long idTarget = null;
	private java.lang.Long idStato = null;
	private java.lang.String fileName = null;
	private java.lang.String numProtocollo = null;
	private java.lang.Long sizeFile = null;
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public java.util.Date getDtInserimento() {
		return dtInserimento;
	}
	public void setDtInserimento(java.util.Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	public java.util.Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	public void setDtAggiornamento(java.util.Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	public java.lang.Long getIdDocIndex() {
		return idDocIndex;
	}
	public void setIdDocIndex(java.lang.Long idDocIndex) {
		this.idDocIndex = idDocIndex;
	}
	public java.lang.Long getIdEntita() {
		return idEntita;
	}
	public void setIdEntita(java.lang.Long idEntita) {
		this.idEntita = idEntita;
	}
	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(java.lang.Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public java.lang.Long getIdTarget() {
		return idTarget;
	}
	public void setIdTarget(java.lang.Long idTarget) {
		this.idTarget = idTarget;
	}
	public java.lang.Long getIdStato() {
		return idStato;
	}
	public void setIdStato(java.lang.Long idStato) {
		this.idStato = idStato;
	}
	public java.lang.String getFileName() {
		return fileName;
	}
	public void setFileName(java.lang.String fileName) {
		this.fileName = fileName;
	}
	public java.lang.String getNumProtocollo() {
		return numProtocollo;
	}
	public void setNumProtocollo(java.lang.String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}
	public java.lang.Long getSizeFile() {
		return sizeFile;
	}
	public void setSizeFile(java.lang.Long sizeFile) {
		this.sizeFile = sizeFile;
	}

}
