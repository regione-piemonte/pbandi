/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.iterAutorizzativi;

public class DocumentoIterVO {
    private Long idDocumentoIndex;
    private Long idTipoDocumentoIndex;
    private String nomeFile;

    private Boolean flagExcel;
    private Integer tipoExcel; //dichiarazione_spesa = 1, proposte_erogazione = 2
    private Long idTargetExcel;

    private Long idProgetto;
    private String codiceVisualizzato;

    public DocumentoIterVO() {
    }

    public Long getIdTargetExcel() {
        return idTargetExcel;
    }

    public void setIdTargetExcel(Long idTargetExcel) {
        this.idTargetExcel = idTargetExcel;
    }

    public Integer getTipoExcel() {
        return tipoExcel;
    }

    public void setTipoExcel(Integer tipoExcel) {
        this.tipoExcel = tipoExcel;
    }

    public Long getIdDocumentoIndex() {
        return idDocumentoIndex;
    }

    public void setIdDocumentoIndex(Long idDocumentoIndex) {
        this.idDocumentoIndex = idDocumentoIndex;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public Long getIdTipoDocumentoIndex() {
        return idTipoDocumentoIndex;
    }

    public void setIdTipoDocumentoIndex(Long idTipoDocumentoIndex) {
        this.idTipoDocumentoIndex = idTipoDocumentoIndex;
    }

    public Boolean getFlagExcel() {
        return flagExcel;
    }

    public void setFlagExcel(Boolean flagExcel) {
        this.flagExcel = flagExcel;
    }

    public Long getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(Long idProgetto) {
        this.idProgetto = idProgetto;
    }

    public String getCodiceVisualizzato() {
        return codiceVisualizzato;
    }

    public void setCodiceVisualizzato(String codiceVisualizzato) {
        this.codiceVisualizzato = codiceVisualizzato;
    }
}
