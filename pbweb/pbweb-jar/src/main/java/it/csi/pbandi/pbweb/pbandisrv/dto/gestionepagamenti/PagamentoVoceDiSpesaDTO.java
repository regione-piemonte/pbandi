package it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti;

public class PagamentoVoceDiSpesaDTO implements java.io.Serializable {

  static final long serialVersionUID = 1;

  private Long idVoceDiSpesa = null;
  private Long idQuotaParteDocSpesa = null;
  private String voceDiSpesa = null;
  private Long idRigoContoEconomico = null;
  private Double importoRendicontato = null;
  private Double totaleAltriPagamenti = null;
  private Double importoVoceDiSpesaCorrente = null;

  public Long getIdVoceDiSpesa() {
    return idVoceDiSpesa;
  }

  public void setIdVoceDiSpesa(Long val) {
    idVoceDiSpesa = val;
  }

  public Long getIdQuotaParteDocSpesa() {
    return idQuotaParteDocSpesa;
  }

  public void setIdQuotaParteDocSpesa(Long val) {
    idQuotaParteDocSpesa = val;
  }

  public String getVoceDiSpesa() {
    return voceDiSpesa;
  }

  public void setVoceDiSpesa(String val) {
    voceDiSpesa = val;
  }

  public Long getIdRigoContoEconomico() {
    return idRigoContoEconomico;
  }

  public void setIdRigoContoEconomico(Long val) {
    idRigoContoEconomico = val;
  }

  public Double getImportoRendicontato() {
    return importoRendicontato;
  }

  public void setImportoRendicontato(Double val) {
    importoRendicontato = val;
  }

  public Double getTotaleAltriPagamenti() {
    return totaleAltriPagamenti;
  }

  public void setTotaleAltriPagamenti(Double val) {
    totaleAltriPagamenti = val;
  }

  public Double getImportoVoceDiSpesaCorrente() {
    return importoVoceDiSpesaCorrente;
  }

  public void setImportoVoceDiSpesaCorrente(Double val) {
    importoVoceDiSpesaCorrente = val;
  }

  @Override
  public String toString() {
    return "PagamentoVoceDiSpesaDTO [idVoceDiSpesa=" + idVoceDiSpesa + ", idQuotaParteDocSpesa=" + idQuotaParteDocSpesa + ", voceDiSpesa=" + voceDiSpesa + ", idRigoContoEconomico=" + idRigoContoEconomico + ", importoRendicontato=" + importoRendicontato + ", totaleAltriPagamenti=" + totaleAltriPagamenti + ", importoVoceDiSpesaCorrente=" + importoVoceDiSpesaCorrente + "]";
  }

}