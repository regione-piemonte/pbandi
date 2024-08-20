/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDTipologiaEnteVO extends GenericVO {

  	
  	private String descBreveTipologiaEnte;
  	
  	private Date dtFineValidita;
  	
  	private String descTipologiaEnte;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idTipologiaEnte;
  	
	public PbandiDTipologiaEnteVO() {}
  	
	public PbandiDTipologiaEnteVO (BigDecimal idTipologiaEnte) {
	
		this. idTipologiaEnte =  idTipologiaEnte;
	}
  	
	public PbandiDTipologiaEnteVO (String descBreveTipologiaEnte, Date dtFineValidita, String descTipologiaEnte, Date dtInizioValidita, BigDecimal idTipologiaEnte) {
	
		this. descBreveTipologiaEnte =  descBreveTipologiaEnte;
		this. dtFineValidita =  dtFineValidita;
		this. descTipologiaEnte =  descTipologiaEnte;
		this. dtInizioValidita =  dtInizioValidita;
		this. idTipologiaEnte =  idTipologiaEnte;
	}
  	
  	
	public String getDescBreveTipologiaEnte() {
		return descBreveTipologiaEnte;
	}
	
	public void setDescBreveTipologiaEnte(String descBreveTipologiaEnte) {
		this.descBreveTipologiaEnte = descBreveTipologiaEnte;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescTipologiaEnte() {
		return descTipologiaEnte;
	}
	
	public void setDescTipologiaEnte(String descTipologiaEnte) {
		this.descTipologiaEnte = descTipologiaEnte;
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
                if (isPKValid() && descBreveTipologiaEnte != null && descTipologiaEnte != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipologiaEnte != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipologiaEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipologiaEnte: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipologiaEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipologiaEnte: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipologiaEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaEnte: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipologiaEnte");
		
	    return pk;
	}
	
	
}
