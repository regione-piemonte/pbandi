/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.model;

/**
 * idTipoSoggetto progrSoggettoDomanda
 */
public class SoggettoEProgressivoTmp {

	private Integer idTipoSoggetto = null;
	private Integer progrSoggettoDomanda = null;

	public SoggettoEProgressivoTmp() {
		// TODO Auto-generated constructor stub
	}

	public SoggettoEProgressivoTmp(Integer idTipoSoggetto, Integer progrSoggettoDomanda) {
		super();
		this.idTipoSoggetto = idTipoSoggetto;
		this.progrSoggettoDomanda = progrSoggettoDomanda;
	}

	public Integer getIdTipoSoggetto() {
		return idTipoSoggetto;
	}

	public void setIdTipoSoggetto(Integer idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}

	public Integer getProgrSoggettoDomanda() {
		return progrSoggettoDomanda;
	}

	public void setProgrSoggettoDomanda(Integer progrSoggettoDomanda) {
		this.progrSoggettoDomanda = progrSoggettoDomanda;
	}

}
