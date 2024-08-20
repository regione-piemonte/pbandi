/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.suggestion;

public class BandoSuggestVO {

    Long idBando;
    String titoloBando;

    public BandoSuggestVO() {
    }

    public Long getIdBando() {
        return idBando;
    }

    public void setIdBando(Long idBando) {
        this.idBando = idBando;
    }

    public String getTitoloBando() {
        return titoloBando;
    }

    public void setTitoloBando(String titoloBando) {
        this.titoloBando = titoloBando;
    }
}
