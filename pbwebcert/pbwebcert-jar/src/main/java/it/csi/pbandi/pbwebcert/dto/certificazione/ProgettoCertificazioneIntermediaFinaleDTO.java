/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.dto.certificazione;

import java.util.Date;

public class ProgettoCertificazioneIntermediaFinaleDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long idDettPropCertAnnual;
	private Long idDettPropostaCertif;
	private Long idPropostaCertificaz;
	private Double importoRevocheRilevCum;
	private Double importoRecuperiCum;
	private Double importoSoppressioniCum;
	private Double importoErogazioniCum;
	private Double importoPagamValidCum;
	private Double importoCertifNettoAnnual;
	private Date dataAgg;
	private Long idUtenteAgg;
	private Double certificatoNettoCumulato;
	private Double certificatoLordoCumulato;
	private Double colonnaC;
	private String descBreveStatoPropostaCert;
	private String descStatoPropostaCertif;
	private String descProposta;
	private String codiceProgetto;
	private String beneficiario;
	private String nomeBandoLinea;
	private Long idStatoPropostaCertif;
	private Long idLineaDiIntervento;
	private Long idProgetto;
	private String asse;
	private Double diffCna;
	private Double diffRev;
	private Double diffRec;
	private Double diffSoppr;
		
	public void setIdDettPropCertAnnual(Long val) {
		idDettPropCertAnnual = val;
	}
	
	public Long getIdDettPropCertAnnual() {
		return idDettPropCertAnnual;
	}

	

	public void setIdDettPropostaCertif(Long val) {
		idDettPropostaCertif = val;
	}

	public Long getIdDettPropostaCertif() {
		return idDettPropostaCertif;
	}
	
	
	
	public void setIdPropostaCertificaz(Long val) {
		idPropostaCertificaz = val;
	}

	public Long getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}

	

	public void setImportoRevocheRilevCum(Double val) {
		importoRevocheRilevCum = val;
	}

	public Double getImportoRevocheRilevCum() {
		return importoRevocheRilevCum;
	}

	

	public void setImportoRecuperiCum(Double val) {
		importoRecuperiCum = val;
	}

	public Double getImportoRecuperiCum() {
		return importoRecuperiCum;
	}

	

	public void setImportoSoppressioniCum(Double val) {
		importoSoppressioniCum = val;
	}

	public Double getImportoSoppressioniCum() {
		return importoSoppressioniCum;
	}

	

	public void setImportoErogazioniCum(Double val) {
		importoErogazioniCum = val;
	}

	public Double getImportoErogazioniCum() {
		return importoErogazioniCum;
	}



	public void setImportoPagamValidCum(Double val) {
		importoPagamValidCum = val;
	}

	public Double getImportoPagamValidCum() {
		return importoPagamValidCum;
	}

	

	public void setImportoCertifNettoAnnual(Double val) {
		importoCertifNettoAnnual = val;
	}

	public Double getImportoCertifNettoAnnual() {
		return importoCertifNettoAnnual;
	}



	public void setDataAgg(Date val) {
		dataAgg = val;
	}

	public Date getDataAgg() {
		return dataAgg;
	}



	public void setIdUtenteAgg(Long val) {
		idUtenteAgg = val;
	}

	public Long getIdUtenteAgg() {
		return idUtenteAgg;
	}

	

	public void setCertificatoNettoCumulato(Double val) {
		certificatoNettoCumulato = val;
	}


	public Double getCertificatoNettoCumulato() {
		return certificatoNettoCumulato;
	}


	public void setCertificatoLordoCumulato(Double val) {
		certificatoLordoCumulato = val;
	}

	public Double getCertificatoLordoCumulato() {
		return certificatoLordoCumulato;
	}



	public void setColonnaC(Double val) {
		colonnaC = val;
	}

	public Double getColonnaC() {
		return colonnaC;
	}



	public void setDescBreveStatoPropostaCert(String val) {
		descBreveStatoPropostaCert = val;
	}

	public String getDescBreveStatoPropostaCert() {
		return descBreveStatoPropostaCert;
	}

	

	public void setDescStatoPropostaCertif(String val) {
		descStatoPropostaCertif = val;
	}

	public String getDescStatoPropostaCertif() {
		return descStatoPropostaCertif;
	}

	

	public void setDescProposta(String val) {
		descProposta = val;
	}

	public String getDescProposta() {
		return descProposta;
	}



	public void setBeneficiario(String val) {
		beneficiario = val;
	}

	public String getBeneficiario() {
		return beneficiario;
	}



	public void setCodiceProgetto(String val) {
		codiceProgetto = val;
	}

	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

	public Long getIdStatoPropostaCertif() {
		return idStatoPropostaCertif;
	}

	public void setIdStatoPropostaCertif(Long idStatoPropostaCertif) {
		this.idStatoPropostaCertif = idStatoPropostaCertif;
	}

	public Long getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(Long idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getAsse() {
		return asse;
	}

	public void setAsse(String asse) {
		this.asse = asse;
	}

	public Double getDiffCna() {
		return diffCna;
	}

	public void setDiffCna(Double diffCna) {
		this.diffCna = diffCna;
	}

	public Double getDiffRev() {
		return diffRev;
	}

	public void setDiffRev(Double diffRev) {
		this.diffRev = diffRev;
	}

	public Double getDiffRec() {
		return diffRec;
	}

	public void setDiffRec(Double diffRec) {
		this.diffRec = diffRec;
	}

	public Double getDiffSoppr() {
		return diffSoppr;
	}

	public void setDiffSoppr(Double diffSoppr) {
		this.diffSoppr = diffSoppr;
	}




}
