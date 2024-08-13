/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class BandoLineaEnteCompAttiviVO extends GenericVO {
	
	private BigDecimal progrBandoLineaEnteComp;
	private BigDecimal idRuoloEnteCompetenza;
	private String descBreveRuoloEnte;
	private BigDecimal idEnteCompetenza;
	private String descBreveEnte;
    private String descBreveTipoEnteCompetenz;
    private BigDecimal progrBandoLineaIntervento;
    private BigDecimal idSettoreEnte;
    private String descBreveSettore;
    private BigDecimal idIndirizzo;
    private Date dtFineValidita;
    private String descEnteSettore;
    private String parolaChiave;
    private String feedbackActa;
    
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
	public String getDescEnteSettore() {
		return descEnteSettore;
	}
	public void setDescEnteSettore(String descEnteSettore) {
		this.descEnteSettore = descEnteSettore;
	}
	public BigDecimal getProgrBandoLineaEnteComp() {
		return progrBandoLineaEnteComp;
	}
	public void setProgrBandoLineaEnteComp(BigDecimal progrBandoLineaEnteComp) {
		this.progrBandoLineaEnteComp = progrBandoLineaEnteComp;
	}
	public BigDecimal getIdRuoloEnteCompetenza() {
		return idRuoloEnteCompetenza;
	}
	public void setIdRuoloEnteCompetenza(BigDecimal idRuoloEnteCompetenza) {
		this.idRuoloEnteCompetenza = idRuoloEnteCompetenza;
	}
	public String getDescBreveRuoloEnte() {
		return descBreveRuoloEnte;
	}
	public void setDescBreveRuoloEnte(String descBreveRuoloEnte) {
		this.descBreveRuoloEnte = descBreveRuoloEnte;
	}
	public BigDecimal getIdEnteCompetenza() {
		return idEnteCompetenza;
	}
	public void setIdEnteCompetenza(BigDecimal idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
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
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public BigDecimal getIdSettoreEnte() {
		return idSettoreEnte;
	}
	public void setIdSettoreEnte(BigDecimal idSettoreEnte) {
		this.idSettoreEnte = idSettoreEnte;
	}
	public String getDescBreveSettore() {
		return descBreveSettore;
	}
	public void setDescBreveSettore(String descBreveSettore) {
		this.descBreveSettore = descBreveSettore;
	}
	public BigDecimal getIdIndirizzo() {
		return idIndirizzo;
	}
	public void setIdIndirizzo(BigDecimal idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

}
