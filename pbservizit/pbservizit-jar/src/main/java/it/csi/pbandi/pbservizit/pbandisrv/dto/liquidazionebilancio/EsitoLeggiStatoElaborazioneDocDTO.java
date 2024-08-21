/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio;

////{PROTECTED REGION ID(R1016453248) ENABLED START////}
/**
 * Inserire qui la documentazione della classe EsitoLeggiStatoElaborazioneDocDTO.
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
public class EsitoLeggiStatoElaborazioneDocDTO implements java.io.Serializable {
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

	////{PROTECTED REGION ID(R-813523786) ENABLED START////}
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
	private java.lang.String numeroAtto = null;

	/**
	 * @generated
	 */
	public void setNumeroAtto(java.lang.String val) {
		numeroAtto = val;
	}

	////{PROTECTED REGION ID(R1288700074) ENABLED START////}
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
	private java.lang.String errore = null;

	/**
	 * @generated
	 */
	public void setErrore(java.lang.String val) {
		errore = val;
	}

	////{PROTECTED REGION ID(R549906397) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo errore. 
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
	public java.lang.String getErrore() {
		return errore;
	}

	/**
	 * @generated
	 */
	private java.lang.String esitoContabilia = null;

	/**
	 * @generated
	 */
	public void setEsitoContabilia(java.lang.String val) {
		esitoContabilia = val;
	}

	////{PROTECTED REGION ID(R-1952710620) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo esitoContabilia. 
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
	public java.lang.String getEsitoContabilia() {
		return esitoContabilia;
	}

	/**
	 * @generated
	 */
	private java.lang.String statoElaborazioneContabilia = null;

	/**
	 * @generated
	 */
	public void setStatoElaborazioneContabilia(java.lang.String val) {
		statoElaborazioneContabilia = val;
	}

	////{PROTECTED REGION ID(R2075455338) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo statoElaborazioneContabilia. 
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
		builder.append("EsitoLeggiStatoElaborazioneDocDTO [esito=");
		builder.append(esito);
		builder.append(", numeroAtto=");
		builder.append(numeroAtto);
		builder.append(", errore=");
		builder.append(errore);
		builder.append(", esitoContabilia=");
		builder.append(esitoContabilia);
		builder.append(", statoElaborazioneContabilia=");
		builder.append(statoElaborazioneContabilia);
		builder.append("]");
		return builder.toString();
	}
	////{PROTECTED REGION END////}
	public java.lang.String getStatoElaborazioneContabilia() {
		return statoElaborazioneContabilia;
	}

	/*PROTECTED REGION ID(R-1605923423) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
