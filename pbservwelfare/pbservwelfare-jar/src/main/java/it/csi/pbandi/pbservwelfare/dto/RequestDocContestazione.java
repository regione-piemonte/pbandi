/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.pbandi.pbservwelfare.dto.DocContestazione;
import java.util.List;
import javax.validation.constraints.*;

public class RequestDocContestazione   {
  private String numeroDomanda = null;
  private Integer identificativoContestazione = null;
  private List<DocContestazione> listaDocContestazione = new ArrayList<DocContestazione>();

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
  
  @JsonProperty("identificativoContestazione")
  public Integer getIdentificativoContestazione() {
    return identificativoContestazione;
  }
  public void setIdentificativoContestazione(Integer identificativoContestazione) {
    this.identificativoContestazione = identificativoContestazione;
  }

  /**
   **/
  
  @JsonProperty("listaDocContestazione")
  public List<DocContestazione> getListaDocContestazione() {
    return listaDocContestazione;
  }
  public void setListaDocContestazione(List<DocContestazione> listaDocContestazione) {
    this.listaDocContestazione = listaDocContestazione;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestDocContestazione requestDocContestazione = (RequestDocContestazione) o;
    return Objects.equals(numeroDomanda, requestDocContestazione.numeroDomanda) &&
        Objects.equals(identificativoContestazione, requestDocContestazione.identificativoContestazione) &&
        Objects.equals(listaDocContestazione, requestDocContestazione.listaDocContestazione);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numeroDomanda, identificativoContestazione, listaDocContestazione);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestDocContestazione {\n");
    
    sb.append("    numeroDomanda: ").append(toIndentedString(numeroDomanda)).append("\n");
    sb.append("    identificativoContestazione: ").append(toIndentedString(identificativoContestazione)).append("\n");
    sb.append("    listaDocContestazione: ").append(toIndentedString(listaDocContestazione)).append("\n");
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
