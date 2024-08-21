/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.revoca;

////{PROTECTED REGION ID(R1845242030) ENABLED START////}
/**
 * Inserire qui la documentazione della classe SaveDsDTO.
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
public class SaveDsDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idRevoca = null;

	/**
	 * @generated
	 */
	public void setIdRevoca(java.lang.Long val) {
		idRevoca = val;
	}

	////{PROTECTED REGION ID(R14033203) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idRevoca. 
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
	public java.lang.Long getIdRevoca() {
		return idRevoca;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idIrregolarita = null;

	/**
	 * @generated
	 */
	public void setIdIrregolarita(java.lang.Long val) {
		idIrregolarita = val;
	}

	////{PROTECTED REGION ID(R-1336475962) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idIrregolarita. 
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
	public java.lang.Long getIdIrregolarita() {
		return idIrregolarita;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idClassRevocaIrreg = null;

	/**
	 * @generated
	 */
	public void setIdClassRevocaIrreg(java.lang.Long val) {
		idClassRevocaIrreg = val;
	}

	////{PROTECTED REGION ID(R-1511829710) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idClassRevocaIrreg. 
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
	public java.lang.Long getIdClassRevocaIrreg() {
		return idClassRevocaIrreg;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDs = null;

	/**
	 * @generated
	 */
	public void setIdDs(java.lang.Long val) {
		idDs = val;
	}

	////{PROTECTED REGION ID(R1299012792) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDs. 
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
	public java.lang.Long getIdDs() {
		return idDs;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoIrregolareDs = null;

	/**
	 * @generated
	 */
	public void setImportoIrregolareDs(java.lang.Double val) {
		importoIrregolareDs = val;
	}

	////{PROTECTED REGION ID(R1594821687) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoIrregolareDs. 
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
	public java.lang.Double getImportoIrregolareDs() {
		return importoIrregolareDs;
	}

	/**
	 * @generated
	 */
	private java.lang.String tipologia = null;

	/**
	 * @generated
	 */
	public void setTipologia(java.lang.String val) {
		tipologia = val;
	}

	////{PROTECTED REGION ID(R-1010616390) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipologia. 
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
	public java.lang.String getTipologia() {
		return tipologia;
	}

	/*PROTECTED REGION ID(R-1647080931) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
