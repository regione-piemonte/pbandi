/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

public class AtlanteVO {
	
	private String idNazione;
	private String idRegione;
	private String idProvincia;
	private String idComune;
	private String descNazione;
	private String descRegione;
	private String descProvincia;
	private String descComune;
	private String Cap; 
	private String siglaProvincia; 
	
	
	
	

	
	public String getSiglaProvincia() {
		return siglaProvincia;
	}
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}
	public String getCap() {
		return Cap;
	}
	public void setCap(String cap) {
		Cap = cap;
	}
	public String getIdNazione() {
		return idNazione;
	}
	public void setIdNazione(String idNazione) {
		this.idNazione = idNazione;
	}
	public String getIdRegione() {
		return idRegione;
	}
	public void setIdRegione(String idRegione) {
		this.idRegione = idRegione;
	}
	public String getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
	public String getIdComune() {
		return idComune;
	}
	public void setIdComune(String idComune) {
		this.idComune = idComune;
	}
	public String getDescNazione() {
		return descNazione;
	}
	public void setDescNazione(String descNazione) {
		this.descNazione = descNazione;
	}
	public String getDescRegione() {
		return descRegione;
	}
	public void setDescRegione(String descRegione) {
		this.descRegione = descRegione;
	}
	public String getDescProvincia() {
		return descProvincia;
	}
	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}
	public String getDescComune() {
		return descComune;
	}
	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}
	
	@Override
	public String toString() {
		return "AtlanteVO [idNazione=" + idNazione + ", idRegione=" + idRegione + ", idProvincia=" + idProvincia
				+ ", idComune=" + idComune + ", descNazione=" + descNazione + ", descRegione=" + descRegione
				+ ", descProvincia=" + descProvincia + ", descComune=" + descComune + "]";
	}

}
