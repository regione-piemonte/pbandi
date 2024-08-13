/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class SoggettoFinanziatoreProgettoVO extends GenericVO {
	
	private BigDecimal idProgetto;
	private BigDecimal idSoggettoFinanziatore;
	private BigDecimal impQuotaSoggFinanziatore;
	private String descSoggFinanziatore;
	private String descBreveSoggFinanziatore;
	private String flagAgevolato;
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdSoggettoFinanziatore() {
		return idSoggettoFinanziatore;
	}
	public void setIdSoggettoFinanziatore(BigDecimal idSoggettoFinanziatore) {
		this.idSoggettoFinanziatore = idSoggettoFinanziatore;
	}
	public BigDecimal getImpQuotaSoggFinanziatore() {
		return impQuotaSoggFinanziatore;
	}
	public void setImpQuotaSoggFinanziatore(BigDecimal impQuotaSoggFinanziatore) {
		this.impQuotaSoggFinanziatore = impQuotaSoggFinanziatore;
	}
	public String getDescSoggFinanziatore() {
		return descSoggFinanziatore;
	}
	public void setDescSoggFinanziatore(String descSoggFinanziatore) {
		this.descSoggFinanziatore = descSoggFinanziatore;
	}
	public String getDescBreveSoggFinanziatore() {
		return descBreveSoggFinanziatore;
	}
	public void setDescBreveSoggFinanziatore(String descBreveSoggFinanziatore) {
		this.descBreveSoggFinanziatore = descBreveSoggFinanziatore;
	}
	public String getFlagAgevolato() {
		return flagAgevolato;
	}
	public void setFlagAgevolato(String flagAgevolato) {
		this.flagAgevolato = flagAgevolato;
	}

}
