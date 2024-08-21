/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.revoca;

////{PROTECTED REGION ID(R-1418310901) ENABLED START////}
/**
 * Inserire qui la documentazione della classe CausaleErogazioneDTO.
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
public class CausaleErogazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Double importoErogazione = null;

	/**
	 * @generated
	 */
	public void setImportoErogazione(java.lang.Double val) {
		importoErogazione = val;
	}

	////{PROTECTED REGION ID(R-1632142854) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoErogazione. 
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
	public java.lang.Double getImportoErogazione() {
		return importoErogazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveCausale = null;

	/**
	 * @generated
	 */
	public void setDescBreveCausale(java.lang.String val) {
		descBreveCausale = val;
	}

	////{PROTECTED REGION ID(R18043574) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveCausale. 
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
	public java.lang.String getDescBreveCausale() {
		return descBreveCausale;
	}

	/**
	 * @generated
	 */
	private java.lang.String descCausale = null;

	/**
	 * @generated
	 */
	public void setDescCausale(java.lang.String val) {
		descCausale = val;
	}

	////{PROTECTED REGION ID(R-462507806) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descCausale. 
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
	public java.lang.String getDescCausale() {
		return descCausale;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtContabile = null;

	/**
	 * @generated
	 */
	public void setDtContabile(java.util.Date val) {
		dtContabile = val;
	}

	////{PROTECTED REGION ID(R1215050068) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtContabile. 
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
	public java.util.Date getDtContabile() {
		return dtContabile;
	}

	/**
	 * @generated
	 */
	private java.lang.String codRiferimentoErogazione = null;

	/**
	 * @generated
	 */
	public void setCodRiferimentoErogazione(java.lang.String val) {
		codRiferimentoErogazione = val;
	}

	////{PROTECTED REGION ID(R-376129486) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codRiferimentoErogazione. 
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
	public java.lang.String getCodRiferimentoErogazione() {
		return codRiferimentoErogazione;
	}

	/*PROTECTED REGION ID(R742471606) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
