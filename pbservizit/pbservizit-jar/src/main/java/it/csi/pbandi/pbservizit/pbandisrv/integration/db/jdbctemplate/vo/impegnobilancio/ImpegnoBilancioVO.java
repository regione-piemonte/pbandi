/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;



public class ImpegnoBilancioVO extends GenericVO {
	
	
	private BigDecimal idImpegno;
	private BigDecimal annoImpegno;
	private BigDecimal numeroImpegno;
	private BigDecimal annoEsercizio;
	private String descImpegno;
	private BigDecimal importoInizialeImpegno;
	private BigDecimal importoAttualeImpegno;
	private BigDecimal totaleLiquidatoImpegno;
	private BigDecimal disponibilitaLiquidare;
	private BigDecimal totaleQuietanzatoImpegno;
	private Date dtInserimento;
	private Date dtAggiornamento; 
	private String cupImpegno;
	private String cigImpegno;
	private BigDecimal numeroCapitoloOrigine;
    
	private BigDecimal idStatoImpegno;
	private String descBreveStatoImpegno;
	private String descStatoImpegno;
   
	private BigDecimal idProvvedimento;
	private BigDecimal annoProvvedimento;
	private String numeroProvvedimento;
	private Date dtProvvedimento;
   
	private BigDecimal idTipoProvvedimento;
	private String descBreveTipoProvvedimento;
	private String descTipoProvvedimento;

	private BigDecimal idEnteCompetenzaProvv;
	private String descEnteProvv;
	private String descBreveEnteProvv;
	private BigDecimal idTipoEnteProvv;
	private String descTipoEnteProvv;
	private String descBreveTipoEnteProvv;
  
	private BigDecimal idCapitolo;
	private BigDecimal numeroCapitolo;
	private BigDecimal numeroArticolo;
	private String descCapitolo;
	private BigDecimal idTipoEnteCap;
	private String descTipoEnteCap;
	private String descBreveTipoEnteCap;
   
	private BigDecimal idTipoFondo;
	private String descBreveTipoFondo;
	private String descTipoFondo ;
   
	private BigDecimal idEnteCompetenzaCap;
	private String descEnteCap;
	private String descBreveEnteCap;
    
	private BigDecimal annoPerente;
	private BigDecimal numeroPerente;
	
	private String codiceFiscale;
	private String ragsoc;
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getRagsoc() {
		return ragsoc;
	}
	public void setRagsoc(String ragsoc) {
		this.ragsoc = ragsoc;
	}
	public BigDecimal getIdImpegno() {
		return idImpegno;
	}
	public void setIdImpegno(BigDecimal idImpegno) {
		this.idImpegno = idImpegno;
	}
	public BigDecimal getAnnoImpegno() {
		return annoImpegno;
	}
	public void setAnnoImpegno(BigDecimal annoImpegno) {
		this.annoImpegno = annoImpegno;
	}
	public BigDecimal getNumeroImpegno() {
		return numeroImpegno;
	}
	public void setNumeroImpegno(BigDecimal numeroImpegno) {
		this.numeroImpegno = numeroImpegno;
	}
	public BigDecimal getAnnoEsercizio() {
		return annoEsercizio;
	}
	public void setAnnoEsercizio(BigDecimal annoEsercizio) {
		this.annoEsercizio = annoEsercizio;
	}
	public BigDecimal getImportoInizialeImpegno() {
		return importoInizialeImpegno;
	}
	public void setImportoInizialeImpegno(BigDecimal importoInizialeImpegno) {
		this.importoInizialeImpegno = importoInizialeImpegno;
	}
	public BigDecimal getImportoAttualeImpegno() {
		return importoAttualeImpegno;
	}
	public void setImportoAttualeImpegno(BigDecimal importoAttualeImpegno) {
		this.importoAttualeImpegno = importoAttualeImpegno;
	}
	public BigDecimal getTotaleLiquidatoImpegno() {
		return totaleLiquidatoImpegno;
	}
	public void setTotaleLiquidatoImpegno(BigDecimal totaleLiquidatoImpegno) {
		this.totaleLiquidatoImpegno = totaleLiquidatoImpegno;
	}
	public BigDecimal getDisponibilitaLiquidare() {
		return disponibilitaLiquidare;
	}
	public void setDisponibilitaLiquidare(BigDecimal disponibilitaLiquidare) {
		this.disponibilitaLiquidare = disponibilitaLiquidare;
	}
	public BigDecimal getTotaleQuietanzatoImpegno() {
		return totaleQuietanzatoImpegno;
	}
	public void setTotaleQuietanzatoImpegno(BigDecimal totaleQuietanzatoImpegno) {
		this.totaleQuietanzatoImpegno = totaleQuietanzatoImpegno;
	}
	public Date getDtInserimento() {
		return dtInserimento;
	}
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	public String getCupImpegno() {
		return cupImpegno;
	}
	public void setCupImpegno(String cupImpegno) {
		this.cupImpegno = cupImpegno;
	}
	public String getCigImpegno() {
		return cigImpegno;
	}
	public void setCigImpegno(String cigImpegno) {
		this.cigImpegno = cigImpegno;
	}
	
	public BigDecimal getIdStatoImpegno() {
		return idStatoImpegno;
	}
	public void setIdStatoImpegno(BigDecimal idStatoImpegno) {
		this.idStatoImpegno = idStatoImpegno;
	}
	public String getDescBreveStatoImpegno() {
		return descBreveStatoImpegno;
	}
	public void setDescBreveStatoImpegno(String descBreveStatoImpegno) {
		this.descBreveStatoImpegno = descBreveStatoImpegno;
	}
	public String getDescStatoImpegno() {
		return descStatoImpegno;
	}
	public void setDescStatoImpegno(String descStatoImpegno) {
		this.descStatoImpegno = descStatoImpegno;
	}
	public BigDecimal getIdProvvedimento() {
		return idProvvedimento;
	}
	public void setIdProvvedimento(BigDecimal idProvvedimento) {
		this.idProvvedimento = idProvvedimento;
	}
	public BigDecimal getAnnoProvvedimento() {
		return annoProvvedimento;
	}
	public void setAnnoProvvedimento(BigDecimal annoProvvedimento) {
		this.annoProvvedimento = annoProvvedimento;
	}
	public String getNumeroProvvedimento() {
		return numeroProvvedimento;
	}
	public void setNumeroProvvedimento(String numeroProvvedimento) {
		this.numeroProvvedimento = numeroProvvedimento;
	}
	public BigDecimal getIdTipoProvvedimento() {
		return idTipoProvvedimento;
	}
	public void setIdTipoProvvedimento(BigDecimal idTipoProvvedimento) {
		this.idTipoProvvedimento = idTipoProvvedimento;
	}
	public String getDescBreveTipoProvvedimento() {
		return descBreveTipoProvvedimento;
	}
	public void setDescBreveTipoProvvedimento(String descBreveTipoProvvedimento) {
		this.descBreveTipoProvvedimento = descBreveTipoProvvedimento;
	}
	public String getDescTipoProvvedimento() {
		return descTipoProvvedimento;
	}
	public void setDescTipoProvvedimento(String descTipoProvvedimento) {
		this.descTipoProvvedimento = descTipoProvvedimento;
	}
	public BigDecimal getIdEnteCompetenzaProvv() {
		return idEnteCompetenzaProvv;
	}
	public void setIdEnteCompetenzaProvv(BigDecimal idEnteCompetenzaProvv) {
		this.idEnteCompetenzaProvv = idEnteCompetenzaProvv;
	}
	public String getDescEnteProvv() {
		return descEnteProvv;
	}
	public void setDescEnteProvv(String descEnteProvv) {
		this.descEnteProvv = descEnteProvv;
	}
	public String getDescBreveEnteProvv() {
		return descBreveEnteProvv;
	}
	public void setDescBreveEnteProvv(String descBreveEnteProvv) {
		this.descBreveEnteProvv = descBreveEnteProvv;
	}
	public BigDecimal getIdCapitolo() {
		return idCapitolo;
	}
	public void setIdCapitolo(BigDecimal idCapitolo) {
		this.idCapitolo = idCapitolo;
	}
	
	public String getDescCapitolo() {
		return descCapitolo;
	}
	public void setDescCapitolo(String descCapitolo) {
		this.descCapitolo = descCapitolo;
	}
	public BigDecimal getIdTipoFondo() {
		return idTipoFondo;
	}
	public void setIdTipoFondo(BigDecimal idTipoFondo) {
		this.idTipoFondo = idTipoFondo;
	}
	public String getDescBreveTipoFondo() {
		return descBreveTipoFondo;
	}
	public void setDescBreveTipoFondo(String descBreveTipoFondo) {
		this.descBreveTipoFondo = descBreveTipoFondo;
	}
	public String getDescTipoFondo() {
		return descTipoFondo;
	}
	public void setDescTipoFondo(String descTipoFondo) {
		this.descTipoFondo = descTipoFondo;
	}
	public BigDecimal getIdEnteCompetenzaCap() {
		return idEnteCompetenzaCap;
	}
	public void setIdEnteCompetenzaCap(BigDecimal idEnteCompetenzaCap) {
		this.idEnteCompetenzaCap = idEnteCompetenzaCap;
	}
	public String getDescEnteCap() {
		return descEnteCap;
	}
	public void setDescEnteCap(String descEnteCap) {
		this.descEnteCap = descEnteCap;
	}
	public String getDescBreveEnteCap() {
		return descBreveEnteCap;
	}
	public void setDescBreveEnteCap(String descBreveEnteCap) {
		this.descBreveEnteCap = descBreveEnteCap;
	}
	public BigDecimal getAnnoPerente() {
		return annoPerente;
	}
	public void setAnnoPerente(BigDecimal annoPerente) {
		this.annoPerente = annoPerente;
	}
	public BigDecimal getNumeroCapitoloOrigine() {
		return numeroCapitoloOrigine;
	}
	public void setNumeroCapitoloOrigine(BigDecimal numeroCapitoloOrigine) {
		this.numeroCapitoloOrigine = numeroCapitoloOrigine;
	}
	public BigDecimal getNumeroCapitolo() {
		return numeroCapitolo;
	}
	public void setNumeroCapitolo(BigDecimal numeroCapitolo) {
		this.numeroCapitolo = numeroCapitolo;
	}
	public BigDecimal getNumeroArticolo() {
		return numeroArticolo;
	}
	public void setNumeroArticolo(BigDecimal numeroArticolo) {
		this.numeroArticolo = numeroArticolo;
	}
	public BigDecimal getNumeroPerente() {
		return numeroPerente;
	}
	public void setNumeroPerente(BigDecimal numeroPerente) {
		this.numeroPerente = numeroPerente;
	}
	public void setIdTipoEnteProvv(BigDecimal idTipoEnteProvv) {
		this.idTipoEnteProvv = idTipoEnteProvv;
	}
	public BigDecimal getIdTipoEnteProvv() {
		return idTipoEnteProvv;
	}
	public void setDescTipoEnteProvv(String descTipoEnteProvv) {
		this.descTipoEnteProvv = descTipoEnteProvv;
	}
	public String getDescTipoEnteProvv() {
		return descTipoEnteProvv;
	}
	public void setDescBreveTipoEnteProvv(String descBreveTipoEnteProvv) {
		this.descBreveTipoEnteProvv = descBreveTipoEnteProvv;
	}
	public String getDescBreveTipoEnteProvv() {
		return descBreveTipoEnteProvv;
	}

	public void setDescImpegno(String descImpegno) {
		this.descImpegno = descImpegno;
	}
	public String getDescImpegno() {
		return descImpegno;
	}
	public void setDtProvvedimento(Date dtProvvedimento) {
		this.dtProvvedimento = dtProvvedimento;
	}
	public Date getDtProvvedimento() {
		return dtProvvedimento;
	}
	public void setIdTipoEnteCap(BigDecimal idTipoEnteCap) {
		this.idTipoEnteCap = idTipoEnteCap;
	}
	public BigDecimal getIdTipoEnteCap() {
		return idTipoEnteCap;
	}
	public void setDescTipoEnteCap(String descTipoEnteCap) {
		this.descTipoEnteCap = descTipoEnteCap;
	}
	public String getDescTipoEnteCap() {
		return descTipoEnteCap;
	}
	public void setDescBreveTipoEnteCap(String descBreveTipoEnteCap) {
		this.descBreveTipoEnteCap = descBreveTipoEnteCap;
	}
	public String getDescBreveTipoEnteCap() {
		return descBreveTipoEnteCap;
	}
	@Override
	public String toString() {
		return "ImpegnoBilancioVO [idImpegno=" + idImpegno + ", annoImpegno=" + annoImpegno + ", numeroImpegno="
				+ numeroImpegno + ", annoEsercizio=" + annoEsercizio + ", descImpegno=" + descImpegno
				+ ", importoInizialeImpegno=" + importoInizialeImpegno + ", importoAttualeImpegno="
				+ importoAttualeImpegno + ", totaleLiquidatoImpegno=" + totaleLiquidatoImpegno
				+ ", disponibilitaLiquidare=" + disponibilitaLiquidare + ", totaleQuietanzatoImpegno="
				+ totaleQuietanzatoImpegno + ", dtInserimento=" + dtInserimento + ", dtAggiornamento=" + dtAggiornamento
				+ ", cupImpegno=" + cupImpegno + ", cigImpegno=" + cigImpegno + ", numeroCapitoloOrigine="
				+ numeroCapitoloOrigine + ", idStatoImpegno=" + idStatoImpegno + ", descBreveStatoImpegno="
				+ descBreveStatoImpegno + ", descStatoImpegno=" + descStatoImpegno + ", idProvvedimento="
				+ idProvvedimento + ", annoProvvedimento=" + annoProvvedimento + ", numeroProvvedimento="
				+ numeroProvvedimento + ", dtProvvedimento=" + dtProvvedimento + ", idTipoProvvedimento="
				+ idTipoProvvedimento + ", descBreveTipoProvvedimento=" + descBreveTipoProvvedimento
				+ ", descTipoProvvedimento=" + descTipoProvvedimento + ", idEnteCompetenzaProvv="
				+ idEnteCompetenzaProvv + ", descEnteProvv=" + descEnteProvv + ", descBreveEnteProvv="
				+ descBreveEnteProvv + ", idTipoEnteProvv=" + idTipoEnteProvv + ", descTipoEnteProvv="
				+ descTipoEnteProvv + ", descBreveTipoEnteProvv=" + descBreveTipoEnteProvv + ", idCapitolo="
				+ idCapitolo + ", numeroCapitolo=" + numeroCapitolo + ", numeroArticolo=" + numeroArticolo
				+ ", descCapitolo=" + descCapitolo + ", idTipoEnteCap=" + idTipoEnteCap + ", descTipoEnteCap="
				+ descTipoEnteCap + ", descBreveTipoEnteCap=" + descBreveTipoEnteCap + ", idTipoFondo=" + idTipoFondo
				+ ", descBreveTipoFondo=" + descBreveTipoFondo + ", descTipoFondo=" + descTipoFondo
				+ ", idEnteCompetenzaCap=" + idEnteCompetenzaCap + ", descEnteCap=" + descEnteCap
				+ ", descBreveEnteCap=" + descBreveEnteCap + ", annoPerente=" + annoPerente + ", numeroPerente="
				+ numeroPerente + ", codiceFiscale=" + codiceFiscale + ", ragsoc=" + ragsoc + "]";
	}
	
}
