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
