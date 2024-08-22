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

public class RequestInfoStrutture   {
  private String codiceStruttura = null;
  private String email = null;

  /**
   **/
  
  @JsonProperty("codiceStruttura")
  public String getCodiceStruttura() {
    return codiceStruttura;
  }
  public void setCodiceStruttura(String codiceStruttura) {
    this.codiceStruttura = codiceStruttura;
  }

  /**
   **/
  
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestInfoStrutture requestInfoStrutture = (RequestInfoStrutture) o;
    return Objects.equals(codiceStruttura, requestInfoStrutture.codiceStruttura) &&
        Objects.equals(email, requestInfoStrutture.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceStruttura, email);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestInfoStrutture {\n");
    
    sb.append("    codiceStruttura: ").append(toIndentedString(codiceStruttura)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
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
