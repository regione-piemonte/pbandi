package it.csi.pbandi.pbweb.dto.rendicontazione;

import java.util.Date;

public class DocIntegrRendDTO implements java.io.Serializable {

    static final long serialVersionUID = 1;

    private Long idDocumentoIndex;
    private Long idFileEntita;
    private String flagEntita;
    private String nomeFile;
    private String note;

    private Date data;

    private Long idIntegrazione;

    public Long getIdDocumentoIndex() {
        return idDocumentoIndex;
    }

    public void setIdDocumentoIndex(Long idDocumentoIndex) {
        this.idDocumentoIndex = idDocumentoIndex;
    }

    public Long getIdFileEntita() {
        return idFileEntita;
    }

    public void setIdFileEntita(Long idFileEntita) {
        this.idFileEntita = idFileEntita;
    }

    public String getFlagEntita() {
        return flagEntita;
    }

    public void setFlagEntita(String flagEntita) {
        this.flagEntita = flagEntita;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
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

    public Long getIdIntegrazione() {
        return idIntegrazione;
    }

    public void setIdIntegrazione(Long idIntegrazione) {
        this.idIntegrazione = idIntegrazione;
    }
}
