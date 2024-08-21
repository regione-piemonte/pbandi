/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice;

////{PROTECTED REGION ID(R-538704777) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DocPagamBandoLineaDTO.
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
public class DocPagamBandoLineaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long progrBandoLineaIntervento = null;

	/**
	 * @generated
	 */
	public void setProgrBandoLineaIntervento(java.lang.Long val) {
		progrBandoLineaIntervento = val;
	}

	////{PROTECTED REGION ID(R89341298) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo progrBandoLineaIntervento. 
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
	public java.lang.Long getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoDocumento = null;

	/**
	 * @generated
	 */
	public void setIdTipoDocumento(java.lang.Long val) {
		idTipoDocumento = val;
	}

	////{PROTECTED REGION ID(R1039376174) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoDocumento. 
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
	public java.lang.Long getIdTipoDocumento() {
		return idTipoDocumento;
	}

	/**
	 * @generated
	 */
	private java.lang.String descTipoDocumento = null;

	/**
	 * @generated
	 */
	public void setDescTipoDocumento(java.lang.String val) {
		descTipoDocumento = val;
	}

	////{PROTECTED REGION ID(R1839347512) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipoDocumento. 
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
	public java.lang.String getDescTipoDocumento() {
		return descTipoDocumento;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveTipoDocumento = null;

	/**
	 * @generated
	 */
	public void setDescBreveTipoDocumento(java.lang.String val) {
		descBreveTipoDocumento = val;
	}

	////{PROTECTED REGION ID(R-983171484) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveTipoDocumento. 
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
	public java.lang.String getDescBreveTipoDocumento() {
		return descBreveTipoDocumento;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idModalitaPagamento = null;

	/**
	 * @generated
	 */
	public void setIdModalitaPagamento(java.lang.Long val) {
		idModalitaPagamento = val;
	}

	////{PROTECTED REGION ID(R969478315) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idModalitaPagamento. 
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
	public java.lang.Long getIdModalitaPagamento() {
		return idModalitaPagamento;
	}

	/**
	 * @generated
	 */
	private java.lang.String descModalitaPagamento = null;

	/**
	 * @generated
	 */
	public void setDescModalitaPagamento(java.lang.String val) {
		descModalitaPagamento = val;
	}

	////{PROTECTED REGION ID(R1090032565) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descModalitaPagamento. 
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
	public java.lang.String getDescModalitaPagamento() {
		return descModalitaPagamento;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveModalitaPagamento = null;

	/**
	 * @generated
	 */
	public void setDescBreveModalitaPagamento(java.lang.String val) {
		descBreveModalitaPagamento = val;
	}

	////{PROTECTED REGION ID(R-169024287) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveModalitaPagamento. 
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
	public java.lang.String getDescBreveModalitaPagamento() {
		return descBreveModalitaPagamento;
	}

	/**
	 * @generated
	 */
	private java.lang.Long progrEccezBanLinDocPag = null;

	/**
	 * @generated
	 */
	public void setProgrEccezBanLinDocPag(java.lang.Long val) {
		progrEccezBanLinDocPag = val;
	}

	////{PROTECTED REGION ID(R1100239545) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo progrEccezBanLinDocPag. 
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
	public java.lang.Long getProgrEccezBanLinDocPag() {
		return progrEccezBanLinDocPag;
	}

	/*PROTECTED REGION ID(R-567673974) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
