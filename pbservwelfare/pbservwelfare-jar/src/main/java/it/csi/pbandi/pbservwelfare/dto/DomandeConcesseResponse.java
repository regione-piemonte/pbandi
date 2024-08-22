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

public class DomandeConcesseResponse   {
  private String messaggio = null;
  private String codiceErrore = null;
  private String esitoServizio = null;
  private String UUID = null;

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
  
  @JsonProperty("UUID")
  public String getUUID() {
    return UUID;
  }
  public void setUUID(String UUID) {
    this.UUID = UUID;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DomandeConcesseResponse domandeConcesseResponse = (DomandeConcesseResponse) o;
    return Objects.equals(messaggio, domandeConcesseResponse.messaggio) &&
        Objects.equals(codiceErrore, domandeConcesseResponse.codiceErrore) &&
        Objects.equals(esitoServizio, domandeConcesseResponse.esitoServizio) &&
        Objects.equals(UUID, domandeConcesseResponse.UUID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(messaggio, codiceErrore, esitoServizio, UUID);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DomandeConcesseResponse {\n");
    
    sb.append("    messaggio: ").append(toIndentedString(messaggio)).append("\n");
    sb.append("    codiceErrore: ").append(toIndentedString(codiceErrore)).append("\n");
    sb.append("    esitoServizio: ").append(toIndentedString(esitoServizio)).append("\n");
    sb.append("    UUID: ").append(toIndentedString(UUID)).append("\n");
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
