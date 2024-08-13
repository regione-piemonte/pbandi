/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiREnteCompetenzaSoggVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idEnteCompetenza;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idSoggetto;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiREnteCompetenzaSoggVO() {}
  	
	public PbandiREnteCompetenzaSoggVO (BigDecimal idEnteCompetenza, BigDecimal idSoggetto) {
	
		this. idEnteCompetenza =  idEnteCompetenza;
		this. idSoggetto =  idSoggetto;
	}
  	
	public PbandiREnteCompetenzaSoggVO (Date dtFineValidita, BigDecimal idEnteCompetenza, BigDecimal idUtenteAgg, BigDecimal idSoggetto, Date dtInizioValidita, BigDecimal idUtenteIns) {
	
		this. dtFineValidita =  dtFineValidita;
		this. idEnteCompetenza =  idEnteCompetenza;
		this. idUtenteAgg =  idUtenteAgg;
		this. idSoggetto =  idSoggetto;
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdEnteCompetenza() {
		return idEnteCompetenza;
	}
	
	public void setIdEnteCompetenza(BigDecimal idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idEnteCompetenza != null && idSoggetto != null ) {
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
	    
	    temp = DataFilter.removeNull( idEnteCompetenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEnteCompetenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idEnteCompetenza");
		
			pk.add("idSoggetto");
		
	    return pk;
	}
	
	
}
