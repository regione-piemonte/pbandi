/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * ClassificatoreGenerico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class ClassificatoreGenerico  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.EntitaCodificataBase  implements java.io.Serializable {
    private java.lang.String tipoCodifica;

    public ClassificatoreGenerico() {
    }

    public ClassificatoreGenerico(
           java.lang.String codice,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato,
           java.lang.String descrizione,
           java.lang.String tipoCodifica) {
        super(
            codice,
            stato,
            descrizione);
        this.tipoCodifica = tipoCodifica;
    }


    /**
     * Gets the tipoCodifica value for this ClassificatoreGenerico.
     * 
     * @return tipoCodifica
     */
    public java.lang.String getTipoCodifica() {
        return tipoCodifica;
    }


    /**
     * Sets the tipoCodifica value for this ClassificatoreGenerico.
     * 
     * @param tipoCodifica
     */
    public void setTipoCodifica(java.lang.String tipoCodifica) {
        this.tipoCodifica = tipoCodifica;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClassificatoreGenerico)) return false;
        ClassificatoreGenerico other = (ClassificatoreGenerico) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.tipoCodifica==null && other.getTipoCodifica()==null) || 
             (this.tipoCodifica!=null &&
              this.tipoCodifica.equals(other.getTipoCodifica())));
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
        if (getTipoCodifica() != null) {
            _hashCode += getTipoCodifica().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClassificatoreGenerico.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "classificatoreGenerico"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoCodifica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoCodifica"));
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
