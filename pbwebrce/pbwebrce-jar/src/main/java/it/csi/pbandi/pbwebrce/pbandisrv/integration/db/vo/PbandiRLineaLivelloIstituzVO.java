/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRLineaLivelloIstituzVO extends GenericVO {
  	
  	private BigDecimal idLivelloIstituzione;
  	
  	private BigDecimal idLineaDiIntervento;
  	
	public PbandiRLineaLivelloIstituzVO() {}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idLivelloIstituzione != null && idLineaDiIntervento != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");	    
	    temp = DataFilter.removeNull( idLivelloIstituzione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLivelloIstituzione: " + temp + "\t\n");	    
	    temp = DataFilter.removeNull( idLineaDiIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiIntervento: " + temp + "\t\n");	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
			pk.add("idLivelloIstituzione");
			pk.add("idLineaDiIntervento");
	    return pk;
	}

	public BigDecimal getIdLivelloIstituzione() {
		return idLivelloIstituzione;
	}

	public void setIdLivelloIstituzione(BigDecimal idLivelloIstituzione) {
		this.idLivelloIstituzione = idLivelloIstituzione;
	}

	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	
}
