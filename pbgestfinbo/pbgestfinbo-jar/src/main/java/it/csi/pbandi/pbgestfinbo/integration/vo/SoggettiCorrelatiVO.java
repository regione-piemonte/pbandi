/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

public class SoggettiCorrelatiVO {
	
	private String nag; // idSoggetto 
	private String nome;
	private String cognome;
	private String codiceFiscale; 
	private String ruolo; 
	private Long quotaPartecipazione; 
	private String tipologia;
	private Long idSoggettoCorellato;
	private String idDomanda;
	private String progSoggDomanda; 
	private String ndg; 
	private int idTipoSoggettoCorrelato; 
	
	
	
	public int getIdTipoSoggettoCorrelato() {
		return idTipoSoggettoCorrelato;
	}
	public void setIdTipoSoggettoCorrelato(int idTipoSoggettoCorrelato) {
		this.idTipoSoggettoCorrelato = idTipoSoggettoCorrelato;
	}
	public String getNdg() {
		return ndg;
	}
	public void setNdg(String ndg) {
		this.ndg = ndg;
	}
	public String getProgSoggDomanda() {
		return progSoggDomanda;
	}
	public void setProgSoggDomanda(String progSoggDomanda) {
		this.progSoggDomanda = progSoggDomanda;
	}
	public String getIdDomanda() {
		return idDomanda;
	}
	public void setIdDomanda(String idDomanda) {
		this.idDomanda = idDomanda;
	}
	public String getNag() {
		return nag;
	}
	public void setNag(String nag) {
		this.nag = nag;
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
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public Long getQuotaPartecipazione() {
		return quotaPartecipazione;
	}
	public void setQuotaPartecipazione(Long quotaPartecipazione) {
		this.quotaPartecipazione = quotaPartecipazione;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public Long getIdSoggettoCorellato() {
		return idSoggettoCorellato;
	}
	public void setIdSoggettoCorellato(Long idSoggettoCorellato) {
		this.idSoggettoCorellato = idSoggettoCorellato;
	} 
	
	
	

}
