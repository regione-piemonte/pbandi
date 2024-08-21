/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDClassificazioneRaVO extends GenericVO {

  	
  	private String codClassificazioneRa;
  	
  	private BigDecimal idClassificazioneRa;
  	
  	private String descrClassificazioneRa;
  	
	public PbandiDClassificazioneRaVO() {}
  	
	public PbandiDClassificazioneRaVO (BigDecimal idClassificazioneRa) {
	
		this. idClassificazioneRa =  idClassificazioneRa;
	}
  	
	public PbandiDClassificazioneRaVO (String codClassificazioneRa, BigDecimal idClassificazioneRa, String descrClassificazioneRa) {
	
		this. codClassificazioneRa =  codClassificazioneRa;
		this. idClassificazioneRa =  idClassificazioneRa;
		this. descrClassificazioneRa =  descrClassificazioneRa;
	}
  	
  	
	public String getCodClassificazioneRa() {
		return codClassificazioneRa;
	}
	
	public void setCodClassificazioneRa(String codClassificazioneRa) {
		this.codClassificazioneRa = codClassificazioneRa;
	}
	
	public BigDecimal getIdClassificazioneRa() {
		return idClassificazioneRa;
	}
	
	public void setIdClassificazioneRa(BigDecimal idClassificazioneRa) {
		this.idClassificazioneRa = idClassificazioneRa;
	}
	
	public String getDescrClassificazioneRa() {
		return descrClassificazioneRa;
	}
	
	public void setDescrClassificazioneRa(String descrClassificazioneRa) {
		this.descrClassificazioneRa = descrClassificazioneRa;
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
		if (idClassificazioneRa != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( codClassificazioneRa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codClassificazioneRa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idClassificazioneRa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idClassificazioneRa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descrClassificazioneRa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrClassificazioneRa: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idClassificazioneRa");
		
	    return pk;
	}
	
	
}
