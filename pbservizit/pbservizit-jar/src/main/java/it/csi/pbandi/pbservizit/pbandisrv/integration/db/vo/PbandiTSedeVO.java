/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiTSedeVO extends GenericVO {

  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private String partitaIva;
  	
  	private BigDecimal idAttivitaAteco;
  	
  	private Date dtFineValidita;
  	
  	private String denominazione;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idSede;
  	
  	private BigDecimal idDimensioneTerritor;
  	
	public PbandiTSedeVO() {}
  	
	public PbandiTSedeVO (BigDecimal idSede) {
	
		this. idSede =  idSede;
	}
  	
	public PbandiTSedeVO (Date dtInizioValidita, BigDecimal idUtenteAgg, String partitaIva, BigDecimal idAttivitaAteco, Date dtFineValidita, String denominazione, BigDecimal idUtenteIns, BigDecimal idSede, BigDecimal idDimensioneTerritor) {
	
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteAgg =  idUtenteAgg;
		this. partitaIva =  partitaIva;
		this. idAttivitaAteco =  idAttivitaAteco;
		this. dtFineValidita =  dtFineValidita;
		this. denominazione =  denominazione;
		this. idUtenteIns =  idUtenteIns;
		this. idSede =  idSede;
		this. idDimensioneTerritor =  idDimensioneTerritor;
	}
  	
  	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public String getPartitaIva() {
		return partitaIva;
	}
	
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	
	public BigDecimal getIdAttivitaAteco() {
		return idAttivitaAteco;
	}
	
	public void setIdAttivitaAteco(BigDecimal idAttivitaAteco) {
		this.idAttivitaAteco = idAttivitaAteco;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDenominazione() {
		return denominazione;
	}
	
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdSede() {
		return idSede;
	}
	
	public void setIdSede(BigDecimal idSede) {
		this.idSede = idSede;
	}
	
	public BigDecimal getIdDimensioneTerritor() {
		return idDimensioneTerritor;
	}
	
	public void setIdDimensioneTerritor(BigDecimal idDimensioneTerritor) {
		this.idDimensioneTerritor = idDimensioneTerritor;
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
		if (idSede != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( partitaIva);
	    if (!DataFilter.isEmpty(temp)) sb.append(" partitaIva: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttivitaAteco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttivitaAteco: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( denominazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" denominazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSede);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSede: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDimensioneTerritor);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDimensioneTerritor: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idSede");
		
	    return pk;
	}
	
	
}
