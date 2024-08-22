/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.pbandi.pbservwelfare.dto.FileInfo;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.*;

public class Fornitori   {
  private Integer idFornitore = null;
  private String codiceFiscaleFornitore = null;
  private String denominazione = null;
  private String nome = null;
  private String cognome = null;
  private String codiceFormaGiuridica = null;
  private Date dataInizio = null;
  private Date dataFine = null;
  private List<FileInfo> file = new ArrayList<FileInfo>();

  /**
   **/
  
  @JsonProperty("idFornitore")
  public Integer getIdFornitore() {
    return idFornitore;
  }
  public void setIdFornitore(Integer idFornitore) {
    this.idFornitore = idFornitore;
  }

  /**
   **/
  
  @JsonProperty("codiceFiscaleFornitore")
  public String getCodiceFiscaleFornitore() {
    return codiceFiscaleFornitore;
  }
  public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
    this.codiceFiscaleFornitore = codiceFiscaleFornitore;
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
  
  @JsonProperty("file")
  public List<FileInfo> getFile() {
    return file;
  }
  public void setFile(List<FileInfo> file) {
    this.file = file;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Fornitori fornitori = (Fornitori) o;
    return Objects.equals(idFornitore, fornitori.idFornitore) &&
        Objects.equals(codiceFiscaleFornitore, fornitori.codiceFiscaleFornitore) &&
        Objects.equals(denominazione, fornitori.denominazione) &&
        Objects.equals(nome, fornitori.nome) &&
        Objects.equals(cognome, fornitori.cognome) &&
        Objects.equals(codiceFormaGiuridica, fornitori.codiceFormaGiuridica) &&
        Objects.equals(dataInizio, fornitori.dataInizio) &&
        Objects.equals(dataFine, fornitori.dataFine) &&
        Objects.equals(file, fornitori.file);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idFornitore, codiceFiscaleFornitore, denominazione, nome, cognome, codiceFormaGiuridica, dataInizio, dataFine, file);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Fornitori {\n");
    
    sb.append("    idFornitore: ").append(toIndentedString(idFornitore)).append("\n");
    sb.append("    codiceFiscaleFornitore: ").append(toIndentedString(codiceFiscaleFornitore)).append("\n");
    sb.append("    denominazione: ").append(toIndentedString(denominazione)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    codiceFormaGiuridica: ").append(toIndentedString(codiceFormaGiuridica)).append("\n");
    sb.append("    dataInizio: ").append(toIndentedString(dataInizio)).append("\n");
    sb.append("    dataFine: ").append(toIndentedString(dataFine)).append("\n");
    sb.append("    file: ").append(toIndentedString(file)).append("\n");
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
