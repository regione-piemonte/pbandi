/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDIndicatoreQsnVO extends GenericVO {

  	
  	private BigDecimal idPrioritaQsn;
  	
  	private String descIndicatoreQsn;
  	
  	private String codIgrueT12;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idIndicatoreQsn;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDIndicatoreQsnVO() {}
  	
	public PbandiDIndicatoreQsnVO (BigDecimal idIndicatoreQsn) {
	
		this. idIndicatoreQsn =  idIndicatoreQsn;
	}
  	
	public PbandiDIndicatoreQsnVO (BigDecimal idPrioritaQsn, String descIndicatoreQsn, String codIgrueT12, Date dtFineValidita, BigDecimal idIndicatoreQsn, Date dtInizioValidita) {
	
		this. idPrioritaQsn =  idPrioritaQsn;
		this. descIndicatoreQsn =  descIndicatoreQsn;
		this. codIgrueT12 =  codIgrueT12;
		this. dtFineValidita =  dtFineValidita;
		this. idIndicatoreQsn =  idIndicatoreQsn;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public BigDecimal getIdPrioritaQsn() {
		return idPrioritaQsn;
	}
	
	public void setIdPrioritaQsn(BigDecimal idPrioritaQsn) {
		this.idPrioritaQsn = idPrioritaQsn;
	}
	
	public String getDescIndicatoreQsn() {
		return descIndicatoreQsn;
	}
	
	public void setDescIndicatoreQsn(String descIndicatoreQsn) {
		this.descIndicatoreQsn = descIndicatoreQsn;
	}
	
	public String getCodIgrueT12() {
		return codIgrueT12;
	}

	public void setCodIgrueT12(String codIgrueT12) {
		this.codIgrueT12 = codIgrueT12;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdIndicatoreQsn() {
		return idIndicatoreQsn;
	}
	
	public void setIdIndicatoreQsn(BigDecimal idIndicatoreQsn) {
		this.idIndicatoreQsn = idIndicatoreQsn;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idPrioritaQsn != null && descIndicatoreQsn != null && codIgrueT12 != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idIndicatoreQsn != null ) {
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
	    
	    temp = DataFilter.removeNull( descIndicatoreQsn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descIndicatoreQsn: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT12);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT12: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndicatoreQsn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndicatoreQsn: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idIndicatoreQsn");
		
	    return pk;
	}
	
	
}
