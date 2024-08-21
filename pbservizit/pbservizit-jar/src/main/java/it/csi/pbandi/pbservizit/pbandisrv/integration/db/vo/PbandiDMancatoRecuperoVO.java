/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import java.util.List;

import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDMancatoRecuperoVO extends GenericVO {

  	
  	private BigDecimal idMancatoRecupero;

  	private String descMancatoRecupero;
  	
  	private Date dtInizioValidita;
  	
  	private Date dtFineValidita;
  	
  	
  	
  	
  	
	public PbandiDMancatoRecuperoVO() {}
  	
	public PbandiDMancatoRecuperoVO (BigDecimal idMancatoRecupero) {
	
		this. idMancatoRecupero =  idMancatoRecupero;
	}
  	
	public PbandiDMancatoRecuperoVO (Date dtFineValidita, String descMancatoRecupero, Date dtInizioValidita, BigDecimal idMancatoRecupero) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descMancatoRecupero =  descMancatoRecupero;
		this. dtInizioValidita =  dtInizioValidita;
		this. idMancatoRecupero =  idMancatoRecupero;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescMancatoRecupero() {
		return descMancatoRecupero;
	}
	
	public void setDescMancatoRecupero(String descMancatoRecupero) {
		this.descMancatoRecupero = descMancatoRecupero;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdMancatoRecupero() {
		return idMancatoRecupero;
	}
	
	public void setIdMancatoRecupero(BigDecimal idMancatoRecupero) {
		this.idMancatoRecupero = idMancatoRecupero;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descMancatoRecupero != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idMancatoRecupero != null ) {
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
	    
	    temp = DataFilter.removeNull( descMancatoRecupero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descMancatoRecupero: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idMancatoRecupero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMancatoRecupero: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	@Override
	public List getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
		pk.add("idMacroSezioneModulo");
	
    return pk;
	}
	
	
	
}
