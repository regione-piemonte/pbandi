/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import it.csi.pbandi.pbweberog.dto.fideiussioni.FiltroRicercaFideiussione;

public class CercaFideiussioniRequest {
	private FiltroRicercaFideiussione filtro;
	private Long idProgetto;
	public FiltroRicercaFideiussione getFiltro() {
		return filtro;
	}
	public void setFiltro(FiltroRicercaFideiussione filtro) {
		this.filtro = filtro;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	
	
}
