/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class EnteDiAppartenenzaVO extends GenericVO {
	
	private BigDecimal idProgetto;
	private String descBreveRuoloEnte;
	private BigDecimal progrBandoLineaIntervento;
	private String descEnte;
	private String indirizzo;
	private String cap;
	private String descComune;
	private String siglaProvincia;
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getDescBreveRuoloEnte() {
		return descBreveRuoloEnte;
	}
	public void setDescBreveRuoloEnte(String descBreveRuoloEnte) {
		this.descBreveRuoloEnte = descBreveRuoloEnte;
	}
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public String getDescEnte() {
		return descEnte;
	}
	public void setDescEnte(String descEnte) {
		this.descEnte = descEnte;
	}
	
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getDescComune() {
		return descComune;
	}
	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}
	public String getSiglaProvincia() {
		return siglaProvincia;
	}
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getIndirizzo() {
		return indirizzo;
	}

}
