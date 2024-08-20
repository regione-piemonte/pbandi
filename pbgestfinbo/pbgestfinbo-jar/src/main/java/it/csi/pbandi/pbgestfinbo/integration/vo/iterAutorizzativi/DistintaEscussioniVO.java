/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.iterAutorizzativi;

import java.util.Date;

public class DistintaEscussioniVO {
    private Integer idProgetto;
    private Integer idBando;
    private Integer idDistinta;
    private Integer idDistintaDett;
    private Integer rigaDistinta;
    private String ibanBonifico;
    private Date dtInizioValidita;
    private Double importoApprovato;
    private Integer idTipoDistinta;
    private Integer idTipoEscussione;
    private String causaleBonifico;
    private Integer idModalitaAgevolazione;

    private Integer idModalitaAgevolazioneRif;

    public DistintaEscussioniVO() {
    }

    public Integer getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(Integer idProgetto) {
        this.idProgetto = idProgetto;
    }

    public Integer getIdBando() {
        return idBando;
    }

    public void setIdBando(Integer idBando) {
        this.idBando = idBando;
    }

    public Integer getIdDistinta() {
        return idDistinta;
    }

    public void setIdDistinta(Integer idDistinta) {
        this.idDistinta = idDistinta;
    }

    public Integer getIdDistintaDett() {
        return idDistintaDett;
    }

    public void setIdDistintaDett(Integer idDistintaDett) {
        this.idDistintaDett = idDistintaDett;
    }

    public Integer getRigaDistinta() {
        return rigaDistinta;
    }

    public void setRigaDistinta(Integer rigaDistinta) {
        this.rigaDistinta = rigaDistinta;
    }

    public String getIbanBonifico() {
        return ibanBonifico;
    }

    public void setIbanBonifico(String ibanBonifico) {
        this.ibanBonifico = ibanBonifico;
    }

    public Date getDtInizioValidita() {
        return dtInizioValidita;
    }

    public void setDtInizioValidita(Date dtInizioValidita) {
        this.dtInizioValidita = dtInizioValidita;
    }

    public Double getImportoApprovato() {
        return importoApprovato;
    }

    public void setImportoApprovato(Double importoApprovato) {
        this.importoApprovato = importoApprovato;
    }

    public Integer getIdTipoDistinta() {
        return idTipoDistinta;
    }

    public void setIdTipoDistinta(Integer idTipoDistinta) {
        this.idTipoDistinta = idTipoDistinta;
    }

    public Integer getIdTipoEscussione() {
        return idTipoEscussione;
    }

    public void setIdTipoEscussione(Integer idTipoEscussione) {
        this.idTipoEscussione = idTipoEscussione;
    }

    public String getCausaleBonifico() {
        return causaleBonifico;
    }

    public void setCausaleBonifico(String causaleBonifico) {
        this.causaleBonifico = causaleBonifico;
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
