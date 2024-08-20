/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;

public class VisualizzaRevocaBancariaVO {
	
	
	private Date dataRicezComunicazioneRevoca; 
	private Date dataRevoca; 
	private BigDecimal impDebitoResiduoBanca;
	private BigDecimal impDebitoResiduoFinpiemonte;
	private Long perCofinanziamentoFinpiemonte; 
	private String numeroProtocollo;
	private String note;
	
	
	public VisualizzaRevocaBancariaVO() {
		super();
		//TODO Auto-generated constructor stub
	}


	public VisualizzaRevocaBancariaVO(Date dataRicezComunicazioneRevoca, Date dataRevoca, BigDecimal impDebitoResiduoBanca,
			BigDecimal impDebitoResiduoFinpiemonte, Long perCofinanziamentoFinpiemonte, String numeroProtocollo, String note) {
		super();
		this.dataRicezComunicazioneRevoca = dataRicezComunicazioneRevoca;
		this.dataRevoca = dataRevoca;
		this.impDebitoResiduoBanca = impDebitoResiduoBanca;
		this.impDebitoResiduoFinpiemonte = impDebitoResiduoFinpiemonte;
		this.perCofinanziamentoFinpiemonte = perCofinanziamentoFinpiemonte;
		this.numeroProtocollo = numeroProtocollo;
		this.note = note;
	}


	public Date getDataRicezComunicazioneRevoca() {
		return dataRicezComunicazioneRevoca;
	}


	public void setDataRicezComunicazioneRevoca(Date dataRicezComunicazioneRevoca) {
		this.dataRicezComunicazioneRevoca = dataRicezComunicazioneRevoca;
	}


	public Date getDataRevoca() {
		return dataRevoca;
	}


	public void setDataRevoca(Date dataRevoca) {
		this.dataRevoca = dataRevoca;
	}


	public BigDecimal getImpDebitoResiduoBanca() {
		return impDebitoResiduoBanca;
	}


	public void setImpDebitoResiduoBanca(BigDecimal impDebitoResiduoBanca) {
		this.impDebitoResiduoBanca = impDebitoResiduoBanca;
	}


	public BigDecimal getImpDebitoResiduoFinpiemonte() {
		return impDebitoResiduoFinpiemonte;
	}


	public void setImpDebitoResiduoFinpiemonte(BigDecimal impDebitoResiduoFinpiemonte) {
		this.impDebitoResiduoFinpiemonte = impDebitoResiduoFinpiemonte;
	}


	public Long getPerCofinanziamentoFinpiemonte() {
		return perCofinanziamentoFinpiemonte;
	}


	public void setPerCofinanziamentoFinpiemonte(Long perCofinanziamentoFinpiemonte) {
		this.perCofinanziamentoFinpiemonte = perCofinanziamentoFinpiemonte;
	}


	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}


	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}
	
	
	
}
	
	
	
	