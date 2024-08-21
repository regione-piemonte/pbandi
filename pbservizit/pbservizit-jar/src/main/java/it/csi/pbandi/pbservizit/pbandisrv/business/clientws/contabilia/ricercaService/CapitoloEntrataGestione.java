/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * CapitoloEntrataGestione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class CapitoloEntrataGestione  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Capitolo  implements java.io.Serializable {
    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Categoria categoria;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ImportoCapitoloEntrataGestione[] listaImportiCapitoloEG;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Tipologia tipologia;

    public CapitoloEntrataGestione() {
    }

    public CapitoloEntrataGestione(
           java.lang.String codice,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato stato,
           java.lang.Integer annoEsercizio,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ClassificatoreGenerico[] classificatoriGenerici,
           java.lang.String descrizione,
           java.lang.String descrizioneArticolo,
           java.lang.Integer numeroArticolo,
           java.lang.Integer numeroCapitolo,
           java.lang.Integer numeroUEB,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.PianoDeiContiFinanziario pianoDeiContiFinanziario,
           java.lang.Boolean rilevanteIva,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.StrutturaAmministrativa sac,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoFinanziamento tipoFinanziamento,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoFondo tipoFondo,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Titolo titolo,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Categoria categoria,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ImportoCapitoloEntrataGestione[] listaImportiCapitoloEG,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Tipologia tipologia) {
        super(
            codice,
            stato,
            annoEsercizio,
            classificatoriGenerici,
            descrizione,
            descrizioneArticolo,
            numeroArticolo,
            numeroCapitolo,
            numeroUEB,
            pianoDeiContiFinanziario,
            rilevanteIva,
            sac,
            tipoFinanziamento,
            tipoFondo,
            titolo);
        this.categoria = categoria;
        this.listaImportiCapitoloEG = listaImportiCapitoloEG;
        this.tipologia = tipologia;
    }


    /**
     * Gets the categoria value for this CapitoloEntrataGestione.
     * 
     * @return categoria
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Categoria getCategoria() {
        return categoria;
    }


    /**
     * Sets the categoria value for this CapitoloEntrataGestione.
     * 
     * @param categoria
     */
    public void setCategoria(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Categoria categoria) {
        this.categoria = categoria;
    }


    /**
     * Gets the listaImportiCapitoloEG value for this CapitoloEntrataGestione.
     * 
     * @return listaImportiCapitoloEG
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ImportoCapitoloEntrataGestione[] getListaImportiCapitoloEG() {
        return listaImportiCapitoloEG;
    }


    /**
     * Sets the listaImportiCapitoloEG value for this CapitoloEntrataGestione.
     * 
     * @param listaImportiCapitoloEG
     */
    public void setListaImportiCapitoloEG(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ImportoCapitoloEntrataGestione[] listaImportiCapitoloEG) {
        this.listaImportiCapitoloEG = listaImportiCapitoloEG;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ImportoCapitoloEntrataGestione getListaImportiCapitoloEG(int i) {
        return this.listaImportiCapitoloEG[i];
    }

    public void setListaImportiCapitoloEG(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ImportoCapitoloEntrataGestione _value) {
        this.listaImportiCapitoloEG[i] = _value;
    }


    /**
     * Gets the tipologia value for this CapitoloEntrataGestione.
     * 
     * @return tipologia
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Tipologia getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this CapitoloEntrataGestione.
     * 
     * @param tipologia
     */
    public void setTipologia(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CapitoloEntrataGestione)) return false;
        CapitoloEntrataGestione other = (CapitoloEntrataGestione) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.categoria==null && other.getCategoria()==null) || 
             (this.categoria!=null &&
              this.categoria.equals(other.getCategoria()))) &&
            ((this.listaImportiCapitoloEG==null && other.getListaImportiCapitoloEG()==null) || 
             (this.listaImportiCapitoloEG!=null &&
              java.util.Arrays.equals(this.listaImportiCapitoloEG, other.getListaImportiCapitoloEG()))) &&
            ((this.tipologia==null && other.getTipologia()==null) || 
             (this.tipologia!=null &&
              this.tipologia.equals(other.getTipologia())));
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
        if (getCategoria() != null) {
            _hashCode += getCategoria().hashCode();
        }
        if (getListaImportiCapitoloEG() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaImportiCapitoloEG());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaImportiCapitoloEG(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CapitoloEntrataGestione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "capitoloEntrataGestione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "categoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "categoria"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaImportiCapitoloEG");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listaImportiCapitoloEG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "importoCapitoloEntrataGestione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "tipologia"));
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
