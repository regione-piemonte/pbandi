/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiRSoggProgSoggCorrelVO extends GenericVO {

  	
  	private BigDecimal progrSoggettoProgetto;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtAggiornamento;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal idUtenteIns;

	private BigDecimal progrSoggettiCorrelati;
  	
  	private Date dtFineValidita;
  	
	public PbandiRSoggProgSoggCorrelVO() {}
  	
	public PbandiRSoggProgSoggCorrelVO (BigDecimal progrSoggettoProgetto, BigDecimal progrSoggettiCorrelati) {
	
		this. progrSoggettoProgetto =  progrSoggettoProgetto;
		this. progrSoggettiCorrelati =  progrSoggettiCorrelati;
	}
  	
	public PbandiRSoggProgSoggCorrelVO (BigDecimal progrSoggettoProgetto, BigDecimal idUtenteAgg, Date dtAggiornamento, Date dtInserimento, BigDecimal idUtenteIns, BigDecimal progrSoggettiCorrelati) {
	
		this. progrSoggettoProgetto =  progrSoggettoProgetto;
		this. idUtenteAgg =  idUtenteAgg;
		this. dtAggiornamento =  dtAggiornamento;
		this. dtInserimento =  dtInserimento;
		this. idUtenteIns =  idUtenteIns;
		this. progrSoggettiCorrelati =  progrSoggettiCorrelati;
	}
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
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
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getProgrSoggettiCorrelati() {
		return progrSoggettiCorrelati;
	}
	
	public void setProgrSoggettiCorrelati(BigDecimal progrSoggettiCorrelati) {
		this.progrSoggettiCorrelati = progrSoggettiCorrelati;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInserimento != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrSoggettoProgetto != null && progrSoggettiCorrelati != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( progrSoggettoProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrSoggettoProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrSoggettiCorrelati);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrSoggettiCorrelati: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("progrSoggettoProgetto");
		
			pk.add("progrSoggettiCorrelati");
		
	    return pk;
	}
	
	
}
