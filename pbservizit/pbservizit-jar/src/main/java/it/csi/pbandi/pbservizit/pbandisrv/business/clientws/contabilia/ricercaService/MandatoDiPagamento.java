/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * MandatoDiPagamento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class MandatoDiPagamento  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.EntitaBase  implements java.io.Serializable {
    private java.lang.Integer annoEsercizio;

    private java.lang.Integer annoImpegno;

    private java.lang.Integer annoLiquidazione;

    private java.lang.Integer annoOrdinativo;

    private java.lang.String codiceSubOrdinativo;

    private java.lang.String creditore;

    private java.util.Calendar dataEmissione;

    private java.math.BigDecimal importoOrdinativo;

    private java.lang.Integer numeroArticolo;

    private java.lang.Integer numeroCapitolo;

    private java.lang.Integer numeroImpegno;

    private java.lang.Integer numeroLiquidazione;

    private java.lang.Integer numeroOrdinativo;

    private java.lang.Integer numeroSubImpegno;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Provvedimento provvedimento;

    private java.lang.String quietanza;

    public MandatoDiPagamento() {
    }

    public MandatoDiPagamento(
           java.lang.String codice,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato,
           java.lang.Integer annoEsercizio,
           java.lang.Integer annoImpegno,
           java.lang.Integer annoLiquidazione,
           java.lang.Integer annoOrdinativo,
           java.lang.String codiceSubOrdinativo,
           java.lang.String creditore,
           java.util.Calendar dataEmissione,
           java.math.BigDecimal importoOrdinativo,
           java.lang.Integer numeroArticolo,
           java.lang.Integer numeroCapitolo,
           java.lang.Integer numeroImpegno,
           java.lang.Integer numeroLiquidazione,
           java.lang.Integer numeroOrdinativo,
           java.lang.Integer numeroSubImpegno,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Provvedimento provvedimento,
           java.lang.String quietanza) {
        super(
            codice,
            stato);
        this.annoEsercizio = annoEsercizio;
        this.annoImpegno = annoImpegno;
        this.annoLiquidazione = annoLiquidazione;
        this.annoOrdinativo = annoOrdinativo;
        this.codiceSubOrdinativo = codiceSubOrdinativo;
        this.creditore = creditore;
        this.dataEmissione = dataEmissione;
        this.importoOrdinativo = importoOrdinativo;
        this.numeroArticolo = numeroArticolo;
        this.numeroCapitolo = numeroCapitolo;
        this.numeroImpegno = numeroImpegno;
        this.numeroLiquidazione = numeroLiquidazione;
        this.numeroOrdinativo = numeroOrdinativo;
        this.numeroSubImpegno = numeroSubImpegno;
        this.provvedimento = provvedimento;
        this.quietanza = quietanza;
    }


    /**
     * Gets the annoEsercizio value for this MandatoDiPagamento.
     * 
     * @return annoEsercizio
     */
    public java.lang.Integer getAnnoEsercizio() {
        return annoEsercizio;
    }


    /**
     * Sets the annoEsercizio value for this MandatoDiPagamento.
     * 
     * @param annoEsercizio
     */
    public void setAnnoEsercizio(java.lang.Integer annoEsercizio) {
        this.annoEsercizio = annoEsercizio;
    }


    /**
     * Gets the annoImpegno value for this MandatoDiPagamento.
     * 
     * @return annoImpegno
     */
    public java.lang.Integer getAnnoImpegno() {
        return annoImpegno;
    }


    /**
     * Sets the annoImpegno value for this MandatoDiPagamento.
     * 
     * @param annoImpegno
     */
    public void setAnnoImpegno(java.lang.Integer annoImpegno) {
        this.annoImpegno = annoImpegno;
    }


    /**
     * Gets the annoLiquidazione value for this MandatoDiPagamento.
     * 
     * @return annoLiquidazione
     */
    public java.lang.Integer getAnnoLiquidazione() {
        return annoLiquidazione;
    }


    /**
     * Sets the annoLiquidazione value for this MandatoDiPagamento.
     * 
     * @param annoLiquidazione
     */
    public void setAnnoLiquidazione(java.lang.Integer annoLiquidazione) {
        this.annoLiquidazione = annoLiquidazione;
    }


    /**
     * Gets the annoOrdinativo value for this MandatoDiPagamento.
     * 
     * @return annoOrdinativo
     */
    public java.lang.Integer getAnnoOrdinativo() {
        return annoOrdinativo;
    }


    /**
     * Sets the annoOrdinativo value for this MandatoDiPagamento.
     * 
     * @param annoOrdinativo
     */
    public void setAnnoOrdinativo(java.lang.Integer annoOrdinativo) {
        this.annoOrdinativo = annoOrdinativo;
    }


    /**
     * Gets the codiceSubOrdinativo value for this MandatoDiPagamento.
     * 
     * @return codiceSubOrdinativo
     */
    public java.lang.String getCodiceSubOrdinativo() {
        return codiceSubOrdinativo;
    }


    /**
     * Sets the codiceSubOrdinativo value for this MandatoDiPagamento.
     * 
     * @param codiceSubOrdinativo
     */
    public void setCodiceSubOrdinativo(java.lang.String codiceSubOrdinativo) {
        this.codiceSubOrdinativo = codiceSubOrdinativo;
    }


    /**
     * Gets the creditore value for this MandatoDiPagamento.
     * 
     * @return creditore
     */
    public java.lang.String getCreditore() {
        return creditore;
    }


    /**
     * Sets the creditore value for this MandatoDiPagamento.
     * 
     * @param creditore
     */
    public void setCreditore(java.lang.String creditore) {
        this.creditore = creditore;
    }


    /**
     * Gets the dataEmissione value for this MandatoDiPagamento.
     * 
     * @return dataEmissione
     */
    public java.util.Calendar getDataEmissione() {
        return dataEmissione;
    }


    /**
     * Sets the dataEmissione value for this MandatoDiPagamento.
     * 
     * @param dataEmissione
     */
    public void setDataEmissione(java.util.Calendar dataEmissione) {
        this.dataEmissione = dataEmissione;
    }


    /**
     * Gets the importoOrdinativo value for this MandatoDiPagamento.
     * 
     * @return importoOrdinativo
     */
    public java.math.BigDecimal getImportoOrdinativo() {
        return importoOrdinativo;
    }


    /**
     * Sets the importoOrdinativo value for this MandatoDiPagamento.
     * 
     * @param importoOrdinativo
     */
    public void setImportoOrdinativo(java.math.BigDecimal importoOrdinativo) {
        this.importoOrdinativo = importoOrdinativo;
    }


    /**
     * Gets the numeroArticolo value for this MandatoDiPagamento.
     * 
     * @return numeroArticolo
     */
    public java.lang.Integer getNumeroArticolo() {
        return numeroArticolo;
    }


    /**
     * Sets the numeroArticolo value for this MandatoDiPagamento.
     * 
     * @param numeroArticolo
     */
    public void setNumeroArticolo(java.lang.Integer numeroArticolo) {
        this.numeroArticolo = numeroArticolo;
    }


    /**
     * Gets the numeroCapitolo value for this MandatoDiPagamento.
     * 
     * @return numeroCapitolo
     */
    public java.lang.Integer getNumeroCapitolo() {
        return numeroCapitolo;
    }


    /**
     * Sets the numeroCapitolo value for this MandatoDiPagamento.
     * 
     * @param numeroCapitolo
     */
    public void setNumeroCapitolo(java.lang.Integer numeroCapitolo) {
        this.numeroCapitolo = numeroCapitolo;
    }


    /**
     * Gets the numeroImpegno value for this MandatoDiPagamento.
     * 
     * @return numeroImpegno
     */
    public java.lang.Integer getNumeroImpegno() {
        return numeroImpegno;
    }


    /**
     * Sets the numeroImpegno value for this MandatoDiPagamento.
     * 
     * @param numeroImpegno
     */
    public void setNumeroImpegno(java.lang.Integer numeroImpegno) {
        this.numeroImpegno = numeroImpegno;
    }


    /**
     * Gets the numeroLiquidazione value for this MandatoDiPagamento.
     * 
     * @return numeroLiquidazione
     */
    public java.lang.Integer getNumeroLiquidazione() {
        return numeroLiquidazione;
    }


    /**
     * Sets the numeroLiquidazione value for this MandatoDiPagamento.
     * 
     * @param numeroLiquidazione
     */
    public void setNumeroLiquidazione(java.lang.Integer numeroLiquidazione) {
        this.numeroLiquidazione = numeroLiquidazione;
    }


    /**
     * Gets the numeroOrdinativo value for this MandatoDiPagamento.
     * 
     * @return numeroOrdinativo
     */
    public java.lang.Integer getNumeroOrdinativo() {
        return numeroOrdinativo;
    }


    /**
     * Sets the numeroOrdinativo value for this MandatoDiPagamento.
     * 
     * @param numeroOrdinativo
     */
    public void setNumeroOrdinativo(java.lang.Integer numeroOrdinativo) {
        this.numeroOrdinativo = numeroOrdinativo;
    }


    /**
     * Gets the numeroSubImpegno value for this MandatoDiPagamento.
     * 
     * @return numeroSubImpegno
     */
    public java.lang.Integer getNumeroSubImpegno() {
        return numeroSubImpegno;
    }


    /**
     * Sets the numeroSubImpegno value for this MandatoDiPagamento.
     * 
     * @param numeroSubImpegno
     */
    public void setNumeroSubImpegno(java.lang.Integer numeroSubImpegno) {
        this.numeroSubImpegno = numeroSubImpegno;
    }


    /**
     * Gets the provvedimento value for this MandatoDiPagamento.
     * 
     * @return provvedimento
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Provvedimento getProvvedimento() {
        return provvedimento;
    }


    /**
     * Sets the provvedimento value for this MandatoDiPagamento.
     * 
     * @param provvedimento
     */
    public void setProvvedimento(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Provvedimento provvedimento) {
        this.provvedimento = provvedimento;
    }


    /**
     * Gets the quietanza value for this MandatoDiPagamento.
     * 
     * @return quietanza
     */
    public java.lang.String getQuietanza() {
        return quietanza;
    }


    /**
     * Sets the quietanza value for this MandatoDiPagamento.
     * 
     * @param quietanza
     */
    public void setQuietanza(java.lang.String quietanza) {
        this.quietanza = quietanza;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MandatoDiPagamento)) return false;
        MandatoDiPagamento other = (MandatoDiPagamento) obj;
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
            ((this.annoImpegno==null && other.getAnnoImpegno()==null) || 
             (this.annoImpegno!=null &&
              this.annoImpegno.equals(other.getAnnoImpegno()))) &&
            ((this.annoLiquidazione==null && other.getAnnoLiquidazione()==null) || 
             (this.annoLiquidazione!=null &&
              this.annoLiquidazione.equals(other.getAnnoLiquidazione()))) &&
            ((this.annoOrdinativo==null && other.getAnnoOrdinativo()==null) || 
             (this.annoOrdinativo!=null &&
              this.annoOrdinativo.equals(other.getAnnoOrdinativo()))) &&
            ((this.codiceSubOrdinativo==null && other.getCodiceSubOrdinativo()==null) || 
             (this.codiceSubOrdinativo!=null &&
              this.codiceSubOrdinativo.equals(other.getCodiceSubOrdinativo()))) &&
            ((this.creditore==null && other.getCreditore()==null) || 
             (this.creditore!=null &&
              this.creditore.equals(other.getCreditore()))) &&
            ((this.dataEmissione==null && other.getDataEmissione()==null) || 
             (this.dataEmissione!=null &&
              this.dataEmissione.equals(other.getDataEmissione()))) &&
            ((this.importoOrdinativo==null && other.getImportoOrdinativo()==null) || 
             (this.importoOrdinativo!=null &&
              this.importoOrdinativo.equals(other.getImportoOrdinativo()))) &&
            ((this.numeroArticolo==null && other.getNumeroArticolo()==null) || 
             (this.numeroArticolo!=null &&
              this.numeroArticolo.equals(other.getNumeroArticolo()))) &&
            ((this.numeroCapitolo==null && other.getNumeroCapitolo()==null) || 
             (this.numeroCapitolo!=null &&
              this.numeroCapitolo.equals(other.getNumeroCapitolo()))) &&
            ((this.numeroImpegno==null && other.getNumeroImpegno()==null) || 
             (this.numeroImpegno!=null &&
              this.numeroImpegno.equals(other.getNumeroImpegno()))) &&
            ((this.numeroLiquidazione==null && other.getNumeroLiquidazione()==null) || 
             (this.numeroLiquidazione!=null &&
              this.numeroLiquidazione.equals(other.getNumeroLiquidazione()))) &&
            ((this.numeroOrdinativo==null && other.getNumeroOrdinativo()==null) || 
             (this.numeroOrdinativo!=null &&
              this.numeroOrdinativo.equals(other.getNumeroOrdinativo()))) &&
            ((this.numeroSubImpegno==null && other.getNumeroSubImpegno()==null) || 
             (this.numeroSubImpegno!=null &&
              this.numeroSubImpegno.equals(other.getNumeroSubImpegno()))) &&
            ((this.provvedimento==null && other.getProvvedimento()==null) || 
             (this.provvedimento!=null &&
              this.provvedimento.equals(other.getProvvedimento()))) &&
            ((this.quietanza==null && other.getQuietanza()==null) || 
             (this.quietanza!=null &&
              this.quietanza.equals(other.getQuietanza())));
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
        if (getAnnoImpegno() != null) {
            _hashCode += getAnnoImpegno().hashCode();
        }
        if (getAnnoLiquidazione() != null) {
            _hashCode += getAnnoLiquidazione().hashCode();
        }
        if (getAnnoOrdinativo() != null) {
            _hashCode += getAnnoOrdinativo().hashCode();
        }
        if (getCodiceSubOrdinativo() != null) {
            _hashCode += getCodiceSubOrdinativo().hashCode();
        }
        if (getCreditore() != null) {
            _hashCode += getCreditore().hashCode();
        }
        if (getDataEmissione() != null) {
            _hashCode += getDataEmissione().hashCode();
        }
        if (getImportoOrdinativo() != null) {
            _hashCode += getImportoOrdinativo().hashCode();
        }
        if (getNumeroArticolo() != null) {
            _hashCode += getNumeroArticolo().hashCode();
        }
        if (getNumeroCapitolo() != null) {
            _hashCode += getNumeroCapitolo().hashCode();
        }
        if (getNumeroImpegno() != null) {
            _hashCode += getNumeroImpegno().hashCode();
        }
        if (getNumeroLiquidazione() != null) {
            _hashCode += getNumeroLiquidazione().hashCode();
        }
        if (getNumeroOrdinativo() != null) {
            _hashCode += getNumeroOrdinativo().hashCode();
        }
        if (getNumeroSubImpegno() != null) {
            _hashCode += getNumeroSubImpegno().hashCode();
        }
        if (getProvvedimento() != null) {
            _hashCode += getProvvedimento().hashCode();
        }
        if (getQuietanza() != null) {
            _hashCode += getQuietanza().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MandatoDiPagamento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "mandatoDiPagamento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoEsercizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoEsercizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoImpegno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoImpegno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoLiquidazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoLiquidazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoOrdinativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoOrdinativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceSubOrdinativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceSubOrdinativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creditore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataEmissione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataEmissione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importoOrdinativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importoOrdinativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
        elemField.setFieldName("numeroImpegno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroImpegno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroLiquidazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroLiquidazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroOrdinativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroOrdinativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provvedimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "provvedimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "provvedimento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quietanza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quietanza"));
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
