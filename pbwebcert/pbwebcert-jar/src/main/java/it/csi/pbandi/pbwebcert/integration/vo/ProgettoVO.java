/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;

public class ProgettoVO {
	private BigDecimal idProgetto;
	private BigDecimal codiceProgetto;
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(BigDecimal codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
}
