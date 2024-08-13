/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

public class PbandiCSchemaFattElettVO extends GenericVO {
  	
	private BigDecimal idSchemaFattElett;
	private String descrizione;
	private String descrBreve;
  	
	public PbandiCSchemaFattElettVO() {}
  	
	public PbandiCSchemaFattElettVO (BigDecimal idSchemaFattElett, String descrizione, String descrBreve) {
		this.idSchemaFattElett =  idSchemaFattElett;
		this.descrizione =  descrizione;
		this.descrBreve =  descrBreve;
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
		if (idSchemaFattElett != null) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();		
			pk.add("idSchemaFattElett");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idSchemaFattElett);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSchemaFattElett: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrizione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descrBreve);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrBreve: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdSchemaFattElett() {
		return idSchemaFattElett;
	}

	public void setIdSchemaFattElett(BigDecimal idSchemaFattElett) {
		this.idSchemaFattElett = idSchemaFattElett;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrBreve() {
		return descrBreve;
	}

	public void setDescrBreve(String descrBreve) {
		this.descrBreve = descrBreve;
	}

}
