/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class SoggettiFinanziatoriPerProgettoVO extends
		GenericVO {
	
	private String descBreveSoggFinanziatore;
	private String descBreveTipoSoggFinanziat;
	private String descPeriodo;
	private String descPeriodoVisualizzata;
	private String descSoggFinanziatore;
	private String estremiProvv;
	private String flagAgevolato;
	private String flagCertificazione;
    private String flagEconomie;
    private String flagLvlprj;
    private BigDecimal id_utente_ins;
    private BigDecimal id_utente_agg;
	private BigDecimal idProgetto;
	private BigDecimal idComune;
	private BigDecimal idDelibera;
	private BigDecimal idNorma;
	private BigDecimal idPeriodo;
	private BigDecimal idProvincia;
    private BigDecimal idSoggetto;
    private BigDecimal idSoggettoFinanziatore;
    private BigDecimal idTipoSoggFinanziat;
    private BigDecimal impQuotaSoggFinanziatore;
	private String note;
	private BigDecimal percQuotaSoggFinanziatore;
	private BigDecimal percQuotaDefault;
	private BigDecimal progrProgSoggFinanziat;

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getFlagLvlprj() {
		return flagLvlprj;
	}
	public void setFlagLvlprj(String flagLvlprj) {
		this.flagLvlprj = flagLvlprj;
	}
	public BigDecimal getIdSoggettoFinanziatore() {
		return idSoggettoFinanziatore;
	}
	public void setIdSoggettoFinanziatore(BigDecimal idSoggettoFinanziatore) {
		this.idSoggettoFinanziatore = idSoggettoFinanziatore;
	}
	public String getEstremiProvv() {
		return estremiProvv;
	}
	public void setEstremiProvv(String estremiProvv) {
		this.estremiProvv = estremiProvv;
	}
	public BigDecimal getPercQuotaSoggFinanziatore() {
		return percQuotaSoggFinanziatore;
	}
	public void setPercQuotaSoggFinanziatore(BigDecimal percQuotaSoggFinanziatore) {
		this.percQuotaSoggFinanziatore = percQuotaSoggFinanziatore;
	}
	public BigDecimal getId_utente_ins() {
		return id_utente_ins;
	}
	public void setId_utente_ins(BigDecimal idUtenteIns) {
		id_utente_ins = idUtenteIns;
	}
	public BigDecimal getId_utente_agg() {
		return id_utente_agg;
	}
	public void setId_utente_agg(BigDecimal idUtenteAgg) {
		id_utente_agg = idUtenteAgg;
	}
	public String getDescBreveSoggFinanziatore() {
		return descBreveSoggFinanziatore;
	}
	public void setDescBreveSoggFinanziatore(String descBreveSoggFinanziatore) {
		this.descBreveSoggFinanziatore = descBreveSoggFinanziatore;
	}
	public String getDescSoggFinanziatore() {
		return descSoggFinanziatore;
	}
	public void setDescSoggFinanziatore(String descSoggFinanziatore) {
		this.descSoggFinanziatore = descSoggFinanziatore;
	}
	public void setProgrProgSoggFinanziat(BigDecimal progrProgSoggFinanziat) {
		this.progrProgSoggFinanziat = progrProgSoggFinanziat;
	}
	public BigDecimal getProgrProgSoggFinanziat() {
		return progrProgSoggFinanziat;
	}
	public String getFlagEconomie() {
		return flagEconomie;
	}
	public void setFlagEconomie(String flagEconomie) {
		this.flagEconomie = flagEconomie;
	}
	public BigDecimal getIdComune() {
		return idComune;
	}
	public void setIdComune(BigDecimal idComune) {
		this.idComune = idComune;
	}
	public BigDecimal getIdDelibera() {
		return idDelibera;
	}
	public void setIdDelibera(BigDecimal idDelibera) {
		this.idDelibera = idDelibera;
	}
	public BigDecimal getIdNorma() {
		return idNorma;
	}
	public void setIdNorma(BigDecimal idNorma) {
		this.idNorma = idNorma;
	}
	public BigDecimal getIdPeriodo() {
		return idPeriodo;
	}
	public void setIdPeriodo(BigDecimal idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	public String getDescPeriodo() {
		return descPeriodo;
	}
	public void setDescPeriodo(String descPeriodo) {
		this.descPeriodo = descPeriodo;
	}
	public BigDecimal getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(BigDecimal idProvincia) {
		this.idProvincia = idProvincia;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setFlagCertificazione(String flagCertificazione) {
		this.flagCertificazione = flagCertificazione;
	}
	public String getFlagCertificazione() {
		return flagCertificazione;
	}
	public void setIdTipoSoggFinanziat(BigDecimal idTipoSoggFinanziat) {
		this.idTipoSoggFinanziat = idTipoSoggFinanziat;
	}
	public BigDecimal getIdTipoSoggFinanziat() {
		return idTipoSoggFinanziat;
	}
	public void setDescBreveTipoSoggFinanziat(String descBreveTipoSoggFinanziat) {
		this.descBreveTipoSoggFinanziat = descBreveTipoSoggFinanziat;
	}
	public String getDescBreveTipoSoggFinanziat() {
		return descBreveTipoSoggFinanziat;
	}
	public void setImpQuotaSoggFinanziatore(BigDecimal impQuotaSoggFinanziatore) {
		this.impQuotaSoggFinanziatore = impQuotaSoggFinanziatore;
	}
	public BigDecimal getImpQuotaSoggFinanziatore() {
		return impQuotaSoggFinanziatore;
	}
	public void setFlagAgevolato(String flagAgevolato) {
		this.flagAgevolato = flagAgevolato;
	}
	public String getFlagAgevolato() {
		return flagAgevolato;
	}
	public String getDescPeriodoVisualizzata() {
		return descPeriodoVisualizzata;
	}
	public void setDescPeriodoVisualizzata(String descPeriodoVisualizzata) {
		this.descPeriodoVisualizzata = descPeriodoVisualizzata;
	}
	public BigDecimal getPercQuotaDefault() {
		return percQuotaDefault;
	}
	public void setPercQuotaDefault(BigDecimal percQuotaDefault) {
		this.percQuotaDefault = percQuotaDefault;
	}
	
	
}
