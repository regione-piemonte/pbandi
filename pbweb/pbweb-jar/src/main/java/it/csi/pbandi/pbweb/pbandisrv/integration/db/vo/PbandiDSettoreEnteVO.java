/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDSettoreEnteVO extends GenericVO {

  	
  	private BigDecimal idSettoreEnte;
  	
  	private Date dtFineValidita;
  	
  	private String descBreveSettore;
  	
  	private BigDecimal idEnteCompetenza;
  	
  	private Date dtInizioValidita;
  	
  	private String descSettore;
  	
	private BigDecimal idIndirizzo;
	
	private String indirizzoMailPec;
  	
	public PbandiDSettoreEnteVO() {}
  	
	public PbandiDSettoreEnteVO (BigDecimal idSettoreEnte) {
	
		this. idSettoreEnte =  idSettoreEnte;
	}
  	
	public PbandiDSettoreEnteVO (BigDecimal idSettoreEnte, Date dtFineValidita, String descBreveSettore, BigDecimal idEnteCompetenza, Date dtInizioValidita, String descSettore,BigDecimal idIndirizzo) {
	
		this. idSettoreEnte =  idSettoreEnte;
		this. dtFineValidita =  dtFineValidita;
		this. descBreveSettore =  descBreveSettore;
		this. idEnteCompetenza =  idEnteCompetenza;
		this. dtInizioValidita =  dtInizioValidita;
		this. descSettore =  descSettore;
		this. idIndirizzo = idIndirizzo;
	}
  	
  	
	public BigDecimal getIdSettoreEnte() {
		return idSettoreEnte;
	}
	
	public void setIdSettoreEnte(BigDecimal idSettoreEnte) {
		this.idSettoreEnte = idSettoreEnte;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveSettore() {
		return descBreveSettore;
	}
	
	public void setDescBreveSettore(String descBreveSettore) {
		this.descBreveSettore = descBreveSettore;
	}
	
	public BigDecimal getIdEnteCompetenza() {
		return idEnteCompetenza;
	}
	
	public void setIdEnteCompetenza(BigDecimal idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescSettore() {
		return descSettore;
	}
	
	public void setDescSettore(String descSettore) {
		this.descSettore = descSettore;
	}
	
	public BigDecimal getIdIndirizzo() {
		return idIndirizzo;
	}

	public void setIdIndirizzo(BigDecimal idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}

	public String getIndirizzoMailPec() {
		return indirizzoMailPec;
	}

	public void setIndirizzoMailPec(String indirizzoMailPec) {
		this.indirizzoMailPec = indirizzoMailPec;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveSettore != null && idEnteCompetenza != null && dtInizioValidita != null && descSettore != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idSettoreEnte != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idSettoreEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSettoreEnte: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveSettore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveSettore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEnteCompetenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEnteCompetenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descSettore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descSettore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndirizzo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndirizzo: " + temp + "\t\n");
	    
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idSettoreEnte");
		
	    return pk;
	}
	
	
}
