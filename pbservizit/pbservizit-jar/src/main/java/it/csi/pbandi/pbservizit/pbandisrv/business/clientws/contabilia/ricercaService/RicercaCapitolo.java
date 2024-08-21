/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * RicercaCapitolo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class RicercaCapitolo  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaPaginataRequest  implements java.io.Serializable {
    private java.lang.Integer numeroArticolo;

    private java.lang.Integer numeroCapitolo;

    private java.lang.Integer numeroUEB;

    public RicercaCapitolo() {
    }

    public RicercaCapitolo(
           java.lang.Integer annoBilancio,
           java.lang.String codiceEnte,
           java.lang.String codiceFruitore,
           java.lang.Integer numeroElementiPerPagina,
           java.lang.Integer numeroPagina,
           java.lang.Integer numeroArticolo,
           java.lang.Integer numeroCapitolo,
           java.lang.Integer numeroUEB) {
        super(
            annoBilancio,
            codiceEnte,
            codiceFruitore,
            numeroElementiPerPagina,
            numeroPagina);
        this.numeroArticolo = numeroArticolo;
        this.numeroCapitolo = numeroCapitolo;
        this.numeroUEB = numeroUEB;
    }


    /**
     * Gets the numeroArticolo value for this RicercaCapitolo.
     * 
     * @return numeroArticolo
     */
    public java.lang.Integer getNumeroArticolo() {
        return numeroArticolo;
    }


    /**
     * Sets the numeroArticolo value for this RicercaCapitolo.
     * 
     * @param numeroArticolo
     */
    public void setNumeroArticolo(java.lang.Integer numeroArticolo) {
        this.numeroArticolo = numeroArticolo;
    }


    /**
     * Gets the numeroCapitolo value for this RicercaCapitolo.
     * 
     * @return numeroCapitolo
     */
    public java.lang.Integer getNumeroCapitolo() {
        return numeroCapitolo;
    }


    /**
     * Sets the numeroCapitolo value for this RicercaCapitolo.
     * 
     * @param numeroCapitolo
     */
    public void setNumeroCapitolo(java.lang.Integer numeroCapitolo) {
        this.numeroCapitolo = numeroCapitolo;
    }


    /**
     * Gets the numeroUEB value for this RicercaCapitolo.
     * 
     * @return numeroUEB
     */
    public java.lang.Integer getNumeroUEB() {
        return numeroUEB;
    }


    /**
     * Sets the numeroUEB value for this RicercaCapitolo.
     * 
     * @param numeroUEB
     */
    public void setNumeroUEB(java.lang.Integer numeroUEB) {
        this.numeroUEB = numeroUEB;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaCapitolo)) return false;
        RicercaCapitolo other = (RicercaCapitolo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.numeroArticolo==null && other.getNumeroArticolo()==null) || 
             (this.numeroArticolo!=null &&
              this.numeroArticolo.equals(other.getNumeroArticolo()))) &&
            ((this.numeroCapitolo==null && other.getNumeroCapitolo()==null) || 
             (this.numeroCapitolo!=null &&
              this.numeroCapitolo.equals(other.getNumeroCapitolo()))) &&
            ((this.numeroUEB==null && other.getNumeroUEB()==null) || 
             (this.numeroUEB!=null &&
              this.numeroUEB.equals(other.getNumeroUEB())));
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
        if (getNumeroArticolo() != null) {
            _hashCode += getNumeroArticolo().hashCode();
        }
        if (getNumeroCapitolo() != null) {
            _hashCode += getNumeroCapitolo().hashCode();
        }
        if (getNumeroUEB() != null) {
            _hashCode += getNumeroUEB().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RicercaCapitolo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitolo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroArticolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroArticolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroCapitolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroCapitolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroUEB");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroUEB"));
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
