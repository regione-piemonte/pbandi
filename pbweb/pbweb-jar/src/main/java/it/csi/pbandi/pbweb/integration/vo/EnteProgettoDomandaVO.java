/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.vo;

import java.sql.Date;

public class EnteProgettoDomandaVO {
	
	private String settore;
	private Long progBandoLineaEnteComp;
	private Long idRuoloEnteCompetenza;
	private String descBreveRuoloEnte;
	private Long idEnteCompetenza;
	private String descEnteCompetenza;
	private String descBreveEnte;
	private String descBreveTipoEnteCompetenz;
	private Long progBandoLineaIntervento;
	private Long idSettoreEnte;
	private String descBreveSettore;
	private String descSettore;
	private String descEnteSettore;
	private Long idIndirizzo;
	private Date dtFineValidita;
	private String parolaChiave;
	private String feedbackActa;
	private Long conservazioneCorrente;
	private Long conservazioneGenerale;
	
	public String getSettore() {
		return settore;
	}
	public void setSettore(String settore) {
		this.settore = settore;
	}
	public Long getProgBandoLineaEnteComp() {
		return progBandoLineaEnteComp;
	}
	public void setProgBandoLineaEnteComp(Long progBandoLineaEnteComp) {
		this.progBandoLineaEnteComp = progBandoLineaEnteComp;
	}
	public Long getIdRuoloEnteCompetenza() {
		return idRuoloEnteCompetenza;
	}
	public void setIdRuoloEnteCompetenza(Long idRuoloEnteCompetenza) {
		this.idRuoloEnteCompetenza = idRuoloEnteCompetenza;
	}
	public String getDescBreveRuoloEnte() {
		return descBreveRuoloEnte;
	}
	public void setDescBreveRuoloEnte(String descBreveRuoloEnte) {
		this.descBreveRuoloEnte = descBreveRuoloEnte;
	}
	public Long getIdEnteCompetenza() {
		return idEnteCompetenza;
	}
	public void setIdEnteCompetenza(Long idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}
	public String getDescEnteCompetenza() {
		return descEnteCompetenza;
	}
	public void setDescEnteCompetenza(String descEnteCompetenza) {
		this.descEnteCompetenza = descEnteCompetenza;
	}
	public String getDescBreveEnte() {
		return descBreveEnte;
	}
	public void setDescBreveEnte(String descBreveEnte) {
		this.descBreveEnte = descBreveEnte;
	}
	public String getDescBreveTipoEnteCompetenz() {
		return descBreveTipoEnteCompetenz;
	}
	public void setDescBreveTipoEnteCompetenz(String descBreveTipoEnteCompetenz) {
		this.descBreveTipoEnteCompetenz = descBreveTipoEnteCompetenz;
	}
	public Long getProgBandoLineaIntervento() {
		return progBandoLineaIntervento;
	}
	public void setProgBandoLineaIntervento(Long progBandoLineaIntervento) {
		this.progBandoLineaIntervento = progBandoLineaIntervento;
	}
	public Long getIdSettoreEnte() {
		return idSettoreEnte;
	}
	public void setIdSettoreEnte(Long idSettoreEnte) {
		this.idSettoreEnte = idSettoreEnte;
	}
	public String getDescBreveSettore() {
		return descBreveSettore;
	}
	public void setDescBreveSettore(String descBreveSettore) {
		this.descBreveSettore = descBreveSettore;
	}
	public String getDescSettore() {
		return descSettore;
	}
	public void setDescSettore(String descSettore) {
		this.descSettore = descSettore;
	}
	public String getDescEnteSettore() {
		return descEnteSettore;
	}
	public void setDescEnteSettore(String descEnteSettore) {
		this.descEnteSettore = descEnteSettore;
	}
	public Long getIdIndirizzo() {
		return idIndirizzo;
	}
	public void setIdIndirizzo(Long idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
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
	public Long getConservazioneCorrente() {
		return conservazioneCorrente;
	}
	public void setConservazioneCorrente(Long conservazioneCorrente) {
		this.conservazioneCorrente = conservazioneCorrente;
	}
	public Long getConservazioneGenerale() {
		return conservazioneGenerale;
	}
	public void setConservazioneGenerale(Long conservazioneGenerale) {
		this.conservazioneGenerale = conservazioneGenerale;
	}
	@Override
	public String toString() {
		return "EnteProgettoDomandaVO [settore=" + settore + ", progBandoLineaEnteComp=" + progBandoLineaEnteComp
				+ ", idRuoloEnteCompetenza=" + idRuoloEnteCompetenza + ", descBreveRuoloEnte=" + descBreveRuoloEnte
				+ ", idEnteCompetenza=" + idEnteCompetenza + ", descEnteCompetenza=" + descEnteCompetenza
				+ ", descBreveEnte=" + descBreveEnte + ", descBreveTipoEnteCompetenz=" + descBreveTipoEnteCompetenz
				+ ", progBandoLineaIntervento=" + progBandoLineaIntervento + ", idSettoreEnte=" + idSettoreEnte
				+ ", descBreveSettore=" + descBreveSettore + ", descSettore=" + descSettore + ", descEnteSettore="
				+ descEnteSettore + ", idIndirizzo=" + idIndirizzo + ", dtFineValidita=" + dtFineValidita
				+ ", parolaChiave=" + parolaChiave + ", feedbackActa=" + feedbackActa + ", conservazioneCorrente="
				+ conservazioneCorrente + ", conservazioneGenerale=" + conservazioneGenerale + "]";
	}

}
