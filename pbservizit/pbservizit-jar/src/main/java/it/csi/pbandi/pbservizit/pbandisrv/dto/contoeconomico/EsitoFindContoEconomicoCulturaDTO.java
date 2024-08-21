/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico;

import java.util.Date;

public class EsitoFindContoEconomicoCulturaDTO implements java.io.Serializable {
  static final long serialVersionUID = 1;
  private boolean locked = true;
  private boolean modificabile = true;
  private boolean copiaModificataPresente = true;
  private ContoEconomicoRimodulazioneCulturaDTO contoEconomico = null;
  private boolean rimodulazionePresente = true;
  private boolean propostaPresente = true;
  private Date dataUltimaProposta = null;
  private Date dataUltimaRimodulazione = null;
  private Date dataPresentazioneDomanda = null;
  private Date dataFineIstruttoria = null;
  private boolean inIstruttoria = true;
  private boolean inStatoRichiesto = true;
  private boolean isContoMainNew = true;
  private String flagUltimoRibassoAstaInProposta = null;
  private String flagUltimoRibassoAstaInRimodulazione = null;
  private Double importoUltimoRibassoAstaInProposta = null;
  private Double percUltimoRibassoAstaInProposta = null;

  public boolean getLocked() {
    return locked;
  }

  public void setLocked(boolean val) {
    locked = val;
  }

  public boolean getModificabile() {
    return modificabile;
  }

  public void setModificabile(boolean val) {
    modificabile = val;
  }

  public boolean getCopiaModificataPresente() {
    return copiaModificataPresente;
  }

  public void setCopiaModificataPresente(boolean val) {
    copiaModificataPresente = val;
  }

  public ContoEconomicoRimodulazioneCulturaDTO getContoEconomico() {
    return contoEconomico;
  }

  public void setContoEconomico(ContoEconomicoRimodulazioneCulturaDTO val) {
    contoEconomico = val;
  }

  public boolean getRimodulazionePresente() {
    return rimodulazionePresente;
  }

  public void setRimodulazionePresente(boolean val) {
    rimodulazionePresente = val;
  }

  public boolean getPropostaPresente() {
    return propostaPresente;
  }

  public void setPropostaPresente(boolean val) {
    propostaPresente = val;
  }

  public Date getDataUltimaProposta() {
    return dataUltimaProposta;
  }

  public void setDataUltimaProposta(Date val) {
    dataUltimaProposta = val;
  }

  public Date getDataUltimaRimodulazione() {
    return dataUltimaRimodulazione;
  }

  public void setDataUltimaRimodulazione(Date val) {
    dataUltimaRimodulazione = val;
  }

  public Date getDataPresentazioneDomanda() {
    return dataPresentazioneDomanda;
  }

  public void setDataPresentazioneDomanda(Date val) {
    dataPresentazioneDomanda = val;
  }

  public Date getDataFineIstruttoria() {
    return dataFineIstruttoria;
  }

  public void setDataFineIstruttoria(Date val) {
    dataFineIstruttoria = val;
  }

  public boolean getInIstruttoria() {
    return inIstruttoria;
  }

  public void setInIstruttoria(boolean val) {
    inIstruttoria = val;
  }

  public boolean getInStatoRichiesto() {
    return inStatoRichiesto;
  }

  public void setInStatoRichiesto(boolean val) {
    inStatoRichiesto = val;
  }

  public boolean getIsContoMainNew() {
    return isContoMainNew;
  }

  public void setIsContoMainNew(boolean val) {
    isContoMainNew = val;
  }

  public String getFlagUltimoRibassoAstaInProposta() {
    return flagUltimoRibassoAstaInProposta;
  }

  public void setFlagUltimoRibassoAstaInProposta(String val) {
    flagUltimoRibassoAstaInProposta = val;
  }

  public String getFlagUltimoRibassoAstaInRimodulazione() {
    return flagUltimoRibassoAstaInRimodulazione;
  }

  public void setFlagUltimoRibassoAstaInRimodulazione(String val) {
    flagUltimoRibassoAstaInRimodulazione = val;
  }

  public Double getImportoUltimoRibassoAstaInProposta() {
    return importoUltimoRibassoAstaInProposta;
  }

  public void setImportoUltimoRibassoAstaInProposta(Double val) {
    importoUltimoRibassoAstaInProposta = val;
  }

  public Double getPercUltimoRibassoAstaInProposta() {
    return percUltimoRibassoAstaInProposta;
  }

  public void setPercUltimoRibassoAstaInProposta(Double val) {
    percUltimoRibassoAstaInProposta = val;
  }
}