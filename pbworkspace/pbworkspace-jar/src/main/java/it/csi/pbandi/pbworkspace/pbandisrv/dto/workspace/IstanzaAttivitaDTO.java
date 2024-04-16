/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace;

////{PROTECTED REGION ID(R632048051) ENABLED START////}
/**
 * Inserire qui la documentazione della classe IstanzaAttivitaDTO.
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
public class IstanzaAttivitaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String codiceVisualizzato = null;

	/**
	 * @generated
	 */
	public void setCodiceVisualizzato(java.lang.String val) {
		codiceVisualizzato = val;
	}

	////{PROTECTED REGION ID(R-293648263) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceVisualizzato. 
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
	public java.lang.String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}

	/**
	 * @generated
	 */
	private java.lang.String denominazioneLock = null;

	/**
	 * @generated
	 */
	public void setDenominazioneLock(java.lang.String val) {
		denominazioneLock = val;
	}

	////{PROTECTED REGION ID(R-1264771298) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo denominazioneLock. 
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
	public java.lang.String getDenominazioneLock() {
		return denominazioneLock;
	}

	/**
	 * @generated
	 */
	private java.lang.String descrAttivita = null;

	/**
	 * @generated
	 */
	public void setDescrAttivita(java.lang.String val) {
		descrAttivita = val;
	}

	////{PROTECTED REGION ID(R1488444054) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descrAttivita. 
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
	public java.lang.String getDescrAttivita() {
		return descrAttivita;
	}

	/**
	 * @generated
	 */
	private java.lang.String descrDefinizioneProcesso = null;

	/**
	 * @generated
	 */
	public void setDescrDefinizioneProcesso(java.lang.String val) {
		descrDefinizioneProcesso = val;
	}

	////{PROTECTED REGION ID(R-160921238) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descrDefinizioneProcesso. 
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
	public java.lang.String getDescrDefinizioneProcesso() {
		return descrDefinizioneProcesso;
	}

	/**
	 * @generated
	 */
	private java.lang.String descrIstanzaProcesso = null;

	/**
	 * @generated
	 */
	public void setDescrIstanzaProcesso(java.lang.String val) {
		descrIstanzaProcesso = val;
	}

	////{PROTECTED REGION ID(R88428944) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descrIstanzaProcesso. 
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
	public java.lang.String getDescrIstanzaProcesso() {
		return descrIstanzaProcesso;
	}

	/**
	 * @generated
	 */
	private java.lang.String descrStato = null;

	/**
	 * @generated
	 */
	public void setDescrStato(java.lang.String val) {
		descrStato = val;
	}

	////{PROTECTED REGION ID(R599678733) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descrStato. 
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
	public java.lang.String getDescrStato() {
		return descrStato;
	}

	/**
	 * @generated
	 */
	private java.lang.String id = null;

	/**
	 * @generated
	 */
	public void setId(java.lang.String val) {
		id = val;
	}

	////{PROTECTED REGION ID(R1807791630) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo id. 
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
	public java.lang.String getId() {
		return id;
	}

	/*PROTECTED REGION ID(R731203960) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
