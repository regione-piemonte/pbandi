/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.quadroprevisionale;

////{PROTECTED REGION ID(R-212376336) ENABLED START////}
/**
 * Inserire qui la documentazione della classe QuadroPrevisionaleComplessivoDTO.
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
public class QuadroPrevisionaleComplessivoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Double preventivo = null;

	/**
	 * @generated
	 */
	public void setPreventivo(java.lang.Double val) {
		preventivo = val;
	}

	////{PROTECTED REGION ID(R-704590598) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo preventivo. 
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
	public java.lang.Double getPreventivo() {
		return preventivo;
	}

	/**
	 * @generated
	 */
	private java.lang.Double spesaAmmessa = null;

	/**
	 * @generated
	 */
	public void setSpesaAmmessa(java.lang.Double val) {
		spesaAmmessa = val;
	}

	////{PROTECTED REGION ID(R-1849595177) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo spesaAmmessa. 
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
	public java.lang.Double getSpesaAmmessa() {
		return spesaAmmessa;
	}

	/**
	 * @generated
	 */
	private java.lang.Double realizzato = null;

	/**
	 * @generated
	 */
	public void setRealizzato(java.lang.Double val) {
		realizzato = val;
	}

	////{PROTECTED REGION ID(R-1911152479) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo realizzato. 
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
	public java.lang.Double getRealizzato() {
		return realizzato;
	}

	/**
	 * @generated
	 */
	private java.lang.Double daRealizzare = null;

	/**
	 * @generated
	 */
	public void setDaRealizzare(java.lang.Double val) {
		daRealizzare = val;
	}

	////{PROTECTED REGION ID(R72996022) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo daRealizzare. 
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
	public java.lang.Double getDaRealizzare() {
		return daRealizzare;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idVoce = null;

	/**
	 * @generated
	 */
	public void setIdVoce(java.lang.Long val) {
		idVoce = val;
	}

	////{PROTECTED REGION ID(R367705478) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idVoce. 
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
	public java.lang.Long getIdVoce() {
		return idVoce;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idVocePadre = null;

	/**
	 * @generated
	 */
	public void setIdVocePadre(java.lang.Long val) {
		idVocePadre = val;
	}

	////{PROTECTED REGION ID(R2038545440) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idVocePadre. 
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
	public java.lang.Long getIdVocePadre() {
		return idVocePadre;
	}

	/**
	 * @generated
	 */
	private java.lang.String descVoce = null;

	/**
	 * @generated
	 */
	public void setDescVoce(java.lang.String val) {
		descVoce = val;
	}

	////{PROTECTED REGION ID(R-1535145988) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descVoce. 
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
	public java.lang.String getDescVoce() {
		return descVoce;
	}

	/**
	 * @generated
	 */
	private boolean macroVoce = true;

	/**
	 * @generated
	 */
	public void setMacroVoce(boolean val) {
		macroVoce = val;
	}

	////{PROTECTED REGION ID(R1323221975) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo macroVoce. 
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
	public boolean getMacroVoce() {
		return macroVoce;
	}

	/**
	 * @generated
	 */
	private boolean haFigli = true;

	/**
	 * @generated
	 */
	public void setHaFigli(boolean val) {
		haFigli = val;
	}

	////{PROTECTED REGION ID(R1820593176) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo haFigli. 
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
	public boolean getHaFigli() {
		return haFigli;
	}

	/*PROTECTED REGION ID(R22148657) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
