/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.esecuzioneattivita;

////{PROTECTED REGION ID(R1897792247) ENABLED START////}
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
	private java.lang.String id = null;

	/**
	 * @generated
	 */
	public void setId(java.lang.String val) {
		id = val;
	}

	////{PROTECTED REGION ID(R-1582748078) ENABLED START////}
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

	/**
	 * @generated
	 */
	private java.lang.String urlMiniApp = null;

	/**
	 * @generated
	 */
	public void setUrlMiniApp(java.lang.String val) {
		urlMiniApp = val;
	}

	////{PROTECTED REGION ID(R-1218409454) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo urlMiniApp. 
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
	public java.lang.String getUrlMiniApp() {
		return urlMiniApp;
	}

	/**
	 * @generated
	 */
	private java.lang.String taskName = null;

	/**
	 * @generated
	 */
	public void setTaskName(java.lang.String val) {
		taskName = val;
	}

	////{PROTECTED REGION ID(R-242354777) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo taskName. 
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
	public java.lang.String getTaskName() {
		return taskName;
	}

	/**
	 * @generated
	 */
	private boolean notifica = true;

	/**
	 * @generated
	 */
	public void setNotifica(boolean val) {
		notifica = val;
	}

	////{PROTECTED REGION ID(R1753339214) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo notifica. 
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
	public boolean getNotifica() {
		return notifica;
	}

	/**
	 * @generated
	 */
	private java.lang.String testoNotifica = null;

	/**
	 * @generated
	 */
	public void setTestoNotifica(java.lang.String val) {
		testoNotifica = val;
	}

	////{PROTECTED REGION ID(R-726612739) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo testoNotifica. 
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
	public java.lang.String getTestoNotifica() {
		return testoNotifica;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.esecuzioneattivita.VariabileDTO[] variabili = null;

	/**
	 * @generated
	 */
	public void setVariabili(
			it.csi.pbandi.pbservizit.pbandisrv.dto.esecuzioneattivita.VariabileDTO[] val) {
		variabili = val;
	}

	////{PROTECTED REGION ID(R829806060) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo variabili. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.esecuzioneattivita.VariabileDTO[] getVariabili() {
		return variabili;
	}

	/*PROTECTED REGION ID(R-1427740812) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
