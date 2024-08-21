/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio;

////{PROTECTED REGION ID(R-167671136) ENABLED START////}
/**
 * Inserire qui la documentazione della classe RiepilogoAttoDiLiquidazionePerProgetto.
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
public class RiepilogoAttoDiLiquidazionePerProgetto
		implements
			java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idAttoDiLiquidazione = null;

	/**
	 * @generated
	 */
	public void setIdAttoDiLiquidazione(java.lang.Long val) {
		idAttoDiLiquidazione = val;
	}

	////{PROTECTED REGION ID(R-264487136) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idAttoDiLiquidazione. 
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
	public java.lang.Long getIdAttoDiLiquidazione() {
		return idAttoDiLiquidazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String modalitaAgevolazione = null;

	/**
	 * @generated
	 */
	public void setModalitaAgevolazione(java.lang.String val) {
		modalitaAgevolazione = val;
	}

	////{PROTECTED REGION ID(R-1089015405) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo modalitaAgevolazione. 
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
	public java.lang.String getModalitaAgevolazione() {
		return modalitaAgevolazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String causaleErogazione = null;

	/**
	 * @generated
	 */
	public void setCausaleErogazione(java.lang.String val) {
		causaleErogazione = val;
	}

	////{PROTECTED REGION ID(R-1747336903) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo causaleErogazione. 
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
	public java.lang.String getCausaleErogazione() {
		return causaleErogazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String estremiAtto = null;

	/**
	 * @generated
	 */
	public void setEstremiAtto(java.lang.String val) {
		estremiAtto = val;
	}

	////{PROTECTED REGION ID(R214482563) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo estremiAtto. 
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
	public java.lang.String getEstremiAtto() {
		return estremiAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.Double ultimoImportoAgevolato = null;

	/**
	 * @generated
	 */
	public void setUltimoImportoAgevolato(java.lang.Double val) {
		ultimoImportoAgevolato = val;
	}

	////{PROTECTED REGION ID(R-1284904692) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo ultimoImportoAgevolato. 
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
	public java.lang.Double getUltimoImportoAgevolato() {
		return ultimoImportoAgevolato;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoLiquidato = null;

	/**
	 * @generated
	 */
	public void setImportoLiquidato(java.lang.Double val) {
		importoLiquidato = val;
	}

	////{PROTECTED REGION ID(R1785681974) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoLiquidato. 
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
	public java.lang.Double getImportoLiquidato() {
		return importoLiquidato;
	}

	/*PROTECTED REGION ID(R564988843) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
