/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;

import it.csi.pbandi.pbweberog.dto.recupero.DettaglioRecupero;

public class RequestModificaRecupero implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idProgetto;
	private DettaglioRecupero recupero;
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public DettaglioRecupero getRecupero() {
		return recupero;
	}
	public void setRecupero(DettaglioRecupero recupero) {
		this.recupero = recupero;
	}
	
	

}
