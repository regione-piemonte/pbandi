/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class PagamentoDocumentoProgettoVO extends GenericVO {

  private BigDecimal idDocumentoDiSpesa;
  private BigDecimal idPagamento;
  private BigDecimal idProgetto;
  private BigDecimal importoPagamento;
  private BigDecimal importoTotaleDocumento;
  private BigDecimal importoRendicontazioneNetto;
  private BigDecimal importoRendicontazioneNc;
  private BigDecimal importoQuietanzato;
  private BigDecimal importoValidato;
  private BigDecimal residuoUtilePagamento;
  private String flagIsUsedDichPrj;
  private String flagPagamento;


  public BigDecimal getImportoTotaleDocumento() {
    return importoTotaleDocumento;
  }

  public void setImportoTotaleDocumento(BigDecimal importoTotaleDocumento) {
    this.importoTotaleDocumento = importoTotaleDocumento;
  }

  public BigDecimal getImportoRendicontazioneNetto() {
    return importoRendicontazioneNetto;
  }

  public void setImportoRendicontazioneNetto(BigDecimal importoRendicontazioneNetto) {
    this.importoRendicontazioneNetto = importoRendicontazioneNetto;
  }

  public BigDecimal getImportoRendicontazioneNc() {
    return importoRendicontazioneNc;
  }

  public void setImportoRendicontazioneNc(BigDecimal importoRendicontazioneNc) {
    this.importoRendicontazioneNc = importoRendicontazioneNc;
  }

  public BigDecimal getImportoQuietanzato() {
    return importoQuietanzato;
  }

  public void setImportoQuietanzato(BigDecimal importoQuietanzato) {
    this.importoQuietanzato = importoQuietanzato;
  }

  public BigDecimal getImportoValidato() {
    return importoValidato;
  }

  public void setImportoValidato(BigDecimal importoValidato) {
    this.importoValidato = importoValidato;
  }

  public BigDecimal getIdDocumentoDiSpesa() {
    return idDocumentoDiSpesa;
  }

  public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
    this.idDocumentoDiSpesa = idDocumentoDiSpesa;
  }

  public BigDecimal getIdPagamento() {
    return idPagamento;
  }

  public void setIdPagamento(BigDecimal idPagamento) {
    this.idPagamento = idPagamento;
  }

  public BigDecimal getIdProgetto() {
    return idProgetto;
  }

  public void setIdProgetto(BigDecimal idProgetto) {
    this.idProgetto = idProgetto;
  }

  public BigDecimal getImportoPagamento() {
    return importoPagamento;
  }

  public void setImportoPagamento(BigDecimal importoPagamento) {
    this.importoPagamento = importoPagamento;
  }

  public BigDecimal getResiduoUtilePagamento() {
    return residuoUtilePagamento;
  }

  public void setResiduoUtilePagamento(BigDecimal residuoUtilePagamento) {
    this.residuoUtilePagamento = residuoUtilePagamento;
  }

  public String getFlagIsUsedDichPrj() {
    return flagIsUsedDichPrj;
  }

  public void setFlagIsUsedDichPrj(String flagIsUsedDichPrj) {
    this.flagIsUsedDichPrj = flagIsUsedDichPrj;
  }

  public String getFlagPagamento() {
    return flagPagamento;
  }

  public void setFlagPagamento(String flagPagamento) {
    this.flagPagamento = flagPagamento;
  }
}
