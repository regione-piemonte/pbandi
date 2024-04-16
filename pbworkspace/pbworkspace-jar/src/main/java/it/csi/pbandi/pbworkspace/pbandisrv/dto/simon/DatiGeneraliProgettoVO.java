/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv.dto.simon;

public class DatiGeneraliProgettoVO {
	private String codiceCup;
	private String codiceProgetto;
	private String annoDecisione;
	private String natura;
	private String tipologia;
	private String settore;
	private String sottosettore;
	private String categoria;
	private String stato;
	private String dataGenerazione;
	
	public String getProvvisorio() {
		return provvisorio;
	}
	public void setProvvisorio(String provvisorio) {
		this.provvisorio = provvisorio;
	}
	private String provvisorio;
	
	public String getCodiceCup() {
		return codiceCup;
	}
	public void setCodiceCup(String codiceCup) {
		this.codiceCup = codiceCup;
	}
	public String getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	public String getAnnoDecisione() {
		return annoDecisione;
	}
	public void setAnnoDecisione(String annoDecisione) {
		this.annoDecisione = annoDecisione;
	}
	public String getNatura() {
		return natura;
	}
	public void setNatura(String natura) {
		this.natura = natura;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getSettore() {
		return settore;
	}
	public void setSettore(String settore) {
		this.settore = settore;
	}
	public String getSottosettore() {
		return sottosettore;
	}
	public void setSottosettore(String sottosettore) {
		this.sottosettore = sottosettore;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getDataGenerazione() {
		return dataGenerazione;
	}
	public void setDataGenerazione(String dataGenerazione) {
		this.dataGenerazione = dataGenerazione;
	}

}
