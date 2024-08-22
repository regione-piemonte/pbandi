/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

public class InfoDaNumeroDomanda {
	
	private Integer idDomanda;
	private Integer idProgetto;
	private String codiceVisualizzato;
	private Integer idSoggetto;
	private Integer idPersonaFisica;
	private String codiceFiscaleSoggetto;
	private String nome;
	private String cognome;
	
	public Integer getIdDomanda() {
		return idDomanda;
	}
	public void setIdDomanda(Integer idDomanda) {
		this.idDomanda = idDomanda;
	}
	public Integer getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Integer idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public Integer getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Integer idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public Integer getIdPersonaFisica() {
		return idPersonaFisica;
	}
	public void setIdPersonaFisica(Integer idPersonaFisica) {
		this.idPersonaFisica = idPersonaFisica;
	}
	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}
	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

}
