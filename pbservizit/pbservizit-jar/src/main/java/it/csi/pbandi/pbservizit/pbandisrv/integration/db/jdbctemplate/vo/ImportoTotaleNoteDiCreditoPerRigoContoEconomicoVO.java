/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class ImportoTotaleNoteDiCreditoPerRigoContoEconomicoVO extends GenericVO {

	private BigDecimal totaleNoteDiCredito;
	private BigDecimal idQuotaParteDocSpesa;
	private BigDecimal idRigoContoEconomico;
	
	public void setTotaleNoteDiCredito(BigDecimal totaleNoteDiCredito) {
		this.totaleNoteDiCredito = totaleNoteDiCredito;
	}

	public BigDecimal getTotaleNoteDiCredito() {
		return totaleNoteDiCredito;
	}

	public void setIdQuotaParteDocSpesa(BigDecimal idQuotaParteDocSpesa) {
		this.idQuotaParteDocSpesa = idQuotaParteDocSpesa;
	}

	public BigDecimal getIdQuotaParteDocSpesa() {
		return idQuotaParteDocSpesa;
	}

	public void setIdRigoContoEconomico(BigDecimal idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}

	public BigDecimal getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}

}
