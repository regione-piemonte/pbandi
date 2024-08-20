/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.controdeduzioni;

import java.util.Date;

public class RichiestaProrogaVO {
    private Long idRichiestaProroga;
    private Date dataRichiestaProroga;
    private String motivazioneProroga;
    private Long numGiorniRichiesti;
    private Long numGiorniApprovati;
    private Long idStatoProroga;
    private String descStatoProroga;

    public Long getIdRichiestaProroga() {
        return idRichiestaProroga;
    }

    public void setIdRichiestaProroga(Long idRichiestaProroga) {
        this.idRichiestaProroga = idRichiestaProroga;
    }

    public Date getDataRichiestaProroga() {
        return dataRichiestaProroga;
    }

    public void setDataRichiestaProroga(Date dataRichiestaProroga) {
        this.dataRichiestaProroga = dataRichiestaProroga;
    }

    public String getMotivazioneProroga() {
        return motivazioneProroga;
    }

    public void setMotivazioneProroga(String motivazioneProroga) {
        this.motivazioneProroga = motivazioneProroga;
    }

    public Long getNumGiorniRichiesti() {
        return numGiorniRichiesti;
    }

    public void setNumGiorniRichiesti(Long numGiorniRichiesti) {
        this.numGiorniRichiesti = numGiorniRichiesti;
    }

    public Long getNumGiorniApprovati() {
        return numGiorniApprovati;
    }

    public void setNumGiorniApprovati(Long numGiorniApprovati) {
        this.numGiorniApprovati = numGiorniApprovati;
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
