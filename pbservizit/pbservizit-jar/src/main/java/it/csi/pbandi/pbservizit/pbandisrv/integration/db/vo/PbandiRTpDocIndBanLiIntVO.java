/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiRTpDocIndBanLiIntVO extends GenericVO {

  	
  	private BigDecimal idTipoDocumentoIndex;
  	
  	private BigDecimal progrBandoLineaIntervento;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idModello;
  	
	public PbandiRTpDocIndBanLiIntVO() {}
  	
	public PbandiRTpDocIndBanLiIntVO (BigDecimal idTipoDocumentoIndex, BigDecimal progrBandoLineaIntervento) {
	
		this. idTipoDocumentoIndex =  idTipoDocumentoIndex;
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
	}
  	
	public PbandiRTpDocIndBanLiIntVO (BigDecimal idTipoDocumentoIndex, BigDecimal progrBandoLineaIntervento, BigDecimal idUtenteAgg, BigDecimal idUtenteIns, BigDecimal idModello) {
	
		this. idTipoDocumentoIndex =  idTipoDocumentoIndex;
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this. idUtenteAgg =  idUtenteAgg;
		this. idUtenteIns =  idUtenteIns;
		this. idModello =  idModello;
	}
  	
  	
	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	
	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdModello() {
		return idModello;
	}
	
	public void setIdModello(BigDecimal idModello) {
		this.idModello = idModello;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoDocumentoIndex != null && progrBandoLineaIntervento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoDocumentoIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDocumentoIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idModello);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idModello: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoDocumentoIndex");
		
			pk.add("progrBandoLineaIntervento");
		
	    return pk;
	}
	
	
}
