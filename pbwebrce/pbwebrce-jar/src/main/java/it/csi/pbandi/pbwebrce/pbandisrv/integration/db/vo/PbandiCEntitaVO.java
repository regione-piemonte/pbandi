/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiCEntitaVO extends GenericVO {

  	
  	private BigDecimal idEntita;
  	
  	private String nomeEntita;
  	
  	private String flagDaTracciare;
  	
	public PbandiCEntitaVO() {}
  	
	public PbandiCEntitaVO (BigDecimal idEntita) {
	
		this. idEntita =  idEntita;
	}
  	
	public PbandiCEntitaVO (BigDecimal idEntita, String nomeEntita, String flagDaTracciare) {
	
		this. idEntita =  idEntita;
		this. nomeEntita =  nomeEntita;
		this. flagDaTracciare =  flagDaTracciare;
	}
  	
  	
	public BigDecimal getIdEntita() {
		return idEntita;
	}
	
	public void setIdEntita(BigDecimal idEntita) {
		this.idEntita = idEntita;
	}
	
	public String getNomeEntita() {
		return nomeEntita;
	}
	
	public void setNomeEntita(String nomeEntita) {
		this.nomeEntita = nomeEntita;
	}
	
	public String getFlagDaTracciare() {
		return flagDaTracciare;
	}
	
	public void setFlagDaTracciare(String flagDaTracciare) {
		this.flagDaTracciare = flagDaTracciare;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && nomeEntita != null && flagDaTracciare != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idEntita != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idEntita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEntita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeEntita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeEntita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagDaTracciare);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagDaTracciare: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idEntita");
		
	    return pk;
	}
	
	
}
