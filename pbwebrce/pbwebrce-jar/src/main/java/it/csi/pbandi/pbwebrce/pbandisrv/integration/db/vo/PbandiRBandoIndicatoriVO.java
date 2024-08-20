/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRBandoIndicatoriVO extends GenericVO {

  	
  	private BigDecimal idBando;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idIndicatori;
  	
	public PbandiRBandoIndicatoriVO() {}
  	
	public PbandiRBandoIndicatoriVO (BigDecimal idBando, BigDecimal idIndicatori) {
	
		this. idBando =  idBando;
		this. idIndicatori =  idIndicatori;
	}
  	
	public PbandiRBandoIndicatoriVO (BigDecimal idBando, BigDecimal idUtenteAgg, BigDecimal idUtenteIns, BigDecimal idIndicatori) {
	
		this. idBando =  idBando;
		this. idUtenteAgg =  idUtenteAgg;
		this. idUtenteIns =  idUtenteIns;
		this. idIndicatori =  idIndicatori;
	}
  	
  	
	public BigDecimal getIdBando() {
		return idBando;
	}
	
	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
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
	
	public BigDecimal getIdIndicatori() {
		return idIndicatori;
	}
	
	public void setIdIndicatori(BigDecimal idIndicatori) {
		this.idIndicatori = idIndicatori;
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
		if (idBando != null && idIndicatori != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBando: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndicatori);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndicatori: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idBando");
		
			pk.add("idIndicatori");
		
	    return pk;
	}
	
	
}
