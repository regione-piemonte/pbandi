/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * Liquidazione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class Liquidazione  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.EntitaBase  implements java.io.Serializable {
    private java.lang.Integer annoImpegno;

    private java.lang.Integer annoLiquidazione;

    private java.math.BigDecimal importoLiquidazione;

    private java.lang.Integer numeroImpegno;

    private java.lang.Integer numeroLiquidazione;

    private java.lang.Integer numeroSubImpegno;

    public Liquidazione() {
    }

    public Liquidazione(
           java.lang.String codice,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato,
           java.lang.Integer annoImpegno,
           java.lang.Integer annoLiquidazione,
           java.math.BigDecimal importoLiquidazione,
           java.lang.Integer numeroImpegno,
           java.lang.Integer numeroLiquidazione,
           java.lang.Integer numeroSubImpegno) {
        super(
            codice,
            stato);
        this.annoImpegno = annoImpegno;
        this.annoLiquidazione = annoLiquidazione;
        this.importoLiquidazione = importoLiquidazione;
        this.numeroImpegno = numeroImpegno;
        this.numeroLiquidazione = numeroLiquidazione;
        this.numeroSubImpegno = numeroSubImpegno;
    }


    /**
     * Gets the annoImpegno value for this Liquidazione.
     * 
     * @return annoImpegno
     */
    public java.lang.Integer getAnnoImpegno() {
        return annoImpegno;
    }


    /**
     * Sets the annoImpegno value for this Liquidazione.
     * 
     * @param annoImpegno
     */
    public void setAnnoImpegno(java.lang.Integer annoImpegno) {
        this.annoImpegno = annoImpegno;
    }


    /**
     * Gets the annoLiquidazione value for this Liquidazione.
     * 
     * @return annoLiquidazione
     */
    public java.lang.Integer getAnnoLiquidazione() {
        return annoLiquidazione;
    }


    /**
     * Sets the annoLiquidazione value for this Liquidazione.
     * 
     * @param annoLiquidazione
     */
    public void setAnnoLiquidazione(java.lang.Integer annoLiquidazione) {
        this.annoLiquidazione = annoLiquidazione;
    }


    /**
     * Gets the importoLiquidazione value for this Liquidazione.
     * 
     * @return importoLiquidazione
     */
    public java.math.BigDecimal getImportoLiquidazione() {
        return importoLiquidazione;
    }


    /**
     * Sets the importoLiquidazione value for this Liquidazione.
     * 
     * @param importoLiquidazione
     */
    public void setImportoLiquidazione(java.math.BigDecimal importoLiquidazione) {
        this.importoLiquidazione = importoLiquidazione;
    }


    /**
     * Gets the numeroImpegno value for this Liquidazione.
     * 
     * @return numeroImpegno
     */
    public java.lang.Integer getNumeroImpegno() {
        return numeroImpegno;
    }


    /**
     * Sets the numeroImpegno value for this Liquidazione.
     * 
     * @param numeroImpegno
     */
    public void setNumeroImpegno(java.lang.Integer numeroImpegno) {
        this.numeroImpegno = numeroImpegno;
    }


    /**
     * Gets the numeroLiquidazione value for this Liquidazione.
     * 
     * @return numeroLiquidazione
     */
    public java.lang.Integer getNumeroLiquidazione() {
        return numeroLiquidazione;
    }


    /**
     * Sets the numeroLiquidazione value for this Liquidazione.
     * 
     * @param numeroLiquidazione
     */
    public void setNumeroLiquidazione(java.lang.Integer numeroLiquidazione) {
        this.numeroLiquidazione = numeroLiquidazione;
    }


    /**
     * Gets the numeroSubImpegno value for this Liquidazione.
     * 
     * @return numeroSubImpegno
     */
    public java.lang.Integer getNumeroSubImpegno() {
        return numeroSubImpegno;
    }


    /**
     * Sets the numeroSubImpegno value for this Liquidazione.
     * 
     * @param numeroSubImpegno
     */
    public void setNumeroSubImpegno(java.lang.Integer numeroSubImpegno) {
        this.numeroSubImpegno = numeroSubImpegno;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Liquidazione)) return false;
        Liquidazione other = (Liquidazione) obj;
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
            ((this.annoLiquidazione==null && other.getAnnoLiquidazione()==null) || 
             (this.annoLiquidazione!=null &&
              this.annoLiquidazione.equals(other.getAnnoLiquidazione()))) &&
            ((this.importoLiquidazione==null && other.getImportoLiquidazione()==null) || 
             (this.importoLiquidazione!=null &&
              this.importoLiquidazione.equals(other.getImportoLiquidazione()))) &&
            ((this.numeroImpegno==null && other.getNumeroImpegno()==null) || 
             (this.numeroImpegno!=null &&
              this.numeroImpegno.equals(other.getNumeroImpegno()))) &&
            ((this.numeroLiquidazione==null && other.getNumeroLiquidazione()==null) || 
             (this.numeroLiquidazione!=null &&
              this.numeroLiquidazione.equals(other.getNumeroLiquidazione()))) &&
            ((this.numeroSubImpegno==null && other.getNumeroSubImpegno()==null) || 
             (this.numeroSubImpegno!=null &&
              this.numeroSubImpegno.equals(other.getNumeroSubImpegno())));
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
        if (getAnnoLiquidazione() != null) {
            _hashCode += getAnnoLiquidazione().hashCode();
        }
        if (getImportoLiquidazione() != null) {
            _hashCode += getImportoLiquidazione().hashCode();
        }
        if (getNumeroImpegno() != null) {
            _hashCode += getNumeroImpegno().hashCode();
        }
        if (getNumeroLiquidazione() != null) {
            _hashCode += getNumeroLiquidazione().hashCode();
        }
        if (getNumeroSubImpegno() != null) {
            _hashCode += getNumeroSubImpegno().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Liquidazione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "liquidazione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoImpegno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoImpegno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoLiquidazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoLiquidazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importoLiquidazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importoLiquidazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroLiquidazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroLiquidazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroSubImpegno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroSubImpegno"));
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
