/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRSoggProgRuoloEnteVO extends GenericVO {

  	
  	private BigDecimal idRuoloEnteCompetenza;
  	
  	private BigDecimal progrSoggettoProgetto;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiRSoggProgRuoloEnteVO() {}
  	
	public PbandiRSoggProgRuoloEnteVO (BigDecimal idRuoloEnteCompetenza, BigDecimal progrSoggettoProgetto) {
	
		this. idRuoloEnteCompetenza =  idRuoloEnteCompetenza;
		this. progrSoggettoProgetto =  progrSoggettoProgetto;
	}
  	
	public PbandiRSoggProgRuoloEnteVO (BigDecimal idRuoloEnteCompetenza, BigDecimal progrSoggettoProgetto, BigDecimal idUtenteAgg, BigDecimal idUtenteIns) {
	
		this. idRuoloEnteCompetenza =  idRuoloEnteCompetenza;
		this. progrSoggettoProgetto =  progrSoggettoProgetto;
		this. idUtenteAgg =  idUtenteAgg;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getIdRuoloEnteCompetenza() {
		return idRuoloEnteCompetenza;
	}
	
	public void setIdRuoloEnteCompetenza(BigDecimal idRuoloEnteCompetenza) {
		this.idRuoloEnteCompetenza = idRuoloEnteCompetenza;
	}
	
	public BigDecimal getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}
	
	public void setProgrSoggettoProgetto(BigDecimal progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
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
		if (idRuoloEnteCompetenza != null && progrSoggettoProgetto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idRuoloEnteCompetenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRuoloEnteCompetenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrSoggettoProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrSoggettoProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idRuoloEnteCompetenza");
		
			pk.add("progrSoggettoProgetto");
		
	    return pk;
	}
	
	
}
