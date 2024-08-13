/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione;

////{PROTECTED REGION ID(R1287090748) ENABLED START////}
/**
 * Inserire qui la documentazione della classe NotaDiCreditoDTO.
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
public class NotaDiCreditoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String numeroDocumento = null;

	/**
	 * @generated
	 */
	public void setNumeroDocumento(java.lang.String val) {
		numeroDocumento = val;
	}

	////{PROTECTED REGION ID(R-129353668) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroDocumento. 
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
	public java.lang.String getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtDocumento = null;

	/**
	 * @generated
	 */
	public void setDtDocumento(java.util.Date val) {
		dtDocumento = val;
	}

	////{PROTECTED REGION ID(R-562259896) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtDocumento. 
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
	public java.util.Date getDtDocumento() {
		return dtDocumento;
	}

	/**
	 * @generated
	 */
	private java.lang.String statoDocumento = null;

	/**
	 * @generated
	 */
	public void setStatoDocumento(java.lang.String val) {
		statoDocumento = val;
	}

	////{PROTECTED REGION ID(R-1934785131) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo statoDocumento. 
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
	public java.lang.String getStatoDocumento() {
		return statoDocumento;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoDocumento = null;

	/**
	 * @generated
	 */
	public void setImportoDocumento(java.lang.Double val) {
		importoDocumento = val;
	}

	////{PROTECTED REGION ID(R247456422) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoDocumento. 
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
	public java.lang.Double getImportoDocumento() {
		return importoDocumento;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO[] vociDiSpesa = null;

	/**
	 * @generated
	 */
	public void setVociDiSpesa(
			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO[] val) {
		vociDiSpesa = val;
	}

	////{PROTECTED REGION ID(R-1738572074) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo vociDiSpesa. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO[] getVociDiSpesa() {
		return vociDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idNotaDiCredito = null;

	/**
	 * @generated
	 */
	public void setIdNotaDiCredito(java.lang.Long val) {
		idNotaDiCredito = val;
	}

	////{PROTECTED REGION ID(R1585471724) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idNotaDiCredito. 
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
	public java.lang.Long getIdNotaDiCredito() {
		return idNotaDiCredito;
	}

	/*PROTECTED REGION ID(R-1362530225) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
