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
 * Class VociDiSpesa.
 * 
 * @version $Revision$ $Date$
 */
public class VociDiSpesa implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _voceList
     */
    private java.util.Vector _voceList;


      //----------------/
     //- Constructors -/
    //----------------/

    public VociDiSpesa() 
     {
        super();
        this._voceList = new java.util.Vector();
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.VociDiSpesa()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vVoce
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addVoce(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce vVoce)
        throws java.lang.IndexOutOfBoundsException
    {
        this._voceList.addElement(vVoce);
    } //-- void addVoce(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce) 

    /**
     * 
     * 
     * @param index
     * @param vVoce
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addVoce(int index, it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce vVoce)
        throws java.lang.IndexOutOfBoundsException
    {
        this._voceList.add(index, vVoce);
    } //-- void addVoce(int, it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce) 

    /**
     * Method enumerateVoce
     * 
     * 
     * 
     * @return an Enumeration over all
     * it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce
     * elements
     */
    public java.util.Enumeration enumerateVoce()
    {
        return this._voceList.elements();
    } //-- java.util.Enumeration enumerateVoce() 

    /**
     * Method getVoce
     * 
     * 
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce
     * at the given index
     */
    public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce getVoce(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        // check bounds for index
        if (index < 0 || index >= this._voceList.size()) {
            throw new IndexOutOfBoundsException("getVoce: Index value '" + index + "' not in range [0.." + (this._voceList.size() - 1) + "]");
        }
        
        return (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce) _voceList.get(index);
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce getVoce(int) 

    /**
     * Method getVoce
     * 
     * 
     * 
     * @return this collection as an Array
     */
    public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce[] getVoce()
    {
        int size = this._voceList.size();
        it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce[] array = new it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce[size];
        for (int index = 0; index < size; index++){
            array[index] = (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce) _voceList.get(index);
        }
        
        return array;
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce[] getVoce() 

    /**
     * Method getVoceCount
     * 
     * 
     * 
     * @return the size of this collection
     */
    public int getVoceCount()
    {
        return this._voceList.size();
    } //-- int getVoceCount() 

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
    public void removeAllVoce()
    {
        this._voceList.clear();
    } //-- void removeAllVoce() 

    /**
     * Method removeVoce
     * 
     * 
     * 
     * @param vVoce
     * @return true if the object was removed from the collection.
     */
    public boolean removeVoce(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce vVoce)
    {
        boolean removed = _voceList.remove(vVoce);
        return removed;
    } //-- boolean removeVoce(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce) 

    /**
     * Method removeVoceAt
     * 
     * 
     * 
     * @param index
     * @return the element removed from the collection
     */
    public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce removeVoceAt(int index)
    {
        Object obj = this._voceList.remove(index);
        return (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce) obj;
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce removeVoceAt(int) 

    /**
     * 
     * 
     * @param index
     * @param vVoce
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setVoce(int index, it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce vVoce)
        throws java.lang.IndexOutOfBoundsException
    {
        // check bounds for index
        if (index < 0 || index >= this._voceList.size()) {
            throw new IndexOutOfBoundsException("setVoce: Index value '" + index + "' not in range [0.." + (this._voceList.size() - 1) + "]");
        }
        
        this._voceList.set(index, vVoce);
    } //-- void setVoce(int, it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce) 

    /**
     * 
     * 
     * @param vVoceArray
     */
    public void setVoce(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce[] vVoceArray)
    {
        //-- copy array
        _voceList.clear();
        
        for (int i = 0; i < vVoceArray.length; i++) {
                this._voceList.add(vVoceArray[i]);
        }
    } //-- void setVoce(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce) 

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
     * it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.VociDiSpesa
     */
    public static it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.VociDiSpesa unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.VociDiSpesa) Unmarshaller.unmarshal(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.VociDiSpesa.class, reader);
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.VociDiSpesa unmarshal(java.io.Reader) 

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
