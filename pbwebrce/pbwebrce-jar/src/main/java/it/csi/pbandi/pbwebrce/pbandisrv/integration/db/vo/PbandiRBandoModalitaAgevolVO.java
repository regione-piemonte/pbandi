/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRBandoModalitaAgevolVO extends GenericVO {

  	
  	private BigDecimal idModalitaAgevolazione;
  	
  	private BigDecimal massimoImportoAgevolato;
  	
  	private BigDecimal idBando;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal percentualeImportoAgevolato;
  	
  	private BigDecimal idUtenteIns;
  	
  	private String flagLiquidazione;
  	
  	private BigDecimal minimoImportoAgevolato;
  	
	public PbandiRBandoModalitaAgevolVO() {}
  	
	public PbandiRBandoModalitaAgevolVO (BigDecimal idModalitaAgevolazione, BigDecimal idBando) {
	
		this. idModalitaAgevolazione =  idModalitaAgevolazione;
		this. idBando =  idBando;
	}
  	
	public PbandiRBandoModalitaAgevolVO (BigDecimal idModalitaAgevolazione, BigDecimal massimoImportoAgevolato, BigDecimal idBando, BigDecimal idUtenteAgg, BigDecimal percentualeImportoAgevolato, BigDecimal idUtenteIns, String flagLiquidazione, BigDecimal minimoImportoAgevolato) {
	
		this. idModalitaAgevolazione =  idModalitaAgevolazione;
		this. massimoImportoAgevolato =  massimoImportoAgevolato;
		this. idBando =  idBando;
		this. idUtenteAgg =  idUtenteAgg;
		this. percentualeImportoAgevolato =  percentualeImportoAgevolato;
		this. idUtenteIns =  idUtenteIns;
		this. flagLiquidazione = flagLiquidazione;
		this. minimoImportoAgevolato =  minimoImportoAgevolato;
	}
  	
  	
	public BigDecimal getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	
	public void setIdModalitaAgevolazione(BigDecimal idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	
	public BigDecimal getMassimoImportoAgevolato() {
		return massimoImportoAgevolato;
	}
	
	public void setMassimoImportoAgevolato(BigDecimal massimoImportoAgevolato) {
		this.massimoImportoAgevolato = massimoImportoAgevolato;
	}
	
	public BigDecimal getIdBando() {
		return idBando;
	}
	
	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getPercentualeImportoAgevolato() {
		return percentualeImportoAgevolato;
	}
	
	public void setPercentualeImportoAgevolato(BigDecimal percentualeImportoAgevolato) {
		this.percentualeImportoAgevolato = percentualeImportoAgevolato;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getFlagLiquidazione() {
		return flagLiquidazione;
	}

	public void setFlagLiquidazione(String flagLiquidazione) {
		this.flagLiquidazione = flagLiquidazione;
	}

	public BigDecimal getMinimoImportoAgevolato() {
		return minimoImportoAgevolato;
	}

	public void setMinimoImportoAgevolato(BigDecimal minimoImportoAgevolato) {
		this.minimoImportoAgevolato = minimoImportoAgevolato;
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
		if (idModalitaAgevolazione != null && idBando != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idModalitaAgevolazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idModalitaAgevolazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( massimoImportoAgevolato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" massimoImportoAgevolato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBando: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percentualeImportoAgevolato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percentualeImportoAgevolato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagLiquidazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagLiquidazione: " + temp + "\t\n");	 
	    
	    temp = DataFilter.removeNull( minimoImportoAgevolato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" minimoImportoAgevolato: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idModalitaAgevolazione");
		
			pk.add("idBando");
		
	    return pk;
	}
	
	
}
