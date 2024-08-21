/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class ImportoCedutoEconomiaVO extends GenericVO{
	private Double importoCeduto;
	private BigDecimal idProgettoCedente;
	
	public Double getImportoCeduto() {
		return importoCeduto;
	}
	public void setImportoCeduto(Double importoCeduto) {
		this.importoCeduto = importoCeduto;
	}
	public BigDecimal getIdProgettoCedente() {
		return idProgettoCedente;
	}
	public void setIdProgettoCedente(BigDecimal idProgettoCedente) {
		this.idProgettoCedente = idProgettoCedente;
	}
}
