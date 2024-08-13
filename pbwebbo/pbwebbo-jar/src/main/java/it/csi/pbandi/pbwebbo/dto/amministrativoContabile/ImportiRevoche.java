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

public class ImportiRevoche   {
  private Long idFondo = null;
  private String codiceDomanda = null;
  private String codiceProgetto = null;
  private Integer idBeneficiario = null;
  private Double importo = null;
  private String causale = null;
  private Integer idAgevolazione = null;

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
  
  @JsonProperty("importo")
  @NotNull
  public Double getImporto() {
    return importo;
  }
  public void setImporto(Double importo) {
    this.importo = importo;
  }

  /**
   **/
  
  @JsonProperty("causale")
  @NotNull
  public String getCausale() {
    return causale;
  }
  public void setCausale(String causale) {
    this.causale = causale;
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImportiRevoche importiRevoche = (ImportiRevoche) o;
    return Objects.equals(idFondo, importiRevoche.idFondo) &&
        Objects.equals(codiceDomanda, importiRevoche.codiceDomanda) &&
        Objects.equals(codiceProgetto, importiRevoche.codiceProgetto) &&
        Objects.equals(idBeneficiario, importiRevoche.idBeneficiario) &&
        Objects.equals(importo, importiRevoche.importo) &&
        Objects.equals(causale, importiRevoche.causale) &&
        Objects.equals(idAgevolazione, importiRevoche.idAgevolazione);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idFondo, codiceDomanda, codiceProgetto, idBeneficiario, importo, causale, idAgevolazione);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ImportiRevoche {\n");
    
    sb.append("    idFondo: ").append(toIndentedString(idFondo)).append("\n");
    sb.append("    codiceDomanda: ").append(toIndentedString(codiceDomanda)).append("\n");
    sb.append("    codiceProgetto: ").append(toIndentedString(codiceProgetto)).append("\n");
    sb.append("    idBeneficiario: ").append(toIndentedString(idBeneficiario)).append("\n");
    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
    sb.append("    causale: ").append(toIndentedString(causale)).append("\n");
    sb.append("    idAgevolazione: ").append(toIndentedString(idAgevolazione)).append("\n");
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
