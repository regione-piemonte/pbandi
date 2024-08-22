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

public class SoggettoDelegatoResponse   {
  private Integer identificativoSoggettoCorrelato = null;
  private String messaggio = null;
  private String esito = null;
  private String codiceErrore = null;

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
  
  @JsonProperty("messaggio")
  public String getMessaggio() {
    return messaggio;
  }
  public void setMessaggio(String messaggio) {
    this.messaggio = messaggio;
  }

  /**
   **/
  
  @JsonProperty("esito")
  public String getEsito() {
    return esito;
  }
  public void setEsito(String esito) {
    this.esito = esito;
  }

  /**
   **/
  
  @JsonProperty("codiceErrore")
  public String getCodiceErrore() {
    return codiceErrore;
  }
  public void setCodiceErrore(String codiceErrore) {
    this.codiceErrore = codiceErrore;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SoggettoDelegatoResponse soggettoDelegatoResponse = (SoggettoDelegatoResponse) o;
    return Objects.equals(identificativoSoggettoCorrelato, soggettoDelegatoResponse.identificativoSoggettoCorrelato) &&
        Objects.equals(messaggio, soggettoDelegatoResponse.messaggio) &&
        Objects.equals(esito, soggettoDelegatoResponse.esito) &&
        Objects.equals(codiceErrore, soggettoDelegatoResponse.codiceErrore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identificativoSoggettoCorrelato, messaggio, esito, codiceErrore);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SoggettoDelegatoResponse {\n");
    
    sb.append("    identificativoSoggettoCorrelato: ").append(toIndentedString(identificativoSoggettoCorrelato)).append("\n");
    sb.append("    messaggio: ").append(toIndentedString(messaggio)).append("\n");
    sb.append("    esito: ").append(toIndentedString(esito)).append("\n");
    sb.append("    codiceErrore: ").append(toIndentedString(codiceErrore)).append("\n");
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
