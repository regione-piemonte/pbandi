/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * BaseRicercaDocumento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public abstract class BaseRicercaDocumento  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaPaginataRequest  implements java.io.Serializable {
    private java.lang.Integer annoDocumento;

    private java.lang.Integer annoRepertorio;

    private java.lang.String codiceSoggetto;

    private java.util.Calendar dataRepertorio;

    private java.lang.String numeroDocumento;

    private java.lang.String numeroRepertorio;

    private java.lang.String registroRepertorio;

    private java.lang.String tipoDocumento;

    public BaseRicercaDocumento() {
    }

    public BaseRicercaDocumento(
           java.lang.Integer annoBilancio,
           java.lang.String codiceEnte,
           java.lang.String codiceFruitore,
           java.lang.Integer numeroElementiPerPagina,
           java.lang.Integer numeroPagina,
           java.lang.Integer annoDocumento,
           java.lang.Integer annoRepertorio,
           java.lang.String codiceSoggetto,
           java.util.Calendar dataRepertorio,
           java.lang.String numeroDocumento,
           java.lang.String numeroRepertorio,
           java.lang.String registroRepertorio,
           java.lang.String tipoDocumento) {
        super(
            annoBilancio,
            codiceEnte,
            codiceFruitore,
            numeroElementiPerPagina,
            numeroPagina);
        this.annoDocumento = annoDocumento;
        this.annoRepertorio = annoRepertorio;
        this.codiceSoggetto = codiceSoggetto;
        this.dataRepertorio = dataRepertorio;
        this.numeroDocumento = numeroDocumento;
        this.numeroRepertorio = numeroRepertorio;
        this.registroRepertorio = registroRepertorio;
        this.tipoDocumento = tipoDocumento;
    }


    /**
     * Gets the annoDocumento value for this BaseRicercaDocumento.
     * 
     * @return annoDocumento
     */
    public java.lang.Integer getAnnoDocumento() {
        return annoDocumento;
    }


    /**
     * Sets the annoDocumento value for this BaseRicercaDocumento.
     * 
     * @param annoDocumento
     */
    public void setAnnoDocumento(java.lang.Integer annoDocumento) {
        this.annoDocumento = annoDocumento;
    }


    /**
     * Gets the annoRepertorio value for this BaseRicercaDocumento.
     * 
     * @return annoRepertorio
     */
    public java.lang.Integer getAnnoRepertorio() {
        return annoRepertorio;
    }


    /**
     * Sets the annoRepertorio value for this BaseRicercaDocumento.
     * 
     * @param annoRepertorio
     */
    public void setAnnoRepertorio(java.lang.Integer annoRepertorio) {
        this.annoRepertorio = annoRepertorio;
    }


    /**
     * Gets the codiceSoggetto value for this BaseRicercaDocumento.
     * 
     * @return codiceSoggetto
     */
    public java.lang.String getCodiceSoggetto() {
        return codiceSoggetto;
    }


    /**
     * Sets the codiceSoggetto value for this BaseRicercaDocumento.
     * 
     * @param codiceSoggetto
     */
    public void setCodiceSoggetto(java.lang.String codiceSoggetto) {
        this.codiceSoggetto = codiceSoggetto;
    }


    /**
     * Gets the dataRepertorio value for this BaseRicercaDocumento.
     * 
     * @return dataRepertorio
     */
    public java.util.Calendar getDataRepertorio() {
        return dataRepertorio;
    }


    /**
     * Sets the dataRepertorio value for this BaseRicercaDocumento.
     * 
     * @param dataRepertorio
     */
    public void setDataRepertorio(java.util.Calendar dataRepertorio) {
        this.dataRepertorio = dataRepertorio;
    }


    /**
     * Gets the numeroDocumento value for this BaseRicercaDocumento.
     * 
     * @return numeroDocumento
     */
    public java.lang.String getNumeroDocumento() {
        return numeroDocumento;
    }


    /**
     * Sets the numeroDocumento value for this BaseRicercaDocumento.
     * 
     * @param numeroDocumento
     */
    public void setNumeroDocumento(java.lang.String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }


    /**
     * Gets the numeroRepertorio value for this BaseRicercaDocumento.
     * 
     * @return numeroRepertorio
     */
    public java.lang.String getNumeroRepertorio() {
        return numeroRepertorio;
    }


    /**
     * Sets the numeroRepertorio value for this BaseRicercaDocumento.
     * 
     * @param numeroRepertorio
     */
    public void setNumeroRepertorio(java.lang.String numeroRepertorio) {
        this.numeroRepertorio = numeroRepertorio;
    }


    /**
     * Gets the registroRepertorio value for this BaseRicercaDocumento.
     * 
     * @return registroRepertorio
     */
    public java.lang.String getRegistroRepertorio() {
        return registroRepertorio;
    }


    /**
     * Sets the registroRepertorio value for this BaseRicercaDocumento.
     * 
     * @param registroRepertorio
     */
    public void setRegistroRepertorio(java.lang.String registroRepertorio) {
        this.registroRepertorio = registroRepertorio;
    }


    /**
     * Gets the tipoDocumento value for this BaseRicercaDocumento.
     * 
     * @return tipoDocumento
     */
    public java.lang.String getTipoDocumento() {
        return tipoDocumento;
    }


    /**
     * Sets the tipoDocumento value for this BaseRicercaDocumento.
     * 
     * @param tipoDocumento
     */
    public void setTipoDocumento(java.lang.String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BaseRicercaDocumento)) return false;
        BaseRicercaDocumento other = (BaseRicercaDocumento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.annoDocumento==null && other.getAnnoDocumento()==null) || 
             (this.annoDocumento!=null &&
              this.annoDocumento.equals(other.getAnnoDocumento()))) &&
            ((this.annoRepertorio==null && other.getAnnoRepertorio()==null) || 
             (this.annoRepertorio!=null &&
              this.annoRepertorio.equals(other.getAnnoRepertorio()))) &&
            ((this.codiceSoggetto==null && other.getCodiceSoggetto()==null) || 
             (this.codiceSoggetto!=null &&
              this.codiceSoggetto.equals(other.getCodiceSoggetto()))) &&
            ((this.dataRepertorio==null && other.getDataRepertorio()==null) || 
             (this.dataRepertorio!=null &&
              this.dataRepertorio.equals(other.getDataRepertorio()))) &&
            ((this.numeroDocumento==null && other.getNumeroDocumento()==null) || 
             (this.numeroDocumento!=null &&
              this.numeroDocumento.equals(other.getNumeroDocumento()))) &&
            ((this.numeroRepertorio==null && other.getNumeroRepertorio()==null) || 
             (this.numeroRepertorio!=null &&
              this.numeroRepertorio.equals(other.getNumeroRepertorio()))) &&
            ((this.registroRepertorio==null && other.getRegistroRepertorio()==null) || 
             (this.registroRepertorio!=null &&
              this.registroRepertorio.equals(other.getRegistroRepertorio()))) &&
            ((this.tipoDocumento==null && other.getTipoDocumento()==null) || 
             (this.tipoDocumento!=null &&
              this.tipoDocumento.equals(other.getTipoDocumento())));
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
        if (getAnnoDocumento() != null) {
            _hashCode += getAnnoDocumento().hashCode();
        }
        if (getAnnoRepertorio() != null) {
            _hashCode += getAnnoRepertorio().hashCode();
        }
        if (getCodiceSoggetto() != null) {
            _hashCode += getCodiceSoggetto().hashCode();
        }
        if (getDataRepertorio() != null) {
            _hashCode += getDataRepertorio().hashCode();
        }
        if (getNumeroDocumento() != null) {
            _hashCode += getNumeroDocumento().hashCode();
        }
        if (getNumeroRepertorio() != null) {
            _hashCode += getNumeroRepertorio().hashCode();
        }
        if (getRegistroRepertorio() != null) {
            _hashCode += getRegistroRepertorio().hashCode();
        }
        if (getTipoDocumento() != null) {
            _hashCode += getTipoDocumento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BaseRicercaDocumento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "baseRicercaDocumento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoRepertorio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoRepertorio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceSoggetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceSoggetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataRepertorio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataRepertorio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroRepertorio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroRepertorio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registroRepertorio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "registroRepertorio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
