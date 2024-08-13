/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.rendicontazione;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AllegatoDTO  implements java.io.Serializable {

    static final long serialVersionUID = 1;

    private Long idFileEntita = null;
    private Long idDocumentoIndex = null;
    private Long idFolder = null;
    private String nome = null;
    private String flagEntita = null; // I se lettera accompagnatoria
    private Long idEntita = null; //70 - quietanza, 123 - documento spesa, 63 - dichiarazione spesa
    private Long idTarget = null;
    private Long idIntegrazioneSpesa = null;

    public Long getIdFileEntita() {
        return idFileEntita;
    }

    public void setIdFileEntita(Long idFileEntita) {
        this.idFileEntita = idFileEntita;
    }

    public Long getIdDocumentoIndex() {
        return idDocumentoIndex;
    }

    public void setIdDocumentoIndex(Long idDocumentoIndex) {
        this.idDocumentoIndex = idDocumentoIndex;
    }

    public Long getIdFolder() {
        return idFolder;
    }

    public void setIdFolder(Long idFolder) {
        this.idFolder = idFolder;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getIdEntita() {
        return idEntita;
    }

    public void setIdEntita(Long idEntita) {
        this.idEntita = idEntita;
    }

    public Long getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(Long idTarget) {
        this.idTarget = idTarget;
    }

    public Long getIdIntegrazioneSpesa() {
        return idIntegrazioneSpesa;
    }

    public void setIdIntegrazioneSpesa(Long idIntegrazioneSpesa) {
        this.idIntegrazioneSpesa = idIntegrazioneSpesa;
    }

    public String getFlagEntita() {
        return flagEntita;
    }

    public void setFlagEntita(String flagEntita) {
        this.flagEntita = flagEntita;
    }
}
