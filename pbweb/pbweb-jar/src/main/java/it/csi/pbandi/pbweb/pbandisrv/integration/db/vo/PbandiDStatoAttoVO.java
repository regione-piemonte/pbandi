/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDStatoAttoVO extends GenericVO {

  	
  	private String descStatoAtto;
  	
  	private Date dtFineValidita;
  	
  	private String descBreveStatoAtto;
  	
  	private BigDecimal idStatoAtto;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDStatoAttoVO() {}
  	
	public PbandiDStatoAttoVO (BigDecimal idStatoAtto) {
	
		this. idStatoAtto =  idStatoAtto;
	}
  	
	public PbandiDStatoAttoVO (String descStatoAtto, Date dtFineValidita, String descBreveStatoAtto, BigDecimal idStatoAtto, Date dtInizioValidita) {
	
		this. descStatoAtto =  descStatoAtto;
		this. dtFineValidita =  dtFineValidita;
		this. descBreveStatoAtto =  descBreveStatoAtto;
		this. idStatoAtto =  idStatoAtto;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getDescStatoAtto() {
		return descStatoAtto;
	}
	
	public void setDescStatoAtto(String descStatoAtto) {
		this.descStatoAtto = descStatoAtto;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveStatoAtto() {
		return descBreveStatoAtto;
	}
	
	public void setDescBreveStatoAtto(String descBreveStatoAtto) {
		this.descBreveStatoAtto = descBreveStatoAtto;
	}
	
	public BigDecimal getIdStatoAtto() {
		return idStatoAtto;
	}
	
	public void setIdStatoAtto(BigDecimal idStatoAtto) {
		this.idStatoAtto = idStatoAtto;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descStatoAtto != null && descBreveStatoAtto != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStatoAtto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descStatoAtto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStatoAtto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveStatoAtto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveStatoAtto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoAtto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoAtto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idStatoAtto");
		
	    return pk;
	}
	
	
}
