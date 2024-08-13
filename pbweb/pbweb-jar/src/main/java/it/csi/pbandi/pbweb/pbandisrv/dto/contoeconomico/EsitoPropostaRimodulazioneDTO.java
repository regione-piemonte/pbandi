/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico;

////{PROTECTED REGION ID(R-20289127) ENABLED START////}
/**
 * Inserire qui la documentazione della classe EsitoPropostaRimodulazioneDTO.
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
public class EsitoPropostaRimodulazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO contoEconomico = null;

	/**
	 * @generated
	 */
	public void setContoEconomico(
			it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO val) {
		contoEconomico = val;
	}

	////{PROTECTED REGION ID(R1371696630) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo contoEconomico. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO getContoEconomico() {
		return contoEconomico;
	}

	/**
	 * @generated
	 */
	private boolean locked = true;

	/**
	 * @generated
	 */
	public void setLocked(boolean val) {
		locked = val;
	}

	////{PROTECTED REGION ID(R164054499) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo locked. 
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
	public boolean getLocked() {
		return locked;
	}

	/*PROTECTED REGION ID(R454556114) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
