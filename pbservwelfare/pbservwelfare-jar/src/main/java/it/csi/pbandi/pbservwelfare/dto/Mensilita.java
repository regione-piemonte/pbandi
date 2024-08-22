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

public class Mensilita   {
  private String valore = null;
  private String sabbatico = null;

  /**
   **/
  
  @JsonProperty("Valore")
  public String getValore() {
    return valore;
  }
  public void setValore(String valore) {
    this.valore = valore;
  }

  /**
   **/
  
  @JsonProperty("Sabbatico")
  public String getSabbatico() {
    return sabbatico;
  }
  public void setSabbatico(String sabbatico) {
    this.sabbatico = sabbatico;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Mensilita mensilita = (Mensilita) o;
    return Objects.equals(valore, mensilita.valore) &&
        Objects.equals(sabbatico, mensilita.sabbatico);
  }

  @Override
  public int hashCode() {
    return Objects.hash(valore, sabbatico);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Mensilita {\n");
    
    sb.append("    valore: ").append(toIndentedString(valore)).append("\n");
    sb.append("    sabbatico: ").append(toIndentedString(sabbatico)).append("\n");
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
