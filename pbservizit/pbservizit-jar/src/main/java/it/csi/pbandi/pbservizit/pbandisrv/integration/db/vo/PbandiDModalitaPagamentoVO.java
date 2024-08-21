/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDModalitaPagamentoVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descBreveModalitaPagamento;
  	
  	private String descModalitaPagamento;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idModalitaPagamento;
  	
	public PbandiDModalitaPagamentoVO() {}
  	
	public PbandiDModalitaPagamentoVO (BigDecimal idModalitaPagamento) {
	
		this. idModalitaPagamento =  idModalitaPagamento;
	}
  	
	public PbandiDModalitaPagamentoVO (Date dtFineValidita, String descBreveModalitaPagamento, String descModalitaPagamento, Date dtInizioValidita, BigDecimal idModalitaPagamento) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descBreveModalitaPagamento =  descBreveModalitaPagamento;
		this. descModalitaPagamento =  descModalitaPagamento;
		this. dtInizioValidita =  dtInizioValidita;
		this. idModalitaPagamento =  idModalitaPagamento;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveModalitaPagamento() {
		return descBreveModalitaPagamento;
	}
	
	public void setDescBreveModalitaPagamento(String descBreveModalitaPagamento) {
		this.descBreveModalitaPagamento = descBreveModalitaPagamento;
	}
	
	public String getDescModalitaPagamento() {
		return descModalitaPagamento;
	}
	
	public void setDescModalitaPagamento(String descModalitaPagamento) {
		this.descModalitaPagamento = descModalitaPagamento;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdModalitaPagamento() {
		return idModalitaPagamento;
	}
	
	public void setIdModalitaPagamento(BigDecimal idModalitaPagamento) {
		this.idModalitaPagamento = idModalitaPagamento;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveModalitaPagamento != null && descModalitaPagamento != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idModalitaPagamento != null ) {
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
	    
	    temp = DataFilter.removeNull( descBreveModalitaPagamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveModalitaPagamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descModalitaPagamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descModalitaPagamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idModalitaPagamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idModalitaPagamento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idModalitaPagamento");
		
	    return pk;
	}
	
	
}
