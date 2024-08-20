/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.util.Date;

public class ControlloDurcVO {
    private Date dataEmissioneDurc;
    private String descEsitoDurc;
    private String descStatoRichiestaDurc;

    private String descTipoRichiesta;
    private Date dataScadenzaDurc;

    public ControlloDurcVO() {
    }

    public Date getDataEmissioneDurc() {
        return dataEmissioneDurc;
    }

    public void setDataEmissioneDurc(Date dataEmissioneDurc) {
        this.dataEmissioneDurc = dataEmissioneDurc;
    }

    public String getDescEsitoDurc() {
        return descEsitoDurc;
    }

    public void setDescEsitoDurc(String descEsitoDurc) {
        this.descEsitoDurc = descEsitoDurc;
    }

    public String getDescStatoRichiestaDurc() {
        return descStatoRichiestaDurc;
    }

    public void setDescStatoRichiestaDurc(String descStatoRichiestaDurc) {
        this.descStatoRichiestaDurc = descStatoRichiestaDurc;
    }

    public Date getDataScadenzaDurc() {
        return dataScadenzaDurc;
    }

    public void setDataScadenzaDurc(Date dataScadenzaDurc) {
        this.dataScadenzaDurc = dataScadenzaDurc;
    }

    public String getDescTipoRichiesta() {
        return descTipoRichiesta;
    }

    public void setDescTipoRichiesta(String descTipoRichiesta) {
        this.descTipoRichiesta = descTipoRichiesta;
    }
}
