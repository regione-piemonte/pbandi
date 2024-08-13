/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRFornitoreAppaltoVO extends GenericVO {

  	
  	
  	private BigDecimal idAppalto;
  	
  	private BigDecimal idFornitore;
  	
  	private BigDecimal idTipoPercettore;

  	
	public PbandiRFornitoreAppaltoVO() {}
  	
	public PbandiRFornitoreAppaltoVO (BigDecimal idAppalto, BigDecimal idFornitore, BigDecimal idTipoPercettore) {
	
		this. idAppalto =  idAppalto;
		this.idFornitore = idFornitore;
		this.idTipoPercettore = idTipoPercettore;
	}
  
  	
  	

	public BigDecimal getIdAppalto() {
		return idAppalto;
	}
	
	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}
	
	
	public BigDecimal getIdFornitore() {
		return idFornitore;
	}
	
	public void setIdFornitore(BigDecimal idFornitore) {
		this.idFornitore = idFornitore;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idAppalto != null && idFornitore != null && idTipoPercettore != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAppalto != null && idFornitore != null && idTipoPercettore != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idAppalto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAppalto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFornitore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFornitore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoPercettore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoPercettore: " + temp + "\t\n");
	      
	    return sb.toString();
	}

	public BigDecimal getIdTipoPercettore() {
		return idTipoPercettore;
	}

	public void setIdTipoPercettore(BigDecimal idTipoPercettore) {
		this.idTipoPercettore = idTipoPercettore;
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
		pk.add("idAppalto");
		
	    return pk;
	}
	
}
