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
 * Class Controlli.
 * 
 * @version $Revision$ $Date$
 */
public class Controlli implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _controlloList
     */
    private java.util.Vector _controlloList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Controlli() 
     {
        super();
        this._controlloList = new java.util.Vector();
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controlli()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vControllo
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addControllo(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo vControllo)
        throws java.lang.IndexOutOfBoundsException
    {
        this._controlloList.addElement(vControllo);
    } //-- void addControllo(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo) 

    /**
     * 
     * 
     * @param index
     * @param vControllo
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addControllo(int index, it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo vControllo)
        throws java.lang.IndexOutOfBoundsException
    {
        this._controlloList.add(index, vControllo);
    } //-- void addControllo(int, it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo) 

    /**
     * Method enumerateControllo
     * 
     * 
     * 
     * @return an Enumeration over all
     * it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo
     * elements
     */
    public java.util.Enumeration enumerateControllo()
    {
        return this._controlloList.elements();
    } //-- java.util.Enumeration enumerateControllo() 

    /**
     * Method getControllo
     * 
     * 
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo
     * at the given index
     */
    public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo getControllo(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        // check bounds for index
        if (index < 0 || index >= this._controlloList.size()) {
            throw new IndexOutOfBoundsException("getControllo: Index value '" + index + "' not in range [0.." + (this._controlloList.size() - 1) + "]");
        }
        
        return (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo) _controlloList.get(index);
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo getControllo(int) 

    /**
     * Method getControllo
     * 
     * 
     * 
     * @return this collection as an Array
     */
    public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo[] getControllo()
    {
        int size = this._controlloList.size();
        it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo[] array = new it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo[size];
        for (int index = 0; index < size; index++){
            array[index] = (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo) _controlloList.get(index);
        }
        
        return array;
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo[] getControllo() 

    /**
     * Method getControlloCount
     * 
     * 
     * 
     * @return the size of this collection
     */
    public int getControlloCount()
    {
        return this._controlloList.size();
    } //-- int getControlloCount() 

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
    public void removeAllControllo()
    {
        this._controlloList.clear();
    } //-- void removeAllControllo() 

    /**
     * Method removeControllo
     * 
     * 
     * 
     * @param vControllo
     * @return true if the object was removed from the collection.
     */
    public boolean removeControllo(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo vControllo)
    {
        boolean removed = _controlloList.remove(vControllo);
        return removed;
    } //-- boolean removeControllo(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo) 

    /**
     * Method removeControlloAt
     * 
     * 
     * 
     * @param index
     * @return the element removed from the collection
     */
    public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo removeControlloAt(int index)
    {
        Object obj = this._controlloList.remove(index);
        return (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo) obj;
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo removeControlloAt(int) 

    /**
     * 
     * 
     * @param index
     * @param vControllo
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setControllo(int index, it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo vControllo)
        throws java.lang.IndexOutOfBoundsException
    {
        // check bounds for index
        if (index < 0 || index >= this._controlloList.size()) {
            throw new IndexOutOfBoundsException("setControllo: Index value '" + index + "' not in range [0.." + (this._controlloList.size() - 1) + "]");
        }
        
        this._controlloList.set(index, vControllo);
    } //-- void setControllo(int, it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo) 

    /**
     * 
     * 
     * @param vControlloArray
     */
    public void setControllo(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo[] vControlloArray)
    {
        //-- copy array
        _controlloList.clear();
        
        for (int i = 0; i < vControlloArray.length; i++) {
                this._controlloList.add(vControlloArray[i]);
        }
    } //-- void setControllo(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controllo) 

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
     * it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controlli
     */
    public static it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controlli unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controlli) Unmarshaller.unmarshal(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controlli.class, reader);
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controlli unmarshal(java.io.Reader) 

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
