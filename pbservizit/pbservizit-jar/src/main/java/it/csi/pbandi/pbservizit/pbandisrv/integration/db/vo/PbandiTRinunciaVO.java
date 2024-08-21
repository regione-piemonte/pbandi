/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiTRinunciaVO extends GenericVO {

  	
  	private String motivoRinuncia;
  	
  	private BigDecimal idRinuncia;
  	
  	private BigDecimal importoDaRestituire;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal giorniRinuncia;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idProgetto;
  	
  	private Date dtRinuncia;
  	
	public PbandiTRinunciaVO() {}
  	
	public PbandiTRinunciaVO (BigDecimal idRinuncia) {
	
		this. idRinuncia =  idRinuncia;
	}
  	
	public PbandiTRinunciaVO (String motivoRinuncia, BigDecimal idRinuncia, BigDecimal importoDaRestituire, BigDecimal idUtenteAgg, BigDecimal giorniRinuncia, BigDecimal idUtenteIns, BigDecimal idProgetto, Date dtRinuncia) {
	
		this. motivoRinuncia =  motivoRinuncia;
		this. idRinuncia =  idRinuncia;
		this. importoDaRestituire =  importoDaRestituire;
		this. idUtenteAgg =  idUtenteAgg;
		this. giorniRinuncia =  giorniRinuncia;
		this. idUtenteIns =  idUtenteIns;
		this. idProgetto =  idProgetto;
		this. dtRinuncia =  dtRinuncia;
	}
  	
  	
	public String getMotivoRinuncia() {
		return motivoRinuncia;
	}
	
	public void setMotivoRinuncia(String motivoRinuncia) {
		this.motivoRinuncia = motivoRinuncia;
	}
	
	public BigDecimal getIdRinuncia() {
		return idRinuncia;
	}
	
	public void setIdRinuncia(BigDecimal idRinuncia) {
		this.idRinuncia = idRinuncia;
	}
	
	public BigDecimal getImportoDaRestituire() {
		return importoDaRestituire;
	}
	
	public void setImportoDaRestituire(BigDecimal importoDaRestituire) {
		this.importoDaRestituire = importoDaRestituire;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getGiorniRinuncia() {
		return giorniRinuncia;
	}
	
	public void setGiorniRinuncia(BigDecimal giorniRinuncia) {
		this.giorniRinuncia = giorniRinuncia;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public Date getDtRinuncia() {
		return dtRinuncia;
	}
	
	public void setDtRinuncia(Date dtRinuncia) {
		this.dtRinuncia = dtRinuncia;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && motivoRinuncia != null && importoDaRestituire != null && giorniRinuncia != null && idUtenteIns != null && idProgetto != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idRinuncia != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( motivoRinuncia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" motivoRinuncia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRinuncia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRinuncia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoDaRestituire);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoDaRestituire: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( giorniRinuncia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" giorniRinuncia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtRinuncia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtRinuncia: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idRinuncia");
		
	    return pk;
	}
	
	
}
