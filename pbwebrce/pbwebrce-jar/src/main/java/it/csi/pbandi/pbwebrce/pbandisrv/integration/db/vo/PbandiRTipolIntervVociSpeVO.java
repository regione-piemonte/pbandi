/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRTipolIntervVociSpeVO extends GenericVO {

  	
  	private BigDecimal idVoceSpesa;
  	
  	private BigDecimal idTipolIntervento;
  	  	
	public PbandiRTipolIntervVociSpeVO() {}
  	
	public PbandiRTipolIntervVociSpeVO (BigDecimal idVoceSpesa, BigDecimal idTipolIntervento) {
		this. idVoceSpesa =  idVoceSpesa;
		this. idTipolIntervento =  idTipolIntervento;
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
		if (idVoceSpesa != null && idTipolIntervento != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    temp = DataFilter.removeNull( idVoceSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idVoceSpesa: " + temp + "\t\n");	    
	    temp = DataFilter.removeNull( idTipolIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipolIntervento: " + temp + "\t\n");
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idVoceSpesa");
		
			pk.add("idTipolIntervento");
		
	    return pk;
	}

	public BigDecimal getIdVoceSpesa() {
		return idVoceSpesa;
	}

	public void setIdVoceSpesa(BigDecimal idVoceSpesa) {
		this.idVoceSpesa = idVoceSpesa;
	}

	public BigDecimal getIdTipolIntervento() {
		return idTipolIntervento;
	}

	public void setIdTipolIntervento(BigDecimal idTipolIntervento) {
		this.idTipolIntervento = idTipolIntervento;
	}
	
}
