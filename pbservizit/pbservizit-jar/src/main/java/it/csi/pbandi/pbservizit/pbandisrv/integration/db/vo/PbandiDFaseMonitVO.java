/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDFaseMonitVO extends GenericVO {

  	
  	private BigDecimal idFaseMonit;
  	
  	private BigDecimal idIter;
  	
  	private Date dtFineValidita;
  	
  	private String codIgrueT35;
  	
  	private String flagObbligatorio;
  	
  	private String descFaseMonit;
  	
  	private String flagControlloIndicat;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDFaseMonitVO() {}
  	
	public PbandiDFaseMonitVO (BigDecimal idFaseMonit) {
	
		this. idFaseMonit =  idFaseMonit;
	}
  	
	public PbandiDFaseMonitVO (BigDecimal idFaseMonit, BigDecimal idIter, Date dtFineValidita, String codIgrueT35, String flagObbligatorio, String descFaseMonit, String flagControlloIndicat, Date dtInizioValidita) {
	
		this. idFaseMonit =  idFaseMonit;
		this. idIter =  idIter;
		this. dtFineValidita =  dtFineValidita;
		this. codIgrueT35 =  codIgrueT35;
		this. flagObbligatorio =  flagObbligatorio;
		this. descFaseMonit =  descFaseMonit;
		this. flagControlloIndicat =  flagControlloIndicat;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public BigDecimal getIdFaseMonit() {
		return idFaseMonit;
	}
	
	public void setIdFaseMonit(BigDecimal idFaseMonit) {
		this.idFaseMonit = idFaseMonit;
	}
	
	public BigDecimal getIdIter() {
		return idIter;
	}
	
	public void setIdIter(BigDecimal idIter) {
		this.idIter = idIter;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getCodIgrueT35() {
		return codIgrueT35;
	}
	
	public void setCodIgrueT35(String codIgrueT35) {
		this.codIgrueT35 = codIgrueT35;
	}
	
	public String getFlagObbligatorio() {
		return flagObbligatorio;
	}
	
	public void setFlagObbligatorio(String flagObbligatorio) {
		this.flagObbligatorio = flagObbligatorio;
	}
	
	public String getDescFaseMonit() {
		return descFaseMonit;
	}
	
	public void setDescFaseMonit(String descFaseMonit) {
		this.descFaseMonit = descFaseMonit;
	}
	
	public String getFlagControlloIndicat() {
		return flagControlloIndicat;
	}
	
	public void setFlagControlloIndicat(String flagControlloIndicat) {
		this.flagControlloIndicat = flagControlloIndicat;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idIter != null && codIgrueT35 != null && flagObbligatorio != null && descFaseMonit != null && flagControlloIndicat != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idFaseMonit != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idFaseMonit);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFaseMonit: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIter);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIter: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT35);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT35: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagObbligatorio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagObbligatorio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descFaseMonit);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descFaseMonit: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagControlloIndicat);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagControlloIndicat: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idFaseMonit");
		
	    return pk;
	}
	
	
}
