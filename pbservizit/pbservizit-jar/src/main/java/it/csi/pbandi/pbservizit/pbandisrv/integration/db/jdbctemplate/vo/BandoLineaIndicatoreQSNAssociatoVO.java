/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRBandoLinIndicatQsnVO;

public class BandoLineaIndicatoreQSNAssociatoVO extends PbandiRBandoLinIndicatQsnVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3727863032790351502L;
	private String descIndicatoreQsn;
	private String codIgrueT12;
	
	public void setDescIndicatoreQsn(String descIndicatoreQsn) {
		this.descIndicatoreQsn = descIndicatoreQsn;
	}
	public String getDescIndicatoreQsn() {
		return descIndicatoreQsn;
	}
	public void setCodIgrueT12(String codIgrueT12) {
		this.codIgrueT12 = codIgrueT12;
	}
	public String getCodIgrueT12() {
		return codIgrueT12;
	}
	
}
