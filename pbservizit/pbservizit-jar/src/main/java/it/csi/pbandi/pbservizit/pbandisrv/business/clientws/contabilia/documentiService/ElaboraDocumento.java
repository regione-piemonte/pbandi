/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * ElaboraDocumento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService;

public class ElaboraDocumento  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.BaseRequest  implements java.io.Serializable {
    private java.lang.String codiceTipoDocumento;

    private java.lang.String contenutoDocumento;

    public ElaboraDocumento() {
    }

    public ElaboraDocumento(
           java.lang.Integer annoBilancio,
           java.lang.String codiceEnte,
           java.lang.String codiceFruitore,
           java.lang.String codiceTipoDocumento,
           java.lang.String contenutoDocumento) {
        super(
            annoBilancio,
            codiceEnte,
            codiceFruitore);
        this.codiceTipoDocumento = codiceTipoDocumento;
        this.contenutoDocumento = contenutoDocumento;
    }


    /**
     * Gets the codiceTipoDocumento value for this ElaboraDocumento.
     * 
     * @return codiceTipoDocumento
     */
    public java.lang.String getCodiceTipoDocumento() {
        return codiceTipoDocumento;
    }


    /**
     * Sets the codiceTipoDocumento value for this ElaboraDocumento.
     * 
     * @param codiceTipoDocumento
     */
    public void setCodiceTipoDocumento(java.lang.String codiceTipoDocumento) {
        this.codiceTipoDocumento = codiceTipoDocumento;
    }


    /**
     * Gets the contenutoDocumento value for this ElaboraDocumento.
     * 
     * @return contenutoDocumento
     */
    public java.lang.String getContenutoDocumento() {
        return contenutoDocumento;
    }


    /**
     * Sets the contenutoDocumento value for this ElaboraDocumento.
     * 
     * @param contenutoDocumento
     */
    public void setContenutoDocumento(java.lang.String contenutoDocumento) {
        this.contenutoDocumento = contenutoDocumento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ElaboraDocumento)) return false;
        ElaboraDocumento other = (ElaboraDocumento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codiceTipoDocumento==null && other.getCodiceTipoDocumento()==null) || 
             (this.codiceTipoDocumento!=null &&
              this.codiceTipoDocumento.equals(other.getCodiceTipoDocumento()))) &&
            ((this.contenutoDocumento==null && other.getContenutoDocumento()==null) || 
             (this.contenutoDocumento!=null &&
              this.contenutoDocumento.equals(other.getContenutoDocumento())));
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
        if (getCodiceTipoDocumento() != null) {
            _hashCode += getCodiceTipoDocumento().hashCode();
        }
        if (getContenutoDocumento() != null) {
            _hashCode += getContenutoDocumento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ElaboraDocumento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "elaboraDocumento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceTipoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceTipoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contenutoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contenutoDocumento"));
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
