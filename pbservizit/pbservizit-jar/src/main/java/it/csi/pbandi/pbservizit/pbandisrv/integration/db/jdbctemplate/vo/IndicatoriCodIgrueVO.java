/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDIndicatoriVO;

public class IndicatoriCodIgrueVO extends PbandiDIndicatoriVO {
	private String codIgrueT13T14;
	private String descIndicatoreLinea;

	public void setCodIgrueT13T14(String codIgrueT13T14) {
		this.codIgrueT13T14 = codIgrueT13T14;
	}
	public String getCodIgrueT13T14() {
		return codIgrueT13T14;
	}
	public void setDescIndicatoreLinea(String descIndicatoreLinea) {
		this.descIndicatoreLinea = descIndicatoreLinea;
	}
	public String getDescIndicatoreLinea() {
		return descIndicatoreLinea;
	}
}