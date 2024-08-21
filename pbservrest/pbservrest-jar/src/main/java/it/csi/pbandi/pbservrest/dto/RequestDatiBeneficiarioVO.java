/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;

public class RequestDatiBeneficiarioVO   {
  private String codiceFiscale = null;
  private String numeroDomanda = null;
  private String codiceDimensioneImpresa = null;
  private String dtValutazioneEsito = null;
  private String descrizioneProvider = null;
  private String codiceRating = null;
  private String dtClassificazione = null;
  private String desBreveClasseRischio = null;

  /**
   **/
  
  @JsonProperty("codiceFiscale")
  public String getCodiceFiscale() {
    return codiceFiscale;
  }
  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
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
  
  @JsonProperty("codiceDimensioneImpresa")
  public String getCodiceDimensioneImpresa() {
    return codiceDimensioneImpresa;
  }
  public void setCodiceDimensioneImpresa(String codiceDimensioneImpresa) {
    this.codiceDimensioneImpresa = codiceDimensioneImpresa;
  }

  /**
   **/
  
  @JsonProperty("dtValutazioneEsito")
  public String getDtValutazioneEsito() {
    return dtValutazioneEsito;
  }
  public void setDtValutazioneEsito(String dtValutazioneEsito) {
    this.dtValutazioneEsito = dtValutazioneEsito;
  }

  /**
   **/
  
  @JsonProperty("descrizioneProvider")
  public String getDescrizioneProvider() {
    return descrizioneProvider;
  }
  public void setDescrizioneProvider(String descrizioneProvider) {
    this.descrizioneProvider = descrizioneProvider;
  }

  /**
   **/
  
  @JsonProperty("codiceRating")
  public String getCodiceRating() {
    return codiceRating;
  }
  public void setCodiceRating(String codiceRating) {
    this.codiceRating = codiceRating;
  }

  /**
   **/
  
  @JsonProperty("dtClassificazione")
  public String getDtClassificazione() {
    return dtClassificazione;
  }
  public void setDtClassificazione(String dtClassificazione) {
    this.dtClassificazione = dtClassificazione;
  }

  /**
   **/
  
  @JsonProperty("desBreveClasseRischio")
  public String getDesBreveClasseRischio() {
    return desBreveClasseRischio;
  }
  public void setDesBreveClasseRischio(String desBreveClasseRischio) {
    this.desBreveClasseRischio = desBreveClasseRischio;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestDatiBeneficiarioVO requestDatiBeneficiarioVO = (RequestDatiBeneficiarioVO) o;
    return Objects.equals(codiceFiscale, requestDatiBeneficiarioVO.codiceFiscale) &&
        Objects.equals(numeroDomanda, requestDatiBeneficiarioVO.numeroDomanda) &&
        Objects.equals(codiceDimensioneImpresa, requestDatiBeneficiarioVO.codiceDimensioneImpresa) &&
        Objects.equals(dtValutazioneEsito, requestDatiBeneficiarioVO.dtValutazioneEsito) &&
        Objects.equals(descrizioneProvider, requestDatiBeneficiarioVO.descrizioneProvider) &&
        Objects.equals(codiceRating, requestDatiBeneficiarioVO.codiceRating) &&
        Objects.equals(dtClassificazione, requestDatiBeneficiarioVO.dtClassificazione) &&
        Objects.equals(desBreveClasseRischio, requestDatiBeneficiarioVO.desBreveClasseRischio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceFiscale, numeroDomanda, codiceDimensioneImpresa, dtValutazioneEsito, descrizioneProvider, codiceRating, dtClassificazione, desBreveClasseRischio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestDatiBeneficiarioVO {\n");
    
    sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
    sb.append("    numeroDomanda: ").append(toIndentedString(numeroDomanda)).append("\n");
    sb.append("    codiceDimensioneImpresa: ").append(toIndentedString(codiceDimensioneImpresa)).append("\n");
    sb.append("    dtValutazioneEsito: ").append(toIndentedString(dtValutazioneEsito)).append("\n");
    sb.append("    descrizioneProvider: ").append(toIndentedString(descrizioneProvider)).append("\n");
    sb.append("    codiceRating: ").append(toIndentedString(codiceRating)).append("\n");
    sb.append("    dtClassificazione: ").append(toIndentedString(dtClassificazione)).append("\n");
    sb.append("    desBreveClasseRischio: ").append(toIndentedString(desBreveClasseRischio)).append("\n");
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
