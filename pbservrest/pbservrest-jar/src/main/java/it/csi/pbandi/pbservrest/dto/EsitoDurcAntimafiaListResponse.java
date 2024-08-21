/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.pbandi.pbservrest.dto.EsitoDurcAntimafia;
import java.util.List;
import javax.validation.constraints.*;

public class EsitoDurcAntimafiaListResponse   {
  private List<EsitoDurcAntimafia> esitoDurcAntimafiaList = new ArrayList<EsitoDurcAntimafia>();

  /**
   **/
  
  @JsonProperty("esitoDurcAntimafiaList")
  public List<EsitoDurcAntimafia> getEsitoDurcAntimafiaList() {
    return esitoDurcAntimafiaList;
  }
  public void setEsitoDurcAntimafiaList(List<EsitoDurcAntimafia> esitoDurcAntimafiaList) {
    this.esitoDurcAntimafiaList = esitoDurcAntimafiaList;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EsitoDurcAntimafiaListResponse esitoDurcAntimafiaListResponse = (EsitoDurcAntimafiaListResponse) o;
    return Objects.equals(esitoDurcAntimafiaList, esitoDurcAntimafiaListResponse.esitoDurcAntimafiaList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(esitoDurcAntimafiaList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsitoDurcAntimafiaListResponse {\n");
    
    sb.append("    esitoDurcAntimafiaList: ").append(toIndentedString(esitoDurcAntimafiaList)).append("\n");
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
