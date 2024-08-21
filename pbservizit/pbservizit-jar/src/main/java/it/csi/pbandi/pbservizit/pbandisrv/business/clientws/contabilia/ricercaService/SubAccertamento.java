/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * SubAccertamento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class SubAccertamento  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.MovimentoGestione  implements java.io.Serializable {
    private java.lang.Integer annoSubAccertamento;

    private java.math.BigDecimal disponibilitaIncassare;

    private java.lang.Integer numeroSubAccertamento;

    public SubAccertamento() {
    }

    public SubAccertamento(
           java.lang.String codice,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato,
           java.lang.String descrizione,
           java.lang.String cig,
           java.lang.String codiceSoggetto,
           java.lang.String cup,
           java.math.BigDecimal importo,
           java.lang.Integer numeroArticolo,
           java.lang.Integer numeroCapitolo,
           java.lang.Integer numeroUEB,
           java.lang.Boolean parereFinanziario,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.PianoDeiContiFinanziario pdc,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Provvedimento provvedimento,
           java.lang.Integer annoSubAccertamento,
           java.math.BigDecimal disponibilitaIncassare,
           java.lang.Integer numeroSubAccertamento) {
        super(
            codice,
            stato,
            descrizione,
            cig,
            codiceSoggetto,
            cup,
            importo,
            numeroArticolo,
            numeroCapitolo,
            numeroUEB,
            parereFinanziario,
            pdc,
            provvedimento);
        this.annoSubAccertamento = annoSubAccertamento;
        this.disponibilitaIncassare = disponibilitaIncassare;
        this.numeroSubAccertamento = numeroSubAccertamento;
    }


    /**
     * Gets the annoSubAccertamento value for this SubAccertamento.
     * 
     * @return annoSubAccertamento
     */
    public java.lang.Integer getAnnoSubAccertamento() {
        return annoSubAccertamento;
    }


    /**
     * Sets the annoSubAccertamento value for this SubAccertamento.
     * 
     * @param annoSubAccertamento
     */
    public void setAnnoSubAccertamento(java.lang.Integer annoSubAccertamento) {
        this.annoSubAccertamento = annoSubAccertamento;
    }


    /**
     * Gets the disponibilitaIncassare value for this SubAccertamento.
     * 
     * @return disponibilitaIncassare
     */
    public java.math.BigDecimal getDisponibilitaIncassare() {
        return disponibilitaIncassare;
    }


    /**
     * Sets the disponibilitaIncassare value for this SubAccertamento.
     * 
     * @param disponibilitaIncassare
     */
    public void setDisponibilitaIncassare(java.math.BigDecimal disponibilitaIncassare) {
        this.disponibilitaIncassare = disponibilitaIncassare;
    }


    /**
     * Gets the numeroSubAccertamento value for this SubAccertamento.
     * 
     * @return numeroSubAccertamento
     */
    public java.lang.Integer getNumeroSubAccertamento() {
        return numeroSubAccertamento;
    }


    /**
     * Sets the numeroSubAccertamento value for this SubAccertamento.
     * 
     * @param numeroSubAccertamento
     */
    public void setNumeroSubAccertamento(java.lang.Integer numeroSubAccertamento) {
        this.numeroSubAccertamento = numeroSubAccertamento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SubAccertamento)) return false;
        SubAccertamento other = (SubAccertamento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.annoSubAccertamento==null && other.getAnnoSubAccertamento()==null) || 
             (this.annoSubAccertamento!=null &&
              this.annoSubAccertamento.equals(other.getAnnoSubAccertamento()))) &&
            ((this.disponibilitaIncassare==null && other.getDisponibilitaIncassare()==null) || 
             (this.disponibilitaIncassare!=null &&
              this.disponibilitaIncassare.equals(other.getDisponibilitaIncassare()))) &&
            ((this.numeroSubAccertamento==null && other.getNumeroSubAccertamento()==null) || 
             (this.numeroSubAccertamento!=null &&
              this.numeroSubAccertamento.equals(other.getNumeroSubAccertamento())));
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
        if (getAnnoSubAccertamento() != null) {
            _hashCode += getAnnoSubAccertamento().hashCode();
        }
        if (getDisponibilitaIncassare() != null) {
            _hashCode += getDisponibilitaIncassare().hashCode();
        }
        if (getNumeroSubAccertamento() != null) {
            _hashCode += getNumeroSubAccertamento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SubAccertamento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "subAccertamento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoSubAccertamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoSubAccertamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disponibilitaIncassare");
        elemField.setXmlName(new javax.xml.namespace.QName("", "disponibilitaIncassare"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroSubAccertamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroSubAccertamento"));
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
