/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;

public class SaldoStralcioVO {
	
	private Long idSaldoStralcio; 			//	Id_saldo_stralcio
	private Long idProgetto; 				//	Id_progetto
	private Long idAttivitaSaldoStralcio;	//	id_attivita_saldo_stralcio
	private Date dataProposta;				//	Dt_proposta
	private Date dataAcettazione; 			//	Dt_accettazione
	private BigDecimal impDebitore; 				//	imp_debitore
	private BigDecimal impConfindi; 				//	imp_confidi
	private Long idAttivitaEsito;			//	Id_attivita_esito
	private Date dataEsito; 				//	dt_esito
	private Boolean flagAgevolazione;
	private Date dataPagamDebitore;			//	Dt_pagam_debitore
	private Date dataPagamConfidi;			//	Dt_pagam_confidi
	private Long idAttivitaRecupero;		//	Id_attivita_recupero
	private BigDecimal impRecuperato; 			//	Imp_recuperato
	private BigDecimal impPerdita; 				//	Imp_perdita
	private BigDecimal impDisimpegno;
	private Date dataInizioValidita;		//	Dt_inizio_validita
	private Date dataFineValidita;			//	Dt_fine_validità
	private Long idUtenteIns;				//	Id_utente_ins
	private Long idUtenteAgg;				//	Id_utente_agg
	private Date dataInserimento; 			//	Dt_inserimento
	private Date dataAggiornamento; 		//	Dt_aggiornamento
	private String note;					//	Note
	
	public Boolean getFlagAgevolazione() {
		return flagAgevolazione;
	}
	public void setFlagAgevolazione(Boolean flagAgevolazione) {
		this.flagAgevolazione = flagAgevolazione;
	}
	public BigDecimal getImpDisimpegno() {
		return impDisimpegno;
	}
	public void setImpDisimpegno(BigDecimal impDisimpegno) {
		this.impDisimpegno = impDisimpegno;
	}
	public Long getIdSaldoStralcio() {
		return idSaldoStralcio;
	}
	public void setIdSaldoStralcio(Long idSaldoStralcio) {
		this.idSaldoStralcio = idSaldoStralcio;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdAttivitaSaldoStralcio() {
		return idAttivitaSaldoStralcio;
	}
	public void setIdAttivitaSaldoStralcio(Long idAttivitaSaldoStralcio) {
		this.idAttivitaSaldoStralcio = idAttivitaSaldoStralcio;
	}
	public Date getDataProposta() {
		return dataProposta;
	}
	public void setDataProposta(Date dataProposta) {
		this.dataProposta = dataProposta;
	}
	public Date getDataAcettazione() {
		return dataAcettazione;
	}
	public void setDataAcettazione(Date dataAcettazione) {
		this.dataAcettazione = dataAcettazione;
	}
	
	public BigDecimal getImpDebitore() {
		return impDebitore;
	}
	public void setImpDebitore(BigDecimal impDebitore) {
		this.impDebitore = impDebitore;
	}
	public BigDecimal getImpConfindi() {
		return impConfindi;
	}
	public void setImpConfindi(BigDecimal impConfindi) {
		this.impConfindi = impConfindi;
	}
	public BigDecimal getImpRecuperato() {
		return impRecuperato;
	}
	public void setImpRecuperato(BigDecimal impRecuperato) {
		this.impRecuperato = impRecuperato;
	}
	public BigDecimal getImpPerdita() {
		return impPerdita;
	}
	public void setImpPerdita(BigDecimal impPerdita) {
		this.impPerdita = impPerdita;
	}
	public Long getIdAttivitaEsito() {
		return idAttivitaEsito;
	}
	public void setIdAttivitaEsito(Long idAttivitaEsito) {
		this.idAttivitaEsito = idAttivitaEsito;
	}
	public Date getDataEsito() {
		return dataEsito;
	}
	public void setDataEsito(Date dataEsito) {
		this.dataEsito = dataEsito;
	}
	public Date getDataPagamDebitore() {
		return dataPagamDebitore;
	}
	public void setDataPagamDebitore(Date dataPagamDebitore) {
		this.dataPagamDebitore = dataPagamDebitore;
	}
	public Date getDataPagamConfidi() {
		return dataPagamConfidi;
	}
	public void setDataPagamConfidi(Date dataPagamConfidi) {
		this.dataPagamConfidi = dataPagamConfidi;
	}
	public Long getIdAttivitaRecupero() {
		return idAttivitaRecupero;
	}
	public void setIdAttivitaRecupero(Long idAttivitaRecupero) {
		this.idAttivitaRecupero = idAttivitaRecupero;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	

}
