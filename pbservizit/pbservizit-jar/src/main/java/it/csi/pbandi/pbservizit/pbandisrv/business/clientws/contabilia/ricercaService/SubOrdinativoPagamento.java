/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * SubOrdinativoPagamento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class SubOrdinativoPagamento  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Ordinativo  implements java.io.Serializable {
    private java.lang.Integer annoImpegno;

    private java.lang.Integer annoLiquidazione;

    private java.math.BigDecimal importo;

    private java.lang.Integer numeroImpegno;

    private java.lang.Integer numeroLiquidazione;

    public SubOrdinativoPagamento() {
    }

    public SubOrdinativoPagamento(
           java.lang.String codiceSoggetto,
           java.lang.String codiceStato,
           java.util.Calendar dataEmissione,
           java.util.Calendar dataQuietanza,
           java.lang.String denominazioneSoggetto,
           java.lang.Integer numeroCapitolo,
           java.lang.Integer numeroOrdinativo,
           java.lang.Integer annoImpegno,
           java.lang.Integer annoLiquidazione,
           java.math.BigDecimal importo,
           java.lang.Integer numeroImpegno,
           java.lang.Integer numeroLiquidazione) {
        super(
            codiceSoggetto,
            codiceStato,
            dataEmissione,
            dataQuietanza,
            denominazioneSoggetto,
            numeroCapitolo,
            numeroOrdinativo);
        this.annoImpegno = annoImpegno;
        this.annoLiquidazione = annoLiquidazione;
        this.importo = importo;
        this.numeroImpegno = numeroImpegno;
        this.numeroLiquidazione = numeroLiquidazione;
    }


    /**
     * Gets the annoImpegno value for this SubOrdinativoPagamento.
     * 
     * @return annoImpegno
     */
    public java.lang.Integer getAnnoImpegno() {
        return annoImpegno;
    }


    /**
     * Sets the annoImpegno value for this SubOrdinativoPagamento.
     * 
     * @param annoImpegno
     */
    public void setAnnoImpegno(java.lang.Integer annoImpegno) {
        this.annoImpegno = annoImpegno;
    }


    /**
     * Gets the annoLiquidazione value for this SubOrdinativoPagamento.
     * 
     * @return annoLiquidazione
     */
    public java.lang.Integer getAnnoLiquidazione() {
        return annoLiquidazione;
    }


    /**
     * Sets the annoLiquidazione value for this SubOrdinativoPagamento.
     * 
     * @param annoLiquidazione
     */
    public void setAnnoLiquidazione(java.lang.Integer annoLiquidazione) {
        this.annoLiquidazione = annoLiquidazione;
    }


    /**
     * Gets the importo value for this SubOrdinativoPagamento.
     * 
     * @return importo
     */
    public java.math.BigDecimal getImporto() {
        return importo;
    }


    /**
     * Sets the importo value for this SubOrdinativoPagamento.
     * 
     * @param importo
     */
    public void setImporto(java.math.BigDecimal importo) {
        this.importo = importo;
    }


    /**
     * Gets the numeroImpegno value for this SubOrdinativoPagamento.
     * 
     * @return numeroImpegno
     */
    public java.lang.Integer getNumeroImpegno() {
        return numeroImpegno;
    }


    /**
     * Sets the numeroImpegno value for this SubOrdinativoPagamento.
     * 
     * @param numeroImpegno
     */
    public void setNumeroImpegno(java.lang.Integer numeroImpegno) {
        this.numeroImpegno = numeroImpegno;
    }


    /**
     * Gets the numeroLiquidazione value for this SubOrdinativoPagamento.
     * 
     * @return numeroLiquidazione
     */
    public java.lang.Integer getNumeroLiquidazione() {
        return numeroLiquidazione;
    }


    /**
     * Sets the numeroLiquidazione value for this SubOrdinativoPagamento.
     * 
     * @param numeroLiquidazione
     */
    public void setNumeroLiquidazione(java.lang.Integer numeroLiquidazione) {
        this.numeroLiquidazione = numeroLiquidazione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SubOrdinativoPagamento)) return false;
        SubOrdinativoPagamento other = (SubOrdinativoPagamento) obj;
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
            ((this.importo==null && other.getImporto()==null) || 
             (this.importo!=null &&
              this.importo.equals(other.getImporto()))) &&
            ((this.numeroImpegno==null && other.getNumeroImpegno()==null) || 
             (this.numeroImpegno!=null &&
              this.numeroImpegno.equals(other.getNumeroImpegno()))) &&
            ((this.numeroLiquidazione==null && other.getNumeroLiquidazione()==null) || 
             (this.numeroLiquidazione!=null &&
              this.numeroLiquidazione.equals(other.getNumeroLiquidazione())));
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
        if (getImporto() != null) {
            _hashCode += getImporto().hashCode();
        }
        if (getNumeroImpegno() != null) {
            _hashCode += getNumeroImpegno().hashCode();
        }
        if (getNumeroLiquidazione() != null) {
            _hashCode += getNumeroLiquidazione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SubOrdinativoPagamento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "subOrdinativoPagamento"));
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
        elemField.setFieldName("importo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importo"));
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
