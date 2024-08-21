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

public class RequestCupVO   {
  private String codiceCup = null;
  private String numeroDomanda = null;

  /**
   **/
  
  @JsonProperty("codiceCup")
  public String getCodiceCup() {
    return codiceCup;
  }
  public void setCodiceCup(String codiceCup) {
    this.codiceCup = codiceCup;
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestCupVO requestCupVO = (RequestCupVO) o;
    return Objects.equals(codiceCup, requestCupVO.codiceCup) &&
        Objects.equals(numeroDomanda, requestCupVO.numeroDomanda);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceCup, numeroDomanda);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestCupVO {\n");
    
    sb.append("    codiceCup: ").append(toIndentedString(codiceCup)).append("\n");
    sb.append("    numeroDomanda: ").append(toIndentedString(numeroDomanda)).append("\n");
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
