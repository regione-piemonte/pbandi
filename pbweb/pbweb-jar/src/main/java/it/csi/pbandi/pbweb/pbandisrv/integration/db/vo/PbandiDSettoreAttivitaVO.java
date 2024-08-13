/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDSettoreAttivitaVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String codSettore;
  	
  	private BigDecimal idSettoreAttivita;
  	
  	private Date dtInizioValidita;
  	
  	private String descSettore;
  	
	public PbandiDSettoreAttivitaVO() {}
  	
	public PbandiDSettoreAttivitaVO (BigDecimal idSettoreAttivita) {
	
		this. idSettoreAttivita =  idSettoreAttivita;
	}
  	
	public PbandiDSettoreAttivitaVO (Date dtFineValidita, String codSettore, BigDecimal idSettoreAttivita, Date dtInizioValidita, String descSettore) {
	
		this. dtFineValidita =  dtFineValidita;
		this. codSettore =  codSettore;
		this. idSettoreAttivita =  idSettoreAttivita;
		this. dtInizioValidita =  dtInizioValidita;
		this. descSettore =  descSettore;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getCodSettore() {
		return codSettore;
	}
	
	public void setCodSettore(String codSettore) {
		this.codSettore = codSettore;
	}
	
	public BigDecimal getIdSettoreAttivita() {
		return idSettoreAttivita;
	}
	
	public void setIdSettoreAttivita(BigDecimal idSettoreAttivita) {
		this.idSettoreAttivita = idSettoreAttivita;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescSettore() {
		return descSettore;
	}
	
	public void setDescSettore(String descSettore) {
		this.descSettore = descSettore;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codSettore != null && dtInizioValidita != null && descSettore != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idSettoreAttivita != null ) {
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
	    
	    temp = DataFilter.removeNull( codSettore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codSettore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSettoreAttivita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSettoreAttivita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descSettore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descSettore: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idSettoreAttivita");
		
	    return pk;
	}
	
	
}
