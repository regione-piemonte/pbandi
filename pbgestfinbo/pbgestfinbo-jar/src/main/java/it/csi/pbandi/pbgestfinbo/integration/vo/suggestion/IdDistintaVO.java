/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.suggestion;

public class IdDistintaVO {

    private Long idDistinta;
    private String descrizioneDistinta;
    private String descrizioneModalitaAgevolazione;

    public IdDistintaVO() {
    }

    public Long getIdDistinta() {
        return idDistinta;
    }

    public void setIdDistinta(Long idDistinta) {
        this.idDistinta = idDistinta;
    }

    public String getDescrizioneDistinta() {
        return descrizioneDistinta;
    }

    public void setDescrizioneDistinta(String descrizioneDistinta) {
        this.descrizioneDistinta = descrizioneDistinta;
    }

    public String getDescrizioneModalitaAgevolazione() {
        return descrizioneModalitaAgevolazione;
    }

    public void setDescrizioneModalitaAgevolazione(String descrizioneModalitaAgevolazione) {
        this.descrizioneModalitaAgevolazione = descrizioneModalitaAgevolazione;
    }
}
