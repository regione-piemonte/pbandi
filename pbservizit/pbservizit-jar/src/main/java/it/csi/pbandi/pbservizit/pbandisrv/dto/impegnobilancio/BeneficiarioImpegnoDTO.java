/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio;

////{PROTECTED REGION ID(R-894067412) ENABLED START////}
/**
 * Inserire qui la documentazione della classe BeneficiarioImpegnoDTO.
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
public class BeneficiarioImpegnoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String descBreveTipologia = null;

	/**
	 * @generated
	 */
	public void setDescBreveTipologia(java.lang.String val) {
		descBreveTipologia = val;
	}

	////{PROTECTED REGION ID(R-1775396703) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveTipologia. 
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
	public java.lang.String getDescBreveTipologia() {
		return descBreveTipologia;
	}

	/**
	 * @generated
	 */
	private java.lang.String ragioneSociale = null;

	/**
	 * @generated
	 */
	public void setRagioneSociale(java.lang.String val) {
		ragioneSociale = val;
	}

	////{PROTECTED REGION ID(R290397071) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo ragioneSociale. 
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
	public java.lang.String getRagioneSociale() {
		return ragioneSociale;
	}

	/**
	 * @generated
	 */
	private java.lang.String codice = null;

	/**
	 * @generated
	 */
	public void setCodice(java.lang.String val) {
		codice = val;
	}

	////{PROTECTED REGION ID(R672730687) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codice. 
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
	public java.lang.String getCodice() {
		return codice;
	}

	/**
	 * @generated
	 */
	private java.lang.String descTipologia = null;

	/**
	 * @generated
	 */
	public void setDescTipologia(java.lang.String val) {
		descTipologia = val;
	}

	////{PROTECTED REGION ID(R-1817076885) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipologia. 
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
	public java.lang.String getDescTipologia() {
		return descTipologia;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagPersonaFisica = null;

	/**
	 * @generated
	 */
	public void setFlagPersonaFisica(java.lang.String val) {
		flagPersonaFisica = val;
	}

	////{PROTECTED REGION ID(R-499475573) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagPersonaFisica. 
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
	public java.lang.String getFlagPersonaFisica() {
		return flagPersonaFisica;
	}

	/**
	 * @generated
	 */
	private java.lang.String codiceFiscale = null;

	/**
	 * @generated
	 */
	public void setCodiceFiscale(java.lang.String val) {
		codiceFiscale = val;
	}

	////{PROTECTED REGION ID(R1263096616) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceFiscale. 
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
	public java.lang.String getCodiceFiscale() {
		return codiceFiscale;
	}

	/**
	 * @generated
	 */
	private java.lang.String partitaIva = null;

	/**
	 * @generated
	 */
	public void setPartitaIva(java.lang.String val) {
		partitaIva = val;
	}

	////{PROTECTED REGION ID(R-2016858051) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo partitaIva. 
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
	public java.lang.String getPartitaIva() {
		return partitaIva;
	}

	/*PROTECTED REGION ID(R-649877643) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
