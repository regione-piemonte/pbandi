/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.dto.profilazione;

import java.math.BigDecimal;
import java.sql.Date;

public class StoricoSaldoStralcioDTO {
	
	private Long idSaldoStralcio; 
	private String nome;
	private String cognome;
	private String descEsito; 
	private String descRecupero; 
	private String descSaldoStralcio; 
	private Date dataEsito; 				//  dt_esito
	private Boolean flagAgevolazione;
	private BigDecimal impDisimpegno;
	private Date dataProposta;				//	Dt_proposta
	private Date dataAcettazione; 			//	Dt_accettazione
	private BigDecimal impDebitore; 				//	imp_debitore
	private BigDecimal impConfindi; 				//	imp_confidi
	private Date dataPagamDebitore;			//	Dt_pagam_debitore
	private Date dataPagamConfidi;			//	Dt_pagam_confidi
	private BigDecimal impRecuperato; 			//	Imp_recuperato
	private BigDecimal impPerdita; 				//	Imp_perdita
	private Date dataInserimento; 			//	Dt_inserimento
	private Date dataAggiornamento; 		//	Dt_aggiornamento
	private String note;					//	Note
	private Long idEsito;
	private Long idRecupero; 
	
	public BigDecimal getImpDisimpegno() {
		return impDisimpegno;
	}
	public void setImpDisimpegno(BigDecimal impDisimpegno) {
		this.impDisimpegno = impDisimpegno;
	}
	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}
	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}
	public Long getIdEsito() {
		return idEsito;
	}
	public Boolean getFlagAgevolazione() {
		return flagAgevolazione;
	}
	public void setFlagAgevolazione(Boolean flagAgevolazione) {
		this.flagAgevolazione = flagAgevolazione;
	}
	public void setIdEsito(Long idEsito) {
		this.idEsito = idEsito;
	}
	public Long getIdRecupero() {
		return idRecupero;
	}
	public void setIdRecupero(Long idRecupero) {
		this.idRecupero = idRecupero;
	}
	
	
	public Long getIdSaldoStralcio() {
		return idSaldoStralcio;
	}
	public void setIdSaldoStralcio(Long idSaldoStralcio) {
		this.idSaldoStralcio = idSaldoStralcio;
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
	public String getDescEsito() {
		return descEsito;
	}
	public void setDescEsito(String descEsito) {
		this.descEsito = descEsito;
	}
	public String getDescRecupero() {
		return descRecupero;
	}
	public void setDescRecupero(String descRecupero) {
		this.descRecupero = descRecupero;
	}
	public String getDescSaldoStralcio() {
		return descSaldoStralcio;
	}
	public void setDescSaldoStralcio(String descSaldoStralcio) {
		this.descSaldoStralcio = descSaldoStralcio;
	}
	public Date getDataEsito() {
		return dataEsito;
	}
	public void setDataEsito(Date dataEsito) {
		this.dataEsito = dataEsito;
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

	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	
	
	

}
