/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto.schedaProgetto;

public class AltroSoggetto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id = null;
	private java.lang.String descSoggetto = null;
	private java.lang.String codiceFiscale = null;
	private java.lang.String denominazione = null;
	private java.lang.String descRelazioneColBeneficiario = null;
	private java.lang.String descRuolo = null;
	private java.lang.String idRelazioneColBeneficiario = null;
	public java.lang.Long getId() {
		return id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public java.lang.String getDescSoggetto() {
		return descSoggetto;
	}
	public void setDescSoggetto(java.lang.String descSoggetto) {
		this.descSoggetto = descSoggetto;
	}
	public java.lang.String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(java.lang.String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public java.lang.String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(java.lang.String denominazione) {
		this.denominazione = denominazione;
	}
	public java.lang.String getDescRelazioneColBeneficiario() {
		return descRelazioneColBeneficiario;
	}
	public void setDescRelazioneColBeneficiario(java.lang.String descRelazioneColBeneficiario) {
		this.descRelazioneColBeneficiario = descRelazioneColBeneficiario;
	}
	public java.lang.String getDescRuolo() {
		return descRuolo;
	}
	public void setDescRuolo(java.lang.String descRuolo) {
		this.descRuolo = descRuolo;
	}
	public java.lang.String getIdRelazioneColBeneficiario() {
		return idRelazioneColBeneficiario;
	}
	public void setIdRelazioneColBeneficiario(java.lang.String idRelazioneColBeneficiario) {
		this.idRelazioneColBeneficiario = idRelazioneColBeneficiario;
	}

}
