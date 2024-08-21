/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.archivioFile;

import java.util.Arrays;

public class FileDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private byte[] bytes = null;
	private java.lang.String codiceVisualizzato = null;
	private java.lang.String descBreveStatoDocSpesa = null;
	private java.util.Date dtInserimento = null;
	private java.util.Date dtAggiornamento = null;
	private java.lang.Long idFolder = null;
	private java.lang.Long idDocumentoIndex = null;
	private java.lang.Long idEntita = null;
	private java.lang.Long idProgetto = null;
	private java.lang.Long idTarget = null;
	private java.lang.Long idStatoDocumentoSpesa = null;
	private java.lang.Boolean isLocked = null;
	private java.lang.String nomeFile = null;
	private java.lang.String numProtocollo = null;
	private java.lang.Long sizeFile = null;
	private java.lang.Long entityAssociated = null;
	private java.util.Date dtEntita = null;
	private java.lang.String flagEntita = null;
	private java.lang.String descStatoTipoDocIndex = null;
	
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public java.lang.String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(java.lang.String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public java.lang.String getDescBreveStatoDocSpesa() {
		return descBreveStatoDocSpesa;
	}
	public void setDescBreveStatoDocSpesa(java.lang.String descBreveStatoDocSpesa) {
		this.descBreveStatoDocSpesa = descBreveStatoDocSpesa;
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
	public java.lang.Long getIdFolder() {
		return idFolder;
	}
	public void setIdFolder(java.lang.Long idFolder) {
		this.idFolder = idFolder;
	}
	public java.lang.Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(java.lang.Long idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
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
	public java.lang.Long getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}
	public void setIdStatoDocumentoSpesa(java.lang.Long idStatoDocumentoSpesa) {
		this.idStatoDocumentoSpesa = idStatoDocumentoSpesa;
	}
	public java.lang.Boolean getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(java.lang.Boolean isLocked) {
		this.isLocked = isLocked;
	}
	public java.lang.String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(java.lang.String nomeFile) {
		this.nomeFile = nomeFile;
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
	public java.lang.Long getEntityAssociated() {
		return entityAssociated;
	}
	public void setEntityAssociated(java.lang.Long entityAssociated) {
		this.entityAssociated = entityAssociated;
	}
	public java.util.Date getDtEntita() {
		return dtEntita;
	}
	public void setDtEntita(java.util.Date dtEntita) {
		this.dtEntita = dtEntita;
	}
	public java.lang.String getFlagEntita() {
		return flagEntita;
	}
	public void setFlagEntita(java.lang.String flagEntita) {
		this.flagEntita = flagEntita;
	}
	public java.lang.String getDescStatoTipoDocIndex() {
		return descStatoTipoDocIndex;
	}
	public void setDescStatoTipoDocIndex(java.lang.String descStatoTipoDocIndex) {
		this.descStatoTipoDocIndex = descStatoTipoDocIndex;
	}
	@Override
	public String toString() {
		return "FileDTO [ codiceVisualizzato=" + codiceVisualizzato
				+ ", descBreveStatoDocSpesa=" + descBreveStatoDocSpesa + ", dtInserimento=" + dtInserimento
				+ ", dtAggiornamento=" + dtAggiornamento + ", idFolder=" + idFolder + ", idDocumentoIndex="
				+ idDocumentoIndex + ", idEntita=" + idEntita + ", idProgetto=" + idProgetto + ", idTarget=" + idTarget
				+ ", idStatoDocumentoSpesa=" + idStatoDocumentoSpesa + ", isLocked=" + isLocked + ", nomeFile="
				+ nomeFile + ", numProtocollo=" + numProtocollo + ", sizeFile=" + sizeFile + ", entityAssociated="
				+ entityAssociated + ", dtEntita=" + dtEntita + ", flagEntita=" + flagEntita
				+ ", descStatoTipoDocIndex=" + descStatoTipoDocIndex + "]";
	}

}
