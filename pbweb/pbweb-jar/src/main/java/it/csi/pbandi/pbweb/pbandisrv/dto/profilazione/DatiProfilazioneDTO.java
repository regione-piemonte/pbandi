/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.profilazione;

////{PROTECTED REGION ID(R1415113262) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DatiProfilazioneDTO.
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
public class DatiProfilazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String[] _vociMenu = null;

	/**
	 * @generated
	 */
	public void setVociMenu(java.lang.String[] val) {
		_vociMenu = val;
	}

	////{PROTECTED REGION ID(R781528844) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo vociMenu. 
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
	public java.lang.String[] getVociMenu() {
		return _vociMenu;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.profilazione.AzioneComponenteGUI[] _azioniComponentiGUI = null;

	/**
	 * @generated
	 */
	public void setAzioniComponentiGUI(
			it.csi.pbandi.pbweb.pbandisrv.dto.profilazione.AzioneComponenteGUI[] val) {
		_azioniComponentiGUI = val;
	}

	////{PROTECTED REGION ID(R-1102243161) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo azioniComponentiGUI. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.profilazione.AzioneComponenteGUI[] getAzioniComponentiGUI() {
		return _azioniComponentiGUI;
	}

}
