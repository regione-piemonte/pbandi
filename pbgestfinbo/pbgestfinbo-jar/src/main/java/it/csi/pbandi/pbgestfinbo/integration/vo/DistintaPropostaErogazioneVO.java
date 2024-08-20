/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.util.Date;

public class DistintaPropostaErogazioneVO {

    //PBANDI_T_PROPOSTA_EROGAZIONE
    private Long idPropostaErogazione;
    private Long importoLordo;
    private Long importoIres;
    //importoLordo - importoIres
    private Long importoNetto;
    private Date dataEsitoDS;

    //PBANDI_T_PROGETTO
    private Long idProgetto;
    private String codiceVisualizzato;
    private String codiceFondoFinpisProgetto;
    private Date dataConcessione;

    //PBANDI_T_ENTE_GIURIDICO / PBANDI_T_PERSONA_FISICA
    private String denominazione;

    //PBANDI_T_SOGGETTO
    private Long idSoggetto;
    private String codiceFiscaleSoggetto;

    //PBANDI_R_SOGGETTO_PROGETTO
    private Long progSoggettoProgetto;
    private Long idEstremiBancari;

    //PBANDI_T_ESTREMI_BANCARI
    private String iban;

    //PBANDI_T_SEDE
    private Long idSede;
    private Long partitaIva;

    //Algoritmo 5.3
    private Boolean verificaPosizione;

    private String flagFinistr;

    private Boolean abilitata;

    public DistintaPropostaErogazioneVO() {
    }

    public Long getIdPropostaErogazione() {
        return idPropostaErogazione;
    }

    public void setIdPropostaErogazione(Long idPropostaErogazione) {
        this.idPropostaErogazione = idPropostaErogazione;
    }

    public Long getImportoLordo() {
        return importoLordo;
    }

    public void setImportoLordo(Long importoLordo) {
        this.importoLordo = importoLordo;
    }

    public Long getImportoIres() {
        return importoIres;
    }

    public void setImportoIres(Long importoIres) {
        this.importoIres = importoIres;
    }

    public Long getImportoNetto() {
        return importoNetto;
    }

    public void setImportoNetto(Long importoNetto) {
        this.importoNetto = importoNetto;
    }

    public Date getDataEsitoDS() {
        return dataEsitoDS;
    }

    public void setDataEsitoDS(Date dataEsitoDS) {
        this.dataEsitoDS = dataEsitoDS;
    }

    public Long getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(Long idProgetto) {
        this.idProgetto = idProgetto;
    }

    public String getCodiceVisualizzato() {
        return codiceVisualizzato;
    }

    public void setCodiceVisualizzato(String codiceVisualizzato) {
        this.codiceVisualizzato = codiceVisualizzato;
    }

    public String getCodiceFondoFinpisProgetto() {
        return codiceFondoFinpisProgetto;
    }

    public void setCodiceFondoFinpisProgetto(String codiceFondoFinpisProgetto) {
        this.codiceFondoFinpisProgetto = codiceFondoFinpisProgetto;
    }

    public Date getDataConcessione() {
        return dataConcessione;
    }

    public void setDataConcessione(Date dataConcessione) {
        this.dataConcessione = dataConcessione;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public Long getIdSoggetto() {
        return idSoggetto;
    }

    public void setIdSoggetto(Long idSoggetto) {
        this.idSoggetto = idSoggetto;
    }

    public String getCodiceFiscaleSoggetto() {
        return codiceFiscaleSoggetto;
    }

    public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
        this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
    }

    public Long getProgSoggettoProgetto() {
        return progSoggettoProgetto;
    }

    public void setProgSoggettoProgetto(Long progSoggettoProgetto) {
        this.progSoggettoProgetto = progSoggettoProgetto;
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

    public Long getIdSede() {
        return idSede;
    }

    public void setIdSede(Long idSede) {
        this.idSede = idSede;
    }

    public Long getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(Long partitaIva) {
        this.partitaIva = partitaIva;
    }

    public Boolean getVerificaPosizione() {
        return verificaPosizione;
    }

    public void setVerificaPosizione(Boolean verificaPosizione) {
        this.verificaPosizione = verificaPosizione;
    }

    public String getFlagFinistr() {
        return flagFinistr;
    }

    public void setFlagFinistr(String flagFinistr) {
        this.flagFinistr = flagFinistr;
    }

    public Boolean getAbilitata() {
        return abilitata;
    }

    public void setAbilitata(Boolean abilitata) {
        this.abilitata = abilitata;
    }
}
