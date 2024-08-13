/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoCausaleErogazVO;

public class BandoCausaleDiErogazioneAssociataVO extends PbandiRBandoCausaleErogazVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3875871741500148756L;
	private String descCausale;
	private String descDimensioneImpresa;
	private String descFormaGiuridica;
	private String descTabellare;
	
	public void setDescTabellare(String descTabellare) {
		this.descTabellare = descTabellare;
	}
	public String getDescTabellare() {
		return descTabellare;
	}
	public String getDescCausale() {
		return descCausale;
	}
	public void setDescCausale(String descCausale) {
		this.descCausale = descCausale;
	}
	public String getDescDimensioneImpresa() {
		return descDimensioneImpresa;
	}
	public void setDescDimensioneImpresa(String descDimensioneImpresa) {
		this.descDimensioneImpresa = descDimensioneImpresa;
	}
	public String getDescFormaGiuridica() {
		return descFormaGiuridica;
	}
	public void setDescFormaGiuridica(String descFormaGiuridica) {
		this.descFormaGiuridica = descFormaGiuridica;
	}
}
