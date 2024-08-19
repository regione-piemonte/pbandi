/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;

import it.csi.pbandi.pbweberog.dto.fideiussioni.Fideiussione;

public class CreaAggiornaFideiussioneRequest {
	
	private Long idProgetto;
	private Fideiussione fideiussione;
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Fideiussione getFideiussione() {
		return fideiussione;
	}
	public void setFideiussione(Fideiussione fideiussione) {
		this.fideiussione = fideiussione;
	}
	
	
}
