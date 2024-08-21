/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa;

////{PROTECTED REGION ID(R-883053246) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DocumentoDiSpesaComunicazioneDTO.
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
public class DocumentoDiSpesaComunicazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idDocumentoDiSpesa = null;

	/**
	 * @generated
	 */
	public void setIdDocumentoDiSpesa(java.lang.Long val) {
		idDocumentoDiSpesa = val;
	}

	////{PROTECTED REGION ID(R285146906) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDocumentoDiSpesa. 
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
	public java.lang.Long getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String tipoDocumentoDiSpesa = null;

	/**
	 * @generated
	 */
	public void setTipoDocumentoDiSpesa(java.lang.String val) {
		tipoDocumentoDiSpesa = val;
	}

	////{PROTECTED REGION ID(R-1734686029) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipoDocumentoDiSpesa. 
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
	public java.lang.String getTipoDocumentoDiSpesa() {
		return tipoDocumentoDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String numeroDocumentoDiSpesa = null;

	/**
	 * @generated
	 */
	public void setNumeroDocumentoDiSpesa(java.lang.String val) {
		numeroDocumentoDiSpesa = val;
	}

	////{PROTECTED REGION ID(R-372668645) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroDocumentoDiSpesa. 
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
	public java.lang.String getNumeroDocumentoDiSpesa() {
		return numeroDocumentoDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Double totalePagamentiDichiarazione = null;

	/**
	 * @generated
	 */
	public void setTotalePagamentiDichiarazione(java.lang.Double val) {
		totalePagamentiDichiarazione = val;
	}

	////{PROTECTED REGION ID(R-425184887) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo totalePagamentiDichiarazione. 
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
	public java.lang.Double getTotalePagamentiDichiarazione() {
		return totalePagamentiDichiarazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoDocumento = null;

	/**
	 * @generated
	 */
	public void setImportoDocumento(java.lang.Double val) {
		importoDocumento = val;
	}

	////{PROTECTED REGION ID(R-1953803860) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoDocumento. 
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
	public java.lang.Double getImportoDocumento() {
		return importoDocumento;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoPagamento = null;

	/**
	 * @generated
	 */
	public void setImportoPagamento(java.lang.Double val) {
		importoPagamento = val;
	}

	////{PROTECTED REGION ID(R-2115497442) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoPagamento. 
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
	public java.lang.Double getImportoPagamento() {
		return importoPagamento;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoRendicontabile = null;

	/**
	 * @generated
	 */
	public void setImportoRendicontabile(java.lang.Double val) {
		importoRendicontabile = val;
	}

	////{PROTECTED REGION ID(R179325335) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoRendicontabile. 
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
	public java.lang.Double getImportoRendicontabile() {
		return importoRendicontabile;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataEmissioneDocumentoDiSpesa = null;

	/**
	 * @generated
	 */
	public void setDataEmissioneDocumentoDiSpesa(java.util.Date val) {
		dataEmissioneDocumentoDiSpesa = val;
	}

	////{PROTECTED REGION ID(R2050514031) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataEmissioneDocumentoDiSpesa. 
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
	public java.util.Date getDataEmissioneDocumentoDiSpesa() {
		return dataEmissioneDocumentoDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String task = null;

	/**
	 * @generated
	 */
	public void setTask(java.lang.String val) {
		task = val;
	}

	////{PROTECTED REGION ID(R1586983367) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo task. 
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
	public java.lang.String getTask() {
		return task;
	}

	/**
	 * @generated
	 */
	private java.lang.String fornitore = null;

	/**
	 * @generated
	 */
	public void setFornitore(java.lang.String val) {
		fornitore = val;
	}

	////{PROTECTED REGION ID(R-1211870928) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo fornitore. 
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
	public java.lang.String getFornitore() {
		return fornitore;
	}

	/*PROTECTED REGION ID(R-2075415137) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
