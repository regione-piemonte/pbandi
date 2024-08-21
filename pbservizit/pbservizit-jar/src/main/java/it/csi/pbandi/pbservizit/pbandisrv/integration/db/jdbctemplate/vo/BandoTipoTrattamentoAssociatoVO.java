/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRBandoTipoTrattamentVO;

public class BandoTipoTrattamentoAssociatoVO extends PbandiRBandoTipoTrattamentVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4293176184058175869L;
	private String descTipoTrattamento;
	private String descBreveTipoTrattamento;
	private String descTabellare;
	
	public void setDescTabellare(String descTabellare) {
		this.descTabellare = descTabellare;
	}
	public String getDescTabellare() {
		return descTabellare;
	}
	public String getDescTipoTrattamento() {
		return descTipoTrattamento;
	}
	public void setDescTipoTrattamento(String descTipoTrattamento) {
		this.descTipoTrattamento = descTipoTrattamento;
	}
	public String getDescBreveTipoTrattamento() {
		return descBreveTipoTrattamento;
	}
	public void setDescBreveTipoTrattamento(String descBreveTipoTrattamento) {
		this.descBreveTipoTrattamento = descBreveTipoTrattamento;
	}
}
