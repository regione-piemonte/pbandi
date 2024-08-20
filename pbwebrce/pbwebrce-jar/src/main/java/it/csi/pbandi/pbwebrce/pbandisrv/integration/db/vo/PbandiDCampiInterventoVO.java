/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;

public class PbandiDCampiInterventoVO extends GenericVO {
	
	private BigDecimal idCampoIntervento;
	private String codCampoIntervento;
	private String descrizione;
	
	public PbandiDCampiInterventoVO() {};
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idCampoIntervento != null) {
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
		pk.add("idCampoIntervento");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idCampoIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCampoIntervento: " + temp + "\t\n");
	    temp = DataFilter.removeNull( codCampoIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codCampoIntervento: " + temp + "\t\n");
	    temp = DataFilter.removeNull( descrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrizione: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdCampoIntervento() {
		return idCampoIntervento;
	}

	public void setIdCampoIntervento(BigDecimal idCampoIntervento) {
		this.idCampoIntervento = idCampoIntervento;
	}

	public String getCodCampoIntervento() {
		return codCampoIntervento;
	}

	public void setCodCampoIntervento(String codCampoIntervento) {
		this.codCampoIntervento = codCampoIntervento;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
