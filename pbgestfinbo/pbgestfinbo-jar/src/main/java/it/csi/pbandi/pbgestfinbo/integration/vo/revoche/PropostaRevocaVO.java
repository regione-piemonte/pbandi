/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;


import java.util.Date;

public class PropostaRevocaVO {

    Long idPropostaRevoca;
    Long numeroPropostaRevoca;

    Long idSoggetto;
    String codiceFiscaleSoggetto;
    Long idBeneficiario; //id_ente_giuridico / id_persona_fisica
    String denominazioneBeneficiario; //denominazione_ente_giuridico / nome+cognome_persona_fisica

    Long idDomanda;
    Long progrBandoLineaIntervento;
    String nomeBandoLinea;

    String titoloProgetto;
    String codiceVisualizzatoProgetto;

    Long idCausaleBlocco;
    String descCausaleBlocco;

    Date dataPropostaRevoca;

    Long idStatoRevoca;
    String descStatoRevoca;

    Date dataStatoRevoca;

    public PropostaRevocaVO() {
    }

    public Long getIdPropostaRevoca() {
        return idPropostaRevoca;
    }

    public void setIdPropostaRevoca(Long idPropostaRevoca) {
        this.idPropostaRevoca = idPropostaRevoca;
    }

    public Long getNumeroPropostaRevoca() {
        return numeroPropostaRevoca;
    }

    public void setNumeroPropostaRevoca(Long numeroPropostaRevoca) {
        this.numeroPropostaRevoca = numeroPropostaRevoca;
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

    public Long getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public String getDenominazioneBeneficiario() {
        return denominazioneBeneficiario;
    }

    public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
        this.denominazioneBeneficiario = denominazioneBeneficiario;
    }

    public Long getIdDomanda() {
        return idDomanda;
    }

    public void setIdDomanda(Long idDomanda) {
        this.idDomanda = idDomanda;
    }

    public Long getProgrBandoLineaIntervento() {
        return progrBandoLineaIntervento;
    }

    public void setProgrBandoLineaIntervento(Long progrBandoLineaIntervento) {
        this.progrBandoLineaIntervento = progrBandoLineaIntervento;
    }

    public String getNomeBandoLinea() {
        return nomeBandoLinea;
    }

    public void setNomeBandoLinea(String nomeBandoLinea) {
        this.nomeBandoLinea = nomeBandoLinea;
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

    public Long getIdCausaleBlocco() {
        return idCausaleBlocco;
    }

    public void setIdCausaleBlocco(Long idCausaleBlocco) {
        this.idCausaleBlocco = idCausaleBlocco;
    }

    public String getDescCausaleBlocco() {
        return descCausaleBlocco;
    }

    public void setDescCausaleBlocco(String descCausaleBlocco) {
        this.descCausaleBlocco = descCausaleBlocco;
    }

    public Date getDataPropostaRevoca() {
        return dataPropostaRevoca;
    }

    public void setDataPropostaRevoca(Date dataPropostaRevoca) {
        this.dataPropostaRevoca = dataPropostaRevoca;
    }

    public Long getIdStatoRevoca() {
        return idStatoRevoca;
    }

    public void setIdStatoRevoca(Long idStatoRevoca) {
        this.idStatoRevoca = idStatoRevoca;
    }

    public String getDescStatoRevoca() {
        return descStatoRevoca;
    }

    public void setDescStatoRevoca(String descStatoRevoca) {
        this.descStatoRevoca = descStatoRevoca;
    }

    public Date getDataStatoRevoca() {
        return dataStatoRevoca;
    }

    public void setDataStatoRevoca(Date dataStatoRevoca) {
        this.dataStatoRevoca = dataStatoRevoca;
    }
}
