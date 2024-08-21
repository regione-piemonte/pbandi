/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico;

////{PROTECTED REGION ID(R581984437) ENABLED START////}
/**
 * Inserire qui la documentazione della classe EsitoFindQuadroPrevisionaleDTO.
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
public class EsitoFindQuadroPrevisionaleDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private boolean isVociVisibili = true;

	/**
	 * @generated
	 */
	public void setIsVociVisibili(boolean val) {
		isVociVisibili = val;
	}

	////{PROTECTED REGION ID(R387181579) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isVociVisibili. 
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
	public boolean getIsVociVisibili() {
		return isVociVisibili;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.QuadroPrevisionaleDTO quadroPrevisionale = null;

	/**
	 * @generated
	 */
	public void setQuadroPrevisionale(
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.QuadroPrevisionaleDTO val) {
		quadroPrevisionale = val;
	}

	////{PROTECTED REGION ID(R-561410360) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo quadroPrevisionale. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.QuadroPrevisionaleDTO getQuadroPrevisionale() {
		return quadroPrevisionale;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataUltimoPreventivo = null;

	/**
	 * @generated
	 */
	public void setDataUltimoPreventivo(java.util.Date val) {
		dataUltimoPreventivo = val;
	}

	////{PROTECTED REGION ID(R2016309399) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataUltimoPreventivo. 
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
	public java.util.Date getDataUltimoPreventivo() {
		return dataUltimoPreventivo;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataUltimaSpesaAmmessa = null;

	/**
	 * @generated
	 */
	public void setDataUltimaSpesaAmmessa(java.util.Date val) {
		dataUltimaSpesaAmmessa = val;
	}

	////{PROTECTED REGION ID(R1407860774) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataUltimaSpesaAmmessa. 
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
	public java.util.Date getDataUltimaSpesaAmmessa() {
		return dataUltimaSpesaAmmessa;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.QuadroPrevisionaleComplessivoDTO[] quadroPrevisionaleComplessivo = null;

	/**
	 * @generated
	 */
	public void setQuadroPrevisionaleComplessivo(
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.QuadroPrevisionaleComplessivoDTO[] val) {
		quadroPrevisionaleComplessivo = val;
	}

	////{PROTECTED REGION ID(R-373718318) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo quadroPrevisionaleComplessivo. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.QuadroPrevisionaleComplessivoDTO[] getQuadroPrevisionaleComplessivo() {
		return quadroPrevisionaleComplessivo;
	}

	/**
	 * @generated
	 */
	private boolean isControlloNuovoImportoBloccante = true;

	/**
	 * @generated
	 */
	public void setIsControlloNuovoImportoBloccante(boolean val) {
		isControlloNuovoImportoBloccante = val;
	}

	////{PROTECTED REGION ID(R-477089223) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isControlloNuovoImportoBloccante. 
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
	public boolean getIsControlloNuovoImportoBloccante() {
		return isControlloNuovoImportoBloccante;
	}

	/*PROTECTED REGION ID(R1655158028) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
