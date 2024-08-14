/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.dto.certificazione;


public class BandoLineaDTO 
implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private Long progrBandoLineaIntervento;
	private String nomeBandoLinea;

	
	public void setProgrBandoLineaIntervento(Long val) {
		progrBandoLineaIntervento = val;
	}

	public Long getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}	

	public void setNomeBandoLinea(String val) {
		nomeBandoLinea = val;
	}

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}



}
