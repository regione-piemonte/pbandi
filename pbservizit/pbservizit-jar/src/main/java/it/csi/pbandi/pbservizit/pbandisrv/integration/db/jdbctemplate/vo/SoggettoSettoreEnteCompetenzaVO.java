/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class SoggettoSettoreEnteCompetenzaVO  extends GenericVO {

	private BigDecimal idSettoreEnte;
	private String descSettore;
	private BigDecimal idEnteCompetenza;
	private BigDecimal idSoggetto;
	private String descBreveTipoEnteCompetenz;
	
	public BigDecimal getIdSettoreEnte() {
		return idSettoreEnte;
	}
	public void setIdSettoreEnte(BigDecimal idSettoreEnte) {
		this.idSettoreEnte = idSettoreEnte;
	}
	public String getDescSettore() {
		return descSettore;
	}
	public void setDescSettore(String descSettore) {
		this.descSettore = descSettore;
	}
	public BigDecimal getIdEnteCompetenza() {
		return idEnteCompetenza;
	}
	public void setIdEnteCompetenza(BigDecimal idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getDescBreveTipoEnteCompetenz() {
		return descBreveTipoEnteCompetenz;
	}
	public void setDescBreveTipoEnteCompetenz(String descBreveTipoEnteCompetenz) {
		this.descBreveTipoEnteCompetenz = descBreveTipoEnteCompetenz;
	}

}
