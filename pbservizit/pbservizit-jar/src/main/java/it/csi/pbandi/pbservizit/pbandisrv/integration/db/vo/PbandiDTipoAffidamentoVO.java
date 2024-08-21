/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoAffidamentoVO extends GenericVO {

  	
  	  	
  	private BigDecimal idTipoAffidamento;
  	
  	private String descTipoAffidamento;
  	
  	
	public PbandiDTipoAffidamentoVO() {}
  	
	public PbandiDTipoAffidamentoVO (BigDecimal idTipoAffidamento) {
	
		this. idTipoAffidamento =  idTipoAffidamento;
	}
  	
	public PbandiDTipoAffidamentoVO (BigDecimal idTipoAffidamento, String descTipoAffidamento) {

		this. idTipoAffidamento =  idTipoAffidamento;
		this. descTipoAffidamento =  descTipoAffidamento;
	}
	
	public BigDecimal getIdTipoAffidamento() {
		return idTipoAffidamento;
	}
	
	public void setIdTipoAffidamento(BigDecimal idTipoAffidamento) {
		this.idTipoAffidamento = idTipoAffidamento;
	}
	
	public String getDescTipoAffidamento() {
		return descTipoAffidamento;
	}
	
	public void setDescTipoAffidamento(String descTipoAffidamento) {
		this.descTipoAffidamento = descTipoAffidamento;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descTipoAffidamento != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoAffidamento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAffidamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAffidamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoAffidamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoAffidamento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idTipoAffidamento");
		
	    return pk;
	}
	
	
}
