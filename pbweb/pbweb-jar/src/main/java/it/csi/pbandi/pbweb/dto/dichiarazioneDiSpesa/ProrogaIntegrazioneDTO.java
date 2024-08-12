package it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa;

import java.util.Date;

public class ProrogaIntegrazioneDTO implements java.io.Serializable {

    static final long serialVersionUID = 1;

    private Long idRichiestaProroga = null;
    private Date dtRichiestaProroga = null;
    private Long numGiorniRichProroga = null;
    private String motivazioneProroga = null;
    private Long numGiorniApprovProroga = null;
    private Long idStatoProroga = null;
    private String descStatoProroga = null;
    private Long idRichiestaIntegrazione = null;
    private Date dtRichiestaIntegrazione = null;
    private Long idStatoRichiestaIntegrazione = null;
    private String descStatoRichiestaIntegrazione = null;

    public Long getIdRichiestaProroga() {
        return idRichiestaProroga;
    }

    public void setIdRichiestaProroga(Long idRichiestaProroga) {
        this.idRichiestaProroga = idRichiestaProroga;
    }

    public Date getDtRichiestaProroga() {
        return dtRichiestaProroga;
    }

    public void setDtRichiestaProroga(Date dtRichiestaProroga) {
        this.dtRichiestaProroga = dtRichiestaProroga;
    }

    public Long getNumGiorniRichProroga() {
        return numGiorniRichProroga;
    }

    public void setNumGiorniRichProroga(Long numGiorniRichProroga) {
        this.numGiorniRichProroga = numGiorniRichProroga;
    }

    public String getMotivazioneProroga() {
        return motivazioneProroga;
    }

    public void setMotivazioneProroga(String motivazioneProroga) {
        this.motivazioneProroga = motivazioneProroga;
    }

    public Long getNumGiorniApprovProroga() {
        return numGiorniApprovProroga;
    }

    public void setNumGiorniApprovProroga(Long numGiorniApprovProroga) {
        this.numGiorniApprovProroga = numGiorniApprovProroga;
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

    public Long getIdRichiestaIntegrazione() {
        return idRichiestaIntegrazione;
    }

    public void setIdRichiestaIntegrazione(Long idRichiestaIntegrazione) {
        this.idRichiestaIntegrazione = idRichiestaIntegrazione;
    }

    public Date getDtRichiestaIntegrazione() {
        return dtRichiestaIntegrazione;
    }

    public void setDtRichiestaIntegrazione(Date dtRichiestaIntegrazione) {
        this.dtRichiestaIntegrazione = dtRichiestaIntegrazione;
    }

    public Long getIdStatoRichiestaIntegrazione() {
        return idStatoRichiestaIntegrazione;
    }

    public void setIdStatoRichiestaIntegrazione(Long idStatoRichiestaIntegrazione) {
        this.idStatoRichiestaIntegrazione = idStatoRichiestaIntegrazione;
    }

    public String getDescStatoRichiestaIntegrazione() {
        return descStatoRichiestaIntegrazione;
    }

    public void setDescStatoRichiestaIntegrazione(String descStatoRichiestaIntegrazione) {
        this.descStatoRichiestaIntegrazione = descStatoRichiestaIntegrazione;
    }
}
