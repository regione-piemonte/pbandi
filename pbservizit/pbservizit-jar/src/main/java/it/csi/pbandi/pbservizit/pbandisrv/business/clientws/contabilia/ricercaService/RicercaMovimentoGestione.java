/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * RicercaMovimentoGestione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class RicercaMovimentoGestione  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaPaginataRequest  implements java.io.Serializable {
    private java.lang.Integer annoProvvedimento;

    private java.lang.String codiceStruttura;

    private java.lang.String codiceTipoProvvedimento;

    private java.lang.String codiceTipoStruttura;

    private java.lang.Integer numeroProvvedimento;

    public RicercaMovimentoGestione() {
    }

    public RicercaMovimentoGestione(
           java.lang.Integer annoBilancio,
           java.lang.String codiceEnte,
           java.lang.String codiceFruitore,
           java.lang.Integer numeroElementiPerPagina,
           java.lang.Integer numeroPagina,
           java.lang.Integer annoProvvedimento,
           java.lang.String codiceStruttura,
           java.lang.String codiceTipoProvvedimento,
           java.lang.String codiceTipoStruttura,
           java.lang.Integer numeroProvvedimento) {
        super(
            annoBilancio,
            codiceEnte,
            codiceFruitore,
            numeroElementiPerPagina,
            numeroPagina);
        this.annoProvvedimento = annoProvvedimento;
        this.codiceStruttura = codiceStruttura;
        this.codiceTipoProvvedimento = codiceTipoProvvedimento;
        this.codiceTipoStruttura = codiceTipoStruttura;
        this.numeroProvvedimento = numeroProvvedimento;
    }


    /**
     * Gets the annoProvvedimento value for this RicercaMovimentoGestione.
     * 
     * @return annoProvvedimento
     */
    public java.lang.Integer getAnnoProvvedimento() {
        return annoProvvedimento;
    }


    /**
     * Sets the annoProvvedimento value for this RicercaMovimentoGestione.
     * 
     * @param annoProvvedimento
     */
    public void setAnnoProvvedimento(java.lang.Integer annoProvvedimento) {
        this.annoProvvedimento = annoProvvedimento;
    }


    /**
     * Gets the codiceStruttura value for this RicercaMovimentoGestione.
     * 
     * @return codiceStruttura
     */
    public java.lang.String getCodiceStruttura() {
        return codiceStruttura;
    }


    /**
     * Sets the codiceStruttura value for this RicercaMovimentoGestione.
     * 
     * @param codiceStruttura
     */
    public void setCodiceStruttura(java.lang.String codiceStruttura) {
        this.codiceStruttura = codiceStruttura;
    }


    /**
     * Gets the codiceTipoProvvedimento value for this RicercaMovimentoGestione.
     * 
     * @return codiceTipoProvvedimento
     */
    public java.lang.String getCodiceTipoProvvedimento() {
        return codiceTipoProvvedimento;
    }


    /**
     * Sets the codiceTipoProvvedimento value for this RicercaMovimentoGestione.
     * 
     * @param codiceTipoProvvedimento
     */
    public void setCodiceTipoProvvedimento(java.lang.String codiceTipoProvvedimento) {
        this.codiceTipoProvvedimento = codiceTipoProvvedimento;
    }


    /**
     * Gets the codiceTipoStruttura value for this RicercaMovimentoGestione.
     * 
     * @return codiceTipoStruttura
     */
    public java.lang.String getCodiceTipoStruttura() {
        return codiceTipoStruttura;
    }


    /**
     * Sets the codiceTipoStruttura value for this RicercaMovimentoGestione.
     * 
     * @param codiceTipoStruttura
     */
    public void setCodiceTipoStruttura(java.lang.String codiceTipoStruttura) {
        this.codiceTipoStruttura = codiceTipoStruttura;
    }


    /**
     * Gets the numeroProvvedimento value for this RicercaMovimentoGestione.
     * 
     * @return numeroProvvedimento
     */
    public java.lang.Integer getNumeroProvvedimento() {
        return numeroProvvedimento;
    }


    /**
     * Sets the numeroProvvedimento value for this RicercaMovimentoGestione.
     * 
     * @param numeroProvvedimento
     */
    public void setNumeroProvvedimento(java.lang.Integer numeroProvvedimento) {
        this.numeroProvvedimento = numeroProvvedimento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaMovimentoGestione)) return false;
        RicercaMovimentoGestione other = (RicercaMovimentoGestione) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.annoProvvedimento==null && other.getAnnoProvvedimento()==null) || 
             (this.annoProvvedimento!=null &&
              this.annoProvvedimento.equals(other.getAnnoProvvedimento()))) &&
            ((this.codiceStruttura==null && other.getCodiceStruttura()==null) || 
             (this.codiceStruttura!=null &&
              this.codiceStruttura.equals(other.getCodiceStruttura()))) &&
            ((this.codiceTipoProvvedimento==null && other.getCodiceTipoProvvedimento()==null) || 
             (this.codiceTipoProvvedimento!=null &&
              this.codiceTipoProvvedimento.equals(other.getCodiceTipoProvvedimento()))) &&
            ((this.codiceTipoStruttura==null && other.getCodiceTipoStruttura()==null) || 
             (this.codiceTipoStruttura!=null &&
              this.codiceTipoStruttura.equals(other.getCodiceTipoStruttura()))) &&
            ((this.numeroProvvedimento==null && other.getNumeroProvvedimento()==null) || 
             (this.numeroProvvedimento!=null &&
              this.numeroProvvedimento.equals(other.getNumeroProvvedimento())));
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
        if (getAnnoProvvedimento() != null) {
            _hashCode += getAnnoProvvedimento().hashCode();
        }
        if (getCodiceStruttura() != null) {
            _hashCode += getCodiceStruttura().hashCode();
        }
        if (getCodiceTipoProvvedimento() != null) {
            _hashCode += getCodiceTipoProvvedimento().hashCode();
        }
        if (getCodiceTipoStruttura() != null) {
            _hashCode += getCodiceTipoStruttura().hashCode();
        }
        if (getNumeroProvvedimento() != null) {
            _hashCode += getNumeroProvvedimento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RicercaMovimentoGestione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaMovimentoGestione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoProvvedimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoProvvedimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceStruttura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceStruttura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceTipoProvvedimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceTipoProvvedimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceTipoStruttura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceTipoStruttura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroProvvedimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroProvvedimento"));
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
