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
 * Class CheckListDataModel.
 * 
 * @version $Revision$ $Date$
 */
public class CheckListDataModel implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _assePrioritario
     */
    private java.lang.String _assePrioritario;

    /**
     * Field _lineaIntervento
     */
    private java.lang.String _lineaIntervento;

    /**
     * Field _bandoRiferimento
     */
    private java.lang.String _bandoRiferimento;

    /**
     * Field _cup
     */
    private java.lang.String _cup;

    /**
     * Field _codiceProgetto
     */
    private java.lang.String _codiceProgetto;

    /**
     * Field _titoloProgetto
     */
    private java.lang.String _titoloProgetto;

    /**
     * Field _denominazioneBeneficiario
     */
    private java.lang.String _denominazioneBeneficiario;

    /**
     * Field _costoTotaleAmmesso
     */
    private java.lang.String _costoTotaleAmmesso;

    /**
     * Field _contributoPubblicoConcesso
     */
    private java.lang.String _contributoPubblicoConcesso;

    /**
     * Field _totaleSpesaRendicontataOperazione
     */
    private java.lang.String _totaleSpesaRendicontataOperazione;

    /**
     * Field _attoConcessioneContributo
     */
    private java.lang.String _attoConcessioneContributo;

    /**
     * Field _dataControllo
     */
    private java.lang.String _dataControllo;

    /**
     * Field _strutturaResponsabile
     */
    private java.lang.String _strutturaResponsabile;

    /**
     * Field _referenteBeneficiario
     */
    private java.lang.String _referenteBeneficiario;

    /**
     * Field _ammontareTotaleSpesaRendicontata
     */
    private java.lang.String _ammontareTotaleSpesaRendicontata;

    /**
     * Field _ammontareTotaleSpesaValidata
     */
    private java.lang.String _ammontareTotaleSpesaValidata;

    /**
     * Field _titoloBando
     */
    private java.lang.String _titoloBando;

    /**
     * Field _firmaResponsabile
     */
    private java.lang.String _firmaResponsabile;

    /**
     * Field _luogoControllo
     */
    private java.lang.String _luogoControllo;

    /**
     * Field _descrizioneProgetto
     */
    private java.lang.String _descrizioneProgetto;

    /**
     * Field _vociDiSpesa
     */
    private it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.VociDiSpesa _vociDiSpesa;

    /**
     * Field _sezioneAppaltiList
     */
    private java.util.Vector _sezioneAppaltiList;

    /**
     * Field _controlli
     */
    private it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controlli _controlli;

    /**
     * Field _irregolarita
     */
    private java.lang.String _irregolarita;

    /**
     * Field _campoIrregolarita
     */
    private java.lang.String _campoIrregolarita;


      //----------------/
     //- Constructors -/
    //----------------/

    public CheckListDataModel() 
     {
        super();
        this._sezioneAppaltiList = new java.util.Vector();
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.CheckListDataModel()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vSezioneAppalti
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addSezioneAppalti(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti vSezioneAppalti)
        throws java.lang.IndexOutOfBoundsException
    {
        this._sezioneAppaltiList.addElement(vSezioneAppalti);
    } //-- void addSezioneAppalti(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti) 

    /**
     * 
     * 
     * @param index
     * @param vSezioneAppalti
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addSezioneAppalti(int index, it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti vSezioneAppalti)
        throws java.lang.IndexOutOfBoundsException
    {
        this._sezioneAppaltiList.add(index, vSezioneAppalti);
    } //-- void addSezioneAppalti(int, it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti) 

    /**
     * Method enumerateSezioneAppalti
     * 
     * 
     * 
     * @return an Enumeration over all
     * it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti
     * elements
     */
    public java.util.Enumeration enumerateSezioneAppalti()
    {
        return this._sezioneAppaltiList.elements();
    } //-- java.util.Enumeration enumerateSezioneAppalti() 

    /**
     * Returns the value of field
     * 'ammontareTotaleSpesaRendicontata'.
     * 
     * @return the value of field 'AmmontareTotaleSpesaRendicontata'
     */
    public java.lang.String getAmmontareTotaleSpesaRendicontata()
    {
        return this._ammontareTotaleSpesaRendicontata;
    } //-- java.lang.String getAmmontareTotaleSpesaRendicontata() 

    /**
     * Returns the value of field 'ammontareTotaleSpesaValidata'.
     * 
     * @return the value of field 'AmmontareTotaleSpesaValidata'.
     */
    public java.lang.String getAmmontareTotaleSpesaValidata()
    {
        return this._ammontareTotaleSpesaValidata;
    } //-- java.lang.String getAmmontareTotaleSpesaValidata() 

    /**
     * Returns the value of field 'assePrioritario'.
     * 
     * @return the value of field 'AssePrioritario'.
     */
    public java.lang.String getAssePrioritario()
    {
        return this._assePrioritario;
    } //-- java.lang.String getAssePrioritario() 

    /**
     * Returns the value of field 'attoConcessioneContributo'.
     * 
     * @return the value of field 'AttoConcessioneContributo'.
     */
    public java.lang.String getAttoConcessioneContributo()
    {
        return this._attoConcessioneContributo;
    } //-- java.lang.String getAttoConcessioneContributo() 

    /**
     * Returns the value of field 'bandoRiferimento'.
     * 
     * @return the value of field 'BandoRiferimento'.
     */
    public java.lang.String getBandoRiferimento()
    {
        return this._bandoRiferimento;
    } //-- java.lang.String getBandoRiferimento() 

    /**
     * Returns the value of field 'campoIrregolarita'.
     * 
     * @return the value of field 'CampoIrregolarita'.
     */
    public java.lang.String getCampoIrregolarita()
    {
        return this._campoIrregolarita;
    } //-- java.lang.String getCampoIrregolarita() 

    /**
     * Returns the value of field 'codiceProgetto'.
     * 
     * @return the value of field 'CodiceProgetto'.
     */
    public java.lang.String getCodiceProgetto()
    {
        return this._codiceProgetto;
    } //-- java.lang.String getCodiceProgetto() 

    /**
     * Returns the value of field 'contributoPubblicoConcesso'.
     * 
     * @return the value of field 'ContributoPubblicoConcesso'.
     */
    public java.lang.String getContributoPubblicoConcesso()
    {
        return this._contributoPubblicoConcesso;
    } //-- java.lang.String getContributoPubblicoConcesso() 

    /**
     * Returns the value of field 'controlli'.
     * 
     * @return the value of field 'Controlli'.
     */
    public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controlli getControlli()
    {
        return this._controlli;
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controlli getControlli() 

    /**
     * Returns the value of field 'costoTotaleAmmesso'.
     * 
     * @return the value of field 'CostoTotaleAmmesso'.
     */
    public java.lang.String getCostoTotaleAmmesso()
    {
        return this._costoTotaleAmmesso;
    } //-- java.lang.String getCostoTotaleAmmesso() 

    /**
     * Returns the value of field 'cup'.
     * 
     * @return the value of field 'Cup'.
     */
    public java.lang.String getCup()
    {
        return this._cup;
    } //-- java.lang.String getCup() 

    /**
     * Returns the value of field 'dataControllo'.
     * 
     * @return the value of field 'DataControllo'.
     */
    public java.lang.String getDataControllo()
    {
        return this._dataControllo;
    } //-- java.lang.String getDataControllo() 

    /**
     * Returns the value of field 'denominazioneBeneficiario'.
     * 
     * @return the value of field 'DenominazioneBeneficiario'.
     */
    public java.lang.String getDenominazioneBeneficiario()
    {
        return this._denominazioneBeneficiario;
    } //-- java.lang.String getDenominazioneBeneficiario() 

    /**
     * Returns the value of field 'descrizioneProgetto'.
     * 
     * @return the value of field 'DescrizioneProgetto'.
     */
    public java.lang.String getDescrizioneProgetto()
    {
        return this._descrizioneProgetto;
    } //-- java.lang.String getDescrizioneProgetto() 

    /**
     * Returns the value of field 'firmaResponsabile'.
     * 
     * @return the value of field 'FirmaResponsabile'.
     */
    public java.lang.String getFirmaResponsabile()
    {
        return this._firmaResponsabile;
    } //-- java.lang.String getFirmaResponsabile() 

    /**
     * Returns the value of field 'irregolarita'.
     * 
     * @return the value of field 'Irregolarita'.
     */
    public java.lang.String getIrregolarita()
    {
        return this._irregolarita;
    } //-- java.lang.String getIrregolarita() 

    /**
     * Returns the value of field 'lineaIntervento'.
     * 
     * @return the value of field 'LineaIntervento'.
     */
    public java.lang.String getLineaIntervento()
    {
        return this._lineaIntervento;
    } //-- java.lang.String getLineaIntervento() 

    /**
     * Returns the value of field 'luogoControllo'.
     * 
     * @return the value of field 'LuogoControllo'.
     */
    public java.lang.String getLuogoControllo()
    {
        return this._luogoControllo;
    } //-- java.lang.String getLuogoControllo() 

    /**
     * Returns the value of field 'referenteBeneficiario'.
     * 
     * @return the value of field 'ReferenteBeneficiario'.
     */
    public java.lang.String getReferenteBeneficiario()
    {
        return this._referenteBeneficiario;
    } //-- java.lang.String getReferenteBeneficiario() 

    /**
     * Method getSezioneAppalti
     * 
     * 
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti
     * at the given index
     */
    public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti getSezioneAppalti(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        // check bounds for index
        if (index < 0 || index >= this._sezioneAppaltiList.size()) {
            throw new IndexOutOfBoundsException("getSezioneAppalti: Index value '" + index + "' not in range [0.." + (this._sezioneAppaltiList.size() - 1) + "]");
        }
        
        return (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti) _sezioneAppaltiList.get(index);
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti getSezioneAppalti(int) 

    /**
     * Method getSezioneAppalti
     * 
     * 
     * 
     * @return this collection as an Array
     */
    public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti[] getSezioneAppalti()
    {
        int size = this._sezioneAppaltiList.size();
        it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti[] array = new it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti[size];
        for (int index = 0; index < size; index++){
            array[index] = (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti) _sezioneAppaltiList.get(index);
        }
        
        return array;
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti[] getSezioneAppalti() 

    /**
     * Method getSezioneAppaltiCount
     * 
     * 
     * 
     * @return the size of this collection
     */
    public int getSezioneAppaltiCount()
    {
        return this._sezioneAppaltiList.size();
    } //-- int getSezioneAppaltiCount() 

    /**
     * Returns the value of field 'strutturaResponsabile'.
     * 
     * @return the value of field 'StrutturaResponsabile'.
     */
    public java.lang.String getStrutturaResponsabile()
    {
        return this._strutturaResponsabile;
    } //-- java.lang.String getStrutturaResponsabile() 

    /**
     * Returns the value of field 'titoloBando'.
     * 
     * @return the value of field 'TitoloBando'.
     */
    public java.lang.String getTitoloBando()
    {
        return this._titoloBando;
    } //-- java.lang.String getTitoloBando() 

    /**
     * Returns the value of field 'titoloProgetto'.
     * 
     * @return the value of field 'TitoloProgetto'.
     */
    public java.lang.String getTitoloProgetto()
    {
        return this._titoloProgetto;
    } //-- java.lang.String getTitoloProgetto() 

    /**
     * Returns the value of field
     * 'totaleSpesaRendicontataOperazione'.
     * 
     * @return the value of field
     * 'TotaleSpesaRendicontataOperazione'.
     */
    public java.lang.String getTotaleSpesaRendicontataOperazione()
    {
        return this._totaleSpesaRendicontataOperazione;
    } //-- java.lang.String getTotaleSpesaRendicontataOperazione() 

    /**
     * Returns the value of field 'vociDiSpesa'.
     * 
     * @return the value of field 'VociDiSpesa'.
     */
    public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.VociDiSpesa getVociDiSpesa()
    {
        return this._vociDiSpesa;
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.VociDiSpesa getVociDiSpesa() 

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
    public void removeAllSezioneAppalti()
    {
        this._sezioneAppaltiList.clear();
    } //-- void removeAllSezioneAppalti() 

    /**
     * Method removeSezioneAppalti
     * 
     * 
     * 
     * @param vSezioneAppalti
     * @return true if the object was removed from the collection.
     */
    public boolean removeSezioneAppalti(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti vSezioneAppalti)
    {
        boolean removed = _sezioneAppaltiList.remove(vSezioneAppalti);
        return removed;
    } //-- boolean removeSezioneAppalti(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti) 

    /**
     * Method removeSezioneAppaltiAt
     * 
     * 
     * 
     * @param index
     * @return the element removed from the collection
     */
    public it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti removeSezioneAppaltiAt(int index)
    {
        Object obj = this._sezioneAppaltiList.remove(index);
        return (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti) obj;
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti removeSezioneAppaltiAt(int) 

    /**
     * Sets the value of field 'ammontareTotaleSpesaRendicontata'.
     * 
     * @param ammontareTotaleSpesaRendicontata the value of field
     * 'ammontareTotaleSpesaRendicontata'.
     */
    public void setAmmontareTotaleSpesaRendicontata(java.lang.String ammontareTotaleSpesaRendicontata)
    {
        this._ammontareTotaleSpesaRendicontata = ammontareTotaleSpesaRendicontata;
    } //-- void setAmmontareTotaleSpesaRendicontata(java.lang.String) 

    /**
     * Sets the value of field 'ammontareTotaleSpesaValidata'.
     * 
     * @param ammontareTotaleSpesaValidata the value of field
     * 'ammontareTotaleSpesaValidata'.
     */
    public void setAmmontareTotaleSpesaValidata(java.lang.String ammontareTotaleSpesaValidata)
    {
        this._ammontareTotaleSpesaValidata = ammontareTotaleSpesaValidata;
    } //-- void setAmmontareTotaleSpesaValidata(java.lang.String) 

    /**
     * Sets the value of field 'assePrioritario'.
     * 
     * @param assePrioritario the value of field 'assePrioritario'.
     */
    public void setAssePrioritario(java.lang.String assePrioritario)
    {
        this._assePrioritario = assePrioritario;
    } //-- void setAssePrioritario(java.lang.String) 

    /**
     * Sets the value of field 'attoConcessioneContributo'.
     * 
     * @param attoConcessioneContributo the value of field
     * 'attoConcessioneContributo'.
     */
    public void setAttoConcessioneContributo(java.lang.String attoConcessioneContributo)
    {
        this._attoConcessioneContributo = attoConcessioneContributo;
    } //-- void setAttoConcessioneContributo(java.lang.String) 

    /**
     * Sets the value of field 'bandoRiferimento'.
     * 
     * @param bandoRiferimento the value of field 'bandoRiferimento'
     */
    public void setBandoRiferimento(java.lang.String bandoRiferimento)
    {
        this._bandoRiferimento = bandoRiferimento;
    } //-- void setBandoRiferimento(java.lang.String) 

    /**
     * Sets the value of field 'campoIrregolarita'.
     * 
     * @param campoIrregolarita the value of field
     * 'campoIrregolarita'.
     */
    public void setCampoIrregolarita(java.lang.String campoIrregolarita)
    {
        this._campoIrregolarita = campoIrregolarita;
    } //-- void setCampoIrregolarita(java.lang.String) 

    /**
     * Sets the value of field 'codiceProgetto'.
     * 
     * @param codiceProgetto the value of field 'codiceProgetto'.
     */
    public void setCodiceProgetto(java.lang.String codiceProgetto)
    {
        this._codiceProgetto = codiceProgetto;
    } //-- void setCodiceProgetto(java.lang.String) 

    /**
     * Sets the value of field 'contributoPubblicoConcesso'.
     * 
     * @param contributoPubblicoConcesso the value of field
     * 'contributoPubblicoConcesso'.
     */
    public void setContributoPubblicoConcesso(java.lang.String contributoPubblicoConcesso)
    {
        this._contributoPubblicoConcesso = contributoPubblicoConcesso;
    } //-- void setContributoPubblicoConcesso(java.lang.String) 

    /**
     * Sets the value of field 'controlli'.
     * 
     * @param controlli the value of field 'controlli'.
     */
    public void setControlli(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controlli controlli)
    {
        this._controlli = controlli;
    } //-- void setControlli(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.Controlli) 

    /**
     * Sets the value of field 'costoTotaleAmmesso'.
     * 
     * @param costoTotaleAmmesso the value of field
     * 'costoTotaleAmmesso'.
     */
    public void setCostoTotaleAmmesso(java.lang.String costoTotaleAmmesso)
    {
        this._costoTotaleAmmesso = costoTotaleAmmesso;
    } //-- void setCostoTotaleAmmesso(java.lang.String) 

    /**
     * Sets the value of field 'cup'.
     * 
     * @param cup the value of field 'cup'.
     */
    public void setCup(java.lang.String cup)
    {
        this._cup = cup;
    } //-- void setCup(java.lang.String) 

    /**
     * Sets the value of field 'dataControllo'.
     * 
     * @param dataControllo the value of field 'dataControllo'.
     */
    public void setDataControllo(java.lang.String dataControllo)
    {
        this._dataControllo = dataControllo;
    } //-- void setDataControllo(java.lang.String) 

    /**
     * Sets the value of field 'denominazioneBeneficiario'.
     * 
     * @param denominazioneBeneficiario the value of field
     * 'denominazioneBeneficiario'.
     */
    public void setDenominazioneBeneficiario(java.lang.String denominazioneBeneficiario)
    {
        this._denominazioneBeneficiario = denominazioneBeneficiario;
    } //-- void setDenominazioneBeneficiario(java.lang.String) 

    /**
     * Sets the value of field 'descrizioneProgetto'.
     * 
     * @param descrizioneProgetto the value of field
     * 'descrizioneProgetto'.
     */
    public void setDescrizioneProgetto(java.lang.String descrizioneProgetto)
    {
        this._descrizioneProgetto = descrizioneProgetto;
    } //-- void setDescrizioneProgetto(java.lang.String) 

    /**
     * Sets the value of field 'firmaResponsabile'.
     * 
     * @param firmaResponsabile the value of field
     * 'firmaResponsabile'.
     */
    public void setFirmaResponsabile(java.lang.String firmaResponsabile)
    {
        this._firmaResponsabile = firmaResponsabile;
    } //-- void setFirmaResponsabile(java.lang.String) 

    /**
     * Sets the value of field 'irregolarita'.
     * 
     * @param irregolarita the value of field 'irregolarita'.
     */
    public void setIrregolarita(java.lang.String irregolarita)
    {
        this._irregolarita = irregolarita;
    } //-- void setIrregolarita(java.lang.String) 

    /**
     * Sets the value of field 'lineaIntervento'.
     * 
     * @param lineaIntervento the value of field 'lineaIntervento'.
     */
    public void setLineaIntervento(java.lang.String lineaIntervento)
    {
        this._lineaIntervento = lineaIntervento;
    } //-- void setLineaIntervento(java.lang.String) 

    /**
     * Sets the value of field 'luogoControllo'.
     * 
     * @param luogoControllo the value of field 'luogoControllo'.
     */
    public void setLuogoControllo(java.lang.String luogoControllo)
    {
        this._luogoControllo = luogoControllo;
    } //-- void setLuogoControllo(java.lang.String) 

    /**
     * Sets the value of field 'referenteBeneficiario'.
     * 
     * @param referenteBeneficiario the value of field
     * 'referenteBeneficiario'.
     */
    public void setReferenteBeneficiario(java.lang.String referenteBeneficiario)
    {
        this._referenteBeneficiario = referenteBeneficiario;
    } //-- void setReferenteBeneficiario(java.lang.String) 

    /**
     * 
     * 
     * @param index
     * @param vSezioneAppalti
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setSezioneAppalti(int index, it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti vSezioneAppalti)
        throws java.lang.IndexOutOfBoundsException
    {
        // check bounds for index
        if (index < 0 || index >= this._sezioneAppaltiList.size()) {
            throw new IndexOutOfBoundsException("setSezioneAppalti: Index value '" + index + "' not in range [0.." + (this._sezioneAppaltiList.size() - 1) + "]");
        }
        
        this._sezioneAppaltiList.set(index, vSezioneAppalti);
    } //-- void setSezioneAppalti(int, it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti) 

    /**
     * 
     * 
     * @param vSezioneAppaltiArray
     */
    public void setSezioneAppalti(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti[] vSezioneAppaltiArray)
    {
        //-- copy array
        _sezioneAppaltiList.clear();
        
        for (int i = 0; i < vSezioneAppaltiArray.length; i++) {
                this._sezioneAppaltiList.add(vSezioneAppaltiArray[i]);
        }
    } //-- void setSezioneAppalti(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti) 

    /**
     * Sets the value of field 'strutturaResponsabile'.
     * 
     * @param strutturaResponsabile the value of field
     * 'strutturaResponsabile'.
     */
    public void setStrutturaResponsabile(java.lang.String strutturaResponsabile)
    {
        this._strutturaResponsabile = strutturaResponsabile;
    } //-- void setStrutturaResponsabile(java.lang.String) 

    /**
     * Sets the value of field 'titoloBando'.
     * 
     * @param titoloBando the value of field 'titoloBando'.
     */
    public void setTitoloBando(java.lang.String titoloBando)
    {
        this._titoloBando = titoloBando;
    } //-- void setTitoloBando(java.lang.String) 

    /**
     * Sets the value of field 'titoloProgetto'.
     * 
     * @param titoloProgetto the value of field 'titoloProgetto'.
     */
    public void setTitoloProgetto(java.lang.String titoloProgetto)
    {
        this._titoloProgetto = titoloProgetto;
    } //-- void setTitoloProgetto(java.lang.String) 

    /**
     * Sets the value of field 'totaleSpesaRendicontataOperazione'.
     * 
     * @param totaleSpesaRendicontataOperazione the value of field
     * 'totaleSpesaRendicontataOperazione'.
     */
    public void setTotaleSpesaRendicontataOperazione(java.lang.String totaleSpesaRendicontataOperazione)
    {
        this._totaleSpesaRendicontataOperazione = totaleSpesaRendicontataOperazione;
    } //-- void setTotaleSpesaRendicontataOperazione(java.lang.String) 

    /**
     * Sets the value of field 'vociDiSpesa'.
     * 
     * @param vociDiSpesa the value of field 'vociDiSpesa'.
     */
    public void setVociDiSpesa(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.VociDiSpesa vociDiSpesa)
    {
        this._vociDiSpesa = vociDiSpesa;
    } //-- void setVociDiSpesa(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.VociDiSpesa) 

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
     * it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.CheckListDataModel
     */
    public static it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.CheckListDataModel unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.CheckListDataModel) Unmarshaller.unmarshal(it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.CheckListDataModel.class, reader);
    } //-- it.csi.pbandi.pbweb.pbandisrv.dto.checklist.gen.checklistdatamodel.CheckListDataModel unmarshal(java.io.Reader) 

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
