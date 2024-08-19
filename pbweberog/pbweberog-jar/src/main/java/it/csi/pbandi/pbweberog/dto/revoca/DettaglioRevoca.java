/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.revoca;

public class DettaglioRevoca implements java.io.Serializable {

	private String codiceCausaleDisimpegno;
	private String dataRevoca;
	private String dtDecorRevoca;
	private String descPeriodoVisualizzata;
	private String estremiDeterminaRevoca;
	private String idAnnoContabile;
	private Long idMotivoRevoca;
	private Long idRevoca;
	private Double importoAgevolato;
	private Double importoErogato;
	private Double importoRevocato;
	private Double importoRecuperato;
	private Double importoRevoca;
	private String modalitaAgevolazione;
	private String noteRevoca;
	private String ordineRecupero;
	private Long idModalitaRecupero;
	private static final long serialVersionUID = 1L;

	
	
	public String getCodiceCausaleDisimpegno() {
		return codiceCausaleDisimpegno;
	}

	public void setCodiceCausaleDisimpegno(String codiceCausaleDisimpegno) {
		this.codiceCausaleDisimpegno = codiceCausaleDisimpegno;
	}

	public String getDataRevoca() {
		return dataRevoca;
	}

	public void setDataRevoca(String dataRevoca) {
		this.dataRevoca = dataRevoca;
	}

	public String getDtDecorRevoca() {
		return dtDecorRevoca;
	}

	public void setDtDecorRevoca(String dtDecorRevoca) {
		this.dtDecorRevoca = dtDecorRevoca;
	}

	public String getDescPeriodoVisualizzata() {
		return descPeriodoVisualizzata;
	}

	public void setDescPeriodoVisualizzata(String descPeriodoVisualizzata) {
		this.descPeriodoVisualizzata = descPeriodoVisualizzata;
	}

	public String getEstremiDeterminaRevoca() {
		return estremiDeterminaRevoca;
	}

	public void setEstremiDeterminaRevoca(String estremiDeterminaRevoca) {
		this.estremiDeterminaRevoca = estremiDeterminaRevoca;
	}

	public String getIdAnnoContabile() {
		return idAnnoContabile;
	}

	public void setIdAnnoContabile(String idAnnoContabile) {
		this.idAnnoContabile = idAnnoContabile;
	}

	public Long getIdMotivoRevoca() {
		return idMotivoRevoca;
	}

	public void setIdMotivoRevoca(Long idMotivoRevoca) {
		this.idMotivoRevoca = idMotivoRevoca;
	}

	public Long getIdRevoca() {
		return idRevoca;
	}

	public void setIdRevoca(Long idRevoca) {
		this.idRevoca = idRevoca;
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

	public Double getImportoRevoca() {
		return importoRevoca;
	}

	public void setImportoRevoca(Double importoRevoca) {
		this.importoRevoca = importoRevoca;
	}

	public String getModalitaAgevolazione() {
		return modalitaAgevolazione;
	}

	public void setModalitaAgevolazione(String modalitaAgevolazione) {
		this.modalitaAgevolazione = modalitaAgevolazione;
	}

	public String getNoteRevoca() {
		return noteRevoca;
	}

	public void setNoteRevoca(String noteRevoca) {
		this.noteRevoca = noteRevoca;
	}

	public String getOrdineRecupero() {
		return ordineRecupero;
	}

	public void setOrdineRecupero(String ordineRecupero) {
		this.ordineRecupero = ordineRecupero;
	}

	public Long getIdModalitaRecupero() {
		return idModalitaRecupero;
	}

	public void setIdModalitaRecupero(Long idModalitaRecupero) {
		this.idModalitaRecupero = idModalitaRecupero;
	}

	public DettaglioRevoca() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
