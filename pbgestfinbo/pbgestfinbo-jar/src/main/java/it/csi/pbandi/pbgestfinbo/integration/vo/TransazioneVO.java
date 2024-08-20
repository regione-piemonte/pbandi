/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class TransazioneVO {
	
	private Long idTransazione; 
	private Long idProgetto; 
	private BigDecimal impRiconciliato; 
	private BigDecimal impTransato; 
	private BigDecimal percTransazione; 
	private BigDecimal impPagato; 
	private Long idBanca; 
	private String note; 
	private Date dataInizioValidita; 		
	private Date dataFineValidita;			
	private Date dataInserimento;				
	private Date dataAggiornamento;	
	private Long idUtenteIns;				
	private Long idUtenteAgg;	
	private String descBanca; 
	
	
	
	public String getDescBanca() {
		return descBanca;
	}
	public void setDescBanca(String descBanca) {
		this.descBanca = descBanca;
	}
	public Long getIdTransazione() {
		return idTransazione;
	}
	public void setIdTransazione(Long idTransazione) {
		this.idTransazione = idTransazione;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getImpRiconciliato() {
		return impRiconciliato;
	}
	public void setImpRiconciliato(BigDecimal impRiconciliato) {
		this.impRiconciliato = impRiconciliato;
	}
	public BigDecimal getImpTransato() {
		return impTransato;
	}
	public void setImpTransato(BigDecimal impTransato) {
		this.impTransato = impTransato;
	}
	public BigDecimal getPercTransazione() {
		return percTransazione;
	}
	public void setPercTransazione(BigDecimal percTransazione) {
		this.percTransazione = percTransazione;
	}
	public BigDecimal getImpPagato() {
		return impPagato;
	}
	public void setImpPagato(BigDecimal impPagato) {
		this.impPagato = impPagato;
	}
	public Long getIdBanca() {
		return idBanca;
	}
	public void setIdBanca(Long idBanca) {
		this.idBanca = idBanca;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
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
	public Long getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(Long idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public Long getIdUtenteAgg() {
		return idUtenteAgg;
	}
	public void setIdUtenteAgg(Long idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	
	
}
