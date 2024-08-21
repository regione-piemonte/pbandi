/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class DocumentiDiSpesaPerValidazioneVO extends GenericVO {
	//private String descBreveStatoValidazSpesa;
	private BigDecimal idDichiarazioneSpesa;
	private Date dataDocumentoDiSpesa;
	private String descTipoDocumentoSpesa;
	private BigDecimal idDocumentoDiSpesa;
	private BigDecimal idDocDiRiferimento;
	private BigDecimal idFornitore;
	private BigDecimal idTipoDocumentoDiSpesa;
	private String numeroDocumentoDiSpesa;
	private BigDecimal importoTotaleDocumento;
	private BigDecimal idProgetto;
	private BigDecimal importoRendicontabile;
	private BigDecimal totaleNoteCredito;
	private BigDecimal totaleImportoQuotaParte;
	private BigDecimal totaleTuttiPagamenti;
	private BigDecimal idStatoDocumentoSpesa;
	private String descBreveStatoDocSpesa;
	private String descStatoDocumentoSpesa;
	private BigDecimal idStatoDocumentoSpesaValid;
	
	/*
	public String getDescBreveStatoValidazSpesa() {
		return descBreveStatoValidazSpesa;
	}
	public void setDescBreveStatoValidazSpesa(String descBreveStatoValidazSpesa) {
		this.descBreveStatoValidazSpesa = descBreveStatoValidazSpesa;
	}
	*/
	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	public Date getDataDocumentoDiSpesa() {
		return dataDocumentoDiSpesa;
	}
	public void setDataDocumentoDiSpesa(Date dataDocumentoDiSpesa) {
		this.dataDocumentoDiSpesa = dataDocumentoDiSpesa;
	}
	public String getDescTipoDocumentoSpesa() {
		return descTipoDocumentoSpesa;
	}
	public void setDescTipoDocumentoSpesa(String descTipoDocumentoSpesa) {
		this.descTipoDocumentoSpesa = descTipoDocumentoSpesa;
	}
	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}
	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}
	public BigDecimal getIdDocDiRiferimento() {
		return idDocDiRiferimento;
	}
	public void setIdDocDiRiferimento(BigDecimal idDocDiRiferimento) {
		this.idDocDiRiferimento = idDocDiRiferimento;
	}
	public BigDecimal getIdFornitore() {
		return idFornitore;
	}
	public void setIdFornitore(BigDecimal idFornitore) {
		this.idFornitore = idFornitore;
	}
	public BigDecimal getIdTipoDocumentoDiSpesa() {
		return idTipoDocumentoDiSpesa;
	}
	public void setIdTipoDocumentoDiSpesa(BigDecimal idTipoDocumentoDiSpesa) {
		this.idTipoDocumentoDiSpesa = idTipoDocumentoDiSpesa;
	}
	public String getNumeroDocumentoDiSpesa() {
		return numeroDocumentoDiSpesa;
	}
	public void setNumeroDocumentoDiSpesa(String numeroDocumentoDiSpesa) {
		this.numeroDocumentoDiSpesa = numeroDocumentoDiSpesa;
	}
	public BigDecimal getImportoTotaleDocumento() {
		return importoTotaleDocumento;
	}
	public void setImportoTotaleDocumento(BigDecimal importoTotaleDocumento) {
		this.importoTotaleDocumento = importoTotaleDocumento;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getImportoRendicontabile() {
		return importoRendicontabile;
	}
	public void setImportoRendicontabile(BigDecimal importoRendicontabile) {
		this.importoRendicontabile = importoRendicontabile;
	}
	public BigDecimal getTotaleNoteCredito() {
		return totaleNoteCredito;
	}
	public void setTotaleNoteCredito(BigDecimal totaleNoteCredito) {
		this.totaleNoteCredito = totaleNoteCredito;
	}
	public BigDecimal getTotaleImportoQuotaParte() {
		return totaleImportoQuotaParte;
	}
	public void setTotaleImportoQuotaParte(BigDecimal totaleImportoQuotaParte) {
		this.totaleImportoQuotaParte = totaleImportoQuotaParte;
	}
	public BigDecimal getTotaleTuttiPagamenti() {
		return totaleTuttiPagamenti;
	}
	public void setTotaleTuttiPagamenti(BigDecimal totaleTuttiPagamenti) {
		this.totaleTuttiPagamenti = totaleTuttiPagamenti;
	}
	public BigDecimal getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}
	public void setIdStatoDocumentoSpesa(BigDecimal idStatoDocumentoSpesa) {
		this.idStatoDocumentoSpesa = idStatoDocumentoSpesa;
	}
	public String getDescBreveStatoDocSpesa() {
		return descBreveStatoDocSpesa;
	}
	public void setDescBreveStatoDocSpesa(String descBreveStatoDocSpesa) {
		this.descBreveStatoDocSpesa = descBreveStatoDocSpesa;
	}
	public String getDescStatoDocumentoSpesa() {
		return descStatoDocumentoSpesa;
	}
	public void setDescStatoDocumentoSpesa(String descStatoDocumentoSpesa) {
		this.descStatoDocumentoSpesa = descStatoDocumentoSpesa;
	}
	public BigDecimal getIdStatoDocumentoSpesaValid() {
		return idStatoDocumentoSpesaValid;
	}
	public void setIdStatoDocumentoSpesaValid(BigDecimal idStatoDocumentoSpesaValid) {
		this.idStatoDocumentoSpesaValid = idStatoDocumentoSpesaValid;
	}
}
