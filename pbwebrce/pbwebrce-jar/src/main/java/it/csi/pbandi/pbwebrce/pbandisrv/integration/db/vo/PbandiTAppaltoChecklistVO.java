/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiTAppaltoChecklistVO extends GenericVO {

  	
  	private BigDecimal idChecklist;
  	
  	private BigDecimal idAppaltoChecklist;
  	
  	private BigDecimal idAppalto;
  	
	public PbandiTAppaltoChecklistVO() {}
  	
	public PbandiTAppaltoChecklistVO (BigDecimal idAppaltoChecklist) {
	
		this. idAppaltoChecklist =  idAppaltoChecklist;
	}
  	
	public PbandiTAppaltoChecklistVO (BigDecimal idChecklist, BigDecimal idAppaltoChecklist, BigDecimal idAppalto) {
	
		this. idChecklist =  idChecklist;
		this. idAppaltoChecklist =  idAppaltoChecklist;
		this. idAppalto =  idAppalto;
	}
  	
  	
	public BigDecimal getIdChecklist() {
		return idChecklist;
	}
	
	public void setIdChecklist(BigDecimal idChecklist) {
		this.idChecklist = idChecklist;
	}
	
	public BigDecimal getIdAppaltoChecklist() {
		return idAppaltoChecklist;
	}
	
	public void setIdAppaltoChecklist(BigDecimal idAppaltoChecklist) {
		this.idAppaltoChecklist = idAppaltoChecklist;
	}
	
	public BigDecimal getIdAppalto() {
		return idAppalto;
	}
	
	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idChecklist != null && idAppalto != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAppaltoChecklist != null ) {
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
	    
	    temp = DataFilter.removeNull( idAppaltoChecklist);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAppaltoChecklist: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAppalto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAppalto: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idAppaltoChecklist");
		
	    return pk;
	}
	
	
}
