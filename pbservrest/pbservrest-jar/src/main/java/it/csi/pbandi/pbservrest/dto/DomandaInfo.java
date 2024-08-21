/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import javax.validation.constraints.*;

public class DomandaInfo   {
  private String tipoSogg = null;
  private BigDecimal codiceBando = null;
  private String numeroDomanda = null;
  private String descStatoDomanda = null;
  private String ndg = null;
  private String numRelazDichSpesa = null;
  private String pec = null;
  private String codiceFiscaleSoggetto = null;
  private BigDecimal importoConcesso = null;
  private String denominazioneEnteGiuridico = null;
  private String partitaIva = null;
  private String acronimoProgetto = null;
  private String nome = null;
  private String cognome = null;
  private String CAP = null;
  private String indirizzo = null;
  private String provincia = null;
  private String nazione = null;
  private String comune = null;
  private String regione = null;
  private String dtNascita = null;
  private String esitoServizio = null;
  private Integer codiceErrore = null;
  private String descErrore = null;

  /**
   **/
  
  @JsonProperty("tipoSogg")
  public String getTipoSogg() {
    return tipoSogg;
  }
  public void setTipoSogg(String tipoSogg) {
    this.tipoSogg = tipoSogg;
  }

  /**
   **/
  
  @JsonProperty("codiceBando")
  public BigDecimal getCodiceBando() {
    return codiceBando;
  }
  public void setCodiceBando(BigDecimal codiceBando) {
    this.codiceBando = codiceBando;
  }

  /**
   **/
  
  @JsonProperty("numeroDomanda")
  public String getNumeroDomanda() {
    return numeroDomanda;
  }
  public void setNumeroDomanda(String numeroDomanda) {
    this.numeroDomanda = numeroDomanda;
  }

  /**
   **/
  
  @JsonProperty("descStatoDomanda")
  public String getDescStatoDomanda() {
    return descStatoDomanda;
  }
  public void setDescStatoDomanda(String descStatoDomanda) {
    this.descStatoDomanda = descStatoDomanda;
  }

  /**
   **/
  
  @JsonProperty("ndg")
  public String getNdg() {
    return ndg;
  }
  public void setNdg(String ndg) {
    this.ndg = ndg;
  }

  /**
   **/
  
  @JsonProperty("numRelazDichSpesa")
  public String getNumRelazDichSpesa() {
    return numRelazDichSpesa;
  }
  public void setNumRelazDichSpesa(String numRelazDichSpesa) {
    this.numRelazDichSpesa = numRelazDichSpesa;
  }

  /**
   **/
  
  @JsonProperty("pec")
  public String getPec() {
    return pec;
  }
  public void setPec(String pec) {
    this.pec = pec;
  }

  /**
   **/
  
  @JsonProperty("codiceFiscaleSoggetto")
  public String getCodiceFiscaleSoggetto() {
    return codiceFiscaleSoggetto;
  }
  public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
    this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
  }

  /**
   **/
  
  @JsonProperty("importoConcesso")
  public BigDecimal getImportoConcesso() {
    return importoConcesso;
  }
  public void setImportoConcesso(BigDecimal importoConcesso) {
    this.importoConcesso = importoConcesso;
  }

  /**
   **/
  
  @JsonProperty("denominazioneEnteGiuridico")
  public String getDenominazioneEnteGiuridico() {
    return denominazioneEnteGiuridico;
  }
  public void setDenominazioneEnteGiuridico(String denominazioneEnteGiuridico) {
    this.denominazioneEnteGiuridico = denominazioneEnteGiuridico;
  }

  /**
   **/
  
  @JsonProperty("partitaIva")
  public String getPartitaIva() {
    return partitaIva;
  }
  public void setPartitaIva(String partitaIva) {
    this.partitaIva = partitaIva;
  }

  /**
   **/
  
  @JsonProperty("acronimoProgetto")
  public String getAcronimoProgetto() {
    return acronimoProgetto;
  }
  public void setAcronimoProgetto(String acronimoProgetto) {
    this.acronimoProgetto = acronimoProgetto;
  }

  /**
   **/
  
  @JsonProperty("nome")
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   **/
  
  @JsonProperty("cognome")
  public String getCognome() {
    return cognome;
  }
  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  /**
   **/
  
  @JsonProperty("CAP")
  public String getCAP() {
    return CAP;
  }
  public void setCAP(String CAP) {
    this.CAP = CAP;
  }

  /**
   **/
  
  @JsonProperty("indirizzo")
  public String getIndirizzo() {
    return indirizzo;
  }
  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  /**
   **/
  
  @JsonProperty("provincia")
  public String getProvincia() {
    return provincia;
  }
  public void setProvincia(String provincia) {
    this.provincia = provincia;
  }

  /**
   **/
  
  @JsonProperty("nazione")
  public String getNazione() {
    return nazione;
  }
  public void setNazione(String nazione) {
    this.nazione = nazione;
  }

  /**
   **/
  
  @JsonProperty("comune")
  public String getComune() {
    return comune;
  }
  public void setComune(String comune) {
    this.comune = comune;
  }

  /**
   **/
  
  @JsonProperty("regione")
  public String getRegione() {
    return regione;
  }
  public void setRegione(String regione) {
    this.regione = regione;
  }

  /**
   **/
  
  @JsonProperty("dtNascita")
  public String getDtNascita() {
    return dtNascita;
  }
  public void setDtNascita(String dtNascita) {
    this.dtNascita = dtNascita;
  }

  /**
   **/
  
  @JsonProperty("esitoServizio")
  public String getEsitoServizio() {
    return esitoServizio;
  }
  public void setEsitoServizio(String esitoServizio) {
    this.esitoServizio = esitoServizio;
  }

  /**
   **/
  
  @JsonProperty("codiceErrore")
  public Integer getCodiceErrore() {
    return codiceErrore;
  }
  public void setCodiceErrore(Integer codiceErrore) {
    this.codiceErrore = codiceErrore;
  }

  /**
   **/
  
  @JsonProperty("descErrore")
  public String getDescErrore() {
    return descErrore;
  }
  public void setDescErrore(String descErrore) {
    this.descErrore = descErrore;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DomandaInfo domandaInfo = (DomandaInfo) o;
    return Objects.equals(tipoSogg, domandaInfo.tipoSogg) &&
        Objects.equals(codiceBando, domandaInfo.codiceBando) &&
        Objects.equals(numeroDomanda, domandaInfo.numeroDomanda) &&
        Objects.equals(descStatoDomanda, domandaInfo.descStatoDomanda) &&
        Objects.equals(ndg, domandaInfo.ndg) &&
        Objects.equals(numRelazDichSpesa, domandaInfo.numRelazDichSpesa) &&
        Objects.equals(pec, domandaInfo.pec) &&
        Objects.equals(codiceFiscaleSoggetto, domandaInfo.codiceFiscaleSoggetto) &&
        Objects.equals(importoConcesso, domandaInfo.importoConcesso) &&
        Objects.equals(denominazioneEnteGiuridico, domandaInfo.denominazioneEnteGiuridico) &&
        Objects.equals(partitaIva, domandaInfo.partitaIva) &&
        Objects.equals(acronimoProgetto, domandaInfo.acronimoProgetto) &&
        Objects.equals(nome, domandaInfo.nome) &&
        Objects.equals(cognome, domandaInfo.cognome) &&
        Objects.equals(CAP, domandaInfo.CAP) &&
        Objects.equals(indirizzo, domandaInfo.indirizzo) &&
        Objects.equals(provincia, domandaInfo.provincia) &&
        Objects.equals(nazione, domandaInfo.nazione) &&
        Objects.equals(comune, domandaInfo.comune) &&
        Objects.equals(regione, domandaInfo.regione) &&
        Objects.equals(dtNascita, domandaInfo.dtNascita) &&
        Objects.equals(esitoServizio, domandaInfo.esitoServizio) &&
        Objects.equals(codiceErrore, domandaInfo.codiceErrore) &&
        Objects.equals(descErrore, domandaInfo.descErrore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tipoSogg, codiceBando, numeroDomanda, descStatoDomanda, ndg, numRelazDichSpesa, pec, codiceFiscaleSoggetto, importoConcesso, denominazioneEnteGiuridico, partitaIva, acronimoProgetto, nome, cognome, CAP, indirizzo, provincia, nazione, comune, regione, dtNascita, esitoServizio, codiceErrore, descErrore);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DomandaInfo {\n");
    
    sb.append("    tipoSogg: ").append(toIndentedString(tipoSogg)).append("\n");
    sb.append("    codiceBando: ").append(toIndentedString(codiceBando)).append("\n");
    sb.append("    numeroDomanda: ").append(toIndentedString(numeroDomanda)).append("\n");
    sb.append("    descStatoDomanda: ").append(toIndentedString(descStatoDomanda)).append("\n");
    sb.append("    ndg: ").append(toIndentedString(ndg)).append("\n");
    sb.append("    numRelazDichSpesa: ").append(toIndentedString(numRelazDichSpesa)).append("\n");
    sb.append("    pec: ").append(toIndentedString(pec)).append("\n");
    sb.append("    codiceFiscaleSoggetto: ").append(toIndentedString(codiceFiscaleSoggetto)).append("\n");
    sb.append("    importoConcesso: ").append(toIndentedString(importoConcesso)).append("\n");
    sb.append("    denominazioneEnteGiuridico: ").append(toIndentedString(denominazioneEnteGiuridico)).append("\n");
    sb.append("    partitaIva: ").append(toIndentedString(partitaIva)).append("\n");
    sb.append("    acronimoProgetto: ").append(toIndentedString(acronimoProgetto)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    CAP: ").append(toIndentedString(CAP)).append("\n");
    sb.append("    indirizzo: ").append(toIndentedString(indirizzo)).append("\n");
    sb.append("    provincia: ").append(toIndentedString(provincia)).append("\n");
    sb.append("    nazione: ").append(toIndentedString(nazione)).append("\n");
    sb.append("    comune: ").append(toIndentedString(comune)).append("\n");
    sb.append("    regione: ").append(toIndentedString(regione)).append("\n");
    sb.append("    dtNascita: ").append(toIndentedString(dtNascita)).append("\n");
    sb.append("    esitoServizio: ").append(toIndentedString(esitoServizio)).append("\n");
    sb.append("    codiceErrore: ").append(toIndentedString(codiceErrore)).append("\n");
    sb.append("    descErrore: ").append(toIndentedString(descErrore)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
