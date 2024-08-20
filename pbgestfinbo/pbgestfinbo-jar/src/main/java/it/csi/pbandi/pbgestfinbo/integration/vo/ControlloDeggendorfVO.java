/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.util.Date;
import java.util.Objects;

public class ControlloDeggendorfVO {
    private Date dataEmissione;
    private String vercor;
    private Boolean esitoRichiesta;
    private Date dataScadenza; //dataEmissione +20gg

    public ControlloDeggendorfVO() {
    }

    public Date getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(Date dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public String getVercor() {
        return vercor;
    }

    public void setVercor(String vercor) {
        this.vercor = vercor;
    }

    public Boolean getEsitoRichiesta() {
        return esitoRichiesta;
    }

    public void setEsitoRichiesta(Boolean esitoRichiesta) {
        this.esitoRichiesta = esitoRichiesta;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ControlloDeggendorfVO that = (ControlloDeggendorfVO) o;

        if (!Objects.equals(dataEmissione, that.dataEmissione))
            return false;
        if (!Objects.equals(esitoRichiesta, that.esitoRichiesta))
            return false;
        return Objects.equals(dataScadenza, that.dataScadenza);
    }

    @Override
    public int hashCode() {
        int result = dataEmissione != null ? dataEmissione.hashCode() : 0;
        result = 31 * result + (esitoRichiesta != null ? esitoRichiesta.hashCode() : 0);
        result = 31 * result + (dataScadenza != null ? dataScadenza.hashCode() : 0);
        return result;
    }
}
