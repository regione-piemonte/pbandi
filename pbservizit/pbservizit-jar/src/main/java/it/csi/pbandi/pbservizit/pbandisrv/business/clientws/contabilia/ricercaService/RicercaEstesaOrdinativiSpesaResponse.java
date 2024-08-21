/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * RicercaEstesaOrdinativiSpesaResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class RicercaEstesaOrdinativiSpesaResponse  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.BaseResponse  implements java.io.Serializable {
    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.MandatoDiPagamento[] mandatiDiPagamento;

    private java.lang.Integer numeroTotaleOrdinativiSpesaTrovati;

    public RicercaEstesaOrdinativiSpesaResponse() {
    }

    public RicercaEstesaOrdinativiSpesaResponse(
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Ente ente,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Errore[] errori,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Esito esito,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Messaggio[] messaggi,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.MandatoDiPagamento[] mandatiDiPagamento,
           java.lang.Integer numeroTotaleOrdinativiSpesaTrovati) {
        super(
            ente,
            errori,
            esito,
            messaggi);
        this.mandatiDiPagamento = mandatiDiPagamento;
        this.numeroTotaleOrdinativiSpesaTrovati = numeroTotaleOrdinativiSpesaTrovati;
    }


    /**
     * Gets the mandatiDiPagamento value for this RicercaEstesaOrdinativiSpesaResponse.
     * 
     * @return mandatiDiPagamento
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.MandatoDiPagamento[] getMandatiDiPagamento() {
        return mandatiDiPagamento;
    }


    /**
     * Sets the mandatiDiPagamento value for this RicercaEstesaOrdinativiSpesaResponse.
     * 
     * @param mandatiDiPagamento
     */
    public void setMandatiDiPagamento(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.MandatoDiPagamento[] mandatiDiPagamento) {
        this.mandatiDiPagamento = mandatiDiPagamento;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.MandatoDiPagamento getMandatiDiPagamento(int i) {
        return this.mandatiDiPagamento[i];
    }

    public void setMandatiDiPagamento(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.MandatoDiPagamento _value) {
        this.mandatiDiPagamento[i] = _value;
    }


    /**
     * Gets the numeroTotaleOrdinativiSpesaTrovati value for this RicercaEstesaOrdinativiSpesaResponse.
     * 
     * @return numeroTotaleOrdinativiSpesaTrovati
     */
    public java.lang.Integer getNumeroTotaleOrdinativiSpesaTrovati() {
        return numeroTotaleOrdinativiSpesaTrovati;
    }


    /**
     * Sets the numeroTotaleOrdinativiSpesaTrovati value for this RicercaEstesaOrdinativiSpesaResponse.
     * 
     * @param numeroTotaleOrdinativiSpesaTrovati
     */
    public void setNumeroTotaleOrdinativiSpesaTrovati(java.lang.Integer numeroTotaleOrdinativiSpesaTrovati) {
        this.numeroTotaleOrdinativiSpesaTrovati = numeroTotaleOrdinativiSpesaTrovati;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaEstesaOrdinativiSpesaResponse)) return false;
        RicercaEstesaOrdinativiSpesaResponse other = (RicercaEstesaOrdinativiSpesaResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.mandatiDiPagamento==null && other.getMandatiDiPagamento()==null) || 
             (this.mandatiDiPagamento!=null &&
              java.util.Arrays.equals(this.mandatiDiPagamento, other.getMandatiDiPagamento()))) &&
            ((this.numeroTotaleOrdinativiSpesaTrovati==null && other.getNumeroTotaleOrdinativiSpesaTrovati()==null) || 
             (this.numeroTotaleOrdinativiSpesaTrovati!=null &&
              this.numeroTotaleOrdinativiSpesaTrovati.equals(other.getNumeroTotaleOrdinativiSpesaTrovati())));
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
        if (getMandatiDiPagamento() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMandatiDiPagamento());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMandatiDiPagamento(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNumeroTotaleOrdinativiSpesaTrovati() != null) {
            _hashCode += getNumeroTotaleOrdinativiSpesaTrovati().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RicercaEstesaOrdinativiSpesaResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaEstesaOrdinativiSpesaResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mandatiDiPagamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mandatiDiPagamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "mandatoDiPagamento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroTotaleOrdinativiSpesaTrovati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroTotaleOrdinativiSpesaTrovati"));
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
