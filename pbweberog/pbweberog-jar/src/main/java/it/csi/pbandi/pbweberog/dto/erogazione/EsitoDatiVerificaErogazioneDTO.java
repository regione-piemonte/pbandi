/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.erogazione;

public class EsitoDatiVerificaErogazioneDTO implements java.io.Serializable {
  private static final long serialVersionUID = 4L;
  private String[] messages;
  private DatiVerificaErogazioneDTO erogazione;
  private Boolean esito;
  private Boolean isRegolaAttiva;

  public EsitoDatiVerificaErogazioneDTO() {
    super();
  }

  public String[] getMessages() {
    return messages;
  }

  public void setMessages(String[] messages) {
    this.messages = messages;
  }

  public DatiVerificaErogazioneDTO getErogazione() {
    return erogazione;
  }

  public void setErogazione(DatiVerificaErogazioneDTO erogazione) {
    this.erogazione = erogazione;
  }

  public Boolean getEsito() {
    return esito;
  }

  public void setEsito(Boolean esito) {
    this.esito = esito;
  }

  public Boolean getIsRegolaAttiva() {
    return isRegolaAttiva;
  }

  public void setIsRegolaAttiva(Boolean isRegolaAttiva) {
    this.isRegolaAttiva = isRegolaAttiva;
  }


}
