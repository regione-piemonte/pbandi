/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;


public class InfoCreaAttoVO extends GenericVO {
	private BigDecimal idAttoLiquidazione;
	private BigDecimal importoAtto;
	private String descSettore;
	private String descCausale;
	private String noteAtto;
	private String descEnte;
	
	public BigDecimal getIdAttoLiquidazione() {
		return idAttoLiquidazione;
	}
	public void setIdAttoLiquidazione(BigDecimal idAttoLiquidazione) {
		this.idAttoLiquidazione = idAttoLiquidazione;
	}
	public BigDecimal getImportoAtto() {
		return importoAtto;
	}
	public void setImportoAtto(BigDecimal importoAtto) {
		this.importoAtto = importoAtto;
	}
	public String getDescSettore() {
		return descSettore;
	}
	public void setDescSettore(String descSettore) {
		this.descSettore = descSettore;
	}
	public String getDescCausale() {
		return descCausale;
	}
	public void setDescCausale(String descCausale) {
		this.descCausale = descCausale;
	}
	public String getNoteAtto() {
		return noteAtto;
	}
	public void setNoteAtto(String noteAtto) {
		this.noteAtto = noteAtto;
	}
	public String getDescEnte() {
		return descEnte;
	}
	public void setDescEnte(String descEnte) {
		this.descEnte = descEnte;
	}


}
