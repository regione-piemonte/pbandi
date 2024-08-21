/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione;

////{PROTECTED REGION ID(R-1020813317) ENABLED START////}
/**
 * Inserire qui la documentazione della classe ModalitaAgevolazioneErogazioneDTO.
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
public class ModalitaAgevolazioneErogazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idModalitaAgevolazione = null;

	/**
	 * @generated
	 */
	public void setIdModalitaAgevolazione(java.lang.Long val) {
		idModalitaAgevolazione = val;
	}

	////{PROTECTED REGION ID(R213597801) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idModalitaAgevolazione. 
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
	public java.lang.Long getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveModalitaAgevolaz = null;

	/**
	 * @generated
	 */
	public void setDescBreveModalitaAgevolaz(java.lang.String val) {
		descBreveModalitaAgevolaz = val;
	}

	////{PROTECTED REGION ID(R1744725646) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveModalitaAgevolaz. 
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
	public java.lang.String getDescBreveModalitaAgevolaz() {
		return descBreveModalitaAgevolaz;
	}

	/**
	 * @generated
	 */
	private java.lang.String descModalitaAgevolazione = null;

	/**
	 * @generated
	 */
	public void setDescModalitaAgevolazione(java.lang.String val) {
		descModalitaAgevolazione = val;
	}

	////{PROTECTED REGION ID(R-1473817057) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descModalitaAgevolazione. 
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
	public java.lang.String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoTotaleRecupero = null;

	/**
	 * @generated
	 */
	public void setImportoTotaleRecupero(java.lang.Double val) {
		importoTotaleRecupero = val;
	}

	////{PROTECTED REGION ID(R1758714375) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoTotaleRecupero. 
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
	public java.lang.Double getImportoTotaleRecupero() {
		return importoTotaleRecupero;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.CausaleErogazioneDTO[] causaliErogazione = null;

	/**
	 * @generated
	 */
	public void setCausaliErogazione(
			it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.CausaleErogazioneDTO[] val) {
		causaliErogazione = val;
	}

	////{PROTECTED REGION ID(R644773954) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo causaliErogazione. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.CausaleErogazioneDTO[] getCausaliErogazione() {
		return causaliErogazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Double ultimoImportoAgevolato = null;

	/**
	 * @generated
	 */
	public void setUltimoImportoAgevolato(java.lang.Double val) {
		ultimoImportoAgevolato = val;
	}

	////{PROTECTED REGION ID(R-247220185) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo ultimoImportoAgevolato. 
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
	public java.lang.Double getUltimoImportoAgevolato() {
		return ultimoImportoAgevolato;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoTotaleErogazioni = null;

	/**
	 * @generated
	 */
	public void setImportoTotaleErogazioni(java.lang.Double val) {
		importoTotaleErogazioni = val;
	}

	////{PROTECTED REGION ID(R-2071566257) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoTotaleErogazioni. 
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
	public java.lang.Double getImportoTotaleErogazioni() {
		return importoTotaleErogazioni;
	}

	/*PROTECTED REGION ID(R-2033773520) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
