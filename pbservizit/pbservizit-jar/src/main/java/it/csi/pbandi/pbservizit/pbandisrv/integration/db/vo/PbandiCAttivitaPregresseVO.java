/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiCAttivitaPregresseVO extends GenericVO {

  	
  	private String codElemento;
  	
  	private String nomeColonna;
  	
  	private String nomeEtichetta;
  	
	public PbandiCAttivitaPregresseVO() {}
  	
	public PbandiCAttivitaPregresseVO (String codElemento) {
		this. codElemento =  codElemento;
	}
  	
	public PbandiCAttivitaPregresseVO (String codElemento, String nomeColonna, String nomeEtichetta) {
	
		this. codElemento =  codElemento;
		this. nomeColonna =  nomeColonna;
		this. nomeEtichetta =  nomeEtichetta;
	}
  	
  	
	public String getCodElemento() {
		return codElemento;
	}
	
	public void setCodElemento(String codElemento) {
		this.codElemento = codElemento;
	}
	
	public String getNomeColonna() {
		return nomeColonna;
	}
	
	public void setNomeColonna(String nomeColonna) {
		this.nomeColonna = nomeColonna;
	}
	
	public String getNomeEtichetta() {
		return nomeEtichetta;
	}
	
	public void setNomeEtichetta(String nomeEtichetta) {
		this.nomeEtichetta = nomeEtichetta;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codElemento != null && nomeColonna != null && nomeEtichetta != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (codElemento != null) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( codElemento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codElemento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeColonna);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeColonna: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeEtichetta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeEtichetta: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		pk.add("codElemento");
	    return pk;
	}
	
	
}
