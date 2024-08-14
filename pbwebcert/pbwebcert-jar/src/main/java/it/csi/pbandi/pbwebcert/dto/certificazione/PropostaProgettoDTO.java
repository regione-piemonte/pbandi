/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.dto.certificazione;

public class PropostaProgettoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long idDettPropostaCertif;
	private String codiceProgetto;
	private String denominazioneBeneficiario;
	private Double importoCertificazioneNetto;
	private Double importoErogato;
	private Double importoPagamenti;
	private Long idPreviewDettPropCer;
	private Double importoRevoche;
	private Boolean flagAttivo;
	private String descLinea;
	private String descBreveLinea;
	
	public void setIdDettPropostaCertif(Long val) {
		idDettPropostaCertif = val;
	}

	public Long getIdDettPropostaCertif() {
		return idDettPropostaCertif;
	}

	public void setImportoErogato(Double val) {
		importoErogato = val;
	}

	public Double getImportoErogato() {
		return importoErogato;
	}

	

	public void setImportoPagamenti(Double val) {
		importoPagamenti = val;
	}

	public Double getImportoPagamenti() {
		return importoPagamenti;
	}


	public void setIdPreviewDettPropCer(Long val) {
		idPreviewDettPropCer = val;
	}

	public Long getIdPreviewDettPropCer() {
		return idPreviewDettPropCer;
	}

	public void setImportoRevoche(Double val) {
		importoRevoche = val;
	}

	public Double getImportoRevoche() {
		return importoRevoche;
	}


	public void setDescLinea(String val) {
		descLinea = val;
	}

	public String getDescLinea() {
		return descLinea;
	}


	public void setDescBreveLinea(String val) {
		descBreveLinea = val;
	}

	public String getDescBreveLinea() {
		return descBreveLinea;
	}

	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}

	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}

	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}

	public Boolean getFlagAttivo() {
		return flagAttivo;
	}

	public void setFlagAttivo(Boolean flagAttivo) {
		this.flagAttivo = flagAttivo;
	}

	public Double getImportoCertificazioneNetto() {
		return importoCertificazioneNetto;
	}

	public void setImportoCertificazioneNetto(Double importoCertificazioneNetto) {
		this.importoCertificazioneNetto = importoCertificazioneNetto;
	}


}
