/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;

public class VociDiSpesaAssociateABandoVO extends PbandiRBandoLineaInterventVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6233806892327304594L;
	private BigDecimal idVoceDiSpesa;
	
	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}

	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
}
