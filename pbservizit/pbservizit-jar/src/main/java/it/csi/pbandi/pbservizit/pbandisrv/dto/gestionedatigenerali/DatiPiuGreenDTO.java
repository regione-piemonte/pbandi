/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali;

////{PROTECTED REGION ID(R-307535872) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DatiPiuGreenDTO.
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
public class DatiPiuGreenDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Boolean regolaBR44Attiva = null;

	/**
	 * @generated
	 */
	public void setRegolaBR44Attiva(java.lang.Boolean val) {
		regolaBR44Attiva = val;
	}

	////{PROTECTED REGION ID(R-1930009965) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo regolaBR44Attiva. 
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
	public java.lang.Boolean getRegolaBR44Attiva() {
		return regolaBR44Attiva;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idProgettoContributo = null;

	/**
	 * @generated
	 */
	public void setIdProgettoContributo(java.lang.Long val) {
		idProgettoContributo = val;
	}

	////{PROTECTED REGION ID(R1156329430) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idProgettoContributo. 
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
	public java.lang.Long getIdProgettoContributo() {
		return idProgettoContributo;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDichSpesaIntegrativa = null;

	/**
	 * @generated
	 */
	public void setIdDichSpesaIntegrativa(java.lang.Long val) {
		idDichSpesaIntegrativa = val;
	}

	////{PROTECTED REGION ID(R789850191) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDichSpesaIntegrativa. 
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
	public java.lang.Long getIdDichSpesaIntegrativa() {
		return idDichSpesaIntegrativa;
	}

	/*PROTECTED REGION ID(R-1328909365) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
