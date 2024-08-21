/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEnteGiuridicoVO;

public class BeneficiarioEnteGiuridicoVO extends PbandiTEnteGiuridicoVO {
	
	private BigDecimal idProgetto;

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	

	

}
