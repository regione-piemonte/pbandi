/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio;

import java.math.BigDecimal;

public class OutputConsultaAttoLiquidazione {
	
	private BigDecimal idAttiLiquidazioneDt;
	private String descStatoDocContabilia;
	
	public BigDecimal getIdAttiLiquidazioneDt() {
		return idAttiLiquidazioneDt;
	}
	public void setIdAttiLiquidazioneDt(BigDecimal idAttiLiquidazioneDt) {
		this.idAttiLiquidazioneDt = idAttiLiquidazioneDt;
	}
	public String getDescStatoDocContabilia() {
		return descStatoDocContabilia;
	}
	public void setDescStatoDocContabilia(String descStatoDocContabilia) {
		this.descStatoDocContabilia = descStatoDocContabilia;
	}

}
