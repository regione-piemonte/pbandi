/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRBandoLinIndRisProVO extends GenericVO {

  	
  	private BigDecimal idIndRisultatoProgram;
  	
  	private BigDecimal progrBandoLineaIntervento;
  	
	public PbandiRBandoLinIndRisProVO() {}
  	
	public PbandiRBandoLinIndRisProVO (BigDecimal idIndRisultatoProgram, BigDecimal progrBandoLineaIntervento) {
	
		this. idIndRisultatoProgram =  idIndRisultatoProgram;
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
	}
  	
	public BigDecimal getIdIndRisultatoProgram() {
		return idIndRisultatoProgram;
	}
	
	public void setIdIndRisultatoProgram(BigDecimal idIndRisultatoProgram) {
		this.idIndRisultatoProgram = idIndRisultatoProgram;
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
		if (idIndRisultatoProgram != null && progrBandoLineaIntervento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndRisultatoProgram);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndRisultatoProgram: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idIndRisultatoProgram");
		
			pk.add("progrBandoLineaIntervento");
		
	    return pk;
	}
	
	
}
