/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

public class PbandiDTipologieVariantiVO extends GenericVO {
  	  	
  	private BigDecimal idTipologiaVariante;
  	
  	private String descrizione;
  	
	public PbandiDTipologieVariantiVO() {}
  	
	public PbandiDTipologieVariantiVO (BigDecimal idTipologiaVariante) {	
		this. idTipologiaVariante =  idTipologiaVariante;
	}
  	
	public PbandiDTipologieVariantiVO (BigDecimal idTipologiaVariante, String descrizione) {
		this. idTipologiaVariante =  idTipologiaVariante;
		this. descrizione =  descrizione;
	}
	
	public BigDecimal getIdTipologiaVariante() {
		return idTipologiaVariante;
	}

	public void setIdTipologiaVariante(BigDecimal idTipologiaVariante) {
		this.idTipologiaVariante = idTipologiaVariante;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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
		if (idTipologiaVariante != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipologiaVariante);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaVariante: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrizione: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		pk.add("idTipologiaVariante");
	    return pk;
	}
	
	
}
