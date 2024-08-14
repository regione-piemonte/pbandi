/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class BandoPropostaCertificazUnionPeriodoVO {
	private BigDecimal idPropostaCertificaz;
	private String descProposta;
	private Date dtOraCreazione;
	private BigDecimal idStatoPropostaCertif;
	private BigDecimal idPeriodo;
	private BigDecimal idTipoPeriodo;
	private String descPeriodo;
	private String descPeriodoVisualizzata;
	private Date dtInizioValidita;
	private Date dtFineValidita;
	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}
	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}
	public String getDescProposta() {
		return descProposta;
	}
	public void setDescProposta(String descProposta) {
		this.descProposta = descProposta;
	}
	public Date getDtOraCreazione() {
		return dtOraCreazione;
	}
	public void setDtOraCreazione(Date dtOraCreazione) {
		this.dtOraCreazione = dtOraCreazione;
	}
	public BigDecimal getIdStatoPropostaCertif() {
		return idStatoPropostaCertif;
	}
	public void setIdStatoPropostaCertif(BigDecimal idStatoPropostaCertif) {
		this.idStatoPropostaCertif = idStatoPropostaCertif;
	}
	public BigDecimal getIdPeriodo() {
		return idPeriodo;
	}
	public void setIdPeriodo(BigDecimal idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	public BigDecimal getIdTipoPeriodo() {
		return idTipoPeriodo;
	}
	public void setIdTipoPeriodo(BigDecimal idTipoPeriodo) {
		this.idTipoPeriodo = idTipoPeriodo;
	}
	public String getDescPeriodo() {
		return descPeriodo;
	}
	public void setDescPeriodo(String descPeriodo) {
		this.descPeriodo = descPeriodo;
	}
	public String getDescPeriodoVisualizzata() {
		return descPeriodoVisualizzata;
	}
	public void setDescPeriodoVisualizzata(String descPeriodoVisualizzata) {
		this.descPeriodoVisualizzata = descPeriodoVisualizzata;
	}
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	
}
