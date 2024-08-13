/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDVoceDiEntrataVO;

public class VoceDiEntrataAssociataVO extends PbandiDVoceDiEntrataVO{

	private BigDecimal idBando;

	public BigDecimal getIdBando() {
		return idBando;
	}

	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}
	
}
