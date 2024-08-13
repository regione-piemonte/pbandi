/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.amministrativoContabile;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;

public class DebitoResiduo   {
  private Long idFondo = null;
  private String codiceDomanda = null;
  private String codiceProgetto = null;
  private Double debitoResiduo = null;

  /**
   **/
  
  @JsonProperty("idFondo")
  @NotNull
  public Long getIdFondo() {
    return idFondo;
  }
  public void setIdFondo(Long idFondo) {
    this.idFondo = idFondo;
  }

  /**
   **/
  
  @JsonProperty("codiceDomanda")
  @NotNull
  @Size(max=10)
  public String getCodiceDomanda() {
    return codiceDomanda;
  }
  public void setCodiceDomanda(String codiceDomanda) {
    this.codiceDomanda = codiceDomanda;
  }

  /**
   **/
  
  @JsonProperty("codiceProgetto")
  @NotNull
  @Size(max=10)
  public String getCodiceProgetto() {
    return codiceProgetto;
  }
  public void setCodiceProgetto(String codiceProgetto) {
    this.codiceProgetto = codiceProgetto;
  }

  /**
   **/
  
  @JsonProperty("debitoResiduo")
  @NotNull
  public Double getDebitoResiduo() {
    return debitoResiduo;
  }
  public void setDebitoResiduo(Double debitoResiduo) {
    this.debitoResiduo = debitoResiduo;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DebitoResiduo debitoResiduo = (DebitoResiduo) o;
    return Objects.equals(idFondo, debitoResiduo.idFondo) &&
        Objects.equals(codiceDomanda, debitoResiduo.codiceDomanda) &&
        Objects.equals(codiceProgetto, debitoResiduo.codiceProgetto) &&
        Objects.equals(debitoResiduo, debitoResiduo.debitoResiduo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idFondo, codiceDomanda, codiceProgetto, debitoResiduo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DebitoResiduo {\n");
    
    sb.append("    idFondo: ").append(toIndentedString(idFondo)).append("\n");
    sb.append("    codiceDomanda: ").append(toIndentedString(codiceDomanda)).append("\n");
    sb.append("    codiceProgetto: ").append(toIndentedString(codiceProgetto)).append("\n");
    sb.append("    debitoResiduo: ").append(toIndentedString(debitoResiduo)).append("\n");
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
