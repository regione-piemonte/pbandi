/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico;

public class ContoEconomicoDTO implements java.io.Serializable {
  static final long serialVersionUID = 1;
  private Double importoSpesaAmmessa = null;
  private Double importoSpesaQuietanziata = null;
  private Double importoSpesaRendicontata = null;
  private Double importoSpesaValidataTotale = null;
  private Double percSpesaQuietanziataSuAmmessa = null;
  private Double percSpesaValidataSuAmmessa = null;
  private String label = null;
  private Long idVoce = null;
  private it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO[] figli = null;
  private java.util.Date dtUltimaSpesaAmmessaInRendicontazione = null;
  private java.util.Date dtUltimaPropostaRimodulazione = null;
  private Long idContoEconomico = null;
  private Long idVocePadre = null;
  private Long idTipologiaVoceDiSpesa = null;
  private Double percSpGenFunz = null;

  public Double getImportoSpesaAmmessa() {
    return importoSpesaAmmessa;
  }

  public void setImportoSpesaAmmessa(Double val) {
    importoSpesaAmmessa = val;
  }

  public Double getImportoSpesaQuietanziata() {
    return importoSpesaQuietanziata;
  }

  public void setImportoSpesaQuietanziata(Double val) {
    importoSpesaQuietanziata = val;
  }

  public Double getImportoSpesaRendicontata() {
    return importoSpesaRendicontata;
  }

  public void setImportoSpesaRendicontata(Double val) {
    importoSpesaRendicontata = val;
  }

  public Double getImportoSpesaValidataTotale() {
    return importoSpesaValidataTotale;
  }

  public void setImportoSpesaValidataTotale(Double val) {
    importoSpesaValidataTotale = val;
  }

  public Double getPercSpesaQuietanziataSuAmmessa() {
    return percSpesaQuietanziataSuAmmessa;
  }

  public void setPercSpesaQuietanziataSuAmmessa(Double val) {
    percSpesaQuietanziataSuAmmessa = val;
  }

  public Double getPercSpesaValidataSuAmmessa() {
    return percSpesaValidataSuAmmessa;
  }

  public void setPercSpesaValidataSuAmmessa(Double val) {
    percSpesaValidataSuAmmessa = val;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String val) {
    label = val;
  }

  public Long getIdVoce() {
    return idVoce;
  }

  public void setIdVoce(Long val) {
    idVoce = val;
  }

  public it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO[] getFigli() {
    return figli;
  }

  public void setFigli(it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO[] val) {
    figli = val;
  }

  public java.util.Date getDtUltimaSpesaAmmessaInRendicontazione() {
    return dtUltimaSpesaAmmessaInRendicontazione;
  }

  public void setDtUltimaSpesaAmmessaInRendicontazione(java.util.Date val) {
    dtUltimaSpesaAmmessaInRendicontazione = val;
  }

  public java.util.Date getDtUltimaPropostaRimodulazione() {
    return dtUltimaPropostaRimodulazione;
  }

  public void setDtUltimaPropostaRimodulazione(java.util.Date val) {
    dtUltimaPropostaRimodulazione = val;
  }

  public Long getIdContoEconomico() {
    return idContoEconomico;
  }

  public void setIdContoEconomico(Long val) {
    idContoEconomico = val;
  }

  public Long getIdVocePadre() {
    return idVocePadre;
  }

  public void setIdVocePadre(Long val) {
    idVocePadre = val;
  }

  public Long getIdTipologiaVoceDiSpesa() {
    return idTipologiaVoceDiSpesa;
  }

  public void setIdTipologiaVoceDiSpesa(Long val) {
    idTipologiaVoceDiSpesa = val;
  }

  public Double getPercSpGenFunz() {
    return percSpGenFunz;
  }

  public void setPercSpGenFunz(Double val) {
    percSpGenFunz = val;
  }
}