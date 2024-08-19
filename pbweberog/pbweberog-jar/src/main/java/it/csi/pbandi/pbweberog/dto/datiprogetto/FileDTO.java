/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.datiprogetto;

import java.util.Date;

public class FileDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;

	private byte[] bytes;
	private String codiceVisualizzato;
	private String descBreveStatoDocSpesa;
	private Date dtInserimento;
	private Date dtAggiornamento;
	private Long idFolder;
	private Long idDocumentoIndex;
	private Long idEntita;
	private Long idProgetto;
	private Long idTarget;
	private Long idStatoDocumentoSpesa;
	private Boolean isLocked;
	private String nomeFile;
	private String numProtocollo;
	private Long sizeFile;
	private Long entityAssociated;
	private Date dtEntita;
	private String flagEntita;
	private String descStatoTipoDocIndex;
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public String getDescBreveStatoDocSpesa() {
		return descBreveStatoDocSpesa;
	}
	public void setDescBreveStatoDocSpesa(String descBreveStatoDocSpesa) {
		this.descBreveStatoDocSpesa = descBreveStatoDocSpesa;
	}
	public Date getDtInserimento() {
		return dtInserimento;
	}
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	public Long getIdFolder() {
		return idFolder;
	}
	public void setIdFolder(Long idFolder) {
		this.idFolder = idFolder;
	}
	public Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(Long idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	public Long getIdEntita() {
		return idEntita;
	}
	public void setIdEntita(Long idEntita) {
		this.idEntita = idEntita;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdTarget() {
		return idTarget;
	}
	public void setIdTarget(Long idTarget) {
		this.idTarget = idTarget;
	}
	public Long getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}
	public void setIdStatoDocumentoSpesa(Long idStatoDocumentoSpesa) {
		this.idStatoDocumentoSpesa = idStatoDocumentoSpesa;
	}
	public Boolean getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getNumProtocollo() {
		return numProtocollo;
	}
	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}
	public Long getSizeFile() {
		return sizeFile;
	}
	public void setSizeFile(Long sizeFile) {
		this.sizeFile = sizeFile;
	}
	public Long getEntityAssociated() {
		return entityAssociated;
	}
	public void setEntityAssociated(Long entityAssociated) {
		this.entityAssociated = entityAssociated;
	}
	public Date getDtEntita() {
		return dtEntita;
	}
	public void setDtEntita(Date dtEntita) {
		this.dtEntita = dtEntita;
	}
	public String getFlagEntita() {
		return flagEntita;
	}
	public void setFlagEntita(String flagEntita) {
		this.flagEntita = flagEntita;
	}
	public String getDescStatoTipoDocIndex() {
		return descStatoTipoDocIndex;
	}
	public void setDescStatoTipoDocIndex(String descStatoTipoDocIndex) {
		this.descStatoTipoDocIndex = descStatoTipoDocIndex;
	}
	
	
	
	

}
