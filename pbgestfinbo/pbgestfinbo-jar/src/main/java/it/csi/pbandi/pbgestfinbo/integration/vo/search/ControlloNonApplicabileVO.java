/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.math.BigDecimal;

public class ControlloNonApplicabileVO {

    private Long idProposta;
    private Long idTipoRichiesta;
    private String motivazione;

    public ControlloNonApplicabileVO() {
    }

    public Long getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(Long idProposta) {
        this.idProposta = idProposta;
    }

    public Long getIdTipoRichiesta() {
        return idTipoRichiesta;
    }

    public void setIdTipoRichiesta(Long idTipoRichiesta) {
        this.idTipoRichiesta = idTipoRichiesta;
    }

    public String getMotivazione() {
        return motivazione;
    }

    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }
}
