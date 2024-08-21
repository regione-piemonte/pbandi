/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * RicercaEstesaLiquidazioniResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class RicercaEstesaLiquidazioniResponse  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.BaseResponse  implements java.io.Serializable {
    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.LiquidazioneAtti[] liquidazioni;

    private java.lang.Integer numeroTotaleLiqudazioniTrovate;

    public RicercaEstesaLiquidazioniResponse() {
    }

    public RicercaEstesaLiquidazioniResponse(
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Ente ente,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Errore[] errori,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Esito esito,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Messaggio[] messaggi,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.LiquidazioneAtti[] liquidazioni,
           java.lang.Integer numeroTotaleLiqudazioniTrovate) {
        super(
            ente,
            errori,
            esito,
            messaggi);
        this.liquidazioni = liquidazioni;
        this.numeroTotaleLiqudazioniTrovate = numeroTotaleLiqudazioniTrovate;
    }


    /**
     * Gets the liquidazioni value for this RicercaEstesaLiquidazioniResponse.
     * 
     * @return liquidazioni
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.LiquidazioneAtti[] getLiquidazioni() {
        return liquidazioni;
    }


    /**
     * Sets the liquidazioni value for this RicercaEstesaLiquidazioniResponse.
     * 
     * @param liquidazioni
     */
    public void setLiquidazioni(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.LiquidazioneAtti[] liquidazioni) {
        this.liquidazioni = liquidazioni;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.LiquidazioneAtti getLiquidazioni(int i) {
        return this.liquidazioni[i];
    }

    public void setLiquidazioni(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.LiquidazioneAtti _value) {
        this.liquidazioni[i] = _value;
    }


    /**
     * Gets the numeroTotaleLiqudazioniTrovate value for this RicercaEstesaLiquidazioniResponse.
     * 
     * @return numeroTotaleLiqudazioniTrovate
     */
    public java.lang.Integer getNumeroTotaleLiqudazioniTrovate() {
        return numeroTotaleLiqudazioniTrovate;
    }


    /**
     * Sets the numeroTotaleLiqudazioniTrovate value for this RicercaEstesaLiquidazioniResponse.
     * 
     * @param numeroTotaleLiqudazioniTrovate
     */
    public void setNumeroTotaleLiqudazioniTrovate(java.lang.Integer numeroTotaleLiqudazioniTrovate) {
        this.numeroTotaleLiqudazioniTrovate = numeroTotaleLiqudazioniTrovate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaEstesaLiquidazioniResponse)) return false;
        RicercaEstesaLiquidazioniResponse other = (RicercaEstesaLiquidazioniResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.liquidazioni==null && other.getLiquidazioni()==null) || 
             (this.liquidazioni!=null &&
              java.util.Arrays.equals(this.liquidazioni, other.getLiquidazioni()))) &&
            ((this.numeroTotaleLiqudazioniTrovate==null && other.getNumeroTotaleLiqudazioniTrovate()==null) || 
             (this.numeroTotaleLiqudazioniTrovate!=null &&
              this.numeroTotaleLiqudazioniTrovate.equals(other.getNumeroTotaleLiqudazioniTrovate())));
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
        if (getLiquidazioni() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLiquidazioni());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLiquidazioni(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNumeroTotaleLiqudazioniTrovate() != null) {
            _hashCode += getNumeroTotaleLiqudazioniTrovate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RicercaEstesaLiquidazioniResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaEstesaLiquidazioniResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liquidazioni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liquidazioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "liquidazioneAtti"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroTotaleLiqudazioniTrovate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroTotaleLiqudazioniTrovate"));
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
