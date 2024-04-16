/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto;

////{PROTECTED REGION ID(R344125117) ENABLED START////}
/**
 * Inserire qui la documentazione della classe BeneficiarioCspDTO.
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
public class BeneficiarioCspDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String codiceFiscaleSoggetto = null;

	/**
	 * @generated
	 */
	public void setCodiceFiscaleSoggetto(java.lang.String val) {
		codiceFiscaleSoggetto = val;
	}

	////{PROTECTED REGION ID(R-360107011) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceFiscaleSoggetto. 
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
	public java.lang.String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}

	/**
	 * @generated
	 */
	private java.lang.String iban = null;

	/**
	 * @generated
	 */
	public void setIban(java.lang.String val) {
		iban = val;
	}

	////{PROTECTED REGION ID(R670334787) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo iban. 
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
	public java.lang.String getIban() {
		return iban;
	}

	/**
	 * @generated
	 */
	private java.lang.String denominazioneEnteGiuridico = null;

	/**
	 * @generated
	 */
	public void setDenominazioneEnteGiuridico(java.lang.String val) {
		denominazioneEnteGiuridico = val;
	}

	////{PROTECTED REGION ID(R-371400648) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo denominazioneEnteGiuridico. 
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
	public java.lang.String getDenominazioneEnteGiuridico() {
		return denominazioneEnteGiuridico;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtCostituzione = null;

	/**
	 * @generated
	 */
	public void setDtCostituzione(java.util.Date val) {
		dtCostituzione = val;
	}

	////{PROTECTED REGION ID(R-1951442457) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtCostituzione. 
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
	public java.util.Date getDtCostituzione() {
		return dtCostituzione;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idFormaGiuridica = null;

	/**
	 * @generated
	 */
	public void setIdFormaGiuridica(java.lang.Long val) {
		idFormaGiuridica = val;
	}

	////{PROTECTED REGION ID(R682382088) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idFormaGiuridica. 
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
	public java.lang.Long getIdFormaGiuridica() {
		return idFormaGiuridica;
	}

	/**
	 * @generated
	 */
	private java.lang.String descFormaGiuridica = null;

	/**
	 * @generated
	 */
	public void setDescFormaGiuridica(java.lang.String val) {
		descFormaGiuridica = val;
	}

	////{PROTECTED REGION ID(R-438156610) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descFormaGiuridica. 
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
	public java.lang.String getDescFormaGiuridica() {
		return descFormaGiuridica;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idSettoreAttivita = null;

	/**
	 * @generated
	 */
	public void setIdSettoreAttivita(java.lang.Long val) {
		idSettoreAttivita = val;
	}

	////{PROTECTED REGION ID(R-64925696) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSettoreAttivita. 
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
	public java.lang.Long getIdSettoreAttivita() {
		return idSettoreAttivita;
	}

	/**
	 * @generated
	 */
	private java.lang.String descSettore = null;

	/**
	 * @generated
	 */
	public void setDescSettore(java.lang.String val) {
		descSettore = val;
	}

	////{PROTECTED REGION ID(R1725747874) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descSettore. 
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
	public java.lang.String getDescSettore() {
		return descSettore;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idAttivitaAteco = null;

	/**
	 * @generated
	 */
	public void setIdAttivitaAteco(java.lang.Long val) {
		idAttivitaAteco = val;
	}

	////{PROTECTED REGION ID(R-1679611042) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idAttivitaAteco. 
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
	public java.lang.Long getIdAttivitaAteco() {
		return idAttivitaAteco;
	}

	/**
	 * @generated
	 */
	private java.lang.String descAteco = null;

	/**
	 * @generated
	 */
	public void setDescAteco(java.lang.String val) {
		descAteco = val;
	}

	////{PROTECTED REGION ID(R-707132336) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descAteco. 
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
	public java.lang.String getDescAteco() {
		return descAteco;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SedeLegaleBeneficiarioCspDTO[] sediLegali = null;

	/**
	 * @generated
	 */
	public void setSediLegali(
			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SedeLegaleBeneficiarioCspDTO[] val) {
		sediLegali = val;
	}

	////{PROTECTED REGION ID(R1859827844) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo sediLegali. 
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
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SedeLegaleBeneficiarioCspDTO[] getSediLegali() {
		return sediLegali;
	}

	/**
	 * @generated
	 */
	private java.lang.String dtCostituzioneStringa = null;

	/**
	 * @generated
	 */
	public void setDtCostituzioneStringa(java.lang.String val) {
		dtCostituzioneStringa = val;
	}

	////{PROTECTED REGION ID(R-1186382199) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtCostituzioneStringa. 
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
	public java.lang.String getDtCostituzioneStringa() {
		return dtCostituzioneStringa;
	}

	/*PROTECTED REGION ID(R30265198) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
