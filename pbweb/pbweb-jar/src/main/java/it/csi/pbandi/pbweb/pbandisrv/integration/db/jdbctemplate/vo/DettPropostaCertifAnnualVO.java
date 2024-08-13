/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class DettPropostaCertifAnnualVO extends GenericVO {
	
	public BigDecimal idDettPropCertAnnual;
	public BigDecimal idDettPropostaCertif;
	public BigDecimal idPropostaCertificaz; 
	public BigDecimal importoRevocheRilevCum; 
	public BigDecimal importoRecuperiCum; 
	public BigDecimal importoSoppressioniCum; 
	public BigDecimal importoErogazioniCum;
	public BigDecimal importoPagamValidCum; 
	public BigDecimal importoCertifNettoAnnual; 
	public Date dataAgg;
	public BigDecimal idUtenteAgg; 
	public BigDecimal certificatoLordoCumulato; 
	public BigDecimal certificatoNettoCumulato; 
	public BigDecimal colonnaC;
	private String 		descBreveStatoPropostaCert;
	private String 		descStatoPropostaCertif;
	private String 		descProposta;
	private BigDecimal 		idStatoPropostaCertif;
	private String 		beneficiario;
  	private String 		codiceProgetto;
  	private BigDecimal 	idLineaDiIntervento;
  	private String 		nomeBandoLinea;
  	private BigDecimal  idProgetto;
  	private String 		asse;
  	  	
	public String getAsse() {
		return asse;
	}

	public void setAsse(String asse) {
		this.asse = asse;
	}

	public void setDescBreveStatoPropostaCert(String descBreveStatoPropostaCert) {
		this.descBreveStatoPropostaCert = descBreveStatoPropostaCert;
	}

	public String getDescBreveStatoPropostaCert() {
		return descBreveStatoPropostaCert;
	}

	public void setDescStatoPropostaCertif(String descStatoPropostaCertif) {
		this.descStatoPropostaCertif = descStatoPropostaCertif;
	}

	public String getDescStatoPropostaCertif() {
		return descStatoPropostaCertif;
	}

	public void setDescProposta(String descProposta) {
		this.descProposta = descProposta;
	}

	public String getDescProposta() {
		return descProposta;
	}

	public void setIdStatoPropostaCertif(BigDecimal idStatoPropostaCertif) {
		this.idStatoPropostaCertif = idStatoPropostaCertif;
	}

	public BigDecimal getIdStatoPropostaCertif() {
		return idStatoPropostaCertif;
	}

	
	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public String getBeneficiario() {
		return beneficiario;
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

	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}

	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getImportoRevocheRilevCum() {
		return importoRevocheRilevCum;
	}

	public void setImportoRevocheRilevCum(BigDecimal importoRevocheRilevCum) {
		this.importoRevocheRilevCum = importoRevocheRilevCum;
	}

	public BigDecimal getImportoRecuperiCum() {
		return importoRecuperiCum;
	}

	public void setImportoRecuperiCum(BigDecimal importoRecuperiCum) {
		this.importoRecuperiCum = importoRecuperiCum;
	}

	public BigDecimal getImportoSoppressioniCum() {
		return importoSoppressioniCum;
	}

	public void setImportoSoppressioniCum(BigDecimal importoSoppressioniCum) {
		this.importoSoppressioniCum = importoSoppressioniCum;
	}

	public BigDecimal getImportoErogazioniCum() {
		return importoErogazioniCum;
	}

	public void setImportoErogazioniCum(BigDecimal importoErogazioniCum) {
		this.importoErogazioniCum = importoErogazioniCum;
	}

	public BigDecimal getImportoPagamValidCum() {
		return importoPagamValidCum;
	}

	public void setImportoPagamValidCum(BigDecimal importoPagamValidCum) {
		this.importoPagamValidCum = importoPagamValidCum;
	}

	public BigDecimal getImportoCertifNettoAnnual() {
		return importoCertifNettoAnnual;
	}

	public void setImportoCertifNettoAnnual(BigDecimal importoCertifNettoAnnual) {
		this.importoCertifNettoAnnual = importoCertifNettoAnnual;
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

	public BigDecimal getCertificatoLordoCumulato() {
		return certificatoLordoCumulato;
	}

	public void setCertificatoLordoCumulato(BigDecimal certificatoLordoCumulato) {
		this.certificatoLordoCumulato = certificatoLordoCumulato;
	}

	public BigDecimal getCertificatoNettoCumulato() {
		return certificatoNettoCumulato;
	}

	public void setCertificatoNettoCumulato(BigDecimal certificatoNettoCumulato) {
		this.certificatoNettoCumulato = certificatoNettoCumulato;
	}

	public BigDecimal getColonnaC() {
		return colonnaC;
	}

	public void setColonnaC(BigDecimal colonnaC) {
		this.colonnaC = colonnaC;
	}
	
}
