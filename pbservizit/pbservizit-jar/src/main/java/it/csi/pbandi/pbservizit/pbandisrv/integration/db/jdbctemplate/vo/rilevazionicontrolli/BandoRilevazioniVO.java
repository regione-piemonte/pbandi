/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.rilevazionicontrolli;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;


public class BandoRilevazioniVO extends GenericVO{
	
	private BigDecimal idAsse;
	private BigDecimal progrBandoLineaIntervento;
	private String nomeBandoLinea;
	private BigDecimal idNormativa;
	
	public BigDecimal getIdAsse() {
		return idAsse;
	}
	public void setIdAsse(BigDecimal idAsse) {
		this.idAsse = idAsse;
	}
	public BigDecimal getIdNormativa() {
		return idNormativa;
	}
	public void setIdNormativa(BigDecimal idNormativa) {
		this.idNormativa = idNormativa;
	}
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}
	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}
}
