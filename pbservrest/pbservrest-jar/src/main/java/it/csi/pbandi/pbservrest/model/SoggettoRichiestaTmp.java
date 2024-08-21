/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.model;

public class SoggettoRichiestaTmp {

	private Integer idSoggetto = null;
	private String descTipoRichiesta = null;

	public SoggettoRichiestaTmp() {
		// TODO Auto-generated constructor stub
	}

	public SoggettoRichiestaTmp(Integer idSoggetto, String descTipoRichiesta) {
		super();
		this.idSoggetto = idSoggetto;
		this.descTipoRichiesta = descTipoRichiesta;
	}

	public Integer getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Integer idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public String getDescTipoRichiesta() {
		return descTipoRichiesta;
	}

	public void setDescTipoRichiesta(String descTipoRichiesta) {
		this.descTipoRichiesta = descTipoRichiesta;
	}

	
}
