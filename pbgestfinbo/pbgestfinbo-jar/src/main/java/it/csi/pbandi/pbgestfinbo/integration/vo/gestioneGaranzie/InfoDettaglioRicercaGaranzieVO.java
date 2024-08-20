/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.gestioneGaranzie;

import java.math.BigDecimal;
import java.util.Date;


public class InfoDettaglioRicercaGaranzieVO {
	
	// REVOCA BANCARIA //
	private Long revoca_idRevocaBancaria;
	private Date revoca_dataRicezioneComunicazione;
	private Date revoca_dataRevoca;
	private BigDecimal revoca_importoDebitoResiduoBanca;
	private BigDecimal revoca_importoDebitoResiduoFinpiemonte;
	private String revoca_numeroProtocollo;
	private String revoca_note;	
	
	// AZIONE RECUPERO BANCA //
	private Long azione_idAzioneRecuperoBanca;
	private Long azione_idAttivitaAzione;
	private String azione_descAttivitaAzione;
	private Date azione_dataAzione;
	private String azione_numeroProtocollo;
	private String azione_note;
	
	// SALDO E STRALCIO //
	private Long saldo_idSaldoStralcio;
	private Long saldo_idAttivita;
	private String saldo_descBreveAttivita;
	private String saldo_descAttivita;
	private Date saldo_dataProposta;
	private Date saldo_dataAccettazione;
	private BigDecimal saldo_importoDebitore;
	private BigDecimal saldo_importoConfidi;
	private BigDecimal saldo_importoDisimpegno;
	private Boolean saldo_flagAgevolazione;
	private Long saldo_idAttivitaEsito;
	private String saldo_descBreveEsito;
	private String saldo_descEsito;
	private Date saldo_dataEsito;
	private Date saldo_dataPagamentoDebitore;
	private Date saldo_dataPagamentoConfidi;
	private Long saldo_idAttivitaRecupero;
	private String saldo_descBreveRecupero;
	private String saldo_descRecupero;
	private BigDecimal saldo_importoRecuperato;
	private BigDecimal saldo_importoPerdita;
	private String saldo_note;
	
	// COMMON //
	private Date com_dataInizioValidita;
	private Date com_dataInserimento;
	private Date com_dataAggiornamento;
	private String com_utenteIns;
	private String com_utenteAgg;
	
	
	
	
	
	public InfoDettaglioRicercaGaranzieVO(Long revoca_idRevocaBancaria, Date revoca_dataRicezioneComunicazione,
			Date revoca_dataRevoca, BigDecimal revoca_importoDebitoResiduoBanca,
			BigDecimal revoca_importoDebitoResiduoFinpiemonte, String revoca_numeroProtocollo, String revoca_note,
			Long azione_idAzioneRecuperoBanca, Long azione_idAttivitaAzione, String azione_descAttivitaAzione,
			Date azione_dataAzione, String azione_numeroProtocollo, String azione_note, Long saldo_idSaldoStralcio,
			Long saldo_idAttivita, String saldo_descBreveAttivita, String saldo_descAttivita, Date saldo_dataProposta,
			Date saldo_dataAccettazione, BigDecimal saldo_importoDebitore, BigDecimal saldo_importoConfidi,
			BigDecimal saldo_importoDisimpegno, Boolean saldo_flagAgevolazione, Long saldo_idAttivitaEsito,
			String saldo_descBreveEsito, String saldo_descEsito, Date saldo_dataEsito, Date saldo_dataPagamentoDebitore,
			Date saldo_dataPagamentoConfidi, Long saldo_idAttivitaRecupero, String saldo_descBreveRecupero,
			String saldo_descRecupero, BigDecimal saldo_importoRecuperato, BigDecimal saldo_importoPerdita,
			String saldo_note, Date com_dataInizioValidita, Date com_dataInserimento, Date com_dataAggiornamento,
			String com_utenteIns, String com_utenteAgg) {
		this.revoca_idRevocaBancaria = revoca_idRevocaBancaria;
		this.revoca_dataRicezioneComunicazione = revoca_dataRicezioneComunicazione;
		this.revoca_dataRevoca = revoca_dataRevoca;
		this.revoca_importoDebitoResiduoBanca = revoca_importoDebitoResiduoBanca;
		this.revoca_importoDebitoResiduoFinpiemonte = revoca_importoDebitoResiduoFinpiemonte;
		this.revoca_numeroProtocollo = revoca_numeroProtocollo;
		this.revoca_note = revoca_note;
		this.azione_idAzioneRecuperoBanca = azione_idAzioneRecuperoBanca;
		this.azione_idAttivitaAzione = azione_idAttivitaAzione;
		this.azione_descAttivitaAzione = azione_descAttivitaAzione;
		this.azione_dataAzione = azione_dataAzione;
		this.azione_numeroProtocollo = azione_numeroProtocollo;
		this.azione_note = azione_note;
		this.saldo_idSaldoStralcio = saldo_idSaldoStralcio;
		this.saldo_idAttivita = saldo_idAttivita;
		this.saldo_descBreveAttivita = saldo_descBreveAttivita;
		this.saldo_descAttivita = saldo_descAttivita;
		this.saldo_dataProposta = saldo_dataProposta;
		this.saldo_dataAccettazione = saldo_dataAccettazione;
		this.saldo_importoDebitore = saldo_importoDebitore;
		this.saldo_importoConfidi = saldo_importoConfidi;
		this.saldo_importoDisimpegno = saldo_importoDisimpegno;
		this.saldo_flagAgevolazione = saldo_flagAgevolazione;
		this.saldo_idAttivitaEsito = saldo_idAttivitaEsito;
		this.saldo_descBreveEsito = saldo_descBreveEsito;
		this.saldo_descEsito = saldo_descEsito;
		this.saldo_dataEsito = saldo_dataEsito;
		this.saldo_dataPagamentoDebitore = saldo_dataPagamentoDebitore;
		this.saldo_dataPagamentoConfidi = saldo_dataPagamentoConfidi;
		this.saldo_idAttivitaRecupero = saldo_idAttivitaRecupero;
		this.saldo_descBreveRecupero = saldo_descBreveRecupero;
		this.saldo_descRecupero = saldo_descRecupero;
		this.saldo_importoRecuperato = saldo_importoRecuperato;
		this.saldo_importoPerdita = saldo_importoPerdita;
		this.saldo_note = saldo_note;
		this.com_dataInizioValidita = com_dataInizioValidita;
		this.com_dataInserimento = com_dataInserimento;
		this.com_dataAggiornamento = com_dataAggiornamento;
		this.com_utenteIns = com_utenteIns;
		this.com_utenteAgg = com_utenteAgg;
	}
	
	
	public InfoDettaglioRicercaGaranzieVO() {
	}


	public Long getAzione_idAzioneRecuperoBanca() {
		return azione_idAzioneRecuperoBanca;
	}
	public void setAzione_idAzioneRecuperoBanca(Long azione_idAzioneRecuperoBanca) {
		this.azione_idAzioneRecuperoBanca = azione_idAzioneRecuperoBanca;
	}
	public Long getAzione_idAttivitaAzione() {
		return azione_idAttivitaAzione;
	}
	public void setAzione_idAttivitaAzione(Long azione_idAttivitaAzione) {
		this.azione_idAttivitaAzione = azione_idAttivitaAzione;
	}
	public String getAzione_descAttivitaAzione() {
		return azione_descAttivitaAzione;
	}
	public void setAzione_descAttivitaAzione(String azione_descAttivitaAzione) {
		this.azione_descAttivitaAzione = azione_descAttivitaAzione;
	}
	public Date getAzione_dataAzione() {
		return azione_dataAzione;
	}
	public void setAzione_dataAzione(Date azione_dataAzione) {
		this.azione_dataAzione = azione_dataAzione;
	}
	public String getAzione_numeroProtocollo() {
		return azione_numeroProtocollo;
	}
	public void setAzione_numeroProtocollo(String azione_numeroProtocollo) {
		this.azione_numeroProtocollo = azione_numeroProtocollo;
	}
	public String getAzione_note() {
		return azione_note;
	}
	public void setAzione_note(String azione_note) {
		this.azione_note = azione_note;
	}
	public Date getCom_dataInizioValidita() {
		return com_dataInizioValidita;
	}
	public void setCom_dataInizioValidita(Date com_dataInizioValidita) {
		this.com_dataInizioValidita = com_dataInizioValidita;
	}
	public Date getCom_dataInserimento() {
		return com_dataInserimento;
	}
	public void setCom_dataInserimento(Date com_dataInserimento) {
		this.com_dataInserimento = com_dataInserimento;
	}
	public Date getCom_dataAggiornamento() {
		return com_dataAggiornamento;
	}
	public void setCom_dataAggiornamento(Date com_dataAggiornamento) {
		this.com_dataAggiornamento = com_dataAggiornamento;
	}
	public String getCom_utenteIns() {
		return com_utenteIns;
	}
	public void setCom_utenteIns(String com_utenteIns) {
		this.com_utenteIns = com_utenteIns;
	}
	public String getCom_utenteAgg() {
		return com_utenteAgg;
	}
	public void setCom_utenteAgg(String com_utenteAgg) {
		this.com_utenteAgg = com_utenteAgg;
	}
	public Long getRevoca_idRevocaBancaria() {
		return revoca_idRevocaBancaria;
	}
	public void setRevoca_idRevocaBancaria(Long revoca_idRevocaBancaria) {
		this.revoca_idRevocaBancaria = revoca_idRevocaBancaria;
	}
	public Date getRevoca_dataRicezioneComunicazione() {
		return revoca_dataRicezioneComunicazione;
	}
	public void setRevoca_dataRicezioneComunicazione(Date revoca_dataRicezioneComunicazione) {
		this.revoca_dataRicezioneComunicazione = revoca_dataRicezioneComunicazione;
	}
	public Date getRevoca_dataRevoca() {
		return revoca_dataRevoca;
	}
	public void setRevoca_dataRevoca(Date revoca_dataRevoca) {
		this.revoca_dataRevoca = revoca_dataRevoca;
	}
	public BigDecimal getRevoca_importoDebitoResiduoBanca() {
		return revoca_importoDebitoResiduoBanca;
	}
	public void setRevoca_importoDebitoResiduoBanca(BigDecimal revoca_importoDebitoResiduoBanca) {
		this.revoca_importoDebitoResiduoBanca = revoca_importoDebitoResiduoBanca;
	}
	public BigDecimal getRevoca_importoDebitoResiduoFinpiemonte() {
		return revoca_importoDebitoResiduoFinpiemonte;
	}
	public void setRevoca_importoDebitoResiduoFinpiemonte(BigDecimal revoca_importoDebitoResiduoFinpiemonte) {
		this.revoca_importoDebitoResiduoFinpiemonte = revoca_importoDebitoResiduoFinpiemonte;
	}
	public String getRevoca_numeroProtocollo() {
		return revoca_numeroProtocollo;
	}
	public void setRevoca_numeroProtocollo(String revoca_numeroProtocollo) {
		this.revoca_numeroProtocollo = revoca_numeroProtocollo;
	}
	public String getRevoca_note() {
		return revoca_note;
	}
	public void setRevoca_note(String revoca_note) {
		this.revoca_note = revoca_note;
	}
	public Long getSaldo_idSaldoStralcio() {
		return saldo_idSaldoStralcio;
	}
	public void setSaldo_idSaldoStralcio(Long saldo_idSaldoStralcio) {
		this.saldo_idSaldoStralcio = saldo_idSaldoStralcio;
	}
	public Long getSaldo_idAttivita() {
		return saldo_idAttivita;
	}
	public void setSaldo_idAttivita(Long saldo_idAttivita) {
		this.saldo_idAttivita = saldo_idAttivita;
	}
	public String getSaldo_descBreveAttivita() {
		return saldo_descBreveAttivita;
	}
	public void setSaldo_descBreveAttivita(String saldo_descBreveAttivita) {
		this.saldo_descBreveAttivita = saldo_descBreveAttivita;
	}
	public String getSaldo_descAttivita() {
		return saldo_descAttivita;
	}
	public void setSaldo_descAttivita(String saldo_descAttivita) {
		this.saldo_descAttivita = saldo_descAttivita;
	}
	public Date getSaldo_dataProposta() {
		return saldo_dataProposta;
	}
	public void setSaldo_dataProposta(Date saldo_dataProposta) {
		this.saldo_dataProposta = saldo_dataProposta;
	}
	public Date getSaldo_dataAccettazione() {
		return saldo_dataAccettazione;
	}
	public void setSaldo_dataAccettazione(Date saldo_dataAccettazione) {
		this.saldo_dataAccettazione = saldo_dataAccettazione;
	}
	public BigDecimal getSaldo_importoDebitore() {
		return saldo_importoDebitore;
	}
	public void setSaldo_importoDebitore(BigDecimal saldo_importoDebitore) {
		this.saldo_importoDebitore = saldo_importoDebitore;
	}
	public BigDecimal getSaldo_importoConfidi() {
		return saldo_importoConfidi;
	}
	public void setSaldo_importoConfidi(BigDecimal saldo_importoConfidi) {
		this.saldo_importoConfidi = saldo_importoConfidi;
	}
	public BigDecimal getSaldo_importoDisimpegno() {
		return saldo_importoDisimpegno;
	}
	public void setSaldo_importoDisimpegno(BigDecimal saldo_importoDisimpegno) {
		this.saldo_importoDisimpegno = saldo_importoDisimpegno;
	}
	public Boolean getSaldo_flagAgevolazione() {
		return saldo_flagAgevolazione;
	}
	public void setSaldo_flagAgevolazione(Boolean saldo_flagAgevolazione) {
		this.saldo_flagAgevolazione = saldo_flagAgevolazione;
	}
	public Long getSaldo_idAttivitaEsito() {
		return saldo_idAttivitaEsito;
	}
	public void setSaldo_idAttivitaEsito(Long saldo_idAttivitaEsito) {
		this.saldo_idAttivitaEsito = saldo_idAttivitaEsito;
	}
	public String getSaldo_descBreveEsito() {
		return saldo_descBreveEsito;
	}
	public void setSaldo_descBreveEsito(String saldo_descBreveEsito) {
		this.saldo_descBreveEsito = saldo_descBreveEsito;
	}
	public String getSaldo_descEsito() {
		return saldo_descEsito;
	}
	public void setSaldo_descEsito(String saldo_descEsito) {
		this.saldo_descEsito = saldo_descEsito;
	}
	public Date getSaldo_dataEsito() {
		return saldo_dataEsito;
	}
	public void setSaldo_dataEsito(Date saldo_dataEsito) {
		this.saldo_dataEsito = saldo_dataEsito;
	}
	public Date getSaldo_dataPagamentoDebitore() {
		return saldo_dataPagamentoDebitore;
	}
	public void setSaldo_dataPagamentoDebitore(Date saldo_dataPagamentoDebitore) {
		this.saldo_dataPagamentoDebitore = saldo_dataPagamentoDebitore;
	}
	public Date getSaldo_dataPagamentoConfidi() {
		return saldo_dataPagamentoConfidi;
	}
	public void setSaldo_dataPagamentoConfidi(Date saldo_dataPagamentoConfidi) {
		this.saldo_dataPagamentoConfidi = saldo_dataPagamentoConfidi;
	}
	public Long getSaldo_idAttivitaRecupero() {
		return saldo_idAttivitaRecupero;
	}
	public void setSaldo_idAttivitaRecupero(Long saldo_idAttivitaRecupero) {
		this.saldo_idAttivitaRecupero = saldo_idAttivitaRecupero;
	}
	public String getSaldo_descBreveRecupero() {
		return saldo_descBreveRecupero;
	}
	public void setSaldo_descBreveRecupero(String saldo_descBreveRecupero) {
		this.saldo_descBreveRecupero = saldo_descBreveRecupero;
	}
	public String getSaldo_descRecupero() {
		return saldo_descRecupero;
	}
	public void setSaldo_descRecupero(String saldo_descRecupero) {
		this.saldo_descRecupero = saldo_descRecupero;
	}
	public BigDecimal getSaldo_importoRecuperato() {
		return saldo_importoRecuperato;
	}
	public void setSaldo_importoRecuperato(BigDecimal saldo_importoRecuperato) {
		this.saldo_importoRecuperato = saldo_importoRecuperato;
	}
	public BigDecimal getSaldo_importoPerdita() {
		return saldo_importoPerdita;
	}
	public void setSaldo_importoPerdita(BigDecimal saldo_importoPerdita) {
		this.saldo_importoPerdita = saldo_importoPerdita;
	}
	public String getSaldo_note() {
		return saldo_note;
	}
	public void setSaldo_note(String saldo_note) {
		this.saldo_note = saldo_note;
	}
	@Override
	public String toString() {
		return "InfoDettaglioRicercaGaranzieVO [revoca_idRevocaBancaria=" + revoca_idRevocaBancaria
				+ ", revoca_dataRicezioneComunicazione=" + revoca_dataRicezioneComunicazione + ", revoca_dataRevoca="
				+ revoca_dataRevoca + ", revoca_importoDebitoResiduoBanca=" + revoca_importoDebitoResiduoBanca
				+ ", revoca_importoDebitoResiduoFinpiemonte=" + revoca_importoDebitoResiduoFinpiemonte
				+ ", revoca_numeroProtocollo=" + revoca_numeroProtocollo + ", revoca_note=" + revoca_note
				+ ", azione_idAzioneRecuperoBanca=" + azione_idAzioneRecuperoBanca + ", azione_idAttivitaAzione="
				+ azione_idAttivitaAzione + ", azione_descAttivitaAzione=" + azione_descAttivitaAzione
				+ ", azione_dataAzione=" + azione_dataAzione + ", azione_numeroProtocollo=" + azione_numeroProtocollo
				+ ", azione_note=" + azione_note + ", saldo_idSaldoStralcio=" + saldo_idSaldoStralcio
				+ ", saldo_idAttivita=" + saldo_idAttivita + ", saldo_descBreveAttivita=" + saldo_descBreveAttivita
				+ ", saldo_descAttivita=" + saldo_descAttivita + ", saldo_dataProposta=" + saldo_dataProposta
				+ ", saldo_dataAccettazione=" + saldo_dataAccettazione + ", saldo_importoDebitore="
				+ saldo_importoDebitore + ", saldo_importoConfidi=" + saldo_importoConfidi
				+ ", saldo_importoDisimpegno=" + saldo_importoDisimpegno + ", saldo_flagAgevolazione="
				+ saldo_flagAgevolazione + ", saldo_idAttivitaEsito=" + saldo_idAttivitaEsito
				+ ", saldo_descBreveEsito=" + saldo_descBreveEsito + ", saldo_descEsito=" + saldo_descEsito
				+ ", saldo_dataEsito=" + saldo_dataEsito + ", saldo_dataPagamentoDebitore="
				+ saldo_dataPagamentoDebitore + ", saldo_dataPagamentoConfidi=" + saldo_dataPagamentoConfidi
				+ ", saldo_idAttivitaRecupero=" + saldo_idAttivitaRecupero + ", saldo_descBreveRecupero="
				+ saldo_descBreveRecupero + ", saldo_descRecupero=" + saldo_descRecupero + ", saldo_importoRecuperato="
				+ saldo_importoRecuperato + ", saldo_importoPerdita=" + saldo_importoPerdita + ", saldo_note="
				+ saldo_note + ", com_dataInizioValidita=" + com_dataInizioValidita + ", com_dataInserimento="
				+ com_dataInserimento + ", com_dataAggiornamento=" + com_dataAggiornamento + ", com_utenteIns="
				+ com_utenteIns + ", com_utenteAgg=" + com_utenteAgg + "]";
	}
	
	

}
