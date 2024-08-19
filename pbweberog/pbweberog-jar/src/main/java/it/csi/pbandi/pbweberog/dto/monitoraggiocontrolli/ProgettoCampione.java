/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.monitoraggiocontrolli;

public class ProgettoCampione implements java.io.Serializable {

	private java.lang.Long idProgetto;
	private java.lang.String codiceVisualizzatoProgetto;
	private java.lang.String denominazioneBeneficiario;
	private java.lang.String nomeBandoLinea;
	private java.lang.String titoloProgetto;
	private java.lang.String dataCampionamento;

	private static final long serialVersionUID = 1L;

	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(java.lang.Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public java.lang.String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	public void setCodiceVisualizzatoProgetto(java.lang.String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}

	public java.lang.String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}

	public void setDenominazioneBeneficiario(java.lang.String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}

	public java.lang.String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(java.lang.String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

	public java.lang.String getTitoloProgetto() {
		return titoloProgetto;
	}

	public void setTitoloProgetto(java.lang.String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}

	public java.lang.String getDataCampionamento() {
		return dataCampionamento;
	}

	public void setDataCampionamento(java.lang.String dataCampionamento) {
		this.dataCampionamento = dataCampionamento;
	}

	public ProgettoCampione() {
		super();
	}

	public String toString() {
		return super.toString();
	}
}
