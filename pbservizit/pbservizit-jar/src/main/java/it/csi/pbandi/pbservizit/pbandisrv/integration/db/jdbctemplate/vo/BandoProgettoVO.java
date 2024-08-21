/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.util.List;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.GenericVO;

public class BandoProgettoVO extends GenericVO {

	

	private BigDecimal idBando;
	private BigDecimal idProgetto;
	private BigDecimal progrBandoLineaIntervento;
	private String titoloBando;
	

	@Override
	public List getPK() {
		return null;
	}

	@Override
	public boolean isPKValid() {
		return false;
	}

	public BigDecimal getIdBando() {
		return idBando;
	}

	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getTitoloBando() {
		return titoloBando;
	}

	public void setTitoloBando(String titoloBando) {
		this.titoloBando = titoloBando;
	}
	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(
			BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

 

}
