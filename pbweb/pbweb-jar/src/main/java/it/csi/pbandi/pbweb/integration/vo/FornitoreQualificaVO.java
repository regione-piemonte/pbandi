/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class FornitoreQualificaVO {
	
	static final long serialVersionUID = 1;
	
	private BigDecimal idFornitore;
    private BigDecimal idSoggettoFornitore;
    private BigDecimal idTipoSoggetto;
    private BigDecimal idFormaGiuridica;
    private String descBreveTipoSoggetto;
    private String descTipoSoggetto;
    private String partitaIvaFornitore;
    private String denominazioneFornitore;
    private String codiceFiscaleFornitore;
    private String cognomeFornitore;
    private String nomeFornitore;
    private Date dtFineValiditaFornitore;
    private BigDecimal progrFornitoreQualifica;
    private BigDecimal costoOrario;
    private String noteQualifica;
    private Date dtFineValiditaFornQual;
    private BigDecimal idQualifica;
    private String descBreveQualifica;
    private String descQualifica;
    private Date dtFineValiditaQualifica;
    private String flagHasQualifica;
	
	public BigDecimal getIdFormaGiuridica() {
		return idFormaGiuridica;
	}
	public void setIdFormaGiuridica(BigDecimal idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}
	public String getFlagHasQualifica() {
		return flagHasQualifica;
	}
	public void setFlagHasQualifica(String flagHasQualifica) {
		this.flagHasQualifica = flagHasQualifica;
	}
	public BigDecimal getIdFornitore() {
		return idFornitore;
	}
	public void setIdFornitore(BigDecimal idFornitore) {
		this.idFornitore = idFornitore;
	}
	public BigDecimal getIdSoggettoFornitore() {
		return idSoggettoFornitore;
	}
	public void setIdSoggettoFornitore(BigDecimal idSoggettoFornitore) {
		this.idSoggettoFornitore = idSoggettoFornitore;
	}
	public BigDecimal getIdTipoSoggetto() {
		return idTipoSoggetto;
	}
	public void setIdTipoSoggetto(BigDecimal idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}
	public String getDescBreveTipoSoggetto() {
		return descBreveTipoSoggetto;
	}
	public void setDescBreveTipoSoggetto(String descBreveTipoSoggetto) {
		this.descBreveTipoSoggetto = descBreveTipoSoggetto;
	}
	public String getDescTipoSoggetto() {
		return descTipoSoggetto;
	}
	public void setDescTipoSoggetto(String descTipoSoggetto) {
		this.descTipoSoggetto = descTipoSoggetto;
	}
	public String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}
	public void setPartitaIvaFornitore(String partitaIvaFornitore) {
		this.partitaIvaFornitore = partitaIvaFornitore;
	}
	public String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}
	public void setDenominazioneFornitore(String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}
	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}
	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}
	public String getCognomeFornitore() {
		return cognomeFornitore;
	}
	public void setCognomeFornitore(String cognomeFornitore) {
		this.cognomeFornitore = cognomeFornitore;
	}
	public String getNomeFornitore() {
		return nomeFornitore;
	}
	public void setNomeFornitore(String nomeFornitore) {
		this.nomeFornitore = nomeFornitore;
	}
	public Date getDtFineValiditaFornitore() {
		return dtFineValiditaFornitore;
	}
	public void setDtFineValiditaFornitore(Date dtFineValiditaFornitore) {
		this.dtFineValiditaFornitore = dtFineValiditaFornitore;
	}
	public BigDecimal getProgrFornitoreQualifica() {
		return progrFornitoreQualifica;
	}
	public void setProgrFornitoreQualifica(BigDecimal progrFornitoreQualifica) {
		this.progrFornitoreQualifica = progrFornitoreQualifica;
	}
	public BigDecimal getCostoOrario() {
		return costoOrario;
	}
	public void setCostoOrario(BigDecimal costoOrario) {
		this.costoOrario = costoOrario;
	}
	public String getNoteQualifica() {
		return noteQualifica;
	}
	public void setNoteQualifica(String noteQualifica) {
		this.noteQualifica = noteQualifica;
	}
	public Date getDtFineValiditaFornQual() {
		return dtFineValiditaFornQual;
	}
	public void setDtFineValiditaFornQual(Date dtFineValiditaFornQual) {
		this.dtFineValiditaFornQual = dtFineValiditaFornQual;
	}
	public BigDecimal getIdQualifica() {
		return idQualifica;
	}
	public void setIdQualifica(BigDecimal idQualifica) {
		this.idQualifica = idQualifica;
	}
	public String getDescBreveQualifica() {
		return descBreveQualifica;
	}
	public void setDescBreveQualifica(String descBreveQualifica) {
		this.descBreveQualifica = descBreveQualifica;
	}
	public String getDescQualifica() {
		return descQualifica;
	}
	public void setDescQualifica(String descQualifica) {
		this.descQualifica = descQualifica;
	}
	public Date getDtFineValiditaQualifica() {
		return dtFineValiditaQualifica;
	}
	public void setDtFineValiditaQualifica(Date dtFineValiditaQualifica) {
		this.dtFineValiditaQualifica = dtFineValiditaQualifica;
	}

}
