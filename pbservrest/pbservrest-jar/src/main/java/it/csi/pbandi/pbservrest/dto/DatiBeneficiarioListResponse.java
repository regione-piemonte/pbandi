/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.pbandi.pbservrest.dto.DatiBeneficiario;
import java.util.List;
import javax.validation.constraints.*;

public class DatiBeneficiarioListResponse   {
  private List<DatiBeneficiario> datiBeneficiarioList = new ArrayList<DatiBeneficiario>();

  /**
   **/
  
  @JsonProperty("datiBeneficiarioList")
  public List<DatiBeneficiario> getDatiBeneficiarioList() {
    return datiBeneficiarioList;
  }
  public void setDatiBeneficiarioList(List<DatiBeneficiario> datiBeneficiarioList) {
    this.datiBeneficiarioList = datiBeneficiarioList;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatiBeneficiarioListResponse datiBeneficiarioListResponse = (DatiBeneficiarioListResponse) o;
    return Objects.equals(datiBeneficiarioList, datiBeneficiarioListResponse.datiBeneficiarioList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datiBeneficiarioList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatiBeneficiarioListResponse {\n");
    
    sb.append("    datiBeneficiarioList: ").append(toIndentedString(datiBeneficiarioList)).append("\n");
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
