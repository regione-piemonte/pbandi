/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;

public class PropostaErogazioneExcelVO {

    private String titoloProgetto;
    private String codiceVisualizzatoProgetto;
    private String denominazioneBeneficiario;
    private String codiceFiscale;
    private String partitaIva;
    private String iban;
    private BigDecimal importoLordo;
    private BigDecimal importoIres;
    private BigDecimal importoNetto;
    private Date dataContabileErogazione;
    private BigDecimal importoErogato;

    public PropostaErogazioneExcelVO() {
    }

    public String getTitoloProgetto() {
        return titoloProgetto;
    }

    public void setTitoloProgetto(String titoloProgetto) {
        this.titoloProgetto = titoloProgetto;
    }

    public String getCodiceVisualizzatoProgetto() {
        return codiceVisualizzatoProgetto;
    }

    public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
        this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
    }

    public String getDenominazioneBeneficiario() {
        return denominazioneBeneficiario;
    }

    public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
        this.denominazioneBeneficiario = denominazioneBeneficiario;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BigDecimal getImportoLordo() {
        return importoLordo;
    }

    public void setImportoLordo(BigDecimal importoLordo) {
        this.importoLordo = importoLordo;
    }

    public BigDecimal getImportoIres() {
        return importoIres;
    }

    public void setImportoIres(BigDecimal importoIres) {
        this.importoIres = importoIres;
    }

    public BigDecimal getImportoNetto() {
        return importoNetto;
    }

    public void setImportoNetto(BigDecimal importoNetto) {
        this.importoNetto = importoNetto;
    }

    public Date getDataContabileErogazione() {
        return dataContabileErogazione;
    }

    public void setDataContabileErogazione(Date dataContabileErogazione) {
        this.dataContabileErogazione = dataContabileErogazione;
    }

    public BigDecimal getImportoErogato() {
        return importoErogato;
    }

    public void setImportoErogato(BigDecimal importoErogato) {
        this.importoErogato = importoErogato;
    }
}
