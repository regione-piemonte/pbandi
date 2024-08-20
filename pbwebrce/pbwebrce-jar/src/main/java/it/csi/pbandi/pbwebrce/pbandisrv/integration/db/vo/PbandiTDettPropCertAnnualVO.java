/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;

public class PbandiTDettPropCertAnnualVO extends GenericVO {

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
	
	/*  Alex: 13/12/2019: codice originale del 2018 che non corrisponde pi� alla tabella

  	private BigDecimal idDettPropCertAnnual;

  	private BigDecimal importoCertificazioneNetto;
  	
  	private BigDecimal idDettPropostaCertif;
  	
  	private BigDecimal idPropostaCertificaz;
  	
  	private BigDecimal importoSoppressioni;
  	
  	private BigDecimal importoRecuperi;
  	  	
  	private BigDecimal importoRevoche;
  	  	
  	private BigDecimal importoErogazioni;
  	  	
  	private BigDecimal importoPagamentiValidati;

	public PbandiTDettPropCertAnnualVO() {}
  	
	public PbandiTDettPropCertAnnualVO (BigDecimal idDettPropCertAnnual) {
	
		this. idDettPropCertAnnual =  idDettPropCertAnnual;
	}
  	
	public PbandiTDettPropCertAnnualVO (BigDecimal importoCertificazioneNetto, BigDecimal idDettPropostaCertif, BigDecimal importoSoppressioni, BigDecimal importoRevoche, BigDecimal importoErogazioni, BigDecimal idDettPropCertAnnual, BigDecimal importoPagamentiValidati, BigDecimal idPropostaCertificaz) {
	
		this. idDettPropCertAnnual =  idDettPropCertAnnual;
		this. importoCertificazioneNetto =  importoCertificazioneNetto;
		this. idDettPropostaCertif =  idDettPropostaCertif;
		this. importoSoppressioni =  importoSoppressioni;
		this. importoRevoche =  importoRevoche;
		this. importoErogazioni =  importoErogazioni;
		this. importoPagamentiValidati =  importoPagamentiValidati;
		this. idPropostaCertificaz =  idPropostaCertificaz;
	}
	
	public BigDecimal getIdDettPropCertAnnual() {
		return idDettPropCertAnnual;
	}

	public void setIdDettPropCertAnnual(BigDecimal idDettPropCertAnnual) {
		this.idDettPropCertAnnual = idDettPropCertAnnual;
	}

	public BigDecimal getImportoCertificazioneNetto() {
		return importoCertificazioneNetto;
	}

	public void setImportoCertificazioneNetto(BigDecimal importoCertificazioneNetto) {
		this.importoCertificazioneNetto = importoCertificazioneNetto;
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

	public BigDecimal getImportoSoppressioni() {
		return importoSoppressioni;
	}

	public void setImportoSoppressioni(BigDecimal importoSoppressioni) {
		this.importoSoppressioni = importoSoppressioni;
	}

	public BigDecimal getImportoRecuperi() {
		return importoRecuperi;
	}

	public void setImportoRecuperi(BigDecimal importoRecuperi) {
		this.importoRecuperi = importoRecuperi;
	}

	public BigDecimal getImportoRevoche() {
		return importoRevoche;
	}

	public void setImportoRevoche(BigDecimal importoRevoche) {
		this.importoRevoche = importoRevoche;
	}

	public BigDecimal getImportoErogazioni() {
		return importoErogazioni;
	}

	public void setImportoErogazioni(BigDecimal importoErogazioni) {
		this.importoErogazioni = importoErogazioni;
	}

	public BigDecimal getImportoPagamentiValidati() {
		return importoPagamentiValidati;
	}

	public void setImportoPagamentiValidati(BigDecimal importoPagamentiValidati) {
		this.importoPagamentiValidati = importoPagamentiValidati;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idDettPropostaCertif != null  && importoErogazioni != null   && idPropostaCertificaz != null ) {
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
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( importoCertificazioneNetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoCertificazioneNetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDettPropostaCertif);
	    if (!DataFilter.isEmpty(temp)) sb.append(" denominazioneBeneficiario: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoSoppressioni);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoSoppressioni: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoRevoche);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoRevoche: " + temp + "\t\n");
	  
	    temp = DataFilter.removeNull( importoErogazioni);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoErogazioni: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDettPropCertAnnual);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDettPropCertAnnual: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoPagamentiValidati);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoPagamentiValidati: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPropostaCertificaz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPropostaCertificaz: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idDettPropCertAnnual");
		
	    return pk;
	}
	*/
	
}
