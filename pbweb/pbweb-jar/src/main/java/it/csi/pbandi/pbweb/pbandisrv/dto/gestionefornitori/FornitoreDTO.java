/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

////{PROTECTED REGION ID(R617561819) ENABLED START////}
/**
 * Inserire qui la documentazione della classe FornitoreDTO.
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
public class FornitoreDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String codiceFiscaleFornitore = null;

	/**
	 * @generated
	 */
	public void setCodiceFiscaleFornitore(java.lang.String val) {
		codiceFiscaleFornitore = val;
	}

	////{PROTECTED REGION ID(R-231558567) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceFiscaleFornitore. 
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
	public java.lang.String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.String[] codQualificheNotIn = null;

	/**
	 * @generated
	 */
	public void setCodQualificheNotIn(java.lang.String[] val) {
		codQualificheNotIn = val;
	}

	////{PROTECTED REGION ID(R1468130904) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codQualificheNotIn. 
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
	public java.lang.String[] getCodQualificheNotIn() {
		return codQualificheNotIn;
	}

	/**
	 * @generated
	 */
	private java.lang.String[] codQualificheIn = null;

	/**
	 * @generated
	 */
	public void setCodQualificheIn(java.lang.String[] val) {
		codQualificheIn = val;
	}

	////{PROTECTED REGION ID(R-379694555) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codQualificheIn. 
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
	public java.lang.String[] getCodQualificheIn() {
		return codQualificheIn;
	}

	/**
	 * @generated
	 */
	private java.lang.String cognomeFornitore = null;

	/**
	 * @generated
	 */
	public void setCognomeFornitore(java.lang.String val) {
		cognomeFornitore = val;
	}

	////{PROTECTED REGION ID(R1542174809) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cognomeFornitore. 
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
	public java.lang.String getCognomeFornitore() {
		return cognomeFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.Double costoOrario = null;

	/**
	 * @generated
	 */
	public void setCostoOrario(java.lang.Double val) {
		costoOrario = val;
	}

	////{PROTECTED REGION ID(R1492954273) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo costoOrario. 
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
	public java.lang.Double getCostoOrario() {
		return costoOrario;
	}

	/**
	 * @generated
	 */
	private java.lang.Double costoRisorsa = null;

	/**
	 * @generated
	 */
	public void setCostoRisorsa(java.lang.Double val) {
		costoRisorsa = val;
	}

	////{PROTECTED REGION ID(R1458333766) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo costoRisorsa. 
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
	public java.lang.Double getCostoRisorsa() {
		return costoRisorsa;
	}

	/**
	 * @generated
	 */
	private java.lang.String denominazioneFornitore = null;

	/**
	 * @generated
	 */
	public void setDenominazioneFornitore(java.lang.String val) {
		denominazioneFornitore = val;
	}

	////{PROTECTED REGION ID(R-1828961977) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo denominazioneFornitore. 
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
	public java.lang.String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.String descQualifica = null;

	/**
	 * @generated
	 */
	public void setDescQualifica(java.lang.String val) {
		descQualifica = val;
	}

	////{PROTECTED REGION ID(R261677519) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descQualifica. 
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
	public java.lang.String getDescQualifica() {
		return descQualifica;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveTipoSoggetto = null;

	/**
	 * @generated
	 */
	public void setDescBreveTipoSoggetto(java.lang.String val) {
		descBreveTipoSoggetto = val;
	}

	////{PROTECTED REGION ID(R1850616850) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveTipoSoggetto. 
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
	public java.lang.String getDescBreveTipoSoggetto() {
		return descBreveTipoSoggetto;
	}

	/**
	 * @generated
	 */
	private java.lang.String descTipoSoggetto = null;

	/**
	 * @generated
	 */
	public void setDescTipoSoggetto(java.lang.String val) {
		descTipoSoggetto = val;
	}

	////{PROTECTED REGION ID(R622169862) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipoSoggetto. 
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
	public java.lang.String getDescTipoSoggetto() {
		return descTipoSoggetto;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtFineValidita = null;

	/**
	 * @generated
	 */
	public void setDtFineValidita(java.util.Date val) {
		dtFineValidita = val;
	}

	////{PROTECTED REGION ID(R-1324162241) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtFineValidita. 
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
	public java.util.Date getDtFineValidita() {
		return dtFineValidita;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean flagHasQualifica = null;

	/**
	 * @generated
	 */
	public void setFlagHasQualifica(java.lang.Boolean val) {
		flagHasQualifica = val;
	}

	////{PROTECTED REGION ID(R-1627355832) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagHasQualifica. 
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
	public java.lang.Boolean getFlagHasQualifica() {
		return flagHasQualifica;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idAttivitaAteco = null;

	/**
	 * @generated
	 */
	public void setIdAttivitaAteco(java.lang.Long val) {
		idAttivitaAteco = val;
	}

	////{PROTECTED REGION ID(R1854200704) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idAttivitaAteco. 
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
	public java.lang.Long getIdAttivitaAteco() {
		return idAttivitaAteco;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idFormaGiuridica = null;

	/**
	 * @generated
	 */
	public void setIdFormaGiuridica(java.lang.Long val) {
		idFormaGiuridica = val;
	}

	////{PROTECTED REGION ID(R-1438603482) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idFormaGiuridica. 
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
	public java.lang.Long getIdFormaGiuridica() {
		return idFormaGiuridica;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idFornitore = null;

	/**
	 * @generated
	 */
	public void setIdFornitore(java.lang.Long val) {
		idFornitore = val;
	}

	////{PROTECTED REGION ID(R317083548) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idFornitore. 
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
	public java.lang.Long getIdFornitore() {
		return idFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idNazione = null;

	/**
	 * @generated
	 */
	public void setIdNazione(java.lang.Long val) {
		idNazione = val;
	}

	////{PROTECTED REGION ID(R-957999890) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idNazione. 
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
	public java.lang.Long getIdNazione() {
		return idNazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idQualifica = null;

	/**
	 * @generated
	 */
	public void setIdQualifica(java.lang.Long val) {
		idQualifica = val;
	}

	////{PROTECTED REGION ID(R1516506053) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idQualifica. 
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
	public java.lang.Long getIdQualifica() {
		return idQualifica;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idSoggettoFornitore = null;

	/**
	 * @generated
	 */
	public void setIdSoggettoFornitore(java.lang.Long val) {
		idSoggettoFornitore = val;
	}

	////{PROTECTED REGION ID(R203341462) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSoggettoFornitore. 
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
	public java.lang.Long getIdSoggettoFornitore() {
		return idSoggettoFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoSoggetto = null;

	/**
	 * @generated
	 */
	public void setIdTipoSoggetto(java.lang.Long val) {
		idTipoSoggetto = val;
	}

	////{PROTECTED REGION ID(R-176318128) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoSoggetto. 
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
	public java.lang.Long getIdTipoSoggetto() {
		return idTipoSoggetto;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean includiFornitoriInvalidi = null;

	/**
	 * @generated
	 */
	public void setIncludiFornitoriInvalidi(java.lang.Boolean val) {
		includiFornitoriInvalidi = val;
	}

	////{PROTECTED REGION ID(R641517111) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo includiFornitoriInvalidi. 
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
	public java.lang.Boolean getIncludiFornitoriInvalidi() {
		return includiFornitoriInvalidi;
	}

	/**
	 * @generated
	 */
	private java.lang.Double monteOre = null;

	/**
	 * @generated
	 */
	public void setMonteOre(java.lang.Double val) {
		monteOre = val;
	}

	////{PROTECTED REGION ID(R1010227968) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo monteOre. 
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
	public java.lang.Double getMonteOre() {
		return monteOre;
	}

	/**
	 * @generated
	 */
	private java.lang.String nomeFornitore = null;

	/**
	 * @generated
	 */
	public void setNomeFornitore(java.lang.String val) {
		nomeFornitore = val;
	}

	////{PROTECTED REGION ID(R-782201314) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo nomeFornitore. 
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
	public java.lang.String getNomeFornitore() {
		return nomeFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.String partitaIvaFornitore = null;

	/**
	 * @generated
	 */
	public void setPartitaIvaFornitore(java.lang.String val) {
		partitaIvaFornitore = val;
	}

	////{PROTECTED REGION ID(R2136564326) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo partitaIvaFornitore. 
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
	public java.lang.String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.String altroCodice = null;

	/**
	 * @generated
	 */
	public void setAltroCodice(java.lang.String val) {
		altroCodice = val;
	}

	////{PROTECTED REGION ID(R37082526) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo altroCodice. 
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
	public java.lang.String getAltroCodice() {
		return altroCodice;
	}

	/**
	 * @generated
	 */
	private java.lang.String codUniIpa = null;

	/**
	 * @generated
	 */
	public void setCodUniIpa(java.lang.String val) {
		codUniIpa = val;
	}

	////{PROTECTED REGION ID(R983412775) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codUniIpa. 
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
	public java.lang.String getCodUniIpa() {
		return codUniIpa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long flagPubblicoPrivato = null;

	/**
	 * @generated
	 */
	public void setFlagPubblicoPrivato(java.lang.Long val) {
		flagPubblicoPrivato = val;
	}

	////{PROTECTED REGION ID(R-2131741512) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagPubblicoPrivato. 
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
	public java.lang.Long getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}

	/*PROTECTED REGION ID(R1175821414) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nFornitoreDTO: ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}
	/*PROTECTED REGION END*/
}
