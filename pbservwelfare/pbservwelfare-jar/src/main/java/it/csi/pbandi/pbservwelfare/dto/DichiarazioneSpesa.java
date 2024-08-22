/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.pbandi.pbservwelfare.dto.DocumentiDiSpesa;
import it.csi.pbandi.pbservwelfare.dto.Mensilita;
import it.csi.pbandi.pbservwelfare.dto.ModelInvioFornitori;
import java.util.List;
import javax.validation.constraints.*;

public class DichiarazioneSpesa   {
  private String numeroDomanda = null;
  private String codiceBando = null;
  private String dataDichiarazioneDiSpesa = null;
  private String codStruttura = null;
  private String idDichiarazioneBuonodom = null;
  private String nomeFile = null;
  private List<Mensilita> mensilita = new ArrayList<Mensilita>();
  private List<DocumentiDiSpesa> documentiDiSpesa = new ArrayList<DocumentiDiSpesa>();
  private List<ModelInvioFornitori> fornitori = new ArrayList<ModelInvioFornitori>();

  /**
   **/
  
  @JsonProperty("Numero_domanda")
  public String getNumeroDomanda() {
    return numeroDomanda;
  }
  public void setNumeroDomanda(String numeroDomanda) {
    this.numeroDomanda = numeroDomanda;
  }

  /**
   **/
  
  @JsonProperty("Codice_bando")
  public String getCodiceBando() {
    return codiceBando;
  }
  public void setCodiceBando(String codiceBando) {
    this.codiceBando = codiceBando;
  }

  /**
   **/
  
  @JsonProperty("Data_Dichiarazione_di_spesa")
  public String getDataDichiarazioneDiSpesa() {
    return dataDichiarazioneDiSpesa;
  }
  public void setDataDichiarazioneDiSpesa(String dataDichiarazioneDiSpesa) {
    this.dataDichiarazioneDiSpesa = dataDichiarazioneDiSpesa;
  }

  /**
   **/
  
  @JsonProperty("Cod_struttura")
  public String getCodStruttura() {
    return codStruttura;
  }
  public void setCodStruttura(String codStruttura) {
    this.codStruttura = codStruttura;
  }

  /**
   **/
  
  @JsonProperty("id_dichiarazione_buonodom")
  public String getIdDichiarazioneBuonodom() {
    return idDichiarazioneBuonodom;
  }
  public void setIdDichiarazioneBuonodom(String idDichiarazioneBuonodom) {
    this.idDichiarazioneBuonodom = idDichiarazioneBuonodom;
  }

  /**
   **/
  
  @JsonProperty("Nome_file")
  public String getNomeFile() {
    return nomeFile;
  }
  public void setNomeFile(String nomeFile) {
    this.nomeFile = nomeFile;
  }

  /**
   **/
  
  @JsonProperty("Mensilita")
  public List<Mensilita> getMensilita() {
    return mensilita;
  }
  public void setMensilita(List<Mensilita> mensilita) {
    this.mensilita = mensilita;
  }

  /**
   **/
  
  @JsonProperty("Documenti_di_spesa")
  public List<DocumentiDiSpesa> getDocumentiDiSpesa() {
    return documentiDiSpesa;
  }
  public void setDocumentiDiSpesa(List<DocumentiDiSpesa> documentiDiSpesa) {
    this.documentiDiSpesa = documentiDiSpesa;
  }

  /**
   **/
  
  @JsonProperty("Fornitori")
  public List<ModelInvioFornitori> getFornitori() {
    return fornitori;
  }
  public void setFornitori(List<ModelInvioFornitori> fornitori) {
    this.fornitori = fornitori;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DichiarazioneSpesa dichiarazioneSpesa = (DichiarazioneSpesa) o;
    return Objects.equals(numeroDomanda, dichiarazioneSpesa.numeroDomanda) &&
        Objects.equals(codiceBando, dichiarazioneSpesa.codiceBando) &&
        Objects.equals(dataDichiarazioneDiSpesa, dichiarazioneSpesa.dataDichiarazioneDiSpesa) &&
        Objects.equals(codStruttura, dichiarazioneSpesa.codStruttura) &&
        Objects.equals(idDichiarazioneBuonodom, dichiarazioneSpesa.idDichiarazioneBuonodom) &&
        Objects.equals(nomeFile, dichiarazioneSpesa.nomeFile) &&
        Objects.equals(mensilita, dichiarazioneSpesa.mensilita) &&
        Objects.equals(documentiDiSpesa, dichiarazioneSpesa.documentiDiSpesa) &&
        Objects.equals(fornitori, dichiarazioneSpesa.fornitori);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numeroDomanda, codiceBando, dataDichiarazioneDiSpesa, codStruttura, idDichiarazioneBuonodom, nomeFile, mensilita, documentiDiSpesa, fornitori);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DichiarazioneSpesa {\n");
    
    sb.append("    numeroDomanda: ").append(toIndentedString(numeroDomanda)).append("\n");
    sb.append("    codiceBando: ").append(toIndentedString(codiceBando)).append("\n");
    sb.append("    dataDichiarazioneDiSpesa: ").append(toIndentedString(dataDichiarazioneDiSpesa)).append("\n");
    sb.append("    codStruttura: ").append(toIndentedString(codStruttura)).append("\n");
    sb.append("    idDichiarazioneBuonodom: ").append(toIndentedString(idDichiarazioneBuonodom)).append("\n");
    sb.append("    nomeFile: ").append(toIndentedString(nomeFile)).append("\n");
    sb.append("    mensilita: ").append(toIndentedString(mensilita)).append("\n");
    sb.append("    documentiDiSpesa: ").append(toIndentedString(documentiDiSpesa)).append("\n");
    sb.append("    fornitori: ").append(toIndentedString(fornitori)).append("\n");
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
