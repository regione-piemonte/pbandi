/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;

import it.csi.pbandi.pbweberog.dto.revoca.DettaglioRevoca;

public class RequestModificaRevoca implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idProgetto;
	private DettaglioRevoca revoca;
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public DettaglioRevoca getRevoca() {
		return revoca;
	}
	public void setRevoca(DettaglioRevoca revoca) {
		this.revoca = revoca;
	}
	
	

}
