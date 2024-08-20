/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRLineaTipoLocalVO extends GenericVO {
  	
  	private BigDecimal idTipoLocalizzazione;
  	
  	private BigDecimal idLineaDiIntervento;
  	
	public PbandiRLineaTipoLocalVO() {}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoLocalizzazione != null && idLineaDiIntervento != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");	    
	    temp = DataFilter.removeNull( idTipoLocalizzazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoLocalizzazione: " + temp + "\t\n");	    
	    temp = DataFilter.removeNull( idLineaDiIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiIntervento: " + temp + "\t\n");	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
			pk.add("idTipoLocalizzazione");
			pk.add("idLineaDiIntervento");
	    return pk;
	}

	public BigDecimal getIdTipoLocalizzazione() {
		return idTipoLocalizzazione;
	}

	public void setIdTipoLocalizzazione(BigDecimal idTipoLocalizzazione) {
		this.idTipoLocalizzazione = idTipoLocalizzazione;
	}

	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	
}
