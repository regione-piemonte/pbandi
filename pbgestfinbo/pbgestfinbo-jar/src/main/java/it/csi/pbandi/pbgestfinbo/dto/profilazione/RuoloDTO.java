/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.dto.profilazione;

public class RuoloDTO {

	private Long idTipoSoggCorr; 
	private String descTipoSoggCorr;
	
	public Long getIdTipoSoggCorr() {
		return idTipoSoggCorr;
	}
	public void setIdTipoSoggCorr(Long idTipoSoggCorr) {
		this.idTipoSoggCorr = idTipoSoggCorr;
	}
	public String getDescTipoSoggCorr() {
		return descTipoSoggCorr;
	}
	public void setDescTipoSoggCorr(String descTipoSoggCorr) {
		this.descTipoSoggCorr = descTipoSoggCorr;
	} 
	
}
