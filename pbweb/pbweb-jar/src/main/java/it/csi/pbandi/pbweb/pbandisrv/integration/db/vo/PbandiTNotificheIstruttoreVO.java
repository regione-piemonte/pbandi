/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTNotificheIstruttoreVO extends GenericVO {

  	
  	private BigDecimal idSoggettoNotifica;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idNotificaAlert;
  	
  	private BigDecimal idSoggetto;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idFrequenza;
  	
	public PbandiTNotificheIstruttoreVO() {}
  	
	public PbandiTNotificheIstruttoreVO (BigDecimal idSoggettoNotifica) {
	
		this. idSoggettoNotifica =  idSoggettoNotifica;
	}
  	
	public PbandiTNotificheIstruttoreVO (BigDecimal idSoggettoNotifica, Date dtFineValidita, BigDecimal idNotificaAlert, BigDecimal idSoggetto, Date dtInizioValidita, BigDecimal idFrequenza) {
	
		this. idSoggettoNotifica =  idSoggettoNotifica;
		this. dtFineValidita =  dtFineValidita;
		this. idNotificaAlert =  idNotificaAlert;
		this. idSoggetto =  idSoggetto;
		this. dtInizioValidita =  dtInizioValidita;
		this. idFrequenza =  idFrequenza;
	}
  	
  	
	public BigDecimal getIdSoggettoNotifica() {
		return idSoggettoNotifica;
	}
	
	public void setIdSoggettoNotifica(BigDecimal idSoggettoNotifica) {
		this.idSoggettoNotifica = idSoggettoNotifica;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdNotificaAlert() {
		return idNotificaAlert;
	}
	
	public void setIdNotificaAlert(BigDecimal idNotificaAlert) {
		this.idNotificaAlert = idNotificaAlert;
	}
	
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdFrequenza() {
		return idFrequenza;
	}
	
	public void setIdFrequenza(BigDecimal idFrequenza) {
		this.idFrequenza = idFrequenza;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idNotificaAlert != null && idFrequenza != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idSoggettoNotifica != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggettoNotifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggettoNotifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNotificaAlert);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNotificaAlert: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFrequenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFrequenza: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idSoggettoNotifica");
		
	    return pk;
	}
	
	
}
