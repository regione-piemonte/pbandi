/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;

public class PbandiRBandiTipolIntervVO extends GenericVO {
	
	private BigDecimal idTipolIntervento;
	private BigDecimal idBando;
	
	public PbandiRBandiTipolIntervVO() {};
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipolIntervento != null && idBando != null) {
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
		pk.add("idBando");
		pk.add("idTipolIntervento");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipolIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipolIntervento: " + temp + "\t\n");
	    temp = DataFilter.removeNull( idBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBando: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdTipolIntervento() {
		return idTipolIntervento;
	}

	public void setIdTipolIntervento(BigDecimal idTipolIntervento) {
		this.idTipolIntervento = idTipolIntervento;
	}

	public BigDecimal getIdBando() {
		return idBando;
	}

	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}

}
