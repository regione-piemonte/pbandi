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

public class InfoContabile   {
  private Integer idAgevolazione = null;
  private String ibanFondo = null;
  private String codCausale = null;
  private String desCausale = null;
  private Double importo = null;
  private Date dataContabile = null;
  private Date dataValuta = null;
  private Integer numRata = null;

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
  
  @JsonProperty("codCausale")
  @NotNull
  @Size(max=4)
  public String getCodCausale() {
    return codCausale;
  }
  public void setCodCausale(String codCausale) {
    this.codCausale = codCausale;
  }

  /**
   **/
  
  @JsonProperty("desCausale")
  @NotNull
  @Size(max=40)
  public String getDesCausale() {
    return desCausale;
  }
  public void setDesCausale(String desCausale) {
    this.desCausale = desCausale;
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
  
  @JsonProperty("dataContabile")
  @NotNull
  public Date getDataContabile() {
    return dataContabile;
  }
  public void setDataContabile(Date dataContabile) {
    this.dataContabile = dataContabile;
  }

  /**
   **/
  
  @JsonProperty("dataValuta")
  @NotNull
  public Date getDataValuta() {
    return dataValuta;
  }
  public void setDataValuta(Date dataValuta) {
    this.dataValuta = dataValuta;
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InfoContabile infoContabile = (InfoContabile) o;
    return Objects.equals(idAgevolazione, infoContabile.idAgevolazione) &&
        Objects.equals(ibanFondo, infoContabile.ibanFondo) &&
        Objects.equals(codCausale, infoContabile.codCausale) &&
        Objects.equals(desCausale, infoContabile.desCausale) &&
        Objects.equals(importo, infoContabile.importo) &&
        Objects.equals(dataContabile, infoContabile.dataContabile) &&
        Objects.equals(dataValuta, infoContabile.dataValuta) &&
        Objects.equals(numRata, infoContabile.numRata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idAgevolazione, ibanFondo, codCausale, desCausale, importo, dataContabile, dataValuta, numRata);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InfoContabile {\n");
    
    sb.append("    idAgevolazione: ").append(toIndentedString(idAgevolazione)).append("\n");
    sb.append("    ibanFondo: ").append(toIndentedString(ibanFondo)).append("\n");
    sb.append("    codCausale: ").append(toIndentedString(codCausale)).append("\n");
    sb.append("    desCausale: ").append(toIndentedString(desCausale)).append("\n");
    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
    sb.append("    dataContabile: ").append(toIndentedString(dataContabile)).append("\n");
    sb.append("    dataValuta: ").append(toIndentedString(dataValuta)).append("\n");
    sb.append("    numRata: ").append(toIndentedString(numRata)).append("\n");
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
