/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.pbandi.pbservwelfare.dto.VociDiSpesa;
import java.util.List;
import javax.validation.constraints.*;

public class EsitoVociDiSpesaResponse   {
  private List<VociDiSpesa> vociDiSpesa = new ArrayList<VociDiSpesa>();
  private String messaggio = null;
  private String codiceErrore = null;
  private String esitoServizio = null;

  /**
   **/
  
  @JsonProperty("vociDiSpesa")
  public List<VociDiSpesa> getVociDiSpesa() {
    return vociDiSpesa;
  }
  public void setVociDiSpesa(List<VociDiSpesa> vociDiSpesa) {
    this.vociDiSpesa = vociDiSpesa;
  }

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
  
  @JsonProperty("esitoServizio")
  public String getEsitoServizio() {
    return esitoServizio;
  }
  public void setEsitoServizio(String esitoServizio) {
    this.esitoServizio = esitoServizio;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EsitoVociDiSpesaResponse esitoVociDiSpesaResponse = (EsitoVociDiSpesaResponse) o;
    return Objects.equals(vociDiSpesa, esitoVociDiSpesaResponse.vociDiSpesa) &&
        Objects.equals(messaggio, esitoVociDiSpesaResponse.messaggio) &&
        Objects.equals(codiceErrore, esitoVociDiSpesaResponse.codiceErrore) &&
        Objects.equals(esitoServizio, esitoVociDiSpesaResponse.esitoServizio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vociDiSpesa, messaggio, codiceErrore, esitoServizio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsitoVociDiSpesaResponse {\n");
    
    sb.append("    vociDiSpesa: ").append(toIndentedString(vociDiSpesa)).append("\n");
    sb.append("    messaggio: ").append(toIndentedString(messaggio)).append("\n");
    sb.append("    codiceErrore: ").append(toIndentedString(codiceErrore)).append("\n");
    sb.append("    esitoServizio: ").append(toIndentedString(esitoServizio)).append("\n");
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
