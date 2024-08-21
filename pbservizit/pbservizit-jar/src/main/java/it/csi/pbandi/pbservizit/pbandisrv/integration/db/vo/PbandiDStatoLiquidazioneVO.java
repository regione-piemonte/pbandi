/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDStatoLiquidazioneVO extends GenericVO {

  	
  	private String descBreveStatoLiquidazione;
  	
  	private Date dtFineValidita;
  	
  	private String descStatoLiquidazione;
  	
  	private BigDecimal idStatoLiquidazione;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDStatoLiquidazioneVO() {}
  	
	public PbandiDStatoLiquidazioneVO (BigDecimal idStatoLiquidazione) {
	
		this. idStatoLiquidazione =  idStatoLiquidazione;
	}
  	
	public PbandiDStatoLiquidazioneVO (String descBreveStatoLiquidazione, Date dtFineValidita, String descStatoLiquidazione, BigDecimal idStatoLiquidazione, Date dtInizioValidita) {
	
		this. descBreveStatoLiquidazione =  descBreveStatoLiquidazione;
		this. dtFineValidita =  dtFineValidita;
		this. descStatoLiquidazione =  descStatoLiquidazione;
		this. idStatoLiquidazione =  idStatoLiquidazione;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getDescBreveStatoLiquidazione() {
		return descBreveStatoLiquidazione;
	}
	
	public void setDescBreveStatoLiquidazione(String descBreveStatoLiquidazione) {
		this.descBreveStatoLiquidazione = descBreveStatoLiquidazione;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescStatoLiquidazione() {
		return descStatoLiquidazione;
	}
	
	public void setDescStatoLiquidazione(String descStatoLiquidazione) {
		this.descStatoLiquidazione = descStatoLiquidazione;
	}
	
	public BigDecimal getIdStatoLiquidazione() {
		return idStatoLiquidazione;
	}
	
	public void setIdStatoLiquidazione(BigDecimal idStatoLiquidazione) {
		this.idStatoLiquidazione = idStatoLiquidazione;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveStatoLiquidazione != null && descStatoLiquidazione != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStatoLiquidazione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveStatoLiquidazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveStatoLiquidazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descStatoLiquidazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStatoLiquidazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoLiquidazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoLiquidazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idStatoLiquidazione");
		
	    return pk;
	}
	
	
}
