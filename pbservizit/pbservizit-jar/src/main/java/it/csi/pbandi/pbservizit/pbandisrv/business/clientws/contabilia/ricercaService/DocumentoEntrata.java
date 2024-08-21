/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * DocumentoEntrata.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class DocumentoEntrata  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Documento  implements java.io.Serializable {
    public DocumentoEntrata() {
    }

    public DocumentoEntrata(
           java.lang.String codice,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato,
           java.lang.String descrizione,
           java.lang.Integer annoDocumento,
           java.lang.Integer annoRepertorio,
           java.math.BigDecimal arrotondamento,
           java.lang.String codiceSoggetto,
           java.util.Calendar dataRepertorio,
           java.math.BigDecimal importoDocumento,
           java.math.BigDecimal importoNettoDocumento,
           java.lang.String numeroDocumento,
           java.lang.String numeroRepertorio,
           java.lang.String registroRepertorio,
           java.lang.String tipoDocumento) {
        super(
            codice,
            stato,
            descrizione,
            annoDocumento,
            annoRepertorio,
            arrotondamento,
            codiceSoggetto,
            dataRepertorio,
            importoDocumento,
            importoNettoDocumento,
            numeroDocumento,
            numeroRepertorio,
            registroRepertorio,
            tipoDocumento);
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocumentoEntrata)) return false;
        DocumentoEntrata other = (DocumentoEntrata) obj;
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
        new org.apache.axis.description.TypeDesc(DocumentoEntrata.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/data/1.0", "documentoEntrata"));
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
