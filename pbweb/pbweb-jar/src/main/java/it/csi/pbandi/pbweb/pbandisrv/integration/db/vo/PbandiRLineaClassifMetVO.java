/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRLineaClassifMetVO extends GenericVO {
  	
  	private BigDecimal idClassificazioneMet;
  	
  	private BigDecimal idLineaDiIntervento;
  	
	public PbandiRLineaClassifMetVO() {}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idClassificazioneMet != null && idLineaDiIntervento != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");	    
	    temp = DataFilter.removeNull( idClassificazioneMet);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idClassificazioneMet: " + temp + "\t\n");	    
	    temp = DataFilter.removeNull( idLineaDiIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiIntervento: " + temp + "\t\n");	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
			pk.add("idClassificazioneMet");
			pk.add("idLineaDiIntervento");
	    return pk;
	}

	public BigDecimal getIdClassificazioneMet() {
		return idClassificazioneMet;
	}

	public void setIdClassificazioneMet(BigDecimal idClassificazioneMet) {
		this.idClassificazioneMet = idClassificazioneMet;
	}

	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	
}
