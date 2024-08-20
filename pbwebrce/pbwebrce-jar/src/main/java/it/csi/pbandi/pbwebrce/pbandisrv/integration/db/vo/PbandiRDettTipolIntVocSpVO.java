/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRDettTipolIntVocSpVO extends GenericVO {

  	private BigDecimal idVoceSpesa;
  	
  	private BigDecimal idDettTipolIntervento;
  	  	
	public PbandiRDettTipolIntVocSpVO() {}
  	
	public PbandiRDettTipolIntVocSpVO (BigDecimal idVoceSpesa, BigDecimal idDettTipolIntervento) {
		this. idVoceSpesa =  idVoceSpesa;
		this. idDettTipolIntervento =  idDettTipolIntervento;
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
		if (idVoceSpesa != null && idDettTipolIntervento != null ) {
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
	    temp = DataFilter.removeNull( idDettTipolIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDettTipolIntervento: " + temp + "\t\n");
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idVoceSpesa");
		
			pk.add("idDettTipolIntervento");
		
	    return pk;
	}

	public BigDecimal getIdVoceSpesa() {
		return idVoceSpesa;
	}

	public void setIdVoceSpesa(BigDecimal idVoceSpesa) {
		this.idVoceSpesa = idVoceSpesa;
	}

	public BigDecimal getIdDettTipolIntervento() {
		return idDettTipolIntervento;
	}

	public void setIdDettTipolIntervento(BigDecimal idDettTipolIntervento) {
		this.idDettTipolIntervento = idDettTipolIntervento;
	}
	
}
