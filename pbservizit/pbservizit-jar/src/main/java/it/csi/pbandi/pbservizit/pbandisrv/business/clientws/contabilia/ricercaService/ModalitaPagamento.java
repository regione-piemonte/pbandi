/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * ModalitaPagamento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class ModalitaPagamento  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.EntitaCodificataBase  implements java.io.Serializable {
    private java.lang.String codiceTipoAccredito;

    private java.lang.String descrizioneTipoAccredito;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Sede sede;

    public ModalitaPagamento() {
    }

    public ModalitaPagamento(
           java.lang.String codice,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato,
           java.lang.String descrizione,
           java.lang.String codiceTipoAccredito,
           java.lang.String descrizioneTipoAccredito,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Sede sede) {
        super(
            codice,
            stato,
            descrizione);
        this.codiceTipoAccredito = codiceTipoAccredito;
        this.descrizioneTipoAccredito = descrizioneTipoAccredito;
        this.sede = sede;
    }


    /**
     * Gets the codiceTipoAccredito value for this ModalitaPagamento.
     * 
     * @return codiceTipoAccredito
     */
    public java.lang.String getCodiceTipoAccredito() {
        return codiceTipoAccredito;
    }


    /**
     * Sets the codiceTipoAccredito value for this ModalitaPagamento.
     * 
     * @param codiceTipoAccredito
     */
    public void setCodiceTipoAccredito(java.lang.String codiceTipoAccredito) {
        this.codiceTipoAccredito = codiceTipoAccredito;
    }


    /**
     * Gets the descrizioneTipoAccredito value for this ModalitaPagamento.
     * 
     * @return descrizioneTipoAccredito
     */
    public java.lang.String getDescrizioneTipoAccredito() {
        return descrizioneTipoAccredito;
    }


    /**
     * Sets the descrizioneTipoAccredito value for this ModalitaPagamento.
     * 
     * @param descrizioneTipoAccredito
     */
    public void setDescrizioneTipoAccredito(java.lang.String descrizioneTipoAccredito) {
        this.descrizioneTipoAccredito = descrizioneTipoAccredito;
    }


    /**
     * Gets the sede value for this ModalitaPagamento.
     * 
     * @return sede
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Sede getSede() {
        return sede;
    }


    /**
     * Sets the sede value for this ModalitaPagamento.
     * 
     * @param sede
     */
    public void setSede(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Sede sede) {
        this.sede = sede;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ModalitaPagamento)) return false;
        ModalitaPagamento other = (ModalitaPagamento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codiceTipoAccredito==null && other.getCodiceTipoAccredito()==null) || 
             (this.codiceTipoAccredito!=null &&
              this.codiceTipoAccredito.equals(other.getCodiceTipoAccredito()))) &&
            ((this.descrizioneTipoAccredito==null && other.getDescrizioneTipoAccredito()==null) || 
             (this.descrizioneTipoAccredito!=null &&
              this.descrizioneTipoAccredito.equals(other.getDescrizioneTipoAccredito()))) &&
            ((this.sede==null && other.getSede()==null) || 
             (this.sede!=null &&
              this.sede.equals(other.getSede())));
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
        if (getCodiceTipoAccredito() != null) {
            _hashCode += getCodiceTipoAccredito().hashCode();
        }
        if (getDescrizioneTipoAccredito() != null) {
            _hashCode += getDescrizioneTipoAccredito().hashCode();
        }
        if (getSede() != null) {
            _hashCode += getSede().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ModalitaPagamento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "modalitaPagamento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceTipoAccredito");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceTipoAccredito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneTipoAccredito");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneTipoAccredito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sede");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sede"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "sede"));
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
