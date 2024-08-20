/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;

public class InterventoSostitutivoVO {

    private Long idDestinatario;
    private String denominazione;
    private String iban;
    private BigDecimal importoDaErogare;

    public Long getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(Long idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BigDecimal getImportoDaErogare() {
        return importoDaErogare;
    }

    public void setImportoDaErogare(BigDecimal importoDaErogare) {
        this.importoDaErogare = importoDaErogare;
    }
}
