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

public class Documenti   {
  private String nomefile = null;
  private String tipologia = null;

  /**
   **/
  
  @JsonProperty("Nomefile")
  public String getNomefile() {
    return nomefile;
  }
  public void setNomefile(String nomefile) {
    this.nomefile = nomefile;
  }

  /**
   **/
  
  @JsonProperty("Tipologia")
  public String getTipologia() {
    return tipologia;
  }
  public void setTipologia(String tipologia) {
    this.tipologia = tipologia;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Documenti documenti = (Documenti) o;
    return Objects.equals(nomefile, documenti.nomefile) &&
        Objects.equals(tipologia, documenti.tipologia);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nomefile, tipologia);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Documenti {\n");
    
    sb.append("    nomefile: ").append(toIndentedString(nomefile)).append("\n");
    sb.append("    tipologia: ").append(toIndentedString(tipologia)).append("\n");
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
