/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * Soggetto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class Soggetto  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.EntitaBase  implements java.io.Serializable {
    private java.lang.String codiceFiscale;

    private java.lang.String codiceFiscaleEstero;

    private java.lang.String[] codiciSoggettiCollegatiPrecedenti;

    private java.lang.String[] codiciSoggettiCollegatiSuccessivi;

    private java.lang.String cognome;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Contatti contatti;

    private java.util.Calendar dataNascita;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ModalitaPagamento[] elencoModalitaPagamento;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Sede[] elencoSedi;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Recapito indirizzoPrincipale;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.NaturaGiuridica naturaGiuridica;

    private java.lang.String nome;

    private java.lang.String partitaIva;

    private java.lang.String ragioneSociale;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Sesso sesso;

    public Soggetto() {
    }

    public Soggetto(
           java.lang.String codice,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato,
           java.lang.String codiceFiscale,
           java.lang.String codiceFiscaleEstero,
           java.lang.String[] codiciSoggettiCollegatiPrecedenti,
           java.lang.String[] codiciSoggettiCollegatiSuccessivi,
           java.lang.String cognome,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Contatti contatti,
           java.util.Calendar dataNascita,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ModalitaPagamento[] elencoModalitaPagamento,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Sede[] elencoSedi,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Recapito indirizzoPrincipale,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.NaturaGiuridica naturaGiuridica,
           java.lang.String nome,
           java.lang.String partitaIva,
           java.lang.String ragioneSociale,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Sesso sesso) {
        super(
            codice,
            stato);
        this.codiceFiscale = codiceFiscale;
        this.codiceFiscaleEstero = codiceFiscaleEstero;
        this.codiciSoggettiCollegatiPrecedenti = codiciSoggettiCollegatiPrecedenti;
        this.codiciSoggettiCollegatiSuccessivi = codiciSoggettiCollegatiSuccessivi;
        this.cognome = cognome;
        this.contatti = contatti;
        this.dataNascita = dataNascita;
        this.elencoModalitaPagamento = elencoModalitaPagamento;
        this.elencoSedi = elencoSedi;
        this.indirizzoPrincipale = indirizzoPrincipale;
        this.naturaGiuridica = naturaGiuridica;
        this.nome = nome;
        this.partitaIva = partitaIva;
        this.ragioneSociale = ragioneSociale;
        this.sesso = sesso;
    }


    /**
     * Gets the codiceFiscale value for this Soggetto.
     * 
     * @return codiceFiscale
     */
    public java.lang.String getCodiceFiscale() {
        return codiceFiscale;
    }


    /**
     * Sets the codiceFiscale value for this Soggetto.
     * 
     * @param codiceFiscale
     */
    public void setCodiceFiscale(java.lang.String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }


    /**
     * Gets the codiceFiscaleEstero value for this Soggetto.
     * 
     * @return codiceFiscaleEstero
     */
    public java.lang.String getCodiceFiscaleEstero() {
        return codiceFiscaleEstero;
    }


    /**
     * Sets the codiceFiscaleEstero value for this Soggetto.
     * 
     * @param codiceFiscaleEstero
     */
    public void setCodiceFiscaleEstero(java.lang.String codiceFiscaleEstero) {
        this.codiceFiscaleEstero = codiceFiscaleEstero;
    }


    /**
     * Gets the codiciSoggettiCollegatiPrecedenti value for this Soggetto.
     * 
     * @return codiciSoggettiCollegatiPrecedenti
     */
    public java.lang.String[] getCodiciSoggettiCollegatiPrecedenti() {
        return codiciSoggettiCollegatiPrecedenti;
    }


    /**
     * Sets the codiciSoggettiCollegatiPrecedenti value for this Soggetto.
     * 
     * @param codiciSoggettiCollegatiPrecedenti
     */
    public void setCodiciSoggettiCollegatiPrecedenti(java.lang.String[] codiciSoggettiCollegatiPrecedenti) {
        this.codiciSoggettiCollegatiPrecedenti = codiciSoggettiCollegatiPrecedenti;
    }

    public java.lang.String getCodiciSoggettiCollegatiPrecedenti(int i) {
        return this.codiciSoggettiCollegatiPrecedenti[i];
    }

    public void setCodiciSoggettiCollegatiPrecedenti(int i, java.lang.String _value) {
        this.codiciSoggettiCollegatiPrecedenti[i] = _value;
    }


    /**
     * Gets the codiciSoggettiCollegatiSuccessivi value for this Soggetto.
     * 
     * @return codiciSoggettiCollegatiSuccessivi
     */
    public java.lang.String[] getCodiciSoggettiCollegatiSuccessivi() {
        return codiciSoggettiCollegatiSuccessivi;
    }


    /**
     * Sets the codiciSoggettiCollegatiSuccessivi value for this Soggetto.
     * 
     * @param codiciSoggettiCollegatiSuccessivi
     */
    public void setCodiciSoggettiCollegatiSuccessivi(java.lang.String[] codiciSoggettiCollegatiSuccessivi) {
        this.codiciSoggettiCollegatiSuccessivi = codiciSoggettiCollegatiSuccessivi;
    }

    public java.lang.String getCodiciSoggettiCollegatiSuccessivi(int i) {
        return this.codiciSoggettiCollegatiSuccessivi[i];
    }

    public void setCodiciSoggettiCollegatiSuccessivi(int i, java.lang.String _value) {
        this.codiciSoggettiCollegatiSuccessivi[i] = _value;
    }


    /**
     * Gets the cognome value for this Soggetto.
     * 
     * @return cognome
     */
    public java.lang.String getCognome() {
        return cognome;
    }


    /**
     * Sets the cognome value for this Soggetto.
     * 
     * @param cognome
     */
    public void setCognome(java.lang.String cognome) {
        this.cognome = cognome;
    }


    /**
     * Gets the contatti value for this Soggetto.
     * 
     * @return contatti
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Contatti getContatti() {
        return contatti;
    }


    /**
     * Sets the contatti value for this Soggetto.
     * 
     * @param contatti
     */
    public void setContatti(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Contatti contatti) {
        this.contatti = contatti;
    }


    /**
     * Gets the dataNascita value for this Soggetto.
     * 
     * @return dataNascita
     */
    public java.util.Calendar getDataNascita() {
        return dataNascita;
    }


    /**
     * Sets the dataNascita value for this Soggetto.
     * 
     * @param dataNascita
     */
    public void setDataNascita(java.util.Calendar dataNascita) {
        this.dataNascita = dataNascita;
    }


    /**
     * Gets the elencoModalitaPagamento value for this Soggetto.
     * 
     * @return elencoModalitaPagamento
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ModalitaPagamento[] getElencoModalitaPagamento() {
        return elencoModalitaPagamento;
    }


    /**
     * Sets the elencoModalitaPagamento value for this Soggetto.
     * 
     * @param elencoModalitaPagamento
     */
    public void setElencoModalitaPagamento(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ModalitaPagamento[] elencoModalitaPagamento) {
        this.elencoModalitaPagamento = elencoModalitaPagamento;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ModalitaPagamento getElencoModalitaPagamento(int i) {
        return this.elencoModalitaPagamento[i];
    }

    public void setElencoModalitaPagamento(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ModalitaPagamento _value) {
        this.elencoModalitaPagamento[i] = _value;
    }


    /**
     * Gets the elencoSedi value for this Soggetto.
     * 
     * @return elencoSedi
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Sede[] getElencoSedi() {
        return elencoSedi;
    }


    /**
     * Sets the elencoSedi value for this Soggetto.
     * 
     * @param elencoSedi
     */
    public void setElencoSedi(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Sede[] elencoSedi) {
        this.elencoSedi = elencoSedi;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Sede getElencoSedi(int i) {
        return this.elencoSedi[i];
    }

    public void setElencoSedi(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Sede _value) {
        this.elencoSedi[i] = _value;
    }


    /**
     * Gets the indirizzoPrincipale value for this Soggetto.
     * 
     * @return indirizzoPrincipale
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Recapito getIndirizzoPrincipale() {
        return indirizzoPrincipale;
    }


    /**
     * Sets the indirizzoPrincipale value for this Soggetto.
     * 
     * @param indirizzoPrincipale
     */
    public void setIndirizzoPrincipale(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Recapito indirizzoPrincipale) {
        this.indirizzoPrincipale = indirizzoPrincipale;
    }


    /**
     * Gets the naturaGiuridica value for this Soggetto.
     * 
     * @return naturaGiuridica
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.NaturaGiuridica getNaturaGiuridica() {
        return naturaGiuridica;
    }


    /**
     * Sets the naturaGiuridica value for this Soggetto.
     * 
     * @param naturaGiuridica
     */
    public void setNaturaGiuridica(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.NaturaGiuridica naturaGiuridica) {
        this.naturaGiuridica = naturaGiuridica;
    }


    /**
     * Gets the nome value for this Soggetto.
     * 
     * @return nome
     */
    public java.lang.String getNome() {
        return nome;
    }


    /**
     * Sets the nome value for this Soggetto.
     * 
     * @param nome
     */
    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }


    /**
     * Gets the partitaIva value for this Soggetto.
     * 
     * @return partitaIva
     */
    public java.lang.String getPartitaIva() {
        return partitaIva;
    }


    /**
     * Sets the partitaIva value for this Soggetto.
     * 
     * @param partitaIva
     */
    public void setPartitaIva(java.lang.String partitaIva) {
        this.partitaIva = partitaIva;
    }


    /**
     * Gets the ragioneSociale value for this Soggetto.
     * 
     * @return ragioneSociale
     */
    public java.lang.String getRagioneSociale() {
        return ragioneSociale;
    }


    /**
     * Sets the ragioneSociale value for this Soggetto.
     * 
     * @param ragioneSociale
     */
    public void setRagioneSociale(java.lang.String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }


    /**
     * Gets the sesso value for this Soggetto.
     * 
     * @return sesso
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Sesso getSesso() {
        return sesso;
    }


    /**
     * Sets the sesso value for this Soggetto.
     * 
     * @param sesso
     */
    public void setSesso(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Sesso sesso) {
        this.sesso = sesso;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Soggetto)) return false;
        Soggetto other = (Soggetto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codiceFiscale==null && other.getCodiceFiscale()==null) || 
             (this.codiceFiscale!=null &&
              this.codiceFiscale.equals(other.getCodiceFiscale()))) &&
            ((this.codiceFiscaleEstero==null && other.getCodiceFiscaleEstero()==null) || 
             (this.codiceFiscaleEstero!=null &&
              this.codiceFiscaleEstero.equals(other.getCodiceFiscaleEstero()))) &&
            ((this.codiciSoggettiCollegatiPrecedenti==null && other.getCodiciSoggettiCollegatiPrecedenti()==null) || 
             (this.codiciSoggettiCollegatiPrecedenti!=null &&
              java.util.Arrays.equals(this.codiciSoggettiCollegatiPrecedenti, other.getCodiciSoggettiCollegatiPrecedenti()))) &&
            ((this.codiciSoggettiCollegatiSuccessivi==null && other.getCodiciSoggettiCollegatiSuccessivi()==null) || 
             (this.codiciSoggettiCollegatiSuccessivi!=null &&
              java.util.Arrays.equals(this.codiciSoggettiCollegatiSuccessivi, other.getCodiciSoggettiCollegatiSuccessivi()))) &&
            ((this.cognome==null && other.getCognome()==null) || 
             (this.cognome!=null &&
              this.cognome.equals(other.getCognome()))) &&
            ((this.contatti==null && other.getContatti()==null) || 
             (this.contatti!=null &&
              this.contatti.equals(other.getContatti()))) &&
            ((this.dataNascita==null && other.getDataNascita()==null) || 
             (this.dataNascita!=null &&
              this.dataNascita.equals(other.getDataNascita()))) &&
            ((this.elencoModalitaPagamento==null && other.getElencoModalitaPagamento()==null) || 
             (this.elencoModalitaPagamento!=null &&
              java.util.Arrays.equals(this.elencoModalitaPagamento, other.getElencoModalitaPagamento()))) &&
            ((this.elencoSedi==null && other.getElencoSedi()==null) || 
             (this.elencoSedi!=null &&
              java.util.Arrays.equals(this.elencoSedi, other.getElencoSedi()))) &&
            ((this.indirizzoPrincipale==null && other.getIndirizzoPrincipale()==null) || 
             (this.indirizzoPrincipale!=null &&
              this.indirizzoPrincipale.equals(other.getIndirizzoPrincipale()))) &&
            ((this.naturaGiuridica==null && other.getNaturaGiuridica()==null) || 
             (this.naturaGiuridica!=null &&
              this.naturaGiuridica.equals(other.getNaturaGiuridica()))) &&
            ((this.nome==null && other.getNome()==null) || 
             (this.nome!=null &&
              this.nome.equals(other.getNome()))) &&
            ((this.partitaIva==null && other.getPartitaIva()==null) || 
             (this.partitaIva!=null &&
              this.partitaIva.equals(other.getPartitaIva()))) &&
            ((this.ragioneSociale==null && other.getRagioneSociale()==null) || 
             (this.ragioneSociale!=null &&
              this.ragioneSociale.equals(other.getRagioneSociale()))) &&
            ((this.sesso==null && other.getSesso()==null) || 
             (this.sesso!=null &&
              this.sesso.equals(other.getSesso())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getCodiceFiscale() != null) {
            _hashCode += getCodiceFiscale().hashCode();
        }
        if (getCodiceFiscaleEstero() != null) {
            _hashCode += getCodiceFiscaleEstero().hashCode();
        }
        if (getCodiciSoggettiCollegatiPrecedenti() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCodiciSoggettiCollegatiPrecedenti());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCodiciSoggettiCollegatiPrecedenti(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCodiciSoggettiCollegatiSuccessivi() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCodiciSoggettiCollegatiSuccessivi());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCodiciSoggettiCollegatiSuccessivi(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCognome() != null) {
            _hashCode += getCognome().hashCode();
        }
        if (getContatti() != null) {
            _hashCode += getContatti().hashCode();
        }
        if (getDataNascita() != null) {
            _hashCode += getDataNascita().hashCode();
        }
        if (getElencoModalitaPagamento() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getElencoModalitaPagamento());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getElencoModalitaPagamento(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getElencoSedi() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getElencoSedi());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getElencoSedi(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIndirizzoPrincipale() != null) {
            _hashCode += getIndirizzoPrincipale().hashCode();
        }
        if (getNaturaGiuridica() != null) {
            _hashCode += getNaturaGiuridica().hashCode();
        }
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
        }
        if (getPartitaIva() != null) {
            _hashCode += getPartitaIva().hashCode();
        }
        if (getRagioneSociale() != null) {
            _hashCode += getRagioneSociale().hashCode();
        }
        if (getSesso() != null) {
            _hashCode += getSesso().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Soggetto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "soggetto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFiscale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceFiscale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFiscaleEstero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceFiscaleEstero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiciSoggettiCollegatiPrecedenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiciSoggettiCollegatiPrecedenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiciSoggettiCollegatiSuccessivi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiciSoggettiCollegatiSuccessivi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cognome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cognome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contatti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contatti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "contatti"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataNascita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataNascita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("elencoModalitaPagamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "elencoModalitaPagamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "modalitaPagamento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("elencoSedi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "elencoSedi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "sede"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indirizzoPrincipale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "indirizzoPrincipale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "recapito"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("naturaGiuridica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "naturaGiuridica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "naturaGiuridica"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partitaIva");
        elemField.setXmlName(new javax.xml.namespace.QName("", "partitaIva"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ragioneSociale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ragioneSociale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sesso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sesso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "sesso"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
