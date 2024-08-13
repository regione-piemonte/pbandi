/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione;

public class RegoleAllegatiIntegrazioneDTO implements java.io.Serializable {

    static final long serialVersionUID = 1;

    private java.lang.Boolean allegatiAmmessiDocumentoSpesa = null;
    private java.lang.Boolean allegatiAmmessiQuietanze = null;
    private java.lang.Boolean allegatiAmmessiGenerici = null;
    private java.lang.Boolean allegatiAmmessi = null;


    public Boolean getAllegatiAmmessiDocumentoSpesa() {
        return allegatiAmmessiDocumentoSpesa;
    }

    public void setAllegatiAmmessiDocumentoSpesa(Boolean allegatiAmmessiDocumentoSpesa) {
        this.allegatiAmmessiDocumentoSpesa = allegatiAmmessiDocumentoSpesa;
    }

    public Boolean getAllegatiAmmessiQuietanze() {
        return allegatiAmmessiQuietanze;
    }

    public void setAllegatiAmmessiQuietanze(Boolean allegatiAmmessiQuietanze) {
        this.allegatiAmmessiQuietanze = allegatiAmmessiQuietanze;
    }

    public Boolean getAllegatiAmmessiGenerici() {
        return allegatiAmmessiGenerici;
    }

    public void setAllegatiAmmessiGenerici(Boolean allegatiAmmessiGenerici) {
        this.allegatiAmmessiGenerici = allegatiAmmessiGenerici;
    }

    public Boolean getAllegatiAmmessi() {
        return allegatiAmmessi;
    }

    public void setAllegatiAmmessi(Boolean allegatiAmmessi) {
        this.allegatiAmmessi = allegatiAmmessi;
    }
}
