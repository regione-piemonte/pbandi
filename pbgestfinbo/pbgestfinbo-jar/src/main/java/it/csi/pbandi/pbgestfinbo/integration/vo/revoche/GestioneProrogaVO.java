/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;

import java.math.BigDecimal;
import java.util.Date;

public class GestioneProrogaVO {

    private Long idProroga;
    private Long idStatoProroga;
    private String descStatoProroga;
    private Date dtRichiestaProroga;
    private Date dtEsitoRichiestaProroga;
    private BigDecimal numGiorniRichiestiProroga;
    private BigDecimal numGiorniApprovatiProroga;
    private String motivazioneProroga;
    private Date dtInserimentoProroga;

    public GestioneProrogaVO() {
    }

    public Long getIdProroga() {
        return idProroga;
    }

    public void setIdProroga(Long idProroga) {
        this.idProroga = idProroga;
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

    public Date getDtRichiestaProroga() {
        return dtRichiestaProroga;
    }

    public void setDtRichiestaProroga(Date dtRichiestaProroga) {
        this.dtRichiestaProroga = dtRichiestaProroga;
    }

    public Date getDtEsitoRichiestaProroga() {
        return dtEsitoRichiestaProroga;
    }

    public void setDtEsitoRichiestaProroga(Date dtEsitoRichiestaProroga) {
        this.dtEsitoRichiestaProroga = dtEsitoRichiestaProroga;
    }

    public BigDecimal getNumGiorniRichiestiProroga() {
        return numGiorniRichiestiProroga;
    }

    public void setNumGiorniRichiestiProroga(BigDecimal numGiorniRichiestiProroga) {
        this.numGiorniRichiestiProroga = numGiorniRichiestiProroga;
    }

    public BigDecimal getNumGiorniApprovatiProroga() {
        return numGiorniApprovatiProroga;
    }

    public void setNumGiorniApprovatiProroga(BigDecimal numGiorniApprovatiProroga) {
        this.numGiorniApprovatiProroga = numGiorniApprovatiProroga;
    }

    public String getMotivazioneProroga() {
        return motivazioneProroga;
    }

    public void setMotivazioneProroga(String motivazioneProroga) {
        this.motivazioneProroga = motivazioneProroga;
    }

    public Date getDtInserimentoProroga() {
        return dtInserimentoProroga;
    }

    public void setDtInserimentoProroga(Date dtInserimentoProroga) {
        this.dtInserimentoProroga = dtInserimentoProroga;
    }
}
