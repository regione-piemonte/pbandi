/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico;

////{PROTECTED REGION ID(R-359127975) ENABLED START////}
/**
 * Inserire qui la documentazione della classe PercentualeBandoSoggFinanzDTO.
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
public class PercentualeBandoSoggFinanzDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idSoggettoFinanziatore = null;

	/**
	 * @generated
	 */
	public void setIdSoggettoFinanziatore(java.lang.Long val) {
		idSoggettoFinanziatore = val;
	}

	////{PROTECTED REGION ID(R1941491106) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSoggettoFinanziatore. 
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
	public java.lang.Long getIdSoggettoFinanziatore() {
		return idSoggettoFinanziatore;
	}

	/**
	 * @generated
	 */
	private java.lang.Double percentualeQuotaSoggFinanz = null;

	/**
	 * @generated
	 */
	public void setPercentualeQuotaSoggFinanz(java.lang.Double val) {
		percentualeQuotaSoggFinanz = val;
	}

	////{PROTECTED REGION ID(R-1942571803) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percentualeQuotaSoggFinanz. 
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
	public java.lang.Double getPercentualeQuotaSoggFinanz() {
		return percentualeQuotaSoggFinanz;
	}

	/*PROTECTED REGION ID(R1229768466) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
