/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto;

public class BandoWidgetDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long progrBandoLineaIntervento;
	private String nomeBandoLinea;
	private Long idBandoLinSoggWidget;

	public Long getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(Long progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

	public Long getIdBandoLinSoggWidget() {
		return idBandoLinSoggWidget;
	}

	public void setIdBandoLinSoggWidget(Long idBandoLinSoggWidget) {
		this.idBandoLinSoggWidget = idBandoLinSoggWidget;
	}

}
