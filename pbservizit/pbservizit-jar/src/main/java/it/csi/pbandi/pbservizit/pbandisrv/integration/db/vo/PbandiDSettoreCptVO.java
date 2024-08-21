/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDSettoreCptVO extends GenericVO {

  	
  	private String codIgrueT3;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idSettoreCpt;
  	
  	private String descSettoreCpt;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDSettoreCptVO() {}
  	
	public PbandiDSettoreCptVO (BigDecimal idSettoreCpt) {
	
		this. idSettoreCpt =  idSettoreCpt;
	}
  	
	public PbandiDSettoreCptVO (String codIgrueT3, Date dtFineValidita, BigDecimal idSettoreCpt, String descSettoreCpt, Date dtInizioValidita) {
	
		this. codIgrueT3 =  codIgrueT3;
		this. dtFineValidita =  dtFineValidita;
		this. idSettoreCpt =  idSettoreCpt;
		this. descSettoreCpt =  descSettoreCpt;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getCodIgrueT3() {
		return codIgrueT3;
	}
	
	public void setCodIgrueT3(String codIgrueT3) {
		this.codIgrueT3 = codIgrueT3;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdSettoreCpt() {
		return idSettoreCpt;
	}
	
	public void setIdSettoreCpt(BigDecimal idSettoreCpt) {
		this.idSettoreCpt = idSettoreCpt;
	}
	
	public String getDescSettoreCpt() {
		return descSettoreCpt;
	}
	
	public void setDescSettoreCpt(String descSettoreCpt) {
		this.descSettoreCpt = descSettoreCpt;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codIgrueT3 != null && descSettoreCpt != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idSettoreCpt != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT3);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT3: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSettoreCpt);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSettoreCpt: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descSettoreCpt);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descSettoreCpt: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idSettoreCpt");
		
	    return pk;
	}
	
	
}
