/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;

public class PbandiDObiettivoTemVO extends GenericVO {
  	
 	private BigDecimal idObiettivoTem;
  	
  	private String codObiettivoTem;
  	
  	private String descrObiettivoTem;
  	  	
	public PbandiDObiettivoTemVO() {}
  	
	public PbandiDObiettivoTemVO (BigDecimal idObiettivoTem) {	
		this.idObiettivoTem =  idObiettivoTem;
	}
  	
	public PbandiDObiettivoTemVO (BigDecimal idObiettivoTem, String codObiettivoTem, String descrObiettivoTem) {
		this.idObiettivoTem =  idObiettivoTem;
		this.codObiettivoTem =  codObiettivoTem;
		this.descrObiettivoTem =  descrObiettivoTem;	
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
		if (idObiettivoTem != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idObiettivoTem);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idObiettivoTem: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codObiettivoTem);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codObiettivoTem: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descrObiettivoTem);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrObiettivoTem: " + temp + "\t\n");
	   	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();		
			pk.add("idObiettivoTem");		
	    return pk;
	}

	public BigDecimal getIdObiettivoTem() {
		return idObiettivoTem;
	}

	public void setIdObiettivoTem(BigDecimal idObiettivoTem) {
		this.idObiettivoTem = idObiettivoTem;
	}

	public String getCodObiettivoTem() {
		return codObiettivoTem;
	}

	public void setCodObiettivoTem(String codObiettivoTem) {
		this.codObiettivoTem = codObiettivoTem;
	}

	public String getDescrObiettivoTem() {
		return descrObiettivoTem;
	}

	public void setDescrObiettivoTem(String descrObiettivoTem) {
		this.descrObiettivoTem = descrObiettivoTem;
	}

}
