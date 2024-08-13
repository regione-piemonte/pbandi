/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTImpegnoVO extends GenericVO {

  	
  	private Date dtInserimentoBilancio;
  	
  	private BigDecimal annoEsercizio;
  	
  	private BigDecimal idEnteCompetenzaDelegato;
  	
  	private BigDecimal numeroCapitoloOrigine;
  	
  	private BigDecimal annoPerente;
  	
  	private BigDecimal disponibilitaLiquidare;
  	
  	private String cupImpegno;
  	
  	private String descTipologiaBeneficiario;
  	
  	private BigDecimal idUtenteIns;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal idImpegno;
  	
  	private BigDecimal idProvvedimento;
  	
  	private BigDecimal importoAttualeImpegno;
  	
  	private Date dtAggiornamentoBilancio;
  	
  	private BigDecimal idBeneficiarioBilancio;
  	
  	private BigDecimal numeroImpegno;
  	
  	private String cigImpegno;
  	
  	private BigDecimal idStatoImpegno;
  	
  	private BigDecimal idCapitolo;
  	
  	private BigDecimal totaleLiquidatoImpegno;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal annoImpegno;
  	
  	private BigDecimal importoInizialeImpegno;
  	
  	private String tipologiaBeneficiario;
  	
  	private BigDecimal numeroPerente;
  	
  	private BigDecimal totaleQuietanzatoImpegno;
  	
  	private String descImpegno;
  	
  	private Date dtAggiornamento;
  	
	public PbandiTImpegnoVO() {}
  	
	public PbandiTImpegnoVO (BigDecimal idImpegno) {
	
		this. idImpegno =  idImpegno;
	}
  	
	public PbandiTImpegnoVO (Date dtInserimentoBilancio, BigDecimal annoEsercizio, BigDecimal idEnteCompetenzaDelegato, BigDecimal numeroCapitoloOrigine, BigDecimal annoPerente, BigDecimal disponibilitaLiquidare, String cupImpegno, String descTipologiaBeneficiario, BigDecimal idUtenteIns, Date dtInserimento, BigDecimal idImpegno, BigDecimal idProvvedimento, BigDecimal importoAttualeImpegno, Date dtAggiornamentoBilancio, BigDecimal idBeneficiarioBilancio, BigDecimal numeroImpegno, String cigImpegno, BigDecimal idStatoImpegno, BigDecimal idCapitolo, BigDecimal totaleLiquidatoImpegno, BigDecimal idUtenteAgg, BigDecimal annoImpegno, BigDecimal importoInizialeImpegno, String tipologiaBeneficiario, BigDecimal numeroPerente, BigDecimal totaleQuietanzatoImpegno, String descImpegno, Date dtAggiornamento) {
	
		this. dtInserimentoBilancio =  dtInserimentoBilancio;
		this. annoEsercizio =  annoEsercizio;
		this. idEnteCompetenzaDelegato =  idEnteCompetenzaDelegato;
		this. numeroCapitoloOrigine =  numeroCapitoloOrigine;
		this. annoPerente =  annoPerente;
		this. disponibilitaLiquidare =  disponibilitaLiquidare;
		this. cupImpegno =  cupImpegno;
		this. descTipologiaBeneficiario =  descTipologiaBeneficiario;
		this. idUtenteIns =  idUtenteIns;
		this. dtInserimento =  dtInserimento;
		this. idImpegno =  idImpegno;
		this. idProvvedimento =  idProvvedimento;
		this. importoAttualeImpegno =  importoAttualeImpegno;
		this. dtAggiornamentoBilancio =  dtAggiornamentoBilancio;
		this. idBeneficiarioBilancio =  idBeneficiarioBilancio;
		this. numeroImpegno =  numeroImpegno;
		this. cigImpegno =  cigImpegno;
		this. idStatoImpegno =  idStatoImpegno;
		this. idCapitolo =  idCapitolo;
		this. totaleLiquidatoImpegno =  totaleLiquidatoImpegno;
		this. idUtenteAgg =  idUtenteAgg;
		this. annoImpegno =  annoImpegno;
		this. importoInizialeImpegno =  importoInizialeImpegno;
		this. tipologiaBeneficiario =  tipologiaBeneficiario;
		this. numeroPerente =  numeroPerente;
		this. totaleQuietanzatoImpegno =  totaleQuietanzatoImpegno;
		this. descImpegno =  descImpegno;
		this. dtAggiornamento =  dtAggiornamento;
	}
  	
  	
	public Date getDtInserimentoBilancio() {
		return dtInserimentoBilancio;
	}
	
	public void setDtInserimentoBilancio(Date dtInserimentoBilancio) {
		this.dtInserimentoBilancio = dtInserimentoBilancio;
	}
	
	public BigDecimal getAnnoEsercizio() {
		return annoEsercizio;
	}
	
	public void setAnnoEsercizio(BigDecimal annoEsercizio) {
		this.annoEsercizio = annoEsercizio;
	}
	
	public BigDecimal getIdEnteCompetenzaDelegato() {
		return idEnteCompetenzaDelegato;
	}
	
	public void setIdEnteCompetenzaDelegato(BigDecimal idEnteCompetenzaDelegato) {
		this.idEnteCompetenzaDelegato = idEnteCompetenzaDelegato;
	}
	
	public BigDecimal getNumeroCapitoloOrigine() {
		return numeroCapitoloOrigine;
	}
	
	public void setNumeroCapitoloOrigine(BigDecimal numeroCapitoloOrigine) {
		this.numeroCapitoloOrigine = numeroCapitoloOrigine;
	}
	
	public BigDecimal getAnnoPerente() {
		return annoPerente;
	}
	
	public void setAnnoPerente(BigDecimal annoPerente) {
		this.annoPerente = annoPerente;
	}
	
	public BigDecimal getDisponibilitaLiquidare() {
		return disponibilitaLiquidare;
	}
	
	public void setDisponibilitaLiquidare(BigDecimal disponibilitaLiquidare) {
		this.disponibilitaLiquidare = disponibilitaLiquidare;
	}
	
	public String getCupImpegno() {
		return cupImpegno;
	}
	
	public void setCupImpegno(String cupImpegno) {
		this.cupImpegno = cupImpegno;
	}
	
	public String getDescTipologiaBeneficiario() {
		return descTipologiaBeneficiario;
	}
	
	public void setDescTipologiaBeneficiario(String descTipologiaBeneficiario) {
		this.descTipologiaBeneficiario = descTipologiaBeneficiario;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public BigDecimal getIdImpegno() {
		return idImpegno;
	}
	
	public void setIdImpegno(BigDecimal idImpegno) {
		this.idImpegno = idImpegno;
	}
	
	public BigDecimal getIdProvvedimento() {
		return idProvvedimento;
	}
	
	public void setIdProvvedimento(BigDecimal idProvvedimento) {
		this.idProvvedimento = idProvvedimento;
	}
	
	public BigDecimal getImportoAttualeImpegno() {
		return importoAttualeImpegno;
	}
	
	public void setImportoAttualeImpegno(BigDecimal importoAttualeImpegno) {
		this.importoAttualeImpegno = importoAttualeImpegno;
	}
	
	public Date getDtAggiornamentoBilancio() {
		return dtAggiornamentoBilancio;
	}
	
	public void setDtAggiornamentoBilancio(Date dtAggiornamentoBilancio) {
		this.dtAggiornamentoBilancio = dtAggiornamentoBilancio;
	}
	
	public BigDecimal getIdBeneficiarioBilancio() {
		return idBeneficiarioBilancio;
	}
	
	public void setIdBeneficiarioBilancio(BigDecimal idBeneficiarioBilancio) {
		this.idBeneficiarioBilancio = idBeneficiarioBilancio;
	}
	
	public BigDecimal getNumeroImpegno() {
		return numeroImpegno;
	}
	
	public void setNumeroImpegno(BigDecimal numeroImpegno) {
		this.numeroImpegno = numeroImpegno;
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
	
	public BigDecimal getIdCapitolo() {
		return idCapitolo;
	}
	
	public void setIdCapitolo(BigDecimal idCapitolo) {
		this.idCapitolo = idCapitolo;
	}
	
	public BigDecimal getTotaleLiquidatoImpegno() {
		return totaleLiquidatoImpegno;
	}
	
	public void setTotaleLiquidatoImpegno(BigDecimal totaleLiquidatoImpegno) {
		this.totaleLiquidatoImpegno = totaleLiquidatoImpegno;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getAnnoImpegno() {
		return annoImpegno;
	}
	
	public void setAnnoImpegno(BigDecimal annoImpegno) {
		this.annoImpegno = annoImpegno;
	}
	
	public BigDecimal getImportoInizialeImpegno() {
		return importoInizialeImpegno;
	}
	
	public void setImportoInizialeImpegno(BigDecimal importoInizialeImpegno) {
		this.importoInizialeImpegno = importoInizialeImpegno;
	}
	
	public String getTipologiaBeneficiario() {
		return tipologiaBeneficiario;
	}
	
	public void setTipologiaBeneficiario(String tipologiaBeneficiario) {
		this.tipologiaBeneficiario = tipologiaBeneficiario;
	}
	
	public BigDecimal getNumeroPerente() {
		return numeroPerente;
	}
	
	public void setNumeroPerente(BigDecimal numeroPerente) {
		this.numeroPerente = numeroPerente;
	}
	
	public BigDecimal getTotaleQuietanzatoImpegno() {
		return totaleQuietanzatoImpegno;
	}
	
	public void setTotaleQuietanzatoImpegno(BigDecimal totaleQuietanzatoImpegno) {
		this.totaleQuietanzatoImpegno = totaleQuietanzatoImpegno;
	}
	
	public String getDescImpegno() {
		return descImpegno;
	}
	
	public void setDescImpegno(String descImpegno) {
		this.descImpegno = descImpegno;
	}
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInserimentoBilancio != null && annoEsercizio != null && idUtenteIns != null && dtInserimento != null && numeroImpegno != null && idStatoImpegno != null && idCapitolo != null && annoImpegno != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idImpegno != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimentoBilancio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimentoBilancio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( annoEsercizio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annoEsercizio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEnteCompetenzaDelegato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEnteCompetenzaDelegato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroCapitoloOrigine);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroCapitoloOrigine: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( annoPerente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annoPerente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( disponibilitaLiquidare);
	    if (!DataFilter.isEmpty(temp)) sb.append(" disponibilitaLiquidare: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cupImpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cupImpegno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipologiaBeneficiario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipologiaBeneficiario: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idImpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idImpegno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProvvedimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProvvedimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoAttualeImpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoAttualeImpegno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamentoBilancio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamentoBilancio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idBeneficiarioBilancio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBeneficiarioBilancio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroImpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroImpegno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cigImpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cigImpegno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoImpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoImpegno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCapitolo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCapitolo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( totaleLiquidatoImpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" totaleLiquidatoImpegno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( annoImpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annoImpegno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoInizialeImpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoInizialeImpegno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tipologiaBeneficiario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tipologiaBeneficiario: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroPerente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroPerente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( totaleQuietanzatoImpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" totaleQuietanzatoImpegno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descImpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descImpegno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idImpegno");
		
	    return pk;
	}
	
	
}
