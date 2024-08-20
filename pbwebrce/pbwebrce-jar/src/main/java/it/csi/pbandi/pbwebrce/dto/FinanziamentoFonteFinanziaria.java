/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

public class FinanziamentoFonteFinanziaria extends FonteFinanziaria implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private java.lang.Long idPeriodo = null;
	private java.lang.String descPeriodo = null;
	private java.lang.Long idNorma = null;
	private java.lang.Long idDelibera = null;
	private java.lang.Long idComune = null;
	private java.lang.Long idProvincia = null;
	private java.lang.Long idSoggettoPrivato = null;
	private java.lang.String estremiProvvedimento = null;
	private java.lang.String note = null;
	private java.lang.Boolean flagEconomie = null;
	private java.lang.Long progrProgSoggFinanziat = null;
	private java.lang.Boolean flagLvlprj = null;
	private java.lang.Boolean flagCertificazione = null;
	private java.lang.String descBreveTipoSoggFinanziat = null;
	private java.lang.String linkDettaglio = null;
	private java.lang.String linkModifica = null;
	private java.lang.Double percQuotaDefault = null;

	public FinanziamentoFonteFinanziaria() {
		super();
	}

	public java.lang.Long getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(java.lang.Long idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public java.lang.String getDescPeriodo() {
		return descPeriodo;
	}

	public void setDescPeriodo(java.lang.String descPeriodo) {
		this.descPeriodo = descPeriodo;
	}

	public java.lang.Long getIdNorma() {
		return idNorma;
	}

	public void setIdNorma(java.lang.Long idNorma) {
		this.idNorma = idNorma;
	}

	public java.lang.Long getIdDelibera() {
		return idDelibera;
	}

	public void setIdDelibera(java.lang.Long idDelibera) {
		this.idDelibera = idDelibera;
	}

	public java.lang.Long getIdComune() {
		return idComune;
	}

	public void setIdComune(java.lang.Long idComune) {
		this.idComune = idComune;
	}

	public java.lang.Long getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(java.lang.Long idProvincia) {
		this.idProvincia = idProvincia;
	}

	public java.lang.Long getIdSoggettoPrivato() {
		return idSoggettoPrivato;
	}

	public void setIdSoggettoPrivato(java.lang.Long idSoggettoPrivato) {
		this.idSoggettoPrivato = idSoggettoPrivato;
	}

	public java.lang.String getEstremiProvvedimento() {
		return estremiProvvedimento;
	}

	public void setEstremiProvvedimento(java.lang.String estremiProvvedimento) {
		this.estremiProvvedimento = estremiProvvedimento;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.Boolean getFlagEconomie() {
		return flagEconomie;
	}

	public void setFlagEconomie(java.lang.Boolean flagEconomie) {
		this.flagEconomie = flagEconomie;
	}

	public java.lang.Long getProgrProgSoggFinanziat() {
		return progrProgSoggFinanziat;
	}

	public void setProgrProgSoggFinanziat(java.lang.Long progrProgSoggFinanziat) {
		this.progrProgSoggFinanziat = progrProgSoggFinanziat;
	}

	public java.lang.Boolean getFlagLvlprj() {
		return flagLvlprj;
	}

	public void setFlagLvlprj(java.lang.Boolean flagLvlprj) {
		this.flagLvlprj = flagLvlprj;
	}

	public java.lang.Boolean getFlagCertificazione() {
		return flagCertificazione;
	}

	public void setFlagCertificazione(java.lang.Boolean flagCertificazione) {
		this.flagCertificazione = flagCertificazione;
	}

	public java.lang.String getDescBreveTipoSoggFinanziat() {
		return descBreveTipoSoggFinanziat;
	}

	public void setDescBreveTipoSoggFinanziat(java.lang.String descBreveTipoSoggFinanziat) {
		this.descBreveTipoSoggFinanziat = descBreveTipoSoggFinanziat;
	}

	public java.lang.String getLinkDettaglio() {
		return linkDettaglio;
	}

	public void setLinkDettaglio(java.lang.String linkDettaglio) {
		this.linkDettaglio = linkDettaglio;
	}

	public java.lang.String getLinkModifica() {
		return linkModifica;
	}

	public void setLinkModifica(java.lang.String linkModifica) {
		this.linkModifica = linkModifica;
	}

	public java.lang.Double getPercQuotaDefault() {
		return percQuotaDefault;
	}

	public void setPercQuotaDefault(java.lang.Double percQuotaDefault) {
		this.percQuotaDefault = percQuotaDefault;
	}

}
