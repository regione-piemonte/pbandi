/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

public class IbanBeneficiario {
    private Integer idDomanda;
    private Integer idProgetto;
    private Integer idSoggettoBeneficiario;
    private Integer progrSoggettoProgetto;
    private Integer idPersonaFisica;
    private Integer idEstremiBancari;
    private String denominazioneBeneficiario;
    private String codiceFiscaleBeneficiario;

    public Integer getIdDomanda() {
        return idDomanda;
    }

    public void setIdDomanda(Integer idDomanda) {
        this.idDomanda = idDomanda;
    }

    public Integer getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(Integer idProgetto) {
        this.idProgetto = idProgetto;
    }

    public Integer getIdSoggettoBeneficiario() {
        return idSoggettoBeneficiario;
    }

    public void setIdSoggettoBeneficiario(Integer idSoggettoBeneficiario) {
        this.idSoggettoBeneficiario = idSoggettoBeneficiario;
    }

    public Integer getProgrSoggettoProgetto() {
        return progrSoggettoProgetto;
    }

    public void setProgrSoggettoProgetto(Integer progrSoggettoProgetto) {
        this.progrSoggettoProgetto = progrSoggettoProgetto;
    }

    public Integer getIdPersonaFisica() {
        return idPersonaFisica;
    }

    public void setIdPersonaFisica(Integer idPersonaFisica) {
        this.idPersonaFisica = idPersonaFisica;
    }

    public Integer getIdEstremiBancari() {
        return idEstremiBancari;
    }

    public void setIdEstremiBancari(Integer idEstremiBancari) {
        this.idEstremiBancari = idEstremiBancari;
    }

    public String getDenominazioneBeneficiario() {
        return denominazioneBeneficiario;
    }

    public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
        this.denominazioneBeneficiario = denominazioneBeneficiario;
    }

    public String getCodiceFiscaleBeneficiario() {
        return codiceFiscaleBeneficiario;
    }

    public void setCodiceFiscaleBeneficiario(String codiceFiscaleBeneficiario) {
        this.codiceFiscaleBeneficiario = codiceFiscaleBeneficiario;
    }

    @Override
    public String toString() {
        return "IbanBeneficiario{" +
                "idDomanda=" + idDomanda +
                ", idProgetto=" + idProgetto +
                ", idSoggettoBeneficiario=" + idSoggettoBeneficiario +
                ", progrSoggettoProgetto=" + progrSoggettoProgetto +
                ", idPersonaFisica=" + idPersonaFisica +
                ", idEstremiBancari=" + idEstremiBancari +
                ", denominazioneBeneficiario='" + denominazioneBeneficiario + '\'' +
                ", codiceFiscaleBeneficiario='" + codiceFiscaleBeneficiario + '\'' +
                '}';
    }
}
