/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class DatiPreErogazioneVO {

    private String titoloBando;
    private String codiceProgetto;
    private Long idProgetto;
    private Long idModalitaAgevolazioneRif;
    private String descrizioneModalitaAgevolazione;
    private String beneficiario;
    private Date dataControlli;
    private String flagPubblicoPrivato;
    private Long idSoggetto;
    private String numeroDomanda;
    private Long idProposta;
    private BigDecimal importoBeneficiario;
    private BigDecimal importoLordo;
    private String ibanBeneficiario;
    private String codiceFiscale;
    private Long codiceBando;

    private Boolean verificaAntimafia;

    private Boolean iterEsitoValidazioneConcluso;
    private Boolean iterEsitoValidazioneInCorso;
    private Boolean iterCommunicazioneInterventoConcluso;
    private Boolean iterCommunicazioneInterventoInCorso;

    private Boolean distintaCreata;

    private List<InterventoSostitutivoVO> listaInterventi;

    private Boolean flagFinistr;

    public DatiPreErogazioneVO() {
    }

    public String getTitoloBando() {
        return titoloBando;
    }

    public void setTitoloBando(String titoloBando) {
        this.titoloBando = titoloBando;
    }

    public String getCodiceProgetto() {
        return codiceProgetto;
    }

    public void setCodiceProgetto(String codiceProgetto) {
        this.codiceProgetto = codiceProgetto;
    }

    public Long getIdModalitaAgevolazioneRif() {
        return idModalitaAgevolazioneRif;
    }

    public void setIdModalitaAgevolazioneRif(Long idModalitaAgevolazioneRif) {
        this.idModalitaAgevolazioneRif = idModalitaAgevolazioneRif;
    }

    public String getDescrizioneModalitaAgevolazione() {
        return descrizioneModalitaAgevolazione;
    }

    public void setDescrizioneModalitaAgevolazione(String descrizioneModalitaAgevolazione) {
        this.descrizioneModalitaAgevolazione = descrizioneModalitaAgevolazione;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public Date getDataControlli() {
        return dataControlli;
    }

    public void setDataControlli(Date dataControlli) {
        this.dataControlli = dataControlli;
    }

    public String getFlagPubblicoPrivato() {
        return flagPubblicoPrivato;
    }

    public void setFlagPubblicoPrivato(String flagPubblicoPrivato) {
        this.flagPubblicoPrivato = flagPubblicoPrivato;
    }

    public Long getIdSoggetto() {
        return idSoggetto;
    }

    public void setIdSoggetto(Long idSoggetto) {
        this.idSoggetto = idSoggetto;
    }

    public String getNumeroDomanda() {
        return numeroDomanda;
    }

    public void setNumeroDomanda(String numeroDomanda) {
        this.numeroDomanda = numeroDomanda;
    }

    public Long getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(Long idProposta) {
        this.idProposta = idProposta;
    }

    public BigDecimal getImportoBeneficiario() {
        return importoBeneficiario;
    }

    public void setImportoBeneficiario(BigDecimal importoBeneficiario) {
        this.importoBeneficiario = importoBeneficiario;
    }

    public BigDecimal getImportoLordo() {
        return importoLordo;
    }

    public void setImportoLordo(BigDecimal importoLordo) {
        this.importoLordo = importoLordo;
    }

    public String getIbanBeneficiario() {
        return ibanBeneficiario;
    }

    public void setIbanBeneficiario(String ibanBeneficiario) {
        this.ibanBeneficiario = ibanBeneficiario;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public Long getCodiceBando() {
        return codiceBando;
    }

    public void setCodiceBando(Long codiceBando) {
        this.codiceBando = codiceBando;
    }

    public Long getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(Long idProgetto) {
        this.idProgetto = idProgetto;
    }

    public Boolean getVerificaAntimafia() {
        return verificaAntimafia;
    }

    public void setVerificaAntimafia(Boolean verificaAntimafia) {
        this.verificaAntimafia = verificaAntimafia;
    }

    public Boolean getIterEsitoValidazioneConcluso() {
        return iterEsitoValidazioneConcluso;
    }

    public void setIterEsitoValidazioneConcluso(Boolean iterEsitoValidazioneConcluso) {
        this.iterEsitoValidazioneConcluso = iterEsitoValidazioneConcluso;
    }

    public Boolean getIterEsitoValidazioneInCorso() {
        return iterEsitoValidazioneInCorso;
    }

    public void setIterEsitoValidazioneInCorso(Boolean iterEsitoValidazioneInCorso) {
        this.iterEsitoValidazioneInCorso = iterEsitoValidazioneInCorso;
    }

    public Boolean getIterCommunicazioneInterventoConcluso() {
        return iterCommunicazioneInterventoConcluso;
    }

    public void setIterCommunicazioneInterventoConcluso(Boolean iterCommunicazioneInterventoConcluso) {
        this.iterCommunicazioneInterventoConcluso = iterCommunicazioneInterventoConcluso;
    }

    public Boolean getIterCommunicazioneInterventoInCorso() {
        return iterCommunicazioneInterventoInCorso;
    }

    public void setIterCommunicazioneInterventoInCorso(Boolean iterCommunicazioneInterventoInCorso) {
        this.iterCommunicazioneInterventoInCorso = iterCommunicazioneInterventoInCorso;
    }

    public Boolean getDistintaCreata() {
        return distintaCreata;
    }

    public void setDistintaCreata(Boolean distintaCreata) {
        this.distintaCreata = distintaCreata;
    }

    public List<InterventoSostitutivoVO> getListaInterventi() {
        return listaInterventi;
    }

    public void setListaInterventi(List<InterventoSostitutivoVO> listaInterventi) {
        this.listaInterventi = listaInterventi;
    }

    public Boolean getFlagFinistr() {
        return flagFinistr;
    }

    public void setFlagFinistr(Boolean flagFinistr) {
        this.flagFinistr = flagFinistr;
    }
}
