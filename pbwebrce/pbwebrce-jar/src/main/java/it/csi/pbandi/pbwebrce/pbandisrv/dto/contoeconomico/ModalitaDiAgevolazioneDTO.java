/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico;

////{PROTECTED REGION ID(R1447799791) ENABLED START////}
/**
 * Inserire qui la documentazione della classe ModalitaDiAgevolazioneDTO.
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
public class ModalitaDiAgevolazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idModalitaDiAgevolazione = null;

	/**
	 * @generated
	 */
	public void setIdModalitaDiAgevolazione(java.lang.Long val) {
		idModalitaDiAgevolazione = val;
	}

	////{PROTECTED REGION ID(R-199707710) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idModalitaDiAgevolazione. 
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
	public java.lang.Long getIdModalitaDiAgevolazione() {
		return idModalitaDiAgevolazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String descrizione = null;

	/**
	 * @generated
	 */
	public void setDescrizione(java.lang.String val) {
		descrizione = val;
	}

	////{PROTECTED REGION ID(R-824025600) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descrizione. 
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
	public java.lang.String getDescrizione() {
		return descrizione;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoMassimoAgevolato = null;

	/**
	 * @generated
	 */
	public void setImportoMassimoAgevolato(java.lang.Double val) {
		importoMassimoAgevolato = val;
	}

	////{PROTECTED REGION ID(R2001205452) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoMassimoAgevolato. 
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
	public java.lang.Double getImportoMassimoAgevolato() {
		return importoMassimoAgevolato;
	}

	/**
	 * @generated
	 */
	private java.lang.Double percImportoMassimoAgevolato = null;

	/**
	 * @generated
	 */
	public void setPercImportoMassimoAgevolato(java.lang.Double val) {
		percImportoMassimoAgevolato = val;
	}

	////{PROTECTED REGION ID(R-1038779770) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percImportoMassimoAgevolato. 
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
	public java.lang.Double getPercImportoMassimoAgevolato() {
		return percImportoMassimoAgevolato;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoRichiestoUltimo = null;

	/**
	 * @generated
	 */
	public void setImportoRichiestoUltimo(java.lang.Double val) {
		importoRichiestoUltimo = val;
	}

	////{PROTECTED REGION ID(R-2093373015) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoRichiestoUltimo. 
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
	public java.lang.Double getImportoRichiestoUltimo() {
		return importoRichiestoUltimo;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoRichiestoNuovo = null;

	/**
	 * @generated
	 */
	public void setImportoRichiestoNuovo(java.lang.Double val) {
		importoRichiestoNuovo = val;
	}

	////{PROTECTED REGION ID(R-766465754) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoRichiestoNuovo. 
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
	public java.lang.Double getImportoRichiestoNuovo() {
		return importoRichiestoNuovo;
	}

	/**
	 * @generated
	 */
	private java.lang.Double percImportoRichiestoNuovo = null;

	/**
	 * @generated
	 */
	public void setPercImportoRichiestoNuovo(java.lang.Double val) {
		percImportoRichiestoNuovo = val;
	}

	////{PROTECTED REGION ID(R942100832) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percImportoRichiestoNuovo. 
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
	public java.lang.Double getPercImportoRichiestoNuovo() {
		return percImportoRichiestoNuovo;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoErogato = null;

	/**
	 * @generated
	 */
	public void setImportoErogato(java.lang.Double val) {
		importoErogato = val;
	}

	////{PROTECTED REGION ID(R-1766064612) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoErogato. 
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
	public java.lang.Double getImportoErogato() {
		return importoErogato;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoAgevolatoUltimo = null;

	/**
	 * @generated
	 */
	public void setImportoAgevolatoUltimo(java.lang.Double val) {
		importoAgevolatoUltimo = val;
	}

	////{PROTECTED REGION ID(R-818439525) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoAgevolatoUltimo. 
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
	public java.lang.Double getImportoAgevolatoUltimo() {
		return importoAgevolatoUltimo;
	}

	/**
	 * @generated
	 */
	private java.lang.Double percImportoAgevolatoUltimo = null;

	/**
	 * @generated
	 */
	public void setPercImportoAgevolatoUltimo(java.lang.Double val) {
		percImportoAgevolatoUltimo = val;
	}

	////{PROTECTED REGION ID(R607517089) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percImportoAgevolatoUltimo. 
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
	public java.lang.Double getPercImportoAgevolatoUltimo() {
		return percImportoAgevolatoUltimo;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoAgevolatoNuovo = null;

	/**
	 * @generated
	 */
	public void setImportoAgevolatoNuovo(java.lang.Double val) {
		importoAgevolatoNuovo = val;
	}

	////{PROTECTED REGION ID(R-1556622860) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoAgevolatoNuovo. 
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
	public java.lang.Double getImportoAgevolatoNuovo() {
		return importoAgevolatoNuovo;
	}

	/**
	 * @generated
	 */
	private java.lang.Double percImportoAgevolatoNuovo = null;

	/**
	 * @generated
	 */
	public void setPercImportoAgevolatoNuovo(java.lang.Double val) {
		percImportoAgevolatoNuovo = val;
	}

	////{PROTECTED REGION ID(R151943726) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percImportoAgevolatoNuovo. 
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
	public java.lang.Double getPercImportoAgevolatoNuovo() {
		return percImportoAgevolatoNuovo;
	}

	/*PROTECTED REGION ID(R-1446611396) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
