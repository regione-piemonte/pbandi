/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice;

////{PROTECTED REGION ID(R-1589394222) ENABLED START////}
/**
 * Inserire qui la documentazione della classe CostantiBandoLineaDTO.
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
public class CostantiBandoLineaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String normaIncentivazione = null;

	/**
	 * @generated
	 */
	public void setNormaIncentivazione(java.lang.String val) {
		normaIncentivazione = val;
	}

	////{PROTECTED REGION ID(R-821211837) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo normaIncentivazione. 
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
	public java.lang.String getNormaIncentivazione() {
		return normaIncentivazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String codiceNormativa = null;

	/**
	 * @generated
	 */
	public void setCodiceNormativa(java.lang.String val) {
		codiceNormativa = val;
	}

	////{PROTECTED REGION ID(R-2064744032) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceNormativa. 
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
	public java.lang.String getCodiceNormativa() {
		return codiceNormativa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoOperazione = null;

	/**
	 * @generated
	 */
	public void setIdTipoOperazione(java.lang.Long val) {
		idTipoOperazione = val;
	}

	////{PROTECTED REGION ID(R-404139515) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoOperazione. 
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
	public java.lang.Long getIdTipoOperazione() {
		return idTipoOperazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idStatoDomanda = null;

	/**
	 * @generated
	 */
	public void setIdStatoDomanda(java.lang.Long val) {
		idStatoDomanda = val;
	}

	////{PROTECTED REGION ID(R-1024339298) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idStatoDomanda. 
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
	public java.lang.Long getIdStatoDomanda() {
		return idStatoDomanda;
	}

	/**
	 * @generated
	 */
	private java.lang.Long progrBandoLineaIntervento = null;

	/**
	 * @generated
	 */
	public void setProgrBandoLineaIntervento(java.lang.Long val) {
		progrBandoLineaIntervento = val;
	}

	////{PROTECTED REGION ID(R-479458953) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo progrBandoLineaIntervento. 
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
	public java.lang.Long getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	/**
	 * @generated
	 */
	private java.lang.Integer statoInvioDomanda = null;

	/**
	 * @generated
	 */
	public void setStatoInvioDomanda(java.lang.Integer val) {
		statoInvioDomanda = val;
	}

	////{PROTECTED REGION ID(R1358721662) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo statoInvioDomanda. 
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
	public java.lang.Integer getStatoInvioDomanda() {
		return statoInvioDomanda;
	}

	/**
	 * @generated
	 */
	private java.lang.Integer statoProgetto = null;

	/**
	 * @generated
	 */
	public void setStatoProgetto(java.lang.Integer val) {
		statoProgetto = val;
	}

	////{PROTECTED REGION ID(R-1458991123) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo statoProgetto. 
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
	public java.lang.Integer getStatoProgetto() {
		return statoProgetto;
	}

	/**
	 * @generated
	 */
	private java.lang.Integer statoContoEconomico = null;

	/**
	 * @generated
	 */
	public void setStatoContoEconomico(java.lang.Integer val) {
		statoContoEconomico = val;
	}

	////{PROTECTED REGION ID(R84197254) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo statoContoEconomico. 
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
	public java.lang.Integer getStatoContoEconomico() {
		return statoContoEconomico;
	}

	/*PROTECTED REGION ID(R-1044811953) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
