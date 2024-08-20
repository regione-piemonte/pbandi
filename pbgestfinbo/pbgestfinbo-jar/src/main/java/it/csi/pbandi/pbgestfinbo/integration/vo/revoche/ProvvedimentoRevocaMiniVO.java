/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;

import java.util.Date;

public class ProvvedimentoRevocaMiniVO {

    private Long idProvvedimentoRevoca;
    private Long numeroProvvedimentoRevoca;

    private Long idSoggetto;
    private String codiceFiscaleSoggetto;
    private Long idBeneficiario;
    private String denominazioneBeneficiario;

    private Long idDomanda;
    private Long progrBandoLineaIntervento;
    private String nomeBandoLinea;

    private Long idProgetto;
    private String titoloProgetto;
    private String codiceVisualizzatoProgetto;

    private Long idCausaleBlocco;
    private String descCausaleBlocco;

    private Date dataProvvedimentoRevoca;

    private Date dataNotifica;

    private Long idStatoRevoca;
    private String descStatoRevoca;
    private Date dataStatoRevoca;

    private Long idAttivitaRevoca;
    private String descAttivitaRevoca;
    private Date dataAttivitaRevoca;

    public ProvvedimentoRevocaMiniVO() {
    }

    public Long getIdProvvedimentoRevoca() {
        return idProvvedimentoRevoca;
    }

    public void setIdProvvedimentoRevoca(Long idProvvedimentoRevoca) {
        this.idProvvedimentoRevoca = idProvvedimentoRevoca;
    }

    public Long getNumeroProvvedimentoRevoca() {
        return numeroProvvedimentoRevoca;
    }

    public void setNumeroProvvedimentoRevoca(Long numeroProvvedimentoRevoca) {
        this.numeroProvvedimentoRevoca = numeroProvvedimentoRevoca;
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

    public Long getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(Long idProgetto) {
        this.idProgetto = idProgetto;
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

    public Date getDataProvvedimentoRevoca() {
        return dataProvvedimentoRevoca;
    }

    public void setDataProvvedimentoRevoca(Date dataProvvedimentoRevoca) {
        this.dataProvvedimentoRevoca = dataProvvedimentoRevoca;
    }

    public Date getDataNotifica() {
        return dataNotifica;
    }

    public void setDataNotifica(Date dataNotifica) {
        this.dataNotifica = dataNotifica;
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

    public Long getIdAttivitaRevoca() {
        return idAttivitaRevoca;
    }

    public void setIdAttivitaRevoca(Long idAttivitaRevoca) {
        this.idAttivitaRevoca = idAttivitaRevoca;
    }

    public String getDescAttivitaRevoca() {
        return descAttivitaRevoca;
    }

    public void setDescAttivitaRevoca(String descAttivitaRevoca) {
        this.descAttivitaRevoca = descAttivitaRevoca;
    }

    public Date getDataAttivitaRevoca() {
        return dataAttivitaRevoca;
    }

    public void setDataAttivitaRevoca(Date dataAttivitaRevoca) {
        this.dataAttivitaRevoca = dataAttivitaRevoca;
    }
}
