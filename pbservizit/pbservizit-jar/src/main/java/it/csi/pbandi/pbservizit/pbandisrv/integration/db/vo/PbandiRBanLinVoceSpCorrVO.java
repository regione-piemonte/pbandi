/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiRBanLinVoceSpCorrVO extends GenericVO {

  	
  	private BigDecimal progrVoceSpesaCorrelata;
  	
  	private BigDecimal progrBandoLineaIntervento;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiRBanLinVoceSpCorrVO() {}
  	
	public PbandiRBanLinVoceSpCorrVO (BigDecimal progrVoceSpesaCorrelata, BigDecimal progrBandoLineaIntervento) {
	
		this. progrVoceSpesaCorrelata =  progrVoceSpesaCorrelata;
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
	}
  	
	public PbandiRBanLinVoceSpCorrVO (BigDecimal progrVoceSpesaCorrelata, BigDecimal progrBandoLineaIntervento, Date dtFineValidita, BigDecimal idUtenteAgg, Date dtInizioValidita, BigDecimal idUtenteIns) {
	
		this. progrVoceSpesaCorrelata =  progrVoceSpesaCorrelata;
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this. dtFineValidita =  dtFineValidita;
		this. idUtenteAgg =  idUtenteAgg;
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getProgrVoceSpesaCorrelata() {
		return progrVoceSpesaCorrelata;
	}
	
	public void setProgrVoceSpesaCorrelata(BigDecimal progrVoceSpesaCorrelata) {
		this.progrVoceSpesaCorrelata = progrVoceSpesaCorrelata;
	}
	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
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
		if (progrVoceSpesaCorrelata != null && progrBandoLineaIntervento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( progrVoceSpesaCorrelata);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrVoceSpesaCorrelata: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("progrVoceSpesaCorrelata");
		
			pk.add("progrBandoLineaIntervento");
		
	    return pk;
	}
	
	
}
