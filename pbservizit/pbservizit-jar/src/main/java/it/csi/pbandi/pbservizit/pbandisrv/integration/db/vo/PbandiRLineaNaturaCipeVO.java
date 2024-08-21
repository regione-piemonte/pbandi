/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;


public class PbandiRLineaNaturaCipeVO extends GenericVO {
  	
  	private BigDecimal idNaturaCipe;
  	
  	private BigDecimal idLineaDiIntervento;
  	
	public PbandiRLineaNaturaCipeVO() {}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idNaturaCipe != null && idLineaDiIntervento != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");	    
	    temp = DataFilter.removeNull( idNaturaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNaturaCipe: " + temp + "\t\n");	    
	    temp = DataFilter.removeNull( idLineaDiIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiIntervento: " + temp + "\t\n");	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
			pk.add("idNaturaCipe");
			pk.add("idLineaDiIntervento");
	    return pk;
	}

	public BigDecimal getIdNaturaCipe() {
		return idNaturaCipe;
	}

	public void setIdNaturaCipe(BigDecimal idNaturaCipe) {
		this.idNaturaCipe = idNaturaCipe;
	}

	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	
}
