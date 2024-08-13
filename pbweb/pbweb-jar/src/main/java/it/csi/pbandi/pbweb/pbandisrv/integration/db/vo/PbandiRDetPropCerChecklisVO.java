/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRDetPropCerChecklisVO extends GenericVO {

  	
  	private BigDecimal idDettPropostaCertif;
  	
  	private BigDecimal idChecklist;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal versione;
  	
	public PbandiRDetPropCerChecklisVO() {}
  	
	public PbandiRDetPropCerChecklisVO (BigDecimal idDettPropostaCertif, BigDecimal idChecklist) {
	
		this. idDettPropostaCertif =  idDettPropostaCertif;
		this. idChecklist =  idChecklist;
	}
  	
	public PbandiRDetPropCerChecklisVO (BigDecimal idDettPropostaCertif, BigDecimal idChecklist, BigDecimal idUtenteAgg, BigDecimal idUtenteIns, BigDecimal versione) {
	
		this. idDettPropostaCertif =  idDettPropostaCertif;
		this. idChecklist =  idChecklist;
		this. idUtenteAgg =  idUtenteAgg;
		this. idUtenteIns =  idUtenteIns;
		this. versione =  versione;
	}
  	
  	
	public BigDecimal getIdDettPropostaCertif() {
		return idDettPropostaCertif;
	}
	
	public void setIdDettPropostaCertif(BigDecimal idDettPropostaCertif) {
		this.idDettPropostaCertif = idDettPropostaCertif;
	}
	
	public BigDecimal getIdChecklist() {
		return idChecklist;
	}
	
	public void setIdChecklist(BigDecimal idChecklist) {
		this.idChecklist = idChecklist;
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
	
	public BigDecimal getVersione() {
		return versione;
	}
	
	public void setVersione(BigDecimal versione) {
		this.versione = versione;
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
		if (idDettPropostaCertif != null && idChecklist != null ) {
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
	    
	    temp = DataFilter.removeNull( idChecklist);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idChecklist: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( versione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" versione: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idDettPropostaCertif");
		
			pk.add("idChecklist");
		
	    return pk;
	}
	
	
}
