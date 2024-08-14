/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;

public class ProgettoNuovaCertificazioneVO {
	private BigDecimal idStatoProgetto;
	private BigDecimal importoCertificazioneNetto;
	private String titoloProgetto;
	private BigDecimal idProgetto;
	private String nomeBandoLinea;
	private String codiceProgetto;
	private String denominazioneBeneficiario;
	private BigDecimal idDettPropostaCertif;
	private BigDecimal impCertifNettoPremodifica;
	private BigDecimal idLineaDiIntervento;
	private String nota;
	
	public void setIdStatoProgetto(BigDecimal val) {
		idStatoProgetto = val;
	}

	public BigDecimal getIdStatoProgetto() {
		return idStatoProgetto;
	}

	public void setImportoCertificazioneNetto(BigDecimal val) {
		importoCertificazioneNetto = val;
	}

	public BigDecimal getImportoCertificazioneNetto() {
		return importoCertificazioneNetto;
	}

	public void setTitoloProgetto(String val) {
		titoloProgetto = val;
	}

	public String getTitoloProgetto() {
		return titoloProgetto;
	}

	public void setIdProgetto(BigDecimal val) {
		idProgetto = val;
	}

	public BigDecimal getIdProgetto() {
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

	public void setImpCertifNettoPremodifica(BigDecimal val) {
		impCertifNettoPremodifica = val;
	}

	public BigDecimal getImpCertifNettoPremodifica() {
		return impCertifNettoPremodifica;
	}

	public void setIdDettPropostaCertif(BigDecimal val) {
		idDettPropostaCertif = val;
	}

	public BigDecimal getIdDettPropostaCertif() {
		return idDettPropostaCertif;
	}

	public void setIdLineaDiIntervento(BigDecimal val) {
		idLineaDiIntervento = val;
	}

	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setNota(String val) {
		nota = val;
	}

	public String getNota() {
		return nota;
	}
}
