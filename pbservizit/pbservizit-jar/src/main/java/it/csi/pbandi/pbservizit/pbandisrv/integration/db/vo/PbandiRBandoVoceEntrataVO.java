/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiRBandoVoceEntrataVO extends GenericVO {

  	
  	private BigDecimal idVoceDiEntrata;
  	
  	private BigDecimal idBando;

  	private BigDecimal idUtenteIns;

  	private BigDecimal idUtenteAgg;
  	
	public PbandiRBandoVoceEntrataVO() {}
  	
	public PbandiRBandoVoceEntrataVO (BigDecimal idVoceDiEntrata, BigDecimal idBando) {
	
		this. idVoceDiEntrata =  idVoceDiEntrata;
		this. idBando =  idBando;
	}
  	
	public PbandiRBandoVoceEntrataVO (BigDecimal idVoceDiEntrata, BigDecimal idBando, BigDecimal idUtenteAgg, BigDecimal idUtenteIns) {
		this. idVoceDiEntrata =  idVoceDiEntrata;
		this. idBando =  idBando;
		this. idUtenteAgg =  idUtenteAgg;
		this. idUtenteIns =  idUtenteIns;
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
		if (idVoceDiEntrata != null && idBando != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();		
			pk.add("idVoceDiEntrata");		
			pk.add("idBando");		
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idVoceDiEntrata);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idVoceDiEntrata: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBando: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdVoceDiEntrata() {
		return idVoceDiEntrata;
	}

	public void setIdVoceDiEntrata(BigDecimal idVoceDiEntrata) {
		this.idVoceDiEntrata = idVoceDiEntrata;
	}

	public BigDecimal getIdBando() {
		return idBando;
	}

	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
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
	
}
