/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiTChecklistHtmlVO extends GenericVO {

  	
  	private BigDecimal idChecklist;
  	
  	private String html;
  	
	public PbandiTChecklistHtmlVO() {}
  	
	public PbandiTChecklistHtmlVO (BigDecimal idChecklist) {
	
		this. idChecklist =  idChecklist;
	}
  	
	public PbandiTChecklistHtmlVO (BigDecimal idChecklist, String html) {
	
		this. idChecklist =  idChecklist;
		this. html =  html;
	}
  	
  	
	public BigDecimal getIdChecklist() {
		return idChecklist;
	}
	
	public void setIdChecklist(BigDecimal idChecklist) {
		this.idChecklist = idChecklist;
	}
	
	public String getHtml() {
		return html;
	}
	
	public void setHtml(String html) {
		this.html = html;
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
		if (idChecklist != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idChecklist);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idChecklist: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( html);
	    if (!DataFilter.isEmpty(temp)) sb.append(" html: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idChecklist");
		
	    return pk;
	}
	
	
}
