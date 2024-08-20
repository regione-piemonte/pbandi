/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;

public class DettaglioGaranziaVO {
	
	private int idTipoAgevolazione; 
	private String tipoAgevolazione; 
	private BigDecimal importoAmmesso; 
	private BigDecimal totaleFondo; 
	private BigDecimal totaleBanca; 
	private Date dtConcessione; 
	private Date dtErogazioneFinanziamento; 
	private BigDecimal importoDebitoResiduo; 
	private BigDecimal importoEscusso; 
	private String descrizioneStato; 
	private String revocaBancaria; 
	private String azioniRecuperoBanca; 
	private String saldoEStralcio;
	private BigDecimal idProgetto; 
	private String statoCredito;
	
	
	public String getStatoCredito() {
		return statoCredito;
	}
	public void setStatoCredito(String statoCredito) {
		this.statoCredito = statoCredito;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public int getIdTipoAgevolazione() {
		return idTipoAgevolazione;
	}
	public void setIdTipoAgevolazione(int idTipoAgevolazione) {
		this.idTipoAgevolazione = idTipoAgevolazione;
	}
	public String getTipoAgevolazione() {
		return tipoAgevolazione;
	}
	public void setTipoAgevolazione(String tipoAgevolazione) {
		this.tipoAgevolazione = tipoAgevolazione;
	}
	public BigDecimal getImportoAmmesso() {
		return importoAmmesso;
	}
	public void setImportoAmmesso(BigDecimal importoAmmesso) {
		this.importoAmmesso = importoAmmesso;
	}
	public BigDecimal getTotaleFondo() {
		return totaleFondo;
	}
	public void setTotaleFondo(BigDecimal totaleFondo) {
		this.totaleFondo = totaleFondo;
	}
	public BigDecimal getTotaleBanca() {
		return totaleBanca;
	}
	public void setTotaleBanca(BigDecimal totaleBanca) {
		this.totaleBanca = totaleBanca;
	}
	public Date getDtConcessione() {
		return dtConcessione;
	}
	public void setDtConcessione(Date dtConcessione) {
		this.dtConcessione = dtConcessione;
	}
	public Date getDtErogazioneFinanziamento() {
		return dtErogazioneFinanziamento;
	}
	public void setDtErogazioneFinanziamento(Date dtErogazioneFinanziamento) {
		this.dtErogazioneFinanziamento = dtErogazioneFinanziamento;
	}
	public BigDecimal getImportoDebitoResiduo() {
		return importoDebitoResiduo;
	}
	public void setImportoDebitoResiduo(BigDecimal importoDebitoResiduo) {
		this.importoDebitoResiduo = importoDebitoResiduo;
	}
	public BigDecimal getImportoEscusso() {
		return importoEscusso;
	}
	public void setImportoEscusso(BigDecimal importoEscusso) {
		this.importoEscusso = importoEscusso;
	}
	public String getDescrizioneStato() {
		return descrizioneStato;
	}
	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}
	public String getRevocaBancaria() {
		return revocaBancaria;
	}
	public void setRevocaBancaria(String revocaBancaria) {
		this.revocaBancaria = revocaBancaria;
	}
	public String getAzioniRecuperoBanca() {
		return azioniRecuperoBanca;
	}
	public void setAzioniRecuperoBanca(String azioniRecuperoBanca) {
		this.azioniRecuperoBanca = azioniRecuperoBanca;
	}
	public String getSaldoEStralcio() {
		return saldoEStralcio;
	}
	public void setSaldoEStralcio(String saldoEStralcio) {
		this.saldoEStralcio = saldoEStralcio;
	} 

	
	
}
