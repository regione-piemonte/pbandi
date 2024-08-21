/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDPermessoVO extends GenericVO {

  	
  	private String descPermesso;
  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idPermesso;
  	
  	private String descBrevePermesso;
  	
	public PbandiDPermessoVO() {}
  	
	public PbandiDPermessoVO (BigDecimal idPermesso) {
	
		this. idPermesso =  idPermesso;
	}
  	
	public PbandiDPermessoVO (String descPermesso, Date dtFineValidita, Date dtInizioValidita, BigDecimal idPermesso, String descBrevePermesso) {
	
		this. descPermesso =  descPermesso;
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioValidita =  dtInizioValidita;
		this. idPermesso =  idPermesso;
		this. descBrevePermesso =  descBrevePermesso;
	}
  	
  	
	public String getDescPermesso() {
		return descPermesso;
	}
	
	public void setDescPermesso(String descPermesso) {
		this.descPermesso = descPermesso;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdPermesso() {
		return idPermesso;
	}
	
	public void setIdPermesso(BigDecimal idPermesso) {
		this.idPermesso = idPermesso;
	}
	
	public String getDescBrevePermesso() {
		return descBrevePermesso;
	}
	
	public void setDescBrevePermesso(String descBrevePermesso) {
		this.descBrevePermesso = descBrevePermesso;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descPermesso != null && dtInizioValidita != null && descBrevePermesso != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idPermesso != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descPermesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descPermesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPermesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPermesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBrevePermesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBrevePermesso: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idPermesso");
		
	    return pk;
	}
	
	
}
