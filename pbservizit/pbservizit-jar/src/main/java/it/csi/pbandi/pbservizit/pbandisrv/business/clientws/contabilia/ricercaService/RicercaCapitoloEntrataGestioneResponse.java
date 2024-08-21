/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * RicercaCapitoloEntrataGestioneResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class RicercaCapitoloEntrataGestioneResponse  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloResponse  implements java.io.Serializable {
    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.CapitoloEntrataGestione[] capitoliEntrataGestione;

    public RicercaCapitoloEntrataGestioneResponse() {
    }

    public RicercaCapitoloEntrataGestioneResponse(
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Ente ente,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Errore[] errori,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Esito esito,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Messaggio[] messaggi,
           java.lang.Integer totaleRisultati,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.CapitoloEntrataGestione[] capitoliEntrataGestione) {
        super(
            ente,
            errori,
            esito,
            messaggi,
            totaleRisultati);
        this.capitoliEntrataGestione = capitoliEntrataGestione;
    }


    /**
     * Gets the capitoliEntrataGestione value for this RicercaCapitoloEntrataGestioneResponse.
     * 
     * @return capitoliEntrataGestione
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.CapitoloEntrataGestione[] getCapitoliEntrataGestione() {
        return capitoliEntrataGestione;
    }


    /**
     * Sets the capitoliEntrataGestione value for this RicercaCapitoloEntrataGestioneResponse.
     * 
     * @param capitoliEntrataGestione
     */
    public void setCapitoliEntrataGestione(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.CapitoloEntrataGestione[] capitoliEntrataGestione) {
        this.capitoliEntrataGestione = capitoliEntrataGestione;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.CapitoloEntrataGestione getCapitoliEntrataGestione(int i) {
        return this.capitoliEntrataGestione[i];
    }

    public void setCapitoliEntrataGestione(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.CapitoloEntrataGestione _value) {
        this.capitoliEntrataGestione[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaCapitoloEntrataGestioneResponse)) return false;
        RicercaCapitoloEntrataGestioneResponse other = (RicercaCapitoloEntrataGestioneResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.capitoliEntrataGestione==null && other.getCapitoliEntrataGestione()==null) || 
             (this.capitoliEntrataGestione!=null &&
              java.util.Arrays.equals(this.capitoliEntrataGestione, other.getCapitoliEntrataGestione())));
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
        if (getCapitoliEntrataGestione() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCapitoliEntrataGestione());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCapitoliEntrataGestione(), i);
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
        new org.apache.axis.description.TypeDesc(RicercaCapitoloEntrataGestioneResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitoloEntrataGestioneResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capitoliEntrataGestione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capitoliEntrataGestione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "capitoloEntrataGestione"));
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
