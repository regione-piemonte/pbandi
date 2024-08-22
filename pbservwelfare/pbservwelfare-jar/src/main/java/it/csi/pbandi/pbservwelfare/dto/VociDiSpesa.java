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

public class VociDiSpesa   {
  private String codiceBando = null;
  private String idVoceDiSpesa = null;
  private String descrizioneVoceDiSpesa = null;
  private String codTipoVoceDiSpesa = null;

  /**
   **/
  
  @JsonProperty("codiceBando")
  public String getCodiceBando() {
    return codiceBando;
  }
  public void setCodiceBando(String codiceBando) {
    this.codiceBando = codiceBando;
  }

  /**
   **/
  
  @JsonProperty("idVoceDiSpesa")
  public String getIdVoceDiSpesa() {
    return idVoceDiSpesa;
  }
  public void setIdVoceDiSpesa(String idVoceDiSpesa) {
    this.idVoceDiSpesa = idVoceDiSpesa;
  }

  /**
   **/
  
  @JsonProperty("descrizioneVoceDiSpesa")
  public String getDescrizioneVoceDiSpesa() {
    return descrizioneVoceDiSpesa;
  }
  public void setDescrizioneVoceDiSpesa(String descrizioneVoceDiSpesa) {
    this.descrizioneVoceDiSpesa = descrizioneVoceDiSpesa;
  }

  /**
   **/
  
  @JsonProperty("codTipoVoceDiSpesa")
  public String getCodTipoVoceDiSpesa() {
    return codTipoVoceDiSpesa;
  }
  public void setCodTipoVoceDiSpesa(String codTipoVoceDiSpesa) {
    this.codTipoVoceDiSpesa = codTipoVoceDiSpesa;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VociDiSpesa vociDiSpesa = (VociDiSpesa) o;
    return Objects.equals(codiceBando, vociDiSpesa.codiceBando) &&
        Objects.equals(idVoceDiSpesa, vociDiSpesa.idVoceDiSpesa) &&
        Objects.equals(descrizioneVoceDiSpesa, vociDiSpesa.descrizioneVoceDiSpesa) &&
        Objects.equals(codTipoVoceDiSpesa, vociDiSpesa.codTipoVoceDiSpesa);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceBando, idVoceDiSpesa, descrizioneVoceDiSpesa, codTipoVoceDiSpesa);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VociDiSpesa {\n");
    
    sb.append("    codiceBando: ").append(toIndentedString(codiceBando)).append("\n");
    sb.append("    idVoceDiSpesa: ").append(toIndentedString(idVoceDiSpesa)).append("\n");
    sb.append("    descrizioneVoceDiSpesa: ").append(toIndentedString(descrizioneVoceDiSpesa)).append("\n");
    sb.append("    codTipoVoceDiSpesa: ").append(toIndentedString(codTipoVoceDiSpesa)).append("\n");
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
