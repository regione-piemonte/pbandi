/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTEconomieVO;

public class EnteDestinatarioComunicazioniVO extends GenericVO {
	
	public BigDecimal idEnteCompetenza;
	public BigDecimal idProgetto;
	public String descEnte;
	public String descBreveEnte;
	
	public BigDecimal getIdEnteCompetenza() {
		return idEnteCompetenza;
	}
	public void setIdEnteCompetenza(BigDecimal idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}
	public String getDescEnte() {
		return descEnte;
	}
	public void setDescEnte(String descEnte) {
		this.descEnte = descEnte;
	}
	public String getDescBreveEnte() {
		return descBreveEnte;
	}
	public void setDescBreveEnte(String descBreveEnte) {
		this.descBreveEnte = descBreveEnte;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
}
