/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRNotifIstrProgettiVO extends GenericVO {

  	
  	private BigDecimal idSoggettoNotifica;
  	
  	private BigDecimal idProgetto;
  	
	public PbandiRNotifIstrProgettiVO() {}
  	
	public PbandiRNotifIstrProgettiVO (BigDecimal idSoggettoNotifica, BigDecimal idProgetto) {
	
		this. idSoggettoNotifica =  idSoggettoNotifica;
		this. idProgetto =  idProgetto;
	}
  	

  	
	public BigDecimal getIdSoggettoNotifica() {
		return idSoggettoNotifica;
	}
	
	public void setIdSoggettoNotifica(BigDecimal idSoggettoNotifica) {
		this.idSoggettoNotifica = idSoggettoNotifica;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idSoggettoNotifica != null && idProgetto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggettoNotifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggettoNotifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idSoggettoNotifica");
		
			pk.add("idProgetto");
		
	    return pk;
	}
	
	
}
