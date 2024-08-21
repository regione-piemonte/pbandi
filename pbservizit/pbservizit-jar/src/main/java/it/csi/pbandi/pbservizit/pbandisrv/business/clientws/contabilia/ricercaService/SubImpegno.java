/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * SubImpegno.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class SubImpegno  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.MovimentoGestione  implements java.io.Serializable {
    private java.lang.Integer annoSubImpegno;

    private java.math.BigDecimal disponibilitaLiquidare;

    private java.lang.Integer numeroSubImpegno;

    public SubImpegno() {
    }

    public SubImpegno(
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
           java.lang.Integer annoSubImpegno,
           java.math.BigDecimal disponibilitaLiquidare,
           java.lang.Integer numeroSubImpegno) {
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
        this.annoSubImpegno = annoSubImpegno;
        this.disponibilitaLiquidare = disponibilitaLiquidare;
        this.numeroSubImpegno = numeroSubImpegno;
    }


    /**
     * Gets the annoSubImpegno value for this SubImpegno.
     * 
     * @return annoSubImpegno
     */
    public java.lang.Integer getAnnoSubImpegno() {
        return annoSubImpegno;
    }


    /**
     * Sets the annoSubImpegno value for this SubImpegno.
     * 
     * @param annoSubImpegno
     */
    public void setAnnoSubImpegno(java.lang.Integer annoSubImpegno) {
        this.annoSubImpegno = annoSubImpegno;
    }


    /**
     * Gets the disponibilitaLiquidare value for this SubImpegno.
     * 
     * @return disponibilitaLiquidare
     */
    public java.math.BigDecimal getDisponibilitaLiquidare() {
        return disponibilitaLiquidare;
    }


    /**
     * Sets the disponibilitaLiquidare value for this SubImpegno.
     * 
     * @param disponibilitaLiquidare
     */
    public void setDisponibilitaLiquidare(java.math.BigDecimal disponibilitaLiquidare) {
        this.disponibilitaLiquidare = disponibilitaLiquidare;
    }


    /**
     * Gets the numeroSubImpegno value for this SubImpegno.
     * 
     * @return numeroSubImpegno
     */
    public java.lang.Integer getNumeroSubImpegno() {
        return numeroSubImpegno;
    }


    /**
     * Sets the numeroSubImpegno value for this SubImpegno.
     * 
     * @param numeroSubImpegno
     */
    public void setNumeroSubImpegno(java.lang.Integer numeroSubImpegno) {
        this.numeroSubImpegno = numeroSubImpegno;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SubImpegno)) return false;
        SubImpegno other = (SubImpegno) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.annoSubImpegno==null && other.getAnnoSubImpegno()==null) || 
             (this.annoSubImpegno!=null &&
              this.annoSubImpegno.equals(other.getAnnoSubImpegno()))) &&
            ((this.disponibilitaLiquidare==null && other.getDisponibilitaLiquidare()==null) || 
             (this.disponibilitaLiquidare!=null &&
              this.disponibilitaLiquidare.equals(other.getDisponibilitaLiquidare()))) &&
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
        if (getAnnoSubImpegno() != null) {
            _hashCode += getAnnoSubImpegno().hashCode();
        }
        if (getDisponibilitaLiquidare() != null) {
            _hashCode += getDisponibilitaLiquidare().hashCode();
        }
        if (getNumeroSubImpegno() != null) {
            _hashCode += getNumeroSubImpegno().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SubImpegno.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "subImpegno"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoSubImpegno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoSubImpegno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disponibilitaLiquidare");
        elemField.setXmlName(new javax.xml.namespace.QName("", "disponibilitaLiquidare"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
