/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRVoceSpesaCorrelataVO extends GenericVO {

  	
  	private BigDecimal progrVoceSpesaCorrelata;
  	
  	private BigDecimal idVoceDiSpesaDiPartenza;
  	
  	private BigDecimal percConfronto;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idVoceDiSpesaCorrelata;
  	
  	private BigDecimal idTipoConfronto;
  	
	public PbandiRVoceSpesaCorrelataVO() {}
  	
	public PbandiRVoceSpesaCorrelataVO (BigDecimal progrVoceSpesaCorrelata) {
	
		this. progrVoceSpesaCorrelata =  progrVoceSpesaCorrelata;
	}
  	
	public PbandiRVoceSpesaCorrelataVO (BigDecimal progrVoceSpesaCorrelata, BigDecimal idVoceDiSpesaDiPartenza, BigDecimal percConfronto, BigDecimal idUtenteAgg, BigDecimal idUtenteIns, BigDecimal idVoceDiSpesaCorrelata, BigDecimal idTipoConfronto) {
	
		this. progrVoceSpesaCorrelata =  progrVoceSpesaCorrelata;
		this. idVoceDiSpesaDiPartenza =  idVoceDiSpesaDiPartenza;
		this. percConfronto =  percConfronto;
		this. idUtenteAgg =  idUtenteAgg;
		this. idUtenteIns =  idUtenteIns;
		this. idVoceDiSpesaCorrelata =  idVoceDiSpesaCorrelata;
		this. idTipoConfronto =  idTipoConfronto;
	}
  	
  	
	public BigDecimal getProgrVoceSpesaCorrelata() {
		return progrVoceSpesaCorrelata;
	}
	
	public void setProgrVoceSpesaCorrelata(BigDecimal progrVoceSpesaCorrelata) {
		this.progrVoceSpesaCorrelata = progrVoceSpesaCorrelata;
	}
	
	public BigDecimal getIdVoceDiSpesaDiPartenza() {
		return idVoceDiSpesaDiPartenza;
	}
	
	public void setIdVoceDiSpesaDiPartenza(BigDecimal idVoceDiSpesaDiPartenza) {
		this.idVoceDiSpesaDiPartenza = idVoceDiSpesaDiPartenza;
	}
	
	public BigDecimal getPercConfronto() {
		return percConfronto;
	}
	
	public void setPercConfronto(BigDecimal percConfronto) {
		this.percConfronto = percConfronto;
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
	
	public BigDecimal getIdVoceDiSpesaCorrelata() {
		return idVoceDiSpesaCorrelata;
	}
	
	public void setIdVoceDiSpesaCorrelata(BigDecimal idVoceDiSpesaCorrelata) {
		this.idVoceDiSpesaCorrelata = idVoceDiSpesaCorrelata;
	}
	
	public BigDecimal getIdTipoConfronto() {
		return idTipoConfronto;
	}
	
	public void setIdTipoConfronto(BigDecimal idTipoConfronto) {
		this.idTipoConfronto = idTipoConfronto;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idVoceDiSpesaDiPartenza != null && idUtenteIns != null && idVoceDiSpesaCorrelata != null && idTipoConfronto != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrVoceSpesaCorrelata != null ) {
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
	    
	    temp = DataFilter.removeNull( idVoceDiSpesaDiPartenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idVoceDiSpesaDiPartenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percConfronto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percConfronto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idVoceDiSpesaCorrelata);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idVoceDiSpesaCorrelata: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoConfronto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoConfronto: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("progrVoceSpesaCorrelata");
		
	    return pk;
	}
	
	
}
