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

public class Anagrafica   {
  private Long idFondo = null;
  private String descrizione = null;
  private String contropartita = null;
  private Integer idNatura = null;
  private Integer idDirezione = null;
  private String note = null;

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
  
  @JsonProperty("descrizione")
  @NotNull
  @Size(max=60)
  public String getDescrizione() {
    return descrizione;
  }
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  /**
   **/
  
  @JsonProperty("contropartita")
  @NotNull
  @Size(max=10)
  public String getContropartita() {
    return contropartita;
  }
  public void setContropartita(String contropartita) {
    this.contropartita = contropartita;
  }

  /**
   **/
  
  @JsonProperty("idNatura")
  @NotNull
  public Integer getIdNatura() {
    return idNatura;
  }
  public void setIdNatura(Integer idNatura) {
    this.idNatura = idNatura;
  }

  /**
   **/
  
  @JsonProperty("idDirezione")
  @NotNull
  public Integer getIdDirezione() {
    return idDirezione;
  }
  public void setIdDirezione(Integer idDirezione) {
    this.idDirezione = idDirezione;
  }

  /**
   **/
  
  @JsonProperty("note")
  @NotNull
  @Size(max=100)
  public String getNote() {
    return note;
  }
  public void setNote(String note) {
    this.note = note;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Anagrafica anagrafica = (Anagrafica) o;
    return Objects.equals(idFondo, anagrafica.idFondo) &&
        Objects.equals(descrizione, anagrafica.descrizione) &&
        Objects.equals(contropartita, anagrafica.contropartita) &&
        Objects.equals(idNatura, anagrafica.idNatura) &&
        Objects.equals(idDirezione, anagrafica.idDirezione) &&
        Objects.equals(note, anagrafica.note);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idFondo, descrizione, contropartita, idNatura, idDirezione, note);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Anagrafica {\n");
    
    sb.append("    idFondo: ").append(toIndentedString(idFondo)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    contropartita: ").append(toIndentedString(contropartita)).append("\n");
    sb.append("    idNatura: ").append(toIndentedString(idNatura)).append("\n");
    sb.append("    idDirezione: ").append(toIndentedString(idDirezione)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
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
