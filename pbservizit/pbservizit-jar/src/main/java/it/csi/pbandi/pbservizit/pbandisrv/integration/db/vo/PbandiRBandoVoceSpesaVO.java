/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiRBandoVoceSpesaVO extends GenericVO {

  	
  	private BigDecimal idVoceDiSpesa;
  	
  	private BigDecimal massimoImportoAmmissibile;
  	
  	private BigDecimal progrOrdinamento;
  	
  	private BigDecimal idBando;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal percentualeImpAmmissibile;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiRBandoVoceSpesaVO() {}
  	
	public PbandiRBandoVoceSpesaVO (BigDecimal idVoceDiSpesa, BigDecimal idBando) {
	
		this. idVoceDiSpesa =  idVoceDiSpesa;
		this. idBando =  idBando;
	}
  	
	public PbandiRBandoVoceSpesaVO (BigDecimal idVoceDiSpesa, BigDecimal massimoImportoAmmissibile, BigDecimal progrOrdinamento, BigDecimal idBando, BigDecimal idUtenteAgg, BigDecimal percentualeImpAmmissibile, BigDecimal idUtenteIns) {
	
		this. idVoceDiSpesa =  idVoceDiSpesa;
		this. massimoImportoAmmissibile =  massimoImportoAmmissibile;
		this. progrOrdinamento =  progrOrdinamento;
		this. idBando =  idBando;
		this. idUtenteAgg =  idUtenteAgg;
		this. percentualeImpAmmissibile =  percentualeImpAmmissibile;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	
	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	
	public BigDecimal getMassimoImportoAmmissibile() {
		return massimoImportoAmmissibile;
	}
	
	public void setMassimoImportoAmmissibile(BigDecimal massimoImportoAmmissibile) {
		this.massimoImportoAmmissibile = massimoImportoAmmissibile;
	}
	
	public BigDecimal getProgrOrdinamento() {
		return progrOrdinamento;
	}
	
	public void setProgrOrdinamento(BigDecimal progrOrdinamento) {
		this.progrOrdinamento = progrOrdinamento;
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
	
	public BigDecimal getPercentualeImpAmmissibile() {
		return percentualeImpAmmissibile;
	}
	
	public void setPercentualeImpAmmissibile(BigDecimal percentualeImpAmmissibile) {
		this.percentualeImpAmmissibile = percentualeImpAmmissibile;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && progrOrdinamento != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idVoceDiSpesa != null && idBando != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idVoceDiSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idVoceDiSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( massimoImportoAmmissibile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" massimoImportoAmmissibile: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrOrdinamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrOrdinamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBando: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percentualeImpAmmissibile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percentualeImpAmmissibile: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idVoceDiSpesa");
		
			pk.add("idBando");
		
	    return pk;
	}
	
	
}
