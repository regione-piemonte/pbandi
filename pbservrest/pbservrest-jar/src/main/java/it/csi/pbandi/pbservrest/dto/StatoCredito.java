/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Date;
import javax.validation.constraints.*;

public class StatoCredito   {
  private String codiceProgetto = null;
  private String statoCredito = null;
  private Date dtInzioValidita = null;

  /**
   **/
  
  @JsonProperty("codiceProgetto")
  public String getCodiceProgetto() {
    return codiceProgetto;
  }
  public void setCodiceProgetto(String codiceProgetto) {
    this.codiceProgetto = codiceProgetto;
  }

  /**
   **/
  
  @JsonProperty("statoCredito")
  public String getStatoCredito() {
    return statoCredito;
  }
  public void setStatoCredito(String statoCredito) {
    this.statoCredito = statoCredito;
  }

  /**
   **/
  
  @JsonProperty("dtInzioValidita")
  public Date getDtInzioValidita() {
    return dtInzioValidita;
  }
  public void setDtInzioValidita(Date dtInzioValidita) {
    this.dtInzioValidita = dtInzioValidita;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StatoCredito statoCredito = (StatoCredito) o;
    return Objects.equals(codiceProgetto, statoCredito.codiceProgetto) &&
        Objects.equals(statoCredito, statoCredito.statoCredito) &&
        Objects.equals(dtInzioValidita, statoCredito.dtInzioValidita);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceProgetto, statoCredito, dtInzioValidita);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatoCredito {\n");
    
    sb.append("    codiceProgetto: ").append(toIndentedString(codiceProgetto)).append("\n");
    sb.append("    statoCredito: ").append(toIndentedString(statoCredito)).append("\n");
    sb.append("    dtInzioValidita: ").append(toIndentedString(dtInzioValidita)).append("\n");
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
