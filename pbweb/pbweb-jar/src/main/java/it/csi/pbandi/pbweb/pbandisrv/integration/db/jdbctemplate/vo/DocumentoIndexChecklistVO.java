package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class DocumentoIndexChecklistVO extends GenericVO {
	private BigDecimal idDocumentoIndex;
	private BigDecimal idProgetto;
	private BigDecimal idTarget;
	private BigDecimal idEntita;
	private String nomeFile;
	private String descBreveStatoTipDocIndex;
	private String descStatoTipoDocIndex;
	private String descBreveTipoDocIndex;
	private String descTipoDocIndex;
	private String soggettoControllore;
	private String flagIrregolarita;
	private Date dtControllo;
	private BigDecimal versione;

	public BigDecimal getVersione() {
		return versione;
	}

	public void setVersione(BigDecimal versione) {
		this.versione = versione;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public BigDecimal getIdTarget() {
		return idTarget;
	}

	public void setIdTarget(BigDecimal idTarget) {
		this.idTarget = idTarget;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getDescTipoDocIndex() {
		return descTipoDocIndex;
	}

	public void setDescTipoDocIndex(String descTipoDocIndex) {
		this.descTipoDocIndex = descTipoDocIndex;
	}

	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getDescBreveStatoTipDocIndex() {
		return descBreveStatoTipDocIndex;
	}

	public void setDescBreveStatoTipDocIndex(String descBreveStatoTipDocIndex) {
		this.descBreveStatoTipDocIndex = descBreveStatoTipDocIndex;
	}

	public String getDescStatoTipoDocIndex() {
		return descStatoTipoDocIndex;
	}

	public void setDescStatoTipoDocIndex(String descStatoTipoDocIndex) {
		this.descStatoTipoDocIndex = descStatoTipoDocIndex;
	}

	public String getDescBreveTipoDocIndex() {
		return descBreveTipoDocIndex;
	}

	public void setDescBreveTipoDocIndex(String descBreveTipoDocIndex) {
		this.descBreveTipoDocIndex = descBreveTipoDocIndex;
	}

	public String getSoggettoControllore() {
		return soggettoControllore;
	}

	public void setSoggettoControllore(String soggettoControllore) {
		this.soggettoControllore = soggettoControllore;
	}

	public String getFlagIrregolarita() {
		return flagIrregolarita;
	}

	public void setFlagIrregolarita(String flagIrregolarita) {
		this.flagIrregolarita = flagIrregolarita;
	}

	public Date getDtControllo() {
		return dtControllo;
	}

	public void setDtControllo(Date dtControllo) {
		this.dtControllo = dtControllo;
	}

	public void setIdEntita(BigDecimal idEntita) {
		this.idEntita = idEntita;
	}

	public BigDecimal getIdEntita() {
		return idEntita;
	}
}
