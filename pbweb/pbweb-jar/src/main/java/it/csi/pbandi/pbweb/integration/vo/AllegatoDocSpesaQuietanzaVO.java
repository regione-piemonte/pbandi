/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.integration.vo;

import java.util.Date;

public class AllegatoDocSpesaQuietanzaVO {

	private String nomeFile;
	private Long idDocumentoDiSpesa;
	private String tipoNumeroDocSpesa;
	private String denomFornitore;
	private Double importoDocSpesa;
	private String noteValidazione;
	private Long idPagamento;
	private String modalitaPagamento;
	private Double importoPagamento;
	private Date dtPagamento;

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Long getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}

	public void setIdDocumentoDiSpesa(Long idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}

	public String getTipoNumeroDocSpesa() {
		return tipoNumeroDocSpesa;
	}

	public void setTipoNumeroDocSpesa(String tipoNumeroDocSpesa) {
		this.tipoNumeroDocSpesa = tipoNumeroDocSpesa;
	}

	public String getDenomFornitore() {
		return denomFornitore;
	}

	public void setDenomFornitore(String denomFornitore) {
		this.denomFornitore = denomFornitore;
	}

	public Double getImportoDocSpesa() {
		return importoDocSpesa;
	}

	public void setImportoDocSpesa(Double importoDocSpesa) {
		this.importoDocSpesa = importoDocSpesa;
	}

	public String getNoteValidazione() {
		return noteValidazione;
	}

	public void setNoteValidazione(String noteValidazione) {
		this.noteValidazione = noteValidazione;
	}

	public Long getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(Long idPagamento) {
		this.idPagamento = idPagamento;
	}

	public String getModalitaPagamento() {
		return modalitaPagamento;
	}

	public void setModalitaPagamento(String modalitaPagamento) {
		this.modalitaPagamento = modalitaPagamento;
	}

	public Double getImportoPagamento() {
		return importoPagamento;
	}

	public void setImportoPagamento(Double importoPagamento) {
		this.importoPagamento = importoPagamento;
	}

	public Date getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(Date dtPagamento) {
		this.dtPagamento = dtPagamento;
	}

}
