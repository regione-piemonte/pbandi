/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

public class ProgettoNuovaCertificazione {
	private Long idDettPropostaCertif;
	private Double nuovoImportoCertificazioneNetto;
	private String nota;
	private Long idPropostaCertificazione;
	private Long idProgetto;
	public Long getIdDettPropostaCertif() {
		return idDettPropostaCertif;
	}
	public void setIdDettPropostaCertif(Long idDettPropostaCertif) {
		this.idDettPropostaCertif = idDettPropostaCertif;
	}
	public Double getNuovoImportoCertificazioneNetto() {
		return nuovoImportoCertificazioneNetto;
	}
	public void setNuovoImportoCertificazioneNetto(Double nuovoImportoCertificazioneNetto) {
		this.nuovoImportoCertificazioneNetto = nuovoImportoCertificazioneNetto;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public Long getIdPropostaCertificazione() {
		return idPropostaCertificazione;
	}
	public void setIdPropostaCertificazione(Long idPropostaCertificazione) {
		this.idPropostaCertificazione = idPropostaCertificazione;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	
}
