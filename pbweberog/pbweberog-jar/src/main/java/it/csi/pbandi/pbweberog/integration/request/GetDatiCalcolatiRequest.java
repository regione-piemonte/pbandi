/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;

import it.csi.pbandi.pbweberog.dto.erogazione.ErogazioneDTO;

public class GetDatiCalcolatiRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idProgetto;
	private ErogazioneDTO erogazioneDTO;
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public ErogazioneDTO getErogazioneDTO() {
		return erogazioneDTO;
	}
	public void setErogazioneDTO(ErogazioneDTO erogazioneDTO) {
		this.erogazioneDTO = erogazioneDTO;
	}
	
	
}
