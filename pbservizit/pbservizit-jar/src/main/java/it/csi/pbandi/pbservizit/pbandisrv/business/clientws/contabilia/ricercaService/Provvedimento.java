/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * Provvedimento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class Provvedimento  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.EntitaBase  implements java.io.Serializable {
    private java.lang.Integer annoProvvedimento;

    private java.lang.String codiceTipoProvvedimento;

    private java.lang.Integer numeroProvvedimento;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.StrutturaAmministrativa sac;

    private java.lang.String statoProvvedimento;

    public Provvedimento() {
    }

    public Provvedimento(
           java.lang.String codice,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato,
           java.lang.Integer annoProvvedimento,
           java.lang.String codiceTipoProvvedimento,
           java.lang.Integer numeroProvvedimento,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.StrutturaAmministrativa sac,
           java.lang.String statoProvvedimento) {
        super(
            codice,
            stato);
        this.annoProvvedimento = annoProvvedimento;
        this.codiceTipoProvvedimento = codiceTipoProvvedimento;
        this.numeroProvvedimento = numeroProvvedimento;
        this.sac = sac;
        this.statoProvvedimento = statoProvvedimento;
    }


    /**
     * Gets the annoProvvedimento value for this Provvedimento.
     * 
     * @return annoProvvedimento
     */
    public java.lang.Integer getAnnoProvvedimento() {
        return annoProvvedimento;
    }


    /**
     * Sets the annoProvvedimento value for this Provvedimento.
     * 
     * @param annoProvvedimento
     */
    public void setAnnoProvvedimento(java.lang.Integer annoProvvedimento) {
        this.annoProvvedimento = annoProvvedimento;
    }


    /**
     * Gets the codiceTipoProvvedimento value for this Provvedimento.
     * 
     * @return codiceTipoProvvedimento
     */
    public java.lang.String getCodiceTipoProvvedimento() {
        return codiceTipoProvvedimento;
    }


    /**
     * Sets the codiceTipoProvvedimento value for this Provvedimento.
     * 
     * @param codiceTipoProvvedimento
     */
    public void setCodiceTipoProvvedimento(java.lang.String codiceTipoProvvedimento) {
        this.codiceTipoProvvedimento = codiceTipoProvvedimento;
    }


    /**
     * Gets the numeroProvvedimento value for this Provvedimento.
     * 
     * @return numeroProvvedimento
     */
    public java.lang.Integer getNumeroProvvedimento() {
        return numeroProvvedimento;
    }


    /**
     * Sets the numeroProvvedimento value for this Provvedimento.
     * 
     * @param numeroProvvedimento
     */
    public void setNumeroProvvedimento(java.lang.Integer numeroProvvedimento) {
        this.numeroProvvedimento = numeroProvvedimento;
    }


    /**
     * Gets the sac value for this Provvedimento.
     * 
     * @return sac
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.StrutturaAmministrativa getSac() {
        return sac;
    }


    /**
     * Sets the sac value for this Provvedimento.
     * 
     * @param sac
     */
    public void setSac(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.StrutturaAmministrativa sac) {
        this.sac = sac;
    }


    /**
     * Gets the statoProvvedimento value for this Provvedimento.
     * 
     * @return statoProvvedimento
     */
    public java.lang.String getStatoProvvedimento() {
        return statoProvvedimento;
    }


    /**
     * Sets the statoProvvedimento value for this Provvedimento.
     * 
     * @param statoProvvedimento
     */
    public void setStatoProvvedimento(java.lang.String statoProvvedimento) {
        this.statoProvvedimento = statoProvvedimento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Provvedimento)) return false;
        Provvedimento other = (Provvedimento) obj;
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
            ((this.codiceTipoProvvedimento==null && other.getCodiceTipoProvvedimento()==null) || 
             (this.codiceTipoProvvedimento!=null &&
              this.codiceTipoProvvedimento.equals(other.getCodiceTipoProvvedimento()))) &&
            ((this.numeroProvvedimento==null && other.getNumeroProvvedimento()==null) || 
             (this.numeroProvvedimento!=null &&
              this.numeroProvvedimento.equals(other.getNumeroProvvedimento()))) &&
            ((this.sac==null && other.getSac()==null) || 
             (this.sac!=null &&
              this.sac.equals(other.getSac()))) &&
            ((this.statoProvvedimento==null && other.getStatoProvvedimento()==null) || 
             (this.statoProvvedimento!=null &&
              this.statoProvvedimento.equals(other.getStatoProvvedimento())));
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
        if (getCodiceTipoProvvedimento() != null) {
            _hashCode += getCodiceTipoProvvedimento().hashCode();
        }
        if (getNumeroProvvedimento() != null) {
            _hashCode += getNumeroProvvedimento().hashCode();
        }
        if (getSac() != null) {
            _hashCode += getSac().hashCode();
        }
        if (getStatoProvvedimento() != null) {
            _hashCode += getStatoProvvedimento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Provvedimento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "provvedimento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoProvvedimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoProvvedimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("numeroProvvedimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroProvvedimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sac");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sac"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "strutturaAmministrativa"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statoProvvedimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statoProvvedimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
