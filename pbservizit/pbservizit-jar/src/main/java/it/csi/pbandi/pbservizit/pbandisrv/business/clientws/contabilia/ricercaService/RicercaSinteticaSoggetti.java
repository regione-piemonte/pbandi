/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * RicercaSinteticaSoggetti.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class RicercaSinteticaSoggetti  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.BaseRicercaSoggetti  implements java.io.Serializable {
    private java.lang.String denominazione;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato;

    public RicercaSinteticaSoggetti() {
    }

    public RicercaSinteticaSoggetti(
           java.lang.Integer annoBilancio,
           java.lang.String codiceEnte,
           java.lang.String codiceFruitore,
           java.lang.String codice,
           java.lang.String codiceFiscale,
           java.lang.String partitaIva,
           java.lang.String denominazione,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato) {
        super(
            annoBilancio,
            codiceEnte,
            codiceFruitore,
            codice,
            codiceFiscale,
            partitaIva);
        this.denominazione = denominazione;
        this.stato = stato;
    }


    /**
     * Gets the denominazione value for this RicercaSinteticaSoggetti.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this RicercaSinteticaSoggetti.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the stato value for this RicercaSinteticaSoggetti.
     * 
     * @return stato
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato getStato() {
        return stato;
    }


    /**
     * Sets the stato value for this RicercaSinteticaSoggetti.
     * 
     * @param stato
     */
    public void setStato(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato) {
        this.stato = stato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaSinteticaSoggetti)) return false;
        RicercaSinteticaSoggetti other = (RicercaSinteticaSoggetti) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.denominazione==null && other.getDenominazione()==null) || 
             (this.denominazione!=null &&
              this.denominazione.equals(other.getDenominazione()))) &&
            ((this.stato==null && other.getStato()==null) || 
             (this.stato!=null &&
              this.stato.equals(other.getStato())));
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
        if (getDenominazione() != null) {
            _hashCode += getDenominazione().hashCode();
        }
        if (getStato() != null) {
            _hashCode += getStato().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RicercaSinteticaSoggetti.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaSinteticaSoggetti"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "stato"));
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
