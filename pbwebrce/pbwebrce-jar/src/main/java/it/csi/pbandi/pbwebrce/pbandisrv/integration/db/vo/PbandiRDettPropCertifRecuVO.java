/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import java.util.List;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRDettPropCertifRecuVO extends GenericVO {

  	
  	private BigDecimal idDettPropostaCertif;
  	
  	private BigDecimal idRecupero;
  	
  	private BigDecimal interessiRecupero;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idTipoRecupero;
  	
  	private BigDecimal importoDocumento;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiRDettPropCertifRecuVO() {}
  	
	public PbandiRDettPropCertifRecuVO (BigDecimal idDettPropostaCertif, BigDecimal idRecupero) {
	
		this. idDettPropostaCertif =  idDettPropostaCertif;
		this. idRecupero =  idRecupero;
	}
  	
	public PbandiRDettPropCertifRecuVO (BigDecimal idDettPropostaCertif, BigDecimal idRecupero, BigDecimal interessiRecupero, BigDecimal idUtenteAgg, BigDecimal idTipoRecupero, BigDecimal importoDocumento, BigDecimal idUtenteIns) {
	
		this. idDettPropostaCertif =  idDettPropostaCertif;
		this. idRecupero =  idRecupero;
		this. interessiRecupero =  interessiRecupero;
		this. idUtenteAgg =  idUtenteAgg;
		this. idTipoRecupero =  idTipoRecupero;
		this. importoDocumento =  importoDocumento;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getIdDettPropostaCertif() {
		return idDettPropostaCertif;
	}
	
	public void setIdDettPropostaCertif(BigDecimal idDettPropostaCertif) {
		this.idDettPropostaCertif = idDettPropostaCertif;
	}
	
	public BigDecimal getIdRecupero() {
		return idRecupero;
	}
	
	public void setIdRecupero(BigDecimal idRecupero) {
		this.idRecupero = idRecupero;
	}
	
	public BigDecimal getInteressiRecupero() {
		return interessiRecupero;
	}
	
	public void setInteressiRecupero(BigDecimal interessiRecupero) {
		this.interessiRecupero = interessiRecupero;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdTipoRecupero() {
		return idTipoRecupero;
	}
	
	public void setIdTipoRecupero(BigDecimal idTipoRecupero) {
		this.idTipoRecupero = idTipoRecupero;
	}
	
	public BigDecimal getImportoDocumento() {
		return importoDocumento;
	}
	
	public void setImportoDocumento(BigDecimal importoDocumento) {
		this.importoDocumento = importoDocumento;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idTipoRecupero != null && importoDocumento != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDettPropostaCertif != null && idRecupero != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idDettPropostaCertif);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDettPropostaCertif: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRecupero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRecupero: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( interessiRecupero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" interessiRecupero: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoRecupero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoRecupero: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	@Override
	public List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
		pk.add("idDettPropostaCertif");
		pk.add("idRecupero");
		
		return pk;
	}
	
}
