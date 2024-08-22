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

public class DocumentiDiSpesa   {
  private String dataDocumento = null;
  private String numeroDocumento = null;
  private String idDocumentoBuonoDom = null;
  private String importoTotaleDocumento = null;
  private String importoQuietanziato = null;
  private String descrizioneDocumento = null;
  private String codiceVoceDiSpesa = null;
  private String codiceFiscaleFornitore = null;
  private String tipologiaDocumento = null;
  private List<Documenti> documenti = new ArrayList<Documenti>();

  /**
   **/
  
  @JsonProperty("Data_documento")
  public String getDataDocumento() {
    return dataDocumento;
  }
  public void setDataDocumento(String dataDocumento) {
    this.dataDocumento = dataDocumento;
  }

  /**
   **/
  
  @JsonProperty("Numero_documento")
  public String getNumeroDocumento() {
    return numeroDocumento;
  }
  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  /**
   **/
  
  @JsonProperty("id_documento_buono_dom")
  public String getIdDocumentoBuonoDom() {
    return idDocumentoBuonoDom;
  }
  public void setIdDocumentoBuonoDom(String idDocumentoBuonoDom) {
    this.idDocumentoBuonoDom = idDocumentoBuonoDom;
  }

  /**
   **/
  
  @JsonProperty("Importo_totale_documento")
  public String getImportoTotaleDocumento() {
    return importoTotaleDocumento;
  }
  public void setImportoTotaleDocumento(String importoTotaleDocumento) {
    this.importoTotaleDocumento = importoTotaleDocumento;
  }

  /**
   **/
  
  @JsonProperty("Importo_quietanziato")
  public String getImportoQuietanziato() {
    return importoQuietanziato;
  }
  public void setImportoQuietanziato(String importoQuietanziato) {
    this.importoQuietanziato = importoQuietanziato;
  }

  /**
   **/
  
  @JsonProperty("Descrizione_documento")
  public String getDescrizioneDocumento() {
    return descrizioneDocumento;
  }
  public void setDescrizioneDocumento(String descrizioneDocumento) {
    this.descrizioneDocumento = descrizioneDocumento;
  }

  /**
   **/
  
  @JsonProperty("Codice_voce_di_spesa")
  public String getCodiceVoceDiSpesa() {
    return codiceVoceDiSpesa;
  }
  public void setCodiceVoceDiSpesa(String codiceVoceDiSpesa) {
    this.codiceVoceDiSpesa = codiceVoceDiSpesa;
  }

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
  
  @JsonProperty("Tipologia_documento")
  public String getTipologiaDocumento() {
    return tipologiaDocumento;
  }
  public void setTipologiaDocumento(String tipologiaDocumento) {
    this.tipologiaDocumento = tipologiaDocumento;
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
    DocumentiDiSpesa documentiDiSpesa = (DocumentiDiSpesa) o;
    return Objects.equals(dataDocumento, documentiDiSpesa.dataDocumento) &&
        Objects.equals(numeroDocumento, documentiDiSpesa.numeroDocumento) &&
        Objects.equals(idDocumentoBuonoDom, documentiDiSpesa.idDocumentoBuonoDom) &&
        Objects.equals(importoTotaleDocumento, documentiDiSpesa.importoTotaleDocumento) &&
        Objects.equals(importoQuietanziato, documentiDiSpesa.importoQuietanziato) &&
        Objects.equals(descrizioneDocumento, documentiDiSpesa.descrizioneDocumento) &&
        Objects.equals(codiceVoceDiSpesa, documentiDiSpesa.codiceVoceDiSpesa) &&
        Objects.equals(codiceFiscaleFornitore, documentiDiSpesa.codiceFiscaleFornitore) &&
        Objects.equals(tipologiaDocumento, documentiDiSpesa.tipologiaDocumento) &&
        Objects.equals(documenti, documentiDiSpesa.documenti);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataDocumento, numeroDocumento, idDocumentoBuonoDom, importoTotaleDocumento, importoQuietanziato, descrizioneDocumento, codiceVoceDiSpesa, codiceFiscaleFornitore, tipologiaDocumento, documenti);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentiDiSpesa {\n");
    
    sb.append("    dataDocumento: ").append(toIndentedString(dataDocumento)).append("\n");
    sb.append("    numeroDocumento: ").append(toIndentedString(numeroDocumento)).append("\n");
    sb.append("    idDocumentoBuonoDom: ").append(toIndentedString(idDocumentoBuonoDom)).append("\n");
    sb.append("    importoTotaleDocumento: ").append(toIndentedString(importoTotaleDocumento)).append("\n");
    sb.append("    importoQuietanziato: ").append(toIndentedString(importoQuietanziato)).append("\n");
    sb.append("    descrizioneDocumento: ").append(toIndentedString(descrizioneDocumento)).append("\n");
    sb.append("    codiceVoceDiSpesa: ").append(toIndentedString(codiceVoceDiSpesa)).append("\n");
    sb.append("    codiceFiscaleFornitore: ").append(toIndentedString(codiceFiscaleFornitore)).append("\n");
    sb.append("    tipologiaDocumento: ").append(toIndentedString(tipologiaDocumento)).append("\n");
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
