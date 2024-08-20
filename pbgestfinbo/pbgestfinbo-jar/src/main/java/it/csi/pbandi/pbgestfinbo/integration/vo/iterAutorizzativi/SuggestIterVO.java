/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.iterAutorizzativi;

import java.math.BigDecimal;

public class SuggestIterVO {
	
	private int id ; 
	private BigDecimal  idSoggetto; 
	private BigDecimal idProgetto; 
	private BigDecimal progBandoLinea; 
	private String denominazione; 
	private String codiceVisualizzato;
	private String nomeBando; 
	private String value;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getProgBandoLinea() {
		return progBandoLinea;
	}
	public void setProgBandoLinea(BigDecimal progBandoLinea) {
		this.progBandoLinea = progBandoLinea;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public String getNomeBando() {
		return nomeBando;
	}
	public void setNomeBando(String nomeBando) {
		this.nomeBando = nomeBando;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	} 
	
	
	
}
