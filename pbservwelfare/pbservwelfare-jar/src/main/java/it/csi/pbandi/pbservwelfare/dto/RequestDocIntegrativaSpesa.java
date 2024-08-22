/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.pbandi.pbservwelfare.dto.DocIntegrazioneSpesa;
import java.util.List;
import javax.validation.constraints.*;

public class RequestDocIntegrativaSpesa   {
  private Integer identificativoRichiestaDiIntegrazione = null;
  private List<DocIntegrazioneSpesa> listaDocIntegrazioneSpesa = new ArrayList<DocIntegrazioneSpesa>();

  /**
   **/
  
  @JsonProperty("identificativoRichiestaDiIntegrazione")
  public Integer getIdentificativoRichiestaDiIntegrazione() {
    return identificativoRichiestaDiIntegrazione;
  }
  public void setIdentificativoRichiestaDiIntegrazione(Integer identificativoRichiestaDiIntegrazione) {
    this.identificativoRichiestaDiIntegrazione = identificativoRichiestaDiIntegrazione;
  }

  /**
   **/
  
  @JsonProperty("listaDocIntegrazioneSpesa")
  public List<DocIntegrazioneSpesa> getListaDocIntegrazioneSpesa() {
    return listaDocIntegrazioneSpesa;
  }
  public void setListaDocIntegrazioneSpesa(List<DocIntegrazioneSpesa> listaDocIntegrazioneSpesa) {
    this.listaDocIntegrazioneSpesa = listaDocIntegrazioneSpesa;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestDocIntegrativaSpesa requestDocIntegrativaSpesa = (RequestDocIntegrativaSpesa) o;
    return Objects.equals(identificativoRichiestaDiIntegrazione, requestDocIntegrativaSpesa.identificativoRichiestaDiIntegrazione) &&
        Objects.equals(listaDocIntegrazioneSpesa, requestDocIntegrativaSpesa.listaDocIntegrazioneSpesa);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identificativoRichiestaDiIntegrazione, listaDocIntegrazioneSpesa);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestDocIntegrativaSpesa {\n");
    
    sb.append("    identificativoRichiestaDiIntegrazione: ").append(toIndentedString(identificativoRichiestaDiIntegrazione)).append("\n");
    sb.append("    listaDocIntegrazioneSpesa: ").append(toIndentedString(listaDocIntegrazioneSpesa)).append("\n");
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
