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

public class DocControdeduzioniResponse   {
  private String esito = null;
  private String messaggioErrore = null;
  private String codiceErrore = null;

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
  
  @JsonProperty("messaggioErrore")
  public String getMessaggioErrore() {
    return messaggioErrore;
  }
  public void setMessaggioErrore(String messaggioErrore) {
    this.messaggioErrore = messaggioErrore;
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
    DocControdeduzioniResponse docControdeduzioniResponse = (DocControdeduzioniResponse) o;
    return Objects.equals(esito, docControdeduzioniResponse.esito) &&
        Objects.equals(messaggioErrore, docControdeduzioniResponse.messaggioErrore) &&
        Objects.equals(codiceErrore, docControdeduzioniResponse.codiceErrore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(esito, messaggioErrore, codiceErrore);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocControdeduzioniResponse {\n");
    
    sb.append("    esito: ").append(toIndentedString(esito)).append("\n");
    sb.append("    messaggioErrore: ").append(toIndentedString(messaggioErrore)).append("\n");
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
