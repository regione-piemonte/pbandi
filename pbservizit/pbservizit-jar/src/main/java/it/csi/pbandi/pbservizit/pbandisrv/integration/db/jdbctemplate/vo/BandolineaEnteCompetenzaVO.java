/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class BandolineaEnteCompetenzaVO extends GenericVO {
	private BigDecimal idEnteCompetenza;
	private BigDecimal progrBandolineaIntervento;
	private String nomeBandolinea;
    private BigDecimal idRuoloEnteCompetenza;
    private String descBreveRuoloEnte;
    private String descRuoloEnte;
    
    
	public BigDecimal getIdEnteCompetenza() {
		return idEnteCompetenza;
	}
	public void setIdEnteCompetenza(BigDecimal idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}
	public BigDecimal getProgrBandolineaIntervento() {
		return progrBandolineaIntervento;
	}
	public void setProgrBandolineaIntervento(BigDecimal progrBandolineaIntervento) {
		this.progrBandolineaIntervento = progrBandolineaIntervento;
	}
	public String getNomeBandolinea() {
		return nomeBandolinea;
	}
	public void setNomeBandolinea(String nomeBandolinea) {
		this.nomeBandolinea = nomeBandolinea;
	}
	public BigDecimal getIdRuoloEnteCompetenza() {
		return idRuoloEnteCompetenza;
	}
	public void setIdRuoloEnteCompetenza(BigDecimal idRuoloEnteCompetenza) {
		this.idRuoloEnteCompetenza = idRuoloEnteCompetenza;
	}
	public String getDescBreveRuoloEnte() {
		return descBreveRuoloEnte;
	}
	public void setDescBreveRuoloEnte(String descBreveRuoloEnte) {
		this.descBreveRuoloEnte = descBreveRuoloEnte;
	}
	public String getDescRuoloEnte() {
		return descRuoloEnte;
	}
	public void setDescRuoloEnte(String descRuoloEnte) {
		this.descRuoloEnte = descRuoloEnte;
	}
    
    
    

}
