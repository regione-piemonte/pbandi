/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.suggestion;

public class IdProgettoVO {
	
	private String codProgetto;
	private Long idProgetto;
	
	private Long idSoggetto;
	private String descTipoSoggetto; 
	
	
	
	
	public String getDescTipoSoggetto() {
		return descTipoSoggetto;
	}

	public void setDescTipoSoggetto(String descTipoSoggetto) {
		this.descTipoSoggetto = descTipoSoggetto;
	}

	public IdProgettoVO( ) {
		
	}
	
	public IdProgettoVO (Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	
	
	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	
	

	public String getCodProgetto() {
		return codProgetto;
	}

	public void setCodProgetto(String codProgetto) {
		this.codProgetto = codProgetto;
	}

	@Override
	public String toString() {
		return "IdProgettoVO [codProgetto=" + codProgetto + ", idProgetto=" + idProgetto + ", idSoggetto=" + idSoggetto
				+ "]";
	}

	



}
