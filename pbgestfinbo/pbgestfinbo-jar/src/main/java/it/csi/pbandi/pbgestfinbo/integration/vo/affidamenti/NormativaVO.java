/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti;

public class NormativaVO {
	
	private Long idLineaIntervento; 
	private String descLinea;
	private Long progrBandoLineaIntervento;
	
	public Long getIdLineaIntervento() {
		return idLineaIntervento;
	}
	public void setIdLineaIntervento(Long idLineaIntervento) {
		this.idLineaIntervento = idLineaIntervento;
	}
	public String getDescLinea() {
		return descLinea;
	}
	public void setDescLinea(String descLinea) {
		this.descLinea = descLinea;
	}
	public Long getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(Long progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	} 
	
	

}
