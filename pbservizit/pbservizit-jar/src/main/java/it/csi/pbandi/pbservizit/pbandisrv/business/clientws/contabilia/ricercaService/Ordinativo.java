/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * Ordinativo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class Ordinativo  implements java.io.Serializable {
    private java.lang.String codiceSoggetto;

    private java.lang.String codiceStato;

    private java.util.Calendar dataEmissione;

    private java.util.Calendar dataQuietanza;

    private java.lang.String denominazioneSoggetto;

    private java.lang.Integer numeroCapitolo;

    private java.lang.Integer numeroOrdinativo;

    public Ordinativo() {
    }

    public Ordinativo(
           java.lang.String codiceSoggetto,
           java.lang.String codiceStato,
           java.util.Calendar dataEmissione,
           java.util.Calendar dataQuietanza,
           java.lang.String denominazioneSoggetto,
           java.lang.Integer numeroCapitolo,
           java.lang.Integer numeroOrdinativo) {
           this.codiceSoggetto = codiceSoggetto;
           this.codiceStato = codiceStato;
           this.dataEmissione = dataEmissione;
           this.dataQuietanza = dataQuietanza;
           this.denominazioneSoggetto = denominazioneSoggetto;
           this.numeroCapitolo = numeroCapitolo;
           this.numeroOrdinativo = numeroOrdinativo;
    }


    /**
     * Gets the codiceSoggetto value for this Ordinativo.
     * 
     * @return codiceSoggetto
     */
    public java.lang.String getCodiceSoggetto() {
        return codiceSoggetto;
    }


    /**
     * Sets the codiceSoggetto value for this Ordinativo.
     * 
     * @param codiceSoggetto
     */
    public void setCodiceSoggetto(java.lang.String codiceSoggetto) {
        this.codiceSoggetto = codiceSoggetto;
    }


    /**
     * Gets the codiceStato value for this Ordinativo.
     * 
     * @return codiceStato
     */
    public java.lang.String getCodiceStato() {
        return codiceStato;
    }


    /**
     * Sets the codiceStato value for this Ordinativo.
     * 
     * @param codiceStato
     */
    public void setCodiceStato(java.lang.String codiceStato) {
        this.codiceStato = codiceStato;
    }


    /**
     * Gets the dataEmissione value for this Ordinativo.
     * 
     * @return dataEmissione
     */
    public java.util.Calendar getDataEmissione() {
        return dataEmissione;
    }


    /**
     * Sets the dataEmissione value for this Ordinativo.
     * 
     * @param dataEmissione
     */
    public void setDataEmissione(java.util.Calendar dataEmissione) {
        this.dataEmissione = dataEmissione;
    }


    /**
     * Gets the dataQuietanza value for this Ordinativo.
     * 
     * @return dataQuietanza
     */
    public java.util.Calendar getDataQuietanza() {
        return dataQuietanza;
    }


    /**
     * Sets the dataQuietanza value for this Ordinativo.
     * 
     * @param dataQuietanza
     */
    public void setDataQuietanza(java.util.Calendar dataQuietanza) {
        this.dataQuietanza = dataQuietanza;
    }


    /**
     * Gets the denominazioneSoggetto value for this Ordinativo.
     * 
     * @return denominazioneSoggetto
     */
    public java.lang.String getDenominazioneSoggetto() {
        return denominazioneSoggetto;
    }


    /**
     * Sets the denominazioneSoggetto value for this Ordinativo.
     * 
     * @param denominazioneSoggetto
     */
    public void setDenominazioneSoggetto(java.lang.String denominazioneSoggetto) {
        this.denominazioneSoggetto = denominazioneSoggetto;
    }


    /**
     * Gets the numeroCapitolo value for this Ordinativo.
     * 
     * @return numeroCapitolo
     */
    public java.lang.Integer getNumeroCapitolo() {
        return numeroCapitolo;
    }


    /**
     * Sets the numeroCapitolo value for this Ordinativo.
     * 
     * @param numeroCapitolo
     */
    public void setNumeroCapitolo(java.lang.Integer numeroCapitolo) {
        this.numeroCapitolo = numeroCapitolo;
    }


    /**
     * Gets the numeroOrdinativo value for this Ordinativo.
     * 
     * @return numeroOrdinativo
     */
    public java.lang.Integer getNumeroOrdinativo() {
        return numeroOrdinativo;
    }


    /**
     * Sets the numeroOrdinativo value for this Ordinativo.
     * 
     * @param numeroOrdinativo
     */
    public void setNumeroOrdinativo(java.lang.Integer numeroOrdinativo) {
        this.numeroOrdinativo = numeroOrdinativo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Ordinativo)) return false;
        Ordinativo other = (Ordinativo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceSoggetto==null && other.getCodiceSoggetto()==null) || 
             (this.codiceSoggetto!=null &&
              this.codiceSoggetto.equals(other.getCodiceSoggetto()))) &&
            ((this.codiceStato==null && other.getCodiceStato()==null) || 
             (this.codiceStato!=null &&
              this.codiceStato.equals(other.getCodiceStato()))) &&
            ((this.dataEmissione==null && other.getDataEmissione()==null) || 
             (this.dataEmissione!=null &&
              this.dataEmissione.equals(other.getDataEmissione()))) &&
            ((this.dataQuietanza==null && other.getDataQuietanza()==null) || 
             (this.dataQuietanza!=null &&
              this.dataQuietanza.equals(other.getDataQuietanza()))) &&
            ((this.denominazioneSoggetto==null && other.getDenominazioneSoggetto()==null) || 
             (this.denominazioneSoggetto!=null &&
              this.denominazioneSoggetto.equals(other.getDenominazioneSoggetto()))) &&
            ((this.numeroCapitolo==null && other.getNumeroCapitolo()==null) || 
             (this.numeroCapitolo!=null &&
              this.numeroCapitolo.equals(other.getNumeroCapitolo()))) &&
            ((this.numeroOrdinativo==null && other.getNumeroOrdinativo()==null) || 
             (this.numeroOrdinativo!=null &&
              this.numeroOrdinativo.equals(other.getNumeroOrdinativo())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCodiceSoggetto() != null) {
            _hashCode += getCodiceSoggetto().hashCode();
        }
        if (getCodiceStato() != null) {
            _hashCode += getCodiceStato().hashCode();
        }
        if (getDataEmissione() != null) {
            _hashCode += getDataEmissione().hashCode();
        }
        if (getDataQuietanza() != null) {
            _hashCode += getDataQuietanza().hashCode();
        }
        if (getDenominazioneSoggetto() != null) {
            _hashCode += getDenominazioneSoggetto().hashCode();
        }
        if (getNumeroCapitolo() != null) {
            _hashCode += getNumeroCapitolo().hashCode();
        }
        if (getNumeroOrdinativo() != null) {
            _hashCode += getNumeroOrdinativo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Ordinativo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "ordinativo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceSoggetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceSoggetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceStato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceStato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataEmissione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataEmissione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataQuietanza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataQuietanza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazioneSoggetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazioneSoggetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("numeroOrdinativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroOrdinativo"));
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
