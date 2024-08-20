/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;
import java.math.BigDecimal;

public class PbandiWCspCostantiVO extends GenericVO{
	
	// CDU-13 V05: nuova classe
	
	private BigDecimal progrBandoLineaIntervento;
	
	private String attributo;
	
	private String valore;
	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public String getAttributo() {
		return attributo;
	}
	public void setAttributo(String attributo) {
		this.attributo = attributo;
	}
	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
	
	public PbandiWCspCostantiVO () {}
	
	public PbandiWCspCostantiVO (BigDecimal progrBandoLineaIntervento, String attributo, String valore) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
		this.attributo = attributo;
		this.valore = valore;
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
		if (progrBandoLineaIntervento != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull(progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull(attributo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" attributo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull(valore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" valore: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		pk.add("progrBandoLineaIntervento");		
	    return pk;
	}

}
