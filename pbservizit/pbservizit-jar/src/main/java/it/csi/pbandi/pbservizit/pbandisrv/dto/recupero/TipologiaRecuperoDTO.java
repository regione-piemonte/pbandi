/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.recupero;

////{PROTECTED REGION ID(R-1800115310) ENABLED START////}
/**
 * Inserire qui la documentazione della classe TipologiaRecuperoDTO.
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
public class TipologiaRecuperoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idTipoRecupero = null;

	/**
	 * @generated
	 */
	public void setIdTipoRecupero(java.lang.Long val) {
		idTipoRecupero = val;
	}

	////{PROTECTED REGION ID(R-1546261896) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoRecupero. 
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
	public java.lang.Long getIdTipoRecupero() {
		return idTipoRecupero;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveTipoRecupero = null;

	/**
	 * @generated
	 */
	public void setDescBreveTipoRecupero(java.lang.String val) {
		descBreveTipoRecupero = val;
	}

	////{PROTECTED REGION ID(R-162785172) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveTipoRecupero. 
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
	public java.lang.String getDescBreveTipoRecupero() {
		return descBreveTipoRecupero;
	}

	/**
	 * @generated
	 */
	private java.lang.String descTipoRecupero = null;

	/**
	 * @generated
	 */
	public void setDescTipoRecupero(java.lang.String val) {
		descTipoRecupero = val;
	}

	////{PROTECTED REGION ID(R-2019058066) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipoRecupero. 
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
	public java.lang.String getDescTipoRecupero() {
		return descTipoRecupero;
	}

	/*PROTECTED REGION ID(R1202409743) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
