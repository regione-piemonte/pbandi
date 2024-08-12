package it.csi.pbandi.pbweb.dto.rendicontazione;

import java.util.Date;

public class DocumentoDiSpesaSospesoDTO implements java.io.Serializable {

    static final long serialVersionUID = 1;

    private Long idDocumentoDiSpesa;
    private String fornitore;
    private String documento;
    private Double importo;
    private String note;

    private Date data;

    public Long getIdDocumentoDiSpesa() {
        return idDocumentoDiSpesa;
    }

    public void setIdDocumentoDiSpesa(Long idDocumentoDiSpesa) {
        this.idDocumentoDiSpesa = idDocumentoDiSpesa;
    }

    public String getFornitore() {
        return fornitore;
    }

    public void setFornitore(String fornitore) {
        this.fornitore = fornitore;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Double getImporto() {
        return importo;
    }

    public void setImporto(Double importo) {
        this.importo = importo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
