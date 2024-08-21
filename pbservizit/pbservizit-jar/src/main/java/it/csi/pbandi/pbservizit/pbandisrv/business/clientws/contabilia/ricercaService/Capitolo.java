/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * Capitolo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class Capitolo  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.EntitaBase  implements java.io.Serializable {
    private java.lang.Integer annoEsercizio;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ClassificatoreGenerico[] classificatoriGenerici;

    private java.lang.String descrizione;

    private java.lang.String descrizioneArticolo;

    private java.lang.Integer numeroArticolo;

    private java.lang.Integer numeroCapitolo;

    private java.lang.Integer numeroUEB;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.PianoDeiContiFinanziario pianoDeiContiFinanziario;

    private java.lang.Boolean rilevanteIva;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.StrutturaAmministrativa sac;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoFinanziamento tipoFinanziamento;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoFondo tipoFondo;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Titolo titolo;

    public Capitolo() {
    }

    public Capitolo(
           java.lang.String codice,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato,
           java.lang.Integer annoEsercizio,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ClassificatoreGenerico[] classificatoriGenerici,
           java.lang.String descrizione,
           java.lang.String descrizioneArticolo,
           java.lang.Integer numeroArticolo,
           java.lang.Integer numeroCapitolo,
           java.lang.Integer numeroUEB,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.PianoDeiContiFinanziario pianoDeiContiFinanziario,
           java.lang.Boolean rilevanteIva,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.StrutturaAmministrativa sac,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoFinanziamento tipoFinanziamento,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoFondo tipoFondo,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Titolo titolo) {
        super(
            codice,
            stato);
        this.annoEsercizio = annoEsercizio;
        this.classificatoriGenerici = classificatoriGenerici;
        this.descrizione = descrizione;
        this.descrizioneArticolo = descrizioneArticolo;
        this.numeroArticolo = numeroArticolo;
        this.numeroCapitolo = numeroCapitolo;
        this.numeroUEB = numeroUEB;
        this.pianoDeiContiFinanziario = pianoDeiContiFinanziario;
        this.rilevanteIva = rilevanteIva;
        this.sac = sac;
        this.tipoFinanziamento = tipoFinanziamento;
        this.tipoFondo = tipoFondo;
        this.titolo = titolo;
    }


    /**
     * Gets the annoEsercizio value for this Capitolo.
     * 
     * @return annoEsercizio
     */
    public java.lang.Integer getAnnoEsercizio() {
        return annoEsercizio;
    }


    /**
     * Sets the annoEsercizio value for this Capitolo.
     * 
     * @param annoEsercizio
     */
    public void setAnnoEsercizio(java.lang.Integer annoEsercizio) {
        this.annoEsercizio = annoEsercizio;
    }


    /**
     * Gets the classificatoriGenerici value for this Capitolo.
     * 
     * @return classificatoriGenerici
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ClassificatoreGenerico[] getClassificatoriGenerici() {
        return classificatoriGenerici;
    }


    /**
     * Sets the classificatoriGenerici value for this Capitolo.
     * 
     * @param classificatoriGenerici
     */
    public void setClassificatoriGenerici(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ClassificatoreGenerico[] classificatoriGenerici) {
        this.classificatoriGenerici = classificatoriGenerici;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ClassificatoreGenerico getClassificatoriGenerici(int i) {
        return this.classificatoriGenerici[i];
    }

    public void setClassificatoriGenerici(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ClassificatoreGenerico _value) {
        this.classificatoriGenerici[i] = _value;
    }


    /**
     * Gets the descrizione value for this Capitolo.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this Capitolo.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the descrizioneArticolo value for this Capitolo.
     * 
     * @return descrizioneArticolo
     */
    public java.lang.String getDescrizioneArticolo() {
        return descrizioneArticolo;
    }


    /**
     * Sets the descrizioneArticolo value for this Capitolo.
     * 
     * @param descrizioneArticolo
     */
    public void setDescrizioneArticolo(java.lang.String descrizioneArticolo) {
        this.descrizioneArticolo = descrizioneArticolo;
    }


    /**
     * Gets the numeroArticolo value for this Capitolo.
     * 
     * @return numeroArticolo
     */
    public java.lang.Integer getNumeroArticolo() {
        return numeroArticolo;
    }


    /**
     * Sets the numeroArticolo value for this Capitolo.
     * 
     * @param numeroArticolo
     */
    public void setNumeroArticolo(java.lang.Integer numeroArticolo) {
        this.numeroArticolo = numeroArticolo;
    }


    /**
     * Gets the numeroCapitolo value for this Capitolo.
     * 
     * @return numeroCapitolo
     */
    public java.lang.Integer getNumeroCapitolo() {
        return numeroCapitolo;
    }


    /**
     * Sets the numeroCapitolo value for this Capitolo.
     * 
     * @param numeroCapitolo
     */
    public void setNumeroCapitolo(java.lang.Integer numeroCapitolo) {
        this.numeroCapitolo = numeroCapitolo;
    }


    /**
     * Gets the numeroUEB value for this Capitolo.
     * 
     * @return numeroUEB
     */
    public java.lang.Integer getNumeroUEB() {
        return numeroUEB;
    }


    /**
     * Sets the numeroUEB value for this Capitolo.
     * 
     * @param numeroUEB
     */
    public void setNumeroUEB(java.lang.Integer numeroUEB) {
        this.numeroUEB = numeroUEB;
    }


    /**
     * Gets the pianoDeiContiFinanziario value for this Capitolo.
     * 
     * @return pianoDeiContiFinanziario
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.PianoDeiContiFinanziario getPianoDeiContiFinanziario() {
        return pianoDeiContiFinanziario;
    }


    /**
     * Sets the pianoDeiContiFinanziario value for this Capitolo.
     * 
     * @param pianoDeiContiFinanziario
     */
    public void setPianoDeiContiFinanziario(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.PianoDeiContiFinanziario pianoDeiContiFinanziario) {
        this.pianoDeiContiFinanziario = pianoDeiContiFinanziario;
    }


    /**
     * Gets the rilevanteIva value for this Capitolo.
     * 
     * @return rilevanteIva
     */
    public java.lang.Boolean getRilevanteIva() {
        return rilevanteIva;
    }


    /**
     * Sets the rilevanteIva value for this Capitolo.
     * 
     * @param rilevanteIva
     */
    public void setRilevanteIva(java.lang.Boolean rilevanteIva) {
        this.rilevanteIva = rilevanteIva;
    }


    /**
     * Gets the sac value for this Capitolo.
     * 
     * @return sac
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.StrutturaAmministrativa getSac() {
        return sac;
    }


    /**
     * Sets the sac value for this Capitolo.
     * 
     * @param sac
     */
    public void setSac(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.StrutturaAmministrativa sac) {
        this.sac = sac;
    }


    /**
     * Gets the tipoFinanziamento value for this Capitolo.
     * 
     * @return tipoFinanziamento
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoFinanziamento getTipoFinanziamento() {
        return tipoFinanziamento;
    }


    /**
     * Sets the tipoFinanziamento value for this Capitolo.
     * 
     * @param tipoFinanziamento
     */
    public void setTipoFinanziamento(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoFinanziamento tipoFinanziamento) {
        this.tipoFinanziamento = tipoFinanziamento;
    }


    /**
     * Gets the tipoFondo value for this Capitolo.
     * 
     * @return tipoFondo
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoFondo getTipoFondo() {
        return tipoFondo;
    }


    /**
     * Sets the tipoFondo value for this Capitolo.
     * 
     * @param tipoFondo
     */
    public void setTipoFondo(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoFondo tipoFondo) {
        this.tipoFondo = tipoFondo;
    }


    /**
     * Gets the titolo value for this Capitolo.
     * 
     * @return titolo
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Titolo getTitolo() {
        return titolo;
    }


    /**
     * Sets the titolo value for this Capitolo.
     * 
     * @param titolo
     */
    public void setTitolo(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Titolo titolo) {
        this.titolo = titolo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Capitolo)) return false;
        Capitolo other = (Capitolo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.annoEsercizio==null && other.getAnnoEsercizio()==null) || 
             (this.annoEsercizio!=null &&
              this.annoEsercizio.equals(other.getAnnoEsercizio()))) &&
            ((this.classificatoriGenerici==null && other.getClassificatoriGenerici()==null) || 
             (this.classificatoriGenerici!=null &&
              java.util.Arrays.equals(this.classificatoriGenerici, other.getClassificatoriGenerici()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.descrizioneArticolo==null && other.getDescrizioneArticolo()==null) || 
             (this.descrizioneArticolo!=null &&
              this.descrizioneArticolo.equals(other.getDescrizioneArticolo()))) &&
            ((this.numeroArticolo==null && other.getNumeroArticolo()==null) || 
             (this.numeroArticolo!=null &&
              this.numeroArticolo.equals(other.getNumeroArticolo()))) &&
            ((this.numeroCapitolo==null && other.getNumeroCapitolo()==null) || 
             (this.numeroCapitolo!=null &&
              this.numeroCapitolo.equals(other.getNumeroCapitolo()))) &&
            ((this.numeroUEB==null && other.getNumeroUEB()==null) || 
             (this.numeroUEB!=null &&
              this.numeroUEB.equals(other.getNumeroUEB()))) &&
            ((this.pianoDeiContiFinanziario==null && other.getPianoDeiContiFinanziario()==null) || 
             (this.pianoDeiContiFinanziario!=null &&
              this.pianoDeiContiFinanziario.equals(other.getPianoDeiContiFinanziario()))) &&
            ((this.rilevanteIva==null && other.getRilevanteIva()==null) || 
             (this.rilevanteIva!=null &&
              this.rilevanteIva.equals(other.getRilevanteIva()))) &&
            ((this.sac==null && other.getSac()==null) || 
             (this.sac!=null &&
              this.sac.equals(other.getSac()))) &&
            ((this.tipoFinanziamento==null && other.getTipoFinanziamento()==null) || 
             (this.tipoFinanziamento!=null &&
              this.tipoFinanziamento.equals(other.getTipoFinanziamento()))) &&
            ((this.tipoFondo==null && other.getTipoFondo()==null) || 
             (this.tipoFondo!=null &&
              this.tipoFondo.equals(other.getTipoFondo()))) &&
            ((this.titolo==null && other.getTitolo()==null) || 
             (this.titolo!=null &&
              this.titolo.equals(other.getTitolo())));
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
        if (getAnnoEsercizio() != null) {
            _hashCode += getAnnoEsercizio().hashCode();
        }
        if (getClassificatoriGenerici() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getClassificatoriGenerici());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getClassificatoriGenerici(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getDescrizioneArticolo() != null) {
            _hashCode += getDescrizioneArticolo().hashCode();
        }
        if (getNumeroArticolo() != null) {
            _hashCode += getNumeroArticolo().hashCode();
        }
        if (getNumeroCapitolo() != null) {
            _hashCode += getNumeroCapitolo().hashCode();
        }
        if (getNumeroUEB() != null) {
            _hashCode += getNumeroUEB().hashCode();
        }
        if (getPianoDeiContiFinanziario() != null) {
            _hashCode += getPianoDeiContiFinanziario().hashCode();
        }
        if (getRilevanteIva() != null) {
            _hashCode += getRilevanteIva().hashCode();
        }
        if (getSac() != null) {
            _hashCode += getSac().hashCode();
        }
        if (getTipoFinanziamento() != null) {
            _hashCode += getTipoFinanziamento().hashCode();
        }
        if (getTipoFondo() != null) {
            _hashCode += getTipoFondo().hashCode();
        }
        if (getTitolo() != null) {
            _hashCode += getTitolo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Capitolo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "capitolo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoEsercizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoEsercizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("classificatoriGenerici");
        elemField.setXmlName(new javax.xml.namespace.QName("", "classificatoriGenerici"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "classificatoreGenerico"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneArticolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneArticolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroArticolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroArticolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroCapitolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroCapitolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroUEB");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroUEB"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pianoDeiContiFinanziario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pianoDeiContiFinanziario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "pianoDeiContiFinanziario"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rilevanteIva");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rilevanteIva"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sac");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sac"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "strutturaAmministrativa"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoFinanziamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoFinanziamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "tipoFinanziamento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoFondo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoFondo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "tipoFondo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "titolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "titolo"));
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
