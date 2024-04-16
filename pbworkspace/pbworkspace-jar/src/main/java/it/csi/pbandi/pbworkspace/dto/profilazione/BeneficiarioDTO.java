/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto.profilazione;

public class BeneficiarioDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private java.lang.Long id_soggetto = null;

	private java.lang.Long id_progetto = null;

	private java.lang.String codiceFiscale = null;

	private java.lang.String cognome = null;

	private java.lang.String nome = null;

	private java.lang.String descrizione = null;

	private java.lang.Integer id = null;

	private java.lang.Long idFormaGiuridica = null;

	private java.lang.Long idDimensioneImpresa = null;

	public java.lang.Long getId_soggetto() {
		return id_soggetto;
	}

	public void setId_soggetto(java.lang.Long id_soggetto) {
		this.id_soggetto = id_soggetto;
	}

	public java.lang.Long getId_progetto() {
		return id_progetto;
	}

	public void setId_progetto(java.lang.Long id_progetto) {
		this.id_progetto = id_progetto;
	}

	public java.lang.String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(java.lang.String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
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

	public java.lang.String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(java.lang.String descrizione) {
		this.descrizione = descrizione;
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Long getIdFormaGiuridica() {
		return idFormaGiuridica;
	}

	public void setIdFormaGiuridica(java.lang.Long idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}

	public java.lang.Long getIdDimensioneImpresa() {
		return idDimensioneImpresa;
	}

	public void setIdDimensioneImpresa(java.lang.Long idDimensioneImpresa) {
		this.idDimensioneImpresa = idDimensioneImpresa;
	}

	
}
