/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.math.BigDecimal;
import java.util.List;

public class RichiestaIntegrazioneVO {
    private Long idGestioneRevoca;
    private BigDecimal numGiorniScadenza;
    private MultipartFormDataInput letteraAccompagnatoria;
    private List<MultipartFormDataInput> altriAllegati;

    public RichiestaIntegrazioneVO() {
    }

    public Long getIdGestioneRevoca() {
        return idGestioneRevoca;
    }

    public void setIdGestioneRevoca(Long idGestioneRevoca) {
        this.idGestioneRevoca = idGestioneRevoca;
    }

    public BigDecimal getNumGiorniScadenza() {
        return numGiorniScadenza;
    }

    public void setNumGiorniScadenza(BigDecimal numGiorniScadenza) {
        this.numGiorniScadenza = numGiorniScadenza;
    }

    public MultipartFormDataInput getLetteraAccompagnatoria() {
        return letteraAccompagnatoria;
    }

    public void setLetteraAccompagnatoria(MultipartFormDataInput letteraAccompagnatoria) {
        this.letteraAccompagnatoria = letteraAccompagnatoria;
    }

    public List<MultipartFormDataInput> getAltriAllegati() {
        return altriAllegati;
    }

    public void setAltriAllegati(List<MultipartFormDataInput> altriAllegati) {
        this.altriAllegati = altriAllegati;
    }
}
