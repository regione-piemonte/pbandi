/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * RicercaDocumentoEntrataResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class RicercaDocumentoEntrataResponse  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.BaseRicercaDocumentoResponse  implements java.io.Serializable {
    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.DocumentoEntrata[] documentiEntrata;

    public RicercaDocumentoEntrataResponse() {
    }

    public RicercaDocumentoEntrataResponse(
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Ente ente,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Errore[] errori,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Esito esito,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Messaggio[] messaggi,
           java.lang.Integer totaleRisultati,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.DocumentoEntrata[] documentiEntrata) {
        super(
            ente,
            errori,
            esito,
            messaggi,
            totaleRisultati);
        this.documentiEntrata = documentiEntrata;
    }


    /**
     * Gets the documentiEntrata value for this RicercaDocumentoEntrataResponse.
     * 
     * @return documentiEntrata
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.DocumentoEntrata[] getDocumentiEntrata() {
        return documentiEntrata;
    }


    /**
     * Sets the documentiEntrata value for this RicercaDocumentoEntrataResponse.
     * 
     * @param documentiEntrata
     */
    public void setDocumentiEntrata(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.DocumentoEntrata[] documentiEntrata) {
        this.documentiEntrata = documentiEntrata;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.DocumentoEntrata getDocumentiEntrata(int i) {
        return this.documentiEntrata[i];
    }

    public void setDocumentiEntrata(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.DocumentoEntrata _value) {
        this.documentiEntrata[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaDocumentoEntrataResponse)) return false;
        RicercaDocumentoEntrataResponse other = (RicercaDocumentoEntrataResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.documentiEntrata==null && other.getDocumentiEntrata()==null) || 
             (this.documentiEntrata!=null &&
              java.util.Arrays.equals(this.documentiEntrata, other.getDocumentiEntrata())));
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
        if (getDocumentiEntrata() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDocumentiEntrata());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDocumentiEntrata(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RicercaDocumentoEntrataResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "ricercaDocumentoEntrataResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentiEntrata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "documentiEntrata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/data/1.0", "documentoEntrata"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
