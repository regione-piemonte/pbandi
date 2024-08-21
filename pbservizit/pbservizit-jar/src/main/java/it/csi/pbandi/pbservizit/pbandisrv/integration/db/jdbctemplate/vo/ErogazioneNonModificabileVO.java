/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class ErogazioneNonModificabileVO extends GenericVO{
	
  	/**
	 * 
	 */
	private static final long serialVersionUID = -1571477062157119817L;
	private BigDecimal idFideiussione;
	
  	public void setIdFideiussione(BigDecimal idFideiussione) {
		this.idFideiussione = idFideiussione;
	}
	public BigDecimal getIdFideiussione() {
		return idFideiussione;
	}
}
