/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio;

////{PROTECTED REGION ID(R1023994583) ENABLED START////}
/**
 * Inserire qui la documentazione della classe FormaGiuridicaDTO.
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
public class FormaGiuridicaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String codFormaGiuridica = null;

	/**
	 * @generated
	 */
	public void setCodFormaGiuridica(java.lang.String val) {
		codFormaGiuridica = val;
	}

	////{PROTECTED REGION ID(R1017498097) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codFormaGiuridica. 
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
	public java.lang.String getCodFormaGiuridica() {
		return codFormaGiuridica;
	}

	/**
	 * @generated
	 */
	private java.lang.String codGefo = null;

	/**
	 * @generated
	 */
	public void setCodGefo(java.lang.String val) {
		codGefo = val;
	}

	////{PROTECTED REGION ID(R-751937272) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codGefo. 
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
	public java.lang.String getCodGefo() {
		return codGefo;
	}

	/**
	 * @generated
	 */
	private java.lang.String descFormaGiuridica = null;

	/**
	 * @generated
	 */
	public void setDescFormaGiuridica(java.lang.String val) {
		descFormaGiuridica = val;
	}

	////{PROTECTED REGION ID(R1924082776) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descFormaGiuridica. 
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
	public java.lang.String getDescFormaGiuridica() {
		return descFormaGiuridica;
	}

	/**
	 * @generated
	 */
	private java.lang.String divisione = null;

	/**
	 * @generated
	 */
	public void setDivisione(java.lang.String val) {
		divisione = val;
	}

	////{PROTECTED REGION ID(R-655016863) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo divisione. 
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
	public java.lang.String getDivisione() {
		return divisione;
	}

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

	////{PROTECTED REGION ID(R1971447355) ENABLED START////}
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
	private java.util.Date dtInizioValidita = null;

	/**
	 * @generated
	 */
	public void setDtInizioValidita(java.util.Date val) {
		dtInizioValidita = val;
	}

	////{PROTECTED REGION ID(R827956605) ENABLED START////}
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
	private java.lang.Long idFormaGiuridica = null;

	/**
	 * @generated
	 */
	public void setIdFormaGiuridica(java.lang.Long val) {
		idFormaGiuridica = val;
	}

	////{PROTECTED REGION ID(R251321122) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idFormaGiuridica. 
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
	public java.lang.Long getIdFormaGiuridica() {
		return idFormaGiuridica;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagPrivato = null;

	/**
	 * @generated
	 */
	public void setFlagPrivato(java.lang.String val) {
		flagPrivato = val;
	}

	////{PROTECTED REGION ID(R1366197578) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagPrivato. 
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
	public java.lang.String getFlagPrivato() {
		return flagPrivato;
	}

	/**
	 * @generated
	 */
	private java.lang.String sezione = null;

	/**
	 * @generated
	 */
	public void setSezione(java.lang.String val) {
		sezione = val;
	}

	////{PROTECTED REGION ID(R298268430) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo sezione. 
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
	public java.lang.String getSezione() {
		return sezione;
	}

	/*PROTECTED REGION ID(R245242132) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
