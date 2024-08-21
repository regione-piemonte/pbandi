/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;



public class PbandiDTemplateNotificaVO extends GenericVO {

  	
  	private BigDecimal  idTemplateNotifica;
  	
  	private String  descrBreveTemplateNotifica;
  	
	private String  compSubject;
	
	private String  compMessage;
  	
	public PbandiDTemplateNotificaVO() {}
  	
	public PbandiDTemplateNotificaVO (BigDecimal idTemplateNotifica) {
	
		this. idTemplateNotifica =  idTemplateNotifica;
	}
  	
	public PbandiDTemplateNotificaVO (  BigDecimal idTemplateNotifica,  String descrBreveTemplateNotifica, String compSubject, String compMessage) {
	
		this. idTemplateNotifica =  idTemplateNotifica;
		this. descrBreveTemplateNotifica =  descrBreveTemplateNotifica;
		this. compSubject =  compSubject;
		this. compMessage =  compMessage;
	}
  	
  	
	public boolean isValid() {
   		return isPKValid();
	}
	
	public BigDecimal getIdTemplateNotifica() {
		return idTemplateNotifica;
	}

	public void setIdTemplateNotifica(BigDecimal idTemplateNotifica) {
		this.idTemplateNotifica = idTemplateNotifica;
	}

	public String getDescrBreveTemplateNotifica() {
		return descrBreveTemplateNotifica;
	}

	public void setDescrBreveTemplateNotifica(String descrBreveTemplateNotifica) {
		this.descrBreveTemplateNotifica = descrBreveTemplateNotifica;
	}

	public String getCompSubject() {
		return compSubject;
	}

	public void setCompSubject(String compSubject) {
		this.compSubject = compSubject;
	}

	public String getCompMessage() {
		return compMessage;
	}

	public void setCompMessage(String compMessage) {
		this.compMessage = compMessage;
	}

	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTemplateNotifica != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTemplateNotifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTemplateNotifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descrBreveTemplateNotifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrBreveTemplateNotifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( compSubject);
	    if (!DataFilter.isEmpty(temp)) sb.append(" compSubject: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( compMessage);
	    if (!DataFilter.isEmpty(temp)) sb.append(" compMessage: " + temp + "\t\n");
	    
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idTemplateNotifica");
		
	    return pk;
	}
	
	
}
