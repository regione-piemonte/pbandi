/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestioneeconomie;


import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class QuotaSoggettoFinanziatoreVO extends GenericVO {
	
	private String descSoggFinanziatore;
	private BigDecimal idSoggettoFinanziatore;
	private BigDecimal idProgetto;
	private Double percQuotaSoggFinanziatore;

	public String getDescSoggFinanziatore() {
		return descSoggFinanziatore;
	}

	public void setDescSoggFinanziatore(String descSoggFinanziatore) {
		this.descSoggFinanziatore = descSoggFinanziatore;
	}

	public BigDecimal getIdSoggettoFinanziatore() {
		return idSoggettoFinanziatore;
	}

	public void setIdSoggettoFinanziatore(BigDecimal idSoggettoFinanziatore) {
		this.idSoggettoFinanziatore = idSoggettoFinanziatore;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public Double getPercQuotaSoggFinanziatore() {
		return percQuotaSoggFinanziatore;
	}

	public void setPercQuotaSoggFinanziatore(Double percQuotaSoggFinanziatore) {
		this.percQuotaSoggFinanziatore = percQuotaSoggFinanziatore;
	}
}
