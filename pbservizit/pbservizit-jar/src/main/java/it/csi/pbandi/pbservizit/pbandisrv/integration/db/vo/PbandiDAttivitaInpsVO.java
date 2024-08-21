/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDAttivitaInpsVO extends GenericVO {

  	
  	private String descAttivitaInps;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idAttivitaInps;
  	
  	private Date dtInizioValidita;
  	
  	private String codAttivitaInps;
  	
	public PbandiDAttivitaInpsVO() {}
  	
	public PbandiDAttivitaInpsVO (BigDecimal idAttivitaInps) {
	
		this. idAttivitaInps =  idAttivitaInps;
	}
  	
	public PbandiDAttivitaInpsVO (String descAttivitaInps, Date dtFineValidita, BigDecimal idAttivitaInps, Date dtInizioValidita, String codAttivitaInps) {
	
		this. descAttivitaInps =  descAttivitaInps;
		this. dtFineValidita =  dtFineValidita;
		this. idAttivitaInps =  idAttivitaInps;
		this. dtInizioValidita =  dtInizioValidita;
		this. codAttivitaInps =  codAttivitaInps;
	}
  	
  	
	public String getDescAttivitaInps() {
		return descAttivitaInps;
	}
	
	public void setDescAttivitaInps(String descAttivitaInps) {
		this.descAttivitaInps = descAttivitaInps;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdAttivitaInps() {
		return idAttivitaInps;
	}
	
	public void setIdAttivitaInps(BigDecimal idAttivitaInps) {
		this.idAttivitaInps = idAttivitaInps;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getCodAttivitaInps() {
		return codAttivitaInps;
	}
	
	public void setCodAttivitaInps(String codAttivitaInps) {
		this.codAttivitaInps = codAttivitaInps;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descAttivitaInps != null && dtInizioValidita != null && codAttivitaInps != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAttivitaInps != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descAttivitaInps);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descAttivitaInps: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttivitaInps);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttivitaInps: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codAttivitaInps);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codAttivitaInps: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idAttivitaInps");
		
	    return pk;
	}
	
	
}
