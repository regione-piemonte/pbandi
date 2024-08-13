/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.amministrativoContabile;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Date;
import javax.validation.constraints.*;

public class InfoPiano   {
  private String ibanFondo = null;
  private Integer idAgevolazione = null;
  private Double importoAgevolazione = null;
  private Double debitoResiduoIniziale = null;
  private Integer numRata = null;
  private Double capitale = null;
  private Double interessi = null;
  private String tipoRata = null;
  private Date dataScadenza = null;
  private Integer statoRata = null;
  private Double debitoResiduo = null;

  /**
   **/
  
  @JsonProperty("ibanFondo")
  @NotNull
  @Size(max=34)
  public String getIbanFondo() {
    return ibanFondo;
  }
  public void setIbanFondo(String ibanFondo) {
    this.ibanFondo = ibanFondo;
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
  
  @JsonProperty("importoAgevolazione")
  @NotNull
  public Double getImportoAgevolazione() {
    return importoAgevolazione;
  }
  public void setImportoAgevolazione(Double importoAgevolazione) {
    this.importoAgevolazione = importoAgevolazione;
  }

  /**
   **/
  
  @JsonProperty("debitoResiduoIniziale")
  @NotNull
  public Double getDebitoResiduoIniziale() {
    return debitoResiduoIniziale;
  }
  public void setDebitoResiduoIniziale(Double debitoResiduoIniziale) {
    this.debitoResiduoIniziale = debitoResiduoIniziale;
  }

  /**
   **/
  
  @JsonProperty("numRata")
  @NotNull
  public Integer getNumRata() {
    return numRata;
  }
  public void setNumRata(Integer numRata) {
    this.numRata = numRata;
  }

  /**
   **/
  
  @JsonProperty("capitale")
  @NotNull
  public Double getCapitale() {
    return capitale;
  }
  public void setCapitale(Double capitale) {
    this.capitale = capitale;
  }

  /**
   **/
  
  @JsonProperty("interessi")
  @NotNull
  public Double getInteressi() {
    return interessi;
  }
  public void setInteressi(Double interessi) {
    this.interessi = interessi;
  }

  /**
   **/
  
  @JsonProperty("tipoRata")
  @NotNull
  @Size(max=50)
  public String getTipoRata() {
    return tipoRata;
  }
  public void setTipoRata(String tipoRata) {
    this.tipoRata = tipoRata;
  }

  /**
   **/
  
  @JsonProperty("dataScadenza")
  @NotNull
  public Date getDataScadenza() {
    return dataScadenza;
  }
  public void setDataScadenza(Date dataScadenza) {
    this.dataScadenza = dataScadenza;
  }

  /**
   **/
  
  @JsonProperty("statoRata")
  @NotNull
  public Integer getStatoRata() {
    return statoRata;
  }
  public void setStatoRata(Integer statoRata) {
    this.statoRata = statoRata;
  }

  /**
   **/
  
  @JsonProperty("debitoResiduo")
  @NotNull
  public Double getDebitoResiduo() {
    return debitoResiduo;
  }
  public void setDebitoResiduo(Double debitoResiduo) {
    this.debitoResiduo = debitoResiduo;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InfoPiano infoPiano = (InfoPiano) o;
    return Objects.equals(ibanFondo, infoPiano.ibanFondo) &&
        Objects.equals(idAgevolazione, infoPiano.idAgevolazione) &&
        Objects.equals(importoAgevolazione, infoPiano.importoAgevolazione) &&
        Objects.equals(debitoResiduoIniziale, infoPiano.debitoResiduoIniziale) &&
        Objects.equals(numRata, infoPiano.numRata) &&
        Objects.equals(capitale, infoPiano.capitale) &&
        Objects.equals(interessi, infoPiano.interessi) &&
        Objects.equals(tipoRata, infoPiano.tipoRata) &&
        Objects.equals(dataScadenza, infoPiano.dataScadenza) &&
        Objects.equals(statoRata, infoPiano.statoRata) &&
        Objects.equals(debitoResiduo, infoPiano.debitoResiduo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ibanFondo, idAgevolazione, importoAgevolazione, debitoResiduoIniziale, numRata, capitale, interessi, tipoRata, dataScadenza, statoRata, debitoResiduo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InfoPiano {\n");
    
    sb.append("    ibanFondo: ").append(toIndentedString(ibanFondo)).append("\n");
    sb.append("    idAgevolazione: ").append(toIndentedString(idAgevolazione)).append("\n");
    sb.append("    importoAgevolazione: ").append(toIndentedString(importoAgevolazione)).append("\n");
    sb.append("    debitoResiduoIniziale: ").append(toIndentedString(debitoResiduoIniziale)).append("\n");
    sb.append("    numRata: ").append(toIndentedString(numRata)).append("\n");
    sb.append("    capitale: ").append(toIndentedString(capitale)).append("\n");
    sb.append("    interessi: ").append(toIndentedString(interessi)).append("\n");
    sb.append("    tipoRata: ").append(toIndentedString(tipoRata)).append("\n");
    sb.append("    dataScadenza: ").append(toIndentedString(dataScadenza)).append("\n");
    sb.append("    statoRata: ").append(toIndentedString(statoRata)).append("\n");
    sb.append("    debitoResiduo: ").append(toIndentedString(debitoResiduo)).append("\n");
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
