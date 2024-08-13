/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTEnteCompetenzaVO;

public class EnteDiCompetenzaImpegnoVO  extends GenericVO {
	
	private String descEnte;
	private BigDecimal idEnteCompetenza;
	
	public String getDescEnte() {
		return descEnte;
	}
	public void setDescEnte(String descEnte) {
		this.descEnte = descEnte;
	}
	public BigDecimal getIdEnteCompetenza() {
		return idEnteCompetenza;
	}
	public void setIdEnteCompetenza(BigDecimal idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}

}