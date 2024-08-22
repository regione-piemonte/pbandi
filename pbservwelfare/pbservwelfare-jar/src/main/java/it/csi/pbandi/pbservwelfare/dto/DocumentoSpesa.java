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

public class DocumentoSpesa   {
  private Integer identificativoDocumentoDiSpesa = null;
  private String noteValidazione = null;
  private String statoDocumento = null;
  private Integer importoTotaleDocumento = null;
  private Integer importoRendicontato = null;
  private Integer importoQuietanzato = null;
  private Integer importoValidato = null;
  private String codiceFiscaleFornitore = null;
  private String denominazioneFornitore = null;
  private String cognome = null;
  private String nome = null;
  private Date dataInizioContratto = null;
  private Date dataFineContratto = null;

  /**
   **/
  
  @JsonProperty("identificativoDocumentoDiSpesa")
  public Integer getIdentificativoDocumentoDiSpesa() {
    return identificativoDocumentoDiSpesa;
  }
  public void setIdentificativoDocumentoDiSpesa(Integer identificativoDocumentoDiSpesa) {
    this.identificativoDocumentoDiSpesa = identificativoDocumentoDiSpesa;
  }

  /**
   **/
  
  @JsonProperty("noteValidazione")
  public String getNoteValidazione() {
    return noteValidazione;
  }
  public void setNoteValidazione(String noteValidazione) {
    this.noteValidazione = noteValidazione;
  }

  /**
   **/
  
  @JsonProperty("statoDocumento")
  public String getStatoDocumento() {
    return statoDocumento;
  }
  public void setStatoDocumento(String statoDocumento) {
    this.statoDocumento = statoDocumento;
  }

  /**
   **/
  
  @JsonProperty("importoTotaleDocumento")
  public Integer getImportoTotaleDocumento() {
    return importoTotaleDocumento;
  }
  public void setImportoTotaleDocumento(Integer importoTotaleDocumento) {
    this.importoTotaleDocumento = importoTotaleDocumento;
  }

  /**
   **/
  
  @JsonProperty("importoRendicontato")
  public Integer getImportoRendicontato() {
    return importoRendicontato;
  }
  public void setImportoRendicontato(Integer importoRendicontato) {
    this.importoRendicontato = importoRendicontato;
  }

  /**
   **/
  
  @JsonProperty("importoQuietanzato")
  public Integer getImportoQuietanzato() {
    return importoQuietanzato;
  }
  public void setImportoQuietanzato(Integer importoQuietanzato) {
    this.importoQuietanzato = importoQuietanzato;
  }

  /**
   **/
  
  @JsonProperty("importoValidato")
  public Integer getImportoValidato() {
    return importoValidato;
  }
  public void setImportoValidato(Integer importoValidato) {
    this.importoValidato = importoValidato;
  }

  /**
   **/
  
  @JsonProperty("codiceFiscaleFornitore")
  public String getCodiceFiscaleFornitore() {
    return codiceFiscaleFornitore;
  }
  public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
    this.codiceFiscaleFornitore = codiceFiscaleFornitore;
  }

  /**
   **/
  
  @JsonProperty("denominazioneFornitore")
  public String getDenominazioneFornitore() {
    return denominazioneFornitore;
  }
  public void setDenominazioneFornitore(String denominazioneFornitore) {
    this.denominazioneFornitore = denominazioneFornitore;
  }

  /**
   **/
  
  @JsonProperty("cognome")
  public String getCognome() {
    return cognome;
  }
  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  /**
   **/
  
  @JsonProperty("nome")
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   **/
  
  @JsonProperty("dataInizioContratto")
  public Date getDataInizioContratto() {
    return dataInizioContratto;
  }
  public void setDataInizioContratto(Date dataInizioContratto) {
    this.dataInizioContratto = dataInizioContratto;
  }

  /**
   **/
  
  @JsonProperty("dataFineContratto")
  public Date getDataFineContratto() {
    return dataFineContratto;
  }
  public void setDataFineContratto(Date dataFineContratto) {
    this.dataFineContratto = dataFineContratto;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentoSpesa documentoSpesa = (DocumentoSpesa) o;
    return Objects.equals(identificativoDocumentoDiSpesa, documentoSpesa.identificativoDocumentoDiSpesa) &&
        Objects.equals(noteValidazione, documentoSpesa.noteValidazione) &&
        Objects.equals(statoDocumento, documentoSpesa.statoDocumento) &&
        Objects.equals(importoTotaleDocumento, documentoSpesa.importoTotaleDocumento) &&
        Objects.equals(importoRendicontato, documentoSpesa.importoRendicontato) &&
        Objects.equals(importoQuietanzato, documentoSpesa.importoQuietanzato) &&
        Objects.equals(importoValidato, documentoSpesa.importoValidato) &&
        Objects.equals(codiceFiscaleFornitore, documentoSpesa.codiceFiscaleFornitore) &&
        Objects.equals(denominazioneFornitore, documentoSpesa.denominazioneFornitore) &&
        Objects.equals(cognome, documentoSpesa.cognome) &&
        Objects.equals(nome, documentoSpesa.nome) &&
        Objects.equals(dataInizioContratto, documentoSpesa.dataInizioContratto) &&
        Objects.equals(dataFineContratto, documentoSpesa.dataFineContratto);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identificativoDocumentoDiSpesa, noteValidazione, statoDocumento, importoTotaleDocumento, importoRendicontato, importoQuietanzato, importoValidato, codiceFiscaleFornitore, denominazioneFornitore, cognome, nome, dataInizioContratto, dataFineContratto);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentoSpesa {\n");
    
    sb.append("    identificativoDocumentoDiSpesa: ").append(toIndentedString(identificativoDocumentoDiSpesa)).append("\n");
    sb.append("    noteValidazione: ").append(toIndentedString(noteValidazione)).append("\n");
    sb.append("    statoDocumento: ").append(toIndentedString(statoDocumento)).append("\n");
    sb.append("    importoTotaleDocumento: ").append(toIndentedString(importoTotaleDocumento)).append("\n");
    sb.append("    importoRendicontato: ").append(toIndentedString(importoRendicontato)).append("\n");
    sb.append("    importoQuietanzato: ").append(toIndentedString(importoQuietanzato)).append("\n");
    sb.append("    importoValidato: ").append(toIndentedString(importoValidato)).append("\n");
    sb.append("    codiceFiscaleFornitore: ").append(toIndentedString(codiceFiscaleFornitore)).append("\n");
    sb.append("    denominazioneFornitore: ").append(toIndentedString(denominazioneFornitore)).append("\n");
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    dataInizioContratto: ").append(toIndentedString(dataInizioContratto)).append("\n");
    sb.append("    dataFineContratto: ").append(toIndentedString(dataFineContratto)).append("\n");
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
