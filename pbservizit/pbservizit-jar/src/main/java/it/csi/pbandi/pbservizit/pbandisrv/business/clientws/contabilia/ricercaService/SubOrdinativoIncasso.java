/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * SubOrdinativoIncasso.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class SubOrdinativoIncasso  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Ordinativo  implements java.io.Serializable {
    private java.lang.Integer annoAccertamento;

    private java.math.BigDecimal importo;

    private java.lang.Integer numeroAccertamento;

    public SubOrdinativoIncasso() {
    }

    public SubOrdinativoIncasso(
           java.lang.String codiceSoggetto,
           java.lang.String codiceStato,
           java.util.Calendar dataEmissione,
           java.util.Calendar dataQuietanza,
           java.lang.String denominazioneSoggetto,
           java.lang.Integer numeroCapitolo,
           java.lang.Integer numeroOrdinativo,
           java.lang.Integer annoAccertamento,
           java.math.BigDecimal importo,
           java.lang.Integer numeroAccertamento) {
        super(
            codiceSoggetto,
            codiceStato,
            dataEmissione,
            dataQuietanza,
            denominazioneSoggetto,
            numeroCapitolo,
            numeroOrdinativo);
        this.annoAccertamento = annoAccertamento;
        this.importo = importo;
        this.numeroAccertamento = numeroAccertamento;
    }


    /**
     * Gets the annoAccertamento value for this SubOrdinativoIncasso.
     * 
     * @return annoAccertamento
     */
    public java.lang.Integer getAnnoAccertamento() {
        return annoAccertamento;
    }


    /**
     * Sets the annoAccertamento value for this SubOrdinativoIncasso.
     * 
     * @param annoAccertamento
     */
    public void setAnnoAccertamento(java.lang.Integer annoAccertamento) {
        this.annoAccertamento = annoAccertamento;
    }


    /**
     * Gets the importo value for this SubOrdinativoIncasso.
     * 
     * @return importo
     */
    public java.math.BigDecimal getImporto() {
        return importo;
    }


    /**
     * Sets the importo value for this SubOrdinativoIncasso.
     * 
     * @param importo
     */
    public void setImporto(java.math.BigDecimal importo) {
        this.importo = importo;
    }


    /**
     * Gets the numeroAccertamento value for this SubOrdinativoIncasso.
     * 
     * @return numeroAccertamento
     */
    public java.lang.Integer getNumeroAccertamento() {
        return numeroAccertamento;
    }


    /**
     * Sets the numeroAccertamento value for this SubOrdinativoIncasso.
     * 
     * @param numeroAccertamento
     */
    public void setNumeroAccertamento(java.lang.Integer numeroAccertamento) {
        this.numeroAccertamento = numeroAccertamento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SubOrdinativoIncasso)) return false;
        SubOrdinativoIncasso other = (SubOrdinativoIncasso) obj;
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
            ((this.importo==null && other.getImporto()==null) || 
             (this.importo!=null &&
              this.importo.equals(other.getImporto()))) &&
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
        if (getImporto() != null) {
            _hashCode += getImporto().hashCode();
        }
        if (getNumeroAccertamento() != null) {
            _hashCode += getNumeroAccertamento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SubOrdinativoIncasso.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "subOrdinativoIncasso"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoAccertamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoAccertamento"));
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
