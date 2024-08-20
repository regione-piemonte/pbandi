/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.List;

public class DistintaInterventiVO {

    private Long idProposta;
    private List<InterventoSostitutivoVO> listaInterventi;

    private String descrizione;

    public Long getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(Long idProposta) {
        this.idProposta = idProposta;
    }

    public List<InterventoSostitutivoVO> getListaInterventi() {
        return listaInterventi;
    }

    public void setListaInterventi(List<InterventoSostitutivoVO> listaInterventi) {
        this.listaInterventi = listaInterventi;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
