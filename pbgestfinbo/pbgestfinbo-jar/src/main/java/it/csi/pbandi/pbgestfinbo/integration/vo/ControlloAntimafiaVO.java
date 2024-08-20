/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.util.Date;

public class ControlloAntimafiaVO {
    private Date dataEmissioneAntimafia;
    private String descEsitoAntimafia;
    private String descStatoRichiestaAntimafia;

    private String descTipoRichiesta;
    private Date dataScadenzaAntimafia;
    private Date dataInvioBDNA;

    public ControlloAntimafiaVO() {
    }

    public Date getDataEmissioneAntimafia() {
        return dataEmissioneAntimafia;
    }

    public void setDataEmissioneAntimafia(Date dataEmissioneAntimafia) {
        this.dataEmissioneAntimafia = dataEmissioneAntimafia;
    }

    public String getDescEsitoAntimafia() {
        return descEsitoAntimafia;
    }

    public void setDescEsitoAntimafia(String descEsitoAntimafia) {
        this.descEsitoAntimafia = descEsitoAntimafia;
    }

    public String getDescStatoRichiestaAntimafia() {
        return descStatoRichiestaAntimafia;
    }

    public void setDescStatoRichiestaAntimafia(String descStatoRichiestaAntimafia) {
        this.descStatoRichiestaAntimafia = descStatoRichiestaAntimafia;
    }

    public Date getDataScadenzaAntimafia() {
        return dataScadenzaAntimafia;
    }

    public void setDataScadenzaAntimafia(Date dataScadenzaAntimafia) {
        this.dataScadenzaAntimafia = dataScadenzaAntimafia;
    }

    public Date getDataInvioBDNA() {
        return dataInvioBDNA;
    }

    public void setDataInvioBDNA(Date dataInvioBDNA) {
        this.dataInvioBDNA = dataInvioBDNA;
    }

    public String getDescTipoRichiesta() {
        return descTipoRichiesta;
    }

    public void setDescTipoRichiesta(String descTipoRichiesta) {
        this.descTipoRichiesta = descTipoRichiesta;
    }
}
