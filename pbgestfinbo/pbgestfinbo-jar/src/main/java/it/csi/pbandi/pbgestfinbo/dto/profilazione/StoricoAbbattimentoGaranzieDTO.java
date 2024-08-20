/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.dto.profilazione;

import java.math.BigDecimal;
import java.sql.Date;

public class StoricoAbbattimentoGaranzieDTO {

	
	private Date dataAbbattimento; 
	private String nome;
	private String cognome; 
	private String note; 
	private Long idAbbattimentoGaranzie; 
	private String descAbbattimento;
	private Date dataInserimento; 
	private Date dataAggiornamento; 
	private BigDecimal impIniziale; 
	private BigDecimal impLiberato; 
	private BigDecimal impNuovo;
	
	
	public Date getDataAbbattimento() {
		return dataAbbattimento;
	}
	public void setDataAbbattimento(Date dataAbbattimento) {
		this.dataAbbattimento = dataAbbattimento;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Long getIdAbbattimentoGaranzie() {
		return idAbbattimentoGaranzie;
	}
	public void setIdAbbattimentoGaranzie(Long idAbbattimentoGaranzie) {
		this.idAbbattimentoGaranzie = idAbbattimentoGaranzie;
	}
	public String getDescAbbattimento() {
		return descAbbattimento;
	}
	public void setDescAbbattimento(String descAbbattimento) {
		this.descAbbattimento = descAbbattimento;
	}
	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}
	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}
	public BigDecimal getImpIniziale() {
		return impIniziale;
	}
	public void setImpIniziale(BigDecimal impIniziale) {
		this.impIniziale = impIniziale;
	}
	public BigDecimal getImpLiberato() {
		return impLiberato;
	}
	public void setImpLiberato(BigDecimal impLiberato) {
		this.impLiberato = impLiberato;
	}
	public BigDecimal getImpNuovo() {
		return impNuovo;
	}
	public void setImpNuovo(BigDecimal impNuovo) {
		this.impNuovo = impNuovo;
	}
	
	
	
	

}
