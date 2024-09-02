/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.0.5</a>, using an XML
 * Schema.
 * $Id$
 */

package it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class ControlloType.
 * 
 * @version $Revision$ $Date$
 */
public class ControlloType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _valore
     */
    private java.lang.String _valore;

    /**
     * Field _nota
     */
    private java.lang.String _nota;


      //----------------/
     //- Constructors -/
    //----------------/

    public ControlloType() 
     {
        super();
    } //-- it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.ControlloType()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'nota'.
     * 
     * @return the value of field 'Nota'.
     */
    public java.lang.String getNota()
    {
        return this._nota;
    } //-- java.lang.String getNota() 

    /**
     * Returns the value of field 'valore'.
     * 
     * @return the value of field 'Valore'.
     */
    public java.lang.String getValore()
    {
        return this._valore;
    } //-- java.lang.String getValore() 

    /**
     * Method isValid
     * 
     * 
     * 
     * @return true if this object is valid according to the schema
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * 
     * 
     * @param out
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * 
     * 
     * @param handler
     * @throws java.io.IOException if an IOException occurs during
     * marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Sets the value of field 'nota'.
     * 
     * @param nota the value of field 'nota'.
     */
    public void setNota(java.lang.String nota)
    {
        this._nota = nota;
    } //-- void setNota(java.lang.String) 

    /**
     * Sets the value of field 'valore'.
     * 
     * @param valore the value of field 'valore'.
     */
    public void setValore(java.lang.String valore)
    {
        this._valore = valore;
    } //-- void setValore(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * 
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled
     * it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.ControlloType
     */
    public static it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.ControlloType unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.ControlloType) Unmarshaller.unmarshal(it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.ControlloType.class, reader);
    } //-- it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.ControlloType unmarshal(java.io.Reader) 

    /**
     * 
     * 
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}