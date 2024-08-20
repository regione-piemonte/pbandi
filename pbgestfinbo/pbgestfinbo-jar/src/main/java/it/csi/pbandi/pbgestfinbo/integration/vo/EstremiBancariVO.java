/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

public class EstremiBancariVO {

    //PBANDI_T_ESTREMI_BANCARI
    private Long idEstremiBancari;
    private String iban;

    //PBANDI_R_BANDO_MOD_AG_ESTR_BAN
    private String codiceFondoFinpis;

    public EstremiBancariVO() {
    }

    public Long getIdEstremiBancari() {
        return idEstremiBancari;
    }

    public void setIdEstremiBancari(Long idEstremiBancari) {
        this.idEstremiBancari = idEstremiBancari;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getCodiceFondoFinpis() {
        return codiceFondoFinpis;
    }

    public void setCodiceFondoFinpis(String codiceFondoFinpis) {
        this.codiceFondoFinpis = codiceFondoFinpis;
    }
}
