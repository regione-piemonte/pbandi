/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class BloccoVO {
	
	
	private String descCausaleBlocco; 
	private Date dataInserimentoBlocco; 
	private Date dataInserimentoSblocco; 
	private Long idBlocco; 
	private BigDecimal idSoggetto;
	private BigDecimal idUtente; 
	private long idCausaleBlocco; 
	private BigDecimal idProgSoggDomanda;
	private String ndg; 
	private String numeroDomanda;
	private String nome; 
	private String cognome; 
	private String descMacroArea; 
	
	
	
	public String getDescMacroArea() {
		return descMacroArea;
	}
	public void setDescMacroArea(String descMacroArea) {
		this.descMacroArea = descMacroArea;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNdg() {
		return ndg;
	}
	public void setNdg(String ndg) {
		this.ndg = ndg;
	}
	public void setIdProgSoggDomanda(BigDecimal idProgSoggDomanda) {
		this.idProgSoggDomanda = idProgSoggDomanda;
	}
	public BigDecimal getIdProgSoggDomanda() {
		return idProgSoggDomanda;
	}
	
	public long getIdCausaleBlocco() {
		return idCausaleBlocco;
	}
	public void setIdCausaleBlocco(long idCausaleBlocco) {
		this.idCausaleBlocco = idCausaleBlocco;
	}

	public void setIdUtente(BigDecimal idUtente) {
		this.idUtente = idUtente;
	}
	public BigDecimal getIdUtente() {
		return idUtente;
	}
	
	public String getDescCausaleBlocco() {
		return descCausaleBlocco;
	}
	public void setDescCausaleBlocco(String descCausaleBlocco) {
		this.descCausaleBlocco = descCausaleBlocco;
	}
	public Date getDataInserimentoBlocco() {
		return dataInserimentoBlocco;
	}
	public void setDataInserimentoBlocco(Date dataInserimentoBlocco) {
		this.dataInserimentoBlocco = dataInserimentoBlocco;
	}
	public Date getDataInserimentoSblocco() {
		return dataInserimentoSblocco;
	}
	public void setDataInserimentoSblocco(Date dataInserimentoSblocco) {
		this.dataInserimentoSblocco = dataInserimentoSblocco;
	}
	public Long getIdBlocco() {
		return idBlocco;
	}
	public void setIdBlocco(Long idBlocco) {
		this.idBlocco = idBlocco;
	}
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getNumeroDomanda() {
		return numeroDomanda;
	}
	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}
	@Override
	public String toString() {
		return "BloccoVO [descCausaleBlocco=" + descCausaleBlocco + ", dataInserimentoBlocco=" + dataInserimentoBlocco
				+ ", dataInserimentoSblocco=" + dataInserimentoSblocco + ", idBlocco=" + idBlocco + ", idSoggetto="
				+ idSoggetto + ", idUtente=" + idUtente + ", idCausaleBlocco=" + idCausaleBlocco
				+ ", idProgSoggDomanda=" + idProgSoggDomanda + ", ndg=" + ndg + ", numeroDomanda=" + numeroDomanda
				+ ", nome=" + nome + ", cognome=" + cognome + ", descMacroArea=" + descMacroArea + "]";
	} 
	
	
	
	

}
