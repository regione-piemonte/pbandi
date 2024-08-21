/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.datiprogetto;

public class EconomiaPerDatiGenerali {
	private Long idProgettoCedente;
	private String descBreveSoggFinanziatore;
	private Double impQuotaEconSoggFinanziat;
	private static final long serialVersionUID = 1L;
	
	
	public void setIdProgettoCedente(Long val) {
		idProgettoCedente = val;
	}

	public Long getIdProgettoCedente() {
		return idProgettoCedente;
	}

	public void setDescBreveSoggFinanziatore(String val) {
		descBreveSoggFinanziatore = val;
	}

	public String getDescBreveSoggFinanziatore() {
		return descBreveSoggFinanziatore;
	}

	public void setImpQuotaEconSoggFinanziat(Double val) {
		impQuotaEconSoggFinanziat = val;
	}

	public Double getImpQuotaEconSoggFinanziat() {
		return impQuotaEconSoggFinanziat;
	}

	public EconomiaPerDatiGenerali() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
