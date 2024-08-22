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

public class ElencoMensilita   {
  private Integer dichiarazioneSpesa = null;
  private Integer anno = null;
  private String mese = null;
  private String esitoValidazione = null;
  private Boolean sabbatico = null;
  private Double importoRendicontato = null;
  private Double importoValidato = null;
  private Double importoInErogazione = null;
  private Date dataAggiornamento = null;

  /**
   **/
  
  @JsonProperty("dichiarazioneSpesa")
  public Integer getDichiarazioneSpesa() {
    return dichiarazioneSpesa;
  }
  public void setDichiarazioneSpesa(Integer dichiarazioneSpesa) {
    this.dichiarazioneSpesa = dichiarazioneSpesa;
  }

  /**
   **/
  
  @JsonProperty("anno")
  public Integer getAnno() {
    return anno;
  }
  public void setAnno(Integer anno) {
    this.anno = anno;
  }

  /**
   **/
  
  @JsonProperty("mese")
  public String getMese() {
    return mese;
  }
  public void setMese(String mese) {
    this.mese = mese;
  }

  /**
   **/
  
  @JsonProperty("esitoValidazione")
  public String getEsitoValidazione() {
    return esitoValidazione;
  }
  public void setEsitoValidazione(String esitoValidazione) {
    this.esitoValidazione = esitoValidazione;
  }

  /**
   **/
  
  @JsonProperty("sabbatico")
  public Boolean isSabbatico() {
    return sabbatico;
  }
  public void setSabbatico(Boolean sabbatico) {
    this.sabbatico = sabbatico;
  }

  /**
   **/
  
  @JsonProperty("importoRendicontato")
  public Double getImportoRendicontato() {
    return importoRendicontato;
  }
  public void setImportoRendicontato(Double importoRendicontato) {
    this.importoRendicontato = importoRendicontato;
  }

  /**
   **/
  
  @JsonProperty("importoValidato")
  public Double getImportoValidato() {
    return importoValidato;
  }
  public void setImportoValidato(Double importoValidato) {
    this.importoValidato = importoValidato;
  }

  /**
   **/
  
  @JsonProperty("importoInErogazione")
  public Double getImportoInErogazione() {
    return importoInErogazione;
  }
  public void setImportoInErogazione(Double importoInErogazione) {
    this.importoInErogazione = importoInErogazione;
  }

  /**
   **/
  
  @JsonProperty("dataAggiornamento")
  public Date getDataAggiornamento() {
    return dataAggiornamento;
  }
  public void setDataAggiornamento(Date dataAggiornamento) {
    this.dataAggiornamento = dataAggiornamento;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ElencoMensilita elencoMensilita = (ElencoMensilita) o;
    return Objects.equals(dichiarazioneSpesa, elencoMensilita.dichiarazioneSpesa) &&
        Objects.equals(anno, elencoMensilita.anno) &&
        Objects.equals(mese, elencoMensilita.mese) &&
        Objects.equals(esitoValidazione, elencoMensilita.esitoValidazione) &&
        Objects.equals(sabbatico, elencoMensilita.sabbatico) &&
        Objects.equals(importoRendicontato, elencoMensilita.importoRendicontato) &&
        Objects.equals(importoValidato, elencoMensilita.importoValidato) &&
        Objects.equals(importoInErogazione, elencoMensilita.importoInErogazione) &&
        Objects.equals(dataAggiornamento, elencoMensilita.dataAggiornamento);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dichiarazioneSpesa, anno, mese, esitoValidazione, sabbatico, importoRendicontato, importoValidato, importoInErogazione, dataAggiornamento);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ElencoMensilita {\n");
    
    sb.append("    dichiarazioneSpesa: ").append(toIndentedString(dichiarazioneSpesa)).append("\n");
    sb.append("    anno: ").append(toIndentedString(anno)).append("\n");
    sb.append("    mese: ").append(toIndentedString(mese)).append("\n");
    sb.append("    esitoValidazione: ").append(toIndentedString(esitoValidazione)).append("\n");
    sb.append("    sabbatico: ").append(toIndentedString(sabbatico)).append("\n");
    sb.append("    importoRendicontato: ").append(toIndentedString(importoRendicontato)).append("\n");
    sb.append("    importoValidato: ").append(toIndentedString(importoValidato)).append("\n");
    sb.append("    importoInErogazione: ").append(toIndentedString(importoInErogazione)).append("\n");
    sb.append("    dataAggiornamento: ").append(toIndentedString(dataAggiornamento)).append("\n");
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
