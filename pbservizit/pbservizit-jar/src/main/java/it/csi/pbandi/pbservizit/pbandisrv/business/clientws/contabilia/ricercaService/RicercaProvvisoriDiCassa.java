/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * RicercaProvvisoriDiCassa.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class RicercaProvvisoriDiCassa  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaPaginataRequest  implements java.io.Serializable {
    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SiNoIndifferenteEnum annullato;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SiNoEnum daRegolarizzare;

    private java.util.Calendar dataA;

    private java.util.Calendar dataDa;

    private java.lang.String descrizioneCausale;

    private java.math.BigDecimal importoA;

    private java.math.BigDecimal importoDa;

    private java.lang.Integer numeroProvvisorio;

    private java.lang.Integer numeroQuietanza;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoProvvisorioDiCassa tipoProvvisorioDiCassa;

    public RicercaProvvisoriDiCassa() {
    }

    public RicercaProvvisoriDiCassa(
           java.lang.Integer annoBilancio,
           java.lang.String codiceEnte,
           java.lang.String codiceFruitore,
           java.lang.Integer numeroElementiPerPagina,
           java.lang.Integer numeroPagina,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SiNoIndifferenteEnum annullato,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SiNoEnum daRegolarizzare,
           java.util.Calendar dataA,
           java.util.Calendar dataDa,
           java.lang.String descrizioneCausale,
           java.math.BigDecimal importoA,
           java.math.BigDecimal importoDa,
           java.lang.Integer numeroProvvisorio,
           java.lang.Integer numeroQuietanza,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoProvvisorioDiCassa tipoProvvisorioDiCassa) {
        super(
            annoBilancio,
            codiceEnte,
            codiceFruitore,
            numeroElementiPerPagina,
            numeroPagina);
        this.annullato = annullato;
        this.daRegolarizzare = daRegolarizzare;
        this.dataA = dataA;
        this.dataDa = dataDa;
        this.descrizioneCausale = descrizioneCausale;
        this.importoA = importoA;
        this.importoDa = importoDa;
        this.numeroProvvisorio = numeroProvvisorio;
        this.numeroQuietanza = numeroQuietanza;
        this.tipoProvvisorioDiCassa = tipoProvvisorioDiCassa;
    }


    /**
     * Gets the annullato value for this RicercaProvvisoriDiCassa.
     * 
     * @return annullato
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SiNoIndifferenteEnum getAnnullato() {
        return annullato;
    }


    /**
     * Sets the annullato value for this RicercaProvvisoriDiCassa.
     * 
     * @param annullato
     */
    public void setAnnullato(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SiNoIndifferenteEnum annullato) {
        this.annullato = annullato;
    }


    /**
     * Gets the daRegolarizzare value for this RicercaProvvisoriDiCassa.
     * 
     * @return daRegolarizzare
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SiNoEnum getDaRegolarizzare() {
        return daRegolarizzare;
    }


    /**
     * Sets the daRegolarizzare value for this RicercaProvvisoriDiCassa.
     * 
     * @param daRegolarizzare
     */
    public void setDaRegolarizzare(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SiNoEnum daRegolarizzare) {
        this.daRegolarizzare = daRegolarizzare;
    }


    /**
     * Gets the dataA value for this RicercaProvvisoriDiCassa.
     * 
     * @return dataA
     */
    public java.util.Calendar getDataA() {
        return dataA;
    }


    /**
     * Sets the dataA value for this RicercaProvvisoriDiCassa.
     * 
     * @param dataA
     */
    public void setDataA(java.util.Calendar dataA) {
        this.dataA = dataA;
    }


    /**
     * Gets the dataDa value for this RicercaProvvisoriDiCassa.
     * 
     * @return dataDa
     */
    public java.util.Calendar getDataDa() {
        return dataDa;
    }


    /**
     * Sets the dataDa value for this RicercaProvvisoriDiCassa.
     * 
     * @param dataDa
     */
    public void setDataDa(java.util.Calendar dataDa) {
        this.dataDa = dataDa;
    }


    /**
     * Gets the descrizioneCausale value for this RicercaProvvisoriDiCassa.
     * 
     * @return descrizioneCausale
     */
    public java.lang.String getDescrizioneCausale() {
        return descrizioneCausale;
    }


    /**
     * Sets the descrizioneCausale value for this RicercaProvvisoriDiCassa.
     * 
     * @param descrizioneCausale
     */
    public void setDescrizioneCausale(java.lang.String descrizioneCausale) {
        this.descrizioneCausale = descrizioneCausale;
    }


    /**
     * Gets the importoA value for this RicercaProvvisoriDiCassa.
     * 
     * @return importoA
     */
    public java.math.BigDecimal getImportoA() {
        return importoA;
    }


    /**
     * Sets the importoA value for this RicercaProvvisoriDiCassa.
     * 
     * @param importoA
     */
    public void setImportoA(java.math.BigDecimal importoA) {
        this.importoA = importoA;
    }


    /**
     * Gets the importoDa value for this RicercaProvvisoriDiCassa.
     * 
     * @return importoDa
     */
    public java.math.BigDecimal getImportoDa() {
        return importoDa;
    }


    /**
     * Sets the importoDa value for this RicercaProvvisoriDiCassa.
     * 
     * @param importoDa
     */
    public void setImportoDa(java.math.BigDecimal importoDa) {
        this.importoDa = importoDa;
    }


    /**
     * Gets the numeroProvvisorio value for this RicercaProvvisoriDiCassa.
     * 
     * @return numeroProvvisorio
     */
    public java.lang.Integer getNumeroProvvisorio() {
        return numeroProvvisorio;
    }


    /**
     * Sets the numeroProvvisorio value for this RicercaProvvisoriDiCassa.
     * 
     * @param numeroProvvisorio
     */
    public void setNumeroProvvisorio(java.lang.Integer numeroProvvisorio) {
        this.numeroProvvisorio = numeroProvvisorio;
    }


    /**
     * Gets the numeroQuietanza value for this RicercaProvvisoriDiCassa.
     * 
     * @return numeroQuietanza
     */
    public java.lang.Integer getNumeroQuietanza() {
        return numeroQuietanza;
    }


    /**
     * Sets the numeroQuietanza value for this RicercaProvvisoriDiCassa.
     * 
     * @param numeroQuietanza
     */
    public void setNumeroQuietanza(java.lang.Integer numeroQuietanza) {
        this.numeroQuietanza = numeroQuietanza;
    }


    /**
     * Gets the tipoProvvisorioDiCassa value for this RicercaProvvisoriDiCassa.
     * 
     * @return tipoProvvisorioDiCassa
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoProvvisorioDiCassa getTipoProvvisorioDiCassa() {
        return tipoProvvisorioDiCassa;
    }


    /**
     * Sets the tipoProvvisorioDiCassa value for this RicercaProvvisoriDiCassa.
     * 
     * @param tipoProvvisorioDiCassa
     */
    public void setTipoProvvisorioDiCassa(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoProvvisorioDiCassa tipoProvvisorioDiCassa) {
        this.tipoProvvisorioDiCassa = tipoProvvisorioDiCassa;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaProvvisoriDiCassa)) return false;
        RicercaProvvisoriDiCassa other = (RicercaProvvisoriDiCassa) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.annullato==null && other.getAnnullato()==null) || 
             (this.annullato!=null &&
              this.annullato.equals(other.getAnnullato()))) &&
            ((this.daRegolarizzare==null && other.getDaRegolarizzare()==null) || 
             (this.daRegolarizzare!=null &&
              this.daRegolarizzare.equals(other.getDaRegolarizzare()))) &&
            ((this.dataA==null && other.getDataA()==null) || 
             (this.dataA!=null &&
              this.dataA.equals(other.getDataA()))) &&
            ((this.dataDa==null && other.getDataDa()==null) || 
             (this.dataDa!=null &&
              this.dataDa.equals(other.getDataDa()))) &&
            ((this.descrizioneCausale==null && other.getDescrizioneCausale()==null) || 
             (this.descrizioneCausale!=null &&
              this.descrizioneCausale.equals(other.getDescrizioneCausale()))) &&
            ((this.importoA==null && other.getImportoA()==null) || 
             (this.importoA!=null &&
              this.importoA.equals(other.getImportoA()))) &&
            ((this.importoDa==null && other.getImportoDa()==null) || 
             (this.importoDa!=null &&
              this.importoDa.equals(other.getImportoDa()))) &&
            ((this.numeroProvvisorio==null && other.getNumeroProvvisorio()==null) || 
             (this.numeroProvvisorio!=null &&
              this.numeroProvvisorio.equals(other.getNumeroProvvisorio()))) &&
            ((this.numeroQuietanza==null && other.getNumeroQuietanza()==null) || 
             (this.numeroQuietanza!=null &&
              this.numeroQuietanza.equals(other.getNumeroQuietanza()))) &&
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
        if (getAnnullato() != null) {
            _hashCode += getAnnullato().hashCode();
        }
        if (getDaRegolarizzare() != null) {
            _hashCode += getDaRegolarizzare().hashCode();
        }
        if (getDataA() != null) {
            _hashCode += getDataA().hashCode();
        }
        if (getDataDa() != null) {
            _hashCode += getDataDa().hashCode();
        }
        if (getDescrizioneCausale() != null) {
            _hashCode += getDescrizioneCausale().hashCode();
        }
        if (getImportoA() != null) {
            _hashCode += getImportoA().hashCode();
        }
        if (getImportoDa() != null) {
            _hashCode += getImportoDa().hashCode();
        }
        if (getNumeroProvvisorio() != null) {
            _hashCode += getNumeroProvvisorio().hashCode();
        }
        if (getNumeroQuietanza() != null) {
            _hashCode += getNumeroQuietanza().hashCode();
        }
        if (getTipoProvvisorioDiCassa() != null) {
            _hashCode += getTipoProvvisorioDiCassa().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RicercaProvvisoriDiCassa.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaProvvisoriDiCassa"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annullato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annullato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "siNoIndifferenteEnum"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("daRegolarizzare");
        elemField.setXmlName(new javax.xml.namespace.QName("", "daRegolarizzare"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "siNoEnum"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataDa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataDa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneCausale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneCausale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importoA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importoA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importoDa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importoDa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroProvvisorio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroProvvisorio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroQuietanza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroQuietanza"));
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
