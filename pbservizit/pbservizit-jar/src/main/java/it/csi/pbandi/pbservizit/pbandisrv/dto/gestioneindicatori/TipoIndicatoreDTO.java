/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneindicatori;

////{PROTECTED REGION ID(R1172879123) ENABLED START////}
/**
 * Inserire qui la documentazione della classe TipoIndicatoreDTO.
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
public class TipoIndicatoreDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idTipoIndicatore = null;

	/**
	 * @generated
	 */
	public void setIdTipoIndicatore(java.lang.Long val) {
		idTipoIndicatore = val;
	}

	////{PROTECTED REGION ID(R-1352711784) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoIndicatore. 
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
	public java.lang.Long getIdTipoIndicatore() {
		return idTipoIndicatore;
	}

	/**
	 * @generated
	 */
	private java.lang.String descTipoIndicatore = null;

	/**
	 * @generated
	 */
	public void setDescTipoIndicatore(java.lang.String val) {
		descTipoIndicatore = val;
	}

	////{PROTECTED REGION ID(R-1938852914) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipoIndicatore. 
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
	public java.lang.String getDescTipoIndicatore() {
		return descTipoIndicatore;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneindicatori.IndicatoreDTO[] indicatori = null;

	/**
	 * @generated
	 */
	public void setIndicatori(
			it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneindicatori.IndicatoreDTO[] val) {
		indicatori = val;
	}

	////{PROTECTED REGION ID(R1762738061) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo indicatori. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneindicatori.IndicatoreDTO[] getIndicatori() {
		return indicatori;
	}

	/**
	 * @generated
	 */
	private boolean flagMonit = true;

	/**
	 * @generated
	 */
	public void setFlagMonit(boolean val) {
		flagMonit = val;
	}

	////{PROTECTED REGION ID(R639402872) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagMonit. 
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
	public boolean getFlagMonit() {
		return flagMonit;
	}

	/*PROTECTED REGION ID(R-1001668370) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
