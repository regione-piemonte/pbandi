/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico;

////{PROTECTED REGION ID(R-1702084895) ENABLED START////}
/**
 * Inserire qui la documentazione della classe EsitoSalvaQuadroPrevisionaleDTO.
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
public class EsitoSalvaQuadroPrevisionaleDTO implements java.io.Serializable {
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

	////{PROTECTED REGION ID(R-343306953) ENABLED START////}
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
	private it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.QuadroPrevisionaleDTO quadroPrevisionale = null;

	/**
	 * @generated
	 */
	public void setQuadroPrevisionale(
			it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.QuadroPrevisionaleDTO val) {
		quadroPrevisionale = val;
	}

	////{PROTECTED REGION ID(R1337113076) ENABLED START////}
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.QuadroPrevisionaleDTO getQuadroPrevisionale() {
		return quadroPrevisionale;
	}

	/*PROTECTED REGION ID(R-1945351606) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
