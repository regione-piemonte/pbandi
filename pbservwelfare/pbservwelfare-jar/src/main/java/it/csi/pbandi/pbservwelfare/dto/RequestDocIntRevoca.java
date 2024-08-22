/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.pbandi.pbservwelfare.dto.DocIntRevoca;
import java.util.List;
import javax.validation.constraints.*;

public class RequestDocIntRevoca   {
  private String numeroDomanda = null;
  private Integer identificativoProcedimentoDiRevoca = null;
  private List<DocIntRevoca> listaDocIntRevoca = new ArrayList<DocIntRevoca>();

  /**
   **/
  
  @JsonProperty("numeroDomanda")
  public String getNumeroDomanda() {
    return numeroDomanda;
  }
  public void setNumeroDomanda(String numeroDomanda) {
    this.numeroDomanda = numeroDomanda;
  }

  /**
   **/
  
  @JsonProperty("identificativoProcedimentoDiRevoca")
  public Integer getIdentificativoProcedimentoDiRevoca() {
    return identificativoProcedimentoDiRevoca;
  }
  public void setIdentificativoProcedimentoDiRevoca(Integer identificativoProcedimentoDiRevoca) {
    this.identificativoProcedimentoDiRevoca = identificativoProcedimentoDiRevoca;
  }

  /**
   **/
  
  @JsonProperty("listaDocIntRevoca")
  public List<DocIntRevoca> getListaDocIntRevoca() {
    return listaDocIntRevoca;
  }
  public void setListaDocIntRevoca(List<DocIntRevoca> listaDocIntRevoca) {
    this.listaDocIntRevoca = listaDocIntRevoca;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestDocIntRevoca requestDocIntRevoca = (RequestDocIntRevoca) o;
    return Objects.equals(numeroDomanda, requestDocIntRevoca.numeroDomanda) &&
        Objects.equals(identificativoProcedimentoDiRevoca, requestDocIntRevoca.identificativoProcedimentoDiRevoca) &&
        Objects.equals(listaDocIntRevoca, requestDocIntRevoca.listaDocIntRevoca);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numeroDomanda, identificativoProcedimentoDiRevoca, listaDocIntRevoca);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestDocIntRevoca {\n");
    
    sb.append("    numeroDomanda: ").append(toIndentedString(numeroDomanda)).append("\n");
    sb.append("    identificativoProcedimentoDiRevoca: ").append(toIndentedString(identificativoProcedimentoDiRevoca)).append("\n");
    sb.append("    listaDocIntRevoca: ").append(toIndentedString(listaDocIntRevoca)).append("\n");
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
