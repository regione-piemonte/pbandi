/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;

public class AllegatoVExcel20212027VO {
  BigDecimal idAsse;
  String descAsse;
  String descBreveLinea;
  BigDecimal importoCertificazioneNetto;

  public BigDecimal getIdAsse() {
    return idAsse;
  }

  public void setIdAsse(BigDecimal idAsse) {
    this.idAsse = idAsse;
  }

  public String getDescAsse() {
    return descAsse;
  }

  public void setDescAsse(String descAsse) {
    this.descAsse = descAsse;
  }

  public BigDecimal getImportoCertificazioneNetto() {
    return importoCertificazioneNetto;
  }

  public void setImportoCertificaizoneNetto(BigDecimal importoCertificazioneNetto) {
    this.importoCertificazioneNetto = importoCertificazioneNetto;
  }



  @Override
  public String toString() {
    return "AllegatoVExcel20212027VO{" +
        "idAsse=" + idAsse +
        ", descAsse='" + descAsse + '\'' +
        ", descBreveAsse='" + descBreveLinea + '\'' +
        ", importoCertificaizoneNetto=" + importoCertificazioneNetto +
        '}';
  }

  public String getDescBreveLinea() {
    return descBreveLinea;
  }

  public void setDescBreveLinea(String descBreveLinea) {
    this.descBreveLinea = descBreveLinea;
  }

  public void setImportoCertificazioneNetto(BigDecimal importoCertificazioneNetto) {
    this.importoCertificazioneNetto = importoCertificazioneNetto;
  }
}
