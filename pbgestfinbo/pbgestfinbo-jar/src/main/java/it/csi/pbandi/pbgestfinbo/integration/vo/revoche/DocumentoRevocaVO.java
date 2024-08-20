/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;

import java.util.Date;

public class DocumentoRevocaVO {

    private Long numeroRevoca;
    private Long idDocumento;
    private String nomeFile;
    private String originiDocumento;
    private Date dataDocumento;

    private Long idTipoDocumento;

    private Boolean bozza;

    public DocumentoRevocaVO() {
    }

    public Long getNumeroRevoca() {
        return numeroRevoca;
    }

    public void setNumeroRevoca(Long numeroRevoca) {
        this.numeroRevoca = numeroRevoca;
    }

    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public String getOriginiDocumento() {
        return originiDocumento;
    }

    public void setOriginiDocumento(String originiDocumento) {
        this.originiDocumento = originiDocumento;
    }

    public Date getDataDocumento() {
        return dataDocumento;
    }

    public void setDataDocumento(Date dataDocumento) {
        this.dataDocumento = dataDocumento;
    }

    public Long getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Long idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public Boolean getBozza() {
        return bozza;
    }

    public void setBozza(Boolean bozza) {
        this.bozza = bozza;
    }
}
