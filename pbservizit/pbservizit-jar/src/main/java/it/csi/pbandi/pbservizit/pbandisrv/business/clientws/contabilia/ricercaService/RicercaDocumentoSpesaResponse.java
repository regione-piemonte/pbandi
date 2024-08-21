/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * RicercaDocumentoSpesaResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class RicercaDocumentoSpesaResponse  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.BaseRicercaDocumentoResponse  implements java.io.Serializable {
    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.DocumentoSpesa[] documentiSpesa;

    public RicercaDocumentoSpesaResponse() {
    }

    public RicercaDocumentoSpesaResponse(
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Ente ente,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Errore[] errori,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Esito esito,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Messaggio[] messaggi,
           java.lang.Integer totaleRisultati,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.DocumentoSpesa[] documentiSpesa) {
        super(
            ente,
            errori,
            esito,
            messaggi,
            totaleRisultati);
        this.documentiSpesa = documentiSpesa;
    }


    /**
     * Gets the documentiSpesa value for this RicercaDocumentoSpesaResponse.
     * 
     * @return documentiSpesa
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.DocumentoSpesa[] getDocumentiSpesa() {
        return documentiSpesa;
    }


    /**
     * Sets the documentiSpesa value for this RicercaDocumentoSpesaResponse.
     * 
     * @param documentiSpesa
     */
    public void setDocumentiSpesa(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.DocumentoSpesa[] documentiSpesa) {
        this.documentiSpesa = documentiSpesa;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.DocumentoSpesa getDocumentiSpesa(int i) {
        return this.documentiSpesa[i];
    }

    public void setDocumentiSpesa(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.DocumentoSpesa _value) {
        this.documentiSpesa[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaDocumentoSpesaResponse)) return false;
        RicercaDocumentoSpesaResponse other = (RicercaDocumentoSpesaResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.documentiSpesa==null && other.getDocumentiSpesa()==null) || 
             (this.documentiSpesa!=null &&
              java.util.Arrays.equals(this.documentiSpesa, other.getDocumentiSpesa())));
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
        if (getDocumentiSpesa() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDocumentiSpesa());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDocumentiSpesa(), i);
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
        new org.apache.axis.description.TypeDesc(RicercaDocumentoSpesaResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "ricercaDocumentoSpesaResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentiSpesa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "documentiSpesa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/data/1.0", "documentoSpesa"));
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
