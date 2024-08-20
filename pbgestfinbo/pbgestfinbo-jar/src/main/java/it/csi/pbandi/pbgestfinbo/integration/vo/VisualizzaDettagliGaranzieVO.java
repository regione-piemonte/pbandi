/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.util.Date;

public class VisualizzaDettagliGaranzieVO {
	
	
	public VisualizzaDettagliGaranzieVO() {
		super();
		//TODO Auto-generated constructor stub
	}
	public VisualizzaDettagliGaranzieVO(String tipoAgevolazione, int importoAmmesso, int totaleFondo, int totaleBanca,
			Date dtConcessione, Date dtErogazioneFinanziamento, int importoDebitoResiduo, int importoEscusso,
			String statoCredito, String revocaBancaria, String azioniRecuperoBanca, String saldoEStralcio) {
		super();
		this.tipoAgevolazione = tipoAgevolazione;
		this.importoAmmesso = importoAmmesso;
		this.totaleFondo = totaleFondo;
		this.totaleBanca = totaleBanca;
		this.dtConcessione = dtConcessione;
		this.dtErogazioneFinanziamento = dtErogazioneFinanziamento;
		this.importoDebitoResiduo = importoDebitoResiduo;
		this.importoEscusso = importoEscusso;
		this.statoCredito = statoCredito;
		this.revocaBancaria = revocaBancaria;
		this.azioniRecuperoBanca = azioniRecuperoBanca;
		this.saldoEStralcio = saldoEStralcio;
	}
	private String tipoAgevolazione; 
	private int importoAmmesso; 
	private int totaleFondo;
	private int totaleBanca;
	private Date dtConcessione; 
	private Date dtErogazioneFinanziamento;
	private int importoDebitoResiduo;
	private int importoEscusso; 
	private String statoCredito; 						
	private String revocaBancaria;							
	private String azioniRecuperoBanca;								
	private String saldoEStralcio;
	
	
	
	public String getTipoAgevolazione() {
		return tipoAgevolazione;
	}
	public void setTipoAgevolazione(String tipoAgevolazione) {
		this.tipoAgevolazione = tipoAgevolazione;
	}
	public int getImportoAmmesso() {
		return importoAmmesso;
	}
	public void setImportoAmmesso(int importoAmmesso) {
		this.importoAmmesso = importoAmmesso;
	}
	public int getTotaleFondo() {
		return totaleFondo;
	}
	public void setTotaleFondo(int totaleFondo) {
		this.totaleFondo = totaleFondo;
	}
	public int getTotaleBanca() {
		return totaleBanca;
	}
	public void setTotaleBanca(int totaleBanca) {
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
	public int getImportoDebitoResiduo() {
		return importoDebitoResiduo;
	}
	public void setImportoDebitoResiduo(int importoDebitoResiduo) {
		this.importoDebitoResiduo = importoDebitoResiduo;
	}
	public int getImportoEscusso() {
		return importoEscusso;
	}
	public void setImportoEscusso(int importoEscusso) {
		this.importoEscusso = importoEscusso;
	}
	public String getStatoCredito() {
		return statoCredito;
	}
	public void setStatoCredito(String statoCredito) {
		this.statoCredito = statoCredito;
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
	
	@Override
	public String toString() {
		return "VisualizzaDettagliGaranzieVO [tipoAgevolazione=" + tipoAgevolazione + ", importoAmmesso="
				+ importoAmmesso + ", totaleFondo=" + totaleFondo + ", totaleBanca=" + totaleBanca + ", dtConcessione="
				+ dtConcessione + ", dtErogazioneFinanziamento=" + dtErogazioneFinanziamento + ", importoDebitoResiduo="
				+ importoDebitoResiduo + ", importoEscusso=" + importoEscusso + ", statoCredito=" + statoCredito
				+ ", revocaBancaria=" + revocaBancaria + ", azioniRecuperoBanca=" + azioniRecuperoBanca
				+ ", saldoEStralcio=" + saldoEStralcio + "]";
	}			
	
}