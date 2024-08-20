/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.iterAutorizzativi;

import java.util.List;

public class IterListVO {
    List<Long> idWorkFlowList;
    String motivazione;

    public IterListVO() {
    }

    public String getMotivazione() {
        return motivazione;
    }

    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }

    public List<Long> getIdWorkFlowList() {
        return idWorkFlowList;
    }

    public void setIdWorkFlowList(List<Long> idWorkFlowList) {
        this.idWorkFlowList = idWorkFlowList;
    }
}
