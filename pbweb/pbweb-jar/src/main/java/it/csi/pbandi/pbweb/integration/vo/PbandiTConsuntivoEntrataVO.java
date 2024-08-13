/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.GenericVO;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PbandiTConsuntivoEntrataVO extends GenericVO {
  private BigDecimal idConsuntivoEntrata;
  private BigDecimal importoEntrata;
  private BigDecimal idVoceDiEntrata;
  private BigDecimal idRigoContoEconomico;
  private String completamento;
  private BigDecimal idUtenteIns;
  private BigDecimal idUtenteAgg;
  private Date dtInizioValidita;
  private Date dtFineValidita;
  private Date dtModifica;

  public PbandiTConsuntivoEntrataVO() {
  }

  public PbandiTConsuntivoEntrataVO(BigDecimal idConsuntivoEntrata) {

    this.idConsuntivoEntrata = idConsuntivoEntrata;
  }

  public PbandiTConsuntivoEntrataVO(BigDecimal idConsuntivoEntrata, BigDecimal importoEntrata, BigDecimal idVoceDiEntrata, BigDecimal idRigoContoEconomico, String completamento, BigDecimal idUtenteIns, BigDecimal idUtenteAgg, Date dtInizioValidita, Date dtFineValidita, Date dtModifica) {
    this.idConsuntivoEntrata = idConsuntivoEntrata;
    this.importoEntrata = importoEntrata;
    this.idVoceDiEntrata = idVoceDiEntrata;
    this.idRigoContoEconomico = idRigoContoEconomico;
    this.completamento = completamento;
    this.idUtenteIns = idUtenteIns;
    this.idUtenteAgg = idUtenteAgg;
    this.dtInizioValidita = dtInizioValidita;
    this.dtFineValidita = dtFineValidita;
    this.dtModifica = dtModifica;
  }

  public PbandiTConsuntivoEntrataVO(long idConsuntivoEntrata) {
    this.idConsuntivoEntrata = BigDecimal.valueOf(idConsuntivoEntrata);
  }


  public boolean isValid() {
    return isPKValid() && dtInizioValidita != null && idRigoContoEconomico != null && idVoceDiEntrata != null && idUtenteIns != null;
  }

  public boolean isPKValid() {
    return idConsuntivoEntrata != null;
  }

  public List<String> getPK() {
    List<String> pk = new ArrayList<>();
    pk.add("idConsuntivoEntrata");
    return pk;
  }

  public BigDecimal getIdConsuntivoEntrata() {
    return idConsuntivoEntrata;
  }

  public void setIdConsuntivoEntrata(BigDecimal idConsuntivoEntrata) {
    this.idConsuntivoEntrata = idConsuntivoEntrata;
  }

  public BigDecimal getImportoEntrata() {
    return importoEntrata;
  }

  public void setImportoEntrata(BigDecimal importoEntrata) {
    this.importoEntrata = importoEntrata;
  }

  public BigDecimal getIdVoceDiEntrata() {
    return idVoceDiEntrata;
  }

  public void setIdVoceDiEntrata(BigDecimal idVoceDiEntrata) {
    this.idVoceDiEntrata = idVoceDiEntrata;
  }

  public BigDecimal getIdRigoContoEconomico() {
    return idRigoContoEconomico;
  }

  public void setIdRigoContoEconomico(BigDecimal idRigoContoEconomico) {
    this.idRigoContoEconomico = idRigoContoEconomico;
  }

  public String getCompletamento() {
    return completamento;
  }

  public void setCompletamento(String completamento) {
    this.completamento = completamento;
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

  public Date getDtInizioValidita() {
    return dtInizioValidita;
  }

  public void setDtInizioValidita(Date dtInizioValidita) {
    this.dtInizioValidita = dtInizioValidita;
  }

  public Date getDtFineValidita() {
    return dtFineValidita;
  }

  public void setDtFineValidita(Date dtFineValidita) {
    this.dtFineValidita = dtFineValidita;
  }

  public Date getDtModifica() {
    return dtModifica;
  }

  public void setDtModifica(Date dtModifica) {
    this.dtModifica = dtModifica;
  }

  @Override
  public String toString() {
    return "PbandiTConsuntivoEntrataVO{" +
        "idConsuntivoEntrata=" + idConsuntivoEntrata +
        ", importoEntrata=" + importoEntrata +
        ", idVoceDiEntrata=" + idVoceDiEntrata +
        ", idRigoContoEconomico=" + idRigoContoEconomico +
        ", completamento='" + completamento + '\'' +
        ", idUtenteIns=" + idUtenteIns +
        ", idUtenteAgg=" + idUtenteAgg +
        ", dtInizioValidita=" + dtInizioValidita +
        ", dtFineValidita=" + dtFineValidita +
        ", dtModifica=" + dtModifica +
        '}';
  }
}
