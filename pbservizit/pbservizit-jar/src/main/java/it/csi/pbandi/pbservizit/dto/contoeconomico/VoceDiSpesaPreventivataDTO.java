/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.contoeconomico;

import java.math.BigDecimal;
import java.sql.Date;

public class VoceDiSpesaPreventivataDTO implements java.io.Serializable {
  static final long serialVersionUID = 3L;
  private BigDecimal idSpesaPreventivata;
  private BigDecimal idRigoContoEconomico;
  private BigDecimal idVoceDiSpesa;
  private BigDecimal idContoEconomico;
  private BigDecimal importoSpesaPreventivata;
  private BigDecimal idUtenteIns;
  private BigDecimal idUtenteAgg;
  private Date dtInserimento;
  private Date dtModifica;

  public BigDecimal getIdSpesaPreventivata() {
    return idSpesaPreventivata;
  }

  public void setIdSpesaPreventivata(BigDecimal idSpesaPreventivata) {
    this.idSpesaPreventivata = idSpesaPreventivata;
  }

  public BigDecimal getIdRigoContoEconomico() {
    return idRigoContoEconomico;
  }

  public void setIdRigoContoEconomico(BigDecimal idRigoContoEconomico) {
    this.idRigoContoEconomico = idRigoContoEconomico;
  }

  public BigDecimal getIdVoceDiSpesa() {
    return idVoceDiSpesa;
  }

  public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
    this.idVoceDiSpesa = idVoceDiSpesa;
  }

  public BigDecimal getIdContoEconomico() {
    return idContoEconomico;
  }

  public void setIdContoEconomico(BigDecimal idContoEconomico) {
    this.idContoEconomico = idContoEconomico;
  }

  public BigDecimal getImportoSpesaPreventivata() {
    return importoSpesaPreventivata;
  }

  public void setImportoSpesaPreventivata(BigDecimal importoSpesaPreventivata) {
    this.importoSpesaPreventivata = importoSpesaPreventivata;
  }

  public BigDecimal getIdUtenteIns() {
    return idUtenteIns;
  }

  public void setIdUtenteIns(BigDecimal idUtenteIns) {
    this.idUtenteIns = idUtenteIns;
  }

  public BigDecimal getIdUtenteAgg() {
    return idUtenteAgg;
  }

  public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
    this.idUtenteAgg = idUtenteAgg;
  }

  public Date getDtInserimento() {
    return dtInserimento;
  }

  public void setDtInserimento(Date dtInserimento) {
    this.dtInserimento = dtInserimento;
  }

  public Date getDtModifica() {
    return dtModifica;
  }

  public void setDtModifica(Date dtModifica) {
    this.dtModifica = dtModifica;
  }

  @Override
  public String toString() {
    return "VoceDiSpesaPreventivataDTO{" +
        "idSpesaPreventivata=" + idSpesaPreventivata +
        ", idRigoContoEconomico=" + idRigoContoEconomico +
        ", idVoceDiSpesa=" + idVoceDiSpesa +
        ", idContoEconomico=" + idContoEconomico +
        ", importoSpesaPreventivata=" + importoSpesaPreventivata +
        ", idUtenteIns=" + idUtenteIns +
        ", idUtenteAgg=" + idUtenteAgg +
        ", dtInserimento=" + dtInserimento +
        ", dtModifica=" + dtModifica +
        '}';
  }
}
