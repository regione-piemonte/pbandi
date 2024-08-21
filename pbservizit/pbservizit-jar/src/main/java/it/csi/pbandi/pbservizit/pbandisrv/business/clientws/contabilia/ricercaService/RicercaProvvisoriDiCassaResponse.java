/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * RicercaProvvisoriDiCassaResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class RicercaProvvisoriDiCassaResponse  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaPaginataResponse  implements java.io.Serializable {
    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ProvvisorioDiCassa[] provvisoriDiCassa;

    public RicercaProvvisoriDiCassaResponse() {
    }

    public RicercaProvvisoriDiCassaResponse(
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Ente ente,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Errore[] errori,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Esito esito,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Messaggio[] messaggi,
           java.lang.Integer totaleRisultati,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ProvvisorioDiCassa[] provvisoriDiCassa) {
        super(
            ente,
            errori,
            esito,
            messaggi,
            totaleRisultati);
        this.provvisoriDiCassa = provvisoriDiCassa;
    }


    /**
     * Gets the provvisoriDiCassa value for this RicercaProvvisoriDiCassaResponse.
     * 
     * @return provvisoriDiCassa
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ProvvisorioDiCassa[] getProvvisoriDiCassa() {
        return provvisoriDiCassa;
    }


    /**
     * Sets the provvisoriDiCassa value for this RicercaProvvisoriDiCassaResponse.
     * 
     * @param provvisoriDiCassa
     */
    public void setProvvisoriDiCassa(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ProvvisorioDiCassa[] provvisoriDiCassa) {
        this.provvisoriDiCassa = provvisoriDiCassa;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ProvvisorioDiCassa getProvvisoriDiCassa(int i) {
        return this.provvisoriDiCassa[i];
    }

    public void setProvvisoriDiCassa(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ProvvisorioDiCassa _value) {
        this.provvisoriDiCassa[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaProvvisoriDiCassaResponse)) return false;
        RicercaProvvisoriDiCassaResponse other = (RicercaProvvisoriDiCassaResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.provvisoriDiCassa==null && other.getProvvisoriDiCassa()==null) || 
             (this.provvisoriDiCassa!=null &&
              java.util.Arrays.equals(this.provvisoriDiCassa, other.getProvvisoriDiCassa())));
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
        if (getProvvisoriDiCassa() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProvvisoriDiCassa());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProvvisoriDiCassa(), i);
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
        new org.apache.axis.description.TypeDesc(RicercaProvvisoriDiCassaResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "ricercaProvvisoriDiCassaResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provvisoriDiCassa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "provvisoriDiCassa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/data/1.0", "provvisorioDiCassa"));
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
