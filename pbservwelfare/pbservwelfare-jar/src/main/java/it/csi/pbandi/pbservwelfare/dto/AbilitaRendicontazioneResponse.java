/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;

public class AbilitaRendicontazioneResponse   {
  private String codiceFiscaleBeneficiario = null;
  private String codiceProgetto = null;
  private Integer idSoggettoBeneficiario = null;
  private Boolean abilitato = null;
  private String esito = null;
  private String messaggio = null;
  private String codiceMessaggio = null;

  /**
   **/
  
  @JsonProperty("codiceFiscaleBeneficiario")
  public String getCodiceFiscaleBeneficiario() {
    return codiceFiscaleBeneficiario;
  }
  public void setCodiceFiscaleBeneficiario(String codiceFiscaleBeneficiario) {
    this.codiceFiscaleBeneficiario = codiceFiscaleBeneficiario;
  }

  /**
   **/
  
  @JsonProperty("codiceProgetto")
  public String getCodiceProgetto() {
    return codiceProgetto;
  }
  public void setCodiceProgetto(String codiceProgetto) {
    this.codiceProgetto = codiceProgetto;
  }

  /**
   **/
  
  @JsonProperty("idSoggettoBeneficiario")
  public Integer getIdSoggettoBeneficiario() {
    return idSoggettoBeneficiario;
  }
  public void setIdSoggettoBeneficiario(Integer idSoggettoBeneficiario) {
    this.idSoggettoBeneficiario = idSoggettoBeneficiario;
  }

  /**
   **/
  
  @JsonProperty("abilitato")
  public Boolean isAbilitato() {
    return abilitato;
  }
  public void setAbilitato(Boolean abilitato) {
    this.abilitato = abilitato;
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
  
  @JsonProperty("messaggio")
  public String getMessaggio() {
    return messaggio;
  }
  public void setMessaggio(String messaggio) {
    this.messaggio = messaggio;
  }

  /**
   **/
  
  @JsonProperty("codiceMessaggio")
  public String getCodiceMessaggio() {
    return codiceMessaggio;
  }
  public void setCodiceMessaggio(String codiceMessaggio) {
    this.codiceMessaggio = codiceMessaggio;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AbilitaRendicontazioneResponse abilitaRendicontazioneResponse = (AbilitaRendicontazioneResponse) o;
    return Objects.equals(codiceFiscaleBeneficiario, abilitaRendicontazioneResponse.codiceFiscaleBeneficiario) &&
        Objects.equals(codiceProgetto, abilitaRendicontazioneResponse.codiceProgetto) &&
        Objects.equals(idSoggettoBeneficiario, abilitaRendicontazioneResponse.idSoggettoBeneficiario) &&
        Objects.equals(abilitato, abilitaRendicontazioneResponse.abilitato) &&
        Objects.equals(esito, abilitaRendicontazioneResponse.esito) &&
        Objects.equals(messaggio, abilitaRendicontazioneResponse.messaggio) &&
        Objects.equals(codiceMessaggio, abilitaRendicontazioneResponse.codiceMessaggio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceFiscaleBeneficiario, codiceProgetto, idSoggettoBeneficiario, abilitato, esito, messaggio, codiceMessaggio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AbilitaRendicontazioneResponse {\n");
    
    sb.append("    codiceFiscaleBeneficiario: ").append(toIndentedString(codiceFiscaleBeneficiario)).append("\n");
    sb.append("    codiceProgetto: ").append(toIndentedString(codiceProgetto)).append("\n");
    sb.append("    idSoggettoBeneficiario: ").append(toIndentedString(idSoggettoBeneficiario)).append("\n");
    sb.append("    abilitato: ").append(toIndentedString(abilitato)).append("\n");
    sb.append("    esito: ").append(toIndentedString(esito)).append("\n");
    sb.append("    messaggio: ").append(toIndentedString(messaggio)).append("\n");
    sb.append("    codiceMessaggio: ").append(toIndentedString(codiceMessaggio)).append("\n");
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
