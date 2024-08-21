/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * RicercaDettaglioSoggetti.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class RicercaDettaglioSoggetti  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.BaseRicercaSoggetti  implements java.io.Serializable {
    public RicercaDettaglioSoggetti() {
    }

    public RicercaDettaglioSoggetti(
           java.lang.Integer annoBilancio,
           java.lang.String codiceEnte,
           java.lang.String codiceFruitore,
           java.lang.String codice,
           java.lang.String codiceFiscale,
           java.lang.String partitaIva) {
        super(
            annoBilancio,
            codiceEnte,
            codiceFruitore,
            codice,
            codiceFiscale,
            partitaIva);
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaDettaglioSoggetti)) return false;
        RicercaDettaglioSoggetti other = (RicercaDettaglioSoggetti) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj);
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RicercaDettaglioSoggetti.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDettaglioSoggetti"));
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
