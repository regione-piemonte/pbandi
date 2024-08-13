/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

public class PbandiRBandiDettTipAiutiVO extends GenericVO {
	
	private BigDecimal idDettTipolAiuti;
	private BigDecimal progrBandoLineaIntervento;
	
	public PbandiRBandiDettTipAiutiVO() {};
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDettTipolAiuti != null && progrBandoLineaIntervento != null) {
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
		pk.add("idDettTipolAiuti");
		pk.add("progrBandoLineaIntervento");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idDettTipolAiuti);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDettTipolAiuti: " + temp + "\t\n");
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdDettTipolAiuti() {
		return idDettTipolAiuti;
	}

	public void setIdDettTipolAiuti(BigDecimal idDettTipolAiuti) {
		this.idDettTipolAiuti = idDettTipolAiuti;
	}

	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

}
