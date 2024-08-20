/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRLineaDimensioneTerVO extends GenericVO {

  	
  	private BigDecimal idLineaDiIntervento;
  	
  	private BigDecimal idDimensioneTerritor;
  	
	public PbandiRLineaDimensioneTerVO() {}
  	
	 
  	
	public PbandiRLineaDimensioneTerVO (BigDecimal idLineaDiIntervento, BigDecimal idDimensioneTerritor) {
	
		this. idLineaDiIntervento =  idLineaDiIntervento;
		this. idDimensioneTerritor =  idDimensioneTerritor;
	}
  	
  	
	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}
	
	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	
	public BigDecimal getIdDimensioneTerritor() {
		return idDimensioneTerritor;
	}
	
	public void setIdDimensioneTerritor(BigDecimal idDimensioneTerritor) {
		this.idDimensioneTerritor = idDimensioneTerritor;
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
		if (idLineaDiIntervento != null && idDimensioneTerritor != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idLineaDiIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDimensioneTerritor);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDimensioneTerritor: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idLineaDiIntervento");
		
			pk.add("idDimensioneTerritor");
		
	    return pk;
	}
	
	
}
