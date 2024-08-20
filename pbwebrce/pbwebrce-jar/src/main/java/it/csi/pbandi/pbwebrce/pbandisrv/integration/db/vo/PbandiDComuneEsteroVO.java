/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDComuneEsteroVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idNazione;
  	
  	private String descComuneEstero;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idComuneEstero;
  	
	public PbandiDComuneEsteroVO() {}
  	
	public PbandiDComuneEsteroVO (BigDecimal idComuneEstero) {
	
		this. idComuneEstero =  idComuneEstero;
	}
  	
	public PbandiDComuneEsteroVO (Date dtFineValidita, BigDecimal idNazione, String descComuneEstero, Date dtInizioValidita, BigDecimal idComuneEstero) {
	
		this. dtFineValidita =  dtFineValidita;
		this. idNazione =  idNazione;
		this. descComuneEstero =  descComuneEstero;
		this. dtInizioValidita =  dtInizioValidita;
		this. idComuneEstero =  idComuneEstero;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdNazione() {
		return idNazione;
	}
	
	public void setIdNazione(BigDecimal idNazione) {
		this.idNazione = idNazione;
	}
	
	public String getDescComuneEstero() {
		return descComuneEstero;
	}
	
	public void setDescComuneEstero(String descComuneEstero) {
		this.descComuneEstero = descComuneEstero;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdComuneEstero() {
		return idComuneEstero;
	}
	
	public void setIdComuneEstero(BigDecimal idComuneEstero) {
		this.idComuneEstero = idComuneEstero;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idNazione != null && descComuneEstero != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idComuneEstero != null ) {
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
	    
	    temp = DataFilter.removeNull( idNazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descComuneEstero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descComuneEstero: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idComuneEstero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idComuneEstero: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idComuneEstero");
		
	    return pk;
	}
	
	
}
