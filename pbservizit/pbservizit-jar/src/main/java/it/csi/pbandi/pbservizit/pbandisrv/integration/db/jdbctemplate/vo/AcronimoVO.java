/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class AcronimoVO extends GenericVO {
	
	private String acronimoProgetto;
	private BigDecimal idProgetto;
	private BigDecimal idDomandaPadre;
/*
 	 * @return the idProgetto
	 */
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	/**
	 * @param idProgetto the idProgetto to set
	 */
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	/**
	 * @return the idDomandaPadre
	 */
	public BigDecimal getIdDomandaPadre() {
		return idDomandaPadre;
	}
	/**
	 * @param idDomandaPadre the idDomandaPadre to set
	 */
	public void setIdDomandaPadre(BigDecimal idDomandaPadre) {
		this.idDomandaPadre = idDomandaPadre;
	}
	public void setAcronimoProgetto(String acronimoProgetto) {
		this.acronimoProgetto = acronimoProgetto;
	}
	public String getAcronimoProgetto() {
		return acronimoProgetto;
	}


}
