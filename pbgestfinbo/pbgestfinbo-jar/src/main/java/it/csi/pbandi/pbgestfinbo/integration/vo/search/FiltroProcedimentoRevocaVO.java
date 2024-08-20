/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.util.Date;

public class FiltroProcedimentoRevocaVO {
    private Long numeroProcedimentoRevoca; //Numero del procedimento di revoca
    private Long idSoggetto; //Beneficiario
    private Long idProgetto; //Progetto
    private Long progrBandoLineaIntervent; //Bando
    private Long idCausaleBlocco; //Causa del procedimento di revoca
    private Long idStatoRevoca; //Stato del procedimento di revoca
    private Long idAttivitaRevoca; //Attività del procedimento di revoca
    private Date dataProcedimentoRevocaFrom;
    private Date dataProcedimentoRevocaTo;

    public FiltroProcedimentoRevocaVO() {
    }

    public Long getNumeroProcedimentoRevoca() {
        return numeroProcedimentoRevoca;
    }

    public void setNumeroProcedimentoRevoca(Long numeroProcedimentoRevoca) {
        this.numeroProcedimentoRevoca = numeroProcedimentoRevoca;
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

    public Long getIdCausaleBlocco() {
        return idCausaleBlocco;
    }

    public void setIdCausaleBlocco(Long idCausaleBlocco) {
        this.idCausaleBlocco = idCausaleBlocco;
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

    public Date getDataProcedimentoRevocaFrom() {
        return dataProcedimentoRevocaFrom;
    }

    public void setDataProcedimentoRevocaFrom(Date dataProcedimentoRevocaFrom) {
        this.dataProcedimentoRevocaFrom = dataProcedimentoRevocaFrom;
    }

    public Date getDataProcedimentoRevocaTo() {
        return dataProcedimentoRevocaTo;
    }

    public void setDataProcedimentoRevocaTo(Date dataProcedimentoRevocaTo) {
        this.dataProcedimentoRevocaTo = dataProcedimentoRevocaTo;
    }
}
