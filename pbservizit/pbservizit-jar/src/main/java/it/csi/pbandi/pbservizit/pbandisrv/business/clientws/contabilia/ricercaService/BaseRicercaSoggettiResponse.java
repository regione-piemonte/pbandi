/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * BaseRicercaSoggettiResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public abstract class BaseRicercaSoggettiResponse  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.BaseRicercaResponse  implements java.io.Serializable {
    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Soggetto[] soggetti;

    public BaseRicercaSoggettiResponse() {
    }

    public BaseRicercaSoggettiResponse(
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Ente ente,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Errore[] errori,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Esito esito,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Messaggio[] messaggi,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Soggetto[] soggetti) {
        super(
            ente,
            errori,
            esito,
            messaggi);
        this.soggetti = soggetti;
    }


    /**
     * Gets the soggetti value for this BaseRicercaSoggettiResponse.
     * 
     * @return soggetti
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Soggetto[] getSoggetti() {
        return soggetti;
    }


    /**
     * Sets the soggetti value for this BaseRicercaSoggettiResponse.
     * 
     * @param soggetti
     */
    public void setSoggetti(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Soggetto[] soggetti) {
        this.soggetti = soggetti;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Soggetto getSoggetti(int i) {
        return this.soggetti[i];
    }

    public void setSoggetti(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Soggetto _value) {
        this.soggetti[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BaseRicercaSoggettiResponse)) return false;
        BaseRicercaSoggettiResponse other = (BaseRicercaSoggettiResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.soggetti==null && other.getSoggetti()==null) || 
             (this.soggetti!=null &&
              java.util.Arrays.equals(this.soggetti, other.getSoggetti())));
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
        if (getSoggetti() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSoggetti());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSoggetti(), i);
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
        new org.apache.axis.description.TypeDesc(BaseRicercaSoggettiResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "baseRicercaSoggettiResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soggetti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "soggetti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "soggetto"));
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
