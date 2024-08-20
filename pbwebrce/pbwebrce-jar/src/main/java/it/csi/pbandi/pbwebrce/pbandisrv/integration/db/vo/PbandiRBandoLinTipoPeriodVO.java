/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRBandoLinTipoPeriodVO extends GenericVO {

  	
  	private BigDecimal idTipoPeriodo;
  	
  	private BigDecimal progrBandoLineaIntervento;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idPeriodo;
  	
	public PbandiRBandoLinTipoPeriodVO() {}
  	
	public PbandiRBandoLinTipoPeriodVO (BigDecimal idTipoPeriodo, BigDecimal progrBandoLineaIntervento, BigDecimal idPeriodo) {
	
		this. idTipoPeriodo =  idTipoPeriodo;
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this.idPeriodo = idPeriodo;
	}
  	
	public PbandiRBandoLinTipoPeriodVO (BigDecimal idTipoPeriodo, BigDecimal progrBandoLineaIntervento, BigDecimal idUtenteAgg, BigDecimal idUtenteIns, BigDecimal idPeriodo) {
	
		this. idTipoPeriodo =  idTipoPeriodo;
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this. idUtenteAgg =  idUtenteAgg;
		this. idUtenteIns =  idUtenteIns;
		this.idPeriodo = idPeriodo;
	}
  	
  	
	public BigDecimal getIdTipoPeriodo() {
		return idTipoPeriodo;
	}
	
	public void setIdTipoPeriodo(BigDecimal idTipoPeriodo) {
		this.idTipoPeriodo = idTipoPeriodo;
	}
	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(BigDecimal idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoPeriodo != null && progrBandoLineaIntervento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoPeriodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoPeriodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoPeriodo");
		
			pk.add("progrBandoLineaIntervento");
		
	    return pk;
	}
	
	
}
