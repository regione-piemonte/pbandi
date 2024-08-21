/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * RicercaImpegnoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class RicercaImpegnoResponse  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaPaginataResponse  implements java.io.Serializable {
    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Impegno[] impegni;

    public RicercaImpegnoResponse() {
    }

    public RicercaImpegnoResponse(
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Ente ente,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Errore[] errori,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Esito esito,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Messaggio[] messaggi,
           java.lang.Integer totaleRisultati,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Impegno[] impegni) {
        super(
            ente,
            errori,
            esito,
            messaggi,
            totaleRisultati);
        this.impegni = impegni;
    }


    /**
     * Gets the impegni value for this RicercaImpegnoResponse.
     * 
     * @return impegni
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Impegno[] getImpegni() {
        return impegni;
    }


    /**
     * Sets the impegni value for this RicercaImpegnoResponse.
     * 
     * @param impegni
     */
    public void setImpegni(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Impegno[] impegni) {
        this.impegni = impegni;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Impegno getImpegni(int i) {
        return this.impegni[i];
    }

    public void setImpegni(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Impegno _value) {
        this.impegni[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaImpegnoResponse)) return false;
        RicercaImpegnoResponse other = (RicercaImpegnoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.impegni==null && other.getImpegni()==null) || 
             (this.impegni!=null &&
              java.util.Arrays.equals(this.impegni, other.getImpegni())));
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
        if (getImpegni() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getImpegni());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getImpegni(), i);
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
        new org.apache.axis.description.TypeDesc(RicercaImpegnoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaImpegnoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("impegni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "impegni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "impegno"));
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
