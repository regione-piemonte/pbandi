/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiTEmailSoggettoVO extends GenericVO {

  	
  	private BigDecimal idSoggetto;
  	
  	private String emailSoggetto;
  	
	public PbandiTEmailSoggettoVO() {}
  	
	public PbandiTEmailSoggettoVO (BigDecimal idSoggetto) {
	
		this. idSoggetto =  idSoggetto;
	}
  	
	public PbandiTEmailSoggettoVO (BigDecimal idSoggetto, String emailSoggetto) {
	
		this. idSoggetto =  idSoggetto;
		this. emailSoggetto =  emailSoggetto;
	}
  	
  	
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	
	public String getEmailSoggetto() {
		return emailSoggetto;
	}
	
	public void setEmailSoggetto(String emailSoggetto) {
		this.emailSoggetto = emailSoggetto;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && emailSoggetto != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idSoggetto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( emailSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" emailSoggetto: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idSoggetto");
		
	    return pk;
	}
	
	
}
