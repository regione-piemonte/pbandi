/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;

public class Esito   {
  private String esitoServizio = null;
  private Integer codiceErrore = null;
  private String descErrore = null;

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
    Esito esito = (Esito) o;
    return Objects.equals(esitoServizio, esito.esitoServizio) &&
        Objects.equals(codiceErrore, esito.codiceErrore) &&
        Objects.equals(descErrore, esito.descErrore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(esitoServizio, codiceErrore, descErrore);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Esito {\n");
    
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
