/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.vo;

public class BeneficiarioProgettoBandoLineaRuoloVO {

	private String beneficiario;
	private String codiceFiscaleSoggetto;
	private String descBreveTipoAnagrafica;
	private String codiceVisualizzato;
	private Long idProgetto;
	private String idIstanzaProcesso;
	private Long idSoggetto;
	private String nomeBandoLinea;
	private Long progrBandoLineaIntervento;

	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}

	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getIdIstanzaProcesso() {
		return idIstanzaProcesso;
	}

	public void setIdIstanzaProcesso(String idIstanzaProcesso) {
		this.idIstanzaProcesso = idIstanzaProcesso;
	}

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

	public Long getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(Long progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}

	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}

	public String getDescBreveTipoAnagrafica() {
		return descBreveTipoAnagrafica;
	}

	public void setDescBreveTipoAnagrafica(String descBreveTipoAnagrafica) {
		this.descBreveTipoAnagrafica = descBreveTipoAnagrafica;
	}

	@Override
	public String toString() {
		return "BeneficiarioProgettoBandoLineaRuoloVO [beneficiario=" + beneficiario + ", codiceFiscaleSoggetto="
				+ codiceFiscaleSoggetto + ", descBreveTipoAnagrafica=" + descBreveTipoAnagrafica
				+ ", codiceVisualizzato=" + codiceVisualizzato + ", idProgetto=" + idProgetto + ", idIstanzaProcesso="
				+ idIstanzaProcesso + ", idSoggetto=" + idSoggetto + ", nomeBandoLinea=" + nomeBandoLinea
				+ ", progrBandoLineaIntervento=" + progrBandoLineaIntervento + "]";
	}

}
