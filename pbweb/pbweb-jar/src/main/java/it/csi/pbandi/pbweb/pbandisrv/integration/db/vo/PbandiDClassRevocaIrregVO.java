/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

public class PbandiDClassRevocaIrregVO extends GenericVO {
  	
	private BigDecimal idClassRevocaIrreg;  	
	private String descrizione;
  	
	public PbandiDClassRevocaIrregVO() {}
  	
	public PbandiDClassRevocaIrregVO (BigDecimal idClassRevocaIrreg, String descrizione) {
		this. idClassRevocaIrreg =  idClassRevocaIrreg;
		this. descrizione =  descrizione;
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
		if (idClassRevocaIrreg != null) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();		
			pk.add("idClassRevocaIrreg");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idClassRevocaIrreg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idClassRevocaIrreg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrizione: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdClassRevocaIrreg() {
		return idClassRevocaIrreg;
	}

	public void setIdClassRevocaIrreg(BigDecimal idClassRevocaIrreg) {
		this.idClassRevocaIrreg = idClassRevocaIrreg;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
