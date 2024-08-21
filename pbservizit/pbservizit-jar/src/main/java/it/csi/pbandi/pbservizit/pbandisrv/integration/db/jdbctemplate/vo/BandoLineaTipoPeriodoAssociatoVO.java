/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRBandoLinTipoPeriodVO;

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
