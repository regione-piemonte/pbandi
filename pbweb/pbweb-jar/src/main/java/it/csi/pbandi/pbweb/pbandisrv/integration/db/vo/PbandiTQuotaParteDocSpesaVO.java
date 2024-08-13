/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTQuotaParteDocSpesaVO extends GenericVO {

  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal importoQuotaParteDocSpesa;
  	
  	private BigDecimal oreLavorate;
  	
  	private BigDecimal idProgetto;
  	
  	private BigDecimal idRigoContoEconomico;
  	
  	private BigDecimal idDocumentoDiSpesa;
  	
  	private BigDecimal idQuotaParteDocSpesa;
  	
  	private BigDecimal workPackages;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiTQuotaParteDocSpesaVO() {}
  	
	public PbandiTQuotaParteDocSpesaVO (BigDecimal idQuotaParteDocSpesa) {
	
		this. idQuotaParteDocSpesa =  idQuotaParteDocSpesa;
	}
  	
	public PbandiTQuotaParteDocSpesaVO (BigDecimal idUtenteAgg, BigDecimal importoQuotaParteDocSpesa, BigDecimal oreLavorate, BigDecimal idProgetto, BigDecimal idRigoContoEconomico, BigDecimal idDocumentoDiSpesa, BigDecimal idQuotaParteDocSpesa, BigDecimal workPackages, BigDecimal idUtenteIns) {
	
		this. idUtenteAgg =  idUtenteAgg;
		this. importoQuotaParteDocSpesa =  importoQuotaParteDocSpesa;
		this. oreLavorate =  oreLavorate;
		this. idProgetto =  idProgetto;
		this. idRigoContoEconomico =  idRigoContoEconomico;
		this. idDocumentoDiSpesa =  idDocumentoDiSpesa;
		this. idQuotaParteDocSpesa =  idQuotaParteDocSpesa;
		this. workPackages =  workPackages;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getImportoQuotaParteDocSpesa() {
		return importoQuotaParteDocSpesa;
	}
	
	public void setImportoQuotaParteDocSpesa(BigDecimal importoQuotaParteDocSpesa) {
		this.importoQuotaParteDocSpesa = importoQuotaParteDocSpesa;
	}
	
	public BigDecimal getOreLavorate() {
		return oreLavorate;
	}
	
	public void setOreLavorate(BigDecimal oreLavorate) {
		this.oreLavorate = oreLavorate;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public BigDecimal getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}
	
	public void setIdRigoContoEconomico(BigDecimal idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}
	
	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}
	
	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}
	
	public BigDecimal getIdQuotaParteDocSpesa() {
		return idQuotaParteDocSpesa;
	}
	
	public void setIdQuotaParteDocSpesa(BigDecimal idQuotaParteDocSpesa) {
		this.idQuotaParteDocSpesa = idQuotaParteDocSpesa;
	}
	
	public BigDecimal getWorkPackages() {
		return workPackages;
	}
	
	public void setWorkPackages(BigDecimal workPackages) {
		this.workPackages = workPackages;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && importoQuotaParteDocSpesa != null && idProgetto != null && idRigoContoEconomico != null && idDocumentoDiSpesa != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idQuotaParteDocSpesa != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoQuotaParteDocSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoQuotaParteDocSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( oreLavorate);
	    if (!DataFilter.isEmpty(temp)) sb.append(" oreLavorate: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRigoContoEconomico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRigoContoEconomico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDocumentoDiSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDocumentoDiSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idQuotaParteDocSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idQuotaParteDocSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( workPackages);
	    if (!DataFilter.isEmpty(temp)) sb.append(" workPackages: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idQuotaParteDocSpesa");
		
	    return pk;
	}
	
	
}
