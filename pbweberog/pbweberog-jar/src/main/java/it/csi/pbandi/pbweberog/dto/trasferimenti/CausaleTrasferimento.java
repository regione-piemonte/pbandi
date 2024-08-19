/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.trasferimenti;

public class CausaleTrasferimento implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Long idCausaleTrasferimento;
	private String descCausaleTrasferimento;


	public Long getIdCausaleTrasferimento() {
		return idCausaleTrasferimento;
	}

	public void setIdCausaleTrasferimento(Long idCausaleTrasferimento) {
		this.idCausaleTrasferimento = idCausaleTrasferimento;
	}

	public String getDescCausaleTrasferimento() {
		return descCausaleTrasferimento;
	}

	public void setDescCausaleTrasferimento(String descCausaleTrasferimento) {
		this.descCausaleTrasferimento = descCausaleTrasferimento;
	}

	public CausaleTrasferimento() {
		super();

	}

	public String toString() {
		return super.toString();
	}

}
