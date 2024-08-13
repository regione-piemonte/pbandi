/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class SettoreEnteBandoRuoloVO extends GenericVO {
	
  
  	private BigDecimal progrBandoLineaEnteComp;
  	private BigDecimal progrBandoLineaSettore;
	public BigDecimal getProgrBandoLineaEnteComp() {
		return progrBandoLineaEnteComp;
	}
	public void setProgrBandoLineaEnteComp(
			BigDecimal progrBandoLineaEnteComp) {
		this.progrBandoLineaEnteComp= progrBandoLineaEnteComp;
	}
	public BigDecimal getProgrBandoLineaSettore() {
		return progrBandoLineaSettore;
	}
	public void setProgrBandoLineaSettore(BigDecimal progrBandoLineaSettore) {
		this.progrBandoLineaSettore = progrBandoLineaSettore;
	}
 
}
