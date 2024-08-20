/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.util.Date;

public class FiltroPropostaRevocaVO {
    private Long idSoggetto;
    private Long idBando;
    private Long idProgetto;
    private Long idCausaleBlocco;
    private Long idStatoRevoca;
    private Long numeroPropostaRevoca;
    private Date dataPropostaRevocaFrom;
    private Date dataPropostaRevocaTo;

    public FiltroPropostaRevocaVO() {
    }

    public Long getIdSoggetto() {
        return idSoggetto;
    }

    public void setIdSoggetto(Long idSoggetto) {
        this.idSoggetto = idSoggetto;
    }

    public Long getIdBando() {
        return idBando;
    }

    public void setIdBando(Long idBando) {
        this.idBando = idBando;
    }

    public Long getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(Long idProgetto) {
        this.idProgetto = idProgetto;
    }

    public Long getIdStatoRevoca() {
        return idStatoRevoca;
    }

    public void setIdStatoRevoca(Long idStatoRevoca) {
        this.idStatoRevoca = idStatoRevoca;
    }

    public Long getNumeroPropostaRevoca() {
        return numeroPropostaRevoca;
    }

    public void setNumeroPropostaRevoca(Long numeroPropostaRevoca) {
        this.numeroPropostaRevoca = numeroPropostaRevoca;
    }

    public Date getDataPropostaRevocaFrom() {
        return dataPropostaRevocaFrom;
    }

    public void setDataPropostaRevocaFrom(Date dataPropostaRevocaFrom) {
        this.dataPropostaRevocaFrom = dataPropostaRevocaFrom;
    }

    public Date getDataPropostaRevocaTo() {
        return dataPropostaRevocaTo;
    }

    public void setDataPropostaRevocaTo(Date dataPropostaRevocaTo) {
        this.dataPropostaRevocaTo = dataPropostaRevocaTo;
    }

    public Long getIdCausaleBlocco() {
        return idCausaleBlocco;
    }

    public void setIdCausaleBlocco(Long idCausaleBlocco) {
        this.idCausaleBlocco = idCausaleBlocco;
    }
}
