/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * LeggiStatoElaborazioneDocumentoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService;

public class LeggiStatoElaborazioneDocumentoResponse  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.BaseResponse  implements java.io.Serializable {
    private java.lang.String responseElaborazione;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.StatoElaborazione statoElaborazione;

    public LeggiStatoElaborazioneDocumentoResponse() {
    }

    public LeggiStatoElaborazioneDocumentoResponse(
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Ente ente,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Errore[] errori,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Esito esito,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Messaggio[] messaggi,
           java.lang.String responseElaborazione,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.StatoElaborazione statoElaborazione) {
        super(
            ente,
            errori,
            esito,
            messaggi);
        this.responseElaborazione = responseElaborazione;
        this.statoElaborazione = statoElaborazione;
    }


    /**
     * Gets the responseElaborazione value for this LeggiStatoElaborazioneDocumentoResponse.
     * 
     * @return responseElaborazione
     */
    public java.lang.String getResponseElaborazione() {
        return responseElaborazione;
    }


    /**
     * Sets the responseElaborazione value for this LeggiStatoElaborazioneDocumentoResponse.
     * 
     * @param responseElaborazione
     */
    public void setResponseElaborazione(java.lang.String responseElaborazione) {
        this.responseElaborazione = responseElaborazione;
    }


    /**
     * Gets the statoElaborazione value for this LeggiStatoElaborazioneDocumentoResponse.
     * 
     * @return statoElaborazione
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.StatoElaborazione getStatoElaborazione() {
        return statoElaborazione;
    }


    /**
     * Sets the statoElaborazione value for this LeggiStatoElaborazioneDocumentoResponse.
     * 
     * @param statoElaborazione
     */
    public void setStatoElaborazione(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.StatoElaborazione statoElaborazione) {
        this.statoElaborazione = statoElaborazione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LeggiStatoElaborazioneDocumentoResponse)) return false;
        LeggiStatoElaborazioneDocumentoResponse other = (LeggiStatoElaborazioneDocumentoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.responseElaborazione==null && other.getResponseElaborazione()==null) || 
             (this.responseElaborazione!=null &&
              this.responseElaborazione.equals(other.getResponseElaborazione()))) &&
            ((this.statoElaborazione==null && other.getStatoElaborazione()==null) || 
             (this.statoElaborazione!=null &&
              this.statoElaborazione.equals(other.getStatoElaborazione())));
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
        if (getResponseElaborazione() != null) {
            _hashCode += getResponseElaborazione().hashCode();
        }
        if (getStatoElaborazione() != null) {
            _hashCode += getStatoElaborazione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LeggiStatoElaborazioneDocumentoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "leggiStatoElaborazioneDocumentoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseElaborazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "responseElaborazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statoElaborazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statoElaborazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "statoElaborazione"));
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
