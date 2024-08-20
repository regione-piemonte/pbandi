/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class MessaMoraVO {
		
	private Long idMessaMora; 
	private Long idProgetto ; 
	private Long idAttivitaMora; // tipo messa in mora
	private Date dataMessaMora; 
	private BigDecimal impMessaMoraComplessiva; 
	private BigDecimal impQuotaCapitaleRevoca; 
	private BigDecimal impAgevolazRevoca; 
	private BigDecimal impCreditoResiduo; 
	private BigDecimal impInteressiMora; 
	private Date dataNotifica; 
	private Long idAttivitaRecuperoMora; 
	private Date dataPagamento; 
	private String note; 
	private Date dataInizioValidita; 						
	private Date dataFineValidita;							
	private Long idUtenteIns;								
	private Long idUtenteAgg;						
	private Date dataInserimento; 							
	private Date dataAggiornamento;
	private String numeroProtocollo;  // Aggiunto da richiesta da FP
	
	
	public BigDecimal getImpMessaMoraComplessiva() {
		return impMessaMoraComplessiva;
	}
	public void setImpMessaMoraComplessiva(BigDecimal impMessaMoraComplessiva) {
		this.impMessaMoraComplessiva = impMessaMoraComplessiva;
	}
	public BigDecimal getImpQuotaCapitaleRevoca() {
		return impQuotaCapitaleRevoca;
	}
	public void setImpQuotaCapitaleRevoca(BigDecimal impQuotaCapitaleRevoca) {
		this.impQuotaCapitaleRevoca = impQuotaCapitaleRevoca;
	}
	public BigDecimal getImpAgevolazRevoca() {
		return impAgevolazRevoca;
	}
	public void setImpAgevolazRevoca(BigDecimal impAgevolazRevoca) {
		this.impAgevolazRevoca = impAgevolazRevoca;
	}
	public BigDecimal getImpCreditoResiduo() {
		return impCreditoResiduo;
	}
	public void setImpCreditoResiduo(BigDecimal impCreditoResiduo) {
		this.impCreditoResiduo = impCreditoResiduo;
	}
	public BigDecimal getImpInteressiMora() {
		return impInteressiMora;
	}
	public void setImpInteressiMora(BigDecimal impInteressiMora) {
		this.impInteressiMora = impInteressiMora;
	}
	public Long getIdMessaMora() {
		return idMessaMora;
	}
	public void setIdMessaMora(Long idMessaMora) {
		this.idMessaMora = idMessaMora;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdAttivitaMora() {
		return idAttivitaMora;
	}
	public void setIdAttivitaMora(Long idAttivitaMora) {
		this.idAttivitaMora = idAttivitaMora;
	}
	public Date getDataMessaMora() {
		return dataMessaMora;
	}
	public void setDataMessaMora(Date dataMessaMora) {
		this.dataMessaMora = dataMessaMora;
	}
	
	public Date getDataNotifica() {
		return dataNotifica;
	}
	public void setDataNotifica(Date dataNotifica) {
		this.dataNotifica = dataNotifica;
	}
	public Long getIdAttivitaRecuperoMora() {
		return idAttivitaRecuperoMora;
	}
	public void setIdAttivitaRecuperoMora(Long idAttivitaRecuperoMora) {
		this.idAttivitaRecuperoMora = idAttivitaRecuperoMora;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
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
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}
	
	
}
