/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * ImportoCapitoloEntrataGestione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class ImportoCapitoloEntrataGestione  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Importo  implements java.io.Serializable {
    private java.math.BigDecimal disponibilitaAccertare;

    private java.math.BigDecimal stanziamento;

    private java.math.BigDecimal stanziamentoCassa;

    private java.math.BigDecimal stanziamentoResiduo;

    public ImportoCapitoloEntrataGestione() {
    }

    public ImportoCapitoloEntrataGestione(
           java.lang.String codice,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato,
           java.lang.Integer annoCompetenza,
           java.math.BigDecimal disponibilitaAccertare,
           java.math.BigDecimal stanziamento,
           java.math.BigDecimal stanziamentoCassa,
           java.math.BigDecimal stanziamentoResiduo) {
        super(
            codice,
            stato,
            annoCompetenza);
        this.disponibilitaAccertare = disponibilitaAccertare;
        this.stanziamento = stanziamento;
        this.stanziamentoCassa = stanziamentoCassa;
        this.stanziamentoResiduo = stanziamentoResiduo;
    }


    /**
     * Gets the disponibilitaAccertare value for this ImportoCapitoloEntrataGestione.
     * 
     * @return disponibilitaAccertare
     */
    public java.math.BigDecimal getDisponibilitaAccertare() {
        return disponibilitaAccertare;
    }


    /**
     * Sets the disponibilitaAccertare value for this ImportoCapitoloEntrataGestione.
     * 
     * @param disponibilitaAccertare
     */
    public void setDisponibilitaAccertare(java.math.BigDecimal disponibilitaAccertare) {
        this.disponibilitaAccertare = disponibilitaAccertare;
    }


    /**
     * Gets the stanziamento value for this ImportoCapitoloEntrataGestione.
     * 
     * @return stanziamento
     */
    public java.math.BigDecimal getStanziamento() {
        return stanziamento;
    }


    /**
     * Sets the stanziamento value for this ImportoCapitoloEntrataGestione.
     * 
     * @param stanziamento
     */
    public void setStanziamento(java.math.BigDecimal stanziamento) {
        this.stanziamento = stanziamento;
    }


    /**
     * Gets the stanziamentoCassa value for this ImportoCapitoloEntrataGestione.
     * 
     * @return stanziamentoCassa
     */
    public java.math.BigDecimal getStanziamentoCassa() {
        return stanziamentoCassa;
    }


    /**
     * Sets the stanziamentoCassa value for this ImportoCapitoloEntrataGestione.
     * 
     * @param stanziamentoCassa
     */
    public void setStanziamentoCassa(java.math.BigDecimal stanziamentoCassa) {
        this.stanziamentoCassa = stanziamentoCassa;
    }


    /**
     * Gets the stanziamentoResiduo value for this ImportoCapitoloEntrataGestione.
     * 
     * @return stanziamentoResiduo
     */
    public java.math.BigDecimal getStanziamentoResiduo() {
        return stanziamentoResiduo;
    }


    /**
     * Sets the stanziamentoResiduo value for this ImportoCapitoloEntrataGestione.
     * 
     * @param stanziamentoResiduo
     */
    public void setStanziamentoResiduo(java.math.BigDecimal stanziamentoResiduo) {
        this.stanziamentoResiduo = stanziamentoResiduo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ImportoCapitoloEntrataGestione)) return false;
        ImportoCapitoloEntrataGestione other = (ImportoCapitoloEntrataGestione) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.disponibilitaAccertare==null && other.getDisponibilitaAccertare()==null) || 
             (this.disponibilitaAccertare!=null &&
              this.disponibilitaAccertare.equals(other.getDisponibilitaAccertare()))) &&
            ((this.stanziamento==null && other.getStanziamento()==null) || 
             (this.stanziamento!=null &&
              this.stanziamento.equals(other.getStanziamento()))) &&
            ((this.stanziamentoCassa==null && other.getStanziamentoCassa()==null) || 
             (this.stanziamentoCassa!=null &&
              this.stanziamentoCassa.equals(other.getStanziamentoCassa()))) &&
            ((this.stanziamentoResiduo==null && other.getStanziamentoResiduo()==null) || 
             (this.stanziamentoResiduo!=null &&
              this.stanziamentoResiduo.equals(other.getStanziamentoResiduo())));
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
        if (getDisponibilitaAccertare() != null) {
            _hashCode += getDisponibilitaAccertare().hashCode();
        }
        if (getStanziamento() != null) {
            _hashCode += getStanziamento().hashCode();
        }
        if (getStanziamentoCassa() != null) {
            _hashCode += getStanziamentoCassa().hashCode();
        }
        if (getStanziamentoResiduo() != null) {
            _hashCode += getStanziamentoResiduo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ImportoCapitoloEntrataGestione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "importoCapitoloEntrataGestione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disponibilitaAccertare");
        elemField.setXmlName(new javax.xml.namespace.QName("", "disponibilitaAccertare"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stanziamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stanziamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stanziamentoCassa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stanziamentoCassa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stanziamentoResiduo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stanziamentoResiduo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
