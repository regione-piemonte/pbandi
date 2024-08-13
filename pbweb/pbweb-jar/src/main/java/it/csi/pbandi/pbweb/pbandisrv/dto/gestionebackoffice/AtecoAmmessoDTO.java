/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice;

////{PROTECTED REGION ID(R1633677447) ENABLED START////}
/**
 * Inserire qui la documentazione della classe AtecoAmmessoDTO.
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
public class AtecoAmmessoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idBando = null;

	/**
	 * @generated
	 */
	public void setIdBando(java.lang.Long val) {
		idBando = val;
	}

	////{PROTECTED REGION ID(R1752397336) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idBando. 
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
	public java.lang.Long getIdBando() {
		return idBando;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idAteco = null;

	/**
	 * @generated
	 */
	public void setIdAteco(java.lang.Long val) {
		idAteco = val;
	}

	////{PROTECTED REGION ID(R1752031164) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idAteco. 
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
	public java.lang.Long getIdAteco() {
		return idAteco;
	}

	/**
	 * @generated
	 */
	private java.lang.String codAteco = null;

	/**
	 * @generated
	 */
	public void setCodAteco(java.lang.String val) {
		codAteco = val;
	}

	////{PROTECTED REGION ID(R-1260561043) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codAteco. 
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
	public java.lang.String getCodAteco() {
		return codAteco;
	}

	/**
	 * @generated
	 */
	private java.lang.String codAtecoNorm = null;

	/**
	 * @generated
	 */
	public void setCodAtecoNorm(java.lang.String val) {
		codAtecoNorm = val;
	}

	////{PROTECTED REGION ID(R587989705) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codAtecoNorm. 
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
	public java.lang.String getCodAtecoNorm() {
		return codAtecoNorm;
	}

	/**
	 * @generated
	 */
	private java.lang.String descAteco = null;

	/**
	 * @generated
	 */
	public void setDescAteco(java.lang.String val) {
		descAteco = val;
	}

	////{PROTECTED REGION ID(R-1123981882) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descAteco. 
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
	public java.lang.String getDescAteco() {
		return descAteco;
	}

	/**
	 * @generated
	 */
	private java.lang.String codSettore = null;

	/**
	 * @generated
	 */
	public void setCodSettore(java.lang.String val) {
		codSettore = val;
	}

	////{PROTECTED REGION ID(R-1838262145) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codSettore. 
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
	public java.lang.String getCodSettore() {
		return codSettore;
	}

	/**
	 * @generated
	 */
	private java.lang.String descSettore = null;

	/**
	 * @generated
	 */
	public void setDescSettore(java.lang.String val) {
		descSettore = val;
	}

	////{PROTECTED REGION ID(R565292696) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descSettore. 
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
	public java.lang.String getDescSettore() {
		return descSettore;
	}

	/*PROTECTED REGION ID(R-2136859206) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
