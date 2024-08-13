/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRPagamentoDichSpesaVO extends GenericVO {

  	
  	private BigDecimal idPagamento;
  	
  	private BigDecimal idDichiarazioneSpesa;
  	
	public PbandiRPagamentoDichSpesaVO() {}
  	
	public PbandiRPagamentoDichSpesaVO (BigDecimal idPagamento, BigDecimal idDichiarazioneSpesa) {
	
		this. idPagamento =  idPagamento;
		this. idDichiarazioneSpesa =  idDichiarazioneSpesa;
	}
  	
  	
	public BigDecimal getIdPagamento() {
		return idPagamento;
	}
	
	public void setIdPagamento(BigDecimal idPagamento) {
		this.idPagamento = idPagamento;
	}
	
	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	
	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idPagamento != null && idDichiarazioneSpesa != null ) {
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
	    
	    temp = DataFilter.removeNull( idDichiarazioneSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDichiarazioneSpesa: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idPagamento");
		
			pk.add("idDichiarazioneSpesa");
		
	    return pk;
	}
	
	
}
