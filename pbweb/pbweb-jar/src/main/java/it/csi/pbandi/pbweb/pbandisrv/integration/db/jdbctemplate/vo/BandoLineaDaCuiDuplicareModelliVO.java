/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

public class BandoLineaDaCuiDuplicareModelliVO extends GenericVO {
	
	// CDU-14 V04: nuova classe
	
	private String progrBandoLineaIntervento;
	
	private String nomeBandoLinea;

	public String getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(String progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrBandoLineaIntervento != null) {
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
	    
	    temp = DataFilter.removeNull(nomeBandoLinea);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeBandoLinea: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("progrBandoLineaIntervento");
		
	    return pk;
	}
}
