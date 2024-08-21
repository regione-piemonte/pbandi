/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * ProvvisorioDiCassa.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class ProvvisorioDiCassa  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.EntitaCodificataBase  implements java.io.Serializable {
    private java.lang.Integer anno;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SiNoEnum annullato;

    private java.lang.String causale;

    private java.util.Calendar data;

    private java.math.BigDecimal importo;

    private java.math.BigDecimal importoDaEmettere;

    private java.math.BigDecimal importoDaRegolarizzare;

    private java.lang.Integer numero;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoProvvisorioDiCassa tipoProvvisorioDiCassa;

    public ProvvisorioDiCassa() {
    }

    public ProvvisorioDiCassa(
           java.lang.String codice,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato,
           java.lang.String descrizione,
           java.lang.Integer anno,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SiNoEnum annullato,
           java.lang.String causale,
           java.util.Calendar data,
           java.math.BigDecimal importo,
           java.math.BigDecimal importoDaEmettere,
           java.math.BigDecimal importoDaRegolarizzare,
           java.lang.Integer numero,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoProvvisorioDiCassa tipoProvvisorioDiCassa) {
        super(
            codice,
            stato,
            descrizione);
        this.anno = anno;
        this.annullato = annullato;
        this.causale = causale;
        this.data = data;
        this.importo = importo;
        this.importoDaEmettere = importoDaEmettere;
        this.importoDaRegolarizzare = importoDaRegolarizzare;
        this.numero = numero;
        this.tipoProvvisorioDiCassa = tipoProvvisorioDiCassa;
    }


    /**
     * Gets the anno value for this ProvvisorioDiCassa.
     * 
     * @return anno
     */
    public java.lang.Integer getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this ProvvisorioDiCassa.
     * 
     * @param anno
     */
    public void setAnno(java.lang.Integer anno) {
        this.anno = anno;
    }


    /**
     * Gets the annullato value for this ProvvisorioDiCassa.
     * 
     * @return annullato
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SiNoEnum getAnnullato() {
        return annullato;
    }


    /**
     * Sets the annullato value for this ProvvisorioDiCassa.
     * 
     * @param annullato
     */
    public void setAnnullato(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SiNoEnum annullato) {
        this.annullato = annullato;
    }


    /**
     * Gets the causale value for this ProvvisorioDiCassa.
     * 
     * @return causale
     */
    public java.lang.String getCausale() {
        return causale;
    }


    /**
     * Sets the causale value for this ProvvisorioDiCassa.
     * 
     * @param causale
     */
    public void setCausale(java.lang.String causale) {
        this.causale = causale;
    }


    /**
     * Gets the data value for this ProvvisorioDiCassa.
     * 
     * @return data
     */
    public java.util.Calendar getData() {
        return data;
    }


    /**
     * Sets the data value for this ProvvisorioDiCassa.
     * 
     * @param data
     */
    public void setData(java.util.Calendar data) {
        this.data = data;
    }


    /**
     * Gets the importo value for this ProvvisorioDiCassa.
     * 
     * @return importo
     */
    public java.math.BigDecimal getImporto() {
        return importo;
    }


    /**
     * Sets the importo value for this ProvvisorioDiCassa.
     * 
     * @param importo
     */
    public void setImporto(java.math.BigDecimal importo) {
        this.importo = importo;
    }


    /**
     * Gets the importoDaEmettere value for this ProvvisorioDiCassa.
     * 
     * @return importoDaEmettere
     */
    public java.math.BigDecimal getImportoDaEmettere() {
        return importoDaEmettere;
    }


    /**
     * Sets the importoDaEmettere value for this ProvvisorioDiCassa.
     * 
     * @param importoDaEmettere
     */
    public void setImportoDaEmettere(java.math.BigDecimal importoDaEmettere) {
        this.importoDaEmettere = importoDaEmettere;
    }


    /**
     * Gets the importoDaRegolarizzare value for this ProvvisorioDiCassa.
     * 
     * @return importoDaRegolarizzare
     */
    public java.math.BigDecimal getImportoDaRegolarizzare() {
        return importoDaRegolarizzare;
    }


    /**
     * Sets the importoDaRegolarizzare value for this ProvvisorioDiCassa.
     * 
     * @param importoDaRegolarizzare
     */
    public void setImportoDaRegolarizzare(java.math.BigDecimal importoDaRegolarizzare) {
        this.importoDaRegolarizzare = importoDaRegolarizzare;
    }


    /**
     * Gets the numero value for this ProvvisorioDiCassa.
     * 
     * @return numero
     */
    public java.lang.Integer getNumero() {
        return numero;
    }


    /**
     * Sets the numero value for this ProvvisorioDiCassa.
     * 
     * @param numero
     */
    public void setNumero(java.lang.Integer numero) {
        this.numero = numero;
    }


    /**
     * Gets the tipoProvvisorioDiCassa value for this ProvvisorioDiCassa.
     * 
     * @return tipoProvvisorioDiCassa
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoProvvisorioDiCassa getTipoProvvisorioDiCassa() {
        return tipoProvvisorioDiCassa;
    }


    /**
     * Sets the tipoProvvisorioDiCassa value for this ProvvisorioDiCassa.
     * 
     * @param tipoProvvisorioDiCassa
     */
    public void setTipoProvvisorioDiCassa(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoProvvisorioDiCassa tipoProvvisorioDiCassa) {
        this.tipoProvvisorioDiCassa = tipoProvvisorioDiCassa;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProvvisorioDiCassa)) return false;
        ProvvisorioDiCassa other = (ProvvisorioDiCassa) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.anno==null && other.getAnno()==null) || 
             (this.anno!=null &&
              this.anno.equals(other.getAnno()))) &&
            ((this.annullato==null && other.getAnnullato()==null) || 
             (this.annullato!=null &&
              this.annullato.equals(other.getAnnullato()))) &&
            ((this.causale==null && other.getCausale()==null) || 
             (this.causale!=null &&
              this.causale.equals(other.getCausale()))) &&
            ((this.data==null && other.getData()==null) || 
             (this.data!=null &&
              this.data.equals(other.getData()))) &&
            ((this.importo==null && other.getImporto()==null) || 
             (this.importo!=null &&
              this.importo.equals(other.getImporto()))) &&
            ((this.importoDaEmettere==null && other.getImportoDaEmettere()==null) || 
             (this.importoDaEmettere!=null &&
              this.importoDaEmettere.equals(other.getImportoDaEmettere()))) &&
            ((this.importoDaRegolarizzare==null && other.getImportoDaRegolarizzare()==null) || 
             (this.importoDaRegolarizzare!=null &&
              this.importoDaRegolarizzare.equals(other.getImportoDaRegolarizzare()))) &&
            ((this.numero==null && other.getNumero()==null) || 
             (this.numero!=null &&
              this.numero.equals(other.getNumero()))) &&
            ((this.tipoProvvisorioDiCassa==null && other.getTipoProvvisorioDiCassa()==null) || 
             (this.tipoProvvisorioDiCassa!=null &&
              this.tipoProvvisorioDiCassa.equals(other.getTipoProvvisorioDiCassa())));
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
        if (getAnno() != null) {
            _hashCode += getAnno().hashCode();
        }
        if (getAnnullato() != null) {
            _hashCode += getAnnullato().hashCode();
        }
        if (getCausale() != null) {
            _hashCode += getCausale().hashCode();
        }
        if (getData() != null) {
            _hashCode += getData().hashCode();
        }
        if (getImporto() != null) {
            _hashCode += getImporto().hashCode();
        }
        if (getImportoDaEmettere() != null) {
            _hashCode += getImportoDaEmettere().hashCode();
        }
        if (getImportoDaRegolarizzare() != null) {
            _hashCode += getImportoDaRegolarizzare().hashCode();
        }
        if (getNumero() != null) {
            _hashCode += getNumero().hashCode();
        }
        if (getTipoProvvisorioDiCassa() != null) {
            _hashCode += getTipoProvvisorioDiCassa().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProvvisorioDiCassa.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/data/1.0", "provvisorioDiCassa"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annullato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annullato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "siNoEnum"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("causale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "causale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("data");
        elemField.setXmlName(new javax.xml.namespace.QName("", "data"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importoDaEmettere");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importoDaEmettere"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importoDaRegolarizzare");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importoDaRegolarizzare"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoProvvisorioDiCassa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoProvvisorioDiCassa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "tipoProvvisorioDiCassa"));
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
