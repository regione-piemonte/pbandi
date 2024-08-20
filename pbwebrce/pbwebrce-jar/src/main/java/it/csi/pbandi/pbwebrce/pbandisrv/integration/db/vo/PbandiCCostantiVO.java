/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiCCostantiVO extends GenericVO {

  	
  	private String attributo;
  	
  	private String valore;
  	
  	
	public PbandiCCostantiVO () {
	
	}
  	
	public PbandiCCostantiVO (String attributo, String valore) {
	
		this. attributo =  attributo;
		this. valore =  valore;
	}
  	
  	
	public String getAttributo() {
		return attributo;
	}
	
	public void setAttributo(String attributo) {
		this.attributo = attributo;
	}
	
	public String getValore() {
		return valore;
	}
	
	public void setValore(String valore) {
		this.valore = valore;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && attributo != null && valore != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = true;

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( attributo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" attributo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( valore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" valore: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
	    return pk;
	}
	
	
}
