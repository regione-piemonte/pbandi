/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio;

////{PROTECTED REGION ID(R1548484041) ENABLED START////}
/**
 * Inserire qui la documentazione della classe PeriodoDTO.
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
public class PeriodoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idPeriodo = null;

	/**
	 * @generated
	 */
	public void setIdPeriodo(java.lang.Long val) {
		idPeriodo = val;
	}

	////{PROTECTED REGION ID(R1230331274) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idPeriodo. 
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
	public java.lang.Long getIdPeriodo() {
		return idPeriodo;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoPeriodo = null;

	/**
	 * @generated
	 */
	public void setIdTipoPeriodo(java.lang.Long val) {
		idTipoPeriodo = val;
	}

	////{PROTECTED REGION ID(R-1029856842) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoPeriodo. 
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
	public java.lang.Long getIdTipoPeriodo() {
		return idTipoPeriodo;
	}

	/**
	 * @generated
	 */
	private java.lang.String descPeriodo = null;

	/**
	 * @generated
	 */
	public void setDescPeriodo(java.lang.String val) {
		descPeriodo = val;
	}

	////{PROTECTED REGION ID(R-1113308780) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descPeriodo. 
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
	public java.lang.String getDescPeriodo() {
		return descPeriodo;
	}

	/**
	 * @generated
	 */
	private java.lang.String descPeriodoVisualizzata = null;

	/**
	 * @generated
	 */
	public void setDescPeriodoVisualizzata(java.lang.String val) {
		descPeriodoVisualizzata = val;
	}

	////{PROTECTED REGION ID(R525986681) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descPeriodoVisualizzata. 
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
	public java.lang.String getDescPeriodoVisualizzata() {
		return descPeriodoVisualizzata;
	}

	/*PROTECTED REGION ID(R-707644104) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
