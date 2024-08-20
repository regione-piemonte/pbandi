/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;

import java.util.Date;

public class PropostaRevocaMiniVO {
    private Long numeroRevoca;
    private Long idProgetto;
    private Long idCausaleBlocco;
    private Long idAutoritaControllante;
    private Date dataPropostaRevoca;

    public PropostaRevocaMiniVO() {
    }

    public PropostaRevocaMiniVO(Long numeroRevoca, Long idProgetto, Long idCausaleBlocco, Long idAutoritaControllante, Date dataPropostaRevoca) {
        this.numeroRevoca = numeroRevoca;
        this.idProgetto = idProgetto;
        this.idCausaleBlocco = idCausaleBlocco;
        this.idAutoritaControllante = idAutoritaControllante;
        this.dataPropostaRevoca = dataPropostaRevoca;
    }

    public Long getNumeroRevoca() {
        return numeroRevoca;
    }

    public void setNumeroRevoca(Long numeroRevoca) {
        this.numeroRevoca = numeroRevoca;
    }

    public Long getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(Long idProgetto) {
        this.idProgetto = idProgetto;
    }

    public Long getIdCausaleBlocco() {
        return idCausaleBlocco;
    }

    public void setIdCausaleBlocco(Long idCausaleBlocco) {
        this.idCausaleBlocco = idCausaleBlocco;
    }

    public Long getIdAutoritaControllante() {
        return idAutoritaControllante;
    }

    public void setIdAutoritaControllante(Long idAutoritaControllante) {
        this.idAutoritaControllante = idAutoritaControllante;
    }

    public Date getDataPropostaRevoca() {
        return dataPropostaRevoca;
    }

    public void setDataPropostaRevoca(Date dataPropostaRevoca) {
        this.dataPropostaRevoca = dataPropostaRevoca;
    }
}
