/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio;

////{PROTECTED REGION ID(R-1488178651) ENABLED START////}
/**
 * Inserire qui la documentazione della classe ModAgevolazioneContributoDTO.
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
public class ModAgevolazioneContributoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idModAgevolazioneContributo = null;

	/**
	 * @generated
	 */
	public void setIdModAgevolazioneContributo(java.lang.Long val) {
		idModAgevolazioneContributo = val;
	}

	////{PROTECTED REGION ID(R-1224505565) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idModAgevolazioneContributo. 
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
	public java.lang.Long getIdModAgevolazioneContributo() {
		return idModAgevolazioneContributo;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveModAgevolazioneContributo = null;

	/**
	 * @generated
	 */
	public void setDescBreveModAgevolazioneContributo(java.lang.String val) {
		descBreveModAgevolazioneContributo = val;
	}

	////{PROTECTED REGION ID(R1023103477) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveModAgevolazioneContributo. 
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
	public java.lang.String getDescBreveModAgevolazioneContributo() {
		return descBreveModAgevolazioneContributo;
	}

	/**
	 * @generated
	 */
	private java.lang.Double quotaImportoAgevolato = null;

	/**
	 * @generated
	 */
	public void setQuotaImportoAgevolato(java.lang.Double val) {
		quotaImportoAgevolato = val;
	}

	////{PROTECTED REGION ID(R-1397175823) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo quotaImportoAgevolato. 
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
	public java.lang.Double getQuotaImportoAgevolato() {
		return quotaImportoAgevolato;
	}

	/*PROTECTED REGION ID(R1748979206) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
