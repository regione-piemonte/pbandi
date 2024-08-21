/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti;

////{PROTECTED REGION ID(R1277140134) ENABLED START////}
/**
 * Inserire qui la documentazione della classe FiltroTrasferimentoDTO.
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
public class FiltroTrasferimentoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idTrasferimento = null;

	/**
	 * @generated
	 */
	public void setIdTrasferimento(java.lang.Long val) {
		idTrasferimento = val;
	}

	////{PROTECTED REGION ID(R2116627818) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTrasferimento. 
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
	public java.lang.Long getIdTrasferimento() {
		return idTrasferimento;
	}

	/**
	 * @generated
	 */
	private java.lang.String codiceTrasferimento = null;

	/**
	 * @generated
	 */
	public void setCodiceTrasferimento(java.lang.String val) {
		codiceTrasferimento = val;
	}

	////{PROTECTED REGION ID(R-780194190) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceTrasferimento. 
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
	public java.lang.String getCodiceTrasferimento() {
		return codiceTrasferimento;
	}

	/**
	 * @generated
	 */
	private java.util.Date daDataTrasferimento = null;

	/**
	 * @generated
	 */
	public void setDaDataTrasferimento(java.util.Date val) {
		daDataTrasferimento = val;
	}

	////{PROTECTED REGION ID(R763325246) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo daDataTrasferimento. 
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
	public java.util.Date getDaDataTrasferimento() {
		return daDataTrasferimento;
	}

	/**
	 * @generated
	 */
	private java.util.Date aDataTrasferimento = null;

	/**
	 * @generated
	 */
	public void setADataTrasferimento(java.util.Date val) {
		aDataTrasferimento = val;
	}

	////{PROTECTED REGION ID(R776557926) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo aDataTrasferimento. 
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
	public java.util.Date getADataTrasferimento() {
		return aDataTrasferimento;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idCausaleTrasferimento = null;

	/**
	 * @generated
	 */
	public void setIdCausaleTrasferimento(java.lang.Long val) {
		idCausaleTrasferimento = val;
	}

	////{PROTECTED REGION ID(R-1795282418) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idCausaleTrasferimento. 
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
	public java.lang.Long getIdCausaleTrasferimento() {
		return idCausaleTrasferimento;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idSoggettoBeneficiario = null;

	/**
	 * @generated
	 */
	public void setIdSoggettoBeneficiario(java.lang.Long val) {
		idSoggettoBeneficiario = val;
	}

	////{PROTECTED REGION ID(R-1991150879) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSoggettoBeneficiario. 
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
	public java.lang.Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagPubblicoPrivato = null;

	/**
	 * @generated
	 */
	public void setFlagPubblicoPrivato(java.lang.String val) {
		flagPubblicoPrivato = val;
	}

	////{PROTECTED REGION ID(R1876276493) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagPubblicoPrivato. 
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
	
	private Long idLineaDiIntervento;
	
	public Long getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(Long idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	////{PROTECTED REGION END////}
	public java.lang.String getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}

	/*PROTECTED REGION ID(R663420581) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
