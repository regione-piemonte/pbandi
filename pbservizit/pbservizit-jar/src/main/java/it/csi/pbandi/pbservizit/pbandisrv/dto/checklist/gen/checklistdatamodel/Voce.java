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
 * Class Voce.
 * 
 * @version $Revision$ $Date$
 */
public class Voce implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _descrizione
     */
    private java.lang.String _descrizione;


      //----------------/
     //- Constructors -/
    //----------------/

    public Voce() 
     {
        super();
    } //-- it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'descrizione'.
     * 
     * @return the value of field 'Descrizione'.
     */
    public java.lang.String getDescrizione()
    {
        return this._descrizione;
    } //-- java.lang.String getDescrizione() 

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
     * Sets the value of field 'descrizione'.
     * 
     * @param descrizione the value of field 'descrizione'.
     */
    public void setDescrizione(java.lang.String descrizione)
    {
        this._descrizione = descrizione;
    } //-- void setDescrizione(java.lang.String) 

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
     * it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce
     */
    public static it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce) Unmarshaller.unmarshal(it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce.class, reader);
    } //-- it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce unmarshal(java.io.Reader) 

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
