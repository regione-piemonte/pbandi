/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.monitoraggioTempi;

import java.io.Serializable;
import java.util.Date;

public class ParametriMonitoraggioTempiAssociatiDTO implements Serializable {
	
	private Long idParamMonitBandoLinea;
	private Long idParametroMonit;
	private Long progrBandoLineaIntervento;
	private Integer numGiorni;
	private Date dtInizioValidita;
	private Date dtFineValidita;
	private String descBreveParametroMonit;
	private String descParametroMonit;
	
	
	
	public Long getIdParamMonitBandoLinea() {
		return idParamMonitBandoLinea;
	}
	public void setIdParamMonitBandoLinea(Long idParamMonitBandoLinea) {
		this.idParamMonitBandoLinea = idParamMonitBandoLinea;
	}
	public Long getIdParametroMonit() {
		return idParametroMonit;
	}
	public void setIdParametroMonit(Long idParametroMonit) {
		this.idParametroMonit = idParametroMonit;
	}
	public Long getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(Long progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public Integer getNumGiorni() {
		return numGiorni;
	}
	public void setNumGiorni(Integer numGiorni) {
		this.numGiorni = numGiorni;
	}
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	public String getDescBreveParametroMonit() {
		return descBreveParametroMonit;
	}
	public void setDescBreveParametroMonit(String decBreveParametroMonit) {
		this.descBreveParametroMonit = decBreveParametroMonit;
	}
	public String getDescParametroMonit() {
		return descParametroMonit;
	}
	public void setDescParametroMonit(String descParametroMonit) {
		this.descParametroMonit = descParametroMonit;
	}
	
	
	
	
	

}
