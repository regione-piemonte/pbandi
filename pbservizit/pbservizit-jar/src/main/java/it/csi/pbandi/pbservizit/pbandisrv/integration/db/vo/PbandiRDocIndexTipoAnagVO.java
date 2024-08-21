/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiRDocIndexTipoAnagVO extends GenericVO {

  	
  	private BigDecimal idTipoDocumentoIndex;
  	
  	private BigDecimal idTipoAnagrafica;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiRDocIndexTipoAnagVO() {}
  	
	public PbandiRDocIndexTipoAnagVO (BigDecimal idTipoDocumentoIndex, BigDecimal idTipoAnagrafica) {
	
		this. idTipoDocumentoIndex =  idTipoDocumentoIndex;
		this. idTipoAnagrafica =  idTipoAnagrafica;
	}
  	
	public PbandiRDocIndexTipoAnagVO (BigDecimal idTipoDocumentoIndex, BigDecimal idTipoAnagrafica, BigDecimal idUtenteAgg, BigDecimal idUtenteIns) {
	
		this. idTipoDocumentoIndex =  idTipoDocumentoIndex;
		this. idTipoAnagrafica =  idTipoAnagrafica;
		this. idUtenteAgg =  idUtenteAgg;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	
	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	
	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	
	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
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
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoDocumentoIndex != null && idTipoAnagrafica != null ) {
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
	    
	    temp = DataFilter.removeNull( idTipoAnagrafica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAnagrafica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoDocumentoIndex");
		
			pk.add("idTipoAnagrafica");
		
	    return pk;
	}
	
	
}
