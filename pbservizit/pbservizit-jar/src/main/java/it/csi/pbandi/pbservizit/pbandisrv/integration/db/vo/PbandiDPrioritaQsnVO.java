/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDPrioritaQsnVO extends GenericVO {

  	
  	private BigDecimal idPrioritaQsn;
  	
  	private BigDecimal codIgrueT12;
  	
  	private Date dtFineValidita;
  	
  	private String descPrioritaQsn;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDPrioritaQsnVO() {}
  	
	public PbandiDPrioritaQsnVO (BigDecimal idPrioritaQsn) {
	
		this. idPrioritaQsn =  idPrioritaQsn;
	}
  	
	public PbandiDPrioritaQsnVO (BigDecimal idPrioritaQsn, BigDecimal codIgrueT12, Date dtFineValidita, String descPrioritaQsn, Date dtInizioValidita) {
	
		this. idPrioritaQsn =  idPrioritaQsn;
		this. codIgrueT12 =  codIgrueT12;
		this. dtFineValidita =  dtFineValidita;
		this. descPrioritaQsn =  descPrioritaQsn;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public BigDecimal getIdPrioritaQsn() {
		return idPrioritaQsn;
	}
	
	public void setIdPrioritaQsn(BigDecimal idPrioritaQsn) {
		this.idPrioritaQsn = idPrioritaQsn;
	}
	
	public BigDecimal getCodIgrueT12() {
		return codIgrueT12;
	}
	
	public void setCodIgrueT12(BigDecimal codIgrueT12) {
		this.codIgrueT12 = codIgrueT12;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescPrioritaQsn() {
		return descPrioritaQsn;
	}
	
	public void setDescPrioritaQsn(String descPrioritaQsn) {
		this.descPrioritaQsn = descPrioritaQsn;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codIgrueT12 != null && descPrioritaQsn != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idPrioritaQsn != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idPrioritaQsn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPrioritaQsn: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT12);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT12: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descPrioritaQsn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descPrioritaQsn: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idPrioritaQsn");
		
	    return pk;
	}
	
	
}
