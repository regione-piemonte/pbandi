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

public class RecuperoNote   {
  private String codiceBeneficiario = null;
  private String codiceFiscaleBeneficiario = null;
  private Long codiceFondo = null;
  private String codiceProgetto = null;
  private Integer agevolazione = null;
  private String areaNota = null;
  private String nota = null;
  private String utenteInserimento = null;
  private String utenteAggiornamento = null;
  private Date dataInserimento = null;
  private Date dataUltimoAggiornamento = null;

  /**
   **/
  
  @JsonProperty("codiceBeneficiario")
  public String getCodiceBeneficiario() {
    return codiceBeneficiario;
  }
  public void setCodiceBeneficiario(String codiceBeneficiario) {
    this.codiceBeneficiario = codiceBeneficiario;
  }

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
  
  @JsonProperty("codiceFondo")
  public Long getCodiceFondo() {
    return codiceFondo;
  }
  public void setCodiceFondo(Long codiceFondo) {
    this.codiceFondo = codiceFondo;
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
  
  @JsonProperty("agevolazione")
  public Integer getAgevolazione() {
    return agevolazione;
  }
  public void setAgevolazione(Integer agevolazione) {
    this.agevolazione = agevolazione;
  }

  /**
   **/
  
  @JsonProperty("areaNota")
  public String getAreaNota() {
    return areaNota;
  }
  public void setAreaNota(String areaNota) {
    this.areaNota = areaNota;
  }

  /**
   **/
  
  @JsonProperty("nota")
  public String getNota() {
    return nota;
  }
  public void setNota(String nota) {
    this.nota = nota;
  }

  /**
   **/
  
  @JsonProperty("utenteInserimento")
  public String getUtenteInserimento() {
    return utenteInserimento;
  }
  public void setUtenteInserimento(String utenteInserimento) {
    this.utenteInserimento = utenteInserimento;
  }

  /**
   **/
  
  @JsonProperty("utenteAggiornamento")
  public String getUtenteAggiornamento() {
    return utenteAggiornamento;
  }
  public void setUtenteAggiornamento(String utenteAggiornamento) {
    this.utenteAggiornamento = utenteAggiornamento;
  }

  /**
   **/
  
  @JsonProperty("dataInserimento")
  public Date getDataInserimento() {
    return dataInserimento;
  }
  public void setDataInserimento(Date dataInserimento) {
    this.dataInserimento = dataInserimento;
  }

  /**
   **/
  
  @JsonProperty("dataUltimoAggiornamento")
  public Date getDataUltimoAggiornamento() {
    return dataUltimoAggiornamento;
  }
  public void setDataUltimoAggiornamento(Date dataUltimoAggiornamento) {
    this.dataUltimoAggiornamento = dataUltimoAggiornamento;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RecuperoNote recuperoNote = (RecuperoNote) o;
    return Objects.equals(codiceBeneficiario, recuperoNote.codiceBeneficiario) &&
        Objects.equals(codiceFiscaleBeneficiario, recuperoNote.codiceFiscaleBeneficiario) &&
        Objects.equals(codiceFondo, recuperoNote.codiceFondo) &&
        Objects.equals(codiceProgetto, recuperoNote.codiceProgetto) &&
        Objects.equals(agevolazione, recuperoNote.agevolazione) &&
        Objects.equals(areaNota, recuperoNote.areaNota) &&
        Objects.equals(nota, recuperoNote.nota) &&
        Objects.equals(utenteInserimento, recuperoNote.utenteInserimento) &&
        Objects.equals(utenteAggiornamento, recuperoNote.utenteAggiornamento) &&
        Objects.equals(dataInserimento, recuperoNote.dataInserimento) &&
        Objects.equals(dataUltimoAggiornamento, recuperoNote.dataUltimoAggiornamento);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceBeneficiario, codiceFiscaleBeneficiario, codiceFondo, codiceProgetto, agevolazione, areaNota, nota, utenteInserimento, utenteAggiornamento, dataInserimento, dataUltimoAggiornamento);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecuperoNote {\n");
    
    sb.append("    codiceBeneficiario: ").append(toIndentedString(codiceBeneficiario)).append("\n");
    sb.append("    codiceFiscaleBeneficiario: ").append(toIndentedString(codiceFiscaleBeneficiario)).append("\n");
    sb.append("    codiceFondo: ").append(toIndentedString(codiceFondo)).append("\n");
    sb.append("    codiceProgetto: ").append(toIndentedString(codiceProgetto)).append("\n");
    sb.append("    agevolazione: ").append(toIndentedString(agevolazione)).append("\n");
    sb.append("    areaNota: ").append(toIndentedString(areaNota)).append("\n");
    sb.append("    nota: ").append(toIndentedString(nota)).append("\n");
    sb.append("    utenteInserimento: ").append(toIndentedString(utenteInserimento)).append("\n");
    sb.append("    utenteAggiornamento: ").append(toIndentedString(utenteAggiornamento)).append("\n");
    sb.append("    dataInserimento: ").append(toIndentedString(dataInserimento)).append("\n");
    sb.append("    dataUltimoAggiornamento: ").append(toIndentedString(dataUltimoAggiornamento)).append("\n");
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
