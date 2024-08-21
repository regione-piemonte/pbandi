/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * LiquidazioneAtti.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class LiquidazioneAtti  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Liquidazione  implements java.io.Serializable {
    private java.lang.Integer annoEsercizio;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Provvedimento atto;

    private java.lang.String beneficiario;

    private java.lang.Integer numeroArticolo;

    private java.lang.Integer numeroCapitolo;

    private java.lang.Integer numeroLiquidazionePrecedente;

    public LiquidazioneAtti() {
    }

    public LiquidazioneAtti(
           java.lang.String codice,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato,
           java.lang.Integer annoImpegno,
           java.lang.Integer annoLiquidazione,
           java.math.BigDecimal importoLiquidazione,
           java.lang.Integer numeroImpegno,
           java.lang.Integer numeroLiquidazione,
           java.lang.Integer numeroSubImpegno,
           java.lang.Integer annoEsercizio,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Provvedimento atto,
           java.lang.String beneficiario,
           java.lang.Integer numeroArticolo,
           java.lang.Integer numeroCapitolo,
           java.lang.Integer numeroLiquidazionePrecedente) {
        super(
            codice,
            stato,
            annoImpegno,
            annoLiquidazione,
            importoLiquidazione,
            numeroImpegno,
            numeroLiquidazione,
            numeroSubImpegno);
        this.annoEsercizio = annoEsercizio;
        this.atto = atto;
        this.beneficiario = beneficiario;
        this.numeroArticolo = numeroArticolo;
        this.numeroCapitolo = numeroCapitolo;
        this.numeroLiquidazionePrecedente = numeroLiquidazionePrecedente;
    }


    /**
     * Gets the annoEsercizio value for this LiquidazioneAtti.
     * 
     * @return annoEsercizio
     */
    public java.lang.Integer getAnnoEsercizio() {
        return annoEsercizio;
    }


    /**
     * Sets the annoEsercizio value for this LiquidazioneAtti.
     * 
     * @param annoEsercizio
     */
    public void setAnnoEsercizio(java.lang.Integer annoEsercizio) {
        this.annoEsercizio = annoEsercizio;
    }


    /**
     * Gets the atto value for this LiquidazioneAtti.
     * 
     * @return atto
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Provvedimento getAtto() {
        return atto;
    }


    /**
     * Sets the atto value for this LiquidazioneAtti.
     * 
     * @param atto
     */
    public void setAtto(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Provvedimento atto) {
        this.atto = atto;
    }


    /**
     * Gets the beneficiario value for this LiquidazioneAtti.
     * 
     * @return beneficiario
     */
    public java.lang.String getBeneficiario() {
        return beneficiario;
    }


    /**
     * Sets the beneficiario value for this LiquidazioneAtti.
     * 
     * @param beneficiario
     */
    public void setBeneficiario(java.lang.String beneficiario) {
        this.beneficiario = beneficiario;
    }


    /**
     * Gets the numeroArticolo value for this LiquidazioneAtti.
     * 
     * @return numeroArticolo
     */
    public java.lang.Integer getNumeroArticolo() {
        return numeroArticolo;
    }


    /**
     * Sets the numeroArticolo value for this LiquidazioneAtti.
     * 
     * @param numeroArticolo
     */
    public void setNumeroArticolo(java.lang.Integer numeroArticolo) {
        this.numeroArticolo = numeroArticolo;
    }


    /**
     * Gets the numeroCapitolo value for this LiquidazioneAtti.
     * 
     * @return numeroCapitolo
     */
    public java.lang.Integer getNumeroCapitolo() {
        return numeroCapitolo;
    }


    /**
     * Sets the numeroCapitolo value for this LiquidazioneAtti.
     * 
     * @param numeroCapitolo
     */
    public void setNumeroCapitolo(java.lang.Integer numeroCapitolo) {
        this.numeroCapitolo = numeroCapitolo;
    }


    /**
     * Gets the numeroLiquidazionePrecedente value for this LiquidazioneAtti.
     * 
     * @return numeroLiquidazionePrecedente
     */
    public java.lang.Integer getNumeroLiquidazionePrecedente() {
        return numeroLiquidazionePrecedente;
    }


    /**
     * Sets the numeroLiquidazionePrecedente value for this LiquidazioneAtti.
     * 
     * @param numeroLiquidazionePrecedente
     */
    public void setNumeroLiquidazionePrecedente(java.lang.Integer numeroLiquidazionePrecedente) {
        this.numeroLiquidazionePrecedente = numeroLiquidazionePrecedente;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LiquidazioneAtti)) return false;
        LiquidazioneAtti other = (LiquidazioneAtti) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.annoEsercizio==null && other.getAnnoEsercizio()==null) || 
             (this.annoEsercizio!=null &&
              this.annoEsercizio.equals(other.getAnnoEsercizio()))) &&
            ((this.atto==null && other.getAtto()==null) || 
             (this.atto!=null &&
              this.atto.equals(other.getAtto()))) &&
            ((this.beneficiario==null && other.getBeneficiario()==null) || 
             (this.beneficiario!=null &&
              this.beneficiario.equals(other.getBeneficiario()))) &&
            ((this.numeroArticolo==null && other.getNumeroArticolo()==null) || 
             (this.numeroArticolo!=null &&
              this.numeroArticolo.equals(other.getNumeroArticolo()))) &&
            ((this.numeroCapitolo==null && other.getNumeroCapitolo()==null) || 
             (this.numeroCapitolo!=null &&
              this.numeroCapitolo.equals(other.getNumeroCapitolo()))) &&
            ((this.numeroLiquidazionePrecedente==null && other.getNumeroLiquidazionePrecedente()==null) || 
             (this.numeroLiquidazionePrecedente!=null &&
              this.numeroLiquidazionePrecedente.equals(other.getNumeroLiquidazionePrecedente())));
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
        if (getAnnoEsercizio() != null) {
            _hashCode += getAnnoEsercizio().hashCode();
        }
        if (getAtto() != null) {
            _hashCode += getAtto().hashCode();
        }
        if (getBeneficiario() != null) {
            _hashCode += getBeneficiario().hashCode();
        }
        if (getNumeroArticolo() != null) {
            _hashCode += getNumeroArticolo().hashCode();
        }
        if (getNumeroCapitolo() != null) {
            _hashCode += getNumeroCapitolo().hashCode();
        }
        if (getNumeroLiquidazionePrecedente() != null) {
            _hashCode += getNumeroLiquidazionePrecedente().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LiquidazioneAtti.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "liquidazioneAtti"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoEsercizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoEsercizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "atto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "provvedimento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beneficiario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "beneficiario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("numeroLiquidazionePrecedente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroLiquidazionePrecedente"));
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
