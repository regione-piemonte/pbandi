/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiTPraticaIstruttoriaVO extends GenericVO {

  	
  	private BigDecimal idPraticaIstruttoria;
  	
  	private Date dtAperturaPratica;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idDomanda;
  	
  	private Date dtChiusuraPratica;
  	
  	private BigDecimal idUtenteIns;
  	
  	private String flagEsitoIstruttoria;
  	
	public PbandiTPraticaIstruttoriaVO() {}
  	
	public PbandiTPraticaIstruttoriaVO (BigDecimal idPraticaIstruttoria) {
	
		this. idPraticaIstruttoria =  idPraticaIstruttoria;
	}
  	
	public PbandiTPraticaIstruttoriaVO (BigDecimal idPraticaIstruttoria, Date dtAperturaPratica, BigDecimal idUtenteAgg, BigDecimal idDomanda, Date dtChiusuraPratica, BigDecimal idUtenteIns, String flagEsitoIstruttoria) {
	
		this. idPraticaIstruttoria =  idPraticaIstruttoria;
		this. dtAperturaPratica =  dtAperturaPratica;
		this. idUtenteAgg =  idUtenteAgg;
		this. idDomanda =  idDomanda;
		this. dtChiusuraPratica =  dtChiusuraPratica;
		this. idUtenteIns =  idUtenteIns;
		this. flagEsitoIstruttoria =  flagEsitoIstruttoria;
	}
  	
  	
	public BigDecimal getIdPraticaIstruttoria() {
		return idPraticaIstruttoria;
	}
	
	public void setIdPraticaIstruttoria(BigDecimal idPraticaIstruttoria) {
		this.idPraticaIstruttoria = idPraticaIstruttoria;
	}
	
	public Date getDtAperturaPratica() {
		return dtAperturaPratica;
	}
	
	public void setDtAperturaPratica(Date dtAperturaPratica) {
		this.dtAperturaPratica = dtAperturaPratica;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdDomanda() {
		return idDomanda;
	}
	
	public void setIdDomanda(BigDecimal idDomanda) {
		this.idDomanda = idDomanda;
	}
	
	public Date getDtChiusuraPratica() {
		return dtChiusuraPratica;
	}
	
	public void setDtChiusuraPratica(Date dtChiusuraPratica) {
		this.dtChiusuraPratica = dtChiusuraPratica;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getFlagEsitoIstruttoria() {
		return flagEsitoIstruttoria;
	}
	
	public void setFlagEsitoIstruttoria(String flagEsitoIstruttoria) {
		this.flagEsitoIstruttoria = flagEsitoIstruttoria;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idDomanda != null && idUtenteIns != null && flagEsitoIstruttoria != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idPraticaIstruttoria != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idPraticaIstruttoria);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPraticaIstruttoria: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAperturaPratica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAperturaPratica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtChiusuraPratica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtChiusuraPratica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagEsitoIstruttoria);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagEsitoIstruttoria: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idPraticaIstruttoria");
		
	    return pk;
	}
	
	
}
