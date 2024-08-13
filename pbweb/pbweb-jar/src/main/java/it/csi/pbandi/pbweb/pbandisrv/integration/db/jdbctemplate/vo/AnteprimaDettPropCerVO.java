/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTPreviewDettPropCerVO;

public class AnteprimaDettPropCerVO extends PbandiTPreviewDettPropCerVO {
	
	private String descBreveLinea;
	private String descLinea;
	
	public String getDescBreveLinea() {
		return descBreveLinea;
	}
	public void setDescBreveLinea(String descBreveLinea) {
		this.descBreveLinea = descBreveLinea;
	}
	public String getDescLinea() {
		return descLinea;
	}
	public void setDescLinea(String descLinea) {
		this.descLinea = descLinea;
	}

}
