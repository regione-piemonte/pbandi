/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;

public class PbandiDRuoloHelpVO extends GenericVO {
 
  	private String descRuoloHelp;
  	
  	private BigDecimal idRuoloHelp;
  	
	public PbandiDRuoloHelpVO() {}
  	
	public PbandiDRuoloHelpVO (BigDecimal idRuoloHelp) {
		this.setIdRuoloHelp(idRuoloHelp);
	}
  	
	public PbandiDRuoloHelpVO (BigDecimal idRuoloHelp,   String descRuoloHelp ) {
		this.setIdRuoloHelp(idRuoloHelp);
		this.setDescRuoloHelp(descRuoloHelp);
	}
  	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && getDescRuoloHelp() != null  ) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (getIdRuoloHelp() != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( getIdRuoloHelp());
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRuoloHelp: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( getDescRuoloHelp());
	    if (!DataFilter.isEmpty(temp)) sb.append(" descRuoloHelp: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idRuoloHelp");
		
	    return pk;
	}

	public String getDescRuoloHelp() {
		return descRuoloHelp;
	}

	public void setDescRuoloHelp(String descRuoloHelp) {
		this.descRuoloHelp = descRuoloHelp;
	}

	public BigDecimal getIdRuoloHelp() {
		return idRuoloHelp;
	}

	public void setIdRuoloHelp(BigDecimal idRuoloHelp) {
		this.idRuoloHelp = idRuoloHelp;
	}
	
	
}
