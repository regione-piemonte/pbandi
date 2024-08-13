/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.amministrativoContabile;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;

public class Natura   {
  private Integer idNatura = null;
  private String descrizione = null;

  /**
   **/
  
  @JsonProperty("idNatura")
  @NotNull
  public Integer getIdNatura() {
    return idNatura;
  }
  public void setIdNatura(Integer idNatura) {
    this.idNatura = idNatura;
  }

  /**
   **/
  
  @JsonProperty("descrizione")
  @NotNull
  @Size(max=60)
  public String getDescrizione() {
    return descrizione;
  }
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Natura natura = (Natura) o;
    return Objects.equals(idNatura, natura.idNatura) &&
        Objects.equals(descrizione, natura.descrizione);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idNatura, descrizione);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Natura {\n");
    
    sb.append("    idNatura: ").append(toIndentedString(idNatura)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
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
