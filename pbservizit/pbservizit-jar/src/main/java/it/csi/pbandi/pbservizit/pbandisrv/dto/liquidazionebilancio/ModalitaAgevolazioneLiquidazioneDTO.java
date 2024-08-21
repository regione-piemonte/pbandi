/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio;

import java.util.Arrays;

////{PROTECTED REGION ID(R48746257) ENABLED START////}
/**
 * Inserire qui la documentazione della classe ModalitaAgevolazioneLiquidazioneDTO.
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
public class ModalitaAgevolazioneLiquidazioneDTO
		implements
			java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.CausaleLiquidazioneDTO[] causaliLiquidazioni = null;

	/**
	 * @generated
	 */
	public void setCausaliLiquidazioni(
			it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.CausaleLiquidazioneDTO[] val) {
		causaliLiquidazioni = val;
	}

	////{PROTECTED REGION ID(R-1349912473) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo causaliLiquidazioni. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.CausaleLiquidazioneDTO[] getCausaliLiquidazioni() {
		return causaliLiquidazioni;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idModalitaAgevolazione = null;

	/**
	 * @generated
	 */
	public void setIdModalitaAgevolazione(java.lang.Long val) {
		idModalitaAgevolazione = val;
	}

	////{PROTECTED REGION ID(R2011257087) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idModalitaAgevolazione. 
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
	public java.lang.Long getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String descModalitaAgevolazione = null;

	/**
	 * @generated
	 */
	public void setDescModalitaAgevolazione(java.lang.String val) {
		descModalitaAgevolazione = val;
	}

	////{PROTECTED REGION ID(R-500096203) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descModalitaAgevolazione. 
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
	public java.lang.String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveModalitaAgevolaz = null;

	/**
	 * @generated
	 */
	public void setDescBreveModalitaAgevolaz(java.lang.String val) {
		descBreveModalitaAgevolaz = val;
	}

	////{PROTECTED REGION ID(R1865301048) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveModalitaAgevolaz. 
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
	public java.lang.String getDescBreveModalitaAgevolaz() {
		return descBreveModalitaAgevolaz;
	}

	/**
	 * @generated
	 */
	private java.lang.Double quotaImportoAgevolato = null;

	/**
	 * @generated
	 */
	public void setQuotaImportoAgevolato(java.lang.Double val) {
		quotaImportoAgevolato = val;
	}

	////{PROTECTED REGION ID(R-1325483899) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo quotaImportoAgevolato. 
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
	public java.lang.Double getQuotaImportoAgevolato() {
		return quotaImportoAgevolato;
	}

	/**
	 * @generated
	 */
	private java.lang.Double totaleImportoLiquidato = null;

	/**
	 * @generated
	 */
	public void setTotaleImportoLiquidato(java.lang.Double val) {
		totaleImportoLiquidato = val;
	}

	////{PROTECTED REGION ID(R1251490216) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo totaleImportoLiquidato. 
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
	
	@Override
	public String toString() {
		return "ModalitaAgevolazioneLiquidazioneDTO [causaliLiquidazioni=" + Arrays.toString(causaliLiquidazioni)
				+ ", idModalitaAgevolazione=" + idModalitaAgevolazione + ", descModalitaAgevolazione="
				+ descModalitaAgevolazione + ", descBreveModalitaAgevolaz=" + descBreveModalitaAgevolaz
				+ ", quotaImportoAgevolato=" + quotaImportoAgevolato + ", totaleImportoLiquidato="
				+ totaleImportoLiquidato + "]";
	}

	////{PROTECTED REGION END////}
	public java.lang.Double getTotaleImportoLiquidato() {
		return totaleImportoLiquidato;
	}

	
	/*PROTECTED REGION ID(R241701040) ENABLED START*/

	/*PROTECTED REGION END*/
}
