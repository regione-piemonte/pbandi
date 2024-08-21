/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class CausaleTrasferimentoVO extends GenericVO {
	
	private BigDecimal idCausaleTrasferimento;
	private String descCausaleTrasferimento;

	public BigDecimal getIdCausaleTrasferimento() {
		return idCausaleTrasferimento;
	}
	public void setIdCausaleTrasferimento(BigDecimal idCausaleTrasferimento) {
		this.idCausaleTrasferimento = idCausaleTrasferimento;
	}
	public String getDescCausaleTrasferimento() {
		return descCausaleTrasferimento;
	}
	public void setDescCausaleTrasferimento(String descCausaleTrasferimento) {
		this.descCausaleTrasferimento = descCausaleTrasferimento;
	}	
}
