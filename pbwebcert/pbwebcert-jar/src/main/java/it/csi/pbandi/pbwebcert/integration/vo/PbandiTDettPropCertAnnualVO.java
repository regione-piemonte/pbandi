/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class PbandiTDettPropCertAnnualVO {
	private BigDecimal idDettPropCertAnnual;
	private BigDecimal idDettPropostaCertif;
	private BigDecimal idPropostaCertificaz;	
	private Double certificatoLordoCumulato;
	private Double certificatoNettoCumulato;
	private Double colonnaC;
	private Double importoCertifNettoAnnual;
	private Double importoErogazioniCum;
	private Double importoPagamValidCum;
	private Double importoRecuperiCum;
	private Double importoRevocheRilevCum;
	private Double importoSoppressioniCum;
	private Date dataAgg;
	private BigDecimal idUtenteAgg;
	
	public PbandiTDettPropCertAnnualVO() {}
  	
	public PbandiTDettPropCertAnnualVO (BigDecimal idDettPropCertAnnual,
			BigDecimal idDettPropostaCertif,
			BigDecimal idPropostaCertificaz,
			Double certificatoLordoCumulato,
			Double certificatoNettoCumulato,
			Double colonnaC,
			Double importoCertifNettoAnnual,
			Double importoErogazioniCum,
			Double importoPagamValidCum,
			Double importoRecuperiCum,
			Double importoRevocheRilevCum,
			Double importoSoppressioniCum,
			Date dataAgg,
			BigDecimal idUtenteAgg) {
		this. idDettPropCertAnnual =  idDettPropCertAnnual;
	}
	
	public PbandiTDettPropCertAnnualVO (BigDecimal idDettPropCertAnnual) {
		this. idDettPropCertAnnual =  idDettPropCertAnnual;
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
		if (idDettPropCertAnnual != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
			pk.add("idDettPropCertAnnual");		
	    return pk;
	}

	public BigDecimal getIdDettPropCertAnnual() {
		return idDettPropCertAnnual;
	}

	public void setIdDettPropCertAnnual(BigDecimal idDettPropCertAnnual) {
		this.idDettPropCertAnnual = idDettPropCertAnnual;
	}

	public BigDecimal getIdDettPropostaCertif() {
		return idDettPropostaCertif;
	}

	public void setIdDettPropostaCertif(BigDecimal idDettPropostaCertif) {
		this.idDettPropostaCertif = idDettPropostaCertif;
	}

	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}

	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}

	public Double getCertificatoLordoCumulato() {
		return certificatoLordoCumulato;
	}

	public void setCertificatoLordoCumulato(Double certificatoLordoCumulato) {
		this.certificatoLordoCumulato = certificatoLordoCumulato;
	}

	public Double getCertificatoNettoCumulato() {
		return certificatoNettoCumulato;
	}

	public void setCertificatoNettoCumulato(Double certificatoNettoCumulato) {
		this.certificatoNettoCumulato = certificatoNettoCumulato;
	}

	public Double getColonnaC() {
		return colonnaC;
	}

	public void setColonnaC(Double colonnaC) {
		this.colonnaC = colonnaC;
	}

	public Double getImportoCertifNettoAnnual() {
		return importoCertifNettoAnnual;
	}

	public void setImportoCertifNettoAnnual(Double importoCertifNettoAnnual) {
		this.importoCertifNettoAnnual = importoCertifNettoAnnual;
	}

	public Double getImportoErogazioniCum() {
		return importoErogazioniCum;
	}

	public void setImportoErogazioniCum(Double importoErogazioniCum) {
		this.importoErogazioniCum = importoErogazioniCum;
	}

	public Double getImportoPagamValidCum() {
		return importoPagamValidCum;
	}

	public void setImportoPagamValidCum(Double importoPagamValidCum) {
		this.importoPagamValidCum = importoPagamValidCum;
	}

	public Double getImportoRecuperiCum() {
		return importoRecuperiCum;
	}

	public void setImportoRecuperiCum(Double importoRecuperiCum) {
		this.importoRecuperiCum = importoRecuperiCum;
	}

	public Double getImportoRevocheRilevCum() {
		return importoRevocheRilevCum;
	}

	public void setImportoRevocheRilevCum(Double importoRevocheRilevCum) {
		this.importoRevocheRilevCum = importoRevocheRilevCum;
	}

	public Double getImportoSoppressioniCum() {
		return importoSoppressioniCum;
	}

	public void setImportoSoppressioniCum(Double importoSoppressioniCum) {
		this.importoSoppressioniCum = importoSoppressioniCum;
	}

	public Date getDataAgg() {
		return dataAgg;
	}

	public void setDataAgg(Date dataAgg) {
		this.dataAgg = dataAgg;
	}

	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

}
