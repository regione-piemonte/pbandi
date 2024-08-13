/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDLineaDiInterventoVO;

import java.math.BigDecimal;

public class LineaDiInterventoPadreVO extends PbandiDLineaDiInterventoVO {

	private BigDecimal progrBandoLineaIntervento;
	private BigDecimal idBando;
	private String descTipoLinea;
	private BigDecimal livelloTipoLinea;

	public void setProgrBandoLineaIntervento(
			BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}

	public BigDecimal getIdBando() {
		return idBando;
	}

	public void setDescTipoLinea(String descTipoLinea) {
		this.descTipoLinea = descTipoLinea;
	}

	public String getDescTipoLinea() {
		return descTipoLinea;
	}

	public void setLivelloTipoLinea(BigDecimal livelloTipoLinea) {
		this.livelloTipoLinea = livelloTipoLinea;
	}

	public BigDecimal getLivelloTipoLinea() {
		return livelloTipoLinea;
	}

}
