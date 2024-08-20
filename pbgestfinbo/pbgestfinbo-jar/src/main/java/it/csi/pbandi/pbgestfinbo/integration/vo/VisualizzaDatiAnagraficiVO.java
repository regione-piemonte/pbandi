/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;

public class VisualizzaDatiAnagraficiVO {
	private String bando; 
	private String progetto; 
	private String denominazione;
	private String cognome;
	private String nome;
	private String codiceFiscale;
	private String partitaIva;
	private String formaGiuridica; 
	private String tipoAnagrafica;
	private String denominazioneBanca;
	private String codBanca;
	private BigDecimal idSoggetto; 
	
	
	public VisualizzaDatiAnagraficiVO() {
		super();
		//TODO Auto-generated constructor stub
	}


	public VisualizzaDatiAnagraficiVO(String bando, String progetto, String denominazione, String cognome, String nome, String codiceFiscale,
			String partitaIva, String formaGiuridica, String tipoAnagrafica, String denominazioneBanca, String codBanca) {
		super();
		this.bando = bando;
		this.progetto = progetto;
		this.denominazione = denominazione;
		this.cognome = cognome;
		this.nome = nome;
		this.codiceFiscale = codiceFiscale;
		this.partitaIva = partitaIva;
		this.formaGiuridica = formaGiuridica;
		this.tipoAnagrafica = tipoAnagrafica;
		this.denominazioneBanca = denominazioneBanca;
		this.codBanca = codBanca;
	}


	


	
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}


	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}


	public String getCodBanca() {
		return codBanca;
	}


	public void setCodBanca(String codBanca) {
		this.codBanca = codBanca;
	}


	public String getDenominazione() {
		return denominazione;
	}


	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}


	public String getBando() {
		return bando;
	}


	public void setBando(String bando) {
		this.bando = bando;
	}


	public String getProgetto() {
		return progetto;
	}


	public void setProgetto(String progetto) {
		this.progetto = progetto;
	}


	

	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}


	public String getPartitaIva() {
		return partitaIva;
	}


	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}


	public String getFormaGiuridica() {
		return formaGiuridica;
	}


	public void setFormaGiuridica(String formaGiuridica) {
		this.formaGiuridica = formaGiuridica;
	}


	public String getTipoAnagrafica() {
		return tipoAnagrafica;
	}


	public void setTipoAnagrafica(String tipoAnagrafica) {
		this.tipoAnagrafica = tipoAnagrafica;
	}


	public String getDenominazioneBanca() {
		return denominazioneBanca;
	}


	public void setDenominazioneBanca(String denominazioneBanca) {
		this.denominazioneBanca = denominazioneBanca;
	}
	
	
	
	
}		