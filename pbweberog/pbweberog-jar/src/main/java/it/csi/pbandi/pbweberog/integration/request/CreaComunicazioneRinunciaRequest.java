/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;

import it.csi.pbandi.pbweberog.dto.rinuncia.DichiarazioneRinuncia;

public class CreaComunicazioneRinunciaRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idProgetto;
	private DichiarazioneRinuncia dichiarazioneRinuncia;
	private Long idDelegato;
	
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public DichiarazioneRinuncia getDichiarazioneRinuncia() {
		return dichiarazioneRinuncia;
	}
	public void setDichiarazioneRinuncia(DichiarazioneRinuncia dichiarazioneRinuncia) {
		this.dichiarazioneRinuncia = dichiarazioneRinuncia;
	}
	public Long getIdDelegato() {
		return idDelegato;
	}
	public void setIdDelegato(Long idDelegato) {
		this.idDelegato = idDelegato;
	}
	
	
	
	
	

}
