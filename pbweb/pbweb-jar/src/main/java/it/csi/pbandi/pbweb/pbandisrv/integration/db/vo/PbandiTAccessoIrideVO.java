/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTAccessoIrideVO extends GenericVO {

  	
  	private String sessionId;
  	
  	private String shibId;
  	
  	private Date accessDate;
  	
  	private String msg;
  	
  	private String irideId;
  	
  	private BigDecimal idAccessoIride;
  	
	public PbandiTAccessoIrideVO() {}
  	
	public PbandiTAccessoIrideVO (BigDecimal idAccessoIride) {
	
		this. idAccessoIride =  idAccessoIride;
	}
  	
	public PbandiTAccessoIrideVO (String sessionId, String shibId, Date accessDate, String msg, String irideId, BigDecimal idAccessoIride) {
	
		this. sessionId =  sessionId;
		this. shibId =  shibId;
		this. accessDate =  accessDate;
		this. msg =  msg;
		this. irideId =  irideId;
		this. idAccessoIride =  idAccessoIride;
	}
  	
  	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getShibId() {
		return shibId;
	}
	
	public void setShibId(String shibId) {
		this.shibId = shibId;
	}
	
	public Date getAccessDate() {
		return accessDate;
	}
	
	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getIrideId() {
		return irideId;
	}
	
	public void setIrideId(String irideId) {
		this.irideId = irideId;
	}
	
	public BigDecimal getIdAccessoIride() {
		return idAccessoIride;
	}
	
	public void setIdAccessoIride(BigDecimal idAccessoIride) {
		this.idAccessoIride = idAccessoIride;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && accessDate != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAccessoIride != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( sessionId);
	    if (!DataFilter.isEmpty(temp)) sb.append(" sessionId: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( shibId);
	    if (!DataFilter.isEmpty(temp)) sb.append(" shibId: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( accessDate);
	    if (!DataFilter.isEmpty(temp)) sb.append(" accessDate: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( msg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" msg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( irideId);
	    if (!DataFilter.isEmpty(temp)) sb.append(" irideId: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAccessoIride);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAccessoIride: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idAccessoIride");
		
	    return pk;
	}
	
	
}
