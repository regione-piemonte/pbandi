package it.csi.pbandi.pbweb.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class ArchivioFileVO {
	
	private Date  dtInserimentoFile;
	private Date  dtInserimentoFolder;
	private Date  dtAggiornamentoFile;
	private Date  dtAggiornamentoFolder;
	private BigDecimal entitiesAssociated;
	private BigDecimal idDocumentoIndex;
	private BigDecimal idEntita;
	private BigDecimal idFile;
	private BigDecimal idFolder;
	private BigDecimal idPadre;
	private BigDecimal idProgetto;
	private BigDecimal idSoggettoBen;
	private BigDecimal idTarget;
	private String islocked;
	private String nomeFile;
	private String  nomeFolder;
	private String numProtocollo;
	private BigDecimal sizeFile;
	private BigDecimal idFileEntita;
	private String flagEntita;					// Jira PBANDI-2815.
	private String descStatoTipoDocIndex;
	private BigDecimal idProgettoFolder;
	
	public BigDecimal getIdProgettoFolder() {
		return idProgettoFolder;
	}
	public void setIdProgettoFolder(BigDecimal idProgettoFolder) {
		this.idProgettoFolder = idProgettoFolder;
	}
	public String getDescStatoTipoDocIndex() {
		return descStatoTipoDocIndex;
	}
	public void setDescStatoTipoDocIndex(String descStatoTipoDocIndex) {
		this.descStatoTipoDocIndex = descStatoTipoDocIndex;
	}
	public String getFlagEntita() {
		return flagEntita;
	}
	public void setFlagEntita(String flagEntita) {
		this.flagEntita = flagEntita;
	}
	public BigDecimal getIdFileEntita() {
		return idFileEntita;
	}
	public void setIdFileEntita(BigDecimal idFileEntita) {
		this.idFileEntita = idFileEntita;
	}
	public String getNumProtocollo() {
		return numProtocollo;
	}
	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}
 
	public BigDecimal getIdEntita() {
		return idEntita;
	}
	public void setIdEntita(BigDecimal idEntita) {
		this.idEntita = idEntita;
	}
	public BigDecimal getIdTarget() {
		return idTarget;
	}
	public void setIdTarget(BigDecimal idTarget) {
		this.idTarget = idTarget;
	}
	public Date getDtInserimentoFile() {
		return dtInserimentoFile;
	}
	public void setDtInserimentoFile(Date dtInserimentoFile) {
		this.dtInserimentoFile = dtInserimentoFile;
	}
	public Date getDtInserimentoFolder() {
		return dtInserimentoFolder;
	}
	public void setDtInserimentoFolder(Date dtInserimentoFolder) {
		this.dtInserimentoFolder = dtInserimentoFolder;
	}
	public Date getDtAggiornamentoFile() {
		return dtAggiornamentoFile;
	}
	public void setDtAggiornamentoFile(Date dtAggiornamentoFile) {
		this.dtAggiornamentoFile = dtAggiornamentoFile;
	}
	public Date getDtAggiornamentoFolder() {
		return dtAggiornamentoFolder;
	}
	public void setDtAggiornamentoFolder(Date dtAggiornamentoFolder) {
		this.dtAggiornamentoFolder = dtAggiornamentoFolder;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
 
	public BigDecimal getIdFolder() {
		return idFolder;
	}
	public void setIdFolder(BigDecimal idFolder) {
		this.idFolder = idFolder;
	}
	public BigDecimal getIdPadre() {
		return idPadre;
	}
	public void setIdPadre(BigDecimal idPadre) {
		this.idPadre = idPadre;
	}
	public String getNomeFolder() {
		return nomeFolder;
	}
	public void setNomeFolder(String nomeFolder) {
		this.nomeFolder = nomeFolder;
	}
	public BigDecimal getIdSoggettoBen() {
		return idSoggettoBen;
	}
	public void setIdSoggettoBen(BigDecimal idSoggettoBen) {
		this.idSoggettoBen = idSoggettoBen;
	}
	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	public BigDecimal getSizeFile() {
		return sizeFile;
	}
	public void setSizeFile(BigDecimal sizeFile) {
		this.sizeFile = sizeFile;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdFile() {
		return idFile;
	}
	public void setIdFile(BigDecimal idFile) {
		this.idFile = idFile;
	}
	public String getIslocked() {
		return islocked;
	}
	public void setIslocked(String islocked) {
		this.islocked = islocked;
	}
	public BigDecimal getEntitiesAssociated() {
		return entitiesAssociated;
	}
	public void setEntitiesAssociated(BigDecimal entitiesAssociated) {
		this.entitiesAssociated = entitiesAssociated;
	}

}
