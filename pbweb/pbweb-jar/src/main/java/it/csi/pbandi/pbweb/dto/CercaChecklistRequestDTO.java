/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

public class CercaChecklistRequestDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private String dichiarazioneSpesa;
	private String dataControllo;
	private String soggettoControllo;
	private String[] stati;
	private String tipologia;
	private String rilevazione;
	private String versione;
	private String idProgetto;

	public String getDichiarazioneSpesa() {
		return dichiarazioneSpesa;
	}

	public void setDichiarazioneSpesa(String dichiarazioneSpesa) {
		this.dichiarazioneSpesa = dichiarazioneSpesa;
	}

	public String getDataControllo() {
		return dataControllo;
	}

	public void setDataControllo(String dataControllo) {
		this.dataControllo = dataControllo;
	}

	public String getSoggettoControllo() {
		return soggettoControllo;
	}

	public void setSoggettoControllo(String soggettoControllo) {
		this.soggettoControllo = soggettoControllo;
	}

	public String[] getStati() {
		return stati;
	}

	public void setStati(String[] stati) {
		this.stati = stati;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getRilevazione() {
		return rilevazione;
	}

	public void setRilevazione(String rilevazione) {
		this.rilevazione = rilevazione;
	}

	public String getVersione() {
		return versione;
	}

	public void setVersione(String versione) {
		this.versione = versione;
	}

	public String getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(String idProgetto) {
		this.idProgetto = idProgetto;
	}

}
