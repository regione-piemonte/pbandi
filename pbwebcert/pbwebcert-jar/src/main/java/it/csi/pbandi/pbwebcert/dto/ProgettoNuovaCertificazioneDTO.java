/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.dto;

public class ProgettoNuovaCertificazioneDTO {
	static final long serialVersionUID = 1;

	private Long idStatoProgetto;
	private Double importoCertificazioneNetto;
	private String titoloProgetto;
	private Long idProgetto;
	private String nomeBandoLinea;
	private String codiceProgetto;
	private String denominazioneBeneficiario;
	private Long idDettPropostaCertif;
	private Double impCertifNettoPremodifica;
	private Long idLineaDiIntervento;
	private String nota;
	
	public void setIdStatoProgetto(Long val) {
		idStatoProgetto = val;
	}

	public Long getIdStatoProgetto() {
		return idStatoProgetto;
	}

	public void setImportoCertificazioneNetto(Double val) {
		importoCertificazioneNetto = val;
	}

	public Double getImportoCertificazioneNetto() {
		return importoCertificazioneNetto;
	}

	public void setTitoloProgetto(String val) {
		titoloProgetto = val;
	}

	public String getTitoloProgetto() {
		return titoloProgetto;
	}

	public void setIdProgetto(Long val) {
		idProgetto = val;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setNomeBandoLinea(String val) {
		nomeBandoLinea = val;
	}

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setCodiceProgetto(String val) {
		codiceProgetto = val;
	}

	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	public void setDenominazioneBeneficiario(String val) {
		denominazioneBeneficiario = val;
	}

	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}

	public void setImpCertifNettoPremodifica(Double val) {
		impCertifNettoPremodifica = val;
	}

	public Double getImpCertifNettoPremodifica() {
		return impCertifNettoPremodifica;
	}

	public void setIdDettPropostaCertif(Long val) {
		idDettPropostaCertif = val;
	}

	public Long getIdDettPropostaCertif() {
		return idDettPropostaCertif;
	}

	public void setIdLineaDiIntervento(Long val) {
		idLineaDiIntervento = val;
	}

	public Long getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setNota(String val) {
		nota = val;
	}

	public String getNota() {
		return nota;
	}
}
