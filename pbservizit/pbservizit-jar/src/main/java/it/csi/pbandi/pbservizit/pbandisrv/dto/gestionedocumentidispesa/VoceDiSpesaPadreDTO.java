/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedocumentidispesa;

////{PROTECTED REGION ID(R-623879942) ENABLED START////}
/**
 * Inserire qui la documentazione della classe VoceDiSpesaPadreDTO.
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
public class VoceDiSpesaPadreDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idVoceDiSpesa = null;

	/**
	 * @generated
	 */
	public void setIdVoceDiSpesa(java.lang.Long val) {
		idVoceDiSpesa = val;
	}

	////{PROTECTED REGION ID(R-464767935) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idVoceDiSpesa. 
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
	public java.lang.Long getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String descVoceDiSpesa = null;

	/**
	 * @generated
	 */
	public void setDescVoceDiSpesa(java.lang.String val) {
		descVoceDiSpesa = val;
	}

	////{PROTECTED REGION ID(R-188701813) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descVoceDiSpesa. 
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
	public java.lang.String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedocumentidispesa.CodiceDescrizioneDTO[] vociDiSpesaFiglie = null;

	/**
	 * @generated
	 */
	public void setVociDiSpesaFiglie(
			it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedocumentidispesa.CodiceDescrizioneDTO[] val) {
		vociDiSpesaFiglie = val;
	}

	////{PROTECTED REGION ID(R-159414404) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo vociDiSpesaFiglie. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedocumentidispesa.CodiceDescrizioneDTO[] getVociDiSpesaFiglie() {
		return vociDiSpesaFiglie;
	}

	/*PROTECTED REGION ID(R-2088836633) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
