/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * BaseAsyncResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService;

public abstract class BaseAsyncResponse  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.BaseResponse  implements java.io.Serializable {
    private java.lang.Integer idOperazioneAsincrona;

    public BaseAsyncResponse() {
    }

    public BaseAsyncResponse(
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Ente ente,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Errore[] errori,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Esito esito,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Messaggio[] messaggi,
           java.lang.Integer idOperazioneAsincrona) {
        super(
            ente,
            errori,
            esito,
            messaggi);
        this.idOperazioneAsincrona = idOperazioneAsincrona;
    }


    /**
     * Gets the idOperazioneAsincrona value for this BaseAsyncResponse.
     * 
     * @return idOperazioneAsincrona
     */
    public java.lang.Integer getIdOperazioneAsincrona() {
        return idOperazioneAsincrona;
    }


    /**
     * Sets the idOperazioneAsincrona value for this BaseAsyncResponse.
     * 
     * @param idOperazioneAsincrona
     */
    public void setIdOperazioneAsincrona(java.lang.Integer idOperazioneAsincrona) {
        this.idOperazioneAsincrona = idOperazioneAsincrona;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BaseAsyncResponse)) return false;
        BaseAsyncResponse other = (BaseAsyncResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idOperazioneAsincrona==null && other.getIdOperazioneAsincrona()==null) || 
             (this.idOperazioneAsincrona!=null &&
              this.idOperazioneAsincrona.equals(other.getIdOperazioneAsincrona())));
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
        if (getIdOperazioneAsincrona() != null) {
            _hashCode += getIdOperazioneAsincrona().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BaseAsyncResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "baseAsyncResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idOperazioneAsincrona");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idOperazioneAsincrona"));
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
