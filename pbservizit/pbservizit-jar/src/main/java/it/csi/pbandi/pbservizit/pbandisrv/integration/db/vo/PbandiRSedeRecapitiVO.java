/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiRSedeRecapitiVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idRecapiti;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idSede;
  	
	public PbandiRSedeRecapitiVO() {}
  	
	public PbandiRSedeRecapitiVO (BigDecimal idRecapiti, BigDecimal idSede) {
	
		this. idRecapiti =  idRecapiti;
		this. idSede =  idSede;
	}
  	
	public PbandiRSedeRecapitiVO (Date dtFineValidita, BigDecimal idUtenteAgg, BigDecimal idRecapiti, BigDecimal idUtenteIns, BigDecimal idSede) {
	
		this. dtFineValidita =  dtFineValidita;
		this. idUtenteAgg =  idUtenteAgg;
		this. idRecapiti =  idRecapiti;
		this. idUtenteIns =  idUtenteIns;
		this. idSede =  idSede;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
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
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdSede() {
		return idSede;
	}
	
	public void setIdSede(BigDecimal idSede) {
		this.idSede = idSede;
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
		if (idRecapiti != null && idSede != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRecapiti);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRecapiti: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSede);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSede: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idRecapiti");
		
			pk.add("idSede");
		
	    return pk;
	}
	
	
}
