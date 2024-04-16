/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.vo;

public class BandoVO {

	private String nomeBandoLinea;
	private Long processo;
	private Long progrBandoLineaIntervento;

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

	public Long getProcesso() {
		return processo;
	}

	public void setProcesso(Long processo) {
		this.processo = processo;
	}

	public Long getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(Long progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	@Override
	public String toString() {
		return "BandoVO [nomeBandoLinea=" + nomeBandoLinea + ", processo=" + processo + ", progrBandoLineaIntervento="
				+ progrBandoLineaIntervento + "]";
	}

	

}
