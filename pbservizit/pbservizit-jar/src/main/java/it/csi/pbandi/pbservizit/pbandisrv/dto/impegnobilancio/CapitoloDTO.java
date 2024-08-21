/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio;

////{PROTECTED REGION ID(R-1740135256) ENABLED START////}
/**
 * Inserire qui la documentazione della classe CapitoloDTO.
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
public class CapitoloDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idCapitolo = null;

	/**
	 * @generated
	 */
	public void setIdCapitolo(java.lang.Long val) {
		idCapitolo = val;
	}

	////{PROTECTED REGION ID(R-2126333576) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idCapitolo. 
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
	public java.lang.Long getIdCapitolo() {
		return idCapitolo;
	}

	/**
	 * @generated
	 */
	private java.lang.Long numeroCapitolo = null;

	/**
	 * @generated
	 */
	public void setNumeroCapitolo(java.lang.Long val) {
		numeroCapitolo = val;
	}

	////{PROTECTED REGION ID(R-1093963911) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroCapitolo. 
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
	public java.lang.Long getNumeroCapitolo() {
		return numeroCapitolo;
	}

	/**
	 * @generated
	 */
	private java.lang.Long numeroArticolo = null;

	/**
	 * @generated
	 */
	public void setNumeroArticolo(java.lang.Long val) {
		numeroArticolo = val;
	}

	////{PROTECTED REGION ID(R2032053561) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroArticolo. 
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
	public java.lang.Long getNumeroArticolo() {
		return numeroArticolo;
	}

	/**
	 * @generated
	 */
	private java.lang.String descCapitolo = null;

	/**
	 * @generated
	 */
	public void setDescCapitolo(java.lang.String val) {
		descCapitolo = val;
	}

	////{PROTECTED REGION ID(R-1354118418) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descCapitolo. 
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
	public java.lang.String getDescCapitolo() {
		return descCapitolo;
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

	////{PROTECTED REGION ID(R-1224236624) ENABLED START////}
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
	private TipoFondoDTO tipoFondo = null;

	/**
	 * @generated
	 */
	public void setTipoFondo(TipoFondoDTO val) {
		tipoFondo = val;
	}

	////{PROTECTED REGION ID(R-724356716) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipoFondo. 
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
	public TipoFondoDTO getTipoFondo() {
		return tipoFondo;
	}

	/**
	 * @generated
	 */
	private java.lang.String provenienza = null;

	/**
	 * @generated
	 */
	public void setProvenienza(java.lang.String val) {
		provenienza = val;
	}

	////{PROTECTED REGION ID(R-661031617) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo provenienza. 
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
	public java.lang.String getProvenienza() {
		return provenienza;
	}

	/*PROTECTED REGION ID(R-665200541) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
