/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDFrequenzaVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descrFrequenza;
  	
  	private String descrBreveFrequenza;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idFrequenza;
  	
	public PbandiDFrequenzaVO() {}
  	
	public PbandiDFrequenzaVO (BigDecimal idFrequenza) {
	
		this. idFrequenza =  idFrequenza;
	}
  	
	public PbandiDFrequenzaVO (Date dtFineValidita, String descrFrequenza, String descrBreveFrequenza, Date dtInizioValidita, BigDecimal idFrequenza) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descrFrequenza =  descrFrequenza;
		this. descrBreveFrequenza =  descrBreveFrequenza;
		this. dtInizioValidita =  dtInizioValidita;
		this. idFrequenza =  idFrequenza;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescrFrequenza() {
		return descrFrequenza;
	}
	
	public void setDescrFrequenza(String descrFrequenza) {
		this.descrFrequenza = descrFrequenza;
	}
	
	public String getDescrBreveFrequenza() {
		return descrBreveFrequenza;
	}
	
	public void setDescrBreveFrequenza(String descrBreveFrequenza) {
		this.descrBreveFrequenza = descrBreveFrequenza;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdFrequenza() {
		return idFrequenza;
	}
	
	public void setIdFrequenza(BigDecimal idFrequenza) {
		this.idFrequenza = idFrequenza;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descrFrequenza != null && descrBreveFrequenza != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idFrequenza != null ) {
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
	    
	    temp = DataFilter.removeNull( descrFrequenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrFrequenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descrBreveFrequenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrBreveFrequenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFrequenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFrequenza: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idFrequenza");
		
	    return pk;
	}
	
	
}
