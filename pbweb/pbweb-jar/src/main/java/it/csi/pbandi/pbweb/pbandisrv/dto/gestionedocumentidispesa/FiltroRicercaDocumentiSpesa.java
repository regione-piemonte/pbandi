package it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa;

import java.util.Date;

public class FiltroRicercaDocumentiSpesa implements java.io.Serializable {
  private static final long serialVersionUID = 1;
  private String rendicontazione = null;
  private String documentiDiSpesa = null;
  private Long idVoceDiSpesa = null;
  private Long idTipologia = null;
  private String numero = null;
  private Date data = null;
  private Long idStato = null;
  private Long idFornitore = null;
  private Long idProgetto = null;
  private Long idSoggettoBeneficiario = null;
  private String task = null;
  private Date dataA = null;
  private Long idCategoria = null;

  public String getRendicontazione() {
    return rendicontazione;
  }

  public void setRendicontazione(String val) {
    rendicontazione = val;
  }

  public String getDocumentiDiSpesa() {
    return documentiDiSpesa;
  }

  public void setDocumentiDiSpesa(String val) {
    documentiDiSpesa = val;
  }

  public Long getIdVoceDiSpesa() {
    return idVoceDiSpesa;
  }

  public void setIdVoceDiSpesa(Long val) {
    idVoceDiSpesa = val;
  }

  public Long getIdTipologia() {
    return idTipologia;
  }

  public void setIdTipologia(Long val) {
    idTipologia = val;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String val) {
    numero = val;
  }

  public Date getData() {
    return data;
  }

  public void setData(Date val) {
    data = val;
  }

  public Long getIdStato() {
    return idStato;
  }

  public void setIdStato(Long val) {
    idStato = val;
  }

  public Long getIdFornitore() {
    return idFornitore;
  }

  public void setIdFornitore(Long val) {
    idFornitore = val;
  }

  public Long getIdProgetto() {
    return idProgetto;
  }

  public void setIdProgetto(Long val) {
    idProgetto = val;
  }

  public Long getIdSoggettoBeneficiario() {
    return idSoggettoBeneficiario;
  }

  public void setIdSoggettoBeneficiario(Long val) {
    idSoggettoBeneficiario = val;
  }

  public String getTask() {
    return task;
  }

  public void setTask(String val) {
    task = val;
  }

  public Date getDataA() {
    return dataA;
  }

  public void setDataA(Date val) {
    dataA = val;
  }

  public Long getIdCategoria() {
    return idCategoria;
  }

  public void setIdCategoria(Long idCategoria) {
    this.idCategoria = idCategoria;
  }
}