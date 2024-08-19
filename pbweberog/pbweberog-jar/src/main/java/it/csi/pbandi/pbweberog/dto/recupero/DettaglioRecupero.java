/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.recupero;

public class DettaglioRecupero implements java.io.Serializable {
	private String dataRecupero;
	private String estremiDeterminaRecupero;
	private Long idRecupero;
	private Long idTipoRecupero;
	private String modalitaAgevolazione;
	private Double importoAgevolato;
	private Double importoErogato;
	private Double importoRevocato;
	private Double importoRecuperato;
	private Double importoRecupero;
	private String noteRecupero;
	private Long idAnnoContabile;
	private static final long serialVersionUID = 1L;

	public String getDataRecupero() {
		return dataRecupero;
	}

	public void setDataRecupero(String dataRecupero) {
		this.dataRecupero = dataRecupero;
	}

	public String getEstremiDeterminaRecupero() {
		return estremiDeterminaRecupero;
	}

	public void setEstremiDeterminaRecupero(String estremiDeterminaRecupero) {
		this.estremiDeterminaRecupero = estremiDeterminaRecupero;
	}

	public Long getIdRecupero() {
		return idRecupero;
	}

	public void setIdRecupero(Long idRecupero) {
		this.idRecupero = idRecupero;
	}

	public Long getIdTipoRecupero() {
		return idTipoRecupero;
	}

	public void setIdTipoRecupero(Long idTipoRecupero) {
		this.idTipoRecupero = idTipoRecupero;
	}

	public String getModalitaAgevolazione() {
		return modalitaAgevolazione;
	}

	public void setModalitaAgevolazione(String modalitaAgevolazione) {
		this.modalitaAgevolazione = modalitaAgevolazione;
	}

	public Double getImportoAgevolato() {
		return importoAgevolato;
	}

	public void setImportoAgevolato(Double importoAgevolato) {
		this.importoAgevolato = importoAgevolato;
	}

	public Double getImportoErogato() {
		return importoErogato;
	}

	public void setImportoErogato(Double importoErogato) {
		this.importoErogato = importoErogato;
	}

	public Double getImportoRevocato() {
		return importoRevocato;
	}

	public void setImportoRevocato(Double importoRevocato) {
		this.importoRevocato = importoRevocato;
	}

	public Double getImportoRecuperato() {
		return importoRecuperato;
	}

	public void setImportoRecuperato(Double importoRecuperato) {
		this.importoRecuperato = importoRecuperato;
	}

	public Double getImportoRecupero() {
		return importoRecupero;
	}

	public void setImportoRecupero(Double importoRecupero) {
		this.importoRecupero = importoRecupero;
	}

	public String getNoteRecupero() {
		return noteRecupero;
	}

	public void setNoteRecupero(String noteRecupero) {
		this.noteRecupero = noteRecupero;
	}

	public Long getIdAnnoContabile() {
		return idAnnoContabile;
	}

	public void setIdAnnoContabile(Long idAnnoContabile) {
		this.idAnnoContabile = idAnnoContabile;
	}

	public DettaglioRecupero() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
