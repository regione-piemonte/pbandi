/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiRRegolaTipoAnagVO extends GenericVO {

  	
  	private BigDecimal idTipoAnagrafica;
  	
  	private BigDecimal progrBandoLineaIntervento;
  	
  	private BigDecimal idRegola;
  	
  	private BigDecimal idTipoBeneficiario;
  	
	public PbandiRRegolaTipoAnagVO() {}
  	
  	
	public PbandiRRegolaTipoAnagVO (BigDecimal idTipoAnagrafica, BigDecimal progrBandoLineaIntervento, BigDecimal idRegola, BigDecimal idTipoBeneficiario) {
	
		this. idTipoAnagrafica =  idTipoAnagrafica;
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this. idRegola =  idRegola;
		this. idTipoBeneficiario =  idTipoBeneficiario;
	}
  	
  	
	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	
	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	
	public BigDecimal getIdRegola() {
		return idRegola;
	}
	
	public void setIdRegola(BigDecimal idRegola) {
		this.idRegola = idRegola;
	}
	
	public BigDecimal getIdTipoBeneficiario() {
		return idTipoBeneficiario;
	}
	
	public void setIdTipoBeneficiario(BigDecimal idTipoBeneficiario) {
		this.idTipoBeneficiario = idTipoBeneficiario;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idTipoAnagrafica != null && progrBandoLineaIntervento != null && idRegola != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAnagrafica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAnagrafica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRegola);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRegola: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoBeneficiario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoBeneficiario: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
	    return pk;
	}
	
	
}
