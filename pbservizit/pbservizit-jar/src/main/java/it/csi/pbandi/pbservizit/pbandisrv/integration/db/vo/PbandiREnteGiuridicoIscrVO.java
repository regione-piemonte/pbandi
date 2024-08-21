/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiREnteGiuridicoIscrVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idEnteGiuridico;
  	
  	private BigDecimal idIscrizione;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiREnteGiuridicoIscrVO() {}
  	
	public PbandiREnteGiuridicoIscrVO (BigDecimal idEnteGiuridico, BigDecimal idIscrizione) {
	
		this. idEnteGiuridico =  idEnteGiuridico;
		this. idIscrizione =  idIscrizione;
	}
  	
	public PbandiREnteGiuridicoIscrVO (Date dtFineValidita, BigDecimal idUtenteAgg, BigDecimal idEnteGiuridico, BigDecimal idIscrizione, BigDecimal idUtenteIns) {
	
		this. dtFineValidita =  dtFineValidita;
		this. idUtenteAgg =  idUtenteAgg;
		this. idEnteGiuridico =  idEnteGiuridico;
		this. idIscrizione =  idIscrizione;
		this. idUtenteIns =  idUtenteIns;
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
	
	public BigDecimal getIdEnteGiuridico() {
		return idEnteGiuridico;
	}
	
	public void setIdEnteGiuridico(BigDecimal idEnteGiuridico) {
		this.idEnteGiuridico = idEnteGiuridico;
	}
	
	public BigDecimal getIdIscrizione() {
		return idIscrizione;
	}
	
	public void setIdIscrizione(BigDecimal idIscrizione) {
		this.idIscrizione = idIscrizione;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
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
		if (idEnteGiuridico != null && idIscrizione != null ) {
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
	    
	    temp = DataFilter.removeNull( idEnteGiuridico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEnteGiuridico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIscrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIscrizione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idEnteGiuridico");
		
			pk.add("idIscrizione");
		
	    return pk;
	}
	
	
}
