/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.columnfilter;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class ProgettoBandoLineaLightFilterByBandoVO extends GenericVO {
	private BigDecimal idBandoLinea;
	private String descrizioneBando;

	public BigDecimal getIdBandoLinea() {
		return idBandoLinea;
	}

	public void setIdBandoLinea(BigDecimal idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}

	public String getDescrizioneBando() {
		return descrizioneBando;
	}

	public void setDescrizioneBando(String descrizioneBando) {
		this.descrizioneBando = descrizioneBando;
	}

}
