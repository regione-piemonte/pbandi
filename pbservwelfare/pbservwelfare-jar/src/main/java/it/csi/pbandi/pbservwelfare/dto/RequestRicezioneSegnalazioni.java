/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Date;
import javax.validation.constraints.*;

public class RequestRicezioneSegnalazioni   {
  private String numeroDomanda = null;
  private String codiceNotifica = null;
  private Date data = null;
  private String descrizioneNotifica = null;

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
  
  @JsonProperty("codiceNotifica")
  public String getCodiceNotifica() {
    return codiceNotifica;
  }
  public void setCodiceNotifica(String codiceNotifica) {
    this.codiceNotifica = codiceNotifica;
  }

  /**
   **/
  
  @JsonProperty("data")
  public Date getData() {
    return data;
  }
  public void setData(Date data) {
    this.data = data;
  }

  /**
   **/
  
  @JsonProperty("descrizioneNotifica")
  public String getDescrizioneNotifica() {
    return descrizioneNotifica;
  }
  public void setDescrizioneNotifica(String descrizioneNotifica) {
    this.descrizioneNotifica = descrizioneNotifica;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestRicezioneSegnalazioni requestRicezioneSegnalazioni = (RequestRicezioneSegnalazioni) o;
    return Objects.equals(numeroDomanda, requestRicezioneSegnalazioni.numeroDomanda) &&
        Objects.equals(codiceNotifica, requestRicezioneSegnalazioni.codiceNotifica) &&
        Objects.equals(data, requestRicezioneSegnalazioni.data) &&
        Objects.equals(descrizioneNotifica, requestRicezioneSegnalazioni.descrizioneNotifica);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numeroDomanda, codiceNotifica, data, descrizioneNotifica);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestRicezioneSegnalazioni {\n");
    
    sb.append("    numeroDomanda: ").append(toIndentedString(numeroDomanda)).append("\n");
    sb.append("    codiceNotifica: ").append(toIndentedString(codiceNotifica)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    descrizioneNotifica: ").append(toIndentedString(descrizioneNotifica)).append("\n");
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
