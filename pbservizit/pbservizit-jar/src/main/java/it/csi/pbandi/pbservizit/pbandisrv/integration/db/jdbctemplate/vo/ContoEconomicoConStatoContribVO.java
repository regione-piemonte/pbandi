/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class ContoEconomicoConStatoContribVO extends ContoEconomicoConStatoVO{
	
	private BigDecimal quotaImportoAgevolato;

	public BigDecimal getQuotaImportoAgevolato() {
		return quotaImportoAgevolato;
	}

	public void setQuotaImportoAgevolato(BigDecimal quotaImportoAgevolato) {
		this.quotaImportoAgevolato = quotaImportoAgevolato;
	}
	
}
