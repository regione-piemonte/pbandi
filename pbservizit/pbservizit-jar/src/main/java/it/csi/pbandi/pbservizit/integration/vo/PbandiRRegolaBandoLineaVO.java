/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.integration.vo;

public class PbandiRRegolaBandoLineaVO {

	private Long progrBandoLineaIntervento;

	private Long idRegola;

	public PbandiRRegolaBandoLineaVO() {
	}

	public PbandiRRegolaBandoLineaVO(Long progrBandoLineaIntervento, Long idRegola) {

		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
		this.idRegola = idRegola;
	}

	public Long getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(Long progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public Long getIdRegola() {
		return idRegola;
	}

	public void setIdRegola(Long idRegola) {
		this.idRegola = idRegola;
	}

	public boolean isValid() {
		boolean isValid = false;
		if (isPKValid()) {
			isValid = true;
		}
		return isValid;
	}

	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrBandoLineaIntervento != null && idRegola != null) {
			isPkValid = true;
		}

		return isPkValid;
	}

	public java.util.List<String> getPK() {
		java.util.List<String> pk = new java.util.ArrayList<String>();

		pk.add("progrBandoLineaIntervento");
		pk.add("idRegola");

		return pk;
	}

}
