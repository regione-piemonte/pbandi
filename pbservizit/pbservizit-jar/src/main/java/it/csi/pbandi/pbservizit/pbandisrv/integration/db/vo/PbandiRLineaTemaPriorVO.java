/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiRLineaTemaPriorVO extends GenericVO {
  	
  	private BigDecimal idTemaPrioritario;
  	
  	private BigDecimal idLineaDiIntervento;
  	
	public PbandiRLineaTemaPriorVO() {}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTemaPrioritario != null && idLineaDiIntervento != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");	    
	    temp = DataFilter.removeNull( idTemaPrioritario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTemaPrioritario: " + temp + "\t\n");	    
	    temp = DataFilter.removeNull( idLineaDiIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiIntervento: " + temp + "\t\n");	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
			pk.add("idTemaPrioritario");
			pk.add("idLineaDiIntervento");
	    return pk;
	}

	public BigDecimal getIdTemaPrioritario() {
		return idTemaPrioritario;
	}

	public void setIdTemaPrioritario(BigDecimal idTemaPrioritario) {
		this.idTemaPrioritario = idTemaPrioritario;
	}

	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	
}
