/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRDettPropCertRevocaVO extends GenericVO {

  	
  	private BigDecimal idDettPropostaCertif;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal importoDocumento;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idRevoca;
  	
	public PbandiRDettPropCertRevocaVO() {}
  	
	public PbandiRDettPropCertRevocaVO (BigDecimal idDettPropostaCertif, BigDecimal idRevoca) {
	
		this. idDettPropostaCertif =  idDettPropostaCertif;
		this. idRevoca =  idRevoca;
	}
  	
	public PbandiRDettPropCertRevocaVO (BigDecimal idDettPropostaCertif, BigDecimal idUtenteAgg, BigDecimal importoDocumento, BigDecimal idUtenteIns, BigDecimal idRevoca) {
	
		this. idDettPropostaCertif =  idDettPropostaCertif;
		this. idUtenteAgg =  idUtenteAgg;
		this. importoDocumento =  importoDocumento;
		this. idUtenteIns =  idUtenteIns;
		this. idRevoca =  idRevoca;
	}
  	
  	
	public BigDecimal getIdDettPropostaCertif() {
		return idDettPropostaCertif;
	}
	
	public void setIdDettPropostaCertif(BigDecimal idDettPropostaCertif) {
		this.idDettPropostaCertif = idDettPropostaCertif;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
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
	
	public BigDecimal getIdRevoca() {
		return idRevoca;
	}
	
	public void setIdRevoca(BigDecimal idRevoca) {
		this.idRevoca = idRevoca;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && importoDocumento != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDettPropostaCertif != null && idRevoca != null ) {
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
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRevoca);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRevoca: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idDettPropostaCertif");
		
			pk.add("idRevoca");
		
	    return pk;
	}
	
	
}
