/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.rilevazionicontrolli;


import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDLineaDiInterventoVO;

public class NormativaRilevazioniVO extends PbandiDLineaDiInterventoVO {
	
	private BigDecimal idNormativa;
	private String normativa;
	
	public BigDecimal getIdNormativa() {
		return idNormativa;
	}
	public void setIdNormativa(BigDecimal idNormativa) {
		this.idNormativa = idNormativa;
	}
	public String getNormativa() {
		return normativa;
	}
	public void setNormativa(String normativa) {
		this.normativa = normativa;
	}

}
