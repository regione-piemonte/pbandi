/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa;

////{PROTECTED REGION ID(R847520217) ENABLED START////}
/**
 * Inserire qui la documentazione della classe TipoDocumentoDiSPesaDTO.
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
public class TipoDocumentoDiSPesaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idTipoDocumentoSpesa = null;

	/**
	 * @generated
	 */
	public void setIdTipoDocumentoSpesa(java.lang.Long val) {
		idTipoDocumentoSpesa = val;
	}

	////{PROTECTED REGION ID(R280647626) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoDocumentoSpesa. 
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
	public java.lang.Long getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveTipoDocSpesa = null;

	/**
	 * @generated
	 */
	public void setDescBreveTipoDocSpesa(java.lang.String val) {
		descBreveTipoDocSpesa = val;
	}

	////{PROTECTED REGION ID(R577741260) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveTipoDocSpesa. 
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
	public java.lang.String getDescBreveTipoDocSpesa() {
		return descBreveTipoDocSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String descTipoDocumentoSpesa = null;

	/**
	 * @generated
	 */
	public void setDescTipoDocumentoSpesa(java.lang.String val) {
		descTipoDocumentoSpesa = val;
	}

	////{PROTECTED REGION ID(R-1502173824) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipoDocumentoSpesa. 
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
	public java.lang.String getDescTipoDocumentoSpesa() {
		return descTipoDocumentoSpesa;
	}

	/*PROTECTED REGION ID(R-811359960) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
