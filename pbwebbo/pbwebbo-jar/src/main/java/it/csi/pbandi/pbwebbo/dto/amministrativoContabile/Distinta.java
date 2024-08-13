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

public class Distinta   {
  private Integer idDistinta = null;
  private Integer rigaDistinta = null;
  private Long idFondo = null;
  private String codiceProgetto = null;
  private String codiceDomanda = null;
  private Integer idTipoDomanda = null;
  private String ibanFondo = null;
  private Long idBeneficiario = null;
  private String ibanBeneficiario = null;
  private Date dataErogazione = null;
  private Double importo = null;
  private Integer numeroRate = null;
  private Integer frequenzaRate = null;
  private String tipoPeriodo = null;
  private Double percentualeInteressi = null;
  private Double importoIres = null;
  private Integer preammortamento = null;
  private Integer stato = null;
  private Double oneri = null;
  private Double importoRevocaCapitale = null;
  private Double importoRevocaInteressi = null;
  private Integer idRevoca = null;
  private Integer idAgevolazione = null;
  private Integer tipoEscussione = null;
  private String causaleEscussione = null;
  private Integer tipoRevoca = null;

  /**
   **/
  
  @JsonProperty("idDistinta")
  @NotNull
  public Integer getIdDistinta() {
    return idDistinta;
  }
  public void setIdDistinta(Integer idDistinta) {
    this.idDistinta = idDistinta;
  }

  /**
   **/
  
  @JsonProperty("rigaDistinta")
  @NotNull
  public Integer getRigaDistinta() {
    return rigaDistinta;
  }
  public void setRigaDistinta(Integer rigaDistinta) {
    this.rigaDistinta = rigaDistinta;
  }

  /**
   **/
  
  @JsonProperty("idFondo")
  @NotNull
  public Long getIdFondo() {
    return idFondo;
  }
  public void setIdFondo(Long idFondo) {
    this.idFondo = idFondo;
  }

  /**
   **/
  
  @JsonProperty("codiceProgetto")
  @NotNull
  @Size(max=25)
  public String getCodiceProgetto() {
    return codiceProgetto;
  }
  public void setCodiceProgetto(String codiceProgetto) {
    this.codiceProgetto = codiceProgetto;
  }

  /**
   **/
  
  @JsonProperty("codiceDomanda")
  @NotNull
  @Size(max=10)
  public String getCodiceDomanda() {
    return codiceDomanda;
  }
  public void setCodiceDomanda(String codiceDomanda) {
    this.codiceDomanda = codiceDomanda;
  }

  /**
   **/
  
  @JsonProperty("idTipoDomanda")
  @NotNull
  public Integer getIdTipoDomanda() {
    return idTipoDomanda;
  }
  public void setIdTipoDomanda(Integer idTipoDomanda) {
    this.idTipoDomanda = idTipoDomanda;
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
  
  @JsonProperty("idBeneficiario")
  @NotNull
  public Long getIdBeneficiario() {
    return idBeneficiario;
  }
  public void setIdBeneficiario(Long idBeneficiario) {
    this.idBeneficiario = idBeneficiario;
  }

  /**
   **/
  
  @JsonProperty("ibanBeneficiario")
  @NotNull
  @Size(max=34)
  public String getIbanBeneficiario() {
    return ibanBeneficiario;
  }
  public void setIbanBeneficiario(String ibanBeneficiario) {
    this.ibanBeneficiario = ibanBeneficiario;
  }

  /**
   **/
  
  @JsonProperty("dataErogazione")
  @NotNull
  public Date getDataErogazione() {
    return dataErogazione;
  }
  public void setDataErogazione(Date dataErogazione) {
    this.dataErogazione = dataErogazione;
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
  
  @JsonProperty("numeroRate")
  @NotNull
  public Integer getNumeroRate() {
    return numeroRate;
  }
  public void setNumeroRate(Integer numeroRate) {
    this.numeroRate = numeroRate;
  }

  /**
   **/
  
  @JsonProperty("frequenzaRate")
  @NotNull
  public Integer getFrequenzaRate() {
    return frequenzaRate;
  }
  public void setFrequenzaRate(Integer frequenzaRate) {
    this.frequenzaRate = frequenzaRate;
  }

  /**
   **/
  
  @JsonProperty("tipoPeriodo")
  @NotNull
  @Size(max=1)
  public String getTipoPeriodo() {
    return tipoPeriodo;
  }
  public void setTipoPeriodo(String tipoPeriodo) {
    this.tipoPeriodo = tipoPeriodo;
  }

  /**
   **/
  
  @JsonProperty("percentualeInteressi")
  @NotNull
  public Double getPercentualeInteressi() {
    return percentualeInteressi;
  }
  public void setPercentualeInteressi(Double percentualeInteressi) {
    this.percentualeInteressi = percentualeInteressi;
  }

  /**
   **/
  
  @JsonProperty("importoIres")
  @NotNull
  public Double getImportoIres() {
    return importoIres;
  }
  public void setImportoIres(Double importoIres) {
    this.importoIres = importoIres;
  }

  /**
   **/
  
  @JsonProperty("preammortamento")
  @NotNull
  public Integer getPreammortamento() {
    return preammortamento;
  }
  public void setPreammortamento(Integer preammortamento) {
    this.preammortamento = preammortamento;
  }

  /**
   **/
  
  @JsonProperty("stato")
  @NotNull
  public Integer getStato() {
    return stato;
  }
  public void setStato(Integer stato) {
    this.stato = stato;
  }

  /**
   **/
  
  @JsonProperty("oneri")
  @NotNull
  public Double getOneri() {
    return oneri;
  }
  public void setOneri(Double oneri) {
    this.oneri = oneri;
  }

  /**
   **/
  
  @JsonProperty("importoRevocaCapitale")
  @NotNull
  public Double getImportoRevocaCapitale() {
    return importoRevocaCapitale;
  }
  public void setImportoRevocaCapitale(Double importoRevocaCapitale) {
    this.importoRevocaCapitale = importoRevocaCapitale;
  }

  /**
   **/
  
  @JsonProperty("importoRevocaInteressi")
  @NotNull
  public Double getImportoRevocaInteressi() {
    return importoRevocaInteressi;
  }
  public void setImportoRevocaInteressi(Double importoRevocaInteressi) {
    this.importoRevocaInteressi = importoRevocaInteressi;
  }

  /**
   **/
  
  @JsonProperty("idRevoca")
  @NotNull
  public Integer getIdRevoca() {
    return idRevoca;
  }
  public void setIdRevoca(Integer idRevoca) {
    this.idRevoca = idRevoca;
  }

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
  
  @JsonProperty("tipoEscussione")
  @NotNull
  public Integer getTipoEscussione() {
    return tipoEscussione;
  }
  public void setTipoEscussione(Integer tipoEscussione) {
    this.tipoEscussione = tipoEscussione;
  }

  /**
   **/
  
  @JsonProperty("causaleEscussione")
  @NotNull
  @Size(max=4000)
  public String getCausaleEscussione() {
    return causaleEscussione;
  }
  public void setCausaleEscussione(String causaleEscussione) {
    this.causaleEscussione = causaleEscussione;
  }

  /**
   **/
  
  @JsonProperty("tipoRevoca")
  @NotNull
  public Integer getTipoRevoca() {
    return tipoRevoca;
  }
  public void setTipoRevoca(Integer tipoRevoca) {
    this.tipoRevoca = tipoRevoca;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Distinta distinta = (Distinta) o;
    return Objects.equals(idDistinta, distinta.idDistinta) &&
        Objects.equals(rigaDistinta, distinta.rigaDistinta) &&
        Objects.equals(idFondo, distinta.idFondo) &&
        Objects.equals(codiceProgetto, distinta.codiceProgetto) &&
        Objects.equals(codiceDomanda, distinta.codiceDomanda) &&
        Objects.equals(idTipoDomanda, distinta.idTipoDomanda) &&
        Objects.equals(ibanFondo, distinta.ibanFondo) &&
        Objects.equals(idBeneficiario, distinta.idBeneficiario) &&
        Objects.equals(ibanBeneficiario, distinta.ibanBeneficiario) &&
        Objects.equals(dataErogazione, distinta.dataErogazione) &&
        Objects.equals(importo, distinta.importo) &&
        Objects.equals(numeroRate, distinta.numeroRate) &&
        Objects.equals(frequenzaRate, distinta.frequenzaRate) &&
        Objects.equals(tipoPeriodo, distinta.tipoPeriodo) &&
        Objects.equals(percentualeInteressi, distinta.percentualeInteressi) &&
        Objects.equals(importoIres, distinta.importoIres) &&
        Objects.equals(preammortamento, distinta.preammortamento) &&
        Objects.equals(stato, distinta.stato) &&
        Objects.equals(oneri, distinta.oneri) &&
        Objects.equals(importoRevocaCapitale, distinta.importoRevocaCapitale) &&
        Objects.equals(importoRevocaInteressi, distinta.importoRevocaInteressi) &&
        Objects.equals(idRevoca, distinta.idRevoca) &&
        Objects.equals(idAgevolazione, distinta.idAgevolazione) &&
        Objects.equals(tipoEscussione, distinta.tipoEscussione) &&
        Objects.equals(causaleEscussione, distinta.causaleEscussione) &&
        Objects.equals(tipoRevoca, distinta.tipoRevoca);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idDistinta, rigaDistinta, idFondo, codiceProgetto, codiceDomanda, idTipoDomanda, ibanFondo, idBeneficiario, ibanBeneficiario, dataErogazione, importo, numeroRate, frequenzaRate, tipoPeriodo, percentualeInteressi, importoIres, preammortamento, stato, oneri, importoRevocaCapitale, importoRevocaInteressi, idRevoca, idAgevolazione, tipoEscussione, causaleEscussione, tipoRevoca);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Distinta {\n");
    
    sb.append("    idDistinta: ").append(toIndentedString(idDistinta)).append("\n");
    sb.append("    rigaDistinta: ").append(toIndentedString(rigaDistinta)).append("\n");
    sb.append("    idFondo: ").append(toIndentedString(idFondo)).append("\n");
    sb.append("    codiceProgetto: ").append(toIndentedString(codiceProgetto)).append("\n");
    sb.append("    codiceDomanda: ").append(toIndentedString(codiceDomanda)).append("\n");
    sb.append("    idTipoDomanda: ").append(toIndentedString(idTipoDomanda)).append("\n");
    sb.append("    ibanFondo: ").append(toIndentedString(ibanFondo)).append("\n");
    sb.append("    idBeneficiario: ").append(toIndentedString(idBeneficiario)).append("\n");
    sb.append("    ibanBeneficiario: ").append(toIndentedString(ibanBeneficiario)).append("\n");
    sb.append("    dataErogazione: ").append(toIndentedString(dataErogazione)).append("\n");
    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
    sb.append("    numeroRate: ").append(toIndentedString(numeroRate)).append("\n");
    sb.append("    frequenzaRate: ").append(toIndentedString(frequenzaRate)).append("\n");
    sb.append("    tipoPeriodo: ").append(toIndentedString(tipoPeriodo)).append("\n");
    sb.append("    percentualeInteressi: ").append(toIndentedString(percentualeInteressi)).append("\n");
    sb.append("    importoIres: ").append(toIndentedString(importoIres)).append("\n");
    sb.append("    preammortamento: ").append(toIndentedString(preammortamento)).append("\n");
    sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
    sb.append("    oneri: ").append(toIndentedString(oneri)).append("\n");
    sb.append("    importoRevocaCapitale: ").append(toIndentedString(importoRevocaCapitale)).append("\n");
    sb.append("    importoRevocaInteressi: ").append(toIndentedString(importoRevocaInteressi)).append("\n");
    sb.append("    idRevoca: ").append(toIndentedString(idRevoca)).append("\n");
    sb.append("    idAgevolazione: ").append(toIndentedString(idAgevolazione)).append("\n");
    sb.append("    tipoEscussione: ").append(toIndentedString(tipoEscussione)).append("\n");
    sb.append("    causaleEscussione: ").append(toIndentedString(causaleEscussione)).append("\n");
    sb.append("    tipoRevoca: ").append(toIndentedString(tipoRevoca)).append("\n");
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
