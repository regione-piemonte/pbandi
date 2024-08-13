/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.amministrativoContabile;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Date;
import javax.validation.constraints.*;

public class StatoDistinte   {
  private Integer idDistinta = null;
  private Integer rigaDistinta = null;
  private Date dataStato = null;
  private Integer stato = null;

  /**
   **/
  
  @JsonProperty("idDistinta")
  @NotNull
  public Integer getIdDistinta() {
    return idDistinta;
  }
  public void setIdDistinta(Integer idDistinta) {
    this.idDistinta = idDistinta;
  }

  /**
   **/
  
  @JsonProperty("rigaDistinta")
  @NotNull
  public Integer getRigaDistinta() {
    return rigaDistinta;
  }
  public void setRigaDistinta(Integer rigaDistinta) {
    this.rigaDistinta = rigaDistinta;
  }

  /**
   **/
  
  @JsonProperty("dataStato")
  @NotNull
  public Date getDataStato() {
    return dataStato;
  }
  public void setDataStato(Date dataStato) {
    this.dataStato = dataStato;
  }

  /**
   **/
  
  @JsonProperty("stato")
  @NotNull
  public Integer getStato() {
    return stato;
  }
  public void setStato(Integer stato) {
    this.stato = stato;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StatoDistinte statoDistinte = (StatoDistinte) o;
    return Objects.equals(idDistinta, statoDistinte.idDistinta) &&
        Objects.equals(rigaDistinta, statoDistinte.rigaDistinta) &&
        Objects.equals(dataStato, statoDistinte.dataStato) &&
        Objects.equals(stato, statoDistinte.stato);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idDistinta, rigaDistinta, dataStato, stato);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatoDistinte {\n");
    
    sb.append("    idDistinta: ").append(toIndentedString(idDistinta)).append("\n");
    sb.append("    rigaDistinta: ").append(toIndentedString(rigaDistinta)).append("\n");
    sb.append("    dataStato: ").append(toIndentedString(dataStato)).append("\n");
    sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
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
