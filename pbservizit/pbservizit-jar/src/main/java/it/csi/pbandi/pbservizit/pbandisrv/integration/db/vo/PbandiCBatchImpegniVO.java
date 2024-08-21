/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import java.util.List;

import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiCBatchImpegniVO extends GenericVO {

  	
  	private BigDecimal annoEsercizio;
  	
  	private String direzione;
  	
  	private BigDecimal numeroCapitolo;
  	
	public PbandiCBatchImpegniVO() {}
  	
	
  	
	public PbandiCBatchImpegniVO (BigDecimal annoEsercizio, String direzione, BigDecimal numeroCapitolo) {
	
		this. annoEsercizio =  annoEsercizio;
		this. direzione =  direzione;
		this. numeroCapitolo =  numeroCapitolo;
	}
  	
  	
	public BigDecimal getAnnoEsercizio() {
		return annoEsercizio;
	}
	
	public void setAnnoEsercizio(BigDecimal annoEsercizio) {
		this.annoEsercizio = annoEsercizio;
	}
	
	public String getDirezione() {
		return direzione;
	}
	
	public void setDirezione(String direzione) {
		this.direzione = direzione;
	}
	
	public BigDecimal getNumeroCapitolo() {
		return numeroCapitolo;
	}
	
	public void setNumeroCapitolo(BigDecimal numeroCapitolo) {
		this.numeroCapitolo = numeroCapitolo;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && annoEsercizio != null && direzione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = true;

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( annoEsercizio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annoEsercizio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( direzione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" direzione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroCapitolo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroCapitolo: " + temp + "\t\n");
	    
	    return sb.toString();
	}



	@Override
	public List getPK() {
		
		return null;
	}
	
	
	
}
