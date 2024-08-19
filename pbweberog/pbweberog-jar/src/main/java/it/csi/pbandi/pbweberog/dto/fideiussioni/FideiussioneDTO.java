/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.fideiussioni;

public class FideiussioneDTO implements java.io.Serializable {
	// il serial version UID è impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private Long idFideiussione;

	/**
	 * @generated
	 */
	public void setIdFideiussione(Long val) {
		idFideiussione = val;
	}

	////{PROTECTED REGION ID(R-80387598) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idFideiussione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessità
	 *          di corrispondenza con una particolare codifica, che può essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public Long getIdFideiussione() {
		return idFideiussione;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoFideiussione;

	/**
	 * @generated
	 */
	public void setImportoFideiussione(java.lang.Double val) {
		importoFideiussione = val;
	}

	////{PROTECTED REGION ID(R1374817389) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoFideiussione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessità
	 *          di corrispondenza con una particolare codifica, che può essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getImportoFideiussione() {
		return importoFideiussione;
	}

	/**
	 * @generated
	 */
	private java.lang.String codRiferimentoFideiussione;

	/**
	 * @generated
	 */
	public void setCodRiferimentoFideiussione(java.lang.String val) {
		codRiferimentoFideiussione = val;
	}

	////{PROTECTED REGION ID(R-313208637) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codRiferimentoFideiussione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessità
	 *          di corrispondenza con una particolare codifica, che può essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getCodRiferimentoFideiussione() {
		return codRiferimentoFideiussione;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtDecorrenza;

	/**
	 * @generated
	 */
	public void setDtDecorrenza(java.util.Date val) {
		dtDecorrenza = val;
	}

	////{PROTECTED REGION ID(R-304511305) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtDecorrenza. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessità
	 *          di corrispondenza con una particolare codifica, che può essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.util.Date getDtDecorrenza() {
		return dtDecorrenza;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtScadenza;

	/**
	 * @generated
	 */
	public void setDtScadenza(java.util.Date val) {
		dtScadenza = val;
	}

	////{PROTECTED REGION ID(R1246534909) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtScadenza. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessità
	 *          di corrispondenza con una particolare codifica, che può essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.util.Date getDtScadenza() {
		return dtScadenza;
	}

	/**
	 * @generated
	 */
	private java.lang.String descEnteEmittente;

	/**
	 * @generated
	 */
	public void setDescEnteEmittente(java.lang.String val) {
		descEnteEmittente = val;
	}

	////{PROTECTED REGION ID(R-1313238138) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descEnteEmittente. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessità
	 *          di corrispondenza con una particolare codifica, che può essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescEnteEmittente() {
		return descEnteEmittente;
	}

	/**
	 * @generated
	 */
	private java.lang.String noteFideiussione;

	/**
	 * @generated
	 */
	public void setNoteFideiussione(java.lang.String val) {
		noteFideiussione = val;
	}

	////{PROTECTED REGION ID(R-221483063) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo noteFideiussione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessità
	 *          di corrispondenza con una particolare codifica, che può essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getNoteFideiussione() {
		return noteFideiussione;
	}

	/**
	 * @generated
	 */
	private Long idTipoTrattamento;

	/**
	 * @generated
	 */
	public void setIdTipoTrattamento(Long val) {
		idTipoTrattamento = val;
	}

	////{PROTECTED REGION ID(R-475251526) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoTrattamento. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessità
	 *          di corrispondenza con una particolare codifica, che può essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public Long getIdTipoTrattamento() {
		return idTipoTrattamento;
	}

	/**
	 * @generated
	 */
	private Long idProgetto;

	/**
	 * @generated
	 */
	public void setIdProgetto(Long val) {
		idProgetto = val;
	}

	////{PROTECTED REGION ID(R-983758391) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idProgetto. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessità
	 *          di corrispondenza con una particolare codifica, che può essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public Long getIdProgetto() {
		return idProgetto;
	}

}
