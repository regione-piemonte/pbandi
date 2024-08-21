/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * RicercaAccertamento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class RicercaAccertamento  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaMovimentoGestione  implements java.io.Serializable {
    private java.lang.Integer annoAccertamento;

    private java.lang.Integer numeroAccertamento;

    public RicercaAccertamento() {
    }

    public RicercaAccertamento(
           java.lang.Integer annoBilancio,
           java.lang.String codiceEnte,
           java.lang.String codiceFruitore,
           java.lang.Integer numeroElementiPerPagina,
           java.lang.Integer numeroPagina,
           java.lang.Integer annoProvvedimento,
           java.lang.String codiceStruttura,
           java.lang.String codiceTipoProvvedimento,
           java.lang.String codiceTipoStruttura,
           java.lang.Integer numeroProvvedimento,
           java.lang.Integer annoAccertamento,
           java.lang.Integer numeroAccertamento) {
        super(
            annoBilancio,
            codiceEnte,
            codiceFruitore,
            numeroElementiPerPagina,
            numeroPagina,
            annoProvvedimento,
            codiceStruttura,
            codiceTipoProvvedimento,
            codiceTipoStruttura,
            numeroProvvedimento);
        this.annoAccertamento = annoAccertamento;
        this.numeroAccertamento = numeroAccertamento;
    }


    /**
     * Gets the annoAccertamento value for this RicercaAccertamento.
     * 
     * @return annoAccertamento
     */
    public java.lang.Integer getAnnoAccertamento() {
        return annoAccertamento;
    }


    /**
     * Sets the annoAccertamento value for this RicercaAccertamento.
     * 
     * @param annoAccertamento
     */
    public void setAnnoAccertamento(java.lang.Integer annoAccertamento) {
        this.annoAccertamento = annoAccertamento;
    }


    /**
     * Gets the numeroAccertamento value for this RicercaAccertamento.
     * 
     * @return numeroAccertamento
     */
    public java.lang.Integer getNumeroAccertamento() {
        return numeroAccertamento;
    }


    /**
     * Sets the numeroAccertamento value for this RicercaAccertamento.
     * 
     * @param numeroAccertamento
     */
    public void setNumeroAccertamento(java.lang.Integer numeroAccertamento) {
        this.numeroAccertamento = numeroAccertamento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaAccertamento)) return false;
        RicercaAccertamento other = (RicercaAccertamento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.annoAccertamento==null && other.getAnnoAccertamento()==null) || 
             (this.annoAccertamento!=null &&
              this.annoAccertamento.equals(other.getAnnoAccertamento()))) &&
            ((this.numeroAccertamento==null && other.getNumeroAccertamento()==null) || 
             (this.numeroAccertamento!=null &&
              this.numeroAccertamento.equals(other.getNumeroAccertamento())));
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
        if (getAnnoAccertamento() != null) {
            _hashCode += getAnnoAccertamento().hashCode();
        }
        if (getNumeroAccertamento() != null) {
            _hashCode += getNumeroAccertamento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RicercaAccertamento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaAccertamento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoAccertamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoAccertamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroAccertamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroAccertamento"));
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
