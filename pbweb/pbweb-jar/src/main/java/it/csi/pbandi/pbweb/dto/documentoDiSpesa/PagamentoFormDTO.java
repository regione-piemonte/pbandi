package it.csi.pbandi.pbweb.dto.documentoDiSpesa;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class PagamentoFormDTO implements java.io.Serializable {

  static final long serialVersionUID = 1;

  private Long idPagamento = null;
  private Long idModalitaPagamento = null;
  private java.util.Date dtPagamento = null;
  private Double importoPagamento = null;
  private Long idCausalePagamento = null;
  private String rifPagamento = null;
  private String flagPagamento = null;

  public Long getIdPagamento() {
    return idPagamento;
  }

  public void setIdPagamento(Long idPagamento) {
    this.idPagamento = idPagamento;
  }

  public Long getIdModalitaPagamento() {
    return idModalitaPagamento;
  }

  public void setIdModalitaPagamento(Long idModalitaPagamento) {
    this.idModalitaPagamento = idModalitaPagamento;
  }

  public java.util.Date getDtPagamento() {
    return dtPagamento;
  }

  public void setDtPagamento(java.util.Date dtPagamento) {
    this.dtPagamento = dtPagamento;
  }

  public Double getImportoPagamento() {
    return importoPagamento;
  }

  public void setImportoPagamento(Double importoPagamento) {
    this.importoPagamento = importoPagamento;
  }

  public Long getIdCausalePagamento() {
    return idCausalePagamento;
  }

  public void setIdCausalePagamento(Long idCausalePagamento) {
    this.idCausalePagamento = idCausalePagamento;
  }

  public String getRifPagamento() {
    return rifPagamento;
  }

  public void setRifPagamento(String rifPagamento) {
    this.rifPagamento = rifPagamento;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("\n" + this.getClass().getName() + ": ");
    try {
      Set<String> properties = BeanUtil.getProperties(this.getClass());
      for (String propName : properties) {
        sb.append("\n" + propName + " = " + BeanUtil.getPropertyValueByName(this, propName));
      }
    } catch (IntrospectionException e) {
    }
    return sb.toString();
  }

  public String getFlagPagamento() {
    return flagPagamento;
  }

  public void setFlagPagamento(String flagPagamento) {
    this.flagPagamento = flagPagamento;
  }
}
