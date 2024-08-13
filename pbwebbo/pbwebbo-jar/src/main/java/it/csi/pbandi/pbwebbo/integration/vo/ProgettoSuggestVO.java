/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.vo;

public class ProgettoSuggestVO {
    private Long idProgetto;
    private String codiceVisualizzato;

    private String titoloProgetto;
    public ProgettoSuggestVO() {
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

    public String getTitoloProgetto() {
        return titoloProgetto;
    }

    public void setTitoloProgetto(String titoloProgetto) {
        this.titoloProgetto = titoloProgetto;
    }
}
