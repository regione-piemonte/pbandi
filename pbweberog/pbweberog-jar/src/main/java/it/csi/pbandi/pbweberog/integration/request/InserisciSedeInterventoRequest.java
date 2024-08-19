/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;

import it.csi.pbandi.pbweberog.dto.datiprogetto.SedeProgettoDTO;

public class InserisciSedeInterventoRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SedeProgettoDTO sedeIntervento;
	private Long progrSoggettoProgetto;
	private Long idSedeIntervento;
	public SedeProgettoDTO getSedeIntervento() {
		return sedeIntervento;
	}
	public void setSedeIntervento(SedeProgettoDTO sedeIntervento) {
		this.sedeIntervento = sedeIntervento;
	}
	public Long getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}
	public void setProgrSoggettoProgetto(Long progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}
	public Long getIdSedeIntervento() {
		return idSedeIntervento;
	}
	public void setIdSedeIntervento(Long idSedeIntervento) {
		this.idSedeIntervento = idSedeIntervento;
	}
	
	
	
}
