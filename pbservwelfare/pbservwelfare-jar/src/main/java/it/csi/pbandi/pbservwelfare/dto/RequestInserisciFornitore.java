/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.pbandi.pbservwelfare.dto.FileAllegato;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.*;

public class RequestInserisciFornitore   {
  private String numeroDomanda = null;
  private String codiceFiscale = null;
  private String cognome = null;
  private String nome = null;
  private String denominazione = null;
  private String partitaIva = null;
  private String codiceFormaGiuridica = null;
  private Date dataInizio = null;
  private Date dataFine = null;
  private List<FileAllegato> files = new ArrayList<FileAllegato>();

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
  
  @JsonProperty("codiceFiscale")
  public String getCodiceFiscale() {
    return codiceFiscale;
  }
  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
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
  
  @JsonProperty("nome")
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   **/
  
  @JsonProperty("denominazione")
  public String getDenominazione() {
    return denominazione;
  }
  public void setDenominazione(String denominazione) {
    this.denominazione = denominazione;
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
  
  @JsonProperty("codiceFormaGiuridica")
  public String getCodiceFormaGiuridica() {
    return codiceFormaGiuridica;
  }
  public void setCodiceFormaGiuridica(String codiceFormaGiuridica) {
    this.codiceFormaGiuridica = codiceFormaGiuridica;
  }

  /**
   **/
  
  @JsonProperty("dataInizio")
  public Date getDataInizio() {
    return dataInizio;
  }
  public void setDataInizio(Date dataInizio) {
    this.dataInizio = dataInizio;
  }

  /**
   **/
  
  @JsonProperty("dataFine")
  public Date getDataFine() {
    return dataFine;
  }
  public void setDataFine(Date dataFine) {
    this.dataFine = dataFine;
  }

  /**
   **/
  
  @JsonProperty("files")
  public List<FileAllegato> getFiles() {
    return files;
  }
  public void setFiles(List<FileAllegato> files) {
    this.files = files;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestInserisciFornitore requestInserisciFornitore = (RequestInserisciFornitore) o;
    return Objects.equals(numeroDomanda, requestInserisciFornitore.numeroDomanda) &&
        Objects.equals(codiceFiscale, requestInserisciFornitore.codiceFiscale) &&
        Objects.equals(cognome, requestInserisciFornitore.cognome) &&
        Objects.equals(nome, requestInserisciFornitore.nome) &&
        Objects.equals(denominazione, requestInserisciFornitore.denominazione) &&
        Objects.equals(partitaIva, requestInserisciFornitore.partitaIva) &&
        Objects.equals(codiceFormaGiuridica, requestInserisciFornitore.codiceFormaGiuridica) &&
        Objects.equals(dataInizio, requestInserisciFornitore.dataInizio) &&
        Objects.equals(dataFine, requestInserisciFornitore.dataFine) &&
        Objects.equals(files, requestInserisciFornitore.files);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numeroDomanda, codiceFiscale, cognome, nome, denominazione, partitaIva, codiceFormaGiuridica, dataInizio, dataFine, files);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestInserisciFornitore {\n");
    
    sb.append("    numeroDomanda: ").append(toIndentedString(numeroDomanda)).append("\n");
    sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    denominazione: ").append(toIndentedString(denominazione)).append("\n");
    sb.append("    partitaIva: ").append(toIndentedString(partitaIva)).append("\n");
    sb.append("    codiceFormaGiuridica: ").append(toIndentedString(codiceFormaGiuridica)).append("\n");
    sb.append("    dataInizio: ").append(toIndentedString(dataInizio)).append("\n");
    sb.append("    dataFine: ").append(toIndentedString(dataFine)).append("\n");
    sb.append("    files: ").append(toIndentedString(files)).append("\n");
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
