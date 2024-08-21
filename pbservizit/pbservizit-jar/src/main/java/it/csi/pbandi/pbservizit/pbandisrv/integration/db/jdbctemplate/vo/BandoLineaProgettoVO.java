/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class BandoLineaProgettoVO extends GenericVO {
	
	private String codiceVisualizzato;
	private BigDecimal idProgetto;
	private BigDecimal idBando;
	private String nomeBandoLinea;
	private String titoloProgetto;
	private BigDecimal progrBandoLineaIntervento;
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public BigDecimal getIdBando() {
		return idBando;
	}
	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}
	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}
	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}
	public String getTitoloProgetto() {
		return titoloProgetto;
	}
	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}
	
	

}
