/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.dto.profilazione;

import java.math.BigDecimal;
import java.sql.Date;

public class StoricoMessaMoraDTO {
	
	private Long idMessaMora; 
	private String nome;
	private String cognome;
	private String descMessaMora; // tipo messa in mora
	private String descRecupero;
	private Date dataMessaMora; 
	private BigDecimal impMessaMoraComplessiva; 
	private BigDecimal impQuotaCapitaleRevoca; 
	private BigDecimal impAgevolazRevoca; 
	private BigDecimal impCreditoResiduo; 
	private BigDecimal impInteressiMora; 
	private Date dataNotifica;
	private Date dataPagamento; 
	private Date dataInserimento; 
	private String note;
	private String numeroProtocollo; // New, richiesto da FP
	
	
	public StoricoMessaMoraDTO(Long idMessaMora, String nome, String cognome, String descMessaMora, String descRecupero,
			Date dataMessaMora, BigDecimal impMessaMoraComplessiva, BigDecimal impQuotaCapitaleRevoca,
			BigDecimal impAgevolazRevoca, BigDecimal impCreditoResiduo, BigDecimal impInteressiMora, Date dataNotifica,
			Date dataPagamento, Date dataInserimento, String note, String numeroProtocollo) {
		this.idMessaMora = idMessaMora;
		this.nome = nome;
		this.cognome = cognome;
		this.descMessaMora = descMessaMora;
		this.descRecupero = descRecupero;
		this.dataMessaMora = dataMessaMora;
		this.impMessaMoraComplessiva = impMessaMoraComplessiva;
		this.impQuotaCapitaleRevoca = impQuotaCapitaleRevoca;
		this.impAgevolazRevoca = impAgevolazRevoca;
		this.impCreditoResiduo = impCreditoResiduo;
		this.impInteressiMora = impInteressiMora;
		this.dataNotifica = dataNotifica;
		this.dataPagamento = dataPagamento;
		this.dataInserimento = dataInserimento;
		this.note = note;
		this.numeroProtocollo = numeroProtocollo;
	}
	
	public StoricoMessaMoraDTO() {
	}

	public Long getIdMessaMora() {
		return idMessaMora;
	}
	public void setIdMessaMora(Long idMessaMora) {
		this.idMessaMora = idMessaMora;
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
	public String getDescMessaMora() {
		return descMessaMora;
	}
	public void setDescMessaMora(String descMessaMora) {
		this.descMessaMora = descMessaMora;
	}
	public String getDescRecupero() {
		return descRecupero;
	}
	public void setDescRecupero(String descRecupero) {
		this.descRecupero = descRecupero;
	}
	public Date getDataMessaMora() {
		return dataMessaMora;
	}
	public void setDataMessaMora(Date dataMessaMora) {
		this.dataMessaMora = dataMessaMora;
	}
	
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
	public Date getDataNotifica() {
		return dataNotifica;
	}
	public void setDataNotifica(Date dataNotifica) {
		this.dataNotifica = dataNotifica;
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
	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}
	@Override
	public String toString() {
		return "StoricoMessaMoraDTO [idMessaMora=" + idMessaMora + ", nome=" + nome + ", cognome=" + cognome
				+ ", descMessaMora=" + descMessaMora + ", descRecupero=" + descRecupero + ", dataMessaMora="
				+ dataMessaMora + ", impMessaMoraComplessiva=" + impMessaMoraComplessiva + ", impQuotaCapitaleRevoca="
				+ impQuotaCapitaleRevoca + ", impAgevolazRevoca=" + impAgevolazRevoca + ", impCreditoResiduo="
				+ impCreditoResiduo + ", impInteressiMora=" + impInteressiMora + ", dataNotifica=" + dataNotifica
				+ ", dataPagamento=" + dataPagamento + ", dataInserimento=" + dataInserimento + ", note=" + note
				+ ", numeroProtocollo=" + numeroProtocollo + "]";
	} 
	
	
	

}
