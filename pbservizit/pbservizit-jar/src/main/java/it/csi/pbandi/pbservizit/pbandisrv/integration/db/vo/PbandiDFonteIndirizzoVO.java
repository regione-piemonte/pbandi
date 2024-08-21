/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDFonteIndirizzoVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idFonteIndirizzo;
  	
  	private String descBreveFonteIndirizzo;
  	
  	private Date dtInizioValidita;
  	
  	private String descFonteIndirizzo;
  	
	public PbandiDFonteIndirizzoVO() {}
  	
	public PbandiDFonteIndirizzoVO (BigDecimal idFonteIndirizzo) {
	
		this. idFonteIndirizzo =  idFonteIndirizzo;
	}
  	
	public PbandiDFonteIndirizzoVO (Date dtFineValidita, BigDecimal idFonteIndirizzo, String descBreveFonteIndirizzo, Date dtInizioValidita, String descFonteIndirizzo) {
	
		this. dtFineValidita =  dtFineValidita;
		this. idFonteIndirizzo =  idFonteIndirizzo;
		this. descBreveFonteIndirizzo =  descBreveFonteIndirizzo;
		this. dtInizioValidita =  dtInizioValidita;
		this. descFonteIndirizzo =  descFonteIndirizzo;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdFonteIndirizzo() {
		return idFonteIndirizzo;
	}
	
	public void setIdFonteIndirizzo(BigDecimal idFonteIndirizzo) {
		this.idFonteIndirizzo = idFonteIndirizzo;
	}
	
	public String getDescBreveFonteIndirizzo() {
		return descBreveFonteIndirizzo;
	}
	
	public void setDescBreveFonteIndirizzo(String descBreveFonteIndirizzo) {
		this.descBreveFonteIndirizzo = descBreveFonteIndirizzo;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescFonteIndirizzo() {
		return descFonteIndirizzo;
	}
	
	public void setDescFonteIndirizzo(String descFonteIndirizzo) {
		this.descFonteIndirizzo = descFonteIndirizzo;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveFonteIndirizzo != null && dtInizioValidita != null && descFonteIndirizzo != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idFonteIndirizzo != null ) {
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
	    
	    temp = DataFilter.removeNull( idFonteIndirizzo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFonteIndirizzo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveFonteIndirizzo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveFonteIndirizzo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descFonteIndirizzo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descFonteIndirizzo: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idFonteIndirizzo");
		
	    return pk;
	}
	
	
}
