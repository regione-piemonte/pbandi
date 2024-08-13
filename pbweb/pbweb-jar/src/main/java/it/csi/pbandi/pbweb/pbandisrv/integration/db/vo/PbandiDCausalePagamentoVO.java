/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDCausalePagamentoVO extends GenericVO {

  	
  	private BigDecimal idCausalePagamento;
  	
  	private Date dtFineValidita;
  	
  	private String descCausalePagamento;
  	
  	private String descBreveCausalePagamento;
  	
  	private String tc39;
  	
  	private Date dtInizioValidita;
  	
  	private String tipoPagamento;
  	
	public PbandiDCausalePagamentoVO() {}
  	
	public PbandiDCausalePagamentoVO (BigDecimal idCausalePagamento) {
	
		this. idCausalePagamento =  idCausalePagamento;
	}
  	
	public PbandiDCausalePagamentoVO (BigDecimal idCausalePagamento, Date dtFineValidita, String descCausalePagamento, String descBreveCausalePagamento, String tc39, Date dtInizioValidita, String tipoPagamento) {
	
		this. idCausalePagamento =  idCausalePagamento;
		this. dtFineValidita =  dtFineValidita;
		this. descCausalePagamento =  descCausalePagamento;
		this. descBreveCausalePagamento =  descBreveCausalePagamento;
		this. tc39 =  tc39;
		this. dtInizioValidita =  dtInizioValidita;
		this. tipoPagamento =  tipoPagamento;
	}
  	
  	
	public BigDecimal getIdCausalePagamento() {
		return idCausalePagamento;
	}
	
	public void setIdCausalePagamento(BigDecimal idCausalePagamento) {
		this.idCausalePagamento = idCausalePagamento;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescCausalePagamento() {
		return descCausalePagamento;
	}
	
	public void setDescCausalePagamento(String descCausalePagamento) {
		this.descCausalePagamento = descCausalePagamento;
	}
	
	public String getDescBreveCausalePagamento() {
		return descBreveCausalePagamento;
	}
	
	public void setDescBreveCausalePagamento(String descBreveCausalePagamento) {
		this.descBreveCausalePagamento = descBreveCausalePagamento;
	}
	
	public String getTc39() {
		return tc39;
	}
	
	public void setTc39(String tc39) {
		this.tc39 = tc39;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getTipoPagamento() {
		return tipoPagamento;
	}
	
	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descCausalePagamento != null && descBreveCausalePagamento != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idCausalePagamento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idCausalePagamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCausalePagamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descCausalePagamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descCausalePagamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveCausalePagamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveCausalePagamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tc39);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tc39: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tipoPagamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tipoPagamento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idCausalePagamento");
		
	    return pk;
	}
	
	
}
