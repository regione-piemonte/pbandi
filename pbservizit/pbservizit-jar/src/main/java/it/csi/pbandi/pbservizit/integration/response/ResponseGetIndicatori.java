/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.response;

import java.util.ArrayList;

import it.csi.pbandi.pbservizit.dto.indicatori.IndicatoreItem;

public class ResponseGetIndicatori {
	private ArrayList<IndicatoreItem> indicatoriMonitoraggio;
	private ArrayList<IndicatoreItem> altriIndicatori;
	public ArrayList<IndicatoreItem> getIndicatoriMonitoraggio() {
		return indicatoriMonitoraggio;
	}
	public void setIndicatoriMonitoraggio(ArrayList<IndicatoreItem> indicatoriMonitoraggio) {
		this.indicatoriMonitoraggio = indicatoriMonitoraggio;
	}
	public ArrayList<IndicatoreItem> getAltriIndicatori() {
		return altriIndicatori;
	}
	public void setAltriIndicatori(ArrayList<IndicatoreItem> altriIndicatori) {
		this.altriIndicatori = altriIndicatori;
	}
	
}
