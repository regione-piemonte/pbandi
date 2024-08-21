/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio;

////{PROTECTED REGION ID(R1993602885) ENABLED START////}
/**
 * Inserire qui la documentazione della classe RichiestaErogazioneDTO.
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
public class RichiestaErogazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String descCausaleErogazione = null;

	/**
	 * @generated
	 */
	public void setDescCausaleErogazione(java.lang.String val) {
		descCausaleErogazione = val;
	}

	////{PROTECTED REGION ID(R1914864419) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descCausaleErogazione. 
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
	public java.lang.String getDescCausaleErogazione() {
		return descCausaleErogazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String numeroRichiestaErogazione = null;

	/**
	 * @generated
	 */
	public void setNumeroRichiestaErogazione(java.lang.String val) {
		numeroRichiestaErogazione = val;
	}

	////{PROTECTED REGION ID(R-523598462) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroRichiestaErogazione. 
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
	public java.lang.String getNumeroRichiestaErogazione() {
		return numeroRichiestaErogazione;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataRichiestaErogazione = null;

	/**
	 * @generated
	 */
	public void setDataRichiestaErogazione(java.util.Date val) {
		dataRichiestaErogazione = val;
	}

	////{PROTECTED REGION ID(R-1651219980) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataRichiestaErogazione. 
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
	public java.util.Date getDataRichiestaErogazione() {
		return dataRichiestaErogazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoRichiestaErogazione = null;

	/**
	 * @generated
	 */
	public void setImportoRichiestaErogazione(java.lang.Double val) {
		importoRichiestaErogazione = val;
	}

	////{PROTECTED REGION ID(R685279262) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoRichiestaErogazione. 
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
	public java.lang.Double getImportoRichiestaErogazione() {
		return importoRichiestaErogazione;
	}

	/*PROTECTED REGION ID(R-264416090) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
