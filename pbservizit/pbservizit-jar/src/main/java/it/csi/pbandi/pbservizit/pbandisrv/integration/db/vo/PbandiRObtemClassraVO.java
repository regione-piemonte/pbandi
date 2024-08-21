/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiRObtemClassraVO extends GenericVO {

  	private BigDecimal idObiettivoTem;
  	
  	private BigDecimal idClassificazioneRa;
  	
	public PbandiRObtemClassraVO() {}
  	 	
	public PbandiRObtemClassraVO (BigDecimal idObiettivoTem, BigDecimal idClassificazioneRa) {	
		this.idObiettivoTem =  idObiettivoTem;
		this.idClassificazioneRa = idClassificazioneRa;
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
		if (idObiettivoTem != null && idClassificazioneRa != null) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idObiettivoTem);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idObiettivoTem: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idClassificazioneRa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idClassificazioneRa: " + temp + "\t\n");
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
			pk.add("idObiettivoTem");
			pk.add("idClassificazioneRa");
	    return pk;
	}

	public BigDecimal getIdObiettivoTem() {
		return idObiettivoTem;
	}

	public void setIdObiettivoTem(BigDecimal idObiettivoTem) {
		this.idObiettivoTem = idObiettivoTem;
	}

	public BigDecimal getIdClassificazioneRa() {
		return idClassificazioneRa;
	}

	public void setIdClassificazioneRa(BigDecimal idClassificazioneRa) {
		this.idClassificazioneRa = idClassificazioneRa;
	}
	
}
