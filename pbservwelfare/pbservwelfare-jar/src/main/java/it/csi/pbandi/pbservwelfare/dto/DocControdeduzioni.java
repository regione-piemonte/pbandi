/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;

public class DocControdeduzioni   {
  private byte[] documento = null;
  private String nomeDocumento = null;

  /**
   **/
  
  @JsonProperty("documento")
  public byte[] getDocumento() {
    return documento;
  }
  public void setDocumento(byte[] documento) {
    this.documento = documento;
  }

  /**
   **/
  
  @JsonProperty("nomeDocumento")
  public String getNomeDocumento() {
    return nomeDocumento;
  }
  public void setNomeDocumento(String nomeDocumento) {
    this.nomeDocumento = nomeDocumento;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocControdeduzioni docControdeduzioni = (DocControdeduzioni) o;
    return Objects.equals(documento, docControdeduzioni.documento) &&
        Objects.equals(nomeDocumento, docControdeduzioni.nomeDocumento);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documento, nomeDocumento);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocControdeduzioni {\n");
    
    sb.append("    documento: ").append(toIndentedString(documento)).append("\n");
    sb.append("    nomeDocumento: ").append(toIndentedString(nomeDocumento)).append("\n");
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
