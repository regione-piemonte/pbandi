/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.dto.profilazione;

public class AtecoDTO {

	private String codAteco;
	private Long idAttAteco;
	private String descAttivitaAteco;
	
	
	
	public Long getIdAttAteco() {
		return idAttAteco;
	}
	public void setIdAttAteco(Long idAttAteco) {
		this.idAttAteco = idAttAteco;
	}
	public String getDescAttivitaAteco() {
		return descAttivitaAteco;
	}
	public void setDescAttivitaAteco(String descAttivitaAeco) {
		this.descAttivitaAteco = descAttivitaAeco;
	}
	public String getCodAteco() {
		return codAteco;
	}
	public void setCodAteco(String codAteco) {
		this.codAteco = codAteco;
	}

	
	
	
}
