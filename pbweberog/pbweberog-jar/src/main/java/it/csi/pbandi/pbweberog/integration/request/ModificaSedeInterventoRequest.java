/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import it.csi.pbandi.pbweberog.dto.datiprogetto.SedeProgettoDTO;

public class ModificaSedeInterventoRequest {
	private SedeProgettoDTO sedeIntervento;
	private Long idSedeInterventoAttuale;
	public SedeProgettoDTO getSedeIntervento() {
		return sedeIntervento;
	}
	public void setSedeIntervento(SedeProgettoDTO sedeIntervento) {
		this.sedeIntervento = sedeIntervento;
	}
	public Long getIdSedeInterventoAttuale() {
		return idSedeInterventoAttuale;
	}
	public void setIdSedeInterventoAttuale(Long idSedeInterventoAttuale) {
		this.idSedeInterventoAttuale = idSedeInterventoAttuale;
	}
	
	
	
}
