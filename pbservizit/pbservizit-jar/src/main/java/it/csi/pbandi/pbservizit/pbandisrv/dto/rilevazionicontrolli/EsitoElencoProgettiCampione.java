/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli;

public class EsitoElencoProgettiCampione implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private ProgettoDTO[] progettiGiaPresenti = null;

	/**
	 * @generated
	 */
	public void setProgettiGiaPresenti(
			ProgettoDTO[] val) {
		progettiGiaPresenti = val;
	}

	////{PROTECTED REGION ID(R1571703745) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo progettiGiaPresenti. 
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
	public ProgettoDTO[] getProgettiGiaPresenti() {
		return progettiGiaPresenti;
	}

	/**
	 * @generated
	 */
	private ProgettoDTO[] progettiDaAggiungere = null;

	/**
	 * @generated
	 */
	public void setProgettiDaAggiungere(
			ProgettoDTO[] val) {
		progettiDaAggiungere = val;
	}

	////{PROTECTED REGION ID(R-1012295109) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo progettiDaAggiungere. 
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
	public ProgettoDTO[] getProgettiDaAggiungere() {
		return progettiDaAggiungere;
	}

	/**
	 * @generated
	 */
	private java.lang.String progettiScartati = null;

	/**
	 * @generated
	 */
	public void setProgettiScartati(java.lang.String val) {
		progettiScartati = val;
	}

	////{PROTECTED REGION ID(R262216783) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo progettiScartati. 
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
	public java.lang.String getProgettiScartati() {
		return progettiScartati;
	}

	/**
	 * @generated
	 */
	private ProgettoDTO[] progettiDelCampione = null;

	/**
	 * @generated
	 */
	public void setProgettiDelCampione(
			ProgettoDTO[] val) {
		progettiDelCampione = val;
	}

	////{PROTECTED REGION ID(R1888505949) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo progettiDelCampione. 
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
	public ProgettoDTO[] getProgettiDelCampione() {
		return progettiDelCampione;
	}

	/*PROTECTED REGION ID(R-427285805) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
