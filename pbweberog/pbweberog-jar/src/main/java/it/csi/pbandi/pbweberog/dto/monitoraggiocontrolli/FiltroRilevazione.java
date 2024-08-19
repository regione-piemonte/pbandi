/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.monitoraggiocontrolli;

public class FiltroRilevazione implements java.io.Serializable {

	private String idAnnoContabile;
	private String idAutoritaControllante;
	private String tipoControllo;
	private String elencoProgetti;
	private String dataCampione;
	private String idLineaIntervento;
	private String idAsse;
	private String idBando;
	private String descAnnoContabile;
	private static final long serialVersionUID = 1L;

	
	
	public String getIdAnnoContabile() {
		return idAnnoContabile;
	}

	public void setIdAnnoContabile(String idAnnoContabile) {
		this.idAnnoContabile = idAnnoContabile;
	}

	public String getIdAutoritaControllante() {
		return idAutoritaControllante;
	}

	public void setIdAutoritaControllante(String idAutoritaControllante) {
		this.idAutoritaControllante = idAutoritaControllante;
	}

	public String getTipoControllo() {
		return tipoControllo;
	}

	public void setTipoControllo(String tipoControllo) {
		this.tipoControllo = tipoControllo;
	}

	public String getElencoProgetti() {
		return elencoProgetti;
	}

	public void setElencoProgetti(String elencoProgetti) {
		this.elencoProgetti = elencoProgetti;
	}

	public String getDataCampione() {
		return dataCampione;
	}

	public void setDataCampione(String dataCampione) {
		this.dataCampione = dataCampione;
	}

	public String getIdLineaIntervento() {
		return idLineaIntervento;
	}

	public void setIdLineaIntervento(String idLineaIntervento) {
		this.idLineaIntervento = idLineaIntervento;
	}

	public String getIdAsse() {
		return idAsse;
	}

	public void setIdAsse(String idAsse) {
		this.idAsse = idAsse;
	}

	public String getIdBando() {
		return idBando;
	}

	public void setIdBando(String idBando) {
		this.idBando = idBando;
	}

	public String getDescAnnoContabile() {
		return descAnnoContabile;
	}

	public void setDescAnnoContabile(String descAnnoContabile) {
		this.descAnnoContabile = descAnnoContabile;
	}

	public FiltroRilevazione() {
		super();

	}

	public String toString() {
		return "FiltroRilevazione [idAnnoContabile=" + idAnnoContabile
				+ ", idAutoritaControllante=" + idAutoritaControllante
				+ ", tipoControllo=" + tipoControllo + ", elencoProgetti="
				+ elencoProgetti + ", dataCampione=" + dataCampione
				+ ", idLineaIntervento=" + idLineaIntervento + ", idAsse="
				+ idAsse + ", idBando=" + idBando + "]";
		
	}
}
