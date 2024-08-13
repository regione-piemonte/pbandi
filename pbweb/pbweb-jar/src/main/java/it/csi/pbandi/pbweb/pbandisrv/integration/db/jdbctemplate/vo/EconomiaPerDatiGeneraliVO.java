/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class EconomiaPerDatiGeneraliVO extends GenericVO {
	
	private BigDecimal idProgettoCedente;
	private String descBreveSoggFinanziatore;
	private Double impQuotaEconSoggFinanziat;
	
	public BigDecimal getIdProgettoCedente() {
		return idProgettoCedente;
	}
	public void setIdProgettoCedente(BigDecimal idProgettoCedente) {
		this.idProgettoCedente = idProgettoCedente;
	}
	public String getDescBreveSoggFinanziatore() {
		return descBreveSoggFinanziatore;
	}
	public void setDescBreveSoggFinanziatore(String descBreveSoggFinanziatore) {
		this.descBreveSoggFinanziatore = descBreveSoggFinanziatore;
	}
	public Double getImpQuotaEconSoggFinanziat() {
		return impQuotaEconSoggFinanziat;
	}
	public void setImpQuotaEconSoggFinanziat(Double impQuotaEconSoggFinanziat) {
		this.impQuotaEconSoggFinanziat = impQuotaEconSoggFinanziat;
	}
	
}
