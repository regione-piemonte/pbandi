/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice;

////{PROTECTED REGION ID(R2141850447) ENABLED START////}
/**
 * Inserire qui la documentazione della classe PbandiDMateriaDTO.
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
public class PbandiDMateriaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.util.Date dtFineValidita = null;

	/**
	 * @generated
	 */
	public void setDtFineValidita(java.util.Date val) {
		dtFineValidita = val;
	}

	////{PROTECTED REGION ID(R-1769679693) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtFineValidita. 
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
	public java.util.Date getDtFineValidita() {
		return dtFineValidita;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idMateria = null;

	/**
	 * @generated
	 */
	public void setIdMateria(java.lang.Long val) {
		idMateria = val;
	}

	////{PROTECTED REGION ID(R58424283) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idMateria. 
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
	public java.lang.Long getIdMateria() {
		return idMateria;
	}

	/**
	 * @generated
	 */
	private java.lang.String descMateria = null;

	/**
	 * @generated
	 */
	public void setDescMateria(java.lang.String val) {
		descMateria = val;
	}

	////{PROTECTED REGION ID(R-629343899) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descMateria. 
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
	public java.lang.String getDescMateria() {
		return descMateria;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtInizioValidita = null;

	/**
	 * @generated
	 */
	public void setDtInizioValidita(java.util.Date val) {
		dtInizioValidita = val;
	}

	////{PROTECTED REGION ID(R492490229) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtInizioValidita. 
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
	public java.util.Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveMateria = null;

	/**
	 * @generated
	 */
	public void setDescBreveMateria(java.lang.String val) {
		descBreveMateria = val;
	}

	////{PROTECTED REGION ID(R-204462527) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveMateria. 
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
	public java.lang.String getDescBreveMateria() {
		return descBreveMateria;
	}

	/*PROTECTED REGION ID(R-315610318) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
