/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;

import it.csi.pbandi.pbweberog.dto.datiprogetto.DatiProgetto;

public class SalvaDatiProgettoRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DatiProgetto datiProgetto;
	public DatiProgetto getDatiProgetto() {
		return datiProgetto;
	}
	public void setDatiProgetto(DatiProgetto datiProgetto) {
		this.datiProgetto = datiProgetto;
	}


	
	
	
}
