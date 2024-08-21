/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * Sede.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class Sede  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.EntitaBase  implements java.io.Serializable {
    private java.lang.String descrizione;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Recapito recapito;

    public Sede() {
    }

    public Sede(
           java.lang.String codice,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato,
           java.lang.String descrizione,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Recapito recapito) {
        super(
            codice,
            stato);
        this.descrizione = descrizione;
        this.recapito = recapito;
    }


    /**
     * Gets the descrizione value for this Sede.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this Sede.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the recapito value for this Sede.
     * 
     * @return recapito
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Recapito getRecapito() {
        return recapito;
    }


    /**
     * Sets the recapito value for this Sede.
     * 
     * @param recapito
     */
    public void setRecapito(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Recapito recapito) {
        this.recapito = recapito;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Sede)) return false;
        Sede other = (Sede) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.recapito==null && other.getRecapito()==null) || 
             (this.recapito!=null &&
              this.recapito.equals(other.getRecapito())));
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
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getRecapito() != null) {
            _hashCode += getRecapito().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Sede.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "sede"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recapito");
        elemField.setXmlName(new javax.xml.namespace.QName("", "recapito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "recapito"));
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
