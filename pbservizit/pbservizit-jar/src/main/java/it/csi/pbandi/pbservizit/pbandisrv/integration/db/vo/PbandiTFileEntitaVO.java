/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;

import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiTFileEntitaVO extends GenericVO {

  	
	private BigDecimal idFile;
	private BigDecimal idFileEntita;	
	private BigDecimal idEntita;
	private BigDecimal idProgetto;
	private Date dtEntita;
	private String flagEntita;
	
	public Date getDtEntita() {
		return dtEntita;
	}
	public void setDtEntita(Date dtEntita) {
		this.dtEntita = dtEntita;
	}
	public String getFlagEntita() {
		return flagEntita;
	}
	public void setFlagEntita(String flagEntita) {
		this.flagEntita = flagEntita;
	}
	public BigDecimal getIdFileEntita() {
		return idFileEntita;
	}
	public void setIdFileEntita(BigDecimal idFileEntita) {
		this.idFileEntita = idFileEntita;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	private BigDecimal idTarget;
	
	public BigDecimal getIdFile() {
		return idFile;
	}
	public void setIdFile(BigDecimal idFile) {
		this.idFile = idFile;
	}
	public BigDecimal getIdEntita() {
		return idEntita;
	}
	public void setIdEntita(BigDecimal idEntita) {
		this.idEntita = idEntita;
	}
	public BigDecimal getIdTarget() {
		return idTarget;
	}
	public void setIdTarget(BigDecimal idTarget) {
		this.idTarget = idTarget;
	}


	public boolean isValid() {
                return  isPKValid() ;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idFileEntita !=null && idFile != null && idEntita!=null && idTarget!=null) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idFile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFile: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEntita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEntita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTarget);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTarget: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFileEntita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFileEntita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagEntita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagEntita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtEntita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtEntita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		pk.add("idTarget");
		pk.add("idFile");
		pk.add("idEntita");
		pk.add("idFileEntita");
	    return pk;
	}
	
	
}
