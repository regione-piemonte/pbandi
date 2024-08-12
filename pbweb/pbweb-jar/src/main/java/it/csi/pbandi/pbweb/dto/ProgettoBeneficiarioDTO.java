package it.csi.pbandi.pbweb.dto;

public class ProgettoBeneficiarioDTO implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private java.lang.String codice = null;

    private java.lang.String descrizione = null;

    private java.lang.String numeroDomanda = null;

    public java.lang.String getCodice() {
        return codice;
    }

    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }

    public java.lang.String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }

    public java.lang.String getNumeroDomanda() {
        return numeroDomanda;
    }

    public void setNumeroDomanda(java.lang.String numeroDomanda) {
        this.numeroDomanda = numeroDomanda;
    }

}
