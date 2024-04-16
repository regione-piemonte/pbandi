/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto;

////{PROTECTED REGION ID(R1229120202) ENABLED START////}
/**
 * Inserire qui la documentazione della classe IndirizziRapprLegaleCspDTO.
 * Consigli:
 * <ul>
 * <li> Descrivere il "concetto" rappresentato dall'entita' (qual'� l'oggetto
 *      del dominio del servizio rappresentato)
 * <li> Se necessario indicare se questo concetto � mantenuto sugli archivi di
 *      una particolare applicazione
 * <li> Se l'oggetto ha un particolare ciclo di vita (stati, es. creato, da approvare, 
 *      approvato, respinto, annullato.....) si pu� decidere di descrivere
 *      la state machine qui o nella documentazione dell'interfaccia del servizio
 *      che manipola quest'oggetto
 * </ul>
 * @generated
 */
////{PROTECTED REGION END////}
public class IndirizziRapprLegaleCspDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;
	
	private String civico = null;
	
	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	/**
	 * @generated
	 */
	private java.lang.String descIndirizzo = null;

	/**
	 * @generated
	 */
	public void setDescIndirizzo(java.lang.String val) {
		descIndirizzo = val;
	}

	////{PROTECTED REGION ID(R876495699) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descIndirizzo. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescIndirizzo() {
		return descIndirizzo;
	}

	/**
	 * @generated
	 */
	private java.lang.String cap = null;

	/**
	 * @generated
	 */
	public void setCap(java.lang.String val) {
		cap = val;
	}

	////{PROTECTED REGION ID(R2123837640) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cap. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getCap() {
		return cap;
	}

	/**
	 * @generated
	 */
	private java.lang.String email = null;

	/**
	 * @generated
	 */
	public void setEmail(java.lang.String val) {
		email = val;
	}

	////{PROTECTED REGION ID(R900699922) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo email. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getEmail() {
		return email;
	}

	/**
	 * @generated
	 */
	private java.lang.String telefono = null;

	/**
	 * @generated
	 */
	public void setTelefono(java.lang.String val) {
		telefono = val;
	}

	////{PROTECTED REGION ID(R-30874690) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo telefono. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getTelefono() {
		return telefono;
	}

	/**
	 * @generated
	 */
	private java.lang.String fax = null;

	/**
	 * @generated
	 */
	public void setFax(java.lang.String val) {
		fax = val;
	}

	////{PROTECTED REGION ID(R2123840531) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo fax. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getFax() {
		return fax;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idNazioneRes = null;

	/**
	 * @generated
	 */
	public void setIdNazioneRes(java.lang.Long val) {
		idNazioneRes = val;
	}

	////{PROTECTED REGION ID(R977867329) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idNazioneRes. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdNazioneRes() {
		return idNazioneRes;
	}

	/**
	 * @generated
	 */
	private java.lang.String descNazioneRes = null;

	/**
	 * @generated
	 */
	public void setDescNazioneRes(java.lang.String val) {
		descNazioneRes = val;
	}

	////{PROTECTED REGION ID(R2046254519) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descNazioneRes. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescNazioneRes() {
		return descNazioneRes;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idRegioneRes = null;

	/**
	 * @generated
	 */
	public void setIdRegioneRes(java.lang.Long val) {
		idRegioneRes = val;
	}

	////{PROTECTED REGION ID(R-1496695244) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idRegioneRes. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdRegioneRes() {
		return idRegioneRes;
	}

	/**
	 * @generated
	 */
	private java.lang.String descRegioneRes = null;

	/**
	 * @generated
	 */
	public void setDescRegioneRes(java.lang.String val) {
		descRegioneRes = val;
	}

	////{PROTECTED REGION ID(R-428308054) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descRegioneRes. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescRegioneRes() {
		return descRegioneRes;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idProvinciaRes = null;

	/**
	 * @generated
	 */
	public void setIdProvinciaRes(java.lang.Long val) {
		idProvinciaRes = val;
	}

	////{PROTECTED REGION ID(R1438880696) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idProvinciaRes. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdProvinciaRes() {
		return idProvinciaRes;
	}

	/**
	 * @generated
	 */
	private java.lang.String descProvinciaRes = null;

	/**
	 * @generated
	 */
	public void setDescProvinciaRes(java.lang.String val) {
		descProvinciaRes = val;
	}

	////{PROTECTED REGION ID(R1661786542) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descProvinciaRes. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescProvinciaRes() {
		return descProvinciaRes;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idComuneRes = null;

	/**
	 * @generated
	 */
	public void setIdComuneRes(java.lang.Long val) {
		idComuneRes = val;
	}

	////{PROTECTED REGION ID(R1251794160) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idComuneRes. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdComuneRes() {
		return idComuneRes;
	}

	/**
	 * @generated
	 */
	private java.lang.String descComuneRes = null;

	/**
	 * @generated
	 */
	public void setDescComuneRes(java.lang.String val) {
		descComuneRes = val;
	}

	////{PROTECTED REGION ID(R316426938) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descComuneRes. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescComuneRes() {
		return descComuneRes;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idNazioneNascita = null;

	/**
	 * @generated
	 */
	public void setIdNazioneNascita(java.lang.Long val) {
		idNazioneNascita = val;
	}

	////{PROTECTED REGION ID(R348541844) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idNazioneNascita. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdNazioneNascita() {
		return idNazioneNascita;
	}

	/**
	 * @generated
	 */
	private java.lang.String descNazioneNascita = null;

	/**
	 * @generated
	 */
	public void setDescNazioneNascita(java.lang.String val) {
		descNazioneNascita = val;
	}

	////{PROTECTED REGION ID(R-187304950) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descNazioneNascita. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescNazioneNascita() {
		return descNazioneNascita;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idRegioneNascita = null;

	/**
	 * @generated
	 */
	public void setIdRegioneNascita(java.lang.Long val) {
		idRegioneNascita = val;
	}

	////{PROTECTED REGION ID(R-1004909049) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idRegioneNascita. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdRegioneNascita() {
		return idRegioneNascita;
	}

	/**
	 * @generated
	 */
	private java.lang.String descRegioneNascita = null;

	/**
	 * @generated
	 */
	public void setDescRegioneNascita(java.lang.String val) {
		descRegioneNascita = val;
	}

	////{PROTECTED REGION ID(R-1540755843) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descRegioneNascita. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescRegioneNascita() {
		return descRegioneNascita;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idProvinciaNascita = null;

	/**
	 * @generated
	 */
	public void setIdProvinciaNascita(java.lang.Long val) {
		idProvinciaNascita = val;
	}

	////{PROTECTED REGION ID(R61161867) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idProvinciaNascita. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdProvinciaNascita() {
		return idProvinciaNascita;
	}

	/**
	 * @generated
	 */
	private java.lang.String descProvinciaNascita = null;

	/**
	 * @generated
	 */
	public void setDescProvinciaNascita(java.lang.String val) {
		descProvinciaNascita = val;
	}

	////{PROTECTED REGION ID(R508468353) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descProvinciaNascita. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescProvinciaNascita() {
		return descProvinciaNascita;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idComuneNascita = null;

	/**
	 * @generated
	 */
	public void setIdComuneNascita(java.lang.Long val) {
		idComuneNascita = val;
	}

	////{PROTECTED REGION ID(R-339267901) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idComuneNascita. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdComuneNascita() {
		return idComuneNascita;
	}

	/**
	 * @generated
	 */
	private java.lang.String descComuneNascita = null;

	/**
	 * @generated
	 */
	public void setDescComuneNascita(java.lang.String val) {
		descComuneNascita = val;
	}

	////{PROTECTED REGION ID(R-2019121267) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descComuneNascita. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescComuneNascita() {
		return descComuneNascita;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idComuneEsteroNascita = null;

	/**
	 * @generated
	 */
	public void setIdComuneEsteroNascita(java.lang.Long val) {
		idComuneEsteroNascita = val;
	}

	////{PROTECTED REGION ID(R-136664825) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idComuneEsteroNascita. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdComuneEsteroNascita() {
		return idComuneEsteroNascita;
	}

	/**
	 * @generated
	 */
	private java.lang.String descComuneEsteroNascita = null;

	/**
	 * @generated
	 */
	public void setDescComuneEsteroNascita(java.lang.String val) {
		descComuneEsteroNascita = val;
	}

	////{PROTECTED REGION ID(R-1712659887) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descComuneEsteroNascita. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescComuneEsteroNascita() {
		return descComuneEsteroNascita;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idComuneEsteroRes = null;

	/**
	 * @generated
	 */
	public void setIdComuneEsteroRes(java.lang.Long val) {
		idComuneEsteroRes = val;
	}

	////{PROTECTED REGION ID(R1727011124) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idComuneEsteroRes. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdComuneEsteroRes() {
		return idComuneEsteroRes;
	}

	/**
	 * @generated
	 */
	private java.lang.String descComuneEsteroRes = null;

	/**
	 * @generated
	 */
	public void setDescComuneEsteroRes(java.lang.String val) {
		descComuneEsteroRes = val;
	}

	////{PROTECTED REGION ID(R-1999337602) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descComuneEsteroRes. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescComuneEsteroRes() {
		return descComuneEsteroRes;
	}

	/*PROTECTED REGION ID(R-1254504127) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
