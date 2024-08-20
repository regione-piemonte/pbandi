/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

public class MetadatiActaVO {

	private Integer idTipoMetadati;
	private String idBando;
	private String idDomanda;
	private String oggettoFascicolo;
	private String soggettoFascicolo;
	private Integer conservazioneGeneraleFascicolo;
//	private String utenteCreazioneFascicolo;
	private String dataTopicaDocumento;
	
	public MetadatiActaVO(Integer idTipoMetadati, String idBando, String idDomanda, String oggettoFascicolo,
			String soggettoFascicolo, Integer conservazioneGeneraleFascicolo, String dataTopicaDocumento) {
		super();
		this.idTipoMetadati = idTipoMetadati;
		this.idBando = idBando;
		this.idDomanda = idDomanda;
		this.oggettoFascicolo = oggettoFascicolo;
		this.soggettoFascicolo = soggettoFascicolo;
		this.conservazioneGeneraleFascicolo = conservazioneGeneraleFascicolo;
		this.dataTopicaDocumento = dataTopicaDocumento;
	}
	public Integer getIdTipoMetadati() {
		return idTipoMetadati;
	}
	public void setIdTipoMetadati(Integer idTipoMetadati) {
		this.idTipoMetadati = idTipoMetadati;
	}
	public String getIdBando() {
		return idBando;
	}
	public void setIdBando(String idBando) {
		this.idBando = idBando;
	}
	public String getIdDomanda() {
		return idDomanda;
	}
	public void setIdDomanda(String idDomanda) {
		this.idDomanda = idDomanda;
	}
	public String getOggettoFascicolo() {
		return oggettoFascicolo;
	}
	public void setOggettoFascicolo(String oggettoFascicolo) {
		this.oggettoFascicolo = oggettoFascicolo;
	}
	public String getSoggettoFascicolo() {
		return soggettoFascicolo;
	}
	public void setSoggettoFascicolo(String soggettoFascicolo) {
		this.soggettoFascicolo = soggettoFascicolo;
	}
	public Integer getConservazioneGeneraleFascicolo() {
		return conservazioneGeneraleFascicolo;
	}
	public void setConservazioneGeneraleFascicolo(Integer conservazioneGeneraleFascicolo) {
		this.conservazioneGeneraleFascicolo = conservazioneGeneraleFascicolo;
	}
	public String getDataTopicaDocumento() {
		return dataTopicaDocumento;
	}
	public void setDataTopicaDocumento(String dataTopicaDocumento) {
		this.dataTopicaDocumento = dataTopicaDocumento;
	}
	@Override
	public String toString() {
		return "MetadatiActaVO [idTipoMetadati=" + idTipoMetadati + ", idBando=" + idBando + ", idDomanda=" + idDomanda
				+ ", oggettoFascicolo=" + oggettoFascicolo + ", soggettoFascicolo=" + soggettoFascicolo
				+ ", conservazioneGeneraleFascicolo=" + conservazioneGeneraleFascicolo + ", dataTopicaDocumento="
				+ dataTopicaDocumento + "]";
	}
	
}
