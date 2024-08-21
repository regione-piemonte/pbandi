/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDIndRisultatoProgramVO;

public class IndRisultatoProgramBandoLineaVO extends
		PbandiDIndRisultatoProgramVO {
	private BigDecimal progrBandoLineaIntervento;

	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

}
