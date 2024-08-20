/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

public class SaveControlloPreErogazioneVO {
    private Long idProposta;
    private Long idSoggetto;
    private Boolean esitoDeggendorf;

    private String vercor;

    public SaveControlloPreErogazioneVO() {
    }

    public Long getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(Long idProposta) {
        this.idProposta = idProposta;
    }

    public Long getIdSoggetto() {
        return idSoggetto;
    }

    public void setIdSoggetto(Long idSoggetto) {
        this.idSoggetto = idSoggetto;
    }

    public Boolean getEsitoDeggendorf() {
        return esitoDeggendorf;
    }

    public void setEsitoDeggendorf(Boolean esitoDeggendorf) {
        this.esitoDeggendorf = esitoDeggendorf;
    }

    public String getVercor() {
        return vercor;
    }

    public void setVercor(String vercor) {
        this.vercor = vercor;
    }
}
