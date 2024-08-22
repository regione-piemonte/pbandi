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

public class RequestRicezioneIban   {
  private String numeroDomanda = null;
  private String iban = null;
  private String codiceStruttura = null;
  private String denominazioneStruttura = null;
  private String intestatario = null;

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
  
  @JsonProperty("iban")
  public String getIban() {
    return iban;
  }
  public void setIban(String iban) {
    this.iban = iban;
  }

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
  
  @JsonProperty("denominazioneStruttura")
  public String getDenominazioneStruttura() {
    return denominazioneStruttura;
  }
  public void setDenominazioneStruttura(String denominazioneStruttura) {
    this.denominazioneStruttura = denominazioneStruttura;
  }

  /**
   **/
  
  @JsonProperty("intestatario")
  public String getIntestatario() {
    return intestatario;
  }
  public void setIntestatario(String intestatario) {
    this.intestatario = intestatario;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestRicezioneIban requestRicezioneIban = (RequestRicezioneIban) o;
    return Objects.equals(numeroDomanda, requestRicezioneIban.numeroDomanda) &&
        Objects.equals(iban, requestRicezioneIban.iban) &&
        Objects.equals(codiceStruttura, requestRicezioneIban.codiceStruttura) &&
        Objects.equals(denominazioneStruttura, requestRicezioneIban.denominazioneStruttura) &&
        Objects.equals(intestatario, requestRicezioneIban.intestatario);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numeroDomanda, iban, codiceStruttura, denominazioneStruttura, intestatario);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestRicezioneIban {\n");
    
    sb.append("    numeroDomanda: ").append(toIndentedString(numeroDomanda)).append("\n");
    sb.append("    iban: ").append(toIndentedString(iban)).append("\n");
    sb.append("    codiceStruttura: ").append(toIndentedString(codiceStruttura)).append("\n");
    sb.append("    denominazioneStruttura: ").append(toIndentedString(denominazioneStruttura)).append("\n");
    sb.append("    intestatario: ").append(toIndentedString(intestatario)).append("\n");
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
