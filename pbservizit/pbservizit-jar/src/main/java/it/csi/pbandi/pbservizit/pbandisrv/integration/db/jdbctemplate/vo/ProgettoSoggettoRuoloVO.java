/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class ProgettoSoggettoRuoloVO extends GenericVO {

    private BigDecimal idProgetto;
    private BigDecimal idSoggetto;
    private String numeroDomanda;
    private BigDecimal idTipoAnagrafica;
    private BigDecimal idSoggettoBeneficiario;
    private BigDecimal progrBandoLineaIntervento;
    private String descBreveTipoAnagrafica;
    private String codiceFiscaleSoggetto;
    private String codiceVisualizzatoProgetto;

    public BigDecimal getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(BigDecimal idProgetto) {
        this.idProgetto = idProgetto;
    }

    public BigDecimal getIdSoggetto() {
        return idSoggetto;
    }

    public void setIdSoggetto(BigDecimal idSoggetto) {
        this.idSoggetto = idSoggetto;
    }

    public String getNumeroDomanda() {
        return numeroDomanda;
    }

    public void setNumeroDomanda(String numeroDomanda) {
        this.numeroDomanda = numeroDomanda;
    }

    public BigDecimal getIdTipoAnagrafica() {
        return idTipoAnagrafica;
    }

    public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
        this.idTipoAnagrafica = idTipoAnagrafica;
    }

    public String getDescBreveTipoAnagrafica() {
        return descBreveTipoAnagrafica;
    }

    public void setDescBreveTipoAnagrafica(String descBreveTipoAnagrafica) {
        this.descBreveTipoAnagrafica = descBreveTipoAnagrafica;
    }

    public String getCodiceFiscaleSoggetto() {
        return codiceFiscaleSoggetto;
    }

    public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
        this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
    }

    public String getCodiceVisualizzatoProgetto() {
        return codiceVisualizzatoProgetto;
    }

    public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
        this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
    }

    public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
        this.idSoggettoBeneficiario = idSoggettoBeneficiario;
    }

    public BigDecimal getIdSoggettoBeneficiario() {
        return idSoggettoBeneficiario;
    }

    public BigDecimal getProgrBandoLineaIntervento() {
        return progrBandoLineaIntervento;
    }

    public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
        this.progrBandoLineaIntervento = progrBandoLineaIntervento;
    }
}
