/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio;

import java.util.Arrays;

////{PROTECTED REGION ID(R1553019961) ENABLED START////}
/**
 * Inserire qui la documentazione della classe EsitoCreaAttoDTO.
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
public class EsitoCreaAttoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

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

	////{PROTECTED REGION ID(R-115823459) ENABLED START////}
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

	////{PROTECTED REGION ID(R-1518712402) ENABLED START////}
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

	////{PROTECTED REGION ID(R1002987263) ENABLED START////}
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
	////{PROTECTED REGION END////}
	public java.lang.String[] getParams() {
		return params;
	}

	/**
	 * @generated
	 */
	private java.lang.String esitoOperazione = null;

	/**
	 * @generated
	 */
	public void setEsitoOperazione(java.lang.String val) {
		esitoOperazione = val;
	}

	////{PROTECTED REGION ID(R-354841247) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo esitoOperazione. 
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
	public java.lang.String getEsitoOperazione() {
		return esitoOperazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String numeroAtto = null;

	/**
	 * @generated
	 */
	public void setNumeroAtto(java.lang.String val) {
		numeroAtto = val;
	}

	////{PROTECTED REGION ID(R-740636381) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroAtto. 
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
	public java.lang.String getNumeroAtto() {
		return numeroAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.String descEsitoOperazione = null;

	/**
	 * @generated
	 */
	public void setDescEsitoOperazione(java.lang.String val) {
		descEsitoOperazione = val;
	}

	////{PROTECTED REGION ID(R1674086160) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descEsitoOperazione. 
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
	public java.lang.String getDescEsitoOperazione() {
		return descEsitoOperazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String annoAtto = null;

	/**
	 * @generated
	 */
	public void setAnnoAtto(java.lang.String val) {
		annoAtto = val;
	}

	////{PROTECTED REGION ID(R-24513291) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo annoAtto. 
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
	public java.lang.String getAnnoAtto() {
		return annoAtto;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataAtto = null;

	/**
	 * @generated
	 */
	public void setDataAtto(java.util.Date val) {
		dataAtto = val;
	}

	////{PROTECTED REGION ID(R-1879817231) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataAtto. 
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
	public java.util.Date getDataAtto() {
		return dataAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.String idOperazioneAsincrona = null;

	/**
	 * @generated
	 */
	public void setIdOperazioneAsincrona(java.lang.String val) {
		idOperazioneAsincrona = val;
	}

	////{PROTECTED REGION ID(R1363033316) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idOperazioneAsincrona. 
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
	public java.lang.String getIdOperazioneAsincrona() {
		return idOperazioneAsincrona;
	}

	/**
	 * @generated
	 */
	private java.lang.String numeroDocSpesa = null;

	/**
	 * @generated
	 */
	public void setNumeroDocSpesa(java.lang.String val) {
		numeroDocSpesa = val;
	}

	////{PROTECTED REGION ID(R2047302739) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroDocSpesa. 
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
		StringBuilder builder = new StringBuilder();
		builder.append("EsitoCreaAttoDTO [esito=");
		builder.append(esito);
		builder.append(", message=");
		builder.append(message);
		builder.append(", params=");
		builder.append(Arrays.toString(params));
		builder.append(", esitoOperazione=");
		builder.append(esitoOperazione);
		builder.append(", numeroAtto=");
		builder.append(numeroAtto);
		builder.append(", descEsitoOperazione=");
		builder.append(descEsitoOperazione);
		builder.append(", annoAtto=");
		builder.append(annoAtto);
		builder.append(", dataAtto=");
		builder.append(dataAtto);
		builder.append(", idOperazioneAsincrona=");
		builder.append(idOperazioneAsincrona);
		builder.append(", numeroDocSpesa=");
		builder.append(numeroDocSpesa);
		builder.append("]");
		return builder.toString();
	}
	////{PROTECTED REGION END////}
	public java.lang.String getNumeroDocSpesa() {
		return numeroDocSpesa;
	}

	/*PROTECTED REGION ID(R-564864270) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
