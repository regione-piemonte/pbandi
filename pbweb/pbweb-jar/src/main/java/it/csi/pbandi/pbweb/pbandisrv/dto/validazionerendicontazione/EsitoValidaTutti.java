/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione;

////{PROTECTED REGION ID(R-1171080403) ENABLED START////}
/**
 * Inserire qui la documentazione della classe EsitoValidaTutti.
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
public class EsitoValidaTutti implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String message = null;

	/**
	 * @generated
	 */
	public void setMessage(java.lang.String val) {
		message = val;
	}

	////{PROTECTED REGION ID(R-1059796934) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo message. 
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
	public java.lang.String getMessage() {
		return message;
	}

	/**
	 * @generated
	 */
	private java.lang.String[] params = null;

	/**
	 * @generated
	 */
	public void setParams(java.lang.String[] val) {
		params = val;
	}

	////{PROTECTED REGION ID(R186506995) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo params. 
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
	public java.lang.String[] getParams() {
		return params;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean esito = null;

	/**
	 * @generated
	 */
	public void setEsito(java.lang.Boolean val) {
		esito = val;
	}

	////{PROTECTED REGION ID(R1104764457) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo esito. 
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
	public java.lang.Boolean getEsito() {
		return esito;
	}

	/**
	 * @generated
	 */
	private java.lang.Long pagamentiDichiarati = null;

	/**
	 * @generated
	 */
	public void setPagamentiDichiarati(java.lang.Long val) {
		pagamentiDichiarati = val;
	}

	////{PROTECTED REGION ID(R-1250570983) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo pagamentiDichiarati. 
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
	public java.lang.Long getPagamentiDichiarati() {
		return pagamentiDichiarati;
	}

	/**
	 * @generated
	 */
	private java.lang.Long pagamentiDaRespingere = null;

	/**
	 * @generated
	 */
	public void setPagamentiDaRespingere(java.lang.Long val) {
		pagamentiDaRespingere = val;
	}

	////{PROTECTED REGION ID(R-1409128298) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo pagamentiDaRespingere. 
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
	public java.lang.Long getPagamentiDaRespingere() {
		return pagamentiDaRespingere;
	}

	/**
	 * @generated
	 */
	private java.lang.Long pagamentiNonValidati = null;

	/**
	 * @generated
	 */
	public void setPagamentiNonValidati(java.lang.Long val) {
		pagamentiNonValidati = val;
	}

	////{PROTECTED REGION ID(R1887860) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo pagamentiNonValidati. 
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
	public java.lang.Long getPagamentiNonValidati() {
		return pagamentiNonValidati;
	}

	/**
	 * @generated
	 */
	private java.lang.Long pagamentiParzialmenteValidati = null;

	/**
	 * @generated
	 */
	public void setPagamentiParzialmenteValidati(java.lang.Long val) {
		pagamentiParzialmenteValidati = val;
	}

	////{PROTECTED REGION ID(R-1428502439) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo pagamentiParzialmenteValidati. 
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
	public java.lang.Long getPagamentiParzialmenteValidati() {
		return pagamentiParzialmenteValidati;
	}

	/**
	 * @generated
	 */
	private java.lang.Long pagamentiSospesi = null;

	/**
	 * @generated
	 */
	public void setPagamentiSospesi(java.lang.Long val) {
		pagamentiSospesi = val;
	}

	////{PROTECTED REGION ID(R108072719) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo pagamentiSospesi. 
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
	public java.lang.Long getPagamentiSospesi() {
		return pagamentiSospesi;
	}

	/**
	 * @generated
	 */
	private java.lang.Long pagamentiValidatiInPrecedenza = null;

	/**
	 * @generated
	 */
	public void setPagamentiValidatiInPrecedenza(java.lang.Long val) {
		pagamentiValidatiInPrecedenza = val;
	}

	////{PROTECTED REGION ID(R-663117503) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo pagamentiValidatiInPrecedenza. 
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
	public java.lang.Long getPagamentiValidatiInPrecedenza() {
		return pagamentiValidatiInPrecedenza;
	}

	/**
	 * @generated
	 */
	private java.lang.Long pagamentiValidatiOra = null;

	/**
	 * @generated
	 */
	public void setPagamentiValidatiOra(java.lang.Long val) {
		pagamentiValidatiOra = val;
	}

	////{PROTECTED REGION ID(R1693848273) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo pagamentiValidatiOra. 
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
	public java.lang.Long getPagamentiValidatiOra() {
		return pagamentiValidatiOra;
	}

	/**
	 * @generated
	 */
	private java.lang.Long totalePagamenti = null;

	/**
	 * @generated
	 */
	public void setTotalePagamenti(java.lang.Long val) {
		totalePagamenti = val;
	}

	////{PROTECTED REGION ID(R185225138) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo totalePagamenti. 
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
	public java.lang.Long getTotalePagamenti() {
		return totalePagamenti;
	}

	/*PROTECTED REGION ID(R-1256982594) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
