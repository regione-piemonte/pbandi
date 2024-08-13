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

public class Direzione   {
  private Integer idDirezione = null;
  private String descrizione = null;
  private Integer tipologia = null;

  /**
   **/
  
  @JsonProperty("idDirezione")
  @NotNull
  public Integer getIdDirezione() {
    return idDirezione;
  }
  public void setIdDirezione(Integer idDirezione) {
    this.idDirezione = idDirezione;
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

  /**
   **/
  
  @JsonProperty("tipologia")
  @NotNull
  public Integer getTipologia() {
    return tipologia;
  }
  public void setTipologia(Integer tipologia) {
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
    Direzione direzione = (Direzione) o;
    return Objects.equals(idDirezione, direzione.idDirezione) &&
        Objects.equals(descrizione, direzione.descrizione) &&
        Objects.equals(tipologia, direzione.tipologia);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idDirezione, descrizione, tipologia);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Direzione {\n");
    
    sb.append("    idDirezione: ").append(toIndentedString(idDirezione)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
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
