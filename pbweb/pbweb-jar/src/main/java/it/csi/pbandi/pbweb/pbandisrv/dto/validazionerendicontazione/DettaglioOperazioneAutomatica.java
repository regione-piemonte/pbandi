/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione;

////{PROTECTED REGION ID(R-1323738267) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DettaglioOperazioneAutomatica.
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
public class DettaglioOperazioneAutomatica implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long documentiDichiarati = null;

	/**
	 * @generated
	 */
	public void setDocumentiDichiarati(java.lang.Long val) {
		documentiDichiarati = val;
	}

	////{PROTECTED REGION ID(R-487737361) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo documentiDichiarati. 
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
	public java.lang.Long getDocumentiDichiarati() {
		return documentiDichiarati;
	}

	/**
	 * @generated
	 */
	private java.lang.Long documentiNonValidati = null;

	/**
	 * @generated
	 */
	public void setDocumentiNonValidati(java.lang.Long val) {
		documentiNonValidati = val;
	}

	////{PROTECTED REGION ID(R-2120073634) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo documentiNonValidati. 
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
	public java.lang.Long getDocumentiNonValidati() {
		return documentiNonValidati;
	}

	/**
	 * @generated
	 */
	private java.lang.Long documentiParzialmenteValidati = null;

	/**
	 * @generated
	 */
	public void setDocumentiParzialmenteValidati(java.lang.Long val) {
		documentiParzialmenteValidati = val;
	}

	////{PROTECTED REGION ID(R841195439) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo documentiParzialmenteValidati. 
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
	public java.lang.Long getDocumentiParzialmenteValidati() {
		return documentiParzialmenteValidati;
	}

	/**
	 * @generated
	 */
	private java.lang.Long documentiValidati = null;

	/**
	 * @generated
	 */
	public void setDocumentiValidati(java.lang.Long val) {
		documentiValidati = val;
	}

	////{PROTECTED REGION ID(R-110592733) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo documentiValidati. 
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
	public java.lang.Long getDocumentiValidati() {
		return documentiValidati;
	}
	
	/**
	 * @generated
	 */
	private java.lang.Long documentiSospesi = null;

	/**
	 * @generated
	 */
	public void setDocumentiSospesi(java.lang.Long val) {
		documentiSospesi = val;
	}

	////{PROTECTED REGION ID(R-110592733) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo documentiValidati. 
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
	public java.lang.Long getDocumentiSospesi() {
		return documentiSospesi;
	}

	/**
	 * @generated
	 */
	private java.lang.Long documentiRespinti = null;

	/**
	 * @generated
	 */
	public void setDocumentiRespinti(java.lang.Long val) {
		documentiRespinti = val;
	}

	////{PROTECTED REGION ID(R970178099) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo documentiRespinti. 
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
	public java.lang.Long getDocumentiRespinti() {
		return documentiRespinti;
	}

	/**
	 * @generated
	 */
	private java.lang.Long documentiElaborati = null;

	/**
	 * @generated
	 */
	public void setDocumentiElaborati(java.lang.Long val) {
		documentiElaborati = val;
	}

	////{PROTECTED REGION ID(R-2137738014) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo documentiElaborati. 
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
	public java.lang.Long getDocumentiElaborati() {
		return documentiElaborati;
	}

	/**
	 * @generated
	 */
	private java.lang.Long totaleDocumenti = null;

	/**
	 * @generated
	 */
	public void setTotaleDocumenti(java.lang.Long val) {
		totaleDocumenti = val;
	}

	////{PROTECTED REGION ID(R932935688) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo totaleDocumenti. 
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
	public java.lang.Long getTotaleDocumenti() {
		return totaleDocumenti;
	}

	/**
	 * @generated
	 */
	private java.lang.Long documentiInValidazione = null;

	/**
	 * @generated
	 */
	public void setDocumentiInValidazione(java.lang.Long val) {
		documentiInValidazione = val;
	}

	////{PROTECTED REGION ID(R-1769115836) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo documentiInValidazione. 
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
	public java.lang.Long getDocumentiInValidazione() {
		return documentiInValidazione;
	}

	/*PROTECTED REGION ID(R720198620) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
