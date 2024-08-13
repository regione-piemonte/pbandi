/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDNormativaAffidamentoVO extends GenericVO {
  	
  	private BigDecimal idNorma;
  	
  	private String descNorma;
  	
	public PbandiDNormativaAffidamentoVO() {}
  	
	public PbandiDNormativaAffidamentoVO (BigDecimal idNorma) {
		this.idNorma =  idNorma;
	}
  	
	public PbandiDNormativaAffidamentoVO (BigDecimal idNorma, String descNorma) {
		this. idNorma =  idNorma;
		this. descNorma =  descNorma;
	}
	
	public BigDecimal getIdNorma() {
		return idNorma;
	}

	public void setIdNorma(BigDecimal idNorma) {
		this.idNorma = idNorma;
	}

	public String getDescNorma() {
		return descNorma;
	}

	public void setDescNorma(String descNorma) {
		this.descNorma = descNorma;
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
		if (idNorma != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idNorma);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNorma: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descNorma);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descNorma: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();		
			pk.add("idNorma");		
	    return pk;
	}
	
	
}
