/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

public class PbandiDCategAnagraficaVO extends GenericVO {

	private BigDecimal idCategAnagrafica;
  	
  	private String descCategAnagrafica;
  	
  	public BigDecimal getIdCategAnagrafica() {
		return idCategAnagrafica;
	}

	public void setIdCategAnagrafica(BigDecimal idCategAnagrafica) {
		this.idCategAnagrafica = idCategAnagrafica;
	}

	public String getDescCategAnagrafica() {
		return descCategAnagrafica;
	}

	public void setDescCategAnagrafica(String descCategAnagrafica) {
		this.descCategAnagrafica = descCategAnagrafica;
	}
	
 	public PbandiDCategAnagraficaVO() {}
  	
	public PbandiDCategAnagraficaVO (BigDecimal idCategAnagrafica) {	
		this. idCategAnagrafica =  idCategAnagrafica;
	}
  	
	public PbandiDCategAnagraficaVO (BigDecimal idCategAnagrafica, String descCategAnagrafica) {	
		this. idCategAnagrafica =  idCategAnagrafica;
		this. descCategAnagrafica =  descCategAnagrafica;
	}  	

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descCategAnagrafica != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idCategAnagrafica != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idCategAnagrafica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCategAnagrafica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descCategAnagrafica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descCategAnagrafica: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idCategAnagrafica");
		
	    return pk;
	}
	
	
}
