/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

public class ElencoDomandeProgettiVO {
	
	
	private Long codiceBando;
	private String numeroDomanda;
	private String descStatoDomanda;
	private String descProgetto;
	private String descStatoProgetto;
	private String legaleRappresentante;
	private Long idDomanda; 
	private Long idProgetto; 
	
	
	
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdDomanda() {
		return idDomanda;
	}
	public void setIdDomanda(Long idDomanda) {
		this.idDomanda = idDomanda;
	}
	public Long getCodiceBando() {
		return codiceBando;
	}
	public void setCodiceBando(Long codiceBando) {
		this.codiceBando = codiceBando;
	}
	public String getNumeroDomanda() {
		return numeroDomanda;
	}
	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}
	public String getDescStatoDomanda() {
		return descStatoDomanda;
	}
	public void setDescStatoDomanda(String descStatoDomanda) {
		this.descStatoDomanda = descStatoDomanda;
	}
	public String getDescProgetto() {
		return descProgetto;
	}
	public void setDescProgetto(String descProgetto) {
		this.descProgetto = descProgetto;
	}
	public String getDescStatoProgetto() {
		return descStatoProgetto;
	}
	public void setDescStatoProgetto(String descStatoProgetto) {
		this.descStatoProgetto = descStatoProgetto;
	}
	public String getLegaleRappresentante() {
		return legaleRappresentante;
	}
	public void setLegaleRappresentante(String legaleRappresentante) {
		this.legaleRappresentante = legaleRappresentante;
	}
	@Override
	public String toString() {
		return "ElencoDomandeProgettiVO [codiceBando=" + codiceBando + ", numeroDomanda=" + numeroDomanda
				+ ", descStatoDomanda=" + descStatoDomanda + ", descProgetto=" + descProgetto + ", descStatoProgetto="
				+ descStatoProgetto + ", legaleRappresentante=" + legaleRappresentante + "]";
	}
	
	
	
	
	
	
}
