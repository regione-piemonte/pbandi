/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.suggestion;

public class AgevolazioneSuggestVO {

    private Long idModalitaAgevolazione;
    private String descModalitaAgevolazione;
    private String descBreveModalitaAgevolazione;

    public AgevolazioneSuggestVO() {
    }

    public Long getIdModalitaAgevolazione() {
        return idModalitaAgevolazione;
    }

    public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
        this.idModalitaAgevolazione = idModalitaAgevolazione;
    }

    public String getDescModalitaAgevolazione() {
        return descModalitaAgevolazione;
    }

    public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
        this.descModalitaAgevolazione = descModalitaAgevolazione;
    }

    public String getDescBreveModalitaAgevolazione() {
        return descBreveModalitaAgevolazione;
    }

    public void setDescBreveModalitaAgevolazione(String descBreveModalitaAgevolazione) {
        this.descBreveModalitaAgevolazione = descBreveModalitaAgevolazione;
    }
}
