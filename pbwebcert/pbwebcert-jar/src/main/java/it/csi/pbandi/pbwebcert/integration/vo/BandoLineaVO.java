/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;

public class BandoLineaVO {
	private BigDecimal progrBandoLineaIntervento;
	private String nomeBandoLinea;
	private BigDecimal idBando;
	private BigDecimal idLinea;
	
	public void setProgrBandoLineaIntervento(BigDecimal val) {
		progrBandoLineaIntervento = val;
	}

	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}	

	public void setNomeBandoLinea(String val) {
		nomeBandoLinea = val;
	}

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setIdBando(BigDecimal val) {
		idBando = val;
	}

	public BigDecimal getIdBando() {
		return idBando;
	}

	public void setIdLinea(BigDecimal val) {
		idLinea = val;
	}

	public BigDecimal getIdLinea() {
		return idLinea;
	}
}
