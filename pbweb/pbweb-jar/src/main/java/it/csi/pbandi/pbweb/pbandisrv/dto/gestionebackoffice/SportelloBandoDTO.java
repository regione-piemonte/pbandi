/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice;

////{PROTECTED REGION ID(R-767944776) ENABLED START////}
/**
 * Inserire qui la documentazione della classe SportelloBandoDTO.
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
public class SportelloBandoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idSportelloBando = null;

	/**
	 * @generated
	 */
	public void setIdSportelloBando(java.lang.Long val) {
		idSportelloBando = val;
	}

	////{PROTECTED REGION ID(R1200056143) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSportelloBando. 
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
	public java.lang.Long getIdSportelloBando() {
		return idSportelloBando;
	}

	/**
	 * @generated
	 */
	private java.lang.String numAtto = null;

	/**
	 * @generated
	 */
	public void setNumAtto(java.lang.String val) {
		numAtto = val;
	}

	////{PROTECTED REGION ID(R-2045112356) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numAtto. 
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
	public java.lang.String getNumAtto() {
		return numAtto;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtAtto = null;

	/**
	 * @generated
	 */
	public void setDtAtto(java.util.Date val) {
		dtAtto = val;
	}

	////{PROTECTED REGION ID(R615383126) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtAtto. 
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
	public java.util.Date getDtAtto() {
		return dtAtto;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtApertura = null;

	/**
	 * @generated
	 */
	public void setDtApertura(java.util.Date val) {
		dtApertura = val;
	}

	////{PROTECTED REGION ID(R899223028) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtApertura. 
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
	public java.util.Date getDtApertura() {
		return dtApertura;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtChiusura = null;

	/**
	 * @generated
	 */
	public void setDtChiusura(java.util.Date val) {
		dtChiusura = val;
	}

	////{PROTECTED REGION ID(R1697038922) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtChiusura. 
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
	public java.util.Date getDtChiusura() {
		return dtChiusura;
	}

	/**
	 * @generated
	 */
	private java.lang.Integer numMaxDomande = null;

	/**
	 * @generated
	 */
	public void setNumMaxDomande(java.lang.Integer val) {
		numMaxDomande = val;
	}

	////{PROTECTED REGION ID(R-256165094) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numMaxDomande. 
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
	public java.lang.Integer getNumMaxDomande() {
		return numMaxDomande;
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

	////{PROTECTED REGION ID(R-652827311) ENABLED START////}
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

	/*PROTECTED REGION ID(R-2012378071) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
