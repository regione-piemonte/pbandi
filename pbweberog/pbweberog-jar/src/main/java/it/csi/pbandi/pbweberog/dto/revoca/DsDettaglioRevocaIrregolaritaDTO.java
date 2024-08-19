/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.revoca;

////{PROTECTED REGION ID(R-996501823) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DsDettaglioRevocaIrregolaritaDTO.
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
public class DsDettaglioRevocaIrregolaritaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idDettRevocaIrreg = null;

	/**
	 * @generated
	 */
	public void setIdDettRevocaIrreg(java.lang.Long val) {
		idDettRevocaIrreg = val;
	}

	////{PROTECTED REGION ID(R-1080114524) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDettRevocaIrreg. 
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
	public java.lang.Long getIdDettRevocaIrreg() {
		return idDettRevocaIrreg;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDichiarazioneSpesa = null;

	/**
	 * @generated
	 */
	public void setIdDichiarazioneSpesa(java.lang.Long val) {
		idDichiarazioneSpesa = val;
	}

	////{PROTECTED REGION ID(R-2134029620) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDichiarazioneSpesa. 
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
	public java.lang.Long getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoIrregolareDs = null;

	/**
	 * @generated
	 */
	public void setImportoIrregolareDs(java.lang.Double val) {
		importoIrregolareDs = val;
	}

	////{PROTECTED REGION ID(R1389414212) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoIrregolareDs. 
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
	public java.lang.Double getImportoIrregolareDs() {
		return importoIrregolareDs;
	}

	/*PROTECTED REGION ID(R1737741248) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
