/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;

import it.csi.pbandi.pbweberog.dto.datiprogetto.Recapiti;

public class RequestSalvaRecapito implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idProgetto;
	private Recapiti recapito;
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Recapiti getRecapito() {
		return recapito;
	}
	public void setRecapito(Recapiti recapito) {
		this.recapito = recapito;
	}
	
	
}
