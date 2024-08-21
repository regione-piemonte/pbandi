/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * RicercaOrdinativoIncassoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class RicercaOrdinativoIncassoResponse  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaPaginataResponse  implements java.io.Serializable {
    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.OrdinativoIncasso[] ordinativiIncasso;

    public RicercaOrdinativoIncassoResponse() {
    }

    public RicercaOrdinativoIncassoResponse(
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Ente ente,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Errore[] errori,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Esito esito,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Messaggio[] messaggi,
           java.lang.Integer totaleRisultati,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.OrdinativoIncasso[] ordinativiIncasso) {
        super(
            ente,
            errori,
            esito,
            messaggi,
            totaleRisultati);
        this.ordinativiIncasso = ordinativiIncasso;
    }


    /**
     * Gets the ordinativiIncasso value for this RicercaOrdinativoIncassoResponse.
     * 
     * @return ordinativiIncasso
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.OrdinativoIncasso[] getOrdinativiIncasso() {
        return ordinativiIncasso;
    }


    /**
     * Sets the ordinativiIncasso value for this RicercaOrdinativoIncassoResponse.
     * 
     * @param ordinativiIncasso
     */
    public void setOrdinativiIncasso(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.OrdinativoIncasso[] ordinativiIncasso) {
        this.ordinativiIncasso = ordinativiIncasso;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.OrdinativoIncasso getOrdinativiIncasso(int i) {
        return this.ordinativiIncasso[i];
    }

    public void setOrdinativiIncasso(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.OrdinativoIncasso _value) {
        this.ordinativiIncasso[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaOrdinativoIncassoResponse)) return false;
        RicercaOrdinativoIncassoResponse other = (RicercaOrdinativoIncassoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.ordinativiIncasso==null && other.getOrdinativiIncasso()==null) || 
             (this.ordinativiIncasso!=null &&
              java.util.Arrays.equals(this.ordinativiIncasso, other.getOrdinativiIncasso())));
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
        if (getOrdinativiIncasso() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOrdinativiIncasso());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOrdinativiIncasso(), i);
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
        new org.apache.axis.description.TypeDesc(RicercaOrdinativoIncassoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaOrdinativoIncassoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ordinativiIncasso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ordinativiIncasso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "ordinativoIncasso"));
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
