/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;

import it.csi.pbandi.pbweberog.dto.revoca.DettaglioRevoca;

public class RequestModificaDisimpegno implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DettaglioRevoca dettaglioRevoca;
	private Long idProgetto;
	public DettaglioRevoca getDettaglioRevoca() {
		return dettaglioRevoca;
	}
	public void setDettaglioRevoca(DettaglioRevoca dettaglioRevoca) {
		this.dettaglioRevoca = dettaglioRevoca;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	
	

}
