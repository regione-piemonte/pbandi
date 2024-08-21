/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * BaseRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public abstract class BaseRequest  implements java.io.Serializable {
    private java.lang.Integer annoBilancio;

    private java.lang.String codiceEnte;

    private java.lang.String codiceFruitore;

    public BaseRequest() {
    }

    public BaseRequest(
           java.lang.Integer annoBilancio,
           java.lang.String codiceEnte,
           java.lang.String codiceFruitore) {
           this.annoBilancio = annoBilancio;
           this.codiceEnte = codiceEnte;
           this.codiceFruitore = codiceFruitore;
    }


    /**
     * Gets the annoBilancio value for this BaseRequest.
     * 
     * @return annoBilancio
     */
    public java.lang.Integer getAnnoBilancio() {
        return annoBilancio;
    }


    /**
     * Sets the annoBilancio value for this BaseRequest.
     * 
     * @param annoBilancio
     */
    public void setAnnoBilancio(java.lang.Integer annoBilancio) {
        this.annoBilancio = annoBilancio;
    }


    /**
     * Gets the codiceEnte value for this BaseRequest.
     * 
     * @return codiceEnte
     */
    public java.lang.String getCodiceEnte() {
        return codiceEnte;
    }


    /**
     * Sets the codiceEnte value for this BaseRequest.
     * 
     * @param codiceEnte
     */
    public void setCodiceEnte(java.lang.String codiceEnte) {
        this.codiceEnte = codiceEnte;
    }


    /**
     * Gets the codiceFruitore value for this BaseRequest.
     * 
     * @return codiceFruitore
     */
    public java.lang.String getCodiceFruitore() {
        return codiceFruitore;
    }


    /**
     * Sets the codiceFruitore value for this BaseRequest.
     * 
     * @param codiceFruitore
     */
    public void setCodiceFruitore(java.lang.String codiceFruitore) {
        this.codiceFruitore = codiceFruitore;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BaseRequest)) return false;
        BaseRequest other = (BaseRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.annoBilancio==null && other.getAnnoBilancio()==null) || 
             (this.annoBilancio!=null &&
              this.annoBilancio.equals(other.getAnnoBilancio()))) &&
            ((this.codiceEnte==null && other.getCodiceEnte()==null) || 
             (this.codiceEnte!=null &&
              this.codiceEnte.equals(other.getCodiceEnte()))) &&
            ((this.codiceFruitore==null && other.getCodiceFruitore()==null) || 
             (this.codiceFruitore!=null &&
              this.codiceFruitore.equals(other.getCodiceFruitore())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAnnoBilancio() != null) {
            _hashCode += getAnnoBilancio().hashCode();
        }
        if (getCodiceEnte() != null) {
            _hashCode += getCodiceEnte().hashCode();
        }
        if (getCodiceFruitore() != null) {
            _hashCode += getCodiceFruitore().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BaseRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "baseRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoBilancio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoBilancio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceEnte");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceEnte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFruitore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceFruitore"));
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
