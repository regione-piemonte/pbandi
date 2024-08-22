/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.pbandi.pbservwelfare.dto.Documenti;
import java.util.List;
import javax.validation.constraints.*;

public class ModelInvioFornitori   {
  private String codiceFiscaleFornitore = null;
  private List<Documenti> documenti = new ArrayList<Documenti>();

  /**
   **/
  
  @JsonProperty("Codice_fiscale_fornitore")
  public String getCodiceFiscaleFornitore() {
    return codiceFiscaleFornitore;
  }
  public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
    this.codiceFiscaleFornitore = codiceFiscaleFornitore;
  }

  /**
   **/
  
  @JsonProperty("Documenti")
  public List<Documenti> getDocumenti() {
    return documenti;
  }
  public void setDocumenti(List<Documenti> documenti) {
    this.documenti = documenti;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModelInvioFornitori modelInvioFornitori = (ModelInvioFornitori) o;
    return Objects.equals(codiceFiscaleFornitore, modelInvioFornitori.codiceFiscaleFornitore) &&
        Objects.equals(documenti, modelInvioFornitori.documenti);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceFiscaleFornitore, documenti);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelInvioFornitori {\n");
    
    sb.append("    codiceFiscaleFornitore: ").append(toIndentedString(codiceFiscaleFornitore)).append("\n");
    sb.append("    documenti: ").append(toIndentedString(documenti)).append("\n");
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
