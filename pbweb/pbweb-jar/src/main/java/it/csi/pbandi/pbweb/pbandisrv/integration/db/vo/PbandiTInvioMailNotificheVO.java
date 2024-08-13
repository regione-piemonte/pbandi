/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTInvioMailNotificheVO extends GenericVO {

  	
  	private BigDecimal idSoggettoNotifica;
  	
  	private Date dtInvioEmail;
  	
  	private BigDecimal idInvioEmail;
  	
	public PbandiTInvioMailNotificheVO() {}
  	
	public PbandiTInvioMailNotificheVO (BigDecimal idInvioEmail) {
	
		this. idInvioEmail =  idInvioEmail;
	}
  	
	public PbandiTInvioMailNotificheVO (BigDecimal idSoggettoNotifica, Date dtInvioEmail, BigDecimal idInvioEmail) {
	
		this. idSoggettoNotifica =  idSoggettoNotifica;
		this. dtInvioEmail =  dtInvioEmail;
		this. idInvioEmail =  idInvioEmail;
	}
  	
  	
	public BigDecimal getIdSoggettoNotifica() {
		return idSoggettoNotifica;
	}
	
	public void setIdSoggettoNotifica(BigDecimal idSoggettoNotifica) {
		this.idSoggettoNotifica = idSoggettoNotifica;
	}
	
	public Date getDtInvioEmail() {
		return dtInvioEmail;
	}
	
	public void setDtInvioEmail(Date dtInvioEmail) {
		this.dtInvioEmail = dtInvioEmail;
	}
	
	public BigDecimal getIdInvioEmail() {
		return idInvioEmail;
	}
	
	public void setIdInvioEmail(BigDecimal idInvioEmail) {
		this.idInvioEmail = idInvioEmail;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idSoggettoNotifica != null && dtInvioEmail != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idInvioEmail != null ) {
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
	    
	    temp = DataFilter.removeNull( dtInvioEmail);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInvioEmail: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idInvioEmail);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idInvioEmail: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idInvioEmail");
		
	    return pk;
	}
	
	
}
