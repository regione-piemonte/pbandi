/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;


public class RichiestaIntegrazioneVO {
	private int idTarget;
	private String utente;
	private int idProgetto;
	
	
	public RichiestaIntegrazioneVO() {
		super();
		//TODO Auto-generated constructor stub
	}


	public RichiestaIntegrazioneVO(int idTarget, String utente, int idProgetto) {
		super();
		this.idTarget = idTarget;
		this.utente = utente;
		this.idProgetto = idProgetto;
	}


	public int getIdProgetto() {
		return idProgetto;
	}


	public void setIdProgetto(int idProgetto) {
		this.idProgetto = idProgetto;
	}


	public void setIdTarget(int idTarget) {
		this.idTarget = idTarget;
	}


	public int getIdTarget() {
		return idTarget;
	}


	public String getUtente() {
		return utente;
	}


	public void setUtente(String utente) {
		this.utente = utente;
	}
	
	
	
}	