/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;

public class ArchivioRevocaVO {
    private Long idGestioneRevoca;
    private String noteRevoca;

    public ArchivioRevocaVO() {
    }

    public ArchivioRevocaVO(Long idGestioneRevoca, String noteRevoca) {
        this.idGestioneRevoca = idGestioneRevoca;
        this.noteRevoca = noteRevoca;
    }

    public Long getIdGestioneRevoca() {
        return idGestioneRevoca;
    }

    public void setIdGestioneRevoca(Long idGestioneRevoca) {
        this.idGestioneRevoca = idGestioneRevoca;
    }

    public String getNoteRevoca() {
        return noteRevoca;
    }

    public void setNoteRevoca(String noteRevoca) {
        this.noteRevoca = noteRevoca;
    }
}
