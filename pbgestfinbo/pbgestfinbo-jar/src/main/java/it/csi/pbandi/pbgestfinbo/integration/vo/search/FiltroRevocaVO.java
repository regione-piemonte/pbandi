/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.util.Date;

public class FiltroRevocaVO {
    private Long idTipologiaRevoca;
    private Long numeroRevoca;
    private Long idSoggetto;
    private Long idProgetto;
    private Long progrBandoLineaIntervent;
    private Long idCausaRevoca;
    private Long idStatoRevoca;
    private Long idAttivitaRevoca;
    private Date dataRevocaFrom;
    private Date dataRevocaTo;

    private String value;

    public FiltroRevocaVO() {
    }

    public Long getNumeroRevoca() {
        return numeroRevoca;
    }

    public void setNumeroRevoca(Long numeroRevoca) {
        this.numeroRevoca = numeroRevoca;
    }

    public Long getIdSoggetto() {
        return idSoggetto;
    }

    public void setIdSoggetto(Long idSoggetto) {
        this.idSoggetto = idSoggetto;
    }

    public Long getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(Long idProgetto) {
        this.idProgetto = idProgetto;
    }

    public Long getProgrBandoLineaIntervent() {
        return progrBandoLineaIntervent;
    }

    public void setProgrBandoLineaIntervent(Long progrBandoLineaIntervent) {
        this.progrBandoLineaIntervent = progrBandoLineaIntervent;
    }

    public Long getIdCausaRevoca() {
        return idCausaRevoca;
    }

    public void setIdCausaRevoca(Long idCausaRevoca) {
        this.idCausaRevoca = idCausaRevoca;
    }

    public Long getIdStatoRevoca() {
        return idStatoRevoca;
    }

    public void setIdStatoRevoca(Long idStatoRevoca) {
        this.idStatoRevoca = idStatoRevoca;
    }

    public Long getIdAttivitaRevoca() {
        return idAttivitaRevoca;
    }

    public void setIdAttivitaRevoca(Long idAttivitaRevoca) {
        this.idAttivitaRevoca = idAttivitaRevoca;
    }

    public Date getDataRevocaFrom() {
        return dataRevocaFrom;
    }

    public void setDataRevocaFrom(Date dataRevocaFrom) {
        this.dataRevocaFrom = dataRevocaFrom;
    }

    public Date getDataRevocaTo() {
        return dataRevocaTo;
    }

    public void setDataRevocaTo(Date dataRevocaTo) {
        this.dataRevocaTo = dataRevocaTo;
    }

    public Long getIdTipologiaRevoca() {
        return idTipologiaRevoca;
    }

    public void setIdTipologiaRevoca(Long idTipologiaRevoca) {
        this.idTipologiaRevoca = idTipologiaRevoca;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
