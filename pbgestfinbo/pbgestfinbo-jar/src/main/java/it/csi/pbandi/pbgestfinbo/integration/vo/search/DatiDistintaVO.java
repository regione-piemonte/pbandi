/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.util.List;

public class DatiDistintaVO {
    private Long idTipoDistinta;
    private Long idModalitaAgevolazione;
    private String descrizione;
    private Long idEstremiBancari;

    private List<Long> listaIdProposteErogazione;

    public DatiDistintaVO() {
    }

    public Long getIdTipoDistinta() {
        return idTipoDistinta;
    }

    public void setIdTipoDistinta(Long idTipoDistinta) {
        this.idTipoDistinta = idTipoDistinta;
    }

    public Long getIdModalitaAgevolazione() {
        return idModalitaAgevolazione;
    }

    public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
        this.idModalitaAgevolazione = idModalitaAgevolazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Long getIdEstremiBancari() {
        return idEstremiBancari;
    }

    public void setIdEstremiBancari(Long idEstremiBancari) {
        this.idEstremiBancari = idEstremiBancari;
    }

    public List<Long> getListaIdProposteErogazione() {
        return listaIdProposteErogazione;
    }

    public void setListaIdProposteErogazione(List<Long> listaIdProposteErogazione) {
        this.listaIdProposteErogazione = listaIdProposteErogazione;
    }
}
