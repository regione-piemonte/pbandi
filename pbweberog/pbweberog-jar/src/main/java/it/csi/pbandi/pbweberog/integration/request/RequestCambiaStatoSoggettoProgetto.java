/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;

public class RequestCambiaStatoSoggettoProgetto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long progrSoggettoProgetto;
	private Long progrSoggettiCorrelati;
	private String codiceFiscale;
	private Long idUtente;
	private Long idSoggetto;
	private String codideRuolo;
	private Long ruoloNuovoUtente;
	private String utenteAbilitatoProgetto;//valori possibili ON/OFF
	private Long idProgetto;
	
	public Long getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}
	public void setProgrSoggettoProgetto(Long progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}
	public Long getProgrSoggettiCorrelati() {
		return progrSoggettiCorrelati;
	}
	public void setProgrSoggettiCorrelati(Long progrSoggettiCorrelati) {
		this.progrSoggettiCorrelati = progrSoggettiCorrelati;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public Long getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}
	public Long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getCodideRuolo() {
		return codideRuolo;
	}
	public void setCodideRuolo(String codideRuolo) {
		this.codideRuolo = codideRuolo;
	}
	public Long getRuoloNuovoUtente() {
		return ruoloNuovoUtente;
	}
	public void setRuoloNuovoUtente(Long ruoloNuovoUtente) {
		this.ruoloNuovoUtente = ruoloNuovoUtente;
	}
	public String getUtenteAbilitatoProgetto() {
		return utenteAbilitatoProgetto;
	}
	public void setUtenteAbilitatoProgetto(String utenteAbilitatoProgetto) {
		this.utenteAbilitatoProgetto = utenteAbilitatoProgetto;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	@Override
	public String toString() {
		return "RequestCambiaStatoSoggettoProgetto [progrSoggettoProgetto=" + progrSoggettoProgetto
				+ ", progrSoggettiCorrelati=" + progrSoggettiCorrelati + ", codiceFiscale=" + codiceFiscale
				+ ", idUtente=" + idUtente + ", idSoggetto=" + idSoggetto + ", codideRuolo=" + codideRuolo
				+ ", ruoloNuovoUtente=" + ruoloNuovoUtente + ", utenteAbilitatoProgetto=" + utenteAbilitatoProgetto
				+ ", idProgetto=" + idProgetto + "]";
	}

}