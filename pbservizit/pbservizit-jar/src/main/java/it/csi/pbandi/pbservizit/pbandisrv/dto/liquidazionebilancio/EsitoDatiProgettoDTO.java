/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio;

import java.util.Arrays;

////{PROTECTED REGION ID(R-732978212) ENABLED START////}
/**
 * Inserire qui la documentazione della classe EsitoDatiProgettoDTO.
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
public class EsitoDatiProgettoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.SpesaProgettoDTO spesaProgetto = null;

	/**
	 * @generated
	 */
	public void setSpesaProgetto(
			it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.SpesaProgettoDTO val) {
		spesaProgetto = val;
	}

	////{PROTECTED REGION ID(R703505278) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo spesaProgetto. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.SpesaProgettoDTO getSpesaProgetto() {
		return spesaProgetto;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.FideiussioneDTO[] fideiussioni = null;

	/**
	 * @generated
	 */
	public void setFideiussioni(
			it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.FideiussioneDTO[] val) {
		fideiussioni = val;
	}

	////{PROTECTED REGION ID(R-584470963) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo fideiussioni. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.FideiussioneDTO[] getFideiussioni() {
		return fideiussioni;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.RichiestaErogazioneDTO[] richiesteErogazione = null;

	/**
	 * @generated
	 */
	public void setRichiesteErogazione(
			it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.RichiestaErogazioneDTO[] val) {
		richiesteErogazione = val;
	}

	////{PROTECTED REGION ID(R764961931) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo richiesteErogazione. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.RichiestaErogazioneDTO[] getRichiesteErogazione() {
		return richiesteErogazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoTotaleRichiesto = null;

	/**
	 * @generated
	 */
	public void setImportoTotaleRichiesto(java.lang.Double val) {
		importoTotaleRichiesto = val;
	}

	////{PROTECTED REGION ID(R1210445735) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoTotaleRichiesto. 
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
	public java.lang.Double getImportoTotaleRichiesto() {
		return importoTotaleRichiesto;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean esito = null;

	/**
	 * @generated
	 */
	public void setEsito(java.lang.Boolean val) {
		esito = val;
	}

	////{PROTECTED REGION ID(R674073306) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo esito. 
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
	public java.lang.Boolean getEsito() {
		return esito;
	}

	/**
	 * @generated
	 */
	private java.lang.String message = null;

	/**
	 * @generated
	 */
	public void setMessage(java.lang.String val) {
		message = val;
	}

	////{PROTECTED REGION ID(R1657834667) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo message. 
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
	public java.lang.String getMessage() {
		return message;
	}

	/**
	 * @generated
	 */
	private java.lang.String[] params = null;

	/**
	 * @generated
	 */
	public void setParams(java.lang.String[] val) {
		params = val;
	}

	////{PROTECTED REGION ID(R-280016798) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo params. 
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
		return "EsitoDatiProgettoDTO [spesaProgetto=" + spesaProgetto + ", fideiussioni="
				+ Arrays.toString(fideiussioni) + ", richiesteErogazione=" + Arrays.toString(richiesteErogazione)
				+ ", importoTotaleRichiesto=" + importoTotaleRichiesto + ", esito=" + esito + ", message=" + message
				+ ", params=" + Arrays.toString(params) + "]";
	}
	////{PROTECTED REGION END////}
	public java.lang.String[] getParams() {
		return params;
	}

	/*PROTECTED REGION ID(R799797231) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
