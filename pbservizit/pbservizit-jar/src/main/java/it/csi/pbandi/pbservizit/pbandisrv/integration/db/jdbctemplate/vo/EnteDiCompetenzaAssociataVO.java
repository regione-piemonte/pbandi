/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;


public class EnteDiCompetenzaAssociataVO extends GenericVO {

	private String descrizione;
	private String descEnte;
	private String descBreveEnte;
	private String ruolo;
	private BigDecimal progrBandoLineaIntervento;
	private BigDecimal progrBandoLineaEnteComp;
	private String parolaChiave;
	private String feedbackActa;
	private BigDecimal conservazioneCorrente;
	private BigDecimal conservazioneGenerale;
	private String indirizzoMailPec; 
	private String indirizzoMail;
	
	public BigDecimal getConservazioneCorrente() {
		return conservazioneCorrente;
	}

	public void setConservazioneCorrente(BigDecimal conservazioneCorrente) {
		this.conservazioneCorrente = conservazioneCorrente;
	}

	public BigDecimal getConservazioneGenerale() {
		return conservazioneGenerale;
	}

	public void setConservazioneGenerale(BigDecimal conservazioneGenerale) {
		this.conservazioneGenerale = conservazioneGenerale;
	}

	public String getParolaChiave() {
		return parolaChiave;
	}

	public void setParolaChiave(String parolaChiave) {
		this.parolaChiave = parolaChiave;
	}

	public String getFeedbackActa() {
		return feedbackActa;
	}

	public void setFeedbackActa(String feedbackActa) {
		this.feedbackActa = feedbackActa;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescEnte(String descEnte) {
		this.descEnte = descEnte;
	}

	public String getDescEnte() {
		return descEnte;
	}

	public void setDescBreveEnte(String descBreveEnte) {
		this.descBreveEnte = descBreveEnte;
	}

	public String getDescBreveEnte() {
		return descBreveEnte;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getRuolo() {
		return ruolo;
	}

	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public BigDecimal getProgrBandoLineaEnteComp() {
		return progrBandoLineaEnteComp;
	}

	public void setProgrBandoLineaEnteComp(BigDecimal progrBandoLineaEnteComp) {
		this.progrBandoLineaEnteComp = progrBandoLineaEnteComp;
	}

	public String getIndirizzoMailPec() {
		return indirizzoMailPec;
	}

	public void setIndirizzoMailPec(String indirizzoMailPec) {
		this.indirizzoMailPec = indirizzoMailPec;
	}

	public String getIndirizzoMail() {
		return indirizzoMail;
	}

	public void setIndirizzoMail(String indirizzoMail) {
		this.indirizzoMail = indirizzoMail;
	}

 

}