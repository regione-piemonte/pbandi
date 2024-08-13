/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDClassificazioneEnteVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descClassificazioneEnte;
  	
  	private String descBreveClassificazEnte;
  	
  	private BigDecimal idClassificazioneEnte;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idTipologiaEnte;
  	
	public PbandiDClassificazioneEnteVO() {}
  	
	public PbandiDClassificazioneEnteVO (BigDecimal idClassificazioneEnte) {
	
		this. idClassificazioneEnte =  idClassificazioneEnte;
	}
  	
	public PbandiDClassificazioneEnteVO (Date dtFineValidita, String descClassificazioneEnte, String descBreveClassificazEnte, BigDecimal idClassificazioneEnte, Date dtInizioValidita, BigDecimal idTipologiaEnte) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descClassificazioneEnte =  descClassificazioneEnte;
		this. descBreveClassificazEnte =  descBreveClassificazEnte;
		this. idClassificazioneEnte =  idClassificazioneEnte;
		this. dtInizioValidita =  dtInizioValidita;
		this. idTipologiaEnte =  idTipologiaEnte;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescClassificazioneEnte() {
		return descClassificazioneEnte;
	}
	
	public void setDescClassificazioneEnte(String descClassificazioneEnte) {
		this.descClassificazioneEnte = descClassificazioneEnte;
	}
	
	public String getDescBreveClassificazEnte() {
		return descBreveClassificazEnte;
	}
	
	public void setDescBreveClassificazEnte(String descBreveClassificazEnte) {
		this.descBreveClassificazEnte = descBreveClassificazEnte;
	}
	
	public BigDecimal getIdClassificazioneEnte() {
		return idClassificazioneEnte;
	}
	
	public void setIdClassificazioneEnte(BigDecimal idClassificazioneEnte) {
		this.idClassificazioneEnte = idClassificazioneEnte;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdTipologiaEnte() {
		return idTipologiaEnte;
	}
	
	public void setIdTipologiaEnte(BigDecimal idTipologiaEnte) {
		this.idTipologiaEnte = idTipologiaEnte;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descClassificazioneEnte != null && descBreveClassificazEnte != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idClassificazioneEnte != null ) {
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
	    
	    temp = DataFilter.removeNull( descClassificazioneEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descClassificazioneEnte: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveClassificazEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveClassificazEnte: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idClassificazioneEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idClassificazioneEnte: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipologiaEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaEnte: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idClassificazioneEnte");
		
	    return pk;
	}
	
	
}
