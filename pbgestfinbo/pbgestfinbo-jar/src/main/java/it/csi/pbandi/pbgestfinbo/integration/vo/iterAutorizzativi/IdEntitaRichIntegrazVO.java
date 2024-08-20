/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.iterAutorizzativi;

public class IdEntitaRichIntegrazVO {
    private Long idEntitaRichiestaIntegraz;
    private Long idRichiestaIntegraz;
    private Long idEntitaGestioneRevoca;
    private Long idGestioneRevoca;

    public Long getIdEntitaRichiestaIntegraz() {
        return idEntitaRichiestaIntegraz;
    }

    public void setIdEntitaRichiestaIntegraz(Long idEntitaRichiestaIntegraz) {
        this.idEntitaRichiestaIntegraz = idEntitaRichiestaIntegraz;
    }

    public Long getIdRichiestaIntegraz() {
        return idRichiestaIntegraz;
    }

    public void setIdRichiestaIntegraz(Long idRichiestaIntegraz) {
        this.idRichiestaIntegraz = idRichiestaIntegraz;
    }

    public Long getIdEntitaGestioneRevoca() {
        return idEntitaGestioneRevoca;
    }

    public void setIdEntitaGestioneRevoca(Long idEntitaGestioneRevoca) {
        this.idEntitaGestioneRevoca = idEntitaGestioneRevoca;
    }

    public Long getIdGestioneRevoca() {
        return idGestioneRevoca;
    }

    public void setIdGestioneRevoca(Long idGestioneRevoca) {
        this.idGestioneRevoca = idGestioneRevoca;
    }
}
