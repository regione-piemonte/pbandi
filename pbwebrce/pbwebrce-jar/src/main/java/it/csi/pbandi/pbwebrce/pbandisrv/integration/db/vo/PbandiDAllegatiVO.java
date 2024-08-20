/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;

public class PbandiDAllegatiVO extends GenericVO {
	
	private BigDecimal idAllegato;
	private String descrizione;
	
	public PbandiDAllegatiVO() {};
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAllegato != null) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk = new java.util.ArrayList<String>();
		pk.add("idAllegato");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idAllegato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAllegato: " + temp + "\t\n");
	    temp = DataFilter.removeNull( descrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrizione: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdAllegato() {
		return idAllegato;
	}

	public void setIdAllegato(BigDecimal idAllegato) {
		this.idAllegato = idAllegato;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
