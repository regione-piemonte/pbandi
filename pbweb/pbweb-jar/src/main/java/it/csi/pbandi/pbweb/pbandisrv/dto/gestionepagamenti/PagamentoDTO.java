/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti;

public class PagamentoDTO implements java.io.Serializable {

  static final long serialVersionUID = 1;

  private String causalePagamento = null;
  private String descBreveModalitaPagamento = null;
  private String descrizioneModalitaPagamento = null;
  private String destinatarioPagamento = null;
  private java.util.Date dtPagamento = null;
  private it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoVoceDiSpesaDTO[] elencoPagamentiVociDiSpesa = null;
  private String estremiPagamento = null;
  private Long idCausalePagamento = null;
  private Long idModalitaPagamento = null;
  private Long idPagamento = null;
  private Long idSoggetto = null;
  private Long idUtenteAgg = null;
  private Long idUtenteIns = null;
  private Double importoPagamento = null;
  private Double importoQuietanzato = null;
  private Double importoRendicontabilePagato = null;
  private Double importoResiduoUtilizzabile = null;
  private Boolean isQuietanzato = null;
  private Boolean isUsedDichiarazioni = null;
  private String rifPagamento = null;
  private String idIntegrazioneSpesa = null;
	private String flagPagamento = null;

  public String getCausalePagamento() {
    return causalePagamento;
  }

  public void setCausalePagamento(String val) {
    causalePagamento = val;
  }

  public String getDescBreveModalitaPagamento() {
    return descBreveModalitaPagamento;
  }

  public void setDescBreveModalitaPagamento(String val) {
    descBreveModalitaPagamento = val;
  }

  public String getDescrizioneModalitaPagamento() {
    return descrizioneModalitaPagamento;
  }

  public void setDescrizioneModalitaPagamento(String val) {
    descrizioneModalitaPagamento = val;
  }

  public String getDestinatarioPagamento() {
    return destinatarioPagamento;
  }

  public void setDestinatarioPagamento(String val) {
    destinatarioPagamento = val;
  }

  public java.util.Date getDtPagamento() {
    return dtPagamento;
  }

  public void setDtPagamento(java.util.Date val) {
    dtPagamento = val;
  }

  public it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoVoceDiSpesaDTO[] getElencoPagamentiVociDiSpesa() {
    return elencoPagamentiVociDiSpesa;
  }

  public void setElencoPagamentiVociDiSpesa(it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoVoceDiSpesaDTO[] val) {
    elencoPagamentiVociDiSpesa = val;
  }

  public String getEstremiPagamento() {
    return estremiPagamento;
  }

  public void setEstremiPagamento(String val) {
    estremiPagamento = val;
  }

  public Long getIdCausalePagamento() {
    return idCausalePagamento;
  }

  public void setIdCausalePagamento(Long val) {
    idCausalePagamento = val;
  }

  public Long getIdModalitaPagamento() {
    return idModalitaPagamento;
  }

  public void setIdModalitaPagamento(Long val) {
    idModalitaPagamento = val;
  }

  public Long getIdPagamento() {
    return idPagamento;
  }

  public void setIdPagamento(Long val) {
    idPagamento = val;
  }

  public Long getIdSoggetto() {
    return idSoggetto;
  }

  public void setIdSoggetto(Long val) {
    idSoggetto = val;
  }

  public Long getIdUtenteAgg() {
    return idUtenteAgg;
  }

  public void setIdUtenteAgg(Long val) {
    idUtenteAgg = val;
  }

  public Long getIdUtenteIns() {
    return idUtenteIns;
  }

  public void setIdUtenteIns(Long val) {
    idUtenteIns = val;
  }

  public Double getImportoPagamento() {
    return importoPagamento;
  }

  public void setImportoPagamento(Double val) {
    importoPagamento = val;
  }

  public Double getImportoQuietanzato() {
    return importoQuietanzato;
  }

  public void setImportoQuietanzato(Double val) {
    importoQuietanzato = val;
  }

  public Double getImportoRendicontabilePagato() {
    return importoRendicontabilePagato;
  }

  public void setImportoRendicontabilePagato(Double val) {
    importoRendicontabilePagato = val;
  }

  public Double getImportoResiduoUtilizzabile() {
    return importoResiduoUtilizzabile;
  }

  public void setImportoResiduoUtilizzabile(Double val) {
    importoResiduoUtilizzabile = val;
  }

  public Boolean getIsQuietanzato() {
    return isQuietanzato;
  }

  public void setIsQuietanzato(Boolean val) {
    isQuietanzato = val;
  }

  public Boolean getIsUsedDichiarazioni() {
    return isUsedDichiarazioni;
  }

  public void setIsUsedDichiarazioni(Boolean val) {
    isUsedDichiarazioni = val;
  }

  public String getRifPagamento() {
    return rifPagamento;
  }

  public void setRifPagamento(String val) {
    rifPagamento = val;
  }

  public String getIdIntegrazioneSpesa() {
    return idIntegrazioneSpesa;
  }

  public void setIdIntegrazioneSpesa(String val) {
    idIntegrazioneSpesa = val;
  }

	public String getFlagPagamento() {
		return flagPagamento;
	}

	public void setFlagPagamento(String flagPagamento) {
		this.flagPagamento = flagPagamento;
	}
}