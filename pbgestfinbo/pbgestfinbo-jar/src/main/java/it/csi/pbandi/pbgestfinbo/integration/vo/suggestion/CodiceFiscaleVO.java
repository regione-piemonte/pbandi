/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.suggestion;

public class CodiceFiscaleVO {
	
	private String codiceFiscale;
	
	private Long idSoggetto;
	
	private String descrTipoSogg;
	
	private int idTipoSogg;
	
	public CodiceFiscaleVO() {
		
	}
	
	public CodiceFiscaleVO(String codiceFiscale, Long idSoggetto) {
		this.codiceFiscale = codiceFiscale;
		this.idSoggetto = idSoggetto;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
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
