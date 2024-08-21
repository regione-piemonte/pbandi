/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali;

////{PROTECTED REGION ID(R-2102660022) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DelegatoDTO.
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
public class DelegatoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String capResidenza = null;

	/**
	 * @generated
	 */
	public void setCapResidenza(java.lang.String val) {
		capResidenza = val;
	}

	////{PROTECTED REGION ID(R-389644445) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo capResidenza. 
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
	public java.lang.String getCapResidenza() {
		return capResidenza;
	}

	/**
	 * @generated
	 */
	private java.lang.String codiceFiscaleSoggetto = null;

	/**
	 * @generated
	 */
	public void setCodiceFiscaleSoggetto(java.lang.String val) {
		codiceFiscaleSoggetto = val;
	}

	////{PROTECTED REGION ID(R-733144944) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceFiscaleSoggetto. 
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
	public java.lang.String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}

	/**
	 * @generated
	 */
	private java.lang.String cognome = null;

	/**
	 * @generated
	 */
	public void setCognome(java.lang.String val) {
		cognome = val;
	}

	////{PROTECTED REGION ID(R354582410) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cognome. 
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
	public java.lang.String getCognome() {
		return cognome;
	}

	/**
	 * @generated
	 */
	private java.lang.String comuneResidenza = null;

	/**
	 * @generated
	 */
	public void setComuneResidenza(java.lang.String val) {
		comuneResidenza = val;
	}

	////{PROTECTED REGION ID(R-1075742538) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo comuneResidenza. 
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
	public java.lang.String getComuneResidenza() {
		return comuneResidenza;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataNascita = null;

	/**
	 * @generated
	 */
	public void setDataNascita(java.util.Date val) {
		dataNascita = val;
	}

	////{PROTECTED REGION ID(R1310367967) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataNascita. 
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
	public java.util.Date getDataNascita() {
		return dataNascita;
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

	////{PROTECTED REGION ID(R240355207) ENABLED START////}
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
	private java.lang.Long idComuneEsteroResidenza = null;

	/**
	 * @generated
	 */
	public void setIdComuneEsteroResidenza(java.lang.Long val) {
		idComuneEsteroResidenza = val;
	}

	////{PROTECTED REGION ID(R-1038729761) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idComuneEsteroResidenza. 
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
	public java.lang.Long getIdComuneEsteroResidenza() {
		return idComuneEsteroResidenza;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idComuneItalianoNascita = null;

	/**
	 * @generated
	 */
	public void setIdComuneItalianoNascita(java.lang.Long val) {
		idComuneItalianoNascita = val;
	}

	////{PROTECTED REGION ID(R-1985858092) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idComuneItalianoNascita. 
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
	public java.lang.Long getIdComuneItalianoNascita() {
		return idComuneItalianoNascita;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idComuneResidenza = null;

	/**
	 * @generated
	 */
	public void setIdComuneResidenza(java.lang.Long val) {
		idComuneResidenza = val;
	}

	////{PROTECTED REGION ID(R-1343675237) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idComuneResidenza. 
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
	public java.lang.Long getIdComuneResidenza() {
		return idComuneResidenza;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idIndirizzoPersonaFisica = null;

	/**
	 * @generated
	 */
	public void setIdIndirizzoPersonaFisica(java.lang.Long val) {
		idIndirizzoPersonaFisica = val;
	}

	////{PROTECTED REGION ID(R383323578) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idIndirizzoPersonaFisica. 
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
	public java.lang.Long getIdIndirizzoPersonaFisica() {
		return idIndirizzoPersonaFisica;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idNazioneResidenza = null;

	/**
	 * @generated
	 */
	public void setIdNazioneResidenza(java.lang.Long val) {
		idNazioneResidenza = val;
	}

	////{PROTECTED REGION ID(R769518636) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idNazioneResidenza. 
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
	public java.lang.Long getIdNazioneResidenza() {
		return idNazioneResidenza;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idPersonaFisica = null;

	/**
	 * @generated
	 */
	public void setIdPersonaFisica(java.lang.Long val) {
		idPersonaFisica = val;
	}

	////{PROTECTED REGION ID(R-718188450) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idPersonaFisica. 
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
	public java.lang.Long getIdPersonaFisica() {
		return idPersonaFisica;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idProvinciaResidenza = null;

	/**
	 * @generated
	 */
	public void setIdProvinciaResidenza(java.lang.Long val) {
		idProvinciaResidenza = val;
	}

	////{PROTECTED REGION ID(R-1986865053) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idProvinciaResidenza. 
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
	public java.lang.Long getIdProvinciaResidenza() {
		return idProvinciaResidenza;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idSoggetto = null;

	/**
	 * @generated
	 */
	public void setIdSoggetto(java.lang.Long val) {
		idSoggetto = val;
	}

	////{PROTECTED REGION ID(R-575346773) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSoggetto. 
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
	public java.lang.Long getIdSoggetto() {
		return idSoggetto;
	}

	/**
	 * @generated
	 */
	private java.lang.String indirizzoResidenza = null;

	/**
	 * @generated
	 */
	public void setIndirizzoResidenza(java.lang.String val) {
		indirizzoResidenza = val;
	}

	////{PROTECTED REGION ID(R1362073031) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo indirizzoResidenza. 
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
	public java.lang.String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}

	/**
	 * @generated
	 */
	private java.lang.String luogoNascita = null;

	/**
	 * @generated
	 */
	public void setLuogoNascita(java.lang.String val) {
		luogoNascita = val;
	}

	////{PROTECTED REGION ID(R1642578031) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo luogoNascita. 
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
	public java.lang.String getLuogoNascita() {
		return luogoNascita;
	}

	/**
	 * @generated
	 */
	private java.lang.String nome = null;

	/**
	 * @generated
	 */
	public void setNome(java.lang.String val) {
		nome = val;
	}

	////{PROTECTED REGION ID(R-1478988189) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo nome. 
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
	public java.lang.String getNome() {
		return nome;
	}

	/**
	 * @generated
	 */
	private java.lang.String provinciaResidenza = null;

	/**
	 * @generated
	 */
	public void setProvinciaResidenza(java.lang.String val) {
		provinciaResidenza = val;
	}

	////{PROTECTED REGION ID(R-53065112) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo provinciaResidenza. 
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
	public java.lang.String getProvinciaResidenza() {
		return provinciaResidenza;
	}

	/*PROTECTED REGION ID(R-1331940927) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
