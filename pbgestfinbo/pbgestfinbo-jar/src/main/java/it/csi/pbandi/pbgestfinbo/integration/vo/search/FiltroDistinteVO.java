/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.util.Date;

public class FiltroDistinteVO {
    private Date dataCreazioneFrom;
    private Date dataCreazioneTo;
    private Long idBeneficiario;

    private Long idBando;

    private Long idAgevolazione;
    private Long idProgetto;
    private Long idDistinta;

    public FiltroDistinteVO() {
    }

    public Date getDataCreazioneFrom() {
        return dataCreazioneFrom;
    }

    public void setDataCreazioneFrom(Date dataCreazioneFrom) {
        this.dataCreazioneFrom = dataCreazioneFrom;
    }

    public Date getDataCreazioneTo() {
        return dataCreazioneTo;
    }

    public void setDataCreazioneTo(Date dataCreazioneTo) {
        this.dataCreazioneTo = dataCreazioneTo;
    }

    public Long getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public Long getIdBando() {
        return idBando;
    }

    public void setIdBando(Long idBando) {
        this.idBando = idBando;
    }

    public Long getIdAgevolazione() {
        return idAgevolazione;
    }

    public void setIdAgevolazione(Long idAgevolazione) {
        this.idAgevolazione = idAgevolazione;
    }

    public Long getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(Long idProgetto) {
        this.idProgetto = idProgetto;
    }

    public Long getIdDistinta() {
        return idDistinta;
    }

    public void setIdDistinta(Long idDistinta) {
        this.idDistinta = idDistinta;
    }
}
