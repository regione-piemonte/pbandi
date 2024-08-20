/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRRevocaIrregVO extends GenericVO {

  	
  	private BigDecimal idIrregolarita;
  	
  	private BigDecimal quotaIrreg;
  	
  	private BigDecimal idRevoca;
  	
	public PbandiRRevocaIrregVO() {}
  	
	public PbandiRRevocaIrregVO (BigDecimal idIrregolarita, BigDecimal idRevoca) {
	
		this. idIrregolarita =  idIrregolarita;
		this. idRevoca =  idRevoca;
	}
  	
	public PbandiRRevocaIrregVO (BigDecimal idIrregolarita, BigDecimal quotaIrreg, BigDecimal idRevoca) {
	
		this. idIrregolarita =  idIrregolarita;
		this. quotaIrreg =  quotaIrreg;
		this. idRevoca =  idRevoca;
	}
  	
  	
	public BigDecimal getIdIrregolarita() {
		return idIrregolarita;
	}
	
	public void setIdIrregolarita(BigDecimal idIrregolarita) {
		this.idIrregolarita = idIrregolarita;
	}
	
	public BigDecimal getQuotaIrreg() {
		return quotaIrreg;
	}
	
	public void setQuotaIrreg(BigDecimal quotaIrreg) {
		this.quotaIrreg = quotaIrreg;
	}
	
	public BigDecimal getIdRevoca() {
		return idRevoca;
	}
	
	public void setIdRevoca(BigDecimal idRevoca) {
		this.idRevoca = idRevoca;
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
		if (idIrregolarita != null && idRevoca != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idIrregolarita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIrregolarita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( quotaIrreg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" quotaIrreg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRevoca);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRevoca: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idIrregolarita");
		
			pk.add("idRevoca");
		
	    return pk;
	}
	
	
}
