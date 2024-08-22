/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.pbandi.pbservwelfare.dto.DocumentoSpesa;
import java.util.List;
import javax.validation.constraints.*;

public class DocumentiSpesaListResponse   {
  private String messaggio = null;
  private String codiceErrore = null;
  private List<DocumentoSpesa> documentiSpesaList = new ArrayList<DocumentoSpesa>();

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
  
  @JsonProperty("documentiSpesaList")
  public List<DocumentoSpesa> getDocumentiSpesaList() {
    return documentiSpesaList;
  }
  public void setDocumentiSpesaList(List<DocumentoSpesa> documentiSpesaList) {
    this.documentiSpesaList = documentiSpesaList;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentiSpesaListResponse documentiSpesaListResponse = (DocumentiSpesaListResponse) o;
    return Objects.equals(messaggio, documentiSpesaListResponse.messaggio) &&
        Objects.equals(codiceErrore, documentiSpesaListResponse.codiceErrore) &&
        Objects.equals(documentiSpesaList, documentiSpesaListResponse.documentiSpesaList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(messaggio, codiceErrore, documentiSpesaList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentiSpesaListResponse {\n");
    
    sb.append("    messaggio: ").append(toIndentedString(messaggio)).append("\n");
    sb.append("    codiceErrore: ").append(toIndentedString(codiceErrore)).append("\n");
    sb.append("    documentiSpesaList: ").append(toIndentedString(documentiSpesaList)).append("\n");
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
