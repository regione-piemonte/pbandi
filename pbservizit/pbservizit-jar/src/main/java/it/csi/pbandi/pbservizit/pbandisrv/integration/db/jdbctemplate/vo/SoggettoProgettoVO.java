/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class SoggettoProgettoVO extends GenericVO {
	
	private BigDecimal idProgetto;
	private BigDecimal idSoggetto;
	private String codiceFiscaleSoggetto;
	private BigDecimal idTipoAnagrafica;
	private String descBreveTipoAnagrafica;
	private String descTipoAnagrafica;
	private String denominazione;
	private BigDecimal idTipoSoggettoCorrelato;
	private BigDecimal progrSoggettiCorrelati;
	private String descTipoSoggettoCorrelato;
	private String descTipoSoggetto;
	private BigDecimal progrSoggettoProgetto;
	private Date dtFineValidita;
	private BigDecimal idTipoSoggetto;
	private BigDecimal flagPubblicoPrivato;
//	private String codUniIpa;

	public BigDecimal getIdTipoSoggetto() {
		return idTipoSoggetto;
	}
	public void setIdTipoSoggetto(BigDecimal idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}
	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}
	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	public String getDescBreveTipoAnagrafica() {
		return descBreveTipoAnagrafica;
	}
	public void setDescBreveTipoAnagrafica(String descBreveTipoAnagrafica) {
		this.descBreveTipoAnagrafica = descBreveTipoAnagrafica;
	}
	public String getDescTipoAnagrafica() {
		return descTipoAnagrafica;
	}
	public void setDescTipoAnagrafica(String descTipoAnagrafica) {
		this.descTipoAnagrafica = descTipoAnagrafica;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public BigDecimal getIdTipoSoggettoCorrelato() {
		return idTipoSoggettoCorrelato;
	}
	public void setIdTipoSoggettoCorrelato(BigDecimal idTipoSoggettoCorrelato) {
		this.idTipoSoggettoCorrelato = idTipoSoggettoCorrelato;
	}
	public BigDecimal getProgrSoggettiCorrelati() {
		return progrSoggettiCorrelati;
	}
	public void setProgrSoggettiCorrelati(BigDecimal progrSoggettiCorrelati) {
		this.progrSoggettiCorrelati = progrSoggettiCorrelati;
	}
	public String getDescTipoSoggettoCorrelato() {
		return descTipoSoggettoCorrelato;
	}
	public void setDescTipoSoggettoCorrelato(String descTipoSoggettoCorrelato) {
		this.descTipoSoggettoCorrelato = descTipoSoggettoCorrelato;
	}
	public String getDescTipoSoggetto() {
		return descTipoSoggetto;
	}
	public void setDescTipoSoggetto(String descTipoSoggetto) {
		this.descTipoSoggetto = descTipoSoggetto;
	}
	public void setProgrSoggettoProgetto(BigDecimal progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}
	public BigDecimal getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}
	public BigDecimal getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}
	public void setFlagPubblicoPrivato(BigDecimal flagPubblicoPrivato) {
		this.flagPubblicoPrivato = flagPubblicoPrivato;
	}
}
