/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDMetodoIndividuazioneVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idMetodoIndividuazione;
  	
  	private Date dtInizioValidita;
  	
  	private String descMetodoInd;
  	
  	private String descBreveMetodoInd;
  	
	public PbandiDMetodoIndividuazioneVO() {}
  	
	public PbandiDMetodoIndividuazioneVO (BigDecimal idMetodoIndividuazione) {
	
		this. idMetodoIndividuazione =  idMetodoIndividuazione;
	}
  	
	public PbandiDMetodoIndividuazioneVO (Date dtFineValidita, BigDecimal idMetodoIndividuazione, Date dtInizioValidita, String descMetodoInd, String descBreveMetodoInd) {
	
		this. dtFineValidita =  dtFineValidita;
		this. idMetodoIndividuazione =  idMetodoIndividuazione;
		this. dtInizioValidita =  dtInizioValidita;
		this. descMetodoInd =  descMetodoInd;
		this. descBreveMetodoInd =  descBreveMetodoInd;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdMetodoIndividuazione() {
		return idMetodoIndividuazione;
	}
	
	public void setIdMetodoIndividuazione(BigDecimal idMetodoIndividuazione) {
		this.idMetodoIndividuazione = idMetodoIndividuazione;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescMetodoInd() {
		return descMetodoInd;
	}
	
	public void setDescMetodoInd(String descMetodoInd) {
		this.descMetodoInd = descMetodoInd;
	}
	
	public String getDescBreveMetodoInd() {
		return descBreveMetodoInd;
	}
	
	public void setDescBreveMetodoInd(String descBreveMetodoInd) {
		this.descBreveMetodoInd = descBreveMetodoInd;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && descMetodoInd != null && descBreveMetodoInd != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idMetodoIndividuazione != null ) {
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
	    
	    temp = DataFilter.removeNull( idMetodoIndividuazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMetodoIndividuazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descMetodoInd);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descMetodoInd: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveMetodoInd);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveMetodoInd: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idMetodoIndividuazione");
		
	    return pk;
	}
	
	
}
