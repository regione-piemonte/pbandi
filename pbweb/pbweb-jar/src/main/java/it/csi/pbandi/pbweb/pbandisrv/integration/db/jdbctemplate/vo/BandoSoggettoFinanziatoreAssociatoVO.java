/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoSoggFinanziatVO;

public class BandoSoggettoFinanziatoreAssociatoVO extends PbandiRBandoSoggFinanziatVO{
	
	private String descSoggFinanziatore;
	private String descTabellare;
	
	public String getDescSoggFinanziatore() {
		return descSoggFinanziatore;
	}
	public void setDescSoggFinanziatore(String descSoggFinanziatore) {
		this.descSoggFinanziatore = descSoggFinanziatore;
	}
	public void setDescTabellare(String descTabellare) {
		this.descTabellare = descTabellare;
	}
	public String getDescTabellare() {
		return descTabellare;
	}
}
