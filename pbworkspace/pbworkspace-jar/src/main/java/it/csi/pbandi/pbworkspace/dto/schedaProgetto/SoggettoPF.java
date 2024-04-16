/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto.schedaProgetto;

public class SoggettoPF implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private java.lang.String iban = null;
	private java.lang.String cognome = null;
	private java.lang.String nome = null;
	private java.lang.String sesso = null;
	private java.lang.String dataNascita = null;
	private java.lang.String indirizzoRes = null;
	private java.lang.String capRes = null;
	private java.lang.String emailRes = null;
	private java.lang.String faxRes = null;
	private java.lang.String telefonoRes = null;
	private it.csi.pbandi.pbworkspace.dto.schedaProgetto.Comune comuneRes = null;
	private it.csi.pbandi.pbworkspace.dto.schedaProgetto.Comune comuneNas = null;
	private java.lang.String codiceFiscale = null;
	private java.util.ArrayList<java.lang.String> ruolo = new java.util.ArrayList<java.lang.String>();
	private java.lang.String idRelazioneColBeneficiario = null;
	private java.lang.String descRelazioneColBeneficiario = null;
	
	private java.lang.String numCivicoRes = null;

	public SoggettoPF() {
		super();
	}

	public java.lang.String getNumCivicoRes() {
		return numCivicoRes;
	}

	public void setNumCivicoRes(java.lang.String numCivicoRes) {
		this.numCivicoRes = numCivicoRes;
	}

	public java.lang.String getIban() {
		return iban;
	}

	public void setIban(java.lang.String iban) {
		this.iban = iban;
	}

	public java.lang.String getCognome() {
		return cognome;
	}

	public void setCognome(java.lang.String cognome) {
		this.cognome = cognome;
	}

	public java.lang.String getNome() {
		return nome;
	}

	public void setNome(java.lang.String nome) {
		this.nome = nome;
	}

	public java.lang.String getSesso() {
		return sesso;
	}

	public void setSesso(java.lang.String sesso) {
		this.sesso = sesso;
	}

	public java.lang.String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(java.lang.String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public java.lang.String getIndirizzoRes() {
		return indirizzoRes;
	}

	public void setIndirizzoRes(java.lang.String indirizzoRes) {
		this.indirizzoRes = indirizzoRes;
	}

	public java.lang.String getCapRes() {
		return capRes;
	}

	public void setCapRes(java.lang.String capRes) {
		this.capRes = capRes;
	}

	public java.lang.String getEmailRes() {
		return emailRes;
	}

	public void setEmailRes(java.lang.String emailRes) {
		this.emailRes = emailRes;
	}

	public java.lang.String getFaxRes() {
		return faxRes;
	}

	public void setFaxRes(java.lang.String faxRes) {
		this.faxRes = faxRes;
	}

	public java.lang.String getTelefonoRes() {
		return telefonoRes;
	}

	public void setTelefonoRes(java.lang.String telefonoRes) {
		this.telefonoRes = telefonoRes;
	}

	public it.csi.pbandi.pbworkspace.dto.schedaProgetto.Comune getComuneRes() {
		return comuneRes;
	}

	public void setComuneRes(it.csi.pbandi.pbworkspace.dto.schedaProgetto.Comune comuneRes) {
		this.comuneRes = comuneRes;
	}

	public it.csi.pbandi.pbworkspace.dto.schedaProgetto.Comune getComuneNas() {
		return comuneNas;
	}

	public void setComuneNas(it.csi.pbandi.pbworkspace.dto.schedaProgetto.Comune comuneNas) {
		this.comuneNas = comuneNas;
	}

	public java.lang.String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(java.lang.String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public java.util.ArrayList<java.lang.String> getRuolo() {
		return ruolo;
	}

	public void setRuolo(java.util.ArrayList<java.lang.String> ruolo) {
		this.ruolo = ruolo;
	}

	public java.lang.String getIdRelazioneColBeneficiario() {
		return idRelazioneColBeneficiario;
	}

	public void setIdRelazioneColBeneficiario(java.lang.String idRelazioneColBeneficiario) {
		this.idRelazioneColBeneficiario = idRelazioneColBeneficiario;
	}

	public java.lang.String getDescRelazioneColBeneficiario() {
		return descRelazioneColBeneficiario;
	}

	public void setDescRelazioneColBeneficiario(java.lang.String descRelazioneColBeneficiario) {
		this.descRelazioneColBeneficiario = descRelazioneColBeneficiario;
	}

}
