/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO extends GenericVO {

	private String codiceFiscaleFornitore;
	private String cognomeFornitore;
	private String denominazioneFornitore;
	private String descTipoDocumentoSpesa;
	private String descBreveStatoDocSpesa;
	private String descBreveTipoDocSpesa;
	private String descVoceDiSpesa;
	private String descModalitaPagamento;
	private String descBreveModalitaPagamento;
	private String descStatoDocumentoSpesa;
	private Date dtEmissioneDocumento;
	private Date dtPagamento;
	private Date dtValuta;
	private BigDecimal idContoEconomico;
	private BigDecimal idDichiarazioneSpesa;
	private BigDecimal idDocumentoDiSpesa;
	private BigDecimal idFornitore;
	private BigDecimal idPagamento;
	private BigDecimal idProgetto;
	private BigDecimal idStatoDocumentoSpesa;
	private BigDecimal idTipoDocumentoSpesa;
	private BigDecimal idTipoFornitore;
	private BigDecimal idVoceDiSpesa;
	private BigDecimal importoTotaleDocumento;
	private BigDecimal importoValidato;
	private BigDecimal importoQuietanzato;
	private BigDecimal importoPagamento;
	private BigDecimal idQuotaParteDocSpesa;
	private BigDecimal importoTotaleRettifica;
	private BigDecimal importoValidatoLordo;
	private String nomeFornitore;
	private String numeroDocumento;
	private String partitaIvaFornitore;
	private BigDecimal progrPagQuotParteDocSp;
	private String task;
	private String tipoInvio;
	private BigDecimal totaleRettificaDoc;
	private BigDecimal validatoPerDichiarazione;
	private String rilievoContabile;
	private Date dtRilievoContabile;

	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}

	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}

	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}

	public BigDecimal getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}

	public void setIdTipoDocumentoSpesa(BigDecimal idTipoDocumentoSpesa) {
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
	}

	public BigDecimal getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}

	public void setIdStatoDocumentoSpesa(BigDecimal idStatoDocumentoSpesa) {
		this.idStatoDocumentoSpesa = idStatoDocumentoSpesa;
	}

	public BigDecimal getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(BigDecimal idPagamento) {
		this.idPagamento = idPagamento;
	}

	/*
	 * public BigDecimal getIdStatoValidazioneSpesa() { return
	 * idStatoValidazioneSpesa; } public void setIdStatoValidazioneSpesa(BigDecimal
	 * idStatoValidazioneSpesa) { this.idStatoValidazioneSpesa =
	 * idStatoValidazioneSpesa; }
	 */
	public BigDecimal getIdFornitore() {
		return idFornitore;
	}

	public void setIdFornitore(BigDecimal idFornitore) {
		this.idFornitore = idFornitore;
	}

	public BigDecimal getIdTipoFornitore() {
		return idTipoFornitore;
	}

	public void setIdTipoFornitore(BigDecimal idTipoFornitore) {
		this.idTipoFornitore = idTipoFornitore;
	}

	/*
	 * public String getDescStatoValidazioneSpesa() { return
	 * descStatoValidazioneSpesa; } public void setDescStatoValidazioneSpesa(String
	 * descStatoValidazioneSpesa) { this.descStatoValidazioneSpesa =
	 * descStatoValidazioneSpesa; } public String getDescBreveStatoValidazSpesa() {
	 * return descBreveStatoValidazSpesa; } public void
	 * setDescBreveStatoValidazSpesa(String descBreveStatoValidazSpesa) {
	 * this.descBreveStatoValidazSpesa = descBreveStatoValidazSpesa; }
	 * 
	 */
	public String getDescTipoDocumentoSpesa() {
		return descTipoDocumentoSpesa;
	}

	public void setDescTipoDocumentoSpesa(String descTipoDocumentoSpesa) {
		this.descTipoDocumentoSpesa = descTipoDocumentoSpesa;
	}

	public String getDescBreveTipoDocSpesa() {
		return descBreveTipoDocSpesa;
	}

	public void setDescBreveTipoDocSpesa(String descBreveTipoDocSpesa) {
		this.descBreveTipoDocSpesa = descBreveTipoDocSpesa;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Date getDtEmissioneDocumento() {
		return dtEmissioneDocumento;
	}

	public void setDtEmissioneDocumento(Date dtEmissioneDocumento) {
		this.dtEmissioneDocumento = dtEmissioneDocumento;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getNomeFornitore() {
		return nomeFornitore;
	}

	public void setNomeFornitore(String nomeFornitore) {
		this.nomeFornitore = nomeFornitore;
	}

	public String getCognomeFornitore() {
		return cognomeFornitore;
	}

	public void setCognomeFornitore(String cognomeFornitore) {
		this.cognomeFornitore = cognomeFornitore;
	}

	public String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}

	public void setDenominazioneFornitore(String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}

	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}

	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}

	public String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}

	public void setPartitaIvaFornitore(String partitaIvaFornitore) {
		this.partitaIvaFornitore = partitaIvaFornitore;
	}

	public String getDescStatoDocumentoSpesa() {
		return descStatoDocumentoSpesa;
	}

	public void setDescStatoDocumentoSpesa(String descStatoDocumentoSpesa) {
		this.descStatoDocumentoSpesa = descStatoDocumentoSpesa;
	}

	public String getDescBreveStatoDocSpesa() {
		return descBreveStatoDocSpesa;
	}

	public void setDescBreveStatoDocSpesa(String descBreveStatoDocSpesa) {
		this.descBreveStatoDocSpesa = descBreveStatoDocSpesa;
	}

	public BigDecimal getImportoTotaleDocumento() {
		return importoTotaleDocumento;
	}

	public void setImportoTotaleDocumento(BigDecimal importoTotaleDocumento) {
		this.importoTotaleDocumento = importoTotaleDocumento;
	}

	public void setIdContoEconomico(BigDecimal idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
	}

	public BigDecimal getIdContoEconomico() {
		return idContoEconomico;
	}

	public String getDescModalitaPagamento() {
		return descModalitaPagamento;
	}

	public void setDescModalitaPagamento(String descModalitaPagamento) {
		this.descModalitaPagamento = descModalitaPagamento;
	}

	public String getDescBreveModalitaPagamento() {
		return descBreveModalitaPagamento;
	}

	public void setDescBreveModalitaPagamento(String descBreveModalitaPagamento) {
		this.descBreveModalitaPagamento = descBreveModalitaPagamento;
	}

	public Date getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(Date dtPagamento) {
		this.dtPagamento = dtPagamento;
	}

	public Date getDtValuta() {
		return dtValuta;
	}

	public void setDtValuta(Date dtValuta) {
		this.dtValuta = dtValuta;
	}

	public BigDecimal getImportoValidato() {
		return importoValidato;
	}

	public void setImportoValidato(BigDecimal importoValidato) {
		this.importoValidato = importoValidato;
	}

	public BigDecimal getImportoQuietanzato() {
		return importoQuietanzato;
	}

	public void setImportoQuietanzato(BigDecimal importoQuietanzato) {
		this.importoQuietanzato = importoQuietanzato;
	}

	public void setImportoPagamento(BigDecimal importoPagamento) {
		this.importoPagamento = importoPagamento;
	}

	public BigDecimal getImportoPagamento() {
		return importoPagamento;
	}

	public void setDescVoceDiSpesa(String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}

	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}

	public void setIdQuotaParteDocSpesa(BigDecimal idQuotaParteDocSpesa) {
		this.idQuotaParteDocSpesa = idQuotaParteDocSpesa;
	}

	public BigDecimal getIdQuotaParteDocSpesa() {
		return idQuotaParteDocSpesa;
	}

	public void setProgrPagQuotParteDocSp(BigDecimal progrPagQuotParteDocSp) {
		this.progrPagQuotParteDocSp = progrPagQuotParteDocSp;
	}

	public BigDecimal getProgrPagQuotParteDocSp() {
		return progrPagQuotParteDocSp;
	}

	public void setImportoValidatoLordo(BigDecimal importoValidatoLordo) {
		this.importoValidatoLordo = importoValidatoLordo;
	}

	public BigDecimal getImportoValidatoLordo() {
		return importoValidatoLordo;
	}

	public void setImportoTotaleRettifica(BigDecimal importoTotaleRettifica) {
		this.importoTotaleRettifica = importoTotaleRettifica;
	}

	public BigDecimal getImportoTotaleRettifica() {
		return importoTotaleRettifica;
	}

	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}

	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public BigDecimal getTotaleRettificaDoc() {
		return totaleRettificaDoc;
	}

	public void setTotaleRettificaDoc(BigDecimal totaleRettificaDoc) {
		this.totaleRettificaDoc = totaleRettificaDoc;
	}

	public BigDecimal getValidatoPerDichiarazione() {
		return validatoPerDichiarazione;
	}

	public void setValidatoPerDichiarazione(BigDecimal validatoPerDichiarazione) {
		this.validatoPerDichiarazione = validatoPerDichiarazione;
	}

	public String getTipoInvio() {
		return tipoInvio;
	}

	public void setTipoInvio(String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}

	public String getRilievoContabile() {
		return rilievoContabile;
	}

	public void setRilievoContabile(String rilievoContabile) {
		this.rilievoContabile = rilievoContabile;
	}

	public Date getDtRilievoContabile() {
		return dtRilievoContabile;
	}

	public void setDtRilievoContabile(Date dtRilievoContabile) {
		this.dtRilievoContabile = dtRilievoContabile;
	}

}
