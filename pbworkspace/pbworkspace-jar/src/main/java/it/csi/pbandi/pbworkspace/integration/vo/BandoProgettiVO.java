/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.vo;

import java.util.List;

public class BandoProgettiVO {
	private String nomeBandoLinea;
	private Long progrBandoLineaIntervento;
	private List<ProgettoNotificheVO> progetti;

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

	public Long getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(Long progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public List<ProgettoNotificheVO> getProgetti() {
		return progetti;
	}

	public void setProgetti(List<ProgettoNotificheVO> progetti) {
		this.progetti = progetti;
	}

}
