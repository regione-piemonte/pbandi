/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;

import java.util.Date;

public class ParamAmmContabileVO {
    private Integer idBando;
    private Integer idProgetto;
    private Long idStato;
    private Date dtStato;
    private Integer idModalitaAgevolazione;
    private Integer idModalitaAgevolazioneRif;

    public ParamAmmContabileVO() {
    }

    public Integer getIdBando() {
        return idBando;
    }

    public void setIdBando(Integer idBando) {
        this.idBando = idBando;
    }

    public Integer getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(Integer idProgetto) {
        this.idProgetto = idProgetto;
    }

    public Long getIdStato() {
        return idStato;
    }

    public void setIdStato(Long idStato) {
        this.idStato = idStato;
    }

    public Date getDtStato() {
        return dtStato;
    }

    public void setDtStato(Date dtStato) {
        this.dtStato = dtStato;
    }

    public Integer getIdModalitaAgevolazione() {
        return idModalitaAgevolazione;
    }

    public void setIdModalitaAgevolazione(Integer idModalitaAgevolazione) {
        this.idModalitaAgevolazione = idModalitaAgevolazione;
    }

    public Integer getIdModalitaAgevolazioneRif() {
        return idModalitaAgevolazioneRif;
    }

    public void setIdModalitaAgevolazioneRif(Integer idModalitaAgevolazioneRif) {
        this.idModalitaAgevolazioneRif = idModalitaAgevolazioneRif;
    }
}
