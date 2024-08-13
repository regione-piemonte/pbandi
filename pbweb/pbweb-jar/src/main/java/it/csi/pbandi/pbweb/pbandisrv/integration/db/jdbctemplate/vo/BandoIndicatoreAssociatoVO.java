/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoIndicatoriVO;

public class BandoIndicatoreAssociatoVO extends PbandiRBandoIndicatoriVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2247659387864123928L;
	private String codIgrue;
	private String descIndicatore;
	private String descIndicatoreLinea;

	public void setCodIgrue(String codIgrue) {
		this.codIgrue = codIgrue;
	}
	public String getCodIgrue() {
		return codIgrue;
	}
	public void setDescIndicatore(String descIndicatore) {
		this.descIndicatore = descIndicatore;
	}
	public String getDescIndicatore() {
		return descIndicatore;
	}
	public void setDescIndicatoreLinea(String descIndicatoreLinea) {
		this.descIndicatoreLinea = descIndicatoreLinea;
	}
	public String getDescIndicatoreLinea() {
		return descIndicatoreLinea;
	}
	
}
