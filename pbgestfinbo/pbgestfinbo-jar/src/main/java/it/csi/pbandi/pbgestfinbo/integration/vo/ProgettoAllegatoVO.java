/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

public class ProgettoAllegatoVO {
    private AllegatoVO letteraAccompagnatoria;
    private Boolean visibilitaLettera;
    private AllegatoVO checklistInterna;
    private Boolean visibilitaChecklist;
    private AllegatoVO reportValidazione;
    private Boolean visibilitaReport;

    private String flagFinistr;
    private String codiceVisualizzatoProgetto;
    private Long idProgetto;

    public AllegatoVO getLetteraAccompagnatoria() {
        return letteraAccompagnatoria;
    }

    public void setLetteraAccompagnatoria(AllegatoVO letteraAccompagnatoria) {
        this.letteraAccompagnatoria = letteraAccompagnatoria;
    }

    public AllegatoVO getChecklistInterna() {
        return checklistInterna;
    }

    public void setChecklistInterna(AllegatoVO checklistInterna) {
        this.checklistInterna = checklistInterna;
    }

    public AllegatoVO getReportValidazione() {
        return reportValidazione;
    }

    public void setReportValidazione(AllegatoVO reportValidazione) {
        this.reportValidazione = reportValidazione;
    }

    public String getFlagFinistr() {
        return flagFinistr;
    }

    public void setFlagFinistr(String flagFinistr) {
        this.flagFinistr = flagFinistr;
    }

    public String getCodiceVisualizzatoProgetto() {
        return codiceVisualizzatoProgetto;
    }

    public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
        this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
    }

    public Long getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(Long idProgetto) {
        this.idProgetto = idProgetto;
    }

    public Boolean getVisibilitaLettera() {
        return visibilitaLettera;
    }

    public void setVisibilitaLettera(Boolean visibilitaLettera) {
        this.visibilitaLettera = visibilitaLettera;
    }

    public Boolean getVisibilitaChecklist() {
        return visibilitaChecklist;
    }

    public void setVisibilitaChecklist(Boolean visibilitaChecklist) {
        this.visibilitaChecklist = visibilitaChecklist;
    }

    public Boolean getVisibilitaReport() {
        return visibilitaReport;
    }

    public void setVisibilitaReport(Boolean visibilitaReport) {
        this.visibilitaReport = visibilitaReport;
    }
}
