/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

public class SettoreDTO implements java.io.Serializable{
  static final long serialVersionUID = 4L;
  private Long idSettore = null;
  private String descrizione = null;
  private String descBreveDirezione = null;
  public Long getIdSettore() {
    return idSettore;
  }

  public void setIdSettore(Long idSettore) {
    this.idSettore = idSettore;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public String getDescBreveDirezione() {
    return descBreveDirezione;
  }

  public void setDescBreveDirezione(String descBreveDirezione) {
    this.descBreveDirezione = descBreveDirezione;
  }

  @Override
  public String toString() {
    return "SettoreDTO{" +
        "idSettore=" + idSettore +
        ", descrizione='" + descrizione + '\'' +
        ", descBreveDirezione='" + descBreveDirezione + '\'' +
        '}';
  }
}
