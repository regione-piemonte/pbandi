/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDSettoreCipeVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descSettoreCipe;
  	
  	private Date dtInizioValidita;
  	
  	private String codSettoreCipe;
  	
  	private BigDecimal idSettoreCipe;
  	
	public PbandiDSettoreCipeVO() {}
  	
	public PbandiDSettoreCipeVO (BigDecimal idSettoreCipe) {
	
		this. idSettoreCipe =  idSettoreCipe;
	}
  	
	public PbandiDSettoreCipeVO (Date dtFineValidita, String descSettoreCipe, Date dtInizioValidita, String codSettoreCipe, BigDecimal idSettoreCipe) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descSettoreCipe =  descSettoreCipe;
		this. dtInizioValidita =  dtInizioValidita;
		this. codSettoreCipe =  codSettoreCipe;
		this. idSettoreCipe =  idSettoreCipe;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescSettoreCipe() {
		return descSettoreCipe;
	}
	
	public void setDescSettoreCipe(String descSettoreCipe) {
		this.descSettoreCipe = descSettoreCipe;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getCodSettoreCipe() {
		return codSettoreCipe;
	}
	
	public void setCodSettoreCipe(String codSettoreCipe) {
		this.codSettoreCipe = codSettoreCipe;
	}
	
	public BigDecimal getIdSettoreCipe() {
		return idSettoreCipe;
	}
	
	public void setIdSettoreCipe(BigDecimal idSettoreCipe) {
		this.idSettoreCipe = idSettoreCipe;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descSettoreCipe != null && dtInizioValidita != null && codSettoreCipe != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idSettoreCipe != null ) {
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
	    
	    temp = DataFilter.removeNull( descSettoreCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descSettoreCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codSettoreCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codSettoreCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSettoreCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSettoreCipe: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idSettoreCipe");
		
	    return pk;
	}
	
	
}
