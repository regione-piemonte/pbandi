/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDTematicaVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String codTematica;
  	
  	private BigDecimal idTematica;
  	
  	private Date dtInizioValidita;
  	
  	private String descTematica;
  	
	public PbandiDTematicaVO() {}
  	
	public PbandiDTematicaVO (BigDecimal idTematica) {
	
		this. idTematica =  idTematica;
	}
  	
	public PbandiDTematicaVO (Date dtFineValidita, String codTematica, BigDecimal idTematica, Date dtInizioValidita, String descTematica) {
	
		this. dtFineValidita =  dtFineValidita;
		this. codTematica =  codTematica;
		this. idTematica =  idTematica;
		this. dtInizioValidita =  dtInizioValidita;
		this. descTematica =  descTematica;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getCodTematica() {
		return codTematica;
	}
	
	public void setCodTematica(String codTematica) {
		this.codTematica = codTematica;
	}
	
	public BigDecimal getIdTematica() {
		return idTematica;
	}
	
	public void setIdTematica(BigDecimal idTematica) {
		this.idTematica = idTematica;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescTematica() {
		return descTematica;
	}
	
	public void setDescTematica(String descTematica) {
		this.descTematica = descTematica;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codTematica != null && dtInizioValidita != null && descTematica != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTematica != null ) {
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
	    
	    temp = DataFilter.removeNull( codTematica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codTematica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTematica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTematica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTematica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTematica: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTematica");
		
	    return pk;
	}
	
	
}
