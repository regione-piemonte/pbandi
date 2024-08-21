/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.pbandi.pbservrest.dto.DomandaInfo;
import java.util.List;
import javax.validation.constraints.*;

public class DomandaInfoListResponse   {
  private String esito = null;
  private String messaggioErrore = null;
  private List<DomandaInfo> domandaInfoList = new ArrayList<DomandaInfo>();

  /**
   **/
  
  @JsonProperty("esito")
  public String getEsito() {
    return esito;
  }
  public void setEsito(String esito) {
    this.esito = esito;
  }

  /**
   **/
  
  @JsonProperty("messaggioErrore")
  public String getMessaggioErrore() {
    return messaggioErrore;
  }
  public void setMessaggioErrore(String messaggioErrore) {
    this.messaggioErrore = messaggioErrore;
  }

  /**
   **/
  
  @JsonProperty("DomandaInfoList")
  public List<DomandaInfo> getDomandaInfoList() {
    return domandaInfoList;
  }
  public void setDomandaInfoList(List<DomandaInfo> domandaInfoList) {
    this.domandaInfoList = domandaInfoList;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DomandaInfoListResponse domandaInfoListResponse = (DomandaInfoListResponse) o;
    return Objects.equals(esito, domandaInfoListResponse.esito) &&
        Objects.equals(messaggioErrore, domandaInfoListResponse.messaggioErrore) &&
        Objects.equals(domandaInfoList, domandaInfoListResponse.domandaInfoList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(esito, messaggioErrore, domandaInfoList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DomandaInfoListResponse {\n");
    
    sb.append("    esito: ").append(toIndentedString(esito)).append("\n");
    sb.append("    messaggioErrore: ").append(toIndentedString(messaggioErrore)).append("\n");
    sb.append("    domandaInfoList: ").append(toIndentedString(domandaInfoList)).append("\n");
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
