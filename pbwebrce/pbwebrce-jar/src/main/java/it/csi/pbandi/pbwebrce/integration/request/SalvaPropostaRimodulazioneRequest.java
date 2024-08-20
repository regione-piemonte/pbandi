/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.request;

import java.util.ArrayList;

import it.csi.pbandi.pbwebrce.dto.ContoEconomicoItem;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.AltriCostiDTO;

public class SalvaPropostaRimodulazioneRequest {

	private Long idProgetto;

	private ArrayList<ContoEconomicoItem> contoEconomicoItemList;

	private ArrayList<AltriCostiDTO> altriCosti;

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

	public ArrayList<AltriCostiDTO> getAltriCosti() {
		return altriCosti;
	}

	public void setAltriCosti(ArrayList<AltriCostiDTO> altriCosti) {
		this.altriCosti = altriCosti;
	}

}
