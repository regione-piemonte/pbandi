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

public class Agevolazione   {
  private Integer idAgevolazione = null;
  private String descrizione = null;

  /**
   **/
  
  @JsonProperty("idAgevolazione")
  @NotNull
  public Integer getIdAgevolazione() {
    return idAgevolazione;
  }
  public void setIdAgevolazione(Integer idAgevolazione) {
    this.idAgevolazione = idAgevolazione;
  }

  /**
   **/
  
  @JsonProperty("descrizione")
  @NotNull
  @Size(max=100)
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
    Agevolazione agevolazione = (Agevolazione) o;
    return Objects.equals(idAgevolazione, agevolazione.idAgevolazione) &&
        Objects.equals(descrizione, agevolazione.descrizione);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idAgevolazione, descrizione);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Agevolazione {\n");
    
    sb.append("    idAgevolazione: ").append(toIndentedString(idAgevolazione)).append("\n");
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
