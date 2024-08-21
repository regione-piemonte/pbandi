/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * Impegno.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class Impegno  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.MovimentoGestione  implements java.io.Serializable {
    private java.lang.Integer annoImpegno;

    private java.lang.Integer annoImpegnoOrigine;

    private java.lang.Integer annoImpegnoRiaccertato;

    private java.math.BigDecimal disponibilitaLiquidare;

    private java.lang.Integer numImpegnoOrigine;

    private java.lang.Integer numImpegnoRiaccertato;

    private java.lang.Integer numeroImpegno;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubImpegno[] subImpegni;

    private java.lang.String tipoImpegno;

    public Impegno() {
    }

    public Impegno(
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
           java.lang.Integer annoImpegno,
           java.lang.Integer annoImpegnoOrigine,
           java.lang.Integer annoImpegnoRiaccertato,
           java.math.BigDecimal disponibilitaLiquidare,
           java.lang.Integer numImpegnoOrigine,
           java.lang.Integer numImpegnoRiaccertato,
           java.lang.Integer numeroImpegno,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubImpegno[] subImpegni,
           java.lang.String tipoImpegno) {
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
        this.annoImpegno = annoImpegno;
        this.annoImpegnoOrigine = annoImpegnoOrigine;
        this.annoImpegnoRiaccertato = annoImpegnoRiaccertato;
        this.disponibilitaLiquidare = disponibilitaLiquidare;
        this.numImpegnoOrigine = numImpegnoOrigine;
        this.numImpegnoRiaccertato = numImpegnoRiaccertato;
        this.numeroImpegno = numeroImpegno;
        this.subImpegni = subImpegni;
        this.tipoImpegno = tipoImpegno;
    }


    /**
     * Gets the annoImpegno value for this Impegno.
     * 
     * @return annoImpegno
     */
    public java.lang.Integer getAnnoImpegno() {
        return annoImpegno;
    }


    /**
     * Sets the annoImpegno value for this Impegno.
     * 
     * @param annoImpegno
     */
    public void setAnnoImpegno(java.lang.Integer annoImpegno) {
        this.annoImpegno = annoImpegno;
    }


    /**
     * Gets the annoImpegnoOrigine value for this Impegno.
     * 
     * @return annoImpegnoOrigine
     */
    public java.lang.Integer getAnnoImpegnoOrigine() {
        return annoImpegnoOrigine;
    }


    /**
     * Sets the annoImpegnoOrigine value for this Impegno.
     * 
     * @param annoImpegnoOrigine
     */
    public void setAnnoImpegnoOrigine(java.lang.Integer annoImpegnoOrigine) {
        this.annoImpegnoOrigine = annoImpegnoOrigine;
    }


    /**
     * Gets the annoImpegnoRiaccertato value for this Impegno.
     * 
     * @return annoImpegnoRiaccertato
     */
    public java.lang.Integer getAnnoImpegnoRiaccertato() {
        return annoImpegnoRiaccertato;
    }


    /**
     * Sets the annoImpegnoRiaccertato value for this Impegno.
     * 
     * @param annoImpegnoRiaccertato
     */
    public void setAnnoImpegnoRiaccertato(java.lang.Integer annoImpegnoRiaccertato) {
        this.annoImpegnoRiaccertato = annoImpegnoRiaccertato;
    }


    /**
     * Gets the disponibilitaLiquidare value for this Impegno.
     * 
     * @return disponibilitaLiquidare
     */
    public java.math.BigDecimal getDisponibilitaLiquidare() {
        return disponibilitaLiquidare;
    }


    /**
     * Sets the disponibilitaLiquidare value for this Impegno.
     * 
     * @param disponibilitaLiquidare
     */
    public void setDisponibilitaLiquidare(java.math.BigDecimal disponibilitaLiquidare) {
        this.disponibilitaLiquidare = disponibilitaLiquidare;
    }


    /**
     * Gets the numImpegnoOrigine value for this Impegno.
     * 
     * @return numImpegnoOrigine
     */
    public java.lang.Integer getNumImpegnoOrigine() {
        return numImpegnoOrigine;
    }


    /**
     * Sets the numImpegnoOrigine value for this Impegno.
     * 
     * @param numImpegnoOrigine
     */
    public void setNumImpegnoOrigine(java.lang.Integer numImpegnoOrigine) {
        this.numImpegnoOrigine = numImpegnoOrigine;
    }


    /**
     * Gets the numImpegnoRiaccertato value for this Impegno.
     * 
     * @return numImpegnoRiaccertato
     */
    public java.lang.Integer getNumImpegnoRiaccertato() {
        return numImpegnoRiaccertato;
    }


    /**
     * Sets the numImpegnoRiaccertato value for this Impegno.
     * 
     * @param numImpegnoRiaccertato
     */
    public void setNumImpegnoRiaccertato(java.lang.Integer numImpegnoRiaccertato) {
        this.numImpegnoRiaccertato = numImpegnoRiaccertato;
    }


    /**
     * Gets the numeroImpegno value for this Impegno.
     * 
     * @return numeroImpegno
     */
    public java.lang.Integer getNumeroImpegno() {
        return numeroImpegno;
    }


    /**
     * Sets the numeroImpegno value for this Impegno.
     * 
     * @param numeroImpegno
     */
    public void setNumeroImpegno(java.lang.Integer numeroImpegno) {
        this.numeroImpegno = numeroImpegno;
    }


    /**
     * Gets the subImpegni value for this Impegno.
     * 
     * @return subImpegni
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubImpegno[] getSubImpegni() {
        return subImpegni;
    }


    /**
     * Sets the subImpegni value for this Impegno.
     * 
     * @param subImpegni
     */
    public void setSubImpegni(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubImpegno[] subImpegni) {
        this.subImpegni = subImpegni;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubImpegno getSubImpegni(int i) {
        return this.subImpegni[i];
    }

    public void setSubImpegni(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubImpegno _value) {
        this.subImpegni[i] = _value;
    }


    /**
     * Gets the tipoImpegno value for this Impegno.
     * 
     * @return tipoImpegno
     */
    public java.lang.String getTipoImpegno() {
        return tipoImpegno;
    }


    /**
     * Sets the tipoImpegno value for this Impegno.
     * 
     * @param tipoImpegno
     */
    public void setTipoImpegno(java.lang.String tipoImpegno) {
        this.tipoImpegno = tipoImpegno;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Impegno)) return false;
        Impegno other = (Impegno) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.annoImpegno==null && other.getAnnoImpegno()==null) || 
             (this.annoImpegno!=null &&
              this.annoImpegno.equals(other.getAnnoImpegno()))) &&
            ((this.annoImpegnoOrigine==null && other.getAnnoImpegnoOrigine()==null) || 
             (this.annoImpegnoOrigine!=null &&
              this.annoImpegnoOrigine.equals(other.getAnnoImpegnoOrigine()))) &&
            ((this.annoImpegnoRiaccertato==null && other.getAnnoImpegnoRiaccertato()==null) || 
             (this.annoImpegnoRiaccertato!=null &&
              this.annoImpegnoRiaccertato.equals(other.getAnnoImpegnoRiaccertato()))) &&
            ((this.disponibilitaLiquidare==null && other.getDisponibilitaLiquidare()==null) || 
             (this.disponibilitaLiquidare!=null &&
              this.disponibilitaLiquidare.equals(other.getDisponibilitaLiquidare()))) &&
            ((this.numImpegnoOrigine==null && other.getNumImpegnoOrigine()==null) || 
             (this.numImpegnoOrigine!=null &&
              this.numImpegnoOrigine.equals(other.getNumImpegnoOrigine()))) &&
            ((this.numImpegnoRiaccertato==null && other.getNumImpegnoRiaccertato()==null) || 
             (this.numImpegnoRiaccertato!=null &&
              this.numImpegnoRiaccertato.equals(other.getNumImpegnoRiaccertato()))) &&
            ((this.numeroImpegno==null && other.getNumeroImpegno()==null) || 
             (this.numeroImpegno!=null &&
              this.numeroImpegno.equals(other.getNumeroImpegno()))) &&
            ((this.subImpegni==null && other.getSubImpegni()==null) || 
             (this.subImpegni!=null &&
              java.util.Arrays.equals(this.subImpegni, other.getSubImpegni()))) &&
            ((this.tipoImpegno==null && other.getTipoImpegno()==null) || 
             (this.tipoImpegno!=null &&
              this.tipoImpegno.equals(other.getTipoImpegno())));
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
        if (getAnnoImpegno() != null) {
            _hashCode += getAnnoImpegno().hashCode();
        }
        if (getAnnoImpegnoOrigine() != null) {
            _hashCode += getAnnoImpegnoOrigine().hashCode();
        }
        if (getAnnoImpegnoRiaccertato() != null) {
            _hashCode += getAnnoImpegnoRiaccertato().hashCode();
        }
        if (getDisponibilitaLiquidare() != null) {
            _hashCode += getDisponibilitaLiquidare().hashCode();
        }
        if (getNumImpegnoOrigine() != null) {
            _hashCode += getNumImpegnoOrigine().hashCode();
        }
        if (getNumImpegnoRiaccertato() != null) {
            _hashCode += getNumImpegnoRiaccertato().hashCode();
        }
        if (getNumeroImpegno() != null) {
            _hashCode += getNumeroImpegno().hashCode();
        }
        if (getSubImpegni() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSubImpegni());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSubImpegni(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTipoImpegno() != null) {
            _hashCode += getTipoImpegno().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Impegno.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "impegno"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoImpegno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoImpegno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoImpegnoOrigine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoImpegnoOrigine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoImpegnoRiaccertato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoImpegnoRiaccertato"));
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
        elemField.setFieldName("numImpegnoOrigine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numImpegnoOrigine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numImpegnoRiaccertato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numImpegnoRiaccertato"));
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
        elemField.setFieldName("subImpegni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "subImpegni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "subImpegno"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoImpegno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoImpegno"));
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
