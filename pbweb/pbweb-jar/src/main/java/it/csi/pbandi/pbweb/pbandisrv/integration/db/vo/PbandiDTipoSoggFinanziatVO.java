/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoSoggFinanziatVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descTipoSoggFinanziatore;
  	
  	private BigDecimal percStandard;
  	
  	private BigDecimal idTipoSoggFinanziat;
  	
  	private Date dtInizioValidita;
  	
  	private String descBreveTipoSoggFinanziat;
  	
	public PbandiDTipoSoggFinanziatVO() {}
  	
	public PbandiDTipoSoggFinanziatVO (BigDecimal idTipoSoggFinanziat) {
	
		this. idTipoSoggFinanziat =  idTipoSoggFinanziat;
	}
  	
	public PbandiDTipoSoggFinanziatVO (Date dtFineValidita, String descTipoSoggFinanziatore, BigDecimal percStandard, BigDecimal idTipoSoggFinanziat, Date dtInizioValidita, String descBreveTipoSoggFinanziat) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descTipoSoggFinanziatore =  descTipoSoggFinanziatore;
		this. percStandard =  percStandard;
		this. idTipoSoggFinanziat =  idTipoSoggFinanziat;
		this. dtInizioValidita =  dtInizioValidita;
		this. descBreveTipoSoggFinanziat =  descBreveTipoSoggFinanziat;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescTipoSoggFinanziatore() {
		return descTipoSoggFinanziatore;
	}
	
	public void setDescTipoSoggFinanziatore(String descTipoSoggFinanziatore) {
		this.descTipoSoggFinanziatore = descTipoSoggFinanziatore;
	}
	
	public BigDecimal getPercStandard() {
		return percStandard;
	}
	
	public void setPercStandard(BigDecimal percStandard) {
		this.percStandard = percStandard;
	}
	
	public BigDecimal getIdTipoSoggFinanziat() {
		return idTipoSoggFinanziat;
	}
	
	public void setIdTipoSoggFinanziat(BigDecimal idTipoSoggFinanziat) {
		this.idTipoSoggFinanziat = idTipoSoggFinanziat;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescBreveTipoSoggFinanziat() {
		return descBreveTipoSoggFinanziat;
	}
	
	public void setDescBreveTipoSoggFinanziat(String descBreveTipoSoggFinanziat) {
		this.descBreveTipoSoggFinanziat = descBreveTipoSoggFinanziat;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descTipoSoggFinanziatore != null && dtInizioValidita != null && descBreveTipoSoggFinanziat != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoSoggFinanziat != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoSoggFinanziatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoSoggFinanziatore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percStandard);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percStandard: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoSoggFinanziat);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoSoggFinanziat: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoSoggFinanziat);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoSoggFinanziat: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idTipoSoggFinanziat");
		
	    return pk;
	}
	
	
}
