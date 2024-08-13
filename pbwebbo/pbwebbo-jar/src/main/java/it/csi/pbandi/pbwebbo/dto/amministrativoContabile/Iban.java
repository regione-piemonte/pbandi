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

public class Iban   {
  private Long idFondo = null;
  private String iban = null;
  private Integer idAgevolazione = null;
  private Integer moltiplicatore = null;
  private String note = null;
  private String tipoConto = null;
  private String fondoInpis = null;

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
  
  @JsonProperty("iban")
  @NotNull
  @Size(max=34)
  public String getIban() {
    return iban;
  }
  public void setIban(String iban) {
    this.iban = iban;
  }

  /**
   **/
  
  @JsonProperty("idAgevolazione")
  @NotNull
  public Integer getIdAgevolazione() {
    return idAgevolazione;
  }
  public void setIdAgevolazione(Integer idAgevolazione) {
    this.idAgevolazione = idAgevolazione;
  }

  /**
   **/
  
  @JsonProperty("moltiplicatore")
  @NotNull
  public Integer getMoltiplicatore() {
    return moltiplicatore;
  }
  public void setMoltiplicatore(Integer moltiplicatore) {
    this.moltiplicatore = moltiplicatore;
  }

  /**
   **/
  
  @JsonProperty("note")
  @NotNull
  @Size(max=50)
  public String getNote() {
    return note;
  }
  public void setNote(String note) {
    this.note = note;
  }

  /**
   **/
  
  @JsonProperty("tipoConto")
  @NotNull
  @Size(max=50)
  public String getTipoConto() {
    return tipoConto;
  }
  public void setTipoConto(String tipoConto) {
    this.tipoConto = tipoConto;
  }

  /**
   **/
  
  @JsonProperty("fondoInpis")
  @NotNull
  @Size(max=8)
  public String getFondoInpis() {
    return fondoInpis;
  }
  public void setFondoInpis(String fondoInpis) {
    this.fondoInpis = fondoInpis;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Iban iban = (Iban) o;
    return Objects.equals(idFondo, iban.idFondo) &&
        Objects.equals(iban, iban.iban) &&
        Objects.equals(idAgevolazione, iban.idAgevolazione) &&
        Objects.equals(moltiplicatore, iban.moltiplicatore) &&
        Objects.equals(note, iban.note) &&
        Objects.equals(tipoConto, iban.tipoConto) &&
        Objects.equals(fondoInpis, iban.fondoInpis);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idFondo, iban, idAgevolazione, moltiplicatore, note, tipoConto, fondoInpis);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Iban {\n");
    
    sb.append("    idFondo: ").append(toIndentedString(idFondo)).append("\n");
    sb.append("    iban: ").append(toIndentedString(iban)).append("\n");
    sb.append("    idAgevolazione: ").append(toIndentedString(idAgevolazione)).append("\n");
    sb.append("    moltiplicatore: ").append(toIndentedString(moltiplicatore)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
    sb.append("    tipoConto: ").append(toIndentedString(tipoConto)).append("\n");
    sb.append("    fondoInpis: ").append(toIndentedString(fondoInpis)).append("\n");
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
