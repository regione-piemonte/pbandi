/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio;

////{PROTECTED REGION ID(R407054497) ENABLED START////}
/**
 * Inserire qui la documentazione della classe ConfigurationDTO.
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
public class ConfigurationDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long genericTimeout = null;

	/**
	 * @generated
	 */
	public void setGenericTimeout(java.lang.Long val) {
		genericTimeout = val;
	}

	////{PROTECTED REGION ID(R-1831180693) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo genericTimeout. 
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
	public java.lang.Long getGenericTimeout() {
		return genericTimeout;
	}

	/**
	 * @generated
	 */
	private java.lang.Long verificaDichiarazioneDiSpesaTimeout = null;

	/**
	 * @generated
	 */
	public void setVerificaDichiarazioneDiSpesaTimeout(java.lang.Long val) {
		verificaDichiarazioneDiSpesaTimeout = val;
	}

	////{PROTECTED REGION ID(R117577102) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo verificaDichiarazioneDiSpesaTimeout. 
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
	public java.lang.Long getVerificaDichiarazioneDiSpesaTimeout() {
		return verificaDichiarazioneDiSpesaTimeout;
	}

	/**
	 * @generated
	 */
	private java.lang.Long inviaDichiarazioneDiSpesaTimeout = null;

	/**
	 * @generated
	 */
	public void setInviaDichiarazioneDiSpesaTimeout(java.lang.Long val) {
		inviaDichiarazioneDiSpesaTimeout = val;
	}

	////{PROTECTED REGION ID(R-1605630226) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo inviaDichiarazioneDiSpesaTimeout. 
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
	public java.lang.Long getInviaDichiarazioneDiSpesaTimeout() {
		return inviaDichiarazioneDiSpesaTimeout;
	}

	/**
	 * @generated
	 */
	private java.lang.Long configurationCache = null;

	/**
	 * @generated
	 */
	public void setConfigurationCache(java.lang.Long val) {
		configurationCache = val;
	}

	////{PROTECTED REGION ID(R-747923827) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo configurationCache. 
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
	public java.lang.Long getConfigurationCache() {
		return configurationCache;
	}

	/**
	 * @generated
	 */
	private java.lang.Long asynchMapCandidateMaxInstancesNumber = null;

	/**
	 * @generated
	 */
	public void setAsynchMapCandidateMaxInstancesNumber(java.lang.Long val) {
		asynchMapCandidateMaxInstancesNumber = val;
	}

	////{PROTECTED REGION ID(R667954679) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo asynchMapCandidateMaxInstancesNumber. 
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
	public java.lang.Long getAsynchMapCandidateMaxInstancesNumber() {
		return asynchMapCandidateMaxInstancesNumber;
	}

	/**
	 * @generated
	 */
	private java.lang.Long lockDocumentoScadutoTimeout = null;

	/**
	 * @generated
	 */
	public void setLockDocumentoScadutoTimeout(java.lang.Long val) {
		lockDocumentoScadutoTimeout = val;
	}

	////{PROTECTED REGION ID(R-1708380884) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo lockDocumentoScadutoTimeout. 
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
	public java.lang.Long getLockDocumentoScadutoTimeout() {
		return lockDocumentoScadutoTimeout;
	}

	/**
	 * @generated
	 */
	private java.lang.Long checklistSelectForUpdateWaitTimeout = null;

	/**
	 * @generated
	 */
	public void setChecklistSelectForUpdateWaitTimeout(java.lang.Long val) {
		checklistSelectForUpdateWaitTimeout = val;
	}

	////{PROTECTED REGION ID(R827270395) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo checklistSelectForUpdateWaitTimeout. 
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
	public java.lang.Long getChecklistSelectForUpdateWaitTimeout() {
		return checklistSelectForUpdateWaitTimeout;
	}

	/**
	 * @generated
	 */
	private java.lang.Long ggCalcoloDtBloccoAccessoUPP = null;

	/**
	 * @generated
	 */
	public void setGgCalcoloDtBloccoAccessoUPP(java.lang.Long val) {
		ggCalcoloDtBloccoAccessoUPP = val;
	}

	////{PROTECTED REGION ID(R1061363424) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo ggCalcoloDtBloccoAccessoUPP. 
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
	public java.lang.Long getGgCalcoloDtBloccoAccessoUPP() {
		return ggCalcoloDtBloccoAccessoUPP;
	}

	/**
	 * @generated
	 */
	private java.lang.String msgAccessoBloccato = null;

	/**
	 * @generated
	 */
	public void setMsgAccessoBloccato(java.lang.String val) {
		msgAccessoBloccato = val;
	}

	////{PROTECTED REGION ID(R-1981158078) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo msgAccessoBloccato. 
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
	public java.lang.String getMsgAccessoBloccato() {
		return msgAccessoBloccato;
	}

	/**
	 * @generated
	 */
	private java.lang.String msgDataBloccoAccesso = null;

	/**
	 * @generated
	 */
	public void setMsgDataBloccoAccesso(java.lang.String val) {
		msgDataBloccoAccesso = val;
	}

	////{PROTECTED REGION ID(R-1178996553) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo msgDataBloccoAccesso. 
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
	public java.lang.String getMsgDataBloccoAccesso() {
		return msgDataBloccoAccesso;
	}

	/**
	 * @generated
	 */
	private java.lang.Long maxNumRecordImpegni = null;

	/**
	 * @generated
	 */
	public void setMaxNumRecordImpegni(java.lang.Long val) {
		maxNumRecordImpegni = val;
	}

	////{PROTECTED REGION ID(R746939253) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo maxNumRecordImpegni. 
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
	public java.lang.Long getMaxNumRecordImpegni() {
		return maxNumRecordImpegni;
	}

	/**
	 * @generated
	 */
	private java.lang.Long minImportoLiquidabileCentesimiBilancio = null;

	/**
	 * @generated
	 */
	public void setMinImportoLiquidabileCentesimiBilancio(java.lang.Long val) {
		minImportoLiquidabileCentesimiBilancio = val;
	}

	////{PROTECTED REGION ID(R1001726330) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo minImportoLiquidabileCentesimiBilancio. 
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
	public java.lang.Long getMinImportoLiquidabileCentesimiBilancio() {
		return minImportoLiquidabileCentesimiBilancio;
	}

	/**
	 * @generated
	 */
	private java.lang.Long maxNumRecordImpegniAllineabiliBil = null;

	/**
	 * @generated
	 */
	public void setMaxNumRecordImpegniAllineabiliBil(java.lang.Long val) {
		maxNumRecordImpegniAllineabiliBil = val;
	}

	////{PROTECTED REGION ID(R1624002900) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo maxNumRecordImpegniAllineabiliBil. 
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
	public java.lang.Long getMaxNumRecordImpegniAllineabiliBil() {
		return maxNumRecordImpegniAllineabiliBil;
	}

	/**
	 * @generated
	 */
	private java.lang.Long timeoutAvvioProgetto = null;

	/**
	 * @generated
	 */
	public void setTimeoutAvvioProgetto(java.lang.Long val) {
		timeoutAvvioProgetto = val;
	}

	////{PROTECTED REGION ID(R959908075) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo timeoutAvvioProgetto. 
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
	public java.lang.Long getTimeoutAvvioProgetto() {
		return timeoutAvvioProgetto;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean verificaCookie = null;

	/**
	 * @generated
	 */
	public void setVerificaCookie(java.lang.Boolean val) {
		verificaCookie = val;
	}

	////{PROTECTED REGION ID(R1018875564) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo verificaCookie. 
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
	public java.lang.Boolean getVerificaCookie() {
		return verificaCookie;
	}

	/**
	 * @generated
	 */
	private java.lang.String archivioAllowedFileExtensions = null;

	/**
	 * @generated
	 */
	public void setArchivioAllowedFileExtensions(java.lang.String val) {
		archivioAllowedFileExtensions = val;
	}

	////{PROTECTED REGION ID(R1272824974) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo archivioAllowedFileExtensions. 
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
	public java.lang.String getArchivioAllowedFileExtensions() {
		return archivioAllowedFileExtensions;
	}

	/**
	 * @generated
	 */
	private java.lang.Long archivioFileSizeLimit = null;

	/**
	 * @generated
	 */
	public void setArchivioFileSizeLimit(java.lang.Long val) {
		archivioFileSizeLimit = val;
	}

	////{PROTECTED REGION ID(R-1925002700) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo archivioFileSizeLimit. 
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
	public java.lang.Long getArchivioFileSizeLimit() {
		return archivioFileSizeLimit;
	}

	/**
	 * @generated
	 */
	private java.lang.Long archivioFileSizeLimitUpload = null;

	/**
	 * @generated
	 */
	public void setArchivioFileSizeLimitUpload(java.lang.Long val) {
		archivioFileSizeLimitUpload = val;
	}

	////{PROTECTED REGION ID(R347098453) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo archivioFileSizeLimitUpload. 
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
	public java.lang.Long getArchivioFileSizeLimitUpload() {
		return archivioFileSizeLimitUpload;
	}

	/*PROTECTED REGION ID(R-165838048) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
