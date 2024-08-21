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
 * Class TabAppalti.
 * 
 * @version $Revision$ $Date$
 */
public class TabAppalti implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _idAppalto
     */
    private java.lang.String _idAppalto;

    /**
     * Field _oggettoAppalto
     */
    private java.lang.String _oggettoAppalto;

    /**
     * Field _sezRigheAppaltoList
     */
    private java.util.Vector _sezRigheAppaltoList;


      //----------------/
     //- Constructors -/
    //----------------/

    public TabAppalti() 
     {
        super();
        this._sezRigheAppaltoList = new java.util.Vector();
    } //-- it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vSezRigheAppalto
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addSezRigheAppalto(it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto vSezRigheAppalto)
        throws java.lang.IndexOutOfBoundsException
    {
        this._sezRigheAppaltoList.addElement(vSezRigheAppalto);
    } //-- void addSezRigheAppalto(it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto) 

    /**
     * 
     * 
     * @param index
     * @param vSezRigheAppalto
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addSezRigheAppalto(int index, it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto vSezRigheAppalto)
        throws java.lang.IndexOutOfBoundsException
    {
        this._sezRigheAppaltoList.add(index, vSezRigheAppalto);
    } //-- void addSezRigheAppalto(int, it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto) 

    /**
     * Method enumerateSezRigheAppalto
     * 
     * 
     * 
     * @return an Enumeration over all
     * it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto
     * elements
     */
    public java.util.Enumeration enumerateSezRigheAppalto()
    {
        return this._sezRigheAppaltoList.elements();
    } //-- java.util.Enumeration enumerateSezRigheAppalto() 

    /**
     * Returns the value of field 'idAppalto'.
     * 
     * @return the value of field 'IdAppalto'.
     */
    public java.lang.String getIdAppalto()
    {
        return this._idAppalto;
    } //-- java.lang.String getIdAppalto() 

    /**
     * Returns the value of field 'oggettoAppalto'.
     * 
     * @return the value of field 'OggettoAppalto'.
     */
    public java.lang.String getOggettoAppalto()
    {
        return this._oggettoAppalto;
    } //-- java.lang.String getOggettoAppalto() 

    /**
     * Method getSezRigheAppalto
     * 
     * 
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto
     * at the given index
     */
    public it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto getSezRigheAppalto(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        // check bounds for index
        if (index < 0 || index >= this._sezRigheAppaltoList.size()) {
            throw new IndexOutOfBoundsException("getSezRigheAppalto: Index value '" + index + "' not in range [0.." + (this._sezRigheAppaltoList.size() - 1) + "]");
        }
        
        return (it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto) _sezRigheAppaltoList.get(index);
    } //-- it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto getSezRigheAppalto(int) 

    /**
     * Method getSezRigheAppalto
     * 
     * 
     * 
     * @return this collection as an Array
     */
    public it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto[] getSezRigheAppalto()
    {
        int size = this._sezRigheAppaltoList.size();
        it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto[] array = new it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto[size];
        for (int index = 0; index < size; index++){
            array[index] = (it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto) _sezRigheAppaltoList.get(index);
        }
        
        return array;
    } //-- it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto[] getSezRigheAppalto() 

    /**
     * Method getSezRigheAppaltoCount
     * 
     * 
     * 
     * @return the size of this collection
     */
    public int getSezRigheAppaltoCount()
    {
        return this._sezRigheAppaltoList.size();
    } //-- int getSezRigheAppaltoCount() 

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
     */
    public void removeAllSezRigheAppalto()
    {
        this._sezRigheAppaltoList.clear();
    } //-- void removeAllSezRigheAppalto() 

    /**
     * Method removeSezRigheAppalto
     * 
     * 
     * 
     * @param vSezRigheAppalto
     * @return true if the object was removed from the collection.
     */
    public boolean removeSezRigheAppalto(it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto vSezRigheAppalto)
    {
        boolean removed = _sezRigheAppaltoList.remove(vSezRigheAppalto);
        return removed;
    } //-- boolean removeSezRigheAppalto(it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto) 

    /**
     * Method removeSezRigheAppaltoAt
     * 
     * 
     * 
     * @param index
     * @return the element removed from the collection
     */
    public it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto removeSezRigheAppaltoAt(int index)
    {
        Object obj = this._sezRigheAppaltoList.remove(index);
        return (it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto) obj;
    } //-- it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto removeSezRigheAppaltoAt(int) 

    /**
     * Sets the value of field 'idAppalto'.
     * 
     * @param idAppalto the value of field 'idAppalto'.
     */
    public void setIdAppalto(java.lang.String idAppalto)
    {
        this._idAppalto = idAppalto;
    } //-- void setIdAppalto(java.lang.String) 

    /**
     * Sets the value of field 'oggettoAppalto'.
     * 
     * @param oggettoAppalto the value of field 'oggettoAppalto'.
     */
    public void setOggettoAppalto(java.lang.String oggettoAppalto)
    {
        this._oggettoAppalto = oggettoAppalto;
    } //-- void setOggettoAppalto(java.lang.String) 

    /**
     * 
     * 
     * @param index
     * @param vSezRigheAppalto
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setSezRigheAppalto(int index, it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto vSezRigheAppalto)
        throws java.lang.IndexOutOfBoundsException
    {
        // check bounds for index
        if (index < 0 || index >= this._sezRigheAppaltoList.size()) {
            throw new IndexOutOfBoundsException("setSezRigheAppalto: Index value '" + index + "' not in range [0.." + (this._sezRigheAppaltoList.size() - 1) + "]");
        }
        
        this._sezRigheAppaltoList.set(index, vSezRigheAppalto);
    } //-- void setSezRigheAppalto(int, it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto) 

    /**
     * 
     * 
     * @param vSezRigheAppaltoArray
     */
    public void setSezRigheAppalto(it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto[] vSezRigheAppaltoArray)
    {
        //-- copy array
        _sezRigheAppaltoList.clear();
        
        for (int i = 0; i < vSezRigheAppaltoArray.length; i++) {
                this._sezRigheAppaltoList.add(vSezRigheAppaltoArray[i]);
        }
    } //-- void setSezRigheAppalto(it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto) 

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
     * it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti
     */
    public static it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti) Unmarshaller.unmarshal(it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti.class, reader);
    } //-- it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti unmarshal(java.io.Reader) 

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
