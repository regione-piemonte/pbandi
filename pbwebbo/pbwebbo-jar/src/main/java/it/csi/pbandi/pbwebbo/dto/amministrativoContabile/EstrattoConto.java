/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.amministrativoContabile;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.pbandi.ammvoservrest.dto.InfoContabile;
import java.util.List;
import javax.validation.constraints.*;

public class EstrattoConto   {
  private Long idFondo = null;
  private String codiceDomanda = null;
  private String codiceProgetto = null;
  private Integer idBeneficiario = null;
  private List<InfoContabile> infoContabili = new ArrayList<InfoContabile>();

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
  @Size(max=25)
  public String getCodiceProgetto() {
    return codiceProgetto;
  }
  public void setCodiceProgetto(String codiceProgetto) {
    this.codiceProgetto = codiceProgetto;
  }

  /**
   **/
  
  @JsonProperty("idBeneficiario")
  @NotNull
  public Integer getIdBeneficiario() {
    return idBeneficiario;
  }
  public void setIdBeneficiario(Integer idBeneficiario) {
    this.idBeneficiario = idBeneficiario;
  }

  /**
   **/
  
  @JsonProperty("infoContabili")
  @NotNull
  public List<InfoContabile> getInfoContabili() {
    return infoContabili;
  }
  public void setInfoContabili(List<InfoContabile> infoContabili) {
    this.infoContabili = infoContabili;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EstrattoConto estrattoConto = (EstrattoConto) o;
    return Objects.equals(idFondo, estrattoConto.idFondo) &&
        Objects.equals(codiceDomanda, estrattoConto.codiceDomanda) &&
        Objects.equals(codiceProgetto, estrattoConto.codiceProgetto) &&
        Objects.equals(idBeneficiario, estrattoConto.idBeneficiario) &&
        Objects.equals(infoContabili, estrattoConto.infoContabili);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idFondo, codiceDomanda, codiceProgetto, idBeneficiario, infoContabili);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EstrattoConto {\n");
    
    sb.append("    idFondo: ").append(toIndentedString(idFondo)).append("\n");
    sb.append("    codiceDomanda: ").append(toIndentedString(codiceDomanda)).append("\n");
    sb.append("    codiceProgetto: ").append(toIndentedString(codiceProgetto)).append("\n");
    sb.append("    idBeneficiario: ").append(toIndentedString(idBeneficiario)).append("\n");
    sb.append("    infoContabili: ").append(toIndentedString(infoContabili)).append("\n");
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
