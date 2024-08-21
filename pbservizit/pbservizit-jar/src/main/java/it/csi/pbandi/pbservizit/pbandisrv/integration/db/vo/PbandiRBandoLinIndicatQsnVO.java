/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiRBandoLinIndicatQsnVO extends GenericVO {

  	
  	private BigDecimal progrBandoLineaIntervento;
  	
  	private BigDecimal idIndicatoreQsn;
  	
	public PbandiRBandoLinIndicatQsnVO() {}
  	
	public PbandiRBandoLinIndicatQsnVO (BigDecimal progrBandoLineaIntervento, BigDecimal idIndicatoreQsn) {
	
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this. idIndicatoreQsn =  idIndicatoreQsn;
	}
 	  	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	
	public BigDecimal getIdIndicatoreQsn() {
		return idIndicatoreQsn;
	}
	
	public void setIdIndicatoreQsn(BigDecimal idIndicatoreQsn) {
		this.idIndicatoreQsn = idIndicatoreQsn;
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
		if (progrBandoLineaIntervento != null && idIndicatoreQsn != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndicatoreQsn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndicatoreQsn: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("progrBandoLineaIntervento");
		
			pk.add("idIndicatoreQsn");
		
	    return pk;
	}
	
	
}
