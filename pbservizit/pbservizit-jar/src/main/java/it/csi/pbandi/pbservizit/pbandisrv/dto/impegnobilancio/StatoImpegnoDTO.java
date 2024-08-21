/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio;

////{PROTECTED REGION ID(R-1375746231) ENABLED START////}
/**
 * Inserire qui la documentazione della classe StatoImpegnoDTO.
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
public class StatoImpegnoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idStatoImpegno = null;

	/**
	 * @generated
	 */
	public void setIdStatoImpegno(java.lang.Long val) {
		idStatoImpegno = val;
	}

	////{PROTECTED REGION ID(R1472343768) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idStatoImpegno. 
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
	public java.lang.Long getIdStatoImpegno() {
		return idStatoImpegno;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveStatoImpegno = null;

	/**
	 * @generated
	 */
	public void setDescBreveStatoImpegno(java.lang.String val) {
		descBreveStatoImpegno = val;
	}

	////{PROTECTED REGION ID(R-602719746) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveStatoImpegno. 
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
	public java.lang.String getDescBreveStatoImpegno() {
		return descBreveStatoImpegno;
	}

	/**
	 * @generated
	 */
	private java.lang.String descStatoImpegno = null;

	/**
	 * @generated
	 */
	public void setDescStatoImpegno(java.lang.String val) {
		descStatoImpegno = val;
	}

	////{PROTECTED REGION ID(R-1248993010) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descStatoImpegno. 
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
	public java.lang.String getDescStatoImpegno() {
		return descStatoImpegno;
	}

	/*PROTECTED REGION ID(R955354530) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
