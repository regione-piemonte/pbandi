/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.amministrativoContabile;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.pbandi.ammvoservrest.dto.InfoPiano;
import java.util.List;
import javax.validation.constraints.*;

public class PianoAmmortamento   {
  private Long idFondo = null;
  private String codiceDomanda = null;
  private String codiceProgetto = null;
  private Integer idBeneficiario = null;
  private List<InfoPiano> infoPiano = new ArrayList<InfoPiano>();

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
  
  @JsonProperty("infoPiano")
  @NotNull
  public List<InfoPiano> getInfoPiano() {
    return infoPiano;
  }
  public void setInfoPiano(List<InfoPiano> infoPiano) {
    this.infoPiano = infoPiano;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PianoAmmortamento pianoAmmortamento = (PianoAmmortamento) o;
    return Objects.equals(idFondo, pianoAmmortamento.idFondo) &&
        Objects.equals(codiceDomanda, pianoAmmortamento.codiceDomanda) &&
        Objects.equals(codiceProgetto, pianoAmmortamento.codiceProgetto) &&
        Objects.equals(idBeneficiario, pianoAmmortamento.idBeneficiario) &&
        Objects.equals(infoPiano, pianoAmmortamento.infoPiano);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idFondo, codiceDomanda, codiceProgetto, idBeneficiario, infoPiano);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PianoAmmortamento {\n");
    
    sb.append("    idFondo: ").append(toIndentedString(idFondo)).append("\n");
    sb.append("    codiceDomanda: ").append(toIndentedString(codiceDomanda)).append("\n");
    sb.append("    codiceProgetto: ").append(toIndentedString(codiceProgetto)).append("\n");
    sb.append("    idBeneficiario: ").append(toIndentedString(idBeneficiario)).append("\n");
    sb.append("    infoPiano: ").append(toIndentedString(infoPiano)).append("\n");
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
