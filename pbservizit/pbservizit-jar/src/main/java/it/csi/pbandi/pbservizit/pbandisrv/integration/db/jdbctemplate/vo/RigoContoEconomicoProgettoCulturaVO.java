/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class RigoContoEconomicoProgettoCulturaVO extends RigoContoEconomicoProgettoVO {
	
	private BigDecimal idVoceDiSpesa;
    private BigDecimal idVoceDiEntrata;
    
	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	public BigDecimal getIdVoceDiEntrata() {
		return idVoceDiEntrata;
	}
	public void setIdVoceDiEntrata(BigDecimal idVoceDiEntrata) {
		this.idVoceDiEntrata = idVoceDiEntrata;
	}
    
}
