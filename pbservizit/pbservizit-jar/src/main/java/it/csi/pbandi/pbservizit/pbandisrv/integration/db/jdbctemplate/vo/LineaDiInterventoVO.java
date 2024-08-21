/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDLineaDiInterventoVO;

public class LineaDiInterventoVO extends PbandiDLineaDiInterventoVO {
	
	private String descBreveCompleta;

	public void setDescBreveCompleta(String descBreveCompleta) {
		this.descBreveCompleta = descBreveCompleta;
	}

	public String getDescBreveCompleta() {
		return descBreveCompleta;
	}

}
