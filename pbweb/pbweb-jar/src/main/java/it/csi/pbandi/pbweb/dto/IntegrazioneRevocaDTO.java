package it.csi.pbandi.pbweb.dto;

public class IntegrazioneRevocaDTO implements java.io.Serializable {

    static final long serialVersionUID = 1;

    private java.lang.Long nRevoca = null;
    private java.lang.Long idRichIntegrazione = null;
    private java.lang.String dataRichiesta = null;
    private java.lang.String dataNotifica = null;
    private java.lang.String dataScadenza = null;
    private java.lang.String dataInvio = null;
    private java.lang.String idStatoRichiesta = null;
    private java.lang.String statoRichiesta = null;
    private java.lang.String longStatoRichiesta = null;
    private java.lang.Boolean allegatiInseriti = null;

    private java.lang.String dtRichiesta = null;
    private java.lang.Long ggRichiesti = null;
    private java.lang.String motivazione = null;
    private java.lang.Long ggApprovati = null;

    private Long idStatoProroga = null;
    private String statoProroga = null;

    public String getDtRichiesta() {
        return dtRichiesta;
    }

    public void setDtRichiesta(String dtRichiesta) {
        this.dtRichiesta = dtRichiesta;
    }

    public java.lang.Long getIdRichIntegrazione() {
        return idRichIntegrazione;
    }

    public void setIdRichIntegrazione(java.lang.Long idRichIntegrazione) {
        this.idRichIntegrazione = idRichIntegrazione;
    }

    public Long getGgApprovati() {
        return ggApprovati;
    }

    public void setGgApprovati(Long ggApprovati) {
        this.ggApprovati = ggApprovati;
    }

    public java.lang.String getIdStatoRichiesta() {
        return idStatoRichiesta;
    }

    public void setIdStatoRichiesta(java.lang.String idStatoRichiesta) {
        this.idStatoRichiesta = idStatoRichiesta;
    }

    public java.lang.String getLongStatoRichiesta() {
        return longStatoRichiesta;
    }

    public void setLongStatoRichiesta(java.lang.String longStatoRichiesta) {
        this.longStatoRichiesta = longStatoRichiesta;
    }

    public Long getGgRichiesti() {
        return ggRichiesti;
    }

    public void setGgRichiesti(Long ggRichiesti) {
        this.ggRichiesti = ggRichiesti;
    }

    public String getMotivazione() {
        return motivazione;
    }

    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }

    public String getStatoProroga() {
        return statoProroga;
    }

    public void setStatoProroga(String statoProroga) {
        this.statoProroga = statoProroga;
    }

    public Boolean getAllegatiInseriti() {
        return allegatiInseriti;
    }

    public void setAllegatiInseriti(Boolean allegatiInseriti) {
        this.allegatiInseriti = allegatiInseriti;
    }

    public java.lang.Long getnRevoca() {
        return nRevoca;
    }

    public void setnRevoca(java.lang.Long nRevoca) {
        this.nRevoca = nRevoca;
    }

    public java.lang.String getDataRichiesta() {
        return dataRichiesta;
    }

    public void setDataRichiesta(java.lang.String dataRichiesta) {
        this.dataRichiesta = dataRichiesta;
    }

    public java.lang.String getDataNotifica() {
        return dataNotifica;
    }

    public void setDataNotifica(java.lang.String dataNotifica) {
        this.dataNotifica = dataNotifica;
    }

    public java.lang.String getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(java.lang.String dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public java.lang.String getDataInvio() {
        return dataInvio;
    }

    public void setDataInvio(java.lang.String dataInvio) {
        this.dataInvio = dataInvio;
    }

    public Long getIdStatoProroga() {
        return idStatoProroga;
    }

    public void setIdStatoProroga(Long idStatoProroga) {
        this.idStatoProroga = idStatoProroga;
    }

    public java.lang.String getStatoRichiesta() {
        return statoRichiesta;
    }

    public void setStatoRichiesta(java.lang.String statoRichiesta) {
        this.statoRichiesta = statoRichiesta;
    }


}
