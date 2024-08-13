/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.documentoDiSpesa;

public class TipologiaVoceDiSpesaDTO implements java.io.Serializable {

  static final long serialVersionUID = 174L;

  private Long idTipologiaVoceDiSpesa = null;

  private String descrizione = null;


  private Long percQuotaContributo = null;


  public Long getIdTipologiaVoceDiSpesa() {
    return idTipologiaVoceDiSpesa;
  }

  public void setIdTipologiaVoceDiSpesa(Long idTipologiaVoceDiSpesa) {
    this.idTipologiaVoceDiSpesa = idTipologiaVoceDiSpesa;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public Long getPercQuotaContributo() {
    return percQuotaContributo;
  }

  public void setPercQuotaContributo(Long percQuotaContributo) {
    this.percQuotaContributo = percQuotaContributo;
  }

  @Override
  public String toString() {
    return "TipologiaVoceDiSpesaDTO{" +
        "id=" + idTipologiaVoceDiSpesa +
        ", descrizione='" + descrizione + '\'' +
        ", PercQuotaContributo=" + percQuotaContributo +
        '}';
  }
}
