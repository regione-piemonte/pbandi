/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;

public class VisualizzaSezioneDettagliGaranziaVO {
	private String tipoAgevolazione; 
	private BigDecimal impAmmesso;
	private BigDecimal totFondo;
	private BigDecimal totBanca;
	private Date dtConcessione;
	private Date dtErogazione;
	private BigDecimal impDebitoResiduo;
	private BigDecimal impEscusso;
	private String statoCredito;
	private String revocaBancaria;
	private String azioniRecBanca;
	private String saldoStralcio;
	
	
	public VisualizzaSezioneDettagliGaranziaVO() {
		super();
		//TODO Auto-generated constructor stub
	}


	public VisualizzaSezioneDettagliGaranziaVO(String tipoAgevolazione, BigDecimal impAmmesso, BigDecimal totFondo,
			BigDecimal totBanca, Date dtConcessione, Date dtErogazione, BigDecimal impDebitoResiduo,
			BigDecimal impEscusso, String statoCredito, String revocaBancaria, String azioniRecBanca,
			String saldoStralcio) {
		super();
		this.tipoAgevolazione = tipoAgevolazione;
		this.impAmmesso = impAmmesso;
		this.totFondo = totFondo;
		this.totBanca = totBanca;
		this.dtConcessione = dtConcessione;
		this.dtErogazione = dtErogazione;
		this.impDebitoResiduo = impDebitoResiduo;
		this.impEscusso = impEscusso;
		this.statoCredito = statoCredito;
		this.revocaBancaria = revocaBancaria;
		this.azioniRecBanca = azioniRecBanca;
		this.saldoStralcio = saldoStralcio;
	}


	public String getTipoAgevolazione() {
		return tipoAgevolazione;
	}


	public void setTipoAgevolazione(String tipoAgevolazione) {
		this.tipoAgevolazione = tipoAgevolazione;
	}


	public BigDecimal getImpAmmesso() {
		return impAmmesso;
	}


	public void setImpAmmesso(BigDecimal impAmmesso) {
		this.impAmmesso = impAmmesso;
	}


	public BigDecimal getTotFondo() {
		return totFondo;
	}


	public void setTotFondo(BigDecimal totFondo) {
		this.totFondo = totFondo;
	}


	public BigDecimal getTotBanca() {
		return totBanca;
	}


	public void setTotBanca(BigDecimal totBanca) {
		this.totBanca = totBanca;
	}


	public Date getDtConcessione() {
		return dtConcessione;
	}


	public void setDtConcessione(Date dtConcessione) {
		this.dtConcessione = dtConcessione;
	}


	public Date getDtErogazione() {
		return dtErogazione;
	}


	public void setDtErogazione(Date dtErogazione) {
		this.dtErogazione = dtErogazione;
	}


	public BigDecimal getImpDebitoResiduo() {
		return impDebitoResiduo;
	}


	public void setImpDebitoResiduo(BigDecimal impDebitoResiduo) {
		this.impDebitoResiduo = impDebitoResiduo;
	}


	public BigDecimal getImpEscusso() {
		return impEscusso;
	}


	public void setImpEscusso(BigDecimal impEscusso) {
		this.impEscusso = impEscusso;
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


	public String getAzioniRecBanca() {
		return azioniRecBanca;
	}


	public void setAzioniRecBanca(String azioniRecBanca) {
		this.azioniRecBanca = azioniRecBanca;
	}


	public String getSaldoStralcio() {
		return saldoStralcio;
	}


	public void setSaldoStralcio(String saldoStralcio) {
		this.saldoStralcio = saldoStralcio;
	}
	
	
	
	
}		