/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Date;
import javax.validation.constraints.*;

public class EsitoDurcAntimafia   {
  private String idDomanda = null;
  private String numeroDomanda = null;
  private String esito = null;
  private Date dtRicezioneDocumentazione = null;
  private Date dtScadenza = null;
  private Date dtChiusuraRichiesta = null;
  private String descTipoRichiesta = null;
  private String descStatoRichiesta = null;
  private Date dtEmissione = null;

  /**
   **/
  
  @JsonProperty("idDomanda")
  public String getIdDomanda() {
    return idDomanda;
  }
  public void setIdDomanda(String idDomanda) {
    this.idDomanda = idDomanda;
  }

  /**
   **/
  
  @JsonProperty("numeroDomanda")
  public String getNumeroDomanda() {
    return numeroDomanda;
  }
  public void setNumeroDomanda(String numeroDomanda) {
    this.numeroDomanda = numeroDomanda;
  }

  /**
   **/
  
  @JsonProperty("esito")
  public String getEsito() {
    return esito;
  }
  public void setEsito(String esito) {
    this.esito = esito;
  }

  /**
   **/
  
  @JsonProperty("dtRicezioneDocumentazione")
  public Date getDtRicezioneDocumentazione() {
    return dtRicezioneDocumentazione;
  }
  public void setDtRicezioneDocumentazione(Date dtRicezioneDocumentazione) {
    this.dtRicezioneDocumentazione = dtRicezioneDocumentazione;
  }

  /**
   **/
  
  @JsonProperty("dtScadenza")
  public Date getDtScadenza() {
    return dtScadenza;
  }
  public void setDtScadenza(Date dtScadenza) {
    this.dtScadenza = dtScadenza;
  }

  /**
   **/
  
  @JsonProperty("dtChiusuraRichiesta")
  public Date getDtChiusuraRichiesta() {
    return dtChiusuraRichiesta;
  }
  public void setDtChiusuraRichiesta(Date dtChiusuraRichiesta) {
    this.dtChiusuraRichiesta = dtChiusuraRichiesta;
  }

  /**
   **/
  
  @JsonProperty("descTipoRichiesta")
  public String getDescTipoRichiesta() {
    return descTipoRichiesta;
  }
  public void setDescTipoRichiesta(String descTipoRichiesta) {
    this.descTipoRichiesta = descTipoRichiesta;
  }

  /**
   **/
  
  @JsonProperty("descStatoRichiesta")
  public String getDescStatoRichiesta() {
    return descStatoRichiesta;
  }
  public void setDescStatoRichiesta(String descStatoRichiesta) {
    this.descStatoRichiesta = descStatoRichiesta;
  }

  /**
   **/
  
  @JsonProperty("dtEmissione")
  public Date getDtEmissione() {
    return dtEmissione;
  }
  public void setDtEmissione(Date dtEmissione) {
    this.dtEmissione = dtEmissione;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EsitoDurcAntimafia esitoDurcAntimafia = (EsitoDurcAntimafia) o;
    return Objects.equals(idDomanda, esitoDurcAntimafia.idDomanda) &&
        Objects.equals(numeroDomanda, esitoDurcAntimafia.numeroDomanda) &&
        Objects.equals(esito, esitoDurcAntimafia.esito) &&
        Objects.equals(dtRicezioneDocumentazione, esitoDurcAntimafia.dtRicezioneDocumentazione) &&
        Objects.equals(dtScadenza, esitoDurcAntimafia.dtScadenza) &&
        Objects.equals(dtChiusuraRichiesta, esitoDurcAntimafia.dtChiusuraRichiesta) &&
        Objects.equals(descTipoRichiesta, esitoDurcAntimafia.descTipoRichiesta) &&
        Objects.equals(descStatoRichiesta, esitoDurcAntimafia.descStatoRichiesta) &&
        Objects.equals(dtEmissione, esitoDurcAntimafia.dtEmissione);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idDomanda, numeroDomanda, esito, dtRicezioneDocumentazione, dtScadenza, dtChiusuraRichiesta, descTipoRichiesta, descStatoRichiesta, dtEmissione);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsitoDurcAntimafia {\n");
    
    sb.append("    idDomanda: ").append(toIndentedString(idDomanda)).append("\n");
    sb.append("    numeroDomanda: ").append(toIndentedString(numeroDomanda)).append("\n");
    sb.append("    esito: ").append(toIndentedString(esito)).append("\n");
    sb.append("    dtRicezioneDocumentazione: ").append(toIndentedString(dtRicezioneDocumentazione)).append("\n");
    sb.append("    dtScadenza: ").append(toIndentedString(dtScadenza)).append("\n");
    sb.append("    dtChiusuraRichiesta: ").append(toIndentedString(dtChiusuraRichiesta)).append("\n");
    sb.append("    descTipoRichiesta: ").append(toIndentedString(descTipoRichiesta)).append("\n");
    sb.append("    descStatoRichiesta: ").append(toIndentedString(descStatoRichiesta)).append("\n");
    sb.append("    dtEmissione: ").append(toIndentedString(dtEmissione)).append("\n");
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
