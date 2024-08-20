/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRPagamentoDocSpesaVO extends GenericVO {

  	
  	private BigDecimal idPagamento;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idProgettoBck;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idDocumentoDiSpesa;
  	
	public PbandiRPagamentoDocSpesaVO() {}
  	
	public PbandiRPagamentoDocSpesaVO (BigDecimal idPagamento, BigDecimal idDocumentoDiSpesa) {
	
		this. idPagamento =  idPagamento;
		this. idDocumentoDiSpesa =  idDocumentoDiSpesa;
	}
  	
	public PbandiRPagamentoDocSpesaVO (BigDecimal idPagamento, BigDecimal idUtenteAgg, BigDecimal idProgettoBck, BigDecimal idUtenteIns, BigDecimal idDocumentoDiSpesa) {
	
		this. idPagamento =  idPagamento;
		this. idUtenteAgg =  idUtenteAgg;
		this. idProgettoBck =  idProgettoBck;
		this. idUtenteIns =  idUtenteIns;
		this. idDocumentoDiSpesa =  idDocumentoDiSpesa;
	}
  	
  	
	public BigDecimal getIdPagamento() {
		return idPagamento;
	}
	
	public void setIdPagamento(BigDecimal idPagamento) {
		this.idPagamento = idPagamento;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdProgettoBck() {
		return idProgettoBck;
	}
	
	public void setIdProgettoBck(BigDecimal idProgettoBck) {
		this.idProgettoBck = idProgettoBck;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}
	
	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
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
		if (idPagamento != null && idDocumentoDiSpesa != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idPagamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPagamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgettoBck);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgettoBck: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDocumentoDiSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDocumentoDiSpesa: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idPagamento");
		
			pk.add("idDocumentoDiSpesa");
		
	    return pk;
	}
	
	
}
