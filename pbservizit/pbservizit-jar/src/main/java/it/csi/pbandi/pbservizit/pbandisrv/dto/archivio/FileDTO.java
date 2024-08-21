/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.archivio;

public class FileDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private byte[] bytes = null;

	public void setBytes(byte[] val) {
		bytes = val;
	}

	public byte[] getBytes() {
		return bytes;
	}

	private String codiceVisualizzato = null;
	private String descBreveStatoDocSpesa = null;
	private Long idFolder = null;
	private Long idDocumentoIndex = null;
	private Long idEntita = null;
	private Long idProgetto = null;
	private Long idTipoDocumentoIndex = null;

	private java.util.Date dtInserimento = null;

	public void setDtInserimento(java.util.Date val) {
		dtInserimento = val;
	}

	public java.util.Date getDtInserimento() {
		return dtInserimento;
	}

	private java.util.Date dtAggiornamento = null;

	public void setDtAggiornamento(java.util.Date val) {
		dtAggiornamento = val;
	}

	public java.util.Date getDtAggiornamento() {
		return dtAggiornamento;
	}

	private Long idTarget = null;
	private Long idStatoDocumentoSpesa = null;
	private Boolean isLocked = null;
	private String nomeFile = null;
	private String numProtocollo = null;
	private Long sizeFile = null;
	private Long entityAssociated = null;
	private String flagEntita = null;
	private String descStatoTipoDocIndex = null;

	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}

	public void setCodiceVisualizzato(String val) {
		codiceVisualizzato = val;
	}

	public String getDescBreveStatoDocSpesa() {
		return descBreveStatoDocSpesa;
	}

	public void setDescBreveStatoDocSpesa(String val) {
		descBreveStatoDocSpesa = val;
	}

	public Long getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(Long val) {
		idFolder = val;
	}

	public Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	public void setIdDocumentoIndex(Long val) {
		idDocumentoIndex = val;
	}

	public Long getIdEntita() {
		return idEntita;
	}

	public void setIdEntita(Long val) {
		idEntita = val;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long val) {
		idProgetto = val;
	}

	public Long getIdTarget() {
		return idTarget;
	}

	public void setIdTarget(Long val) {
		idTarget = val;
	}

	public Long getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}

	public void setIdStatoDocumentoSpesa(Long val) {
		idStatoDocumentoSpesa = val;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean val) {
		isLocked = val;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String val) {
		nomeFile = val;
	}

	public String getNumProtocollo() {
		return numProtocollo;
	}

	public void setNumProtocollo(String val) {
		numProtocollo = val;
	}

	public Long getSizeFile() {
		return sizeFile;
	}

	public void setSizeFile(Long val) {
		sizeFile = val;
	}

	private java.util.Date dtEntita = null;

	public void setDtEntita(java.util.Date val) {
		dtEntita = val;
	}

	public java.util.Date getDtEntita() {
		return dtEntita;
	}

	public Long getEntityAssociated() {
		return entityAssociated;
	}

	public void setEntityAssociated(Long val) {
		entityAssociated = val;
	}

	public String getFlagEntita() {
		return flagEntita;
	}

	public void setFlagEntita(String val) {
		flagEntita = val;
	}

	public String getDescStatoTipoDocIndex() {
		return descStatoTipoDocIndex;
	}

	public void setDescStatoTipoDocIndex(String val) {
		descStatoTipoDocIndex = val;
	}

	public Long getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}

	public void setIdTipoDocumentoIndex(Long idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
}