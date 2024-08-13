/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class FornitoreQualificaFilterFornitoreVO extends GenericVO {
	
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
    private String flagHasQualifica;
   
	public BigDecimal getIdFormaGiuridica() {
		return idFormaGiuridica;
	}
	public void setIdFormaGiuridica(BigDecimal idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
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
	public String getFlagHasQualifica() {
		return flagHasQualifica;
	}
	public void setFlagHasQualifica(String flagHasQualifica) {
		this.flagHasQualifica = flagHasQualifica;
	}
	
}
