/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;

public class SoggettoCorrelato   {
  private Integer identificativoSoggettoCorrelato = null;
  private String tipoSoggetto = null;
  private String nome = null;
  private String cognome = null;
  private String codiceFiscale = null;
  private String codiceComuneNascita = null;
  private String descrizioneComuneNascita = null;
  private String descrizioneComuneEsteroNascita = null;
  private String dataNascita = null;
  private String mail = null;
  private String telefono = null;
  private String codiceComuneResidenza = null;
  private String descrizioneComuneResidenza = null;
  private String descrizioneComuneEsteroResidenza = null;
  private String indirizzo = null;
  private String cap = null;

  /**
   **/
  
  @JsonProperty("identificativoSoggettoCorrelato")
  public Integer getIdentificativoSoggettoCorrelato() {
    return identificativoSoggettoCorrelato;
  }
  public void setIdentificativoSoggettoCorrelato(Integer identificativoSoggettoCorrelato) {
    this.identificativoSoggettoCorrelato = identificativoSoggettoCorrelato;
  }

  /**
   **/
  
  @JsonProperty("tipoSoggetto")
  public String getTipoSoggetto() {
    return tipoSoggetto;
  }
  public void setTipoSoggetto(String tipoSoggetto) {
    this.tipoSoggetto = tipoSoggetto;
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
  
  @JsonProperty("codiceFiscale")
  public String getCodiceFiscale() {
    return codiceFiscale;
  }
  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  /**
   **/
  
  @JsonProperty("codiceComuneNascita")
  public String getCodiceComuneNascita() {
    return codiceComuneNascita;
  }
  public void setCodiceComuneNascita(String codiceComuneNascita) {
    this.codiceComuneNascita = codiceComuneNascita;
  }

  /**
   **/
  
  @JsonProperty("descrizioneComuneNascita")
  public String getDescrizioneComuneNascita() {
    return descrizioneComuneNascita;
  }
  public void setDescrizioneComuneNascita(String descrizioneComuneNascita) {
    this.descrizioneComuneNascita = descrizioneComuneNascita;
  }

  /**
   **/
  
  @JsonProperty("descrizioneComuneEsteroNascita")
  public String getDescrizioneComuneEsteroNascita() {
    return descrizioneComuneEsteroNascita;
  }
  public void setDescrizioneComuneEsteroNascita(String descrizioneComuneEsteroNascita) {
    this.descrizioneComuneEsteroNascita = descrizioneComuneEsteroNascita;
  }

  /**
   **/
  
  @JsonProperty("dataNascita")
  public String getDataNascita() {
    return dataNascita;
  }
  public void setDataNascita(String dataNascita) {
    this.dataNascita = dataNascita;
  }

  /**
   **/
  
  @JsonProperty("mail")
  public String getMail() {
    return mail;
  }
  public void setMail(String mail) {
    this.mail = mail;
  }

  /**
   **/
  
  @JsonProperty("telefono")
  public String getTelefono() {
    return telefono;
  }
  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  /**
   **/
  
  @JsonProperty("codiceComuneResidenza")
  public String getCodiceComuneResidenza() {
    return codiceComuneResidenza;
  }
  public void setCodiceComuneResidenza(String codiceComuneResidenza) {
    this.codiceComuneResidenza = codiceComuneResidenza;
  }

  /**
   **/
  
  @JsonProperty("descrizioneComuneResidenza")
  public String getDescrizioneComuneResidenza() {
    return descrizioneComuneResidenza;
  }
  public void setDescrizioneComuneResidenza(String descrizioneComuneResidenza) {
    this.descrizioneComuneResidenza = descrizioneComuneResidenza;
  }

  /**
   **/
  
  @JsonProperty("descrizioneComuneEsteroResidenza")
  public String getDescrizioneComuneEsteroResidenza() {
    return descrizioneComuneEsteroResidenza;
  }
  public void setDescrizioneComuneEsteroResidenza(String descrizioneComuneEsteroResidenza) {
    this.descrizioneComuneEsteroResidenza = descrizioneComuneEsteroResidenza;
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
  
  @JsonProperty("cap")
  public String getCap() {
    return cap;
  }
  public void setCap(String cap) {
    this.cap = cap;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SoggettoCorrelato soggettoCorrelato = (SoggettoCorrelato) o;
    return Objects.equals(identificativoSoggettoCorrelato, soggettoCorrelato.identificativoSoggettoCorrelato) &&
        Objects.equals(tipoSoggetto, soggettoCorrelato.tipoSoggetto) &&
        Objects.equals(nome, soggettoCorrelato.nome) &&
        Objects.equals(cognome, soggettoCorrelato.cognome) &&
        Objects.equals(codiceFiscale, soggettoCorrelato.codiceFiscale) &&
        Objects.equals(codiceComuneNascita, soggettoCorrelato.codiceComuneNascita) &&
        Objects.equals(descrizioneComuneNascita, soggettoCorrelato.descrizioneComuneNascita) &&
        Objects.equals(descrizioneComuneEsteroNascita, soggettoCorrelato.descrizioneComuneEsteroNascita) &&
        Objects.equals(dataNascita, soggettoCorrelato.dataNascita) &&
        Objects.equals(mail, soggettoCorrelato.mail) &&
        Objects.equals(telefono, soggettoCorrelato.telefono) &&
        Objects.equals(codiceComuneResidenza, soggettoCorrelato.codiceComuneResidenza) &&
        Objects.equals(descrizioneComuneResidenza, soggettoCorrelato.descrizioneComuneResidenza) &&
        Objects.equals(descrizioneComuneEsteroResidenza, soggettoCorrelato.descrizioneComuneEsteroResidenza) &&
        Objects.equals(indirizzo, soggettoCorrelato.indirizzo) &&
        Objects.equals(cap, soggettoCorrelato.cap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identificativoSoggettoCorrelato, tipoSoggetto, nome, cognome, codiceFiscale, codiceComuneNascita, descrizioneComuneNascita, descrizioneComuneEsteroNascita, dataNascita, mail, telefono, codiceComuneResidenza, descrizioneComuneResidenza, descrizioneComuneEsteroResidenza, indirizzo, cap);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SoggettoCorrelato {\n");
    
    sb.append("    identificativoSoggettoCorrelato: ").append(toIndentedString(identificativoSoggettoCorrelato)).append("\n");
    sb.append("    tipoSoggetto: ").append(toIndentedString(tipoSoggetto)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
    sb.append("    codiceComuneNascita: ").append(toIndentedString(codiceComuneNascita)).append("\n");
    sb.append("    descrizioneComuneNascita: ").append(toIndentedString(descrizioneComuneNascita)).append("\n");
    sb.append("    descrizioneComuneEsteroNascita: ").append(toIndentedString(descrizioneComuneEsteroNascita)).append("\n");
    sb.append("    dataNascita: ").append(toIndentedString(dataNascita)).append("\n");
    sb.append("    mail: ").append(toIndentedString(mail)).append("\n");
    sb.append("    telefono: ").append(toIndentedString(telefono)).append("\n");
    sb.append("    codiceComuneResidenza: ").append(toIndentedString(codiceComuneResidenza)).append("\n");
    sb.append("    descrizioneComuneResidenza: ").append(toIndentedString(descrizioneComuneResidenza)).append("\n");
    sb.append("    descrizioneComuneEsteroResidenza: ").append(toIndentedString(descrizioneComuneEsteroResidenza)).append("\n");
    sb.append("    indirizzo: ").append(toIndentedString(indirizzo)).append("\n");
    sb.append("    cap: ").append(toIndentedString(cap)).append("\n");
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
