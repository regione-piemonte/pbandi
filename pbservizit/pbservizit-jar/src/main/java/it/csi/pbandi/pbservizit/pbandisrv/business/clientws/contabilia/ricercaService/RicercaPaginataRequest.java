/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * RicercaPaginataRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public abstract class RicercaPaginataRequest  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.BaseRicercaRequest  implements java.io.Serializable {
    private java.lang.Integer numeroElementiPerPagina;

    private java.lang.Integer numeroPagina;

    public RicercaPaginataRequest() {
    }

    public RicercaPaginataRequest(
           java.lang.Integer annoBilancio,
           java.lang.String codiceEnte,
           java.lang.String codiceFruitore,
           java.lang.Integer numeroElementiPerPagina,
           java.lang.Integer numeroPagina) {
        super(
            annoBilancio,
            codiceEnte,
            codiceFruitore);
        this.numeroElementiPerPagina = numeroElementiPerPagina;
        this.numeroPagina = numeroPagina;
    }


    /**
     * Gets the numeroElementiPerPagina value for this RicercaPaginataRequest.
     * 
     * @return numeroElementiPerPagina
     */
    public java.lang.Integer getNumeroElementiPerPagina() {
        return numeroElementiPerPagina;
    }


    /**
     * Sets the numeroElementiPerPagina value for this RicercaPaginataRequest.
     * 
     * @param numeroElementiPerPagina
     */
    public void setNumeroElementiPerPagina(java.lang.Integer numeroElementiPerPagina) {
        this.numeroElementiPerPagina = numeroElementiPerPagina;
    }


    /**
     * Gets the numeroPagina value for this RicercaPaginataRequest.
     * 
     * @return numeroPagina
     */
    public java.lang.Integer getNumeroPagina() {
        return numeroPagina;
    }


    /**
     * Sets the numeroPagina value for this RicercaPaginataRequest.
     * 
     * @param numeroPagina
     */
    public void setNumeroPagina(java.lang.Integer numeroPagina) {
        this.numeroPagina = numeroPagina;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaPaginataRequest)) return false;
        RicercaPaginataRequest other = (RicercaPaginataRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.numeroElementiPerPagina==null && other.getNumeroElementiPerPagina()==null) || 
             (this.numeroElementiPerPagina!=null &&
              this.numeroElementiPerPagina.equals(other.getNumeroElementiPerPagina()))) &&
            ((this.numeroPagina==null && other.getNumeroPagina()==null) || 
             (this.numeroPagina!=null &&
              this.numeroPagina.equals(other.getNumeroPagina())));
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
        if (getNumeroElementiPerPagina() != null) {
            _hashCode += getNumeroElementiPerPagina().hashCode();
        }
        if (getNumeroPagina() != null) {
            _hashCode += getNumeroPagina().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RicercaPaginataRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "ricercaPaginataRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroElementiPerPagina");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroElementiPerPagina"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroPagina");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroPagina"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
