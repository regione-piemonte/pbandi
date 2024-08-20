/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.controdeduzioni;

import java.util.Date;

public class ControdeduzioneVO {
    private Long idGestioneRevoca;
    private Long numeroRevoca;
    private Date dataNotifica;
    private String numeroProtocollo;
    private String causaRevoca;

    private Long idControdeduzione;
    private Long numeroControdeduzione;
    private Long idStatoControdeduzione;
    private String descStatoControdeduzione;
    private Date dtStatoControdeduzione;
    private Long idAttivitaControdeduzione;
    private String descAttivitaControdeduzione;
    private Date dtAttivitaControdeduzione;
    private Date dtScadenzaControdeduzione; //DT_NOTIFICA + GG_RISPOSTA + GG_PROROGHE

    private Boolean isAbilitatoIntegra; //Stato Controdeduzione = aperta
    private Boolean isAbilitatoInvia; //Stato Controdeduzione = aperta | Attività != richiesta proroga | associata la lettera
    private Boolean isAbilitatoElimina; //Stato Controdeduzione = aperta
    private Boolean isAbilitatoAtti;
    private Boolean isAbilitatoControdeduz; // idControdeduzione = null

    private Long idStatoProroga; //Stato dell'ultima proroga
    private String descStatoProroga;

    public Long getIdGestioneRevoca() {
        return idGestioneRevoca;
    }

    public void setIdGestioneRevoca(Long idGestioneRevoca) {
        this.idGestioneRevoca = idGestioneRevoca;
    }

    public Long getNumeroRevoca() {
        return numeroRevoca;
    }

    public void setNumeroRevoca(Long numeroRevoca) {
        this.numeroRevoca = numeroRevoca;
    }

    public Date getDataNotifica() {
        return dataNotifica;
    }

    public void setDataNotifica(Date dataNotifica) {
        this.dataNotifica = dataNotifica;
    }

    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {
        this.numeroProtocollo = numeroProtocollo;
    }

    public String getCausaRevoca() {
        return causaRevoca;
    }

    public void setCausaRevoca(String causaRevoca) {
        this.causaRevoca = causaRevoca;
    }

    public Long getIdControdeduzione() {
        return idControdeduzione;
    }

    public void setIdControdeduzione(Long idControdeduzione) {
        this.idControdeduzione = idControdeduzione;
    }

    public Long getNumeroControdeduzione() {
        return numeroControdeduzione;
    }

    public void setNumeroControdeduzione(Long numeroControdeduzione) {
        this.numeroControdeduzione = numeroControdeduzione;
    }

    public Long getIdStatoControdeduzione() {
        return idStatoControdeduzione;
    }

    public void setIdStatoControdeduzione(Long idStatoControdeduzione) {
        this.idStatoControdeduzione = idStatoControdeduzione;
    }

    public String getDescStatoControdeduzione() {
        return descStatoControdeduzione;
    }

    public void setDescStatoControdeduzione(String descStatoControdeduzione) {
        this.descStatoControdeduzione = descStatoControdeduzione;
    }

    public Date getDtStatoControdeduzione() {
        return dtStatoControdeduzione;
    }

    public void setDtStatoControdeduzione(Date dtStatoControdeduzione) {
        this.dtStatoControdeduzione = dtStatoControdeduzione;
    }

    public Long getIdAttivitaControdeduzione() {
        return idAttivitaControdeduzione;
    }

    public void setIdAttivitaControdeduzione(Long idAttivitaControdeduzione) {
        this.idAttivitaControdeduzione = idAttivitaControdeduzione;
    }

    public String getDescAttivitaControdeduzione() {
        return descAttivitaControdeduzione;
    }

    public void setDescAttivitaControdeduzione(String descAttivitaControdeduzione) {
        this.descAttivitaControdeduzione = descAttivitaControdeduzione;
    }

    public Date getDtAttivitaControdeduzione() {
        return dtAttivitaControdeduzione;
    }

    public void setDtAttivitaControdeduzione(Date dtAttivitaControdeduzione) {
        this.dtAttivitaControdeduzione = dtAttivitaControdeduzione;
    }

    public Date getDtScadenzaControdeduzione() {
        return dtScadenzaControdeduzione;
    }

    public void setDtScadenzaControdeduzione(Date dtScadenzaControdeduzione) {
        this.dtScadenzaControdeduzione = dtScadenzaControdeduzione;
    }

    public Boolean getAbilitatoIntegra() {
        return isAbilitatoIntegra;
    }

    public void setAbilitatoIntegra(Boolean abilitatoIntegra) {
        isAbilitatoIntegra = abilitatoIntegra;
    }

    public Boolean getAbilitatoInvia() {
        return isAbilitatoInvia;
    }

    public void setAbilitatoInvia(Boolean abilitatoInvia) {
        isAbilitatoInvia = abilitatoInvia;
    }

    public Boolean getAbilitatoElimina() {
        return isAbilitatoElimina;
    }

    public void setAbilitatoElimina(Boolean abilitatoElimina) {
        isAbilitatoElimina = abilitatoElimina;
    }

    public Boolean getAbilitatoAtti() {
        return isAbilitatoAtti;
    }

    public void setAbilitatoAtti(Boolean abilitatoAtti) {
        isAbilitatoAtti = abilitatoAtti;
    }

    public Boolean getAbilitatoControdeduz() {
        return isAbilitatoControdeduz;
    }

    public void setAbilitatoControdeduz(Boolean abilitatoControdeduz) {
        isAbilitatoControdeduz = abilitatoControdeduz;
    }

    public Long getIdStatoProroga() {
        return idStatoProroga;
    }

    public void setIdStatoProroga(Long idStatoProroga) {
        this.idStatoProroga = idStatoProroga;
    }

    public String getDescStatoProroga() {
        return descStatoProroga;
    }

    public void setDescStatoProroga(String descStatoProroga) {
        this.descStatoProroga = descStatoProroga;
    }
}
