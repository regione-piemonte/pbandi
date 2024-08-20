/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;
import java.math.BigDecimal;

public class PbandiRTipolBenBandiVO extends GenericVO {
	
	private BigDecimal idTipolBeneficiario;
	private BigDecimal progrBandoLineaIntervento;
	
	public PbandiRTipolBenBandiVO() {};
	
	public PbandiRTipolBenBandiVO (BigDecimal idTipolBeneficiario, BigDecimal progrBandoLineaIntervento) {		
		this.idTipolBeneficiario = idTipolBeneficiario;
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipolBeneficiario != null && progrBandoLineaIntervento != null) {
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
		pk.add("idTipolBeneficiario");
		pk.add("progrBandoLineaIntervento");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipolBeneficiario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipolBeneficiario: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdTipolBeneficiario() {
		return idTipolBeneficiario;
	}

	public void setIdTipolBeneficiario(BigDecimal idTipolBeneficiario) {
		this.idTipolBeneficiario = idTipolBeneficiario;
	}

	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	

}
