/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;

public class AllegatoVO {
    private Long idDocumentoIndex;
    private String name;
    private Long size;

    public AllegatoVO() {
    }

    public AllegatoVO(Long idDocumentoIndex, String name, Long size) {
        this.idDocumentoIndex = idDocumentoIndex;
        this.name = name;
        this.size = size;
    }

    public Long getIdDocumentoIndex() {
        return idDocumentoIndex;
    }

    public void setIdDocumentoIndex(Long idDocumentoIndex) {
        this.idDocumentoIndex = idDocumentoIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
