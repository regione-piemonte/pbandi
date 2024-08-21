/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;
import java.sql.Date;


public class PbandiREconomSoggFinanziatVO extends GenericVO{
	
  	private BigDecimal 	idEconomia;
  	
  	private BigDecimal 	idSoggettoFinanziatore;
  	
  	private Double 		percQuotaSoggFinanziat;
  	
  	private BigDecimal 	impQuotaEconSoggFinanziat;
  	
  	private Date 		dtAggiornamento;

  	private Date 		dtInserimento;
  	  	
  	private BigDecimal 	idUtenteIns;
  	
  	private BigDecimal 	idUtenteAgg;
  	
  	private String 		tipologiaProgetto;

	public PbandiREconomSoggFinanziatVO(){}
  	
  	public PbandiREconomSoggFinanziatVO(BigDecimal idEconomia){
  		this.idEconomia = idEconomia;
  	}
  	
  	public PbandiREconomSoggFinanziatVO(BigDecimal idEconomia,             
  			BigDecimal idSoggettoFinanziatore,    	                    
  			Double percQuotaSoggFinanziat,       	                    
  			BigDecimal impQuotaEconSoggFinanziat,	                     	                    
  			Date dtInserimento,      	                    
  			Date dtAggiornamento,         	                    
  			BigDecimal idUtenteIns,          	                    
  			BigDecimal idUtenteAgg,
  			String tipologiaProgetto) {

  		this.idEconomia  				=  idEconomia ;                                                      
  		this.idSoggettoFinanziatore 	= idSoggettoFinanziatore  ;  		                                              
  		this.percQuotaSoggFinanziat     = percQuotaSoggFinanziat; 		                                              
  		this.impQuotaEconSoggFinanziat  = impQuotaEconSoggFinanziat;  		                                                                                        
  		this.dtInserimento         		= dtInserimento;                                        
  		this.dtAggiornamento            = dtAggiornamento;  		                                             
  		this.idUtenteIns             	= idUtenteIns;	                                              
  		this.idUtenteAgg             	= idUtenteAgg;       
  		this.tipologiaProgetto			= tipologiaProgetto;
  	}

	public BigDecimal getIdEconomia() {
		return idEconomia;
	}

	public void setIdEconomia(BigDecimal idEconomia) {
		this.idEconomia = idEconomia;
	}

	public BigDecimal getIdSoggettoFinanziatore() {
		return idSoggettoFinanziatore;
	}

	public void setIdSoggettoFinanziatore(BigDecimal idSoggettoFinanziatore) {
		this.idSoggettoFinanziatore = idSoggettoFinanziatore;
	}

	public Double getPercQuotaSoggFinanziat() {
		return percQuotaSoggFinanziat;
	}

	public void setPercQuotaSoggFinanziat(Double percQuotaSoggFinanziat) {
		this.percQuotaSoggFinanziat = percQuotaSoggFinanziat;
	}

	public BigDecimal getImpQuotaEconSoggFinanziat() {
		return impQuotaEconSoggFinanziat;
	}

	public void setImpQuotaEconSoggFinanziat(BigDecimal impQuotaEconSoggFinanziat) {
		this.impQuotaEconSoggFinanziat = impQuotaEconSoggFinanziat;
	}

	public Date getDtInserimento() {
		return dtInserimento;
	}

	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}

	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}

	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}

	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
  	
  	public String getTipologiaProgetto() {
		return tipologiaProgetto;
	}

	public void setTipologiaProgetto(String tipologiaProgetto) {
		this.tipologiaProgetto = tipologiaProgetto;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && percQuotaSoggFinanziat != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idEconomia != null && idSoggettoFinanziatore != null && tipologiaProgetto != null) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idEconomia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEconomia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggettoFinanziatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggettoFinanziatore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percQuotaSoggFinanziat);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percQuotaSoggFinanziat: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( impQuotaEconSoggFinanziat);
	    if (!DataFilter.isEmpty(temp)) sb.append(" impQuotaEconSoggFinanziat: " + temp + "\t\n");
	   
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tipologiaProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tipologiaProgetto: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idEconomia");
			pk.add("idSoggettoFinanziatore");
			pk.add("tipologiaProgetto");
		
	    return pk;
	}

  	

}
