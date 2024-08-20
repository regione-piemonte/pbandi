/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;

public class VisualizzaSaldoStralcioVO {
	private String descEsito; 
	private Date dataEsito; 
	private String nome;
	private String cognome;
	private Date dataProposta;
	private Date dataAcettazione; 
	private String descSaldoStralcio;
	private BigDecimal impDebitore;
	private BigDecimal impConfidi;
	private Date dataPagamDebitore;
	private Date dataPagamConfidi;
	private String descRecupero;
	private BigDecimal impRecuperato;
	private BigDecimal impPerdita;
	private String note;
	
	public VisualizzaSaldoStralcioVO() {
		super();
	}

	public VisualizzaSaldoStralcioVO(String descEsito, Date dataEsito, String nome, String cognome, Date dataProposta,
			Date dataAcettazione, String descSaldoStralcio, BigDecimal impDebitore, BigDecimal impConfidi, Date dataPagamDebitore,
			Date dataPagamConfidi, String descRecupero, BigDecimal impRecuperato, BigDecimal impPerdita,
			String note) {
		super();
		this.descEsito = descEsito;
		this.dataEsito = dataEsito;
		this.nome = nome;
		this.cognome = cognome;
		this.dataProposta = dataProposta;
		this.dataAcettazione = dataAcettazione;
		this.descSaldoStralcio = descSaldoStralcio;
		this.impDebitore = impDebitore;
		this.impConfidi = impConfidi;
		this.dataPagamDebitore = dataPagamDebitore;
		this.dataPagamConfidi = dataPagamConfidi;
		this.descRecupero = descRecupero;
		this.impRecuperato = impRecuperato;
		this.impPerdita = impPerdita;
		this.note = note;
	}

	public String getDescEsito() {
		return descEsito;
	}

	public void setDescEsito(String descEsito) {
		this.descEsito = descEsito;
	}

	public Date getDataEsito() {
		return dataEsito;
	}

	public void setDataEsito(Date dataEsito) {
		this.dataEsito = dataEsito;
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

	public String getDescSaldoStralcio() {
		return descSaldoStralcio;
	}

	public void setDescSaldoStralcio(String descSaldoStralcio) {
		this.descSaldoStralcio = descSaldoStralcio;
	}

	public BigDecimal getImpDebitore() {
		return impDebitore;
	}

	public void setImpDebitore(BigDecimal impDebitore) {
		this.impDebitore = impDebitore;
	}

	public BigDecimal getImpConfidi() {
		return impConfidi;
	}

	public void setImpConfidi(BigDecimal impConfidi) {
		this.impConfidi = impConfidi;
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

	public String getDescRecupero() {
		return descRecupero;
	}

	public void setDescRecupero(String descRecupero) {
		this.descRecupero = descRecupero;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}		