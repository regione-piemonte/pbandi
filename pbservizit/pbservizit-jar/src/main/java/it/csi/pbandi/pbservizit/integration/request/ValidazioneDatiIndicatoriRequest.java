/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.request;

import java.util.ArrayList;

import it.csi.pbandi.pbservizit.dto.indicatori.IndicatoreItem;

public class ValidazioneDatiIndicatoriRequest {
	
	private ArrayList<IndicatoreItem> indicatori;
	private ArrayList<IndicatoreItem> altriIndicatori;
	
	private Long idProgetto;
	
	public ArrayList<IndicatoreItem> getIndicatori() {
		return indicatori;
	}
	public void setIndicatori(ArrayList<IndicatoreItem> indicatori) {
		this.indicatori = indicatori;
	}	
	
	public ArrayList<IndicatoreItem> getAltriIndicatori() {
		return altriIndicatori;
	}
	public void setAltriIndicatori(ArrayList<IndicatoreItem> altriIndicatori) {
		this.altriIndicatori = altriIndicatori;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	

	
}
