/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.rettifica;

////{PROTECTED REGION ID(R2083941906) ENABLED START////}
/**
 * Inserire qui la documentazione della classe PagamentoRettificaDTO.
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
public class PagamentoRettificaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

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

	////{PROTECTED REGION ID(R946456826) ENABLED START////}
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

	////{PROTECTED REGION ID(R-474795332) ENABLED START////}
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
	private java.util.Date dtPagamento = null;

	/**
	 * @generated
	 */
	public void setDtPagamento(java.util.Date val) {
		dtPagamento = val;
	}

	////{PROTECTED REGION ID(R1178581796) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtPagamento. 
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
	public java.util.Date getDtPagamento() {
		return dtPagamento;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtValuta = null;

	/**
	 * @generated
	 */
	public void setDtValuta(java.util.Date val) {
		dtValuta = val;
	}

	////{PROTECTED REGION ID(R-548722781) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtValuta. 
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
	public java.util.Date getDtValuta() {
		return dtValuta;
	}

	/**
	 * @generated
	 */
	private java.lang.String descStatoValidazioneSpesa = null;

	/**
	 * @generated
	 */
	public void setDescStatoValidazioneSpesa(java.lang.String val) {
		descStatoValidazioneSpesa = val;
	}

	////{PROTECTED REGION ID(R-1954489380) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descStatoValidazioneSpesa. 
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
	public java.lang.String getDescStatoValidazioneSpesa() {
		return descStatoValidazioneSpesa;
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

	////{PROTECTED REGION ID(R1933914862) ENABLED START////}
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
	private java.lang.Long idPagamento = null;

	/**
	 * @generated
	 */
	public void setIdPagamento(java.lang.Long val) {
		idPagamento = val;
	}

	////{PROTECTED REGION ID(R-366991623) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idPagamento. 
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
	public java.lang.Long getIdPagamento() {
		return idPagamento;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.VoceDiSpesaRettificaDTO[] vociDiSpesa = null;

	/**
	 * @generated
	 */
	public void setVociDiSpesa(
			it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.VoceDiSpesaRettificaDTO[] val) {
		vociDiSpesa = val;
	}

	////{PROTECTED REGION ID(R163963200) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo vociDiSpesa. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.VoceDiSpesaRettificaDTO[] getVociDiSpesa() {
		return vociDiSpesa;
	}

	/*PROTECTED REGION ID(R1232018383) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
