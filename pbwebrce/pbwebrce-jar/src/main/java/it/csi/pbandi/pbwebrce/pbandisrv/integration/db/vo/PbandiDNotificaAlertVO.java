/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDNotificaAlertVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idNotificaAlert;
  	
  	private String descrNotifica;
  	
  	private String descrBreveNotifica;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDNotificaAlertVO() {}
  	
	public PbandiDNotificaAlertVO (BigDecimal idNotificaAlert) {
	
		this. idNotificaAlert =  idNotificaAlert;
	}
  	
	public PbandiDNotificaAlertVO (Date dtFineValidita, BigDecimal idNotificaAlert, String descrNotifica, String descrBreveNotifica, Date dtInizioValidita) {
	
		this. dtFineValidita =  dtFineValidita;
		this. idNotificaAlert =  idNotificaAlert;
		this. descrNotifica =  descrNotifica;
		this. descrBreveNotifica =  descrBreveNotifica;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdNotificaAlert() {
		return idNotificaAlert;
	}
	
	public void setIdNotificaAlert(BigDecimal idNotificaAlert) {
		this.idNotificaAlert = idNotificaAlert;
	}
	
	public String getDescrNotifica() {
		return descrNotifica;
	}
	
	public void setDescrNotifica(String descrNotifica) {
		this.descrNotifica = descrNotifica;
	}
	
	public String getDescrBreveNotifica() {
		return descrBreveNotifica;
	}
	
	public void setDescrBreveNotifica(String descrBreveNotifica) {
		this.descrBreveNotifica = descrBreveNotifica;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descrNotifica != null && descrBreveNotifica != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idNotificaAlert != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNotificaAlert);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNotificaAlert: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descrNotifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrNotifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descrBreveNotifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrBreveNotifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idNotificaAlert");
		
	    return pk;
	}
	
	
}
