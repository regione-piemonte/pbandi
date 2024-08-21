/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiTIndirizzoVO extends GenericVO {

  	
  	private BigDecimal idViaL2;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idIndirizzo;
  	
  	private String bis;
  	
  	private BigDecimal idGeoRiferimento;
  	
  	private String cap;
  	
  	private BigDecimal idProvincia;
  	
  	private BigDecimal idFonteIndirizzo;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idComune;
  	
  	private String civico;
  	
  	private BigDecimal idNazione;
  	
  	private BigDecimal idRegione;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idComuneEstero;
  	
  	private String descIndirizzo;
  	
	public PbandiTIndirizzoVO() {}
  	
	public PbandiTIndirizzoVO (BigDecimal idIndirizzo) {
	
		this. idIndirizzo =  idIndirizzo;
	}
  	
	public PbandiTIndirizzoVO (BigDecimal idViaL2, BigDecimal idUtenteAgg, Date dtInizioValidita, BigDecimal idIndirizzo, String bis, BigDecimal idGeoRiferimento, String cap, BigDecimal idProvincia, BigDecimal idFonteIndirizzo, Date dtFineValidita, BigDecimal idComune, String civico, BigDecimal idNazione, BigDecimal idRegione, BigDecimal idUtenteIns, BigDecimal idComuneEstero, String descIndirizzo) {
	
		this. idViaL2 =  idViaL2;
		this. idUtenteAgg =  idUtenteAgg;
		this. dtInizioValidita =  dtInizioValidita;
		this. idIndirizzo =  idIndirizzo;
		this. bis =  bis;
		this. idGeoRiferimento =  idGeoRiferimento;
		this. cap =  cap;
		this. idProvincia =  idProvincia;
		this. idFonteIndirizzo =  idFonteIndirizzo;
		this. dtFineValidita =  dtFineValidita;
		this. idComune =  idComune;
		this. civico =  civico;
		this. idNazione =  idNazione;
		this. idRegione =  idRegione;
		this. idUtenteIns =  idUtenteIns;
		this. idComuneEstero =  idComuneEstero;
		this. descIndirizzo =  descIndirizzo;
	}
  	
  	
	public BigDecimal getIdViaL2() {
		return idViaL2;
	}
	
	public void setIdViaL2(BigDecimal idViaL2) {
		this.idViaL2 = idViaL2;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdIndirizzo() {
		return idIndirizzo;
	}
	
	public void setIdIndirizzo(BigDecimal idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	
	public String getBis() {
		return bis;
	}
	
	public void setBis(String bis) {
		this.bis = bis;
	}
	
	public BigDecimal getIdGeoRiferimento() {
		return idGeoRiferimento;
	}
	
	public void setIdGeoRiferimento(BigDecimal idGeoRiferimento) {
		this.idGeoRiferimento = idGeoRiferimento;
	}
	
	public String getCap() {
		return cap;
	}
	
	public void setCap(String cap) {
		this.cap = cap;
	}
	
	public BigDecimal getIdProvincia() {
		return idProvincia;
	}
	
	public void setIdProvincia(BigDecimal idProvincia) {
		this.idProvincia = idProvincia;
	}
	
	public BigDecimal getIdFonteIndirizzo() {
		return idFonteIndirizzo;
	}
	
	public void setIdFonteIndirizzo(BigDecimal idFonteIndirizzo) {
		this.idFonteIndirizzo = idFonteIndirizzo;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdComune() {
		return idComune;
	}
	
	public void setIdComune(BigDecimal idComune) {
		this.idComune = idComune;
	}
	
	public String getCivico() {
		return civico;
	}
	
	public void setCivico(String civico) {
		this.civico = civico;
	}
	
	public BigDecimal getIdNazione() {
		return idNazione;
	}
	
	public void setIdNazione(BigDecimal idNazione) {
		this.idNazione = idNazione;
	}
	
	public BigDecimal getIdRegione() {
		return idRegione;
	}
	
	public void setIdRegione(BigDecimal idRegione) {
		this.idRegione = idRegione;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdComuneEstero() {
		return idComuneEstero;
	}
	
	public void setIdComuneEstero(BigDecimal idComuneEstero) {
		this.idComuneEstero = idComuneEstero;
	}
	
	public String getDescIndirizzo() {
		return descIndirizzo;
	}
	
	public void setDescIndirizzo(String descIndirizzo) {
		this.descIndirizzo = descIndirizzo;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idIndirizzo != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idViaL2);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idViaL2: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndirizzo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndirizzo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( bis);
	    if (!DataFilter.isEmpty(temp)) sb.append(" bis: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idGeoRiferimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idGeoRiferimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cap);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cap: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProvincia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProvincia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFonteIndirizzo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFonteIndirizzo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idComune);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idComune: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( civico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" civico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRegione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRegione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idComuneEstero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idComuneEstero: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descIndirizzo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descIndirizzo: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idIndirizzo");
		
	    return pk;
	}
	
	
}
