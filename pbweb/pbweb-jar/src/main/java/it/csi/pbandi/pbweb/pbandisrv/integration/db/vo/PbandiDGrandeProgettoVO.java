/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDGrandeProgettoVO extends GenericVO {

  	
  	private String codGrandeProgetto;
  	
  	private String codProgramma;
  	
  	private BigDecimal idGrandeProgetto;
  	
  	private String descGrandeProgetto;
  	
	public PbandiDGrandeProgettoVO() {}
  	
	public PbandiDGrandeProgettoVO (BigDecimal idGrandeProgetto) {
	
		this. idGrandeProgetto =  idGrandeProgetto;
	}
  	
	public PbandiDGrandeProgettoVO (String codGrandeProgetto, String codProgramma, BigDecimal idGrandeProgetto, String descGrandeProgetto) {
	
		this. codGrandeProgetto =  codGrandeProgetto;
		this. codProgramma =  codProgramma;
		this. idGrandeProgetto =  idGrandeProgetto;
		this. descGrandeProgetto =  descGrandeProgetto;
	}
  	
  	
	public String getCodGrandeProgetto() {
		return codGrandeProgetto;
	}
	
	public void setCodGrandeProgetto(String codGrandeProgetto) {
		this.codGrandeProgetto = codGrandeProgetto;
	}
	
	public String getCodProgramma() {
		return codProgramma;
	}
	
	public void setCodProgramma(String codProgramma) {
		this.codProgramma = codProgramma;
	}
	
	public BigDecimal getIdGrandeProgetto() {
		return idGrandeProgetto;
	}
	
	public void setIdGrandeProgetto(BigDecimal idGrandeProgetto) {
		this.idGrandeProgetto = idGrandeProgetto;
	}
	
	public String getDescGrandeProgetto() {
		return descGrandeProgetto;
	}
	
	public void setDescGrandeProgetto(String descGrandeProgetto) {
		this.descGrandeProgetto = descGrandeProgetto;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idGrandeProgetto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( codGrandeProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codGrandeProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codProgramma);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codProgramma: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idGrandeProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idGrandeProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descGrandeProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descGrandeProgetto: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idGrandeProgetto");
		
	    return pk;
	}
	
	
}
