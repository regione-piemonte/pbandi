/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDSistemaProtocolloVO extends GenericVO {

  	
  	private String descrizione;
  	
  	private BigDecimal idSistemaProt;
  	
	public PbandiDSistemaProtocolloVO() {}
  	
	public PbandiDSistemaProtocolloVO (BigDecimal idSistemaProt) {
	
		this. idSistemaProt =  idSistemaProt;
	}
  	
	public PbandiDSistemaProtocolloVO (String descrizione, BigDecimal idSistemaProt) {
	
		this. descrizione =  descrizione;
		this. idSistemaProt =  idSistemaProt;
	}
  	
  	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public BigDecimal getIdSistemaProt() {
		return idSistemaProt;
	}
	
	public void setIdSistemaProt(BigDecimal idSistemaProt) {
		this.idSistemaProt = idSistemaProt;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descrizione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idSistemaProt != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrizione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSistemaProt);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSistemaProt: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idSistemaProt");
		
	    return pk;
	}
	
	
}
