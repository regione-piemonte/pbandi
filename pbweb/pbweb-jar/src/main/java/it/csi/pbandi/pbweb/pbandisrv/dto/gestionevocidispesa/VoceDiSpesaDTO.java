/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.gestionevocidispesa;

public class VoceDiSpesaDTO implements java.io.Serializable {
  static final long serialVersionUID = 1;
  private Long idVoceDiSpesa = null;
  private Long idRigoContoEconomico = null;
  private Long idProgetto = null;
  private Long idQuotaParteDocSpesa = null;
  private Long idDocSpesa = null;
  private Double importo = null;
  private String descVoceDiSpesa = null;
  private Double importoRichiesto = null;
  private Double importoAgevolato = null;
  private Double importoFinanziamento = null;
  private Long idVoceDiSpesaPadre = null;
  private Double oreLavorate = null;
  private Double costoOrario = null;
  private Long idTipoDocumentoDiSpesa = null;
  private String descVoceDiSpesaCompleta = null;
  private Double importoRendicontato = null;
  private Double importoResiduoAmmesso = null;
  private String descVoceDiSpesaPadre = null;
	private Long idTipologiaVoceDiSpesa = null;

  public Long getIdVoceDiSpesa() {
    return idVoceDiSpesa;
  }

  public void setIdVoceDiSpesa(Long val) {
    idVoceDiSpesa = val;
  }

  public Long getIdRigoContoEconomico() {
    return idRigoContoEconomico;
  }

  public void setIdRigoContoEconomico(Long val) {
    idRigoContoEconomico = val;
  }

  public Long getIdProgetto() {
    return idProgetto;
  }

  public void setIdProgetto(Long val) {
    idProgetto = val;
  }

  public Long getIdQuotaParteDocSpesa() {
    return idQuotaParteDocSpesa;
  }

  public void setIdQuotaParteDocSpesa(Long val) {
    idQuotaParteDocSpesa = val;
  }

  public Long getIdDocSpesa() {
    return idDocSpesa;
  }

  public void setIdDocSpesa(Long val) {
    idDocSpesa = val;
  }

  public Double getImporto() {
    return importo;
  }

  public void setImporto(Double val) {
    importo = val;
  }

  public String getDescVoceDiSpesa() {
    return descVoceDiSpesa;
  }

  public void setDescVoceDiSpesa(String val) {
    descVoceDiSpesa = val;
  }

  public Double getImportoRichiesto() {
    return importoRichiesto;
  }

  public void setImportoRichiesto(Double val) {
    importoRichiesto = val;
  }

  public Double getImportoAgevolato() {
    return importoAgevolato;
  }

  public void setImportoAgevolato(Double val) {
    importoAgevolato = val;
  }

  public Double getImportoFinanziamento() {
    return importoFinanziamento;
  }

  public void setImportoFinanziamento(Double val) {
    importoFinanziamento = val;
  }

  public Long getIdVoceDiSpesaPadre() {
    return idVoceDiSpesaPadre;
  }

  public void setIdVoceDiSpesaPadre(Long val) {
    idVoceDiSpesaPadre = val;
  }

  public Double getOreLavorate() {
    return oreLavorate;
  }

  public void setOreLavorate(Double val) {
    oreLavorate = val;
  }

  public Double getCostoOrario() {
    return costoOrario;
  }

  public void setCostoOrario(Double val) {
    costoOrario = val;
  }

  public Long getIdTipoDocumentoDiSpesa() {
    return idTipoDocumentoDiSpesa;
  }

  public void setIdTipoDocumentoDiSpesa(Long val) {
    idTipoDocumentoDiSpesa = val;
  }

  public String getDescVoceDiSpesaCompleta() {
    return descVoceDiSpesaCompleta;
  }

  public void setDescVoceDiSpesaCompleta(String val) {
    descVoceDiSpesaCompleta = val;
  }

  public Double getImportoRendicontato() {
    return importoRendicontato;
  }

  public void setImportoRendicontato(Double val) {
    importoRendicontato = val;
  }

  public Double getImportoResiduoAmmesso() {
    return importoResiduoAmmesso;
  }

  public void setImportoResiduoAmmesso(Double val) {
    importoResiduoAmmesso = val;
  }

  public String getDescVoceDiSpesaPadre() {
    return descVoceDiSpesaPadre;
  }

  public void setDescVoceDiSpesaPadre(String val) {
    descVoceDiSpesaPadre = val;
  }

	public Long getIdTipologiaVoceDiSpesa() {
		return idTipologiaVoceDiSpesa;
	}

	public void setIdTipologiaVoceDiSpesa(Long idTipologiaVoceDiSpesa) {
		this.idTipologiaVoceDiSpesa = idTipologiaVoceDiSpesa;
	}

  @Override
  public String toString() {
    return "VoceDiSpesaDTO{" +
        "idVoceDiSpesa=" + idVoceDiSpesa +
        ", idRigoContoEconomico=" + idRigoContoEconomico +
        ", idProgetto=" + idProgetto +
        ", idQuotaParteDocSpesa=" + idQuotaParteDocSpesa +
        ", idDocSpesa=" + idDocSpesa +
        ", importo=" + importo +
        ", descVoceDiSpesa='" + descVoceDiSpesa + '\'' +
        ", importoRichiesto=" + importoRichiesto +
        ", importoAgevolato=" + importoAgevolato +
        ", importoFinanziamento=" + importoFinanziamento +
        ", idVoceDiSpesaPadre=" + idVoceDiSpesaPadre +
        ", oreLavorate=" + oreLavorate +
        ", costoOrario=" + costoOrario +
        ", idTipoDocumentoDiSpesa=" + idTipoDocumentoDiSpesa +
        ", descVoceDiSpesaCompleta='" + descVoceDiSpesaCompleta + '\'' +
        ", importoRendicontato=" + importoRendicontato +
        ", importoResiduoAmmesso=" + importoResiduoAmmesso +
        ", descVoceDiSpesaPadre='" + descVoceDiSpesaPadre + '\'' +
        ", idTipologiaVoceDiSpesa=" + idTipologiaVoceDiSpesa +
        '}';
  }
}