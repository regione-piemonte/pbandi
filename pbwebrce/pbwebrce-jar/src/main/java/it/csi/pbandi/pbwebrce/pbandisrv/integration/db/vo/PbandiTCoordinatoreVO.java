/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiTCoordinatoreVO extends GenericVO {

  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idRecapiti;
  	
  	private String descCoordinatore;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idIndirizzo;
  	
  	private BigDecimal idCoordinatore;
  	
	public PbandiTCoordinatoreVO() {}
  	
	public PbandiTCoordinatoreVO (BigDecimal idCoordinatore) {
	
		this. idCoordinatore =  idCoordinatore;
	}
  	
	public PbandiTCoordinatoreVO (BigDecimal idUtenteAgg, BigDecimal idRecapiti, String descCoordinatore, BigDecimal idUtenteIns, BigDecimal idIndirizzo, BigDecimal idCoordinatore) {
	
		this. idUtenteAgg =  idUtenteAgg;
		this. idRecapiti =  idRecapiti;
		this. descCoordinatore =  descCoordinatore;
		this. idUtenteIns =  idUtenteIns;
		this. idIndirizzo =  idIndirizzo;
		this. idCoordinatore =  idCoordinatore;
	}
  	
  	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdRecapiti() {
		return idRecapiti;
	}
	
	public void setIdRecapiti(BigDecimal idRecapiti) {
		this.idRecapiti = idRecapiti;
	}
	
	public String getDescCoordinatore() {
		return descCoordinatore;
	}
	
	public void setDescCoordinatore(String descCoordinatore) {
		this.descCoordinatore = descCoordinatore;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdIndirizzo() {
		return idIndirizzo;
	}
	
	public void setIdIndirizzo(BigDecimal idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	
	public BigDecimal getIdCoordinatore() {
		return idCoordinatore;
	}
	
	public void setIdCoordinatore(BigDecimal idCoordinatore) {
		this.idCoordinatore = idCoordinatore;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idRecapiti != null && descCoordinatore != null && idUtenteIns != null && idIndirizzo != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idCoordinatore != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRecapiti);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRecapiti: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descCoordinatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descCoordinatore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndirizzo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndirizzo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCoordinatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCoordinatore: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idCoordinatore");
		
	    return pk;
	}
	
	
}
