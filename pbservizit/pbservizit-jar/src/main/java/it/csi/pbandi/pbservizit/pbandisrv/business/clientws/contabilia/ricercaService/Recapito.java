/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * Recapito.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class Recapito  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.EntitaBase  implements java.io.Serializable {
    private java.lang.String cap;

    private java.lang.String codiceIstatComune;

    private java.lang.String codiceNazione;

    private java.lang.String comune;

    private java.lang.String descrizioneNazione;

    private java.lang.String indirizzo;

    private java.lang.String numeroCivico;

    private java.lang.String provincia;

    private java.lang.String sedime;

    public Recapito() {
    }

    public Recapito(
           java.lang.String codice,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato,
           java.lang.String cap,
           java.lang.String codiceIstatComune,
           java.lang.String codiceNazione,
           java.lang.String comune,
           java.lang.String descrizioneNazione,
           java.lang.String indirizzo,
           java.lang.String numeroCivico,
           java.lang.String provincia,
           java.lang.String sedime) {
        super(
            codice,
            stato);
        this.cap = cap;
        this.codiceIstatComune = codiceIstatComune;
        this.codiceNazione = codiceNazione;
        this.comune = comune;
        this.descrizioneNazione = descrizioneNazione;
        this.indirizzo = indirizzo;
        this.numeroCivico = numeroCivico;
        this.provincia = provincia;
        this.sedime = sedime;
    }


    /**
     * Gets the cap value for this Recapito.
     * 
     * @return cap
     */
    public java.lang.String getCap() {
        return cap;
    }


    /**
     * Sets the cap value for this Recapito.
     * 
     * @param cap
     */
    public void setCap(java.lang.String cap) {
        this.cap = cap;
    }


    /**
     * Gets the codiceIstatComune value for this Recapito.
     * 
     * @return codiceIstatComune
     */
    public java.lang.String getCodiceIstatComune() {
        return codiceIstatComune;
    }


    /**
     * Sets the codiceIstatComune value for this Recapito.
     * 
     * @param codiceIstatComune
     */
    public void setCodiceIstatComune(java.lang.String codiceIstatComune) {
        this.codiceIstatComune = codiceIstatComune;
    }


    /**
     * Gets the codiceNazione value for this Recapito.
     * 
     * @return codiceNazione
     */
    public java.lang.String getCodiceNazione() {
        return codiceNazione;
    }


    /**
     * Sets the codiceNazione value for this Recapito.
     * 
     * @param codiceNazione
     */
    public void setCodiceNazione(java.lang.String codiceNazione) {
        this.codiceNazione = codiceNazione;
    }


    /**
     * Gets the comune value for this Recapito.
     * 
     * @return comune
     */
    public java.lang.String getComune() {
        return comune;
    }


    /**
     * Sets the comune value for this Recapito.
     * 
     * @param comune
     */
    public void setComune(java.lang.String comune) {
        this.comune = comune;
    }


    /**
     * Gets the descrizioneNazione value for this Recapito.
     * 
     * @return descrizioneNazione
     */
    public java.lang.String getDescrizioneNazione() {
        return descrizioneNazione;
    }


    /**
     * Sets the descrizioneNazione value for this Recapito.
     * 
     * @param descrizioneNazione
     */
    public void setDescrizioneNazione(java.lang.String descrizioneNazione) {
        this.descrizioneNazione = descrizioneNazione;
    }


    /**
     * Gets the indirizzo value for this Recapito.
     * 
     * @return indirizzo
     */
    public java.lang.String getIndirizzo() {
        return indirizzo;
    }


    /**
     * Sets the indirizzo value for this Recapito.
     * 
     * @param indirizzo
     */
    public void setIndirizzo(java.lang.String indirizzo) {
        this.indirizzo = indirizzo;
    }


    /**
     * Gets the numeroCivico value for this Recapito.
     * 
     * @return numeroCivico
     */
    public java.lang.String getNumeroCivico() {
        return numeroCivico;
    }


    /**
     * Sets the numeroCivico value for this Recapito.
     * 
     * @param numeroCivico
     */
    public void setNumeroCivico(java.lang.String numeroCivico) {
        this.numeroCivico = numeroCivico;
    }


    /**
     * Gets the provincia value for this Recapito.
     * 
     * @return provincia
     */
    public java.lang.String getProvincia() {
        return provincia;
    }


    /**
     * Sets the provincia value for this Recapito.
     * 
     * @param provincia
     */
    public void setProvincia(java.lang.String provincia) {
        this.provincia = provincia;
    }


    /**
     * Gets the sedime value for this Recapito.
     * 
     * @return sedime
     */
    public java.lang.String getSedime() {
        return sedime;
    }


    /**
     * Sets the sedime value for this Recapito.
     * 
     * @param sedime
     */
    public void setSedime(java.lang.String sedime) {
        this.sedime = sedime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Recapito)) return false;
        Recapito other = (Recapito) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.cap==null && other.getCap()==null) || 
             (this.cap!=null &&
              this.cap.equals(other.getCap()))) &&
            ((this.codiceIstatComune==null && other.getCodiceIstatComune()==null) || 
             (this.codiceIstatComune!=null &&
              this.codiceIstatComune.equals(other.getCodiceIstatComune()))) &&
            ((this.codiceNazione==null && other.getCodiceNazione()==null) || 
             (this.codiceNazione!=null &&
              this.codiceNazione.equals(other.getCodiceNazione()))) &&
            ((this.comune==null && other.getComune()==null) || 
             (this.comune!=null &&
              this.comune.equals(other.getComune()))) &&
            ((this.descrizioneNazione==null && other.getDescrizioneNazione()==null) || 
             (this.descrizioneNazione!=null &&
              this.descrizioneNazione.equals(other.getDescrizioneNazione()))) &&
            ((this.indirizzo==null && other.getIndirizzo()==null) || 
             (this.indirizzo!=null &&
              this.indirizzo.equals(other.getIndirizzo()))) &&
            ((this.numeroCivico==null && other.getNumeroCivico()==null) || 
             (this.numeroCivico!=null &&
              this.numeroCivico.equals(other.getNumeroCivico()))) &&
            ((this.provincia==null && other.getProvincia()==null) || 
             (this.provincia!=null &&
              this.provincia.equals(other.getProvincia()))) &&
            ((this.sedime==null && other.getSedime()==null) || 
             (this.sedime!=null &&
              this.sedime.equals(other.getSedime())));
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
        if (getCap() != null) {
            _hashCode += getCap().hashCode();
        }
        if (getCodiceIstatComune() != null) {
            _hashCode += getCodiceIstatComune().hashCode();
        }
        if (getCodiceNazione() != null) {
            _hashCode += getCodiceNazione().hashCode();
        }
        if (getComune() != null) {
            _hashCode += getComune().hashCode();
        }
        if (getDescrizioneNazione() != null) {
            _hashCode += getDescrizioneNazione().hashCode();
        }
        if (getIndirizzo() != null) {
            _hashCode += getIndirizzo().hashCode();
        }
        if (getNumeroCivico() != null) {
            _hashCode += getNumeroCivico().hashCode();
        }
        if (getProvincia() != null) {
            _hashCode += getProvincia().hashCode();
        }
        if (getSedime() != null) {
            _hashCode += getSedime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Recapito.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "recapito"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cap");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cap"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceIstatComune");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceIstatComune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceNazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceNazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comune");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneNazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneNazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indirizzo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "indirizzo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroCivico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroCivico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provincia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "provincia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sedime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sedime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
