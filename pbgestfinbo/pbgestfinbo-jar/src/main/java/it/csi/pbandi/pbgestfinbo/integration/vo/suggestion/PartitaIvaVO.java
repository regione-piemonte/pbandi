/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.suggestion;

public class PartitaIvaVO {

	private String partitaIva;

	private Long idSoggetto;

	private String descrTipoSogg;

	private int idTipoSogg;


	public PartitaIvaVO( ) {

	}


	public String getPartitaIva() {
		return partitaIva;
	}


	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}


	public Long getIdSoggetto() {
		return idSoggetto;
	}


	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}


	public String getDescrTipoSogg() {
		return descrTipoSogg;
	}


	public void setDescrTipoSogg(String descrTipoSogg) {
		this.descrTipoSogg = descrTipoSogg;
	}


	public int getIdTipoSogg() {
		return idTipoSogg;
	}


	public void setIdTipoSogg(int idTipoSogg) {
		this.idTipoSogg = idTipoSogg;
	}

	

}
