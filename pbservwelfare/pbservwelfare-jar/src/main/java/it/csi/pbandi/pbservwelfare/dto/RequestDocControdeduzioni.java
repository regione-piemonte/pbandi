/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.pbandi.pbservwelfare.dto.DocControdeduzioni;
import java.util.List;
import javax.validation.constraints.*;

public class RequestDocControdeduzioni   {
  private String numeroDomanda = null;
  private Integer identificativoControdeduzione = null;
  private List<DocControdeduzioni> listaDocControdeduzioni = new ArrayList<DocControdeduzioni>();

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
  
  @JsonProperty("identificativoControdeduzione")
  public Integer getIdentificativoControdeduzione() {
    return identificativoControdeduzione;
  }
  public void setIdentificativoControdeduzione(Integer identificativoControdeduzione) {
    this.identificativoControdeduzione = identificativoControdeduzione;
  }

  /**
   **/
  
  @JsonProperty("listaDocControdeduzioni")
  public List<DocControdeduzioni> getListaDocControdeduzioni() {
    return listaDocControdeduzioni;
  }
  public void setListaDocControdeduzioni(List<DocControdeduzioni> listaDocControdeduzioni) {
    this.listaDocControdeduzioni = listaDocControdeduzioni;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestDocControdeduzioni requestDocControdeduzioni = (RequestDocControdeduzioni) o;
    return Objects.equals(numeroDomanda, requestDocControdeduzioni.numeroDomanda) &&
        Objects.equals(identificativoControdeduzione, requestDocControdeduzioni.identificativoControdeduzione) &&
        Objects.equals(listaDocControdeduzioni, requestDocControdeduzioni.listaDocControdeduzioni);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numeroDomanda, identificativoControdeduzione, listaDocControdeduzioni);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestDocControdeduzioni {\n");
    
    sb.append("    numeroDomanda: ").append(toIndentedString(numeroDomanda)).append("\n");
    sb.append("    identificativoControdeduzione: ").append(toIndentedString(identificativoControdeduzione)).append("\n");
    sb.append("    listaDocControdeduzioni: ").append(toIndentedString(listaDocControdeduzioni)).append("\n");
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
