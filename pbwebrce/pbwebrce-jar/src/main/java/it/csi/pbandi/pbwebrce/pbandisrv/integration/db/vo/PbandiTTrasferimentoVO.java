/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;


public class PbandiTTrasferimentoVO extends GenericVO {

	private BigDecimal idTrasferimento;
	private String codiceTrasferimento;
	private Date dtTrasferimento;
	private BigDecimal idCausaleTrasferimento;
	private BigDecimal importoTrasferimento;
	private BigDecimal idSoggettoBeneficiario;
	private String flagPubblicoPrivato;
	private BigDecimal idUtenteIns;
	private BigDecimal idUtenteAgg;
  	
	public PbandiTTrasferimentoVO() {}
  	
	public PbandiTTrasferimentoVO (BigDecimal idTrasferimento) {
	
		this. idTrasferimento =  idTrasferimento;
	}
  	
	public PbandiTTrasferimentoVO (BigDecimal idTrasferimento, String codiceTrasferimento, BigDecimal importoTrasferimento, BigDecimal idUtenteIns, BigDecimal idUtenteAgg, 
			Date dtTrasferimento, String flagPubblicoPrivato, BigDecimal idCausaleTrasferimento, BigDecimal idSoggettoBeneficiario) {
	
		this. idTrasferimento =  idTrasferimento;
		this. codiceTrasferimento =  codiceTrasferimento;
		this. importoTrasferimento =  importoTrasferimento;
		this. idUtenteIns = idUtenteIns;
		this. idUtenteAgg = idUtenteAgg;
		this. dtTrasferimento =  dtTrasferimento;
		this. flagPubblicoPrivato = flagPubblicoPrivato;
		this. idCausaleTrasferimento = idCausaleTrasferimento;
		this. idSoggettoBeneficiario = idSoggettoBeneficiario;

	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getImportoTrasferimento() {
		return importoTrasferimento;
	}
	
	public void setImportoTrasferimento(BigDecimal importoTrasferimento) {
		this.importoTrasferimento = importoTrasferimento;
	}
		
	public Date getDtTrasferimento() {
		return dtTrasferimento;
	}
	
	public void setDtTrasferimento(Date dtTrasferimento) {
		this.dtTrasferimento = dtTrasferimento;
	}	
	
	public BigDecimal getIdTrasferimento() {
		return idTrasferimento;
	}
	
	public void setIdTrasferimento(BigDecimal idTrasferimento) {
		this.idTrasferimento = idTrasferimento;
	}
	
	public String getCodiceTrasferimento() {
		return codiceTrasferimento;
	}

	public void setCodiceTrasferimento(String codiceTrasferimento) {
		this.codiceTrasferimento = codiceTrasferimento;
	}

	public BigDecimal getIdCausaleTrasferimento() {
		return idCausaleTrasferimento;
	}

	public void setIdCausaleTrasferimento(BigDecimal idCausaleTrasferimento) {
		this.idCausaleTrasferimento = idCausaleTrasferimento;
	}

	public BigDecimal getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}

	public String getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}

	public void setFlagPubblicoPrivato(String flagPubblicoPrivato) {
		this.flagPubblicoPrivato = flagPubblicoPrivato;
	}

	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idTrasferimento != null && codiceTrasferimento != null && importoTrasferimento != null && idCausaleTrasferimento != null && idSoggettoBeneficiario != null && dtTrasferimento != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTrasferimento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTrasferimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTrasferimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codiceTrasferimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceTrasferimento: " + temp + "\t\n");
	   
	    temp = DataFilter.removeNull( idCausaleTrasferimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCausaleTrasferimento: " + temp + "\t\n");
	   
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoTrasferimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoTrasferimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtTrasferimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtTrasferimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagPubblicoPrivato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagPubblicoPrivato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggettoBeneficiario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggettoBeneficiario: " + temp + "\t\n");
	    
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idTrasferimento");
		
	    return pk;
	}
	
	
}
