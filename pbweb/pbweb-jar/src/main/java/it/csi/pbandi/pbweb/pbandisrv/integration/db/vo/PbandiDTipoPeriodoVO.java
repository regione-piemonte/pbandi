/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoPeriodoVO extends GenericVO {

  	
  	private String descTipoPeriodo;
  	
  	private BigDecimal idTipoPeriodo;
  	
  	private Date dtFineValidita;
  	
  	private String descBreveTipoPeriodo;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDTipoPeriodoVO() {}
  	
	public PbandiDTipoPeriodoVO (BigDecimal idTipoPeriodo) {
	
		this. idTipoPeriodo =  idTipoPeriodo;
	}
  	
	public PbandiDTipoPeriodoVO (String descTipoPeriodo, BigDecimal idTipoPeriodo, Date dtFineValidita, String descBreveTipoPeriodo, Date dtInizioValidita) {
	
		this. descTipoPeriodo =  descTipoPeriodo;
		this. idTipoPeriodo =  idTipoPeriodo;
		this. dtFineValidita =  dtFineValidita;
		this. descBreveTipoPeriodo =  descBreveTipoPeriodo;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getDescTipoPeriodo() {
		return descTipoPeriodo;
	}
	
	public void setDescTipoPeriodo(String descTipoPeriodo) {
		this.descTipoPeriodo = descTipoPeriodo;
	}
	
	public BigDecimal getIdTipoPeriodo() {
		return idTipoPeriodo;
	}
	
	public void setIdTipoPeriodo(BigDecimal idTipoPeriodo) {
		this.idTipoPeriodo = idTipoPeriodo;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveTipoPeriodo() {
		return descBreveTipoPeriodo;
	}
	
	public void setDescBreveTipoPeriodo(String descBreveTipoPeriodo) {
		this.descBreveTipoPeriodo = descBreveTipoPeriodo;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descTipoPeriodo != null && descBreveTipoPeriodo != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoPeriodo != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoPeriodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoPeriodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoPeriodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoPeriodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoPeriodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoPeriodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoPeriodo");
		
	    return pk;
	}
	
	
}
