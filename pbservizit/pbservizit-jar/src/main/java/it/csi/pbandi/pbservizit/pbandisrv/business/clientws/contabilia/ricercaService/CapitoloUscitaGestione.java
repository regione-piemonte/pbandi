/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * CapitoloUscitaGestione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class CapitoloUscitaGestione  extends it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Capitolo  implements java.io.Serializable {
    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ImportoCapitoloUscitaGestione[] importiUG;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Macroaggregato macroaggregato;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Missione missione;

    private it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Programma programma;

    public CapitoloUscitaGestione() {
    }

    public CapitoloUscitaGestione(
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
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ImportoCapitoloUscitaGestione[] importiUG,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Macroaggregato macroaggregato,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Missione missione,
           it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Programma programma) {
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
        this.importiUG = importiUG;
        this.macroaggregato = macroaggregato;
        this.missione = missione;
        this.programma = programma;
    }


    /**
     * Gets the importiUG value for this CapitoloUscitaGestione.
     * 
     * @return importiUG
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ImportoCapitoloUscitaGestione[] getImportiUG() {
        return importiUG;
    }


    /**
     * Sets the importiUG value for this CapitoloUscitaGestione.
     * 
     * @param importiUG
     */
    public void setImportiUG(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ImportoCapitoloUscitaGestione[] importiUG) {
        this.importiUG = importiUG;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ImportoCapitoloUscitaGestione getImportiUG(int i) {
        return this.importiUG[i];
    }

    public void setImportiUG(int i, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ImportoCapitoloUscitaGestione _value) {
        this.importiUG[i] = _value;
    }


    /**
     * Gets the macroaggregato value for this CapitoloUscitaGestione.
     * 
     * @return macroaggregato
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Macroaggregato getMacroaggregato() {
        return macroaggregato;
    }


    /**
     * Sets the macroaggregato value for this CapitoloUscitaGestione.
     * 
     * @param macroaggregato
     */
    public void setMacroaggregato(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Macroaggregato macroaggregato) {
        this.macroaggregato = macroaggregato;
    }


    /**
     * Gets the missione value for this CapitoloUscitaGestione.
     * 
     * @return missione
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Missione getMissione() {
        return missione;
    }


    /**
     * Sets the missione value for this CapitoloUscitaGestione.
     * 
     * @param missione
     */
    public void setMissione(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Missione missione) {
        this.missione = missione;
    }


    /**
     * Gets the programma value for this CapitoloUscitaGestione.
     * 
     * @return programma
     */
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Programma getProgramma() {
        return programma;
    }


    /**
     * Sets the programma value for this CapitoloUscitaGestione.
     * 
     * @param programma
     */
    public void setProgramma(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Programma programma) {
        this.programma = programma;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CapitoloUscitaGestione)) return false;
        CapitoloUscitaGestione other = (CapitoloUscitaGestione) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.importiUG==null && other.getImportiUG()==null) || 
             (this.importiUG!=null &&
              java.util.Arrays.equals(this.importiUG, other.getImportiUG()))) &&
            ((this.macroaggregato==null && other.getMacroaggregato()==null) || 
             (this.macroaggregato!=null &&
              this.macroaggregato.equals(other.getMacroaggregato()))) &&
            ((this.missione==null && other.getMissione()==null) || 
             (this.missione!=null &&
              this.missione.equals(other.getMissione()))) &&
            ((this.programma==null && other.getProgramma()==null) || 
             (this.programma!=null &&
              this.programma.equals(other.getProgramma())));
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
        if (getImportiUG() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getImportiUG());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getImportiUG(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMacroaggregato() != null) {
            _hashCode += getMacroaggregato().hashCode();
        }
        if (getMissione() != null) {
            _hashCode += getMissione().hashCode();
        }
        if (getProgramma() != null) {
            _hashCode += getProgramma().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CapitoloUscitaGestione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "capitoloUscitaGestione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importiUG");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importiUG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "importoCapitoloUscitaGestione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("macroaggregato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "macroaggregato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "macroaggregato"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("missione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "missione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "missione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("programma");
        elemField.setXmlName(new javax.xml.namespace.QName("", "programma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "programma"));
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
