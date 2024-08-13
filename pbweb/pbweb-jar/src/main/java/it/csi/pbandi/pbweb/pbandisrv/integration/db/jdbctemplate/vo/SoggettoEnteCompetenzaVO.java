/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class SoggettoEnteCompetenzaVO extends GenericVO {
	
	
	private BigDecimal idEnteCompetenza;
	private BigDecimal idSoggetto;
	private String descBreveEnte;
	private String descEnte;
  
	private BigDecimal idTipoEnteCompetenza;
	private String descBreveTipoEnteCompetenz;
	private String descTipoEnteCompetenza;
	private String codIgrueT51;
	
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
	public String getDescBreveEnte() {
		return descBreveEnte;
	}
	public void setDescBreveEnte(String descBreveEnte) {
		this.descBreveEnte = descBreveEnte;
	}
	public String getDescEnte() {
		return descEnte;
	}
	public void setDescEnte(String descEnte) {
		this.descEnte = descEnte;
	}
	public BigDecimal getIdTipoEnteCompetenza() {
		return idTipoEnteCompetenza;
	}
	public void setIdTipoEnteCompetenza(BigDecimal idTipoEnteCompetenza) {
		this.idTipoEnteCompetenza = idTipoEnteCompetenza;
	}
	public String getDescBreveTipoEnteCompetenz() {
		return descBreveTipoEnteCompetenz;
	}
	public void setDescBreveTipoEnteCompetenz(String descBreveTipoEnteCompetenz) {
		this.descBreveTipoEnteCompetenz = descBreveTipoEnteCompetenz;
	}
	public String getDescTipoEnteCompetenza() {
		return descTipoEnteCompetenza;
	}
	public void setDescTipoEnteCompetenza(String descTipoEnteCompetenza) {
		this.descTipoEnteCompetenza = descTipoEnteCompetenza;
	}
	public String getCodIgrueT51() {
		return codIgrueT51;
	}
	public void setCodIgrueT51(String codIgrueT51) {
		this.codIgrueT51 = codIgrueT51;
	}

}
