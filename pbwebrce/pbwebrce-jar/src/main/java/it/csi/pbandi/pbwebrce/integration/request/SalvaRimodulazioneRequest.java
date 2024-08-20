/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.request;

import java.util.ArrayList;

import it.csi.pbandi.pbwebrce.dto.ContoEconomicoItem;

public class SalvaRimodulazioneRequest {
	
	private Long idProgetto;
	
	private ArrayList<ContoEconomicoItem> contoEconomicoItemList;
	
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public ArrayList<ContoEconomicoItem> getContoEconomicoItemList() {
		return contoEconomicoItemList;
	}
	public void setContoEconomicoItemList(ArrayList<ContoEconomicoItem> contoEconomicoItemList) {
		this.contoEconomicoItemList = contoEconomicoItemList;
	}
	
}
