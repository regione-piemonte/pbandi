/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.model;

public class DomandaRichiestaTmp {

	private Integer idSoggetto = null;
	private Integer idDomanda = null;


	public DomandaRichiestaTmp() {
		// TODO Auto-generated constructor stub
	}


	public DomandaRichiestaTmp(Integer idSoggetto, Integer idDomanda) {
		super();
		this.idSoggetto = idSoggetto;
		this.idDomanda = idDomanda;
	}


	public Integer getIdSoggetto() {
		return idSoggetto;
	}


	public void setIdSoggetto(Integer idSoggetto) {
		this.idSoggetto = idSoggetto;
	}


	public Integer getIdDomanda() {
		return idDomanda;
	}


	public void setIdDomanda(Integer idDomanda) {
		this.idDomanda = idDomanda;
	}
	
	
}
