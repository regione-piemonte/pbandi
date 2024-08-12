package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLinTipoPeriodVO;

public class BandoLineaTipoPeriodoAssociatoVO extends PbandiRBandoLinTipoPeriodVO {
	
	// CDU-14 V04: nuova classe
	
	private String descBreveTipoPeriodo;
	private String descTipoPeriodo;
	
	public String getDescBreveTipoPeriodo() {
		return descBreveTipoPeriodo;
	}
	public void setDescBreveTipoPeriodo(String descBreveTipoPeriodo) {
		this.descBreveTipoPeriodo = descBreveTipoPeriodo;
	}
	public String getDescTipoPeriodo() {
		return descTipoPeriodo;
	}
	public void setDescTipoPeriodo(String descTipoPeriodo) {
		this.descTipoPeriodo = descTipoPeriodo;
	}

}
