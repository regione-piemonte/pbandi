/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio;

////{PROTECTED REGION ID(R-1598714419) ENABLED START////}
/**
 * Inserire qui la documentazione della classe ProvvedimentoDTO.
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
public class ProvvedimentoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idProvvedimento = null;

	/**
	 * @generated
	 */
	public void setIdProvvedimento(java.lang.Long val) {
		idProvvedimento = val;
	}

	////{PROTECTED REGION ID(R588094412) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idProvvedimento. 
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
	public java.lang.Long getIdProvvedimento() {
		return idProvvedimento;
	}

	/**
	 * @generated
	 */
	private java.lang.Long annoProvvedimento = null;

	/**
	 * @generated
	 */
	public void setAnnoProvvedimento(java.lang.Long val) {
		annoProvvedimento = val;
	}

	////{PROTECTED REGION ID(R-528255911) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo annoProvvedimento. 
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
	public java.lang.Long getAnnoProvvedimento() {
		return annoProvvedimento;
	}

	/**
	 * @generated
	 */
	private java.lang.String numeroProvvedimento = null;

	/**
	 * @generated
	 */
	public void setNumeroProvvedimento(java.lang.String val) {
		numeroProvvedimento = val;
	}

	////{PROTECTED REGION ID(R-432609045) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroProvvedimento. 
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
	public java.lang.String getNumeroProvvedimento() {
		return numeroProvvedimento;
	}

	/**
	 * @generated
	 */
	private TipoProvvedimentoDTO tipoProvvedimento = null;

	/**
	 * @generated
	 */
	public void setTipoProvvedimento(
			TipoProvvedimentoDTO val) {
		tipoProvvedimento = val;
	}

	////{PROTECTED REGION ID(R687631763) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipoProvvedimento. 
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
	public TipoProvvedimentoDTO getTipoProvvedimento() {
		return tipoProvvedimento;
	}

	/**
	 * @generated
	 */
	private EnteCompetenzaDTO enteCompetenza = null;

	/**
	 * @generated
	 */
	public void setEnteCompetenza(
			EnteCompetenzaDTO val) {
		enteCompetenza = val;
	}

	////{PROTECTED REGION ID(R-1390402027) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo enteCompetenza. 
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
	public EnteCompetenzaDTO getEnteCompetenza() {
		return enteCompetenza;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataProvvedimento = null;

	/**
	 * @generated
	 */
	public void setDataProvvedimento(java.util.Date val) {
		dataProvvedimento = val;
	}

	////{PROTECTED REGION ID(R521945309) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataProvvedimento. 
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
	public java.util.Date getDataProvvedimento() {
		return dataProvvedimento;
	}

	/**
	 * @generated
	 */
	private EnteCompetenzaDTO enteDelegata = null;

	/**
	 * @generated
	 */
	public void setEnteDelegata(
			EnteCompetenzaDTO val) {
		enteDelegata = val;
	}

	////{PROTECTED REGION ID(R-888627512) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo enteDelegata. 
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
	public EnteCompetenzaDTO getEnteDelegata() {
		return enteDelegata;
	}

	/*PROTECTED REGION ID(R-559365772) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
