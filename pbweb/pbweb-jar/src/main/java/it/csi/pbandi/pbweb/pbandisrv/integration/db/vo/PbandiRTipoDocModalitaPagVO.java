/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRTipoDocModalitaPagVO extends GenericVO {

  	
  	private BigDecimal idTipoDocumentoSpesa;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idModalitaPagamento;
  	
	public PbandiRTipoDocModalitaPagVO() {}
  	
	public PbandiRTipoDocModalitaPagVO (BigDecimal idTipoDocumentoSpesa, BigDecimal idModalitaPagamento) {
	
		this. idTipoDocumentoSpesa =  idTipoDocumentoSpesa;
		this. idModalitaPagamento =  idModalitaPagamento;
	}
  	
	public PbandiRTipoDocModalitaPagVO (BigDecimal idTipoDocumentoSpesa, BigDecimal idUtenteAgg, BigDecimal idUtenteIns, BigDecimal idModalitaPagamento) {
	
		this. idTipoDocumentoSpesa =  idTipoDocumentoSpesa;
		this. idUtenteAgg =  idUtenteAgg;
		this. idUtenteIns =  idUtenteIns;
		this. idModalitaPagamento =  idModalitaPagamento;
	}
  	
  	
	public BigDecimal getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}
	
	public void setIdTipoDocumentoSpesa(BigDecimal idTipoDocumentoSpesa) {
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdModalitaPagamento() {
		return idModalitaPagamento;
	}
	
	public void setIdModalitaPagamento(BigDecimal idModalitaPagamento) {
		this.idModalitaPagamento = idModalitaPagamento;
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
		if (idTipoDocumentoSpesa != null && idModalitaPagamento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoDocumentoSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDocumentoSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idModalitaPagamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idModalitaPagamento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoDocumentoSpesa");
		
			pk.add("idModalitaPagamento");
		
	    return pk;
	}
	
	
}
