/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import java.util.List;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTAccessoUppVO extends GenericVO {

  	
  	private Date dtBloccoAccesso;
  	
  	private BigDecimal idUtente;
  	
  	private Date dtPrimoAccesso;
  	
	public PbandiTAccessoUppVO() {}
  	
	public PbandiTAccessoUppVO (BigDecimal idUtente) {
	
		this. idUtente =  idUtente;
	}
  	
	public PbandiTAccessoUppVO (Date dtBloccoAccesso, BigDecimal idUtente, Date dtPrimoAccesso) {
	
		this. dtBloccoAccesso =  dtBloccoAccesso;
		this. idUtente =  idUtente;
		this. dtPrimoAccesso =  dtPrimoAccesso;
	}
  	
  	
	public Date getDtBloccoAccesso() {
		return dtBloccoAccesso;
	}
	
	public void setDtBloccoAccesso(Date dtBloccoAccesso) {
		this.dtBloccoAccesso = dtBloccoAccesso;
	}
	
	public BigDecimal getIdUtente() {
		return idUtente;
	}
	
	public void setIdUtente(BigDecimal idUtente) {
		this.idUtente = idUtente;
	}
	
	public Date getDtPrimoAccesso() {
		return dtPrimoAccesso;
	}
	
	public void setDtPrimoAccesso(Date dtPrimoAccesso) {
		this.dtPrimoAccesso = dtPrimoAccesso;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtBloccoAccesso != null && dtPrimoAccesso != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idUtente != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtBloccoAccesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtBloccoAccesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtPrimoAccesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtPrimoAccesso: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	@Override
	public List getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
		pk.add("idUtente");
	
    return pk;
	}
	
	
	
}
