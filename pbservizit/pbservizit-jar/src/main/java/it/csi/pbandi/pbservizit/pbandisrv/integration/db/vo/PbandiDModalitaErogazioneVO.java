/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDModalitaErogazioneVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idModalitaErogazione;
  	
  	private String descModalitaErogazione;
  	
  	private Date dtInizioValidita;
  	
  	private String descBreveModalitaErogazione;
  	
  	private String flagBilancio;
  	
	public PbandiDModalitaErogazioneVO() {}
  	
	public PbandiDModalitaErogazioneVO (BigDecimal idModalitaErogazione) {
	
		this. idModalitaErogazione =  idModalitaErogazione;
	}
  	
	public PbandiDModalitaErogazioneVO (Date dtFineValidita, BigDecimal idModalitaErogazione, String descModalitaErogazione, Date dtInizioValidita, String descBreveModalitaErogazione ,String flagBilancio) {
	
		this. dtFineValidita =  dtFineValidita;
		this. idModalitaErogazione =  idModalitaErogazione;
		this. descModalitaErogazione =  descModalitaErogazione;
		this. dtInizioValidita =  dtInizioValidita;
		this. descBreveModalitaErogazione =  descBreveModalitaErogazione;
		this. flagBilancio =  flagBilancio;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdModalitaErogazione() {
		return idModalitaErogazione;
	}
	
	public void setIdModalitaErogazione(BigDecimal idModalitaErogazione) {
		this.idModalitaErogazione = idModalitaErogazione;
	}
	
	public String getDescModalitaErogazione() {
		return descModalitaErogazione;
	}
	
	public void setDescModalitaErogazione(String descModalitaErogazione) {
		this.descModalitaErogazione = descModalitaErogazione;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescBreveModalitaErogazione() {
		return descBreveModalitaErogazione;
	}
	
	public void setDescBreveModalitaErogazione(String descBreveModalitaErogazione) {
		this.descBreveModalitaErogazione = descBreveModalitaErogazione;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descModalitaErogazione != null && dtInizioValidita != null && descBreveModalitaErogazione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idModalitaErogazione != null ) {
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
	    
	    temp = DataFilter.removeNull( idModalitaErogazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idModalitaErogazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descModalitaErogazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descModalitaErogazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveModalitaErogazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveModalitaErogazione: " + temp + "\t\n");

	    temp = DataFilter.removeNull( flagBilancio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagBilancio: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idModalitaErogazione");
		
	    return pk;
	}

	public void setFlagBilancio(String flagBilancio) {
		this.flagBilancio = flagBilancio;
	}

	public String getFlagBilancio() {
		return flagBilancio;
	}
	
	
}
