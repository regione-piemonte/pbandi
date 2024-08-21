/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * OrdinativoPagamento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class OrdinativoPagamento  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Ordinativo  implements java.io.Serializable {
    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubOrdinativoPagamento[] quotePagamento;

    public OrdinativoPagamento() {
    }

    public OrdinativoPagamento(
           java.lang.String codiceSoggetto,
           java.lang.String codiceStato,
           java.util.Calendar dataEmissione,
           java.util.Calendar dataQuietanza,
           java.lang.String denominazioneSoggetto,
           java.lang.Integer numeroCapitolo,
           java.lang.Integer numeroOrdinativo,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubOrdinativoPagamento[] quotePagamento) {
        super(
            codiceSoggetto,
            codiceStato,
            dataEmissione,
            dataQuietanza,
            denominazioneSoggetto,
            numeroCapitolo,
            numeroOrdinativo);
        this.quotePagamento = quotePagamento;
    }


    /**
     * Gets the quotePagamento value for this OrdinativoPagamento.
     * 
     * @return quotePagamento
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubOrdinativoPagamento[] getQuotePagamento() {
        return quotePagamento;
    }


    /**
     * Sets the quotePagamento value for this OrdinativoPagamento.
     * 
     * @param quotePagamento
     */
    public void setQuotePagamento(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubOrdinativoPagamento[] quotePagamento) {
        this.quotePagamento = quotePagamento;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubOrdinativoPagamento getQuotePagamento(int i) {
        return this.quotePagamento[i];
    }

    public void setQuotePagamento(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubOrdinativoPagamento _value) {
        this.quotePagamento[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrdinativoPagamento)) return false;
        OrdinativoPagamento other = (OrdinativoPagamento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.quotePagamento==null && other.getQuotePagamento()==null) || 
             (this.quotePagamento!=null &&
              java.util.Arrays.equals(this.quotePagamento, other.getQuotePagamento())));
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
        if (getQuotePagamento() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getQuotePagamento());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getQuotePagamento(), i);
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
        new org.apache.axis.description.TypeDesc(OrdinativoPagamento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "ordinativoPagamento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quotePagamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quotePagamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "subOrdinativoPagamento"));
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
