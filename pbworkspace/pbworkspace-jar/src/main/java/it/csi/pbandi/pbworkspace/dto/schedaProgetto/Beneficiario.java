/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto.schedaProgetto;

public class Beneficiario implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private java.lang.String nome = null;
	private java.lang.String cognome = null;
	private java.lang.Integer idCombo = null;
	private java.lang.String descrizione = null;
	private java.lang.String codiceFiscale = null;
	private java.lang.String sede = null;
	private java.lang.Long idSoggetto = null;
	private java.lang.Long idBeneficiario = null;

	public Beneficiario() {
		super();
	}

	public java.lang.String getNome() {
		return nome;
	}

	public void setNome(java.lang.String nome) {
		this.nome = nome;
	}

	public java.lang.String getCognome() {
		return cognome;
	}

	public void setCognome(java.lang.String cognome) {
		this.cognome = cognome;
	}

	public java.lang.Integer getIdCombo() {
		return idCombo;
	}

	public void setIdCombo(java.lang.Integer idCombo) {
		this.idCombo = idCombo;
	}

	public java.lang.String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(java.lang.String descrizione) {
		this.descrizione = descrizione;
	}

	public java.lang.String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(java.lang.String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public java.lang.String getSede() {
		return sede;
	}

	public void setSede(java.lang.String sede) {
		this.sede = sede;
	}

	public java.lang.Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(java.lang.Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public java.lang.Long getIdBeneficiario() {
		return idBeneficiario;
	}

	public void setIdBeneficiario(java.lang.Long idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("[Beneficiario]");
		sb.append("\n CodiceFiscale=" + getCodiceFiscale());
		sb.append("\n getCognome=" + getCognome());
		sb.append("\n getNome=" + getNome());
		sb.append("\n getDescrizione=" + getDescrizione());
		sb.append("\n getIdBeneficiario=" + getIdBeneficiario());
		sb.append("\n getIdCombo=" + getIdCombo());
		sb.append("\n getIdSoggetto=" + getIdSoggetto());
		sb.append("\n getSede=" + getSede());

		return sb.toString();
	}
}
