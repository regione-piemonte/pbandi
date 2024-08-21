/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;


public class PbandiDCausaleErogazioneVO extends GenericVO {

  private BigDecimal idCausaleErogazione;
  private String descCausale;
  private String descBreveCausale;
  private String flagCertificazione;
  private Date dtInizioValidita;
  private BigDecimal idTipoTrattamento;
  private BigDecimal progrOrdinamento;
  private String flagIterStandard;
  private String codIgrue;
  private String idTipoDocumentoIndex;
  private String idCausaleTrasferimento;
  private Date dtFineValidita;

  public PbandiDCausaleErogazioneVO() {
  }

  public PbandiDCausaleErogazioneVO(BigDecimal idCausaleErogazione) {

    this.idCausaleErogazione = idCausaleErogazione;
  }

  public PbandiDCausaleErogazioneVO(String flagIterStandard, String descBreveCausale, Date dtInizioValidita, BigDecimal idCausaleErogazione, BigDecimal progrOrdinamento, String descCausale, BigDecimal idTipoTrattamento, String flagCertificazione, Date dtFineValidita) {

    this.flagIterStandard = flagIterStandard;
    this.descBreveCausale = descBreveCausale;
    this.dtInizioValidita = dtInizioValidita;
    this.idCausaleErogazione = idCausaleErogazione;
    this.progrOrdinamento = progrOrdinamento;
    this.descCausale = descCausale;
    this.idTipoTrattamento = idTipoTrattamento;
    this.flagCertificazione = flagCertificazione;
    this.dtFineValidita = dtFineValidita;
  }

  public PbandiDCausaleErogazioneVO(BigDecimal idCausaleErogazione, String descCausale, String descBreveCausale, String flagCertificazione, Date dtInizioValidita, BigDecimal idTipoTrattamento, BigDecimal progrOrdinamento, String flagIterStandard, String codIgrue, String idTipoDocumentoIndex, String idCausaleTrasferimento, Date dtFineValidita) {
    this.idCausaleErogazione = idCausaleErogazione;
    this.descCausale = descCausale;
    this.descBreveCausale = descBreveCausale;
    this.flagCertificazione = flagCertificazione;
    this.dtInizioValidita = dtInizioValidita;
    this.idTipoTrattamento = idTipoTrattamento;
    this.progrOrdinamento = progrOrdinamento;
    this.flagIterStandard = flagIterStandard;
    this.codIgrue = codIgrue;
    this.idTipoDocumentoIndex = idTipoDocumentoIndex;
    this.idCausaleTrasferimento = idCausaleTrasferimento;
    this.dtFineValidita = dtFineValidita;
  }


  public String getFlagIterStandard() {
    return flagIterStandard;
  }

  public void setFlagIterStandard(String flagIterStandard) {
    this.flagIterStandard = flagIterStandard;
  }

  public String getDescBreveCausale() {
    return descBreveCausale;
  }

  public void setDescBreveCausale(String descBreveCausale) {
    this.descBreveCausale = descBreveCausale;
  }

  public Date getDtInizioValidita() {
    return dtInizioValidita;
  }

  public void setDtInizioValidita(Date dtInizioValidita) {
    this.dtInizioValidita = dtInizioValidita;
  }

  public BigDecimal getIdCausaleErogazione() {
    return idCausaleErogazione;
  }

  public void setIdCausaleErogazione(BigDecimal idCausaleErogazione) {
    this.idCausaleErogazione = idCausaleErogazione;
  }

  public BigDecimal getProgrOrdinamento() {
    return progrOrdinamento;
  }

  public void setProgrOrdinamento(BigDecimal progrOrdinamento) {
    this.progrOrdinamento = progrOrdinamento;
  }

  public String getDescCausale() {
    return descCausale;
  }

  public void setDescCausale(String descCausale) {
    this.descCausale = descCausale;
  }

  public BigDecimal getIdTipoTrattamento() {
    return idTipoTrattamento;
  }

  public void setIdTipoTrattamento(BigDecimal idTipoTrattamento) {
    this.idTipoTrattamento = idTipoTrattamento;
  }

  public String getFlagCertificazione() {
    return flagCertificazione;
  }

  public void setFlagCertificazione(String flagCertificazione) {
    this.flagCertificazione = flagCertificazione;
  }

  public Date getDtFineValidita() {
    return dtFineValidita;
  }

  public void setDtFineValidita(Date dtFineValidita) {
    this.dtFineValidita = dtFineValidita;
  }

  public boolean isValid() {
    boolean isValid = false;
    if (isPKValid() && flagIterStandard != null && descBreveCausale != null && dtInizioValidita != null && progrOrdinamento != null && descCausale != null && flagCertificazione != null) {
      isValid = true;
    }
    return isValid;
  }

  public boolean isPKValid() {
    boolean isPkValid = false;
    if (idCausaleErogazione != null) {
      isPkValid = true;
    }

    return isPkValid;
  }

  public String toString() {

    String temp = "";
    StringBuffer sb = new StringBuffer();
    sb.append("\t\n" + this.getClass().getName() + "\t\n");

    temp = DataFilter.removeNull(flagIterStandard);
    if (!DataFilter.isEmpty(temp)) sb.append(" flagIterStandard: " + temp + "\t\n");

    temp = DataFilter.removeNull(descBreveCausale);
    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveCausale: " + temp + "\t\n");

    temp = DataFilter.removeNull(dtInizioValidita);
    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");

    temp = DataFilter.removeNull(idCausaleErogazione);
    if (!DataFilter.isEmpty(temp)) sb.append(" idCausaleErogazione: " + temp + "\t\n");

    temp = DataFilter.removeNull(progrOrdinamento);
    if (!DataFilter.isEmpty(temp)) sb.append(" progrOrdinamento: " + temp + "\t\n");

    temp = DataFilter.removeNull(descCausale);
    if (!DataFilter.isEmpty(temp)) sb.append(" descCausale: " + temp + "\t\n");

    temp = DataFilter.removeNull(idTipoTrattamento);
    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoTrattamento: " + temp + "\t\n");

    temp = DataFilter.removeNull(flagCertificazione);
    if (!DataFilter.isEmpty(temp)) sb.append(" flagCertificazione: " + temp + "\t\n");

    temp = DataFilter.removeNull(dtFineValidita);
    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");

    temp = DataFilter.removeNull(codIgrue);
    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrue: " + temp + "\t\n");

    temp = DataFilter.removeNull(idTipoDocumentoIndex);
    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDocumentoIndex: " + temp + "\t\n");

    temp = DataFilter.removeNull(idCausaleTrasferimento);
    if (!DataFilter.isEmpty(temp)) sb.append(" idCausaleTrasferimento: " + temp + "\t\n");

    return sb.toString();
  }

  public List<String> getPK() {
    List<String> pk = new ArrayList<>();
    pk.add("idCausaleErogazione");
    return pk;
  }


  public String getCodIgrue() {
    return codIgrue;
  }

  public void setCodIgrue(String codIgrue) {
    this.codIgrue = codIgrue;
  }

  public String getIdTipoDocumentoIndex() {
    return idTipoDocumentoIndex;
  }

  public void setIdTipoDocumentoIndex(String idTipoDocumentoIndex) {
    this.idTipoDocumentoIndex = idTipoDocumentoIndex;
  }

  public String getIdCausaleTrasferimento() {
    return idCausaleTrasferimento;
  }

  public void setIdCausaleTrasferimento(String idCausaleTrasferimento) {
    this.idCausaleTrasferimento = idCausaleTrasferimento;
  }
}
