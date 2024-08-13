/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.documentoDiSpesa;

public class PagamentoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.String causalePagamento = null;
	private java.lang.String descBreveModalitaPagamento = null;
	private java.lang.String descrizioneModalitaPagamento = null;
	private java.lang.String destinatarioPagamento = null;
	private java.util.Date dtPagamento = null;
	private PagamentoVoceDiSpesaDTO[] elencoPagamentiVociDiSpesa = null;
	private java.lang.String estremiPagamento = null;
	private java.lang.Long idCausalePagamento = null;
	private java.lang.Long idModalitaPagamento = null;
	private java.lang.Long idPagamento = null;
	private java.lang.Long idSoggetto = null;
	private java.lang.Long idUtenteAgg = null;
	private java.lang.Long idUtenteIns = null;
	private java.lang.Double importoPagamento = null;
	private java.lang.Double importoQuietanzato = null;
	private java.lang.Double importoRendicontabilePagato = null;
	private java.lang.Double importoResiduoUtilizzabile = null;
	private java.lang.Boolean isQuietanzato = null;
	private java.lang.Boolean isUsedDichiarazioni = null;
	private java.lang.String rifPagamento = null;
	
	public java.lang.String getCausalePagamento() {
		return causalePagamento;
	}
	public void setCausalePagamento(java.lang.String causalePagamento) {
		this.causalePagamento = causalePagamento;
	}
	public java.lang.String getDescBreveModalitaPagamento() {
		return descBreveModalitaPagamento;
	}
	public void setDescBreveModalitaPagamento(java.lang.String descBreveModalitaPagamento) {
		this.descBreveModalitaPagamento = descBreveModalitaPagamento;
	}
	public java.lang.String getDescrizioneModalitaPagamento() {
		return descrizioneModalitaPagamento;
	}
	public void setDescrizioneModalitaPagamento(java.lang.String descrizioneModalitaPagamento) {
		this.descrizioneModalitaPagamento = descrizioneModalitaPagamento;
	}
	public java.lang.String getDestinatarioPagamento() {
		return destinatarioPagamento;
	}
	public void setDestinatarioPagamento(java.lang.String destinatarioPagamento) {
		this.destinatarioPagamento = destinatarioPagamento;
	}
	public java.util.Date getDtPagamento() {
		return dtPagamento;
	}
	public void setDtPagamento(java.util.Date dtPagamento) {
		this.dtPagamento = dtPagamento;
	}
	public PagamentoVoceDiSpesaDTO[] getElencoPagamentiVociDiSpesa() {
		return elencoPagamentiVociDiSpesa;
	}
	public void setElencoPagamentiVociDiSpesa(PagamentoVoceDiSpesaDTO[] elencoPagamentiVociDiSpesa) {
		this.elencoPagamentiVociDiSpesa = elencoPagamentiVociDiSpesa;
	}
	public java.lang.String getEstremiPagamento() {
		return estremiPagamento;
	}
	public void setEstremiPagamento(java.lang.String estremiPagamento) {
		this.estremiPagamento = estremiPagamento;
	}
	public java.lang.Long getIdCausalePagamento() {
		return idCausalePagamento;
	}
	public void setIdCausalePagamento(java.lang.Long idCausalePagamento) {
		this.idCausalePagamento = idCausalePagamento;
	}
	public java.lang.Long getIdModalitaPagamento() {
		return idModalitaPagamento;
	}
	public void setIdModalitaPagamento(java.lang.Long idModalitaPagamento) {
		this.idModalitaPagamento = idModalitaPagamento;
	}
	public java.lang.Long getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(java.lang.Long idPagamento) {
		this.idPagamento = idPagamento;
	}
	public java.lang.Long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(java.lang.Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public java.lang.Long getIdUtenteAgg() {
		return idUtenteAgg;
	}
	public void setIdUtenteAgg(java.lang.Long idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	public java.lang.Long getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(java.lang.Long idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public java.lang.Double getImportoPagamento() {
		return importoPagamento;
	}
	public void setImportoPagamento(java.lang.Double importoPagamento) {
		this.importoPagamento = importoPagamento;
	}
	public java.lang.Double getImportoQuietanzato() {
		return importoQuietanzato;
	}
	public void setImportoQuietanzato(java.lang.Double importoQuietanzato) {
		this.importoQuietanzato = importoQuietanzato;
	}
	public java.lang.Double getImportoRendicontabilePagato() {
		return importoRendicontabilePagato;
	}
	public void setImportoRendicontabilePagato(java.lang.Double importoRendicontabilePagato) {
		this.importoRendicontabilePagato = importoRendicontabilePagato;
	}
	public java.lang.Double getImportoResiduoUtilizzabile() {
		return importoResiduoUtilizzabile;
	}
	public void setImportoResiduoUtilizzabile(java.lang.Double importoResiduoUtilizzabile) {
		this.importoResiduoUtilizzabile = importoResiduoUtilizzabile;
	}
	public java.lang.Boolean getIsQuietanzato() {
		return isQuietanzato;
	}
	public void setIsQuietanzato(java.lang.Boolean isQuietanzato) {
		this.isQuietanzato = isQuietanzato;
	}
	public java.lang.Boolean getIsUsedDichiarazioni() {
		return isUsedDichiarazioni;
	}
	public void setIsUsedDichiarazioni(java.lang.Boolean isUsedDichiarazioni) {
		this.isUsedDichiarazioni = isUsedDichiarazioni;
	}
	public java.lang.String getRifPagamento() {
		return rifPagamento;
	}
	public void setRifPagamento(java.lang.String rifPagamento) {
		this.rifPagamento = rifPagamento;
	}
	
}
