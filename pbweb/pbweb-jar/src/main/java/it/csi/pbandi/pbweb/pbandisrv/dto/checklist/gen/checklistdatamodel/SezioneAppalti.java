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

package it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class SezioneAppalti.
 * 
 * @version $Revision$ $Date$
 */
public class SezioneAppalti implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _tabAppaltiList
     */
    private java.util.Vector _tabAppaltiList;


      //----------------/
     //- Constructors -/
    //----------------/

    public SezioneAppalti() 
     {
        super();
        this._tabAppaltiList = new java.util.Vector();
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vTabAppalti
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addTabAppalti(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti vTabAppalti)
        throws java.lang.IndexOutOfBoundsException
    {
        this._tabAppaltiList.addElement(vTabAppalti);
    } //-- void addTabAppalti(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti) 

    /**
     * 
     * 
     * @param index
     * @param vTabAppalti
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addTabAppalti(int index, it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti vTabAppalti)
        throws java.lang.IndexOutOfBoundsException
    {
        this._tabAppaltiList.add(index, vTabAppalti);
    } //-- void addTabAppalti(int, it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti) 

    /**
     * Method enumerateTabAppalti
     * 
     * 
     * 
     * @return an Enumeration over all
     * it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti
     * elements
     */
    public java.util.Enumeration enumerateTabAppalti()
    {
        return this._tabAppaltiList.elements();
    } //-- java.util.Enumeration enumerateTabAppalti() 

    /**
     * Method getTabAppalti
     * 
     * 
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti
     * at the given index
     */
    public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti getTabAppalti(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        // check bounds for index
        if (index < 0 || index >= this._tabAppaltiList.size()) {
            throw new IndexOutOfBoundsException("getTabAppalti: Index value '" + index + "' not in range [0.." + (this._tabAppaltiList.size() - 1) + "]");
        }
        
        return (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti) _tabAppaltiList.get(index);
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti getTabAppalti(int) 

    /**
     * Method getTabAppalti
     * 
     * 
     * 
     * @return this collection as an Array
     */
    public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti[] getTabAppalti()
    {
        int size = this._tabAppaltiList.size();
        it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti[] array = new it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti[size];
        for (int index = 0; index < size; index++){
            array[index] = (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti) _tabAppaltiList.get(index);
        }
        
        return array;
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti[] getTabAppalti() 

    /**
     * Method getTabAppaltiCount
     * 
     * 
     * 
     * @return the size of this collection
     */
    public int getTabAppaltiCount()
    {
        return this._tabAppaltiList.size();
    } //-- int getTabAppaltiCount() 

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
    public void removeAllTabAppalti()
    {
        this._tabAppaltiList.clear();
    } //-- void removeAllTabAppalti() 

    /**
     * Method removeTabAppalti
     * 
     * 
     * 
     * @param vTabAppalti
     * @return true if the object was removed from the collection.
     */
    public boolean removeTabAppalti(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti vTabAppalti)
    {
        boolean removed = _tabAppaltiList.remove(vTabAppalti);
        return removed;
    } //-- boolean removeTabAppalti(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti) 

    /**
     * Method removeTabAppaltiAt
     * 
     * 
     * 
     * @param index
     * @return the element removed from the collection
     */
    public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti removeTabAppaltiAt(int index)
    {
        Object obj = this._tabAppaltiList.remove(index);
        return (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti) obj;
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti removeTabAppaltiAt(int) 

    /**
     * 
     * 
     * @param index
     * @param vTabAppalti
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setTabAppalti(int index, it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti vTabAppalti)
        throws java.lang.IndexOutOfBoundsException
    {
        // check bounds for index
        if (index < 0 || index >= this._tabAppaltiList.size()) {
            throw new IndexOutOfBoundsException("setTabAppalti: Index value '" + index + "' not in range [0.." + (this._tabAppaltiList.size() - 1) + "]");
        }
        
        this._tabAppaltiList.set(index, vTabAppalti);
    } //-- void setTabAppalti(int, it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti) 

    /**
     * 
     * 
     * @param vTabAppaltiArray
     */
    public void setTabAppalti(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti[] vTabAppaltiArray)
    {
        //-- copy array
        _tabAppaltiList.clear();
        
        for (int i = 0; i < vTabAppaltiArray.length; i++) {
                this._tabAppaltiList.add(vTabAppaltiArray[i]);
        }
    } //-- void setTabAppalti(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti) 

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
     * it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti
     */
    public static it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti) Unmarshaller.unmarshal(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti.class, reader);
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti unmarshal(java.io.Reader) 

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
