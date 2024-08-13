/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;
import java.math.BigDecimal;

public class PbandiRBandiPremialitaVO extends GenericVO {
	
	private BigDecimal idPremialita;
	private BigDecimal progrBandoLineaIntervento;
	
	public PbandiRBandiPremialitaVO() {};
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idPremialita != null && progrBandoLineaIntervento != null) {
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
		pk.add("idPremialita");
		pk.add("progrBandoLineaIntervento");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idPremialita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPremialita: " + temp + "\t\n");
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdPremialita() {
		return idPremialita;
	}

	public void setIdPremialita(BigDecimal idPremialita) {
		this.idPremialita = idPremialita;
	}

	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

}
