/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.irregolarita;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;


public class IrregolaritaMaxVersioneVO extends GenericVO {
	
	private BigDecimal idIrregolaritaPadre;
	private BigDecimal maxVersione;
	
	public void setIdIrregolaritaPadre(BigDecimal idIrregolaritaPadre) {
		this.idIrregolaritaPadre = idIrregolaritaPadre;
	}
	public BigDecimal getIdIrregolaritaPadre() {
		return idIrregolaritaPadre;
	}
	public void setMaxVersione(BigDecimal maxVersione) {
		this.maxVersione = maxVersione;
	}
	public BigDecimal getMaxVersione() {
		return maxVersione;
	}

}
