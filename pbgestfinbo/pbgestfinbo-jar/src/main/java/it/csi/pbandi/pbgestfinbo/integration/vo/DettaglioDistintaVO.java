/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.util.ArrayList;
import java.util.List;

public class DettaglioDistintaVO {

    private Long idDistinta;
    private Boolean bozza;

    //PBANDI_T_BANDO
    private Long idBando;
    private String titoloBando;

    //PBANDI_D_MODALITA_AGEVOLAZIONE
    private Long idModalitaAgevolazione;
    private Long idModalitaAgevolazioneRif;
    private String descModalitaAgevolazione;

    //PBANDI_T_DISTINTA
    private String descDistinta;

    //estremi bancari selezionati per copia
    private EstremiBancariVO estremiBancariVO;

    //altre
    private List<EstremiBancariVO> estremiBancariList;
    private List <DistintaPropostaErogazioneVO> distintaPropostaErogazioneList;

    private List<ProgettoAllegatoVO> progettoAllegatoVOList;

    public DettaglioDistintaVO() {
    }

    public Boolean getBozza() {
        return bozza;
    }

    public void setBozza(Boolean bozza) {
        this.bozza = bozza;
    }

    public Long getIdDistinta() {
        return idDistinta;
    }

    public void setIdDistinta(Long idDistinta) {
        this.idDistinta = idDistinta;
    }

    public Long getIdBando() {
        return idBando;
    }

    public void setIdBando(Long idBando) {
        this.idBando = idBando;
    }

    public String getTitoloBando() {
        return titoloBando;
    }

    public void setTitoloBando(String titoloBando) {
        this.titoloBando = titoloBando;
    }

    public Long getIdModalitaAgevolazione() {
        return idModalitaAgevolazione;
    }

    public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
        this.idModalitaAgevolazione = idModalitaAgevolazione;
    }

    public Long getIdModalitaAgevolazioneRif() {
        return idModalitaAgevolazioneRif;
    }

    public void setIdModalitaAgevolazioneRif(Long idModalitaAgevolazioneRif) {
        this.idModalitaAgevolazioneRif = idModalitaAgevolazioneRif;
    }

    public String getDescModalitaAgevolazione() {
        return descModalitaAgevolazione;
    }

    public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
        this.descModalitaAgevolazione = descModalitaAgevolazione;
    }

    public String getDescDistinta() {
        return descDistinta;
    }

    public void setDescDistinta(String descDistinta) {
        this.descDistinta = descDistinta;
    }

    public List<EstremiBancariVO> getEstremiBancariList() {
        return estremiBancariList;
    }

    public void setEstremiBancariList(List<EstremiBancariVO> estremiBancariList) {
        this.estremiBancariList = estremiBancariList;
    }

    public EstremiBancariVO getEstremiBancariVO() {
        return estremiBancariVO;
    }

    public void setEstremiBancariVO(EstremiBancariVO estremiBancariVO) {
        this.estremiBancariVO = estremiBancariVO;
    }

    public List<DistintaPropostaErogazioneVO> getDistintaPropostaErogazioneList() {
        return distintaPropostaErogazioneList;
    }

    public void setDistintaPropostaErogazioneList(List<DistintaPropostaErogazioneVO> distintaPropostaErogazioneList) {
        this.distintaPropostaErogazioneList = distintaPropostaErogazioneList;
    }

    public List<ProgettoAllegatoVO> getProgettoAllegatoVOList() {
        return progettoAllegatoVOList;
    }

    public void setProgettoAllegatoVOList(List<ProgettoAllegatoVO> progettoAllegatoVOList) {
        this.progettoAllegatoVOList = progettoAllegatoVOList;
    }

    public void addProgettoAllegatoVO(ProgettoAllegatoVO progettoAllegatoVO) {
        if(this.progettoAllegatoVOList == null){
            this.progettoAllegatoVOList = new ArrayList<>();
        }

        progettoAllegatoVOList.add(progettoAllegatoVO);
    }

    public void addAllProgettoAllegatoVO(List<ProgettoAllegatoVO> progettoAllegatoVO) {
        if(this.progettoAllegatoVOList == null){
            this.progettoAllegatoVOList = new ArrayList<>();
        }

        progettoAllegatoVOList.addAll(progettoAllegatoVO);
    }
}
