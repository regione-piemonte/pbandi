/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.suggestion;

public class NumeroRevocaSuggestVO {
    private Long idGestioneRevoca;
    private Long numeroRevoca;

    public NumeroRevocaSuggestVO() {
    }

    public Long getIdGestioneRevoca() {
        return idGestioneRevoca;
    }

    public void setIdGestioneRevoca(Long idGestioneRevoca) {
        this.idGestioneRevoca = idGestioneRevoca;
    }

    public Long getNumeroRevoca() {
        return numeroRevoca;
    }

    public void setNumeroRevoca(Long numeroRevoca) {
        this.numeroRevoca = numeroRevoca;
    }
}
