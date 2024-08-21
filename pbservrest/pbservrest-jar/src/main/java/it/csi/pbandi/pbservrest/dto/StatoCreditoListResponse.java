/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.pbandi.pbservrest.dto.StatoCredito;
import java.util.List;
import javax.validation.constraints.*;

public class StatoCreditoListResponse   {
  private List<StatoCredito> statiCreditoList = new ArrayList<StatoCredito>();

  /**
   **/
  
  @JsonProperty("statiCreditoList")
  public List<StatoCredito> getStatiCreditoList() {
    return statiCreditoList;
  }
  public void setStatiCreditoList(List<StatoCredito> statiCreditoList) {
    this.statiCreditoList = statiCreditoList;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StatoCreditoListResponse statoCreditoListResponse = (StatoCreditoListResponse) o;
    return Objects.equals(statiCreditoList, statoCreditoListResponse.statiCreditoList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statiCreditoList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatoCreditoListResponse {\n");
    
    sb.append("    statiCreditoList: ").append(toIndentedString(statiCreditoList)).append("\n");
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
