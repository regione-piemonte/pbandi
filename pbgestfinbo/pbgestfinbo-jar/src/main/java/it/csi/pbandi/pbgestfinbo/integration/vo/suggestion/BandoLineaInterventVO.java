/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.suggestion;

public class BandoLineaInterventVO {
    private Long idBandoLineaIntervent;
    private String nomeBandoLineaIntervent;

    public BandoLineaInterventVO() {
    }

    public Long getIdBandoLineaIntervent() {
        return idBandoLineaIntervent;
    }

    public void setIdBandoLineaIntervent(Long idBandoLineaIntervent) {
        this.idBandoLineaIntervent = idBandoLineaIntervent;
    }

    public String getNomeBandoLineaIntervent() {
        return nomeBandoLineaIntervent;
    }

    public void setNomeBandoLineaIntervent(String nomeBandoLineaIntervent) {
        this.nomeBandoLineaIntervent = nomeBandoLineaIntervent;
    }
}
