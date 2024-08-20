/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;

public class RicercaDistintaPropErogPlusVO {

    private Long idDistinta;
    private String descrizioneDistinta;
    private Long idTipoDistinta;
    private String descTipoDistinta;
    private String descBreveTipoDistinta;
    private String utenteCreazione;
    private Date dataCreazioneDistinta;
    private String descStatoDistinta;

    private String statoIterAutorizzativo;

    private Long idPropostaErogazione;

    private Long idProgetto;
    private String codiceVisualizzato;
    private String codiceFondoFinpis;

    private Long idSoggetto;
    private String denominazione;

    private String codiceFiscaleSoggetto;

    private Long idSede;
    private String partitaIva;

    private Long progrSoggettoProgetto;
    private Long idEstremiBancari;

    private String iban;
    private String codiceFondoFinpisBanca;

    private BigDecimal importoNetto;
    private BigDecimal importoLordo;

    private Date dataContabileErogazione;
    private BigDecimal importoErogato;

    public RicercaDistintaPropErogPlusVO() {
    }

    public Long getIdDistinta() {
        return idDistinta;
    }

    public void setIdDistinta(Long idDistinta) {
        this.idDistinta = idDistinta;
    }

    public String getDescrizioneDistinta() {
        return descrizioneDistinta;
    }

    public void setDescrizioneDistinta(String descrizioneDistinta) {
        this.descrizioneDistinta = descrizioneDistinta;
    }

    public Long getIdTipoDistinta() {
        return idTipoDistinta;
    }

    public void setIdTipoDistinta(Long idTipoDistinta) {
        this.idTipoDistinta = idTipoDistinta;
    }

    public String getDescTipoDistinta() {
        return descTipoDistinta;
    }

    public void setDescTipoDistinta(String descTipoDistinta) {
        this.descTipoDistinta = descTipoDistinta;
    }

    public String getDescBreveTipoDistinta() {
        return descBreveTipoDistinta;
    }

    public void setDescBreveTipoDistinta(String descBreveTipoDistinta) {
        this.descBreveTipoDistinta = descBreveTipoDistinta;
    }

    public String getUtenteCreazione() {
        return utenteCreazione;
    }

    public void setUtenteCreazione(String utenteCreazione) {
        this.utenteCreazione = utenteCreazione;
    }

    public Date getDataCreazioneDistinta() {
        return dataCreazioneDistinta;
    }

    public void setDataCreazioneDistinta(Date dataCreazioneDistinta) {
        this.dataCreazioneDistinta = dataCreazioneDistinta;
    }

    public String getDescStatoDistinta() {
        return descStatoDistinta;
    }

    public void setDescStatoDistinta(String descStatoDistinta) {
        this.descStatoDistinta = descStatoDistinta;
    }

    public String getStatoIterAutorizzativo() {
        return statoIterAutorizzativo;
    }

    public void setStatoIterAutorizzativo(String statoIterAutorizzativo) {
        this.statoIterAutorizzativo = statoIterAutorizzativo;
    }

    public Long getIdPropostaErogazione() {
        return idPropostaErogazione;
    }

    public void setIdPropostaErogazione(Long idPropostaErogazione) {
        this.idPropostaErogazione = idPropostaErogazione;
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

    public String getCodiceFondoFinpis() {
        return codiceFondoFinpis;
    }

    public void setCodiceFondoFinpis(String codiceFondoFinpis) {
        this.codiceFondoFinpis = codiceFondoFinpis;
    }

    public Long getIdSoggetto() {
        return idSoggetto;
    }

    public void setIdSoggetto(Long idSoggetto) {
        this.idSoggetto = idSoggetto;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getCodiceFiscaleSoggetto() {
        return codiceFiscaleSoggetto;
    }

    public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
        this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
    }

    public Long getIdSede() {
        return idSede;
    }

    public void setIdSede(Long idSede) {
        this.idSede = idSede;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public Long getProgrSoggettoProgetto() {
        return progrSoggettoProgetto;
    }

    public void setProgrSoggettoProgetto(Long progrSoggettoProgetto) {
        this.progrSoggettoProgetto = progrSoggettoProgetto;
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

    public String getCodiceFondoFinpisBanca() {
        return codiceFondoFinpisBanca;
    }

    public void setCodiceFondoFinpisBanca(String codiceFondoFinpisBanca) {
        this.codiceFondoFinpisBanca = codiceFondoFinpisBanca;
    }

    public BigDecimal getImportoNetto() {
        return importoNetto;
    }

    public void setImportoNetto(BigDecimal importoNetto) {
        this.importoNetto = importoNetto;
    }

    public BigDecimal getImportoLordo() {
        return importoLordo;
    }

    public void setImportoLordo(BigDecimal importoLordo) {
        this.importoLordo = importoLordo;
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
