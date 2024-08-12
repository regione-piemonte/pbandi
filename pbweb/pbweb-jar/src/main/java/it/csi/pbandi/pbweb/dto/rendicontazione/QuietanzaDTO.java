package it.csi.pbandi.pbweb.dto.rendicontazione;

import java.util.Date;

public class QuietanzaDTO  implements java.io.Serializable {

    static final long serialVersionUID = 1;

    private Long idPagamento = null;
    private Long idModalitaPagamento = null;
    private String descModalitaPagamento = null;
    private Date dataPagamento = null;
    private Double importoPagamento = null;

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

    public String getDescModalitaPagamento() {
        return descModalitaPagamento;
    }

    public void setDescModalitaPagamento(String descModalitaPagamento) {
        this.descModalitaPagamento = descModalitaPagamento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Double getImportoPagamento() {
        return importoPagamento;
    }

    public void setImportoPagamento(Double importoPagamento) {
        this.importoPagamento = importoPagamento;
    }
}
