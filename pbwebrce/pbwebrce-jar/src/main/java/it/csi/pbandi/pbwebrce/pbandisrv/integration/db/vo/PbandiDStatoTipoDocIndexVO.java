/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDStatoTipoDocIndexVO extends GenericVO {

  	
  	private String descBreveStatoTipDocIndex;
  	
  	private String descStatoTipoDocIndex;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idStatoTipoDocIndex;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDStatoTipoDocIndexVO() {}
  	
	public PbandiDStatoTipoDocIndexVO (BigDecimal idStatoTipoDocIndex) {
	
		this. idStatoTipoDocIndex =  idStatoTipoDocIndex;
	}
  	
	public PbandiDStatoTipoDocIndexVO (String descBreveStatoTipDocIndex, String descStatoTipoDocIndex, Date dtFineValidita, BigDecimal idStatoTipoDocIndex, Date dtInizioValidita) {
	
		this. descBreveStatoTipDocIndex =  descBreveStatoTipDocIndex;
		this. descStatoTipoDocIndex =  descStatoTipoDocIndex;
		this. dtFineValidita =  dtFineValidita;
		this. idStatoTipoDocIndex =  idStatoTipoDocIndex;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getDescBreveStatoTipDocIndex() {
		return descBreveStatoTipDocIndex;
	}
	
	public void setDescBreveStatoTipDocIndex(String descBreveStatoTipDocIndex) {
		this.descBreveStatoTipDocIndex = descBreveStatoTipDocIndex;
	}
	
	public String getDescStatoTipoDocIndex() {
		return descStatoTipoDocIndex;
	}
	
	public void setDescStatoTipoDocIndex(String descStatoTipoDocIndex) {
		this.descStatoTipoDocIndex = descStatoTipoDocIndex;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdStatoTipoDocIndex() {
		return idStatoTipoDocIndex;
	}
	
	public void setIdStatoTipoDocIndex(BigDecimal idStatoTipoDocIndex) {
		this.idStatoTipoDocIndex = idStatoTipoDocIndex;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveStatoTipDocIndex != null && descStatoTipoDocIndex != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStatoTipoDocIndex != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveStatoTipDocIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveStatoTipDocIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descStatoTipoDocIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStatoTipoDocIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoTipoDocIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoTipoDocIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idStatoTipoDocIndex");
		
	    return pk;
	}
	
	
}
