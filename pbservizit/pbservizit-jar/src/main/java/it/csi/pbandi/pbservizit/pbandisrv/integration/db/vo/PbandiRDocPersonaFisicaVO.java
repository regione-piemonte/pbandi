/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiRDocPersonaFisicaVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idDocumento;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idPersonaFisica;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiRDocPersonaFisicaVO() {}
  	
	public PbandiRDocPersonaFisicaVO (BigDecimal idDocumento, BigDecimal idPersonaFisica) {
	
		this. idDocumento =  idDocumento;
		this. idPersonaFisica =  idPersonaFisica;
	}
  	
	public PbandiRDocPersonaFisicaVO (Date dtFineValidita, BigDecimal idDocumento, BigDecimal idUtenteAgg, BigDecimal idPersonaFisica, BigDecimal idUtenteIns) {
	
		this. dtFineValidita =  dtFineValidita;
		this. idDocumento =  idDocumento;
		this. idUtenteAgg =  idUtenteAgg;
		this. idPersonaFisica =  idPersonaFisica;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdDocumento() {
		return idDocumento;
	}
	
	public void setIdDocumento(BigDecimal idDocumento) {
		this.idDocumento = idDocumento;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdPersonaFisica() {
		return idPersonaFisica;
	}
	
	public void setIdPersonaFisica(BigDecimal idPersonaFisica) {
		this.idPersonaFisica = idPersonaFisica;
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
		if (idDocumento != null && idPersonaFisica != null ) {
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
	    
	    temp = DataFilter.removeNull( idDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPersonaFisica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPersonaFisica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idDocumento");
		
			pk.add("idPersonaFisica");
		
	    return pk;
	}
	
	
}
