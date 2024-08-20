/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDStatoFinanziarioVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descStatoFinanziario;
  	
  	private String descBreveStatoFinanziario;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idStatoFinanziario;
  	
	public PbandiDStatoFinanziarioVO() {}
  	
	public PbandiDStatoFinanziarioVO (BigDecimal idStatoFinanziario) {
	
		this. idStatoFinanziario =  idStatoFinanziario;
	}
  	
	public PbandiDStatoFinanziarioVO (Date dtFineValidita, String descStatoFinanziario, String descBreveStatoFinanziario, Date dtInizioValidita, BigDecimal idStatoFinanziario) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descStatoFinanziario =  descStatoFinanziario;
		this. descBreveStatoFinanziario =  descBreveStatoFinanziario;
		this. dtInizioValidita =  dtInizioValidita;
		this. idStatoFinanziario =  idStatoFinanziario;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescStatoFinanziario() {
		return descStatoFinanziario;
	}
	
	public void setDescStatoFinanziario(String descStatoFinanziario) {
		this.descStatoFinanziario = descStatoFinanziario;
	}
	
	public String getDescBreveStatoFinanziario() {
		return descBreveStatoFinanziario;
	}
	
	public void setDescBreveStatoFinanziario(String descBreveStatoFinanziario) {
		this.descBreveStatoFinanziario = descBreveStatoFinanziario;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdStatoFinanziario() {
		return idStatoFinanziario;
	}
	
	public void setIdStatoFinanziario(BigDecimal idStatoFinanziario) {
		this.idStatoFinanziario = idStatoFinanziario;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descStatoFinanziario != null && descBreveStatoFinanziario != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStatoFinanziario != null ) {
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
	    
	    temp = DataFilter.removeNull( descStatoFinanziario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStatoFinanziario: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveStatoFinanziario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveStatoFinanziario: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoFinanziario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoFinanziario: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idStatoFinanziario");
		
	    return pk;
	}
	
	
}
