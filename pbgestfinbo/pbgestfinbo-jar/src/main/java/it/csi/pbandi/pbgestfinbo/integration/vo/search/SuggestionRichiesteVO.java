/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.math.BigDecimal;

public class SuggestionRichiesteVO {
	
	private BigDecimal idSoggetto;
	
	private BigDecimal idPersonaFisica;
	
	private BigDecimal idDomanda;
	
	private BigDecimal idStatoRichiesta;
	
	private BigDecimal idTipoRichiesta;
	
	private String descTipoRichiesta;
	
	private String descStatoRichiesta;
	
	private String numeroDomanda;
	
	private BigDecimal codiceBando;
	
	private String richiedente;
	
	private String cfSoggetto;

	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public BigDecimal getIdPersonaFisica() {
		return idPersonaFisica;
	}

	public void setIdPersonaFisica(BigDecimal idPersonaFisica) {
		this.idPersonaFisica = idPersonaFisica;
	}

	public BigDecimal getIdDomanda() {
		return idDomanda;
	}

	public void setIdDomanda(BigDecimal idDomanda) {
		this.idDomanda = idDomanda;
	}

	public BigDecimal getIdStatoRichiesta() {
		return idStatoRichiesta;
	}

	public void setIdStatoRichiesta(BigDecimal idStatoRichiesta) {
		this.idStatoRichiesta = idStatoRichiesta;
	}

	public BigDecimal getIdTipoRichiesta() {
		return idTipoRichiesta;
	}

	public void setIdTipoRichiesta(BigDecimal idTipoRichiesta) {
		this.idTipoRichiesta = idTipoRichiesta;
	}

	public String getDescTipoRichiesta() {
		return descTipoRichiesta;
	}

	public void setDescTipoRichiesta(String descTipoRichiesta) {
		this.descTipoRichiesta = descTipoRichiesta;
	}

	public String getDescStatoRichiesta() {
		return descStatoRichiesta;
	}

	public void setDescStatoRichiesta(String descStatoRichiesta) {
		this.descStatoRichiesta = descStatoRichiesta;
	}

	public String getNumeroDomanda() {
		return numeroDomanda;
	}

	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}

	public BigDecimal getCodiceBando() {
		return codiceBando;
	}

	public void setCodiceBando(BigDecimal codiceBando) {
		this.codiceBando = codiceBando;
	}

	public String getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(String richiedente) {
		this.richiedente = richiedente;
	}

	public SuggestionRichiesteVO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getCfSoggetto() {
		return cfSoggetto;
	}

	public void setCfSoggetto(String cfSoggetto) {
		this.cfSoggetto = cfSoggetto;
	}

	@Override
	public String toString() {
		return "SuggestionRichiesteVO [idSoggetto=" + idSoggetto + ", idPersonaFisica=" + idPersonaFisica
				+ ", idDomanda=" + idDomanda + ", idStatoRichiesta=" + idStatoRichiesta + ", idTipoRichiesta="
				+ idTipoRichiesta + ", descTipoRichiesta=" + descTipoRichiesta + ", descStatoRichiesta="
				+ descStatoRichiesta + ", numeroDomanda=" + numeroDomanda + ", codiceBando=" + codiceBando
				+ ", richiedente=" + richiedente + ", cfSoggetto=" + cfSoggetto + "]";
	}
	
	
	
	

}
