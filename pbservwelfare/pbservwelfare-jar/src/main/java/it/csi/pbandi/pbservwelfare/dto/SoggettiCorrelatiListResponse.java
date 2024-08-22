/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.pbandi.pbservwelfare.dto.SoggettoCorrelato;
import java.util.List;
import javax.validation.constraints.*;

public class SoggettiCorrelatiListResponse   {
  private String esitoServizio = null;
  private String messaggio = null;
  private String codiceErrore = null;
  private List<SoggettoCorrelato> soggettiCorrelatiList = new ArrayList<SoggettoCorrelato>();

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
  
  @JsonProperty("soggettiCorrelatiList")
  public List<SoggettoCorrelato> getSoggettiCorrelatiList() {
    return soggettiCorrelatiList;
  }
  public void setSoggettiCorrelatiList(List<SoggettoCorrelato> soggettiCorrelatiList) {
    this.soggettiCorrelatiList = soggettiCorrelatiList;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SoggettiCorrelatiListResponse soggettiCorrelatiListResponse = (SoggettiCorrelatiListResponse) o;
    return Objects.equals(esitoServizio, soggettiCorrelatiListResponse.esitoServizio) &&
        Objects.equals(messaggio, soggettiCorrelatiListResponse.messaggio) &&
        Objects.equals(codiceErrore, soggettiCorrelatiListResponse.codiceErrore) &&
        Objects.equals(soggettiCorrelatiList, soggettiCorrelatiListResponse.soggettiCorrelatiList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(esitoServizio, messaggio, codiceErrore, soggettiCorrelatiList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SoggettiCorrelatiListResponse {\n");
    
    sb.append("    esitoServizio: ").append(toIndentedString(esitoServizio)).append("\n");
    sb.append("    messaggio: ").append(toIndentedString(messaggio)).append("\n");
    sb.append("    codiceErrore: ").append(toIndentedString(codiceErrore)).append("\n");
    sb.append("    soggettiCorrelatiList: ").append(toIndentedString(soggettiCorrelatiList)).append("\n");
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
