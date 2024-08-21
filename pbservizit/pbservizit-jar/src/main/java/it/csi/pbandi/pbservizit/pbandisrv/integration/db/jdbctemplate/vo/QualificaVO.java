/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRFornitoreQualificaVO;

public class QualificaVO extends PbandiRFornitoreQualificaVO{

	static final long serialVersionUID = 1;
	
	private java.lang.String descQualifica = null;

	public java.lang.String getDescQualifica() {
		return descQualifica;
	}

	public void setDescQualifica(java.lang.String descQualifica) {
		this.descQualifica = descQualifica;
	}

	

}
