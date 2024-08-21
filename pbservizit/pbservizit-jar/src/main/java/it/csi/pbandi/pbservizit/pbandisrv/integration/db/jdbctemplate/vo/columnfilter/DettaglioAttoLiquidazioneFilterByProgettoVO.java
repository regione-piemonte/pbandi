/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.columnfilter;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class DettaglioAttoLiquidazioneFilterByProgettoVO extends GenericVO{
	private BigDecimal idProgetto;
	private String codiceVisualizzatoProgetto;

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}
	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}
}
