/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;



public class PbandiRProgettiAppaltiVO extends GenericVO {

  	
  	private BigDecimal idProgetto;
  	
  	private BigDecimal idAppalto;
	
  	private BigDecimal idTipoDocumentoIndex;
  	
	public PbandiRProgettiAppaltiVO() {}
  	
	public PbandiRProgettiAppaltiVO (BigDecimal idProgetto, BigDecimal idAppalto,BigDecimal idTipoDocumentoIndex) {
	
		this. idProgetto =  idProgetto;
		this. idAppalto =  idAppalto;
		this. idTipoDocumentoIndex =idTipoDocumentoIndex;
	}
 	  	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public BigDecimal getIdAppalto() {
		return idAppalto;
	}
	
	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}
	
	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}

	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
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
		if (idProgetto != null && idAppalto != null & idTipoDocumentoIndex!=null) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAppalto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAppalto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoDocumentoIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDocumentoIndex: " + temp + "\t\n"); 
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idProgetto");
		
			pk.add("idAppalto");
		
	    return pk;
	}


	
	
}
