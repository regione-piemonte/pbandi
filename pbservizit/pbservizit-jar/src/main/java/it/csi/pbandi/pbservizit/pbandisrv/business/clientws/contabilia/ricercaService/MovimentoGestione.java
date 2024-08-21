/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * MovimentoGestione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class MovimentoGestione  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.EntitaCodificataBase  implements java.io.Serializable {
    private java.lang.String cig;

    private java.lang.String codiceSoggetto;

    private java.lang.String cup;

    private java.math.BigDecimal importo;

    private java.lang.Integer numeroArticolo;

    private java.lang.Integer numeroCapitolo;

    private java.lang.Integer numeroUEB;

    private java.lang.Boolean parereFinanziario;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.PianoDeiContiFinanziario pdc;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Provvedimento provvedimento;

    public MovimentoGestione() {
    }

    public MovimentoGestione(
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
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Provvedimento provvedimento) {
        super(
            codice,
            stato,
            descrizione);
        this.cig = cig;
        this.codiceSoggetto = codiceSoggetto;
        this.cup = cup;
        this.importo = importo;
        this.numeroArticolo = numeroArticolo;
        this.numeroCapitolo = numeroCapitolo;
        this.numeroUEB = numeroUEB;
        this.parereFinanziario = parereFinanziario;
        this.pdc = pdc;
        this.provvedimento = provvedimento;
    }


    /**
     * Gets the cig value for this MovimentoGestione.
     * 
     * @return cig
     */
    public java.lang.String getCig() {
        return cig;
    }


    /**
     * Sets the cig value for this MovimentoGestione.
     * 
     * @param cig
     */
    public void setCig(java.lang.String cig) {
        this.cig = cig;
    }


    /**
     * Gets the codiceSoggetto value for this MovimentoGestione.
     * 
     * @return codiceSoggetto
     */
    public java.lang.String getCodiceSoggetto() {
        return codiceSoggetto;
    }


    /**
     * Sets the codiceSoggetto value for this MovimentoGestione.
     * 
     * @param codiceSoggetto
     */
    public void setCodiceSoggetto(java.lang.String codiceSoggetto) {
        this.codiceSoggetto = codiceSoggetto;
    }


    /**
     * Gets the cup value for this MovimentoGestione.
     * 
     * @return cup
     */
    public java.lang.String getCup() {
        return cup;
    }


    /**
     * Sets the cup value for this MovimentoGestione.
     * 
     * @param cup
     */
    public void setCup(java.lang.String cup) {
        this.cup = cup;
    }


    /**
     * Gets the importo value for this MovimentoGestione.
     * 
     * @return importo
     */
    public java.math.BigDecimal getImporto() {
        return importo;
    }


    /**
     * Sets the importo value for this MovimentoGestione.
     * 
     * @param importo
     */
    public void setImporto(java.math.BigDecimal importo) {
        this.importo = importo;
    }


    /**
     * Gets the numeroArticolo value for this MovimentoGestione.
     * 
     * @return numeroArticolo
     */
    public java.lang.Integer getNumeroArticolo() {
        return numeroArticolo;
    }


    /**
     * Sets the numeroArticolo value for this MovimentoGestione.
     * 
     * @param numeroArticolo
     */
    public void setNumeroArticolo(java.lang.Integer numeroArticolo) {
        this.numeroArticolo = numeroArticolo;
    }


    /**
     * Gets the numeroCapitolo value for this MovimentoGestione.
     * 
     * @return numeroCapitolo
     */
    public java.lang.Integer getNumeroCapitolo() {
        return numeroCapitolo;
    }


    /**
     * Sets the numeroCapitolo value for this MovimentoGestione.
     * 
     * @param numeroCapitolo
     */
    public void setNumeroCapitolo(java.lang.Integer numeroCapitolo) {
        this.numeroCapitolo = numeroCapitolo;
    }


    /**
     * Gets the numeroUEB value for this MovimentoGestione.
     * 
     * @return numeroUEB
     */
    public java.lang.Integer getNumeroUEB() {
        return numeroUEB;
    }


    /**
     * Sets the numeroUEB value for this MovimentoGestione.
     * 
     * @param numeroUEB
     */
    public void setNumeroUEB(java.lang.Integer numeroUEB) {
        this.numeroUEB = numeroUEB;
    }


    /**
     * Gets the parereFinanziario value for this MovimentoGestione.
     * 
     * @return parereFinanziario
     */
    public java.lang.Boolean getParereFinanziario() {
        return parereFinanziario;
    }


    /**
     * Sets the parereFinanziario value for this MovimentoGestione.
     * 
     * @param parereFinanziario
     */
    public void setParereFinanziario(java.lang.Boolean parereFinanziario) {
        this.parereFinanziario = parereFinanziario;
    }


    /**
     * Gets the pdc value for this MovimentoGestione.
     * 
     * @return pdc
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.PianoDeiContiFinanziario getPdc() {
        return pdc;
    }


    /**
     * Sets the pdc value for this MovimentoGestione.
     * 
     * @param pdc
     */
    public void setPdc(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.PianoDeiContiFinanziario pdc) {
        this.pdc = pdc;
    }


    /**
     * Gets the provvedimento value for this MovimentoGestione.
     * 
     * @return provvedimento
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Provvedimento getProvvedimento() {
        return provvedimento;
    }


    /**
     * Sets the provvedimento value for this MovimentoGestione.
     * 
     * @param provvedimento
     */
    public void setProvvedimento(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Provvedimento provvedimento) {
        this.provvedimento = provvedimento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MovimentoGestione)) return false;
        MovimentoGestione other = (MovimentoGestione) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.cig==null && other.getCig()==null) || 
             (this.cig!=null &&
              this.cig.equals(other.getCig()))) &&
            ((this.codiceSoggetto==null && other.getCodiceSoggetto()==null) || 
             (this.codiceSoggetto!=null &&
              this.codiceSoggetto.equals(other.getCodiceSoggetto()))) &&
            ((this.cup==null && other.getCup()==null) || 
             (this.cup!=null &&
              this.cup.equals(other.getCup()))) &&
            ((this.importo==null && other.getImporto()==null) || 
             (this.importo!=null &&
              this.importo.equals(other.getImporto()))) &&
            ((this.numeroArticolo==null && other.getNumeroArticolo()==null) || 
             (this.numeroArticolo!=null &&
              this.numeroArticolo.equals(other.getNumeroArticolo()))) &&
            ((this.numeroCapitolo==null && other.getNumeroCapitolo()==null) || 
             (this.numeroCapitolo!=null &&
              this.numeroCapitolo.equals(other.getNumeroCapitolo()))) &&
            ((this.numeroUEB==null && other.getNumeroUEB()==null) || 
             (this.numeroUEB!=null &&
              this.numeroUEB.equals(other.getNumeroUEB()))) &&
            ((this.parereFinanziario==null && other.getParereFinanziario()==null) || 
             (this.parereFinanziario!=null &&
              this.parereFinanziario.equals(other.getParereFinanziario()))) &&
            ((this.pdc==null && other.getPdc()==null) || 
             (this.pdc!=null &&
              this.pdc.equals(other.getPdc()))) &&
            ((this.provvedimento==null && other.getProvvedimento()==null) || 
             (this.provvedimento!=null &&
              this.provvedimento.equals(other.getProvvedimento())));
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
        if (getCig() != null) {
            _hashCode += getCig().hashCode();
        }
        if (getCodiceSoggetto() != null) {
            _hashCode += getCodiceSoggetto().hashCode();
        }
        if (getCup() != null) {
            _hashCode += getCup().hashCode();
        }
        if (getImporto() != null) {
            _hashCode += getImporto().hashCode();
        }
        if (getNumeroArticolo() != null) {
            _hashCode += getNumeroArticolo().hashCode();
        }
        if (getNumeroCapitolo() != null) {
            _hashCode += getNumeroCapitolo().hashCode();
        }
        if (getNumeroUEB() != null) {
            _hashCode += getNumeroUEB().hashCode();
        }
        if (getParereFinanziario() != null) {
            _hashCode += getParereFinanziario().hashCode();
        }
        if (getPdc() != null) {
            _hashCode += getPdc().hashCode();
        }
        if (getProvvedimento() != null) {
            _hashCode += getProvvedimento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MovimentoGestione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "movimentoGestione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cig");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cig"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("cup");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("numeroUEB");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroUEB"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parereFinanziario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parereFinanziario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pdc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pdc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "pianoDeiContiFinanziario"));
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
