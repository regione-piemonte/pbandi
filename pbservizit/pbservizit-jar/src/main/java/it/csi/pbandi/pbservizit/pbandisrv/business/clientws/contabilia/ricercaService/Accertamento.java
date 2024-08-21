/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * Accertamento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class Accertamento  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.MovimentoGestione  implements java.io.Serializable {
    private java.lang.Integer annoAccertamento;

    private java.lang.Integer annoAccertamentoOrigine;

    private java.lang.Integer annoAccertamentoRiaccertato;

    private java.math.BigDecimal disponibilitaIncassare;

    private java.lang.Integer numAccertamentoOrigine;

    private java.lang.Integer numAccertamentoRiaccertato;

    private java.lang.Integer numeroAccertamento;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubAccertamento[] subAccertamenti;

    public Accertamento() {
    }

    public Accertamento(
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
           java.lang.Integer annoAccertamento,
           java.lang.Integer annoAccertamentoOrigine,
           java.lang.Integer annoAccertamentoRiaccertato,
           java.math.BigDecimal disponibilitaIncassare,
           java.lang.Integer numAccertamentoOrigine,
           java.lang.Integer numAccertamentoRiaccertato,
           java.lang.Integer numeroAccertamento,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubAccertamento[] subAccertamenti) {
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
        this.annoAccertamento = annoAccertamento;
        this.annoAccertamentoOrigine = annoAccertamentoOrigine;
        this.annoAccertamentoRiaccertato = annoAccertamentoRiaccertato;
        this.disponibilitaIncassare = disponibilitaIncassare;
        this.numAccertamentoOrigine = numAccertamentoOrigine;
        this.numAccertamentoRiaccertato = numAccertamentoRiaccertato;
        this.numeroAccertamento = numeroAccertamento;
        this.subAccertamenti = subAccertamenti;
    }


    /**
     * Gets the annoAccertamento value for this Accertamento.
     * 
     * @return annoAccertamento
     */
    public java.lang.Integer getAnnoAccertamento() {
        return annoAccertamento;
    }


    /**
     * Sets the annoAccertamento value for this Accertamento.
     * 
     * @param annoAccertamento
     */
    public void setAnnoAccertamento(java.lang.Integer annoAccertamento) {
        this.annoAccertamento = annoAccertamento;
    }


    /**
     * Gets the annoAccertamentoOrigine value for this Accertamento.
     * 
     * @return annoAccertamentoOrigine
     */
    public java.lang.Integer getAnnoAccertamentoOrigine() {
        return annoAccertamentoOrigine;
    }


    /**
     * Sets the annoAccertamentoOrigine value for this Accertamento.
     * 
     * @param annoAccertamentoOrigine
     */
    public void setAnnoAccertamentoOrigine(java.lang.Integer annoAccertamentoOrigine) {
        this.annoAccertamentoOrigine = annoAccertamentoOrigine;
    }


    /**
     * Gets the annoAccertamentoRiaccertato value for this Accertamento.
     * 
     * @return annoAccertamentoRiaccertato
     */
    public java.lang.Integer getAnnoAccertamentoRiaccertato() {
        return annoAccertamentoRiaccertato;
    }


    /**
     * Sets the annoAccertamentoRiaccertato value for this Accertamento.
     * 
     * @param annoAccertamentoRiaccertato
     */
    public void setAnnoAccertamentoRiaccertato(java.lang.Integer annoAccertamentoRiaccertato) {
        this.annoAccertamentoRiaccertato = annoAccertamentoRiaccertato;
    }


    /**
     * Gets the disponibilitaIncassare value for this Accertamento.
     * 
     * @return disponibilitaIncassare
     */
    public java.math.BigDecimal getDisponibilitaIncassare() {
        return disponibilitaIncassare;
    }


    /**
     * Sets the disponibilitaIncassare value for this Accertamento.
     * 
     * @param disponibilitaIncassare
     */
    public void setDisponibilitaIncassare(java.math.BigDecimal disponibilitaIncassare) {
        this.disponibilitaIncassare = disponibilitaIncassare;
    }


    /**
     * Gets the numAccertamentoOrigine value for this Accertamento.
     * 
     * @return numAccertamentoOrigine
     */
    public java.lang.Integer getNumAccertamentoOrigine() {
        return numAccertamentoOrigine;
    }


    /**
     * Sets the numAccertamentoOrigine value for this Accertamento.
     * 
     * @param numAccertamentoOrigine
     */
    public void setNumAccertamentoOrigine(java.lang.Integer numAccertamentoOrigine) {
        this.numAccertamentoOrigine = numAccertamentoOrigine;
    }


    /**
     * Gets the numAccertamentoRiaccertato value for this Accertamento.
     * 
     * @return numAccertamentoRiaccertato
     */
    public java.lang.Integer getNumAccertamentoRiaccertato() {
        return numAccertamentoRiaccertato;
    }


    /**
     * Sets the numAccertamentoRiaccertato value for this Accertamento.
     * 
     * @param numAccertamentoRiaccertato
     */
    public void setNumAccertamentoRiaccertato(java.lang.Integer numAccertamentoRiaccertato) {
        this.numAccertamentoRiaccertato = numAccertamentoRiaccertato;
    }


    /**
     * Gets the numeroAccertamento value for this Accertamento.
     * 
     * @return numeroAccertamento
     */
    public java.lang.Integer getNumeroAccertamento() {
        return numeroAccertamento;
    }


    /**
     * Sets the numeroAccertamento value for this Accertamento.
     * 
     * @param numeroAccertamento
     */
    public void setNumeroAccertamento(java.lang.Integer numeroAccertamento) {
        this.numeroAccertamento = numeroAccertamento;
    }


    /**
     * Gets the subAccertamenti value for this Accertamento.
     * 
     * @return subAccertamenti
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubAccertamento[] getSubAccertamenti() {
        return subAccertamenti;
    }


    /**
     * Sets the subAccertamenti value for this Accertamento.
     * 
     * @param subAccertamenti
     */
    public void setSubAccertamenti(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubAccertamento[] subAccertamenti) {
        this.subAccertamenti = subAccertamenti;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubAccertamento getSubAccertamenti(int i) {
        return this.subAccertamenti[i];
    }

    public void setSubAccertamenti(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubAccertamento _value) {
        this.subAccertamenti[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Accertamento)) return false;
        Accertamento other = (Accertamento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.annoAccertamento==null && other.getAnnoAccertamento()==null) || 
             (this.annoAccertamento!=null &&
              this.annoAccertamento.equals(other.getAnnoAccertamento()))) &&
            ((this.annoAccertamentoOrigine==null && other.getAnnoAccertamentoOrigine()==null) || 
             (this.annoAccertamentoOrigine!=null &&
              this.annoAccertamentoOrigine.equals(other.getAnnoAccertamentoOrigine()))) &&
            ((this.annoAccertamentoRiaccertato==null && other.getAnnoAccertamentoRiaccertato()==null) || 
             (this.annoAccertamentoRiaccertato!=null &&
              this.annoAccertamentoRiaccertato.equals(other.getAnnoAccertamentoRiaccertato()))) &&
            ((this.disponibilitaIncassare==null && other.getDisponibilitaIncassare()==null) || 
             (this.disponibilitaIncassare!=null &&
              this.disponibilitaIncassare.equals(other.getDisponibilitaIncassare()))) &&
            ((this.numAccertamentoOrigine==null && other.getNumAccertamentoOrigine()==null) || 
             (this.numAccertamentoOrigine!=null &&
              this.numAccertamentoOrigine.equals(other.getNumAccertamentoOrigine()))) &&
            ((this.numAccertamentoRiaccertato==null && other.getNumAccertamentoRiaccertato()==null) || 
             (this.numAccertamentoRiaccertato!=null &&
              this.numAccertamentoRiaccertato.equals(other.getNumAccertamentoRiaccertato()))) &&
            ((this.numeroAccertamento==null && other.getNumeroAccertamento()==null) || 
             (this.numeroAccertamento!=null &&
              this.numeroAccertamento.equals(other.getNumeroAccertamento()))) &&
            ((this.subAccertamenti==null && other.getSubAccertamenti()==null) || 
             (this.subAccertamenti!=null &&
              java.util.Arrays.equals(this.subAccertamenti, other.getSubAccertamenti())));
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
        if (getAnnoAccertamento() != null) {
            _hashCode += getAnnoAccertamento().hashCode();
        }
        if (getAnnoAccertamentoOrigine() != null) {
            _hashCode += getAnnoAccertamentoOrigine().hashCode();
        }
        if (getAnnoAccertamentoRiaccertato() != null) {
            _hashCode += getAnnoAccertamentoRiaccertato().hashCode();
        }
        if (getDisponibilitaIncassare() != null) {
            _hashCode += getDisponibilitaIncassare().hashCode();
        }
        if (getNumAccertamentoOrigine() != null) {
            _hashCode += getNumAccertamentoOrigine().hashCode();
        }
        if (getNumAccertamentoRiaccertato() != null) {
            _hashCode += getNumAccertamentoRiaccertato().hashCode();
        }
        if (getNumeroAccertamento() != null) {
            _hashCode += getNumeroAccertamento().hashCode();
        }
        if (getSubAccertamenti() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSubAccertamenti());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSubAccertamenti(), i);
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
        new org.apache.axis.description.TypeDesc(Accertamento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "accertamento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoAccertamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoAccertamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoAccertamentoOrigine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoAccertamentoOrigine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoAccertamentoRiaccertato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoAccertamentoRiaccertato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disponibilitaIncassare");
        elemField.setXmlName(new javax.xml.namespace.QName("", "disponibilitaIncassare"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numAccertamentoOrigine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numAccertamentoOrigine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numAccertamentoRiaccertato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numAccertamentoRiaccertato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroAccertamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroAccertamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subAccertamenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "subAccertamenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "subAccertamento"));
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
