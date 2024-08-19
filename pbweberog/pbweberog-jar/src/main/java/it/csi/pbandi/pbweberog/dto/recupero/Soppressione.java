/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.recupero;

public class Soppressione implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	private java.lang.Long idSoppressione = null;
	private java.lang.Long idProgetto = null;
	private java.lang.Double importoSoppressione = null;
	private java.util.Date dataSoppressione = null;
	private java.lang.String estremiDetermina = null;
	private java.lang.String note = null;
	private java.lang.Long idAnnoContabile = null;
	private java.lang.String flagMonit = null;
	
	public java.lang.String getFlagMonit() {
		return flagMonit;
	}
	public void setFlagMonit(java.lang.String flagMonit) {
		this.flagMonit = flagMonit;
	}
	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(java.lang.Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public java.lang.Long getIdSoppressione() {
		return idSoppressione;
	}
	public void setIdSoppressione(java.lang.Long idSoppressione) {
		this.idSoppressione = idSoppressione;
	}
	public java.lang.Double getImportoSoppressione() {
		return importoSoppressione;
	}
	public void setImportoSoppressione(java.lang.Double importoSoppressione) {
		this.importoSoppressione = importoSoppressione;
	}
	public java.util.Date getDataSoppressione() {
		return dataSoppressione;
	}
	public void setDataSoppressione(java.util.Date dataSoppressione) {
		this.dataSoppressione = dataSoppressione;
	}
	public java.lang.String getEstremiDetermina() {
		return estremiDetermina;
	}
	public void setEstremiDetermina(java.lang.String estremiDetermina) {
		this.estremiDetermina = estremiDetermina;
	}
	public java.lang.String getNote() {
		return note;
	}
	public void setNote(java.lang.String note) {
		this.note = note;
	}
	public java.lang.Long getIdAnnoContabile() {
		return idAnnoContabile;
	}
	public void setIdAnnoContabile(java.lang.Long idAnnoContabile) {
		this.idAnnoContabile = idAnnoContabile;
	}

}
