/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;

public class PbandiTPreviewDettPropCerVO {
 	private BigDecimal importoCertificazioneNetto;
  	private String denominazioneBeneficiario;
  	private BigDecimal idUtenteAgg;  	
  	private String nomeBandoLinea;  	
  	private BigDecimal importoRevoche;  	
  	private String codiceProgetto; 	
  	private BigDecimal idProgetto;  	
  	private BigDecimal idPreviewDettPropCer;  	
  	private BigDecimal progrBandoLineaIntervento;  	
  	private BigDecimal importoPagamenti;  	
  	private BigDecimal idUtenteIns;  	
  	private BigDecimal idSoggetto;
   	private BigDecimal idPropostaCertificaz;  	
  	private Boolean flagAttivo;
 	private BigDecimal idLineaDiIntervento;
 	
	public PbandiTPreviewDettPropCerVO() {}
  	
	public PbandiTPreviewDettPropCerVO (BigDecimal idPreviewDettPropCer) {
	
		this. idPreviewDettPropCer =  idPreviewDettPropCer;
	}
  	
	public PbandiTPreviewDettPropCerVO (BigDecimal importoCertificazioneNetto, String denominazioneBeneficiario, BigDecimal idUtenteAgg, String nomeBandoLinea, BigDecimal importoRevoche, String codiceProgetto, BigDecimal idProgetto, BigDecimal idPreviewDettPropCer, BigDecimal progrBandoLineaIntervento, BigDecimal importoPagamenti, BigDecimal idUtenteIns, BigDecimal idSoggettoBeneficiario, BigDecimal idPropostaCertificaz, Boolean flagAttivo) {
	
		this. importoCertificazioneNetto =  importoCertificazioneNetto;
		this. denominazioneBeneficiario =  denominazioneBeneficiario;
		this. idUtenteAgg =  idUtenteAgg;
		this. nomeBandoLinea =  nomeBandoLinea;
		this. importoRevoche =  importoRevoche;
		this. codiceProgetto =  codiceProgetto;
		this. idProgetto =  idProgetto;
		this. idPreviewDettPropCer =  idPreviewDettPropCer;
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this. importoPagamenti =  importoPagamenti;
		this. idUtenteIns =  idUtenteIns;
		this. idSoggetto =  idSoggettoBeneficiario;
		this. idPropostaCertificaz =  idPropostaCertificaz;
		this. flagAttivo =  flagAttivo;
	}

	public BigDecimal getImportoCertificazioneNetto() {
		return importoCertificazioneNetto;
	}

	public void setImportoCertificazioneNetto(BigDecimal importoCertificazioneNetto) {
		this.importoCertificazioneNetto = importoCertificazioneNetto;
	}

	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}

	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}

	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

	public BigDecimal getImportoRevoche() {
		return importoRevoche;
	}

	public void setImportoRevoche(BigDecimal importoRevoche) {
		this.importoRevoche = importoRevoche;
	}

	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdPreviewDettPropCer() {
		return idPreviewDettPropCer;
	}

	public void setIdPreviewDettPropCer(BigDecimal idPreviewDettPropCer) {
		this.idPreviewDettPropCer = idPreviewDettPropCer;
	}

	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public BigDecimal getImportoPagamenti() {
		return importoPagamenti;
	}

	public void setImportoPagamenti(BigDecimal importoPagamenti) {
		this.importoPagamenti = importoPagamenti;
	}

	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggetto = idSoggettoBeneficiario;
	}

	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}

	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}

	public Boolean getFlagAttivo() {
		return flagAttivo;
	}

	public void setFlagAttivo(Boolean flagAttivo) {
		this.flagAttivo = flagAttivo;
	}

	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}

	
	
}
