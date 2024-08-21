/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * StrutturaAmministrativa.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class StrutturaAmministrativa  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.EntitaCodificataBase  implements java.io.Serializable {
    private java.lang.String codiceTipoStruttura;

    public StrutturaAmministrativa() {
    }

    public StrutturaAmministrativa(
           java.lang.String codice,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato,
           java.lang.String descrizione,
           java.lang.String codiceTipoStruttura) {
        super(
            codice,
            stato,
            descrizione);
        this.codiceTipoStruttura = codiceTipoStruttura;
    }


    /**
     * Gets the codiceTipoStruttura value for this StrutturaAmministrativa.
     * 
     * @return codiceTipoStruttura
     */
    public java.lang.String getCodiceTipoStruttura() {
        return codiceTipoStruttura;
    }


    /**
     * Sets the codiceTipoStruttura value for this StrutturaAmministrativa.
     * 
     * @param codiceTipoStruttura
     */
    public void setCodiceTipoStruttura(java.lang.String codiceTipoStruttura) {
        this.codiceTipoStruttura = codiceTipoStruttura;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StrutturaAmministrativa)) return false;
        StrutturaAmministrativa other = (StrutturaAmministrativa) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codiceTipoStruttura==null && other.getCodiceTipoStruttura()==null) || 
             (this.codiceTipoStruttura!=null &&
              this.codiceTipoStruttura.equals(other.getCodiceTipoStruttura())));
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
        if (getCodiceTipoStruttura() != null) {
            _hashCode += getCodiceTipoStruttura().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StrutturaAmministrativa.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "strutturaAmministrativa"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceTipoStruttura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceTipoStruttura"));
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
