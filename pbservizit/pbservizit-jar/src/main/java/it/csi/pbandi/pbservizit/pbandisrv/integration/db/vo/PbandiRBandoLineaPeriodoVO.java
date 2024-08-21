/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiRBandoLineaPeriodoVO extends GenericVO {

  	
  	private BigDecimal idPeriodo;
  	
  	private BigDecimal progrBandoLineaIntervento;
  	
  	
	public PbandiRBandoLineaPeriodoVO() {}
  	
  	
	public PbandiRBandoLineaPeriodoVO (BigDecimal idPeriodo, BigDecimal progrBandoLineaIntervento) {
	
		this. idPeriodo =  idPeriodo;
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
	}
  	
  	
	public BigDecimal getIdPeriodo() {
		return idPeriodo;
	}
	
	public void setIdPeriodo(BigDecimal idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
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
		if (idPeriodo != null && progrBandoLineaIntervento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idPeriodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPeriodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idPeriodo");
		
			pk.add("progrBandoLineaIntervento");
		
	    return pk;
	}
	
	
}
