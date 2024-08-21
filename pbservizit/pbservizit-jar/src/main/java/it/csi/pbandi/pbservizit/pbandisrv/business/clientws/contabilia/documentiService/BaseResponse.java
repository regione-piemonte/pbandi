/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * BaseResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService;

public abstract class BaseResponse  implements java.io.Serializable {
    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Ente ente;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Errore[] errori;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Esito esito;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Messaggio[] messaggi;

    public BaseResponse() {
    }

    public BaseResponse(
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Ente ente,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Errore[] errori,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Esito esito,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Messaggio[] messaggi) {
           this.ente = ente;
           this.errori = errori;
           this.esito = esito;
           this.messaggi = messaggi;
    }


    /**
     * Gets the ente value for this BaseResponse.
     * 
     * @return ente
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Ente getEnte() {
        return ente;
    }


    /**
     * Sets the ente value for this BaseResponse.
     * 
     * @param ente
     */
    public void setEnte(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Ente ente) {
        this.ente = ente;
    }


    /**
     * Gets the errori value for this BaseResponse.
     * 
     * @return errori
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Errore[] getErrori() {
        return errori;
    }


    /**
     * Sets the errori value for this BaseResponse.
     * 
     * @param errori
     */
    public void setErrori(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Errore[] errori) {
        this.errori = errori;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Errore getErrori(int i) {
        return this.errori[i];
    }

    public void setErrori(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Errore _value) {
        this.errori[i] = _value;
    }


    /**
     * Gets the esito value for this BaseResponse.
     * 
     * @return esito
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Esito getEsito() {
        return esito;
    }


    /**
     * Sets the esito value for this BaseResponse.
     * 
     * @param esito
     */
    public void setEsito(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Esito esito) {
        this.esito = esito;
    }


    /**
     * Gets the messaggi value for this BaseResponse.
     * 
     * @return messaggi
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Messaggio[] getMessaggi() {
        return messaggi;
    }


    /**
     * Sets the messaggi value for this BaseResponse.
     * 
     * @param messaggi
     */
    public void setMessaggi(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Messaggio[] messaggi) {
        this.messaggi = messaggi;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Messaggio getMessaggi(int i) {
        return this.messaggi[i];
    }

    public void setMessaggi(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Messaggio _value) {
        this.messaggi[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BaseResponse)) return false;
        BaseResponse other = (BaseResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ente==null && other.getEnte()==null) || 
             (this.ente!=null &&
              this.ente.equals(other.getEnte()))) &&
            ((this.errori==null && other.getErrori()==null) || 
             (this.errori!=null &&
              java.util.Arrays.equals(this.errori, other.getErrori()))) &&
            ((this.esito==null && other.getEsito()==null) || 
             (this.esito!=null &&
              this.esito.equals(other.getEsito()))) &&
            ((this.messaggi==null && other.getMessaggi()==null) || 
             (this.messaggi!=null &&
              java.util.Arrays.equals(this.messaggi, other.getMessaggi())));
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
        if (getEnte() != null) {
            _hashCode += getEnte().hashCode();
        }
        if (getErrori() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getErrori());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getErrori(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEsito() != null) {
            _hashCode += getEsito().hashCode();
        }
        if (getMessaggi() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMessaggi());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMessaggi(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BaseResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "baseResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "ente"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errori");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errori"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "errore"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esito");
        elemField.setXmlName(new javax.xml.namespace.QName("", "esito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "esito"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messaggi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "messaggi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "messaggio"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
