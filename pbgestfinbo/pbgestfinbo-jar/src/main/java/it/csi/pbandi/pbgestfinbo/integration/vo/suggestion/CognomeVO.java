/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.suggestion;

public class CognomeVO {
	
	private String cognome;
	
	private Long idSoggetto;

	private String descrTipoSogg;

	private int idTipoSogg;

	public CognomeVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
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
