/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentazione;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class ProgettoUtenteVO extends GenericVO {

    private BigDecimal idProgetto;
    private String codiceVisualizzatoProgetto;
    private String numeroDomanda;

    public void setIdProgetto(BigDecimal idProgetto) {
        this.idProgetto = idProgetto;
    }

    public BigDecimal getIdProgetto() {
        return idProgetto;
    }

    public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
        this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
    }

    public String getCodiceVisualizzatoProgetto() {
        return codiceVisualizzatoProgetto;
    }

    public String getNumeroDomanda() {
        return numeroDomanda;
    }

    public void setNumeroDomanda(String numeroDomanda) {
        this.numeroDomanda = numeroDomanda;
    }

}
