/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTFornitoreVO;

import java.math.BigDecimal;

public class FornitoreProgettoVO extends PbandiTFornitoreVO {

	private BigDecimal idProgetto;
	private String fornitoreTabella;

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getFornitoreTabella() {
		return fornitoreTabella;
	}

	public void setFornitoreTabella(String fornitoreTabella) {
		this.fornitoreTabella = fornitoreTabella;
	}

}
