/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.util.Date;
import java.util.List;

public class RicercaDistintaPropErogVO {

    private Long idDistinta;
    private String descrizioneDistinta;
    private Long idTipoDistinta;
    private String descTipoDistinta;
    private String descBreveTipoDistinta;
    private String utenteCreazione;
    private Date dataCreazioneDistinta;
    private Long idStatoDistinta;
    private String descStatoDistinta;

    private String statoIterAutorizzativo;

    private List<PropostaErogazioneExcelVO> propostaErogazioneExcelVOS;

    public RicercaDistintaPropErogVO() {
    }

    public Date getDataCreazioneDistinta() {
        return dataCreazioneDistinta;
    }

    public void setDataCreazioneDistinta(Date dataCreazioneDistinta) {
        this.dataCreazioneDistinta = dataCreazioneDistinta;
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

    public String getDescStatoDistinta() {
        return descStatoDistinta;
    }

    public Long getIdStatoDistinta() {
        return idStatoDistinta;
    }

    public void setIdStatoDistinta(Long idStatoDistinta) {
        this.idStatoDistinta = idStatoDistinta;
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

    public List<PropostaErogazioneExcelVO> getPropostaErogazioneExcelVOS() {
        return propostaErogazioneExcelVOS;
    }

    public void setPropostaErogazioneExcelVOS(List<PropostaErogazioneExcelVO> propostaErogazioneExcelVOS) {
        this.propostaErogazioneExcelVOS = propostaErogazioneExcelVOS;
    }
}
