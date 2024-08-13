/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa;

import java.util.Date;

public class DocumentoDiSpesaDTO implements java.io.Serializable {

  static final long serialVersionUID = 1;

  private String codiceFiscaleFornitore = null;
  private Date dataDocumentoDiSpesa = null;
  private String descTipoDocumentoDiSpesa = null;
  private Long idDocumentoDiSpesa = null;
  private Long idDocDiRiferimento = null;
  private Long idFornitore = null;
  private Long idSoggetto = null;
  private Long idTipoDocumentoDiSpesa = null;
  private String motivazione = null;
  private String nomeFornitore = null;
  private String numeroDocumentoDiSpesa = null;
  private String task = null;
  private String tipoInvio = null;
  private String flagElettronico = null;
  private Boolean allegatiPresenti = null;
  private String descBreveTipoDocumentoDiSpesa = null;
  private String ruolo = null;
  private Date dataFirmaContratto = null;

  public String getCodiceFiscaleFornitore() {
    return codiceFiscaleFornitore;
  }

  public void setCodiceFiscaleFornitore(String val) {
    codiceFiscaleFornitore = val;
  }

  public Date getDataDocumentoDiSpesa() {
    return dataDocumentoDiSpesa;
  }

  public void setDataDocumentoDiSpesa(Date val) {
    dataDocumentoDiSpesa = val;
  }

  public String getDescTipoDocumentoDiSpesa() {
    return descTipoDocumentoDiSpesa;
  }

  public void setDescTipoDocumentoDiSpesa(String val) {
    descTipoDocumentoDiSpesa = val;
  }

  public Long getIdDocumentoDiSpesa() {
    return idDocumentoDiSpesa;
  }

  public void setIdDocumentoDiSpesa(Long val) {
    idDocumentoDiSpesa = val;
  }

  public Long getIdDocDiRiferimento() {
    return idDocDiRiferimento;
  }

  public void setIdDocDiRiferimento(Long val) {
    idDocDiRiferimento = val;
  }

  public Long getIdFornitore() {
    return idFornitore;
  }

  public void setIdFornitore(Long val) {
    idFornitore = val;
  }

  public Long getIdSoggetto() {
    return idSoggetto;
  }

  public void setIdSoggetto(Long val) {
    idSoggetto = val;
  }

  public Long getIdTipoDocumentoDiSpesa() {
    return idTipoDocumentoDiSpesa;
  }

  public void setIdTipoDocumentoDiSpesa(Long val) {
    idTipoDocumentoDiSpesa = val;
  }

  public String getMotivazione() {
    return motivazione;
  }

  public void setMotivazione(String val) {
    motivazione = val;
  }

  public String getNomeFornitore() {
    return nomeFornitore;
  }

  public void setNomeFornitore(String val) {
    nomeFornitore = val;
  }

  public String getNumeroDocumentoDiSpesa() {
    return numeroDocumentoDiSpesa;
  }

  public void setNumeroDocumentoDiSpesa(String val) {
    numeroDocumentoDiSpesa = val;
  }

  public String getTask() {
    return task;
  }

  public void setTask(String val) {
    task = val;
  }

  public String getTipoInvio() {
    return tipoInvio;
  }

  public void setTipoInvio(String val) {
    tipoInvio = val;
  }

  public String getFlagElettronico() {
    return flagElettronico;
  }

  public void setFlagElettronico(String val) {
    flagElettronico = val;
  }

  public Boolean getAllegatiPresenti() {
    return allegatiPresenti;
  }

  public void setAllegatiPresenti(Boolean val) {
    allegatiPresenti = val;
  }

  public String getDescBreveTipoDocumentoDiSpesa() {
    return descBreveTipoDocumentoDiSpesa;
  }

  public void setDescBreveTipoDocumentoDiSpesa(String descBreveTipoDocumentoDiSpesa) {
    this.descBreveTipoDocumentoDiSpesa = descBreveTipoDocumentoDiSpesa;
  }

  public String getRuolo() {
    return ruolo;
  }

  public void setRuolo(String ruolo) {
    this.ruolo = ruolo;
  }

  public Date getDataFirmaContratto() {
    return dataFirmaContratto;
  }

  public void setDataFirmaContratto(Date dataFirmaContratto) {
    this.dataFirmaContratto = dataFirmaContratto;
  }
}