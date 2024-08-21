/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class TotaleRendicontatoProgettoVO extends GenericVO {
	private BigDecimal idProgetto ;
	private BigDecimal importoRendicontato;
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public BigDecimal getImportoRendicontato() {
		return importoRendicontato;
	}
	public void setImportoRendicontato(BigDecimal importoRendicontato) {
		this.importoRendicontato = importoRendicontato;
	}

}
