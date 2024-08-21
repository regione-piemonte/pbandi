/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiRLineaStrumentoAttVO extends GenericVO {

  	
  	private BigDecimal idStrumentoAttuativo;
  	
  	private BigDecimal idLineaDiIntervento;
  	
	public PbandiRLineaStrumentoAttVO() {}
  	
 
  	
	public PbandiRLineaStrumentoAttVO (BigDecimal idStrumentoAttuativo, BigDecimal idLineaDiIntervento) {
	
		this. idStrumentoAttuativo =  idStrumentoAttuativo;
		this. idLineaDiIntervento =  idLineaDiIntervento;
	}
  	
  	
	public BigDecimal getIdStrumentoAttuativo() {
		return idStrumentoAttuativo;
	}
	
	public void setIdStrumentoAttuativo(BigDecimal idStrumentoAttuativo) {
		this.idStrumentoAttuativo = idStrumentoAttuativo;
	}
	
	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}
	
	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
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
		if (idStrumentoAttuativo != null && idLineaDiIntervento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idStrumentoAttuativo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStrumentoAttuativo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idLineaDiIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiIntervento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idStrumentoAttuativo");
		
			pk.add("idLineaDiIntervento");
		
	    return pk;
	}
	
	
}
