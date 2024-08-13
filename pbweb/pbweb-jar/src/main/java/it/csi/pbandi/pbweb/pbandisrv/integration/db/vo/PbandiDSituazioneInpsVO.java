/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDSituazioneInpsVO extends GenericVO {

  	
  	private BigDecimal idSituazioneInps;
  	
  	private String descBreveSituazioneInps;
  	
  	private Date dtFineValidita;
  	
  	private String descSituazioneInps;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDSituazioneInpsVO() {}
  	
	public PbandiDSituazioneInpsVO (BigDecimal idSituazioneInps) {
	
		this. idSituazioneInps =  idSituazioneInps;
	}
  	
	public PbandiDSituazioneInpsVO (BigDecimal idSituazioneInps, String descBreveSituazioneInps, Date dtFineValidita, String descSituazioneInps, Date dtInizioValidita) {
	
		this. idSituazioneInps =  idSituazioneInps;
		this. descBreveSituazioneInps =  descBreveSituazioneInps;
		this. dtFineValidita =  dtFineValidita;
		this. descSituazioneInps =  descSituazioneInps;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public BigDecimal getIdSituazioneInps() {
		return idSituazioneInps;
	}
	
	public void setIdSituazioneInps(BigDecimal idSituazioneInps) {
		this.idSituazioneInps = idSituazioneInps;
	}
	
	public String getDescBreveSituazioneInps() {
		return descBreveSituazioneInps;
	}
	
	public void setDescBreveSituazioneInps(String descBreveSituazioneInps) {
		this.descBreveSituazioneInps = descBreveSituazioneInps;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescSituazioneInps() {
		return descSituazioneInps;
	}
	
	public void setDescSituazioneInps(String descSituazioneInps) {
		this.descSituazioneInps = descSituazioneInps;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveSituazioneInps != null && descSituazioneInps != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idSituazioneInps != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idSituazioneInps);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSituazioneInps: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveSituazioneInps);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveSituazioneInps: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descSituazioneInps);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descSituazioneInps: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idSituazioneInps");
		
	    return pk;
	}
	
	
}
