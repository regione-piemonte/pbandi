/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class DatiIntegrazioneDsDTO implements Serializable {

    private Long idIntegrazioneSpesa;
    private String dataRichiesta;
    private String dataInvio;
    private String descrizione;
    private ArrayList<DocumentoAllegatoDTO> allegati;

    public String getDataInvio() {
        return dataInvio;
    }

    public void setDataInvio(String dataInvio) {
        this.dataInvio = dataInvio;
    }

    public Long getIdIntegrazioneSpesa() {
        return idIntegrazioneSpesa;
    }

    public void setIdIntegrazioneSpesa(Long idIntegrazioneSpesa) {
        this.idIntegrazioneSpesa = idIntegrazioneSpesa;
    }

    public String getDataRichiesta() {
        return dataRichiesta;
    }

    public void setDataRichiesta(String dataRichiesta) {
        this.dataRichiesta = dataRichiesta;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public ArrayList<DocumentoAllegatoDTO> getAllegati() {
        return allegati;
    }

    public void setAllegati(ArrayList<DocumentoAllegatoDTO> allegati) {
        this.allegati = allegati;
    }

}
