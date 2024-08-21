/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDNaturaSanzioneVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descBreveNaturaSanzione;
  	
  	private BigDecimal idNaturaSanzione;
  	
  	private Date dtInizioValidita;
  	
  	private String descNaturaSanzione;
  	
	public PbandiDNaturaSanzioneVO() {}
  	
	public PbandiDNaturaSanzioneVO (BigDecimal idNaturaSanzione) {
	
		this. idNaturaSanzione =  idNaturaSanzione;
	}
  	
	public PbandiDNaturaSanzioneVO (Date dtFineValidita, String descBreveNaturaSanzione, BigDecimal idNaturaSanzione, Date dtInizioValidita, String descNaturaSanzione) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descBreveNaturaSanzione =  descBreveNaturaSanzione;
		this. idNaturaSanzione =  idNaturaSanzione;
		this. dtInizioValidita =  dtInizioValidita;
		this. descNaturaSanzione =  descNaturaSanzione;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveNaturaSanzione() {
		return descBreveNaturaSanzione;
	}
	
	public void setDescBreveNaturaSanzione(String descBreveNaturaSanzione) {
		this.descBreveNaturaSanzione = descBreveNaturaSanzione;
	}
	
	public BigDecimal getIdNaturaSanzione() {
		return idNaturaSanzione;
	}
	
	public void setIdNaturaSanzione(BigDecimal idNaturaSanzione) {
		this.idNaturaSanzione = idNaturaSanzione;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescNaturaSanzione() {
		return descNaturaSanzione;
	}
	
	public void setDescNaturaSanzione(String descNaturaSanzione) {
		this.descNaturaSanzione = descNaturaSanzione;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveNaturaSanzione != null && dtInizioValidita != null && descNaturaSanzione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idNaturaSanzione != null ) {
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
	    
	    temp = DataFilter.removeNull( descBreveNaturaSanzione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveNaturaSanzione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNaturaSanzione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNaturaSanzione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descNaturaSanzione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descNaturaSanzione: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idNaturaSanzione");
		
	    return pk;
	}
	
	
}
