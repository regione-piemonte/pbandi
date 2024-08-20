/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRBandoLinTipoAiutoVO extends GenericVO {

  	
  	private BigDecimal progrBandoLineaIntervento;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idTipoAiuto;
  	
	public PbandiRBandoLinTipoAiutoVO() {}
  	
	public PbandiRBandoLinTipoAiutoVO (BigDecimal progrBandoLineaIntervento, BigDecimal idTipoAiuto) {
	
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this. idTipoAiuto =  idTipoAiuto;
	}
  	
	public PbandiRBandoLinTipoAiutoVO (BigDecimal progrBandoLineaIntervento, Date dtFineValidita, BigDecimal idTipoAiuto) {
	
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this. dtFineValidita =  dtFineValidita;
		this. idTipoAiuto =  idTipoAiuto;
	}
  	
  	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdTipoAiuto() {
		return idTipoAiuto;
	}
	
	public void setIdTipoAiuto(BigDecimal idTipoAiuto) {
		this.idTipoAiuto = idTipoAiuto;
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
		if (progrBandoLineaIntervento != null && idTipoAiuto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAiuto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAiuto: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("progrBandoLineaIntervento");
		
			pk.add("idTipoAiuto");
		
	    return pk;
	}
	
	
}
