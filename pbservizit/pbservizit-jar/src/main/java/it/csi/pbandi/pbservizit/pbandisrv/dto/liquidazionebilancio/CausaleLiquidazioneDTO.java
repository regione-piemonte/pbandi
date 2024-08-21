/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio;

////{PROTECTED REGION ID(R-905134600) ENABLED START////}
/**
 * Inserire qui la documentazione della classe CausaleLiquidazioneDTO.
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
public class CausaleLiquidazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idCausaleErogazione = null;

	/**
	 * @generated
	 */
	public void setIdCausaleErogazione(java.lang.Long val) {
		idCausaleErogazione = val;
	}

	////{PROTECTED REGION ID(R1573597958) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idCausaleErogazione. 
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
	public java.lang.Long getIdCausaleErogazione() {
		return idCausaleErogazione;
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

	////{PROTECTED REGION ID(R657659861) ENABLED START////}
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
	private java.lang.String descBreveCausale = null;

	/**
	 * @generated
	 */
	public void setDescBreveCausale(java.lang.String val) {
		descBreveCausale = val;
	}

	////{PROTECTED REGION ID(R-2050471005) ENABLED START////}
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
	private java.lang.Long idAttoLiquidazione = null;

	/**
	 * @generated
	 */
	public void setIdAttoLiquidazione(java.lang.Long val) {
		idAttoLiquidazione = val;
	}

	////{PROTECTED REGION ID(R-1389321837) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idAttoLiquidazione. 
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
	public java.lang.Long getIdAttoLiquidazione() {
		return idAttoLiquidazione;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtEmissioneAtto = null;

	/**
	 * @generated
	 */
	public void setDtEmissioneAtto(java.util.Date val) {
		dtEmissioneAtto = val;
	}

	////{PROTECTED REGION ID(R1255496484) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtEmissioneAtto. 
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
	public java.util.Date getDtEmissioneAtto() {
		return dtEmissioneAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.String numeroAtto = null;

	/**
	 * @generated
	 */
	public void setNumeroAtto(java.lang.String val) {
		numeroAtto = val;
	}

	////{PROTECTED REGION ID(R1574989858) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroAtto. 
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
	public java.lang.String getNumeroAtto() {
		return numeroAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoLiquidatoAtto = null;

	/**
	 * @generated
	 */
	public void setImportoLiquidatoAtto(java.lang.Double val) {
		importoLiquidatoAtto = val;
	}

	////{PROTECTED REGION ID(R-1541164388) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoLiquidatoAtto. 
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
	public java.lang.Double getImportoLiquidatoAtto() {
		return importoLiquidatoAtto;
	}

	/*PROTECTED REGION ID(R-1006914797) ENABLED START*/

	/*PROTECTED REGION END*/
}
