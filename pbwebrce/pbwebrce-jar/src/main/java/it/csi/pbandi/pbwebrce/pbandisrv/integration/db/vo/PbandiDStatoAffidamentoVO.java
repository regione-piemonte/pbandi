/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDStatoAffidamentoVO extends GenericVO {

  	private BigDecimal idStatoAffidamento;
  	
  	private String descStatoAffidamento;
  	
  	private String descBreveStatoAffidamento;

	public PbandiDStatoAffidamentoVO() {}
  	
	public PbandiDStatoAffidamentoVO (BigDecimal idStatoAffidamento) {
		this.idStatoAffidamento = idStatoAffidamento;
	}
  	
	public PbandiDStatoAffidamentoVO (BigDecimal idStatoAffidamento,String descStatoAffidamento,String descBreveStatoAffidamento) {
		this.idStatoAffidamento = idStatoAffidamento;
		this.descStatoAffidamento = descStatoAffidamento;
		this.descBreveStatoAffidamento = descBreveStatoAffidamento;
	}
  		
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStatoAffidamento != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoAffidamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoAffidamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descStatoAffidamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStatoAffidamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveStatoAffidamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveStatoAffidamento: " + temp + "\t\n");

	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();	
		pk.add("idStatoAffidamento");
	    return pk;
	}

	public BigDecimal getIdStatoAffidamento() {
		return idStatoAffidamento;
	}

	public void setIdStatoAffidamento(BigDecimal idStatoAffidamento) {
		this.idStatoAffidamento = idStatoAffidamento;
	}

	public String getDescStatoAffidamento() {
		return descStatoAffidamento;
	}

	public void setDescStatoAffidamento(String descStatoAffidamento) {
		this.descStatoAffidamento = descStatoAffidamento;
	}

	public String getDescBreveStatoAffidamento() {
		return descBreveStatoAffidamento;
	}

	public void setDescBreveStatoAffidamento(String descBreveStatoAffidamento) {
		this.descBreveStatoAffidamento = descBreveStatoAffidamento;
	}
	
}
