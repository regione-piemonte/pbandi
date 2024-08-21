/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa;

public class RappresentanteLegaleDTO implements java.io.Serializable {

  static final long serialVersionUID = 1;

  private Long idSoggetto = null;
  private Long idPersonaFisica = null;
  private Long idIndirizzoPersonaFisica = null;
  private String cognome = null;
  private String nome = null;
  private java.util.Date dataNascita = null;
  private Long idComuneItalianoNascita = null;
  private Long idComuneEsteroNascita = null;
  private String luogoNascita = null;
  private String indirizzoResidenza = null;
  private String capResidenza = null;
  private Long idComuneResidenza = null;
  private Long idComuneEsteroResidenza = null;
  private String comuneResidenza = null;
  private String provinciaResidenza = null;
  private Long idProvinciaResidenza = null;
  private Long idNazioneResidenza = null;
  private String codiceFiscaleSoggetto = null;

  public Long getIdSoggetto() {
    return idSoggetto;
  }

  public void setIdSoggetto(Long val) {
    idSoggetto = val;
  }

  public Long getIdPersonaFisica() {
    return idPersonaFisica;
  }

  public void setIdPersonaFisica(Long val) {
    idPersonaFisica = val;
  }

  public Long getIdIndirizzoPersonaFisica() {
    return idIndirizzoPersonaFisica;
  }

  public void setIdIndirizzoPersonaFisica(Long val) {
    idIndirizzoPersonaFisica = val;
  }

  public String getCognome() {
    return cognome;
  }

  public void setCognome(String val) {
    cognome = val;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String val) {
    nome = val;
  }

  public java.util.Date getDataNascita() {
    return dataNascita;
  }

  public void setDataNascita(java.util.Date val) {
    dataNascita = val;
  }

  public Long getIdComuneItalianoNascita() {
    return idComuneItalianoNascita;
  }

  public void setIdComuneItalianoNascita(Long val) {
    idComuneItalianoNascita = val;
  }

  public Long getIdComuneEsteroNascita() {
    return idComuneEsteroNascita;
  }

  public void setIdComuneEsteroNascita(Long val) {
    idComuneEsteroNascita = val;
  }

  public String getLuogoNascita() {
    return luogoNascita;
  }

  public void setLuogoNascita(String val) {
    luogoNascita = val;
  }

  public String getIndirizzoResidenza() {
    return indirizzoResidenza;
  }

  public void setIndirizzoResidenza(String val) {
    indirizzoResidenza = val;
  }

  public String getCapResidenza() {
    return capResidenza;
  }

  public void setCapResidenza(String val) {
    capResidenza = val;
  }

  public Long getIdComuneResidenza() {
    return idComuneResidenza;
  }

  public void setIdComuneResidenza(Long val) {
    idComuneResidenza = val;
  }

  public Long getIdComuneEsteroResidenza() {
    return idComuneEsteroResidenza;
  }

  public void setIdComuneEsteroResidenza(Long val) {
    idComuneEsteroResidenza = val;
  }

  public String getComuneResidenza() {
    return comuneResidenza;
  }

  public void setComuneResidenza(String val) {
    comuneResidenza = val;
  }

  public String getProvinciaResidenza() {
    return provinciaResidenza;
  }

  public void setProvinciaResidenza(String val) {
    provinciaResidenza = val;
  }

  public Long getIdProvinciaResidenza() {
    return idProvinciaResidenza;
  }

  public void setIdProvinciaResidenza(Long val) {
    idProvinciaResidenza = val;
  }

  public Long getIdNazioneResidenza() {
    return idNazioneResidenza;
  }

  public void setIdNazioneResidenza(Long val) {
    idNazioneResidenza = val;
  }

  public String getCodiceFiscaleSoggetto() {
    return codiceFiscaleSoggetto;
  }

  public void setCodiceFiscaleSoggetto(String val) {
    codiceFiscaleSoggetto = val;
  }

}