/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDTipologiaAppaltoVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descBreveAppalto;
  	
  	private BigDecimal idTipologiaAppalto;
  	
  	private String descTipologiaAppalto;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDTipologiaAppaltoVO() {}
  	
	public PbandiDTipologiaAppaltoVO (BigDecimal idTipologiaAppalto) {
	
		this. idTipologiaAppalto =  idTipologiaAppalto;
	}
  	
	public PbandiDTipologiaAppaltoVO (Date dtFineValidita, String descBreveAppalto, BigDecimal idTipologiaAppalto, String descTipologiaAppalto, Date dtInizioValidita) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descBreveAppalto =  descBreveAppalto;
		this. idTipologiaAppalto =  idTipologiaAppalto;
		this. descTipologiaAppalto =  descTipologiaAppalto;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveAppalto() {
		return descBreveAppalto;
	}
	
	public void setDescBreveAppalto(String descBreveAppalto) {
		this.descBreveAppalto = descBreveAppalto;
	}
	
	public BigDecimal getIdTipologiaAppalto() {
		return idTipologiaAppalto;
	}
	
	public void setIdTipologiaAppalto(BigDecimal idTipologiaAppalto) {
		this.idTipologiaAppalto = idTipologiaAppalto;
	}
	
	public String getDescTipologiaAppalto() {
		return descTipologiaAppalto;
	}
	
	public void setDescTipologiaAppalto(String descTipologiaAppalto) {
		this.descTipologiaAppalto = descTipologiaAppalto;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveAppalto != null && descTipologiaAppalto != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipologiaAppalto != null ) {
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
	    
	    temp = DataFilter.removeNull( descBreveAppalto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveAppalto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipologiaAppalto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaAppalto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipologiaAppalto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipologiaAppalto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idTipologiaAppalto");
		
	    return pk;
	}
	
	
}
