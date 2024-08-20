/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.util.Date;

public class SearchVO {

	private String cf;
	private Long idSoggetto;
	private String pIva;
	private String denominazione;
	private String nome;
	private String cognome;
	private Long idDomanda;
	private Long idProgetto;
	private Long idSede;
	private String descTipoSogg;
	private Long idEnteGiuridico;
	private Date dtFineValidita;
	private String flagBloccoAnag;
	private String ndg; 
	
	
	
	
	public String getNdg() {
		return ndg;
	}
	public void setNdg(String ndg) {
		this.ndg = ndg;
	}
	public SearchVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public Long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getpIva() {
		return pIva;
	}
	public void setpIva(String pIva) {
		this.pIva = pIva;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
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
	public Long getIdDomanda() {
		return idDomanda;
	}
	public void setIdDomanda(Long idDomanda) {
		this.idDomanda = idDomanda;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdSede() {
		return idSede;
	}
	public void setIdSede(Long idSede) {
		this.idSede = idSede;
	}
	public String getDescTipoSogg() {
		return descTipoSogg;
	}
	public void setDescTipoSogg(String descTipoSogg) {
		this.descTipoSogg = descTipoSogg;
	}
	public Long getIdEnteGiuridico() {
		return idEnteGiuridico;
	}
	public void setIdEnteGiuridico(Long idEnteGiuridico) {
		this.idEnteGiuridico = idEnteGiuridico;
	}
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	public String getFlagBloccoAnag() {
		return flagBloccoAnag;
	}
	public void setFlagBloccoAnag(String flagBloccoAnag) {
		this.flagBloccoAnag = flagBloccoAnag;
	}
	
	
	
	
	
}
