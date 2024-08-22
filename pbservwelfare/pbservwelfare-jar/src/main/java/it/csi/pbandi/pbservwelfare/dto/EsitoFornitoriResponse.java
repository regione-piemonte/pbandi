/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.pbandi.pbservwelfare.dto.Fornitori;
import java.util.List;
import javax.validation.constraints.*;

public class EsitoFornitoriResponse   {
  private List<Fornitori> fornitori = new ArrayList<Fornitori>();
  private String messaggio = null;
  private String codiceErrore = null;

  /**
   **/
  
  @JsonProperty("fornitori")
  public List<Fornitori> getFornitori() {
    return fornitori;
  }
  public void setFornitori(List<Fornitori> fornitori) {
    this.fornitori = fornitori;
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
    EsitoFornitoriResponse esitoFornitoriResponse = (EsitoFornitoriResponse) o;
    return Objects.equals(fornitori, esitoFornitoriResponse.fornitori) &&
        Objects.equals(messaggio, esitoFornitoriResponse.messaggio) &&
        Objects.equals(codiceErrore, esitoFornitoriResponse.codiceErrore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fornitori, messaggio, codiceErrore);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsitoFornitoriResponse {\n");
    
    sb.append("    fornitori: ").append(toIndentedString(fornitori)).append("\n");
    sb.append("    messaggio: ").append(toIndentedString(messaggio)).append("\n");
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
