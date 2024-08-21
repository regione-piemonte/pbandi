/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio;

////{PROTECTED REGION ID(R1647912403) ENABLED START////}
/**
 * Inserire qui la documentazione della classe NazioneDTO.
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
public class NazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String codIstatNazione = null;

	/**
	 * @generated
	 */
	public void setCodIstatNazione(java.lang.String val) {
		codIstatNazione = val;
	}

	////{PROTECTED REGION ID(R1868300652) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codIstatNazione. 
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
	public java.lang.String getCodIstatNazione() {
		return codIstatNazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String descNazione = null;

	/**
	 * @generated
	 */
	public void setDescNazione(java.lang.String val) {
		descNazione = val;
	}

	////{PROTECTED REGION ID(R1345287040) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descNazione. 
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
	public java.lang.String getDescNazione() {
		return descNazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagAppartenenzaUe = null;

	/**
	 * @generated
	 */
	public void setFlagAppartenenzaUe(java.lang.String val) {
		flagAppartenenzaUe = val;
	}

	////{PROTECTED REGION ID(R-1627555222) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagAppartenenzaUe. 
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
	public java.lang.String getFlagAppartenenzaUe() {
		return flagAppartenenzaUe;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idNazione = null;

	/**
	 * @generated
	 */
	public void setIdNazione(java.lang.Long val) {
		idNazione = val;
	}

	////{PROTECTED REGION ID(R845448438) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idNazione. 
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
	public java.lang.Long getIdNazione() {
		return idNazione;
	}

	/*PROTECTED REGION ID(R2090233070) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
