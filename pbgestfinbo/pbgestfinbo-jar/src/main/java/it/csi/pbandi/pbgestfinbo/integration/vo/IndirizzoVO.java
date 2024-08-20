/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

public class IndirizzoVO {

	private Long idIndirizzo;
	private Long idNazione; 
	private Long idComune; 
	private Long idComuneEstero; 
	private Long idRegione; 
	private Long idProvincia;
	public Long getIdIndirizzo() {
		return idIndirizzo;
	}
	public void setIdIndirizzo(Long idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	public Long getIdNazione() {
		return idNazione;
	}
	public void setIdNazione(Long idNazione) {
		this.idNazione = idNazione;
	}
	public Long getIdComune() {
		return idComune;
	}
	public void setIdComune(Long idComune) {
		this.idComune = idComune;
	}
	public Long getIdComuneEstero() {
		return idComuneEstero;
	}
	public void setIdComuneEstero(Long idComuneEstero) {
		this.idComuneEstero = idComuneEstero;
	}
	public Long getIdRegione() {
		return idRegione;
	}
	public void setIdRegione(Long idRegione) {
		this.idRegione = idRegione;
	}
	public Long getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(Long idProvincia) {
		this.idProvincia = idProvincia;
	} 
	
}
