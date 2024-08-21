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

public class DatiBeneficiario   {
  private String idDataBeneficiario = null;
  private String codiceDimensioneImpresa = null;
  private Date dtValutazioneEsito = null;
  private Date dtClassificazione = null;
  private String descIndirizzo = null;
  private String descComune = null;
  private String descComuneEstero = null;
  private String descProvincia = null;
  private String descNazione = null;
  private String cap = null;
  private String pec = null;
  private String descProvider = null;
  private String codiceRating = null;
  private String desBreveClasseRischio = null;

  /**
   **/
  
  @JsonProperty("idDataBeneficiario")
  public String getIdDataBeneficiario() {
    return idDataBeneficiario;
  }
  public void setIdDataBeneficiario(String idDataBeneficiario) {
    this.idDataBeneficiario = idDataBeneficiario;
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
  public Date getDtValutazioneEsito() {
    return dtValutazioneEsito;
  }
  public void setDtValutazioneEsito(Date dtValutazioneEsito) {
    this.dtValutazioneEsito = dtValutazioneEsito;
  }

  /**
   **/
  
  @JsonProperty("dtClassificazione")
  public Date getDtClassificazione() {
    return dtClassificazione;
  }
  public void setDtClassificazione(Date dtClassificazione) {
    this.dtClassificazione = dtClassificazione;
  }

  /**
   **/
  
  @JsonProperty("descIndirizzo")
  public String getDescIndirizzo() {
    return descIndirizzo;
  }
  public void setDescIndirizzo(String descIndirizzo) {
    this.descIndirizzo = descIndirizzo;
  }

  /**
   **/
  
  @JsonProperty("descComune")
  public String getDescComune() {
    return descComune;
  }
  public void setDescComune(String descComune) {
    this.descComune = descComune;
  }

  /**
   **/
  
  @JsonProperty("descComuneEstero")
  public String getDescComuneEstero() {
    return descComuneEstero;
  }
  public void setDescComuneEstero(String descComuneEstero) {
    this.descComuneEstero = descComuneEstero;
  }

  /**
   **/
  
  @JsonProperty("descProvincia")
  public String getDescProvincia() {
    return descProvincia;
  }
  public void setDescProvincia(String descProvincia) {
    this.descProvincia = descProvincia;
  }

  /**
   **/
  
  @JsonProperty("descNazione")
  public String getDescNazione() {
    return descNazione;
  }
  public void setDescNazione(String descNazione) {
    this.descNazione = descNazione;
  }

  /**
   **/
  
  @JsonProperty("cap")
  public String getCap() {
    return cap;
  }
  public void setCap(String cap) {
    this.cap = cap;
  }

  /**
   **/
  
  @JsonProperty("pec")
  public String getPec() {
    return pec;
  }
  public void setPec(String pec) {
    this.pec = pec;
  }

  /**
   **/
  
  @JsonProperty("descProvider")
  public String getDescProvider() {
    return descProvider;
  }
  public void setDescProvider(String descProvider) {
    this.descProvider = descProvider;
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
    DatiBeneficiario datiBeneficiario = (DatiBeneficiario) o;
    return Objects.equals(idDataBeneficiario, datiBeneficiario.idDataBeneficiario) &&
        Objects.equals(codiceDimensioneImpresa, datiBeneficiario.codiceDimensioneImpresa) &&
        Objects.equals(dtValutazioneEsito, datiBeneficiario.dtValutazioneEsito) &&
        Objects.equals(dtClassificazione, datiBeneficiario.dtClassificazione) &&
        Objects.equals(descIndirizzo, datiBeneficiario.descIndirizzo) &&
        Objects.equals(descComune, datiBeneficiario.descComune) &&
        Objects.equals(descComuneEstero, datiBeneficiario.descComuneEstero) &&
        Objects.equals(descProvincia, datiBeneficiario.descProvincia) &&
        Objects.equals(descNazione, datiBeneficiario.descNazione) &&
        Objects.equals(cap, datiBeneficiario.cap) &&
        Objects.equals(pec, datiBeneficiario.pec) &&
        Objects.equals(descProvider, datiBeneficiario.descProvider) &&
        Objects.equals(codiceRating, datiBeneficiario.codiceRating) &&
        Objects.equals(desBreveClasseRischio, datiBeneficiario.desBreveClasseRischio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idDataBeneficiario, codiceDimensioneImpresa, dtValutazioneEsito, dtClassificazione, descIndirizzo, descComune, descComuneEstero, descProvincia, descNazione, cap, pec, descProvider, codiceRating, desBreveClasseRischio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatiBeneficiario {\n");
    
    sb.append("    idDataBeneficiario: ").append(toIndentedString(idDataBeneficiario)).append("\n");
    sb.append("    codiceDimensioneImpresa: ").append(toIndentedString(codiceDimensioneImpresa)).append("\n");
    sb.append("    dtValutazioneEsito: ").append(toIndentedString(dtValutazioneEsito)).append("\n");
    sb.append("    dtClassificazione: ").append(toIndentedString(dtClassificazione)).append("\n");
    sb.append("    descIndirizzo: ").append(toIndentedString(descIndirizzo)).append("\n");
    sb.append("    descComune: ").append(toIndentedString(descComune)).append("\n");
    sb.append("    descComuneEstero: ").append(toIndentedString(descComuneEstero)).append("\n");
    sb.append("    descProvincia: ").append(toIndentedString(descProvincia)).append("\n");
    sb.append("    descNazione: ").append(toIndentedString(descNazione)).append("\n");
    sb.append("    cap: ").append(toIndentedString(cap)).append("\n");
    sb.append("    pec: ").append(toIndentedString(pec)).append("\n");
    sb.append("    descProvider: ").append(toIndentedString(descProvider)).append("\n");
    sb.append("    codiceRating: ").append(toIndentedString(codiceRating)).append("\n");
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
