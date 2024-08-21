/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * RicercaImpegno.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class RicercaImpegno  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaMovimentoGestione  implements java.io.Serializable {
    private java.lang.Integer annoImpegno;

    private java.lang.Integer numeroImpegno;

    public RicercaImpegno() {
    }

    public RicercaImpegno(
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
           java.lang.Integer annoImpegno,
           java.lang.Integer numeroImpegno) {
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
        this.annoImpegno = annoImpegno;
        this.numeroImpegno = numeroImpegno;
    }


    /**
     * Gets the annoImpegno value for this RicercaImpegno.
     * 
     * @return annoImpegno
     */
    public java.lang.Integer getAnnoImpegno() {
        return annoImpegno;
    }


    /**
     * Sets the annoImpegno value for this RicercaImpegno.
     * 
     * @param annoImpegno
     */
    public void setAnnoImpegno(java.lang.Integer annoImpegno) {
        this.annoImpegno = annoImpegno;
    }


    /**
     * Gets the numeroImpegno value for this RicercaImpegno.
     * 
     * @return numeroImpegno
     */
    public java.lang.Integer getNumeroImpegno() {
        return numeroImpegno;
    }


    /**
     * Sets the numeroImpegno value for this RicercaImpegno.
     * 
     * @param numeroImpegno
     */
    public void setNumeroImpegno(java.lang.Integer numeroImpegno) {
        this.numeroImpegno = numeroImpegno;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaImpegno)) return false;
        RicercaImpegno other = (RicercaImpegno) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.annoImpegno==null && other.getAnnoImpegno()==null) || 
             (this.annoImpegno!=null &&
              this.annoImpegno.equals(other.getAnnoImpegno()))) &&
            ((this.numeroImpegno==null && other.getNumeroImpegno()==null) || 
             (this.numeroImpegno!=null &&
              this.numeroImpegno.equals(other.getNumeroImpegno())));
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
        if (getAnnoImpegno() != null) {
            _hashCode += getAnnoImpegno().hashCode();
        }
        if (getNumeroImpegno() != null) {
            _hashCode += getNumeroImpegno().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RicercaImpegno.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaImpegno"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoImpegno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoImpegno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroImpegno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroImpegno"));
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
