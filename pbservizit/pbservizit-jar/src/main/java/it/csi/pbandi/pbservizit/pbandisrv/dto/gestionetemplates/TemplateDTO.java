/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.gestionetemplates;

////{PROTECTED REGION ID(R1721105364) ENABLED START////}
/**
 * Inserire qui la documentazione della classe TemplateDTO.
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
public class TemplateDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idTemplate = null;

	/**
	 * @generated
	 */
	public void setIdTemplate(java.lang.Long val) {
		idTemplate = val;
	}

	////{PROTECTED REGION ID(R1256994185) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTemplate. 
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
	public java.lang.Long getIdTemplate() {
		return idTemplate;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoTemplate = null;

	/**
	 * @generated
	 */
	public void setIdTipoTemplate(java.lang.Long val) {
		idTipoTemplate = val;
	}

	////{PROTECTED REGION ID(R-1898489507) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoTemplate. 
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
	public java.lang.Long getIdTipoTemplate() {
		return idTipoTemplate;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoDocumentoIndex = null;

	/**
	 * @generated
	 */
	public void setIdTipoDocumentoIndex(java.lang.Long val) {
		idTipoDocumentoIndex = val;
	}

	////{PROTECTED REGION ID(R-1815048927) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoDocumentoIndex. 
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
	public java.lang.Long getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}

	/**
	 * @generated
	 */
	private java.lang.Long progrBandolineaIntervento = null;

	/**
	 * @generated
	 */
	public void setProgrBandolineaIntervento(java.lang.Long val) {
		progrBandolineaIntervento = val;
	}

	////{PROTECTED REGION ID(R1314234581) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo progrBandolineaIntervento. 
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
	public java.lang.Long getProgrBandolineaIntervento() {
		return progrBandolineaIntervento;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveTipoDocIndex = null;

	/**
	 * @generated
	 */
	public void setDescBreveTipoDocIndex(java.lang.String val) {
		descBreveTipoDocIndex = val;
	}

	////{PROTECTED REGION ID(R314542477) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveTipoDocIndex. 
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
	public java.lang.String getDescBreveTipoDocIndex() {
		return descBreveTipoDocIndex;
	}

	/**
	 * @generated
	 */
	private java.lang.String descTipoDocIndex = null;

	/**
	 * @generated
	 */
	public void setDescTipoDocIndex(java.lang.String val) {
		descTipoDocIndex = val;
	}

	////{PROTECTED REGION ID(R812994259) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipoDocIndex. 
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
	public java.lang.String getDescTipoDocIndex() {
		return descTipoDocIndex;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveTipoTemplate = null;

	/**
	 * @generated
	 */
	public void setDescBreveTipoTemplate(java.lang.String val) {
		descBreveTipoTemplate = val;
	}

	////{PROTECTED REGION ID(R-1828118643) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveTipoTemplate. 
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
	public java.lang.String getDescBreveTipoTemplate() {
		return descBreveTipoTemplate;
	}

	/**
	 * @generated
	 */
	private java.lang.String nomeBandolinea = null;

	/**
	 * @generated
	 */
	public void setNomeBandolinea(java.lang.String val) {
		nomeBandolinea = val;
	}

	////{PROTECTED REGION ID(R-20185312) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo nomeBandolinea. 
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
	public java.lang.String getNomeBandolinea() {
		return nomeBandolinea;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtInserimento = null;

	/**
	 * @generated
	 */
	public void setDtInserimento(java.util.Date val) {
		dtInserimento = val;
	}

	////{PROTECTED REGION ID(R651120351) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtInserimento. 
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
	public java.util.Date getDtInserimento() {
		return dtInserimento;
	}

	/*PROTECTED REGION ID(R456684663) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
