/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;

import it.csi.pbandi.pbweberog.dto.monitoraggiocontrolli.FiltroRilevazione;

public class RequestGetProgettiCampione implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FiltroRilevazione filtro;
	private String idProgetti;
	public FiltroRilevazione getFiltro() {
		return filtro;
	}
	public void setFiltro(FiltroRilevazione filtro) {
		this.filtro = filtro;
	}
	public String getIdProgetti() {
		return idProgetti;
	}
	public void setIdProgetti(String idProgetti) {
		this.idProgetti = idProgetti;
	}
	
	
	
}
